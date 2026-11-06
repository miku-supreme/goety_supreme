package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.util.ServantSkillContinuity;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class ServantSkillContinuity {

    public static final double REACQUIRE_RADIUS = 16.0D;

    private ServantSkillContinuity() {
    }

    public static LivingEntity reacquire(Mob servant, LivingEntity exclude) {
        if (servant == null || !servant.isAlive() || servant.level().isClientSide()) {
            return null;
        }
        LivingEntity best = null;
        double bestDistance = Double.MAX_VALUE;
        for (LivingEntity candidate : candidates(servant)) {
            if (candidate == exclude) {
                continue;
            }
            double distance = candidate.distanceToSqr(servant);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }
        return best;
    }

    private static List<LivingEntity> candidates(Mob servant) {
        Level level = servant.level();
        AABB box = servant.getBoundingBox().inflate(REACQUIRE_RADIUS);
        return level.getEntitiesOfClass(LivingEntity.class, box,
                candidate -> candidate != servant
                        && candidate.isAlive() && !candidate.isSpectator()
                        && candidate instanceof Enemy
                        && servant.canAttack(candidate)
                        && !servant.isAlliedTo(candidate)
                        && candidate.distanceToSqr(servant) <= REACQUIRE_RADIUS * REACQUIRE_RADIUS);
    }

    @SubscribeEvent
    public static void onTargetDeath(LivingDeathEvent event) {
        LivingEntity dead = event.getEntity();
        if (dead == null || dead.level().isClientSide()) {
            return;
        }
        Level level = dead.level();
        AABB box = dead.getBoundingBox().inflate(REACQUIRE_RADIUS);
        for (Mob mob : level.getEntitiesOfClass(Mob.class, box, ServantSkillContinuity::isServant)) {
            if (mob.getTarget() != dead) {
                continue;
            }
            LivingEntity replacement = reacquire(mob, dead);
            if (replacement != null) {
                mob.setTarget(replacement);
            }
        }
    }

    public static boolean isServant(Mob mob) {
        return mob instanceof IOwned owned && owned.getTrueOwner() != null;
    }
}
