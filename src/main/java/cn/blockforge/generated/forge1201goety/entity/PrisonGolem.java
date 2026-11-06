package cn.blockforge.generated.goetysupreme.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.util.AstralSetDropin;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GolemClips;
import cn.blockforge.generated.goetysupreme.event.GolemConstructionEvents;
import cn.blockforge.generated.goetysupreme.util.GolemMolds;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;
import cn.blockforge.generated.goetysupreme.event.MobilePrisonBindingEvents;
import cn.blockforge.generated.goetysupreme.entity.MobilePrisonBlockEntity;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.PrisonGolem;
import cn.blockforge.generated.goetysupreme.renderer.PrisonGolemRenderer;
import cn.blockforge.generated.goetysupreme.util.PrisonServantResolver;

public class PrisonGolem extends AbstractGolemServant {
    public static final int VARIANT_RED = 0;
    public static final int VARIANT_PINK = 1;

    public static final float SCALE = 1.25F;

    public static final int ATTACK_VARIANT_SWEEP = 0;

    public static final int ATTACK_VARIANT_SMASH = 1;

    public static final double SMASH_CHANCE_PERCENT = 30.0D;

    public static final int SWEEP_STREAK_BEFORE_SMASH = 3;

    public static final double SWEEP_FORWARD_OFFSET = 1.0D;

    public static final double SWEEP_RADIUS = 2.0D;

    public static final double SWEEP_REACH = SWEEP_FORWARD_OFFSET + SWEEP_RADIUS;

    public static final double SWEEP_VERTICAL_RADIUS = 3.0D;

    public static final double SMASH_RADIUS = 3.0D;

    public static final double SMASH_FORWARD_OFFSET = AbstractGolemServant.GRAVE_SLAM_FORWARD_OFFSET;

    public static final double SWEEP_KNOCKBACK = 0.4D;

    public static final double SMASH_KNOCKBACK = 1.3D;

    public static final double SMASH_LAUNCH = AbstractGolemServant.SLAM_LAUNCH;

    public static final double SMASH_MAX_HEALTH_DAMAGE_PERCENT = 6.0D;

    public static final float SMASH_BONUS_DAMAGE = 5.0F;

    public static final float FLOOR_BONUS = 2.0F;

    private static final double SHOCKWAVE_RADIUS = 4.0D;

    private static final int SHOCKWAVE_COLOR = 0xE8452A;

    private int attackVariant = ATTACK_VARIANT_SWEEP;

    private int sweepStreak;

    private boolean attackImpactDone;

    private Entity pendingCaptureTarget;

