package cn.blockforge.generated.goetysupreme.mixin;

import com.Polarice3.Goety.client.render.ScatterBombRenderer;
import com.Polarice3.Goety.common.entities.projectiles.ScatterBomb;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;

@Mixin(value = ScatterBombRenderer.class, remap = false)
public abstract class GoetyScatterBombScaleMixin {

    @Inject(
            method = "render(Lcom/Polarice3/Goety/common/entities/projectiles/ScatterBomb;FF"
                    + "Lcom/mojang/blaze3d/vertex/PoseStack;"
                    + "Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/Polarice3/Goety/client/render/model/ScatterMineModel;"
                            + "m_7695_(Lcom/mojang/blaze3d/vertex/PoseStack;"
                            + "Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V",
                    shift = At.Shift.BEFORE),
            require = 0)
    private void goetySupreme$scaleUpAntiairBomb(ScatterBomb bomb, float yaw, float partial,
            PoseStack pose, MultiBufferSource buffer, int light, CallbackInfo callback) {
        if (bomb.isNoGravity()) {
            pose.pushPose();
            pose.scale(1.5F, 1.5F, 1.5F);
        }
    }

    @Inject(
            method = "render(Lcom/Polarice3/Goety/common/entities/projectiles/ScatterBomb;FF"
                    + "Lcom/mojang/blaze3d/vertex/PoseStack;"
                    + "Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/Polarice3/Goety/client/render/model/ScatterMineModel;"
                            + "m_7695_(Lcom/mojang/blaze3d/vertex/PoseStack;"
                            + "Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V",
                    shift = At.Shift.AFTER),
            require = 0)
    private void goetySupreme$scaleDownAntiairBomb(ScatterBomb bomb, float yaw, float partial,
            PoseStack pose, MultiBufferSource buffer, int light, CallbackInfo callback) {
        if (bomb.isNoGravity()) {
            pose.popPose();
        }
    }
}
