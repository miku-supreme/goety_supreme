package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.Comparator;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancerServant;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.WarlordNecromancerHate;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordServant;

public class WitherWarlordServant extends AbstractWitherWarlord {

    public static final double TANTRUM_RADIUS = 6.0D;

    private static final int TANTRUM_TAUNT_TICKS = 32;

    private static final int TANTRUM_RETREAT_TICKS = 60;

    private static final double TANTRUM_RETREAT_DISTANCE = 10.0D;

    private static final double TANTRUM_RETREAT_SPEED = 1.0D;

    private static final int TANTRUM_COOLDOWN_TICKS = 240;

    public WitherWarlordServant(EntityType<? extends WitherWarlordServant> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.followGoal();
        this.targetRetaliateGoal();
        this.ownerHurtGoals();
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, GreenSmith.class, true, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class,
                10, true, false,
                victim -> WarlordNecromancerHate.isNecromancerKind(victim) && !this.isFriendlyTo(victim)));
        this.targetSelector.addGoal(2, new SummonTargetGoal(this, true, true));
        this.goalSelector.addGoal(3, new TantrumGoal(this));
    }

    @Override
    public int getSummonLimit(LivingEntity owner) {
        return ServantLimits.limitFor(getType());
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
        onExecuted(player);
    }

    @Override
    public void warnKill(Player player) {
        setKillChance(60);
        if (player != null) {
            player.displayClientMessage(Component.translatable(
                    "info.goety_supreme.servant.try_kill.wither_warlord"), true);
        }
    }

    private void onExecuted(Player player) {
        if (!(level() instanceof ServerLevel serverLevel)) {
            discard();
            return;
        }
        WitherWarlord hostile = ModEntityTypes.WITHER_WARLORD.get().create(serverLevel);
        if (hostile == null) {
            hurt(ModDamageSource.getDamageSource(level(), ModDamageSource.DISMISSED), Float.MAX_VALUE);
            return;
        }
        CompoundTag carry = new CompoundTag();
        try {
            saveWithoutId(carry);
            carry.remove("UUID");
            carry.remove("UUIDMost");
            carry.remove("UUIDLeast");
        } catch (RuntimeException | LinkageError ignored) {
            carry = new CompoundTag();
        }
        hostile.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        hostile.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        hostile.setPersistenceRequired();
        if (!carry.isEmpty()) {
            try {
                hostile.load(carry);
            } catch (RuntimeException | LinkageError ignored) {
            }
        }
        hostile.setTrueOwner(null);
        hostile.setOwnerId(null);
        hostile.setWandering(false);
        hostile.setStaying(false);
        hostile.setHasLifespan(false);
        hostile.setItemSlot(EquipmentSlot.HEAD, new ItemStack(GeneratedMod.WITHER_WARLORD_HELMET.get()));
        hostile.setDropChance(EquipmentSlot.HEAD, 0.0F);
        for (MobEffectInstance effect : getActiveEffects()) {
            hostile.addEffect(new MobEffectInstance(effect));
        }
        hostile.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        hostile.setYRot(getYRot());
        hostile.setXRot(getXRot());
        hostile.setYBodyRot(this.yBodyRot);
        hostile.setYHeadRot(getYHeadRot());
        hostile.setDeltaMovement(getDeltaMovement());
        hostile.setOldPosAndRot();
        hostile.setHealth(hostile.getMaxHealth());
        if (player != null && !player.isCreative() && !player.isSpectator()) {
            hostile.setTarget(player);
        }
        serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, getX(), getY(2.0D), getZ(),
                40, 0.8D, 1.2D, 0.8D, 0.05D);
        playSound(SoundEvents.WITHER_SPAWN, 3.0F, 0.8F);
        if (serverLevel.addFreshEntity(hostile)) {
            hostile.performBetrayalSkills();
            GoetySummonFx.play(serverLevel, hostile);
            discard();
        }
    }

    private static final class TantrumGoal extends Goal {

        private static final int SEARCH_INTERVAL = 20;

        private final WitherWarlordServant warlord;
        private int cooldown;
        private int searchCooldown;
        private int elapsed;
        @Nullable
        private GreenSmithServant target;

        TantrumGoal(WitherWarlordServant warlord) {
            this.warlord = warlord;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.cooldown > 0) {
                --this.cooldown;
                return false;
            }
            WitherWarlordServant lord = this.warlord;
            if (lord.level().isClientSide() || lord.isDeadOrDying()) {
                return false;
            }
            if (isEngaged(lord)) {
                return false;
            }
            if (!lord.isBladeSheathed()) {
                return false;
            }
            if (this.searchCooldown > 0) {
                --this.searchCooldown;
                return false;
            }
            this.searchCooldown = SEARCH_INTERVAL;
            GreenSmithServant smith = lord.level()
                    .getEntitiesOfClass(GreenSmithServant.class,
                            lord.getBoundingBox().inflate(TANTRUM_RADIUS),
                            other -> other.isAlive())
                    .stream()
                    .filter(other -> lord.distanceTo(other) <= TANTRUM_RADIUS)
                    .min(Comparator.comparingDouble(lord::distanceToSqr))
                    .orElse(null);
            if (smith == null) {
                return false;
            }
            this.target = smith;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            if (this.elapsed >= TANTRUM_TAUNT_TICKS + TANTRUM_RETREAT_TICKS) {
                return false;
            }
            if (isEngaged(this.warlord)) {
                return false;
            }
            GreenSmithServant smith = this.target;
            if (smith == null || !smith.isAlive()) {
                return false;
            }
            return this.elapsed <= TANTRUM_TAUNT_TICKS
                    || this.warlord.distanceTo(smith) < TANTRUM_RETREAT_DISTANCE;
        }

        private static boolean isEngaged(WitherWarlordServant lord) {
            LivingEntity engaged = lord.getTarget();
            if (engaged != null && engaged.isAlive()) {
                return true;
            }
            return lord.isCombatStance() || lord.isActionLocked();
        }

        @Override
        public void start() {
            this.elapsed = 0;
            this.warlord.getNavigation().stop();
            this.warlord.setTantrumAnimating(true);
            this.faceTarget();
            this.angryParticles();
        }

        @Override
        public void tick() {
            ++this.elapsed;
            GreenSmithServant smith = this.target;
            if (smith == null) {
                return;
            }
            if (this.elapsed <= TANTRUM_TAUNT_TICKS) {
                this.warlord.getNavigation().stop();
                this.faceTarget();
                if (this.elapsed % 6 == 0) {
                    this.angryParticles();
                }
                return;
            }
            if (this.warlord.isTantrumAnimating()) {
                this.warlord.setTantrumAnimating(false);
            }
            Vec3 away = this.warlord.position().subtract(smith.position());
            if (away.lengthSqr() < 1.0E-4D) {
                away = new Vec3(1.0D, 0.0D, 0.0D);
            }
            Vec3 dest = this.warlord.position().add(away.normalize().scale(TANTRUM_RADIUS));
            this.warlord.getNavigation().moveTo(dest.x, dest.y, dest.z, TANTRUM_RETREAT_SPEED);
            this.warlord.getLookControl().setLookAt(dest.x, dest.y, dest.z);
        }

        @Override
        public void stop() {
            this.warlord.setTantrumAnimating(false);
            this.warlord.getNavigation().stop();
            this.cooldown = TANTRUM_COOLDOWN_TICKS;
            this.target = null;
        }

        private void faceTarget() {
            GreenSmithServant smith = this.target;
            if (smith != null) {
                this.warlord.getLookControl().setLookAt(smith, 30.0F, 30.0F);
            }
        }

        private void angryParticles() {
            if (!(this.warlord.level() instanceof ServerLevel server)) {
                return;
            }
            double x = this.warlord.getX();
            double y = this.warlord.getY() + this.warlord.getBbHeight() * 0.92D;
            double z = this.warlord.getZ();
            server.sendParticles(ParticleTypes.ANGRY_VILLAGER,
                    x, y, z, 4, 0.45D, 0.25D, 0.45D, 0.0D);
        }
    }
}
