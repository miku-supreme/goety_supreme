package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.IceStormRenderer;
import com.Polarice3.Goety.common.entities.projectiles.IceStorm;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.CryptIceStorm;
import cn.blockforge.generated.goetysupreme.renderer.CryptIceStormRenderer;

public class CryptIceStormRenderer extends EntityRenderer<CryptIceStorm> {

    private final IceStormRenderer delegate;

    public CryptIceStormRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.delegate = new IceStormRenderer(context);
    }

    @Override
    public void render(CryptIceStorm entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        this.delegate.render((IceStorm) entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(CryptIceStorm entity) {
        return this.delegate.getTextureLocation((IceStorm) entity);
    }
}
