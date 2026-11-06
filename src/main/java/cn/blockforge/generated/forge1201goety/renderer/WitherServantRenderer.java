package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.renderer.WitherServantArmorLayer;
import cn.blockforge.generated.goetysupreme.renderer.WitherServantGlowLayer;
import cn.blockforge.generated.goetysupreme.model.WitherServantModel;
import cn.blockforge.generated.goetysupreme.renderer.WitherServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.WitherServantStarLayer;
import cn.blockforge.generated.goetysupreme.renderer.SupremeChargeAuraRenderer;
import net.minecraft.client.renderer.MultiBufferSource;

public final class WitherServantRenderer extends MobRenderer<WitherServant, WitherServantModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/wither/wither.png");
    private static final ResourceLocation INVULNERABLE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation ANNIHILATION_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/wither_annihilation.png");

    private static final ResourceLocation ANNIHILATION_GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/wither_annihilation_glow.png");

    public WitherServantRenderer(EntityRendererProvider.Context context) {
        super(context, new WitherServantModel(context.bakeLayer(WitherServantModel.LAYER)), 1.0F);
        this.addLayer(new WitherServantArmorLayer(this, context.getModelSet()));
        this.addLayer(new WitherServantStarLayer(this));
        this.addLayer(WitherServantGlowLayer.emissive(this, ANNIHILATION_GLOW_TEXTURE));
    }

    @Override
    public void render(WitherServant servant, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        super.render(servant, entityYaw, partialTicks, poseStack, buffer, packedLight);
        SupremeChargeAuraRenderer.render(servant, servant.supremeChargeAura(), partialTicks, poseStack,
                buffer);
    }

    @Override
    public void scale(WitherServant servant, PoseStack poseStack, float partialTick) {
        float size = 2.0F;
        int invuln = servant.getInvulnerableTicks();
        if (invuln > 0) {
            size -= ((float) invuln - partialTick) / 220.0F * 0.5F;
        }
        poseStack.scale(size, size, size);
    }

    @Override
    public ResourceLocation getTextureLocation(WitherServant servant) {
        if (servant.isStarEmbedded()) {
            return ANNIHILATION_TEXTURE;
        }
        int invuln = servant.getInvulnerableTicks();
        if (invuln > 0 && (invuln > 80 || invuln / 5 % 2 != 1)) {
            return INVULNERABLE_TEXTURE;
        }
        return TEXTURE;
    }

    @Override
    public int getBlockLightLevel(WitherServant servant, net.minecraft.core.BlockPos pos) {
        return 15;
    }
}
