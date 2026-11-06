package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldierGlowLayer;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierModel;

public class SerponColonialSoldierGlowLayer<T extends AbstractSerpon>
        extends EyesLayer<T, SerponColonialSoldierModel<T>> {

    private final ResourceLocation texture;

    public SerponColonialSoldierGlowLayer(
            RenderLayerParent<T, SerponColonialSoldierModel<T>> parent, ResourceLocation texture) {
        super(parent);
        this.texture = texture;
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(this.texture);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int packedLight,
            T entity, float limbSwing, float limbSwingAmount,
            float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isPrepare()) {
            return;
        }
        super.render(pose, buffer, packedLight, entity, limbSwing, limbSwingAmount, partialTicks,
                ageInTicks, netHeadYaw, headPitch);
    }
}
