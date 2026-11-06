package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
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
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.ReaperScytheModel;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;

public class ReaperScytheModel extends HierarchicalModel<ReaperSpinEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "reaper_scythe"), "main");

    private final ModelPart root;

    public ReaperScytheModel(ModelPart root) {
        this.root = root;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition rootDef = mesh.getRoot();
        PartDefinition b0 = rootDef.addOrReplaceChild("镰刀", CubeListBuilder.create(), PartPose.offsetAndRotation(4.94614F, 11.78037F, -0.32502F, ((float)Math.toRadians(82.50000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        PartDefinition b1 = b0.addOrReplaceChild("handle", CubeListBuilder.create(), PartPose.offsetAndRotation(1.05386F, 6.83997F, 19.10770F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        PartDefinition b1_p0 = b1.addOrReplaceChild("b1_pivot0", CubeListBuilder.create(), PartPose.offsetAndRotation(-1F, -4F, -19.50000F, ((float)Math.toRadians(2.50000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b1_p0.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(65, 32).addBox(-0.50000F, -6.20000F, 0.20000F, 1F, 23F, 1F), PartPose.ZERO);
        PartDefinition b1_p1 = b1.addOrReplaceChild("b1_pivot1", CubeListBuilder.create(), PartPose.offsetAndRotation(-1F, -22F, -18.50000F, ((float)Math.toRadians(-2.50000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b1_p1.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(60, 39).addBox(-0.50000F, 6F, -0.50000F, 1F, 6F, 1F), PartPose.ZERO);
        PartDefinition b1_p2 = b1.addOrReplaceChild("b1_pivot2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1F, -10F, -7.50000F, ((float)Math.toRadians(-90.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b1_p2.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(70, 39).addBox(-0.50000F, 7F, -0.50000F, 1F, 5F, 1F), PartPose.ZERO);
        PartDefinition b2 = b0.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offsetAndRotation(1.05386F, 6.83997F, 19.10770F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b2.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(56, 47).addBox(0.50000F, -22F, -17F, 1F, 2F, 1F), PartPose.ZERO);
        b2.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(56, 47).addBox(-3.50000F, -22F, -21F, 1F, 2F, 1F), PartPose.ZERO);
        b2.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(56, 47).addBox(0.50000F, -22F, -21F, 1F, 2F, 1F), PartPose.ZERO);
        b2.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(56, 47).addBox(-3.50000F, -22F, -17F, 1F, 2F, 1F), PartPose.ZERO);
        b2.addOrReplaceChild("cube4", CubeListBuilder.create().texOffs(65, 51).addBox(-3.50000F, -17F, -21F, 5F, 1F, 5F), PartPose.ZERO);
        b2.addOrReplaceChild("cube5", CubeListBuilder.create().texOffs(65, 58).addBox(-3.50000F, -20.70000F, -21F, 5F, 4F, 5F, new CubeDeformation(-0.31250F, -0.25000F, -0.31250F)), PartPose.ZERO);
        PartDefinition b3 = b2.addOrReplaceChild("镰刀头", CubeListBuilder.create(), PartPose.offsetAndRotation(-1F, -13.36954F, -30.76186F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b3.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(80, 26).addBox(-0.50000F, -6.71429F, 6.92857F, 1F, 6F, 5F), PartPose.ZERO);
        b3.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(115, 26).addBox(-0.50000F, -5.71429F, 3.92857F, 1F, 6F, 3F), PartPose.ZERO);
        b3.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(105, 9).addBox(-0.50000F, -4.71429F, 0.92857F, 1F, 6F, 3F), PartPose.ZERO);
        b3.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(91, 14).addBox(-0.50000F, -2.71429F, -2.07143F, 1F, 5F, 3F), PartPose.ZERO);
        b3.addOrReplaceChild("cube4", CubeListBuilder.create().texOffs(100, 30).addBox(-0.50000F, -0.71429F, -5.07143F, 1F, 4F, 3F), PartPose.ZERO);
        b3.addOrReplaceChild("cube5", CubeListBuilder.create().texOffs(118, 46).addBox(-0.50000F, 1.28571F, -7.07143F, 1F, 3F, 2F), PartPose.ZERO);
        b3.addOrReplaceChild("cube6", CubeListBuilder.create().texOffs(106, 47).addBox(-0.50000F, 3.28571F, -8.07143F, 1F, 2F, 2F), PartPose.ZERO);
        PartDefinition b4 = b0.addOrReplaceChild("staffhead", CubeListBuilder.create(), PartPose.offsetAndRotation(1.05386F, 6.83997F, 19.10770F, ((float)Math.toRadians(-0.00000F)), ((float)Math.toRadians(0.00000F)), ((float)Math.toRadians(-0.00000F))));
        b4.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(48, 50).addBox(-2.50000F, -20F, -20F, 3F, 3F, 3F), PartPose.ZERO);
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(ReaperSpinEntity entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
    }
}
