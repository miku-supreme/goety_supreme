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
import cn.blockforge.generated.goetysupreme.model.DroughtFireballModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ScrollUvVertexConsumer;

public class DroughtFireballModel<T extends net.minecraft.world.entity.Entity>
        extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "drought_fireball"), "main");

    private final ModelPart root;
    private final ModelPart main;

    public DroughtFireballModel(ModelPart root) {
        this.root = root;
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create(),
                PartPose.offset(0.0F, 22.0F, 0.0F));
        main.addOrReplaceChild("in", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition out = main.addOrReplaceChild("out", CubeListBuilder.create(),
                PartPose.rotation(1.5708F, 0.0F, 0.0F));
        out.addOrReplaceChild("out_r1", CubeListBuilder.create()
                        .texOffs(0, 22)
                        .addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(-0.255F)),
                PartPose.rotation(0.0F, 0.0F, 1.5708F));
        out.addOrReplaceChild("out_r2", CubeListBuilder.create()
                        .texOffs(0, 9)
                        .addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(-0.25F)),
                PartPose.rotation(-1.5708F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        this.main.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.main.xRot = headPitch * ((float) Math.PI / 180.0F);
    }
}
