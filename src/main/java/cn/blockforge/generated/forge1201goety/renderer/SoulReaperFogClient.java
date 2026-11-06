package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.common.entities.hostile.Wight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.config.SoulReaperConfig;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperFogClient;
import cn.blockforge.generated.goetysupreme.effect.SoulReaperOmenEffect;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT)
public final class SoulReaperFogClient {

    private static final float DARK_PHASE = 0.45F;

    private static final float SOUL_R = 0.78F;
    private static final float SOUL_G = 0.94F;
    private static final float SOUL_B = 1.00F;

    private static final double UNSEEN_FOG_RANGE = 64.0D;

    private SoulReaperFogClient() {
    }

    private static float omenProgress() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || GeneratedMod.SOUL_REAPER_OMEN == null) {
            return -1.0F;
        }
        MobEffectInstance omen = player.getEffect(GeneratedMod.SOUL_REAPER_OMEN.get());
        if (omen == null) {
            return -1.0F;
        }
        int total = omen.getAmplifier() == SoulReaperOmenEffect.PHASE_ARRIVAL
                ? Math.max(1, Math.max(SoulReaperConfig.SPAWN_RITUAL_TICKS.get(),
                        SoulReaperConfig.SPAWN_WIGHT_DEATH_TICKS.get()))
                : Math.max(1, SoulReaperConfig.SPAWN_RITUAL_TICKS.get());
        return Mth.clamp(1.0F - (float) omen.getDuration() / (float) total, 0.0F, 1.0F);
    }

    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        float progress = omenProgress();
        if (progress >= 0.0F) {
            if (progress < DARK_PHASE) {
                float k = progress / DARK_PHASE;
                event.setRed(event.getRed() * (1.0F - k));
                event.setGreen(event.getGreen() * (1.0F - k));
                event.setBlue(event.getBlue() * (1.0F - k));
                return;
            }
            float k = (progress - DARK_PHASE) / (1.0F - DARK_PHASE);
            event.setRed(Mth.lerp(k, 0.0F, SOUL_R));
            event.setGreen(Mth.lerp(k, 0.0F, SOUL_G));
            event.setBlue(Mth.lerp(k, 0.0F, SOUL_B));
            return;
        }
        if (hasUnseenWight()) {
            event.setRed(SOUL_R);
            event.setGreen(SOUL_G);
            event.setBlue(SOUL_B);
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        float progress = omenProgress();
        if (progress >= 0.0F) {
            float near = 0.0F;
            float far;
            if (progress < DARK_PHASE) {
                far = Mth.lerp(progress / DARK_PHASE, 48.0F, 14.0F);
            } else {
                far = Mth.lerp((progress - DARK_PHASE) / (1.0F - DARK_PHASE), 14.0F, 26.0F);
            }
            event.setNearPlaneDistance(near);
            event.setFarPlaneDistance(far);
            event.setCanceled(true);
        }
    }

    private static boolean hasUnseenWight() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return false;
        }
        if (player.isCreative() || player.isSpectator()) {
            return false;
        }
        return !player.level().getEntitiesOfClass(Wight.class,
                player.getBoundingBox().inflate(UNSEEN_FOG_RANGE),
                wight -> wight.isAlive() && !wight.isScreaming() && !wight.isInvisible()
                        && !wight.isHallucination()).isEmpty();
    }
}
