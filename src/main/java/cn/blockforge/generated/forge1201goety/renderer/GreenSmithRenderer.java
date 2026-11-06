package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.GreenSmithModel;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithRenderer;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithStaffFxLayer;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class GreenSmithRenderer extends MobRenderer<AbstractGreenSmith, GreenSmithModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/green_smith.png");

    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/green_smith_glow.png");

    private static final float BASE_SIZE_SCALE = 1.365F;

    public GreenSmithRenderer(EntityRendererProvider.Context context) {
        super(context, new GreenSmithModel(context.bakeLayer(GreenSmithModel.LAYER_LOCATION)), 0.6F);
        this.addLayer(new GreenSmithGlowLayer(this));
        this.addLayer(new GreenSmithStaffFxLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractGreenSmith entity) {
        return TEXTURE;
    }

    @Override
    public void scale(AbstractGreenSmith entity, PoseStack poseStack, float partialTick) {
        float size = BASE_SIZE_SCALE * entity.getSoulJarSizeScale();
        poseStack.scale(size, size, size);
    }

    @Override
    public float getFlipDegrees(AbstractGreenSmith entity) {
        return 0.0F;
    }

    private static final class GreenSmithGlowLayer
            extends EyesLayer<AbstractGreenSmith, GreenSmithModel> {

        private GreenSmithGlowLayer(RenderLayerParent<AbstractGreenSmith, GreenSmithModel> parent) {
            super(parent);
        }

        @Override
        public RenderType renderType() {
            return RenderType.eyes(GLOW_TEXTURE);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                AbstractGreenSmith entity, float limbSwing, float limbSwingAmount, float partialTick,
                float ageInTicks, float netHeadYaw, float headPitch) {
            if (entity.deathTime < 12) {
                super.render(poseStack, bufferSource, packedLight, entity, limbSwing, limbSwingAmount,
                        partialTick, ageInTicks, netHeadYaw, headPitch);
            }
        }
    }
}
