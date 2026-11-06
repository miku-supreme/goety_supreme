package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.renderer.JavelinThrowPose;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierModel;

public final class JavelinThrowPose {

    private JavelinThrowPose() {
    }

    public static boolean isJavelin(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null) {
            return false;
        }
        String path = id.getPath();
        return path.equals("javelin") || path.endsWith("_javelin");
    }

    public static boolean isUsingJavelin(net.minecraft.world.entity.LivingEntity entity) {
        return entity != null && entity.isUsingItem() && isJavelin(entity.getUseItem());
    }

    public static boolean isJavelinForArm(net.minecraft.world.entity.LivingEntity entity,
            HumanoidArm arm, boolean throwSpearPose) {
        if (entity == null) {
            return false;
        }
        if (throwSpearPose) {
            boolean mainSide = entity.getMainArm() == arm;
            return isJavelin(mainSide ? entity.getMainHandItem() : entity.getOffhandItem());
        }
        if (!entity.isUsingItem() || !isJavelin(entity.getUseItem())) {
            return false;
        }
        HumanoidArm usedArm = entity.getUsedItemHand() == net.minecraft.world.InteractionHand.MAIN_HAND
                ? entity.getMainArm() : entity.getMainArm().getOpposite();
        return usedArm == arm;
    }

    public static void followHeadYaw(HumanoidModel<?> model, HumanoidArm arm) {
        ModelPart part = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        part.yRot = model.head.yRot;
    }

    public static void applyJavelinUse(HumanoidModel<?> model, HumanoidArm arm) {
        ModelPart part = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        part.xRot = part.xRot * 0.5F - (float) Math.PI;
        part.yRot = model.head.yRot;
    }
}
