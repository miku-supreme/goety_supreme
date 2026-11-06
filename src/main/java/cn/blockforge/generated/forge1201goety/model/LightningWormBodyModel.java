package cn.blockforge.generated.goetysupreme.model;

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
import cn.blockforge.generated.goetysupreme.model.LightningWormBodyModel;
import cn.blockforge.generated.goetysupreme.entity.LightningWormPartEntity;

public class LightningWormBodyModel<T extends LightningWormPartEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "lightning_worm_body"), "main");
    private final ModelPart root;

    public LightningWormBodyModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(26, 24).addBox(-4.5f, -4.0f, 5.0f, 9.0f, 9.0f, 10.0f, new CubeDeformation(0.0f))
                .texOffs(26, 24).addBox(-4.5f, -4.0f, 5.0f, 9.0f, 9.0f, 10.0f, new CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 19.0f, -10.0f));
        PartDefinition body_right_wing = body.addOrReplaceChild("body_right_wing", CubeListBuilder.create(),
                PartPose.offset(-1.5f, -4.0f, 7.0f));
        body_right_wing.addOrReplaceChild("right_wing_r3", CubeListBuilder.create()
                .texOffs(37, 7).addBox(-4.5f, 0.0f, -3.0f, 9.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.5f, 9.0f, 3.0f, 0.0f, 0.0f, -0.7854f));
        body_right_wing.addOrReplaceChild("right_wing_r4", CubeListBuilder.create()
                .texOffs(37, 7).addBox(-4.5f, 0.0f, -3.0f, 9.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.5f, 0.0f, 3.0f, 0.0f, 0.0f, 0.7854f));
        PartDefinition body_left_wing = body.addOrReplaceChild("body_left_wing", CubeListBuilder.create(),
                PartPose.offset(1.5f, -4.0f, 7.0f));
        body_left_wing.addOrReplaceChild("left_wing_r3", CubeListBuilder.create()
                .texOffs(37, 7).mirror().addBox(-4.5f, 0.0f, -3.0f, 9.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).mirror(false),
                PartPose.offsetAndRotation(4.5f, 9.0f, 3.0f, 0.0f, 0.0f, 0.7854f));
        body_left_wing.addOrReplaceChild("left_wing_r4", CubeListBuilder.create()
                .texOffs(37, 7).mirror().addBox(-4.5f, 0.0f, -3.0f, 9.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).mirror(false),
                PartPose.offsetAndRotation(4.5f, 0.0f, 3.0f, 0.0f, 0.0f, -0.7854f));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
