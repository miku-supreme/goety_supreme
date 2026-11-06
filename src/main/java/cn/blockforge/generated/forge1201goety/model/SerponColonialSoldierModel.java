package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.JavelinThrowPose;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.renderer.JerotesSpearAnimation;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierModel;
import cn.blockforge.generated.goetysupreme.entity.SerponFlameWizard;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;
import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;

public class SerponColonialSoldierModel<T extends AbstractSerpon>
        extends PlayerModel<T> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "serpon_colonial_soldier"), "main");

    private static final float LEG_XROT = 0.05F;
    private static final float LEG_Y = 8.5F;
    private static final float LEG_ZSCALE = 0.95F;

    public SerponColonialSoldierModel(ModelPart root) {
        super(root, false);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = PlayerModel.createMesh(CubeDeformation.NONE, false);
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entity.deathTime > 0) {
            ServantDeathPose.humanoid(this, entity, ageInTicks, 1.15F, 2.6F, 0.30F);
            this.leftSleeve.copyFrom(this.leftArm);
            this.rightSleeve.copyFrom(this.rightArm);
            this.leftPants.copyFrom(this.leftLeg);
            this.rightPants.copyFrom(this.rightLeg);
            this.hat.copyFrom(this.head);
            return;
        }

        applySerponLegs(limbSwing, limbSwingAmount);

        if (isShieldUp(entity)) {
            ModelPart offHand = entity.getMainArm() == HumanoidArm.RIGHT ? this.leftArm : this.rightArm;
            poseBlockingArm(offHand, false);
        }
        if (isTwoHandedBlockUp(entity)) {
            ModelPart mainArm = entity.getMainArm() == HumanoidArm.RIGHT ? this.rightArm : this.leftArm;
            poseBlockingArm(mainArm, false);
        }
        if (holdsHighGuardSword(entity)) {
            ModelPart mainArm = entity.getMainArm() == HumanoidArm.RIGHT ? this.rightArm : this.leftArm;
            mainArm.xRot = -1.8F;
        }
        if (entity.isAimingBlowpipe()) {
            applyBlowpipe();
        }
        if (entity instanceof SerponFlameWizard wizard
                && wizard.isSpellHumanoidTwoHanded() && wizard.isCasting()) {
            applyTwoHandedSpell(ageInTicks);
        }

        ServantSpearPose.apply(entity, this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entity.isUsingItem() && JavelinThrowPose.isJavelin(entity.getUseItem())) {
            HumanoidArm usedArm = entity.getUsedItemHand() == net.minecraft.world.InteractionHand.MAIN_HAND
                    ? entity.getMainArm() : entity.getMainArm().getOpposite();
            JavelinThrowPose.applyJavelinUse(this, usedArm);
        }

        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
        this.hat.copyFrom(this.head);
    }

    @Override
    public void setupAttackAnimation(T entity, float ageInTicks) {
        if (this.attackTime > 0.0F
                && entity.getTarget() != null
                && entity.getOffhandItem().getItem() instanceof SwordItem) {
            AnimationUtils.swingWeaponDown(this.rightArm, this.leftArm, entity, this.attackTime, ageInTicks);
            return;
        }
        super.setupAttackAnimation(entity, ageInTicks);
    }

    private static boolean isShieldUp(AbstractSerpon entity) {
        return entity.hasSpikedShieldInOffhand()
                && (entity.getUseItem().getItem() instanceof ShieldItem
                        || entity.getBlockTicks() > 0);
    }

    private static boolean isTwoHandedBlockUp(AbstractSerpon entity) {
        return entity.supportsTwoHandedBlock()
                && entity.isUsingItem()
                && entity.getTarget() != null
                && entity.getOffhandItem().isEmpty()
                && entity.getUseItem().getUseAnimation() == UseAnim.BLOCK
                && entity.attackAnim <= 0.0F;
    }

    private boolean holdsHighGuardSword(AbstractSerpon entity) {
        ItemStack main = entity.getMainHandItem();
        if (entity.getTarget() == null || !(main.getItem() instanceof SwordItem)) {
            return false;
        }
        if (JerotesItems.isTwoHanded(main) || JerotesItems.isDagger(main)) {
            return false;
        }
        return this.attackTime <= 0.0F;
    }

    private void applySerponLegs(float limbSwing, float limbSwingAmount) {
        float swing = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        this.leftLeg.xRot = LEG_XROT;
        this.rightLeg.xRot = LEG_XROT;
        this.leftLeg.yRot = 0.0F;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.zRot = 0.0F;
        this.rightLeg.zRot = 0.0F;
        this.leftLeg.y = LEG_Y;
        this.rightLeg.y = LEG_Y;
        this.leftLeg.zScale = LEG_ZSCALE;
        this.rightLeg.zScale = LEG_ZSCALE;
        this.leftLeg.x = swing + 2.0F;
        this.rightLeg.x = swing - 2.0F;
    }

    private void poseBlockingArm(ModelPart arm, boolean leftHanded) {
        if (arm == this.leftArm) {
            arm.xRot = arm.xRot * 0.5F - 0.9424779F
                    + Mth.clamp(this.head.xRot, -1.3962634F, 0.43633232F);
            arm.yRot = (leftHanded ? -30.0F : 30.0F) * ((float) Math.PI / 180.0F)
                    + Mth.clamp(this.head.yRot, -0.5235988F, 0.5235988F);
        } else {
            arm.xRot = arm.xRot * 0.5F - 0.9424779F
                    + Mth.clamp(this.head.xRot, -1.3962634F, 0.43633232F);
            arm.yRot = (leftHanded ? 30.0F : -30.0F) * ((float) Math.PI / 180.0F)
                    + Mth.clamp(this.head.yRot, -0.5235988F, 0.5235988F);
        }
    }

    private void applyBlowpipe() {
        float pitch = Mth.clamp(this.head.xRot, -1.2F, 1.2F) - 1.4835298F;
        this.rightArm.xRot = pitch;
        this.rightArm.yRot = this.head.yRot - 0.5235988F;
        this.leftArm.xRot = pitch;
        this.leftArm.yRot = this.head.yRot + 0.5235988F;
    }

    private void applyTwoHandedSpell(float ageInTicks) {
        float cast = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
        this.rightArm.z = 0.0F;
        this.rightArm.x = -5.0F;
        this.rightArm.xRot = cast;
        this.rightArm.zRot = 2.3561945F;
        this.rightArm.yRot = 0.0F;
        this.leftArm.z = 0.0F;
        this.leftArm.x = 5.0F;
        this.leftArm.xRot = cast;
        this.leftArm.zRot = -2.3561945F;
        this.leftArm.yRot = 0.0F;
    }
}
