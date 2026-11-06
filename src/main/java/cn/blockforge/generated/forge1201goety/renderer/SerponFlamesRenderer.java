package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;
import cn.blockforge.generated.goetysupreme.renderer.SerponFlamesRenderer;

public class SerponFlamesRenderer extends EntityRenderer<SerponFlamesEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotes", "textures/entity/beam/ray.png");

    private static final int MAX_LIFE = 20;
    private static final int USE_LIFE = 10;
    private static final int CIRCLE_START = 5;
    private static final int STRIPE_START = 3;
    private static final int STRIPE_STOP = 15;
    private static final int STRIP_COUNT = 10;
    private static final float INNER_RADIUS = 0.5F;
    private static final float OUTER_RADIUS = 0.25F;
    private static final float RING_SPAWN_MULT = 1.6F;
    private static final float POINT_RADIUS_MUL = 0.08F;
    private static final float RING_ARC_DEG = 4.5F;
    private static final float RING_SPIN_SPEED = 14.0F;
    private static final float RING_HALF_THICK = 0.045F;
    private static final float CONE_HALF_WIDTH = 0.09F;
    private static final float CONE_TRAIL_LEN_FRAC = 0.3F;
    private static final float FORWARD_OFFSET = 0.4F;

    private static final int COLOR_I = 0xFF8114;
    private static final int COLOR_II = 0xFFE2CB;
    private static final int COLOR_III = 0xFF1C14;

    public SerponFlamesRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SerponFlamesEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        Entity owner = entity.getOwner();
        if (owner != null) {
            this.renderSpray(entity, owner, partialTick, poseStack, buffer);
        }
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private void renderSpray(SerponFlamesEntity entity, Entity owner, float partialTick,
            PoseStack poseStack, MultiBufferSource buffer) {
        Vec3 casterPos = owner.getPosition(partialTick);
        Vec3 forward = owner.getViewVector(partialTick);
        if (forward.lengthSqr() < 1.0E-6D) {
            forward = new Vec3(0.0D, 0.0D, 1.0D);
        }
        forward = forward.normalize();
        Vec3 right = forward.cross(new Vec3(0.0D, 1.0D, 0.0D));
        if (right.lengthSqr() < 1.0E-6D) {
            right = new Vec3(1.0D, 0.0D, 0.0D);
        }
        right = right.normalize();
        Vec3 up = right.cross(forward).normalize();
        Vec3 origin = casterPos.add(0.0D, owner.getBbHeight() * 0.7D, 0.0D)
                .add(forward.scale(FORWARD_OFFSET));
        Vec3 entityPos = entity.getPosition(partialTick);

        float life = entity.tickCount + partialTick;
        float globalFade = 1.0F;
        if (life > (float) STRIPE_STOP) {
            globalFade = 1.0F - Mth.clamp((life - STRIPE_STOP)
                    / (float) Math.max(1, MAX_LIFE - STRIPE_STOP), 0.0F, 1.0F);
        }
        if (globalFade <= 0.001F) {
            return;
        }

        float range = (float) entity.getReach();
        float halfAngleRad = (float) Math.toRadians(entity.getSpellAngle() * 0.5F);
        float baseRadius = range * (float) Math.tan(halfAngleRad);
        int id = entity.getId();
        Matrix4f matrix = poseStack.last().pose();
        VertexConsumer consumer = buffer.getBuffer(RenderType.lightning());

        if (life <= (float) STRIPE_STOP) {
            float radiusMul = this.computeRingRadiusMul(life);
            float alphaMul;
            if (life <= (float) CIRCLE_START) {
                alphaMul = Mth.clamp(life / (float) CIRCLE_START * 1.5F, 0.0F, 1.0F);
            } else if (life <= (float) USE_LIFE) {
                alphaMul = 1.0F;
            } else {
                alphaMul = 0.85F + 0.15F * Mth.sin(life * 0.9F);
            }
            this.renderRings(consumer, matrix, entityPos, origin, right, up, forward,
                    life, radiusMul, alphaMul, globalFade, id);
        }

        if (life >= (float) STRIPE_START) {
            float coneT = Mth.clamp((life - STRIPE_START)
                    / (float) Math.max(1, STRIPE_STOP - STRIPE_START), 0.0F, 1.0F);
            this.renderCone(consumer, matrix, entityPos, origin, forward, right, up,
                    coneT, life, globalFade, range, baseRadius, id);
        }
    }

    private void renderRings(VertexConsumer consumer, Matrix4f matrix, Vec3 entityPos,
            Vec3 origin, Vec3 right, Vec3 up, Vec3 forward,
            float life, float radiusMul, float alphaMul, float globalFade, int id) {
        Vec3 thickDir = forward.scale(RING_HALF_THICK);
        for (int i = 0; i < STRIP_COUNT; ++i) {
            boolean innerRing = (i & 1) == 0;
            float baseRadius = innerRing ? INNER_RADIUS : OUTER_RADIUS;
            float radius = baseRadius * radiusMul;
            if (radius <= 0.001F) {
                continue;
            }
            float baseAngle = 360.0F * (float) i / (float) STRIP_COUNT;
            float jitterStatic = (hash01(id, i, 1) - 0.5F) * 6.0F;
            float spinDir = innerRing ? 1.0F : -1.0F;
            float spinMul = 0.85F + hash01(id, i, 2) * 0.3F;
            float angleDeg = baseAngle + jitterStatic + spinDir * spinMul * RING_SPIN_SPEED * life;
            float a0 = (float) Math.toRadians(angleDeg);
            float a1 = (float) Math.toRadians(angleDeg + RING_ARC_DEG);
            Vec3 p0 = origin.add(right.scale(Mth.cos(a0) * radius)).add(up.scale(Mth.sin(a0) * radius));
            Vec3 p1 = origin.add(right.scale(Mth.cos(a1) * radius)).add(up.scale(Mth.sin(a1) * radius));
            float widthPulse = 0.8F + 0.2F * Mth.sin(life * 1.1F + hash01(id, i, 8) * 6.283F);
            Vec3 thick = thickDir.scale(widthPulse);
            Vec3 in0 = p0.subtract(thick);
            Vec3 out0 = p0.add(thick);
            Vec3 in1 = p1.subtract(thick);
            Vec3 out1 = p1.add(thick);
            float phase = hash01(id, i, 7) * 6.283F;
            float flick = 0.8F + 0.2F * Mth.sin(life * 1.4F + phase);
            int alpha = (int) (Mth.clamp(alphaMul * flick, 0.0F, 1.0F) * globalFade * 200.0F);
            if (alpha <= 1) {
                continue;
            }
            float colorT = ((angleDeg % 360.0F) + 360.0F) % 360.0F / 360.0F;
            int r = lerpColorChannel(COLOR_I, COLOR_II, COLOR_III, colorT, 16);
            int g = lerpColorChannel(COLOR_I, COLOR_II, COLOR_III, colorT, 8);
            int b = lerpColorChannel(COLOR_I, COLOR_II, COLOR_III, colorT, 0);
            this.addVertex(consumer, matrix, in0.subtract(entityPos), r, g, b, alpha);
            this.addVertex(consumer, matrix, out0.subtract(entityPos), r, g, b, alpha);
            this.addVertex(consumer, matrix, out1.subtract(entityPos), r, g, b, alpha);
            this.addVertex(consumer, matrix, in1.subtract(entityPos), r, g, b, alpha);
        }
    }

    private void renderCone(VertexConsumer consumer, Matrix4f matrix, Vec3 entityPos,
            Vec3 origin, Vec3 forward, Vec3 right, Vec3 up,
            float coneT, float life, float globalFade, float range, float baseRadius, int id) {
        for (int i = 0; i < STRIP_COUNT; ++i) {
            float ignite = hash01(id, i, 3) * 0.35F;
            float local = Mth.clamp((coneT - ignite) / Math.max(1.0E-4F, 1.0F - ignite), 0.0F, 1.0F);
            if (local <= 0.0F) {
                continue;
            }
            float edgeBias = (float) Math.pow(hash01(id, i, 4), 0.4F);
            float rotAngle = hash01(id, i, 5) * 360.0F;
            float lenVar = 0.75F + hash01(id, i, 6) * 0.5F;
            float ra = (float) Math.toRadians(rotAngle);
            Vec3 radialDir = right.scale(Mth.cos(ra)).add(up.scale(Mth.sin(ra)));
            Vec3 endPoint = origin.add(forward.scale(range * lenVar))
                    .add(radialDir.scale(baseRadius * edgeBias * lenVar));
            float travel = Mth.clamp(local, 0.0F, 1.0F);
            float headFrac = Math.max(0.001F, easeOutCubic(travel));
            float tailFrac = Math.max(0.0F, headFrac - CONE_TRAIL_LEN_FRAC);
            Vec3 head = lerpVec(origin, endPoint, headFrac);
            Vec3 tail = lerpVec(origin, endPoint, tailFrac);
            Vec3 dir = endPoint.subtract(origin).normalize();
            Vec3 sideRef = Math.abs(dir.y) < 0.9D ? new Vec3(0.0D, 1.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
            Vec3 waveAxis = dir.cross(sideRef).normalize();
            Vec3 sideAxis = dir.cross(waveAxis).normalize();
            float alphaIn = Mth.clamp(travel * 2.5F, 0.0F, 1.0F);
            float alphaOut = 1.0F - Mth.clamp((local - 0.55F) / 0.45F, 0.0F, 1.0F);
            float alpha = alphaIn * alphaOut * globalFade;
            if (alpha <= 0.005F) {
                continue;
            }
            float widthScale = 0.6F + hash01(id, i, 9) * 0.6F;
            float wavePhase = hash01(id, i, 10) * 6.283F;
            float timeSeed = life * 3.0F;
            int rHead = lerpColorChannel(COLOR_I, COLOR_II, COLOR_III, headFrac, 16);
            int gHead = lerpColorChannel(COLOR_I, COLOR_II, COLOR_III, headFrac, 8);
            int bHead = lerpColorChannel(COLOR_I, COLOR_II, COLOR_III, headFrac, 0);
            int rTail = lerpColorChannel(COLOR_I, COLOR_II, COLOR_III, tailFrac, 16);
            int gTail = lerpColorChannel(COLOR_I, COLOR_II, COLOR_III, tailFrac, 8);
            int bTail = lerpColorChannel(COLOR_I, COLOR_II, COLOR_III, tailFrac, 0);
            this.drawWavyRibbon(consumer, matrix, entityPos, tail, head, waveAxis, sideAxis,
                    CONE_HALF_WIDTH * widthScale, wavePhase, timeSeed, alpha, alpha * 0.15F,
                    rTail, gTail, bTail, rHead, gHead, bHead);
        }
    }

    private void drawWavyRibbon(VertexConsumer consumer, Matrix4f matrix, Vec3 entityPos,
            Vec3 start, Vec3 end, Vec3 waveAxis, Vec3 sideAxis,
            float maxHalfWidth, float wavePhase, float timeSeed, float alphaHead, float alphaTail,
            int rTail, int gTail, int bTail, int rHead, int gHead, int bHead) {
        int segments = 4;
        Vec3 dir = end.subtract(start);
        if (dir.lengthSqr() < 1.0E-8D) {
            return;
        }
        Vec3[] centerLine = new Vec3[segments + 1];
        float[] widths = new float[segments + 1];
        for (int s = 0; s <= segments; ++s) {
            float t = (float) s / (float) segments;
            Vec3 base = start.add(dir.scale(t));
            float envelope = Mth.sin(t * (float) Math.PI);
            float wave = Mth.sin((float) (t * 1.6F * Math.PI * 2.0 + wavePhase + timeSeed)) * 0.09F * envelope;
            float driftSecondary = Mth.cos((float) (t * 1.6F * Math.PI * 1.3F + wavePhase * 1.7F + timeSeed * 0.7F))
                    * 0.09F * 0.4F * envelope;
            centerLine[s] = base.add(waveAxis.scale(wave)).add(sideAxis.scale(driftSecondary));
            widths[s] = maxHalfWidth * (float) Math.pow(Mth.sin(Mth.clamp(t, 0.02F, 0.98F) * (float) Math.PI), 0.7F);
        }
        for (int s = 0; s < segments; ++s) {
            Vec3 c0 = centerLine[s];
            Vec3 c1 = centerLine[s + 1];
            float t0 = (float) s / (float) segments;
            float t1 = (float) (s + 1) / (float) segments;
            float w0 = widths[s];
            float w1 = widths[s + 1];
            float a0 = Mth.lerp(t0, alphaTail, alphaHead);
            float a1 = Mth.lerp(t1, alphaTail, alphaHead);
            if (a0 <= 0.003F && a1 <= 0.003F) {
                continue;
            }
            int r0 = (int) Mth.lerp(t0, (float) rTail, (float) rHead);
            int g0 = (int) Mth.lerp(t0, (float) gTail, (float) gHead);
            int b0 = (int) Mth.lerp(t0, (float) bTail, (float) bHead);
            int r1 = (int) Mth.lerp(t1, (float) rTail, (float) rHead);
            int g1 = (int) Mth.lerp(t1, (float) gTail, (float) gHead);
            int b1 = (int) Mth.lerp(t1, (float) bTail, (float) bHead);
            Vec3 p0a = c0.subtract(waveAxis.scale(w0));
            Vec3 p0b = c0.add(waveAxis.scale(w0));
            Vec3 p1a = c1.subtract(waveAxis.scale(w1));
            Vec3 p1b = c1.add(waveAxis.scale(w1));
            this.addVertex(consumer, matrix, p0a.subtract(entityPos), r0, g0, b0, (int) (a0 * 200.0F));
            this.addVertex(consumer, matrix, p0b.subtract(entityPos), r0, g0, b0, (int) (a0 * 200.0F));
            this.addVertex(consumer, matrix, p1b.subtract(entityPos), r1, g1, b1, (int) (a1 * 200.0F));
            this.addVertex(consumer, matrix, p1a.subtract(entityPos), r1, g1, b1, (int) (a1 * 200.0F));
        }
    }

    private float computeRingRadiusMul(float life) {
        if (life <= (float) CIRCLE_START) {
            float t = Mth.clamp(life / (float) Math.max(1, CIRCLE_START), 0.0F, 1.0F);
            return Mth.lerp(easeOutQuad(t), RING_SPAWN_MULT, 1.0F);
        }
        if (life <= (float) USE_LIFE) {
            float t = Mth.clamp((life - CIRCLE_START)
                    / (float) Math.max(1, USE_LIFE - CIRCLE_START), 0.0F, 1.0F);
            return Mth.lerp(t * t, 1.0F, POINT_RADIUS_MUL);
        }
        return POINT_RADIUS_MUL;
    }

    private void addVertex(VertexConsumer consumer, Matrix4f matrix, Vec3 pos, int r, int g, int b, int a) {
        consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
                .color(r, g, b, Mth.clamp(a, 0, 255))
                .endVertex();
    }

    private static Vec3 lerpVec(Vec3 a, Vec3 b, float t) {
        return new Vec3(
                a.x + (b.x - a.x) * t,
                a.y + (b.y - a.y) * t,
                a.z + (b.z - a.z) * t);
    }

    private static float easeOutQuad(float t) {
        return 1.0F - (1.0F - t) * (1.0F - t);
    }

    private static float easeOutCubic(float t) {
        float f = t - 1.0F;
        return f * f * f + 1.0F;
    }

    private static float hash01(int id, int i, int salt) {
        int h = id * 374761393 + i * 668265263 + salt * -1640531527;
        h = (h ^ h >>> 15) * -2048144789;
        h = (h ^ h >>> 13) * -1028477387;
        h ^= h >>> 16;
        return (float) (h & 0xFFFFFF) / 1.6777215E7F;
    }

    private static int lerpColorChannel(int c1, int c2, int c3, float t, int shift) {
        int ch1 = c1 >> shift & 0xFF;
        int ch2 = c2 >> shift & 0xFF;
        int ch3 = c3 >> shift & 0xFF;
        if (t < 0.5F) {
            return (int) Mth.lerp(t / 0.5F, (float) ch1, (float) ch2);
        }
        return (int) Mth.lerp((t - 0.5F) / 0.5F, (float) ch2, (float) ch3);
    }

    @Override
    public ResourceLocation getTextureLocation(SerponFlamesEntity entity) {
        return TEXTURE;
    }
}
