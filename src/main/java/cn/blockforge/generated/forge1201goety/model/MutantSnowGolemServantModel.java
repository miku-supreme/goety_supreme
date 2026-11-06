package cn.blockforge.generated.goetysupreme.model;

import fuzs.mutantmonsters.client.model.MutantSnowGolemModel;
import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.model.MutantSnowGolemServantModel;
import cn.blockforge.generated.goetysupreme.model.MutantSnowGolemShoulderArmorModel;
import cn.blockforge.generated.goetysupreme.model.MutantZombieServantModel;

public final class MutantSnowGolemServantModel extends EntityModel<MutantSnowGolemServant> {
    private final MutantSnowGolemModel delegate;
    private final ModelPart pelvis;
    private final ModelPart abdomen;
    private final ModelPart chest;
    private MutantSnowGolemServant boundServant;
    private MutantSnowGolem proxy;

    public MutantSnowGolemServantModel(ModelPart root) {
        this.delegate = new MutantSnowGolemModel(root);
        this.pelvis = root.getChild("pelvis");
        this.abdomen = pelvis.getChild("abdomen");
        this.chest = abdomen.getChild("chest");
    }

    public void renderShoulderArmor(com.mojang.blaze3d.vertex.PoseStack poseStack,
            MutantSnowGolemShoulderArmorModel armorModel,
            com.mojang.blaze3d.vertex.VertexConsumer vertexConsumer,
            int packedLight, int packedOverlay) {
        if (!pelvis.visible || !abdomen.visible || !chest.visible) {
            return;
        }
        poseStack.pushPose();
        pelvis.translateAndRotate(poseStack);
        abdomen.translateAndRotate(poseStack);
        chest.translateAndRotate(poseStack);
        armorModel.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void prepareMobModel(MutantSnowGolemServant servant, float limbSwing, float limbSwingAmount,
            float partialTick) {
        MutantSnowGolem golem = updateProxy(servant);
        if (golem != null) {
            delegate.prepareMobModel(golem, limbSwing, limbSwingAmount, partialTick);
        }
    }

    @Override
    public void setupAnim(MutantSnowGolemServant servant, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        MutantSnowGolem golem = updateProxy(servant);
        if (golem != null) {
            delegate.setupAnim(golem, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @Override
    public void renderToBuffer(com.mojang.blaze3d.vertex.PoseStack poseStack,
            com.mojang.blaze3d.vertex.VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
            float red, float green, float blue, float alpha) {
        delegate.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void copyPropertiesTo(MutantSnowGolemModel other) {
        delegate.copyPropertiesTo(other);
    }

    private MutantSnowGolem updateProxy(MutantSnowGolemServant servant) {
        Level level = servant.level();
        if (proxy == null || proxy.level() != level || boundServant != servant) {
            proxy = createProxy(servant, level);
            boundServant = servant;
        }
        if (proxy == null) {
            return null;
        }
        proxy.deathTime = servant.deathTime;
        proxy.setPos(servant.getX(), servant.getY(), servant.getZ());
        proxy.yBodyRot = servant.yBodyRot;
        proxy.yBodyRotO = servant.yBodyRotO;
        proxy.yHeadRot = servant.yHeadRot;
        proxy.yHeadRotO = servant.yHeadRotO;
        return proxy;
    }

    private MutantSnowGolem createProxy(MutantSnowGolemServant servant, Level level) {
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(
                ResourceLocation.fromNamespaceAndPath("mutantmonsters", "mutant_snow_golem"));
        if (type == null) {
            return null;
        }
        @SuppressWarnings({"rawtypes", "unchecked"})
        EntityType<? extends MutantSnowGolem> golemType = (EntityType) type;
        MutantSnowGolem created = new MutantSnowGolem(golemType, level) {
            @Override
            public boolean isThrowing() {
                return boundServant != null && boundServant.isThrowing();
            }

            @Override
            public int getThrowingTick() {
                return boundServant == null ? 0 : boundServant.getThrowingTick();
            }
        };
        created.setNoAi(true);
        return created;
    }
}
