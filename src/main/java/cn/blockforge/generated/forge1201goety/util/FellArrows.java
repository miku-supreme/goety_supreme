package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import cn.blockforge.generated.goetysupreme.util.FellArrows;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class FellArrows {

    private static final String TAG_BOW = "goety_supreme.fell_bow_arrow";

    private static final String TAG_CROSSBOW = "goety_supreme.fell_crossbow_arrow";

    private static final float BOW_DAMAGE_BONUS = 1.0F;

    private static final int EFFECT_TICKS = 100;

    private static final int EROSION_AMPLIFIER = 1;

    private static final byte CROSSBOW_PIERCE_LEVEL = 2;

    private FellArrows() {
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof AbstractArrow arrow)) {
            return;
        }
        if (!(arrow.getOwner() instanceof LivingEntity shooter)) {
            return;
        }
        if (holds(shooter, GeneratedMod.FELL_BOW)) {
            arrow.addTag(TAG_BOW);
        } else if (holds(shooter, GeneratedMod.FELL_CROSSBOW)) {
            arrow.addTag(TAG_CROSSBOW);
            if (arrow.getPierceLevel() < CROSSBOW_PIERCE_LEVEL) {
                arrow.setPierceLevel(CROSSBOW_PIERCE_LEVEL);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractArrow arrow)) {
            return;
        }
        boolean bow = arrow.getTags().contains(TAG_BOW);
        boolean crossbow = arrow.getTags().contains(TAG_CROSSBOW);
        if (!bow && !crossbow) {
            return;
        }
        if (bow) {
            event.setAmount(event.getAmount() + BOW_DAMAGE_BONUS);
        }
        LivingEntity victim = event.getEntity();
        if (victim.level().isClientSide) {
            return;
        }
        LivingEntity attacker = arrow.getOwner() instanceof LivingEntity owner ? owner : null;
        RegistryObject<MobEffect> effect = bow ? GoetyEffects.SAPPED : GoetyEffects.BUSTED;
        int amplifier = bow ? EROSION_AMPLIFIER : 0;
        MobEffect mobEffect = effect == null ? null : effect.get();
        if (mobEffect != null) {
            victim.addEffect(
                    new MobEffectInstance(mobEffect, EFFECT_TICKS, amplifier, false, true), attacker);
        }
    }

    private static boolean holds(LivingEntity entity, RegistryObject<Item> item) {
        ItemStack main = entity.getMainHandItem();
        if (!main.isEmpty() && main.is(item.get())) {
            return true;
        }
        ItemStack off = entity.getOffhandItem();
        return !off.isEmpty() && off.is(item.get());
    }
}
