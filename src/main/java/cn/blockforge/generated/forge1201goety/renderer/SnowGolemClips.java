package cn.blockforge.generated.goetysupreme.renderer;

import cn.blockforge.generated.goetysupreme.renderer.GolemClip;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.model.AbominableSnowGolemModel;

public final class SnowGolemClips {

    public static final float IDLE_LENGTH = 60.0F;

    public static final float WALK_LENGTH = 40.0F;

    private SnowGolemClips() {
    }

    private static final class Frame {
        final float tick;
        final float x;
        final float y;
        final float z;

        Frame(float tick, float x, float y, float z) {
            this.tick = tick;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private static Frame[] frames(float... flat) {
        Frame[] out = new Frame[flat.length / 4];
        for (int i = 0; i < out.length; i++) {
            out[i] = new Frame(flat[i * 4], flat[i * 4 + 1], flat[i * 4 + 2], flat[i * 4 + 3]);
        }
        return out;
    }

    private static void rot(GolemClip clip, String bone, Frame[] rot) {
        rot(clip, bone, rot, false);
    }

    private static void rotSmooth(GolemClip clip, String bone, Frame[] rot) {
        rot(clip, bone, rot, true);
    }

    private static void rot(GolemClip clip, String bone, Frame[] rot, boolean smooth) {
        GolemClip.Bone b = clip.track().bone(bone);
        for (Frame f : rot) {
            b.frame(f.tick, f.x, f.y, f.z, 0.0F, 0.0F, 0.0F, smooth);
        }
    }

    private static void rotOff(GolemClip clip, String bone, Frame[] rot, Frame[] off) {
        rotOff(clip, bone, rot, off, false);
    }

    private static void rotOffSmooth(GolemClip clip, String bone, Frame[] rot, Frame[] off) {
        rotOff(clip, bone, rot, off, true);
    }

    private static void rotOff(GolemClip clip, String bone, Frame[] rot, Frame[] off, boolean smooth) {
        GolemClip.Bone b = clip.track().bone(bone);
        int n = Math.min(rot.length, off.length);
        for (int i = 0; i < n; i++) {
            b.frame(rot[i].tick, rot[i].x, rot[i].y, rot[i].z,
                    off[i].x, off[i].y, off[i].z, smooth);
        }
    }

    public static GolemClip idle() {
        GolemClip clip = new GolemClip(60);
        rotSmooth(clip, "head", frames(0F, 0F, 0F, 0F, 15F, 0F, -4F, 0F, 30F, 0F, 0F, 0F, 45F, 0F, 4F, 0F, 60F, 0F, 0F, 0F));
        rotSmooth(clip, "left_arm", frames(0F, 0.7788F, 0F, 0F, 15F, 1.8421F, 0F, 0F, 30F, -0.7788F, 0F, 0F, 45F, -1.8421F, 0F, 0F, 60F, 0.7788F, 0F, 0F));
        rotSmooth(clip, "right_arm", frames(0F, 0F, 0F, 0F, 15F, 2F, 0F, 0F, 30F, 0F, 0F, 0F, 45F, -2F, 0F, 0F, 60F, 0F, 0F, 0F));
        return clip;
    }

    public static GolemClip walk() {
        GolemClip clip = new GolemClip(40);
        rotOffSmooth(clip, "upper_body", frames(0F, 0F, 0F, 0F, 5F, 0F, 0F, 0F, 12.5F, 0F, 0F, 0F, 20F, 0F, 0F, 0F, 25F, 0F, 0F, 0F, 32.5F, 0F, 0F, 0F, 40F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 0F, 5F, 0F, -2F, 0F, 12.5F, 0F, 0F, 0F, 20F, 0F, 0F, 0F, 25F, 0F, -2F, 0F, 32.5F, 0F, 0F, 0F, 40F, 0F, 0F, 0F));
        rotSmooth(clip, "left_arm", frames(0F, 45F, 0F, 0F, 20F, -45F, 0F, 0F, 40F, 45F, 0F, 0F));
        rotSmooth(clip, "right_arm", frames(0F, -45F, 0F, 0F, 20F, 45F, 0F, 0F, 40F, -45F, 0F, 0F));
        rotOffSmooth(clip, "left_leg", frames(0F, 0F, 0F, 0F, 20F, 0F, 0F, 0F, 30F, 0F, 0F, 0F, 40F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, -3F, 20F, 0F, 0F, 3F, 30F, 0F, 3F, 0F, 40F, 0F, 0F, -3F));
        rotOffSmooth(clip, "right_leg", frames(0F, 0F, 0F, 0F, 10F, 0F, 0F, 0F, 20F, 0F, 0F, 0F, 40F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 3F, 10F, 0F, 3F, 0F, 20F, 0F, 0F, -3F, 40F, 0F, 0F, 3F));
        return clip;
    }

    public static GolemClip attack() {
        GolemClip clip = new GolemClip(40);
        rotSmooth(clip, "upper_body", frames(0F, 0F, 0F, 0F, 10F, -24F, 0F, 0F, 15F, -25F, 0F, 0F, 21.6666F, -25F, 0F, 0F, 25F, 70F, 0F, 0F, 40F, 0F, 0F, 0F));
        rotSmooth(clip, "left_arm", frames(0F, 0F, 0F, 0F, 10F, -150F, 0F, 0F, 15F, -170F, 0F, 0F, 18.3334F, -175F, 0F, 0F, 21.6666F, -120F, 0F, 0F, 40F, 0F, 0F, 0F));
        rotSmooth(clip, "right_arm", frames(0F, 0F, 0F, 0F, 10F, -150F, 0F, 0F, 15F, -165F, 0F, 0F, 18.3334F, -175F, 0F, 0F, 21.6666F, -120F, 0F, 0F, 40F, 0F, 0F, 0F));
        rotOffSmooth(clip, "right_leg", frames(20F, 0F, 0F, 0F, 21.6666F, 0F, 0F, 0F, 24.1666F, 0F, 0F, 0F, 40F, 0F, 0F, 0F),
                frames(20F, 0F, 0F, 0F, 21.6666F, 0F, 3F, -4F, 24.1666F, 0F, 0F, -6F, 40F, 0F, 0F, 0F));
        return clip;
    }

    public static GolemClip throwSnowball() {
        GolemClip clip = new GolemClip(63);
        rot(clip, "upper_body", frames(0F, 0F, 0F, 0F, 3.3334F, -12.5F, 0F, 0F, 5.8334F, 0F, 0F, 0F, 19.1666F, 0F, 0F, 0F, 21.6666F, -7.5F, 0F, 0F, 25F, 0F, 0F, 0F, 50F, 0F, 3600F, 0F));
        rot(clip, "head", frames(0F, 0F, 0F, 0F, 53.3334F, 0F, 0F, 0F, 55F, 0F, 27.5F, 0F, 56.6666F, 0F, -25F, 0F, 58.3334F, 0F, 27.5F, 0F, 60F, 0F, -25F, 0F, 62.5F, 0F, 0F, 0F));
        rot(clip, "left_arm", frames(0F, 0F, 0F, 0F, 5F, -47.5F, 0F, 0F, 10F, -47.5F, 0F, 0F, 13.3334F, -46.1453F, 12.809F, 12.025F, 15F, -46.1453F, 12.809F, 12.025F, 16.6666F, -44.6849F, 18.1548F, 17.4861F, 18.3334F, -46.5093F, 11.0006F, 10.2608F, 20F, -44.6849F, 18.1548F, 17.4861F, 21.6666F, -46.5093F, 11.0006F, 10.2608F, 25F, 0F, -90F, -90F, 49.1666F, 0F, -90F, -90F, 51.6666F, 0F, 0F, 0F));
        rot(clip, "right_arm", frames(0F, 0F, 0F, 0F, 5F, -47.5F, 0F, 0F, 10F, -47.5F, 0F, 0F, 13.3334F, -45.7211F, -14.6055F, -13.8146F, 15F, -45.7211F, -14.6055F, -13.8146F, 16.6666F, -46.8147F, -9.1822F, -8.5182F, 18.3334F, -44.0685F, -19.9034F, -19.3762F, 20F, -46.8147F, -9.1822F, -8.5182F, 21.6666F, -44.0685F, -19.9034F, -19.3762F, 25F, 55.6944F, 85.7776F, 143.1523F, 49.1666F, 55.6944F, 85.7776F, 143.1523F, 51.6666F, 0F, 0F, 0F));
        return clip;
    }

    public static GolemClip spinLoop() {
        GolemClip clip = new GolemClip(25);
        rot(clip, "upper_body", frames(0F, 0F, 0F, 0F, 25F, 0F, 3600F, 0F));
        rot(clip, "left_arm", frames(0F, 0F, -90F, -90F, 25F, 0F, -90F, -90F));
        rot(clip, "right_arm", frames(0F, 55.6944F, 85.7776F, 143.1523F, 25F, 55.6944F, 85.7776F, 143.1523F));
        rot(clip, "head", frames(0F, 0F, 0F, 0F, 25F, 0F, 0F, 0F));
        return clip;
    }

    public static GolemClip snowballFinish() {
        GolemClip clip = new GolemClip(13);
        rot(clip, "upper_body", frames(0F, 0F, 0F, 0F, 13F, 0F, 0F, 0F));
        rot(clip, "head", frames(0F, 0F, 0F, 0F, 3.3334F, 0F, 27.5F, 0F,
                5F, 0F, -25F, 0F, 6.6666F, 0F, 27.5F, 0F,
                8.3334F, 0F, -25F, 0F, 10F, 0F, 0F, 0F, 13F, 0F, 0F, 0F));
        rot(clip, "left_arm", frames(0F, 0F, -90F, -90F, 13F, 0F, 0F, 0F));
        rot(clip, "right_arm", frames(0F, 55.6944F, 85.7776F, 143.1523F, 13F, 0F, 0F, 0F));
        return clip;
    }

    public static GolemClip throwBlock() {
        GolemClip clip = new GolemClip(40);
        rotSmooth(clip, "upper_body", frames(0F, 0F, 0F, 0F, 6.6666F, 0F, 0F, -4F, 10F, 0F, 0F, -7.5F, 17.5F, 0F, 0F, -7.5F, 20F, 44.3238F, 31.9997F, 15.0003F, 23.3334F, 54.7177F, 43.4584F, 31.8256F, 40F, 0F, 0F, 0F));
        rotSmooth(clip, "head", frames(0F, 0F, 0F, 0F, 10F, -3.7929F, -61.9118F, -26.0818F, 17.5F, -3.7929F, -61.9118F, -26.0818F, 20F, -17.7192F, -35.8963F, -3.0082F, 23.3334F, -45.5376F, -45.3495F, 1.3047F, 40F, 0F, 0F, 0F));
        rotSmooth(clip, "left_arm", frames(0F, 0F, 0F, 0F, 10F, -180F, 0F, 0F, 17.5F, -180F, 0F, 0F, 20F, -172.7073F, -21.3903F, 67.8394F, 22.5F, -100.7796F, -0.8282F, -2.6953F, 25.8334F, -75.3553F, 13.7036F, 1.057F, 40F, 0F, 0F, 0F));
        rotSmooth(clip, "right_arm", frames(0F, 0F, 0F, 0F, 10F, 0F, 0F, 20F, 17.5F, 0F, 0F, 20F, 22.5F, 56.1707F, -32.6833F, 3.0314F, 25.8334F, 60.5294F, -17.7436F, -7.092F, 40F, 0F, 0F, 0F));
        rotOffSmooth(clip, "right_leg", frames(0F, 0F, 0F, 0F, 5F, 0F, 0F, 5F, 10F, 0F, 0F, 10F, 17.5F, 0F, 0F, 10F, 22.5F, 65F, 0F, 10F, 25.8334F, 75F, 0F, 10F, 40F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 0F, 5F, -1F, 3F, 0F, 10F, -2F, 0F, 0F, 17.5F, -2F, 0F, 0F, 22.5F, -1.5556F, 0F, 0F, 25.8334F, -1.2593F, 0F, 0F, 40F, 0F, 0F, 0F));
        return clip;
    }

    public static GolemClip frostBreath() {
        GolemClip clip = new GolemClip(95);
        rotOff(clip, "upper_body", frames(0F, 0F, 0F, 0F, 10F, -40F, 0F, 0F, 15F, -40F, 0F, 0F, 20F, 40F, 0F, 0F, 20.8334F, 40.2244F, 0F, 0F, 21.6666F, 40.4487F, 0F, 0F, 22.5F, 40.6731F, 0F, 0F, 23.3334F, 40.8975F, 0F, 0F, 24.1666F, 41.1218F, 0F, 0F, 25F, 41.3462F, 0F, 0F, 25.8334F, 41.5705F, 0F, 0F, 26.6666F, 41.7949F, 0F, 0F, 27.5F, 42.0192F, 0F, 0F, 28.3334F, 42.2436F, 0F, 0F, 29.1666F, 42.4679F, 0F, 0F, 30F, 42.6923F, 0F, 0F, 30.8334F, 42.9167F, 0F, 0F, 31.6666F, 43.141F, 0F, 0F, 32.5F, 43.3654F, 0F, 0F, 33.3334F, 43.5898F, 0F, 0F, 34.1666F, 43.8141F, 0F, 0F, 35F, 44.0385F, 0F, 0F, 35.8334F, 44.2628F, 0F, 0F, 36.6666F, 44.4872F, 0F, 0F, 37.5F, 44.7115F, 0F, 0F, 38.3334F, 44.9359F, 0F, 0F, 39.1666F, 45.1602F, 0F, 0F, 40F, 45.3846F, 0F, 0F, 40.8334F, 45.609F, 0F, 0F, 41.6666F, 45.8333F, 0F, 0F, 42.5F, 46.0577F, 0F, 0F, 43.3334F, 46.2821F, 0F, 0F, 44.1666F, 46.5064F, 0F, 0F, 45F, 46.7308F, 0F, 0F, 45.8334F, 46.9551F, 0F, 0F, 46.6666F, 47.1795F, 0F, 0F, 47.5F, 47.4038F, 0F, 0F, 48.3334F, 47.6282F, 0F, 0F, 49.1666F, 47.8525F, 0F, 0F, 50F, 48.0769F, 0F, 0F, 50.8334F, 48.3013F, 0F, 0F, 51.6666F, 48.5256F, 0F, 0F, 52.5F, 48.75F, 0F, 0F, 53.3334F, 48.9744F, 0F, 0F, 54.1666F, 49.1987F, 0F, 0F, 55F, 49.4231F, 0F, 0F, 55.8334F, 49.6475F, 0F, 0F, 56.6666F, 49.8718F, 0F, 0F, 57.5F, 50.0962F, 0F, 0F, 58.3334F, 50.3205F, 0F, 0F, 59.1666F, 50.5449F, 0F, 0F, 60F, 50.7692F, 0F, 0F, 60.8334F, 50.9936F, 0F, 0F, 61.6666F, 51.2179F, 0F, 0F, 62.5F, 51.4423F, 0F, 0F, 63.3334F, 51.6667F, 0F, 0F, 64.1666F, 51.891F, 0F, 0F, 65F, 52.1154F, 0F, 0F, 65.8334F, 52.3398F, 0F, 0F, 66.6666F, 52.5641F, 0F, 0F, 67.5F, 52.7885F, 0F, 0F, 68.3334F, 53.0128F, 0F, 0F, 69.1666F, 53.2372F, 0F, 0F, 70F, 53.4615F, 0F, 0F, 70.8334F, 53.6859F, 0F, 0F, 71.6666F, 53.9102F, 0F, 0F, 72.5F, 54.1346F, 0F, 0F, 73.3334F, 54.359F, 0F, 0F, 74.1666F, 54.5833F, 0F, 0F, 75F, 54.8077F, 0F, 0F, 75.8334F, 55.0321F, 0F, 0F, 76.6666F, 55.2564F, 0F, 0F, 77.5F, 55.4808F, 0F, 0F, 78.3334F, 55.7051F, 0F, 0F, 79.1666F, 55.9295F, 0F, 0F, 80F, 56.1538F, 0F, 0F, 85F, 57.5F, 0F, 0F, 95F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 0F, 10F, 0F, 0F, 0F, 15F, 0F, 0F, 0F, 20F, 0F, 0F, 0F, 20.8334F, 0.5F, 0F, 0F, 21.6666F, -0.5F, 0F, 0F, 22.5F, 0.5F, 0F, 0F, 23.3334F, -0.5F, 0F, 0F, 24.1666F, 0.5F, 0F, 0F, 25F, -0.5F, 0F, 0F, 25.8334F, 0.5F, 0F, 0F, 26.6666F, -0.5F, 0F, 0F, 27.5F, 0.5F, 0F, 0F, 28.3334F, -0.5F, 0F, 0F, 29.1666F, 0.5F, 0F, 0F, 30F, -0.5F, 0F, 0F, 30.8334F, 0.5F, 0F, 0F, 31.6666F, -0.5F, 0F, 0F, 32.5F, 0.5F, 0F, 0F, 33.3334F, -0.5F, 0F, 0F, 34.1666F, 0F, 0F, 0F, 35F, 0.5F, 0F, 0F, 35.8334F, -0.5F, 0F, 0F, 36.6666F, 0.5F, 0F, 0F, 37.5F, -0.5F, 0F, 0F, 38.3334F, 0.5F, 0F, 0F, 39.1666F, -0.5F, 0F, 0F, 40F, 0.5F, 0F, 0F, 40.8334F, -0.5F, 0F, 0F, 41.6666F, 0.5F, 0F, 0F, 42.5F, -0.5F, 0F, 0F, 43.3334F, 0.5F, 0F, 0F, 44.1666F, -0.5F, 0F, 0F, 45F, 0.5F, 0F, 0F, 45.8334F, -0.5F, 0F, 0F, 46.6666F, 0.5F, 0F, 0F, 47.5F, -0.5F, 0F, 0F, 48.3334F, 0F, 0F, 0F, 49.1666F, 0.5F, 0F, 0F, 50F, -0.5F, 0F, 0F, 50.8334F, 0.5F, 0F, 0F, 51.6666F, -0.5F, 0F, 0F, 52.5F, 0F, 0F, 0F, 53.3334F, 0.5F, 0F, 0F, 54.1666F, -0.5F, 0F, 0F, 55F, 0.5F, 0F, 0F, 55.8334F, -0.5F, 0F, 0F, 56.6666F, 0.5F, 0F, 0F, 57.5F, -0.5F, 0F, 0F, 58.3334F, 0.5F, 0F, 0F, 59.1666F, -0.5F, 0F, 0F, 60F, 0.5F, 0F, 0F, 60.8334F, -0.5F, 0F, 0F, 61.6666F, 0.5F, 0F, 0F, 62.5F, -0.5F, 0F, 0F, 63.3334F, 0.5F, 0F, 0F, 64.1666F, -0.5F, 0F, 0F, 65F, 0.5F, 0F, 0F, 65.8334F, -0.5F, 0F, 0F, 66.6666F, 0F, 0F, 0F, 67.5F, 0.5F, 0F, 0F, 68.3334F, -0.5F, 0F, 0F, 69.1666F, 0.5F, 0F, 0F, 70F, -0.5F, 0F, 0F, 70.8334F, 0.5F, 0F, 0F, 71.6666F, -0.5F, 0F, 0F, 72.5F, 0.5F, 0F, 0F, 73.3334F, -0.5F, 0F, 0F, 74.1666F, 0.5F, 0F, 0F, 75F, -0.5F, 0F, 0F, 75.8334F, 0.5F, 0F, 0F, 76.6666F, -0.5F, 0F, 0F, 77.5F, 0.5F, 0F, 0F, 78.3334F, -0.5F, 0F, 0F, 79.1666F, 0.5F, 0F, 0F, 80F, -0.5F, 0F, 0F, 85F, -0.3333F, 0F, 0F, 95F, 0F, 0F, 0F));
        rot(clip, "head", frames(0F, 0F, 0F, 0F, 10F, -17.5F, 0F, 0F, 15F, -17.5F, 0F, 0F, 20F, -40F, 0F, 0F, 82.5F, -40F, 0F, 0F, 85F, 57.5F, 0F, 0F, 95F, 0F, 0F, 0F));
        rot(clip, "left_arm", frames(0F, 0F, 0F, 0F, 10F, -120F, 17.5F, 0F, 15F, -120F, 17.5F, 0F, 20F, 90F, -37.5F, -180F, 22.5F, 90F, -32.5F, -180F, 25F, 90F, -37.5F, -180F, 27.5F, 90F, -32.5F, -180F, 30F, 90F, -37.5F, -180F, 32.5F, 90F, -32.5F, -180F, 35F, 90F, -37.5F, -180F, 37.5F, 90F, -32.5F, -180F, 40F, 90F, -37.5F, -180F, 42.5F, 90F, -32.5F, -180F, 45F, 90F, -37.5F, -180F, 47.5F, 90F, -32.5F, -180F, 50F, 90F, -37.5F, -180F, 52.5F, 90F, -32.5F, -180F, 55F, 90F, -37.5F, -180F, 57.5F, 90F, -32.5F, -180F, 60F, 90F, -37.5F, -180F, 62.5F, 90F, -32.5F, -180F, 65F, 90F, -37.5F, -180F, 67.5F, 90F, -32.5F, -180F, 70F, 90F, -37.5F, -180F, 72.5F, 90F, -32.5F, -180F, 75F, 90F, -37.5F, -180F, 77.5F, 90F, -32.5F, -180F, 80F, 90F, -37.5F, -180F, 95F, 0F, 0F, 0F));
        rot(clip, "right_arm", frames(0F, 0F, 0F, 0F, 10F, -120F, -17.5F, 0F, 15F, -120F, -17.5F, 0F, 20F, 90F, 32.5F, 180F, 22.5F, 90F, 27.5F, 180F, 25F, 90F, 32.5F, 180F, 27.5F, 90F, 27.5F, 180F, 30F, 90F, 32.5F, 180F, 32.5F, 90F, 27.5F, 180F, 35F, 90F, 32.5F, 180F, 37.5F, 90F, 27.5F, 180F, 40F, 90F, 32.5F, 180F, 42.5F, 90F, 27.5F, 180F, 45F, 90F, 32.5F, 180F, 47.5F, 90F, 27.5F, 180F, 50F, 90F, 32.5F, 180F, 52.5F, 90F, 27.5F, 180F, 55F, 90F, 32.5F, 180F, 57.5F, 90F, 27.5F, 180F, 60F, 90F, 32.5F, 180F, 62.5F, 90F, 27.5F, 180F, 65F, 90F, 32.5F, 180F, 67.5F, 90F, 27.5F, 180F, 70F, 90F, 32.5F, 180F, 72.5F, 90F, 27.5F, 180F, 75F, 90F, 32.5F, 180F, 77.5F, 90F, 27.5F, 180F, 80F, 90F, 32.5F, 180F, 95F, 0F, 0F, 0F));
        rotOff(clip, "right_leg", frames(0F, 0F, 0F, 0F, 15F, 0F, 0F, 0F, 19.1666F, 0F, 0F, 0F, 79.1666F, 0F, 0F, 0F, 85F, 0F, 0F, 0F, 95F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 0F, 15F, 0F, 3F, -3F, 19.1666F, 0F, 0F, -6F, 79.1666F, 0F, 0F, -6F, 85F, 0F, 3F, -3.42F, 95F, 0F, 0F, 0F));
        return clip;
    }
}
