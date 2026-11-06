package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.util.TargetImmunity;

public final class TargetImmunity {

    public enum Form {

        TOTAL,

        WHITELIST,

        BLACKLIST
    }

    private static final List<MobEffect> BENIGN_PROBES =
            List.of(MobEffects.ABSORPTION, MobEffects.MOVEMENT_SPEED, MobEffects.GLOWING);

    private static final List<MobEffect> HARMFUL_PROBES =
            List.of(MobEffects.LEVITATION, MobEffects.DARKNESS, MobEffects.POISON);

    private static final int CACHE_TICKS = 40;

    private record Cached(Form form, int tick) {
    }

    private static final Map<LivingEntity, Cached> CACHE =
            Collections.synchronizedMap(new WeakHashMap<>());

    private TargetImmunity() {
    }

    public static Form of(LivingEntity target) {
        if (target == null) {
            return Form.TOTAL;
        }
        int now = target.tickCount;
        Cached cached = CACHE.get(target);
        if (cached != null && now - cached.tick() < CACHE_TICKS) {
            return cached.form();
        }
        Form form = classify(target);
        CACHE.put(target, new Cached(form, now));
        return form;
    }

    private static Form classify(LivingEntity target) {
        int benign = acceptedCount(target, BENIGN_PROBES);
        if (benign == 0) {
            return Form.TOTAL;
        }
        int harmful = acceptedCount(target, HARMFUL_PROBES);
        if (harmful == 0 || benign < BENIGN_PROBES.size()) {
            return Form.WHITELIST;
        }
        return Form.BLACKLIST;
    }

    private static int acceptedCount(LivingEntity target, List<MobEffect> effects) {
        int count = 0;
        for (MobEffect effect : effects) {
            if (target.canBeAffected(new MobEffectInstance(effect, 1))) {
                count++;
            }
        }
        return count;
    }

    public static boolean lands(LivingEntity target, MobEffect core, MobEffect... extras) {
        if (target == null || core == null) {
            return false;
        }
        Form form = of(target);
        if (form == Form.TOTAL) {
            return false;
        }
        boolean coreOk = target.canBeAffected(new MobEffectInstance(core, 1));
        if (form == Form.WHITELIST) {
            return coreOk;
        }
        if (coreOk) {
            return true;
        }
        for (MobEffect extra : extras) {
            if (extra != null && target.canBeAffected(new MobEffectInstance(extra, 1))) {
                return true;
            }
        }
        return false;
    }

    public static boolean weakeningLands(LivingEntity target) {
        if (GoetyEffects.SAPPED == null) {
            return target != null && target.canBeAffected(new MobEffectInstance(MobEffects.WEAKNESS, 1));
        }
        return lands(target, GoetyEffects.SAPPED.get(), MobEffects.WEAKNESS);
    }
}
