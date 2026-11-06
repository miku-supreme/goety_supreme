package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;

import fuzs.mutantmonsters.client.model.MutantZombieModel;
import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.model.MutantZombieServantModel;
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;
import cn.blockforge.generated.goetysupreme.entity.WeaponAnchorItemInHandLayer;

public final class MutantZombieServantModel extends EntityModel<MutantZombieServant> implements ArmedModel {

    private static final float HAND_DROP = 0.8125F;

    private final MutantZombieModel delegate;
    private final ModelPart root;

    private final ModelPart[] rightArmChain;
    private final ModelPart[] leftArmChain;
    private MutantZombie proxy;

    public MutantZombieServantModel(ModelPart root) {
        this.root = root;
        this.delegate = new MutantZombieModel(root);
        this.rightArmChain = chain("pelvis", "waist", "chest", "arm1", "fore_arm1");
        this.leftArmChain = chain("pelvis", "waist", "chest", "arm2", "fore_arm2");
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        for (ModelPart bone : arm == HumanoidArm.RIGHT ? this.rightArmChain : this.leftArmChain) {
            bone.translateAndRotate(poseStack);
        }
        poseStack.translate(0.0F, HAND_DROP, 0.0F);
    }

    public ModelPart[] chain(String... path) {
        ModelPart[] result = new ModelPart[path.length + 1];
        result[0] = this.root;
        ModelPart current = this.root;
        for (int i = 0; i < path.length; i++) {
            current = SafeModelParts.find(current, path[i]);
            result[i + 1] = current;
        }
        return result;
    }

    public static LayerDefinition createBodyLayer() {
        return MutantZombieModel.createBodyLayer();
    }

    @Override
    public void prepareMobModel(MutantZombieServant servant, float limbSwing, float limbSwingAmount, float partialTick) {
        MutantZombie mutant = updateProxy(servant);
        delegate.prepareMobModel(mutant, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public void setupAnim(MutantZombieServant servant, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        MutantZombie mutant = updateProxy(servant);
        delegate.setupAnim(mutant, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public void renderToBuffer(com.mojang.blaze3d.vertex.PoseStack poseStack,
            com.mojang.blaze3d.vertex.VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
            float red, float green, float blue, float alpha) {
        delegate.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private MutantZombie updateProxy(MutantZombieServant servant) {
        Level level = servant.level();
        if (proxy == null || proxy.level() != level) {
            EntityType<? extends MutantZombie> type = (EntityType<? extends MutantZombie>) (EntityType) EntityType.ZOMBIE;
            proxy = new MutantZombie(type, level);
        }

        proxy.setAnimation(servant.getAnimation());
        proxy.setAnimationTick(servant.getAnimationTick());
        proxy.deathTime = servant.deathTime;
        proxy.throwHitTick = servant.getThrowHitTick();
        proxy.throwFinishTick = servant.getThrowFinishTick();
        proxy.vanishTime = 0;
        proxy.setPos(servant.getX(), servant.getY(), servant.getZ());
        proxy.yBodyRot = servant.yBodyRot;
        proxy.yBodyRotO = servant.yBodyRotO;
        proxy.yHeadRot = servant.yHeadRot;
        proxy.yHeadRotO = servant.yHeadRotO;
        return proxy;
    }
}
