package cn.blockforge.generated.goetysupreme.renderer;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import cn.blockforge.generated.goetysupreme.renderer.GolemClip;
import cn.blockforge.generated.goetysupreme.util.IfritAnimator;
import cn.blockforge.generated.goetysupreme.util.RagingMonstrosityAnimator;
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;

public final class GolemClip {

    public static final class Frame {
        final float tick;
        float rotX;
        float rotY;
        float rotZ;
        float offX;
        float offY;
        float offZ;
        float scaleX = 1.0F;
        float scaleY = 1.0F;
        float scaleZ = 1.0F;
        boolean hasRot;

        boolean smooth;

        Frame(float tick) {
            this.tick = tick;
        }

        public Frame rot(float x, float y, float z) {
            this.rotX = x;
            this.rotY = y;
            this.rotZ = z;
            this.hasRot = true;
            return this;
        }

        public Frame offset(float x, float y, float z) {
            this.offX = x;
            this.offY = y;
            this.offZ = z;
            return this;
        }

        public Frame scale(float x, float y, float z) {
            this.scaleX = x;
            this.scaleY = y;
            this.scaleZ = z;
            return this;
        }

        public Frame smooth(boolean value) {
            this.smooth = value;
            return this;
        }
    }

    public static final class Bone {
        final String bone;
        final List<Frame> frames = new ArrayList<>();

        Bone(String bone) {
            this.bone = bone;
        }

        public Bone frame(float tick, float rotX, float rotY, float rotZ) {
            frames.add(new Frame(tick).rot(rotX, rotY, rotZ));
            return this;
        }

        public Bone frame(float tick, float rotX, float rotY, float rotZ, float offX, float offY, float offZ) {
            frames.add(new Frame(tick).rot(rotX, rotY, rotZ).offset(offX, offY, offZ));
            return this;
        }

        public Bone frame(float tick, float rotX, float rotY, float rotZ,
                float offX, float offY, float offZ, boolean smooth) {
            frames.add(new Frame(tick).rot(rotX, rotY, rotZ).offset(offX, offY, offZ).smooth(smooth));
            return this;
        }
    }

    @FunctionalInterface
    public interface BoneLookup {
        ModelPart find(String name);
    }

    public static final class Track {
        private final List<Bone> bones = new ArrayList<>();

        public Bone bone(String name) {
            Bone b = new Bone(name);
            bones.add(b);
            return b;
        }

        public boolean isEmpty() {
            return bones.isEmpty();
        }

        public void apply(BoneLookup lookup, float tick) {
            for (Bone bone : bones) {
                ModelPart part = lookup.find(bone.bone);
                if (part == null || part == SafeModelParts.dummy()) {
                    continue;
                }
                sample(bone.frames, tick, part);
            }
        }

        public void apply(BoneLookup lookup, float tick, float weight) {
            for (Bone bone : bones) {
                ModelPart part = lookup.find(bone.bone);
                if (part == null || part == SafeModelParts.dummy()) {
                    continue;
                }
                sampleWeighted(bone.frames, tick, part, weight);
            }
        }

        public void applyOnly(BoneLookup lookup, float tick, float weight,
                java.util.function.Predicate<String> filter) {
            for (Bone bone : bones) {
                if (!filter.test(bone.bone)) {
                    continue;
                }
                ModelPart part = lookup.find(bone.bone);
                if (part == null || part == SafeModelParts.dummy()) {
                    continue;
                }
                sampleWeighted(bone.frames, tick, part, weight);
            }
        }
    }

    public final float lengthTicks;

    public final boolean loop;
    private final Track track = new Track();

    public GolemClip(float lengthTicks) {
        this(lengthTicks, false);
    }

    public GolemClip(float lengthTicks, boolean loop) {
        this.lengthTicks = lengthTicks;
        this.loop = loop;
    }

    public Track track() {
        return track;
    }

    public void apply(BoneLookup lookup, float tick) {
        track.apply(lookup, Math.max(0.0F, Math.min(tick, lengthTicks)));
    }

