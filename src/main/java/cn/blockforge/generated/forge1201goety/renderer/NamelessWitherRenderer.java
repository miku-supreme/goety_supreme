package cn.blockforge.generated.goetysupreme.renderer;

import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.NamelessWither;
import cn.blockforge.generated.goetysupreme.model.NamelessWitherModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class NamelessWitherRenderer extends MobRenderer<NamelessWither, NamelessWitherModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/nameless_wither.png");

    private static final ResourceLocation GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/nameless_wither_glow.png");

    public NamelessWitherRenderer(EntityRendererProvider.Context context) {
        super(context, new NamelessWitherModel(context.bakeLayer(NamelessWitherModel.LAYER)), 1.0F);
        this.addLayer(new NamelessWitherShellLayer(this, GLOW_TEXTURE));
    }

    @Override
    public void scale(NamelessWither entity, PoseStack poseStack, float partialTick) {
        float scale = 2.0F * entity.getScale();
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(NamelessWither entity) {
        return TEXTURE;
    }

    @Override
    protected RenderType getRenderType(NamelessWither entity, boolean showBody, boolean translucent,
            boolean showOutline) {
        // 模型贴图带半透明（绿豆外壳 cube25 alpha<255），必须用 translucent 渲染才能显示半透明；
        // 身体等 alpha=255 的部分在 translucent 下依然不透明。
        return RenderType.entityTranslucent(this.getTextureLocation(entity));
    }

    @Override
    public int getBlockLightLevel(NamelessWither entity, net.minecraft.core.BlockPos pos) {
        return 15;
    }
}
