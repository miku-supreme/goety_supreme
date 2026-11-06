package cn.blockforge.generated.goetysupreme.block;

import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import fuzs.mutantmonsters.world.entity.projectile.ThrowableBlock;
import java.util.UUID;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.block.AbominableSnowGolemSnowBlock;
import cn.blockforge.generated.goetysupreme.entity.HarmfulSnowball;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.block.MutantSnowGolemServantIceBlock;

public final class AbominableSnowGolemSnowBlock extends ThrowableBlock {

    private static final double SPLASH_RADIUS = 3.5D;

    private static final double SPLASH_DISTANCE_SQR = SPLASH_RADIUS * SPLASH_RADIUS;

    private static final int SPLASH_PARTICLE_COUNT = 96;

    private static final int SPLASH_SNOWFLAKE_COUNT = 40;

    private static final int CHILL_TICKS = 100;

    private float damage;

    public AbominableSnowGolemSnowBlock(EntityType<? extends AbominableSnowGolemSnowBlock> entityType, Level level) {
        super(entityType, level);
    }

    public AbominableSnowGolemSnowBlock(MutantSnowGolem standIn, AbominableSnowGolemServant servant) {
        this(ModEntityTypes.ABOMINABLE_SNOW_GOLEM_SNOW_BLOCK.get(), servant.level());
        ThrowableBlock trajectory = new ThrowableBlock(standIn);
        CompoundTag state = new CompoundTag();
        trajectory.saveWithoutId(state);
        load(state);
        setUUID(UUID.randomUUID());
        setOwner(servant);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    private AbominableSnowGolemServant servant() {
        return getOwner() instanceof AbominableSnowGolemServant servant ? servant : null;
    }

    @Override
    public BlockState getBlockState() {
        return Blocks.SNOW_BLOCK.defaultBlockState();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", damage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Damage")) {
            damage = tag.getFloat("Damage");
        }
    }

    @Override
    public boolean canHitEntity(Entity target) {
        AbominableSnowGolemServant servant = servant();
        if (servant == null || target == this || isAlly(servant, target)) {
            return false;
        }
        return super.canHitEntity(target);
    }

    private boolean isAlly(AbominableSnowGolemServant servant, Entity candidate) {
        if (candidate == servant) {
            return true;
        }
        LivingEntity master = servant.getTrueOwner();
        if (master != null && candidate == master) {
            return true;
        }
        return candidate instanceof LivingEntity living && servant.isAlliedTo(living);
    }

    @Override
    public void onHit(HitResult result) {
        Level level = level();
        if (level.isClientSide()) {
            discard();
            return;
        }
        AbominableSnowGolemServant servant = servant();
        if (servant == null) {
            discard();
            return;
        }
        LivingEntity owner = servant.getTrueOwner() != null ? servant.getTrueOwner() : servant;
        Entity directHit = result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult entityHit
                ? entityHit.getEntity() : null;
        AABB box = getBoundingBox().inflate(SPLASH_RADIUS, SPLASH_RADIUS, SPLASH_RADIUS);
        for (Mob mob : level.getEntitiesOfClass(Mob.class, box, this::canDamage)) {
            if (mob == servant || mob == directHit) {
                continue;
            }
            if (inSplash(mob, SPLASH_RADIUS, SPLASH_RADIUS)) {
                mob.hurt(ModDamageSource.summonAttack(servant, owner), servant.snowBlockAoeDamage());
                servant.applyMeleeChill(mob, CHILL_TICKS, 0);
            }
        }
        if (directHit instanceof LivingEntity living && canDamage(living)) {
            living.hurt(ModDamageSource.summonAttack(servant, owner), damage);
            servant.applyMeleeChill(living, CHILL_TICKS, 0);
        }
        level.broadcastEntityEvent(this, (byte) 3);
        playSound(Blocks.SNOW_BLOCK.defaultBlockState().getSoundType().getBreakSound(), 1.5F,
                0.8F + (random.nextFloat() - random.nextFloat()) * 0.2F);
        discard();
    }

    private boolean inSplash(Entity mob, double radius, double height) {
        double dx = mob.getX() - getX();
        double dz = mob.getZ() - getZ();
        double dy = mob.getBoundingBox().getCenter().y - getY();
        return dx * dx + dz * dz <= radius * radius && Math.abs(dy) <= height;
    }

    private boolean canDamage(Entity candidate) {
        AbominableSnowGolemServant servant = servant();
        if (servant == null || candidate == null || candidate == this || !candidate.isAlive()) {
            return false;
        }
        return !isAlly(servant, candidate);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id != 3) {
            super.handleEntityEvent(id);
            return;
        }
        if (!level().isClientSide) {
            return;
        }
        BlockState snow = Blocks.SNOW_BLOCK.defaultBlockState();
        for (int i = 0; i < 60; ++i) {
            double x = getX() + (random.nextFloat() * getBbWidth() * 2.0F) - getBbWidth();
            double y = getY() + 0.5D + random.nextFloat() * getBbHeight();
            double z = getZ() + (random.nextFloat() * getBbWidth() * 2.0F) - getBbWidth();
            double vx = (random.nextFloat() - random.nextFloat()) * 3.0D;
            double vy = 0.5D + random.nextFloat() * 2.0D;
            double vz = (random.nextFloat() - random.nextFloat()) * 3.0D;
            level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, snow), x, y, z, vx, vy, vz);
        }
        for (int i = 0; i < SPLASH_PARTICLE_COUNT; ++i) {
            double angle = random.nextDouble() * (Math.PI * 2.0D);
            double radius = 0.35D + random.nextDouble() * SPLASH_RADIUS;
            double x = getX() + Math.cos(angle) * radius;
            double z = getZ() + Math.sin(angle) * radius;
            double y = getY() + 0.2D + random.nextDouble() * 0.9D;
            double speed = 0.18D + random.nextDouble() * 0.22D;
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            double vy = 0.12D + random.nextDouble() * 0.24D;
            level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, snow), x, y, z, vx, vy, vz);
        }
        for (int i = 0; i < SPLASH_SNOWFLAKE_COUNT; ++i) {
            double angle = (Math.PI * 2.0D) * i / SPLASH_SNOWFLAKE_COUNT + random.nextDouble() * 0.12D;
            double radius = SPLASH_RADIUS * (0.82D + random.nextDouble() * 0.18D);
            double x = getX() + Math.cos(angle) * radius;
            double z = getZ() + Math.sin(angle) * radius;
            level().addParticle(ParticleTypes.SNOWFLAKE, x, getY() + 0.1D, z,
                    0.0D, 0.02D + random.nextDouble() * 0.03D, 0.0D);
        }
    }
}
