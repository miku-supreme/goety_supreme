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
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServantRenderState;
import cn.blockforge.generated.goetysupreme.renderer.MutantEndermanVoidEyeLayer;

public final class MutantEndermanVoidEyeLayer extends RenderLayer<MutantEnderman, EntityModel<MutantEnderman>> {
    private static final ResourceLocation GLINT =
            ResourceLocation.fromNamespaceAndPath("goety", "textures/entity/enderling/endersent_glint.png");

    public MutantEndermanVoidEyeLayer(RenderLayerParent<MutantEnderman, EntityModel<MutantEnderman>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poses, MultiBufferSource buffers, int light, MutantEnderman entity,
            float swing, float amount, float partialTick, float age, float headYaw, float headPitch) {
        if (!(entity instanceof MutantEndermanServantRenderState state)
                || !state.hasVoidEyeUpgrade() || entity.isInvisible()) return;
        float time = entity.tickCount + partialTick;
        getParentModel().renderToBuffer(poses, buffers.getBuffer(RenderType.energySwirl(GLINT,
                Mth.cos(time * 0.02F) * 3.0F % 1.0F, time * 0.01F % 1.0F)), light,
                OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
    }
}
