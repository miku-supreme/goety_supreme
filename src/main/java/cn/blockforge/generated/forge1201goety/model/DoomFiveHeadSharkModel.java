package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.entity.DoomFiveHeadShark;
import cn.blockforge.generated.goetysupreme.model.DoomFiveHeadSharkModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public final class DoomFiveHeadSharkModel<T extends DoomFiveHeadShark> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "doom_five_head_shark"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head1;
    private final ModelPart upper_jaw1;
    private final ModelPart lower_jaw1;
    private final ModelPart head2;
    private final ModelPart upper_jaw2;
    private final ModelPart lower_jaw2;
    private final ModelPart head3;
    private final ModelPart upper_jaw3;
    private final ModelPart lower_jaw3;
    private final ModelPart tail;
    private final ModelPart tail_seg;
    private final ModelPart head4;
    private final ModelPart upper_jaw4;
    private final ModelPart lower_jaw4;
    private final ModelPart head5;
    private final ModelPart upper_jaw5;
    private final ModelPart lower_jaw5;

    public DoomFiveHeadSharkModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("root").getChild("frame").getChild("body");
        this.neck = root.getChild("root").getChild("frame").getChild("body").getChild("neck");
        this.head1 = root.getChild("root").getChild("frame").getChild("body").getChild("neck").getChild("head1");
        this.upper_jaw1 = root.getChild("root").getChild("frame").getChild("body").getChild("neck").getChild("head1").getChild("upper_jaw1");
        this.lower_jaw1 = root.getChild("root").getChild("frame").getChild("body").getChild("neck").getChild("head1").getChild("lower_jaw1");
        this.head2 = root.getChild("root").getChild("frame").getChild("body").getChild("neck").getChild("head2");
        this.upper_jaw2 = root.getChild("root").getChild("frame").getChild("body").getChild("neck").getChild("head2").getChild("upper_jaw2");
        this.lower_jaw2 = root.getChild("root").getChild("frame").getChild("body").getChild("neck").getChild("head2").getChild("lower_jaw2");
        this.head3 = root.getChild("root").getChild("frame").getChild("body").getChild("neck").getChild("head3");
        this.upper_jaw3 = root.getChild("root").getChild("frame").getChild("body").getChild("neck").getChild("head3").getChild("upper_jaw3");
        this.lower_jaw3 = root.getChild("root").getChild("frame").getChild("body").getChild("neck").getChild("head3").getChild("lower_jaw3");
        this.tail = root.getChild("root").getChild("frame").getChild("tail");
        this.tail_seg = root.getChild("root").getChild("frame").getChild("tail").getChild("tail_seg");
        this.head4 = root.getChild("root").getChild("frame").getChild("head4");
        this.upper_jaw4 = root.getChild("root").getChild("frame").getChild("head4").getChild("upper_jaw4");
        this.lower_jaw4 = root.getChild("root").getChild("frame").getChild("head4").getChild("lower_jaw4");
        this.head5 = root.getChild("root").getChild("frame").getChild("head5");
        this.upper_jaw5 = root.getChild("root").getChild("frame").getChild("head5").getChild("upper_jaw5");
        this.lower_jaw5 = root.getChild("root").getChild("frame").getChild("head5").getChild("lower_jaw5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();
        PartDefinition root = parts.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition frame = root.addOrReplaceChild("frame", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition body = frame.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition body_r0 = body.addOrReplaceChild("body_r0", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.25F, -6.0F, 6.3F, -0.3491F, 0.0F, -0.0F));
        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -5.1F, 7.5F, -0.0F, 0.0F, -0.0F));
        PartDefinition head1 = neck.addOrReplaceChild("head1", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 1.1F, -6.5F, 0.2618F, 0.0F, -0.0F));
        PartDefinition upper_jaw1 = head1.addOrReplaceChild("upper_jaw1", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -2.0F, 1.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition lower_jaw1 = head1.addOrReplaceChild("lower_jaw1", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition head2 = neck.addOrReplaceChild("head2", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.2509F, 0.5F, -4.781F, -0.1745F, 0.6109F, -0.0F));
        PartDefinition upper_jaw2 = head2.addOrReplaceChild("upper_jaw2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -2.0F, 1.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition lower_jaw2 = head2.addOrReplaceChild("lower_jaw2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, 0.3054F, 0.0F, -0.0F));
        PartDefinition head3 = neck.addOrReplaceChild("head3", CubeListBuilder.create(), PartPose.offsetAndRotation(3.8112F, 0.5F, -2.9778F, -0.2618F, -0.6109F, -0.0F));
        PartDefinition upper_jaw3 = head3.addOrReplaceChild("upper_jaw3", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -2.0F, 1.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition lower_jaw3 = head3.addOrReplaceChild("lower_jaw3", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, 0.3054F, 0.0F, -0.0F));
        PartDefinition tail = frame.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition tail_seg = tail.addOrReplaceChild("tail_seg", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.3333F, -4.9983F, 29.968F, -0.0F, 0.0F, -0.0F));
        PartDefinition tail_seg_r0 = tail_seg.addOrReplaceChild("tail_seg_r0", CubeListBuilder.create(), PartPose.offsetAndRotation(-15.6667F, 2.4983F, 4.032F, 0.8727F, 0.0F, -0.0F));
        PartDefinition tail_seg_r1 = tail_seg.addOrReplaceChild("tail_seg_r1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.3333F, 2.4983F, -30.968F, -0.8727F, 0.0F, -0.0F));
        PartDefinition head4 = frame.addOrReplaceChild("head4", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.6875F, -3.6F, 8.5237F, 0.2618F, 1.2217F, -0.0F));
        PartDefinition upper_jaw4 = head4.addOrReplaceChild("upper_jaw4", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -2.0F, 1.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition lower_jaw4 = head4.addOrReplaceChild("lower_jaw4", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition head5 = frame.addOrReplaceChild("head5", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5402F, -3.6F, 10.486F, 0.2618F, -1.2217F, -0.0F));
        PartDefinition upper_jaw5 = head5.addOrReplaceChild("upper_jaw5", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -2.0F, 1.0F, -0.0F, 0.0F, -0.0F));
        PartDefinition lower_jaw5 = head5.addOrReplaceChild("lower_jaw5", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, -0.0F, 0.0F, -0.0F));
        body.addOrReplaceChild("c_body_0", CubeListBuilder.create().texOffs(0, 17).addBox(-6.5F, -10.0F, 6.0F, 10.0F, 10.0F, 16.0F), PartPose.ZERO);
        body_r0.addOrReplaceChild("c_body_r0_0", CubeListBuilder.create().texOffs(32, 35).addBox(0.25F, -16.4108F, 0.6705F, 0.0F, 12.0F, 8.0F), PartPose.ZERO);
        neck.addOrReplaceChild("c_neck_0", CubeListBuilder.create().texOffs(36, 13).addBox(-5.0F, -4.5F, -6.5F, 9.0F, 9.0F, 7.0F), PartPose.ZERO);
        upper_jaw1.addOrReplaceChild("c_upper_jaw1_0", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.6F, -11.0F, 7.0F, 5.0F, 12.0F), PartPose.ZERO);
        upper_jaw1.addOrReplaceChild("c_upper_jaw1_1", CubeListBuilder.create().texOffs(75, 2).addBox(-2.0F, 2.0F, -9.0F, 3.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw1.addOrReplaceChild("c_lower_jaw1_0", CubeListBuilder.create().texOffs(74, 13).addBox(-1.5F, -3.0F, -8.0F, 2.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw1.addOrReplaceChild("c_lower_jaw1_1", CubeListBuilder.create().texOffs(38, 0).addBox(-3.5F, -1.6F, -9.0F, 6.0F, 2.0F, 11.0F), PartPose.ZERO);
        upper_jaw2.addOrReplaceChild("c_upper_jaw2_0", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.6F, -11.0F, 7.0F, 5.0F, 12.0F), PartPose.ZERO);
        upper_jaw2.addOrReplaceChild("c_upper_jaw2_1", CubeListBuilder.create().texOffs(75, 2).addBox(-2.0F, 2.0F, -9.0F, 3.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw2.addOrReplaceChild("c_lower_jaw2_0", CubeListBuilder.create().texOffs(74, 13).addBox(-1.5F, -3.0F, -8.0F, 2.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw2.addOrReplaceChild("c_lower_jaw2_1", CubeListBuilder.create().texOffs(38, 0).addBox(-3.5F, -1.6F, -9.0F, 6.0F, 2.0F, 11.0F), PartPose.ZERO);
        upper_jaw3.addOrReplaceChild("c_upper_jaw3_0", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.6F, -11.0F, 7.0F, 5.0F, 12.0F), PartPose.ZERO);
        upper_jaw3.addOrReplaceChild("c_upper_jaw3_1", CubeListBuilder.create().texOffs(75, 2).addBox(-2.0F, 2.0F, -9.0F, 3.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw3.addOrReplaceChild("c_lower_jaw3_0", CubeListBuilder.create().texOffs(74, 13).addBox(-1.5F, -3.0F, -8.0F, 2.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw3.addOrReplaceChild("c_lower_jaw3_1", CubeListBuilder.create().texOffs(38, 0).addBox(-3.5F, -1.6F, -9.0F, 6.0F, 2.0F, 11.0F), PartPose.ZERO);
        tail.addOrReplaceChild("c_tail_0", CubeListBuilder.create().texOffs(0, 43).addBox(-5.5F, -9.0F, 22.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        tail_seg.addOrReplaceChild("c_tail_seg_0", CubeListBuilder.create().texOffs(48, 39).addBox(-2.1667F, -2.0017F, -0.968F, 4.0F, 5.0F, 10.0F), PartPose.ZERO);
        tail_seg_r0.addOrReplaceChild("c_tail_seg_r0_0", CubeListBuilder.create().texOffs(84, 22).addBox(15.5F, -3.5F, 3.0F, 0.0F, 5.0F, 10.0F), PartPose.ZERO);
        tail_seg_r1.addOrReplaceChild("c_tail_seg_r1_0", CubeListBuilder.create().texOffs(84, 19).addBox(0.0F, -31.5F, 22.0F, 0.0F, 5.0F, 8.0F), PartPose.ZERO);
        upper_jaw4.addOrReplaceChild("c_upper_jaw4_0", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.6F, -11.0F, 7.0F, 5.0F, 12.0F), PartPose.ZERO);
        upper_jaw4.addOrReplaceChild("c_upper_jaw4_1", CubeListBuilder.create().texOffs(75, 2).addBox(-2.0F, 2.0F, -9.0F, 3.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw4.addOrReplaceChild("c_lower_jaw4_0", CubeListBuilder.create().texOffs(74, 13).addBox(-1.5F, -3.0F, -8.0F, 2.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw4.addOrReplaceChild("c_lower_jaw4_1", CubeListBuilder.create().texOffs(38, 0).addBox(-3.5F, -1.6F, -9.0F, 6.0F, 2.0F, 11.0F), PartPose.ZERO);
        upper_jaw5.addOrReplaceChild("c_upper_jaw5_0", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.6F, -11.0F, 7.0F, 5.0F, 12.0F), PartPose.ZERO);
        upper_jaw5.addOrReplaceChild("c_upper_jaw5_1", CubeListBuilder.create().texOffs(75, 2).addBox(-2.0F, 2.0F, -9.0F, 3.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw5.addOrReplaceChild("c_lower_jaw5_0", CubeListBuilder.create().texOffs(74, 13).addBox(-1.5F, -3.0F, -8.0F, 2.0F, 2.0F, 8.0F), PartPose.ZERO);
        lower_jaw5.addOrReplaceChild("c_lower_jaw5_1", CubeListBuilder.create().texOffs(38, 0).addBox(-3.5F, -1.6F, -9.0F, 6.0F, 2.0F, 11.0F), PartPose.ZERO);
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float swim = limbSwing * 0.6F;
        float amount = Mth.clamp(limbSwingAmount, 0.0F, 1.0F);
        this.tail.yRot = Mth.cos(swim) * 0.35F * amount + Mth.cos(ageInTicks * 0.06F) * 0.08F;
        this.tail_seg.yRot = Mth.cos(swim - 0.9F) * 0.45F * amount
                + Mth.cos(ageInTicks * 0.06F - 0.9F) * 0.10F;
        float yaw = netHeadYaw * Mth.DEG_TO_RAD * 0.35F;
        float pitch = headPitch * Mth.DEG_TO_RAD * 0.25F;
        float[] phase = {0.0F, 0.9F, 1.8F, 2.7F, 3.6F};
        ModelPart[] heads = {this.head1, this.head2, this.head3, this.head4, this.head5};
        ModelPart[] uppers = {this.upper_jaw1, this.upper_jaw2, this.upper_jaw3, this.upper_jaw4, this.upper_jaw5};
        ModelPart[] lowers = {this.lower_jaw1, this.lower_jaw2, this.lower_jaw3, this.lower_jaw4, this.lower_jaw5};
        for (int i = 0; i < 5; i++) {
            float wobble = Mth.cos(swim - phase[i]) * 0.12F * amount
                    + Mth.cos(ageInTicks * 0.05F - phase[i]) * 0.04F;
            heads[i].yRot += yaw + wobble;
            heads[i].xRot += pitch;
            float bite = (Mth.sin(ageInTicks * 0.25F - phase[i]) * 0.5F + 0.5F) * 0.35F;
            lowers[i].xRot += bite;
            uppers[i].xRot -= bite * 0.15F;
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
