package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.GreenSmithDamageGuard;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class GreenSmithDamageGuard {

    private GreenSmithDamageGuard() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void sameChainImmune(LivingAttackEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        Entity attacker = event.getSource().getEntity();
        if (attacker == null || attacker instanceof Player || event.getEntity() instanceof Player) {
            return;
        }
        if (!(attacker instanceof AbstractGreenSmith)
                && !(event.getEntity() instanceof AbstractGreenSmith)) {
            return;
        }
        if (ServantFaction.isSameFaction(attacker, event.getEntity())) {
            event.setCanceled(true);
        }
    }
}
