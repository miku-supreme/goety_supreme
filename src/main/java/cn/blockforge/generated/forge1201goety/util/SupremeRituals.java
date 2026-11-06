package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.ritual.ModRitualFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.LichCheckRitual;
import cn.blockforge.generated.goetysupreme.entity.NamelessWitherRitual;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobSummonRitual;
import cn.blockforge.generated.goetysupreme.entity.SedimentWeaponRitual;
import cn.blockforge.generated.goetysupreme.util.SupremeRituals;

public final class SupremeRituals {

    private static final ResourceLocation RITUAL_REGISTRY =
            ResourceLocation.fromNamespaceAndPath("goety", "ritual_factory");

    public static final DeferredRegister<ModRitualFactory> RITUALS =
            DeferredRegister.create(RITUAL_REGISTRY, GeneratedMod.MOD_ID);

    public static final RegistryObject<ModRitualFactory> SUMMON_PLAYER_MOB =
            RITUALS.register("summon_player_mob", () -> new ModRitualFactory(PlayerMobSummonRitual::new));

    public static final RegistryObject<ModRitualFactory> SUMMON_SEDIMENT_WEAPON =
            RITUALS.register("summon_sediment_weapon",
                    () -> new ModRitualFactory(SedimentWeaponRitual::new));

    public static final RegistryObject<ModRitualFactory> LICH_CHECK =
            RITUALS.register("lich_check",
                    () -> new ModRitualFactory(LichCheckRitual::new));

    public static final RegistryObject<ModRitualFactory> NAMELESS_WITHER =
            RITUALS.register("nameless_wither",
                    () -> new ModRitualFactory(NamelessWitherRitual::new));

    private SupremeRituals() {
    }
}
