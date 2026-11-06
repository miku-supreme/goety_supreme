package cn.blockforge.generated.goetysupreme.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.Mth;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GolemClip;
import cn.blockforge.generated.goetysupreme.entity.IfritClone;
import cn.blockforge.generated.goetysupreme.util.IfritCloneAnimator;
import cn.blockforge.generated.goetysupreme.model.IfritCloneModel;

public final class IfritCloneAnimator {

    private static final String IDLE = "idle";
    private static final String WALK = "walk";
    private static final String DEATH = "death";

    private static final float WALK_TICKS_PER_RADIAN = 1.25F;

    private static final float WALK_WEIGHT_GAIN = 12.0F;

    private static final java.util.Set<String> WALK_LEG_BONES = java.util.Set.of("left_leg", "right_leg");

    private static Map<String, GolemClip> clips;
    private static List<ModelPart> cachedParts = List.of();
    private static ModelPart cachedPartsRoot;

    private static final Map<Integer, float[]> ANIM_STATE = new HashMap<>();

    private static final Map<Integer, Clock> ACTION_CLOCKS = new HashMap<>();

    private static final Map<Integer, OutBlend> OUT_BLENDS = new HashMap<>();

    private static final int OUT_BLEND_TICKS = 3;

    private static final int ACTION_BLEND_TICKS = 3;
    private static final float ACTION_SNAP_TICKS = 2.0F;
    private static final float ACTION_RESYNC_GAIN = 0.08F;
    private static final float ACTION_RESYNC_LIMIT = 0.04F;
    private static final float ACTION_MAX_STEP = 1.5F;

    private static final class Clock {
        String clip = "";
        float time;
        float lastAge;
        boolean valid;
    }

    private static final class OutBlend {
        String lastClip = "";
        float lastTick;
        String fromClip = "";
        float fromTick;
        float endAge;
        int span = OUT_BLEND_TICKS;
    }

    private IfritCloneAnimator() {
    }

    public static void setup(IfritCloneModel model, IfritClone entity, float limbSwing, float limbSwingAmount,
            float ageInTicks, float netHeadYaw, float headPitch) {
        Map<String, GolemClip> all = clips();
        ModelPart root = model.root();
        resetPose(root);
        if (all.isEmpty()) {
            return;
        }
        GolemClip.BoneLookup lookup = model::part;
        float partial = Mth.frac(ageInTicks);

        String wanted;
        float tick;
        boolean blendWalk = false;
        if (entity.deathTime > 0 || !entity.isAlive()) {
            wanted = DEATH;
            tick = actionTick(entity, DEATH, entity.deathTime + partial, ageInTicks);
            invalidateStatePhase(entity);
        } else {
            String clipKey = entity.currentClipKey();
            if (clipKey != null) {
                wanted = clipKey;
                tick = actionTick(entity, clipKey, entity.getActionTick() + partial, ageInTicks);
                invalidateStatePhase(entity);
            } else {
                wanted = IDLE;
                tick = idlePhase(entity, ageInTicks);
                blendWalk = true;
            }
        }

        GolemClip clip = all.get(wanted);
        if (clip == null) {
            wanted = IDLE;
            clip = all.get(IDLE);
            blendWalk = true;
        }
        if (clip != null) {
            if (clip.loop && tick > 0.0F) {
                tick = tick % clip.lengthTicks;
            }
            clip.apply(lookup, tick);
        }

        OutBlend blend = OUT_BLENDS.computeIfAbsent(entity.getId(), key -> new OutBlend());
        if (OUT_BLENDS.size() > 256) {
            OUT_BLENDS.clear();
            blend = OUT_BLENDS.computeIfAbsent(entity.getId(), key -> new OutBlend());
        }
        boolean action = !(IDLE.equals(wanted) || WALK.equals(wanted));
        if (blend.endAge <= 0.0F && !blend.lastClip.isEmpty() && !blend.lastClip.equals(wanted)
                && !IDLE.equals(blend.lastClip) && !WALK.equals(blend.lastClip)
                && !DEATH.equals(blend.lastClip)) {
            blend.fromClip = blend.lastClip;
            blend.fromTick = blend.lastTick;
            blend.span = action ? ACTION_BLEND_TICKS : OUT_BLEND_TICKS;
            blend.endAge = ageInTicks + blend.span;
        }
        if (blend.endAge > 0.0F) {
            float weight = Mth.clamp((blend.endAge - ageInTicks) / (float) blend.span, 0.0F, 1.0F);
            if (weight <= 0.0F) {
                blend.endAge = 0.0F;
            } else {
                GolemClip from = all.get(blend.fromClip);
                if (from != null) {
                    from.apply(lookup, blend.fromTick, weight);
                }
            }
        }
        blend.lastClip = wanted;
        blend.lastTick = tick;

        if (blendWalk) {
            GolemClip walk = all.get(WALK);
            if (walk != null) {
                float weight = Math.min(limbSwingAmount * WALK_WEIGHT_GAIN, 1.0F);
                if (weight > 0.0F) {
                    float walkTick = (limbSwing * WALK_TICKS_PER_RADIAN) % walk.lengthTicks;
                    walk.apply(lookup, walkTick, weight);
                }
            }
        } else if (action && !DEATH.equals(wanted) && entity.onGround()
                && !entity.keepsAuthoredLegs()) {
            GolemClip walk = all.get(WALK);
            if (walk != null) {
                float weight = Math.min(limbSwingAmount * WALK_WEIGHT_GAIN, 1.0F);
                if (weight > 0.0F) {
                    float walkTick = (limbSwing * WALK_TICKS_PER_RADIAN) % walk.lengthTicks;
                    walk.applyOnly(lookup, walkTick, weight, WALK_LEG_BONES::contains);
                }
            }
        }

        applyHeadLook(model, netHeadYaw, headPitch);
    }

