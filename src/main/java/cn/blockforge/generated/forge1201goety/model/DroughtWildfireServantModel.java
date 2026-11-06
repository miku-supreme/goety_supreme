package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.animation.KeyframeAnimations;
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
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.entity.DroughtWildfireServant;
import cn.blockforge.generated.goetysupreme.renderer.DroughtWildfireServantAnimation;
import cn.blockforge.generated.goetysupreme.model.DroughtWildfireServantModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public final class DroughtWildfireServantModel<T extends DroughtWildfireServant>
        extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "drought_wildfire_servant"),
                    "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart body_1;
    private final ModelPart body_2;
    private final ModelPart body_3;
    private final ModelPart shield1;
    private final ModelPart shield1_in;
    private final ModelPart shield_three;
    private final ModelPart shield3;
    private final ModelPart shield3_in;
    private final ModelPart shield_two;
    private final ModelPart shield2;
    private final ModelPart shield2_in;
    private final ModelPart shield_one;
    private final ModelPart shield4;
    private final ModelPart shield4_in;

    public DroughtWildfireServantModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.body_1 = this.body.getChild("body_1");
        this.body_2 = this.body.getChild("body_2");
        this.body_3 = this.body.getChild("body_3");
        ModelPart shield = root.getChild("shield");
        this.shield1 = shield.getChild("shield1");
        this.shield1_in = this.shield1.getChild("shield1_in");
        this.shield_three = shield.getChild("shield_three");
        this.shield3 = this.shield_three.getChild("shield3");
        this.shield3_in = this.shield3.getChild("shield3_in");
        this.shield_two = this.shield_three.getChild("shield_two");
        this.shield2 = this.shield_two.getChild("shield2");
        this.shield2_in = this.shield2.getChild("shield2_in");
        this.shield_one = this.shield_two.getChild("shield_one");
        this.shield4 = this.shield_one.getChild("shield4");
        this.shield4_in = this.shield4.getChild("shield4_in");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(32, 47).addBox(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new CubeDeformation(0.5f)).texOffs(0, 55).addBox(-8.0f, -12.0f, 1.0f, 16.0f, 9.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 3.0f, 0.0f));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 21.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(16, 16).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.45f)), PartPose.offset(0.0f, 3.0f, 0.0f));
        PartDefinition body_1 = body.addOrReplaceChild("body_1", CubeListBuilder.create().texOffs(16, 35).addBox(-2.5f, -0.5f, -2.5f, 5.0f, 1.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 4.0f, 0.0f));
        PartDefinition body_2 = body.addOrReplaceChild("body_2", CubeListBuilder.create().texOffs(0, 41).addBox(-2.5f, -4.0f, -2.5f, 5.0f, 8.0f, 5.0f, new CubeDeformation(0.5f)).texOffs(16, 35).addBox(-2.5f, -0.5f, -2.5f, 5.0f, 1.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 6.0f, 0.0f));
        PartDefinition body_3 = body.addOrReplaceChild("body_3", CubeListBuilder.create().texOffs(16, 35).addBox(-2.5f, -0.5f, -2.5f, 5.0f, 1.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 8.0f, 0.0f));
        PartDefinition shield = partdefinition.addOrReplaceChild("shield", CubeListBuilder.create(), PartPose.offset(0.0f, 8.0f, 0.0f));
        PartDefinition shield1 = shield.addOrReplaceChild("shield1", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, -9.0f));
        PartDefinition shield1_in = shield1.addOrReplaceChild("shield1_in", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0f, -7.2544f, -0.5f, 12.0f, 18.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(32, 20).addBox(-6.0f, -7.2544f, -0.5f, 12.0f, 18.0f, 2.0f, new CubeDeformation(0.25f)), PartPose.offset(0.0f, 1.2544f, 0.0f));
        PartDefinition shield_three = shield.addOrReplaceChild("shield_three", CubeListBuilder.create(), PartPose.offset(0.0f, 16.0f, 0.0f));
        PartDefinition shield3 = shield_three.addOrReplaceChild("shield3", CubeListBuilder.create(), PartPose.offset(0.0f, -16.0f, 9.0f));
        PartDefinition shield3_in = shield3.addOrReplaceChild("shield3_in", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0f, -7.2544f, -0.5f, 12.0f, 18.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(32, 20).addBox(-6.0f, -7.2544f, -0.5f, 12.0f, 18.0f, 2.0f, new CubeDeformation(0.25f)), PartPose.offsetAndRotation(0.0f, 1.2544f, 0.0f, 0.0f, 3.1416f, 0.0f));
        PartDefinition shield_two = shield_three.addOrReplaceChild("shield_two", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition shield2 = shield_two.addOrReplaceChild("shield2", CubeListBuilder.create(), PartPose.offset(9.0f, -16.0f, 0.0f));
        PartDefinition shield2_in = shield2.addOrReplaceChild("shield2_in", CubeListBuilder.create().texOffs(32, 20).addBox(-6.0f, -7.2544f, -0.5f, 12.0f, 18.0f, 2.0f, new CubeDeformation(0.25f)).texOffs(32, 0).addBox(-6.0f, -7.2544f, -0.5f, 12.0f, 18.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 1.2544f, 0.0f, 0.0f, -1.5708f, 0.0f));
        PartDefinition shield_one = shield_two.addOrReplaceChild("shield_one", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition shield4 = shield_one.addOrReplaceChild("shield4", CubeListBuilder.create(), PartPose.offset(-9.0f, -16.0f, 0.0f));
        PartDefinition shield4_in = shield4.addOrReplaceChild("shield4_in", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0f, -7.2544f, -0.5f, 12.0f, 18.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(32, 20).addBox(-6.0f, -7.2544f, -0.5f, 12.0f, 18.0f, 2.0f, new CubeDeformation(0.25f)), PartPose.offsetAndRotation(0.0f, 1.2544f, 0.0f, 0.0f, 1.5708f, 0.0f));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.shield1.visible = entity.getShieldCount() >= 4;
        this.shield3.visible = entity.getShieldCount() >= 3;
        this.shield2.visible = entity.getShieldCount() >= 2;
        this.shield4.visible = entity.getShieldCount() >= 1;
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
        this.animateWalk(DroughtWildfireServantAnimation.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);
        if (entity.isAggressive()) {
            if (entity.getShieldCount() >= 4) {
                this.animateIdle2(entity, entity.idle1AnimationState,
                        DroughtWildfireServantAnimation.IDLE2, ageInTicks);
            } else if (entity.getShieldCount() >= 3) {
                this.animateIdle2(entity, entity.idle1AnimationState,
                        DroughtWildfireServantAnimation.IDLE3, ageInTicks);
            } else if (entity.getShieldCount() >= 2) {
                this.animateIdle2(entity, entity.idle1AnimationState,
                        DroughtWildfireServantAnimation.IDLE4, ageInTicks);
            } else {
                this.animateIdle2(entity, entity.idle1AnimationState,
                        DroughtWildfireServantAnimation.IDLE5, ageInTicks);
            }
        } else {
            this.animate(entity.idle1AnimationState, DroughtWildfireServantAnimation.IDLE1, ageInTicks);
        }
        KeyframeAnimations.animate(this, DroughtWildfireServantAnimation.IDLEBASE, 0L,
                Math.min(1.0F, entity.idleAnimProgress / 10.0F), new Vector3f());
        this.animate(entity.shootAnimationState, DroughtWildfireServantAnimation.SHOOT, ageInTicks);
        this.animate(entity.flameAnimationState, DroughtWildfireServantAnimation.WIND, ageInTicks);
        this.animate(entity.droughtBlazeAnimationState, DroughtWildfireServantAnimation.BLAZE, ageInTicks);
        this.animate(entity.attack1AnimationState, DroughtWildfireServantAnimation.ATTACK1, ageInTicks);
        this.animate(entity.attack2AnimationState, DroughtWildfireServantAnimation.ATTACK2, ageInTicks);
        this.animate(entity.deadAnimationState, DroughtWildfireServantAnimation.DEAD, ageInTicks);
    }

    private void animateIdle2(T wildfire, AnimationState animationState,
            net.minecraft.client.animation.AnimationDefinition animation, float ageInTicks) {
        animationState.updateTime(ageInTicks, 1.0F);
        animationState.ifStarted(state -> KeyframeAnimations.animate(this, animation,
                (long) (Math.min(1.0F, wildfire.rollAnimProgress % 10.0F / 10.0F)
                        * animation.lengthInSeconds() * 1000.0F),
                Math.min(1.0F, wildfire.idleAnimProgress / 10.0F), new Vector3f()));
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
