package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.renderer.WitherServantGlowLayer;
import cn.blockforge.generated.goetysupreme.model.WitherServantModel;

public final class WitherServantGlowLayer extends RenderLayer<WitherServant, WitherServantModel> {

    private static final Map<ResourceLocation, RenderType> GLOW_TYPES = new ConcurrentHashMap<>();

    private final ResourceLocation maskTexture;

    public WitherServantGlowLayer(RenderLayerParent<WitherServant, WitherServantModel> parent,
            ResourceLocation maskTexture) {
        super(parent);
        this.maskTexture = maskTexture;
    }

    public static WitherServantGlowLayer emissive(
            RenderLayerParent<WitherServant, WitherServantModel> parent, ResourceLocation maskTexture) {
        return new WitherServantGlowLayer(parent, maskTexture);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            WitherServant servant, float limbSwing, float limbSwingAmount, float partialTick,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (servant.isInvisible() || !servant.isStarEmbedded()) {
            return;
        }
        this.getParentModel().renderToBuffer(poseStack,
                bufferSource.getBuffer(trueColorGlowType(this.maskTexture)),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static RenderType trueColorGlowType(ResourceLocation texture) {
        return GLOW_TYPES.computeIfAbsent(texture, TrueColorGlowStates::build);
    }

    private static final class TrueColorGlowStates extends RenderStateShard {

        private TrueColorGlowStates() {
            super("true_color_glow_shards", () -> {
            }, () -> {
            });
        }

        static RenderType build(ResourceLocation texture) {
            return RenderType.create(
                    "wither_annihilation_true_color_glow",
                    DefaultVertexFormat.NEW_ENTITY,
                    VertexFormat.Mode.QUADS,
                    256,
                    false,
                    true,
                    RenderType.CompositeState.builder()
                            .setShaderState(RENDERTYPE_EYES_SHADER)
                            .setTextureState(new TextureStateShard(texture, false, false))
                            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                            .setWriteMaskState(COLOR_WRITE)
                            .createCompositeState(false));
        }
    }
}