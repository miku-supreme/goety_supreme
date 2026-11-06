package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.mutantmonsters.world.entity.mutant.MutantEnderman;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.renderer.MutantEndermanHandGlowLayer;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServantRenderState;

public final class MutantEndermanHandGlowLayer extends RenderLayer<MutantEnderman, EntityModel<MutantEnderman>> {
    private static final ResourceLocation GLOW =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "textures/entity/mutant_enderman_void_eye_glow.png");

    public MutantEndermanHandGlowLayer(RenderLayerParent<MutantEnderman, EntityModel<MutantEnderman>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poses, MultiBufferSource buffers, int light, MutantEnderman entity,
            float swing, float amount, float partialTick, float age, float headYaw, float headPitch) {
        if (!(entity instanceof MutantEndermanServantRenderState state)
                || !state.hasVoidEyeUpgrade() || entity.isInvisible()) {
            return;
        }
        getParentModel().renderToBuffer(poses, buffers.getBuffer(RenderType.eyes(GLOW)), light,
                OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
