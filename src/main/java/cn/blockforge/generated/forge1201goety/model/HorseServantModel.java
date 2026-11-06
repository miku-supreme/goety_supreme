package cn.blockforge.generated.goetysupreme.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.HorseServantModel;
import cn.blockforge.generated.goetysupreme.model.IronGolemServantModel;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;

public final class HorseServantModel<T extends SkeletonHorseServant> extends AgeableListModel<T> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "skeleton_horse_servant"), "main");

    private final ModelPart body;
    private final ModelPart headParts;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightHindBabyLeg;
    private final ModelPart leftHindBabyLeg;
    private final ModelPart rightFrontBabyLeg;
    private final ModelPart leftFrontBabyLeg;
    private final ModelPart tail;
    private final ModelPart[] saddleParts;
    private final ModelPart[] ridingParts;

    public HorseServantModel(ModelPart root) {
        super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
        this.body = root.getChild("body");
        this.headParts = root.getChild("head_parts");
        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");
        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");
        this.rightHindBabyLeg = root.getChild("right_hind_baby_leg");
        this.leftHindBabyLeg = root.getChild("left_hind_baby_leg");
        this.rightFrontBabyLeg = root.getChild("right_front_baby_leg");
        this.leftFrontBabyLeg = root.getChild("left_front_baby_leg");
        this.tail = this.body.getChild("tail");
        this.saddleParts = new ModelPart[]{
                this.body.getChild("saddle"),
                this.headParts.getChild("left_saddle_mouth"),
                this.headParts.getChild("right_saddle_mouth"),
                this.headParts.getChild("head_saddle"),
                this.headParts.getChild("mouth_saddle_wrap")
        };
        this.ridingParts = new ModelPart[]{
                this.headParts.getChild("left_saddle_line"),
                this.headParts.getChild("right_saddle_line")
        };
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(HorseModel.createBodyMesh(CubeDeformation.NONE), 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.young = false;
        for (ModelPart part : this.saddleParts) {
            part.visible = false;
        }
        for (ModelPart part : this.ridingParts) {
            part.visible = false;
        }
        this.rightHindLeg.visible = true;
        this.leftHindLeg.visible = true;
        this.rightFrontLeg.visible = true;
        this.leftFrontLeg.visible = true;
        this.rightHindBabyLeg.visible = false;
        this.leftHindBabyLeg.visible = false;
        this.rightFrontBabyLeg.visible = false;
        this.leftFrontBabyLeg.visible = false;
        this.body.y = 11.0F;

        float pitch = Mth.clamp(headPitch, -20.0F, 20.0F) * ((float) Math.PI / 180.0F);
        float yaw = Mth.clamp(netHeadYaw, -40.0F, 40.0F) * ((float) Math.PI / 180.0F);
        this.headParts.xRot = 0.5235988F + pitch * 0.6F;
        this.headParts.yRot = yaw;
        this.body.xRot = 0.0F;

        float front = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.8F * limbSwingAmount;
        float hind = Mth.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.leftFrontLeg.xRot = front;
        this.rightFrontLeg.xRot = -front;
        this.leftFrontLeg.y = 14.0F;
        this.rightFrontLeg.y = 14.0F;
        this.leftFrontLeg.z = -10.0F;
        this.rightFrontLeg.z = -10.0F;
        this.leftHindLeg.xRot = hind;
        this.rightHindLeg.xRot = -hind;

        this.tail.xRot = 0.5235988F + limbSwingAmount * 0.75F;
        this.tail.y = -5.0F + limbSwingAmount;
        this.tail.z = 2.0F + limbSwingAmount * 2.0F;
        this.tail.yRot = Mth.cos(ageInTicks * 0.1F) * 0.08F;
    }

    @Override
    public Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.headParts);
    }

    @Override
    public Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg,
                this.rightFrontLeg, this.leftFrontLeg);
    }
}
