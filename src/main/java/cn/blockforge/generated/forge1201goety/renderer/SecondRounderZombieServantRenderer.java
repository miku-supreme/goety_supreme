package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderZombieServant;
import cn.blockforge.generated.goetysupreme.model.SecondRounderZombieServantModel;
import cn.blockforge.generated.goetysupreme.renderer.SecondRounderZombieServantRenderer;

public final class SecondRounderZombieServantRenderer
        extends MobRenderer<SecondRounderZombieServant, SecondRounderZombieServantModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/second_rounder_zombie.png");

    public SecondRounderZombieServantRenderer(EntityRendererProvider.Context context) {
        super(context, new SecondRounderZombieServantModel(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public float getFlipDegrees(SecondRounderZombieServant entity) {
        return 0.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(SecondRounderZombieServant entity) {
        return TEXTURE;
    }

    @Override
    public void scale(SecondRounderZombieServant entity, PoseStack poseStack, float partialTick) {
        if (entity.isGiant()) {
            poseStack.scale(SecondRounderZombieServant.GIANT_SCALE,
                    SecondRounderZombieServant.GIANT_SCALE,
                    SecondRounderZombieServant.GIANT_SCALE);
            this.shadowRadius = 0.9F;
        } else {
            poseStack.scale(1.05F, 1.05F, 1.05F);
            this.shadowRadius = 0.53F;
        }
    }
}
