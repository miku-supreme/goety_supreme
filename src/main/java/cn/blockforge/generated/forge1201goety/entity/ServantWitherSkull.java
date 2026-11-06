package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.projectiles.NecroticPool;
import com.Polarice3.Goety.common.entities.util.AlliedEffectCloud;
import com.Polarice3.Goety.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import com.Polarice3.Goety.utils.MobUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.config.WitherServantConfig;
import cn.blockforge.generated.goetysupreme.renderer.AstralSkullParticle;
import cn.blockforge.generated.goetysupreme.entity.EnhancedWitherSkull;
import cn.blockforge.generated.goetysupreme.renderer.EnhancedWitherSkullRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.ServantWitherSkull;
import cn.blockforge.generated.goetysupreme.renderer.ServantWitherSkullRenderer;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public class ServantWitherSkull extends WitherSkull {

    private static final EntityDataAccessor<Boolean> DATA_ASTRAL_SKULL =
            SynchedEntityData.defineId(ServantWitherSkull.class, EntityDataSerializers.BOOLEAN);

    public static final float VANILLA_DIRECT_DAMAGE = 8.0F;

    public static final float SERVANT_DIRECT_DAMAGE = 10.0F;

    public static final int NAMELESS_SKULL_EXPLOSION_RADIUS = 3;
    public static final float NAMELESS_SKULL_EXPLOSION_DAMAGE = 22.0F;

    public static final float MAIN_HEAD_DAMAGE_BONUS = 2.0F;

    private float directDamage = VANILLA_DIRECT_DAMAGE;

    private static final float CLOUD_RADIUS = 3.0F;

    private boolean mainHeadShot;

    private float maxHealthBonusFraction;

    public ServantWitherSkull(EntityType<? extends WitherSkull> type, Level level) {
        super(type, level);
    }

    public ServantWitherSkull(Level level, LivingEntity owner, double dx, double dy, double dz) {
        this(ModEntityTypes.SERVANT_WITHER_SKULL.get(), level);
        this.moveTo(owner.getX(), owner.getY(), owner.getZ(), this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (length != 0.0D) {
            this.xPower = dx / length * 0.1D;
            this.yPower = dy / length * 0.1D;
            this.zPower = dz / length * 0.1D;
        }
        this.setOwner(owner);
        this.setRot(owner.getYRot(), owner.getXRot());
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ASTRAL_SKULL, false);
    }

    public void setAstralSkull(boolean astral) {
        this.entityData.set(DATA_ASTRAL_SKULL, astral);
    }

    public boolean isAstralSkull() {
        return Boolean.TRUE.equals(this.entityData.get(DATA_ASTRAL_SKULL));
    }

    public void setMainHeadShot(boolean mainHeadShot) {
        this.mainHeadShot = mainHeadShot;
    }

    public boolean isMainHeadShot() {
        return this.mainHeadShot;
    }

    public void setDirectDamage(float damage) {
        this.directDamage = Math.max(0.0F, damage);
    }

    public float getDirectDamage() {
        return this.directDamage;
    }

    public void setMaxHealthBonusFraction(float fraction) {
        this.maxHealthBonusFraction = Math.max(0.0F, fraction);
    }

    public float getMaxHealthBonusFraction() {
        return this.maxHealthBonusFraction;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("MainHeadShot", this.mainHeadShot);
        tag.putFloat("DirectDamage", this.directDamage);
        tag.putFloat("MaxHealthBonusFraction", this.maxHealthBonusFraction);
        tag.putBoolean("AstralSkull", this.isAstralSkull());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.mainHeadShot = tag.getBoolean("MainHeadShot");
        if (tag.contains("DirectDamage")) {
            this.directDamage = Math.max(0.0F, tag.getFloat("DirectDamage"));
        }
        this.maxHealthBonusFraction = tag.getFloat("MaxHealthBonusFraction");
        this.setAstralSkull(tag.getBoolean("AstralSkull"));
    }

    private static final int TRAIL_PARTICLES_PER_TICK = 2;

    private static final double TRAIL_SPREAD = 0.22D;

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.isAstralSkull()) {
            this.spawnAstralTrail();
        }
    }

    private void spawnAstralTrail() {
        for (int index = 0; index < TRAIL_PARTICLES_PER_TICK; ++index) {
            double offsetX = (this.random.nextDouble() - 0.5D) * TRAIL_SPREAD;
            double offsetY = (this.random.nextDouble() - 0.5D) * TRAIL_SPREAD;
            double offsetZ = (this.random.nextDouble() - 0.5D) * TRAIL_SPREAD;
            this.level().addParticle(GeneratedMod.ASTRAL_SKULL_SPARK.get(),
                    this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ,
                    -this.xPower * 0.10D + (this.random.nextDouble() - 0.5D) * 0.015D,
                    -this.yPower * 0.10D + (this.random.nextDouble() - 0.5D) * 0.015D,
                    -this.zPower * 0.10D + (this.random.nextDouble() - 0.5D) * 0.015D);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void applyDirectSkullDamage(LivingHurtEvent event) {
        if (event.getAmount() <= 0.0F || event.getSource().is(DamageTypes.EXPLOSION)) {
            return;
        }
        if (event.getSource().getDirectEntity() instanceof ServantWitherSkull skull) {
            float amount = skull.getDirectDamage();
            if (skull.isMainHeadShot()) {
                amount += MAIN_HEAD_DAMAGE_BONUS;
            }
            float fraction = skull.getMaxHealthBonusFraction();
            if (fraction > 0.0F) {
                amount += event.getEntity().getMaxHealth() * fraction;
            }
            event.setAmount(amount);
        }
    }

    @SubscribeEvent
    public static void healOwnerOnKill(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.EXPLOSION)) {
            return;
        }
        if (source.getDirectEntity() instanceof ServantWitherSkull skull
                && skull.getOwner() instanceof LivingEntity owner && owner.isAlive()) {
            if (owner instanceof NamelessWither nameless
                    && event.getEntity().level() instanceof ServerLevel server && !event.getEntity().isRemoved()) {
                nameless.onKill(event.getEntity());
                NecroticPool pool = new NecroticPool(ModEntityType.NECROTIC_POOL.get(), server);
                pool.setWarmupDelayTicks(0);
                pool.setOwner(nameless.getTrueOwner() != null ? nameless.getTrueOwner() : nameless);
                pool.setDamage((float) nameless.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5F);
                pool.setDuration(100);
                pool.setSoundEvent(ModSounds.SOUL_ORB_EXPLODE.get());
                pool.setWarmupColor(0x2f4e17);
                pool.setColor(0x2f4e17);
                pool.setPos(event.getEntity().position().add(0, 0.25F, 0));
                server.addFreshEntity(pool);
            }
            if (owner instanceof WitherServant wither && wither.isAlive()) {
                wither.heal(5.0F);
            }
        }
    }

    public float explosionPower() {
        if (getOwner() instanceof WitherServant servant && servant.isRaging()) {
            return WitherServantConfig.SKULL_EXPLOSION_POWER_RAGING.get().floatValue();
        }
        if (getOwner() instanceof NamelessWither) {
            return 1.5F;
        }
        return 1.0F;
    }

    @Override
    public void onHit(HitResult result) {
        HitResult.Type type = result.getType();
        if (type == HitResult.Type.ENTITY) {
            onHitEntity((EntityHitResult) result);
            level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(),
                    GameEvent.Context.of(this, (BlockState) null));
        } else if (type == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) result;
            onHitBlock(blockHit);
            BlockPos pos = blockHit.getBlockPos();
            level().gameEvent(GameEvent.PROJECTILE_LAND, pos,
                    GameEvent.Context.of(this, level().getBlockState(pos)));
        }
        if (!level().isClientSide) {
            float power = explosionPower();
            level().explode(this, getX(), getY(), getZ(), power, false,
                    Level.ExplosionInteraction.NONE);
            if (getOwner() instanceof NamelessWither && level() instanceof ServerLevel serverLevel) {
                // 半径 3 的大簇爆炸粒子（照原版爆炸的大簇效果，量砍 1/4：16 → 12）
                serverLevel.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 12,
                        3.0D, 3.0D, 3.0D, 1.0D);
                serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 1,
                        0.0D, 0.0D, 0.0D, 1.0D);
            }
            spawnScaledExplosionParticles(power);
            discard();
        }
    }

    private static final float EXPLOSION_PARTICLES_PER_POWER = 4.0F;

    private static final double EXPLOSION_PARTICLE_SPREAD_PER_POWER = 0.55D;

    private static final float BIG_EXPLOSION_EMITTER_RADIUS = 2.0F;

    private void spawnScaledExplosionParticles(float power) {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        if (this.isAstralSkull()) {
            spawnAstralExplosionParticles(serverLevel, power);
        }
        int extra = Math.max(1, Math.round(EXPLOSION_PARTICLES_PER_POWER * power)) - 1;
        if (extra <= 0) {
            return;
        }
        double spread = EXPLOSION_PARTICLE_SPREAD_PER_POWER * power;
        serverLevel.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), extra,
                spread, spread, spread, 0.0D);
        if (power >= BIG_EXPLOSION_EMITTER_RADIUS) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                    getX(), getY(), getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private static final float ASTRAL_BURST_PER_POWER = 12.0F;

    private static final double ASTRAL_BURST_SPEED = 0.34D;

    private void spawnAstralExplosionParticles(ServerLevel serverLevel, float power) {
        int count = Math.max(1, Math.round(ASTRAL_BURST_PER_POWER * power));
        double spread = EXPLOSION_PARTICLE_SPREAD_PER_POWER * power;
        serverLevel.sendParticles(GeneratedMod.ASTRAL_SKULL_SPARK.get(),
                getX(), getY(), getZ(), count, spread, spread, spread, ASTRAL_BURST_SPEED);
        serverLevel.sendParticles(GeneratedMod.ASTRAL_SKULL_SPARK.get(),
                getX(), getY(), getZ(), Math.max(1, count / 2),
                spread * 0.6D, spread * 0.6D, spread * 0.6D, 0.0D);
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide && !(getOwner() instanceof NamelessWither)) {
            spawnImpactCloud();
        }
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        Entity victim = result.getEntity();
        Entity owner = getOwner();
        boolean undeadImmune = !level().isClientSide && victim instanceof WitherBoss
                && owner instanceof WitherServant;
        super.onHitEntity(result);
        if (undeadImmune) {
            forceUndeadImmuneHit(victim, owner);
        }
    }

    private void forceUndeadImmuneHit(Entity victim, Entity owner) {
        if (!(victim instanceof LivingEntity living)) {
            return;
        }
        living.invulnerableTime = 0;
        boolean hurt = living.hurt(damageSources().witherSkull(this, this), getDirectDamage());
        if (!hurt) {
            return;
        }
        if (owner instanceof LivingEntity livingOwner) {
            if (living.isAlive()) {
                doEnchantDamageEffects(livingOwner, living);
            } else {
                livingOwner.heal(5.0F);
            }
        }
        applyImpactWither(living);
    }

    public void applyImpactWither(LivingEntity victim) {
        int seconds = 0;
        Difficulty difficulty = level().getDifficulty();
        if (difficulty == Difficulty.NORMAL) {
            seconds = 10;
        } else if (difficulty == Difficulty.HARD) {
            seconds = 40;
        }
        if (seconds > 0) {
            victim.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * seconds, 1),
                    getEffectSource());
        }
    }

    public void spawnImpactCloud() {
        spawnCloud(200, 1);
    }

    public final void spawnCloud(int durationTicks, int amplifier) {
        if (!(getOwner() instanceof LivingEntity owner)) {
            return;
        }
        AlliedEffectCloud cloud = new AlliedEffectCloud(level(), getX(), getY(), getZ());
        cloud.setOwner(owner);
        cloud.setParticle(GeneratedMod.SKULL_CLOUD_SMOKE.get());
        cloud.addEffect(new MobEffectInstance(MobEffects.WITHER, durationTicks, amplifier));
        cloud.setRadius(CLOUD_RADIUS);
        cloud.setDuration(durationTicks);
        level().addFreshEntity(cloud);
    }
}
