package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public interface BreathAreaEntity {

    int getLifeTick();

    LivingEntity getOwner();

    int getSpellLevel();

    float getMaxReach();

    float getSpellAngle();

    int beamLightI();

    int beamLightII();

    int beamLightIII();

    default int getMaxLife() {
        return 40;
    }

    default int getCircleStart() {
        return 5;
    }

    default int getCircleStop() {
        return this.getHurtTime();
    }

    default int getHurtTime() {
        return 10;
    }

    default int getBallStopStripeStart() {
        return 8;
    }

    default int getStripeStop() {
        int level = this.getSpellLevel();
        if (level <= 3) {
            return 14;
        }
        if (level <= 6) {
            return 17;
        }
        if (level <= 8) {
            return 20;
        }
        return 24;
    }

    default float getInnerCircleRadius() {
        return 1.0F;
    }

    default float getOuterCircleRadius() {
        return 0.75F;
    }

    default float getRingSpawnRadiusMult() {
        return 1.6F;
    }

    default float getPointRadiusMul() {
        return 0.08F;
    }

    default int getStripCount() {
        return 40;
    }

    default float computeRingRadiusMul(float life) {
        return computeRingRadiusMul(life, this.getCircleStart(), this.getCircleStop(),
                this.getRingSpawnRadiusMult(), this.getPointRadiusMul());
    }

    default int stripColor(float t) {
        return stripColor(this.beamLightI(), this.beamLightII(), this.beamLightIII(), t);
    }

    default ResourceLocation getTextureLocation() {
        return TEXTURE;
    }

    ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            "jerotes", "textures/entity/beam/ray.png");

    public static float computeRingRadiusMul(float life, int circleStart, int circleStop,
            float spawnRadiusMult, float pointRadiusMul) {
        if (life <= (float) circleStart) {
            float t = Mth.clamp(life / (float) Math.max(1, circleStart), 0.0F, 1.0F);
            return Mth.lerp(easeOutQuad(t), spawnRadiusMult, 1.0F);
        }
        if (life <= (float) circleStop) {
            float t = Mth.clamp((life - (float) circleStart)
                    / (float) Math.max(1, circleStop - circleStart), 0.0F, 1.0F);
            return Mth.lerp(easeInQuad(t), 1.0F, pointRadiusMul);
        }
        return pointRadiusMul;
    }

    public static int stripColor(int c1, int c2, int c3, float t) {
        float[] rgb = gradientColor(c1, c2, c3, t);
        int r = (int) (Mth.clamp(rgb[0], 0.0F, 1.0F) * 255.0F);
        int g = (int) (Mth.clamp(rgb[1], 0.0F, 1.0F) * 255.0F);
        int b = (int) (Mth.clamp(rgb[2], 0.0F, 1.0F) * 255.0F);
        return r << 16 | g << 8 | b;
    }

    private static float easeOutQuad(float t) {
        return 1.0F - (1.0F - t) * (1.0F - t);
    }

    private static float easeInQuad(float t) {
        return t * t;
    }

    private static float[] gradientColor(int c1, int c2, int c3, float t) {
        int r;
        int g;
        int b;
        if (t < 0.5F) {
            float lt = t / 0.5F;
            r = (int) Mth.lerp(lt, (float) (c1 >> 16 & 0xFF), (float) (c2 >> 16 & 0xFF));
            g = (int) Mth.lerp(lt, (float) (c1 >> 8 & 0xFF), (float) (c2 >> 8 & 0xFF));
            b = (int) Mth.lerp(lt, (float) (c1 & 0xFF), (float) (c2 & 0xFF));
        } else {
            float lt = (t - 0.5F) / 0.5F;
            r = (int) Mth.lerp(lt, (float) (c2 >> 16 & 0xFF), (float) (c3 >> 16 & 0xFF));
            g = (int) Mth.lerp(lt, (float) (c2 >> 8 & 0xFF), (float) (c3 >> 8 & 0xFF));
            b = (int) Mth.lerp(lt, (float) (c2 & 0xFF), (float) (c3 & 0xFF));
        }
        return new float[] { (float) r / 255.0F, (float) g / 255.0F, (float) b / 255.0F };
    }
}
