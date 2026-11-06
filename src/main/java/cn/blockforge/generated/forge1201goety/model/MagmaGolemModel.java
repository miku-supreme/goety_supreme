package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.model.AbstractGolemServantModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GolemClip;
import cn.blockforge.generated.goetysupreme.renderer.GolemClips;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolem;
import cn.blockforge.generated.goetysupreme.model.MagmaGolemModel;

public final class MagmaGolemModel extends AbstractGolemServantModel<MagmaGolem> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "magma_golem"), "main");

    public MagmaGolemModel(ModelPart root) {
        super(root);
    }

    @Override
    public float walkLengthTicks() {
        return 40.0F;
    }

    @Override
    public GolemClip walkClip() {
        return GolemClips.magmaWalk();
    }

    @Override
    public float chargeLengthTicks() {
        return 20.0F;
    }

    @Override
    public GolemClip chargeClip() {
        return GolemClips.magmaCharge();
    }

    @Override
    public GolemClip sweepClip() {
        return GolemClips.magmaSweep();
    }

    @Override
    public GolemClip smashClip() {
        return GolemClips.magmaSweep();
    }

    @Override
    public GolemClip fireballClip() {
        return GolemClips.magmaPrepareFireball();
    }

    public static LayerDefinition createBodyLayer() {
        return AbstractGolemServantModel.createBodyLayer(
                AbstractGolemServantModel.LAVA_UPPER_PITCH,
                AbstractGolemServantModel.ARM_LIFT,
                AbstractGolemServantModel.ARM_LIFT);
    }
}
