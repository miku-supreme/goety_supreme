package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import cn.blockforge.generated.goetysupreme.renderer.ClientIfritClawTrail;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.renderer.IfritClawTrailLayer;
import cn.blockforge.generated.goetysupreme.model.IfritModel;
import cn.blockforge.generated.goetysupreme.renderer.IfritRenderer;

public final class IfritClawTrailLayer extends RenderLayer<Ifrit, IfritModel> {

    public IfritClawTrailLayer(RenderLayerParent<Ifrit, IfritModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Ifrit entity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {
        ClientIfritClawTrail.render(entity, this.getParentModel(), poseStack, buffer, partialTick);
    }
}
