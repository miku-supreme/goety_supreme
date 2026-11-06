package cn.blockforge.generated.goetysupreme.model;

import fuzs.mutantmonsters.client.model.MutantEndermanModel;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import cn.blockforge.generated.goetysupreme.model.MutantEndermanVoidEyeModel;

public final class MutantEndermanVoidEyeModel extends MutantEndermanModel {
    private static final String[][] PARTS = {
            new String[] {"pelvis", ""},
            new String[] {"abdomen", "pelvis"},
            new String[] {"chest", "abdomen"},
            new String[] {"neck", "chest"},
            new String[] {"head", "neck"},
            new String[] {"mouth", "head"},
            new String[] {"right_arm", "chest"},
            new String[] {"right_fore_arm", "right_arm"},
            new String[] {"right_hand", "right_fore_arm"},
            new String[] {"right_finger0", "right_hand"},
            new String[] {"right_fore_finger0", "right_finger0"},
            new String[] {"right_finger1", "right_hand"},
            new String[] {"right_fore_finger1", "right_finger1"},
            new String[] {"right_finger2", "right_hand"},
            new String[] {"right_fore_finger2", "right_finger2"},
            new String[] {"right_thumb", "right_hand"},
            new String[] {"left_arm", "chest"},
            new String[] {"left_fore_arm", "left_arm"},
            new String[] {"left_hand", "left_fore_arm"},
            new String[] {"left_finger0", "left_hand"},
            new String[] {"left_fore_finger0", "left_finger0"},
            new String[] {"left_finger1", "left_hand"},
            new String[] {"left_fore_finger1", "left_finger1"},
            new String[] {"left_finger2", "left_hand"},
            new String[] {"left_fore_finger2", "left_finger2"},
            new String[] {"left_thumb", "left_hand"},
            new String[] {"lower_right_arm", "chest"},
            new String[] {"lower_right_fore_arm", "lower_right_arm"},
            new String[] {"lower_right_hand", "lower_right_fore_arm"},
            new String[] {"lower_right_finger0", "lower_right_hand"},
            new String[] {"lower_right_fore_finger0", "lower_right_finger0"},
            new String[] {"lower_right_finger1", "lower_right_hand"},
            new String[] {"lower_right_fore_finger1", "lower_right_finger1"},
            new String[] {"lower_right_finger2", "lower_right_hand"},
            new String[] {"lower_right_fore_finger2", "lower_right_finger2"},
            new String[] {"lower_right_thumb", "lower_right_hand"},
            new String[] {"lower_left_arm", "chest"},
            new String[] {"lower_left_fore_arm", "lower_left_arm"},
            new String[] {"lower_left_hand", "lower_left_fore_arm"},
            new String[] {"lower_left_finger0", "lower_left_hand"},
            new String[] {"lower_left_fore_finger0", "lower_left_finger0"},
            new String[] {"lower_left_finger1", "lower_left_hand"},
            new String[] {"lower_left_fore_finger1", "lower_left_finger1"},
            new String[] {"lower_left_finger2", "lower_left_hand"},
            new String[] {"lower_left_fore_finger2", "lower_left_finger2"},
            new String[] {"lower_left_thumb", "lower_left_hand"},
            new String[] {"leg_joint1", "abdomen"},
            new String[] {"leg_joint2", "abdomen"},
            new String[] {"leg1", "leg_joint1"},
            new String[] {"leg2", "leg_joint2"},
            new String[] {"fore_leg1", "leg1"},
            new String[] {"fore_leg2", "leg2"},
    };
    private final ModelPart[] sourceParts = new ModelPart[PARTS.length];
    private final ModelPart[] bodyParts = new ModelPart[PARTS.length];

    MutantEndermanVoidEyeModel(ModelPart animatedRoot) {
        this(animatedRoot, createBodyLayer().bakeRoot());
    }

    private MutantEndermanVoidEyeModel(ModelPart animatedRoot, ModelPart bodyRoot) {
        super(bodyRoot);
        Map<String, ModelPart> source = new HashMap<>();
        Map<String, ModelPart> body = new HashMap<>();
        source.put("", animatedRoot);
        body.put("", bodyRoot);
        for (int i = 0; i < PARTS.length; i++) {
            String name = PARTS[i][0];
            String parent = PARTS[i][1];
            sourceParts[i] = source.get(parent).getChild(name);
            bodyParts[i] = body.get(parent).getChild(name);
            source.put(name, sourceParts[i]);
            body.put(name, bodyParts[i]);
        }
    }

