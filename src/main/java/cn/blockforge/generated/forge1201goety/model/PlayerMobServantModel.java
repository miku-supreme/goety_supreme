package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.model.PlayerMobServantModel;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinLayers;
import cn.blockforge.generated.goetysupreme.model.SedimentServantModel;
import cn.blockforge.generated.goetysupreme.renderer.ServantSpearPose;
import cn.blockforge.generated.goetysupreme.entity.SkinProbePlayer;

public class PlayerMobServantModel extends PlayerModel<PlayerMobServant> {

    public static final float SKIN_LAYER_GROW = 1.0F;

    public static final ModelLayerLocation LAYER_3D = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "player_mob_servant_3d"), "main");

    public static final ModelLayerLocation LAYER_SLIM_3D = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "player_mob_servant_slim_3d"), "main");

    public static final ModelLayerLocation LAYER_PREVIEW = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "player_mob_servant_preview"), "main");

    public static final ModelLayerLocation LAYER_SLIM_PREVIEW = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "player_mob_servant_slim_preview"), "main");

    public static ModelLayerLocation previewLayer(boolean slim) {
        return slim ? LAYER_SLIM_PREVIEW : LAYER_PREVIEW;
    }

    public PlayerMobServantModel(ModelPart root, boolean slim) {
        super(root, slim);
    }

    public static ModelLayerLocation layer(boolean slim, boolean grownShell) {
        if (!grownShell) {
            return slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER;
        }
        return slim ? LAYER_SLIM_3D : LAYER_3D;
    }

    public static LayerDefinition create3DSkinLayer(boolean slim) {
        MeshDefinition mesh = PlayerModel.createMesh(CubeDeformation.NONE, slim);
        PartDefinition root = mesh.getRoot();
        CubeDeformation grow = new CubeDeformation(SKIN_LAYER_GROW);

        root.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, grow), PartPose.ZERO);
        root.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(16, 32)
                .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, grow), PartPose.ZERO);

        float sleeveWidth = slim ? 3.0F : 4.0F;
        float sleeveY = slim ? 2.5F : 2.0F;
        float rightSleeveX = slim ? -2.0F : -3.0F;
        root.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(48, 48)
                .addBox(-1.0F, -2.0F, -2.0F, sleeveWidth, 12.0F, 4.0F, grow),
                PartPose.offset(5.0F, sleeveY, 0.0F));
        root.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(40, 32)
                .addBox(rightSleeveX, -2.0F, -2.0F, sleeveWidth, 12.0F, 4.0F, grow),
                PartPose.offset(-5.0F, sleeveY, 0.0F));
        root.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 48)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, grow),
                PartPose.offset(1.9F, 12.0F, 0.0F));
        root.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 32)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, grow),
                PartPose.offset(-1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createPreviewLayer(boolean slim) {
        return LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE, slim), 64, 64);
    }

    @Override
    public void setupAnim(PlayerMobServant entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ServantSpearPose.apply(entity, this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.hat.copyFrom(this.head);
        this.jacket.copyFrom(this.body);
        this.leftSleeve.copyFrom(this.leftArm);
        this.rightSleeve.copyFrom(this.rightArm);
    }
}
