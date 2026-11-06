package cn.blockforge.generated.goetysupreme.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.model.AbstractGolemServantModel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GolemClip;
import cn.blockforge.generated.goetysupreme.renderer.GolemClips;
import cn.blockforge.generated.goetysupreme.entity.PrisonGolem;
import cn.blockforge.generated.goetysupreme.model.PrisonGolemModel;

public final class PrisonGolemModel extends AbstractGolemServantModel<PrisonGolem> {
    public static final ModelLayerLocation LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "prison_golem"), "main");

    public PrisonGolemModel(ModelPart root) {
        super(root);
    }

    @Override
    public float walkLengthTicks() {
        return 20.0F;
    }

    @Override
    public GolemClip walkClip() {
        return GolemClips.walk();
    }

    @Override
    public float chargeLengthTicks() {
        return 10.0F;
    }

    @Override
    public GolemClip chargeClip() {
        return GolemClips.charge();
    }

    @Override
    public GolemClip sweepClip() {
        return GolemClips.sweep();
    }

    @Override
    public GolemClip smashClip() {
        return GolemClips.smash();
    }

    @Override
    public GolemClip fireballClip() {
        return GolemClips.smash();
    }

    public static LayerDefinition createBodyLayer() {
        return AbstractGolemServantModel.createBodyLayer(0.0F, 0.0F);
    }
}
