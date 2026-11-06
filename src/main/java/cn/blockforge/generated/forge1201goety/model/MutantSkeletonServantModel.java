package cn.blockforge.generated.goetysupreme.model;

import fuzs.mutantmonsters.animation.Animation;
import fuzs.mutantmonsters.client.model.MutantSkeletonModel;
import fuzs.mutantmonsters.world.entity.mutant.MutantSkeleton;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.util.MutantSkeletonHorns;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.model.MutantSkeletonServantModel;
import cn.blockforge.generated.goetysupreme.model.MutantSnowGolemServantModel;
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;

public final class MutantSkeletonServantModel extends EntityModel<MutantSkeletonServant> {
    private final MutantSkeletonModel delegate;
    private final ModelPart root;
    private final ModelPart horns = MutantSkeletonHorns.createLayer().bakeRoot();
    private final ModelPart[] headChain;
    private boolean horned;
    private MutantSkeletonServant boundServant;
    private MutantSkeleton proxy;

    public MutantSkeletonServantModel(ModelPart root, ModelPart crossbowRoot) {
        this.root = root;
        this.delegate = new MutantSkeletonModel(root, crossbowRoot);
        String[] path = {"base", "pelvis", "waist", "middle1", "middle2", "middle3", "neck", "head", "inner_head"};
        this.headChain = new ModelPart[path.length];
        ModelPart part = root;
        for (int i = 0; i < path.length; i++) {
            part = SafeModelParts.find(part, path[i]);
            headChain[i] = part;
        }
    }

    public MutantSkeletonModel getDelegate() {
        return delegate;
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

    @Override
    public void prepareMobModel(MutantSkeletonServant servant, float limbSwing, float limbSwingAmount,
            float partialTick) {
        MutantSkeleton skeleton = updateProxy(servant);
        if (skeleton != null) {
            delegate.prepareMobModel(skeleton, limbSwing, limbSwingAmount, partialTick);
        }
    }

    @Override
    public void setupAnim(MutantSkeletonServant servant, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        horned = servant.hasHornedAppearance();
        MutantSkeleton skeleton = updateProxy(servant);
        if (skeleton != null) {
            delegate.setupAnim(skeleton, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }

    @Override
    public void renderToBuffer(com.mojang.blaze3d.vertex.PoseStack poseStack,
            com.mojang.blaze3d.vertex.VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
            float red, float green, float blue, float alpha) {
        delegate.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        if (horned) {
            poseStack.pushPose();
            for (ModelPart part : headChain) {
                if (!part.visible) {
                    poseStack.popPose();
                    return;
                }
                part.translateAndRotate(poseStack);
            }
            horns.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        }
    }

    private MutantSkeleton updateProxy(MutantSkeletonServant servant) {
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

    private MutantSkeleton createProxy(MutantSkeletonServant servant, Level level) {
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(
                ResourceLocation.fromNamespaceAndPath("mutantmonsters", "mutant_skeleton"));
        if (type == null) {
            return null;
        }
        @SuppressWarnings({"rawtypes", "unchecked"})
        EntityType<? extends MutantSkeleton> skeletonType = (EntityType) type;
        MutantSkeleton created = new MutantSkeleton(skeletonType, level) {
            @Override
            public Animation getAnimation() {
                return boundServant == null ? Animation.NONE : boundServant.getAnimation();
            }

            @Override
            public int getAnimationTick() {
                return boundServant == null ? 0 : boundServant.getAnimationTick();
            }
        };
        created.setNoAi(true);
        return created;
    }
}
