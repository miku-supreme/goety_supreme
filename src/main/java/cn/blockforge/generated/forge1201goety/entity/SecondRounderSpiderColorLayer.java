package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSpiderColorLayer;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSpiderServant;

public final class SecondRounderSpiderColorLayer<T extends SecondRounderSpiderServant, M extends EntityModel<T>>
        extends RenderLayer<T, M> {

    private static final ResourceLocation COLOR = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/second_rounder_spider_color.png");
    private static final ResourceLocation COLOR_PUSH = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/second_rounder_spider_color_push.png");

    private final M model;

    public SecondRounderSpiderColorLayer(RenderLayerParent<T, M> parent, M model) {
        super(parent);
        this.model = model;
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int light, T entity, float limbSwing,
            float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) {
            return;
        }
        ResourceLocation texture = entity.getPushTick() > 0 ? COLOR_PUSH : COLOR;
        int color = entity.getColor();
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, texture, pose, buffer, light,
                entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks,
                red, green, blue);
    }
}
