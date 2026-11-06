package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;
import cn.blockforge.generated.goetysupreme.event.RingSetBonusEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class RingSetBonusEvents {

    private static final float DROWNED_ABYSS_TRIDENT_BONUS = 2.0F;

    private RingSetBonusEvents() {
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.getSource().getDirectEntity() instanceof ThrownTrident trident) {
            applyDrownedTridentBonus(event, trident);
            return;
        }
        Entity attacker = event.getSource().getEntity();
        if (!(attacker instanceof LivingEntity living)) {
            return;
        }
        float bonus = RingSetBonuses.meleeDamageBonus(living);
        if (bonus > 0.0F) {
            event.setAmount(event.getAmount() + bonus);
        }
    }

    private static void applyDrownedTridentBonus(LivingHurtEvent event, ThrownTrident trident) {
        Entity thrower = event.getSource().getEntity() != null
                ? event.getSource().getEntity()
                : trident.getOwner();
        if (!(thrower instanceof LivingEntity living)
                || !RingSetBonuses.pathIs(living, RingSetBonuses.DROWNED)) {
            return;
        }
        Player owner = RingSetBonuses.abyssScopedOwner(living);
        if (owner == null) {
            return;
        }
        event.setAmount(event.getAmount() + DROWNED_ABYSS_TRIDENT_BONUS);
    }
}
