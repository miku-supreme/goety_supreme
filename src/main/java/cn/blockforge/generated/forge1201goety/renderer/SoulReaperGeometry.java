package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperGeometry;

public final class SoulReaperGeometry {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "soul_reaper_geometry"), "main");

    private SoulReaperGeometry() {
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition bone0 = root.addOrReplaceChild("skeleton", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, 24F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition bone1 = bone0.addOrReplaceChild("上半身", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, -12F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition bone2 = bone1.addOrReplaceChild("头", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, -12F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube3 = bone2.addOrReplaceChild("cube3",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4F, -8F, -4F, 8F, 8F, 8F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone4 = bone2.addOrReplaceChild("hat", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, 0F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube5 = bone4.addOrReplaceChild("cube5",
                CubeListBuilder.create().texOffs(38, 0).addBox(-4F, -11.15F, -4F, 8F, 10F, 8F, new CubeDeformation(0.5F)),
                PartPose.ZERO);
        PartDefinition bone6 = bone4.addOrReplaceChild("horns", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, 24F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube7 = bone6.addOrReplaceChild("cube7",
                CubeListBuilder.create().texOffs(24, 92).addBox(-9F, -38.2F, 0.2F, 18F, 8F, 0F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone8 = bone2.addOrReplaceChild("头2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, -4F, 2.33333F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube9 = bone8.addOrReplaceChild("cube9",
                CubeListBuilder.create().texOffs(27, 112).addBox(-4F, -4F, -6.33333F, 8F, 8F, 8F, new CubeDeformation(0.5F)),
                PartPose.ZERO);
        PartDefinition cube10 = bone8.addOrReplaceChild("cube10",
                CubeListBuilder.create().texOffs(104, 116).addBox(-4F, -4F, 1.66667F, 8F, 8F, 4F, new CubeDeformation(0.5F)),
                PartPose.ZERO);
        PartDefinition cube11 = bone8.addOrReplaceChild("cube11",
                CubeListBuilder.create().texOffs(96, 82).addBox(-4F, -4F, -6.33333F, 8F, 8F, 8F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube12 = bone8.addOrReplaceChild("cube12",
                CubeListBuilder.create().texOffs(96, 99).addBox(-4F, -4F, -6.33333F, 8F, 8F, 8F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone13 = bone1.addOrReplaceChild("除了头", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, -12F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition bone14 = bone13.addOrReplaceChild("右手", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-5F, 2F, 0F, ((float) Math.toRadians(-80F)), ((float) Math.toRadians(15F)), ((float) Math.toRadians(0F))));
        PartDefinition bone15 = bone14.addOrReplaceChild("right_pauldron", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, 0F, 1F, ((float) Math.toRadians(60F)), ((float) Math.toRadians(-5F)), ((float) Math.toRadians(-15F))));
        PartDefinition cube16 = bone15.addOrReplaceChild("cube16",
                CubeListBuilder.create().texOffs(0, 48).addBox(-4F, -4F, -3F, 5F, 5F, 6F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone17 = bone14.addOrReplaceChild("右手2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, 0F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(-2.5F)), ((float) Math.toRadians(0F))));
        PartDefinition cube18 = bone17.addOrReplaceChild("cube18",
                CubeListBuilder.create().texOffs(79, 113).addBox(-2F, -2F, -2F, 4F, 11F, 4F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube19 = bone17.addOrReplaceChild("cube19",
                CubeListBuilder.create().texOffs(66, 102).addBox(-1F, -2F, -1F, 2F, 12F, 2F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone20 = bone17.addOrReplaceChild("镰刀", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.05386F, 9.78037F, -0.32502F, ((float) Math.toRadians(82.5F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition bone21 = bone20.addOrReplaceChild("handle", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.05386F, 6.83997F, 19.1077F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube22 = bone21.addOrReplaceChild("cube22",
                CubeListBuilder.create().texOffs(65, 32).addBox(-0.5F, -6.2F, 0.2F, 1F, 23F, 1F, new CubeDeformation(0F)),
                PartPose.offsetAndRotation(1F, -4F, -19.5F, ((float) Math.toRadians(2.5F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube23 = bone21.addOrReplaceChild("cube23",
                CubeListBuilder.create().texOffs(60, 39).addBox(-0.5F, 6F, -0.5F, 1F, 6F, 1F, new CubeDeformation(0F)),
                PartPose.offsetAndRotation(1F, -22F, -18.5F, ((float) Math.toRadians(-2.5F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube24 = bone21.addOrReplaceChild("cube24",
                CubeListBuilder.create().texOffs(70, 39).addBox(-0.5F, 7F, -0.5F, 1F, 5F, 1F, new CubeDeformation(0F)),
                PartPose.offsetAndRotation(1F, -10F, -7.5F, ((float) Math.toRadians(-90F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition bone25 = bone20.addOrReplaceChild("group", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.05386F, 6.83997F, 19.1077F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube26 = bone25.addOrReplaceChild("cube26",
                CubeListBuilder.create().texOffs(56, 47).addBox(-1.5F, -22F, -17F, 1F, 2F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube27 = bone25.addOrReplaceChild("cube27",
                CubeListBuilder.create().texOffs(56, 47).addBox(2.5F, -22F, -21F, 1F, 2F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube28 = bone25.addOrReplaceChild("cube28",
                CubeListBuilder.create().texOffs(56, 47).addBox(-1.5F, -22F, -21F, 1F, 2F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube29 = bone25.addOrReplaceChild("cube29",
                CubeListBuilder.create().texOffs(56, 47).addBox(2.5F, -22F, -17F, 1F, 2F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube30 = bone25.addOrReplaceChild("cube30",
                CubeListBuilder.create().texOffs(65, 51).addBox(-1.5F, -17F, -21F, 5F, 1F, 5F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube31 = bone25.addOrReplaceChild("cube31",
                CubeListBuilder.create().texOffs(65, 58).addBox(-1.5F, -20.7F, -21F, 5F, 4F, 5F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone32 = bone25.addOrReplaceChild("镰刀头", CubeListBuilder.create(),
                PartPose.offsetAndRotation(1F, -13.36954F, -30.76186F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube33 = bone32.addOrReplaceChild("cube33",
                CubeListBuilder.create().texOffs(80, 26).addBox(-0.5F, -6.71429F, 6.92857F, 1F, 6F, 5F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube34 = bone32.addOrReplaceChild("cube34",
                CubeListBuilder.create().texOffs(115, 26).addBox(-0.5F, -5.71429F, 3.92857F, 1F, 6F, 3F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube35 = bone32.addOrReplaceChild("cube35",
                CubeListBuilder.create().texOffs(105, 9).addBox(-0.5F, -4.71429F, 0.92857F, 1F, 6F, 3F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube36 = bone32.addOrReplaceChild("cube36",
                CubeListBuilder.create().texOffs(91, 14).addBox(-0.5F, -2.71429F, -2.07143F, 1F, 5F, 3F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube37 = bone32.addOrReplaceChild("cube37",
                CubeListBuilder.create().texOffs(100, 30).addBox(-0.5F, -0.71429F, -5.07143F, 1F, 4F, 3F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube38 = bone32.addOrReplaceChild("cube38",
                CubeListBuilder.create().texOffs(118, 46).addBox(-0.5F, 1.28571F, -7.07143F, 1F, 3F, 2F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube39 = bone32.addOrReplaceChild("cube39",
                CubeListBuilder.create().texOffs(106, 47).addBox(-0.5F, 3.28571F, -8.07143F, 1F, 2F, 2F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone40 = bone20.addOrReplaceChild("staffhead", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.05386F, 6.83997F, 19.1077F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube41 = bone40.addOrReplaceChild("cube41",
                CubeListBuilder.create().texOffs(48, 50).addBox(-0.5F, -20F, -20F, 3F, 3F, 3F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone42 = bone13.addOrReplaceChild("左肩", CubeListBuilder.create(),
                PartPose.offsetAndRotation(5F, 2F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(-5F))));
        PartDefinition bone43 = bone42.addOrReplaceChild("leftItem", CubeListBuilder.create(),
                PartPose.offsetAndRotation(1F, 7F, 1F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition bone44 = bone42.addOrReplaceChild("left_pauldron", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, 0F, 1F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube45 = bone44.addOrReplaceChild("cube45",
                CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-1F, -4F, -2.7F, 5F, 5F, 6F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone46 = bone42.addOrReplaceChild("左手", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.3F, 0F, 0.4F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube47 = bone46.addOrReplaceChild("cube47",
                CubeListBuilder.create().texOffs(79, 113).mirror().addBox(-2F, -2F, -2F, 4F, 11F, 4F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube48 = bone46.addOrReplaceChild("cube48",
                CubeListBuilder.create().texOffs(66, 102).addBox(-1F, -2F, -1F, 2F, 12F, 2F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone49 = bone13.addOrReplaceChild("披风", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, 0F, 2F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube50 = bone49.addOrReplaceChild("cube50",
                CubeListBuilder.create().texOffs(24, 64).addBox(-8F, 0F, -2F, 16F, 24F, 4F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone51 = bone49.addOrReplaceChild("棺材", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.5375F, 8.49142F, 5.04375F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(-22.5F))));
        PartDefinition cube52 = bone51.addOrReplaceChild("cube52",
                CubeListBuilder.create().texOffs(0, 122).addBox(-2.4625F, 14.50858F, -2.24375F, 5F, 1F, 5F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube53 = bone51.addOrReplaceChild("cube53",
                CubeListBuilder.create().texOffs(-1, 115).addBox(-3.9625F, -11.49142F, -2.24375F, 8F, 1F, 5F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube54 = bone51.addOrReplaceChild("cube54",
                CubeListBuilder.create().texOffs(1, 65).addBox(-3.4625F, -10.49142F, 2.00625F, 7F, 25F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube55 = bone51.addOrReplaceChild("cube55",
                CubeListBuilder.create().texOffs(1, 65).addBox(-3.4625F, -10.49142F, -2.99375F, 7F, 25F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube56 = bone51.addOrReplaceChild("cube56",
                CubeListBuilder.create().texOffs(67, 72).mirror().addBox(-0.5F, -17F, -2.5F, 1F, 17F, 5F, new CubeDeformation(0F)),
                PartPose.offsetAndRotation(-2.8625F, 15.50858F, -0.04375F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(-8F))));
        PartDefinition cube57 = bone51.addOrReplaceChild("cube57",
                CubeListBuilder.create().texOffs(67, 72).addBox(-0.5F, -17F, -2.5F, 1F, 17F, 5F, new CubeDeformation(0F)),
                PartPose.offsetAndRotation(2.9375F, 15.50858F, -0.04375F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(8F))));
        PartDefinition cube58 = bone51.addOrReplaceChild("cube58",
                CubeListBuilder.create().texOffs(53, 102).mirror().addBox(-0.5F, -10F, -2.5F, 1F, 10F, 5F, new CubeDeformation(0F)),
                PartPose.offsetAndRotation(-5.2625F, -1.19142F, -0.04375F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(8F))));
        PartDefinition cube59 = bone51.addOrReplaceChild("cube59",
                CubeListBuilder.create().texOffs(53, 102).addBox(-0.5F, -10F, -2.5F, 1F, 10F, 5F, new CubeDeformation(0F)),
                PartPose.offsetAndRotation(5.3375F, -1.19142F, -0.04375F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(-8F))));
        PartDefinition cube60 = bone51.addOrReplaceChild("cube60",
                CubeListBuilder.create().texOffs(0, 93).addBox(3.2375F, -10.49142F, 1.75625F, 1F, 19F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube61 = bone51.addOrReplaceChild("cube61",
                CubeListBuilder.create().texOffs(0, 93).mirror().addBox(-4.2625F, -10.49142F, 1.75625F, 1F, 19F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube62 = bone51.addOrReplaceChild("cube62",
                CubeListBuilder.create().texOffs(0, 93).mirror().addBox(-4.2625F, -10.49142F, -2.84375F, 1F, 19F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube63 = bone51.addOrReplaceChild("cube63",
                CubeListBuilder.create().texOffs(0, 93).addBox(3.2375F, -10.49142F, -2.84375F, 1F, 19F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube64 = bone51.addOrReplaceChild("cube64",
                CubeListBuilder.create().texOffs(6, 102).addBox(3.9375F, -5.49142F, -2.84375F, 1F, 8F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube65 = bone51.addOrReplaceChild("cube65",
                CubeListBuilder.create().texOffs(6, 102).mirror().addBox(-5.0625F, -5.49142F, -2.84375F, 1F, 8F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube66 = bone51.addOrReplaceChild("cube66",
                CubeListBuilder.create().texOffs(6, 102).addBox(3.9375F, -5.49142F, 1.75625F, 1F, 8F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition cube67 = bone51.addOrReplaceChild("cube67",
                CubeListBuilder.create().texOffs(6, 102).mirror().addBox(-5.0625F, -5.49142F, 1.75625F, 1F, 8F, 1F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone68 = bone13.addOrReplaceChild("身", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, 0F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube69 = bone68.addOrReplaceChild("cube69",
                CubeListBuilder.create().texOffs(99, 57).addBox(-4F, 0F, -1F, 8F, 20F, 4F, new CubeDeformation(0.25F)),
                PartPose.ZERO);
        PartDefinition cube70 = bone68.addOrReplaceChild("cube70",
                CubeListBuilder.create().texOffs(16, 16).addBox(-4F, -1.5F, -2F, 8F, 12F, 4F, new CubeDeformation(0F)),
                PartPose.ZERO);
        PartDefinition bone71 = bone68.addOrReplaceChild("肋骨", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0F, 0.4F, 2.7F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube72 = bone71.addOrReplaceChild("cube72",
                CubeListBuilder.create().texOffs(76, 97).addBox(-3.5F, -0.4F, -3F, 7F, 7F, 3F, new CubeDeformation(0F)),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 128, 128);
    }
}
