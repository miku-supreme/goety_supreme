package cn.blockforge.generated.goetysupreme.model;

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
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldierOtherLayer;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierOtherModel;

public class SerponColonialSoldierOtherModel<T extends AbstractSerpon>
        extends PlayerModel<T> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "serpon_colonial_soldier_other"), "main");

    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;

    public SerponColonialSoldierOtherModel(ModelPart root) {
        super(root, false);
        ModelPart body = root.getChild("body");
        ModelPart tail = body.getChild("tail");
        this.leftWing = body.getChild("left_wing");
        this.rightWing = body.getChild("right_wing");
        this.tail1 = tail.getChild("tail_1");
        this.tail2 = tail.getChild("tail_2");
        this.tail3 = tail.getChild("tail_3");
        this.tail4 = tail.getChild("tail_4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("ear", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("cloak", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("jacket", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(),
                PartPose.offset(0.0F, 6.0F, 0.0F));
        horn.addOrReplaceChild("left_horn_r1",
                CubeListBuilder.create().texOffs(0, -8).mirror()
                        .addBox(0.0F, -2.0F, -4.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                        .mirror(false),
                PartPose.offsetAndRotation(4.75F, -15.5F, 4.0F, 0.0F, 0.0F, 0.4363F));
        horn.addOrReplaceChild("right_horn_r1",
                CubeListBuilder.create().texOffs(0, -8)
                        .addBox(0.0F, -2.0F, -4.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.75F, -15.5F, 4.0F, 0.0F, 0.0F, -0.4363F));
        head.addOrReplaceChild("hair",
                CubeListBuilder.create().texOffs(16, -4)
                        .addBox(0.0F, -31.25F, 4.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(16, -4)
                        .addBox(0.0F, -27.25F, 3.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(),
                PartPose.offset(0.0F, -3.0F, -5.25F));
        mouth.addOrReplaceChild("mouth_out_r1",
                CubeListBuilder.create().texOffs(18, 21)
                        .addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.15F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1396F, 0.0F, 0.0F));
        mouth.addOrReplaceChild("mouth_r1",
                CubeListBuilder.create().texOffs(0, 21)
                        .addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0785F, 0.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition leftWing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.ZERO);
        leftWing.addOrReplaceChild("left_wing_r1",
                CubeListBuilder.create().texOffs(0, -6).mirror()
                        .addBox(0.1221F, -3.1272F, -1.5013F, 0.0F, 17.0F, 10.0F, new CubeDeformation(0.0F))
                        .mirror(false),
                PartPose.offsetAndRotation(2.5F, 1.5F, 2.0F, 0.2182F, 0.4363F, 0.0F));
        PartDefinition rightWing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.ZERO);
        rightWing.addOrReplaceChild("right_wing_r1",
                CubeListBuilder.create().texOffs(0, -6)
                        .addBox(-0.1221F, -3.1272F, -1.5013F, 0.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, 1.5F, 2.0F, 0.2182F, -0.4363F, 0.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(),
                PartPose.offset(0.0F, 30.0F, 0.0F));
        PartDefinition tail1 = tail.addOrReplaceChild("tail_1", CubeListBuilder.create(), PartPose.ZERO);
        tail1.addOrReplaceChild("tail_1_r1",
                CubeListBuilder.create().texOffs(34, 0)
                        .addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -9.5F, 2.5F, 1.2217F, 0.0F, 0.0F));
        PartDefinition tail2 = tail.addOrReplaceChild("tail_2", CubeListBuilder.create(), PartPose.ZERO);
        tail2.addOrReplaceChild("tail_2_r1",
                CubeListBuilder.create().texOffs(34, 10)
                        .addBox(-2.0F, -2.5F, -1.5F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -8.0F, 7.0F, 1.5272F, 0.0F, 0.0F));
        PartDefinition tail3 = tail.addOrReplaceChild("tail_3", CubeListBuilder.create(), PartPose.ZERO);
        tail3.addOrReplaceChild("tail_3_r1",
                CubeListBuilder.create().texOffs(48, 10)
                        .addBox(-1.0F, 11.0F, 6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.25F, -1.75F, 1.5708F, 0.0F, 0.0F));
        tail.addOrReplaceChild("tail_4",
                CubeListBuilder.create().texOffs(54, -5)
                        .addBox(0.0F, -8.0F, 13.5F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO);

        PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        leftArm.addOrReplaceChild("left_arm_r1",
                CubeListBuilder.create().texOffs(20, -3).mirror()
                        .addBox(-0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                        .mirror(false),
                PartPose.offsetAndRotation(3.3F, 3.5F, 0.75F, 1.3526F, 0.0F, 0.0873F));
        PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        rightArm.addOrReplaceChild("right_arm_r1",
                CubeListBuilder.create().texOffs(20, -3)
                        .addBox(0.05F, -2.0F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.3F, 3.5F, 0.75F, 1.3526F, 0.0F, -0.0873F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float sway = 0.4F * limbSwingAmount + 0.3F;
        if (!entity.onGround()) {
            this.leftWing.yRot = Mth.cos((ageInTicks + limbSwing) / 2.5F * 0.6662F + (float) Math.PI) * sway;
            this.rightWing.yRot = Mth.cos((ageInTicks + limbSwing) / 2.5F * 0.6662F) * sway;
        }
        this.tail1.x = 2.0F * Mth.cos(limbSwing * 0.6662F + 0.2F) * limbSwingAmount;
        this.tail2.x = 4.0F * Mth.cos(limbSwing * 0.6662F + 0.4F) * limbSwingAmount;
        this.tail3.x = 7.0F * Mth.cos(limbSwing * 0.6662F + 0.6F) * limbSwingAmount;
        this.tail4.x = 9.0F * Mth.cos(limbSwing * 0.6662F + 0.8F) * limbSwingAmount;
    }
}