    public void apply(BoneLookup lookup, float tick, float weight) {
        if (weight >= 1.0F) {
            apply(lookup, tick);
            return;
        }
        if (weight <= 0.0F) {
            return;
        }
        track.apply(lookup, Math.max(0.0F, Math.min(tick, lengthTicks)), weight);
    }

    public void applyOnly(BoneLookup lookup, float tick, float weight,
            java.util.function.Predicate<String> filter) {
        if (weight <= 0.0F) {
            return;
        }
        track.applyOnly(lookup, Math.max(0.0F, Math.min(tick, lengthTicks)), Math.min(weight, 1.0F),
                filter);
    }

    public static void sample(List<Frame> frames, float tick, ModelPart part) {
        if (frames.isEmpty()) {
            return;
        }
        if (frames.size() == 1 || tick <= frames.get(0).tick) {
            write(frames.get(0), part);
            return;
        }
        if (tick >= frames.get(frames.size() - 1).tick) {
            write(frames.get(frames.size() - 1), part);
            return;
        }
        int i = segment(frames, tick);
        Frame a = frames.get(i);
        Frame b = frames.get(i + 1);
        float span = b.tick - a.tick;
        float u = span <= 0.0F ? 1.0F : (tick - a.tick) / span;
        if (!a.smooth || frames.size() < 3) {
            writeMix(a, b, u, part);
            return;
        }
        Frame prev = i > 0 ? frames.get(i - 1) : a;
        Frame next = i + 2 < frames.size() ? frames.get(i + 2) : b;
        writeCatmullRom(prev, a, b, next, u, part);
    }

    public static void sampleWeighted(List<Frame> frames, float tick, ModelPart part, float weight) {
        if (frames.isEmpty()) {
            return;
        }
        if (frames.size() == 1 || tick <= frames.get(0).tick) {
            writeWeighted(frames.get(0), part, weight);
            return;
        }
        if (tick >= frames.get(frames.size() - 1).tick) {
            writeWeighted(frames.get(frames.size() - 1), part, weight);
            return;
        }
        int i = segment(frames, tick);
        Frame a = frames.get(i);
        Frame b = frames.get(i + 1);
        float span = b.tick - a.tick;
        float u = span <= 0.0F ? 1.0F : (tick - a.tick) / span;
        writeWeightedMix(a, b, u, part, weight);
    }

    private static int segment(List<Frame> frames, float tick) {
        int lo = 0;
        int hi = frames.size() - 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (frames.get(mid).tick < tick) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return Math.max(0, lo - 1);
    }

    private static void writeMix(Frame a, Frame b, float u, ModelPart part) {
        writeValues(mix(a.rotX, b.rotX, u), mix(a.rotY, b.rotY, u), mix(a.rotZ, b.rotZ, u),
                mix(a.offX, b.offX, u), mix(a.offY, b.offY, u), mix(a.offZ, b.offZ, u),
                mix(a.scaleX, b.scaleX, u), mix(a.scaleY, b.scaleY, u), mix(a.scaleZ, b.scaleZ, u),
                true, part);
    }

    private static void writeWeightedMix(Frame a, Frame b, float u, ModelPart part, float weight) {
        writeWeightedValues(mix(a.rotX, b.rotX, u), mix(a.rotY, b.rotY, u), mix(a.rotZ, b.rotZ, u),
                mix(a.offX, b.offX, u), mix(a.offY, b.offY, u), mix(a.offZ, b.offZ, u),
                mix(a.scaleX, b.scaleX, u), mix(a.scaleY, b.scaleY, u), mix(a.scaleZ, b.scaleZ, u),
                true, part, weight);
    }

