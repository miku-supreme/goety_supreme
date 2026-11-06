package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.AbstractWitherWarlordRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.entity.WarlordSaberTrailLayer;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;

public final class WarlordSaberTrailLayer<T extends AbstractWitherWarlord,
        M extends WitherWarlordModel<T>> extends RenderLayer<T, M> {

    public WarlordSaberTrailLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {
        ClientWarlordSaberTrail.render(entity, this.getParentModel(), poseStack, buffer, partialTick);
    }
}
