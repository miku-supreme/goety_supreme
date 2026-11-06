package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.common.entities.projectiles.CorruptedBeam;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.config.CorruptedFocusLaserConfig;
import cn.blockforge.generated.goetysupreme.event.CorruptedFocusLaserEvents;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;
import cn.blockforge.generated.goetysupreme.entity.NamelessLaserBeam;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class CorruptedFocusLaserEvents {

    private CorruptedFocusLaserEvents() {
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!CorruptedFocusLaserConfig.enabled()) {
            return;
        }
        Entity entity = event.getEntity();
        if (!(entity instanceof CorruptedBeam beam) || entity instanceof GreenSmithLaserBeam
                || entity instanceof NamelessLaserBeam) {
            return;
        }
        if (!beam.itemBase) {
            return;
        }
        Level level = event.getLevel();
        GreenSmithLaserBeam laser = new GreenSmithLaserBeam(ModEntityTypes.GREEN_SMITH_LASER.get(), level);
        laser.setPos(beam.getX(), beam.getY(), beam.getZ());
        laser.setYRot(beam.getYRot());
        laser.setXRot(beam.getXRot());
        laser.setOwner(beam.getOwner());
        laser.setExtraDamage(beam.extraDamage);
        laser.setItemBase(beam.itemBase);
        event.setCanceled(true);
        level.addFreshEntity(laser);
    }
}
