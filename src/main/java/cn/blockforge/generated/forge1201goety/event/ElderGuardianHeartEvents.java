package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.event.ElderGuardianHeartEvents;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.WitherStarEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class ElderGuardianHeartEvents {

    private static final float BASE_CHANCE = 0.25F;

    private static final float CHANCE_PER_LOOTING = 0.025F;

    private ElderGuardianHeartEvents() {
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof ElderGuardian)) {
            return;
        }
        float chance = BASE_CHANCE + CHANCE_PER_LOOTING * Math.max(0, event.getLootingLevel());
        if (event.getEntity().getRandom().nextFloat() < chance) {
            event.getEntity().spawnAtLocation(new ItemStack(Items.HEART_OF_THE_SEA));
        }
    }
}
