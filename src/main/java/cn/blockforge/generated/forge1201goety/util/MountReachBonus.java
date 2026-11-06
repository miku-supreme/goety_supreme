package cn.blockforge.generated.goetysupreme.util;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraftforge.common.ForgeMod;
import cn.blockforge.generated.goetysupreme.util.MountReachBonus;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.mixin.GoetyMountReachMixin;

public final class MountReachBonus {

    public static final double WITHER_SKELETON_HORSE = 1.0D;

    public static final double SKELETON_HORSE = 0.5D;

    public static final UUID RIDER_REACH_MODIFIER_ID =
            UUID.fromString("c4f1a90e-2b77-4d1e-8a63-5f0d9b1c7e42");
    private static final String RIDER_REACH_MODIFIER_NAME = "goety_supreme:mount_reach";

    private MountReachBonus() {
    }

    public static double bonusFor(@Nullable Entity vehicle) {
        if (vehicle instanceof WitherSkeletonHorseServant) {
            return WITHER_SKELETON_HORSE;
        }
        if (vehicle instanceof SkeletonHorseServant || vehicle instanceof SkeletonHorse) {
            return SKELETON_HORSE;
        }
        return 0.0D;
    }

    public static void applyToRider(@Nullable Entity rider, double bonus) {
        if (!(rider instanceof LivingEntity living)) {
            return;
        }
        AttributeInstance reach = living.getAttribute(ForgeMod.ENTITY_REACH.get());
        if (reach == null) {
            return;
        }
        reach.removeModifier(RIDER_REACH_MODIFIER_ID);
        if (bonus > 0.0D) {
            reach.addTransientModifier(new AttributeModifier(RIDER_REACH_MODIFIER_ID,
                    RIDER_REACH_MODIFIER_NAME, bonus, AttributeModifier.Operation.ADDITION));
        }
    }
}
