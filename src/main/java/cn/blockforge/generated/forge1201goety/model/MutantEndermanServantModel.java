package cn.blockforge.generated.goetysupreme.model;

import fuzs.mutantmonsters.client.model.MutantEndermanModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.model.MutantEndermanServantModel;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServantRenderState;

public final class MutantEndermanServantModel extends EntityModel<MutantEndermanServant> {
    private final MutantEndermanModel delegate;
    private MutantEndermanServantRenderState state;

    public MutantEndermanServantModel(ModelPart root) {
        delegate = new MutantEndermanModel(root);
    }

    public static LayerDefinition createBodyLayer() {
        return MutantEndermanModel.createBodyLayer();
    }

    public void translateRotateArm(com.mojang.blaze3d.vertex.PoseStack poses, int arm) {
        delegate.translateRotateArm(poses, arm);
    }

    private MutantEndermanServantRenderState stateFor(MutantEndermanServant servant) {
        if (state == null || state.level() != servant.level()) {
            state = new MutantEndermanServantRenderState(servant.level());
        }
        state.bind(servant);
        delegate.attackTime = attackTime;
        delegate.riding = riding;
        delegate.young = young;
        return state;
    }

    @Override
    public void prepareMobModel(MutantEndermanServant servant, float swing, float amount, float partialTick) {
        delegate.prepareMobModel(stateFor(servant), swing, amount, partialTick);
    }

    @Override
    public void setupAnim(MutantEndermanServant servant, float swing, float amount, float age,
            float yaw, float pitch) {
        delegate.setupAnim(stateFor(servant), swing, amount, age, yaw, pitch);
    }

    @Override
    public void renderToBuffer(com.mojang.blaze3d.vertex.PoseStack poses,
            com.mojang.blaze3d.vertex.VertexConsumer vertices, int light, int overlay,
            float red, float green, float blue, float alpha) {
        delegate.renderToBuffer(poses, vertices, light, overlay, red, green, blue, alpha);
    }
}
