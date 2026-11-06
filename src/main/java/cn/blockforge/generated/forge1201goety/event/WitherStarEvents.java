package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.event.WitherStarEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class WitherStarEvents {
    private WitherStarEvents() {
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof WitherBoss) || event.getEntity() instanceof WitherServant) {
            return;
        }
        float chance = 0.01F + 0.005F * Math.max(0, event.getLootingLevel());
        if (event.getEntity().getRandom().nextFloat() < chance) {
            event.getEntity().spawnAtLocation(new ItemStack(GeneratedMod.EVIL_STAR.get()));
        }
    }
}
