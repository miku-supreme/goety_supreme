package cn.blockforge.generated.goetysupreme.model;

import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.NamelessWither;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;
import cn.blockforge.generated.goetysupreme.renderer.NamelessWitherAnimations;

public final class NamelessWitherModel extends HierarchicalModel<NamelessWither> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "nameless_wither"), "main");

    private final ModelPart root;
    private final ModelPart namelessBone;
    private final ModelPart bodyBone;
    private final ModelPart cape;
    private final ModelPart mainHead;
    private final ModelPart leftHead;
    private final ModelPart rightHead;
    private final ModelPart mainCrown;
    private final ModelPart leftCrown;
    private final ModelPart rightCrown;
    private final ModelPart ribcage;
    private final ModelPart core;
    private final ModelPart shell;
    private final ModelPart shellCenter;
    private final ModelPart shellMid;
    private final ModelPart shellOuter;

    public NamelessWitherModel(ModelPart root) {
        this.root = root;
        this.namelessBone = SafeModelParts.findPath(root, "凋灵");
        this.bodyBone = SafeModelParts.findPath(root, "凋灵", "除了头");
        this.cape = SafeModelParts.findPath(root, "凋灵", "除了头", "披风");
        this.mainHead = SafeModelParts.findPath(root, "凋灵", "main_head");
        this.leftHead = SafeModelParts.findPath(root, "凋灵", "left_head");
        this.rightHead = SafeModelParts.findPath(root, "凋灵", "right_head");
        this.mainCrown = SafeModelParts.findPath(root, "凋灵", "main_head", "cube4");
        this.leftCrown = SafeModelParts.findPath(root, "凋灵", "left_head", "cube5");
        this.rightCrown = SafeModelParts.findPath(root, "凋灵", "right_head", "cube6");
        this.ribcage = SafeModelParts.findPath(root, "凋灵", "除了头", "肋骨");
        this.core = SafeModelParts.findPath(root, "凋灵", "除了头", "绿豆");
        this.shell = SafeModelParts.findPath(root, "凋灵", "除了头", "球壳");
        this.shellCenter = SafeModelParts.findPath(root, "凋灵", "除了头", "球壳", "shell_center");
        this.shellMid = SafeModelParts.findPath(root, "凋灵", "除了头", "球壳", "shell_mid");
        this.shellOuter = SafeModelParts.findPath(root, "凋灵", "除了头", "球壳", "shell_outer");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition bone0 = root.addOrReplaceChild("凋灵", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0F, 27F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition leftHead = bone0.addOrReplaceChild("left_head", CubeListBuilder.create(),
            PartPose.offsetAndRotation(-8F, -23F, 0F, 0F, 0F, 0F));
        leftHead.addOrReplaceChild("cube1",
            CubeListBuilder.create().texOffs(40, 0).addBox(-3F, -3F, -3F, 6F, 6F, 6F, new CubeDeformation(0F)),
            PartPose.ZERO);
        leftHead.addOrReplaceChild("cube5",
            CubeListBuilder.create().texOffs(32, 40).addBox(-1F, -6F, -1F, 6F, 6F, 6F, new CubeDeformation(0.25F)),
            PartPose.offset(-2F, 0F, -2F));
        PartDefinition rightHead = bone0.addOrReplaceChild("right_head", CubeListBuilder.create(),
            PartPose.offsetAndRotation(10F, -23F, 0F, 0F, 0F, 0F));
        rightHead.addOrReplaceChild("cube2",
            CubeListBuilder.create().texOffs(40, 0).addBox(-3F, -3F, -3F, 6F, 6F, 6F, new CubeDeformation(0F)),
            PartPose.ZERO);
        rightHead.addOrReplaceChild("cube6",
            CubeListBuilder.create().texOffs(32, 40).addBox(-1F, -6F, -1F, 6F, 6F, 6F, new CubeDeformation(0.25F)),
            PartPose.offset(-2F, 0F, -2F));
        PartDefinition mainHead = bone0.addOrReplaceChild("main_head", CubeListBuilder.create(),
            PartPose.offsetAndRotation(1F, -26F, 1F, 0F, 0F, 0F));
        mainHead.addOrReplaceChild("cube3",
            CubeListBuilder.create().texOffs(0, 26).addBox(-4F, -4F, -4F, 8F, 8F, 8F, new CubeDeformation(0F)),
            PartPose.ZERO);
        mainHead.addOrReplaceChild("cube4",
            CubeListBuilder.create().texOffs(32, 26).addBox(-1F, -6F, -1F, 8F, 6F, 8F, new CubeDeformation(0.25F)),
            PartPose.offset(-3F, 0F, -3F));
        PartDefinition bone7 = bone0.addOrReplaceChild("除了头", CubeListBuilder.create(),
            PartPose.offsetAndRotation(1F, -22F, 1F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube8 = bone7.addOrReplaceChild("cube8",
            CubeListBuilder.create().texOffs(0, 50).addBox(-1.5F, 3F, -2F, 3F, 10F, 3F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube9 = bone7.addOrReplaceChild("cube9",
            CubeListBuilder.create().texOffs(0, 20).addBox(-10F, 0F, -2F, 20F, 3F, 3F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube10 = bone7.addOrReplaceChild("cube10",
            CubeListBuilder.create().texOffs(40, 16).addBox(-4F, 3F, -2F, 8F, 1F, 3F, new CubeDeformation(0.01F)),
            PartPose.ZERO);
        PartDefinition cube11 = bone7.addOrReplaceChild("cube11",
            CubeListBuilder.create().texOffs(26, 52).addBox(0.5F, -5F, -1F, 3F, 6F, 3F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(-2F, 17F, 1F, ((float) Math.toRadians(25F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition bone12 = bone7.addOrReplaceChild("肋骨", CubeListBuilder.create(),
            PartPose.offsetAndRotation(-4F, 12F, 0F, ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube13 = bone12.addOrReplaceChild("cube13",
            CubeListBuilder.create().texOffs(40, 12).addBox(-1.5F, -2F, -0.5F, 11F, 2F, 2F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube14 = bone12.addOrReplaceChild("cube14",
            CubeListBuilder.create().texOffs(40, 12).addBox(-1.5F, -8F, -0.5F, 11F, 2F, 2F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube15 = bone12.addOrReplaceChild("cube15",
            CubeListBuilder.create().texOffs(40, 12).addBox(-1.5F, -5F, -0.5F, 11F, 2F, 2F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube16 = bone12.addOrReplaceChild("cube16",
            CubeListBuilder.create().texOffs(12, 50).addBox(-1.5F, -5F, -6.5F, 1F, 2F, 6F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube17 = bone12.addOrReplaceChild("cube17",
            CubeListBuilder.create().texOffs(12, 50).addBox(-1.5F, -2F, -6.5F, 1F, 2F, 6F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube18 = bone12.addOrReplaceChild("cube18",
            CubeListBuilder.create().texOffs(12, 50).mirror().addBox(8.5F, -2F, -6.5F, 1F, 2F, 6F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube19 = bone12.addOrReplaceChild("cube19",
            CubeListBuilder.create().texOffs(12, 50).mirror().addBox(8.5F, -5F, -6.5F, 1F, 2F, 6F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube20 = bone12.addOrReplaceChild("cube20",
            CubeListBuilder.create().texOffs(12, 50).mirror().addBox(8.5F, -8F, -6.5F, 1F, 2F, 6F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube21 = bone12.addOrReplaceChild("cube21",
            CubeListBuilder.create().texOffs(12, 50).addBox(-1.5F, -8F, -6.5F, 1F, 2F, 6F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube22 = bone12.addOrReplaceChild("cube22",
            CubeListBuilder.create().texOffs(0, 42).addBox(-1.5F, -8F, -6.5F, 11F, 8F, 0F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition bone23 = bone7.addOrReplaceChild("绿豆", CubeListBuilder.create(),
            PartPose.offsetAndRotation(-0.1F, 7.4F, -3F, ((float) Math.toRadians(30F)), ((float) Math.toRadians(-45F)), ((float) Math.toRadians(-22.5F))));
        PartDefinition cube24 = bone23.addOrReplaceChild("cube24",
            CubeListBuilder.create().texOffs(46, 20).addBox(-1.86763F, -1.50288F, -1.43986F, 3F, 3F, 3F, new CubeDeformation(0F)),
            PartPose.ZERO);
        PartDefinition cube25 = bone23.addOrReplaceChild("cube25",
            CubeListBuilder.create().texOffs(49, 53).addBox(-1.86763F, -1.50288F, -1.43986F, 3F, 3F, 3F, new CubeDeformation(0.5F)),
            PartPose.ZERO);
        PartDefinition shell = bone7.addOrReplaceChild("球壳", CubeListBuilder.create(),
            PartPose.offset(-0.1F, 7.4F, -3F));
        shell.addOrReplaceChild("shell_center",
            CubeListBuilder.create().texOffs(64, 0).addBox(-2F, -2F, -2F, 4F, 4F, 4F, new CubeDeformation(-1.0F)),
            PartPose.ZERO);
        shell.addOrReplaceChild("shell_mid",
            CubeListBuilder.create().texOffs(64, 32).addBox(-2F, -2F, -2F, 4F, 4F, 4F, new CubeDeformation(-3.75F)),
            PartPose.ZERO);
        shell.addOrReplaceChild("shell_outer",
            CubeListBuilder.create().texOffs(64, 64).addBox(-2F, -2F, -2F, 4F, 4F, 4F, new CubeDeformation(-5.0F)),
            PartPose.ZERO);
        PartDefinition bone26 = bone7.addOrReplaceChild("披风", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0F, -0.99F, 2F, ((float) Math.toRadians(27.5F)), ((float) Math.toRadians(0F)), ((float) Math.toRadians(0F))));
        PartDefinition cube27 = bone26.addOrReplaceChild("cube27",
            CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -2F, -4F, 15F, 15F, 5F, new CubeDeformation(0F)),
            PartPose.ZERO);

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void prepareMobModel(NamelessWither entity, float limbSwing, float limbSwingAmount,
            float partialTick) {
    }

    private static void setupHeadRotation(NamelessWither entity, ModelPart part, int head) {
        part.yRot = (entity.getHeadYRot(head) - entity.yBodyRot) * Mth.DEG_TO_RAD;
        part.xRot = entity.getHeadXRot(head) * Mth.DEG_TO_RAD;
    }

    private static void resetBone(ModelPart part, float x, float y, float z,
            float rxDeg, float ryDeg, float rzDeg) {
        part.setPos(x, y, z);
        part.setRotation((float) Math.toRadians(rxDeg), (float) Math.toRadians(ryDeg),
                (float) Math.toRadians(rzDeg));
        part.xScale = 1.0F;
        part.yScale = 1.0F;
        part.zScale = 1.0F;
    }

    @Override
    public void setupAnim(NamelessWither entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        // 每帧把 5 个会被动画驱动的骨骼恢复到各自的初始姿态（位置/旋转/缩放），
        // 但不整模型 resetPose —— 整模型 reset 会把拼好的 cube 拆散。
        resetBone(this.namelessBone, 0.0F, 27.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        resetBone(this.bodyBone, 1.0F, -22.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        resetBone(this.ribcage, -4.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        resetBone(this.core, -0.1F, 7.4F, -3.0F, 30.0F, -45.0F, -22.5F);
        resetBone(this.shell, -0.1F, 7.4F, -3.0F, 0.0F, 0.0F, 0.0F);
        // 球壳三层自转——照觉醒无名者法杖头球的转速（NamelessOneAnimation.HEART_OF_THE_NIGHT:
        // staffhead 16.5 秒转 1440°，约 4.36°/tick），三层按 1:0.75:0.5 交错
        float shellSpin = ageInTicks * (float) (360.0D / 82.5D * Math.PI / 180.0D);
        // 直接赋值（不是 +=）：这三层每帧没被 resetBone 复位，+= 会把历史角度累加导致越转越快
        this.shellCenter.xRot = shellSpin;
        this.shellCenter.zRot = shellSpin;
        this.shellMid.xRot = -shellSpin * 0.75F;
        this.shellMid.zRot = -shellSpin * 0.75F;
        this.shellOuter.xRot = shellSpin * 0.5F;
        this.shellOuter.zRot = shellSpin * 0.5F;
        resetBone(this.cape, 0.0F, -0.99F, 2.0F, 27.5F, 0.0F, 0.0F);
        // 三头独立转动（自己实现，绕每个头自己的 pivot；不再借用凋灵仆从的 headYaw 骨骼）
        this.mainHead.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.mainHead.xRot = Mth.clamp(headPitch, -45.0F, 45.0F) * Mth.DEG_TO_RAD;
        this.leftHead.yRot = (entity.getHeadYRot(0) - entity.yBodyRot) * Mth.DEG_TO_RAD;
        this.leftHead.xRot = entity.getHeadXRot(0) * Mth.DEG_TO_RAD;
        this.rightHead.yRot = (entity.getHeadYRot(1) - entity.yBodyRot) * Mth.DEG_TO_RAD;
        this.rightHead.xRot = entity.getHeadXRot(1) * Mth.DEG_TO_RAD;
        this.animate(entity.beamAnimationState, NamelessWitherAnimations.BEAM, ageInTicks);
        this.animate(entity.beamEndAnimationState, NamelessWitherAnimations.BEAM_END, ageInTicks);
        this.animate(entity.chargeAnimationState, NamelessWitherAnimations.CHARGE, ageInTicks);
        this.animate(entity.hoverAttackAnimationState, NamelessWitherAnimations.HOVER_ATTACK, ageInTicks);
        this.animate(entity.shellAnimationState, NamelessWitherAnimations.SHELL, ageInTicks);
        this.animate(entity.flyAnimationState, NamelessWitherAnimations.FLY, ageInTicks);
        this.animate(entity.flyEndAnimationState, NamelessWitherAnimations.FLY_END, ageInTicks);
        this.animate(entity.idleAnimationState, NamelessWitherAnimations.REST, ageInTicks);
        // 球壳默认隐藏，只有发光层在前摇时打开（避免主渲染画球壳）
        this.shell.visible = false;
    }

    public void renderBody(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
            int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setShellVisible(boolean visible) {
        this.shell.visible = visible;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
            int packedOverlay, float red, float green, float blue, float alpha) {
        this.renderBody(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
