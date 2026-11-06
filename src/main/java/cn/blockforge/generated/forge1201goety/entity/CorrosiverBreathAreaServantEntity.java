package cn.blockforge.generated.goetysupreme.entity;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.CorrosiverBreathFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverBreathAreaServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBreathAreaServantEntity;
import cn.blockforge.generated.goetysupreme.compat.JerotesCoreBridge;
import cn.blockforge.generated.goetysupreme.compat.JerotesElementBolt;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.renderer.SprayBeamRenderer;
import cn.blockforge.generated.goetysupreme.entity.BreathAreaEntity;

public class CorrosiverBreathAreaServantEntity extends Entity implements BreathAreaEntity {

    private static final int MAX_LIFE = 40;

    private static final int HURT_TIME = 10;

    private static final double MAX_REACH = 27.0D;

    private static final float SPELL_ANGLE = 22.5F;

    private static final int PARTICLE_COUNT = 160;

    private static final int COLOR_I = 0x1FAFC2;

    private static final int COLOR_II = 0x74E0EE;

    private static final int COLOR_III = 0x15747C;

    private static final EntityDataAccessor<Integer> DATA_CASTER_ID =
            SynchedEntityData.defineId(CorrosiverBreathAreaServantEntity.class,
                    EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SPELL_LEVEL =
            SynchedEntityData.defineId(CorrosiverBreathAreaServantEntity.class,
                    EntityDataSerializers.INT);

    private int lifeTick;
    private int spellLevel = 5;
    private LivingEntity caster;

    private float damageOverride = -1.0F;

    public CorrosiverBreathAreaServantEntity(EntityType<? extends CorrosiverBreathAreaServantEntity> type,
            Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public CorrosiverBreathAreaServantEntity(Level level, LivingEntity caster) {
        this(ModEntityTypes.CORROSIVER_BREATH_AREA_SERVANT.get(), level);
        this.caster = caster;
        this.entityData.set(DATA_CASTER_ID, caster.getId());
    }

    public void setSpellLevel(int level) {
        this.spellLevel = Math.max(1, level);
        this.entityData.set(DATA_SPELL_LEVEL, this.spellLevel);
    }

    public void setDamageOverride(float damage) {
        this.damageOverride = damage;
    }

    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }

    @Override
    public int getLifeTick() {
        return this.lifeTick;
    }

    @Override
    public float getMaxReach() {
        return (float) MAX_REACH;
    }

    @Override
    public float getSpellAngle() {
        return SPELL_ANGLE;
    }

    @Override
    public int beamLightI() {
        return COLOR_I;
    }

    @Override
    public int beamLightII() {
        return COLOR_II;
    }

    @Override
    public int beamLightIII() {
        return COLOR_III;
    }

    public LivingEntity getCaster() {
        return this.caster;
    }

    @Override
    public LivingEntity getOwner() {
        return this.caster;
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(DATA_CASTER_ID, -1);
        this.entityData.define(DATA_SPELL_LEVEL, 5);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        ++this.lifeTick;
        if (this.caster == null) {
            Entity resolved = this.level().getEntity(this.entityData.get(DATA_CASTER_ID));
            if (resolved instanceof LivingEntity living) {
                this.caster = living;
            }
        }
        if (this.level().isClientSide) {
            if (this.spellLevel != this.entityData.get(DATA_SPELL_LEVEL)) {
                this.spellLevel = Math.max(1, this.entityData.get(DATA_SPELL_LEVEL));
            }
            int stripeStart = this.getBallStopStripeStart();
            if (this.lifeTick >= stripeStart && this.lifeTick < this.getStripeStop()) {
                spawnConeParticles(this.lifeTick);
            }
            if (this.lifeTick == stripeStart || this.lifeTick == stripeStart + 1
                    || this.lifeTick == stripeStart + 2 || this.lifeTick == stripeStart + 3) {
                spawnBoltParticles();
            }
            return;
        }
        if (this.lifeTick == HURT_TIME && this.caster != null && this.level() instanceof ServerLevel server) {
            coneAttack(server);
        }
        if (this.lifeTick > MAX_LIFE) {
            this.discard();
        }
    }

    private void coneAttack(ServerLevel server) {
        LivingEntity owner = this.caster;
        Vec3 origin = owner.position();
        Vec3 look = owner.getViewVector(1.0F);
        if (look.lengthSqr() < 1.0E-6D) {
            return;
        }
        look = look.normalize();
        double cosLimit = Math.cos(Math.toRadians(SPELL_ANGLE));
        Set<LivingEntity> alreadyHit = new HashSet<>();
        for (Entity entity : server.getEntitiesOfClass(Entity.class,
                owner.getBoundingBox().inflate(MAX_REACH))) {
            LivingEntity victim;
            if (entity instanceof LivingEntity living) {
                victim = living;
            } else if (entity instanceof PartEntity<?> part && part.getParent() instanceof LivingEntity parent) {
                victim = parent;
            } else {
                continue;
            }
            if (victim == owner || owner.isAlliedTo(victim) || !alreadyHit.add(victim)) {
                continue;
            }
            if (!owner.canAttack(victim)) {
                continue;
            }
            if (owner.distanceTo(victim) > MAX_REACH) {
                continue;
            }
            Vec3 to = victim.position().add(0.0D, victim.getBbHeight() * 0.5D, 0.0D).subtract(origin);
            if (to.lengthSqr() < 1.0E-6D) {
                dealDamageTo(victim);
                continue;
            }
            if (look.dot(to.normalize()) < cosLimit) {
                continue;
            }
            if (!owner.hasLineOfSight(victim)) {
                continue;
            }
            dealDamageTo(victim);
        }
    }

    private void dealDamageTo(LivingEntity victim) {
        if (!victim.isAlive() || victim.isInvulnerable()) {
            return;
        }
        boolean saved = JerotesCoreBridge.savingSpellThrow(victim);
        float damage = breathDamage();
        boolean hit = victim.hurt(JerotesMagicDamage.corrosion(this, this.caster),
                saved ? damage / 2.0F : damage);
        if (hit && !saved) {
            int duration = (6 + (this.spellLevel - 1) * 3) * 20;
            int amplifier = (int) Math.ceil(this.spellLevel / 2.0D);
            Entity source = this.caster != null ? this.caster : this;
            applyEffect(victim, "corrosive", MobEffects.POISON, duration, amplifier, source);
            applyEffect(victim, "weakness", MobEffects.WEAKNESS, duration, amplifier, source);
            applyEffect(victim, "slowness", MobEffects.MOVEMENT_SLOWDOWN, duration, amplifier, source);
        }
        if (hit && !this.isSilent()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    AbstractJerotesServant.jerotesCoreSound("spell", SoundEvents.WITCH_THROW),
                    SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
    }

    private float breathDamage() {
        if (this.damageOverride >= 0.0F) {
            return this.damageOverride;
        }
        return rollDice(this.spellLevel * 2, 6) * CorrosiverServant.ownerSkillScale(this.caster);
    }

    private void spawnConeParticles(int lifeTick) {
        LivingEntity owner = this.caster;
        if (owner == null) {
            return;
        }
        Vec3 casterPos = owner.position();
        Vec3 forward = owner.getViewVector(1.0F);
        if (forward.lengthSqr() < 1.0E-6D) {
            forward = new Vec3(0.0D, 0.0D, 1.0D);
        }
        forward = forward.normalize();
        Vec3 worldUp = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 right = forward.cross(worldUp);
        if (right.lengthSqr() < 1.0E-6D) {
            right = new Vec3(1.0D, 0.0D, 0.0D);
        }
        right = right.normalize();
        Vec3 up = right.cross(forward).normalize();
        Vec3 origin = casterPos.add(0.0D, owner.getBbHeight() * 0.7D, 0.0D).add(forward.scale(0.4D));
        float range = (float) MAX_REACH;
        float baseRadius = range * (float) Math.tan(Math.toRadians(SPELL_ANGLE * 0.5D));
        int start = this.getBallStopStripeStart();
        int stop = this.getStripeStop();
        float progress = (float) (lifeTick - start) / (float) Math.max(1, stop - start);
        ParticleOptions particle = AbstractJerotesServant.jerotesParticle("corrosiver_breath",
                ParticleTypes.SNOWFLAKE);
        float speedScale = 0.02F + this.spellLevel * 0.005F;
        for (int i = 0; i < PARTICLE_COUNT; ++i) {
            float depth = Mth.clamp(progress + (this.random.nextFloat() - 0.5F) * 0.3F, 0.0F, 1.0F);
            float radiusAtDepth = baseRadius * depth;
            float angleRad = this.random.nextFloat() * 2.0F * (float) Math.PI;
            float distFromCenter = radiusAtDepth * (float) Math.sqrt(this.random.nextFloat());
            Vec3 radialDir = right.scale(Math.cos(angleRad)).add(up.scale(Math.sin(angleRad)));
            Vec3 point = origin.add(forward.scale(depth * range)).add(radialDir.scale(distFromCenter));
            this.level().addParticle(particle, point.x, point.y, point.z,
                    (this.random.nextFloat() - 0.5F) * speedScale,
                    (this.random.nextFloat() - 0.5F) * speedScale * 0.5F,
                    (this.random.nextFloat() - 0.5F) * speedScale);
        }
    }

    private void spawnBoltParticles() {
        JerotesElementBolt.spawnFan(this.level(), this.caster, (float) MAX_REACH, SPELL_ANGLE,
                COLOR_I, COLOR_II, COLOR_III, PARTICLE_COUNT / 4, this.random);
    }

    private float rollDice(int count, int sides) {
        int total = 0;
        for (int i = 0; i < Math.max(1, count); ++i) {
            total += this.random.nextInt(sides) + 1;
        }
        return total;
    }

    private static void applyEffect(LivingEntity target, String path, MobEffect fallback,
            int duration, int amplifier, Entity source) {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotes", path));
        target.addEffect(new MobEffectInstance(effect == null ? fallback : effect,
                duration, amplifier, false, effect != null), source);
    }
}
