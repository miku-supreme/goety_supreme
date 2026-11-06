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
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;
import cn.blockforge.generated.goetysupreme.model.WarPythonBodyModel;
import cn.blockforge.generated.goetysupreme.entity.WarPythonPartEntity;

public class WarPythonBodyModel<T extends WarPythonPartEntity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "war_python_body"), "main");

    private final ModelPart root;

    public WarPythonBodyModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 45)
                        .addBox(-4.5F, -4.5F, -5.0F, 9.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 19.5F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.deathTime > 0) {
            float t = ServantDeathPose.progress(entity, ageInTicks);
            ModelPart body = this.root.getChild("body");
            body.y = 19.5F + 6.0F * t;
            body.xScale = 1.0F - 0.5F * t;
            body.yScale = 1.0F - 0.5F * t;
            body.zScale = 1.0F - 0.5F * t;
        }
    }
}
