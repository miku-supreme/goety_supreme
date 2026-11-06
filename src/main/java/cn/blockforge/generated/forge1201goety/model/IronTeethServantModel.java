package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
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
import cn.blockforge.generated.goetysupreme.entity.IronTeethServant;
import cn.blockforge.generated.goetysupreme.model.IronTeethServantModel;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;

public final class IronTeethServantModel<T extends IronTeethServant> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "iron_teeth_servant"), "main");

    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart body;
    private final ModelPart body2;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public IronTeethServantModel(ModelPart root) {
        this.head = root.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.body = root.getChild("body");
        this.body2 = root.getChild("body2");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -10.0F, -11.0F, 8.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 4.0F));
        head.addOrReplaceChild("neck_r1",
                CubeListBuilder.create().texOffs(26, 0)
                        .addBox(-2.0F, -5.0F, -4.5F, 4.0F, 6.0F, 3.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, -2.0F, 2.5F, 0.3927F, 0.0F, 0.0F));
        head.addOrReplaceChild("jaw",
                CubeListBuilder.create().texOffs(0, 15)
                        .addBox(-4.0F, -1.0F, -9.0F, 8.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -4.0F, -2.0F));

        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(52, 1)
                        .addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offset(0.0F, 6.0F, 0.0F));
        body.addOrReplaceChild("body_r1",
                CubeListBuilder.create().texOffs(40, 1)
                        .addBox(-2.0F, -3.0F, 1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition body2 = root.addOrReplaceChild("body2",
                CubeListBuilder.create().texOffs(52, 20)
                        .addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.4F)),
                PartPose.offset(0.0F, 6.0F, 0.0F));
        body2.addOrReplaceChild("body_r2",
                CubeListBuilder.create().texOffs(40, 20)
                        .addBox(-2.0F, -3.0F, 1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        root.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(36, 9).mirror()
                        .addBox(-0.9F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(44, 9).mirror()
                        .addBox(-0.9F, 8.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
                        .texOffs(52, 9).mirror()
                        .addBox(-0.9F, 10.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false),
                PartPose.offset(1.9F, 12.0F, 0.0F));

        root.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(36, 9)
                        .addBox(-1.1F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(44, 9)
                        .addBox(-1.1F, 8.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
                        .texOffs(52, 9).mirror()
                        .addBox(-1.1F, 10.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false),
                PartPose.offset(-1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (entity.deathTime > 0) {
            float t = ServantDeathPose.progress(entity, ageInTicks);
            this.head.xScale = 1.0F;
            this.head.yScale = 1.0F;
            this.head.xRot = 1.2F * t;
            this.jaw.xRot = -0.7F * t;
            this.body.xRot = 0.6F * t;
            this.body2.xRot = 0.4F * t;
            this.body.y = 6.0F + 1.5F * t;
            this.leftLeg.xRot -= 0.5F * t;
            this.rightLeg.xRot += 0.5F * t;
            return;
        }
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);
        float swing = 0.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * swing;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * swing;

        int attackTick = entity.getAttackTick();
        if (attackTick > 7) {
            this.head.xRot = 1.5F * Mth.triangleWave(attackTick, 15.0F);
            this.jaw.xRot = -2.0F * Mth.triangleWave(attackTick, 15.0F);
            this.head.xScale = 1.5F;
            this.head.yScale = 2.0F;
        } else {
            this.head.xScale = 1.0F;
            this.head.yScale = 1.0F;
            this.head.xRot = headPitch * 4.0E-4F;
            this.jaw.xRot = 0.0F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight,
            int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.body2.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
