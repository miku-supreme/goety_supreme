package cn.blockforge.generated.goetysupreme.renderer;

import cn.blockforge.generated.goetysupreme.entity.BreathAreaEntity;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import cn.blockforge.generated.goetysupreme.renderer.SprayBeamRenderer;

public abstract class SprayBeamRenderer<T extends Entity> extends EntityRenderer<T> {

    private static final float FORWARD_OFFSET = 0.4F;

    private static final float RING_STRIP_HALF_THICK = 0.045F;

    private static final float RING_STRIP_ARC_DEG = 4.5F;

    private static final float RING_SPIN_SPEED = 14.0F;

    private static final float FLICKER_FREQ = 1.4F;

    private static final int CONE_SEGMENTS = 4;

    private static final float CONE_WAVE_AMP = 0.09F;

    private static final float CONE_WAVE_FREQ = 1.6F;

    private static final float CONE_WAVE_SPEED = 3.0F;

    private static final float CONE_MAX_HALF_WIDTH = 0.09F;

    private static final float CONE_FLIGHT_FRAC = 1.0F;

    private static final float CONE_DRIFT_EXTRA = 0.0F;

    private static final float CONE_TRAIL_LEN_FRAC = 0.3F;

    private static final float CONE_IGNITE_SPREAD = 0.35F;

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotes", "textures/entity/beam/ray.png");
    private static final Vec3 WORLD_UP = new Vec3(0.0D, 1.0D, 0.0D);

    public SprayBeamRenderer(EntityRendererProvider.Context context) {
        super(context); // J:60-62
    }

    public abstract Entity beamCaster(T entity);

    public abstract float beamLife(T entity, float partialTick);

    public abstract int beamMaxLife(T entity);

    public abstract int circleStart(T entity);

    public abstract int circleStop(T entity);

    public abstract int stripeStart(T entity);

    public abstract int stripeStop(T entity);

    public abstract float maxReach(T entity);

    public abstract float spellAngle(T entity);

    public abstract int colorI(T entity);

    public abstract int colorII(T entity);

    public abstract int colorIII(T entity);

    public abstract int stripCount(T entity);

    public abstract float innerRadius(T entity);

    public abstract float outerRadius(T entity);

    public abstract float ringSpawnRadiusMult(T entity);

    public abstract float pointRadiusMul(T entity);

    public double originHeightFactor(T entity) {
        return 0.7D;
    }

