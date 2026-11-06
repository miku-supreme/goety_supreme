package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.mutantmonsters.client.renderer.entity.MutantEndermanRenderer;
import fuzs.mutantmonsters.world.entity.mutant.MutantEnderman;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.renderer.MutantEndermanHandGlowLayer;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantEndermanServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServantRenderState;
import cn.blockforge.generated.goetysupreme.renderer.MutantEndermanVoidEyeLayer;

public final class MutantEndermanServantRenderer extends EntityRenderer<MutantEndermanServant> {
    private static final ResourceLocation VOID_EYE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "textures/entity/mutant_enderman_void_eye.png");
    private final MutantEndermanRenderer delegate;
    private MutantEndermanServantRenderState state;

    public MutantEndermanServantRenderer(EntityRendererProvider.Context context) {
        super(context);
        shadowRadius = 0.8F;
        delegate = new MutantEndermanRenderer(context) {
            {
                addLayer(new MutantEndermanVoidEyeLayer(this));
                addLayer(new MutantEndermanHandGlowLayer(this));
            }

            @Override
            public ResourceLocation getTextureLocation(MutantEnderman entity) {
                if (entity instanceof MutantEndermanServantRenderState renderState
                        && renderState.hasVoidEyeTexture()) {
                    return VOID_EYE_TEXTURE;
                }
                return super.getTextureLocation(entity);
            }

            @Override
            public boolean shouldShowName(MutantEnderman entity) {
                return false;
            }
        };
    }

    private MutantEndermanServantRenderState stateFor(MutantEndermanServant servant) {
        if (state == null || state.level() != servant.level()) {
            state = new MutantEndermanServantRenderState(servant.level());
        }
        state.bind(servant);
        return state;
    }

    @Override
    public boolean shouldRender(MutantEndermanServant servant, Frustum frustum, double x, double y, double z) {
        return super.shouldRender(servant, frustum, x, y, z)
                || delegate.shouldRender(stateFor(servant), frustum, x, y, z);
    }

    @Override
    public void render(MutantEndermanServant servant, float yaw, float partialTick, PoseStack poses,
            MultiBufferSource buffers, int light) {
        shadowRadius = servant.isClone() ? 0.5F : 0.8F;
        shadowStrength = servant.isClone() ? 0.5F : 1.0F - Mth.clamp(
                (servant.deathTime - 80.0F) / Math.max(1, servant.getDeathDuration() - 80), 0.0F, 1.0F);
        delegate.render(stateFor(servant), yaw, partialTick, poses, buffers, light);
        super.render(servant, yaw, partialTick, poses, buffers, light);
    }

    @Override
    public boolean shouldShowName(MutantEndermanServant servant) {
        return servant.shouldShowName()
                || servant.hasCustomName() && entityRenderDispatcher.crosshairPickEntity == servant;
    }

    @Override
    public Vec3 getRenderOffset(MutantEndermanServant servant, float partialTick) {
        return delegate.getRenderOffset(stateFor(servant), partialTick);
    }

    @Override
    public ResourceLocation getTextureLocation(MutantEndermanServant servant) {
        return servant.hasVoidEyeUpgrade() ? VOID_EYE_TEXTURE : delegate.getTextureLocation(stateFor(servant));
    }
}
