package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.AgeableHierarchicalModel;
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
import cn.blockforge.generated.goetysupreme.model.WarPythonModel;
import cn.blockforge.generated.goetysupreme.entity.WarPythonPartEntity;
import cn.blockforge.generated.goetysupreme.entity.WarPythonServant;

public class WarPythonModel<T extends WarPythonServant> extends AgeableHierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "war_python"), "main");

    private final ModelPart root;

    public WarPythonModel(ModelPart root) {
        super(0.5F, 24.0F);
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-5.0F, -4.5F, -6.0F, 10.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 19.5F, -1.0F));
        head.addOrReplaceChild("mouth",
                CubeListBuilder.create()
                        .texOffs(0, 30)
                        .addBox(-4.0F, -1.0F, -7.25F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.001F))
                        .texOffs(0, 21)
                        .addBox(-4.0F, -4.75F, -7.25F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 1.5F, -3.75F));
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
            ModelPart head = this.root.getChild("head");
            ModelPart mouth = head.getChild("mouth");
            head.y = 19.5F + 6.0F * t;
            head.xRot = 0.9F * t;
            mouth.xRot = -0.8F * t;
        }
    }
}
