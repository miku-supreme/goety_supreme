package cn.blockforge.generated.goetysupreme.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import cn.blockforge.generated.goetysupreme.util.BoneArmorRig;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieServantArmorLayer;
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;

public final class BoneArmorRig {

    public record Box(float x, float y, float z, float w, float h, float d) {
    }

    private record Piece(ModelPart part, ModelPart[] chain,
            float x, float y, float z,
            float sx, float sy, float sz,
            float ox, float oy, float oz,
            ModelPart[] hidden) {
    }

    private final List<Piece> pieces = new ArrayList<>();

    public void attach(ModelPart part, Box armor, Box target, ModelPart... chain) {
        add(part, armor, target, null, chain);
    }

    public void attachHiding(ModelPart part, Box armor, Box target, ModelPart[] hidden, ModelPart... chain) {
        add(part, armor, target, hidden, chain);
    }

    private void add(ModelPart part, Box armor, Box target, ModelPart[] hidden, ModelPart... chain) {
        for (ModelPart bone : chain) {
            if (bone == null || bone == SafeModelParts.dummy()) {
                return;
            }
        }
        part.loadPose(PartPose.ZERO);
        float sx = target.w() / armor.w();
        float sy = target.h() / armor.h();
        float sz = target.d() / armor.d();
        this.pieces.add(new Piece(part, chain,
                target.x(), target.y(), target.z(),
                sx, sy, sz,
                armor.x(), armor.y(), armor.z(),
                hidden));
    }

    public void render(PoseStack pose, VertexConsumer consumer, int light, float red, float green, float blue) {
        for (Piece piece : this.pieces) {
            ModelPart[] hidden = piece.hidden();
            boolean[] previous = hidden == null ? null : new boolean[hidden.length];
            if (hidden != null) {
                for (int i = 0; i < hidden.length; i++) {
                    if (hidden[i] != null) {
                        previous[i] = hidden[i].visible;
                        hidden[i].visible = false;
                    }
                }
            }
            pose.pushPose();
            for (ModelPart bone : piece.chain()) {
                bone.translateAndRotate(pose);
            }
            pose.translate(piece.x() / 16.0F, piece.y() / 16.0F, piece.z() / 16.0F);
            pose.scale(piece.sx(), piece.sy(), piece.sz());
            pose.translate(-piece.ox() / 16.0F, -piece.oy() / 16.0F, -piece.oz() / 16.0F);
            piece.part().render(pose, consumer, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
            pose.popPose();
            if (hidden != null) {
                for (int i = 0; i < hidden.length; i++) {
                    if (hidden[i] != null) {
                        hidden[i].visible = previous[i];
                    }
                }
            }
        }
    }

    public static Box fitVisibleBand(float visibleFrom, float visibleTo,
            float yTop, float yBottom, float width, float depth) {
        float height = (yBottom - yTop) / (visibleTo - visibleFrom);
        float centerY = yTop + height * (0.5F - visibleFrom);
        return new Box(0.0F, centerY, 0.0F, width, height, depth);
    }

    public static final float LEGGINGS_LEG_FROM = 0.0F;
    public static final float LEGGINGS_LEG_TO = 9.0F / 12.0F;

    public static final float LEGGINGS_BODY_FROM = 7.0F / 12.0F;
    public static final float LEGGINGS_BODY_TO = 1.0F;

    public static final float BOOT_FROM = 0.5F;
    public static final float BOOT_TO = 1.0F;

    public static boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    public static String armorTexture(Entity entity, ItemStack stack, EquipmentSlot slot, String type) {
        ArmorItem armorItem = (ArmorItem) stack.getItem();
        ArmorMaterial material = armorItem.getMaterial();
        String name = material.getName();
        String namespace = "minecraft";
        int separator = name.indexOf(':');
        if (separator != -1) {
            namespace = name.substring(0, separator);
            name = name.substring(separator + 1);
        }
        String path = String.format("%s:textures/models/armor/%s_layer_%d%s.png",
                namespace, name, usesInnerModel(slot) ? 2 : 1, type == null ? "" : "_" + type);
        return ForgeHooksClient.getArmorTexture(entity, stack, path, slot, type);
    }

    public static float[] dyeColor(ItemStack stack) {
        if (stack.getItem() instanceof DyeableLeatherItem dyeable) {
            int color = dyeable.getColor(stack);
            return new float[] {
                (color >> 16 & 0xFF) / 255.0F,
                (color >> 8 & 0xFF) / 255.0F,
                (color & 0xFF) / 255.0F
            };
        }
        return null;
    }
}
