package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordItemInHandLayer;

public class SedimentLordItemInHandLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel>
        extends ItemInHandLayer<T, M> {

    private final ItemInHandRenderer itemInHandRenderer;

    public SedimentLordItemInHandLayer(RenderLayerParent<T, M> parent, ItemInHandRenderer itemInHandRenderer) {
        super(parent, itemInHandRenderer);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void renderArmWithItem(LivingEntity entity, ItemStack stack, ItemDisplayContext context,
                                     HumanoidArm arm, PoseStack pose, MultiBufferSource buffer, int light) {
        if (stack.isEmpty()) {
            return;
        }
        pose.pushPose();
        this.getParentModel().translateToHand(arm, pose);
        pose.mulPose(Axis.XP.rotationDegrees(-90.0F));
        pose.mulPose(Axis.YP.rotationDegrees(180.0F));
        boolean left = arm == HumanoidArm.LEFT;
        pose.translate(0.0F, 0.125F, -0.125F);
        this.itemInHandRenderer.renderItem(entity, stack, context, left, pose, buffer, light);
        pose.popPose();
    }
}
