package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSoulJarGuard;
import cn.blockforge.generated.goetysupreme.mixin.ReviveServantItemSoulJarGuardMixin;

public final class GreenSmithSoulJarGuard {

    private GreenSmithSoulJarGuard() {
    }

    public static boolean isGreenSmithSummoned(Entity target) {
        return target != null
                && target.getPersistentData().contains(AbstractGreenSmith.NECROMANCER_TAG);
    }

    public static boolean isGreenSmithSummonedNecromancer(LivingEntity target) {
        return isGreenSmithSummoned(target);
    }

    public static boolean shouldBlockSoulJar(Entity target) {
        if (target == null) {
            return false;
        }
        if (target.level().isClientSide()) {
            return true;
        }
        return isGreenSmithSummoned(target);
    }

    public static void inheritSummonTag(Entity summoned, LivingEntity summoner) {
        if (summoned == null || summoner == null || summoned == summoner) {
            return;
        }
        if (!(summoned instanceof AbstractNecromancer)) {
            return;
        }
        if (summoned.getPersistentData().contains(AbstractGreenSmith.NECROMANCER_TAG)) {
            return;
        }
        if (summoner.getPersistentData().contains(AbstractGreenSmith.NECROMANCER_TAG)) {
            summoned.getPersistentData().putUUID(AbstractGreenSmith.NECROMANCER_TAG,
                    summoner.getPersistentData().getUUID(AbstractGreenSmith.NECROMANCER_TAG));
        }
    }

    public static boolean neutralizeJar(Entity entity, ItemStack jar) {
        if (!isGreenSmithSummoned(entity)) {
            return false;
        }
        if (jar != null) {
            jar.setCount(0);
        }
        return true;
    }
}
