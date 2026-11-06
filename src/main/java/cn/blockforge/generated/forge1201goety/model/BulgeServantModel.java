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
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.renderer.BulgeServantAnimation;
import cn.blockforge.generated.goetysupreme.model.BulgeServantModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;

public final class BulgeServantModel<T extends BulgeServant> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "bulge_servant"), "main");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body_top;
    private final ModelPart body_bottom;

    public BulgeServantModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body_top = root.getChild("body_top");
        this.body_bottom = root.getChild("body_bottom");
    }

    public static LayerDefinition createBodyLayer() {
MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 5.0f, 0.0f));
        PartDefinition body_top = partdefinition.addOrReplaceChild("body_top", CubeListBuilder.create(), PartPose.offset(0.0f, 8.0f, 0.0f));
        PartDefinition stick1 = body_top.addOrReplaceChild("stick1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(-6.0f, 0.0f, -6.0f));
        PartDefinition stick2 = body_top.addOrReplaceChild("stick2", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(6.0f, 0.0f, -6.0f));
        PartDefinition stick3 = body_top.addOrReplaceChild("stick3", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(6.0f, 0.0f, 6.0f));
        PartDefinition stick4 = body_top.addOrReplaceChild("stick4", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(-6.0f, 0.0f, 6.0f));
        PartDefinition body_bottom = partdefinition.addOrReplaceChild("body_bottom", CubeListBuilder.create(), PartPose.offset(0.0f, 14.0f, 0.0f));
        PartDefinition stick5 = body_bottom.addOrReplaceChild("stick5", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(-3.0f, 0.0f, -3.0f));
        PartDefinition stick6 = body_bottom.addOrReplaceChild("stick6", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(3.0f, 0.0f, -3.0f));
        PartDefinition stick7 = body_bottom.addOrReplaceChild("stick7", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(3.0f, 0.0f, 3.0f));
        PartDefinition stick8 = body_bottom.addOrReplaceChild("stick8", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0f, -1.0f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(-3.0f, 0.0f, 3.0f));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (entity.deathTime > 0) {
            float t = ServantDeathPose.progress(entity, ageInTicks);
            this.head.y -= 6.5F * t;
            this.head.xScale = 1.0F - 0.55F * t;
            this.head.yScale = 1.0F - 0.55F * t;
            this.head.zScale = 1.0F - 0.55F * t;
            this.body_top.y -= 3.5F * t;
            this.body_bottom.y -= 1.5F * t;
            flattenSticks(this.body_top, t);
            flattenSticks(this.body_bottom, t);
            return;
        }
        this.head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.head.xRot = headPitch * Mth.DEG_TO_RAD;
        this.animateWalk(BulgeServantAnimation.WALK, limbSwing, limbSwingAmount, 2.0F, 2.0F);
        this.animate(entity.idleAnimationState, entity.isAggressive()
                ? BulgeServantAnimation.ATTACKIDLE : BulgeServantAnimation.IDLE, ageInTicks);
        this.animate(entity.shootAnimationState, BulgeServantAnimation.SHOOT, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    private static void flattenSticks(ModelPart parent, float t) {
        for (ModelPart stick : parent.getAllParts().toList()) {
            if (stick == parent) {
                continue;
            }
            stick.xRot += 0.5F * t;
            stick.zRot += 0.35F * t;
            stick.xScale = 1.0F - 0.35F * t;
            stick.zScale = 1.0F - 0.35F * t;
        }
    }
}
