package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.renderer.SedimentLordServantAnimation;
import cn.blockforge.generated.goetysupreme.model.SedimentServantModel;
import cn.blockforge.generated.goetysupreme.entity.SedimentSoulStabilizerServant;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;
import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;

public class SedimentServantModel<T extends AbstractSedimentServant> extends PlayerModel<T> {

    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "sediment_servant"), "main");

    private static final float SPELL_ARM_X = 5.0F;
    private static final float SPELL_ARM_ZROT = 2.3561945F;
    private static final float SPELL_SWAY_SPEED = 0.6662F;
    private static final float SPELL_SWAY_AMPLITUDE = 0.25F;

    private static final float BODY_EXTRA_TILT = 0.2F;

    private static final float BODY_Y = 1.0F, BODY_Z = -2.0F;
    private static final float HEAD_Y = 1.2F, HEAD_Z = -2.0F;
    private static final float ARM_Y = 2.5F, ARM_Z = -2.0F;

    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    private final ModelPart root;

    private final boolean lordMode;
    private final ModelPart leftWeapon;
    private final ModelPart rightWeapon;

    public SedimentServantModel(ModelPart root) {
        this(root, false);
    }

    public SedimentServantModel(ModelPart root, boolean lordMode) {
        super(root, false);
        this.root = root;
        this.lordMode = lordMode;
        this.leftWeapon = root.getChild("left_arm").getChild("left_weapon");
        this.rightWeapon = root.getChild("right_arm").getChild("right_weapon");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = PlayerModel.createMesh(CubeDeformation.NONE, false);
        mesh.getRoot().getChild("right_arm").addOrReplaceChild("right_weapon",
                CubeListBuilder.create(), PartPose.offset(-1.0F, 8.0F, 0.0F));
        mesh.getRoot().getChild("left_arm").addOrReplaceChild("left_weapon",
                CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Nullable
    private T renderEntity;

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.renderEntity = entity;
        if (lordMode && entity instanceof SedimentLordServant lord) {
            applyLordAnimations(lord, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            return;
        }
        this.root.getAllParts().forEach(ModelPart::resetPose);
        applyBowPose(entity);
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        applyResurrecterBuild();
        if (entity.deathTime > 0) {
            ServantDeathPose.kneel(this, entity, ageInTicks, 2.9F);
            syncOuterLayers();
            return;
        }
        applyBlockPose(entity);
        applySpellCast(entity, ageInTicks);
        ServantSpearPose.apply(entity, this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        syncOuterLayers();
    }

    private void applyBowPose(T entity) {
        boolean drawingBow = entity.isUsingItem() && entity.getUseItem().getItem() instanceof BowItem;
        HumanoidModel.ArmPose pose = drawingBow
                ? HumanoidModel.ArmPose.BOW_AND_ARROW : HumanoidModel.ArmPose.EMPTY;
        this.rightArmPose = pose;
        this.leftArmPose = pose;
    }

    private void syncOuterLayers() {
        this.hat.copyFrom(this.head);
        this.jacket.copyFrom(this.body);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
    }

    private void applyLordAnimations(SedimentLordServant lord, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);

        float walk = lord.getAttackWalkAnim() / 10.0F;
        animateWalk(SedimentLordServantAnimation.ATTACKWALK, limbSwing, limbSwingAmount * walk, 1.5F, 2.0F);
        animateWalk(SedimentLordServantAnimation.WALK, limbSwing, limbSwingAmount * (1.0F - walk), 1.5F, 2.0F);

        animate(lord.startAnimationState, SedimentLordServantAnimation.START, ageInTicks);
        animate(lord.idleAnimationState, SedimentLordServantAnimation.IDLE, ageInTicks);
        animate(lord.attack1AnimationState, SedimentLordServantAnimation.ATTACK1, ageInTicks);
        animate(lord.attack2AnimationState, SedimentLordServantAnimation.ATTACK2, ageInTicks);
        animate(lord.swordAttack1AnimationState, SedimentLordServantAnimation.SWORDATTACK1, ageInTicks);
        animate(lord.swordAttack2AnimationState, SedimentLordServantAnimation.SWORDATTACK2, ageInTicks);
        animate(lord.swordAttack3AnimationState, SedimentLordServantAnimation.SWORDATTACK3, ageInTicks);
        animate(lord.swordAttack4AnimationState, SedimentLordServantAnimation.SWORDATTACK4, ageInTicks);
        animate(lord.leadAnimationState, SedimentLordServantAnimation.LEAD, ageInTicks);
        animate(lord.block1AnimationState, SedimentLordServantAnimation.BLOCK1, ageInTicks);
        animate(lord.block2AnimationState, SedimentLordServantAnimation.BLOCK2, ageInTicks);
        animate(lord.block3AnimationState, SedimentLordServantAnimation.BLOCK3, ageInTicks);
        animate(lord.shieldAttackAnimationState, SedimentLordServantAnimation.SHIELDATTACK, ageInTicks);
        animate(lord.dead1AnimationState, SedimentLordServantAnimation.DEAD1, ageInTicks);
        animate(lord.dead2AnimationState, SedimentLordServantAnimation.DEAD2, ageInTicks);
        animate(lord.pushingAttackAnimationState, SedimentLordServantAnimation.PUSHINGATTACK, ageInTicks);
        animate(lord.tripAttackAnimationState, SedimentLordServantAnimation.TRIPATTACK, ageInTicks);
        animate(lord.sweepingAttackAnimationState, SedimentLordServantAnimation.SWEEPINGATTACK, ageInTicks);
        ItemStack mainHand = lord.getMainHandItem();
        if (!mainHand.isEmpty() && mainHand.getItem() instanceof net.minecraft.world.item.TieredItem tiered
                && tiered.canDisableShield(mainHand, new ItemStack(net.minecraft.world.item.Items.SHIELD), lord, lord)) {
            animate(lord.precisionAttackAnimationState, SedimentLordServantAnimation.PRECISIONATTACK2, ageInTicks);
        } else {
            animate(lord.precisionAttackAnimationState, SedimentLordServantAnimation.PRECISIONATTACK1, ageInTicks);
        }
        animate(lord.menacingAttackAnimationState, SedimentLordServantAnimation.MENACINGATTACK, ageInTicks);
        animate(lord.maneuveringAttackAnimationState, SedimentLordServantAnimation.MANEUVERINGATTACK, ageInTicks);
        animate(lord.lungingAttackAnimationState, SedimentLordServantAnimation.LUNGINGATTACK, ageInTicks);
        animate(lord.goadingAttackAnimationState, SedimentLordServantAnimation.GOADINGATTACK, ageInTicks);
        animate(lord.feintingAttackAnimationState, SedimentLordServantAnimation.FEINTINGATTACK, ageInTicks);
        animate(lord.disarmingAttackAnimationState, SedimentLordServantAnimation.DISARMINGATTACK, ageInTicks);
        animate(lord.distractingStrikeAnimationState, SedimentLordServantAnimation.DISTRACTINGSTRIKE, ageInTicks);

        syncOuterLayers();
    }

    public void animate(AnimationState state, AnimationDefinition definition, float ageInTicks) {
        state.updateTime(ageInTicks, 1.0F);
        state.ifStarted(animState -> applyAnimation(this, definition, animState.getAccumulatedTime(), 1.0F));
    }

    public void animateWalk(AnimationDefinition definition, float limbSwing, float limbSwingAmount,
            float speed, float scale) {
        long accumulatedTime = (long) (limbSwing * 50.0F * speed);
        float interpolationScale = Math.min(limbSwingAmount * scale, 1.0F);
        applyAnimation(this, definition, accumulatedTime, interpolationScale);
    }

    private static void applyAnimation(SedimentServantModel<?> model, AnimationDefinition definition,
            long accumulatedTime, float scale) {
        float elapsed = getElapsedSeconds(definition, accumulatedTime);
        for (Map.Entry<String, List<AnimationChannel>> entry : definition.boneAnimations().entrySet()) {
            Optional<ModelPart> part = model.getAnyDescendantWithName(entry.getKey());
            part.ifPresent(modelPart -> entry.getValue().forEach(channel -> {
                Keyframe[] frames = channel.keyframes();
                int i = Math.max(0, Mth.binarySearch(0, frames.length, index -> elapsed <= frames[index].timestamp()) - 1);
                int j = Math.min(frames.length - 1, i + 1);
                Keyframe current = frames[i];
                Keyframe next = frames[j];
                float delta = elapsed - current.timestamp();
                float progress = Mth.clamp(delta / (next.timestamp() - current.timestamp()), 0.0F, 1.0F);
                next.interpolation().apply(ANIMATION_VECTOR_CACHE, progress, frames, i, j, scale);
                channel.target().apply(modelPart, ANIMATION_VECTOR_CACHE);
            }));
        }
    }

    private static float getElapsedSeconds(AnimationDefinition definition, long accumulatedTime) {
        float seconds = (float) accumulatedTime / 1000.0F;
        return definition.looping() ? seconds % definition.lengthInSeconds() : seconds;
    }

    public Optional<ModelPart> getAnyDescendantWithName(String name) {
        return name.equals("root") ? Optional.of(this.root)
                : this.root.getAllParts().filter(part -> part.hasChild(name)).findFirst()
                        .map(part -> part.getChild(name));
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        if (!lordMode) {
            super.translateToHand(arm, poseStack);
            return;
        }
        boolean left = arm == HumanoidArm.LEFT;
        (left ? this.leftArm : this.rightArm).translateAndRotate(poseStack);
        (left ? this.leftWeapon : this.rightWeapon).translateAndRotate(poseStack);
    }

    private void applyResurrecterBuild() {
        this.body.xRot += BODY_EXTRA_TILT;
        this.leftArm.zRot -= BODY_EXTRA_TILT;
        this.rightArm.zRot += BODY_EXTRA_TILT;

        this.body.y = BODY_Y;
        this.body.z = BODY_Z;
        this.head.y = HEAD_Y;
        this.head.z = HEAD_Z;
        this.leftArm.y = ARM_Y;
        this.leftArm.z = ARM_Z;
        this.rightArm.y = ARM_Y;
        this.rightArm.z = ARM_Z;
    }

    private void applyBlockPose(T entity) {
        if (entity.isUsingItem() && entity.getUseItem().getItem() instanceof ShieldItem) {
            boolean leftHand = entity.getUsedItemHand() == InteractionHand.OFF_HAND;
            poseBlockingArm(leftHand ? this.leftArm : this.rightArm);
            return;
        }
        ItemStack main = entity.getMainHandItem();
        if (main.isEmpty() || main.getItem() instanceof ShieldItem
                || main.getUseAnimation() != UseAnim.BLOCK
                || !entity.getOffhandItem().isEmpty()
                || !entity.isUsingItem()
                || entity.getUseItem().getUseAnimation() != UseAnim.BLOCK
                || entity.attackAnim > 0.0F) {
            return;
        }
        poseBlockingArm(entity.getMainArm() == HumanoidArm.RIGHT ? this.rightArm : this.leftArm);
    }

    private void poseBlockingArm(ModelPart arm) {
        if (arm == this.leftArm) {
            arm.xRot = arm.xRot * 0.5F - 0.9424779F
                    + Mth.clamp(this.head.xRot, -1.3962634F, 0.43633232F);
            arm.yRot = 30.0F * ((float) Math.PI / 180.0F)
                    + Mth.clamp(this.head.yRot, -0.5235988F, 0.5235988F);
        } else {
            arm.xRot = arm.xRot * 0.5F - 0.9424779F
                    + Mth.clamp(this.head.xRot, -1.3962634F, 0.43633232F);
            arm.yRot = -30.0F * ((float) Math.PI / 180.0F)
                    + Mth.clamp(this.head.yRot, -0.5235988F, 0.5235988F);
        }
    }

    private void applySpellCast(T entity, float ageInTicks) {
        if (entity.getCastTicks() <= 0) {
            return;
        }
        ModelPart castArm = entity.getMainArm() == HumanoidArm.RIGHT ? this.rightArm : this.leftArm;
        castArm.z = 0.0F;
        castArm.x = entity.getMainArm() == HumanoidArm.RIGHT ? -SPELL_ARM_X : SPELL_ARM_X;
        castArm.xRot = Mth.cos(ageInTicks * SPELL_SWAY_SPEED) * SPELL_SWAY_AMPLITUDE;
        castArm.zRot = entity.getMainArm() == HumanoidArm.RIGHT
                ? SPELL_ARM_ZROT : -SPELL_ARM_ZROT;
        castArm.yRot = 0.0F;
    }
}
