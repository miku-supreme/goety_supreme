package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.config.NamelessWitherConfig;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.util.WitheringMinionControl;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import com.Polarice3.Goety.common.entities.neutral.ZPiglinServant;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.SEHelper;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.utils.MiscCapHelper;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Enemy;
import cn.blockforge.generated.goetysupreme.util.ServantTargetRules;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NamelessWither extends NamelessWitherBase {

    private static final TagKey<EntityType<?>> GOETY_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "bosses"));
    private static final TagKey<EntityType<?>> GOETY_MINI_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "mini_bosses"));
    private static final TagKey<EntityType<?>> FORGE_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("forge", "bosses"));
    private static final ResourceLocation UNHOLY_BLOOD =
            ResourceLocation.fromNamespaceAndPath("goety", "unholy_blood");
    public static final String CLONE_TAG = "NamelessWitherClone";

    int cloneSlot;

    private boolean greenAppleUnlocked;
    private static final String GREEN_APPLE_MINION_TAG = "GoetySupremeGreenAppleMinion";
    private static final int GREEN_APPLE_MINION_CAP = 16;
    private boolean brokenScytheUnlocked;
    private boolean coffinShardUnlocked;
    private boolean unholyBloodUnlocked;
    private boolean netherStarUnlocked;

    private float ambitionBonusHealth;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState flyEndAnimationState = new AnimationState();
    private int flyEndTicks;
    public final AnimationState beamEndAnimationState = new AnimationState();
    private int beamEndTicks;
    public final AnimationState beamAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState hoverAttackAnimationState = new AnimationState();
    public final AnimationState shellAnimationState = new AnimationState();

    private int bloodCooldown;
    private int laserCooldown;
    private int slamCooldown;
    private static final EntityDataAccessor<Integer> DATA_SLAM_CHARGE =
            SynchedEntityData.defineId(NamelessWither.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SHELL_ACTIVE =
            SynchedEntityData.defineId(NamelessWither.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CHARGING =
            SynchedEntityData.defineId(NamelessWither.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_LASER_TICKS =
            SynchedEntityData.defineId(NamelessWither.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_HOVERING =
            SynchedEntityData.defineId(NamelessWither.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_HAS_TARGET =
            SynchedEntityData.defineId(NamelessWither.class, EntityDataSerializers.BOOLEAN);

    private boolean slamFired;
    private int diamondSpawnDelay;
    private int diveTicks;
    private int skullSwarmCooldown;
    private int beamDelay;
    private int skullShots;
    private int laserTicks;
    @Nullable
    private UUID laserTarget;
    @Nullable
    private NamelessLaserBeam laserBeam;

    public NamelessWither(EntityType<? extends Summoned> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
        this.setHostile(false);
        this.setNoGravity(true);
        this.laserCooldown = 1200;
        this.slamCooldown = 600;
        this.skullSwarmCooldown = 300;
    }

    public static AttributeSupplier.Builder createNamelessAttributes() {
        return NamelessWitherBase.createServantAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.FLYING_SPEED, 0.5D);
    }

    public static AttributeSupplier.Builder createServantAttributes() {
        return createNamelessAttributes();
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_SLAM_CHARGE, 0);
        this.entityData.define(DATA_CHARGING, false);
        this.entityData.define(DATA_LASER_TICKS, 0);
        this.entityData.define(DATA_SHELL_ACTIVE, false);
        this.entityData.define(DATA_HOVERING, false);
        this.entityData.define(DATA_HAS_TARGET, false);
    }

    public static boolean canSummon(Player player) {
        return false;
    }

    public boolean isClone() {
        return this.getPersistentData().getBoolean(CLONE_TAG);
    }

    public void setClone(boolean clone) {
        this.getPersistentData().putBoolean(CLONE_TAG, clone);
    }

    public void markClone(int slot) {
        this.setClone(true);
        this.cloneSlot = slot;
    }

    public int getCloneSlot() {
        return this.cloneSlot;
    }

    @Nullable
    public static NamelessWither createClone(ServerLevel server, LivingEntity owner, int slot) {
        NamelessWither clone = ModEntityTypes.NAMELESS_WITHER.get().create(server);
        if (clone == null) {
            return null;
        }
        clone.setTrueOwner(owner);
        clone.markClone(slot);
        clone.setHealth(clone.getMaxHealth());
        return clone;
    }

    @Override
    public void customServerAiStep() {
        if (this.isClone()) {
            if (!this.level().isClientSide) {
                NamelessClone.tick(this);
            }
            return;
        }
        super.customServerAiStep();
        this.tickPassives();
        this.tickSkills();
        this.entityData.set(DATA_CHARGING, this.ramTicks > 0);
        this.entityData.set(DATA_LASER_TICKS, this.laserTicks);
        this.entityData.set(DATA_HOVERING, this.isHoveringAtStation());
        this.entityData.set(DATA_HAS_TARGET, this.getTarget() != null && this.getTarget().isAlive());
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.tickClientAnimations();
        }
    }

    public boolean isShellActive() {
        return this.entityData.get(DATA_SHELL_ACTIVE);
    }

    private void tickClientAnimations() {
        boolean hasTarget = this.entityData.get(DATA_HAS_TARGET);
        boolean hovering = this.entityData.get(DATA_HOVERING) && hasTarget;
        boolean charging = this.entityData.get(DATA_CHARGING);
        boolean firing = this.entityData.get(DATA_LASER_TICKS) > 0;
        if (this.entityData.get(DATA_SHELL_ACTIVE)) {
            this.shellAnimationState.startIfStopped(this.tickCount);
        } else {
            this.shellAnimationState.stop();
        }
        if (this.flyEndTicks > 0) {
            --this.flyEndTicks;
            startOnlyAnimation(this.flyEndAnimationState);
            // 播完后不 stop：收尾动画保持在末帧（22.5° 站桩姿势），
            // 下一帧切目标动画时由 startOnlyAnimation 同帧 stop/start，
            // 避免中间一帧回到绑定位置（渡魂之镰同款问题的修法）。
            return;
        }
        if (this.beamEndTicks > 0) {
            --this.beamEndTicks;
            startOnlyAnimation(this.beamEndAnimationState);
            return;
        }
        AnimationState target;
        if (charging) {
            target = this.chargeAnimationState;
        } else if (firing) {
            target = this.beamAnimationState;
        } else if (hovering) {
            target = this.hoverAttackAnimationState;
        } else if (hasTarget) {
            target = this.flyAnimationState;
        } else {
            target = this.idleAnimationState;
        }
        if (target != this.flyAnimationState && this.flyAnimationState.isStarted()) {
            this.flyEndAnimationState.startIfStopped(this.tickCount);
            this.flyEndTicks = 15;
            startOnlyAnimation(this.flyEndAnimationState);
            return;
        }
        if (target != this.beamAnimationState && this.beamAnimationState.isStarted()) {
            this.beamEndAnimationState.startIfStopped(this.tickCount);
            this.beamEndTicks = 15;
            startOnlyAnimation(this.beamEndAnimationState);
            return;
        }
        startOnlyAnimation(target);
    }

    private void startOnlyAnimation(AnimationState keep) {
        AnimationState[] all = {
                this.idleAnimationState, this.flyAnimationState, this.beamAnimationState,
                this.chargeAnimationState, this.hoverAttackAnimationState, this.flyEndAnimationState,
                this.beamEndAnimationState
        };
        for (AnimationState state : all) {
            if (state == keep) {
                state.startIfStopped(this.tickCount);
            } else {
                state.stop();
            }
        }
    }

    private void tickPassives() {
        if (this.tickCount % 20 == 0) {
            if (this.getHealth() < this.getMaxHealth()) {
                this.heal((float) (double) NamelessWitherConfig.REGEN_PER_SECOND.get());
            }
            this.tickWitherLifesteal();
            this.tickCoffinSouls();
            this.tickGreenAppleAura();
        }
        this.tickWitherArmor();
        this.tickBrokenScythe();
        this.tickBloodMinions();
    }

    /**
     * 青苹果解锁后：水平 16 格、垂直 10 格内友方亡灵获得亡灵化（goety NECROSIS，照
     * NecroticCandlestickBlockEntity:32 同款效果）+ 免疫阳光直射（MiscCapHelper.setSunscreen，
     * 照 NecroticCandlestickBlockEntity:27 同款）。每 20 tick 刷新 10 秒时长，走出范围后
     * 10 秒内效果消失。
     */
    private void tickGreenAppleAura() {
        if (!this.greenAppleUnlocked || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        double radius = 16.0D;
        AABB area = this.getBoundingBox().inflate(radius, 10.0D, radius);
        double centerY = this.getY() + this.getBbHeight() * 0.5D;
        for (LivingEntity ally : this.level().getEntitiesOfClass(LivingEntity.class, area,
                candidate -> candidate != this && candidate.isAlive()
                        && candidate.getMobType() == MobType.UNDEAD
                        && this.sharesRootOwner(this, candidate)
                        && Math.abs(candidate.getY() + candidate.getBbHeight() * 0.5D - centerY) <= 10.0D)) {
            MiscCapHelper.setSunscreen(ally, 200);
            ally.addEffect(new MobEffectInstance(GoetyEffects.NECROSIS.get(), 200, 0, false, false));
        }
    }

    private void tickWitherLifesteal() {
        double radius = NamelessWitherConfig.LIFESTEAL_RADIUS.get();
        AABB area = this.getBoundingBox().inflate(radius, 10.0D, radius);
        double centerY = this.getY() + this.getBbHeight() * 0.5D;
        int withered = 0;
        for (LivingEntity enemy : this.level().getEntitiesOfClass(LivingEntity.class, area,
                candidate -> candidate != this && candidate.isAlive() && !this.isAlliedTo(candidate)
                        && candidate.hasEffect(MobEffects.WITHER)
                        && Math.abs(candidate.getY() + candidate.getBbHeight() * 0.5D - centerY) <= 10.0D)) {
            ++withered;
        }
        if (withered <= 0) {
            return;
        }
        double heal = Math.min(withered * NamelessWitherConfig.LIFESTEAL_PER_ENEMY.get(),
                NamelessWitherConfig.LIFESTEAL_CAP.get());
        this.heal((float) heal);
    }

    private void tickCoffinSouls() {
        if (!this.coffinShardUnlocked) {
            return;
        }
        LivingEntity owner = this.getTrueOwner();
        if (owner instanceof Player player && player.isAlive()) {
            SEHelper.increaseSouls(player, (int) Math.round(NamelessWitherConfig.COFFIN_SOUL_PER_SECOND.get()));
        }
    }

    private void tickWitherArmor() {
        boolean active = this.isWitherArmorActive();
        if (active) {
            if (this.tickCount % NamelessWitherConfig.ARMOR_RESISTANCE_REFRESH.get() == 0) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
                        NamelessWitherConfig.ARMOR_RESISTANCE_DURATION.get(),
                        NamelessWitherConfig.ARMOR_RESISTANCE_AMPLIFIER.get(), false, false), this);
            }
        } else if (this.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
            this.removeEffect(MobEffects.DAMAGE_RESISTANCE);
        }
        if (this.unholyBloodUnlocked) {
            this.setAttributeBase(Attributes.ARMOR, NamelessWitherConfig.BLOOD_ARMOR.get());
            this.setAttributeBase(Attributes.ARMOR_TOUGHNESS, NamelessWitherConfig.BLOOD_TOUGHNESS.get());
        }
    }

    private boolean isWitherArmorActive() {
        return this.netherStarUnlocked
                || this.getHealth() <= this.getMaxHealth() * NamelessWitherConfig.ARMOR_THRESHOLD.get();
    }

    private void tickBrokenScythe() {
        if (!this.brokenScytheUnlocked || this.tickCount % NamelessWitherConfig.SCYTHE_INTERVAL_TICKS.get() != 0) {
            return;
        }
        LivingEntity owner = this.getTrueOwner();
        if (!(owner instanceof Player player) || !player.isAlive()) {
            return;
        }
        double radius = NamelessWitherConfig.SCYTHE_RADIUS.get();
        AABB area = this.getBoundingBox().inflate(radius, 10.0D, radius);
        double centerY = this.getY() + this.getBbHeight() * 0.5D;
        double drained = 0.0D;
        for (LivingEntity enemy : this.level().getEntitiesOfClass(LivingEntity.class, area,
                candidate -> candidate != this && candidate.isAlive() && candidate instanceof Enemy
                        && this.canAttack(candidate) && !this.isAlliedTo(candidate)
                        && !ServantTargetRules.isDamned(candidate))) {
            double dx = enemy.getX() - this.getX();
            double dz = enemy.getZ() - this.getZ();
            if (dx * dx + dz * dz > radius * radius) {
                continue;
            }
            if (Math.abs(enemy.getY() + enemy.getBbHeight() * 0.5D - centerY) > 10.0D) {
                continue;
            }
            drained += NamelessWitherConfig.SCYTHE_DRAIN.get();
            // 敌人被吸取扣血（照摄魂棺 hurtVictim）
            enemy.hurt(ModDamageSource.indirectSoul(this, this),
                    (float) (double) NamelessWitherConfig.SCYTHE_DRAIN.get());
            if (this.level() instanceof ServerLevel server) {
                // 吸取线（伤害）：绿色火焰连续粒子线（每 0.3 格一个），从敌人连到本体核心
                Vec3 from = new Vec3(enemy.getX(), enemy.getY(0.8D), enemy.getZ());
                Vec3 to = new Vec3(this.getX(), this.getEyeY() + 0.05D, this.getZ());
                double dist = from.distanceTo(to);
                int steps = Math.max(4, (int) Math.ceil(dist / 0.3D));
                for (int s = 0; s <= steps; s++) {
                    double t = s / (double) steps;
                    server.sendParticles(GeneratedMod.NAMELESS_FLAME.get(),
                            from.x + (to.x - from.x) * t,
                            from.y + (to.y - from.y) * t,
                            from.z + (to.z - from.z) * t,
                            1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        if (drained <= 0.0D) {
            return;
        }
        // 每次吸取：灵魂束聚晶释放音效（GreenSmithSpells:185 同款 soul_bolt_cast）
        this.playSound(GreenSmithFx.sound("soul_bolt_cast"), 1.0F, 0.8F);
        float healed = (float) Math.min(drained, NamelessWitherConfig.SCYTHE_HEAL_CAP.get());
        java.util.List<LivingEntity> healTargets = new java.util.ArrayList<>();
        if (player.getHealth() < player.getMaxHealth()) {
            player.heal(healed);
            healTargets.add(player);
        } else {
            healTargets.addAll(this.healAllies(healed));
        }
        if (this.level() instanceof ServerLevel server) {
            // 赏的治疗线：绿色 SOUL 粒子，连续线从本体核心连到每个实际被治疗的目标
            for (LivingEntity healTarget : healTargets) {
                Vec3 from = new Vec3(this.getX(), this.getEyeY() + 0.05D, this.getZ());
                Vec3 to = new Vec3(healTarget.getX(), healTarget.getY(1.2D), healTarget.getZ());
                double dist = from.distanceTo(to);
                int steps = Math.max(4, (int) Math.ceil(dist / 0.3D));
                for (int s = 0; s <= steps; s++) {
                    double t = s / (double) steps;
                    server.sendParticles(GeneratedMod.NAMELESS_SOUL.get(),
                            from.x + (to.x - from.x) * t,
                            from.y + (to.y - from.y) * t,
                            from.z + (to.z - from.z) * t,
                            1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                server.sendParticles(GeneratedMod.NAMELESS_SOUL.get(),
                        healTarget.getX(), healTarget.getY(1.2D), healTarget.getZ(),
                        12, 0.4D, 0.6D, 0.4D, 0.05D);
            }
        }
    }

    private java.util.List<LivingEntity> healAllies(float amount) {
        double radius = NamelessWitherConfig.SCYTHE_RADIUS.get();
        AABB area = this.getBoundingBox().inflate(radius, 10.0D, radius);
        double centerY = this.getY() + this.getBbHeight() * 0.5D;
        List<LivingEntity> allies = this.level().getEntitiesOfClass(LivingEntity.class, area,
                candidate -> candidate != this && candidate.isAlive() && candidate.getMobType() == MobType.UNDEAD
                        && this.sharesRootOwner(this, candidate)
                        && Math.abs(candidate.getY() + candidate.getBbHeight() * 0.5D - centerY) <= 10.0D
                        && candidate.getHealth() < candidate.getMaxHealth());
        allies.sort((a, b) -> Float.compare(a.getHealth() / a.getMaxHealth(), b.getHealth() / b.getMaxHealth()));
        int count = Math.min(NamelessWitherConfig.SCYTHE_ALLY_COUNT.get(), allies.size());
        java.util.List<LivingEntity> healed = new java.util.ArrayList<>();
        for (int i = 0; i < count; ++i) {
            allies.get(i).heal(amount);
            healed.add(allies.get(i));
        }
        return healed;
    }

    /**
     * 友方判定：沿 trueOwner 链上溯到顶，顶端相同即同一阵营。
     * 这样主人（玩家）的死灵法师仆从的仆从（主人链）也算友方，照收割死法 isAlly 的主人链思路扩展。
     */
    private boolean sharesRootOwner(LivingEntity a, LivingEntity b) {
        LivingEntity rootA = rootOwner(a);
        LivingEntity rootB = rootOwner(b);
        return rootA != null && rootA == rootB;
    }

    private static LivingEntity rootOwner(LivingEntity entity) {
        LivingEntity current = entity;
        for (int i = 0; i < 8; i++) {
            if (current instanceof Summoned summoned) {
                LivingEntity up = summoned.getTrueOwner();
                if (up != null) {
                    current = up;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return current;
    }

    private void tickBloodMinions() {
        if (!this.unholyBloodUnlocked) {
            return;
        }
        if (this.bloodCooldown > 0) {
            --this.bloodCooldown;
            return;
        }
        // 没有敌人时不召唤、不消耗冷却
        if (this.getTarget() == null) {
            return;
        }
        this.bloodCooldown = NamelessWitherConfig.BLOOD_COOLDOWN_TICKS.get();
        // 不洁之血召唤：照狂暴形态召唤代码的判定/特效/音效基础，生物换成 4 猪灵 + 2 骷髅
        this.summonBloodMinions();
    }

    private void tickSkills() {
        if (this.laserCooldown > 0) {
            --this.laserCooldown;
        }
        if (this.slamCooldown > 0) {
            --this.slamCooldown;
        }
        if (this.skullSwarmCooldown > 0) {
            --this.skullSwarmCooldown;
        }
        if (this.diamondSpawnDelay > 0) {
            --this.diamondSpawnDelay;
            if (this.diamondSpawnDelay == 0 && this.level() instanceof ServerLevel server) {
                NamelessClone.spawnDiamond(this);
            }
        }
        if (this.beamDelay > 0) {
            --this.beamDelay;
            if (this.beamDelay == 0) {
                this.castNamelessBeam();
            }
        }
        this.tickCorruptedLaser();
        int slamCharge = this.entityData.get(DATA_SLAM_CHARGE);
        if (slamCharge > 0) {
            if (this.level() instanceof ServerLevel server) {
                float progress = (float) (NamelessWitherConfig.SLAM_CHARGE_TICKS.get() - slamCharge + 1)
                        / (float) NamelessWitherConfig.SLAM_CHARGE_TICKS.get();
                NamelessSphereShell.spawnAt(server, this.getX(), this.getY() + this.getBbHeight() * 0.5D,
                        this.getZ(), NamelessSphereShell.wrapRadius(this) * progress);
            }
            this.entityData.set(DATA_SLAM_CHARGE, slamCharge - 1);
            if (slamCharge == 1) {
                this.beginRamCharge();
            }
        }
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        if (this.laserCooldown <= 0 && this.isHoveringAtStation()) {
            this.laserCooldown = 1200;
            this.castCorruptedLaser();
        }
        if (this.slamCooldown <= 0) {
            this.slamCooldown = 600;
            this.castWitherSlam();
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float power) {
        super.performRangedAttack(target, power);
    }

    @Override
    protected void onSkullLaunched() {
        this.onSkullFired();
    }

    @Override
    public LivingEntity getControllingPassenger() {
        return null;
    }

    @Override
    public void handleRoar(LivingEntity pilot) {
    }

    protected void onSkullFired() {
        ++this.skullShots;
        if (this.skullShots % NamelessWitherConfig.BEAM_EVERY_SKULLS.get() == 0) {
            this.beamDelay = NamelessWitherConfig.BEAM_DELAY_TICKS.get();
        }
        if (this.skullSwarmCooldown <= 0
                && this.getRandom().nextFloat() < 0.1F) {
            this.skullSwarmCooldown = 300;
            this.castSkullSwarm();
        }
    }

    protected void castNamelessBeam() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        Vec3 spawn = new Vec3(this.getX(), this.getY() + this.getBbHeight() * 0.5D, this.getZ());
        Vec3 direction = target.getEyePosition().subtract(spawn);
        if (direction.lengthSqr() < 1.0E-4D) {
            return;
        }
        direction = direction.normalize();
        NamelessBeam beam = NamelessBeam.create(server, this, direction.x, direction.y, direction.z);
        if (beam == null) {
            return;
        }
        beam.setPos(spawn.x, spawn.y, spawn.z);
        server.addFreshEntity(beam);
    }

    protected void castCorruptedLaser() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        float yawOffset = (this.getRandom().nextBoolean() ? 1.0F : -1.0F)
                * (AbstractGreenSmith.LASER_START_YAW_MIN
                        + this.getRandom().nextFloat() * (AbstractGreenSmith.LASER_START_YAW_MAX - AbstractGreenSmith.LASER_START_YAW_MIN));
        float pitchOffset = (this.getRandom().nextFloat() * 2.0F - 1.0F) * AbstractGreenSmith.LASER_START_PITCH_OFFSET;
        this.aimBeside(target, yawOffset, pitchOffset);
        this.laserTarget = target.getUUID();
        this.laserTicks = 60;
        NamelessLaserBeam beam = new NamelessLaserBeam(ModEntityTypes.NAMELESS_LASER.get(), server);
        Vec3 look = this.getLookAngle();
        // 光束出生点：核心位置（再向上挪 2 像素贴近绿豆核心）
        beam.setPos(this.getX() + look.x / 2.0D, this.getEyeY() + 0.05D, this.getZ() + look.z / 2.0D);
        beam.setYRot(this.getYRot());
        beam.setXRot(this.getXRot());
        beam.setOwner(this);
        beam.setItemBase(false);
        server.addFreshEntity(beam);
        this.laserBeam = beam;
    }

    private void tickCorruptedLaser() {
        if (this.laserTicks <= 0) {
            return;
        }
        --this.laserTicks;
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        if (this.laserBeam != null && !this.laserBeam.isAlive()) {
            this.laserBeam = null;
        }
        LivingEntity locked = this.laserTarget != null
                && server.getEntity(this.laserTarget) instanceof LivingEntity living ? living : null;
        if (locked != null && locked.isAlive()) {
            this.aimAt(locked, AbstractGreenSmith.LASER_TRACK_TURN_DEGREES);
        }
        if (this.laserTicks <= 0) {
            this.laserTarget = null;
            if (this.laserBeam != null) {
                this.laserBeam.discard();
                this.laserBeam = null;
            }
        }
    }

    private void aimAt(LivingEntity target, float maxTurn) {
        Vec3 direction = target.getEyePosition().subtract(this.getEyePosition());
        if (direction.lengthSqr() < 1.0E-6D) {
            return;
        }
        float wantedYaw = Mth.wrapDegrees((float) Math.toDegrees(Mth.atan2(-direction.x, direction.z)));
        float horizontal = (float) Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        float wantedPitch = (float) -Math.toDegrees(Mth.atan2(direction.y, horizontal));
        float yaw = this.getYRot() + Mth.clamp(Mth.wrapDegrees(wantedYaw - this.getYRot()), -maxTurn, maxTurn);
        float pitch = this.getXRot() + Mth.clamp(wantedPitch - this.getXRot(), -maxTurn, maxTurn);
        this.setYRot(yaw);
        this.setYBodyRot(yaw);
        this.setYHeadRot(yaw);
        this.setXRot(pitch);
    }

    private void aimBeside(LivingEntity target, float yawOffset, float pitchOffset) {
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        double dy = target.getEyeY() - this.getEyeY();
        double flat = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, flat) * (180.0D / Math.PI)));
        this.setYRot(yaw + yawOffset);
        this.setXRot(Mth.clamp(pitch + pitchOffset, -90.0F, 90.0F));
        this.yBodyRot = this.getYRot();
        this.setYHeadRot(this.getYRot());
    }

    protected void castWitherSlam() {
        if (!this.level().isClientSide) {
            if (this.level() instanceof ServerLevel server) {
                // 聚气粒子：新建的绿色发光粒子（照腐化激光粒子的做法），形式照渡魂之镰 gatheringParticles
                ServerParticleUtil.gatheringParticles(GeneratedMod.NAMELESS_GATHER.get(), this, server);
            }
            this.entityData.set(DATA_SLAM_CHARGE, NamelessWitherConfig.SLAM_CHARGE_TICKS.get());
            this.entityData.set(DATA_SHELL_ACTIVE, true);
        }
    }

    private void beginRamCharge() {
        // 前摇结束目标死亡也不断技能：照当前朝向直线冲出（锁方向时目标已死则用视线方向）
        this.ramHits.clear();
        this.ramCooldown = 160 + this.getRandom().nextInt(120);
        this.ramTicks = NamelessWitherBase.RAM_TOTAL;
        this.slamFired = false;
        this.diveTicks = 0;
        // 冲刺蓄力：施法音效 + 冲出去时的呼啸音效
        this.playSound(net.minecraft.sounds.SoundEvents.EVOKER_CAST_SPELL, 1.2F, 0.9F);
        this.playSound(net.minecraft.sounds.SoundEvents.WITHER_SHOOT, 1.5F, 0.5F);
    }

    private void fireRamImpact() {
        if (this.slamFired) {
            return;
        }
        this.slamFired = true;
        this.setDeltaMovement(Vec3.ZERO);
        this.entityData.set(DATA_SHELL_ACTIVE, false);
        if (this.level() instanceof ServerLevel server) {
            NamelessShockwave.castAtAttack(server, this, this.getX(), this.getY(), this.getZ());
            NamelessClone.spawnSidePair(this);
            this.diamondSpawnDelay = 20;
        }
    }

    @Override
    protected void onRamHit(LivingEntity victim) {
        this.fireRamImpact();
    }

    @Override
    protected void doChargeDive(@Nullable LivingEntity target) {
        super.doChargeDive(target);
        if (this.level().isClientSide || this.slamFired) {
            return;
        }
        ++this.diveTicks;
        if (this.onGround() || this.horizontalCollision) {
            this.fireRamImpact();
            this.ramTicks = this.ramRecoverTicks;
        } else if (this.diveTicks > 40) {
            // 俯冲 2 秒没撞到目标：收尾回升，不再无限冲锋
            this.entityData.set(DATA_SHELL_ACTIVE, false);
            this.ramTicks = this.ramRecoverTicks;
        }
    }

    @Override
    protected boolean shouldRamDestroyBlocks() {
        return false;
    }

    protected void castSkullSwarm() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        NamelessSkullVolley.launch(server, this, target);
    }

    protected void summonGreenAppleSkeleton(LivingEntity victim) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        int existing = 0;
        for (net.minecraft.world.entity.Entity entity : server.getAllEntities()) {
            if (entity instanceof Summoned summoned && summoned.getTrueOwner() == this
                    && summoned.getPersistentData().getBoolean(GREEN_APPLE_MINION_TAG)) {
                if (++existing >= GREEN_APPLE_MINION_CAP) {
                    return;
                }
            }
        }
        Summoned minion;
        if (this.getRandom().nextFloat() < 0.25F) {
            minion = new WitherSkeletonServant(ModEntityType.WITHER_SKELETON_SERVANT.get(), server);
        } else {
            ResourceLocation variant = AbstractGreenSmith.SKELETON_SERVANT_VARIANTS[
                    this.getRandom().nextInt(AbstractGreenSmith.SKELETON_SERVANT_VARIANTS.length)];
            EntityType<?> type = net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES.getValue(variant);
            Entity created = type == null ? null : type.create(server);
            if (!(created instanceof Summoned summoned)) {
                return;
            }
            minion = summoned;
        }
        minion.getPersistentData().putBoolean(GREEN_APPLE_MINION_TAG, true);
        minion.setTrueOwner(this);
        minion.moveTo(victim.getX(), victim.getY(), victim.getZ(), this.getYRot(), 0.0F);
        minion.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS);
        minion.setPersistenceRequired();
        minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                net.minecraft.world.entity.MobSpawnType.MOB_SUMMONED, null, null);
        ScaledSummonCircle circle = new ScaledSummonCircle(server,
                new Vec3(victim.getX(), victim.getY(), victim.getZ()), minion, false, true, this);
        circle.lifeSpan = 20;
        circle.noParticles = false;
        server.addFreshEntity(circle);
    }

    protected void summonBloodMinions() {
        if (this.level().isClientSide || !(this.level() instanceof ServerLevel server)) {
            return;
        }
        // 照狂暴形态召唤代码的判定/特效/音效基础（summonWitheringMinions），生物换成 4 猪灵 + 2 骷髅
        int count = Math.min(6, WitheringMinionControl.LIMIT - WitheringMinionControl.count(this, server));
        if (count <= 0) {
            return;
        }
        for (int index = 0; index < count; ++index) {
            boolean piglin = index < 4;
            double angle = index * Mth.TWO_PI / count + this.random.nextDouble() * 0.8D;
            double spawnX = this.getX() + Mth.cos((float) angle) * 2.2D;
            double spawnZ = this.getZ() + Mth.sin((float) angle) * 2.2D;
            if (piglin) {
                ZPiglinServant minion = new ZPiglinServant(ModEntityType.ZPIGLIN_SERVANT.get(), server);
                minion.moveTo(spawnX, MinionGroundPlacement.groundFeetY(server, spawnX, spawnZ,
                        this.getY(), MinionGroundPlacement.SKELETON_CLEARANCE), spawnZ, 0.0F, 0.0F);
                minion.setTrueOwner(this);
                minion.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS);
                minion.setPersistenceRequired();
                minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                        net.minecraft.world.entity.MobSpawnType.TRIGGERED, null, null);
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.LEGS, new ItemStack(Items.GOLDEN_LEGGINGS));
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.FEET, new ItemStack(Items.GOLDEN_BOOTS));
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND,
                        new ItemStack(index % 2 == 0 ? Items.GOLDEN_SWORD : Items.GOLDEN_AXE));
                if (server.addFreshEntity(minion)) {
                    GoetySummonFx.play(server, minion);
                    server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, minion.getX(), minion.getY(1.0D),
                            minion.getZ(), 12, 0.5D, 0.8D, 0.5D, 0.05D);
                }
            } else {
                WitherSkeletonServant minion = new WitherSkeletonServant(
                        ModEntityType.WITHER_SKELETON_SERVANT.get(), server);
                minion.moveTo(spawnX, MinionGroundPlacement.groundFeetY(server, spawnX, spawnZ,
                        this.getY(), MinionGroundPlacement.SKELETON_CLEARANCE), spawnZ, 0.0F, 0.0F);
                minion.setTrueOwner(this);
                minion.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS);
                minion.setPersistenceRequired();
                minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                        net.minecraft.world.entity.MobSpawnType.TRIGGERED, null, null);
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, new ItemStack(Items.NETHERITE_HELMET));
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST, new ItemStack(Items.NETHERITE_CHESTPLATE));
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.LEGS, new ItemStack(Items.NETHERITE_LEGGINGS));
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.FEET, new ItemStack(Items.NETHERITE_BOOTS));
                minion.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND,
                        new ItemStack(index == 4 ? Items.NETHERITE_AXE : Items.NETHERITE_SWORD));
                WitheringMinionControl.initialize(minion, this);
                if (minion.isRemoved()) {
                    continue;
                }
                if (server.addFreshEntity(minion)) {
                    GoetySummonFx.play(server, minion);
                    server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, minion.getX(), minion.getY(1.0D),
                            minion.getZ(), 12, 0.5D, 0.8D, 0.5D, 0.05D);
                }
            }
        }
        // 凋灵生成音效 + 射击呼啸
        this.playSound(net.minecraft.sounds.SoundEvents.WITHER_SPAWN, 1.0F, 1.0F);
        this.playSound(net.minecraft.sounds.SoundEvents.WITHER_SHOOT, 2.0F, 0.7F);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living && !living.isAlive()) {
            this.onKill(living);
        }
        return hit;
    }

    public void onKill(LivingEntity victim) {
        this.heal((float) (double) NamelessWitherConfig.KILL_HEAL.get());
        this.growAmbition(victim);
        if (this.greenAppleUnlocked) {
            this.summonGreenAppleSkeleton(victim);
        }
        if (this.coffinShardUnlocked) {
            LivingEntity owner = this.getTrueOwner();
            if (owner instanceof Player player) {
                int souls = (int) Math.round(victim.getMaxHealth()
                        * NamelessWitherConfig.COFFIN_KILL_SOUL_FRACTION.get());
                SEHelper.increaseSouls(player, souls);
            }
        }
    }

    private void growAmbition(LivingEntity victim) {
        float bonus;
        if (this.isBoss(victim)) {
            bonus = (float) (double) NamelessWitherConfig.AMBITION_BOSS.get();
        } else if (this.isMiniBoss(victim)) {
            bonus = (float) (double) NamelessWitherConfig.AMBITION_MINIBOSS.get();
        } else if (victim.getMaxHealth() > 100.0F) {
            bonus = (float) (double) NamelessWitherConfig.AMBITION_LARGE.get();
        } else if (victim.getMaxHealth() > 50.0F) {
            bonus = (float) (double) NamelessWitherConfig.AMBITION_MEDIUM.get();
        } else {
            bonus = (float) (double) NamelessWitherConfig.AMBITION_SMALL.get();
        }
        float cap = (float) (double) NamelessWitherConfig.AMBITION_MAX_HEALTH.get();
        float maxHealth = this.getMaxHealth();
        if (maxHealth >= cap) {
            return;
        }
        float newMax = Math.min(cap, maxHealth + bonus);
        int beforeTier = (int) (this.ambitionBonusHealth / 50.0F);
        this.ambitionBonusHealth += newMax - maxHealth;
        this.setAttributeBase(Attributes.MAX_HEALTH, newMax);
        if (!this.level().isClientSide) {
            // 每次击杀增长：SOUL 粒子 + 灵魂沙峡谷低语（whisper）
            if (this.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.SOUL,
                        this.getX(), this.getY(1.5D), this.getZ(), 30, 0.6D, 1.0D, 0.6D, 0.05D);
            }
            this.playSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD.value(), 1.0F, 1.0F);
            int afterTier = (int) (this.ambitionBonusHealth / 50.0F);
            if (newMax >= cap) {
                // 体型到最大：信标激活音效
                this.playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 1.0F);
            } else if (afterTier > beforeTier) {
                // 体型增大一档：重生锚充能音效
                this.playSound(SoundEvents.RESPAWN_ANCHOR_CHARGE, 1.0F, 1.0F);
            }
        }
    }

    private boolean isBoss(LivingEntity victim) {
        EntityType<?> type = victim.getType();
        return victim instanceof WitherBoss || victim instanceof EnderDragon
                || type.is(GOETY_BOSSES) || type.is(FORGE_BOSSES);
    }

    private boolean isMiniBoss(LivingEntity victim) {
        return victim.getType().is(GOETY_MINI_BOSSES);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(ModDamageSource.DISMISSED)) {
            return super.hurt(source, amount);
        }
        if (this.isWitherArmorActive() && source.is(DamageTypeTags.IS_PROJECTILE)) {
            return false;
        }
        Entity attacker = source.getEntity();
        if (attacker instanceof LivingEntity living && living.getMobType() == MobType.UNDEAD) {
            amount *= (float) (double) NamelessWitherConfig.UNDEAD_DAMAGE_MULTIPLIER.get();
        }
        Entity direct = source.getDirectEntity();
        if (direct instanceof LivingEntity living && living.getMobType() == MobType.UNDEAD) {
            amount *= (float) (double) NamelessWitherConfig.UNDEAD_DAMAGE_MULTIPLIER.get();
        }
        if (!this.level().isDay()) {
            amount *= (float) (double) NamelessWitherConfig.NIGHT_DAMAGE_MULTIPLIER.get();
        }
        boolean hurt = super.hurt(source, amount);
        if (hurt) {
            this.invulnerableTime = NamelessWitherConfig.INVULNERABLE_TICKS.get();
        }
        return hurt;
    }

    @Override
    public void actuallyHurt(DamageSource source, float amount) {
        if (source.is(ModDamageSource.DISMISSED)) {
            super.actuallyHurt(source, amount);
            return;
        }
        float cap = this.getMaxHealth() * (float) (double) NamelessWitherConfig.DAMAGE_CAP_FRACTION.get();
        super.actuallyHurt(source, Math.min(amount, cap));
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide) {
            this.deathBlast();
        }
        super.die(source);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                    this.getX(), this.getY(1.2D), this.getZ(), 8, 2.0D, 1.2D, 2.0D, 0.0D);
        }
    }

    private void deathBlast() {
        double radius = NamelessWitherConfig.DEATH_EXPLOSION_RADIUS.get();
        AABB area = this.getBoundingBox().inflate(radius);
        List<LivingEntity> victims = this.level().getEntitiesOfClass(LivingEntity.class, area,
                candidate -> candidate != this && candidate.isAlive() && !this.isAlliedTo(candidate));
        for (LivingEntity victim : victims) {
            double distance = Math.sqrt(this.distanceToSqr(victim));
            if (distance > radius) {
                continue;
            }
            float damage = distance <= 1.0D
                    ? (float) (double) NamelessWitherConfig.DEATH_EXPLOSION_DAMAGE.get()
                    : (float) (double) NamelessWitherConfig.DEATH_SHOCKWAVE_DAMAGE.get();
            victim.invulnerableTime = 0;
            victim.hurt(this.damageSources().explosion(this, this), damage);
        }
        this.playSound(SoundEvents.GENERIC_EXPLODE, 6.0F, 0.5F);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                    this.getX(), this.getY(1.0D), this.getZ(), 4, 2.0D, 1.0D, 2.0D, 0.0D);
            server.sendParticles(ParticleTypes.LARGE_SMOKE,
                    this.getX(), this.getY(1.2D), this.getZ(), 80, 3.0D, 2.0D, 3.0D, 0.05D);
        }
    }

    @Override
    public float getScale() {
        float bonus = Math.min((float) (double) NamelessWitherConfig.AMBITION_MAX_SIZE_BONUS.get(),
                this.ambitionBonusHealth / 50.0F * (float) (double) NamelessWitherConfig.AMBITION_SIZE_PER_50.get());
        return 1.0F + bonus;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (this.getTrueOwner() == player && this.isAlive()) {
            if (isSoulJar(held) && ServantFeedHeal.feed(this, player, held, 100.0F)) {
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            if (held.is(Items.BONE) && ServantFeedHeal.feed(this, player, held, 2.0F)) {
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        if (!this.level().isClientSide && this.getTrueOwner() == player && !held.isEmpty()) {
            boolean consumed = this.tryUnlock(player, held);
            if (consumed) {
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
                this.level().playSound(null, this.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP,
                        SoundSource.NEUTRAL, 1.0F, 1.0F);
                if (this.level() instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                            this.getX(), this.getY(1.0D), this.getZ(),
                            20, 0.5D, 1.0D, 0.5D, 0.05D);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private boolean tryUnlock(Player player, ItemStack held) {
        if (held.is(GeneratedMod.GREEN_APPLE.get())) {
            this.greenAppleUnlocked = true;
            return true;
        }
        if (held.is(GeneratedMod.BROKEN_REAPER_SCYTHE.get())) {
            this.brokenScytheUnlocked = true;
            return true;
        }
        if (held.is(GeneratedMod.COFFIN_SHARD.get())) {
            this.coffinShardUnlocked = true;
            return true;
        }
        if (held.is(BuiltInRegistries.ITEM.get(UNHOLY_BLOOD))) {
            this.unholyBloodUnlocked = true;
            this.setAttributeBase(Attributes.ARMOR, NamelessWitherConfig.BLOOD_ARMOR.get());
            this.setAttributeBase(Attributes.ARMOR_TOUGHNESS, NamelessWitherConfig.BLOOD_TOUGHNESS.get());
            // 有敌人时才立即召唤（无敌人时冷却保持就绪，等有目标再召唤）
            if (this.getTarget() != null) {
                this.bloodCooldown = NamelessWitherConfig.BLOOD_COOLDOWN_TICKS.get();
                this.summonBloodMinions();
            }
            return true;
        }
        if (held.is(Items.NETHER_STAR)) {
            this.netherStarUnlocked = true;
            return true;
        }
        return false;
    }

    private void setAttributeBase(net.minecraft.world.entity.ai.attributes.Attribute attribute, double value) {
        AttributeInstance instance = this.getAttribute(attribute);
        if (instance != null && Math.abs(instance.getBaseValue() - value) > 1.0E-4D) {
            instance.setBaseValue(value);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("GreenAppleUnlocked", this.greenAppleUnlocked);
        tag.putBoolean("BrokenScytheUnlocked", this.brokenScytheUnlocked);
        tag.putBoolean("CoffinShardUnlocked", this.coffinShardUnlocked);
        tag.putBoolean("UnholyBloodUnlocked", this.unholyBloodUnlocked);
        tag.putBoolean("NetherStarUnlocked", this.netherStarUnlocked);
        tag.putFloat("AmbitionBonusHealth", this.ambitionBonusHealth);
        tag.putInt("BloodCooldown", this.bloodCooldown);
        tag.putInt("LaserCooldown", this.laserCooldown);
        tag.putInt("SlamCooldown", this.slamCooldown);
        tag.putInt("SkullShots", this.skullShots);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.greenAppleUnlocked = tag.getBoolean("GreenAppleUnlocked");
        this.brokenScytheUnlocked = tag.getBoolean("BrokenScytheUnlocked");
        this.coffinShardUnlocked = tag.getBoolean("CoffinShardUnlocked");
        this.unholyBloodUnlocked = tag.getBoolean("UnholyBloodUnlocked");
        this.netherStarUnlocked = tag.getBoolean("NetherStarUnlocked");
        this.ambitionBonusHealth = tag.getFloat("AmbitionBonusHealth");
        this.bloodCooldown = tag.getInt("BloodCooldown");
        this.laserCooldown = tag.getInt("LaserCooldown");
        this.slamCooldown = tag.getInt("SlamCooldown");
        this.skullShots = tag.getInt("SkullShots");
        if (!this.level().isClientSide) {
            float maxHealth = Math.min((float) (double) NamelessWitherConfig.AMBITION_MAX_HEALTH.get(),
                    (float) (double) NamelessWitherConfig.MAX_HEALTH.get() + this.ambitionBonusHealth);
            this.setAttributeBase(Attributes.MAX_HEALTH, maxHealth);
        }
    }
}