    private static void applyHeadLook(IfritCloneModel model, float netHeadYaw, float headPitch) {
        if (netHeadYaw == 0.0F && headPitch == 0.0F) {
            return;
        }
        ModelPart head = model.part("head");
        head.yRot += netHeadYaw * Mth.DEG_TO_RAD;
        head.xRot += headPitch * Mth.DEG_TO_RAD;
    }

    private static float idlePhase(IfritClone entity, float ageInTicks) {
        float[] rec = ANIM_STATE.computeIfAbsent(entity.getId(), key -> new float[]{-1.0F, 0.0F});
        if (ANIM_STATE.size() > 256) {
            ANIM_STATE.clear();
            rec = ANIM_STATE.computeIfAbsent(entity.getId(), key -> new float[]{-1.0F, 0.0F});
        }
        if (rec[0] != 1.0F) {
            rec[0] = 1.0F;
            rec[1] = ageInTicks;
        }
        return ageInTicks - rec[1];
    }

    private static void invalidateStatePhase(IfritClone entity) {
        float[] rec = ANIM_STATE.computeIfAbsent(entity.getId(), key -> new float[]{-1.0F, 0.0F});
        rec[0] = -1.0F;
        if (ANIM_STATE.size() > 256) {
            ANIM_STATE.clear();
        }
    }

    private static float actionTick(IfritClone entity, String clip, float synced, float ageInTicks) {
        Clock clock = ACTION_CLOCKS.computeIfAbsent(entity.getId(), key -> new Clock());
        if (ACTION_CLOCKS.size() > 256) {
            ACTION_CLOCKS.clear();
            clock = ACTION_CLOCKS.computeIfAbsent(entity.getId(), key -> new Clock());
        }
        if (!clock.valid || !clip.equals(clock.clip)) {
            clock.valid = true;
            clock.clip = clip;
            clock.time = synced;
            clock.lastAge = ageInTicks;
            return synced;
        }
        float delta = ageInTicks - clock.lastAge;
        clock.lastAge = ageInTicks;
        if (delta > 0.0F && delta <= ACTION_MAX_STEP) {
            clock.time += delta;
        }
        float error = synced - clock.time;
        if (error >= ACTION_SNAP_TICKS || error <= -ACTION_SNAP_TICKS) {
            clock.time = synced;
        } else {
            clock.time += Mth.clamp(error * ACTION_RESYNC_GAIN,
                    -ACTION_RESYNC_LIMIT, ACTION_RESYNC_LIMIT);
        }
        return clock.time;
    }

    private static void resetPose(ModelPart root) {
        if (cachedPartsRoot != root) {
            cachedParts = root.getAllParts().toList();
            cachedPartsRoot = root;
        }
        for (int i = 0; i < cachedParts.size(); i++) {
            cachedParts.get(i).resetPose();
        }
    }

    private static Map<String, GolemClip> clips() {
        if (clips == null) {
            clips = load();
        }
        return clips;
    }

    private static Map<String, GolemClip> load() {
        Map<String, GolemClip> out = new LinkedHashMap<>();
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                "clips/ifrit.json");
        try {
            Optional<Resource> resource = Minecraft.getInstance().getResourceManager().getResource(id);
            if (resource.isEmpty()) {
                return out;
            }
            try (BufferedReader reader = resource.get().openAsReader()) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                JsonObject clipsJson = root.getAsJsonObject("clips");
                for (Map.Entry<String, JsonElement> entry : clipsJson.entrySet()) {
                    JsonObject clipJson = entry.getValue().getAsJsonObject();
                    GolemClip clip = new GolemClip(clipJson.get("len").getAsFloat(),
                            clipJson.has("loop") && clipJson.get("loop").getAsBoolean());
                    GolemClip.Track track = clip.track();
                    for (Map.Entry<String, JsonElement> bones
                            : clipJson.getAsJsonObject("bones").entrySet()) {
                        GolemClip.Bone bone = track.bone(bones.getKey());
                        for (JsonElement frameElement : bones.getValue().getAsJsonArray()) {
                            JsonArray frame = frameElement.getAsJsonArray();
                            boolean smooth = frame.size() > 7 && frame.get(7).getAsInt() != 0;
                            bone.frame(frame.get(0).getAsFloat(), frame.get(1).getAsFloat(),
                                    frame.get(2).getAsFloat(), frame.get(3).getAsFloat(),
                                    frame.get(4).getAsFloat(), frame.get(5).getAsFloat(),
                                    frame.get(6).getAsFloat(), smooth);
                        }
                    }
                    out.put(entry.getKey(), clip);
                }
            }
        } catch (Exception exception) {
            com.mojang.logging.LogUtils.getLogger().error("[goety_supreme] 读取炼狱亲主动画失败", exception);
        }
        return out;
    }

    public static void invalidate() {
        clips = null;
        cachedParts = List.of();
        cachedPartsRoot = null;
        ANIM_STATE.clear();
        ACTION_CLOCKS.clear();
        OUT_BLENDS.clear();
    }
}
