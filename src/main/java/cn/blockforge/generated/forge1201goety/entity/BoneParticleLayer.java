package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Predicate;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.renderer.BoneParticleFx;
import cn.blockforge.generated.goetysupreme.entity.BoneParticleLayer;

@OnlyIn(Dist.CLIENT)
public final class BoneParticleLayer<T extends LivingEntity, M extends HierarchicalModel<T>>
        extends RenderLayer<T, M> {

    @FunctionalInterface
    public interface Pose<M> {
        void apply(M model, PoseStack poseStack);
    }

    private final Pose<M> pose;
    private final Predicate<T> active;
    private final int slot;
    private final int count;
    private final double spread;
    private final double upward;
    private final double localY;
    private final ParticleOptions[] particles;

    public BoneParticleLayer(RenderLayerParent<T, M> parent, Pose<M> pose, Predicate<T> active,
            int slot, int count, double spread, double upward, double localY,
            ParticleOptions... particles) {
        super(parent);
        this.pose = pose;
        this.active = active;
        this.slot = slot;
        this.count = count;
        this.spread = spread;
        this.upward = upward;
        this.localY = localY;
        this.particles = particles;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {
        if (!this.active.test(entity)) {
            return;
        }
        poseStack.pushPose();
        this.pose.apply(this.getParentModel(), poseStack);
        if (this.localY != 0.0D) {
            poseStack.translate(0.0D, this.localY, 0.0D);
        }
        BoneParticleFx.emit(entity, poseStack, partialTick, this.slot, this.count,
                this.spread, this.upward, this.particles);
        poseStack.popPose();
    }
}
