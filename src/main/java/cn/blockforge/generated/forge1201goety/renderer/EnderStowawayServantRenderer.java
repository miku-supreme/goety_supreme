package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.EnderStowawayServant;
import cn.blockforge.generated.goetysupreme.renderer.EnderStowawayServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;

public final class EnderStowawayServantRenderer
        extends MobRenderer<EnderStowawayServant, EndermanModel<EnderStowawayServant>> {

    private static final ResourceLocation NORMAL = jerotes("ender_stowaway.png");
    private static final ResourceLocation ELITE = jerotes("ender_stowaway_elite.png");

    private final RandomSource random = RandomSource.create();

    public EnderStowawayServantRenderer(EntityRendererProvider.Context context) {
        super(context, new EndermanModel<>(context.bakeLayer(ModelLayers.ENDERMAN)), 0.5F);
        this.addLayer(new EnderEyesLayer(this));
    }

    @Override
    public void render(EnderStowawayServant servant, float yaw, float partialTick, PoseStack poses,
            MultiBufferSource buffers, int light) {
        this.getModel().creepy = servant.isCreepy();
        super.render(servant, yaw, partialTick, poses, buffers, light);
    }

    @Override
    public Vec3 getRenderOffset(EnderStowawayServant servant, float partialTick) {
        if (servant.isCreepy()) {
            double offset = 0.02D * (double) servant.getScale();
            return new Vec3(this.random.nextGaussian() * offset, 0.0D, this.random.nextGaussian() * offset);
        }
        return super.getRenderOffset(servant, partialTick);
    }

    @Override
    public void scale(EnderStowawayServant entity, PoseStack poses, float partialTick) {
        float t = ServantDeathPose.progress(entity, partialTick);
        float shrink = 1.0F - 0.7F * t;
        poses.scale(shrink, shrink, shrink);
        poses.translate(0.0D, -0.30D * t, 0.0D);
        super.scale(entity, poses, partialTick);
    }

    @Override
    public float getFlipDegrees(EnderStowawayServant entity) {
        return 0.0F;
    }

    private static ResourceLocation jerotes(String file) {
        return ResourceLocation.fromNamespaceAndPath("jerotesvillage", "textures/entity/" + file);
    }

    @Override
    public ResourceLocation getTextureLocation(EnderStowawayServant entity) {
        return entity.isElite() ? ELITE : NORMAL;
    }
}
