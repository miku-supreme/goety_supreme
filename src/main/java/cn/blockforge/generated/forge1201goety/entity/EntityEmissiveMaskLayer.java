package cn.blockforge.generated.goetysupreme.entity;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import cn.blockforge.generated.goetysupreme.entity.EntityEmissiveMaskLayer;
import cn.blockforge.generated.goetysupreme.entity.ServantEmissiveGlowLayer;

public final class EntityEmissiveMaskLayer<T extends Entity, M extends EntityModel<T>>
        extends RenderLayer<T, M> {

    private final Function<T, ResourceLocation> maskTexture;
    private final Predicate<T> shouldGlow;
    private final Function<ResourceLocation, RenderType> renderType;
    private final float tintRed;
    private final float tintGreen;
    private final float tintBlue;
    private final float tintAlpha;

    public EntityEmissiveMaskLayer(RenderLayerParent<T, M> parent,
            Function<T, ResourceLocation> maskTexture) {
        this(parent, maskTexture, entity -> true);
    }

    public EntityEmissiveMaskLayer(RenderLayerParent<T, M> parent,
            Function<T, ResourceLocation> maskTexture, Predicate<T> shouldGlow) {
        this(parent, maskTexture, shouldGlow, RenderType::eyes);
    }

    public EntityEmissiveMaskLayer(RenderLayerParent<T, M> parent,
            Function<T, ResourceLocation> maskTexture, Predicate<T> shouldGlow,
            Function<ResourceLocation, RenderType> renderType) {
        this(parent, maskTexture, shouldGlow, renderType, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public EntityEmissiveMaskLayer(RenderLayerParent<T, M> parent,
            Function<T, ResourceLocation> maskTexture, Predicate<T> shouldGlow,
            Function<ResourceLocation, RenderType> renderType,
            float tintRed, float tintGreen, float tintBlue, float tintAlpha) {
        super(parent);
        this.maskTexture = Objects.requireNonNull(maskTexture, "maskTexture");
        this.shouldGlow = Objects.requireNonNull(shouldGlow, "shouldGlow");
        this.renderType = Objects.requireNonNull(renderType, "renderType");
        this.tintRed = tintRed;
        this.tintGreen = tintGreen;
        this.tintBlue = tintBlue;
        this.tintAlpha = tintAlpha;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
            T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (entity.isInvisible() || !this.shouldGlow.test(entity)) {
            return;
        }
        ResourceLocation texture = this.maskTexture.apply(entity);
        if (texture == null) {
            return;
        }
        this.getParentModel().renderToBuffer(poseStack,
                bufferSource.getBuffer(this.renderType.apply(texture)),
                packedLight, OverlayTexture.NO_OVERLAY,
                this.tintRed, this.tintGreen, this.tintBlue, this.tintAlpha);
    }
}
