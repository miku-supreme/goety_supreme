package cn.blockforge.generated.goetysupreme.util;

import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.MagmaGolemFriendlyExplosion;
import cn.blockforge.generated.goetysupreme.util.ServantTargetRules;
import cn.blockforge.generated.goetysupreme.network.WitherRoarPacket;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.util.WitherServantFriendlyFire;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class WitherServantFriendlyFire {
    private WitherServantFriendlyFire() {
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        Entity direct = event.getSource().getDirectEntity();
        if (direct instanceof Projectile projectile
                && projectile.getOwner() instanceof WitherServant servant
                && servant.isAlliedTo(event.getEntity())) {
            event.setCanceled(true);
            return;
        }
        if (event.getEntity() instanceof WitherServant mount
                && event.getSource().getEntity() instanceof net.minecraft.world.entity.player.Player puncher
                && puncher.getVehicle() == mount) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onDetonate(ExplosionEvent.Detonate event) {
        WitherServant servant = explosionOwner(event.getExplosion());
        if (servant == null) {
            return;
        }
        List<Entity> affected = event.getAffectedEntities();
        affected.removeIf(entity -> entity instanceof ItemEntity || servant.isAlliedTo(entity)
                || ServantTargetRules.isTechnicalEntity(entity));
    }

    private static WitherServant explosionOwner(Explosion explosion) {
        Entity direct = explosion.getDirectSourceEntity();
        if (direct instanceof WitherServant servant) {
            return servant;
        }
        if (direct instanceof Projectile projectile
                && projectile.getOwner() instanceof WitherServant servant) {
            return servant;
        }
        return null;
    }
}
