package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.common.entities.ally.golem.RedstoneCube;
import com.Polarice3.Goety.common.entities.projectiles.ScatterMine;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.RagingStarCubeMineHandler;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import cn.blockforge.generated.goetysupreme.mixin.GoetyRedstoneMonstrosityRagingMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyScatterBombSprayMineMixin;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class RagingStarCubeMineHandler {

    private RagingStarCubeMineHandler() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSummonedCubeDeath(LivingDeathEvent event) {
        if (event.isCanceled() || !(event.getEntity() instanceof RedstoneCube cube)) {
            return;
        }
        if (cube.level().isClientSide() || !(cube.level() instanceof ServerLevel level)) {
            return;
        }
        if (!cube.getPersistentData().getBoolean(RagingStarSupport.RAGING_CUBE_MINE)) {
            return;
        }
        ScatterMine mine = new ScatterMine(level, cube, cube.position());
        level.addFreshEntity(mine);
    }
}