    public void copyAnimatedPose() {
        for (int i = 0; i < sourceParts.length; i++) {
            bodyParts[i].copyFrom(sourceParts[i]);
            bodyParts[i].visible = sourceParts[i].visible;
            bodyParts[i].skipDraw = sourceParts[i].skipDraw;
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition pelvis = root.addOrReplaceChild("pelvis",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -15.5F, 8.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition abdomen = pelvis.addOrReplaceChild("abdomen",
                CubeListBuilder.create()
                        .texOffs(32, 0).mirror(false)
                        .addBox(-4.0F, -10.0F, -2.0F, 8.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition chest = abdomen.addOrReplaceChild("chest",
                CubeListBuilder.create()
                        .texOffs(50, 8).mirror(false)
                        .addBox(-5.0F, -16.0F, -3.0F, 10.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition neck = chest.addOrReplaceChild("neck",
                CubeListBuilder.create()
                        .texOffs(32, 14).mirror(false)
                        .addBox(-1.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -15.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition head = neck.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).mirror(false)
                        .addBox(-4.0F, -4.0F, -8.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.5F))
                        .texOffs(0, 14).mirror(false)
                        .addBox(-4.0F, 3.0F, -8.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 3.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition mouth = head.addOrReplaceChild("mouth",
                CubeListBuilder.create()
                        .texOffs(0, 24).mirror(false)
                        .addBox(-4.0F, 3.0F, -8.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_arm = chest.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(92, 0).mirror(false)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(-4.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_fore_arm = right_arm.addOrReplaceChild("right_fore_arm",
                CubeListBuilder.create()
                        .texOffs(104, 0).mirror(false)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 21.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_hand = right_fore_arm.addOrReplaceChild("right_hand",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_finger0 = right_hand.addOrReplaceChild("right_finger0",
                CubeListBuilder.create()
                        .texOffs(56, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_fore_finger0 = right_finger0.addOrReplaceChild("right_fore_finger0",
                CubeListBuilder.create()
                        .texOffs(64, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_finger1 = right_hand.addOrReplaceChild("right_finger1",
                CubeListBuilder.create()
                        .texOffs(60, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_fore_finger1 = right_finger1.addOrReplaceChild("right_fore_finger1",
                CubeListBuilder.create()
                        .texOffs(68, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 6.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_finger2 = right_hand.addOrReplaceChild("right_finger2",
                CubeListBuilder.create()
                        .texOffs(56, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_fore_finger2 = right_finger2.addOrReplaceChild("right_fore_finger2",
                CubeListBuilder.create()
                        .texOffs(64, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition right_thumb = right_hand.addOrReplaceChild("right_thumb",
                CubeListBuilder.create()
                        .texOffs(72, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_arm = chest.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(92, 0).mirror(true)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(4.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_fore_arm = left_arm.addOrReplaceChild("left_fore_arm",
                CubeListBuilder.create()
                        .texOffs(104, 0).mirror(true)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 21.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_hand = left_fore_arm.addOrReplaceChild("left_hand",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_finger0 = left_hand.addOrReplaceChild("left_finger0",
                CubeListBuilder.create()
                        .texOffs(56, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_fore_finger0 = left_finger0.addOrReplaceChild("left_fore_finger0",
                CubeListBuilder.create()
                        .texOffs(64, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_finger1 = left_hand.addOrReplaceChild("left_finger1",
                CubeListBuilder.create()
                        .texOffs(60, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_fore_finger1 = left_finger1.addOrReplaceChild("left_fore_finger1",
                CubeListBuilder.create()
                        .texOffs(68, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 6.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_finger2 = left_hand.addOrReplaceChild("left_finger2",
                CubeListBuilder.create()
                        .texOffs(56, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_fore_finger2 = left_finger2.addOrReplaceChild("left_fore_finger2",
                CubeListBuilder.create()
                        .texOffs(64, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition left_thumb = left_hand.addOrReplaceChild("left_thumb",
                CubeListBuilder.create()
                        .texOffs(72, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_arm = chest.addOrReplaceChild("lower_right_arm",
                CubeListBuilder.create()
                        .texOffs(92, 0).mirror(false)
                        .addBox(-1.5F, 6.0F, -1.5F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(-4.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_fore_arm = lower_right_arm.addOrReplaceChild("lower_right_fore_arm",
                CubeListBuilder.create()
                        .texOffs(104, 0).mirror(false)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 21.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_hand = lower_right_fore_arm.addOrReplaceChild("lower_right_hand",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_finger0 = lower_right_hand.addOrReplaceChild("lower_right_finger0",
                CubeListBuilder.create()
                        .texOffs(56, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_fore_finger0 = lower_right_finger0.addOrReplaceChild("lower_right_fore_finger0",
                CubeListBuilder.create()
                        .texOffs(64, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_finger1 = lower_right_hand.addOrReplaceChild("lower_right_finger1",
                CubeListBuilder.create()
                        .texOffs(60, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_fore_finger1 = lower_right_finger1.addOrReplaceChild("lower_right_fore_finger1",
                CubeListBuilder.create()
                        .texOffs(68, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 6.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_finger2 = lower_right_hand.addOrReplaceChild("lower_right_finger2",
                CubeListBuilder.create()
                        .texOffs(56, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_fore_finger2 = lower_right_finger2.addOrReplaceChild("lower_right_fore_finger2",
                CubeListBuilder.create()
                        .texOffs(64, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_right_thumb = lower_right_hand.addOrReplaceChild("lower_right_thumb",
                CubeListBuilder.create()
                        .texOffs(72, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_arm = chest.addOrReplaceChild("lower_left_arm",
                CubeListBuilder.create()
                        .texOffs(92, 0).mirror(true)
                        .addBox(-1.5F, 6.0F, -1.5F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(4.0F, -14.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_fore_arm = lower_left_arm.addOrReplaceChild("lower_left_fore_arm",
                CubeListBuilder.create()
                        .texOffs(104, 0).mirror(true)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 21.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_hand = lower_left_fore_arm.addOrReplaceChild("lower_left_hand",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 17.5F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_finger0 = lower_left_hand.addOrReplaceChild("lower_left_finger0",
                CubeListBuilder.create()
                        .texOffs(56, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_fore_finger0 = lower_left_finger0.addOrReplaceChild("lower_left_fore_finger0",
                CubeListBuilder.create()
                        .texOffs(64, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_finger1 = lower_left_hand.addOrReplaceChild("lower_left_finger1",
                CubeListBuilder.create()
                        .texOffs(60, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_fore_finger1 = lower_left_finger1.addOrReplaceChild("lower_left_fore_finger1",
                CubeListBuilder.create()
                        .texOffs(68, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 6.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_finger2 = lower_left_hand.addOrReplaceChild("lower_left_finger2",
                CubeListBuilder.create()
                        .texOffs(56, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_fore_finger2 = lower_left_finger2.addOrReplaceChild("lower_left_fore_finger2",
                CubeListBuilder.create()
                        .texOffs(64, 0).mirror(true)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.59000003F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 5.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition lower_left_thumb = lower_left_hand.addOrReplaceChild("lower_left_thumb",
                CubeListBuilder.create()
                        .texOffs(72, 0).mirror(false)
                        .addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.6F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, -0.5F, 0.0F, 0.0F, 0.0F));
        PartDefinition leg_joint1 = abdomen.addOrReplaceChild("leg_joint1",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-1.5F, 0.0F, 0.75F, 0.0F, 0.0F, 0.0F));
        PartDefinition leg_joint2 = abdomen.addOrReplaceChild("leg_joint2",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(1.5F, 0.0F, 0.75F, 0.0F, 0.0F, 0.0F));
        PartDefinition leg1 = leg_joint1.addOrReplaceChild("leg1",
                CubeListBuilder.create()
                        .texOffs(0, 34).mirror(false)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 24.0F, 3.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition leg2 = leg_joint2.addOrReplaceChild("leg2",
                CubeListBuilder.create()
                        .texOffs(0, 34).mirror(true)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 24.0F, 3.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition fore_leg1 = leg1.addOrReplaceChild("fore_leg1",
                CubeListBuilder.create()
                        .texOffs(12, 34).mirror(false)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 24.0F, 3.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(0.0F, 23.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition fore_leg2 = leg2.addOrReplaceChild("fore_leg2",
                CubeListBuilder.create()
                        .texOffs(12, 34).mirror(true)
                        .addBox(-1.5F, 0.0F, -1.5F, 3.0F, 24.0F, 3.0F, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(0.0F, 23.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
