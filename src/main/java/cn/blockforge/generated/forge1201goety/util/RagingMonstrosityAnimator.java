package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.ally.golem.RedstoneMonstrosity;
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
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GolemClip;
import cn.blockforge.generated.goetysupreme.util.RagingArmAim;
import cn.blockforge.generated.goetysupreme.util.RagingMonstrosityAnimator;
import cn.blockforge.generated.goetysupreme.model.RagingMonstrosityModel;
import cn.blockforge.generated.goetysupreme.renderer.RagingMonstrosityRenderer;
import cn.blockforge.generated.goetysupreme.api.RagingSkillHolder;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;

public final class RagingMonstrosityAnimator {

    public static final String IDLE = "idle";
    public static final String WALK = "walk";
    public static final String SMASH = "smash";
    public static final String SLAM = "slam";
    public static final String ROAR = "roar";
    public static final String SPRAY = "spray";
    public static final String SUMMON = "summon";
    public static final String ANTIAIR = "antiair";
    public static final String WIPE = "wipe";
    public static final String CHARGE = "charge";
    public static final String DEATH = "death";

    private static final String[] SKILL_CLIPS = {
            IDLE, SMASH, SLAM, ROAR, SPRAY, CHARGE, SUMMON, ANTIAIR, WIPE,
    };

    private static final float WALK_TICKS_PER_RADIAN = 2.5F;

    private static final float WALK_WEIGHT_GAIN = 20.0F;

    private static Map<String, GolemClip> clips;

    private static List<ModelPart> cachedParts = List.of();
    private static ModelPart cachedPartsRoot;

    private static final Map<Integer, float[]> ANIM_STATE = new HashMap<>();

    private static final class OutBlend {

        String lastClip = "";

        float lastTick;

        String fromClip = "";
        float fromTick;

        float endAge;
    }

    private static final Map<Integer, OutBlend> OUT_BLENDS = new HashMap<>();

    private static final int OUT_BLEND_TICKS = 3;

    private static final class ActionClock {

        private String clip = "";

        private float time;

        private float lastAge;
        private boolean valid;
    }

    private static final Map<Integer, ActionClock> ACTION_CLOCKS = new HashMap<>();

    private static final float ACTION_SNAP_TICKS = 2.0F;

    private static final float ACTION_RESYNC_GAIN = 0.08F;

    private static final float ACTION_RESYNC_LIMIT = 0.04F;

    private static final float ACTION_MAX_STEP = 1.5F;

    private RagingMonstrosityAnimator() {
    }

