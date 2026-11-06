package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import java.util.Set;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.ImmutableRingEvents;
import cn.blockforge.generated.goetysupreme.item.UniversalSoulJarItem;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyOminousSunkenNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.SomeIllagerServantsSunkenNecromancerFocusMixin;

public class UniversalSoulJarItem extends Item {

    private static final Set<ResourceLocation> ALLOWED_NECROMANCERS = Set.of(
            ResourceLocation.fromNamespaceAndPath("goety", "necromancer"),
            ResourceLocation.fromNamespaceAndPath("goety", "necromancer_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "mossy_necromancer"),
            ResourceLocation.fromNamespaceAndPath("goety", "mossy_necromancer_servant"),
            ResourceLocation.fromNamespaceAndPath("goety", "cairn_necromancer"),
            ResourceLocation.fromNamespaceAndPath("goety", "cairn_necromancer_servant"),
            ResourceLocation.fromNamespaceAndPath("goety_cataclysm", "draugr_necromancer"),
            ResourceLocation.fromNamespaceAndPath("goety_cataclysm", "draugr_necromancer_servant"),
            ResourceLocation.fromNamespaceAndPath("goetyominous", "sunken_necromancer"),
            ResourceLocation.fromNamespaceAndPath("goetyominous", "sunken_necromancer_servant"),
            ResourceLocation.fromNamespaceAndPath("goetyominus", "sunken_necromancer"),
            ResourceLocation.fromNamespaceAndPath("goetyominus", "sunken_necromancer_servant"),
            ResourceLocation.fromNamespaceAndPath("someillagerservants", "sunken_necromancer"),
            ResourceLocation.fromNamespaceAndPath("someillagerservants", "sunken_necromancer_servant"));

    public UniversalSoulJarItem() {
        super(new Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
    }

    public static boolean tryFeed(AbstractNecromancer necromancer, Player player, ItemStack stack,
            InteractionHand hand) {
        if (necromancer == null || player == null || stack == null || stack.isEmpty()) {
            return false;
        }
        if (!stack.is(GeneratedMod.UNIVERSAL_SOUL_JAR.get())) {
            return false;
        }
        if (necromancer.getTrueOwner() != player) {
            return false;
        }
        if (!isAllowedNecromancer(necromancer)) {
            return false;
        }
        if (!necromancer.level().isClientSide) {
            if (necromancer.getNecroLevel() < AbstractNecromancer.MAX_LEVEL) {
                necromancer.setNecroLevel(necromancer.getNecroLevel() + 1);
            }
            necromancer.setHealth(necromancer.getMaxHealth());
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (necromancer.level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.SOUL,
                        necromancer.getX(), necromancer.getY(0.8D), necromancer.getZ(),
                        24, 0.4D, 0.7D, 0.4D, 0.02D);
            }
            necromancer.playLaughSound();
            player.swing(hand);
        }
        return true;
    }

    public static boolean isAllowedNecromancer(LivingEntity entity) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (key != null && ALLOWED_NECROMANCERS.contains(key)) {
            return true;
        }
        String name = entity.getClass().getName();
        return name.contains("DraugrNecromancer") || name.contains("SunkenNecromancer");
    }
}
