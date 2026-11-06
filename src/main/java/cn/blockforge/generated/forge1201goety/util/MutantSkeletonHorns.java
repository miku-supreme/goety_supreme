package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import cn.blockforge.generated.goetysupreme.util.MutantSkeletonHorns;

public final class MutantSkeletonHorns {
    private MutantSkeletonHorns() {
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition left_hinge = root.addOrReplaceChild("left_hinge",
                CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -4.0F, -1.8F, -0.1745329F, 0.0F, 0.0872665F));
        left_hinge.addOrReplaceChild("left_root", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3141593F, 0.0F, 0.1745329F));
        left_hinge.addOrReplaceChild("left_spine", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.75F, -5.0F, -0.75F, 1.5F, 5.0F, 1.5F),
                PartPose.offsetAndRotation(0.3F, -3.2F, 1.6F, -0.3490659F, 0.0F, 0.1745329F));
        left_hinge.addOrReplaceChild("left_tip", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.7F, -6.7F, 3.2F, -0.2792527F, 0.0F, 0.1745329F));
        left_hinge.addOrReplaceChild("left_lower_vane", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.75F, -4.0F, -0.7F, 1.5F, 4.0F, 1.4F),
                PartPose.offsetAndRotation(0.15F, -1.8F, 1.4F, -0.418879F, 0.0F, 0.1745329F));
        left_hinge.addOrReplaceChild("left_middle_vane", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.6F, -4.0F, -0.65F, 1.2F, 4.0F, 1.3F),
                PartPose.offsetAndRotation(0.45F, -4.2F, 2.8F, -0.418879F, 0.0F, 0.1745329F));
        left_hinge.addOrReplaceChild("left_upper_vane", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.7F, -6.0F, 3.6F, -0.3490659F, 0.0F, 0.1745329F));
        PartDefinition right_hinge = root.addOrReplaceChild("right_hinge",
                CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, -4.0F, -1.8F, -0.1745329F, 0.0F, -0.0872665F));
        right_hinge.addOrReplaceChild("right_root", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3141593F, 0.0F, -0.1745329F));
        right_hinge.addOrReplaceChild("right_spine", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.75F, -5.0F, -0.75F, 1.5F, 5.0F, 1.5F),
                PartPose.offsetAndRotation(-0.3F, -3.2F, 1.6F, -0.3490659F, 0.0F, -0.1745329F));
        right_hinge.addOrReplaceChild("right_tip", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-0.7F, -6.7F, 3.2F, -0.2792527F, 0.0F, -0.1745329F));
        right_hinge.addOrReplaceChild("right_lower_vane", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.75F, -4.0F, -0.7F, 1.5F, 4.0F, 1.4F),
                PartPose.offsetAndRotation(-0.15F, -1.8F, 1.4F, -0.418879F, 0.0F, -0.1745329F));
        right_hinge.addOrReplaceChild("right_middle_vane", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.6F, -4.0F, -0.65F, 1.2F, 4.0F, 1.3F),
                PartPose.offsetAndRotation(-0.45F, -4.2F, 2.8F, -0.418879F, 0.0F, -0.1745329F));
        right_hinge.addOrReplaceChild("right_upper_vane", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(-0.7F, -6.0F, 3.6F, -0.3490659F, 0.0F, -0.1745329F));
        return LayerDefinition.create(mesh, 128, 128);
    }
}
