package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.FlyingMob;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.ai.FollowOwnerGoal;
import cn.blockforge.generated.goetysupreme.ai.WitheringSkeletonFollowGoal;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ServantTargetRules;
import cn.blockforge.generated.goetysupreme.util.WitheringMinionControl;
import cn.blockforge.generated.goetysupreme.data.WitheringMinionLedger;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class WitheringMinionControl {
    public static final int LIMIT = 8;
    public static final String OWNER = "goety_supreme:withering_summoner";
    private static final String SPEED_GIVEN = "goety_supreme:withering_speed_given";
    private static final Set<WitherSkeletonServant> ATTACHED =
            Collections.newSetFromMap(new WeakHashMap<>());

    private WitheringMinionControl() { }

    public static void initialize(WitherSkeletonServant minion, LivingEntity owner) {
        CompoundTag data = minion.getPersistentData();
        data.putUUID(OWNER, owner.getUUID());
        if (minion.level() instanceof ServerLevel server
                && !WitheringMinionLedger.get(server).register(minion.getUUID(), owner.getUUID())) {
            minion.discard();
            return;
        }
        if (!data.getBoolean(SPEED_GIVEN)) {
            data.putBoolean(SPEED_GIVEN, true);
            minion.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, -1, 0,
                    false, false, false)); // 不显示药水粒子，也不显示状态图标。
        }
        attach(minion);
    }

    public static LivingEntity summoner(WitherSkeletonServant minion) {
        CompoundTag data = minion.getPersistentData();
        if (data.hasUUID(OWNER) && minion.level() instanceof ServerLevel server) {
            Entity owner = server.getEntity(data.getUUID(OWNER));
            return owner instanceof WitherServant wither ? wither : null;
        }
        return null;
    }

    public static int count(LivingEntity owner, ServerLevel server) {
        for (ServerLevel level : server.getServer().getAllLevels()) {
            java.util.List<WitherSkeletonServant> owned = new java.util.ArrayList<>();
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof WitherSkeletonServant minion && minion.isAlive()
                        && owner.getUUID().equals(minion.getOwnerId())) owned.add(minion);
            }
            for (WitherSkeletonServant minion : owned) initialize(minion, owner);
        }
        return WitheringMinionLedger.get(server).count(owner.getUUID());
    }

    private static void attach(WitherSkeletonServant minion) {
        if (!ATTACHED.add(minion)) return;
        if (minion.getAttribute(Attributes.FOLLOW_RANGE) != null) {
            minion.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(WitheringSkeletonFollowGoal.FOLLOW_RANGE);
        }
        minion.goalSelector.removeAllGoals(goal -> goal.getClass().getName()
                .equals("com.Polarice3.Goety.common.entities.ally.Summoned$FollowOwnerGoal"));
        minion.goalSelector.addGoal(4, new WitheringSkeletonFollowGoal(minion));
        minion.targetSelector.removeAllGoals(goal -> goal instanceof SummonTargetGoal);
        minion.targetSelector.addGoal(1, new GroundFirstTargetGoal(minion));
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof WitherSkeletonServant minion)
                || !(minion.level() instanceof ServerLevel)) return;
        if (!minion.getPersistentData().hasUUID(OWNER)) {
            if (minion.getTrueOwner() instanceof WitherServant owner && owner.isStarEmbedded()) {
                initialize(minion, owner);
            } else return;
        } else if (!ATTACHED.contains(minion)) {
            ServerLevel server = (ServerLevel) minion.level();
            if (!WitheringMinionLedger.get(server).register(minion.getUUID(),
                    minion.getPersistentData().getUUID(OWNER))) {
                minion.discard();
                return;
            }
            attach(minion);
        }
        if (!minion.isAlive()) return;
        if (minion.tickCount % 20 == 0 && !minion.isCommanded()
                && minion.getTarget() != null && isAirTarget(minion.getTarget())) {
            LivingEntity ground = GroundFirstTargetGoal.nearestGround(minion);
            if (ground != null) minion.setTarget(ground);
        }
    }

    @SubscribeEvent
    public static void onLeave(EntityLeaveLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof WitherSkeletonServant && event.getLevel() instanceof ServerLevel server
                && entity.getPersistentData().hasUUID(OWNER)
                && entity.getRemovalReason() != null && entity.getRemovalReason().shouldDestroy()) {
            WitheringMinionLedger.get(server).release(entity.getUUID());
            ATTACHED.remove(entity);
        }
    }

    public static boolean isAirTarget(LivingEntity target) {
        if (target.onGround()) return false;
        return target instanceof FlyingMob
                || target instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon
                || target instanceof net.minecraft.world.entity.animal.FlyingAnimal
                || target.isNoGravity() || target.isFallFlying()
                || target instanceof Mob mob && (mob.getNavigation() instanceof FlyingPathNavigation
                        || mob.getMoveControl() instanceof FlyingMoveControl);
    }

    private static final class GroundFirstTargetGoal extends SummonTargetGoal {
        private final WitherSkeletonServant minion;

        GroundFirstTargetGoal(WitherSkeletonServant minion) {
            super(minion, candidate -> validEnemy(minion, candidate));
            this.minion = minion;
        }

        private static boolean validEnemy(WitherSkeletonServant minion, LivingEntity candidate) {
            return candidate instanceof Enemy && candidate.isAlive() && minion.canAttack(candidate)
                    && !minion.isAlliedTo(candidate) && !ServantTargetRules.isDamned(candidate);
        }

        static LivingEntity nearestGround(WitherSkeletonServant minion) {
            TargetingConditions conditions = TargetingConditions.forCombat().range(32.0D)
                    .selector(candidate -> validEnemy(minion, candidate) && !isAirTarget(candidate));
            LivingEntity best = null;
            double distance = Double.MAX_VALUE;
            for (LivingEntity candidate : minion.level().getEntitiesOfClass(LivingEntity.class,
                    minion.getBoundingBox().inflate(32.0D, 12.0D, 32.0D))) {
                if (!conditions.test(minion, candidate)) continue;
                double next = minion.distanceToSqr(candidate);
                if (next < distance) { best = candidate; distance = next; }
            }
            return best;
        }

        @Override
        public void findTarget() {
            this.target = nearestGround(minion);
            if (this.target == null) super.findTarget(); // 没有地面敌人时才考虑飞行单位。
        }
    }
}
