package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.config.WitherServantConfig;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.event.WitherServantGriefingEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class WitherServantGriefingEvents {

    @SubscribeEvent
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        if (WitherServantConfig.blockDestructionAllowed()) {
            return;
        }
        Explosion explosion = event.getExplosion();
        if (!causedByServant(explosion)) {
            return;
        }
        event.getAffectedBlocks().clear();
    }

    private static boolean causedByServant(Explosion explosion) {
        if (explosion.getExploder() instanceof WitherServant) {
            return true;
        }
        LivingEntity indirect = explosion.getIndirectSourceEntity();
        return indirect instanceof WitherServant;
    }

    private WitherServantGriefingEvents() {
    }
}
