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
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.SeaWaveServant;
import cn.blockforge.generated.goetysupreme.renderer.SeaWaveServantAnimation;
import cn.blockforge.generated.goetysupreme.model.SeaWaveServantModel;

public final class SeaWaveServantModel<T extends SeaWaveServant> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "seawave_servant"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart body_1;
    private final ModelPart body_2;
    private final ModelPart body_3;
    private final ModelPart trident;
    private final ModelPart trident1;
    private final ModelPart trident1_in;
    private final ModelPart trident_three;
    private final ModelPart trident3;
    private final ModelPart trident3_in;
    private final ModelPart trident_two;
    private final ModelPart trident2;
    private final ModelPart trident2_in;
    private final ModelPart trident_one;
    private final ModelPart trident4;
    private final ModelPart trident4_in;

    public SeaWaveServantModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.body_1 = this.body.getChild("body_1");
        this.body_2 = this.body.getChild("body_2");
        this.body_3 = this.body.getChild("body_3");
        this.trident = root.getChild("trident");
        this.trident1 = this.trident.getChild("trident1");
        this.trident1_in = this.trident1.getChild("trident1_in");
        this.trident_three = this.trident.getChild("trident_three");
        this.trident3 = this.trident_three.getChild("trident3");
        this.trident3_in = this.trident3.getChild("trident3_in");
        this.trident_two = this.trident_three.getChild("trident_two");
        this.trident2 = this.trident_two.getChild("trident2");
        this.trident2_in = this.trident2.getChild("trident2_in");
        this.trident_one = this.trident_two.getChild("trident_one");
        this.trident4 = this.trident_one.getChild("trident4");
        this.trident4_in = this.trident4.getChild("trident4_in");
    }

    public static LayerDefinition createBodyLayer() {
MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(32, 47).addBox(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new CubeDeformation(0.5f)).texOffs(0, 54).addBox(-6.0f, -9.0f, 1.0f, 12.0f, 9.0f, 1.0f, new CubeDeformation(0.5f)), PartPose.offset(0.0f, 3.0f, 0.0f));
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 21.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(16, 16).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.45f)), PartPose.offset(0.0f, 3.0f, 0.0f));
        PartDefinition body_1 = body.addOrReplaceChild("body_1", CubeListBuilder.create().texOffs(-13, 41).addBox(-6.5f, 0.0f, -6.5f, 13.0f, 0.0f, 13.0f, new CubeDeformation(0.0f)).texOffs(16, 35).addBox(-2.5f, -0.5f, -2.5f, 5.0f, 1.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 4.0f, 0.0f));
        PartDefinition body_2 = body.addOrReplaceChild("body_2", CubeListBuilder.create().texOffs(-13, 41).addBox(-6.5f, 0.0f, -6.5f, 13.0f, 0.0f, 13.0f, new CubeDeformation(0.0f)).texOffs(16, 35).addBox(-2.5f, -0.5f, -2.5f, 5.0f, 1.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 6.0f, 0.0f));
        PartDefinition body_3 = body.addOrReplaceChild("body_3", CubeListBuilder.create().texOffs(-13, 41).addBox(-6.5f, 0.0f, -6.5f, 13.0f, 0.0f, 13.0f, new CubeDeformation(0.0f)).texOffs(16, 35).addBox(-2.5f, -0.5f, -2.5f, 5.0f, 1.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 8.0f, 0.0f));
        PartDefinition trident = partdefinition.addOrReplaceChild("trident", CubeListBuilder.create(), PartPose.offset(0.0f, 8.0f, 0.0f));
        PartDefinition trident1 = trident.addOrReplaceChild("trident1", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, -9.0f));
        PartDefinition trident1_in = trident1.addOrReplaceChild("trident1_in", CubeListBuilder.create().texOffs(38, 12).mirror().addBox(1.5f, -10.2544f, -0.5f, 1.0f, 3.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(32, 0).addBox(-1.0f, -17.2544f, -0.5f, 2.0f, 32.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(44, 3).addBox(-2.0f, -6.2544f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 0.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 13.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 7.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(38, 0).addBox(-3.5f, -12.2544f, -0.5f, 7.0f, 2.0f, 1.0f, new CubeDeformation(0.05f)).texOffs(38, 12).addBox(-2.5f, -10.2544f, -0.5f, 1.0f, 3.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 1.2544f, 0.0f));
        PartDefinition stick_r1 = trident1_in.addOrReplaceChild("stick_r1", CubeListBuilder.create().texOffs(44, 9).mirror().addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)).mirror(false), PartPose.offsetAndRotation(4.6986f, -16.0441f, 0.0f, 0.0f, 0.0f, 2.7925f));
        PartDefinition stick_r2 = trident1_in.addOrReplaceChild("stick_r2", CubeListBuilder.create().texOffs(38, 3).mirror().addBox(-1.5f, -4.5f, -0.5f, 2.0f, 5.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(3.25f, -11.7544f, 0.0f, 0.0f, 0.0f, 0.4363f));
        PartDefinition stick_r3 = trident1_in.addOrReplaceChild("stick_r3", CubeListBuilder.create().texOffs(44, 9).addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)), PartPose.offsetAndRotation(-4.6986f, -16.0441f, 0.0f, 0.0f, 0.0f, -2.7925f));
        PartDefinition stick_r4 = trident1_in.addOrReplaceChild("stick_r4", CubeListBuilder.create().texOffs(38, 3).addBox(-0.5f, -4.5f, -0.5f, 2.0f, 5.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.25f, -11.7544f, 0.0f, 0.0f, 0.0f, -0.4363f));
        PartDefinition stick_r5 = trident1_in.addOrReplaceChild("stick_r5", CubeListBuilder.create().texOffs(38, 9).addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)), PartPose.offsetAndRotation(0.0f, -17.2544f, 0.0f, 0.0f, 0.0f, 0.7854f));
        PartDefinition trident_three = trident.addOrReplaceChild("trident_three", CubeListBuilder.create(), PartPose.offset(0.0f, 16.0f, 0.0f));
        PartDefinition trident3 = trident_three.addOrReplaceChild("trident3", CubeListBuilder.create(), PartPose.offset(0.0f, -16.0f, 9.0f));
        PartDefinition trident3_in = trident3.addOrReplaceChild("trident3_in", CubeListBuilder.create().texOffs(38, 12).mirror().addBox(1.5f, -10.2544f, -0.5f, 1.0f, 3.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(32, 0).addBox(-1.0f, -17.2544f, -0.5f, 2.0f, 32.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(38, 0).addBox(-3.5f, -12.2544f, -0.5f, 7.0f, 2.0f, 1.0f, new CubeDeformation(0.05f)).texOffs(38, 12).addBox(-2.5f, -10.2544f, -0.5f, 1.0f, 3.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(44, 3).addBox(-2.0f, -6.2544f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 0.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 13.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 7.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)), PartPose.offsetAndRotation(0.0f, 1.2544f, 0.0f, 0.0f, 3.1416f, 0.0f));
        PartDefinition stick_r6 = trident3_in.addOrReplaceChild("stick_r6", CubeListBuilder.create().texOffs(44, 9).addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)), PartPose.offsetAndRotation(-4.6986f, -16.0441f, 0.0f, 0.0f, 0.0f, -2.7925f));
        PartDefinition stick_r7 = trident3_in.addOrReplaceChild("stick_r7", CubeListBuilder.create().texOffs(44, 9).mirror().addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)).mirror(false), PartPose.offsetAndRotation(4.6986f, -16.0441f, 0.0f, 0.0f, 0.0f, 2.7925f));
        PartDefinition stick_r8 = trident3_in.addOrReplaceChild("stick_r8", CubeListBuilder.create().texOffs(38, 3).mirror().addBox(-1.5f, -4.5f, -0.5f, 2.0f, 5.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(3.25f, -11.7544f, 0.0f, 0.0f, 0.0f, 0.4363f));
        PartDefinition stick_r9 = trident3_in.addOrReplaceChild("stick_r9", CubeListBuilder.create().texOffs(38, 3).addBox(-0.5f, -4.5f, -0.5f, 2.0f, 5.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.25f, -11.7544f, 0.0f, 0.0f, 0.0f, -0.4363f));
        PartDefinition stick_r10 = trident3_in.addOrReplaceChild("stick_r10", CubeListBuilder.create().texOffs(38, 9).addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)), PartPose.offsetAndRotation(0.0f, -17.2544f, 0.0f, 0.0f, 0.0f, 0.7854f));
        PartDefinition trident_two = trident_three.addOrReplaceChild("trident_two", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition trident2 = trident_two.addOrReplaceChild("trident2", CubeListBuilder.create(), PartPose.offset(9.0f, -16.0f, 0.0f));
        PartDefinition trident2_in = trident2.addOrReplaceChild("trident2_in", CubeListBuilder.create().texOffs(38, 12).mirror().addBox(1.5f, -10.2544f, -0.5f, 1.0f, 3.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(32, 0).addBox(-1.0f, -17.2544f, -0.5f, 2.0f, 32.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(44, 3).addBox(-2.0f, -6.2544f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 0.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 13.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 7.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(38, 0).addBox(-3.5f, -12.2544f, -0.5f, 7.0f, 2.0f, 1.0f, new CubeDeformation(0.05f)).texOffs(38, 12).addBox(-2.5f, -10.2544f, -0.5f, 1.0f, 3.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 1.2544f, 0.0f, 0.0f, -1.5708f, 0.0f));
        PartDefinition stick_r11 = trident2_in.addOrReplaceChild("stick_r11", CubeListBuilder.create().texOffs(44, 9).mirror().addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)).mirror(false), PartPose.offsetAndRotation(4.6986f, -16.0441f, 0.0f, 0.0f, 0.0f, 2.7925f));
        PartDefinition stick_r12 = trident2_in.addOrReplaceChild("stick_r12", CubeListBuilder.create().texOffs(38, 3).mirror().addBox(-1.5f, -4.5f, -0.5f, 2.0f, 5.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(3.25f, -11.7544f, 0.0f, 0.0f, 0.0f, 0.4363f));
        PartDefinition stick_r13 = trident2_in.addOrReplaceChild("stick_r13", CubeListBuilder.create().texOffs(44, 9).addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)), PartPose.offsetAndRotation(-4.6986f, -16.0441f, 0.0f, 0.0f, 0.0f, -2.7925f));
        PartDefinition stick_r14 = trident2_in.addOrReplaceChild("stick_r14", CubeListBuilder.create().texOffs(38, 3).addBox(-0.5f, -4.5f, -0.5f, 2.0f, 5.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.25f, -11.7544f, 0.0f, 0.0f, 0.0f, -0.4363f));
        PartDefinition stick_r15 = trident2_in.addOrReplaceChild("stick_r15", CubeListBuilder.create().texOffs(38, 9).addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)), PartPose.offsetAndRotation(0.0f, -17.2544f, 0.0f, 0.0f, 0.0f, 0.7854f));
        PartDefinition trident_one = trident_two.addOrReplaceChild("trident_one", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition trident4 = trident_one.addOrReplaceChild("trident4", CubeListBuilder.create(), PartPose.offset(-9.0f, -16.0f, 0.0f));
        PartDefinition trident4_in = trident4.addOrReplaceChild("trident4_in", CubeListBuilder.create().texOffs(38, 12).mirror().addBox(1.5f, -10.2544f, -0.5f, 1.0f, 3.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(32, 0).addBox(-1.0f, -17.2544f, -0.5f, 2.0f, 32.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(38, 0).addBox(-3.5f, -12.2544f, -0.5f, 7.0f, 2.0f, 1.0f, new CubeDeformation(0.05f)).texOffs(38, 12).addBox(-2.5f, -10.2544f, -0.5f, 1.0f, 3.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(44, 3).addBox(-2.0f, -6.2544f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 0.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 13.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)).texOffs(44, 3).addBox(-2.0f, 7.7456f, -0.5f, 4.0f, 1.0f, 1.0f, new CubeDeformation(0.1f)), PartPose.offsetAndRotation(0.0f, 1.2544f, 0.0f, 0.0f, 1.5708f, 0.0f));
        PartDefinition stick_r16 = trident4_in.addOrReplaceChild("stick_r16", CubeListBuilder.create().texOffs(44, 9).addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)), PartPose.offsetAndRotation(-4.6986f, -16.0441f, 0.0f, 0.0f, 0.0f, -2.7925f));
        PartDefinition stick_r17 = trident4_in.addOrReplaceChild("stick_r17", CubeListBuilder.create().texOffs(44, 9).mirror().addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)).mirror(false), PartPose.offsetAndRotation(4.6986f, -16.0441f, 0.0f, 0.0f, 0.0f, 2.7925f));
        PartDefinition stick_r18 = trident4_in.addOrReplaceChild("stick_r18", CubeListBuilder.create().texOffs(38, 3).mirror().addBox(-1.5f, -4.5f, -0.5f, 2.0f, 5.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(3.25f, -11.7544f, 0.0f, 0.0f, 0.0f, 0.4363f));
        PartDefinition stick_r19 = trident4_in.addOrReplaceChild("stick_r19", CubeListBuilder.create().texOffs(38, 3).addBox(-0.5f, -4.5f, -0.5f, 2.0f, 5.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.25f, -11.7544f, 0.0f, 0.0f, 0.0f, -0.4363f));
        PartDefinition stick_r20 = trident4_in.addOrReplaceChild("stick_r20", CubeListBuilder.create().texOffs(38, 9).addBox(-1.0f, -1.0f, -0.5f, 2.0f, 2.0f, 1.0f, new CubeDeformation(-0.05f)), PartPose.offsetAndRotation(0.0f, -17.2544f, 0.0f, 0.0f, 0.0f, 0.7854f));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.trident1.visible = entity.getTridentCount() >= 4;
        this.trident3.visible = entity.getTridentCount() >= 3;
        this.trident2.visible = entity.getTridentCount() >= 2;
        this.trident4.visible = entity.getTridentCount() >= 1;
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
        this.animateWalk(SeaWaveServantAnimation.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);
        if (entity.isAggressive()) {
            if (entity.getTridentCount() >= 4) {
                this.animateIdle2(entity, entity.idle1AnimationState, SeaWaveServantAnimation.IDLE2, ageInTicks);
            } else if (entity.getTridentCount() >= 3) {
                this.animateIdle2(entity, entity.idle1AnimationState, SeaWaveServantAnimation.IDLE3, ageInTicks);
            } else if (entity.getTridentCount() >= 2) {
                this.animateIdle2(entity, entity.idle1AnimationState, SeaWaveServantAnimation.IDLE4, ageInTicks);
            } else if (entity.getTridentCount() >= 1) {
                this.animateIdle2(entity, entity.idle1AnimationState, SeaWaveServantAnimation.IDLE5, ageInTicks);
            } else {
                this.animateIdle2(entity, entity.idle1AnimationState, SeaWaveServantAnimation.IDLE5, ageInTicks);
            }
        } else {
            this.animate(entity.idle1AnimationState, SeaWaveServantAnimation.IDLE1, ageInTicks);
        }
        KeyframeAnimations.animate(this, SeaWaveServantAnimation.IDLEBASE, 0L,
                Math.min(1.0F, entity.idleAnimProgress / 10.0F), new Vector3f());
        this.animate(entity.shootAnimationState, SeaWaveServantAnimation.SHOOT, ageInTicks);
        this.animate(entity.windAnimationState, SeaWaveServantAnimation.WIND, ageInTicks);
        this.animate(entity.attack1AnimationState, SeaWaveServantAnimation.ATTACK1, ageInTicks);
        this.animate(entity.attack2AnimationState, SeaWaveServantAnimation.ATTACK2, ageInTicks);
        this.animate(entity.bulgeAnimationState, SeaWaveServantAnimation.BULGE, ageInTicks);
        this.animate(entity.deadAnimationState, SeaWaveServantAnimation.DEAD, ageInTicks);
    }

    private void animateIdle2(T seawave, AnimationState animationState,
            net.minecraft.client.animation.AnimationDefinition animation, float ageInTicks) {
        animationState.updateTime(ageInTicks, 1.0F);
        animationState.ifStarted(state -> KeyframeAnimations.animate(this, animation,
                (long) (Math.min(1.0F, seawave.rollAnimProgress % 10.0F / 10.0F)
                        * animation.lengthInSeconds() * 1000.0F),
                Math.min(1.0F, seawave.idleAnimProgress / 10.0F), new Vector3f()));
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
