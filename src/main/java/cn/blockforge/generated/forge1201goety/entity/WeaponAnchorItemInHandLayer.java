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
import cn.blockforge.generated.goetysupreme.entity.WeaponAnchorItemInHandLayer;

public class WeaponAnchorItemInHandLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel>
        extends ItemInHandLayer<T, M> {

    private final ItemInHandRenderer itemInHandRenderer;

    private final float scale;

    public WeaponAnchorItemInHandLayer(RenderLayerParent<T, M> parent, ItemInHandRenderer itemInHandRenderer) {
        this(parent, itemInHandRenderer, 1.0F);
    }

    public WeaponAnchorItemInHandLayer(RenderLayerParent<T, M> parent, ItemInHandRenderer itemInHandRenderer,
            float scale) {
        super(parent, itemInHandRenderer);
        this.itemInHandRenderer = itemInHandRenderer;
        this.scale = scale;
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
        if (this.scale != 1.0F) {
            pose.scale(this.scale, this.scale, this.scale);
        }
        this.itemInHandRenderer.renderItem(entity, stack, context, left, pose, buffer, light);
        pose.popPose();
    }
}
