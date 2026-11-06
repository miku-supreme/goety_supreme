package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.spell.CorrosiveIceSpikeFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.CorrosiveIceSpikeServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverBreathServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class CorrosiveIceSpikeServantEntity extends ThrowableItemProjectile {

    private static final int MAX_LIFE = 80;

    public static final double GRAVITY = 0.03D;

    public static final double DRAG = 0.99D;

    private int spellLevel = 5;

    private float damageOverride = -1.0F;

    public CorrosiveIceSpikeServantEntity(EntityType<? extends CorrosiveIceSpikeServantEntity> type, Level level) {
        super(type, level);
    }

    public CorrosiveIceSpikeServantEntity(Level level, LivingEntity shooter) {
        super(ModEntityTypes.CORROSIVE_ICE_SPIKE_SERVANT.get(), shooter, level);
    }

    public void setSpellLevel(int level) {
        this.spellLevel = level;
    }

    public void setDamageOverride(float damage) {
        this.damageOverride = damage;
    }

    @Override
    public Item getDefaultItem() {
        ItemStack stack = AbstractJerotesServant.jerotesItem("corrosive_ice_spike");
        return stack.isEmpty() ? Items.ICE : stack.getItem();
    }

    @Override
    public float getGravity() {
        return (float) GRAVITY;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(),
                    0.0D, 0.0D, 0.0D);
            return;
        }
        if (this.tickCount > MAX_LIFE) {
            this.discard();
        }
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level().isClientSide) {
            return;
        }
        Entity owner = this.getOwner();
        if (result.getEntity() instanceof LivingEntity target) {
            if (target == owner || (owner != null && owner.isAlliedTo(target))) {
                return;
            }
            target.invulnerableTime = 0;
            target.hurt(JerotesMagicDamage.freezeMagic(this, owner),
                    this.damageOverride >= 0.0F ? this.damageOverride
                            : CorrosiverBreathServantEntity.rollDice(4 + (this.spellLevel - 3), 4)
                                    * CorrosiverServant.ownerSkillScale(owner));
            int freezeTime = 6 + (this.spellLevel - 1) * 3;
            int frozen = 140 + 20 * freezeTime;
            if (target.getTicksFrozen() < frozen) {
                target.setTicksFrozen(frozen);
            }
            MobEffect corrosive = net.minecraftforge.registries.ForgeRegistries.MOB_EFFECTS.getValue(
                    net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("jerotes", "corrosive"));
            target.addEffect(new MobEffectInstance(
                    corrosive == null ? MobEffects.MOVEMENT_SLOWDOWN : corrosive,
                    20, 0, false, corrosive != null));
        }
        this.discard();
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (this.level().isClientSide) {
            return;
        }
        if (this.level() instanceof ServerLevel server) {
            BlockPos center = result.getBlockPos();
            for (BlockPos pos : BlockPos.betweenClosed(center.offset(-2, -2, -2), center.offset(2, 2, 2))) {
                if (pos.distSqr(center) > 4.0D) {
                    continue;
                }
                BlockState state = server.getBlockState(pos);
                if (state.getFluidState().is(Fluids.WATER)) {
                    server.setBlockAndUpdate(pos, Blocks.FROSTED_ICE.defaultBlockState());
                } else if (isFire(state)) {
                    server.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                }
            }
        }
        this.discard();
    }

    private static boolean isFire(BlockState state) {
        return state.is(Blocks.FIRE) || state.is(Blocks.SOUL_FIRE)
                || state.is(Blocks.CAMPFIRE) || state.is(Blocks.SOUL_CAMPFIRE)
                || state.is(Blocks.TORCH) || state.is(Blocks.WALL_TORCH)
                || state.is(Blocks.SOUL_TORCH) || state.is(Blocks.SOUL_WALL_TORCH)
                || state.is(Blocks.CANDLE) || state.is(Blocks.CANDLE_CAKE);
    }

    @Override
    public boolean isPickable() {
        return false;
    }
}
