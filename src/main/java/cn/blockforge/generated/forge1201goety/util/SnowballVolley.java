package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.utils.CuriosFinder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.monster.Blaze;
import cn.blockforge.generated.goetysupreme.config.SnowGolemServantConfig;
import cn.blockforge.generated.goetysupreme.util.SnowballVolley;

public final class SnowballVolley {

    private SnowballVolley() {
    }

    public static float damage(int potency, LivingEntity victim) {
        return damage(SnowGolemServantConfig.SNOWBALL_BASE_DAMAGE.get().floatValue(), potency, victim);
    }

    public static float damage(float baseDamage, int potency, LivingEntity victim) {
        float bonus = Math.max(0, potency);
        float damage = baseDamage
                + SnowGolemServantConfig.SNOWBALL_POTENCY_BONUS.get().floatValue() * bonus;
        return victim instanceof Blaze ? damage * 3.0F : damage;
    }

    public static void applyEffect(LivingEntity patron, int potency, LivingEntity victim) {
        if (victim == null || !victim.isAlive()) {
            return;
        }
        MobEffect effect = MobEffects.MOVEMENT_SLOWDOWN;
        if (patron != null && CuriosFinder.hasFrostRobes(patron)) {
            effect = GoetyEffects.FREEZING.get();
        }
        int safePotency = Math.max(0, potency);
        int duration = 100 + safePotency * 20;
        int amplifier = safePotency / 3;
        victim.addEffect(new MobEffectInstance(effect, duration, amplifier));
    }

    public static boolean isAlliedTo(LivingEntity thrower, Entity candidate) {
        if (thrower == null || candidate == null || thrower == candidate) {
            return true;
        }
        if (thrower instanceof IOwned self && self.getTrueOwner() != null
                && candidate == self.getTrueOwner()) {
            return true;
        }
        if (candidate instanceof LivingEntity living && thrower.isAlliedTo(living)) {
            return true;
        }
        if (candidate instanceof IOwned other) {
            if (thrower instanceof IOwned self) {
                if (self.getTrueOwner() != null && self.getTrueOwner().getUUID().equals(other.getOwnerId())) {
                    return true;
                }
            } else if (thrower.getUUID().equals(other.getOwnerId())) {
                return true;
            }
        }
        if (candidate instanceof OwnableEntity ownable) {
            LivingEntity owner = ownable.getOwner();
            if (owner == thrower || (thrower instanceof IOwned self && self.getTrueOwner() == owner)) {
                return true;
            }
        }
        return false;
    }
}
