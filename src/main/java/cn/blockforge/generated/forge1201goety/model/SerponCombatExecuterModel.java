package cn.blockforge.generated.goetysupreme.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.animation.AnimationChannel;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.SedimentServantModel;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierModel;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.renderer.SerponCombatExecuterAnimation;
import cn.blockforge.generated.goetysupreme.model.SerponCombatExecuterModel;

public class SerponCombatExecuterModel extends PlayerModel<SerponCombatExecuter> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "serpon_combat_executer"), "main");

    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    private final ModelPart root;

    private final ModelPart leftWeapon;
    private final ModelPart rightWeapon;

    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public SerponCombatExecuterModel(ModelPart root) {
        super(root, false);
        this.root = root;
        this.leftWeapon = root.getChild("left_arm").getChild("left_weapon");
        this.rightWeapon = root.getChild("right_arm").getChild("right_weapon");
        this.leftWing = root.getChild("body").getChild("left_wing");
        this.rightWing = root.getChild("body").getChild("right_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        var root = mesh.getRoot();
        root.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);

        var head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        var horn = head.addOrReplaceChild("horn", CubeListBuilder.create(),
                PartPose.offset(0.0F, -8.5F, 1.0F));
        horn.addOrReplaceChild("left_horn_r1",
                CubeListBuilder.create().texOffs(104, 65).mirror()
                        .addBox(0.0F, -6.0F, -4.0F, 0.0F, 8.0F, 12.0F, CubeDeformation.NONE)
                        .mirror(false),
                PartPose.offsetAndRotation(4.75F, -1.0F, 3.0F, 0.0F, 0.0F, 0.4363F));
        horn.addOrReplaceChild("right_horn_r1",
                CubeListBuilder.create().texOffs(104, 65)
                        .addBox(0.0F, -6.0F, -4.0F, 0.0F, 8.0F, 12.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-4.75F, -1.0F, 3.0F, 0.0F, 0.0F, -0.4363F));
        head.addOrReplaceChild("hair",
                CubeListBuilder.create().texOffs(120, 81)
                        .addBox(0.0F, -4.0F, -1.5F, 0.0F, 4.0F, 4.0F, CubeDeformation.NONE)
                        .texOffs(120, 81)
                        .addBox(0.0F, 0.0F, -2.5F, 0.0F, 4.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, -3.25F, 5.5F));
        var mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -3.0F, -5.25F, -0.0873F, 0.0F, 0.0F));
        mouth.addOrReplaceChild("mouth_out_r1",
                CubeListBuilder.create().texOffs(91, 119)
                        .addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.15F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1396F, 0.0F, 0.0F));
        mouth.addOrReplaceChild("mouth_r1",
                CubeListBuilder.create().texOffs(73, 119)
                        .addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0785F, 0.0F, 0.0F));

        root.addOrReplaceChild("hat",
                CubeListBuilder.create().texOffs(32, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        var body = root.addOrReplaceChild("body", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("main_body",
                CubeListBuilder.create().texOffs(16, 16)
                        .addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        var leftWing = body.addOrReplaceChild("left_wing",
                CubeListBuilder.create().texOffs(0, 57).mirror()
                        .addBox(1.3787F, -9.5F, -4.1213F, 0.0F, 24.0F, 16.0F, CubeDeformation.NONE)
                        .mirror(false),
                PartPose.offsetAndRotation(2.0F, 3.0F, 4.0F, 0.0F, 0.7854F, 0.0F));
        leftWing.addOrReplaceChild("left_wing_1",
                CubeListBuilder.create().texOffs(0, 81).mirror()
                        .addBox(0.0F, -10.0F, 0.0F, 0.0F, 31.0F, 16.0F, CubeDeformation.NONE)
                        .mirror(false),
                PartPose.offsetAndRotation(1.3787F, -1.5F, 11.8787F, 0.0F, 0.5236F, 0.0F));
        var rightWing = body.addOrReplaceChild("right_wing",
                CubeListBuilder.create().texOffs(0, 57)
                        .addBox(-1.3787F, -9.5F, -4.1213F, 0.0F, 24.0F, 16.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-2.0F, 3.0F, 4.0F, 0.0F, -0.7854F, 0.0F));
        rightWing.addOrReplaceChild("right_wing_1",
                CubeListBuilder.create().texOffs(0, 81)
                        .addBox(0.0F, -10.0F, 0.0F, 0.0F, 31.0F, 16.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-1.3787F, -1.5F, 11.8787F, 0.0F, -0.5236F, 0.0F));

        var tail = body.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(64, 50)
                        .addBox(-2.0F, -2.0F, -2.4F, 4.0F, 14.0F, 0.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        var tail1 = tail.addOrReplaceChild("tail_1",
                CubeListBuilder.create().texOffs(16, 48)
                        .addBox(-0.1F, 1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(-0.05F))
                        .texOffs(0, 16)
                        .addBox(-3.9F, 1.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(-0.05F))
                        .texOffs(0, 48)
                        .addBox(0.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                        .texOffs(0, 32)
                        .addBox(-4.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
        var tail2 = tail1.addOrReplaceChild("tail_2",
                CubeListBuilder.create().texOffs(108, 91)
                        .addBox(-3.0F, 0.5F, -2.0F, 6.0F, 6.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, 0.3491F, 0.0F, 0.0F));
        var tail3 = tail2.addOrReplaceChild("tail_3",
                CubeListBuilder.create().texOffs(108, 101)
                        .addBox(-3.0F, -0.25F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, 6.5F, 0.0F, 0.5236F, 0.0F, 0.0F));
        var tail4 = tail3.addOrReplaceChild("tail_4",
                CubeListBuilder.create().texOffs(106, 111)
                        .addBox(-2.0F, 0.5F, -1.5F, 4.0F, 6.0F, 3.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.48F, 0.0F, 0.0F));
        var tail5 = tail4.addOrReplaceChild("tail_5",
                CubeListBuilder.create().texOffs(114, 120)
                        .addBox(-2.0F, 0.25F, -1.5F, 4.0F, 5.0F, 3.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0436F, 0.0F, 0.0F));
        var tail6 = tail5.addOrReplaceChild("tail_6",
                CubeListBuilder.create().texOffs(120, 111)
                        .addBox(-1.0F, 0.25F, -1.0F, 2.0F, 5.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 4.75F, 0.0F, 0.0873F, 0.0F, 0.0F));
        var tail7 = tail6.addOrReplaceChild("tail_7", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 4.75F, 0.0F, 0.3054F, 0.0F, 0.0F));
        tail7.addOrReplaceChild("tail_7_r1",
                CubeListBuilder.create().texOffs(106, 78)
                        .addBox(0.0F, -1.0F, -3.5F, 0.0F, 2.0F, 11.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        root.addOrReplaceChild("jacket",
                CubeListBuilder.create().texOffs(16, 32)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        var leftArm = root.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(32, 48)
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        leftArm.addOrReplaceChild("left_armother_r1",
                CubeListBuilder.create().texOffs(106, 78).mirror()
                        .addBox(-0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, CubeDeformation.NONE)
                        .mirror(false),
                PartPose.offsetAndRotation(3.25F, 3.5F, 0.75F, 1.3526F, 0.0F, 0.0873F));
        leftArm.addOrReplaceChild("left_weapon", CubeListBuilder.create(),
                PartPose.offset(1.0F, 8.0F, 0.0F));
        root.addOrReplaceChild("left_sleeve",
                CubeListBuilder.create().texOffs(48, 48)
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        var rightArm = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(40, 16)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        rightArm.addOrReplaceChild("right_armother_r1",
                CubeListBuilder.create().texOffs(106, 78).mirror()
                        .addBox(0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, CubeDeformation.NONE)
                        .mirror(false),
                PartPose.offsetAndRotation(-3.25F, 3.5F, 0.75F, 1.3526F, 0.0F, -0.0873F));
        rightArm.addOrReplaceChild("right_weapon", CubeListBuilder.create(),
                PartPose.offset(-1.0F, 8.0F, 0.0F));
        root.addOrReplaceChild("right_sleeve",
                CubeListBuilder.create().texOffs(40, 32)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
                PartPose.offset(-5.0F, 2.0F, 0.0F));

        root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));
        root.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.0F, 0.0F));
        root.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(SerponCombatExecuter entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);

        if (entity.isAggressive() && !entity.getMainHandItem().isEmpty()) {
            animateWalk(SerponCombatExecuterAnimation.ATTACKWALK, limbSwing, limbSwingAmount, 1.5F, 2.0F);
        } else {
            animateWalk(SerponCombatExecuterAnimation.WALK, limbSwing, limbSwingAmount, 1.5F, 2.0F);
        }

        animate(entity.idleAnimationState, SerponCombatExecuterAnimation.IDLE, ageInTicks);
        animate(entity.attack1AnimationState, SerponCombatExecuterAnimation.ATTACK1, ageInTicks);
        animate(entity.attack2AnimationState, SerponCombatExecuterAnimation.ATTACK2, ageInTicks);
        animate(entity.swordAttack1AnimationState, SerponCombatExecuterAnimation.SWORDATTACK1, ageInTicks);
        animate(entity.swordAttack2AnimationState, SerponCombatExecuterAnimation.SWORDATTACK2, ageInTicks);
        animate(entity.swordAttack3AnimationState, SerponCombatExecuterAnimation.SWORDATTACK3, ageInTicks);
        animate(entity.swordAttack4AnimationState, SerponCombatExecuterAnimation.SWORDATTACK4, ageInTicks);
        animate(entity.block1AnimationState, SerponCombatExecuterAnimation.BLOCK1, ageInTicks);
        animate(entity.block2AnimationState, SerponCombatExecuterAnimation.BLOCK2, ageInTicks);
        animate(entity.block3AnimationState, SerponCombatExecuterAnimation.BLOCK3, ageInTicks);
        animate(entity.flameAttackAnimationState, SerponCombatExecuterAnimation.FLAMEATTACK, ageInTicks);
        animate(entity.windAttackAnimationState, SerponCombatExecuterAnimation.WINDATTACK, ageInTicks);
        animate(entity.hotWindSpellAnimationState, SerponCombatExecuterAnimation.HOTWINDSPELL, ageInTicks);
        animate(entity.pikeSpellAnimationState, SerponCombatExecuterAnimation.PIKESPELL, ageInTicks);
        animate(entity.meteorSpellAnimationState, SerponCombatExecuterAnimation.METEORSPELL, ageInTicks);
        animate(entity.pushAnimationState, SerponCombatExecuterAnimation.PUSH, ageInTicks);
        animate(entity.deadAnimationState, SerponCombatExecuterAnimation.DEAD, ageInTicks);

        this.hat.copyFrom(this.head);
        this.jacket.copyFrom(this.body);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
        this.leftPants.copyFrom(this.leftLeg);
        this.rightPants.copyFrom(this.rightLeg);

        if (entity.isPrepare()) {
            this.leftWing.xScale = 1.2F;
            this.leftWing.yScale = 1.2F;
            this.leftWing.zScale = 1.2F;
            this.rightWing.xScale = 1.2F;
            this.rightWing.yScale = 1.2F;
            this.rightWing.zScale = 1.2F;
        }
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

    private static void applyAnimation(SerponCombatExecuterModel model,
            AnimationDefinition definition, long accumulatedTime, float scale) {
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
        boolean left = arm == HumanoidArm.LEFT;
        (left ? this.leftArm : this.rightArm).translateAndRotate(poseStack);
        (left ? this.leftWeapon : this.rightWeapon).translateAndRotate(poseStack);
    }
}
