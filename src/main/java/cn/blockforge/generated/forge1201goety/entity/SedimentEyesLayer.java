package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentEyesLayer;
import cn.blockforge.generated.goetysupreme.model.SedimentServantModel;

public class SedimentEyesLayer<T extends AbstractSedimentServant, M extends SedimentServantModel<T>>
        extends EyesLayer<T, M> {

    private final ResourceLocation texture;

    public SedimentEyesLayer(RenderLayerParent<T, M> parent, ResourceLocation texture) {
        super(parent);
        this.texture = texture;
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(this.texture);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (!entity.isAggressive() && entity.getTarget() == null) {
            return;
        }
        super.render(pose, buffer, packedLight, entity, limbSwing, limbSwingAmount, partialTicks,
                ageInTicks, netHeadYaw, headPitch);
    }
}
