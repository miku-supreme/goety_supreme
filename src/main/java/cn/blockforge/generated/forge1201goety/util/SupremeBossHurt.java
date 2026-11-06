package cn.blockforge.generated.goetysupreme.util;

import cn.blockforge.generated.goetysupreme.mixin.LivingEntityAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeHooks;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;

public final class SupremeBossHurt {

    @FunctionalInterface
    public interface Limit {

        float apply(float damage);

        Limit NONE = damage -> damage;

        static Limit cap(float cap) {
            return cap > 0.0F ? damage -> Math.min(damage, cap) : NONE;
        }
    }

    @FunctionalInterface
    public interface EntryAction {

        void onEntry(LivingEntity boss, DamageSource source, float rawAmount);
    }

    private SupremeBossHurt() {
    }

    public static float hurt(LivingEntity boss, DamageSource source, float amount, float cap) {
        return hurt(boss, source, amount, Limit.cap(cap), 1.0F, null);
    }

    public static float hurt(LivingEntity boss, DamageSource source, float amount, float cap,
            float postCapMultiplier) {
        return hurt(boss, source, amount, Limit.cap(cap), postCapMultiplier, null);
    }

    public static float hurt(LivingEntity boss, DamageSource source, float amount, Limit limit) {
        return hurt(boss, source, amount, limit, 1.0F, null);
    }

    public static float hurt(LivingEntity boss, DamageSource source, float amount, Limit limit,
            float postCapMultiplier) {
        return hurt(boss, source, amount, limit, postCapMultiplier, null);
    }

    public static float hurt(LivingEntity boss, DamageSource source, float amount, Limit limit,
            float postCapMultiplier, EntryAction entryAction) {
        if (boss.isInvulnerableTo(source)) {
            return 0.0F;
        }
        if (entryAction != null) {
            entryAction.onEntry(boss, source, amount);
        }
        if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            amount = (limit == null ? Limit.NONE : limit).apply(amount);
        }
        if (postCapMultiplier != 1.0F) {
            amount *= postCapMultiplier;
        }
        LivingEntityAccessor accessor = (LivingEntityAccessor) boss;
        amount = ForgeHooks.onLivingHurt(boss, source, amount);
        if (amount <= 0.0F) {
            return 0.0F;
        }
        amount = accessor.goetySupreme$invokeGetDamageAfterArmorAbsorb(source, amount);
        amount = accessor.goetySupreme$invokeGetDamageAfterMagicAbsorb(source, amount);
        float absorbed = Math.max(amount - boss.getAbsorptionAmount(), 0.0F);
        boss.setAbsorptionAmount(boss.getAbsorptionAmount() - (amount - absorbed));
        float unabsorbed = amount - absorbed;
        if (unabsorbed > 0.0F && unabsorbed < 3.4028235E37F
                && source.getEntity() instanceof ServerPlayer player) {
            player.awardStat(Stats.DAMAGE_DEALT, Math.round(unabsorbed * 10.0F));
        }
        absorbed = ForgeHooks.onLivingDamage(boss, source, absorbed);
        if (absorbed != 0.0F) {
            boss.getCombatTracker().recordDamage(source, absorbed);
            boss.setHealth(boss.getHealth() - absorbed);
            boss.setAbsorptionAmount(boss.getAbsorptionAmount() - absorbed);
            boss.gameEvent(GameEvent.ENTITY_DAMAGE);
        }
        return absorbed;
    }
}
