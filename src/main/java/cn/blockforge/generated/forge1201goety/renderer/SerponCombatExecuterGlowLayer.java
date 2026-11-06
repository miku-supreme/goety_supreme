package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.renderer.SerponCombatExecuterGlowLayer;
import cn.blockforge.generated.goetysupreme.model.SerponCombatExecuterModel;

public class SerponCombatExecuterGlowLayer
        extends EyesLayer<SerponCombatExecuter, SerponCombatExecuterModel> {

    private final ResourceLocation texture;

    public SerponCombatExecuterGlowLayer(
            RenderLayerParent<SerponCombatExecuter, SerponCombatExecuterModel> parent,
            ResourceLocation texture) {
        super(parent);
        this.texture = texture;
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(this.texture);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int packedLight,
            SerponCombatExecuter entity, float limbSwing, float limbSwingAmount,
            float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible() || !entity.isAlive()) {
            return;
        }
        super.render(pose, buffer, packedLight, entity, limbSwing, limbSwingAmount, partialTicks,
                ageInTicks, netHeadYaw, headPitch);
    }
}
