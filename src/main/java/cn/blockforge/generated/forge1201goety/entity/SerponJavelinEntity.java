package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponJavelinEntity;

public class SerponJavelinEntity extends AbstractArrow {

    private static final float THROW_DAMAGE = 8.0F;

    private float damageOverride = -1.0F;

    public SerponJavelinEntity(EntityType<? extends SerponJavelinEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SerponJavelinEntity(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntityTypes.SERPON_JAVELIN.get(), owner, level);
        setBaseDamage(THROW_DAMAGE);
        this.pickup = Pickup.DISALLOWED;
    }

    public void setDamageOverride(float damage) {
        this.damageOverride = damage;
    }

    @Override
    public ItemStack getPickupItem() {
        return AbstractJerotesServant.jerotesItem("serpon_javelin");
    }

    @Override
    public void tick() {
        super.tick();
        if (!inGround) {
            level().addParticle(ParticleTypes.FLAME, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canHitEntity(Entity target) {
        if (getOwner() instanceof AbstractJerotesServant servant && servant.isAlliedTo(target)) {
            return false;
        }
        return super.canHitEntity(target);
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        if (level().isClientSide) {
            discard();
            return;
        }
        Entity target = result.getEntity();
        if (isIgnoredTarget(target)) {
            return;
        }
        if (getOwner() instanceof AbstractJerotesServant servant && servant.isAlliedTo(target)) {
            return;
        }
        target.hurt(JerotesMagicDamage.bypassesCooldownShoot(this, getOwner()),
                this.damageOverride >= 0.0F ? this.damageOverride : THROW_DAMAGE);
        if (target instanceof LivingEntity living) {
            doPostHurtEffects(living);
        }
        if (!isSilent()) {
            level().playSound(null, getX(), getY(), getZ(), SoundEvents.TRIDENT_HIT,
                    SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
    }

    private boolean noBlockFire;

    public void setNoBlockFire(boolean noBlockFire) {
        this.noBlockFire = noBlockFire;
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (level().isClientSide) {
            return;
        }
        if (!this.noBlockFire && level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            BlockPos pos = result.getBlockPos().relative(result.getDirection());
            if (level().isEmptyBlock(pos)) {
                level().setBlockAndUpdate(pos, BaseFireBlock.getState(level(), pos));
            }
        }
    }

    @Override
    public void onHit(HitResult result) {
        super.onHit(result);
        if (level().isClientSide || result.getType() == HitResult.Type.MISS) {
            return;
        }
        if (result instanceof EntityHitResult entityHit && isIgnoredTarget(entityHit.getEntity())) {
            return;
        }
        discard();
    }

    private static boolean isIgnoredTarget(Entity target) {
        return target instanceof SerponCombatExecuter
                || AbstractJerotesServant.isJerotesEntity(target, "serpon_combat_executer");
    }
}
