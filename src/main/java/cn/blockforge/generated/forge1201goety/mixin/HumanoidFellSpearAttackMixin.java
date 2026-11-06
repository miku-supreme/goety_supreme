package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.renderer.FellSpearPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;

@Mixin(value = HumanoidModel.class, remap = false)
public abstract class HumanoidFellSpearAttackMixin {

    @Inject(method = {"m_7884_(Lnet/minecraft/world/entity/LivingEntity;F)V",
                      "setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V"},
            at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$fellSpearAttack(LivingEntity entity, float ageInTicks, CallbackInfo callback) {
        HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;
        if (model.attackTime <= 0.0F || !FellSpearPose.holds(entity)) {
            return;
        }
        FellSpearPose.applyAttack(model, entity);
        callback.cancel();
    }
}
