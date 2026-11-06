package cn.blockforge.generated.goetysupreme.renderer;

import java.util.Set;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackGlowLayer;
import cn.blockforge.generated.goetysupreme.model.ScorchbackModel;

@OnlyIn(Dist.CLIENT)
public class ScorchbackGlowLayer extends RenderLayer<Scorchback, ScorchbackModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
            "textures/entity/scorchback/scorchback.png");

    private static final Set<String> GLOW_PARTS = Set.of(
            "l_eye_iris", "r_eye_iris", "l_eye_pupil", "r_eye_pupil",
            "front_head_spike", "back_head_spike",
            "front_l_head_spike", "front_r_head_spike",
            "back_l_head_spike", "back_r_head_spike",
            "front_body_spike", "front_l_body_spike", "front_r_body_spike",
            "back_l_body_spike", "back_r_body_spike",
            "l_tail1_spike", "r_tail1_spike", "l_tail2_spike", "r_tail2_spike");

    public ScorchbackGlowLayer(RenderLayerParent<Scorchback, ScorchbackModel> parent) {
        super(parent);
    }

    @Override
    public void render(com.mojang.blaze3d.vertex.PoseStack poseStack, MultiBufferSource buffer,
            int packedLight, Scorchback entity, float limbSwing, float limbSwingAmount,
            float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        this.getParentModel().renderOnly(poseStack, buffer.getBuffer(RenderType.entityTranslucentEmissive(TEXTURE)),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F,
                GLOW_PARTS);
    }
}
