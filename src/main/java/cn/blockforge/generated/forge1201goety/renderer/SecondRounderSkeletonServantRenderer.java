package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSkeletonServant;
import cn.blockforge.generated.goetysupreme.model.SecondRounderSkeletonServantModel;
import cn.blockforge.generated.goetysupreme.renderer.SecondRounderSkeletonServantRenderer;

public final class SecondRounderSkeletonServantRenderer
        extends MobRenderer<SecondRounderSkeletonServant,
                SecondRounderSkeletonServantModel<SecondRounderSkeletonServant>> {

    private static final ResourceLocation NONE = jerotes("second_rounder_skeleton_0.png");
    private static final ResourceLocation ABC = jerotes("second_rounder_skeleton_1.png");
    private static final ResourceLocation AB = jerotes("second_rounder_skeleton_2.png");
    private static final ResourceLocation BC = jerotes("second_rounder_skeleton_3.png");
    private static final ResourceLocation AC = jerotes("second_rounder_skeleton_4.png");
    private static final ResourceLocation A = jerotes("second_rounder_skeleton_5.png");
    private static final ResourceLocation B = jerotes("second_rounder_skeleton_6.png");
    private static final ResourceLocation C = jerotes("second_rounder_skeleton_7.png");

    public SecondRounderSkeletonServantRenderer(EntityRendererProvider.Context context) {
        super(context, new SecondRounderSkeletonServantModel<>(context.bakeLayer(ModelLayers.SKELETON)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.SKELETON_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.SKELETON_OUTER_ARMOR)),
                context.getModelManager()));
    }

    private static ResourceLocation jerotes(String file) {
        return ResourceLocation.fromNamespaceAndPath("jerotesvillage", "textures/entity/" + file);
    }

    @Override
    public float getFlipDegrees(SecondRounderSkeletonServant entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(SecondRounderSkeletonServant entity) {
        boolean a = entity.isSkinA();
        boolean b = entity.isSkinB();
        boolean c = entity.isSkinC();
        if (a && b && c) {
            return ABC;
        }
        if (a && b) {
            return AB;
        }
        if (b && c) {
            return BC;
        }
        if (a && c) {
            return AC;
        }
        if (a) {
            return A;
        }
        if (b) {
            return B;
        }
        if (c) {
            return C;
        }
        return NONE;
    }

    @Override
    public void scale(SecondRounderSkeletonServant entity, PoseStack poseStack, float partialTick) {
        if (entity.isGiant()) {
            poseStack.scale(SecondRounderSkeletonServant.GIANT_SCALE,
                    SecondRounderSkeletonServant.GIANT_SCALE,
                    SecondRounderSkeletonServant.GIANT_SCALE);
            this.shadowRadius = 0.9F;
        } else {
            poseStack.scale(1.05F, 1.05F, 1.05F);
            this.shadowRadius = 0.53F;
        }
    }
}
