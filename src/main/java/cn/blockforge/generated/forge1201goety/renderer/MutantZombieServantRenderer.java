package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fuzs.mutantmonsters.client.init.ClientModRegistry;
import fuzs.mutantmonsters.client.renderer.entity.AlternateMobRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieServantArmorLayer;
import cn.blockforge.generated.goetysupreme.model.MutantZombieServantModel;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.WeaponAnchorItemInHandLayer;

public final class MutantZombieServantRenderer
        extends AlternateMobRenderer<MutantZombieServant, MutantZombieServantModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("mutantmonsters", "textures/entity/mutant_zombie.png");
    private static final ResourceLocation BERSERK_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "textures/entity/mutant_zombie_berserk.png");

    private static final float WEAPON_SCALE = 1.5F;

    public MutantZombieServantRenderer(EntityRendererProvider.Context context) {
        super(context,
                new MutantZombieServantModel(context.bakeLayer(ClientModRegistry.MUTANT_ZOMBIE)),
                1.0F);
        this.addLayer(new MutantZombieServantArmorLayer(this, context));
        this.addLayer(new WeaponAnchorItemInHandLayer<>(this, context.getItemInHandRenderer(), WEAPON_SCALE));
    }

    @Override
    public Vec3 getRenderOffset(MutantZombieServant servant, float partialTick) {
        return new Vec3(0.0D, -0.0975D, 0.0D);
    }

    @Override
    public void scale(MutantZombieServant servant, PoseStack poseStack, float partialTick) {
        float scale = 1.3F * (servant.hasBerserkTexture() ? MutantZombieServant.berserkSizeScale() : 1.0F);
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public void setupRotations(MutantZombieServant servant, PoseStack poseStack, float ageInTicks,
            float rotationYaw, float partialTick) {
        if (servant.deathTime <= 0) {
            super.setupRotations(servant, poseStack, ageInTicks, rotationYaw, partialTick);
            return;
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
        int deathTicks = Math.min(20, servant.deathTime);
        boolean reversing = false;
        if (servant.deathTime > 100) {
            deathTicks = 140 - servant.deathTime;
            reversing = true;
        }
        if (deathTicks > 0) {
            float progress = reversing
                    ? ((float) deathTicks - partialTick) / 40.0F * 1.6F
                    : ((float) deathTicks + partialTick - 1.0F) / 20.0F * 1.6F;
            progress = Mth.sqrt(progress);
            if (progress > 1.0F) {
                progress = 1.0F;
            }
            poseStack.mulPose(Axis.XN.rotationDegrees(progress * getFlipDegrees(servant)));
        }
    }

    @Override
    public float getFlipDegrees(MutantZombieServant servant) {
        return 80.0F;
    }

    @Override
    public RenderType getRenderType(MutantZombieServant servant, boolean showBody, boolean translucent,
            boolean glint) {
        return super.getRenderType(servant, showBody,
                translucent || servant.getVanishTime() > 0, glint);
    }

    @Override
    public float getAlpha(MutantZombieServant servant, float partialTick) {
        return servant.getVanishTime() > 0
                ? 1.0F - (servant.getVanishTime() + partialTick) / 100.0F * 0.6F
                : 1.0F;
    }

    @Override
    public boolean showsHurtColor(MutantZombieServant servant) {
        return super.showsHurtColor(servant) || servant.deathTime > 0 && servant.getLives() <= 0;
    }

    @Override
    public ResourceLocation getTextureLocation(MutantZombieServant servant) {
        return servant.hasBerserkTexture() ? BERSERK_TEXTURE : TEXTURE;
    }
}
