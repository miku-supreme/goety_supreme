package cn.blockforge.generated.goetysupreme.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageSpellBridge;

public final class JerotesVillageSpellBridge {

    private static final boolean LOADED = ModList.get().isLoaded("jerotesvillage");

    private JerotesVillageSpellBridge() {
    }

    public static boolean isLoaded() {
        return LOADED;
    }

    public static boolean wormThunderstorm(int spellLevel, LivingEntity caster, Entity target) {
        return Impl.wormThunderstorm(spellLevel, caster, target);
    }

    public static boolean chaoticDischarge(int spellLevel, LivingEntity caster, Entity target) {
        return Impl.chaoticDischarge(spellLevel, caster, target);
    }

    private static final class Impl {
        static boolean wormThunderstorm(int spellLevel, LivingEntity caster, Entity target) {
            return com.jerotes.jerotesvillage.spell.OtherSpellList
                    .WormThunderstorm(spellLevel, caster, target).spellUse();
        }

        static boolean chaoticDischarge(int spellLevel, LivingEntity caster, Entity target) {
            return com.jerotes.jerotesvillage.spell.OtherSpellList
                    .ChaoticDischarge(spellLevel, caster, target).spellUse();
        }
    }
}
