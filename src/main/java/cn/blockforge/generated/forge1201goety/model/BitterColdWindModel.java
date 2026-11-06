package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
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
import net.minecraft.world.entity.HumanoidArm;
import cn.blockforge.generated.goetysupreme.model.BitterColdWindModel;
import cn.blockforge.generated.goetysupreme.entity.BitterColdWindServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;

public class BitterColdWindModel extends EntityModel<BitterColdWindServant>
        implements ArmedModel, HeadedModel {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "bitter_cold_wind"), "main");

    private final ModelPart top;
    private final ModelPart middle;
    private final ModelPart bottom;
    private final ModelPart eye;

    public BitterColdWindModel(ModelPart root) {
        this.top = root.getChild("top");
        this.middle = root.getChild("middle");
        this.bottom = root.getChild("bottom");
        this.eye = root.getChild("eye");
    }

    public static LayerDefinition createBodyLayer() {
    MeshDefinition mesh = new MeshDefinition();
    PartDefinition rootDef = mesh.getRoot();
    PartDefinition top = rootDef.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0f, -4.0f, -9.0f, 18.0f, 8.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(6, 6).addBox(-6.0f, -4.0f, -6.0f, 12.0f, 8.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(105, 57).addBox(-2.5f, -4.0f, -2.5f, 5.0f, 8.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)7.0f, (float)0.0f));
    PartDefinition middle = rootDef.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(74, 28).addBox(-6.0f, -3.0f, -6.0f, 12.0f, 6.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(78, 32).addBox(-4.0f, -3.0f, -4.0f, 8.0f, 6.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(49, 71).addBox(-2.5f, -3.0f, -2.5f, 5.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)14.0f, (float)0.0f));
    PartDefinition bottom = rootDef.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(1, 83).addBox(-2.5f, -3.5f, -2.5f, 5.0f, 7.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)20.5f, (float)0.0f));
    PartDefinition eye = rootDef.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(21, 123).addBox(-6.0f, -18.0f, -9.0f, 3.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 123).addBox(3.0f, -18.0f, -9.0f, 3.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset((float)0.0f, (float)24.0f, (float)0.0f));
    return LayerDefinition.create((MeshDefinition)mesh, (int)128, (int)128);
    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
            int packedOverlay, float red, float green, float blue, float alpha) {
        this.top.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.middle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.bottom.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.eye.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(BitterColdWindServant entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.deathTime > 0 && !entity.isAlive()) {
            float t = ServantDeathPose.progress(entity, ageInTicks);
            this.eye.yRot = 0.0F;
            this.eye.xRot = 0.0F;
            this.eye.y = 24.0F + 18.0F * t;
            this.top.x = 0.0F;
            this.top.z = 0.0F;
            this.top.yRot = 0.0F;
            this.top.y = 7.0F + 12.0F * t;
            this.top.xScale = 1.0F - 0.7F * t;
            this.top.yScale = 1.0F - 0.7F * t;
            this.top.zScale = 1.0F - 0.7F * t;
            this.middle.x = 0.0F;
            this.middle.z = 0.0F;
            this.middle.yRot = 0.0F;
            this.middle.y = 14.0F + 8.0F * t;
            this.middle.xScale = 1.0F - 0.7F * t;
            this.middle.yScale = 1.0F - 0.7F * t;
            this.middle.zScale = 1.0F - 0.7F * t;
            this.bottom.x = 0.0F;
            this.bottom.z = 0.0F;
            this.bottom.yRot = 0.0F;
            this.bottom.y = 20.5F + 4.0F * t;
            this.bottom.xScale = 1.0F - 0.7F * t;
            this.bottom.yScale = 1.0F - 0.7F * t;
            this.bottom.zScale = 1.0F - 0.7F * t;
            return;
        }
        this.eye.y = 24.0F;
        this.top.y = 7.0F;
        this.middle.y = 14.0F;
        this.bottom.y = 20.5F;
        this.top.xScale = 1.0F;
        this.top.yScale = 1.0F;
        this.top.zScale = 1.0F;
        this.middle.xScale = 1.0F;
        this.middle.yScale = 1.0F;
        this.middle.zScale = 1.0F;
        this.bottom.xScale = 1.0F;
        this.bottom.yScale = 1.0F;
        this.bottom.zScale = 1.0F;
        this.eye.yRot = netHeadYaw * 0.004F;
        this.eye.xRot = headPitch * 4.0E-4F;
        float spin = ageInTicks * (float) Math.PI * -0.1F;
        this.top.x = Mth.cos(spin) * 1.0F * 0.6F;
        this.top.z = Mth.sin(spin) * 1.0F * 0.6F;
        this.middle.x = Mth.sin(spin) * 0.5F * 0.8F;
        this.middle.z = Mth.cos(spin) * 0.8F;
        this.bottom.x = Mth.cos(spin) * -0.25F * 1.0F;
        this.bottom.z = Mth.sin(spin) * -0.25F * 1.0F;
        this.top.yRot = -ageInTicks * 0.3F;
        this.middle.yRot = -ageInTicks * 0.25F;
        this.bottom.yRot = -ageInTicks * 0.2F;
    }

    public ModelPart getArm(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? this.top : this.middle;
    }

    @Override
    public ModelPart getHead() {
        return this.eye;
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        this.getArm(arm).translateAndRotate(poseStack);
    }
}
