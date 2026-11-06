package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;
import cn.blockforge.generated.goetysupreme.renderer.SerponFireApostleAnimation;
import cn.blockforge.generated.goetysupreme.model.SerponFireApostleModel;

public class SerponFireApostleModel extends PlayerModel<SerponFireApostle> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "serpon_fire_apostle"), "main");

    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    private final ModelPart root;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart left_weapon;
    private final ModelPart right_weapon;

    public SerponFireApostleModel(ModelPart root) {
        super(root, false);
        this.root = root;
        this.right_wing = root.getChild("body").getChild("right_wing");
        this.left_wing = root.getChild("body").getChild("left_wing");
        this.left_weapon = root.getChild("left_arm").getChild("left_weapon");
        this.right_weapon = root.getChild("right_arm").getChild("right_weapon");
    }

    public static LayerDefinition createBodyLayer() {
    MeshDefinition mesh = new MeshDefinition();
    PartDefinition rootDef = mesh.getRoot();
    rootDef.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
    rootDef.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
    PartDefinition head = rootDef.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
    PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)-3.0f, (float)-4.75f));
    PartDefinition mouth_out_r1 = mouth.addOrReplaceChild("mouth_out_r1", CubeListBuilder.create().texOffs(82, 21).addBox(-3.0f, -3.0f, -1.5f, 6.0f, 6.0f, 3.0f, new CubeDeformation(0.15f)), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)0.0f, (float)0.1396f, (float)0.0f, (float)0.0f));
    PartDefinition mouth_r1 = mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(64, 21).addBox(-3.0f, -3.0f, -1.5f, 6.0f, 6.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0785f, (float)0.0f, (float)0.0f));
    PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(80, -4).addBox(0.0f, -31.25f, 4.0f, 0.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(80, -4).addBox(0.0f, -27.25f, 3.0f, 0.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
    PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)30.0f, (float)0.0f));
    PartDefinition left_horn_r1 = horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(64, -8).mirror().addBox(0.0f, -2.0f, -4.0f, 0.0f, 4.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)4.75f, (float)-39.5f, (float)4.0f, (float)0.0f, (float)0.0f, (float)0.4363f));
    PartDefinition right_horn_r1 = horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(64, -8).addBox(0.0f, -2.0f, -4.0f, 0.0f, 4.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-4.75f, (float)-39.5f, (float)4.0f, (float)0.0f, (float)0.0f, (float)-0.4363f));
    PartDefinition hat = rootDef.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.5f)), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
    PartDefinition body = rootDef.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
    PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 48).addBox(0.0f, -3.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(-0.05f)).texOffs(0, 16).addBox(-4.0f, -3.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(-0.05f)).texOffs(0, 32).addBox(-4.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.25f)).texOffs(0, 48).addBox(0.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.25f)), PartPose.offset((float)0.0f, (float)12.0f, (float)1.0f));
    PartDefinition tail_1 = tail.addOrReplaceChild("tail_1", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)7.5f, (float)0.5f));
    PartDefinition tail_1_r1 = tail_1.addOrReplaceChild("tail_1_r1", CubeListBuilder.create().texOffs(98, 0).addBox(-3.0f, -3.0f, -2.0f, 6.0f, 6.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)1.0f, (float)2.0f, (float)1.2217f, (float)0.0f, (float)0.0f));
    PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)2.0f, (float)3.5f));
    PartDefinition tail_2_r1 = tail_2.addOrReplaceChild("tail_2_r1", CubeListBuilder.create().texOffs(98, 10).addBox(-2.0f, -2.5f, -1.5f, 4.0f, 5.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)0.5f, (float)3.0f, (float)1.5272f, (float)0.0f, (float)0.0f));
    PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)1.25f, (float)5.25f));
    PartDefinition tail_3_r1 = tail_3.addOrReplaceChild("tail_3_r1", CubeListBuilder.create().texOffs(112, 10).addBox(-1.0f, 11.0f, 6.0f, 2.0f, 5.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)0.0f, (float)7.0f, (float)-11.0f, (float)1.5708f, (float)0.0f, (float)0.0f));
    PartDefinition tail_4 = tail_3.addOrReplaceChild("tail_4", CubeListBuilder.create().texOffs(118, -5).addBox(0.0f, -1.0f, 0.0f, 0.0f, 2.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)0.25f, (float)4.25f));
    PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
    PartDefinition right_wing_r1 = right_wing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(64, -6).addBox(0.0f, -8.0f, -5.0f, 0.0f, 17.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation((float)-4.5f, (float)5.5f, (float)6.0f, (float)0.2182f, (float)-0.4363f, (float)0.0f));
    PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
    PartDefinition left_wing_r1 = left_wing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(64, -6).mirror().addBox(0.0f, -8.0f, -5.0f, 0.0f, 17.0f, 10.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)4.5f, (float)5.5f, (float)6.0f, (float)0.2182f, (float)0.4363f, (float)0.0f));
    PartDefinition jacket = rootDef.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.25f)), PartPose.offset((float)0.0f, (float)0.0f, (float)0.0f));
    PartDefinition left_arm = rootDef.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset((float)5.0f, (float)2.0f, (float)0.0f));
    PartDefinition left_armother_r1 = left_arm.addOrReplaceChild("left_armother_r1", CubeListBuilder.create().texOffs(84, -3).mirror().addBox(-0.05f, -2.0f, -3.5f, 0.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)3.25f, (float)3.5f, (float)0.75f, (float)1.3526f, (float)0.0f, (float)0.0873f));
    PartDefinition left_weapon = left_arm.addOrReplaceChild("left_weapon", CubeListBuilder.create(), PartPose.offset((float)1.0f, (float)8.0f, (float)0.0f));
    PartDefinition left_sleeve = rootDef.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.25f)), PartPose.offset((float)5.0f, (float)2.0f, (float)0.0f));
    PartDefinition right_arm = rootDef.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset((float)-5.0f, (float)2.0f, (float)0.0f));
    PartDefinition right_armother_r1 = right_arm.addOrReplaceChild("right_armother_r1", CubeListBuilder.create().texOffs(84, -3).mirror().addBox(0.05f, -2.0f, -3.5f, 0.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation((float)-3.25f, (float)3.5f, (float)0.75f, (float)1.3526f, (float)0.0f, (float)-0.0873f));
    PartDefinition right_weapon = right_arm.addOrReplaceChild("right_weapon", CubeListBuilder.create(), PartPose.offset((float)-1.0f, (float)8.0f, (float)0.0f));
    PartDefinition right_sleeve = rootDef.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32).addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.25f)), PartPose.offset((float)-5.0f, (float)2.0f, (float)0.0f));
    PartDefinition left_leg = rootDef.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0f, 0.0f, -1.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.25f)), PartPose.offset((float)2.0f, (float)12.0f, (float)0.0f));
    PartDefinition left_pants = rootDef.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.offset((float)2.0f, (float)12.0f, (float)0.0f));
    PartDefinition right_leg = rootDef.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0f, 0.0f, -0.75f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.25f)), PartPose.offset((float)-2.0f, (float)12.0f, (float)-0.25f));
    PartDefinition right_pants = rootDef.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.offset((float)-2.0f, (float)12.0f, (float)0.0f));
    return LayerDefinition.create((MeshDefinition)mesh, (int)128, (int)64);
    }

    @Override
    public void setupAnim(SerponFireApostle entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);
        float attackProgress = Mth.clamp(entity.attackAnimProgress, 0.0F, 10.0F);
        animateWalk(SerponFireApostleAnimation.ATTACKWALK, limbSwing,
                limbSwingAmount * Math.min(1.0F, attackProgress / 10.0F), 1.5F, 2.0F);
        animateWalk(SerponFireApostleAnimation.WALK, limbSwing,
                limbSwingAmount * Math.min(1.0F, 1.0F - attackProgress / 10.0F), 1.5F, 2.0F);
        animate(entity.idleAnimationState, SerponFireApostleAnimation.IDLE, ageInTicks);
        animate(entity.attack1AnimationState, SerponFireApostleAnimation.ATTACK1, ageInTicks);
        animate(entity.attack2AnimationState, SerponFireApostleAnimation.ATTACK2, ageInTicks);
        animate(entity.attack3AnimationState, SerponFireApostleAnimation.ATTACK3, ageInTicks);
        animate(entity.attack4AnimationState, SerponFireApostleAnimation.ATTACK4, ageInTicks);
        animate(entity.attack5AnimationState, SerponFireApostleAnimation.ATTACK5, ageInTicks);
        animate(entity.deadAnimationState, SerponFireApostleAnimation.DEAD, ageInTicks);
        if (entity.isPrepare()) {
            this.left_wing.xScale = 1.2F;
            this.left_wing.yScale = 1.2F;
            this.left_wing.zScale = 1.2F;
            this.right_wing.xScale = 1.2F;
            this.right_wing.yScale = 1.2F;
            this.right_wing.zScale = 1.2F;
        }
        applyBlockPose(entity);
        this.hat.copyFrom(this.head);
        this.jacket.copyFrom(this.body);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);
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

    private static void applyAnimation(SerponFireApostleModel model,
            AnimationDefinition definition, long accumulatedTime, float scale) {
        float elapsed = getElapsedSeconds(definition, accumulatedTime);
        for (Map.Entry<String, List<net.minecraft.client.animation.AnimationChannel>> entry
                : definition.boneAnimations().entrySet()) {
            Optional<ModelPart> part = model.getAnyDescendantWithName(entry.getKey());
            part.ifPresent(modelPart -> entry.getValue().forEach(channel -> {
                Keyframe[] frames = channel.keyframes();
                int i = Math.max(0, Mth.binarySearch(0, frames.length,
                        index -> elapsed <= frames[index].timestamp()) - 1);
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
    public void translateToHand(HumanoidArm arm, PoseStack pose) {
        boolean left = arm == HumanoidArm.LEFT;
        (left ? this.leftArm : this.rightArm).translateAndRotate(pose);
        (left ? this.left_weapon : this.right_weapon).translateAndRotate(pose);
    }

    private void applyBlockPose(SerponFireApostle entity) {
        if (entity.getTarget() == null || !entity.getTarget().isAlive()) {
            return;
        }
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
}
