package cn.blockforge.generated.goetysupreme.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractSoulReaper;
import cn.blockforge.generated.goetysupreme.entity.AbstractWildMage;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.entity.DoomFiveHeadShark;
import cn.blockforge.generated.goetysupreme.entity.EndermanServant;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GoetySkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.entity.IceGolemServant;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompatManager;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolem;
import cn.blockforge.generated.goetysupreme.entity.NamelessWither;
import cn.blockforge.generated.goetysupreme.event.ModEntityEvents;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompatManager;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;
import cn.blockforge.generated.goetysupreme.entity.PrisonGolem;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritEntity;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritServant;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.SoulCoffin;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEntityEvents {
    private ModEntityEvents() {
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.SNOW_GOLEM_SERVANT.get(), SnowGolemServant.registerAttributes().build());
        event.put(ModEntityTypes.IRON_GOLEM_SERVANT.get(), IronGolemServant.registerAttributes().build());
        event.put(ModEntityTypes.SKELETON_HORSE_SERVANT.get(), SkeletonHorseServant.createAttributes().build());
        event.put(ModEntityTypes.WITHER_SKELETON_HORSE_SERVANT.get(),
                WitherSkeletonHorseServant.createAttributes().build());
        event.put(ModEntityTypes.ENDERMAN_SERVANT.get(), EndermanServant.registerAttributes().build());
        event.put(ModEntityTypes.GOETY_ZOMBIE_SERVANT.get(), GoetyZombieServant.createServantAttributes().build());
        event.put(ModEntityTypes.GOETY_SKELETON_SERVANT.get(), GoetySkeletonServant.createServantAttributes().build());
        event.put(ModEntityTypes.PLAYER_MOB_SERVANT.get(), PlayerMobServant.createServantAttributes().build());
        event.put(ModEntityTypes.SAKURA_SPIRIT.get(), SakuraSpiritEntity.createAttributes().build());
        event.put(ModEntityTypes.SAKURA_SPIRIT_SERVANT.get(), SakuraSpiritServant.createAttributes().build());
        event.put(ModEntityTypes.WITHER_SERVANT.get(), WitherServant.createServantAttributes().build());
        event.put(ModEntityTypes.NAMELESS_WITHER.get(), NamelessWither.createServantAttributes().build());
        event.put(ModEntityTypes.FURNACE_GOLEM.get(), FurnaceGolem.registerAttributes().build());
        event.put(ModEntityTypes.MAGMA_GOLEM.get(), MagmaGolem.registerAttributes().build());
        event.put(ModEntityTypes.PRISON_GOLEM.get(), PrisonGolem.registerAttributes().build());
        event.put(ModEntityTypes.WITHER_WARLORD.get(), AbstractWitherWarlord.createAttributes().build());
        event.put(ModEntityTypes.WITHER_WARLORD_SERVANT.get(),
                AbstractWitherWarlord.createAttributes().build());
        event.put(ModEntityTypes.ICE_GOLEM_SERVANT.get(), IceGolemServant.registerAttributes().build());
        event.put(ModEntityTypes.PRISMARINE_SOLDIER.get(), PrismarineSoldier.createAttributes().build());
        event.put(ModEntityTypes.WILD_MAGE.get(), AbstractWildMage.createAttributes().build());
        event.put(ModEntityTypes.WILD_MAGE_SERVANT.get(), AbstractWildMage.createAttributes().build());
        event.put(ModEntityTypes.REAPER_NECROMANCER.get(),
                AbstractReaperNecromancer.createAttributes().build());
        event.put(ModEntityTypes.REAPER_NECROMANCER_SERVANT.get(),
                AbstractReaperNecromancer.createAttributes().build());
        event.put(ModEntityTypes.SOUL_REAPER.get(), AbstractSoulReaper.createAttributes().build());
        event.put(ModEntityTypes.SOUL_REAPER_SERVANT.get(),
                AbstractSoulReaper.createAttributes().build());
        event.put(ModEntityTypes.SOUL_COFFIN.get(), SoulCoffin.createAttributes().build());
        event.put(ModEntityTypes.GREEN_SMITH.get(), AbstractGreenSmith.createAttributes().build());
        event.put(ModEntityTypes.GREEN_SMITH_SERVANT.get(),
                AbstractGreenSmith.createAttributes().build());
        event.put(ModEntityTypes.IFRIT.get(), Ifrit.createAttributes().build());
        event.put(ModEntityTypes.IFRIT_CLONE.get(), Ifrit.createAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH, 80.0D)
                .build());
        event.put(ModEntityTypes.SCORCHBACK.get(), Scorchback.createAttributes().build());
        event.put(ModEntityTypes.CRYPT_NECROMANCER_SERVANT.get(),
                CryptNecromancerServant.createAttributes().build());
        event.put(ModEntityTypes.DOOM_FIVE_HEAD_SHARK.get(),
                DoomFiveHeadShark.createAttributes().build());
        if (JerotesVillageCompat.isJerotesVillageLoaded()) {
            JerotesVillageCompatManager.setCustomAttributes(event);
        }
        if (MutantMonstersCompat.isLoaded()) {
            MutantMonstersCompatManager.setCustomAttributes(event);
        }
    }
}
