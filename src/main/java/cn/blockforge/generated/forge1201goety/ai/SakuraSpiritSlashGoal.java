package cn.blockforge.generated.goetysupreme.ai;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;
import cn.blockforge.generated.goetysupreme.api.ISakuraSpirit;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritSlashEntity;

public class SakuraSpiritSlashGoal extends Goal {
    private static final int BURST_INTERVAL = 6;
    private static final int BURST_SIZE = 3;
    private static final int LONG_COOLDOWN = 300;
    private static final int MELEE_RESPECT_TICKS = 100;
    private static final double SHOOT_SPEED = 0.8D;

    private final Mob mob;
    private final ISakuraSpirit spirit;
    private int nextShotTick;
    private int burstCount;

    public SakuraSpiritSlashGoal(Mob mob, ISakuraSpirit spirit) {
        this.mob = mob;
        this.spirit = spirit;
    }

    @Override
    public boolean canUse() {
        if (!SakuraSpiritConfig.SLASH_VOLLEY.get()) {
            return false;
        }
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (!this.mob.hasLineOfSight(target)) {
            return false;
        }
        double distSqr = this.mob.distanceToSqr(target);
        double maxRange = SakuraSpiritConfig.SLASH_MAX_RANGE.get();
        double minRange = SakuraSpiritConfig.SLASH_MIN_RANGE.get();
        if (distSqr > maxRange * maxRange) {
            return false;
        }
        if (this.mob.getHealth() < this.mob.getMaxHealth() * SakuraSpiritConfig.lowHealthRatio()) {
            return true;
        }
        if (this.mob.tickCount < this.nextShotTick) {
            return false;
        }
        if (distSqr <= minRange * minRange) {
            return this.mob.tickCount - this.spirit.getLastMeleeHitTick() > MELEE_RESPECT_TICKS;
        }
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }
        Level level = this.mob.level();
        SakuraSpiritSlashEntity slash = new SakuraSpiritSlashEntity(level, this.mob);
        slash.setDamage(SakuraSpiritConfig.slashDamageOrZero());
        double dx = target.getX() - this.mob.getX();
        double dy = target.getY(0.5D) - this.mob.getEyeY();
        double dz = target.getZ() - this.mob.getZ();
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        level.playSound(null, this.mob.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP,
                SoundSource.PLAYERS, 0.5F, 1.0F);
        slash.setYRot((float) (Mth.atan2(-dx, -dz) * (180D / Math.PI)));
        slash.setXRot((float) (Mth.atan2(-dy, Math.sqrt(dx * dx + dz * dz)) * (180D / Math.PI)));
        slash.setDeltaMovement(dx / dist * SHOOT_SPEED, dy / dist * SHOOT_SPEED, dz / dist * SHOOT_SPEED);
        level.addFreshEntity(slash);
        if (++this.burstCount >= BURST_SIZE) {
            this.nextShotTick = this.mob.tickCount + LONG_COOLDOWN;
            this.burstCount = 0;
        } else {
            this.nextShotTick = this.mob.tickCount + BURST_INTERVAL;
        }
    }
}
