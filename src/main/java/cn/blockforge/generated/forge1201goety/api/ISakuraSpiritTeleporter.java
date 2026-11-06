package cn.blockforge.generated.goetysupreme.api;

import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.ai.SakuraSpiritTeleportGoal;
import cn.blockforge.generated.goetysupreme.api.ISakuraSpiritTeleporter;

public interface ISakuraSpiritTeleporter {

    boolean tryTeleport();

    boolean tryTeleportAwayFrom(LivingEntity target, double minDistance);

    boolean tryTeleportNearTo(LivingEntity target, double minDistance, double maxDistance);
}