    private static final EntityDataAccessor<Integer> DATA_VARIANT =
            SynchedEntityData.defineId(PrisonGolem.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_SUMMON_TICK =
            SynchedEntityData.defineId(PrisonGolem.class, EntityDataSerializers.INT);

    public static final int SUMMON_TICKS = 60;

    private final List<PrisonBind> prisonBinds = new ArrayList<>();

    private boolean legacyBindChecked;

    private static final double PRISON_CENSUS_RADIUS = 24.0D;

    private record PrisonBind(BlockPos anchor, String dim) {
    }

    private static final TagKey<EntityType<?>> GOETY_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "bosses"));
    private static final TagKey<EntityType<?>> GOETY_MINI_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "mini_bosses"));
    private static final TagKey<EntityType<?>> FORGE_BOSSES =
            TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("forge", "bosses"));

    public PrisonGolem(EntityType<? extends PrisonGolem> type, Level level) {
        super(type, level);
        if (!level.isClientSide) {
            this.entityData.set(DATA_VARIANT, level.random.nextInt(2));
        }
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return baseAttributes(GolemServantConfig.PRISON);
    }

    @Override
    public GolemServantConfig.Section configSection() {
        return GolemServantConfig.PRISON;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.is(AstralSetDropin.PRISON_STEEL_BLOCK_ITEM.get())) {
            double amount = GolemServantConfig.PRISON.FEED_HEAL_AMOUNT.get();
            if (amount > 0.0D) {
                LivingEntity owner = getTrueOwner();
                if (owner == null || owner == player) {
                    if (getHealth() < getMaxHealth()) {
                        heal((float) amount);
                        if (!level().isClientSide()) {
                            playSound(SoundEvents.ANVIL_PLACE, 1.0F,
                                    (random.nextFloat() - random.nextFloat()) * 0.2F + 0.9F);
                            if (!player.getAbilities().instabuild) {
                                held.shrink(1);
                            }
                        }
                        return InteractionResult.sidedSuccess(level().isClientSide());
                    }
                    return InteractionResult.PASS;
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public int meleeAnticipationTicks() {
        return (AbstractGolemServant.SWEEP_IMPACT_TICK + AbstractGolemServant.SMASH_IMPACT_TICK) / 2;
    }

    @Override
    public double meleeAnticipationMax() {
        return 2.5D;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> goal instanceof net.minecraft.world.entity.ai.goal.MeleeAttackGoal);
        this.goalSelector.addGoal(1, new PrisonMeleeGoal(this, 1.0D, true));
    }

    private static final class PrisonMeleeGoal extends net.minecraft.world.entity.ai.goal.MeleeAttackGoal {
        private final PrisonGolem golem;

        PrisonMeleeGoal(PrisonGolem golem, double speed, boolean followTarget) {
            super(golem, speed, followTarget);
            this.golem = golem;
        }

        @Override
        public boolean canUse() {
            return !golem.isAttackAnimating() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return golem.isAttackAnimating() || super.canContinueToUse();
        }

        @Override
        public void checkAndPerformAttack(net.minecraft.world.entity.LivingEntity target, double distanceSqr) {
            if (golem.isAttackAnimating()) {
                return;
            }
            if (meleeGapDistanceSqr(golem, target) <= golem.meleeAnticipatedReachSqr(target)
                    && this.isTimeToAttack()) {
                golem.doHurtTarget(target);
                this.resetAttackCooldown();
            }
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!isAlive()) {
            return;
        }
        tickPrisonAttack();
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDeadOrDying();
    }

    @Override
    public boolean isPushable() {
        return !this.isDeadOrDying();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance instance) {
        MobEffect effect = instance.getEffect();
        if (effect == MobEffects.MOVEMENT_SLOWDOWN) {
            return false;
        }
        return super.canBeAffected(instance);
    }

    @Override
    public float rollAttackDamage(float damage) {
        return damage * (0.75F + this.random.nextFloat() * 0.5F) + FLOOR_BONUS;
    }

    @Override
    public void onAttackHit(Entity target) {
        if (level().isClientSide || !(target instanceof LivingEntity living) || !living.isAlive()) {
            return;
        }
        living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 150, 1), this);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, VARIANT_RED);
        this.entityData.define(DATA_SUMMON_TICK, 0);
    }

    public boolean isPink() {
        return this.entityData.get(DATA_VARIANT) == VARIANT_PINK;
    }

    @Override
    public void setBoundPos(BlockPos pos) {
        if (pos != null && !level().isClientSide) {
            BlockPos anchor = MobilePrisonBlockEntity.findAnchor(level(), pos);
            if (anchor != null) {
                addPrisonBind(anchor);
                this.legacyBindChecked = true;
                super.setBoundPos(null);
                return;
            }
        }
        super.setBoundPos(pos);
    }

    private void addPrisonBind(BlockPos anchor) {
        String dim = level().dimension().location().toString();
        for (PrisonBind bind : prisonBinds) {
            if (bind.anchor().equals(anchor) && bind.dim().equals(dim)) {
                return;
            }
        }
        this.prisonBinds.add(new PrisonBind(anchor.immutable(), dim));
    }

    private void migrateLegacyPrisonBind() {
        if (legacyBindChecked) {
            return;
        }
        legacyBindChecked = true;
        BlockPos bound = getBoundPos();
        if (bound == null || !prisonBinds.isEmpty()) {
            return;
        }
        BlockPos anchor = MobilePrisonBlockEntity.findAnchor(level(), bound);
        if (anchor == null) {
            return;
        }
        addPrisonBind(anchor);
        super.setBoundPos(null);
    }

    public boolean isSummoning() {
        return this.entityData.get(DATA_SUMMON_TICK) > 0;
    }

    public float summonProgress(float partialTick) {
        int remaining = this.entityData.get(DATA_SUMMON_TICK);
        if (remaining <= 0) {
            return 1.0F;
        }
        return Mth.clamp((SUMMON_TICKS - remaining + partialTick) / (float) SUMMON_TICKS, 0.0F, 1.0F);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType reason, SpawnGroupData data, CompoundTag tag) {
        if (reason == MobSpawnType.MOB_SUMMONED) {
            this.entityData.set(DATA_SUMMON_TICK, SUMMON_TICKS);
        }
        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            migrateLegacyPrisonBind();
            int remaining = this.entityData.get(DATA_SUMMON_TICK);
            if (remaining > 0) {
                this.entityData.set(DATA_SUMMON_TICK, remaining - 1);
                this.getNavigation().stop();
                this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.entityData.get(DATA_VARIANT));
        tag.putInt("SweepStreak", this.sweepStreak);
        if (!prisonBinds.isEmpty()) {
            ListTag list = new ListTag();
            for (PrisonBind bind : prisonBinds) {
                CompoundTag entry = new CompoundTag();
                entry.put("Pos", NbtUtils.writeBlockPos(bind.anchor()));
                entry.putString("Dim", bind.dim());
                list.add(entry);
            }
            tag.put("PrisonAnchors", list);
            PrisonBind first = prisonBinds.get(0);
            tag.put("PrisonAnchor", NbtUtils.writeBlockPos(first.anchor()));
            tag.putString("PrisonDim", first.dim());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_VARIANT, tag.getInt("Variant"));
        this.sweepStreak = tag.getInt("SweepStreak");
        this.prisonBinds.clear();
        ListTag list = tag.getList("PrisonAnchors", 10);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.getCompound(i);
            if (entry.contains("Pos", 10)) {
                this.prisonBinds.add(new PrisonBind(
                        NbtUtils.readBlockPos(entry.getCompound("Pos")).immutable(),
                        entry.getString("Dim")));
            }
        }
        if (this.prisonBinds.isEmpty() && tag.contains("PrisonAnchor", 10)) {
            this.prisonBinds.add(new PrisonBind(
                    NbtUtils.readBlockPos(tag.getCompound("PrisonAnchor")).immutable(),
                    tag.getString("PrisonDim")));
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean smash = this.sweepStreak >= SWEEP_STREAK_BEFORE_SMASH
                || this.random.nextDouble() * 100.0D < SMASH_CHANCE_PERCENT;
        this.sweepStreak = smash ? 0 : this.sweepStreak + 1;
        this.attackVariant = smash ? ATTACK_VARIANT_SMASH : ATTACK_VARIANT_SWEEP;
        this.attackImpactDone = false;
        this.pendingCaptureTarget = target;
        startAttack(smash ? AbstractGolemServant.ATTACK_SMASH : AbstractGolemServant.ATTACK_SWEEP,
                smash ? AbstractGolemServant.ATTACK_LENGTHS[AbstractGolemServant.ATTACK_SMASH]
                        : AbstractGolemServant.ATTACK_LENGTHS[AbstractGolemServant.ATTACK_SWEEP]);
        return true;
    }

    public int getMeleeVariant() {
        return this.attackVariant;
    }

    @Override
    public float[] recoveryImpactTicks() {
        return getAttackAnimation() == AbstractGolemServant.ATTACK_SWEEP
                ? AbstractGolemServant.SWEEP_RECOVERY_IMPACT
                : null;
    }

    public void tickPrisonAttack() {
        if (this.level().isClientSide || this.attackImpactDone || !isAttackAnimating()) {
            return;
        }
        boolean smash = this.attackVariant == ATTACK_VARIANT_SMASH;
        int impact = smash ? AbstractGolemServant.SMASH_IMPACT_TICK : AbstractGolemServant.SWEEP_IMPACT_TICK;
        if (!attackReached(impact)) {
            return;
        }
        this.attackImpactDone = true;
        double radius = smash ? SMASH_RADIUS : SWEEP_RADIUS;
        float bonus = smash ? SMASH_BONUS_DAMAGE : 0.0F;
        float baseDamage = rollAttackDamage((float) getAttributeValue(Attributes.ATTACK_DAMAGE)) + bonus;
        boolean captured = false;
        Entity captureTarget = this.pendingCaptureTarget;
        this.pendingCaptureTarget = null;
        if (captureTarget != null) {
            float captureBonus = bonus;
            if (smash && captureTarget instanceof LivingEntity livingTarget) {
                captureBonus += maxHealthPercentDamage(livingTarget);
            }
            if (tryCaptureDefeatedEnemy(captureTarget, captureBonus)) {
                captured = true;
            }
        }
        for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class,
                smash ? slamAttackBox(radius)
                        : attackBox(SWEEP_FORWARD_OFFSET, SWEEP_RADIUS, SWEEP_VERTICAL_RADIUS),
                this::canAttack)) {
            if (victim == this || isAlliedTo(victim)) {
                continue;
            }
            float damage = baseDamage + (smash ? maxHealthPercentDamage(victim) : 0.0F);
            if (victim.hurt(this.damageSources().mobAttack(this), damage)) {
                if (smash) {
                    applySlamKnockback(victim, SMASH_KNOCKBACK, SMASH_LAUNCH);
                } else {
                    applyMeleeKnockback(victim, SWEEP_KNOCKBACK);
                }
                onAttackHit(victim);
            }
        }
        if (smash) {
            spawnShockwave();
        }
        if (!captured) {
            this.playSound(net.minecraft.sounds.SoundEvents.IRON_GOLEM_ATTACK, 1.2F, smash ? 0.7F : 1.1F);
        }
    }

    @Override
    public double meleeStartReach(net.minecraft.world.entity.LivingEntity target) {
        return Math.min(super.meleeStartReach(target), SWEEP_REACH);
    }

    private void spawnShockwave() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        float yawRad = slamFacingYaw() * Mth.DEG_TO_RAD;
        double lookX = -Mth.sin(yawRad);
        double lookZ = Mth.cos(yawRad);
        double x = getX() + lookX * SMASH_FORWARD_OFFSET;
        double y = getY() + 0.15D;
        double z = getZ() + lookZ * SMASH_FORWARD_OFFSET;
        BlockState ground = groundStateBelow(server, x, getY(), z);
        BlockParticleOption blockParticles = new BlockParticleOption(ParticleTypes.BLOCK, ground);
        ColorUtil color = new ColorUtil(SHOCKWAVE_COLOR);
        double particleY = y + 0.05D;
        server.sendParticles(new SmashParticleOption(color, 2.5F, 8),
                x, particleY, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        server.sendParticles(new SmashParticleOption(color, 4.0F, 10),
                x, particleY, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        server.sendParticles(new SmashParticleOption(color, 5.5F, 12),
                x, particleY, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        ServerParticleUtil.windShockwaveParticle(server, color, (float) SHOCKWAVE_RADIUS, 0.0F, 12,
                new Vec3(x, y, z));
        for (int i = 0; i < 8; i++) {
            ServerParticleUtil.circularParticles(server, blockParticles, x, y, z,
                    (float) SHOCKWAVE_RADIUS);
        }
        this.playSound(net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE, 1.2F, 0.6F);
    }

    private static BlockState groundStateBelow(ServerLevel level, double x, double y, double z) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(
                Mth.floor(x), Mth.floor(y + 0.1D), Mth.floor(z));
        for (int i = 0; i < 4; i++) {
            BlockState state = level.getBlockState(cursor);
            if (!state.isAir()) {
                return state;
            }
            cursor.move(0, -1, 0);
        }
        return level.getBlockState(BlockPos.containing(x, y - 0.1D, z));
    }

    private boolean tryCaptureDefeatedEnemy(Entity target, float bonus) {
        if (level().isClientSide || !(target instanceof LivingEntity living)) return false;
        if (!living.isAlive() || living instanceof Player || isAlliedTo(living)) return false;
        if (getTrueOwner() == null) return false;
        BlockPos prison = pickPrisonForCapture(living);
        if (prison == null && GolemServantConfig.PRISON.CONVERT_REQUIRES_PRISON.get()) return false;
        if (living.getMaxHealth() > GolemServantConfig.PRISON.CONVERT_MAX_HEALTH.get()) return false;
        if (isBossLike(living)) return false;
        if (!wouldDefeat(living, bonus)) return false;
        if (random.nextFloat() * 100.0F >= GolemServantConfig.PRISON.CONVERT_CHANCE_PERCENT.get()) return false;
        EntityType<?> servantType = findServantType(living.getType(), living.getMaxHealth());
        if (servantType == null) return false;
        Entity created = servantType.create(level());
        if (!(created instanceof LivingEntity servant)) return false;
        return capture(living, servant, prison);
    }

    private List<BlockPos> livePrisonAnchors() {
        List<BlockPos> anchors = new ArrayList<>();
        if (level().isClientSide) {
            return anchors;
        }
        String dim = level().dimension().location().toString();
        Iterator<PrisonBind> it = prisonBinds.iterator();
        while (it.hasNext()) {
            PrisonBind bind = it.next();
            if (!bind.dim().isEmpty() && !bind.dim().equals(dim)) {
                continue;
            }
            BlockPos anchor = MobilePrisonBlockEntity.findAnchor(level(), bind.anchor());
            if (anchor == null) {
                it.remove();
                continue;
            }
            anchors.add(anchor);
        }
        return anchors;
    }

    private BlockPos pickPrisonForCapture(Entity victim) {
        BlockPos best = null;
        int bestCount = Integer.MAX_VALUE;
        double bestDistSqr = Double.MAX_VALUE;
        for (BlockPos anchor : livePrisonAnchors()) {
            int count = imprisonedServantCount(anchor);
            double distSqr = victim.distanceToSqr(Vec3.atCenterOf(anchor));
            if (count < bestCount || (count == bestCount && distSqr < bestDistSqr)) {
                best = anchor;
                bestCount = count;
                bestDistSqr = distSqr;
            }
        }
        return best;
    }

    private int imprisonedServantCount(BlockPos anchor) {
        AABB box = new AABB(MobilePrisonBlockEntity.interiorOf(anchor))
                .inflate(PRISON_CENSUS_RADIUS);
        int count = 0;
        for (LivingEntity other : level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (other instanceof IServant servant && anchor.equals(servant.getBoundPos())) {
                count++;
            }
        }
        return count;
    }

    private static boolean isBossLike(LivingEntity living) {
        EntityType<?> type = living.getType();
        return type.is(GOETY_BOSSES) || type.is(GOETY_MINI_BOSSES) || type.is(FORGE_BOSSES);
    }

    private static float maxHealthPercentDamage(LivingEntity target) {
        return (float) (target.getMaxHealth() * SMASH_MAX_HEALTH_DAMAGE_PERCENT / 100.0D);
    }

    private boolean wouldDefeat(LivingEntity target, float bonus) {
        float damage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE);
        float rolled = rollAttackDamage(damage) + bonus;
        float afterArmor = CombatRules.getDamageAfterAbsorb(rolled,
                (float) target.getArmorValue(),
                (float) target.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        return afterArmor >= target.getHealth() + target.getAbsorptionAmount();
    }

    private boolean capture(LivingEntity victim, LivingEntity servant, BlockPos prison) {
        LivingEntity owner = getTrueOwner();
        double victimX = victim.getX();
        double victimY = victim.getY();
        double victimZ = victim.getZ();
        servant.moveTo(victim.getX(), victim.getY(), victim.getZ(), victim.getYRot(), victim.getXRot());
        if (owner != null && servant instanceof IOwned owned) {
            owned.setTrueOwner(owner);
            owned.setOwnerId(owner.getUUID());
        }
        if (servant instanceof Mob spawnMob) {
            spawnMob.finalizeSpawn((ServerLevel) level(),
                    level().getCurrentDifficultyAt(servant.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        }
        transferVictimNbt(victim, servant);
        if (owner != null && servant instanceof IOwned rebound) {
            rebound.setTrueOwner(owner);
            rebound.setOwnerId(owner.getUUID());
        }
        float percent = GolemServantConfig.PRISON.CONVERT_HEALTH_PERCENT.get().floatValue();
        servant.setHealth(Math.max(1.0F, servant.getMaxHealth() * percent / 100.0F));
        if (servant instanceof IOwned recaptured) {
            recaptured.setHostile(false);
            recaptured.setNatural(false);
        }
        if (servant instanceof Mob keepMob) {
            keepMob.setPersistenceRequired();
        }
        boolean toPrison = prison != null && GolemServantConfig.PRISON.TELEPORT_TO_PRISON.get();
        if (toPrison) {
            imprison(servant, prison);
        }
        if (!level().addFreshEntity(servant)) {
            return false;
        }
        victim.discard();
        this.playSound(net.minecraft.sounds.SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        if (level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.PORTAL,
                    victim.getX(), victim.getY(0.8D), victim.getZ(), 32, 0.4D, 0.6D, 0.4D, 0.3D);
            if (toPrison) {
                server.sendParticles(ParticleTypes.PORTAL,
                        servant.getX(), servant.getY(0.8D), servant.getZ(), 32, 0.4D, 0.6D, 0.4D, 0.3D);
            }
        }
        if (toPrison) {
            playTeleportSound(victimX, victimY, victimZ);
            playTeleportSound(servant.getX(), servant.getY(), servant.getZ());
        }
        return true;
    }

    private void playTeleportSound(double x, double y, double z) {
        level().playSound(null, x, y, z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);
    }

    private static final java.util.List<String> CAPTURE_CARRY_BAN = java.util.List.of(
            "id", "UUID", "UUIDMost", "UUIDLeast", "Pos", "Motion", "Rotation",
            "FallDistance", "Fire", "Air", "Health", "AbsorptionAmount", "HurtTime",
            "HurtByTimestamp", "DeathTime", "AttributeModifiers", "Passengers",
            "Team", "Tags", "Owner", "Leashed", "DataOwner", "PersistenceRequired",
            "Invulnerable", "HandItems", "ArmorItems", "bodyArmorItem", "Saddle", "GoetyOwner");

    private static void transferVictimNbt(LivingEntity victim, LivingEntity servant) {
        CompoundTag carry = new CompoundTag();
        try {
            victim.saveWithoutId(carry);
        } catch (RuntimeException | LinkageError ignored) {
            return;
        }
        for (String ban : CAPTURE_CARRY_BAN) {
            carry.remove(ban);
        }
        carry.putFloat("Health", servant.getMaxHealth());
        try {
            servant.load(carry);
        } catch (RuntimeException | LinkageError ignored) {
        }
        net.minecraft.world.entity.EquipmentSlot[] slots = {
                net.minecraft.world.entity.EquipmentSlot.MAINHAND,
                net.minecraft.world.entity.EquipmentSlot.OFFHAND,
                net.minecraft.world.entity.EquipmentSlot.HEAD,
                net.minecraft.world.entity.EquipmentSlot.CHEST,
                net.minecraft.world.entity.EquipmentSlot.LEGS,
                net.minecraft.world.entity.EquipmentSlot.FEET};
        for (net.minecraft.world.entity.EquipmentSlot slot : slots) {
            ItemStack worn = victim.getItemBySlot(slot);
            if (!worn.isEmpty()) {
                servant.setItemSlot(slot, worn.copy());
            }
        }
    }

    @Override
    public void tryKill(Player player) {
        if (level().isClientSide()) {
            return;
        }
        if (getKillChance() <= 0) {
            warnKill(player);
            return;
        }
        hurt(ModDamageSource.getDamageSource(level(), ModDamageSource.DISMISSED), Float.MAX_VALUE);
    }

    private void imprison(LivingEntity servant, BlockPos anchor) {
        BlockPos cell = MobilePrisonBlockEntity.interiorOf(anchor);
        servant.moveTo(cell.getX() + 0.5D, cell.getY(), cell.getZ() + 0.5D,
                random.nextFloat() * 360.0F, 0.0F);
        servant.teleportTo(servant.getX(), servant.getY(), servant.getZ());
        servant.resetFallDistance();
        if (servant instanceof IServant bound) {
            bound.setBoundPos(anchor);
            bound.setWandering(true);
            bound.setStaying(false);
        }
    }

    private EntityType<?> findServantType(EntityType<?> targetType, float targetMaxHealth) {
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(targetType);
        if (id == null || isCaptureBanned(id)) return null;
        return PrisonServantResolver.resolve(level(), id, targetMaxHealth);
    }

    private static EntityType<?> goetyEntity(String path) {
        return BuiltInRegistries.ENTITY_TYPE.getOptional(ResourceLocation.fromNamespaceAndPath("goety", path)).orElse(null);
    }

    private static boolean isCaptureBanned(ResourceLocation id) {
        return id.equals(ResourceLocation.fromNamespaceAndPath("goety", "vizier_clone"))
                || id.equals(ResourceLocation.fromNamespaceAndPath("minecraft", "ender_dragon"))
                || id.equals(ResourceLocation.fromNamespaceAndPath("goety", "ender_keeper"))
                || id.equals(ResourceLocation.fromNamespaceAndPath("goety", "obsidian_monolith"))
                || id.equals(ResourceLocation.fromNamespaceAndPath("twilightforest", "lich"))
                || id.equals(ResourceLocation.fromNamespaceAndPath("goetyawaken", "hostile_mushroom_monstrosity"));
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (getTrueOwner() == null) {
            return;
        }
        spawnAtLocation(new ItemStack(Blocks.REDSTONE_BLOCK, 3 + random.nextInt(2)), 0.3F);
        spawnAtLocation(new ItemStack(AstralSetDropin.PRISON_STEEL_BLOCK_ITEM.get(), 2 + random.nextInt(3)), 0.3F);
    }

    public static final int AMBIENT_SOUND_INTERVAL = 120;

    @Override
    public SoundEvent getAmbientSound() {
        return GeneratedMod.PRISON_GOLEM_AMBIENT.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return AMBIENT_SOUND_INTERVAL;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return GeneratedMod.PRISON_GOLEM_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return GeneratedMod.PRISON_GOLEM_DEATH.get();
    }

    @Override
    public boolean usesAnimationDrivenFootsteps() {
        return true;
    }

    @Override
    public void playFootstepSound() {
        playSound(GeneratedMod.PRISON_GOLEM_STEP.get(), 1.0F, 1.0F);
    }
}
