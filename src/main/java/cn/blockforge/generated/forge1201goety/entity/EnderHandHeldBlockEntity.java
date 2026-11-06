package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import fuzs.mutantmonsters.world.entity.projectile.ThrowableBlock;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import cn.blockforge.generated.goetysupreme.config.EndermanHandFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.EnderHandHeldBlockEntity;
import cn.blockforge.generated.goetysupreme.spell.EndermanHandFocusSpell;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.block.MutantEndermanServantThrownBlock;

public final class EnderHandHeldBlockEntity extends ThrowableBlock {

    public static final String SEQ_KEY = "GoetySupremeHeldBlockSeq";

    private int ownerMissingTicks;

    public EnderHandHeldBlockEntity(EntityType<? extends EnderHandHeldBlockEntity> type, Level level) {
        super(type, level);
    }

    public static EnderHandHeldBlockEntity pickup(ServerLevel level, Player player, BlockState state, BlockPos pos,
            int seq) {
        ThrowableBlock template = new ThrowableBlock(player, state, pos);
        CompoundTag tag = new CompoundTag();
        template.saveWithoutId(tag);
        EnderHandHeldBlockEntity carried = new EnderHandHeldBlockEntity(
                ModEntityTypes.ENDER_HAND_HELD_BLOCK.get(), level);
        carried.load(tag);
        carried.setUUID(UUID.randomUUID());
        carried.setOwner(player);
        carried.getPersistentData().putInt(SEQ_KEY, seq);
        level.addFreshEntity(carried);
        return carried;
    }

    public void releaseForThrow() {
        if (this.isHeld()) {
            writeHeld(false);
        }
    }

    @Override
    public void tick() {
        boolean server = !this.level().isClientSide();
        if (server && this.isHeld()) {
            if (this.isStale()) {
                this.discard(); // 玩家已另举了新的：旧的静默消散，绝不落地成第二份方块
                return;
            }
            if (this.getOwner() == null && ++this.ownerMissingTicks > 200) {
                this.discard(); // 主人长期离线：底账还在玩家数据里，登录时补挂
                return;
            }
        }
        super.tick();
        if (server && !this.isHeld() && this.wantHeld()) {
            writeHeld(true);
        }
    }

    private boolean wantHeld() {
        if (!(this.getOwner() instanceof Player player)) {
            return false;
        }
        CompoundTag data = player.getPersistentData();
        return data.contains(EndermanHandFocusSpell.HELD_BLOCK_TAG)
                && data.getInt(SEQ_KEY) == this.getPersistentData().getInt(SEQ_KEY)
                && !player.isSpectator() && player.isAlive();
    }

    private boolean isStale() {
        if (!(this.getOwner() instanceof Player p)) {
            return false;
        }
        if (isHoldingHandWeapon(p)) {
            return false;
        }
        return p.getPersistentData().getInt(SEQ_KEY) != this.getPersistentData().getInt(SEQ_KEY);
    }

    private static boolean isHoldingHandWeapon(Player player) {
        return matchesHandWeapon(player.getMainHandItem()) || matchesHandWeapon(player.getOffhandItem());
    }

    private static boolean matchesHandWeapon(net.minecraft.world.item.ItemStack stack) {
        return net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem())
                .equals(ENDER_SOUL_HAND_ID);
    }

    private static final net.minecraft.resources.ResourceLocation ENDER_SOUL_HAND_ID =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mutantmonsters", "endersoul_hand");

    private void writeHeld(boolean held) {
        CompoundTag tag = new CompoundTag();
        this.saveWithoutId(tag);
        tag.putBoolean("Held", held);
        this.load(tag);
    }

    @Override
    public void onHit(HitResult result) {
        if (level().isClientSide() || this.isHeld()) {
            return;
        }
        Player owner = getOwner() instanceof Player player ? player : null;
        int potency = getPersistentData().getInt("GoetySupremePotency");
        float direct = (float) (EndermanHandFocusConfig.BLOCK_DAMAGE.get().doubleValue()
                + EndermanHandFocusConfig.POTENCY_BONUS.get().doubleValue() * potency);
        float area = (float) EndermanHandFocusConfig.BLOCK_AREA_DAMAGE.get().doubleValue();
        boolean voidEclipse = getPersistentData().getBoolean("GoetySupremeVoidEclipse");
        if (result instanceof EntityHitResult hit && hit.getEntity() instanceof LivingEntity target
                && canDamage(target)) {
            hurtWithConfig(target, direct, owner, voidEclipse);
        }
        if (area > 0.0F) {
            for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class,
                    getBoundingBox().inflate(2.0D), this::canDamage)) {
                hurtWithConfig(victim, area, owner, voidEclipse);
            }
        }
        level().broadcastEntityEvent(this, (byte) 3);
        BlockState state = getBlockState();
        if (state != null && !state.isAir()) {
            playSound(state.getSoundType().getBreakSound(), 0.8F,
                    0.8F + (random.nextFloat() - random.nextFloat()) * 0.2F);
        }
        discard();
    }

    private boolean canDamage(net.minecraft.world.entity.Entity target) {
        if (!(target instanceof LivingEntity) || !target.isAlive() || target == this.getOwner()
                || target.isSpectator()) {
            return false;
        }
        LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
        return owner == null || !owner.isAlliedTo(target);
    }

    private void hurtWithConfig(LivingEntity target, float damage, Player owner, boolean voidEclipse) {
        if (damage <= 0.0F) {
            return;
        }
        target.hurt(level().damageSources().mobProjectile(this, owner), damage);
        int duration = EndermanHandFocusConfig.VOID_ECLIPSE_DURATION.get();
        if (voidEclipse && duration > 0 && GoetyEffects.VOID_TOUCHED.isPresent()
                && target.canBeAffected(new MobEffectInstance(GoetyEffects.VOID_TOUCHED.get(), duration, 0))) {
            target.addEffect(new MobEffectInstance(GoetyEffects.VOID_TOUCHED.get(), duration, 0));
        }
    }
}