    private static float actionTick(RedstoneMonstrosity entity, String clip, float synced,
            float ageInTicks) {
        ActionClock clock = ACTION_CLOCKS.computeIfAbsent(entity.getId(), key -> new ActionClock());
        if (ACTION_CLOCKS.size() > 256) {
            ACTION_CLOCKS.clear();
            clock = ACTION_CLOCKS.computeIfAbsent(entity.getId(), key -> new ActionClock());
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

    public static void setup(RagingMonstrosityModel model, RedstoneMonstrosity entity,
            float limbSwing, float limbSwingAmount, float ageInTicks) {
        ModelPart root = model.root();
        resetPose(root);
        if (!(entity instanceof RagingStarServant servant) || !servant.isRagingStarred()) {
            return;
        }
        Map<String, GolemClip> all = clips();
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
        } else if (entity instanceof RagingSkillHolder holder && holder.getRagingSkillId() > 0
                && holder.getRagingSkillId() < SKILL_CLIPS.length) {
            wanted = SKILL_CLIPS[holder.getRagingSkillId()];
            tick = actionTick(entity, wanted, holder.getRagingSkillTick() + partial, ageInTicks);
            invalidateStatePhase(entity);
        } else {
            wanted = null;
            tick = 0.0F;
        }

        if (wanted == null) {
            int state = entity.getCurrentAnimation();
            float[] rec = ANIM_STATE.computeIfAbsent(entity.getId(), key -> new float[]{-1.0F, 0.0F});
            if ((int) rec[0] != state) {
                rec[0] = state;
                rec[1] = ageInTicks;
            }
            if (ANIM_STATE.size() > 256) {
                ANIM_STATE.clear();
            }
            float since = Math.max(0.0F, ageInTicks - rec[1]);
            if (entity.isCurrentAnimation(RedstoneMonstrosity.ATTACK)) {
                wanted = SMASH;
                tick = actionTick(entity, SMASH,
                        Math.max(since, entity.attackTick + partial), ageInTicks);
            } else if (entity.isCurrentAnimation(RedstoneMonstrosity.BELCH)) {
                wanted = SPRAY;
                tick = since;
            } else if (entity.isCurrentAnimation(RedstoneMonstrosity.SUMMON)) {
                wanted = SUMMON;
                tick = actionTick(entity, SUMMON,
                        Math.max(since, entity.summonTick + partial), ageInTicks);
            } else {
                wanted = IDLE;
                tick = since;
                blendWalk = entity.canAnimateMove();
            }
        }

        GolemClip clip = all.get(wanted);
        if (clip == null) {
            clip = all.get(IDLE);
            wanted = IDLE;
        }
        if (clip != null) {
            if (tick > 0.0F && clip.loop) {
                tick = tick % clip.lengthTicks;
            }
            clip.apply(lookup, tick);
        }

        OutBlend blend = OUT_BLENDS.computeIfAbsent(entity.getId(), key -> new OutBlend());
        boolean action = !(IDLE.equals(wanted) || WALK.equals(wanted));
        if (action) {
            blend.endAge = 0.0F;
        } else if (blend.endAge <= 0.0F
                && !blend.lastClip.isEmpty()
                && !IDLE.equals(blend.lastClip)
                && !WALK.equals(blend.lastClip)
                && !blend.lastClip.equals(wanted)) {
            blend.fromClip = blend.lastClip;
            blend.fromTick = blend.lastTick;
            blend.endAge = ageInTicks + OUT_BLEND_TICKS;
        }
        if (blend.endAge > 0.0F) {
            float weight = Mth.clamp((blend.endAge - ageInTicks) / (float) OUT_BLEND_TICKS, 0.0F, 1.0F);
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
        if (OUT_BLENDS.size() > 256) {
            OUT_BLENDS.clear();
        }

        if (blendWalk) {
            GolemClip walk = all.get(WALK);
            if (walk != null) {
                float weight = Math.min(limbSwingAmount * WALK_WEIGHT_GAIN, 1.0F);
                if (weight > 0.0F) {
                    float walkTick = (limbSwing * WALK_TICKS_PER_RADIAN) % walk.lengthTicks;
                    walk.apply(lookup, walkTick, weight);
                }
            }
        }

        if (ANTIAIR.equals(wanted) && entity instanceof RagingSkillHolder holder) {
            int aimId = holder.getRagingAimTargetId();
            if (aimId != 0 && entity.level().getEntity(aimId) instanceof LivingEntity aimTarget
                    && aimTarget.isAlive()) {
                RagingArmAim.apply(model, entity, aimTarget, partial, tick);
            }
        }
    }

    private static void invalidateStatePhase(RedstoneMonstrosity entity) {
        float[] rec = ANIM_STATE.computeIfAbsent(entity.getId(), key -> new float[]{-1.0F, 0.0F});
        rec[0] = -1.0F;
        if (ANIM_STATE.size() > 256) {
            ANIM_STATE.clear();
        }
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
                "clips/raging_monstrosity.json");
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
                    for (Map.Entry<String, JsonElement> bones : clipJson.getAsJsonObject("bones").entrySet()) {
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
            com.mojang.logging.LogUtils.getLogger().error(
                    "[goety_supreme] 读取狂暴红石巨兽动画失败", exception);
        }
        return out;
    }

    public static void invalidate() {
        clips = null;
        cachedParts = List.of();
        cachedPartsRoot = null;
        ANIM_STATE.clear();
        OUT_BLENDS.clear();
        ACTION_CLOCKS.clear();
    }
}
