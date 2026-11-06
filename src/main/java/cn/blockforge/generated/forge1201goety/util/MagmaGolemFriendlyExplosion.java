package cn.blockforge.generated.goetysupreme.util;

import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolem;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolemFireball;
import cn.blockforge.generated.goetysupreme.util.MagmaGolemFriendlyExplosion;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class MagmaGolemFriendlyExplosion {

    private MagmaGolemFriendlyExplosion() {
    }

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public static void onDetonate(ExplosionEvent.Detonate event) {
        Explosion explosion = event.getExplosion();
        List<Entity> affected = event.getAffectedEntities();
        if (isMagmaGolemExplosion(explosion)) {
            affected.removeIf(entity -> entity instanceof ItemEntity);
        }
        if (!(explosion.getDirectSourceEntity() instanceof MagmaGolemFireball fireball)) {
            return;
        }
        if (!(fireball.getOwner() instanceof LivingEntity owner)) {
            return;
        }
        affected.removeIf(entity -> isAlly(owner, entity));
    }

    private static boolean isMagmaGolemExplosion(Explosion explosion) {
        if (explosion.getExploder() instanceof MagmaGolem
                || explosion.getDirectSourceEntity() instanceof MagmaGolem
                || explosion.getIndirectSourceEntity() instanceof MagmaGolem) {
            return true;
        }
        return explosion.getDirectSourceEntity() instanceof net.minecraft.world.entity.projectile.Projectile projectile
                && projectile.getOwner() instanceof MagmaGolem;
    }

    private static boolean isAlly(LivingEntity owner, Entity entity) {
        if (entity == owner || entity == null) {
            return entity == owner;
        }
        if (owner.isAlliedTo(entity)) {
            return true;
        }
        return ServantFaction.isSameFaction(owner, entity);
    }
}
