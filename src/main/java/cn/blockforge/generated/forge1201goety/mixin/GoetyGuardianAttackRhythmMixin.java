package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.config.GuardianServantConfig;
import com.Polarice3.Goety.common.entities.ally.GuardianServant;
import java.lang.reflect.Field;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.Polarice3.Goety.common.entities.ally.GuardianServant$GuardianAttackGoal",
        remap = false)
public abstract class GoetyGuardianAttackRhythmMixin {

    private static final org.slf4j.Logger LOGGER = com.mojang.logging.LogUtils.getLogger();

    private static Field goetySupreme$guardianField;
    private static Field goetySupreme$attackTimeField;
    private static Field goetySupreme$elderField;
    private static boolean goetySupreme$lookupFailed;

    private static void goetySupreme$resolveFields(Object goal) {
        if (goetySupreme$guardianField != null && goetySupreme$attackTimeField != null) {
            return;
        }
        if (goetySupreme$lookupFailed) {
            return;
        }
        try {
            goetySupreme$guardianField = goal.getClass().getDeclaredField("guardian");
            goetySupreme$attackTimeField = goal.getClass().getDeclaredField("attackTime");
            goetySupreme$guardianField.setAccessible(true);
            goetySupreme$attackTimeField.setAccessible(true);
            try {
                goetySupreme$elderField = goal.getClass().getDeclaredField("elder");
                goetySupreme$elderField.setAccessible(true);
            } catch (NoSuchFieldException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
            }
        } catch (ReflectiveOperationException e) {
            goetySupreme$lookupFailed = true;
            LOGGER.warn("[goety_supreme] GuardianAttackGoal 结构变了，守卫者仆从连射功能不可用。");
        }
    }

    private static Mob goetySupreme$guardian(Object goal) {
        goetySupreme$resolveFields(goal);
        if (goetySupreme$guardianField == null) {
            return null;
        }
        try {
            Object guardian = goetySupreme$guardianField.get(goal);
            return guardian instanceof Mob mob ? mob : null;
        } catch (ReflectiveOperationException e) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", e.toString());
            return null;
        }
    }

    private static int goetySupreme$attackTime(Object goal, int fallback) {
        if (goetySupreme$attackTimeField == null) {
            return fallback;
        }
        try {
            return goetySupreme$attackTimeField.getInt(goal);
        } catch (ReflectiveOperationException e) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", e.toString());
            return fallback;
        }
    }

    private static boolean goetySupreme$elder(Object goal, Mob guardian) {
        if (goetySupreme$elderField != null) {
            try {
                return goetySupreme$elderField.getBoolean(goal);
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return guardian != null && "ElderGuardianServant".equals(guardian.getClass().getSimpleName());
    }

    private int goetySupreme$prevAttackTime = Integer.MIN_VALUE;
    private LivingEntity goetySupreme$lastTarget;
    private int goetySupreme$beamsFired;

    @Inject(method = "m_8056_", at = @At("TAIL"), require = 0)
    private void goetySupreme$burstRoundStart(CallbackInfo callback) {
        this.goetySupreme$prevAttackTime = Integer.MIN_VALUE;
        this.goetySupreme$lastTarget = null;
        this.goetySupreme$beamsFired = 0;
    }

    @Inject(method = "m_8037_", at = @At("HEAD"), require = 0)
    private void goetySupreme$burstSnapshot(CallbackInfo callback) {
        Mob self = goetySupreme$guardian(this);
        if (self == null) {
            this.goetySupreme$prevAttackTime = Integer.MIN_VALUE;
            this.goetySupreme$lastTarget = null;
            return;
        }
        this.goetySupreme$prevAttackTime = goetySupreme$attackTime(this, Integer.MIN_VALUE);
        this.goetySupreme$lastTarget = self.getTarget();
    }

    @Inject(method = "m_8037_", at = @At("TAIL"), require = 0)
    private void goetySupreme$burstContinue(CallbackInfo callback) {
        int prev = this.goetySupreme$prevAttackTime;
        LivingEntity last = this.goetySupreme$lastTarget;
        this.goetySupreme$prevAttackTime = Integer.MIN_VALUE;
        this.goetySupreme$lastTarget = null;
        if (last == null || prev == Integer.MIN_VALUE) {
            return;
        }
        Mob self = goetySupreme$guardian(this);
        if (self == null) {
            return;
        }
        int now = goetySupreme$attackTime(this, Integer.MIN_VALUE);
        if (now == Integer.MIN_VALUE || now != prev + 1) {
            return;
        }
        if (!(self instanceof GuardianServant guardianServant)) {
            return;
        }
        int duration = guardianServant.getAttackDuration();
        if (now < duration) {
            return; // 还没到开火阈值，本刻没有射出光束
        }
        this.goetySupreme$beamsFired++;
        if (!GuardianServantConfig.BURST_ENABLED.get()) {
            return;
        }
        int limit = GuardianServantConfig.BURST_SHOTS.get();
        if (limit > 0 && this.goetySupreme$beamsFired >= limit) {
            return;
        }
        if (!last.isAlive()) {
            return;
        }
        if (!goetySupreme$elder(this, self) && self.distanceToSqr(last) <= 9.0D) {
            return;
        }
        if (self.getTarget() == null) {
            self.setTarget(last);
        }
        int gap = Math.max(1, GuardianServantConfig.BURST_INTERVAL.get());
        goetySupreme$setAttackTime(this, duration - gap);
        self.level().broadcastEntityEvent(self, (byte) 4);
    }

    private static void goetySupreme$setAttackTime(Object goal, int value) {
        if (goetySupreme$attackTimeField == null) {
            return;
        }
        try {
            goetySupreme$attackTimeField.setInt(goal, value);
        } catch (ReflectiveOperationException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
        }
    }
}
