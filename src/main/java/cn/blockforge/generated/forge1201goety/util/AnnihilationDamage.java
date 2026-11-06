package cn.blockforge.generated.goetysupreme.util;

import java.lang.reflect.Field;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import cn.blockforge.generated.goetysupreme.debug.AnnihilationProbe;
import cn.blockforge.generated.goetysupreme.util.AnnihilationDamage;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import net.minecraft.world.entity.LivingEntity;

public final class AnnihilationDamage {
    private static final ResourceKey<DamageType> PIERCE = key("annihilation_pierce");
    private static final ResourceKey<DamageType> FORCE = key("annihilation_pierce_invuln");

    private static final String[] GOETY_GATE_FIELDS = {"moddedInvul", "obsidianInvul"};

    private AnnihilationDamage() { }

    private static ResourceKey<DamageType> key(String path) {
        return ResourceKey.create(Registries.DAMAGE_TYPE,
                ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, path));
    }

    public static boolean hurt(LivingEntity owner, LivingEntity victim, float amount, float fallback) {
        if (!victim.isAlive() || victim.isInvulnerable()) {
            if (AnnihilationProbe.isEnabled()) {
                AnnihilationProbe.record("[ANNIHILATION-REJECT] 入口即退 " + AnnihilationProbe.entity(victim)
                        + " alive=" + victim.isAlive() + " invulnerable=" + victim.isInvulnerable());
            }
            return false;
        }
        if (AnnihilationProbe.isEnabled()) {
            AnnihilationProbe.record("[ANNIHILATION-START] victim=" + AnnihilationProbe.entity(victim)
                    + " amount=" + amount + " fallback=" + fallback
                    + " dist=" + AnnihilationProbe.trim(owner.distanceTo(victim))
                    + " " + AnnihilationProbe.state(victim)
                    + " | " + AnnihilationProbe.goetyGates(victim)
                    + " | config{" + AnnihilationProbe.goetyConfig() + "}");
        }
        clearGoetyGates(victim);
        ResourceKey<DamageType> primary = victim instanceof Player ? PIERCE : FORCE;
        WitherSkull impact = new WitherSkull(EntityType.WITHER_SKULL, owner.level());
        impact.setOwner(owner);
        impact.setPos(victim.position());
        try {
            float before = victim.getHealth();
            boolean applied = hit(owner, victim, impact, owner, primary, amount);
            if (!applied && victim.isAlive()) {
                applied = hit(owner, victim, impact, impact, primary, amount);
            }
            if (!applied && victim.isAlive() && !victim.isInvulnerable() && !(victim instanceof Player)) {
                applied = hit(owner, victim, impact, impact, FORCE, fallback);
            }
            if (!(victim instanceof Player) && victim.isAlive() && !victim.isInvulnerable()
                    && (applied || AnnihilationProbe.hasGoetyGates(victim))) {
                applied |= topUp(owner, victim, impact, primary, amount, before);
            }
            return applied;
        } finally {
            impact.discard();
        }
    }

    public static boolean hurtPlayer(LivingEntity owner, Player victim, float amount) {
        if (!victim.isAlive() || victim.isInvulnerable() || amount <= 0.0F || !Float.isFinite(amount)) {
            return false;
        }
        WitherSkull impact = new WitherSkull(EntityType.WITHER_SKULL, owner.level());
        impact.setOwner(owner);
        impact.setPos(victim.position());
        try {
            return hit(owner, victim, impact, owner, PIERCE, amount);
        } finally {
            impact.discard();
        }
    }

    private static void clearGoetyGates(LivingEntity victim) {
        boolean cleared = false;
        for (String name : GOETY_GATE_FIELDS) {
            cleared |= setIntField(victim, name, 0);
        }
        if (cleared && AnnihilationProbe.isEnabled()) {
            AnnihilationProbe.record("[GATE-CLEAR] " + AnnihilationProbe.entity(victim)
                    + " 清零 " + String.join("/", GOETY_GATE_FIELDS));
        }
    }

    private static boolean setIntField(Object target, String name, int value) {
        Class<?> cls = target.getClass();
        while (cls != null) {
            try {
                Field field = cls.getDeclaredField(name);
                if (field.getType() != int.class) {
                    return false;
                }
                field.setAccessible(true);
                if (field.getInt(target) == value) {
                    return false;
                }
                field.setInt(target, value);
                return true;
            } catch (NoSuchFieldException missing) {
                cls = cls.getSuperclass();
            } catch (Throwable t) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", t.toString());
                return false;
            }
        }
        return false;
    }

    private static boolean topUp(LivingEntity owner, LivingEntity victim, WitherSkull impact,
            ResourceKey<DamageType> type, float amount, float before) {
        float dealt = before - victim.getHealth();
        float remaining = amount - dealt;
        if (remaining <= 1.0E-3F) {
            return dealt > 1.0E-3F;
        }
        if (victim.getHealth() - remaining <= 0.0F) {
            clearGoetyGates(victim);
            victim.invulnerableTime = 0;
            boolean killed = victim.hurt(source(owner, impact, impact, type), Float.MAX_VALUE);
            if (AnnihilationProbe.isEnabled()) {
                AnnihilationProbe.record("[TOPUP] 致死补足 " + AnnihilationProbe.entity(victim)
                        + " 已掉=" + AnnihilationProbe.trim(dealt)
                        + " 差额=" + AnnihilationProbe.trim(remaining)
                        + " 补刀=" + killed);
            }
            return true;
        }
        victim.setHealth(victim.getHealth() - remaining);
        if (AnnihilationProbe.isEnabled()) {
            AnnihilationProbe.record("[TOPUP] 补足 " + AnnihilationProbe.entity(victim)
                    + " 已掉=" + AnnihilationProbe.trim(dealt)
                    + " 差额=" + AnnihilationProbe.trim(remaining));
        }
        return true;
    }

    private static DamageSource source(LivingEntity owner, WitherSkull direct, Entity cause,
            ResourceKey<DamageType> type) {
        return new DamageSource(owner.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), direct, cause);
    }

    private static boolean hit(LivingEntity owner, LivingEntity victim, WitherSkull direct,
            net.minecraft.world.entity.Entity cause, ResourceKey<DamageType> type, float amount) {
        if (victim.isInvulnerable() || amount <= 0.0F || !Float.isFinite(amount)) {
            if (AnnihilationProbe.isEnabled()) {
                AnnihilationProbe.record("[HIT-SKIP] type=" + type.location() + " amount=" + amount
                        + " invulnerable=" + victim.isInvulnerable()
                        + " " + AnnihilationProbe.entity(victim));
            }
            return false;
        }
        if (!(victim instanceof Player)) {
            victim.invulnerableTime = 0;
        }
        DamageSource source = source(owner, direct, cause, type);
        float before = victim.getHealth();
        boolean result = victim.hurt(source, amount);
        if (AnnihilationProbe.isEnabled()) {
            float after = victim.getHealth();
            AnnihilationProbe.record("[HIT] " + AnnihilationProbe.damageType(source)
                    + " " + AnnihilationProbe.chain(source)
                    + " amount=" + amount
                    + " isInvulnerableTo=" + victim.isInvulnerableTo(source)
                    + " hurt=" + result
                    + " hp=" + AnnihilationProbe.trim(before) + "->" + AnnihilationProbe.trim(after)
                    + " delta=" + AnnihilationProbe.trim(before - after)
                    + (result && before <= after ? "  <== hurt 返回 true 但血量没掉" : "")
                    + " " + AnnihilationProbe.state(victim)
                    + " | " + AnnihilationProbe.goetyGates(victim));
        }
        return result;
    }
}
