package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.MutantSnowGolemShoulderArmorModel;

public final class MutantSnowGolemShoulderArmorModel extends Model {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "mutant_snow_golem_shoulder_armor"), "main");
    private final ModelPart root;

    public MutantSnowGolemShoulderArmorModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        float tilt = (float) Math.toRadians(15.0);
        root.addOrReplaceChild("left_plate", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-3.5F, -1.0F, -4.0F, 7.0F, 2.0F, 8.0F),
                PartPose.offsetAndRotation(-11.5F, -12.0F, 0.0F, 0.0F, 0.0F, -tilt));
        root.addOrReplaceChild("right_plate", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-3.5F, -1.0F, -4.0F, 7.0F, 2.0F, 8.0F),
                PartPose.offsetAndRotation(11.5F, -12.0F, 0.0F, 0.0F, 0.0F, tilt));
        return LayerDefinition.create(mesh, 32, 16);
    }

    public void render(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay) {
        root.render(poseStack, consumer, packedLight, packedOverlay);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
            int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
