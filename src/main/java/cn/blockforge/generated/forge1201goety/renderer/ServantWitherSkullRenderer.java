package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.WitherSkull;
import com.mojang.blaze3d.vertex.PoseStack;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.NamelessWither;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.ServantWitherSkull;
import cn.blockforge.generated.goetysupreme.renderer.ServantWitherSkullRenderer;

public final class ServantWitherSkullRenderer extends WitherSkullRenderer {

    public static final ResourceLocation ASTRAL_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/astral_wither_skull.png");

    public static boolean usesAstralTexture(WitherSkull skull) {
        return skull instanceof ServantWitherSkull servant && servant.isAstralSkull();
    }

    public ServantWitherSkullRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(WitherSkull skull, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        if (skull.getOwner() instanceof NamelessWither) {
            poseStack.scale(1.2F, 1.2F, 1.2F);
        }
        super.render(skull, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(WitherSkull skull) {
        return usesAstralTexture(skull) ? ASTRAL_TEXTURE : super.getTextureLocation(skull);
    }
}
