package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.ModRenderType;
import com.Polarice3.Goety.utils.ModelPartPose;
import com.Polarice3.Goety.utils.ModelSnapshot;
import com.Polarice3.Goety.utils.ModelUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.AbstractWitherWarlordRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ClientIfritAfterimage;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordAfterimage;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;

@OnlyIn(Dist.CLIENT)
public final class ClientWarlordAfterimage {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/wither_warlord.png");

    private static final RenderType AFTERIMAGE = ModRenderType.entityTranslucentNoDepth(TEXTURE);

    private static final float INTERVAL = 1.5F;

    private static final float LIFESPAN = 4.5F;

    private static final int MAX_SNAPSHOTS = 32;

    private static final float MAX_ALPHA = 0.35F;

    private static final Map<Integer, Trail> TRAILS = new HashMap<>();

    private static final List<String> PART_NAMES = List.of(
            "all", "upper_body", "head", "netherite_helmet",
            "right_arm", "right_arm_swing", "saber", "sheath", "right_shoulder",
            "left_arm", "left_shoulder", "left_shoulder_cloth", "cape",
            "pants", "right_leg", "left_leg",
            "skirt_group", "skirt_left", "skirt_right");

    private static final class Trail {
        final List<Pair<Vec3, ModelSnapshot>> snapshots = new ArrayList<>();
        float lastTick;
    }

    private ClientWarlordAfterimage() {
    }

    public static void render(AbstractWitherWarlord entity, WitherWarlordModel<?> mainModel,
            WitherWarlordModel<?> shadowModel, PoseStack poseStack, MultiBufferSource buffer,
            int packedLight, float partialTick) {
        float age = entity.tickCount + partialTick;
        Trail trail = TRAILS.computeIfAbsent(entity.getId(), key -> new Trail());
        if (TRAILS.size() > 256) {
            TRAILS.clear();
            trail = TRAILS.computeIfAbsent(entity.getId(), key -> new Trail());
        }

        double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());

        if (entity.getSkill() == AbstractWitherWarlord.SKILL_GREAT_LEAP
                && !entity.isInvisible() && entity.deathTime <= 0
                && (trail.snapshots.isEmpty() || age - trail.lastTick > INTERVAL)) {
            float yaw = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
            Map<String, ModelPartPose> poses = ModelUtil.saveModelSnapshot(PART_NAMES,
                    name -> Optional.of(mainModel.part(name)));
            trail.snapshots.add(0, Pair.of(new Vec3(x, y, z), new ModelSnapshot(0.0F, yaw, age, poses)));
            trail.lastTick = age;
        }

        trail.snapshots.removeIf(pair -> age - pair.getSecond().timestamp() > LIFESPAN);
        while (trail.snapshots.size() > MAX_SNAPSHOTS) {
            trail.snapshots.remove(trail.snapshots.size() - 1);
        }
        if (trail.snapshots.isEmpty()) {
            return;
        }

        for (Pair<Vec3, ModelSnapshot> pair : trail.snapshots) {
            Vec3 at = pair.getFirst();
            ModelSnapshot snapshot = pair.getSecond();
            float alpha = (1.0F - Mth.clamp(age - snapshot.timestamp(), 0.0F, LIFESPAN) / LIFESPAN)
                    * MAX_ALPHA;
            if (alpha <= 0.0F) {
                continue;
            }
            ModelUtil.loadPoseFromSnapshot(snapshot.poses(),
                    name -> Optional.of(shadowModel.part(name)));
            poseStack.pushPose();
            poseStack.translate(at.x - x, at.y - y, at.z - z);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - snapshot.yRot()));
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            poseStack.scale(AbstractWitherWarlordRenderer.SCALE, AbstractWitherWarlordRenderer.SCALE,
                    AbstractWitherWarlordRenderer.SCALE);
            poseStack.translate(0.0D, -1.501D, 0.0D);
            VertexConsumer consumer = buffer.getBuffer(AFTERIMAGE);
            shadowModel.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, alpha);
            poseStack.popPose();
        }
    }

    public static void invalidate() {
        TRAILS.clear();
    }
}
