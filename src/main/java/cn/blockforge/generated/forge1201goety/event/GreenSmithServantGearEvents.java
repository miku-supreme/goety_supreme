package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.api.entities.IOwned;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithServant;
import cn.blockforge.generated.goetysupreme.event.GreenSmithServantGearEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class GreenSmithServantGearEvents {

    private static final TagKey<EntityType<?>> GOETY_SKELETON_SERVANTS = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("goety", "skeleton_servants"));

    private static final TagKey<EntityType<?>> GOETY_ZOMBIE_SERVANTS = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath("goety", "zombie_servants"));

    private static final float SKELETON_IRON_SWORD_CHANCE = 0.10F;

    private static final float ZOMBIE_IRON_SWORD_CHANCE = 0.15F;

    private static final String ROLLED_TAG = "GoetySupremeGreenSmithSword";

    private GreenSmithServantGearEvents() {
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide() || event.loadedFromDisk()) {
            return;
        }
        if (!(event.getEntity() instanceof Mob servant) || !(servant instanceof IOwned)) {
            return;
        }
        if (servant.getPersistentData().getBoolean(ROLLED_TAG)) {
            return;
        }
        boolean skeleton = isSkeletonServant(servant);
        boolean zombie = !skeleton && isZombieServant(servant);
        if (!skeleton && !zombie) {
            return;
        }
        ItemStack mainhand = servant.getMainHandItem();
        if (skeleton && !(mainhand.getItem() instanceof BowItem)
                && !(mainhand.getItem() instanceof CrossbowItem)) {
            return;
        }
        if (zombie && !mainhand.isEmpty()) {
            return;
        }
        if (!summonedByGreenSmith(servant)) {
            return;
        }
        servant.getPersistentData().putBoolean(ROLLED_TAG, true);
        float chance = skeleton ? SKELETON_IRON_SWORD_CHANCE : ZOMBIE_IRON_SWORD_CHANCE;
        if (servant.getRandom().nextFloat() >= chance) {
            return;
        }
        servant.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        servant.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    private static boolean isSkeletonServant(LivingEntity entity) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (key == null || key.getPath().contains("necromancer")) {
            return false;
        }
        return entity.getType().is(GOETY_SKELETON_SERVANTS)
                || key.getPath().contains("skeleton")
                || key.getPath().equals("stray_servant")
                || key.getPath().equals("rattled_servant");
    }

    private static boolean isZombieServant(LivingEntity entity) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (key == null || key.getPath().contains("necromancer") || key.getPath().contains("ravager")) {
            return false;
        }
        return entity.getType().is(GOETY_ZOMBIE_SERVANTS)
                || key.getPath().contains("zombie");
    }

    private static boolean summonedByGreenSmith(LivingEntity servant) {
        LivingEntity owner = ((IOwned) servant).getTrueOwner();
        int guard = 0;
        while (owner != null && guard++ < 16) {
            if (owner instanceof AbstractGreenSmith) {
                return true;
            }
            if (!(owner instanceof IOwned owned)) {
                return false;
            }
            LivingEntity next = owned.getTrueOwner();
            if (next == owner) {
                return false;
            }
            owner = next;
        }
        return false;
    }
}
