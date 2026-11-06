package cn.blockforge.generated.goetysupreme.block;

import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import fuzs.mutantmonsters.world.entity.projectile.ThrowableBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.IceShardFocusConfig;
import cn.blockforge.generated.goetysupreme.util.MutantThrowStandIns;
import cn.blockforge.generated.goetysupreme.util.SnowballVolley;
import cn.blockforge.generated.goetysupreme.block.SpellIceShardBlock;

public final class SpellIceShardBlock extends ThrowableBlock {

    private static final double BASE_SPLASH_RADIUS = 2.5D;

    private static final double RADIUS_PER_LEVEL = 0.15D;

    private final LivingEntity caster;
    private final int potency;
    private final int radiusLevel;
    private final boolean frostStaff;

    public SpellIceShardBlock(MutantSnowGolem standIn, LivingEntity caster, int potency,
            int radiusLevel, boolean frostStaff) {
        super(standIn);
        this.caster = caster;
        this.potency = Math.max(0, potency);
        this.radiusLevel = Math.max(0, radiusLevel);
        this.frostStaff = frostStaff;
        setOwner(caster);
    }

    private double radiusScale() {
        return 1.0D + RADIUS_PER_LEVEL * this.radiusLevel;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale((float) this.radiusScale());
    }

    @Override
    public boolean canHitEntity(Entity target) {
        if (target == this || target == caster || SnowballVolley.isAlliedTo(caster, target)) {
            return false;
        }
        return super.canHitEntity(target);
    }

    @Override
    public void onHit(HitResult result) {
        Level level = level();
        if (level.isClientSide()) {
            discard();
            return;
        }
        float damage = this.rollBaseDamage()
                + (float) (IceShardFocusConfig.POTENCY_BONUS.get().doubleValue() * potency);
        if (this.frostStaff) {
            damage += IceShardFocusConfig.FROST_STAFF_DAMAGE_BONUS.get().floatValue();
        }
        double splashRadius = BASE_SPLASH_RADIUS * this.radiusScale();
        double splashHeight = splashRadius;
        AABB box = getBoundingBox().inflate(splashRadius, splashHeight, splashRadius);
        for (Mob mob : level.getEntitiesOfClass(Mob.class, box, this::canDamage)) {
            if (mob == caster || !inSplash(mob, splashRadius, splashHeight)) {
                continue;
            }
            mob.hurt(level().damageSources().thrown(this, caster), damage);
            applyHitEffect(mob);
        }
        if (result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult entityHit) {
            Entity hit = entityHit.getEntity();
            if (hit instanceof LivingEntity living && canDamage(hit)) {
                living.hurt(level().damageSources().thrown(this, caster), damage);
                applyHitEffect(living);
            }
        }
        if (level instanceof ServerLevel server) {
            int particles = (int) Math.round(18 * this.radiusScale());
            server.sendParticles(ParticleTypes.SNOWFLAKE, getX(), getY(), getZ(),
                    particles, splashRadius * 0.45D, 0.25D, splashRadius * 0.45D, 0.02D);
        }
        level.broadcastEntityEvent(this, (byte) 3);
        playBreakSound();
        discard();
    }

    private boolean inSplash(Entity mob, double radius, double height) {
        double dx = mob.getX() - getX();
        double dz = mob.getZ() - getZ();
        double dy = mob.getBoundingBox().getCenter().y - getY();
        return dx * dx + dz * dz <= radius * radius && Math.abs(dy) <= height;
    }

    private float rollBaseDamage() {
        int min = (int) Math.round(IceShardFocusConfig.ICE_DAMAGE.get());
        int max = (int) Math.round(IceShardFocusConfig.ICE_DAMAGE_MAX.get());
        if (max < min) {
            max = min;
        }
        return (float) (min + this.random.nextInt(max - min + 1));
    }

    private void applyHitEffect(LivingEntity victim) {
        if (victim == null || !victim.isAlive()) {
            return;
        }
        net.minecraft.world.effect.MobEffect effect =
                net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN;
        if (com.Polarice3.Goety.utils.CuriosFinder.hasFrostRobes(caster)) {
            effect = com.Polarice3.Goety.common.effects.GoetyEffects.FREEZING.get();
        }
        int duration = IceShardFocusConfig.EFFECT_DURATION.get();
        if (duration > 0) {
            victim.addEffect(new net.minecraft.world.effect.MobEffectInstance(effect, duration, 0), caster);
        }
    }

    private boolean canDamage(Entity candidate) {
        return candidate != null && candidate != this && candidate != caster && candidate.isAlive()
                && !SnowballVolley.isAlliedTo(caster, candidate)
                && (candidate instanceof Mob || candidate instanceof LivingEntity);
    }

    private void playBreakSound() {
        BlockState ice = Blocks.ICE.defaultBlockState();
        playSound(ice.getSoundType().getBreakSound(), 0.8F,
                0.8F + (random.nextFloat() - random.nextFloat()) * 0.2F);
    }
}