    private static void writeCatmullRom(Frame p0, Frame p1, Frame p2, Frame p3, float u, ModelPart part) {
        writeValues(catmullRom(p0.rotX, p1.rotX, p2.rotX, p3.rotX, u),
                catmullRom(p0.rotY, p1.rotY, p2.rotY, p3.rotY, u),
                catmullRom(p0.rotZ, p1.rotZ, p2.rotZ, p3.rotZ, u),
                catmullRom(p0.offX, p1.offX, p2.offX, p3.offX, u),
                catmullRom(p0.offY, p1.offY, p2.offY, p3.offY, u),
                catmullRom(p0.offZ, p1.offZ, p2.offZ, p3.offZ, u),
                catmullRom(p0.scaleX, p1.scaleX, p2.scaleX, p3.scaleX, u),
                catmullRom(p0.scaleY, p1.scaleY, p2.scaleY, p3.scaleY, u),
                catmullRom(p0.scaleZ, p1.scaleZ, p2.scaleZ, p3.scaleZ, u),
                true, part);
    }

    private static float catmullRom(float p0, float p1, float p2, float p3, float u) {
        float v0 = (p2 - p0) * 0.5F;
        float v1 = (p3 - p1) * 0.5F;
        float u2 = u * u;
        float u3 = u2 * u;
        float v = (2.0F * p1 - 2.0F * p2 + v0 + v1) * u3
                + (-3.0F * p1 + 3.0F * p2 - 2.0F * v0 - v1) * u2
                + v0 * u + p1;
        float lo = Math.min(p1, p2);
        float hi = Math.max(p1, p2);
        return v < lo ? lo : (v > hi ? hi : v);
    }

    private static void write(Frame f, ModelPart part) {
        writeValues(f.rotX, f.rotY, f.rotZ, f.offX, f.offY, f.offZ,
                f.scaleX, f.scaleY, f.scaleZ, f.hasRot, part);
    }

    private static void writeValues(float rotX, float rotY, float rotZ,
            float offX, float offY, float offZ,
            float scaleX, float scaleY, float scaleZ, boolean hasRot, ModelPart part) {
        if (hasRot) {
            float deg = (float) Math.PI / 180.0F;
            part.xRot = rotX * deg;
            part.yRot = rotY * deg;
            part.zRot = rotZ * deg;
        }
        PartPose base = part.getInitialPose();
        part.x = base.x + offX;
        part.y = base.y - offY;
        part.z = base.z + offZ;
        if (scaleX != 1.0F || scaleY != 1.0F || scaleZ != 1.0F) {
            part.xScale = scaleX;
            part.yScale = scaleY;
            part.zScale = scaleZ;
        }
    }

    private static void writeWeighted(Frame f, ModelPart part, float weight) {
        writeWeightedValues(f.rotX, f.rotY, f.rotZ, f.offX, f.offY, f.offZ,
                f.scaleX, f.scaleY, f.scaleZ, f.hasRot, part, weight);
    }

    private static void writeWeightedValues(float rotX, float rotY, float rotZ,
            float offX, float offY, float offZ,
            float scaleX, float scaleY, float scaleZ, boolean hasRot,
            ModelPart part, float weight) {
        if (hasRot) {
            float deg = (float) Math.PI / 180.0F;
            part.xRot = mix(part.xRot, nearest(part.xRot, rotX * deg), weight);
            part.yRot = mix(part.yRot, nearest(part.yRot, rotY * deg), weight);
            part.zRot = mix(part.zRot, nearest(part.zRot, rotZ * deg), weight);
        }
        PartPose base = part.getInitialPose();
        part.x = mix(part.x, base.x + offX, weight);
        part.y = mix(part.y, base.y - offY, weight);
        part.z = mix(part.z, base.z + offZ, weight);
        part.xScale = mix(part.xScale, scaleX, weight);
        part.yScale = mix(part.yScale, scaleY, weight);
        part.zScale = mix(part.zScale, scaleZ, weight);
    }

    private static float mix(float a, float b, float u) {
        return a + (b - a) * u;
    }

    private static float nearest(float reference, float angle) {
        float twoPi = (float) (Math.PI * 2.0);
        float delta = angle - reference;
        return reference + delta - twoPi * Math.round(delta / twoPi);
    }
}
