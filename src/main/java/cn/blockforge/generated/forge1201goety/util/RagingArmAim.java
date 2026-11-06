package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.ally.golem.RedstoneMonstrosity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.util.RagingArmAim;
import cn.blockforge.generated.goetysupreme.model.RagingMonstrosityModel;

public final class RagingArmAim {

    private static final int SOLVE_ITERATIONS = 3;

    private static final float FADE_IN_START = 2.0F;

    private static final float FADE_IN_END = 10.0F;

    private static final float HOLD_END = 28.0F;

    private static final float FADE_OUT_END = 38.0F;

    private static final float MODEL_ORIGIN_Y = -1.501F;

    private static final Matrix4f PARENT = new Matrix4f();
    private static final Matrix4f ARM_M = new Matrix4f();
    private static final Matrix4f SHOULDER_M = new Matrix4f();
    private static final Matrix4f HAND_M = new Matrix4f();
    private static final Matrix3f PARENT_ROT = new Matrix3f();
    private static final Quaternionf PART_ROT = new Quaternionf();
    private static final Quaternionf Q_ANIM = new Quaternionf();
    private static final Quaternionf Q_AIM = new Quaternionf();
    private static final Quaternionf Q_STEP = new Quaternionf();
    private static final Quaternionf Q_C = new Quaternionf();
    private static final Quaternionf Q_TMP = new Quaternionf();
    private static final Vector3f V_SHOULDER = new Vector3f();
    private static final Vector3f V_HAND = new Vector3f();
    private static final Vector3f V_CURRENT = new Vector3f();
    private static final Vector3f V_DESIRED = new Vector3f();
    private static final Vector3f V_TARGET = new Vector3f();

    private RagingArmAim() {
    }

    public static void apply(RagingMonstrosityModel model, RedstoneMonstrosity entity,
            LivingEntity target, float partial, float animTick) {
        float weight = weight(animTick);
        if (weight <= 0.0F) {
            return;
        }

        ModelPart root = model.part("root");
        ModelPart hip = model.part("hip");
        ModelPart upper = model.part("upper");
        ModelPart arm = model.part("right_arm");
        ModelPart shoulder = model.part("right_shoulder");
        ModelPart hand = model.part("right_hand");

        PARENT.identity();
        mulPart(PARENT, root);
        mulPart(PARENT, hip);
        mulPart(PARENT, upper);
        PARENT_ROT.set(PARENT).invert();

        Q_ANIM.rotationZYX(arm.zRot, arm.yRot, arm.xRot);
        Q_AIM.set(Q_ANIM);

        Vec3 targetCenter = target.getPosition(partial)
                .add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        Vec3 entityPos = entity.getPosition(partial);
        float bodyYaw = Mth.rotLerp(partial, entity.yBodyRotO, entity.yBodyRot);

        Q_C.rotationY((float) Math.toRadians(180.0D - bodyYaw)).mul(Q_TMP.rotationZ((float) Math.PI));
        V_TARGET.set((float) (targetCenter.x - entityPos.x),
                (float) (targetCenter.y - entityPos.y),
                (float) (targetCenter.z - entityPos.z));
        Q_TMP.set(Q_C).conjugate().transform(V_TARGET);
        V_TARGET.sub(0.0F, MODEL_ORIGIN_Y, 0.0F);

        for (int i = 0; i < SOLVE_ITERATIONS; i++) {
            ARM_M.set(PARENT);
            ARM_M.translate(arm.x / 16.0F, arm.y / 16.0F, arm.z / 16.0F);
            ARM_M.rotate(Q_AIM);

            SHOULDER_M.set(ARM_M);
            mulPart(SHOULDER_M, shoulder);
            V_SHOULDER.set(SHOULDER_M.m30(), SHOULDER_M.m31(), SHOULDER_M.m32());

            HAND_M.set(SHOULDER_M);
            mulPart(HAND_M, hand);
            V_HAND.set(HAND_M.m30(), HAND_M.m31(), HAND_M.m32());

            V_CURRENT.set(V_HAND).sub(V_SHOULDER);
            V_DESIRED.set(V_TARGET).sub(V_SHOULDER);
            PARENT_ROT.transform(V_CURRENT);
            PARENT_ROT.transform(V_DESIRED);

            Q_STEP.rotationTo(V_CURRENT, V_DESIRED);
            Q_AIM.premul(Q_STEP);
        }

        Q_ANIM.slerp(Q_AIM, weight);
        writeEuler(arm, Q_ANIM);
    }

    private static float weight(float tick) {
        if (tick < FADE_IN_START) {
            return 0.0F;
        }
        if (tick < FADE_IN_END) {
            return (tick - FADE_IN_START) / (FADE_IN_END - FADE_IN_START);
        }
        if (tick <= HOLD_END) {
            return 1.0F;
        }
        if (tick < FADE_OUT_END) {
            return 1.0F - (tick - HOLD_END) / (FADE_OUT_END - HOLD_END);
        }
        return 0.0F;
    }

    private static void mulPart(Matrix4f matrix, ModelPart part) {
        matrix.translate(part.x / 16.0F, part.y / 16.0F, part.z / 16.0F);
        matrix.rotate(PART_ROT.rotationZYX(part.zRot, part.yRot, part.xRot));
    }

    private static void writeEuler(ModelPart part, Quaternionf q) {
        float w = q.w();
        float x = q.x();
        float y = q.y();
        float z = q.z();
        float sy = -2.0F * (x * z - w * y);
        sy = Mth.clamp(sy, -1.0F, 1.0F);
        float eulerY = (float) Math.asin(sy);
        float eulerX;
        float eulerZ;
        if (Math.abs(sy) < 0.9999F) {
            eulerX = (float) Math.atan2(2.0F * (y * z + w * x), 1.0F - 2.0F * (x * x + y * y));
            eulerZ = (float) Math.atan2(2.0F * (x * y + w * z), 1.0F - 2.0F * (y * y + z * z));
        } else {
            eulerX = (float) Math.atan2(-2.0F * (y * z - w * x), 1.0F - 2.0F * (x * x + z * z));
            eulerZ = 0.0F;
        }
        part.xRot = eulerX;
        part.yRot = eulerY;
        part.zRot = eulerZ;
    }
}
