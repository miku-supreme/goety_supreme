package cn.blockforge.generated.goetysupreme.compat;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import cn.blockforge.generated.goetysupreme.entity.BitterColdWindServant;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.entity.DroughtBlazeServant;
import cn.blockforge.generated.goetysupreme.entity.DroughtWildfireServant;
import cn.blockforge.generated.goetysupreme.entity.EnderStowawayServant;
import cn.blockforge.generated.goetysupreme.entity.FlamoarServant;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballServant;
import cn.blockforge.generated.goetysupreme.entity.FrostYetiServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.HeavyArmoredSedimentGuardServant;
import cn.blockforge.generated.goetysupreme.entity.IronTeethServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompatManager;
import cn.blockforge.generated.goetysupreme.entity.LightningWormPartEntity;
import cn.blockforge.generated.goetysupreme.entity.LightningWormServant;
import cn.blockforge.generated.goetysupreme.event.ModEntityEvents;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.PrimordierServant;
import cn.blockforge.generated.goetysupreme.entity.RoyalWarPythonServant;
import cn.blockforge.generated.goetysupreme.entity.SeaWaveServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderAnimatedArmorServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSpiderServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderZombieServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentSoulStabilizerServant;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldier;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;
import cn.blockforge.generated.goetysupreme.entity.SerponFlameWizard;
import cn.blockforge.generated.goetysupreme.entity.SerponIntruder;
import cn.blockforge.generated.goetysupreme.entity.SerponPioneer;
import cn.blockforge.generated.goetysupreme.entity.SerponRioter;
import cn.blockforge.generated.goetysupreme.entity.WarPythonPartEntity;
import cn.blockforge.generated.goetysupreme.entity.WarPythonServant;

public final class JerotesVillageCompatManager {

    private JerotesVillageCompatManager() {
    }

    public static void init(IEventBus modEventBus) {
        ModEntityTypes.JEROTES_ENTITY_TYPES.register(modEventBus);
        GeneratedMod.JEROTES_BLOCKS.register(modEventBus);
        GeneratedMod.JEROTES_ITEMS.register(modEventBus);
    }

    public static void setCustomAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.SEDIMENT_GUARD_SERVANT.get(),
                SedimentGuardServant.registerAttributes().build());
        event.put(ModEntityTypes.SEDIMENT_SOUL_STABILIZER_SERVANT.get(),
                SedimentSoulStabilizerServant.registerAttributes().build());
        event.put(ModEntityTypes.HEAVY_ARMORED_SEDIMENT_GUARD_SERVANT.get(),
                HeavyArmoredSedimentGuardServant.registerAttributes().build());
        event.put(ModEntityTypes.SEDIMENT_LORD_SERVANT.get(),
                SedimentLordServant.registerAttributes().build());
        event.put(ModEntityTypes.CORROSIVER_SERVANT.get(),
                CorrosiverServant.createAttributes().build());
        event.put(ModEntityTypes.LIGHTNING_WORM_SERVANT.get(),
                LightningWormServant.createAttributes().build());
        event.put(ModEntityTypes.LIGHTNING_WORM_PART.get(),
                LightningWormPartEntity.createAttributes().build());
        event.put(ModEntityTypes.BITTER_COLD_WIND_SERVANT.get(),
                BitterColdWindServant.createAttributes().build());
        event.put(ModEntityTypes.FLAMOAR_SERVANT.get(),
                FlamoarServant.createAttributes().build());
        event.put(ModEntityTypes.PRIMORDIER_SERVANT.get(),
                PrimordierServant.createAttributes().build());
        event.put(ModEntityTypes.FROST_YETI_SERVANT.get(),
                FrostYetiServant.createAttributes().build());
        event.put(ModEntityTypes.FLYING_FIREBALL_SERVANT.get(),
                FlyingFireballServant.createAttributes().build());
        event.put(ModEntityTypes.IRON_TEETH_SERVANT.get(),
                IronTeethServant.createAttributes().build());
        event.put(ModEntityTypes.BULGE_SERVANT.get(), BulgeServant.createAttributes().build());
        event.put(ModEntityTypes.SEA_WAVE_SERVANT.get(), SeaWaveServant.createAttributes().build());
        event.put(ModEntityTypes.DROUGHT_WILDFIRE_SERVANT.get(),
                DroughtWildfireServant.createAttributes().build());
        event.put(ModEntityTypes.DROUGHT_BLAZE_SERVANT.get(),
                DroughtBlazeServant.createAttributes().build());
        event.put(ModEntityTypes.SECOND_ROUNDER_ZOMBIE_SERVANT.get(),
                SecondRounderZombieServant.createAttributes().build());
        event.put(ModEntityTypes.SECOND_ROUNDER_SKELETON_SERVANT.get(),
                SecondRounderSkeletonServant.createAttributes().build());
        event.put(ModEntityTypes.SECOND_ROUNDER_SPIDER_SERVANT.get(),
                SecondRounderSpiderServant.createAttributes().build());
        event.put(ModEntityTypes.ENDER_STOWAWAY_SERVANT.get(),
                EnderStowawayServant.createAttributes().build());
        event.put(ModEntityTypes.SERPON_COLONIAL_SOLDIER.get(),
                SerponColonialSoldier.createAttributes().build());
        event.put(ModEntityTypes.SERPON_RIOTER.get(),
                SerponRioter.createAttributes().build());
        event.put(ModEntityTypes.SERPON_PIONEER.get(),
                SerponPioneer.createAttributes().build());
        event.put(ModEntityTypes.SERPON_FIRE_APOSTLE.get(),
                SerponFireApostle.createAttributes().build());
        event.put(ModEntityTypes.SERPON_INTRUDER.get(),
                SerponIntruder.createAttributes().build());
        event.put(ModEntityTypes.SERPON_FLAME_WIZARD.get(),
                SerponFlameWizard.createAttributes().build());
        event.put(ModEntityTypes.WAR_PYTHON_SERVANT.get(),
                WarPythonServant.createAttributes().build());
        event.put(ModEntityTypes.WAR_PYTHON_PART.get(),
                WarPythonPartEntity.createAttributes().build());
        event.put(ModEntityTypes.ROYAL_WAR_PYTHON_SERVANT.get(),
                RoyalWarPythonServant.createAttributes().build());
        event.put(ModEntityTypes.ROYAL_WAR_PYTHON_PART.get(),
                WarPythonPartEntity.createAttributes().build());
        event.put(ModEntityTypes.SERPON_COMBAT_EXECUTER.get(),
                SerponCombatExecuter.createAttributes().build());
        event.put(ModEntityTypes.SECOND_ROUNDER_ANIMATED_ARMOR_SERVANT.get(),
                SecondRounderAnimatedArmorServant.createAttributes().build());
    }
}
