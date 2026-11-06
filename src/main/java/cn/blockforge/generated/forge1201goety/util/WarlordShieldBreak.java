package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.ally.undead.skeleton.VanguardServant;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.BlackguardServant;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageServantShieldBreak;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.WarlordShieldBreak;

public final class WarlordShieldBreak {

    public static final int DISABLE_TICKS = 100;

    private static final ResourceKey<DamageType> WARLORD_SLASH_PIERCE =
            ResourceKey.create(Registries.DAMAGE_TYPE,
                    ResourceLocation.fromNamespaceAndPath("goety_supreme", "warlord_slash_pierce"));

    private WarlordShieldBreak() {
    }

    public static DamageSource piercingSource(LivingEntity attacker) {
        return attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolder(WARLORD_SLASH_PIERCE)
                .<DamageSource>map(holder -> new DamageSource(holder, attacker))
                .orElseGet(() -> attacker.damageSources().mobAttack(attacker));
    }

    public static void breakGuard(LivingEntity victim) {
        if (victim instanceof Player player) {
            if (player.isBlocking()) {
                player.disableShield(true);
            }
        } else if (victim instanceof PlayerMobServant servant) {
            servant.warlordBreakShield(DISABLE_TICKS);
        } else if (JerotesVillageCompat.isJvillageServantLoadable()
                && JvillageServantShieldBreak.breakShield(victim, DISABLE_TICKS)) {
        } else if (victim.isBlocking()) {
            victim.stopUsingItem();
        }
    }

    public static boolean hasGoetyGuard(LivingEntity victim) {
        return victim instanceof VanguardServant || victim instanceof BlackguardServant;
    }

    public static void attackThroughGoetyGuard(LivingEntity victim, Runnable hit) {
        if (victim instanceof VanguardServant vanguard && vanguard.hasShield()) {
            int health = vanguard.getShieldHealth();
            vanguard.setShield(false);
            try {
                hit.run();
            } finally {
                vanguard.setShield(true);
                vanguard.setShieldHealth(health);
            }
        } else if (victim instanceof BlackguardServant blackguard && blackguard.hasShield()) {
            int health = blackguard.getShieldHealth();
            blackguard.setShield(false);
            try {
                hit.run();
            } finally {
                blackguard.setShield(true);
                blackguard.setShieldHealth(health);
            }
        } else {
            hit.run();
        }
    }
}
