package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.mutantmonsters.client.init.ClientModRegistry;
import fuzs.mutantmonsters.client.renderer.entity.layers.EndersoulLayer;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.EndersoulCloneServant;
import cn.blockforge.generated.goetysupreme.renderer.EndersoulCloneServantRenderer;

public final class EndersoulCloneServantRenderer
        extends MobRenderer<EndersoulCloneServant, EndermanModel<EndersoulCloneServant>> {
    public EndersoulCloneServantRenderer(EntityRendererProvider.Context context) {
        super(context, new EndermanModel<>(context.bakeLayer(ClientModRegistry.ENDERSOUL_CLONE)), 0.5F);
        shadowStrength = 0.5F;
        addLayer(new EndersoulLayer<>(this));
    }

    @Override
    public void render(EndersoulCloneServant entity, float yaw, float partialTick, PoseStack poses,
            MultiBufferSource buffers, int light) {
        model.creepy = entity.isAggressive();
        super.render(entity, yaw, partialTick, poses, buffers, light);
    }

    @Override
    public RenderType getRenderType(EndersoulCloneServant entity, boolean visible, boolean translucent,
            boolean glowing) {
        return null;
    }

    @Override
    public float getFlipDegrees(EndersoulCloneServant entity) {
        return 0.0F;
    }

    @Override
    public Vec3 getRenderOffset(EndersoulCloneServant entity, float partialTick) {
        return entity.isAggressive() ? new Vec3(entity.getRandom().nextGaussian() * 0.02D, 0.0D,
                entity.getRandom().nextGaussian() * 0.02D) : Vec3.ZERO;
    }

    @Override
    public ResourceLocation getTextureLocation(EndersoulCloneServant entity) {
        return EndersoulLayer.TEXTURE;
    }
}
