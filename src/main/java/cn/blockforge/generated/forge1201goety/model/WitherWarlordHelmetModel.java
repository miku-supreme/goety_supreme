package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordHelmetModel;

public final class WitherWarlordHelmetModel extends HumanoidModel<LivingEntity> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "wither_warlord_helmet"), "main");

    public WitherWarlordHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayer(new CubeDeformation(0.5F));
    }

    public static LayerDefinition createOuterLayer() {
        return createBodyLayer(new CubeDeformation(0.5F));
    }

    public static LayerDefinition createInnerLayer() {
        return createBodyLayer(new CubeDeformation(0.0F));
    }

    public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
        MeshDefinition mesh = HumanoidModel.createMesh(deformation, 0.0F);
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition left_arm = root.addOrReplaceChild("left_arm", CubeListBuilder.create(),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(),
                PartPose.offset(1.9F, 12.0F, 0.0F));
        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(),
                PartPose.offset(-1.9F, 12.0F, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        head.addOrReplaceChild("cube_0", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.5f)), PartPose.ZERO);
        PartDefinition pivot_0 = head.addOrReplaceChild("pivot_0", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0f, -5.0f, 0.0f, 0.0f, ((float) Math.toRadians(45.0)), 0.0f));
        pivot_0.addOrReplaceChild("cube_1", CubeListBuilder.create().texOffs(32, 0).addBox(0.0f, -2.0f, 0.0f, 4.0f, 2.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.ZERO);
        head.addOrReplaceChild("cube_2", CubeListBuilder.create().texOffs(46, 0).addBox(5.0f, -7.0f, -4.0f, 2.0f, 2.0f, 3.0f, new CubeDeformation(-0.1f)), PartPose.ZERO);
        head.addOrReplaceChild("cube_3", CubeListBuilder.create().texOffs(0, 16).addBox(5.0f, -7.0f, -6.0f, 2.0f, 2.0f, 3.0f, new CubeDeformation(-0.2f)), PartPose.ZERO);
        PartDefinition pivot_1 = head.addOrReplaceChild("pivot_1", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0f, -6.0f, -6.0f, 0.0f, 0.0f, ((float) Math.toRadians(22.5))));
        pivot_1.addOrReplaceChild("cube_4", CubeListBuilder.create().texOffs(10, 16).addBox(0.0f, -2.0f, 0.0f, 3.0f, 2.0f, 2.0f, new CubeDeformation(-0.3f)), PartPose.ZERO);
        PartDefinition pivot_2 = head.addOrReplaceChild("pivot_2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0f, -8.85355f, -6.0f, 0.0f, 0.0f, ((float) Math.toRadians(45.0))));
        pivot_2.addOrReplaceChild("cube_5", CubeListBuilder.create().texOffs(20, 16).addBox(0.35355f, -1.64645f, 0.0f, 4.0f, 2.0f, 2.0f, new CubeDeformation(-0.5f)), PartPose.ZERO);
        head.addOrReplaceChild("cube_6", CubeListBuilder.create().texOffs(32, 16).addBox(1.2f, -12.0f, -6.0f, 2.0f, 4.0f, 2.0f, new CubeDeformation(-0.6f)), PartPose.ZERO);
        PartDefinition pivot_3 = head.addOrReplaceChild("pivot_3", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0f, -5.0f, 0.0f, 0.0f, ((float) Math.toRadians(-45.0)), 0.0f));
        pivot_3.addOrReplaceChild("cube_7", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0f, -2.0f, 0.0f, 4.0f, 2.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.ZERO);
        head.addOrReplaceChild("cube_8", CubeListBuilder.create().texOffs(54, 16).addBox(-7.0f, -7.0f, -4.0f, 2.0f, 2.0f, 3.0f, new CubeDeformation(-0.1f)), PartPose.ZERO);
        head.addOrReplaceChild("cube_9", CubeListBuilder.create().texOffs(0, 22).addBox(-7.0f, -7.0f, -6.0f, 2.0f, 2.0f, 3.0f, new CubeDeformation(-0.2f)), PartPose.ZERO);
        PartDefinition pivot_4 = head.addOrReplaceChild("pivot_4", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0f, -6.0f, -6.0f, 0.0f, 0.0f, ((float) Math.toRadians(-22.5))));
        pivot_4.addOrReplaceChild("cube_10", CubeListBuilder.create().texOffs(10, 22).addBox(-3.0f, -2.0f, 0.0f, 3.0f, 2.0f, 2.0f, new CubeDeformation(-0.3f)), PartPose.ZERO);
        PartDefinition pivot_5 = head.addOrReplaceChild("pivot_5", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0f, -8.85355f, -6.0f, 0.0f, 0.0f, ((float) Math.toRadians(-45.0))));
        pivot_5.addOrReplaceChild("cube_11", CubeListBuilder.create().texOffs(20, 22).addBox(-4.35355f, -1.64645f, 0.0f, 4.0f, 2.0f, 2.0f, new CubeDeformation(-0.5f)), PartPose.ZERO);
        head.addOrReplaceChild("cube_12", CubeListBuilder.create().texOffs(32, 22).addBox(-3.2f, -12.0f, -6.0f, 2.0f, 4.0f, 2.0f, new CubeDeformation(-0.6f)), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }
}
