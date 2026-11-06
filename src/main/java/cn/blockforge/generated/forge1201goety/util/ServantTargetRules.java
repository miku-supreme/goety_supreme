package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.ally.IServant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ServantTargetRules;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class ServantTargetRules {
    private static final ResourceLocation DAMNED = ResourceLocation.fromNamespaceAndPath("goety", "damned");

    private ServantTargetRules() { }

    public static boolean isDamned(LivingEntity target) {
        return target != null && DAMNED.equals(BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()));
    }

    public static boolean isTechnicalEntity(Entity target) {
        return target instanceof Projectile || target instanceof AreaEffectCloud
                || target instanceof ArmorStand || (target != null && !target.isAttackable())
                || (target instanceof LivingEntity living && isDamned(living));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTarget(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof IServant && isDamned(event.getNewTarget())) {
            event.setNewTarget(null);
        }
    }
}
