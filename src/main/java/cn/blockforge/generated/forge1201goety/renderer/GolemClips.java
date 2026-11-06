package cn.blockforge.generated.goetysupreme.renderer;

import cn.blockforge.generated.goetysupreme.renderer.GolemClip;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.model.AbstractGolemServantModel;

public final class GolemClips {

    private GolemClips() {
    }

    public static final float HAND_OFFSET_X = 0.0F;
    public static final float HAND_OFFSET_Y = 54.0F;
    public static final float HAND_OFFSET_Z = -20.0F;

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
        GolemClip.Bone b = clip.track().bone(bone);
        for (Frame f : rot) {
            b.frame(f.tick, f.x, f.y, f.z, 0.0F, 0.0F, 0.0F, true);
        }
    }

    private static void rotOff(GolemClip clip, String bone, Frame[] rot, Frame[] off) {
        GolemClip.Bone b = clip.track().bone(bone);
        int n = Math.min(rot.length, off.length);
        for (int i = 0; i < n; i++) {
            b.frame(rot[i].tick, rot[i].x, rot[i].y, rot[i].z,
                    off[i].x, off[i].y, off[i].z, true);
        }
    }

    public static GolemClip walk() {
        GolemClip clip = new GolemClip(20);
        rotOff(clip, "upper_body", frames(0F, 0F, 7.5F, 0F, 5F, 0F, 0F, 0F, 8.3334F, 0F, -5.0001F, 0F, 10F, 0F, -7.5F, 0F, 15F, 0F, 0F, 0F, 18.3334F, 0F, 5.0001F, 0F, 20F, 0F, 7.5F, 0F),
                frames(0F, 0F, 0F, 0F, 5F, 0F, 1F, 0F, 8.3334F, 0F, -1F, 0F, 10F, 0F, 0F, 0F, 15F, 0F, 1F, 0F, 18.3334F, 0F, -1F, 0F, 20F, 0F, 0F, 0F));
        rot(clip, "head", frames(0F, 0F, 0F, 0F));
        rot(clip, "left_arm", frames(0F, -15F, 0F, 0F, 10F, 15F, 0F, 0F, 20F, -15F, 0F, 0F));
        rot(clip, "right_arm", frames(0F, 15F, 0F, 0F, 10F, -15F, 0F, 0F, 20F, 15F, 0F, 0F));
        rotOff(clip, "left_leg", frames(0F, 28F, 0F, 0F, 5F, 0F, 0F, 0F, 10F, -28F, 0F, 0F, 20F, 28F, 0F, 0F),
                frames(0F, 0F, 0F, 3F, 5F, 0F, 3F, 0F, 10F, 0F, 0F, -3F, 20F, 0F, 0F, 3F));
        rotOff(clip, "right_leg", frames(0F, -28F, 0F, 0F, 10F, 28F, 0F, 0F, 15F, 0F, 0F, 0F, 20F, -28F, 0F, 0F),
                frames(0F, 0F, 0F, -3F, 10F, 0F, 0F, 3F, 15F, 0F, 3F, 0F, 20F, 0F, 0F, -3F));
        return clip;
    }

    public static GolemClip charge() {
        GolemClip clip = new GolemClip(10);
        rotOff(clip, "upper_body", frames(0F, 7.5071F, 2.4786F, 0.3265F, 5F, 7.5284F, -4.9571F, -0.6542F, 10F, 7.5071F, 2.4786F, 0.3265F),
                frames(0F, 0.25F, 0F, 0F, 5F, -0.25F, 0F, 0F, 10F, 0.25F, 0F, 0F));
        rotOff(clip, "head", frames(0F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, -3F));
        rotOff(clip, "left_arm", frames(0F, -9.9627F, -0.8672F, -4.9244F, 5F, 10.0373F, -0.8672F, -4.9244F, 7.5F, 0.1127F, -0.8606F, -9.925F, 10F, -9.9627F, -0.8672F, -4.9244F),
                frames(0F, 0F, 0F, 0F, 5F, 0F, 0F, 0F, 7.5F, 0F, 1F, 0F, 10F, 0F, 0F, 0F));
        rotOff(clip, "right_arm", frames(0F, 9.9627F, -0.8672F, 4.9244F, 2.5F, -0.1127F, -0.8606F, 9.925F, 5F, -10.0373F, -0.8672F, 4.9244F, 10F, 9.9627F, -0.8672F, 4.9244F),
                frames(0F, 0F, 0F, 0F, 2.5F, 0F, 1F, 0F, 5F, 0F, 0F, 0F, 10F, 0F, 0F, 0F));
        rotOff(clip, "left_leg", frames(0F, 0F, 0F, 0F, 2.5F, 0F, 0F, 0F, 5F, 0F, 0F, 0F, 10F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 3F, 2.5F, 0F, 3F, 0F, 5F, 0F, 0F, -3F, 10F, 0F, 0F, 3F));
        rotOff(clip, "right_leg", frames(0F, 0F, 0F, 0F, 5F, 0F, 0F, 0F, 7.5F, 0F, 0F, 0F, 10F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, -3F, 5F, 0F, 0F, 3F, 7.5F, 0F, 3F, 0F, 10F, 0F, 0F, -3F));
        return clip;
    }

    public static GolemClip sweep() {
        GolemClip clip = new GolemClip(27);
        rot(clip, "upper_body", frames(0F, 0F, 0F, 0F, 8F, 33.0561F, 15.3165F, 6.6367F, 14.66672F, 46.7254F, 31.4855F, 16.7652F, 16.66672F, 46.7254F, 31.4855F, 16.7652F, 20F, -4.0749F, -2.5245F, -14.2542F, 26.66672F, 0F, 0F, 0F));
        rot(clip, "head", frames(0F, 0F, 0F, 0F));
        rot(clip, "left_arm", frames(0F, 0F, 0F, 0F, 8F, -69.1115F, 16.4137F, 6.1548F, 14.66672F, -69.1115F, 16.4137F, 6.1548F, 16.66672F, -69.1115F, 16.4137F, 6.1548F, 20F, 43.2199F, -62.0092F, -136.7812F, 20.66672F, 13.072F, -8.9439F, -91.945F, 26.66672F, 0F, 0F, 0F));
        rot(clip, "right_arm", frames(0F, 0F, 0F, 0F, 4F, 2.4905F, -0.2178F, 4.9952F, 8F, 5F, 0F, 0F, 14.66672F, 5F, 0F, 0F, 16.66672F, 5F, 0F, 0F, 20F, -7.8788F, -1.9114F, 22.4229F, 26.66672F, 0F, 0F, 0F));
        rotOff(clip, "right_leg", frames(0F, 0F, 0F, 0F, 4F, 0F, 0F, 0F, 8F, 0F, 0F, 0F, 20F, 0F, 0F, 0F, 23.33328F, 0F, 0F, 0F, 26.66672F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 0F, 4F, 0F, 3F, -2F, 8F, 0F, 0F, -4F, 20F, 0F, 0F, -4F, 23.33328F, 0F, 2F, -2F, 26.66672F, 0F, 0F, 0F));
        return clip;
    }

    public static GolemClip smash() {
        GolemClip clip = new GolemClip(40);
        rot(clip, "upper_body", frames(0F, 0F, 0F, 0F, 10F, -15F, 0F, 0F, 15F, -22.5F, 0F, 0F, 18.3334F, -22.5F, 0F, 0F, 25.8334F, -15F, 0F, 0F, 29.1666F, 112.5F, 0F, 0F, 30F, 112.5F, 0F, 0F, 30.8334F, 112.5F, 0F, 0F, 40F, 0F, 0F, 0F));
        rot(clip, "head", frames(0F, 0F, 0F, 0F, 40F, 0F, 0F, 0F));
        rot(clip, "left_arm", frames(0F, 0F, 0F, 0F, 5F, -160.63F, 0F, 0F, 10F, -187.5F, 0F, 0F, 15F, -187.5F, 0F, 0F, 18.3334F, -184.4348F, -2.8631F, -22.3264F, 25.8334F, -184.4348F, -2.8631F, -22.3264F, 29.1666F, -174.4348F, -2.8631F, -22.3264F, 30.8334F, -184.4348F, -2.8631F, -22.3264F, 40F, 0F, 0F, 0F));
        rot(clip, "right_arm", frames(0F, 0F, 0F, 0F, 5F, -158.75F, 0F, 0F, 10F, -185F, 0F, 0F, 15F, -185F, 0F, 0F, 18.3334F, -184.6212F, 1.9114F, 22.4229F, 25.8334F, -184.6212F, 1.9114F, 22.4229F, 29.1666F, -174.6212F, 1.9114F, 22.4229F, 30.8334F, -184.6212F, 1.9114F, 22.4229F, 40F, 0F, 0F, 0F));
        rotOff(clip, "left_leg", frames(0F, 0F, 0F, 0F, 2.5F, 0F, 0F, 0F, 5F, 0F, 0F, 0F, 32.5F, 0F, 0F, 0F, 40F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 0F, 2.5F, 0F, 2F, -2F, 5F, 0F, 0F, -4F, 32.5F, 0F, 0F, -4F, 40F, 0F, 0F, 0F));
        return clip;
    }

    public static GolemClip magmaWalk() {
        GolemClip clip = new GolemClip(40);
        rot(clip, "upper_body", frames(0F, 20F, 7.5F, 0F, 10.8334F, 20F, -7.2049F, 0F, 20F, 20F, -7.5F, 0F, 40F, 20F, 7.5F, 0F));
        rot(clip, "head", frames(0F, -40F, 0F, 0F));
        rot(clip, "left_arm", frames(0F, -35F, 0F, 0F, 20F, -5F, 0F, 0F, 40F, -35F, 0F, 0F));
        rot(clip, "right_arm", frames(0F, 15F, 0F, 0F, 20F, -15F, 0F, 0F, 40F, 15F, 0F, 0F));
        rotOff(clip, "left_leg", frames(0F, 35F, 0F, 0F, 10F, 15F, 0F, 0F, 15F, 5F, 0F, 0F, 20F, 0F, 0F, 0F, 40F, 35F, 0F, 0F),
                frames(0F, 0F, 0F, 3F, 10F, 0F, 3F, 0F, 15F, 0F, 1.5F, -1.5F, 20F, 0F, 0F, -3F, 40F, 0F, 0F, 3F));
        rotOff(clip, "right_leg", frames(0F, 0F, 0F, 0F, 20F, 35F, 0F, 0F, 30F, 15F, 0F, 0F, 35F, 5F, 0F, 0F, 40F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, -3F, 20F, 0F, 0F, 3F, 30F, 0F, 3F, 0F, 35F, 0F, 1.5F, -1.5F, 40F, 0F, 0F, -3F));
        return clip;
    }

    public static GolemClip magmaCharge() {
        GolemClip clip = new GolemClip(20);
        rotOff(clip, "upper_body", frames(0F, 7.5071F, 2.4786F, 0.3265F, 10F, 7.5284F, -4.9571F, -0.6542F, 20F, 7.5071F, 2.4786F, 0.3265F),
                frames(0F, 0.25F, 0F, 0F, 10F, -0.25F, 0F, 0F, 20F, 0.25F, 0F, 0F));
        rotOff(clip, "head", frames(0F, -20F, 0F, 0F),
                frames(0F, 0F, 0F, -3F));
        rotOff(clip, "left_arm", frames(0F, -29.9627F, -0.8672F, -4.9244F, 10F, -9.9627F, -0.8672F, -4.9244F, 15F, -19.8873F, -0.8606F, -9.925F, 20F, -29.9627F, -0.8672F, -4.9244F),
                frames(0F, 0F, 0F, 0F, 10F, 0F, 0F, 0F, 15F, 0F, 1F, 0F, 20F, 0F, 0F, 0F));
        rotOff(clip, "right_arm", frames(0F, 9.9627F, -0.8672F, 4.9244F, 5F, -0.1127F, -0.8606F, 9.925F, 10F, -10.0373F, -0.8672F, 4.9244F, 20F, 9.9627F, -0.8672F, 4.9244F),
                frames(0F, 0F, 0F, 0F, 5F, 0F, 1F, 0F, 10F, 0F, 0F, 0F, 20F, 0F, 0F, 0F));
        rotOff(clip, "left_leg", frames(0F, 0F, 0F, 0F, 5F, 0F, 0F, 0F, 10F, 0F, 0F, 0F, 20F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 3F, 5F, 0F, 3F, 0F, 10F, 0F, 0F, -3F, 20F, 0F, 0F, 3F));
        rotOff(clip, "right_leg", frames(0F, 0F, 0F, 0F, 10F, 0F, 0F, 0F, 15F, 0F, 0F, 0F, 20F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, -3F, 10F, 0F, 0F, 3F, 15F, 0F, 3F, 0F, 20F, 0F, 0F, -3F));
        return clip;
    }

    public static GolemClip magmaSweep() {
        GolemClip clip = new GolemClip(36);
        rot(clip, "upper_body", frames(0F, 0F, 0F, 0F, 12.66672F, -4.0749F, -2.5245F, -14.2542F, 15.33328F, 46.7254F, 31.4855F, 16.7652F, 19.33328F, 46.7254F, 31.4855F, 16.7652F, 26.66672F, 33.0561F, 15.3165F, 6.6367F, 36F, 0F, 0F, 0F));
        rot(clip, "head", frames(0F, -20F, 0F, 0F));
        rot(clip, "left_arm", frames(0F, -20F, 0F, 0F, 9.33328F, 23.2199F, -62.0092F, -136.7812F, 12F, -6.928F, -8.9439F, -91.945F, 15.33328F, -89.1115F, 16.4137F, 6.1548F, 19.33328F, -89.1115F, 16.4137F, 6.1548F, 26.66672F, -89.1115F, 16.4137F, 6.1548F, 36F, -20F, 0F, 0F));
        rot(clip, "right_arm", frames(0F, 0F, 0F, 0F, 12.66672F, -7.8788F, -1.9114F, 22.4229F, 15.33328F, 5F, 0F, 0F, 19.33328F, 5F, 0F, 0F, 26.66672F, 5F, 0F, 0F, 31.33328F, 2.4905F, -0.2178F, 4.9952F, 36F, 0F, 0F, 0F));
        rotOff(clip, "right_leg", frames(0F, 0F, 0F, 0F, 10F, 0F, 0F, 0F, 12.66672F, 0F, 0F, 0F, 26.66672F, 0F, 0F, 0F, 31.33328F, 0F, 0F, 0F, 36F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 0F, 10F, 0F, 2F, -2F, 12.66672F, 0F, 0F, -4F, 26.66672F, 0F, 0F, -4F, 31.33328F, 0F, 3F, -2F, 36F, 0F, 0F, 0F));
        return clip;
    }

    public static GolemClip magmaPrepareFireball() {
        GolemClip clip = new GolemClip(50);
        rot(clip, "upper_body", frames(0F, 0F, 0F, 0F, 10F, -15F, 0F, 0F, 15F, -22.5F, 0F, 0F, 35.8334F, -22.5F, 0F, 0F, 50F, 0F, 0F, 0F));
        rot(clip, "head", frames(0F, -20F, 0F, 0F, 50F, -17.5F, 0F, 0F));
        rot(clip, "left_arm", frames(0F, -20F, 0F, 0F, 5F, -180.63F, 0F, 0F, 10F, -207.5F, 0F, 0F, 15F, -207.5F, 0F, 0F, 17.5F, -205.97F, -1.43F, -11.16F, 24.1666F, -207.5F, 0F, 0F, 27.5F, -205.97F, -1.43F, -11.16F, 31.6666F, -204.4348F, -2.8631F, -22.3264F, 35F, -194.4348F, -2.8631F, -22.3264F, 39.1666F, 26.186F, -0.8483F, -6.6152F, 50F, -20F, 0F, 0F));
        rot(clip, "right_arm", frames(0F, 0F, 0F, 0F, 5F, -158.75F, 0F, 0F, 10F, -185F, 0F, 0F, 15F, -185F, 0F, 0F, 20F, -184.81F, 0.96F, 11.21F, 23.3334F, -185F, 0F, 0F, 27.5F, -184.81F, 0.96F, 11.21F, 31.6666F, -184.6212F, 1.9114F, 22.4229F, 35F, -174.6212F, 1.9114F, 22.4229F, 39.1666F, 43.6308F, 0.5663F, 6.6438F, 50F, 0F, 0F, 0F));
        rotOff(clip, "left_leg", frames(0F, 0F, 0F, 0F, 5F, 0F, 0F, 0F, 10F, 0F, 0F, 0F, 32.5F, 0F, 0F, 0F, 50F, 0F, 0F, 0F),
                frames(0F, 0F, 0F, 0F, 5F, 0F, 2F, -2F, 10F, 0F, 0F, -4F, 32.5F, 0F, 0F, -4F, 50F, 0F, 0F, 0F));
        return clip;
    }
}
