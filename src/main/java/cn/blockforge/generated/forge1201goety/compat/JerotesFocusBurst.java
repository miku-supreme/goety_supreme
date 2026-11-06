package cn.blockforge.generated.goetysupreme.compat;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

public final class JerotesFocusBurst {

    public static final int CASTING_INTERVAL_TICKS = 5;

    public static boolean shouldFire(int castTime, int intervalTicks) {
        int interval = Math.max(1, intervalTicks);
        return (castTime - 1) % interval == 0;
    }

    public static Vec3 rotateY(Vec3 vec, double degrees) {
        double rad = Math.toRadians(degrees);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        return new Vec3(vec.x * cos - vec.z * sin, vec.y, vec.x * sin + vec.z * cos);
    }

    public static SoundEvent jerotesSound(String path, SoundEvent fallback) {
        SoundEvent event = ForgeRegistries.SOUND_EVENTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
        return event == null ? fallback : event;
    }

    public static void castSound(LivingEntity caster, String soundPath, SoundEvent fallbackSound) {
        if (caster.isSilent()) {
            return;
        }
        SoundEvent sound = jerotesSound(soundPath, fallbackSound);
        if (caster.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                    sound, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    public static void showCastDisplay(ServerLevel level, LivingEntity caster, String displayPath, ParticleOptions fallback) {
        level.sendParticles(AbstractJerotesServant.jerotesParticle(displayPath, fallback),
                caster.getX(), caster.getBoundingBox().maxY + 0.5D, caster.getZ(),
                0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public static void showCastDisplay(ServerLevel level, LivingEntity caster, String displayPath,
            ParticleOptions fallback, int spellLevel) {
        showCastDisplay(level, caster, displayPath, fallback);
        if (spellLevel <= 0) {
            return;
        }
        level.sendParticles(AbstractJerotesServant.jerotesCoreParticle(
                        "spell_level_" + Math.min(spellLevel, 9), net.minecraft.core.particles.ParticleTypes.ENCHANT),
                caster.getX(), caster.getBoundingBox().maxY + 1.0D, caster.getZ(),
                0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public static void faceTarget(LivingEntity caster, LivingEntity target) {
        if (!(caster instanceof PlayerMobServant) || target == null || !target.isAlive()) {
            return;
        }
        double dx = target.getX() - caster.getX();
        double dy = target.getEyeY() - caster.getEyeY();
        double dz = target.getZ() - caster.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, horizontal) * (180.0D / Math.PI)));
        caster.setYRot(yaw);
        caster.setXRot(pitch);
        caster.setYHeadRot(yaw);
        caster.yBodyRot = yaw;
        ((PlayerMobServant) caster).getLookControl().setLookAt(target, 360.0F, 360.0F);
    }

    private static final Deque<PendingShot> PENDING_SHOTS = new ArrayDeque<>();

    public static void scheduleShot(int delayTicks, Runnable shot) {
        if (delayTicks <= 0) {
            shot.run();
            return;
        }
        PENDING_SHOTS.add(new PendingShot(delayTicks, shot));
    }

    public static void scheduleExtraShots(int shots, int intervalTicks, LivingEntity caster, Runnable shot) {
        int interval = Math.max(1, intervalTicks);
        for (int i = 1; i < shots; ++i) {
            scheduleShot(i * interval, () -> {
                if (!caster.isRemoved()) {
                    if (caster instanceof PlayerMobServant servant) {
                        faceTarget(servant, servant.getTarget());
                    }
                    shot.run();
                }
            });
        }
    }

    private static final class PendingShot {
        private int ticksLeft;
        private final Runnable shot;

        private PendingShot(int ticksLeft, Runnable shot) {
            this.ticksLeft = ticksLeft;
            this.shot = shot;
        }
    }

    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
    public static final class BurstSink {
        private BurstSink() {
        }

        @SubscribeEvent
        public static void onServerTickEnd(TickEvent.ServerTickEvent event) {
            if (event.phase != TickEvent.Phase.END || PENDING_SHOTS.isEmpty()) {
                return;
            }
            for (Iterator<PendingShot> it = PENDING_SHOTS.iterator(); it.hasNext();) {
                PendingShot pending = it.next();
                if (--pending.ticksLeft <= 0) {
                    pending.shot.run();
                    it.remove();
                }
            }
        }
    }

    private JerotesFocusBurst() {
    }
}