    public boolean drawCone(T entity) {
        return true;
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, PoseStack poseStack,
            MultiBufferSource bufferIn, int packedLightIn) {
        Entity caster = this.beamCaster(entityIn);                                          // J:67-68
        Entity reference = caster != null ? caster : entityIn;                              // J:68
        Vec3 casterPos = new Vec3(                                                          // J:69
                Mth.lerp((double) partialTicks, reference.xo, reference.getX()),
                Mth.lerp((double) partialTicks, reference.yo, reference.getY()),
                Mth.lerp((double) partialTicks, reference.zo, reference.getZ()));
        Vec3 forward = reference.getViewVector(partialTicks);                               // J:70
        if (forward.lengthSqr() < 1.0E-6D) {                                                // J:71
            forward = new Vec3(0.0D, 0.0D, 1.0D);                                           // J:72
        }
        forward = forward.normalize();                                                      // J:74
        Vec3 right = forward.cross(WORLD_UP).normalize();                                   // J:74
        if (right.lengthSqr() < 1.0E-6D) {                                                  // J:74
            right = new Vec3(1.0D, 0.0D, 0.0D);                                             // J:75
        }
        Vec3 up = right.cross(forward).normalize();                                         // J:77
        Vec3 origin = casterPos.add(0.0D, (double) reference.getBbHeight()                   // J:78
                * this.originHeightFactor(entityIn), 0.0D)
                .add(forward.scale((double) FORWARD_OFFSET));                               // J:78
        Vec3 entityRenderPos = new Vec3(                                                    // J:79
                Mth.lerp((double) partialTicks, entityIn.xo, entityIn.getX()),
                Mth.lerp((double) partialTicks, entityIn.yo, entityIn.getY()),
                Mth.lerp((double) partialTicks, entityIn.zo, entityIn.getZ()));
        float life = this.beamLife(entityIn, partialTicks);                                 // J:80
        int circleStart = this.circleStart(entityIn);                                       // J:81
        int circleStop = this.circleStop(entityIn);                                         // J:82
        int stripeStart = this.stripeStart(entityIn);                                       // J:83
        int stripeStop = this.stripeStop(entityIn);                                         // J:84
        int maxLife = this.beamMaxLife(entityIn);                                           // J:85
        float globalFade = 1.0F;                                                            // J:86
        if (life > (float) stripeStop) {                                                    // J:87
            globalFade = 1.0F - Mth.clamp(                                                  // J:88
                    (life - (float) stripeStop) / (float) Math.max(1, maxLife - stripeStop),
                    0.0F, 1.0F);
        }
        if (globalFade > 0.001F) {                                                          // J:90
            VertexConsumer consumer = bufferIn.getBuffer(                                    // J:91
                    JerotesRenderType.glowDoubleSided(this.getTextureLocation(entityIn)));
            Matrix4f matrix = poseStack.last().pose();                                      // J:92
            Matrix3f normalMat = poseStack.last().normal();                                 // J:93
            if (life <= (float) circleStart) {                                              // J:94
                float radiusMul = this.computeRingRadiusMul(entityIn, life);                // J:95
                float t = Mth.clamp(life / (float) Math.max(1, circleStart), 0.0F, 1.0F);   // J:96
                float alphaMul = Mth.clamp(t * 1.5F, 0.0F, 1.0F);                           // J:97
                this.renderRings(entityIn, consumer, matrix, normalMat, entityRenderPos,    // J:98
                        origin, right, up, forward, life, radiusMul, alphaMul, globalFade);
            } else if (life <= (float) circleStop) {                                        // J:99
                float radiusMul = this.computeRingRadiusMul(entityIn, life);                // J:100
                this.renderRings(entityIn, consumer, matrix, normalMat, entityRenderPos,    // J:101
                        origin, right, up, forward, life, radiusMul, 1.0F, globalFade);
            } else if (life <= (float) stripeStart) {                                       // J:102
                float pulse = 0.85F + 0.15F * Mth.sin(life * 0.9F);                         // J:103
                this.renderRings(entityIn, consumer, matrix, normalMat, entityRenderPos,    // J:104
                        origin, right, up, forward, life, this.computeRingRadiusMul(entityIn, life),
                        pulse, globalFade);
            }
            if (this.drawCone(entityIn) && life >= (float) stripeStart) {
                float coneT = Mth.clamp((life - (float) stripeStart)
                        / (float) Math.max(1, stripeStop - stripeStart), 0.0F, 1.0F);
                this.renderCone(entityIn, consumer, matrix, normalMat, entityRenderPos,
                        origin, forward, right, up, coneT, life, globalFade);
            }
        }
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);  // J:107
    }

    private void renderRings(T entityIn, VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMat,
            Vec3 entityRenderPos, Vec3 origin, Vec3 right, Vec3 up, Vec3 forward,
            float life, float radiusMul, float alphaMul, float globalFade) {
        int stripCount = this.stripCount(entityIn);                                         // J:111
        float innerRadius = this.innerRadius(entityIn);                                     // J:112
        float outerRadius = this.outerRadius(entityIn);                                     // J:113
        int colorI = this.colorI(entityIn);                                                 // J:114
        int colorII = this.colorII(entityIn);                                               // J:115
        int colorIII = this.colorIII(entityIn);                                             // J:116
        int id = entityIn.getId();                                                          // J:117
        Vec3 thickDir = forward.scale((double) RING_STRIP_HALF_THICK);                      // J:118
        for (int i = 0; i < stripCount; ++i) {                                              // J:119
            boolean innerRing = (i & 1) == 0;                                               // J:120
            float baseRadius = innerRing ? innerRadius : outerRadius;                       // J:121
            float radius = baseRadius * radiusMul;                                          // J:122
            if (radius <= 0.001F) {                                                         // J:123
                continue;
            }
            float baseAngle = 360.0F * (float) i / (float) stripCount;                      // J:124
            float jitterStatic = (hash01(id, i, 1) - 0.5F) * 6.0F;                          // J:125
            float spinDir = innerRing ? 1.0F : -1.0F;                                       // J:126
            float spinMul = 0.85F + hash01(id, i, 2) * 0.3F;                                // J:127
            float angleDeg = baseAngle + jitterStatic + spinDir * spinMul * RING_SPIN_SPEED * life; // J:128
            float a0 = (float) Math.toRadians(angleDeg);                                    // J:129
            float a1 = (float) Math.toRadians(angleDeg + RING_STRIP_ARC_DEG);               // J:130
            Vec3 p0 = origin.add(right.scale((double) (Mth.cos(a0) * radius)))              // J:131
                    .add(up.scale((double) (Mth.sin(a0) * radius)));
            Vec3 p1 = origin.add(right.scale((double) (Mth.cos(a1) * radius)))              // J:132
                    .add(up.scale((double) (Mth.sin(a1) * radius)));
            float widthPulse = 0.8F + 0.2F * Mth.sin(life * 1.1F + hash01(id, i, 8) * 6.283F); // J:133
            Vec3 thick = thickDir.scale((double) widthPulse);                               // J:134
            Vec3 in0 = p0.subtract(thick);                                                  // J:135
            Vec3 out0 = p0.add(thick);                                                      // J:136
            Vec3 in1 = p1.subtract(thick);                                                  // J:137
            Vec3 out1 = p1.add(thick);                                                      // J:138
            float phase = hash01(id, i, 7) * 6.283F;                                        // J:139
            float flick = 0.8F + 0.2F * Mth.sin(life * FLICKER_FREQ + phase);               // J:140
            int alpha = (int) (Mth.clamp(alphaMul * flick, 0.0F, 1.0F) * globalFade * 200.0F); // J:141
            float colorT = ((angleDeg % 360.0F) + 360.0F) % 360.0F / 360.0F;                // J:142
            int rgb = BreathAreaEntity.stripColor(colorI, colorII, colorIII, colorT);       // J:143
            int r = rgb >> 16 & 0xFF;                                                       // J:144
            int g = rgb >> 8 & 0xFF;                                                        // J:145
            int b = rgb & 0xFF;                                                             // J:146
            this.addVertex(consumer, matrix, normalMat, in0.subtract(entityRenderPos),      // J:147
                    r, g, b, alpha, 0.0F, 0.0F);
            this.addVertex(consumer, matrix, normalMat, out0.subtract(entityRenderPos),     // J:148
                    r, g, b, alpha, 1.0F, 0.0F);
            this.addVertex(consumer, matrix, normalMat, out1.subtract(entityRenderPos),     // J:149
                    r, g, b, alpha, 1.0F, 1.0F);
            this.addVertex(consumer, matrix, normalMat, in1.subtract(entityRenderPos),      // J:150
                    r, g, b, alpha, 0.0F, 1.0F);
        }
    }

    private void renderCone(T entityIn, VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMat,
            Vec3 entityRenderPos, Vec3 origin, Vec3 forward, Vec3 right, Vec3 up,
            float coneT, float life, float globalFade) {
        int stripCount = this.stripCount(entityIn);                                         // J:155
        float range = this.maxReach(entityIn);                                              // J:156
        float halfAngleRad = (float) Math.toRadians(this.spellAngle(entityIn) * 0.5F);      // J:157
        float baseRadius = range * (float) Math.tan(halfAngleRad);                          // J:158
        int colorI = this.colorI(entityIn);                                                 // J:159
        int colorII = this.colorII(entityIn);                                               // J:160
        int colorIII = this.colorIII(entityIn);                                             // J:161
        int id = entityIn.getId();                                                          // J:162
        for (int i = 0; i < stripCount; ++i) {                                              // J:163
            float ignite = hash01(id, i, 3) * CONE_IGNITE_SPREAD;                           // J:165
            float local = Mth.clamp((coneT - ignite) / Math.max(1.0E-4F, 1.0F - ignite),    // J:166
                    0.0F, 1.0F);
            if (local <= 0.0F) {                                                            // J:167
                continue;
            }
            float edgeBias = (float) Math.pow(hash01(id, i, 4), 0.4F);                      // J:168
            float rotAngle = hash01(id, i, 5) * 360.0F;                                     // J:169
            float lenVar = 0.75F + hash01(id, i, 6) * 0.5F;                                 // J:170
            float ra = (float) Math.toRadians(rotAngle);                                    // J:171
            Vec3 radialDir = right.scale((double) Mth.cos(ra))                              // J:172
                    .add(up.scale((double) Mth.sin(ra)));
            Vec3 endPoint = origin.add(forward.scale((double) (range * lenVar)))            // J:173
                    .add(radialDir.scale((double) (baseRadius * edgeBias * lenVar)));
            float travel = Mth.clamp(local / CONE_FLIGHT_FRAC, 0.0F, 1.0F);                 // J:174
            float posFrac = easeOutCubic(travel);                                           // J:175
            if (local > 1.0F) {                                                             // J:176
                float driftT = (local - 1.0F) / 0.0F;                                       // J:177
                posFrac += driftT * CONE_DRIFT_EXTRA;                                       // J:178
            }
            float headFrac = Math.max(0.001F, posFrac);                                     // J:180
            float tailFrac = Math.max(0.0F, headFrac - CONE_TRAIL_LEN_FRAC);                // J:181
            float headFracClamped = Mth.clamp(headFrac, 0.0F, 1.0F);                        // J:182
            float tailFracClamped = Mth.clamp(tailFrac, 0.0F, 1.0F);                        // J:183
            Vec3 head = lerpVec(origin, endPoint, (double) headFrac);                       // J:184
            Vec3 tail = lerpVec(origin, endPoint, (double) tailFrac);                       // J:185
            Vec3 dir = endPoint.subtract(origin).normalize();                               // J:186
            Vec3 sideRef = Math.abs(dir.y) < 0.9D                                           // J:187
                    ? new Vec3(0.0D, 1.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
            Vec3 waveAxis = dir.cross(sideRef).normalize();                                 // J:188
            Vec3 sideAxis = dir.cross(waveAxis).normalize();                                // J:189
            float alphaIn = Mth.clamp(travel * 2.5F, 0.0F, 1.0F);                           // J:190
            float alphaOut = 1.0F - Mth.clamp((local - 0.55F) / 0.45F, 0.0F, 1.0F);        // J:191
            float alpha = alphaIn * alphaOut * globalFade;                                  // J:191
            if (alpha <= 0.005F) {                                                          // J:192
                continue;
            }
            float widthScale = 0.6F + hash01(id, i, 9) * 0.6F;                              // J:193
            float wavePhase = hash01(id, i, 10) * 6.283F;                                   // J:194
            float timeSeed = life * CONE_WAVE_SPEED;                                        // J:195
            int rHead = this.lerpColorChannel(colorI, colorII, colorIII, headFracClamped, 16); // J:196
            int gHead = this.lerpColorChannel(colorI, colorII, colorIII, headFracClamped, 8);  // J:197
            int bHead = this.lerpColorChannel(colorI, colorII, colorIII, headFracClamped, 0);  // J:198
            int rTail = this.lerpColorChannel(colorI, colorII, colorIII, tailFracClamped, 16); // J:199
            int gTail = this.lerpColorChannel(colorI, colorII, colorIII, tailFracClamped, 8);  // J:200
            int bTail = this.lerpColorChannel(colorI, colorII, colorIII, tailFracClamped, 0);  // J:201
            this.drawWavyRibbon(consumer, matrix, normalMat, entityRenderPos, tail, head,      // J:202
                    waveAxis, sideAxis, CONE_MAX_HALF_WIDTH * widthScale, CONE_WAVE_AMP,
                    CONE_WAVE_FREQ, wavePhase, timeSeed, alpha, alpha * 0.15F,
                    CONE_SEGMENTS, rTail, gTail, bTail, rHead, gHead, bHead);
        }
    }

    private void drawWavyRibbon(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMat,
            Vec3 entityRenderPos, Vec3 start, Vec3 end, Vec3 waveAxis, Vec3 sideAxis,
            float maxHalfWidth, float waveAmp, float waveFreq, float wavePhase, float timeSeed,
            float alphaHead, float alphaTail, int segments,
            int rTail, int gTail, int bTail, int rHead, int gHead, int bHead) {
        Vec3 dir = end.subtract(start);                                                     // J:208
        if (dir.lengthSqr() < 1.0E-8D) {                                                    // J:209
            return;                                                                         // J:210
        }
        Vec3[] centerLine = new Vec3[segments + 1];                                         // J:212
        float[] widths = new float[segments + 1];                                           // J:213
        for (int s = 0; s <= segments; ++s) {                                               // J:214
            float t = (float) s / (float) segments;                                         // J:215
            Vec3 base = start.add(dir.scale((double) t));                                   // J:216
            float envelope = Mth.sin(t * (float) Math.PI);                                  // J:217
            float wave = Mth.sin((float) ((double) (t * waveFreq) * Math.PI * 2.0D          // J:218
                    + (double) wavePhase + (double) timeSeed)) * waveAmp * envelope;
            float driftSecondary = Mth.cos((float) ((double) (t * waveFreq) * Math.PI       // J:219
                    * (double) 1.3F + (double) (wavePhase * 1.7F)
                    + (double) (timeSeed * 0.7F))) * waveAmp * 0.4F * envelope;
            centerLine[s] = base.add(waveAxis.scale((double) wave))                         // J:220
                    .add(sideAxis.scale((double) driftSecondary));
            widths[s] = maxHalfWidth * (float) Math.pow(                                    // J:221
                    Mth.sin(Mth.clamp(t, 0.02F, 0.98F) * (float) Math.PI), 0.7F);
        }
        for (int s = 0; s < segments; ++s) {                                                // J:223
            Vec3 c0 = centerLine[s];                                                        // J:224
            Vec3 c1 = centerLine[s + 1];                                                    // J:225
            float t0 = (float) s / (float) segments;                                        // J:226
            float t1 = (float) (s + 1) / (float) segments;                                  // J:227
            float w0 = widths[s];                                                           // J:228
            float w1 = widths[s + 1];                                                       // J:229
            float a0 = Mth.lerp(t0, alphaTail, alphaHead);                                  // J:230
            float a1 = Mth.lerp(t1, alphaTail, alphaHead);                                  // J:231
            int r0 = (int) Mth.lerp(t0, (float) rTail, (float) rHead);                      // J:232
            int g0 = (int) Mth.lerp(t0, (float) gTail, (float) gHead);                      // J:233
            int b0 = (int) Mth.lerp(t0, (float) bTail, (float) bHead);                      // J:234
            int r1 = (int) Mth.lerp(t1, (float) rTail, (float) rHead);                      // J:235
            int g1 = (int) Mth.lerp(t1, (float) gTail, (float) gHead);                      // J:236
            int b1 = (int) Mth.lerp(t1, (float) bTail, (float) bHead);                      // J:237
            Vec3 p0a = c0.subtract(waveAxis.scale((double) w0));                            // J:238
            Vec3 p0b = c0.add(waveAxis.scale((double) w0));                                 // J:239
            Vec3 p1a = c1.subtract(waveAxis.scale((double) w1));                            // J:240
            Vec3 p1b = c1.add(waveAxis.scale((double) w1));                                 // J:241
            this.addVertex(consumer, matrix, normalMat, p0a.subtract(entityRenderPos),      // J:242
                    r0, g0, b0, (int) (a0 * 200.0F), 0.0F, t0);
            this.addVertex(consumer, matrix, normalMat, p0b.subtract(entityRenderPos),      // J:243
                    r0, g0, b0, (int) (a0 * 200.0F), 1.0F, t0);
            this.addVertex(consumer, matrix, normalMat, p1b.subtract(entityRenderPos),      // J:244
                    r1, g1, b1, (int) (a1 * 200.0F), 1.0F, t1);
            this.addVertex(consumer, matrix, normalMat, p1a.subtract(entityRenderPos),      // J:245
                    r1, g1, b1, (int) (a1 * 200.0F), 0.0F, t1);
        }
    }

    private static Vec3 lerpVec(Vec3 a, Vec3 b, double t) {
        return new Vec3(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t, a.z + (b.z - a.z) * t);
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

    private int lerpColorChannel(int c1, int c2, int c3, float t, int shift) {
        int ch1 = c1 >> shift & 0xFF;
        int ch2 = c2 >> shift & 0xFF;
        int ch3 = c3 >> shift & 0xFF;
        if (t < 0.5F) {
            return (int) Mth.lerp(t / 0.5F, (float) ch1, (float) ch2);
        }
        return (int) Mth.lerp((t - 0.5F) / 0.5F, (float) ch2, (float) ch3);
    }

    private void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normal, Vec3 pos,
            int r, int g, int b, int a, float u, float v) {
        consumer.vertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(0xF000F0) // J:277 的 0xF000F0 = 15728880
                .normal(normal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(T e, Frustum frustum, double d, double d2, double d3) {
        if (!e.shouldRender(d, d2, d3)) {                                                   // J:286
            return false;
        }
        if (e.noCulling) {                                                                  // J:289
            return true;
        }
        AABB aabb = e.getBoundingBoxForCulling().inflate(128.0D);                           // J:292
        if (aabb.hasNaN() || aabb.getSize() == 0.0D) {                                      // J:293
            aabb = new AABB(e.getX() - 128.0D, e.getY() - 128.0D, e.getZ() - 128.0D,         // J:294
                    e.getX() + 128.0D, e.getY() + 128.0D, e.getZ() + 128.0D);
        }
        return frustum.isVisible(aabb);                                                     // J:296
    }

    private float computeRingRadiusMul(T entity, float life) {
        int start = this.circleStart(entity);
        int stop = this.circleStop(entity);
        return BreathAreaEntity.computeRingRadiusMul(life, start, stop,
                this.ringSpawnRadiusMult(entity), this.pointRadiusMul(entity));
    }
}

