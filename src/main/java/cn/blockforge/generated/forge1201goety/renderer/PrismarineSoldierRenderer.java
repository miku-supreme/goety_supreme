package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;
import cn.blockforge.generated.goetysupreme.model.PrismarineSoldierModel;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineSoldierRenderer;

public class PrismarineSoldierRenderer
        extends MobRenderer<PrismarineSoldier, PrismarineSoldierModel> {

    public static final float MODEL_SCALE = 0.762F;

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/prismarine_soldier.png");

    private static final ResourceLocation EYES_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/prismarine_soldier_eyes.png");

    public PrismarineSoldierRenderer(EntityRendererProvider.Context context) {
        super(context,
                new PrismarineSoldierModel(context.bakeLayer(PrismarineSoldierModel.LAYER)),
                0.6F);
        this.addLayer(new PrismarineEyesLayer(this));
        this.addLayer(new PrismarineHeldItemLayer(this, context.getItemInHandRenderer()));
        this.addLayer(new PrismarineArmorLayer(this, context.getModelSet()));
        this.addLayer(new PrismarineEmpoweredLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(PrismarineSoldier entity) {
        return TEXTURE;
    }

    @Override
    public void scale(PrismarineSoldier entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }

    private static final class PrismarineArmorLayer
            extends RenderLayer<PrismarineSoldier, PrismarineSoldierModel> {

        private static final float[] ANCHOR_HEAD = {2.0F / 16.0F, -1.0F / 16.0F, -1.0F / 16.0F};
        private static final float[] ANCHOR_BODY = {0.0F, -23.0F / 16.0F, 0.0F};
        private static final float[] ANCHOR_RIGHT_ARM = {10.0F / 16.0F, -2.0F / 16.0F, 0.0F};
        private static final float[] ANCHOR_LEFT_ARM = {-10.0F / 16.0F, -2.0F / 16.0F, 0.0F};
        private static final float[] ANCHOR_RIGHT_LEG = {4.0F / 16.0F, -25.0F / 16.0F, 0.0F};
        private static final float[] ANCHOR_LEFT_LEG = {-4.0F / 16.0F, -25.0F / 16.0F, 0.0F};

        private static final float ARMOR_SCALE = 2.0F;

        private final HumanoidModel<PrismarineSoldier> inner;
        private final HumanoidModel<PrismarineSoldier> outer;

        private PrismarineArmorLayer(RenderLayerParent<PrismarineSoldier, PrismarineSoldierModel> renderer,
                EntityModelSet modelSet) {
            super(renderer);
            this.inner = new HumanoidModel<>(modelSet.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
            this.outer = new HumanoidModel<>(modelSet.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
        }

        @Override
        public void render(PoseStack pose, MultiBufferSource buffer, int light, PrismarineSoldier entity,
                float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                float netHeadYaw, float headPitch) {
            if (!wearsArmor(entity)) {
                return;
            }
            this.renderPiece(pose, buffer, entity, EquipmentSlot.HEAD, light, this.outer,
                    PrismarineSoldierModel.ARMOR_BONE_HEAD, ANCHOR_HEAD, this.outer.head, this.outer.hat);
            this.renderPiece(pose, buffer, entity, EquipmentSlot.CHEST, light, this.outer,
                    PrismarineSoldierModel.ARMOR_BONE_BODY, ANCHOR_BODY, this.outer.body);
            this.renderPiece(pose, buffer, entity, EquipmentSlot.CHEST, light, this.outer,
                    PrismarineSoldierModel.ARMOR_BONE_RIGHT_ARM, ANCHOR_RIGHT_ARM, this.outer.rightArm);
            this.renderPiece(pose, buffer, entity, EquipmentSlot.CHEST, light, this.outer,
                    PrismarineSoldierModel.ARMOR_BONE_LEFT_ARM, ANCHOR_LEFT_ARM, this.outer.leftArm);
            this.renderPiece(pose, buffer, entity, EquipmentSlot.LEGS, light, this.inner,
                    PrismarineSoldierModel.ARMOR_BONE_BODY, ANCHOR_BODY, this.inner.body);
            this.renderPiece(pose, buffer, entity, EquipmentSlot.LEGS, light, this.inner,
                    PrismarineSoldierModel.ARMOR_BONE_RIGHT_LEG, ANCHOR_RIGHT_LEG, this.inner.rightLeg);
            this.renderPiece(pose, buffer, entity, EquipmentSlot.LEGS, light, this.inner,
                    PrismarineSoldierModel.ARMOR_BONE_LEFT_LEG, ANCHOR_LEFT_LEG, this.inner.leftLeg);
            this.renderPiece(pose, buffer, entity, EquipmentSlot.FEET, light, this.outer,
                    PrismarineSoldierModel.ARMOR_BONE_RIGHT_LEG, ANCHOR_RIGHT_LEG, this.outer.rightLeg);
            this.renderPiece(pose, buffer, entity, EquipmentSlot.FEET, light, this.outer,
                    PrismarineSoldierModel.ARMOR_BONE_LEFT_LEG, ANCHOR_LEFT_LEG, this.outer.leftLeg);
        }

        private static boolean wearsArmor(PrismarineSoldier entity) {
            return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArmorItem
                    || entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem
                    || entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem
                    || entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ArmorItem;
        }

        private void renderPiece(PoseStack pose, MultiBufferSource buffer, PrismarineSoldier entity,
                EquipmentSlot slot, int light, HumanoidModel<PrismarineSoldier> model,
                int bone, float[] anchor, ModelPart... parts) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (!(stack.getItem() instanceof ArmorItem armor) || armor.getEquipmentSlot() != slot) {
                return;
            }
            for (ModelPart part : parts) {
                part.xRot = 0.0F;
                part.yRot = 0.0F;
                part.zRot = 0.0F;
            }
            ResourceLocation texture = armorTexture(entity, stack, slot);
            VertexConsumer consumer = buffer.getBuffer(RenderType.armorCutoutNoCull(texture));
            pose.pushPose();
            this.getParentModel().pushBoneTransform(bone, pose);
            pose.translate(anchor[0], anchor[1], anchor[2]);
            pose.scale(ARMOR_SCALE, ARMOR_SCALE, ARMOR_SCALE);
            for (ModelPart part : parts) {
                part.render(pose, consumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            pose.popPose();
        }

        private static ResourceLocation armorTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
            String name = ((ArmorItem) stack.getItem()).getMaterial().getName();
            String domain = "minecraft";
            int split = name.indexOf(':');
            if (split != -1) {
                domain = name.substring(0, split);
                name = name.substring(split + 1);
            }
            String path = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d.png",
                    domain, name, slot == EquipmentSlot.LEGS ? 2 : 1);
            return ResourceLocation.parse(ForgeHooksClient.getArmorTexture(entity, stack, path, slot, null));
        }
    }

    private static final class PrismarineEyesLayer
            extends EyesLayer<PrismarineSoldier, PrismarineSoldierModel> {

        private static final RenderType EYES_RENDER_TYPE = RenderType.eyes(EYES_TEXTURE);

        private PrismarineEyesLayer(RenderLayerParent<PrismarineSoldier, PrismarineSoldierModel> renderer) {
            super(renderer);
        }

        @Override
        public RenderType renderType() {
            return EYES_RENDER_TYPE;
        }
    }

    private static final class PrismarineEmpoweredLayer
            extends RenderLayer<PrismarineSoldier, PrismarineSoldierModel> {

        private static final ResourceLocation ENERGY_SWIRL =
                ResourceLocation.parse("textures/entity/creeper/creeper_armor.png");

        private static final float SHELL_INFLATE = 1.03F;

        private static final float SCROLL_SPEED = 0.01F;

        private PrismarineEmpoweredLayer(RenderLayerParent<PrismarineSoldier, PrismarineSoldierModel> renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack pose, MultiBufferSource buffer, int light, PrismarineSoldier entity,
                float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                float netHeadYaw, float headPitch) {
            if (!entity.isLightningEmpowered()) {
                return;
            }
            float pulse = PrismarineSoldier.auraPulse(ageInTicks);
            if (pulse <= 0.02F) {
                return;
            }
            float scroll = ageInTicks * SCROLL_SPEED;
            VertexConsumer consumer = buffer.getBuffer(RenderType.energySwirl(ENERGY_SWIRL,
                    scroll % 1.0F, scroll % 1.0F));
            pose.pushPose();
            pose.translate(0.0F, 0.01F, 0.0F);
            pose.scale(SHELL_INFLATE, SHELL_INFLATE, SHELL_INFLATE);
            this.getParentModel().renderToBuffer(pose, consumer, light, OverlayTexture.NO_OVERLAY,
                    pulse, pulse, pulse, 1.0F);
            pose.popPose();
        }
    }

    private static final class PrismarineHeldItemLayer
            extends ItemInHandLayer<PrismarineSoldier, PrismarineSoldierModel> {

        private static final float HAND_X = 2.0F / 16.0F;

        private static final float HAND_Z = -1.0F / 16.0F;

        private static final float ITEM_SCALE = 2.2F;

        private final ItemInHandRenderer itemInHandRenderer;

        private PrismarineHeldItemLayer(RenderLayerParent<PrismarineSoldier, PrismarineSoldierModel> renderer,
                ItemInHandRenderer itemInHandRenderer) {
            super(renderer, itemInHandRenderer);
            this.itemInHandRenderer = itemInHandRenderer;
        }

        @Override
        public void renderArmWithItem(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext,
                HumanoidArm arm, PoseStack pose, MultiBufferSource buffer, int light) {
            if (stack.isEmpty()) {
                return;
            }
            boolean left = arm == HumanoidArm.LEFT;
            pose.pushPose();
            this.getParentModel().translateToHand(arm, pose);
            pose.mulPose(Axis.XP.rotationDegrees(-90.0F));
            pose.mulPose(Axis.YP.rotationDegrees(180.0F));
            pose.translate(left ? -HAND_X : HAND_X, HAND_Z, 0.0F);
            pose.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
            this.itemInHandRenderer.renderItem(entity, stack, displayContext, left, pose, buffer, light);
            pose.popPose();
        }
    }
}
