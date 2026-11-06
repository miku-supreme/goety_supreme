package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.HarmfulSnowball;
import cn.blockforge.generated.goetysupreme.util.IframeHit;

public final class IframeHit {

    public static final int KNOCKBACK_WINDOW_TICKS = 10;

    private IframeHit() {
    }

    public static boolean hurt(LivingEntity victim, DamageSource source, float damage) {
        int invulnerable = victim.invulnerableTime;
        boolean knockbackAllowed = invulnerable <= KNOCKBACK_WINDOW_TICKS;
        Vec3 motion = victim.getDeltaMovement();
        victim.invulnerableTime = 0;
        boolean hurt = victim.hurt(source, damage);
        victim.invulnerableTime = Math.max(invulnerable, victim.invulnerableTime);
        if (hurt && !knockbackAllowed && victim.isAlive()) {
            victim.setDeltaMovement(motion);
            victim.hasImpulse = true;
        }
        return hurt;
    }
}
