package cn.blockforge.generated.goetysupreme.renderer;

import cn.blockforge.generated.goetysupreme.entity.NamelessWither;
import cn.blockforge.generated.goetysupreme.model.NamelessWitherModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/**
 * 无名魔杖球壳发光层——照觉醒无名者 NamelessOneEmissiveLayer 的形式：
 * 球壳是模型部件（跟随模型骨骼运动），用独立的发光贴图（nameless_wither_glow.png，
 * 上面烘焙了无名魔杖 orb 图案）在发光层渲染整模型，只有球壳 UV 区域有图案，
 * 因此只有球壳发光。前摇时 setShellVisible(true) 显示。
 */
public final class NamelessWitherShellLayer extends RenderLayer<NamelessWither, NamelessWitherModel> {

    private final ResourceLocation texture;

    public NamelessWitherShellLayer(RenderLayerParent<NamelessWither, NamelessWitherModel> parent,
            ResourceLocation texture) {
        super(parent);
        this.texture = texture;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            NamelessWither entity, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        NamelessWitherModel model = this.getParentModel();
        model.setShellVisible(entity.isShellActive());
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.eyes(this.texture));
        model.renderToBuffer(poseStack, consumer, 0xF00000, OverlayTexture.NO_OVERLAY,
                1.5F, 1.5F, 1.5F, 0.6F);
    }
}
