package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.DoomFiveHeadShark;
import cn.blockforge.generated.goetysupreme.model.DoomFiveHeadSharkModel;
import cn.blockforge.generated.goetysupreme.renderer.DoomFiveHeadSharkRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public class DoomFiveHeadSharkRenderer
        extends MobRenderer<DoomFiveHeadShark, DoomFiveHeadSharkModel<DoomFiveHeadShark>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/doom_five_head_shark/doom_five_head_shark.png");

    private static final ResourceLocation DEEPSEEK_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            GeneratedMod.MOD_ID, "textures/entity/doom_five_head_shark/doom_five_head_shark_deepseek.png");

    private static final float BASE_SCALE = 1.2F * 1.25F;

    private static final float UPGRADED_SCALE = 1.35F * 1.25F;

    public DoomFiveHeadSharkRenderer(EntityRendererProvider.Context context) {
        super(context, new DoomFiveHeadSharkModel<>(context.bakeLayer(DoomFiveHeadSharkModel.LAYER)), 0.65F);
    }

    @Override
    public ResourceLocation getTextureLocation(DoomFiveHeadShark entity) {
        return hasDeepseekName(entity) ? DEEPSEEK_TEXTURE : TEXTURE;
    }

    private static boolean hasDeepseekName(DoomFiveHeadShark entity) {
        Component customName = entity.getCustomName();
        if (customName == null) {
            return false;
        }
        String name = ChatFormatting.stripFormatting(customName.getString()).trim();
        return "deepseek".equalsIgnoreCase(name);
    }

    @Override
    public float getFlipDegrees(DoomFiveHeadShark entity) {
        return 0.0F;
    }

    @Override
    public void scale(DoomFiveHeadShark entity, PoseStack poseStack, float partialTicks) {
        float scale = entity.isUpgraded() ? UPGRADED_SCALE : BASE_SCALE;
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public void setupRotations(DoomFiveHeadShark entity, PoseStack poseStack, float ageInTicks,
            float rotationYaw, float partialTicks) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
    }
}
