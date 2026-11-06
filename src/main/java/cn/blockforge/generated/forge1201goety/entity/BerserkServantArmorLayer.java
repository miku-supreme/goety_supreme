package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.render.model.ZombieServantModel;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.ZombieServant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import cn.blockforge.generated.goetysupreme.entity.BerserkServantArmorLayer;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.renderer.GoetyZombieServantRenderer;

public final class BerserkServantArmorLayer<T extends ZombieServant, M extends ZombieServantModel<T>>
        extends RenderLayer<T, M> {

    private static final EquipmentSlot[] ARMOR_SLOTS = {
        EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HEAD
    };

    private static final float TINT_R = 0.46F;
    private static final float TINT_G = 0.42F;
    private static final float TINT_B = 0.55F;

    private final HumanoidArmorModel<T> innerModel;
    private final HumanoidArmorModel<T> outerModel;

    public BerserkServantArmorLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        super(parent);
        this.innerModel = new HumanoidArmorModel<>(modelSet.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR));
        this.outerModel = new HumanoidArmorModel<>(modelSet.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR));
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int light, T entity, float limbSwing,
            float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entity instanceof GoetyZombieServant berserk) || !berserk.hasBerserkTexture()) {
            return;
        }
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (!(stack.getItem() instanceof ArmorItem armorItem) || armorItem.getEquipmentSlot() != slot) {
                continue;
            }
            HumanoidArmorModel<T> model = usesInnerModel(slot) ? this.innerModel : this.outerModel;
            this.getParentModel().copyPropertiesTo(model);
            setPartVisibility(model, slot);
            VertexConsumer consumer = buffer.getBuffer(
                    RenderType.armorCutoutNoCull(armorTexture(entity, stack, slot)));
            model.renderToBuffer(pose, consumer, light, OverlayTexture.NO_OVERLAY,
                    TINT_R, TINT_G, TINT_B, 1.0F);
        }
    }

    private static boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    private static void setPartVisibility(HumanoidArmorModel<?> model, EquipmentSlot slot) {
        model.setAllVisible(false);
        switch (slot) {
            case HEAD -> {
                model.head.visible = true;
                model.hat.visible = true;
            }
            case CHEST -> {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
            }
            case LEGS -> {
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            case FEET -> {
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            default -> {
            }
        }
    }

    private static ResourceLocation armorTexture(ZombieServant entity, ItemStack stack, EquipmentSlot slot) {
        ArmorItem armorItem = (ArmorItem) stack.getItem();
        ArmorMaterial material = armorItem.getMaterial();
        String name = material.getName();
        String namespace = "minecraft";
        int separator = name.indexOf(':');
        if (separator != -1) {
            namespace = name.substring(0, separator);
            name = name.substring(separator + 1);
        }
        String path = String.format("%s:textures/models/armor/%s_layer_%d.png",
                namespace, name, usesInnerModel(slot) ? 2 : 1);
        path = ForgeHooksClient.getArmorTexture(entity, stack, path, slot, null);
        return ResourceLocation.parse(path);
    }
}
