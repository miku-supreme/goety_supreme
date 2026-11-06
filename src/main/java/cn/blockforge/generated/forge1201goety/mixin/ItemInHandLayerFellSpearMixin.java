package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearPose;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemInHandLayer.class, remap = false)
public abstract class ItemInHandLayerFellSpearMixin {

    @Inject(method = "m_117184_(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;"
            + "Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/entity/HumanoidArm;"
            + "Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;m_269530_("
                            + "Lnet/minecraft/world/entity/LivingEntity;"
                            + "Lnet/minecraft/world/item/ItemStack;"
                            + "Lnet/minecraft/world/item/ItemDisplayContext;Z"
                            + "Lcom/mojang/blaze3d/vertex/PoseStack;"
                            + "Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
                    shift = At.Shift.BEFORE),
            require = 0)
    private void goetySupreme$fellSpearItemSrg(LivingEntity entity, ItemStack stack,
            ItemDisplayContext context, HumanoidArm arm, PoseStack pose, MultiBufferSource buffer,
            int light, CallbackInfo callback) {
        apply(entity, stack, arm, pose);
    }

    @Inject(method = "renderArmWithItem(Lnet/minecraft/world/entity/LivingEntity;"
            + "Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;"
            + "Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;"
            + "Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem("
                            + "Lnet/minecraft/world/entity/LivingEntity;"
                            + "Lnet/minecraft/world/item/ItemStack;"
                            + "Lnet/minecraft/world/item/ItemDisplayContext;Z"
                            + "Lcom/mojang/blaze3d/vertex/PoseStack;"
                            + "Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
                    shift = At.Shift.BEFORE),
            require = 0)
    private void goetySupreme$fellSpearItemOfficial(LivingEntity entity, ItemStack stack,
            ItemDisplayContext context, HumanoidArm arm, PoseStack pose, MultiBufferSource buffer,
            int light, CallbackInfo callback) {
        apply(entity, stack, arm, pose);
    }

    private void apply(LivingEntity entity, ItemStack stack, HumanoidArm arm, PoseStack pose) {
        if (!(stack.getItem() instanceof FellSpearItem)) {
            return;
        }
        EntityModel<?> model = ((ItemInHandLayer<?, ?>) (Object) this).getParentModel();
        FellSpearPose.applyThirdPersonItem(pose, entity, arm, model.attackTime);
    }
}
