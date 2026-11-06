package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;

public final class ServantDeathPose {

    private ServantDeathPose() {
    }

    public static final int PROGRESS_TICKS = 20;

    public static float progress(LivingEntity entity, float ageInTicks) {
        if (entity.deathTime <= 0) {
            return 0.0F;
        }
        float partialTick = ageInTicks - Mth.floor(ageInTicks);
        float raw = Mth.clamp((entity.deathTime + partialTick) / PROGRESS_TICKS, 0.0F, 1.0F);
        return raw * raw;
    }

    public static void humanoid(HumanoidModel<?> model, LivingEntity entity, float ageInTicks,
            float bodyPitch, float sink, float armSpread) {
        float t = progress(entity, ageInTicks);
        if (t <= 0.0F) {
            return;
        }
        model.body.xRot += bodyPitch * t;
        sinkAll(model, sink * t);
        model.head.xRot += 0.55F * t;
        model.leftArm.xRot -= 0.30F * t;
        model.rightArm.xRot -= 0.30F * t;
        model.leftArm.zRot += armSpread * t;
        model.rightArm.zRot -= armSpread * t;
        model.leftLeg.xRot -= 0.85F * t;
        model.rightLeg.xRot += 0.45F * t;
        model.hat.copyFrom(model.head);
    }

    public static void kneel(HumanoidModel<?> model, LivingEntity entity, float ageInTicks,
            float sink) {
        float t = progress(entity, ageInTicks);
        if (t <= 0.0F) {
            return;
        }
        model.body.xRot += 0.55F * t;
        sinkAll(model, sink * t);
        model.head.xRot += 0.85F * t;
        model.leftArm.xRot -= 0.20F * t;
        model.rightArm.xRot -= 0.20F * t;
        model.leftLeg.xRot -= 1.35F * t;
        model.rightLeg.xRot += 0.30F * t;
        model.hat.copyFrom(model.head);
    }

    private static void sinkAll(HumanoidModel<?> model, float sink) {
        model.head.y = sink;
        model.body.y = sink;
        model.leftArm.y = 2.0F + sink;
        model.rightArm.y = 2.0F + sink;
        model.leftLeg.y = 12.0F + sink;
        model.rightLeg.y = 12.0F + sink;
    }
}
