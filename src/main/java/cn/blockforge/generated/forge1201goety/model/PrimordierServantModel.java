package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.HierarchicalModel;
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
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.entity.PrimordierServant;
import cn.blockforge.generated.goetysupreme.renderer.PrimordierServantAnimation;
import cn.blockforge.generated.goetysupreme.model.PrimordierServantModel;

public class PrimordierServantModel extends HierarchicalModel<PrimordierServant> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "primordier_servant"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;

    public PrimordierServantModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, 6.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -33.8138F, -28.5811F));
        neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 53).addBox(-10.0F, -9.0F, -9.0F, 20.0F, 18.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -5.0F, 0.3054F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(139, 177).addBox(-10.5F, -11.6485F, -16.6908F, 21.0F, 15.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(181, 177).addBox(-10.5F, 1.3515F, -16.6908F, 21.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(139, 212).addBox(-10.5F, -11.6485F, -19.6908F, 21.0F, 15.0F, 23.0F, new CubeDeformation(0.5F)).texOffs(48, 21).addBox(10.5F, -9.6485F, -8.6908F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(48, 21).addBox(-15.5F, -9.6485F, -8.6908F, 5.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 41).addBox(-10.5F, 3.2515F, -2.7908F, 21.0F, 6.0F, 6.0F, new CubeDeformation(-0.05F)), PartPose.offset(0.0F, 8.8138F, -8.9189F));
        head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 21).addBox(-10.5F, -2.5F, -2.5F, 21.0F, 17.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.1485F, -17.1908F));
        PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create(), PartPose.offset(-7.47F, -11.8537F, -4.5547F));
        right_horn.addOrReplaceChild("right_horn_r1", CubeListBuilder.create().texOffs(70, 0).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(70, 12).addBox(2.0F, -6.0F, -4.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -14.3973F, 6.0573F, -0.3927F, 0.0F, 0.0F));
        right_horn.addOrReplaceChild("right_horn_r2", CubeListBuilder.create().texOffs(94, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -7.0063F, 2.9958F, -0.3927F, 0.0F, 0.0F));
        right_horn.addOrReplaceChild("right_horn_r3", CubeListBuilder.create().texOffs(94, 12).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 17.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.3927F, 0.0F, 0.0F));
        PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create(), PartPose.offset(7.47F, -11.8537F, -4.5547F));
        left_horn.addOrReplaceChild("left_horn_r1", CubeListBuilder.create().texOffs(70, 0).mirror().addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(70, 12).mirror().addBox(-6.0F, -6.0F, -4.0F, 4.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, -14.3973F, 6.0573F, -0.3927F, 0.0F, 0.0F));
        left_horn.addOrReplaceChild("left_horn_r2", CubeListBuilder.create().texOffs(94, 0).mirror().addBox(-4.0F, -2.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -7.0063F, 2.9958F, -0.3927F, 0.0F, 0.0F));
        left_horn.addOrReplaceChild("left_horn_r3", CubeListBuilder.create().texOffs(94, 12).mirror().addBox(-2.0F, -5.0F, -2.0F, 4.0F, 17.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.3927F, 0.0F, 0.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, 2.3515F, -13.6908F, 21.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(40, 39).addBox(-10.5F, 4.3515F, -13.6908F, 21.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));
        PartDefinition body1 = body.addOrReplaceChild("body1", CubeListBuilder.create(), PartPose.offset(-1.5F, -12.0F, -10.0F));
        body1.addOrReplaceChild("body1_r1", CubeListBuilder.create().texOffs(139, 64).addBox(-25.5F, -13.5F, -22.0F, 51.0F, 27.0F, 37.0F, new CubeDeformation(2.25F)).texOffs(139, 0).addBox(-25.5F, -13.5F, -22.0F, 51.0F, 27.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -9.5114F, -5.2722F, -1.0036F, 0.0F, 0.0F));
        PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, -2.8558F, -7.2881F));
        body2.addOrReplaceChild("body2_r1", CubeListBuilder.create().texOffs(139, 128).addBox(-16.5F, -12.0F, -10.5F, 33.0F, 28.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.3986F, 1.9074F, -1.2654F, 0.0F, 0.0F));
        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-25.5F, -30.0F, -21.0F));
        right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(336, 0).addBox(-6.0F, -9.0F, -7.5F, 12.0F, 18.0F, 15.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-3.3817F, 2.5654F, 0.2014F, 0.2132F, -0.0469F, 0.475F));
        right_arm.addOrReplaceChild("right_arm_r2", CubeListBuilder.create().texOffs(336, 33).addBox(-7.0F, -7.0F, -7.5F, 13.0F, 32.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8978F, 8.2235F, 1.5F, 0.2182F, 0.0F, 0.2618F));
        right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(336, 80).addBox(-7.2F, -1.7F, -10.3F, 14.0F, 27.0F, 17.0F, new CubeDeformation(0.5F)).texOffs(336, 124).addBox(-7.2F, 25.3F, -10.3F, 14.0F, 7.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 30.5F, 8.5F));
        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(25.5F, -30.0F, -21.0F));
        left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(336, 0).mirror().addBox(-6.0F, -9.0F, -7.5F, 12.0F, 18.0F, 15.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(3.3817F, 2.5654F, 0.2014F, 0.2132F, 0.0469F, -0.475F));
        left_arm.addOrReplaceChild("left_arm_r2", CubeListBuilder.create().texOffs(336, 33).mirror().addBox(-6.0F, -7.0F, -7.5F, 13.0F, 32.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.8978F, 8.2235F, 1.5F, 0.2182F, 0.0F, -0.2618F));
        left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(336, 80).mirror().addBox(-4.2F, -1.7F, -10.3F, 14.0F, 27.0F, 17.0F, new CubeDeformation(0.5F)).mirror(false).texOffs(336, 124).mirror().addBox(-4.2F, 25.3F, -10.3F, 14.0F, 7.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 30.5F, 8.5F));
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-13.5F, -9.0F, 1.0F));
        right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(410, 0).addBox(-7.5F, -13.5F, -7.0F, 15.0F, 27.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 4.861F, 1.7424F, -0.2618F, 0.0F, 0.0F));
        right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(410, 41).addBox(-7.0F, -2.5F, -6.0F, 14.0F, 21.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 14.7F, -1.0F));
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(13.5F, -9.0F, 1.0F));
        left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(410, 0).mirror().addBox(-7.5F, -13.5F, -7.0F, 15.0F, 27.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 4.8099F, 1.3542F, -0.2618F, 0.0F, 0.0F));
        left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(410, 41).mirror().addBox(-7.0F, -2.5F, -6.0F, 14.0F, 21.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 14.7F, -1.0F));
        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(PrimordierServant entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = Mth.clamp(headPitch, -30.0F, 30.0F) * ((float) Math.PI / 180.0F);
        this.head.yRot = Mth.clamp(netHeadYaw, -25.0F, 25.0F) * ((float) Math.PI / 180.0F);
        if (entity.specialAction()) {
            this.animateWalk(PrimordierServantAnimation.WALK, limbSwing, limbSwingAmount, 1.5F, 2.0F);
        } else if (entity.isAggressive()) {
            float walk = entity.getAttackWalk() / 40.0F * 0.8F;
            this.animateWalk(PrimordierServantAnimation.RUSHARM, limbSwing,
                    limbSwingAmount * (1.0F - walk + 0.2F), 1.5F, 2.0F);
            this.animateWalk(PrimordierServantAnimation.RUSH, limbSwing,
                    limbSwingAmount * (0.2F + (1.0F - walk + 0.2F) * 0.8F), 1.5F, 2.0F);
        } else {
            this.animateWalk(PrimordierServantAnimation.WALK, limbSwing, limbSwingAmount, 1.5F, 2.0F);
        }
        this.animate(entity.attack1AnimationState, PrimordierServantAnimation.ATTACK1, ageInTicks);
        this.animate(entity.attack2AnimationState, PrimordierServantAnimation.ATTACK2, ageInTicks);
        this.animate(entity.attack3AnimationState, PrimordierServantAnimation.ATTACK3, ageInTicks);
        this.animate(entity.attack4AnimationState, PrimordierServantAnimation.ATTACK4, ageInTicks);
        this.animate(entity.attack5AnimationState, PrimordierServantAnimation.ATTACK5, ageInTicks);
        this.animate(entity.attack6AnimationState, PrimordierServantAnimation.ATTACK6, ageInTicks);
        this.animate(entity.attack7AnimationState, PrimordierServantAnimation.ATTACK7, ageInTicks);
        this.animate(entity.attack8AnimationState, PrimordierServantAnimation.ATTACK8, ageInTicks);
        this.animate(entity.idleAnimationState, PrimordierServantAnimation.IDLE, ageInTicks);
        net.minecraft.client.animation.KeyframeAnimations.animate(this, PrimordierServantAnimation.JUMP, 0L,
                entity.isAlive() ? entity.getJumpAnim() / 20.0F : 0.0F, new Vector3f());
        this.animate(entity.shockAnimationState, PrimordierServantAnimation.SHOCK, ageInTicks);
        this.animate(entity.deadAnimationState, PrimordierServantAnimation.DEAD, ageInTicks);
    }
}
