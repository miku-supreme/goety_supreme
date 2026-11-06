package cn.blockforge.generated.goetysupreme.renderer;

import com.jerotes.jerotes.client.model.Modelspecial_action;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.renderer.JerotesSpearAnimation;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.renderer.JvillageSpearPose;

public final class JvillageSpearPose {

    private static Modelspecial_action<LivingEntity> dummy;
    private static boolean bakeFailed;

    private JvillageSpearPose() {
    }

    public static boolean apply(LivingEntity living, ModelPart head, ModelPart rightArm,
            ModelPart leftArm, ModelPart body, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        Modelspecial_action<LivingEntity> instance = dummy();
        if (instance == null) {
            return false;
        }
        try {
            Modelspecial_action.specialAnim(living, instance, head, rightArm, leftArm, body,
                    limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            return true;
        } catch (RuntimeException | LinkageError broken) {
            bakeFailed = true;
            return false;
        }
    }

    private static Modelspecial_action<LivingEntity> dummy() {
        if (dummy != null || bakeFailed) {
            return dummy;
        }
        try {
            ModelPart root = Minecraft.getInstance().getEntityModels()
                    .bakeLayer(Modelspecial_action.LAYER_LOCATION);
            dummy = new Modelspecial_action<>(root);
        } catch (RuntimeException | LinkageError missing) {
            bakeFailed = true;
            dummy = null;
        }
        return dummy;
    }
}
