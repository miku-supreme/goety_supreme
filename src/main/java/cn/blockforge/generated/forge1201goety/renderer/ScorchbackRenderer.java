package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackChargeSpinLayer;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackGlowLayer;
import cn.blockforge.generated.goetysupreme.model.ScorchbackModel;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SupremeChargeAuraRenderer;

public class ScorchbackRenderer extends MobRenderer<Scorchback, ScorchbackModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/scorchback/scorchback.png");

    public ScorchbackRenderer(EntityRendererProvider.Context context) {
        super(context, new ScorchbackModel(context.bakeLayer(ScorchbackModel.LAYER)), 1.0F);
        this.addLayer(new ScorchbackGlowLayer(this));
        this.addLayer(new ScorchbackChargeSpinLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Scorchback entity) {
        return TEXTURE;
    }

    @Override
    public void render(Scorchback entity, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        SupremeChargeAuraRenderer.render(entity, entity.supremeChargeAura(), partialTicks, poseStack, buffer);
    }

    @Override
    public void setupRotations(Scorchback entity, com.mojang.blaze3d.vertex.PoseStack poses,
            float ageInTicks, float bodyYaw, float partialTick) {
        super.setupRotations(entity, poses, ageInTicks,
                entity.hasActionFacing() && entity.isAlive() ? entity.getActionYaw() : bodyYaw, partialTick);
    }

    @Override
    public float getFlipDegrees(Scorchback entity) {
        return 0.0F;
    }

    @Override
    public void scale(Scorchback entity, com.mojang.blaze3d.vertex.PoseStack poseStack,
            float partialTick) {
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }
    }
}
