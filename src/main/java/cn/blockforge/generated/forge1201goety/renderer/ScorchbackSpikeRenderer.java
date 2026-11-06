package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.ModModelLayer;
import com.Polarice3.Goety.client.render.model.ViciousToothModel;
import com.Polarice3.Goety.common.entities.projectiles.ViciousTooth;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.entity.ScorchbackSpike;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackSpikeRenderer;

@OnlyIn(Dist.CLIENT)
public class ScorchbackSpikeRenderer extends EntityRenderer<ScorchbackSpike> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("goety",
            "textures/entity/projectiles/vicious_tooth.png");

    private static final float SCALE = 0.28F;

    private final ViciousToothModel<ViciousTooth> model;

    public ScorchbackSpikeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ViciousToothModel<>(context.bakeLayer(ModModelLayer.VICIOUS_TOOTH));
    }

    @Override
    public void render(ScorchbackSpike entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - entity.getYRot()));
        poseStack.scale(-SCALE, -SCALE, SCALE);
        poseStack.translate(0.0D, -2.1D, 0.0D);
        this.model.renderToBuffer(poseStack, buffer.getBuffer(net.minecraft.client.renderer.RenderType.entityCutout(TEXTURE)),
                packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.9F);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ScorchbackSpike entity) {
        return TEXTURE;
    }
}
