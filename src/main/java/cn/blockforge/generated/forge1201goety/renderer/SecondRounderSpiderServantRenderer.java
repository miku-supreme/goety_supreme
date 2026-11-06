package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSpiderColorLayer;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSpiderServant;
import cn.blockforge.generated.goetysupreme.renderer.SecondRounderSpiderServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;

public final class SecondRounderSpiderServantRenderer
        extends MobRenderer<SecondRounderSpiderServant, SpiderModel<SecondRounderSpiderServant>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/second_rounder_spider.png");

    private static final ResourceLocation PUSH_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/second_rounder_spider_push.png");

    public SecondRounderSpiderServantRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiderModel<>(context.bakeLayer(ModelLayers.SPIDER)), 0.8F);
        this.shadowRadius *= 1.2F;
        this.addLayer(new SecondRounderSpiderColorLayer<>(this,
                new SpiderModel<>(context.bakeLayer(ModelLayers.SPIDER))));
    }

    @Override
    public void scale(SecondRounderSpiderServant entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
        float t = ServantDeathPose.progress(entity, partialTick);
        if (t > 0.0F) {
            poseStack.scale(1.0F - 0.20F * t, 1.0F - 0.55F * t, 1.0F - 0.20F * t);
            poseStack.translate(0.0D, -0.55D * t, 0.0D);
        }
    }

    @Override
    public float getFlipDegrees(SecondRounderSpiderServant entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(SecondRounderSpiderServant entity) {
        return entity.getPushTick() > 0 ? PUSH_TEXTURE : TEXTURE;
    }
}
