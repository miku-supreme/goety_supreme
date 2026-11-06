package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.entity.FlungBlockEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.api.RagingSkillHolder;
import cn.blockforge.generated.goetysupreme.item.RagingStarItem;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import cn.blockforge.generated.goetysupreme.util.SupremeChargeAura;
import cn.blockforge.generated.goetysupreme.api.SupremeChargeAuraHolder;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.client.particles.SlamParticleOption;
import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ally.golem.RedstoneCube;
import com.Polarice3.Goety.common.entities.ally.golem.RedstoneMonstrosity;
import com.Polarice3.Goety.common.entities.projectiles.ScatterBomb;
import com.Polarice3.Goety.common.entities.util.CameraShake;
import com.Polarice3.Goety.common.entities.util.SummonCircleVariant;
import com.Polarice3.Goety.config.AttributesConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import com.Polarice3.Goety.common.entities.ModEntityType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.util.RagingArmAim;
import cn.blockforge.generated.goetysupreme.renderer.RagingMonstrosityRenderer;
import cn.blockforge.generated.goetysupreme.event.RagingStarCubeMineHandler;
import cn.blockforge.generated.goetysupreme.renderer.RoarWaveParticle;
import cn.blockforge.generated.goetysupreme.mixin.GoetyScatterBombSprayMineMixin;
import cn.blockforge.generated.goetysupreme.mixin.RagingWipeGrabRideMixin;

