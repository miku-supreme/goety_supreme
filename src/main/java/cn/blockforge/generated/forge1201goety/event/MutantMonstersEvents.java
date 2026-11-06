package cn.blockforge.generated.goetysupreme.event;

import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import java.lang.reflect.Field;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompatManager;
import cn.blockforge.generated.goetysupreme.event.MutantMonstersEvents;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServantArrow;
import cn.blockforge.generated.goetysupreme.api.RoarGuaranteeTarget;
import cn.blockforge.generated.goetysupreme.event.ServantHandler;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.ai.WildMutantRoarGuarantee;

public final class MutantMonstersEvents {

    private static final String TRACK_SUMMONER_GOAL =
            "fuzs.mutantmonsters.world.entity.ai.goal.TrackSummonerGoal";

    private static final Field TRACK_SUMMONER;

    static {
        Field summonerField = null;
        try {
            Class<?> goalClass = Class.forName(TRACK_SUMMONER_GOAL);
            summonerField = goalClass.getDeclaredField("mutantZombie");
            summonerField.setAccessible(true);
        } catch (ReflectiveOperationException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
        }
        TRACK_SUMMONER = summonerField;
    }

    private MutantMonstersEvents() {
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (event.getEntity() instanceof fuzs.mutantmonsters.world.entity.mutant.MutantEnderman enderman) {
            var attack = enderman.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE);
            if (attack != null && attack.getBaseValue() != 10.0D) {
                attack.setBaseValue(10.0D);
            }
            return;
        }
        if (!(event.getEntity() instanceof MutantZombie zombie)) {
            return;
        }
        if (!(zombie instanceof RoarGuaranteeTarget target)) {
            return;
        }
        if (target.goetySupreme$getGoalSelector().getAvailableGoals().stream()
                .anyMatch(wrapped -> wrapped.getGoal() instanceof WildMutantRoarGuarantee)) {
            return;
        }
        target.goetySupreme$getGoalSelector().addGoal(-1, new WildMutantRoarGuarantee(zombie));
    }

    @SubscribeEvent
    public static void addBoneDamage(LivingHurtEvent event) {
        MutantSkeletonServantArrow.addBoneDamage(event);
    }

    @SubscribeEvent
    public static void onSummonedZombieJoin(EntityJoinLevelEvent event) {
        if (!MutantMonstersCompat.isLoaded()) {
            return;
        }
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof Zombie zombie)
                || !hasMutantSummonerGoal(zombie) || !(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        MutantZombie summoner = getSummoner(zombie);
        if (summoner == null || summoner.isRemoved()) {
            return;
        }

        GoetyZombieServant servant = ModEntityTypes.GOETY_ZOMBIE_SERVANT.get().create(level);
        if (servant == null) {
            return;
        }

        servant.moveTo(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.getYRot(), zombie.getXRot());
        servant.setTrueOwner(summoner);
        servant.finalizeSpawn(level, level.getCurrentDifficultyAt(zombie.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);

        servant.restoreFrom(zombie);
        servant.setUUID(UUID.randomUUID());
        servant.moveTo(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.getYRot(), zombie.getXRot());
        servant.setTrueOwner(summoner);
        servant.setTarget(null);
        servant.setLastHurtByMob(null);
        servant.setLastHurtMob(null);
        servant.setNatural(false);
        servant.setHostile(false);
        servant.setWandering(false);
        servant.setStaying(false);
        servant.setLimitedLife(ServantLimits.SUMMONED_LIFESPAN_TICKS);
        servant.setPersistenceRequired();

        if (!level.addFreshEntity(servant)) {
            return;
        }
        GoetySummonFx.play(level, servant);
        event.setCanceled(true);
    }

    private static boolean hasMutantSummonerGoal(Zombie zombie) {
        for (WrappedGoal wrappedGoal : zombie.goalSelector.getAvailableGoals()) {
            if (wrappedGoal.getGoal().getClass().getName().equals(TRACK_SUMMONER_GOAL)) {
                return true;
            }
        }
        return false;
    }

    private static MutantZombie getSummoner(Zombie zombie) {
        if (TRACK_SUMMONER == null) {
            return null;
        }
        for (WrappedGoal wrappedGoal : zombie.goalSelector.getAvailableGoals()) {
            Goal goal = wrappedGoal.getGoal();
            if (!goal.getClass().getName().equals(TRACK_SUMMONER_GOAL)) {
                continue;
            }
            try {
                Object summoner = TRACK_SUMMONER.get(goal);
                return summoner instanceof MutantZombie mutantZombie ? mutantZombie : null;
            } catch (IllegalAccessException ignored) {
                return null;
            }
        }
        return null;
    }
}
