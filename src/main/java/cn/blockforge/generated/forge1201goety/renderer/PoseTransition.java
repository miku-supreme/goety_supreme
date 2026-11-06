package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import org.joml.Vector3f;

public final class PoseTransition {
    private PoseTransition() {
    }

    public static AnimationDefinition build(AnimationDefinition pose, float length, boolean intoPose) {
        AnimationDefinition.Builder builder = AnimationDefinition.Builder.withLength(length);
        pose.boneAnimations().forEach((bone, channels) -> {
            for (AnimationChannel channel : channels) {
                Vector3f held = channel.keyframes()[0].target();
                if (held.equals(0.0F, 0.0F, 0.0F)) {
                    continue;
                }
                Keyframe from = intoPose
                        ? new Keyframe(0.0F, new Vector3f(), AnimationChannel.Interpolations.LINEAR)
                        : new Keyframe(0.0F, new Vector3f(held), AnimationChannel.Interpolations.LINEAR);
                Keyframe to = intoPose
                        ? new Keyframe(length, new Vector3f(held), AnimationChannel.Interpolations.LINEAR)
                        : new Keyframe(length, new Vector3f(), AnimationChannel.Interpolations.LINEAR);
                builder.addAnimation(bone, new AnimationChannel(channel.target(), from, to));
            }
        });
        return builder.build();
    }

    public static AnimationDefinition filterBones(AnimationDefinition def, java.util.Set<String> bones, boolean keep) {
        AnimationDefinition.Builder builder = AnimationDefinition.Builder.withLength(def.lengthInSeconds());
        if (def.looping()) {
            builder.looping();
        }
        def.boneAnimations().forEach((bone, channels) -> {
            boolean match = bones.contains(bone);
            if (match == keep) {
                for (AnimationChannel channel : channels) {
                    builder.addAnimation(bone, channel);
                }
            }
        });
        return builder.build();
    }

    public static AnimationDefinition buildBetween(AnimationDefinition from, AnimationDefinition to, float length) {
        AnimationDefinition.Builder builder = AnimationDefinition.Builder.withLength(length);
        from.boneAnimations().forEach((bone, channels) -> {
            for (AnimationChannel channel : channels) {
                Keyframe[] keys = channel.keyframes();
                Vector3f start = new Vector3f(keys[keys.length - 1].target());
                Vector3f end = new Vector3f();
                var toChannels = to.boneAnimations().get(bone);
                if (toChannels != null) {
                    for (AnimationChannel tc : toChannels) {
                        if (tc.target() == channel.target()) {
                            end.set(tc.keyframes()[0].target());
                            break;
                        }
                    }
                }
                builder.addAnimation(bone, new AnimationChannel(channel.target(),
                        new Keyframe(0.0F, start, AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(length, end, AnimationChannel.Interpolations.LINEAR)));
            }
        });
        to.boneAnimations().forEach((bone, channels) -> {
            for (AnimationChannel channel : channels) {
                boolean exists = false;
                var fromChannels = from.boneAnimations().get(bone);
                if (fromChannels != null) {
                    for (AnimationChannel fc : fromChannels) {
                        if (fc.target() == channel.target()) {
                            exists = true;
                            break;
                        }
                    }
                }
                if (exists) {
                    continue;
                }
                Vector3f end = new Vector3f(channel.keyframes()[0].target());
                builder.addAnimation(bone, new AnimationChannel(channel.target(),
                        new Keyframe(0.0F, new Vector3f(), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(length, end, AnimationChannel.Interpolations.LINEAR)));
            }
        });
        return builder.build();
    }
}