@Mixin(value = RedstoneMonstrosity.class, remap = false)
public abstract class GoetyRedstoneMonstrosityRagingMixin
        implements RagingStarServant, RagingSkillHolder, SupremeChargeAuraHolder {

    @Unique
    private static final EntityDataAccessor<Boolean> RAGING_STARRED =
            SynchedEntityData.defineId(RedstoneMonstrosity.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private static final EntityDataAccessor<Integer> RAGING_SKILL =
            SynchedEntityData.defineId(RedstoneMonstrosity.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Integer> RAGING_SKILL_TICK =
            SynchedEntityData.defineId(RedstoneMonstrosity.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Integer> RAGING_GRAB =
            SynchedEntityData.defineId(RedstoneMonstrosity.class, EntityDataSerializers.INT);

    @Unique
    private static final EntityDataAccessor<Integer> RAGING_AIM =
            SynchedEntityData.defineId(RedstoneMonstrosity.class, EntityDataSerializers.INT);

    @Unique
    private static final UUID RAGING_ARMOR_UUID =
            UUID.fromString("8f2c1d54-2c7a-4b1e-9d55-0c6b6d1a7e01");
    @Unique
    private static final UUID RAGING_TOUGHNESS_UUID =
            UUID.fromString("8f2c1d54-2c7a-4b1e-9d55-0c6b6d1a7e02");

    @Unique
    private static final UUID RAGING_ATTACK_UUID =
            UUID.fromString("8f2c1d54-2c7a-4b1e-9d55-0c6b6d1a7e03");

    @Unique
    private static final double RAGING_ARMOR = 12.0D;
    @Unique
    private static final double RAGING_TOUGHNESS = 6.0D;

    @Unique
    private static final int DYNAMIC_SHIELD_TICKS = 10;

    @Unique
    private static final float SUMMON_DAMAGE_MULTIPLIER = 0.25F;

    @Unique
    private static final int[] SKILL_LENGTH = {0, 60, 52, 80, 48, 153, 72, 46, 55};

    @Unique
    private static final int[] SKILL_COOLDOWN = {0, 12, 12, 600, 160, 40, 260, 30, 240};

    @Unique
    private static final float GOETY_SOUND_VOLUME = 3.0F;

    @Unique
    private static final float ROAR_SOUND_VOLUME = GOETY_SOUND_VOLUME * 2.5F;

    @Shadow
    private int summonCool;

    @Unique
    private final int[] goetySupreme$skillCd = new int[9];

    @Unique
    private Vec3 goetySupreme$chargeDir = Vec3.ZERO;

    @Unique
    private boolean goetySupreme$wipeQuaked;

    @Unique
    private int goetySupreme$puntTicks;
    @Unique
    private int goetySupreme$puntId;
    @Unique
    private Vec3 goetySupreme$puntVec = Vec3.ZERO;

    @Unique
    private static final int QUAKE_TICKS = 18;
    @Unique
    private static final double QUAKE_SPEED = 0.8D;

    @Unique
    private static final double QUAKE_START = 1.2D;

    @Unique
    private static final double QUAKE_SPREAD = Math.PI * 0.6D;

    @Unique
    private static final int QUAKE_SAMPLES = 9;

    @Unique
    private static final double QUAKE_COLUMN = 0.9D;

    @Unique
    private static final float SMASH_DAMAGE_SCALE = 1.0F;

    @Unique
    private static final float SMASH_HEALTH_PERCENT = 0.10F;

    @Unique
    private static final float SLAM_DAMAGE_SCALE = 0.5F;

    @Unique
    private static final float SLAM_HEALTH_PERCENT = 0.05F;

    @Unique
    private static final float WIPE_DAMAGE_SCALE = 0.75F;

    @Unique
    private static final float WIPE_DRAG_HEALTH_PERCENT = 0.05F;

    @Unique
    private static final float QUAKE_DAMAGE_SCALE = 0.5F;

    @Unique
    private static final float QUAKE_HEALTH_PERCENT = 0.06F;

    @Unique
    private static final float CHARGE_STOMP_SCALE = 0.5F;

    @Unique
    private static final float CHARGE_DAMAGE_SCALE = 0.75F;

    @Unique
    private static final float ROAR_DAMAGE_SCALE = 0.2F;

    @Unique
    private static final float WIPE_USE_CHANCE = 0.30F;

    @Unique
    private static final double WIPE_HAND_FORWARD = 5.4D;

    @Unique
    private int goetySupreme$quakeTicks;

    @Unique
    private int goetySupreme$quakeAge;

    @Unique
    private Vec3 goetySupreme$quakeDir = Vec3.ZERO;
    @Unique
    private double goetySupreme$quakeOriginX;
    @Unique
    private double goetySupreme$quakeOriginZ;
    @Unique
    private double goetySupreme$quakeRefY;

    @Unique
    private final Set<Integer> goetySupreme$quakeHits = new HashSet<>();

    @Unique
    private Vec3 goetySupreme$wipeDir = Vec3.ZERO;

    @Unique
    private int goetySupreme$shieldTick = -1000;

    @Unique
    private DamageSource goetySupreme$incomingSource;

    @Inject(method = "m_8097_", at = @At("TAIL"), require = 0)
    private void goetySupreme$defineRagingData(CallbackInfo callback) {
        SynchedEntityData data = ((Entity) (Object) this).getEntityData();
        data.define(RAGING_STARRED, Boolean.FALSE);
        data.define(RAGING_SKILL, 0);
        data.define(RAGING_SKILL_TICK, 0);
        data.define(RAGING_GRAB, 0);
        data.define(RAGING_AIM, 0);
    }

    @Inject(method = "m_7380_", at = @At("TAIL"), require = 0)
    private void goetySupreme$saveRaging(CompoundTag tag, CallbackInfo callback) {
        tag.putBoolean(RagingStarSupport.NBT_KEY, this.isRagingStarred());
    }

    @Inject(method = "m_7378_", at = @At("TAIL"), require = 0)
    private void goetySupreme$loadRaging(CompoundTag tag, CallbackInfo callback) {
        if (tag.contains(RagingStarSupport.NBT_KEY) && tag.getBoolean(RagingStarSupport.NBT_KEY)) {
            this.setRagingStarred(true);
        }
    }

    @Inject(method = "m_6153_", at = @At("TAIL"))
    private void goetySupreme$dropRagingStar(CallbackInfo callback) {
        RedstoneMonstrosity self = (RedstoneMonstrosity) (Object) this;
        if (self.level().isClientSide() || this.goetySupreme$ragingStarDropped) {
            return;
        }
        if (!this.isRagingStarred() || self.deathTime < STAR_DROP_DEATH_TIME) {
            return;
        }
        this.goetySupreme$ragingStarDropped = true;
        self.spawnAtLocation(new ItemStack(GeneratedMod.RAGING_STAR.get()));
    }

    @Unique
    private boolean goetySupreme$ragingStarDropped;

    @Unique
    private static final int STAR_DROP_DEATH_TIME = 100;

    @Override
    public boolean isRagingStarred() {
        SynchedEntityData data = ((Entity) (Object) this).getEntityData();
        return data.hasItem(RAGING_STARRED) && Boolean.TRUE.equals(data.get(RAGING_STARRED));
    }

    @Override
    public void setRagingStarred(boolean starred) {
        RedstoneMonstrosity self = (RedstoneMonstrosity) (Object) this;
        if (starred == this.isRagingStarred()) {
            return;
        }
        self.getEntityData().set(RAGING_STARRED, starred);
        if (self.level().isClientSide()) {
            return;
        }
        self.getPersistentData().putBoolean(RagingStarSupport.NBT_KEY, starred);
        if (!starred) {
            return;
        }
        AttributeInstance armor = self.getAttribute(Attributes.ARMOR);
        if (armor != null && armor.getModifier(RAGING_ARMOR_UUID) == null) {
            armor.addPermanentModifier(new AttributeModifier(RAGING_ARMOR_UUID,
                    "Raging star armor", RAGING_ARMOR, AttributeModifier.Operation.ADDITION));
        }
        AttributeInstance toughness = self.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (toughness != null && toughness.getModifier(RAGING_TOUGHNESS_UUID) == null) {
            toughness.addPermanentModifier(new AttributeModifier(RAGING_TOUGHNESS_UUID,
                    "Raging star toughness", RAGING_TOUGHNESS, AttributeModifier.Operation.ADDITION));
        }
        AttributeInstance attack = self.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null && attack.getModifier(RAGING_ATTACK_UUID) == null) {
            attack.addPermanentModifier(new AttributeModifier(RAGING_ATTACK_UUID,
                    "Raging star attack", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        self.setHealth(self.getMaxHealth());
    }

    @Override
    public int getRagingSkillId() {
        SynchedEntityData data = ((Entity) (Object) this).getEntityData();
        return data.hasItem(RAGING_SKILL) ? data.get(RAGING_SKILL) : 0;
    }

    @Override
    public int getRagingSkillTick() {
        SynchedEntityData data = ((Entity) (Object) this).getEntityData();
        return data.hasItem(RAGING_SKILL_TICK) ? data.get(RAGING_SKILL_TICK) : 0;
    }

    @Override
    public int getRagingGrabId() {
        SynchedEntityData data = ((Entity) (Object) this).getEntityData();
        return data.hasItem(RAGING_GRAB) ? data.get(RAGING_GRAB) : 0;
    }

    @Override
    public int getRagingAimTargetId() {
        SynchedEntityData data = ((Entity) (Object) this).getEntityData();
        return data.hasItem(RAGING_AIM) ? data.get(RAGING_AIM) : 0;
    }

    @Inject(method = "m_6071_", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$letStarHandleInteraction(Player player, InteractionHand hand,
            CallbackInfoReturnable<InteractionResult> callback) {
        if (player.getItemInHand(hand).getItem() instanceof RagingStarItem) {
            callback.setReturnValue(InteractionResult.PASS);
        }
    }

    @Inject(method = "m_6469_", at = @At("HEAD"), require = 0)
    private void goetySupreme$captureIncomingSource(DamageSource source, float amount,
            CallbackInfoReturnable<Boolean> callback) {
        this.goetySupreme$incomingSource = source;
    }

    @ModifyVariable(method = "m_6475_", at = @At("HEAD"), argsOnly = true, require = 0)
    private float goetySupreme$scaleIncomingDamage(float amount) {
        RedstoneMonstrosity self = (RedstoneMonstrosity) (Object) this;
        if (!this.isRagingStarred()) {
            return amount;
        }
        DamageSource source = this.goetySupreme$incomingSource;
        float cap = (float) (double) AttributesConfig.RedstoneMonstrosityDamageCap.get();
        if (cap > 0.0F
                && (source == null || !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))) {
            amount = Math.min(amount, cap);
        }
        amount *= RagingStarSupport.DAMAGE_MULTIPLIER;
        if (this.getRagingSkillId() == RAGING_SKILL_SUMMON
                && (source == null || !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))) {
            amount *= SUMMON_DAMAGE_MULTIPLIER;
        }
        int elapsed = self.tickCount - this.goetySupreme$shieldTick;
        if (elapsed >= 0 && elapsed < DYNAMIC_SHIELD_TICKS) {
            float remaining = (DYNAMIC_SHIELD_TICKS - elapsed) / (float) DYNAMIC_SHIELD_TICKS;
            amount *= 1.0F - remaining;
        }
        this.goetySupreme$shieldTick = self.tickCount;
        return Math.max(amount, 0.0F);
    }

    @Unique
    private SupremeChargeAura goetySupreme$chargeAura;

    @Unique
    @Override
    public SupremeChargeAura supremeChargeAura() {
        if (this.goetySupreme$chargeAura == null) {
            this.goetySupreme$chargeAura = SupremeChargeAura.of(SupremeChargeAura.Theme.REDSTONE);
        }
        return this.goetySupreme$chargeAura;
    }

    @Inject(method = "m_8119_", at = @At("TAIL"), require = 0)
    private void goetySupreme$ragingTick(CallbackInfo callback) {
        RedstoneMonstrosity self = (RedstoneMonstrosity) (Object) this;
        if (self.level().isClientSide()) {
            RagingStarSupport.tickStarParticles(self);
            int skill = self.getEntityData().get(RAGING_SKILL);
            int tick = self.getEntityData().get(RAGING_SKILL_TICK);
            this.supremeChargeAura().tick(self,
                    skill == RAGING_SKILL_CHARGE && tick >= 41 && tick <= 87);
            return;
        }
        if (!this.isRagingStarred()) {
            return;
        }
        this.goetySupreme$tickSkills(self);
    }

    @Unique
    private boolean goetySupreme$skillSystemActive(RedstoneMonstrosity self) {
        return this.getTrueOwner() instanceof Player;
    }

    @Unique
    private boolean goetySupreme$isStandby(RedstoneMonstrosity self) {
        return self.isStaying()
                || self.isCurrentAnimation(RedstoneMonstrosity.ACTIVATE)
                || self.isCurrentAnimation(RedstoneMonstrosity.TO_SIT)
                || self.isCurrentAnimation(RedstoneMonstrosity.SIT)
                || self.isCurrentAnimation(RedstoneMonstrosity.TO_STAND);
    }

    @Unique
    private void goetySupreme$tickSkills(RedstoneMonstrosity self) {
        for (int i = 1; i < this.goetySupreme$skillCd.length; i++) {
            if (this.goetySupreme$skillCd[i] > 0) {
                this.goetySupreme$skillCd[i]--;
            }
        }
        if (this.goetySupreme$puntTicks > 0) {
            this.goetySupreme$puntTicks--;
            this.goetySupreme$applyPunt(self);
        }
        this.goetySupreme$tickQuake(self);
        if (!this.goetySupreme$skillSystemActive(self)) {
            if (this.getRagingSkillId() > 0) {
                self.getEntityData().set(RAGING_SKILL, 0);
                self.getEntityData().set(RAGING_SKILL_TICK, 0);
                self.getEntityData().set(RAGING_AIM, 0);
                self.setBelching(false);
            }
            return;
        }

        if (this.goetySupreme$isStandby(self)) {
            int standbySkill = this.getRagingSkillId();
            if (standbySkill > 0) {
                this.goetySupreme$endSkill(self, standbySkill);
                self.getEntityData().set(RAGING_SKILL, 0);
                self.getEntityData().set(RAGING_SKILL_TICK, 0);
                self.getEntityData().set(RAGING_AIM, 0);
            }
            self.setBelching(false);
            self.belchCool = Math.max(self.belchCool, 40);
            this.summonCool = Math.max(this.summonCool, 40);
            return;
        }

        self.belchCool = Math.max(self.belchCool, 40);
        this.summonCool = Math.max(this.summonCool, 40);
        self.setBelching(this.getRagingSkillId() > 0);

        Entity first = self.getFirstPassenger();
        if (first instanceof Player && first.getId() != this.getRagingGrabId()) {
            int running = this.getRagingSkillId();
            if (running > 0) {
                this.goetySupreme$endSkill(self, running);
                self.getEntityData().set(RAGING_SKILL, 0);
                self.getEntityData().set(RAGING_SKILL_TICK, 0);
            }
            return;
        }
        int skill = this.getRagingSkillId();
        if (skill > 0) {
            int tick = this.getRagingSkillTick() + 1;
            self.getEntityData().set(RAGING_SKILL_TICK, tick);
            this.goetySupreme$applySkill(self, skill, tick);
            if (tick >= SKILL_LENGTH[skill]) {
                this.goetySupreme$endSkill(self, skill);
                self.getEntityData().set(RAGING_SKILL, 0);
                self.getEntityData().set(RAGING_SKILL_TICK, 0);
                this.goetySupreme$skillCd[skill] = SKILL_COOLDOWN[skill];
            }
            return;
        }
        LivingEntity target = self.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        int pick = this.goetySupreme$pickSkill(self, target);
        if (pick <= 0) {
            return;
        }
        self.getEntityData().set(RAGING_SKILL, pick);
        self.getEntityData().set(RAGING_SKILL_TICK, 1);
        this.goetySupreme$beginSkill(self, pick);
    }

    @Unique
    private int goetySupreme$pickSkill(RedstoneMonstrosity self, LivingEntity target) {
        double distance = self.distanceTo(target);
        double height = target.getY() - self.getY();
        boolean airborne = !target.onGround() && height > 2.0D;
        boolean close = self.targetClose(target)
                || self.getBoundingBox().inflate(2.0D).intersects(target.getBoundingBox());
        boolean slamReach = close || distance <= 6.0D;

        if (airborne && (distance > 8.0D || height > 6.0D) && this.goetySupreme$skillCd[RAGING_SKILL_ANTIAIR] == 0) {
            return RAGING_SKILL_ANTIAIR;
        }
        boolean canSmash = this.goetySupreme$skillCd[RAGING_SKILL_SMASH] == 0;
        boolean canSlam = slamReach && this.goetySupreme$skillCd[RAGING_SKILL_SLAM] == 0;
        boolean canWipe = this.goetySupreme$skillCd[RAGING_SKILL_WIPE] == 0
                && self.getPassengers().stream().noneMatch(p -> p.getId() != this.getRagingGrabId());
        if (close || distance <= 6.5D) {
            boolean bossTarget = RagingStarSupport.isBossLike(target);
            if (bossTarget) {
                if (canWipe && self.getRandom().nextFloat() < WIPE_USE_CHANCE) {
                    return RAGING_SKILL_WIPE;
                }
            } else {
                if (canWipe && (!canSmash || !canSlam)) {
                    return RAGING_SKILL_WIPE;
                }
                if (canWipe && self.getRandom().nextInt(2) == 0) {
                    return RAGING_SKILL_WIPE;
                }
            }
            boolean canRoar = this.goetySupreme$skillCd[RAGING_SKILL_ROAR] == 0;
            if (canRoar && self.getRandom().nextBoolean()) {
                return RAGING_SKILL_ROAR;
            }
            int[] melee = new int[2];
            int meleeCount = 0;
            if (canSmash) {
                melee[meleeCount++] = RAGING_SKILL_SMASH;
            }
            if (canSlam) {
                melee[meleeCount++] = RAGING_SKILL_SLAM;
            }
            if (meleeCount > 0) {
                return melee[self.getRandom().nextInt(meleeCount)];
            }
            if (canWipe) {
                return RAGING_SKILL_WIPE;
            }
            if (canRoar) {
                return RAGING_SKILL_ROAR;
            }
            return 0;
        }
        if (distance <= 13.0D) {
            if (this.goetySupreme$skillCd[RAGING_SKILL_SPRAY] == 0 && !self.targetClose(target)) {
                return RAGING_SKILL_SPRAY;
            }
            if (this.goetySupreme$skillCd[RAGING_SKILL_SUMMON] == 0 && goetySupreme$cubeCount(self) < 3) {
                return RAGING_SKILL_SUMMON;
            }
            if (canSmash && distance <= 8.0D) {
                return RAGING_SKILL_SMASH;
            }
        }
        if (!airborne && distance >= 10.0D && distance <= 26.0D
                && self.hasLineOfSight(target)
                && self.getRandom().nextFloat() * 100.0F < 18.0F
                && this.goetySupreme$skillCd[RAGING_SKILL_ANTIAIR] == 0) {
            return RAGING_SKILL_ANTIAIR;
        }
        if (distance > 12.0D && this.goetySupreme$skillCd[RAGING_SKILL_CHARGE] == 0) {
            return RAGING_SKILL_CHARGE;
        }
        return 0;
    }

    @Unique
    private static int goetySupreme$cubeCount(RedstoneMonstrosity self) {
        return self.level().getEntitiesOfClass(RedstoneCube.class,
                self.getBoundingBox().inflate(32.0D)).size();
    }

    @Unique
    private void goetySupreme$aimSkill(RedstoneMonstrosity self, int skill, int tick) {
        LivingEntity target = self.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        switch (skill) {
            case RAGING_SKILL_CHARGE, RAGING_SKILL_WIPE -> {
            }
            case RAGING_SKILL_SUMMON -> {
            }
            case RAGING_SKILL_SPRAY -> {
                if (tick < 15) {
                    MobUtil.rotateTo(self, target, 5.0F);
                }
            }
            case RAGING_SKILL_ANTIAIR -> {
                if (tick < 18) {
                    MobUtil.rotateTo(self, target, 5.0F);
                }
            }
            default -> {
                if (tick < 30) {
                    MobUtil.rotateTo(self, target, 5.0F);
                }
            }
        }
    }

    @Unique
    private void goetySupreme$beginSkill(RedstoneMonstrosity self, int skill) {
        self.getNavigation().stop();
        self.setBelching(true);
        LivingEntity target = self.getTarget();
        if (target != null) {
            MobUtil.instaLook(self, target);
        }
        if (skill == RAGING_SKILL_ANTIAIR) {
            self.getEntityData().set(RAGING_AIM,
                    target != null && target.isAlive() ? target.getId() : 0);
        }
        if (skill == RAGING_SKILL_CHARGE) {
            this.goetySupreme$chargeDir = this.goetySupreme$flatToward(self, target);
            this.goetySupreme$faceChargeDir(self);
        }
        if (skill == RAGING_SKILL_WIPE) {
            this.goetySupreme$wipeQuaked = false;
            this.goetySupreme$wipeDir = this.goetySupreme$flatToward(self, target);
        }
        this.goetySupreme$applySkill(self, skill, 1);
    }

    @Unique
    private void goetySupreme$endSkill(RedstoneMonstrosity self, int skill) {
        if (skill == RAGING_SKILL_WIPE) {
            this.goetySupreme$finishWipe(self);
        }
        if (skill == RAGING_SKILL_ANTIAIR) {
            self.getEntityData().set(RAGING_AIM, 0);
        }
        self.setBelching(false);
    }

    @Unique
    private void goetySupreme$finishWipe(RedstoneMonstrosity self) {
        this.goetySupreme$throwGrabbed(self);
        if (!this.goetySupreme$wipeQuaked) {
            this.goetySupreme$wipeQuaked = true;
            this.goetySupreme$beginWipeQuake(self);
        }
    }

    @Unique
    private void goetySupreme$applySkill(RedstoneMonstrosity self, int skill, int tick) {
        if (skill != RAGING_SKILL_CHARGE) {
            this.goetySupreme$root(self);
        }
        this.goetySupreme$aimSkill(self, skill, tick);
        switch (skill) {
            case RAGING_SKILL_SMASH -> {
                if (tick == 1) {
                    self.playSound(ModSounds.REDSTONE_MONSTROSITY_GROWL.get(),
                            GOETY_SOUND_VOLUME, self.getVoicePitch());
                }
                if (tick == 19) {
                    this.goetySupreme$smashImpact(self, 1.0F, 5.0D, 1.25D,
                            this.goetySupreme$attackDamage(self) * SMASH_DAMAGE_SCALE,
                            SMASH_HEALTH_PERCENT, 1.0F);
                }
            }
            case RAGING_SKILL_SLAM -> {
                if (tick == 1) {
                    self.playSound(ModSounds.REDSTONE_MONSTROSITY_GROWL.get(),
                            GOETY_SOUND_VOLUME * 0.8F, self.getVoicePitch());
                }
                if (tick == 22) {
                    this.goetySupreme$smashImpact(self, 0.55F, 6.3D, 0.4D,
                            this.goetySupreme$attackDamage(self) * SLAM_DAMAGE_SCALE,
                            SLAM_HEALTH_PERCENT, 0.5F);
                }
                if (tick == 40) {
                    this.goetySupreme$smashImpact(self, 0.65F, 6.3D, 0.45D,
                            this.goetySupreme$attackDamage(self) * SLAM_DAMAGE_SCALE,
                            SLAM_HEALTH_PERCENT, 0.5F);
                }
            }
            case RAGING_SKILL_ROAR -> {
                if (tick == 10) {
                    self.playSound(ModSounds.REDSTONE_MONSTROSITY_AMBIENT.get(),
                            ROAR_SOUND_VOLUME, 0.7F);
                }
                if (tick == 30) {
                    this.goetySupreme$aoe(self, 14.0D,
                            this.goetySupreme$attackDamage(self) * ROAR_DAMAGE_SCALE, 0.0D);
                    this.goetySupreme$roarShockwave(self);
                    this.goetySupreme$roarBurst(self);
                }
                if (tick == 30 || tick == 32 || tick == 34) {
                    this.goetySupreme$roarWave(self);
                }
            }
            case RAGING_SKILL_SPRAY -> {
                if (tick == 1) {
                    self.playSound(ModSounds.REDSTONE_MONSTROSITY_ARM.get(),
                            GOETY_SOUND_VOLUME * 0.7F, self.getVoicePitch());
                }
                if (tick > 5 && tick < 15) {
                    self.makeBigGlow();
                }
                if (tick == 15) {
                    this.goetySupreme$belchScatterBombs(self);
                }
            }
            case RAGING_SKILL_CHARGE -> {
                this.goetySupreme$faceChargeDir(self);
                if (tick == 1) {
                    self.playSound(ModSounds.REDSTONE_MONSTROSITY_CHARGE.get(),
                            GOETY_SOUND_VOLUME, 1.0F);
                }
                if (tick == 18) {
                    self.playSound(ModSounds.REDSTONE_MONSTROSITY_STEP.get(),
                            GOETY_SOUND_VOLUME, 1.0F);
                    this.goetySupreme$smashImpact(self, 0.6F, 2.5D, 0.6F,
                            this.goetySupreme$attackDamage(self) * CHARGE_STOMP_SCALE,
                            (float) (double) AttributesConfig.RedstoneMonstrosityHPPercentDamage.get(), 1.0F);
                }
                if (tick >= 41 && tick <= 87) {
                    LivingEntity aim = self.getTarget();
                    if (aim != null && aim.isAlive()) {
                        Vec3 toTarget = this.goetySupreme$flatToward(self, aim);
                        if (this.goetySupreme$chargeDir.dot(toTarget) > 0.2D) {
                            this.goetySupreme$chargeDir = toTarget;
                        }
                    }
                    this.goetySupreme$chargeStep(self);
                    this.goetySupreme$chargeContact(self);
                }
                if (tick > 87) {
                    this.goetySupreme$root(self);
                }
                if (tick == 103) {
                    this.goetySupreme$smashImpact(self, 1.0F, 3.0D, 1.0F,
                            this.goetySupreme$attackDamage(self) * SMASH_DAMAGE_SCALE,
                            SMASH_HEALTH_PERCENT, 1.0F);
                }
            }
            case RAGING_SKILL_SUMMON -> {
                if (tick == 1) {
                    self.playSound(ModSounds.REDSTONE_MONSTROSITY_CHARGE.get(),
                            GOETY_SOUND_VOLUME, 1.0F);
                }
                this.goetySupreme$summonWindup(self, tick);
                if (tick == 22) {
                    this.goetySupreme$summonImpact(self);
                }
                if (tick == 42) {
                    this.goetySupreme$spawnRedstoneCubes(self);
                }
            }
            case RAGING_SKILL_ANTIAIR -> {
                LivingEntity aim = self.getTarget();
                if (aim != null && aim.isAlive()) {
                    self.getEntityData().set(RAGING_AIM, aim.getId());
                }
                if (tick == 18) {
                    self.playSound(ModSounds.REDSTONE_MONSTROSITY_BELCH.get(),
                            GOETY_SOUND_VOLUME, 0.7F);
                    this.goetySupreme$fireAntiairVolley(self, self.getTarget());
                }
            }
            case RAGING_SKILL_WIPE -> {
                this.goetySupreme$holdGrabbed(self);
                if (tick == RagingSkillHolder.WIPE_GRAB_TICK) {
                    this.goetySupreme$grabTarget(self);
                }
                if (tick >= RagingSkillHolder.WIPE_TEAR_FROM
                        && tick <= RagingSkillHolder.WIPE_TEAR_TO) {
                    this.goetySupreme$tearGround(self);
                }
                for (int damageTick : RagingSkillHolder.WIPE_DAMAGE_TICKS) {
                    if (tick == damageTick) {
                        this.goetySupreme$wipeDamage(self);
                        break;
                    }
                }
                if (tick == RagingSkillHolder.WIPE_THROW_TICK) {
                    this.goetySupreme$finishWipe(self);
                }
            }
            default -> {
            }
        }
    }

    @Unique
    private float goetySupreme$attackDamage(RedstoneMonstrosity self) {
        return (float) self.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Unique
    private void goetySupreme$smashImpact(RedstoneMonstrosity self, float power,
            double forward, double rangeScale, float baseDamage, float healthPercent,
            float knockbackScale) {
        float volume = GOETY_SOUND_VOLUME;
        self.makeBigGlow();
        self.playSound(ModSounds.REDSTONE_MONSTROSITY_SMASH.get(), volume * 2.0F * power, 0.2F);
        self.playSound(SoundEvents.GENERIC_EXPLODE, volume * 0.5F * power, 0.9F);

        Vec3 look = this.goetySupreme$flatLook(self);
        AABB aabb = new AABB(self.blockPosition()).move(look.scale(forward))
                .inflate(RedstoneMonstrosity.MELEE_RANGE * rangeScale);
        for (LivingEntity victim : self.level().getEntitiesOfClass(LivingEntity.class, aabb)) {
            if (MobUtil.areAllies(self, victim)) {
                continue;
            }
            this.goetySupreme$dealMelee(self, victim,
                    baseDamage + victim.getMaxHealth() * healthPercent, knockbackScale);
        }
        CameraShake.cameraShake(self.level(), self.position(), 25.0F * power, 0.3F, 0, 20);
        if (self.level() instanceof ServerLevel server) {
            ColorUtil color = new ColorUtil(11163136);
            Vec3 at = self.position().add(look.scale(forward));
            server.sendParticles(new SlamParticleOption(color,
                            (float) (self.getBbWidth() * 2.0F * power), 20),
                    at.x, at.y, at.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Unique
    private void goetySupreme$dealMelee(RedstoneMonstrosity self, LivingEntity target, float damage,
            float knockbackScale) {
        float knockback = (float) self.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        float yaw = self.getYRot() * ((float) Math.PI / 180.0F);
        double dx = Mth.sin(yaw);
        double dz = -Mth.cos(yaw);
        if (target.hurt(self.getServantAttack(), damage)) {
            if (knockback > 0.0F) {
                if (target.getBbWidth() > self.getBbWidth()) {
                    target.knockback(knockback * 0.5F * knockbackScale, dx, dz);
                } else {
                    MobUtil.forcefulKnockBack(target, knockback * 0.5F * knockbackScale, dx, dz, 0.5D);
                }
                self.setDeltaMovement(self.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }
            self.doEnchantDamageEffects(self, target);
            self.setLastHurtMob(target);
        }
        if (target instanceof Player player && player.isBlocking()) {
            player.disableShield(true);
        } else {
            MobUtil.disableShield(target);
        }
    }

    @Unique
    private void goetySupreme$aoe(RedstoneMonstrosity self, double radius, float damage, double knockback) {
        this.goetySupreme$hurtArea(self, self.getBoundingBox().inflate(radius), damage, knockback);
    }

    @Unique
    private void goetySupreme$hurtArea(RedstoneMonstrosity self, AABB box, float damage, double knockback) {
        LivingEntity owner = this.getTrueOwner();
        for (LivingEntity victim : self.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (!victim.isAlive() || RagingStarSupport.isFriendlyUnit(self, victim)) {
                continue;
            }
            victim.hurt(com.Polarice3.Goety.utils.ModDamageSource.summonAttack(self, owner), damage);
            if (knockback <= 0.0D) {
                continue;
            }
            Vec3 push = victim.position().subtract(self.position());
            Vec3 dir = push.horizontalDistanceSqr() < 1.0E-4D ? self.getLookAngle() : push.normalize();
            victim.push(dir.x * knockback, 0.35D, dir.z * knockback);
        }
    }

    @Unique
    private void goetySupreme$roarBurst(RedstoneMonstrosity self) {
        this.goetySupreme$roarBurst(self, 14.0D, 40, 100, 10.0F, 2.0D);
    }

    @Unique
    private void goetySupreme$roarBurst(RedstoneMonstrosity self, double radius, int stunTicks,
            int debuffTicks, float heal, double knockRadius) {
        List<MobEffectInstance> harmful = new java.util.ArrayList<>();
        for (MobEffectInstance instance : self.getActiveEffects()) {
            if (!instance.getEffect().isBeneficial()) {
                harmful.add(instance);
            }
        }
        for (MobEffectInstance instance : harmful) {
            self.removeEffect(instance.getEffect());
        }
        self.heal(heal);

        AABB knockBox = self.getBoundingBox().inflate(knockRadius);
        for (LivingEntity victim : self.level().getEntitiesOfClass(LivingEntity.class,
                self.getBoundingBox().inflate(radius))) {
            if (!victim.isAlive() || RagingStarSupport.isFriendlyUnit(self, victim)) {
                continue;
            }
            victim.addEffect(new MobEffectInstance(GoetyEffects.STUNNED.get(), stunTicks));
            victim.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, debuffTicks, 0));
            victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, debuffTicks, 1));
            if (knockBox.intersects(victim.getBoundingBox())) {
                Vec3 push = victim.position().subtract(self.position());
                Vec3 dir = push.horizontalDistanceSqr() < 1.0E-4D
                        ? self.getLookAngle() : push.normalize();
                victim.push(dir.x * 1.5D, 0.4D, dir.z * 1.5D);
            }
        }
    }

    @Unique
    private void goetySupreme$root(RedstoneMonstrosity self) {
        self.getNavigation().stop();
        Vec3 delta = self.getDeltaMovement();
        self.setDeltaMovement(0.0D, delta.y, 0.0D);
        self.hasImpulse = true;
    }

    @Unique
    private void goetySupreme$faceChargeDir(RedstoneMonstrosity self) {
        Vec3 flat = this.goetySupreme$chargeDir;
        if (flat.lengthSqr() < 1.0E-6D) {
            return;
        }
        float yaw = (float) (Mth.atan2(-flat.x, flat.z) * (180.0D / Math.PI));
        self.setYRot(yaw);
        self.yBodyRot = yaw;
        self.yHeadRot = yaw;
    }

    @Unique
    private void goetySupreme$chargeStep(RedstoneMonstrosity self) {
        self.getNavigation().stop();
        Vec3 flat = this.goetySupreme$chargeDir;
        if (flat.lengthSqr() < 1.0E-6D) {
            flat = this.goetySupreme$flatLook(self);
        }
        this.goetySupreme$faceChargeDir(self);
        self.setDeltaMovement(flat.x * 0.5D, self.getDeltaMovement().y, flat.z * 0.5D);
        self.hasImpulse = true;
        this.goetySupreme$chargeDust(self);
    }

    @Unique
    private void goetySupreme$chargeDust(RedstoneMonstrosity self) {
        if (!(self.level() instanceof ServerLevel server)) {
            return;
        }
        BlockPos below = BlockPos.containing(self.getX(), self.getY() - 0.2D, self.getZ());
        BlockState state = self.level().getBlockState(below);
        if (state.isAir()) {
            state = self.level().getBlockState(below.below());
        }
        if (!state.isAir()) {
            server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                    self.getX(), self.getY() + 0.1D, self.getZ(),
                    4, self.getBbWidth() * 0.4D, 0.05D, self.getBbWidth() * 0.4D, 0.02D);
        }
        if (self.tickCount % 8 == 0) {
            self.playSound(ModSounds.REDSTONE_MONSTROSITY_STEP.get(), GOETY_SOUND_VOLUME, 1.0F);
        }
    }

    @Unique
    private void goetySupreme$chargeContact(RedstoneMonstrosity self) {
        LivingEntity owner = this.getTrueOwner();
        AABB box = self.getBoundingBox().inflate(1.4D, 0.6D, 1.4D);
        float baseDamage = this.goetySupreme$attackDamage(self) * CHARGE_DAMAGE_SCALE;
        float healthPercent = (float) (double) AttributesConfig.RedstoneMonstrosityHPPercentDamage.get();
        for (LivingEntity victim : self.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (!victim.isAlive() || RagingStarSupport.isFriendlyUnit(self, victim)) {
                continue;
            }
            float damage = baseDamage + victim.getMaxHealth() * healthPercent;
            if (!victim.hurt(com.Polarice3.Goety.utils.ModDamageSource.summonAttack(self, owner),
                    damage)) {
                continue;
            }
            Vec3 chargeDir = this.goetySupreme$chargeDir;
            if (chargeDir.lengthSqr() < 1.0E-6D) {
                chargeDir = this.goetySupreme$flatLook(self);
            }
            double originX = self.getX() - chargeDir.x * 2.5D;
            double originZ = self.getZ() - chargeDir.z * 2.5D;
            double dx = victim.getX() - originX;
            double dz = victim.getZ() - originZ;
            double distSqr = Math.max(dx * dx + dz * dz, 0.05D);
            victim.push(dx / distSqr * 5.0D, 0.35D, dz / distSqr * 5.0D);
        }
    }

    @SuppressWarnings("unchecked")
    @Unique
    private static net.minecraft.world.entity.EntityType<
            ? extends com.Polarice3.Goety.common.entities.neutral.Owned> goetySupreme$redstoneCubeType() {
        return (net.minecraft.world.entity.EntityType<
                ? extends com.Polarice3.Goety.common.entities.neutral.Owned>)
                (net.minecraft.world.entity.EntityType<?>) ModEntityType.REDSTONE_CUBE.get();
    }

    @Unique
    private void goetySupreme$summonWindup(RedstoneMonstrosity self, int tick) {
        self.makeBigGlow();
        if (!(self.level() instanceof ServerLevel server)) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            server.sendParticles(ModParticleTypes.ELECTRIC.get(),
                    self.getRandomX(0.5D), self.getY() + self.getBbHeight() * 0.6D, self.getRandomZ(0.5D),
                    0, server.random.nextGaussian() * 0.02D, server.random.nextGaussian() * 0.02D,
                    server.random.nextGaussian() * 0.02D, 0.5D);
        }
        if (tick >= 10 && tick <= 22) {
            for (int i = 0; i < 4; i++) {
                server.sendParticles(ModParticleTypes.BIG_FIRE_GROUND.get(),
                        self.getX() + (self.getRandom().nextDouble() - 0.5D) * self.getBbWidth() * 2.0D,
                        self.blockPosition().getY() + 0.5D,
                        self.getZ() + (self.getRandom().nextDouble() - 0.5D) * self.getBbWidth() * 2.0D,
                        0, 0.0D, 0.0D, 0.0D, 0.5D);
            }
        }
        if (tick == 20) {
            CameraShake.cameraShake(self.level(), self.position(), 25.0F, 0.3F, 0, 20);
        }
    }

    @Unique
    private void goetySupreme$summonImpact(RedstoneMonstrosity self) {
        self.playSound(ModSounds.REDSTONE_MONSTROSITY_BELCH.get(), GOETY_SOUND_VOLUME, 0.7F);
        CameraShake.cameraShake(self.level(), self.position(), 25.0F, 0.3F, 0, 20);
        if (!(self.level() instanceof ServerLevel server)) {
            return;
        }
        ColorUtil color = new ColorUtil(16744960);
        double leftX = self.getXLeft();
        double leftZ = self.getZLeft();
        double rightX = self.getXRight();
        double rightZ = self.getZRight();
        double leftY = BlockFinder.moveDownToGround(self);
        double rightY = BlockFinder.moveDownToGround(self);
        server.sendParticles(new SmashParticleOption(color, 3.0F, 1.5F, 10),
                leftX, leftY, leftZ, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        server.sendParticles(new SmashParticleOption(color, 3.0F, 1.5F, 10),
                rightX, rightY, rightZ, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        ServerParticleUtil.sendGodRay(server, leftX, leftY, leftZ, color);
        ServerParticleUtil.sendGodRay(server, rightX, rightY, rightZ, color);

        AABB aabb = new AABB(self.blockPosition()).inflate(RedstoneMonstrosity.MELEE_RANGE / 2.0D);
        float yaw = self.getYRot() * ((float) Math.PI / 180.0F);
        for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, aabb)) {
            if (victim == self || MobUtil.areAllies(self, victim)) {
                continue;
            }
            if (!victim.hurt(self.getServantAttack(), 2.0F)) {
                continue;
            }
            float force = 2.0F;
            if (victim.getBbWidth() > self.getBbWidth()) {
                victim.knockback(force * 0.5F, Mth.sin(yaw), -Mth.cos(yaw));
            } else {
                MobUtil.forcefulKnockBack(victim, force * 0.5F, Mth.sin(yaw), -Mth.cos(yaw), 0.5D);
            }
        }
    }

    @Unique
    private void goetySupreme$spawnRedstoneCubes(RedstoneMonstrosity self) {
        Level level = self.level();
        BlockPos center = self.blockPosition();
        for (int i = 0; i < 16; i++) {
            BlockPos pos = center.offset(level.random.nextInt(32) - 16, 0, level.random.nextInt(32) - 16);
            RedstoneCube cube = new RedstoneCube(goetySupreme$redstoneCubeType(), level);
            cube.getPersistentData().putBoolean(RagingStarSupport.RAGING_CUBE_MINE, true);
            cube.addEffect(new MobEffectInstance(GoetyEffects.BUFF.get(), -1, 0, false, false));
            Vec3 at = Vec3.atBottomCenterOf(pos);
            if (!level.noCollision(cube, cube.getBoundingBox().move(at))
                    || !BlockFinder.canSeeBlock(self, at)) {
                continue;
            }
            SummonCircleVariant circle = new SummonCircleVariant(level, at, cube, self);
            circle.playSound(ModSounds.DIRT_DEBRIS.get(), 3.0F, 0.4F);
            level.addFreshEntity(circle);
        }
    }

    @Unique
    private void goetySupreme$belchScatterBombs(RedstoneMonstrosity self) {
        Level level = self.level();
        Vec3 aim = self.position().add(self.getLookAngle().scale(10.0D));
        LivingEntity target = self.getTarget();
        float distance = target == null ? 13.0F : target.distanceTo(self);
        self.playSound(ModSounds.REDSTONE_MONSTROSITY_BELCH.get(), GOETY_SOUND_VOLUME, 0.7F);
        self.playSound(ModSounds.REDSTONE_MONSTROSITY_GROWL.get(),
                GOETY_SOUND_VOLUME - 1.5F, 0.9F);
        for (int i = 0; i < 8; i++) {
            ScatterBomb bomb = new ScatterBomb(self, level);
            bomb.getPersistentData().putBoolean(RagingStarSupport.SPRAY_MINE, true);
            double dx = aim.x - self.getX();
            double dy = aim.y - bomb.getY();
            double dz = aim.z - self.getZ();
            double flat = Math.sqrt(dx * dx + dz * dz);
            bomb.setXRot(bomb.getXRot() - -20.0F);
            bomb.setPos(self.getX(), self.getY() + 4.5D, self.getZ());
            float velocity = Math.max(0.25F, Math.min(1.0F, distance / 13.0F));
            this.goetySupreme$shootScatterBomb(self, bomb, dx, dy + flat, dz, velocity, 30.0F);
            level.addFreshEntity(bomb);
        }
    }

    @Unique
    private void goetySupreme$shootScatterBomb(RedstoneMonstrosity self, ScatterBomb bomb,
            double xPower, double yPower, double zPower, float velocity, float inaccuracy) {
        Vec3 velocityVec = new Vec3(xPower, yPower, zPower).normalize()
                .add(self.getRandom().triangle(0.0D, 0.0172275D * inaccuracy),
                        self.getRandom().triangle(0.0D, 0.0861375D),
                        self.getRandom().triangle(0.0D, 0.0172275D * inaccuracy))
                .scale(velocity);
        bomb.setDeltaMovement(velocityVec);
        double horizontal = velocityVec.horizontalDistance();
        bomb.setYRot((float) (Mth.atan2(velocityVec.x, velocityVec.z) * (180.0D / Math.PI)));
        bomb.setXRot((float) (Mth.atan2(velocityVec.y, horizontal) * (180.0D / Math.PI)));
        bomb.yRotO = bomb.getYRot();
        bomb.xRotO = bomb.getXRot();
    }

    @Unique
    private void goetySupreme$fireAntiairVolley(RedstoneMonstrosity self, LivingEntity target) {
        double[] offsets = {-0.06D, 0.0D, 0.06D};
        for (double offset : offsets) {
            this.goetySupreme$fireScatterBomb(self, target, true, offset);
        }
    }

    @Unique
    private void goetySupreme$fireScatterBomb(RedstoneMonstrosity self, LivingEntity target,
            boolean homing, double yawOffset) {
        Level level = self.level();
        ScatterBomb bomb = new ScatterBomb(self, level);
        Vec3 eye = self.position().add(0.0D, self.getBbHeight() * 0.8D, 0.0D);
        bomb.setPos(eye.x, eye.y, eye.z);
        bomb.setNoGravity(homing);

        Vec3 aim;
        if (target != null && target.isAlive()) {
            Vec3 to = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D).subtract(eye);
            double flat = Math.sqrt(to.x * to.x + to.z * to.z);
            double lift = homing ? flat * 0.05D : flat * 0.25D;
            aim = new Vec3(to.x, to.y + lift, to.z);
        } else {
            aim = self.getLookAngle().scale(8.0D);
        }
        if (yawOffset != 0.0D) {
            double cos = Math.cos(yawOffset);
            double sin = Math.sin(yawOffset);
            aim = new Vec3(aim.x * cos - aim.z * sin, aim.y, aim.x * sin + aim.z * cos);
        }
        double length = aim.length();
        if (length < 1.0E-4D) {
            aim = self.getLookAngle();
            length = Math.max(aim.length(), 1.0E-4D);
        }
        double speed = homing ? 2.4D : 1.0D;
        bomb.setDeltaMovement(aim.scale(speed / length));

        if (homing) {
            CompoundTag data = bomb.getPersistentData();
            data.putBoolean(RagingStarSupport.ANTIAIR_HOMING, true);
            data.putLong(RagingStarSupport.ANTIAIR_EXPIRE,
                    level.getGameTime() + RagingStarSupport.ANTIAIR_LIFESPAN_TICKS);
            if (target != null) {
                data.putInt(RagingStarSupport.ANTIAIR_TARGET, target.getId());
            }
        }
        level.addFreshEntity(bomb);
    }

    @Unique
    private void goetySupreme$grabTarget(RedstoneMonstrosity self) {
        if (this.getRagingGrabId() != 0) {
            return;
        }
        Vec3 flat = this.goetySupreme$wipeDir.lengthSqr() < 1.0E-6D
                ? this.goetySupreme$flatLook(self) : this.goetySupreme$wipeDir;
        double handX = self.getX() + flat.x * WIPE_HAND_FORWARD;
        double handZ = self.getZ() + flat.z * WIPE_HAND_FORWARD;
        double reach = 3.0D;
        AABB box = new AABB(handX - reach, self.getY() - 1.5D, handZ - reach,
                handX + reach, self.getY() + self.getBbHeight(), handZ + reach);
        LivingEntity normal = null;
        java.util.List<LivingEntity> bosses = new java.util.ArrayList<>();
        double bestNormal = Double.MAX_VALUE;
        for (LivingEntity candidate : self.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (!candidate.isAlive() || candidate == self
                    || RagingStarSupport.isFriendlyUnit(self, candidate)) {
                continue;
            }
            if (RagingStarSupport.isBossLike(candidate)) {
                bosses.add(candidate);
                continue;
            }
            double dist = self.distanceToSqr(candidate);
            if (dist < bestNormal) {
                bestNormal = dist;
                normal = candidate;
            }
        }
        if (normal != null) {
            self.getEntityData().set(RAGING_GRAB, normal.getId());
            normal.startRiding(self, true);
            self.playSound(ModSounds.REDSTONE_MONSTROSITY_ARM.get(),
                    GOETY_SOUND_VOLUME * 0.7F, self.getVoicePitch());
        }
        for (LivingEntity boss : bosses) {
            this.goetySupreme$hurtBossOnly(self, boss);
        }
        if (normal == null && bosses.isEmpty()) {
            this.goetySupreme$wipeSweep(self, box);
        }
    }

    @Unique
    private void goetySupreme$hurtBossOnly(RedstoneMonstrosity self, LivingEntity boss) {
        LivingEntity owner = this.getTrueOwner();
        boss.hurt(com.Polarice3.Goety.utils.ModDamageSource.summonAttack(self, owner),
                this.goetySupreme$attackDamage(self) * WIPE_DAMAGE_SCALE
                        + boss.getMaxHealth()
                        * (float) (double) AttributesConfig.RedstoneMonstrosityHPPercentDamage.get());
    }

    @Unique
    private void goetySupreme$wipeSweep(RedstoneMonstrosity self, AABB box) {
        LivingEntity owner = this.getTrueOwner();
        float baseDamage = this.goetySupreme$attackDamage(self) * WIPE_DAMAGE_SCALE;
        float healthPercent = (float) (double) AttributesConfig.RedstoneMonstrosityHPPercentDamage.get();
        for (LivingEntity victim : self.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (!victim.isAlive() || RagingStarSupport.isFriendlyUnit(self, victim)) {
                continue;
            }
            victim.hurt(com.Polarice3.Goety.utils.ModDamageSource.summonAttack(self, owner),
                    baseDamage + victim.getMaxHealth() * healthPercent);
        }
    }

    @Unique
    private void goetySupreme$holdGrabbed(RedstoneMonstrosity self) {
        int id = this.getRagingGrabId();
        if (id == 0) {
            return;
        }
        Entity grabbed = self.level().getEntity(id);
        if (grabbed == null) {
            self.getEntityData().set(RAGING_GRAB, 0);
            return;
        }
        grabbed.setDeltaMovement(Vec3.ZERO);
        grabbed.fallDistance = 0.0F;
        grabbed.hurtMarked = true;
        if (!(grabbed instanceof Player)) {
            Vec3 flat = this.goetySupreme$wipeDir.lengthSqr() < 1.0E-6D
                    ? this.goetySupreme$flatLook(self) : this.goetySupreme$wipeDir;
            double forward = WIPE_HAND_FORWARD;
            double height = RagingSkillHolder.wipeHoldHeight(this.getRagingSkillTick());
            grabbed.setPos(self.getX() + flat.x * forward, self.getY() + height,
                    self.getZ() + flat.z * forward);
            grabbed.setOldPosAndRot();
            grabbed.hurtMarked = true;
        }
    }

    @Unique
    private void goetySupreme$tearGround(RedstoneMonstrosity self) {
        if (!(self.level() instanceof ServerLevel server)) {
            return;
        }
        Vec3 flat = this.goetySupreme$wipeDir.lengthSqr() < 1.0E-6D
                ? this.goetySupreme$flatLook(self) : this.goetySupreme$wipeDir;
        for (int i = 0; i < 3; i++) {
            double forward = 1.5D + self.getRandom().nextDouble() * 4.5D;
            double side = (self.getRandom().nextDouble() - 0.5D) * 3.2D;
            double x = self.getX() + flat.x * forward - flat.z * side;
            double z = self.getZ() + flat.z * forward + flat.x * side;
            BlockPos pos = BlockPos.containing(x, self.getY() - 0.2D, z);
            BlockState state = self.level().getBlockState(pos);
            if (state.isAir()) {
                state = self.level().getBlockState(pos.below());
            }
            if (state.isAir()) {
                continue;
            }
            server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                    x, self.getY() + 0.15D, z, 5, 0.2D, 0.1D, 0.2D, 0.02D);
            if (self.getRandom().nextInt(3) == 0) {
                self.level().addFreshEntity(new FlungBlockEntity(self.level(), x, self.getY() + 0.1D, z,
                        state, 0.25F + self.getRandom().nextFloat() * 0.25F));
            }
        }
        if (self.getRandom().nextInt(3) == 0) {
            self.playSound(ModSounds.DIRT_DEBRIS.get(), 1.5F, 0.6F);
        }
    }

    @Unique
    private void goetySupreme$wipeDamage(RedstoneMonstrosity self) {
        int id = this.getRagingGrabId();
        if (id == 0) {
            return;
        }
        Entity grabbed = self.level().getEntity(id);
        if (grabbed instanceof LivingEntity living && living.isAlive()
                && !RagingStarSupport.isFriendlyUnit(self, living)) {
            this.goetySupreme$dealMelee(self, living,
                    this.goetySupreme$attackDamage(self) * WIPE_DAMAGE_SCALE
                            + living.getMaxHealth() * WIPE_DRAG_HEALTH_PERCENT, 1.0F);
        }
        self.playSound(ModSounds.REDSTONE_MONSTROSITY_STEP.get(), GOETY_SOUND_VOLUME, 0.8F);
    }

    @Unique
    private void goetySupreme$throwGrabbed(RedstoneMonstrosity self) {
        int id = this.getRagingGrabId();
        if (id == 0) {
            return;
        }
        Entity grabbed = self.level().getEntity(id);
        if (grabbed != null && grabbed.getVehicle() == self) {
            grabbed.stopRiding();
        }
        if (grabbed != null && grabbed.getVehicle() == self) {
            grabbed.stopRiding();
        }
        self.getEntityData().set(RAGING_GRAB, 0);
        if (grabbed == null) {
            return;
        }
        Vec3 flat = this.goetySupreme$wipeDir.lengthSqr() < 1.0E-6D
                ? this.goetySupreme$flatLook(self) : this.goetySupreme$wipeDir;
        Vec3 punt = new Vec3(flat.x * 1.3D, 0.5D, flat.z * 1.3D);
        grabbed.setDeltaMovement(punt);
        grabbed.hurtMarked = true;
        grabbed.hasImpulse = true;
        this.goetySupreme$puntId = grabbed.getId();
        this.goetySupreme$puntVec = punt;
        this.goetySupreme$puntTicks = 2;
        self.playSound(SoundEvents.GENERIC_EXPLODE, GOETY_SOUND_VOLUME * 0.6F, 0.8F);
    }

    @Unique
    private void goetySupreme$applyPunt(RedstoneMonstrosity self) {
        if (this.goetySupreme$puntId == 0) {
            this.goetySupreme$puntTicks = 0;
            return;
        }
        Entity victim = self.level().getEntity(this.goetySupreme$puntId);
        if (victim != null && victim.isAlive() && victim.getVehicle() != self) {
            victim.setDeltaMovement(this.goetySupreme$puntVec);
            victim.hurtMarked = true;
            victim.hasImpulse = true;
        }
        if (this.goetySupreme$puntTicks <= 0) {
            this.goetySupreme$puntId = 0;
        }
    }

    @Unique
    private void goetySupreme$beginWipeQuake(RedstoneMonstrosity self) {
        this.goetySupreme$quakeDir = this.goetySupreme$wipeDir.lengthSqr() < 1.0E-6D
                ? this.goetySupreme$flatLook(self) : this.goetySupreme$wipeDir;
        this.goetySupreme$quakeOriginX = self.getX();
        this.goetySupreme$quakeOriginZ = self.getZ();
        this.goetySupreme$quakeRefY = self.getY();
        this.goetySupreme$quakeAge = 0;
        this.goetySupreme$quakeTicks = QUAKE_TICKS;
        this.goetySupreme$quakeHits.clear();
        self.playSound(ModSounds.REDSTONE_MONSTROSITY_SMASH.get(), GOETY_SOUND_VOLUME * 1.6F, 0.3F);
        self.playSound(ModSounds.DIRT_DEBRIS.get(), GOETY_SOUND_VOLUME * 1.2F, 0.5F);
        CameraShake.cameraShake(self.level(), self.position(), 45.0F, 0.6F, 0, 30);
    }

    @Unique
    private void goetySupreme$tickQuake(RedstoneMonstrosity self) {
        if (this.goetySupreme$quakeTicks <= 0) {
            return;
        }
        this.goetySupreme$quakeTicks--;
        if (!(self.level() instanceof ServerLevel server)) {
            this.goetySupreme$quakeTicks = 0;
            return;
        }
        Vec3 dir = this.goetySupreme$quakeDir;
        double front = QUAKE_START + QUAKE_SPEED * this.goetySupreme$quakeAge;
        LivingEntity owner = this.getTrueOwner();

        double frontGround = this.goetySupreme$groundAt(self, this.goetySupreme$quakeOriginX
                + dir.x * front, this.goetySupreme$quakeOriginZ + dir.z * front,
                this.goetySupreme$quakeRefY);

        for (int i = 0; i < QUAKE_SAMPLES; i++) {
            double ratio = QUAKE_SAMPLES <= 1 ? 0.0D : (double) i / (QUAKE_SAMPLES - 1) - 0.5D;
            double angle = ratio * QUAKE_SPREAD;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double sampleX = dir.x * cos - dir.z * sin;
            double sampleZ = dir.x * sin + dir.z * cos;
            double px = this.goetySupreme$quakeOriginX + sampleX * front;
            double pz = this.goetySupreme$quakeOriginZ + sampleZ * front;
            double ground = this.goetySupreme$groundAt(self, px, pz, frontGround + 1.5D);
            BlockPos pos = BlockPos.containing(px, ground - 0.15D, pz);
            BlockState state = self.level().getBlockState(pos);
            if (state.isAir()) {
                state = self.level().getBlockState(pos.below());
            }
            if (state.isAir()) {
                continue;
            }
            server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                    px, ground + 0.2D, pz, 5, 0.28D, 0.18D, 0.28D, 0.08D);
            server.sendParticles(ParticleTypes.CLOUD, px, ground + 0.15D, pz,
                    1, 0.18D, 0.06D, 0.18D, 0.02D);
            if (self.getRandom().nextInt(2) == 0) {
                server.addFreshEntity(new FlungBlockEntity(self.level(), px, ground + 0.1D, pz,
                        state, 0.35F + self.getRandom().nextFloat() * 0.4F));
            }

            AABB column = new AABB(px - QUAKE_COLUMN, ground - 2.0D, pz - QUAKE_COLUMN,
                    px + QUAKE_COLUMN, ground + 3.0D, pz + QUAKE_COLUMN);
            for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, column)) {
                if (victim == self || !victim.isAlive()
                        || RagingStarSupport.isFriendlyUnit(self, victim)) {
                    continue;
                }
                if (!this.goetySupreme$quakeHits.add(victim.getId())) {
                    continue;
                }
                if (!victim.hurt(com.Polarice3.Goety.utils.ModDamageSource.summonAttack(self, owner),
                        this.goetySupreme$attackDamage(self) * QUAKE_DAMAGE_SCALE
                                + victim.getMaxHealth() * QUAKE_HEALTH_PERCENT)) {
                    this.goetySupreme$quakeHits.remove(victim.getId());
                    continue;
                }
                AttributeInstance armorAttr = victim.getAttribute(Attributes.ARMOR);
                double armor = armorAttr == null ? 0.0D : Math.max(armorAttr.getValue(), 0.0D);
                double scale = Math.max(0.0D, 1.0D - armor) + 0.5D;
                double pushX = victim.getX() - this.goetySupreme$quakeOriginX;
                double pushZ = victim.getZ() - this.goetySupreme$quakeOriginZ;
                if (pushX * pushX + pushZ * pushZ < 1.0E-4D) {
                    pushX = dir.x;
                    pushZ = dir.z;
                } else {
                    double len = Math.sqrt(pushX * pushX + pushZ * pushZ);
                    pushX /= len;
                    pushZ /= len;
                }
                victim.push(pushX * 1.1D * scale, 0.45D * scale, pushZ * 1.1D * scale);
                if (victim.getBbWidth() <= 1.0F) {
                    victim.push(0.0D, 0.35D, 0.0D);
                }
            }
        }

        if (this.goetySupreme$quakeAge % 3 == 0) {
            double cx = this.goetySupreme$quakeOriginX + dir.x * front;
            double cz = this.goetySupreme$quakeOriginZ + dir.z * front;
            server.playSound(null, cx, frontGround, cz, ModSounds.DIRT_DEBRIS.get(),
                    SoundSource.HOSTILE, GOETY_SOUND_VOLUME, 0.55F + self.getRandom().nextFloat() * 0.12F);
            server.playSound(null, cx, frontGround, cz, ModSounds.REDSTONE_MONSTROSITY_STEP.get(),
                    SoundSource.HOSTILE, GOETY_SOUND_VOLUME * 0.8F, 0.7F);
        }
        this.goetySupreme$quakeAge++;
    }

    @Unique
    private double goetySupreme$groundAt(RedstoneMonstrosity self, double x, double z, double refY) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        pos.set(Mth.floor(x), Mth.floor(refY), Mth.floor(z));
        for (int i = 0; i < 8; i++) {
            if (!self.level().getBlockState(pos).isAir()) {
                return pos.getY() + 1.0D;
            }
            pos.move(0, -1, 0);
        }
        return refY;
    }

    private static final int ROAR_SHOCKWAVE_COLOR = 0xFF1A0A;

    @Unique
    private void goetySupreme$roarShockwave(RedstoneMonstrosity self) {
        CameraShake.cameraShake(self.level(), self.position(), 35.0F, 0.5F, 0, 25);
        if (!(self.level() instanceof ServerLevel server)) {
            return;
        }
        server.sendParticles(new SlamParticleOption(new ColorUtil(ROAR_SHOCKWAVE_COLOR),
                        (float) (self.getBbWidth() * 3.0F), 24),
                self.getX(), self.getY() + 0.1D, self.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    @Unique
    private void goetySupreme$roarWave(RedstoneMonstrosity self) {
        if (!(self.level() instanceof ServerLevel server)) {
            return;
        }
        Vec3 dir = this.goetySupreme$flatLook(self);
        double forward = self.getBbWidth() * 0.6D;
        double mouthX = self.getX() + dir.x * forward;
        double mouthY = self.getY() + self.getBbHeight() * 0.70D;
        double mouthZ = self.getZ() + dir.z * forward;
        server.sendParticles(GeneratedMod.ROAR_WAVE.get(),
                mouthX, mouthY, mouthZ, 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    @Inject(method = "m_7327_", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$blockVanillaMelee(Entity target,
            CallbackInfoReturnable<Boolean> callback) {
        RedstoneMonstrosity self = (RedstoneMonstrosity) (Object) this;
        if (this.isRagingStarred() && this.goetySupreme$skillSystemActive(self)) {
            callback.setReturnValue(true);
        }
    }

    @Unique
    private Vec3 goetySupreme$flatLook(RedstoneMonstrosity self) {
        Vec3 look = self.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0D, look.z);
        return flat.lengthSqr() < 1.0E-6D ? new Vec3(0.0D, 0.0D, 1.0D) : flat.normalize();
    }

    @Unique
    private Vec3 goetySupreme$flatToward(RedstoneMonstrosity self, LivingEntity target) {
        if (target != null && target.isAlive()) {
            Vec3 delta = target.position().subtract(self.position());
            Vec3 flat = new Vec3(delta.x, 0.0D, delta.z);
            if (flat.lengthSqr() > 1.0E-6D) {
                return flat.normalize();
            }
        }
        return this.goetySupreme$flatLook(self);
    }
}
