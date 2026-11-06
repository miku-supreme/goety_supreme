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
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballServant;
import cn.blockforge.generated.goetysupreme.model.FlyingFireballServantModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.ServantDeathPose;

public final class FlyingFireballServantModel<T extends FlyingFireballServant>
        extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "flying_fireball_servant"), "main");

    public static final ResourceLocation OUT_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/flying_fireball_out.png");

    public static final ResourceLocation FIRE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotesvillage", "textures/entity/flying_fireball_fire.png");

    private static final int FIRE_PLANES = 16;

    private final ModelPart root;
    private final ModelPart main;

    public FlyingFireballServantModel(ModelPart root) {
        this.root = root;
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create(),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        main.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-12.0F, -18.0F, -4.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 32)
                        .addBox(-12.0F, -18.0F, -4.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.5F)),
                PartPose.offset(4.0F, 3.0F, -4.0F));
        main.addOrReplaceChild("out",
                CubeListBuilder.create().texOffs(64, 0)
                        .addBox(-12.0F, -18.0F, -4.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.55F)),
                PartPose.offset(4.0F, 3.0F, -4.0F));
        main.addOrReplaceChild("fire_right", firePlanes(false),
                PartPose.offsetAndRotation(-4.0F, -7.0F, 16.25F, -1.5708F, 0.0F, 0.0F));
        main.addOrReplaceChild("fire_left", firePlanes(true),
                PartPose.offsetAndRotation(4.0F, -7.0F, 16.25F, -1.5708F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 640, 64);
    }

    private static CubeListBuilder firePlanes(boolean mirror) {
        CubeListBuilder builder = CubeListBuilder.create();
        for (int i = 0; i < FIRE_PLANES; i++) {
            builder.texOffs(608 - i * 32, -16);
            if (mirror) {
                builder.mirror();
            }
            builder.addBox(0.0F, 6.75F - i, -8.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F));
            if (mirror) {
                builder.mirror(false);
            }
        }
        return builder;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (entity.deathTime > 0 && !entity.isAlive()) {
            float t = ServantDeathPose.progress(entity, ageInTicks);
            this.main.xScale = 1.0F - 0.85F * t;
            this.main.yScale = 1.0F - 0.85F * t;
            this.main.zScale = 1.0F - 0.85F * t;
            this.main.y = 23.0F + 4.0F * t;
            this.main.yRot = 2.5F * t;
            return;
        }
        this.main.xScale = 1.0F;
        this.main.yScale = 1.0F;
        this.main.zScale = 1.0F;
        this.main.y = 23.0F;
        this.main.yRot = netHeadYaw / 57.295776F;
        this.main.xRot = headPitch / 57.295776F;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
