package cn.blockforge.generated.goetysupreme.model;

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
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.SerponJavelinEntity;
import cn.blockforge.generated.goetysupreme.model.SerponJavelinModel;

public class SerponJavelinModel extends EntityModel<SerponJavelinEntity> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "serpon_javelin"), "main");

    private final ModelPart body;

    public SerponJavelinModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-0.5F, -11.0F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 10.0F, 0.0F));
        body.addOrReplaceChild("head_r1",
                CubeListBuilder.create().texOffs(4, 4)
                        .addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -12.25F, 0.0F, 0.0F, 0.0F, -0.7854F));
        body.addOrReplaceChild("rod_r1",
                CubeListBuilder.create().texOffs(20, 0)
                        .addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(3.5429F, -9.9571F, 0.0F, 0.0F, 0.0F, -0.7854F));
        body.addOrReplaceChild("rod_r2",
                CubeListBuilder.create().texOffs(12, 4)
                        .addBox(-1.5F, -1.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -9.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        body.addOrReplaceChild("rod_r3",
                CubeListBuilder.create().texOffs(20, 0)
                        .addBox(-0.5F, -1.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(-3.5429F, -9.9571F, 0.0F, 0.0F, 0.0F, 0.7854F));
        body.addOrReplaceChild("rod_r4",
                CubeListBuilder.create().texOffs(12, 0)
                        .addBox(0.5F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.5F, -9.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        body.addOrReplaceChild("rod_r5",
                CubeListBuilder.create().texOffs(4, 0)
                        .addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, -9.5F, 0.0F, 0.0F, 0.0F, -0.7854F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(SerponJavelinEntity entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(com.mojang.blaze3d.vertex.PoseStack pose, com.mojang.blaze3d.vertex.VertexConsumer buffer,
            int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.body.render(pose, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
