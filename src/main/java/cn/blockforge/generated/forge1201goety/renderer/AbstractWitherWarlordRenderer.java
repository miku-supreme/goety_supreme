package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.AbstractWitherWarlordRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordAfterimage;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.entity.EntityEmissiveMaskLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.IfritRenderer;
import cn.blockforge.generated.goetysupreme.util.SupremeChargeAura;
import cn.blockforge.generated.goetysupreme.renderer.SupremeChargeAuraRenderer;
import cn.blockforge.generated.goetysupreme.entity.WarlordSaberTrailLayer;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordArmorLayer;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordHelmetLayer;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;

public abstract class AbstractWitherWarlordRenderer<T extends AbstractWitherWarlord>
        extends MobRenderer<T, WitherWarlordModel<T>> {

    public static final float SCALE = 2.0645F;

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/wither_warlord.png");

    private static final ResourceLocation GLOW =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/wither_warlord_glow.png");

    private static final float[] SKY_BEAM_COLOR = {0.30F, 0.015F, 0.05F};

    private final WitherWarlordModel<T> shadowModel;

    public AbstractWitherWarlordRenderer(EntityRendererProvider.Context context) {
        super(context, new WitherWarlordModel<>(context.bakeLayer(WitherWarlordModel.LAYER)), 0.7F);
        this.shadowModel = new WitherWarlordModel<>(context.bakeLayer(WitherWarlordModel.LAYER));
        this.addLayer(new WitherWarlordArmorLayer<>(this, context.getModelSet()));
        this.addLayer(new WitherWarlordHelmetLayer<>(this, context.getModelSet()));
        this.addLayer(new WarlordSaberTrailLayer<>(this));
        this.addLayer(new EntityEmissiveMaskLayer<>(this, entity -> GLOW));
    }

    @Override
    public void scale(T entity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(SCALE, SCALE, SCALE);
        super.scale(entity, poseStack, partialTickTime);
    }

    @Override
    public float getFlipDegrees(T entity) {
        return 0.0F;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        ClientWarlordSaberTrail.captureFrameBase(entity.getId(), poseStack.last().pose());
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        ClientWarlordAfterimage.render(entity, this.getModel(), this.shadowModel, poseStack, buffer,
                packedLight, partialTicks);
        if (!entity.isSkyBeamLit()) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(-0.5D, 0.0D, -0.5D);
        BeaconRenderer.renderBeaconBeam(poseStack, buffer, BeaconRenderer.BEAM_LOCATION,
                partialTicks, 1.0F, entity.level().getGameTime(),
                0, AbstractWitherWarlord.SKY_BEAM_HEIGHT, SKY_BEAM_COLOR, 0.25F, 0.3F);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}
