package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.renderer.GeneratedRenderTypes;

@OnlyIn(Dist.CLIENT)
public final class GeneratedRenderTypes {

    private static final RenderStateShard.TransparencyStateShard ADDITIVE =
            new RenderStateShard.TransparencyStateShard("goety_supreme_additive_transparency", () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            }, () -> {
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            });

    private static final RenderStateShard.WriteMaskStateShard COLOR_WRITE =
            new RenderStateShard.WriteMaskStateShard(true, false);

    private static final RenderStateShard.CullStateShard NO_CULL =
            new RenderStateShard.CullStateShard(false);

    private static final RenderStateShard.OverlayStateShard OVERLAY =
            new RenderStateShard.OverlayStateShard(true);

    private static final RenderStateShard.ShaderStateShard EYES_SHADER =
            new RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeEyesShader);

    private GeneratedRenderTypes() {
    }

    public static RenderType wraith(ResourceLocation texture) {
        return RenderType.create("goety_supreme_wraith",
                DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true,
                RenderType.CompositeState.builder()
                        .setShaderState(EYES_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                        .setTransparencyState(ADDITIVE)
                        .setCullState(NO_CULL)
                        .setOverlayState(OVERLAY)
                        .setWriteMaskState(COLOR_WRITE)
                        .createCompositeState(false));
    }
}
