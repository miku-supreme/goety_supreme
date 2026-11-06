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
import cn.blockforge.generated.goetysupreme.entity.EndermanServant;
import cn.blockforge.generated.goetysupreme.renderer.EndermanServantRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.RagingStarChestLayer;
import cn.blockforge.generated.goetysupreme.entity.ServantEmissiveGlowLayer;

public final class EndermanServantRenderer
        extends MobRenderer<EndermanServant, EndermanModel<EndermanServant>> {

    private static final ResourceLocation VANILLA_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/enderman/enderman.png");

    private static final int FRAME_TICKS = 2;

    private static final int FRAME_COUNT = 24;
    private static final ResourceLocation[] VOID_FRAMES = buildVoidFrames();

    private static final ResourceLocation VOID_GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/enderman_servant_void_glow.png");

    private static final float ENDERMAN_STAR_HALF_PX = 2.0F;

    private final RandomSource random = RandomSource.create();

    public EndermanServantRenderer(EntityRendererProvider.Context context) {
        super(context, new EndermanModel<>(context.bakeLayer(ModelLayers.ENDERMAN)), 0.5F);
        addLayer(new EnderEyesLayer<>(this));
        addLayer(new ServantEmissiveGlowLayer<>(this, VOID_GLOW_TEXTURE));
        addLayer(new RagingStarChestLayer<>(this, ENDERMAN_STAR_HALF_PX));
    }

    private static ResourceLocation[] buildVoidFrames() {
        ResourceLocation[] frames = new ResourceLocation[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            frames[i] = ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/enderman_servant_void_" + i + ".png");
        }
        return frames;
    }

    @Override
    public void render(EndermanServant servant, float yaw, float partialTick, PoseStack poses,
            MultiBufferSource buffers, int light) {
        this.getModel().creepy = servant.isCreepy();
        super.render(servant, yaw, partialTick, poses, buffers, light);
    }

    @Override
    public Vec3 getRenderOffset(EndermanServant servant, float partialTick) {
        if (servant.isCreepy()) {
            double offset = 0.02D * (double) servant.getScale();
            return new Vec3(this.random.nextGaussian() * offset, 0.0D, this.random.nextGaussian() * offset);
        }
        return super.getRenderOffset(servant, partialTick);
    }

    @Override
    public ResourceLocation getTextureLocation(EndermanServant servant) {
        if (!servant.isRagingStarred()) {
            return VANILLA_TEXTURE;
        }
        int frame = Math.floorMod(servant.tickCount, FRAME_TICKS * FRAME_COUNT) / FRAME_TICKS;
        return VOID_FRAMES[frame];
    }

    @Override
    public void scale(EndermanServant entity, PoseStack poseStack, float partialTick) {
        super.scale(entity, poseStack, partialTick);
        if (entity.isRagingStarred()) {
            float widthScale = EndermanServant.starWidthScale();
            poseStack.scale(widthScale, EndermanServant.starHeightScale(), widthScale);
            this.shadowRadius = 0.5F * widthScale;
        }
    }
}
