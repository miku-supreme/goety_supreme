package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
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
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.model.WitherServantModel;

public final class WitherServantModel extends HierarchicalModel<WitherServant> {

    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "wither_servant"), "main");

    private final ModelPart root;
    private final ModelPart shoulders;
    private final ModelPart centerHead;
    private final ModelPart rightHead;
    private final ModelPart leftHead;
    private final ModelPart ribcage;
    private final ModelPart tail;

    public WitherServantModel(ModelPart root) {
        this.root = root;
        this.shoulders = SafeModelParts.find(root, "shoulders");
        this.ribcage = SafeModelParts.find(root, "ribcage");
        this.tail = SafeModelParts.find(root, "tail");
        this.centerHead = SafeModelParts.find(root, "center_head");
        this.rightHead = SafeModelParts.find(root, "right_head");
        this.leftHead = SafeModelParts.find(root, "left_head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partdefinition = mesh.getRoot();
        partdefinition.addOrReplaceChild("shoulders",
                CubeListBuilder.create().texOffs(0, 16).addBox(-10.0F, 3.9F, -0.5F, 20.0F, 3.0F, 3.0F,
                        CubeDeformation.NONE),
                PartPose.ZERO);
        partdefinition.addOrReplaceChild("ribcage",
                CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F,
                        CubeDeformation.NONE)
                        .texOffs(24, 22)
                        .addBox(-4.0F, 1.5F, 0.5F, 11.0F, 2.0F, 2.0F, CubeDeformation.NONE).texOffs(24, 22)
                        .addBox(-4.0F, 4.0F, 0.5F, 11.0F, 2.0F, 2.0F, CubeDeformation.NONE).texOffs(24, 22)
                        .addBox(-4.0F, 6.5F, 0.5F, 11.0F, 2.0F, 2.0F, CubeDeformation.NONE),
                PartPose.offsetAndRotation(-2.0F, 6.9F, -0.5F, 0.20420352F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(12, 22).addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F,
                        CubeDeformation.NONE),
                PartPose.offsetAndRotation(-2.0F, 6.9F + Mth.cos(0.20420352F) * 10.0F,
                        -0.5F + Mth.sin(0.20420352F) * 10.0F, 0.83252203F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("center_head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F,
                        CubeDeformation.NONE),
                PartPose.ZERO);
        CubeListBuilder sideHead = CubeListBuilder.create().texOffs(32, 0)
                .addBox(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, CubeDeformation.NONE);
        partdefinition.addOrReplaceChild("right_head", sideHead,
                PartPose.offset(-8.0F, 4.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_head", sideHead,
                PartPose.offset(10.0F, 4.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    public ModelPart shouldersPart() {
        return shoulders;
    }

    public void applyRootTransform(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
    }

    @Override
    public void prepareMobModel(WitherServant servant, float limbSwing, float limbSwingAmount, float partialTick) {
        setupHeadRotation(servant, rightHead, 0);
        setupHeadRotation(servant, leftHead, 1);
    }

    private static void setupHeadRotation(WitherServant servant, ModelPart part, int head) {
        part.yRot = (servant.getHeadYRot(head) - servant.yBodyRot) * Mth.DEG_TO_RAD;
        part.xRot = servant.getHeadXRot(head) * Mth.DEG_TO_RAD;
    }

    @Override
    public void setupAnim(WitherServant servant, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        centerHead.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        centerHead.xRot = headPitch * Mth.DEG_TO_RAD;
        float wave = Mth.cos(ageInTicks * 0.1F);
        ribcage.xRot = (0.065F + 0.05F * wave) * Mth.PI;
        tail.setPos(-2.0F, 6.9F + Mth.cos(ribcage.xRot) * 10.0F, -0.5F + Mth.sin(ribcage.xRot) * 10.0F);
        tail.xRot = (0.265F + 0.1F * wave) * Mth.PI;
    }
}
