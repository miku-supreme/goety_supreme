package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.particles.CircleExplodeParticleOption;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.client.particles.SphereExplodeParticleOption;
import cn.blockforge.generated.goetysupreme.client.particles.SoulVFXBridge;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.SEHelper;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.SoulReaperConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractSoulReaper;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.entity.SoulCoffin;

public class SoulCoffin extends Summoned {

    private static final float SHOCKWAVE_RED = 42.0F / 255.0F;
    private static final float SHOCKWAVE_GREEN = 201.0F / 255.0F;
    private static final float SHOCKWAVE_BLUE = 207.0F / 255.0F;

    public static final int ACTION_NONE = 0;
    public static final int ACTION_LAND = 1;
    public static final int ACTION_EXPLODE = 2;
    public static final int ACTION_REVIVE = 3;

    private static final EntityDataAccessor<Integer> DATA_ACTION =
            SynchedEntityData.defineId(SoulCoffin.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ACTION_SEQ =
            SynchedEntityData.defineId(SoulCoffin.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_DOWNED =
            SynchedEntityData.defineId(SoulCoffin.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> DATA_DRAINING =
            SynchedEntityData.defineId(SoulCoffin.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState drainAnimationState = new AnimationState();
    public final AnimationState landAnimationState = new AnimationState();
    public final AnimationState explodeAnimationState = new AnimationState();
    public final AnimationState reviveAnimationState = new AnimationState();

    private int action;
    private int actionSeq;
    private int lastActionSeq = -1;
    private boolean downed;

    private boolean falling;
    private double fallTargetY;

    private int landTicks;

    private int burstTicks;

    private int drainPauseTicks;

    private int reviveTeleportTicks;

    private int drainTick;

    private int noDrainTicks;

    private int masterMissingTicks;

    private static final int NO_DRAIN_TELEPORT_TICKS = 8 * 20;

    public SoulCoffin(EntityType<? extends SoulCoffin> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 0;
        this.setNoGravity(true);
        this.setPersistenceRequired();
        this.setCanPickUpLoot(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, SoulReaperConfig.COFFIN_HEALTH.get())
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ACTION, ACTION_NONE);
        this.entityData.define(DATA_ACTION_SEQ, 0);
        this.entityData.define(DATA_DOWNED, false);
        this.entityData.define(DATA_DRAINING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("SoulCoffinDowned", this.downed);
        tag.putInt("SoulCoffinDrainPause", this.drainPauseTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.downed = tag.getBoolean("SoulCoffinDowned");
        this.drainPauseTicks = tag.getInt("SoulCoffinDrainPause");
        this.entityData.set(DATA_DOWNED, this.downed);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBeSeenAsEnemy() {
        return !this.isDowned() && this.drainPauseTicks <= 0;
    }

    @Override
    public void push(double x, double y, double z) {
    }

    @Override
    public boolean isDeadOrDying() {
        return false;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            if (this.getMasterId() == null) {
                this.discard();
                return;
            }
            AbstractSoulReaper master = this.getMaster();
            if (master != null && (!master.isAlive() || master.isRemoved()
                    || master.level() != this.level())) {
                this.discard();
                return;
            }
        }
        super.tick();
    }

    @Override
    public void die(DamageSource source) {
        if (this.level().isClientSide) {
            return;
        }
        this.startRevive();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide || this.downed) {
            return false;
        }
        Entity attacker = source.getEntity();
        AbstractSoulReaper master = this.getMaster();
        if (attacker == master) {
            return false;
        }
        if (master != null && attacker instanceof LivingEntity living && master.isAlly(living)) {
            return false;
        }
        boolean hurt = super.hurt(source, amount);
        if (hurt && this.getHealth() <= 0.0F) {
            this.startRevive();
        }
        return hurt;
    }

    public void forceDown() {
        this.playSoulBurst();
        this.downed = true;
        this.entityData.set(DATA_DOWNED, true);
        this.setHealth(0.0F);
        this.falling = false;
        this.drainPauseTicks = 0;
    }

    public void playSoulBurst() {
        this.setAction(ACTION_EXPLODE);
        this.burstTicks = 22;
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(),
                    this.getY() + 0.8D, this.getZ(), 30, 0.5D, 0.7D, 0.5D, 0.02D);
        }
        this.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 0.8F);
    }

    public boolean isDowned() {
        return this.entityData.get(DATA_DOWNED);
    }

    public boolean isDraining() {
        return this.entityData.get(DATA_DRAINING);
    }

    private void setDraining(boolean draining) {
        if (this.entityData.get(DATA_DRAINING) != draining) {
            this.entityData.set(DATA_DRAINING, draining);
        }
    }

    public void setMaster(AbstractSoulReaper master) {
        this.setTrueOwner(master);
        this.setPersistenceRequired();
    }

    @Nullable
    public UUID getMasterId() {
        return this.getOwnerId();
    }

    @Nullable
    public AbstractSoulReaper getMaster() {
        return this.getTrueOwner() instanceof AbstractSoulReaper reaper ? reaper : null;
    }

    public boolean isOwnedBy(AbstractSoulReaper reaper) {
        return reaper != null && reaper.getUUID().equals(this.getMasterId());
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        AbstractSoulReaper master = this.getMaster();
        if (master != null && entity instanceof LivingEntity living) {
            return master.isAlly(living);
        }
        return super.isAlliedTo(entity);
    }

    private void teleportWithEffects(double x, double y, double z) {
        if (!(this.level() instanceof ServerLevel server)) {
            this.teleportTo(x, y, z);
            return;
        }
        double ox = this.getX();
        double oy = this.getY() + 0.8D;
        double oz = this.getZ();
        server.sendParticles(ParticleTypes.PORTAL, ox, oy, oz, 32, 0.4D, 0.5D, 0.4D, 0.3D);
        server.sendParticles(ParticleTypes.REVERSE_PORTAL, ox, oy, oz, 16, 0.3D, 0.4D, 0.3D, 0.1D);
        this.teleportTo(x, y, z);
        traceSoulTrail(server, new Vec3(ox, oy, oz), new Vec3(x, y + 0.8D, z));
        server.sendParticles(ParticleTypes.PORTAL, x, y + 0.8D, z, 32, 0.4D, 0.5D, 0.4D, 0.3D);
        server.sendParticles(ParticleTypes.REVERSE_PORTAL, x, y + 0.8D, z, 16, 0.3D, 0.4D, 0.3D, 0.1D);
        this.playSound(GeneratedMod.SOUL_COFFIN_TELEPORT.get(), 1.2F, 1.0F);
    }

    private static void traceSoulTrail(ServerLevel server, Vec3 from, Vec3 to) {
        drawSoulLine(server, from, to, Mth.clamp((int) (from.distanceTo(to) * 2.0D), 2, 48));
    }

    private static void traceDrainLine(ServerLevel server, Vec3 from, Vec3 to) {
        drawSoulLine(server, from, to, Mth.clamp((int) (from.distanceTo(to) * 2.0D / 3.0D), 1, 48));
    }

    private static void drawSoulLine(ServerLevel server, Vec3 from, Vec3 to, int steps) {
        for (int i = 0; i <= steps; i++) {
            Vec3 p = from.lerp(to, i / (double) steps);
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, p.x, p.y, p.z, 1,
                    0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    public int getAction() {
        return this.entityData.get(DATA_ACTION);
    }

    private void setAction(int newAction) {
        this.action = newAction;
        ++this.actionSeq;
        this.entityData.set(DATA_ACTION, newAction);
        this.entityData.set(DATA_ACTION_SEQ, this.actionSeq);
    }

    public void beginEntrance(double targetY) {
        this.falling = true;
        this.fallTargetY = targetY;
        this.setAction(ACTION_NONE);
    }

    public void slamAt(LivingEntity target, boolean multi) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        double y = target.getY() + 0.6D;
        this.teleportWithEffects(target.getX(), y, target.getZ());
        this.setAction(ACTION_LAND);
        this.landTicks = 8;
        server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY() + 0.4D,
                this.getZ(), 26, 0.6D, 0.3D, 0.6D, 0.04D);
        this.playSound(SoundEvents.GENERIC_EXPLODE, 1.2F, 0.9F);
        this.areaDamage(server, SoulReaperConfig.SLAM_RADIUS.get(),
                (float) (double) SoulReaperConfig.SLAM_DAMAGE.get(), false);
        if (multi) {
            this.areaDamage(server, SoulReaperConfig.SLAM_RADIUS.get() + 1.0D,
                    (float) (double) SoulReaperConfig.SLAM_SPLASH_DAMAGE.get(), true);
            server.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 0.5D,
                    this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    public void shockwave() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        this.setAction(ACTION_LAND);
        this.landTicks = 8;
        float radius = (float) (double) SoulReaperConfig.SHOCKWAVE_RADIUS.get();
        double y = this.getY() + 0.5D;
        for (int i = 0; i < 8; i++) {
            Vec3 point = this.position().add(0.0D, 0.5D, 0.0D)
                    .offsetRandom(server.getRandom(), radius);
            server.sendParticles(ModParticleTypes.SOUL_EXPLODE.get(),
                    point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        server.sendParticles(new CircleExplodeParticleOption(
                        SHOCKWAVE_RED, SHOCKWAVE_GREEN, SHOCKWAVE_BLUE, radius, 1),
                this.getX(), y, this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        server.sendParticles(new SphereExplodeParticleOption(
                        SHOCKWAVE_RED, SHOCKWAVE_GREEN, SHOCKWAVE_BLUE, radius, 1),
                this.getX(), y, this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        if (GoetyCompat.isGoety60()) {
            SoulVFXBridge.cubeSpawn(server, new Vec3(this.getX(), y, this.getZ()));
        }
        server.playSound(null, this.blockPosition(), ModSounds.SOUL_EXPLODE.get(),
                SoundSource.HOSTILE, 2.0F, 1.0F);
        List<LivingEntity> victims = this.victimsInRadius(server, radius);
        for (LivingEntity victim : victims) {
            this.hurtVictim(server, victim, (float) (double) SoulReaperConfig.SHOCKWAVE_DAMAGE.get(), true);
            victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
                    SoulReaperConfig.SHOCKWAVE_WEAKNESS_TICKS.get(), 0, false, true));
        }
    }

    public void startRevive() {
        if (this.level().isClientSide) {
            return;
        }
        this.downed = false;
        this.entityData.set(DATA_DOWNED, false);
        this.falling = false;
        this.setHealth(this.getMaxHealth());
        this.setAction(ACTION_REVIVE);
        this.drainPauseTicks = SoulReaperConfig.COFFIN_REVIVE_PAUSE_TICKS.get();
        this.reviveTeleportTicks = this.drainPauseTicks;
        AbstractSoulReaper master = this.getMaster();
        if (master != null) {
            Vec3 side = master.position().add(master.getLookAngle().scale(1.5D));
            this.teleportWithEffects(side.x, master.getY() + 0.2D, side.z);
        }
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + 0.8D,
                    this.getZ(), 30, 0.5D, 0.7D, 0.5D, 0.02D);
        }
        this.playSound(GeneratedMod.SOUL_COFFIN_BREAK.get(), 1.3F, 1.0F);
    }

    public void repositionNearMaster() {
        AbstractSoulReaper master = this.getMaster();
        if (master == null || this.level().isClientSide) {
            return;
        }
        Vec3 side = master.position().add(master.getLookAngle().scale(2.0D));
        this.teleportWithEffects(side.x, master.getY() + 0.2D, side.z);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, side.x, side.y + 0.8D, side.z,
                    16, 0.4D, 0.6D, 0.4D, 0.02D);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.tickClientAnimations();
            return;
        }
        if (this.falling) {
            this.tickFalling();
        }
        if (this.landTicks > 0 && --this.landTicks == 0 && this.getAction() == ACTION_LAND) {
            this.setAction(ACTION_NONE);
        }
        if (this.burstTicks > 0 && --this.burstTicks == 0
                && !this.downed && this.getAction() == ACTION_EXPLODE) {
            this.setAction(ACTION_NONE);
        }
        if (this.drainPauseTicks > 0) {
            --this.drainPauseTicks;
        }
        if (this.reviveTeleportTicks > 0 && --this.reviveTeleportTicks == 0) {
            this.teleportAboveTarget();
        }
        if (this.getMasterId() != null && this.getMaster() == null) {
            if (++this.masterMissingTicks > 200) {
                this.discard();
                return;
            }
        } else {
            this.masterMissingTicks = 0;
        }
        this.tickDrain();
        this.tickNoDrainFallback();
    }

