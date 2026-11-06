package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.SkeletonServantRenderer;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.AbstractSkeletonServant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySkeletonServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.RagingStarChestLayer;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.entity.ServantEmissiveGlowLayer;
import cn.blockforge.generated.goetysupreme.entity.SkeletonServantQuiverLayer;

public final class GoetySkeletonServantRenderer extends SkeletonServantRenderer {

    private static final ResourceLocation ARCHER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/skeleton_servant_archer.png");

    private static final ResourceLocation EYE_GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/skeleton_servant_archer_glow.png");

    public GoetySkeletonServantRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new SkeletonServantQuiverLayer<>(this));
        this.addLayer(new ServantEmissiveGlowLayer<>(this, EYE_GLOW_TEXTURE));
        this.addLayer(new RagingStarChestLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeletonServant entity) {
        if (!(entity instanceof RagingStarServant star) || !star.isRagingStarred()) {
            return super.getTextureLocation(entity);
        }
        return ARCHER_TEXTURE;
    }
}
