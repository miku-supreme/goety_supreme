package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.entity.FlamoarServant;
import cn.blockforge.generated.goetysupreme.model.FlamoarServantModel;
import cn.blockforge.generated.goetysupreme.renderer.FlamoarServantRenderer;

public class FlamoarServantRenderer extends MobRenderer<FlamoarServant, FlamoarServantModel> {

    private static final ResourceLocation TEXTURE = glowTexture("flamoar.png");
    private static final ResourceLocation LADY_TEXTURE = glowTexture("flamoar_lady.png");
    private static final ResourceLocation GLOW = glowTexture("flamoar_glow.png");
    private static final ResourceLocation LADY_GLOW = glowTexture("flamoar_lady_glow.png");

    private final FlamoarServantModel normal;
    private final FlamoarServantModel lady;
    private int typeState = -1;

    public FlamoarServantRenderer(EntityRendererProvider.Context context) {
        super(context, new FlamoarServantModel(
                context.bakeLayer(FlamoarServantModel.LAYER_LOCATION)), 0.0F);
        this.normal = this.getModel();
        this.lady = new FlamoarServantModel(
                context.bakeLayer(FlamoarServantModel.LADY_LAYER_LOCATION));
        this.addLayer(new GlowLayer(this, new FlamoarServantModel(
                context.bakeLayer(FlamoarServantModel.LAYER_LOCATION)), false));
        this.addLayer(new GlowLayer(this, new FlamoarServantModel(
                context.bakeLayer(FlamoarServantModel.LADY_LAYER_LOCATION)), true));
        this.addLayer(new LadyBodyLayer(this, new FlamoarServantModel(
                context.bakeLayer(FlamoarServantModel.LADY_LAYER_LOCATION))));
        this.addLayer(new ItemLayer(this, context.getItemInHandRenderer()));
    }

    private static ResourceLocation glowTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath("jerotesvillage",
                "textures/entity/flamoar/" + name);
    }

    @Override
    public void render(FlamoarServant entity, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        int state = entity.isLady() ? 1 : 0;
        if (state != this.typeState) {
            this.model = state == 1 ? this.lady : this.normal;
            this.typeState = state;
        }
        entity.thisTickRenderTime += 1.0F;
        float smooth = 1.0F / (entity.lastTickRenderTime + 1.0F);
        entity.attackAnimProgress = Mth.lerp(smooth, entity.attackAnimProgress, entity.getAttackAnim());
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public void scale(FlamoarServant entity, PoseStack poseStack, float partialTicks) {
        poseStack.scale(1.35F, 1.35F, 1.35F);
        super.scale(entity, poseStack, partialTicks);
    }

    @Override
    public float getFlipDegrees(FlamoarServant entity) {
        return 0.0F;
    }

    @Override
    public int getBlockLightLevel(FlamoarServant entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(FlamoarServant entity) {
        return entity.isLady() ? LADY_TEXTURE : TEXTURE;
    }

    private static class GlowLayer extends RenderLayer<FlamoarServant, FlamoarServantModel> {
        private final FlamoarServantModel model;
        private final boolean lady;

        GlowLayer(RenderLayerParent<FlamoarServant, FlamoarServantModel> parent,
                FlamoarServantModel model, boolean lady) {
            super(parent);
            this.model = model;
            this.lady = lady;
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                FlamoarServant entity, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.isLady() != this.lady || entity.isInvisible()) {
                return;
            }
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(this.lady ? LADY_GLOW : GLOW));
            this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private static class LadyBodyLayer extends RenderLayer<FlamoarServant, FlamoarServantModel> {
        private final FlamoarServantModel model;

        LadyBodyLayer(RenderLayerParent<FlamoarServant, FlamoarServantModel> parent,
                FlamoarServantModel model) {
            super(parent);
            this.model = model;
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                FlamoarServant entity, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (!entity.isLady() || entity.isInvisible()) {
                return;
            }
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(LADY_TEXTURE));
            this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private static class ItemLayer extends RenderLayer<FlamoarServant, FlamoarServantModel> {
        private final ItemInHandRenderer itemInHandRenderer;

        ItemLayer(RenderLayerParent<FlamoarServant, FlamoarServantModel> parent,
                ItemInHandRenderer itemInHandRenderer) {
            super(parent);
            this.itemInHandRenderer = itemInHandRenderer;
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                FlamoarServant entity, float limbSwing, float limbSwingAmount, float partialTicks,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.isInvisible()) {
                return;
            }
            boolean mainRight = entity.getMainArm() == HumanoidArm.RIGHT;
            ItemStack right = mainRight ? entity.getMainHandItem() : entity.getOffhandItem();
            ItemStack left = mainRight ? entity.getOffhandItem() : entity.getMainHandItem();
            if (right.isEmpty() && left.isEmpty()) {
                return;
            }
            poseStack.pushPose();
            renderArmWithItem(entity, right, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                    HumanoidArm.RIGHT, poseStack, buffer, packedLight);
            renderArmWithItem(entity, left, ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                    HumanoidArm.LEFT, poseStack, buffer, packedLight);
            poseStack.popPose();
        }

        private void renderArmWithItem(FlamoarServant entity, ItemStack stack,
                ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack,
                MultiBufferSource buffer, int packedLight) {
            if (stack.isEmpty()) {
                return;
            }
            poseStack.pushPose();
            this.getParentModel().translateToHand(arm, poseStack);
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            if (!entity.isLady()) {
                poseStack.scale(1.25F, 1.25F, 1.25F);
                poseStack.translate(0.0F, -0.125F, -0.2F);
            }
            this.itemInHandRenderer.renderItem(entity, stack, displayContext,
                    arm == HumanoidArm.LEFT, poseStack, buffer, packedLight);
            poseStack.popPose();
        }
    }
}