    private void tickNoDrainFallback() {
        AbstractSoulReaper master = this.getMaster();
        if (master == null || this.downed || this.falling
                || master.getTarget() == null || !master.getTarget().isAlive()) {
            this.noDrainTicks = 0;
            return;
        }
        if (++this.noDrainTicks < NO_DRAIN_TELEPORT_TICKS) {
            return;
        }
        this.noDrainTicks = 0;
        this.teleportWithEffects(master.getX(), master.getY(), master.getZ());
        this.shockwave();
    }

    private void tickFalling() {
        double fromY = this.getY() + 0.8D;
        double next = this.getY() - 1.2D;
        if (next <= this.fallTargetY) {
            this.setPos(this.getX(), this.fallTargetY, this.getZ());
            this.falling = false;
            this.setAction(ACTION_LAND);
            this.landTicks = 10;
            if (this.level() instanceof ServerLevel server) {
                traceSoulTrail(server, new Vec3(this.getX(), fromY, this.getZ()),
                        new Vec3(this.getX(), this.getY() + 0.8D, this.getZ()));
                server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY() + 0.3D,
                        this.getZ(), 24, 0.6D, 0.3D, 0.6D, 0.04D);
            }
            this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.1F);
        } else {
            this.setPos(this.getX(), next, this.getZ());
            if (this.level() instanceof ServerLevel server) {
                traceSoulTrail(server, new Vec3(this.getX(), fromY, this.getZ()),
                        new Vec3(this.getX(), this.getY() + 0.8D, this.getZ()));
                if (this.tickCount % 2 == 0) {
                    server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + 0.5D,
                            this.getZ(), 4, 0.2D, 0.2D, 0.2D, 0.01D);
                }
            }
        }
    }

    private void teleportAboveTarget() {
        AbstractSoulReaper master = this.getMaster();
        LivingEntity target = master != null ? master.getTarget() : null;
        if (target != null) {
            this.teleportWithEffects(target.getX(), target.getY() + 3.0D, target.getZ());
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY() + 0.8D,
                        this.getZ(), 20, 0.4D, 0.5D, 0.4D, 0.02D);
            }
        }
    }

    private void tickDrain() {
        if (this.downed || this.drainPauseTicks > 0 || this.falling) {
            this.setDraining(false);
            return;
        }
        AbstractSoulReaper master = this.getMaster();
        if (master == null || master.getTarget() == null || !master.getTarget().isAlive()) {
            this.drainTick = 0;
            this.setDraining(false);
            return;
        }
        if (++this.drainTick < 20) {
            return;
        }
        this.drainTick = 0;
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        double radius = SoulReaperConfig.COFFIN_DRAIN_RADIUS.get();
        float drained = 0.0F;
        boolean absorbed = false;
        List<LivingEntity> victims = this.drainVictims(server, radius, master);
        this.setDraining(!victims.isEmpty());
        for (LivingEntity victim : victims) {
            if (!victim.isInvulnerable()) {
                victim.invulnerableTime = 0;
            }
            float health = (float) (double) SoulReaperConfig.COFFIN_DRAIN_HEALTH.get();
            if (this.hurtVictim(server, victim, health, true)) {
                drained += health;
                absorbed = true;
            }
            int soul = SoulReaperConfig.COFFIN_DRAIN_SOUL.get();
            if (soul > 0 && victim instanceof Player player && SEHelper.getSoulsAmount(player, soul)) {
                SEHelper.decreaseSouls(player, soul);
                absorbed = true;
                if (master.isServantForm() && master.getTrueOwner() instanceof Player owner) {
                    SEHelper.increaseSouls(owner, soul);
                }
            }
            traceDrainLine(server, new Vec3(this.getX(), this.getY() + 0.8D, this.getZ()),
                    new Vec3(victim.getX(), victim.getY() + victim.getBbHeight() * 0.5D, victim.getZ()));
            server.sendParticles(ParticleTypes.SOUL, victim.getX(),
                    victim.getY() + victim.getBbHeight() * 0.5D, victim.getZ(), 3,
                    0.3D, 0.3D, 0.3D, 0.01D);
        }
        if (absorbed) {
            this.noDrainTicks = 0;
        }
        if (!victims.isEmpty()) {
            this.playSound(GeneratedMod.SOUL_COFFIN_DRAIN.get(), 1.1F, 1.0F);
        }
        if (drained > 0.0F) {
            if (master.isServantForm()) {
                float cap = (float) SoulReaperConfig.coffinHealCapForLevel(master.getGrowthLevel());
                master.heal(Math.min(drained, cap));
            } else {
                float back = Math.min(
                        drained * (float) (double) SoulReaperConfig.COFFIN_DRAIN_BACK_RATIO.get(),
                        (float) (double) SoulReaperConfig.COFFIN_DRAIN_BACK_CAP.get());
                if (back > 0.0F) {
                    master.heal(back);
                }
            }
        }
    }

    private List<LivingEntity> drainVictims(ServerLevel server, double radius, AbstractSoulReaper master) {
        AABB area = this.getBoundingBox().inflate(radius);
        return server.getEntitiesOfClass(LivingEntity.class, area,
                e -> e.isAlive() && e != this && !this.isAlly(e) && master.canActivelyTarget(e));
    }

    private List<LivingEntity> victimsInRadius(ServerLevel server, double radius) {
        AABB area = this.getBoundingBox().inflate(radius);
        return server.getEntitiesOfClass(LivingEntity.class, area,
                e -> e.isAlive() && e != this && !this.isAlly(e));
    }

    private void areaDamage(ServerLevel server, double radius, float damage, boolean magic) {
        for (LivingEntity victim : this.victimsInRadius(server, radius)) {
            this.hurtVictim(server, victim, damage, magic);
        }
    }

    private boolean isAlly(LivingEntity entity) {
        AbstractSoulReaper master = this.getMaster();
        return master != null && master.isAlly(entity);
    }

    private boolean hurtVictim(ServerLevel server, LivingEntity victim, float amount, boolean magic) {
        AbstractSoulReaper master = this.getMaster();
        float bonus = master != null ? master.soulPowerDamageBonus() : 0.0F;
        float soulPower = master != null ? master.soulPowerDamageMultiplier() : 1.0F;
        float multiplier = master != null ? master.damageMultiplierAgainst(victim) : 1.0F;
        DamageSource source = master != null
                ? (magic ? master.damageSources().indirectMagic(this, master)
                        : master.damageSources().mobAttack(master))
                : this.damageSources().magic();
        return victim.hurt(source, (amount * soulPower + bonus) * multiplier);
    }

    private void tickClientAnimations() {
        int seq = this.entityData.get(DATA_ACTION_SEQ);
        if (seq != this.lastActionSeq) {
            this.lastActionSeq = seq;
            switch (this.getAction()) {
                case ACTION_LAND -> this.landAnimationState.start(this.tickCount);
                case ACTION_EXPLODE -> this.explodeAnimationState.start(this.tickCount);
                case ACTION_REVIVE -> this.reviveAnimationState.start(this.tickCount);
                default -> {
                }
            }
        }
        boolean draining = this.isDraining() && !this.isDowned() && this.getAction() == ACTION_NONE;
        if (draining) {
            this.drainAnimationState.startIfStopped(this.tickCount);
        } else {
            this.drainAnimationState.stop();
        }
    }

    @Override
    public void registerGoals() {
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public void playStepSound(net.minecraft.core.BlockPos pos,
            net.minecraft.world.level.block.state.BlockState state) {
    }

    @Override
    public net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SOUL_ESCAPE;
    }

    @Override
    public net.minecraft.sounds.SoundEvent getDeathSound() {
        return SoundEvents.SOUL_ESCAPE;
    }

    @Override
    public void setYHeadRot(float rotation) {
    }

    @Override
    public void setYBodyRot(float rotation) {
    }

    @Override
    public void setDeltaMovement(Vec3 movement) {
        super.setDeltaMovement(Vec3.ZERO);
    }

    public int getActionSeq() {
        return this.entityData.get(DATA_ACTION_SEQ);
    }
}
