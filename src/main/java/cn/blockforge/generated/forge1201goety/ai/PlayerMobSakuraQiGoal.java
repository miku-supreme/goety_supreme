package cn.blockforge.generated.goetysupreme.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.compat.SakuraGearCompat;

public final class PlayerMobSakuraQiGoal extends Goal {

    private static final double MIN_RANGE = 3.0D;
    private static final double MAX_RANGE = 9.0D;

    private static final int MAX_LIFE_TICKS = 3;

    private final PlayerMobServant servant;
    private int life;
    private boolean fired;

    public PlayerMobSakuraQiGoal(PlayerMobServant servant) {
        this.servant = servant;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return ready();
    }

    @Override
    public boolean canContinueToUse() {
        return !fired && life < MAX_LIFE_TICKS && ready();
    }

    @Override
    public void start() {
        life = 0;
        fired = false;
        servant.getNavigation().stop();
        servant.setCombatChaseSprint(false);
        LivingEntity target = servant.getTarget();
        if (target != null) {
            servant.getLookControl().setLookAt(target, 30.0F, 30.0F);
            servant.lookAt(target, 180.0F, 180.0F);
        }
    }

    @Override
    public void tick() {
        life++;
        LivingEntity target = servant.getTarget();
        if (target == null) {
            return;
        }
        servant.getNavigation().stop();
        servant.getLookControl().setLookAt(target, 30.0F, 30.0F);
        servant.lookAt(target, 180.0F, 180.0F);
        if (SakuraGearCompat.tryFireQi(servant)) {
            fired = true;
        }
    }

    private boolean ready() {
        if (servant.level().isClientSide || !SakuraGearCompat.isLoaded()) {
            return false;
        }
        LivingEntity target = servant.getTarget();
        if (target == null || !target.isAlive() || !servant.isValidCombatTargetForGoal(target)) {
            return false;
        }
        ItemStack main = servant.getMainHandItem();
        if (!SakuraGearCompat.isQiSword(main)) {
            return false;
        }
        double distanceSqr = servant.distanceToSqr(target);
        if (distanceSqr < MIN_RANGE * MIN_RANGE || distanceSqr > MAX_RANGE * MAX_RANGE) {
            return false;
        }
        return servant.getSensing().hasLineOfSight(target) && SakuraGearCompat.isQiReady(servant);
    }
}
