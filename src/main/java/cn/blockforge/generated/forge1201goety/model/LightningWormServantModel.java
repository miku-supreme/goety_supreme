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
import cn.blockforge.generated.goetysupreme.entity.LightningWormServant;
import cn.blockforge.generated.goetysupreme.model.LightningWormServantModel;

public class LightningWormServantModel<T extends LightningWormServant> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "lightning_worm_servant"), "main");
    private final ModelPart root;

    public LightningWormServantModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.5f, -4.0f, -5.0f, 9.0f, 9.0f, 10.0f, new CubeDeformation(0.0f))
                .texOffs(0, 32).addBox(-4.5f, -4.0f, -7.0f, 9.0f, 9.0f, 2.0f, new CubeDeformation(0.0f))
                .texOffs(0, 19).addBox(-5.5f, -5.0f, -7.0f, 11.0f, 11.0f, 2.0f, new CubeDeformation(0.0f))
                .texOffs(0, 43).addBox(-5.5f, -5.0f, -5.25f, 11.0f, 11.0f, 5.0f, new CubeDeformation(-0.25f)),
                PartPose.offset(0.0f, 19.0f, 0.0f));
        PartDefinition right_wing = head.addOrReplaceChild("right_wing", CubeListBuilder.create(),
                PartPose.offset(-1.5f, -4.0f, -3.0f));
        right_wing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create()
                .texOffs(37, 0).addBox(-4.5f, 0.0f, -3.0f, 9.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.5f, 9.0f, 3.0f, 0.0f, 0.0f, -0.7854f));
        right_wing.addOrReplaceChild("right_wing_r2", CubeListBuilder.create()
                .texOffs(37, 0).addBox(-4.5f, 0.0f, -3.0f, 9.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.5f, 0.0f, 3.0f, 0.0f, 0.0f, 0.7854f));
        PartDefinition left_wing = head.addOrReplaceChild("left_wing", CubeListBuilder.create(),
                PartPose.offset(1.5f, -4.0f, -3.0f));
        left_wing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create()
                .texOffs(37, 0).mirror().addBox(-4.5f, 0.0f, -3.0f, 9.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).mirror(false),
                PartPose.offsetAndRotation(4.5f, 9.0f, 3.0f, 0.0f, 0.0f, 0.7854f));
        left_wing.addOrReplaceChild("left_wing_r2", CubeListBuilder.create()
                .texOffs(37, 0).mirror().addBox(-4.5f, 0.0f, -3.0f, 9.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)).mirror(false),
                PartPose.offsetAndRotation(4.5f, 0.0f, 3.0f, 0.0f, 0.0f, -0.7854f));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.root.getChild("head").yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.root.getChild("head").xRot = headPitch * ((float) Math.PI / 180.0F);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
