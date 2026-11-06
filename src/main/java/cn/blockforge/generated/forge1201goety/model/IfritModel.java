package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.util.IfritAnimator;
import cn.blockforge.generated.goetysupreme.model.IfritModel;

public final class IfritModel extends EntityModel<Ifrit> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "ifrit"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();

    public IfritModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.root = root;
        ModelPart r = need(root, "root");
        this.parts.put("root", r);
        this.parts.put("hip", need(r, "hip"));
        this.parts.put("left_leg", need(need(r, "hip"), "left_leg"));
        this.parts.put("right_leg", need(need(r, "hip"), "right_leg"));
        this.parts.put("body", need(need(r, "hip"), "body"));
        this.parts.put("cape_left", need(need(need(r, "hip"), "body"), "cape_left"));
        this.parts.put("cape_right", need(need(need(r, "hip"), "body"), "cape_right"));
        this.parts.put("shoulders", need(need(need(r, "hip"), "body"), "shoulders"));
        this.parts.put("neck", need(need(need(need(r, "hip"), "body"), "shoulders"), "neck"));
        this.parts.put("head", need(need(need(need(need(r, "hip"), "body"), "shoulders"), "neck"), "head"));
        this.parts.put("left_eye", need(need(need(need(need(r, "hip"), "body"), "shoulders"), "neck"), "head").getChild("left_eye"));
        this.parts.put("right_eye", need(need(need(need(need(r, "hip"), "body"), "shoulders"), "neck"), "head").getChild("right_eye"));
        this.parts.put("left_arm", need(need(need(need(r, "hip"), "body"), "shoulders"), "left_arm"));
        this.parts.put("left_claw", need(need(need(need(need(r, "hip"), "body"), "shoulders"), "left_arm"), "left_claw"));
        this.parts.put("right_arm", need(need(need(need(r, "hip"), "body"), "shoulders"), "right_arm"));
        this.parts.put("right_claw", need(need(need(need(need(r, "hip"), "body"), "shoulders"), "right_arm"), "right_claw"));
    }

    private static ModelPart need(ModelPart parent, String name) {
        if (parent == null || !parent.hasChild(name)) {
            throw new IllegalStateException("IfritModel missing part: " + name);
        }
        return parent.getChild(name);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition hip = root.addOrReplaceChild("hip", CubeListBuilder.create(), PartPose.offset(0.0F, -47.0F, 0.0F));

        PartDefinition left_leg = hip.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(117, 3).addBox(-1.0F, 0.0F, 0.2505F, 2.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, -0.0789F));

        PartDefinition right_leg = hip.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(117, 3).addBox(-1.0F, 0.0F, 0.1716F, 2.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition body = hip.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cape_left = body.addOrReplaceChild("cape_left", CubeListBuilder.create(), PartPose.offset(3.0F, -15.0F, 3.5F));

        PartDefinition cube_r1 = cape_left.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, 21).addBox(-9.0F, -3.0F, 8.0F, 14.0F, 55.0F, 0.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.5858F, 2.0F, -4.9142F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cape_right = body.addOrReplaceChild("cape_right", CubeListBuilder.create(), PartPose.offset(-3.0F, -14.0F, 3.0F));

        PartDefinition cube_r2 = cape_right.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(78, 21).addBox(-14.0F, -56.0F, -7.0F, 14.0F, 55.0F, 0.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-2.0F, 54.0F, 12.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition shoulders = body.addOrReplaceChild("shoulders", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition Shoulders_0_r1 = shoulders.addOrReplaceChild("Shoulders_0_r1", CubeListBuilder.create().texOffs(1, 5).addBox(-6.8787F, -20.0F, -6.8787F, 14.0F, 21.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 3.1416F));

        PartDefinition neck = shoulders.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -0.0001F, 0.0046F));

        PartDefinition Head_14_r1 = head.addOrReplaceChild("Head_14_r1", CubeListBuilder.create().texOffs(68, 1).mirror().addBox(-4.618F, -4.618F, -8.9867F, 9.0F, 9.0F, 8.9868F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.7854F, 0.0F));

        PartDefinition Head_13_r1 = head.addOrReplaceChild("Head_13_r1", CubeListBuilder.create().texOffs(7, 58).mirror().addBox(-10.618F, 8.8908F, 4.6181F, 0.0F, 15.0F, 1.904F, new CubeDeformation(0.1F)).mirror(false)
        .texOffs(6, 60).addBox(-10.522F, 8.8908F, -3.3819F, 1.904F, 6.0F, 0.0F, new CubeDeformation(0.1F))
        .texOffs(11, 62).addBox(-10.618F, 8.8908F, -3.2859F, 0.0F, 6.0F, 2.904F, new CubeDeformation(0.1F))
        .texOffs(8, 60).addBox(-10.522F, 8.8908F, 6.6181F, 1.904F, 15.0F, 0.0F, new CubeDeformation(0.1F))
        .texOffs(26, 24).addBox(-10.618F, 5.9736F, -0.3819F, 3.0F, 3.0132F, 7.0F, new CubeDeformation(0.0F))
        .texOffs(31, 27).mirror().addBox(-10.618F, 5.9736F, -3.3819F, 3.0F, 3.0132F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
        .texOffs(59, 88).addBox(-7.618F, 5.9736F, -3.3819F, 3.0F, 3.0132F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 3.1416F));

        PartDefinition Head_6_r1 = head.addOrReplaceChild("Head_6_r1", CubeListBuilder.create().texOffs(7, 58).addBox(0.382F, -5.1092F, -1.3819F, 0.0F, 11.0F, 1.904F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-2.1213F, -14.0F, 12.0208F, 0.0F, 0.7854F, 3.1416F));

        PartDefinition Head_5_r1 = head.addOrReplaceChild("Head_5_r1", CubeListBuilder.create().texOffs(13, 65).addBox(0.382F, 8.8908F, 10.6181F, 2.904F, 6.0F, 0.0F, new CubeDeformation(0.1F))
        .texOffs(7, 64).addBox(-6.522F, 8.8908F, 10.6181F, 1.904F, 11.0F, 0.0F, new CubeDeformation(0.1F))
        .texOffs(13, 63).mirror().addBox(3.382F, 8.8908F, 8.6181F, 0.0F, 6.0F, 1.904F, new CubeDeformation(0.1F)).mirror(false)
        .texOffs(19, 24).mirror().addBox(-6.618F, 5.9736F, 7.6181F, 7.0F, 3.0132F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
        .texOffs(30, 22).addBox(0.382F, 5.9736F, 7.6181F, 3.0F, 3.0132F, 3.0F, new CubeDeformation(0.0F))
        .texOffs(78, 11).addBox(0.382F, 5.9736F, 4.6181F, 3.0F, 3.0132F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 3.1416F));

        PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create(), PartPose.offset(2.8397F, -5.0001F, -3.3979F));

        PartDefinition L_EyeIris_0_r1 = left_eye.addOrReplaceChild("L_EyeIris_0_r1", CubeListBuilder.create().texOffs(87, 4).addBox(-1.984F, 0.0001F, -0.0074F, 4.0F, 0.0F, 0.9985F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 1.5708F, -0.7854F, 0.0F));

        PartDefinition left_arm = shoulders.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(10.2263F, 0.0F, -0.5355F));

        PartDefinition L_Arm_0_r1 = left_arm.addOrReplaceChild("L_Arm_0_r1", CubeListBuilder.create().texOffs(40, 80).addBox(-9.024F, -2.976F, -22.0F, 18.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7071F, -0.41F, -2.7071F, -1.5708F, 0.7854F, -3.1416F));

        PartDefinition left_claw = left_arm.addOrReplaceChild("left_claw", CubeListBuilder.create(), PartPose.offset(1.4831F, 22.0F, -1.4831F));

        PartDefinition L_Claw_0_r1 = left_claw.addOrReplaceChild("L_Claw_0_r1", CubeListBuilder.create().texOffs(-35, 43).addBox(-8.024F, -0.8787F, -43.0F, 14.0F, 0.0F, 59.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.0F, -1.5708F, 0.7854F, -3.1416F));

        PartDefinition right_arm = shoulders.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-9.1415F, 0.0F, -0.4507F));

        PartDefinition R_Arm_0_r1 = right_arm.addOrReplaceChild("R_Arm_0_r1", CubeListBuilder.create().texOffs(44, 80).addBox(-8.976F, -4.096F, -22.0F, 18.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F, 0.0F));

        PartDefinition right_claw = right_arm.addOrReplaceChild("right_claw", CubeListBuilder.create(), PartPose.offset(-1.5679F, 22.0F, -1.5679F));

        PartDefinition R_Claw_0_r1 = right_claw.addOrReplaceChild("R_Claw_0_r1", CubeListBuilder.create().texOffs(-35, 43).addBox(-5.976F, 0.1213F, -43.0F, 14.0F, 0.0F, 59.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.7854F, 0.0F));

        PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create(), PartPose.offset(-2.8001F, -5.0F, -3.3629F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public ModelPart root() {
        return this.root;
    }

    public ModelPart part(String name) {
        return this.parts.get(name);
    }

    public void setupAnim(Ifrit entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
        IfritAnimator.setup(this, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void applyClawTransform(PoseStack poseStack, boolean left) {
        ModelPart part = this.root;
        part.translateAndRotate(poseStack);
        for (String name : left
                ? new String[]{"root", "hip", "body", "shoulders", "left_arm", "left_claw", "L_Claw_0_r1"}
                : new String[]{"root", "hip", "body", "shoulders", "right_arm", "right_claw", "R_Claw_0_r1"}) {
            part = part.getChild(name);
            part.translateAndRotate(poseStack);
        }
    }

    public void applyRightHandTransform(PoseStack poseStack) {
        ModelPart part = this.root;
        part.translateAndRotate(poseStack);
        for (String name : new String[]{"root", "hip", "body", "shoulders", "right_arm", "right_claw"}) {
            part = part.getChild(name);
            part.translateAndRotate(poseStack);
        }
    }
}
