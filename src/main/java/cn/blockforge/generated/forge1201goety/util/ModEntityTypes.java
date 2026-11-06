package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.block.AbominableSnowGolemSnowBlock;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.entity.AncientSecondRounderArrowEntity;
import cn.blockforge.generated.goetysupreme.entity.BitterColdWindServant;
import cn.blockforge.generated.goetysupreme.entity.BoneScoutArrow;
import cn.blockforge.generated.goetysupreme.entity.BulgeBulletServant;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.entity.CorrosiveIceSpikeServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverBreathAreaServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverBreathServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.entity.CorrosiveSnowCloudServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CryptIceStorm;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.entity.DoomFiveHeadShark;
import cn.blockforge.generated.goetysupreme.entity.DroughtBlazeServant;
import cn.blockforge.generated.goetysupreme.entity.DroughtFireballServantEntity;
import cn.blockforge.generated.goetysupreme.entity.DroughtWildfireServant;
import cn.blockforge.generated.goetysupreme.entity.EnderHandHeldBlockEntity;
import cn.blockforge.generated.goetysupreme.entity.EndermanServant;
import cn.blockforge.generated.goetysupreme.entity.EndersoulCloneServant;
import cn.blockforge.generated.goetysupreme.entity.EndersoulFragmentServant;
import cn.blockforge.generated.goetysupreme.entity.EnderStowawayServant;
import cn.blockforge.generated.goetysupreme.entity.EnhancedWitherSkull;
import cn.blockforge.generated.goetysupreme.entity.FlamoarServant;
import cn.blockforge.generated.goetysupreme.entity.FlungBlockEntity;
import cn.blockforge.generated.goetysupreme.entity.FlyingFireballServant;
import cn.blockforge.generated.goetysupreme.entity.FrostYetiServant;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolem;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBombEntity;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBreathAreaServantEntity;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBreathEntity;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurWhirlpoolEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GoetySkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.entity.GreenBoltEntity;
import cn.blockforge.generated.goetysupreme.entity.GreenFireEntity;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;
import cn.blockforge.generated.goetysupreme.entity.NamelessLaserBeam;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithRenderer;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithServant;
import cn.blockforge.generated.goetysupreme.entity.HarmfulSnowball;
import cn.blockforge.generated.goetysupreme.entity.HeavyArmoredSedimentGuardServant;
import cn.blockforge.generated.goetysupreme.entity.IceGolemServant;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.entity.IfritClone;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;
import cn.blockforge.generated.goetysupreme.entity.IronTeethServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompatManager;
import cn.blockforge.generated.goetysupreme.entity.LightningWormChainLightningEntity;
import cn.blockforge.generated.goetysupreme.entity.LightningWormPartEntity;
import cn.blockforge.generated.goetysupreme.entity.LightningWormServant;
import cn.blockforge.generated.goetysupreme.entity.LightningWormThunderstormEntity;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolem;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolemFireball;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.block.MutantEndermanServantThrownBlock;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompatManager;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServantArrow;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.entity.PrimordierServant;
import cn.blockforge.generated.goetysupreme.entity.PrismarineArrowEntity;
import cn.blockforge.generated.goetysupreme.entity.PrismarineBladeSlashEntity;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;
import cn.blockforge.generated.goetysupreme.entity.PrisonGolem;
import cn.blockforge.generated.goetysupreme.renderer.PrisonGolemRenderer;
import cn.blockforge.generated.goetysupreme.entity.ReaperBladeEntity;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancerServant;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;
import cn.blockforge.generated.goetysupreme.entity.RoyalWarPythonServant;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritEntity;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritServant;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritSlashEntity;
import cn.blockforge.generated.goetysupreme.entity.ScaledSummonCircle;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.entity.ScorchbackQuill;
import cn.blockforge.generated.goetysupreme.entity.ScorchbackSpike;
import cn.blockforge.generated.goetysupreme.entity.SeaWaveServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderAnimatedArmorServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSkeletonServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSpiderServant;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderZombieServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardArrow;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentMissileEntity;
import cn.blockforge.generated.goetysupreme.entity.SedimentSoulStabilizerServant;
import cn.blockforge.generated.goetysupreme.entity.SerpenperthMeteorEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponColonialSoldier;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponDartEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;
import cn.blockforge.generated.goetysupreme.entity.SerponFireballEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesServantEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlameWizard;
import cn.blockforge.generated.goetysupreme.entity.SerponIntruder;
import cn.blockforge.generated.goetysupreme.entity.SerponJavelinEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponPioneer;
import cn.blockforge.generated.goetysupreme.entity.SerponRioter;
import cn.blockforge.generated.goetysupreme.entity.SerponSiroccoEntity;
import cn.blockforge.generated.goetysupreme.entity.ServantThrownTrident;
import cn.blockforge.generated.goetysupreme.entity.ServantWitherSkull;
import cn.blockforge.generated.goetysupreme.renderer.ServantWitherSkullRenderer;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.SoulCoffin;
import cn.blockforge.generated.goetysupreme.entity.SoulReaper;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperRenderer;
import cn.blockforge.generated.goetysupreme.entity.SoulReaperServant;
import cn.blockforge.generated.goetysupreme.entity.TrackingIceSpike;
import cn.blockforge.generated.goetysupreme.entity.WarlordBladeTrail;
import cn.blockforge.generated.goetysupreme.renderer.WarlordBladeTrailRenderer;
import cn.blockforge.generated.goetysupreme.entity.WarlordHellfireSlash;
import cn.blockforge.generated.goetysupreme.entity.WarPythonPartEntity;
import cn.blockforge.generated.goetysupreme.entity.WarPythonServant;
import cn.blockforge.generated.goetysupreme.entity.WildMage;
import cn.blockforge.generated.goetysupreme.entity.WildMageServant;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.entity.NamelessWither;
import cn.blockforge.generated.goetysupreme.entity.NamelessBeam;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordServant;
import cn.blockforge.generated.goetysupreme.mixin.GoetyRedstoneMonstrosityRagingMixin;

public final class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GeneratedMod.MOD_ID);

    public static final DeferredRegister<EntityType<?>> JEROTES_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GeneratedMod.MOD_ID);

    public static final DeferredRegister<EntityType<?>> MUTANT_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GeneratedMod.MOD_ID);

    public static final RegistryObject<EntityType<MutantZombieServant>> MUTANT_ZOMBIE_SERVANT =
            MUTANT_ENTITY_TYPES.register("mutant_zombie_servant", () -> EntityType.Builder
                    .of(MutantZombieServant::new, MobCategory.MISC)
                    .sized(1.8F, 3.2F)
                    .clientTrackingRange(8)
                    .build("mutant_zombie_servant"));

    public static final RegistryObject<EntityType<MutantSnowGolemServant>> MUTANT_SNOW_GOLEM_SERVANT =
            MUTANT_ENTITY_TYPES.register("mutant_snow_golem_servant", () -> EntityType.Builder
                    .of(MutantSnowGolemServant::new, MobCategory.MISC)
                    .sized(1.15F, 2.3F)
                    .clientTrackingRange(8)
                    .build("mutant_snow_golem_servant"));

    public static final RegistryObject<EntityType<SnowGolemServant>> SNOW_GOLEM_SERVANT =
            ENTITY_TYPES.register("snow_golem_servant", () -> EntityType.Builder
                    .of(SnowGolemServant::new, MobCategory.MISC)
                    .sized(0.7F, 1.9F)
                    .clientTrackingRange(8)
                    .build("snow_golem_servant"));

    public static final RegistryObject<EntityType<IronGolemServant>> IRON_GOLEM_SERVANT =
            ENTITY_TYPES.register("iron_golem_servant", () -> EntityType.Builder
                    .of(IronGolemServant::new, MobCategory.MISC)
                    .sized(1.4F, 2.7F)
                    .clientTrackingRange(8)
                    .build("iron_golem_servant"));

    public static final RegistryObject<EntityType<EndermanServant>> ENDERMAN_SERVANT =
            ENTITY_TYPES.register("enderman_servant", () -> EntityType.Builder
                    .of(EndermanServant::new, MobCategory.MISC)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build("enderman_servant"));

    public static final RegistryObject<EntityType<MutantSkeletonServant>> MUTANT_SKELETON_SERVANT =
            MUTANT_ENTITY_TYPES.register("mutant_skeleton_servant", () -> EntityType.Builder
                    .of(MutantSkeletonServant::new, MobCategory.MISC)
                    .sized(1.2F, 3.6F)
                    .clientTrackingRange(8)
                    .build("mutant_skeleton_servant"));

    public static final RegistryObject<EntityType<MutantEndermanServant>> MUTANT_ENDERMAN_SERVANT =
            MUTANT_ENTITY_TYPES.register("mutant_enderman_servant", () -> EntityType.Builder
                    .of(MutantEndermanServant::new, MobCategory.MISC)
                    .sized(1.2F, 4.2F)
                    .clientTrackingRange(8)
                    .build("mutant_enderman_servant"));

    public static final RegistryObject<EntityType<MutantSkeletonServantArrow>> MUTANT_SKELETON_ARROW =
            MUTANT_ENTITY_TYPES.register("mutant_skeleton_arrow", () -> EntityType.Builder
                    .<MutantSkeletonServantArrow>of(MutantSkeletonServantArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(8).updateInterval(1)
                    .build("mutant_skeleton_arrow"));

    public static final RegistryObject<EntityType<EndersoulCloneServant>> ENDERSOUL_CLONE_SERVANT =
            MUTANT_ENTITY_TYPES.register("endersoul_clone_servant", () -> EntityType.Builder
                    .of(EndersoulCloneServant::new, MobCategory.MISC)
                    .sized(0.6F, 2.9F).clientTrackingRange(8).updateInterval(2).noSave()
                    .build("endersoul_clone_servant"));

    public static final RegistryObject<EntityType<EndersoulFragmentServant>> ENDERSOUL_FRAGMENT_SERVANT =
            MUTANT_ENTITY_TYPES.register("endersoul_fragment_servant", () -> EntityType.Builder
                    .of(EndersoulFragmentServant::new, MobCategory.MISC)
                    .sized(0.75F, 0.75F).clientTrackingRange(8).updateInterval(2)
                    .build("endersoul_fragment_servant"));

    public static final RegistryObject<EntityType<MutantEndermanServantThrownBlock>> MUTANT_ENDERMAN_THROWN_BLOCK =
            MUTANT_ENTITY_TYPES.register("mutant_enderman_thrown_block", () -> EntityType.Builder
                    .<MutantEndermanServantThrownBlock>of(MutantEndermanServantThrownBlock::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F).clientTrackingRange(8).updateInterval(2)
                    .build("mutant_enderman_thrown_block"));

    public static final RegistryObject<EntityType<EnderHandHeldBlockEntity>> ENDER_HAND_HELD_BLOCK =
            MUTANT_ENTITY_TYPES.register("ender_hand_held_block", () -> EntityType.Builder
                    .<EnderHandHeldBlockEntity>of(EnderHandHeldBlockEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F).clientTrackingRange(6).updateInterval(Integer.MAX_VALUE)
                    .build("ender_hand_held_block"));

    public static final RegistryObject<EntityType<GoetyZombieServant>> GOETY_ZOMBIE_SERVANT =
            ENTITY_TYPES.register("zombie_servant", () -> EntityType.Builder
                    .of(GoetyZombieServant::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build("zombie_servant"));

    public static final RegistryObject<EntityType<GoetySkeletonServant>> GOETY_SKELETON_SERVANT =
            ENTITY_TYPES.register("skeleton_servant", () -> EntityType.Builder
                    .of(GoetySkeletonServant::new, MobCategory.MISC)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(8)
                    .build("skeleton_servant"));

    public static final RegistryObject<EntityType<BoneScoutArrow>> BONE_SCOUT_ARROW =
            ENTITY_TYPES.register("bone_scout_arrow", () -> EntityType.Builder
                    .<BoneScoutArrow>of(BoneScoutArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("bone_scout_arrow"));

    public static final RegistryObject<EntityType<PlayerMobServant>> PLAYER_MOB_SERVANT =
            ENTITY_TYPES.register("player_mob_servant", () -> EntityType.Builder
                    .of(PlayerMobServant::new, MobCategory.MISC)
                    .sized(0.6F, 1.8F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("player_mob_servant"));

    public static final RegistryObject<EntityType<SakuraSpiritEntity>> SAKURA_SPIRIT =
            ENTITY_TYPES.register("sakura_spirit", () -> EntityType.Builder
                    .of(SakuraSpiritEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .clientTrackingRange(8)
                    .build("sakura_spirit"));

    public static final RegistryObject<EntityType<SakuraSpiritServant>> SAKURA_SPIRIT_SERVANT =
            ENTITY_TYPES.register("sakura_spirit_servant", () -> EntityType.Builder
                    .of(SakuraSpiritServant::new, MobCategory.MISC)
                    .sized(0.6F, 1.8F)
                    .clientTrackingRange(8)
                    .build("sakura_spirit_servant"));

    public static final RegistryObject<EntityType<WitherServant>> WITHER_SERVANT =
            ENTITY_TYPES.register("wither_servant", () -> EntityType.Builder
                    .of(WitherServant::new, MobCategory.MISC)
                    .sized(0.9F, 3.5F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("wither_servant"));

    public static final RegistryObject<EntityType<NamelessWither>> NAMELESS_WITHER =
            ENTITY_TYPES.register("nameless_wither", () -> EntityType.Builder
                    .<NamelessWither>of(NamelessWither::new, MobCategory.MONSTER)
                    .sized(0.9F, 3.5F)
                    .fireImmune()
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("nameless_wither"));

    public static final RegistryObject<EntityType<NamelessBeam>> NAMELESS_BEAM =
            ENTITY_TYPES.register("nameless_beam", () -> EntityType.Builder
                    .<NamelessBeam>of(NamelessBeam::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("nameless_beam"));

    public static final RegistryObject<EntityType<FurnaceGolem>> FURNACE_GOLEM =
            ENTITY_TYPES.register("furnace_golem", () -> EntityType.Builder
                    .<FurnaceGolem>of(FurnaceGolem::new, MobCategory.CREATURE)
                    .sized(1.4F, 2.7F)
                    .clientTrackingRange(10)
                    .build("furnace_golem"));


    public static final RegistryObject<EntityType<MagmaGolem>> MAGMA_GOLEM =
            ENTITY_TYPES.register("magma_golem", () -> EntityType.Builder
                    .<MagmaGolem>of(MagmaGolem::new, MobCategory.CREATURE)
                    .sized(1.4F, 2.7F)
                    .clientTrackingRange(10)
                    .build("magma_golem"));

    public static final RegistryObject<EntityType<MagmaGolemFireball>> MAGMA_FIREBALL =
            ENTITY_TYPES.register("magma_fireball", () -> EntityType.Builder
                    .<MagmaGolemFireball>of(MagmaGolemFireball::new, MobCategory.MISC)
                    .sized(0.75F, 0.75F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("magma_fireball"));

    public static final RegistryObject<EntityType<PrisonGolem>> PRISON_GOLEM =
            ENTITY_TYPES.register("prison_golem", () -> EntityType.Builder
                    .<PrisonGolem>of(PrisonGolem::new, MobCategory.CREATURE)
                    .sized(1.75F, 3.375F)
                    .clientTrackingRange(10)
                    .build("prison_golem"));

    public static final RegistryObject<EntityType<IceGolemServant>> ICE_GOLEM_SERVANT =
            ENTITY_TYPES.register("ice_golem_servant", () -> EntityType.Builder
                    .<IceGolemServant>of(IceGolemServant::new, MobCategory.CREATURE)
                    .sized(1.25F, 2.58F)
                    .clientTrackingRange(10)
                    .build("ice_golem_servant"));

    public static final RegistryObject<EntityType<AbominableSnowGolemServant>> ABOMINABLE_SNOW_GOLEM_SERVANT =
            MUTANT_ENTITY_TYPES.register("abominable_snow_golem_servant", () -> EntityType.Builder
                    .<AbominableSnowGolemServant>of(AbominableSnowGolemServant::new, MobCategory.CREATURE)
                    .sized(2.5F, 5.36F)
                    .clientTrackingRange(10)
                    .build("abominable_snow_golem_servant"));

    public static final RegistryObject<EntityType<AbominableSnowGolemSnowBlock>> ABOMINABLE_SNOW_GOLEM_SNOW_BLOCK =
            MUTANT_ENTITY_TYPES.register("abominable_snow_golem_snow_block", () -> EntityType.Builder
                    .<AbominableSnowGolemSnowBlock>of(AbominableSnowGolemSnowBlock::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F).clientTrackingRange(8).updateInterval(2)
                    .build("abominable_snow_golem_snow_block"));

    public static final RegistryObject<EntityType<HarmfulSnowball>> HARMFUL_SNOWBALL =
            ENTITY_TYPES.register("harmful_snowball", () -> EntityType.Builder
                    .<HarmfulSnowball>of(HarmfulSnowball::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("harmful_snowball"));

    public static final RegistryObject<EntityType<ServantThrownTrident>> SERVANT_THROWN_TRIDENT =
            ENTITY_TYPES.register("servant_thrown_trident", () -> EntityType.Builder
                    .<ServantThrownTrident>of(ServantThrownTrident::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("servant_thrown_trident"));

    public static final RegistryObject<EntityType<ServantWitherSkull>> SERVANT_WITHER_SKULL =
            ENTITY_TYPES.register("servant_wither_skull", () -> EntityType.Builder
                    .<ServantWitherSkull>of(ServantWitherSkull::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("servant_wither_skull"));

    public static final RegistryObject<EntityType<EnhancedWitherSkull>> ENHANCED_WITHER_SKULL =
            ENTITY_TYPES.register("enhanced_wither_skull", () -> EntityType.Builder
                    .<EnhancedWitherSkull>of(EnhancedWitherSkull::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("enhanced_wither_skull"));

    public static final RegistryObject<EntityType<SakuraSpiritSlashEntity>> SAKURA_SPIRIT_SLASH =
            ENTITY_TYPES.register("sakura_spirit_projectile", () -> EntityType.Builder
                    .<SakuraSpiritSlashEntity>of(SakuraSpiritSlashEntity::new, MobCategory.MISC)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(4)
                    .build("sakura_spirit_projectile"));

    public static final RegistryObject<EntityType<PrismarineSoldier>> PRISMARINE_SOLDIER =
            ENTITY_TYPES.register("prismarine_soldier", () -> EntityType.Builder
                    .<PrismarineSoldier>of(PrismarineSoldier::new, MobCategory.MISC)
                    .sized(0.7F, 3.0F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("prismarine_soldier"));

    public static final RegistryObject<EntityType<WildMage>> WILD_MAGE =
            ENTITY_TYPES.register("wild_mage", () -> EntityType.Builder
                    .<WildMage>of(WildMage::new, MobCategory.MONSTER)
                    .sized(0.7F, 2.9F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("wild_mage"));

    public static final RegistryObject<EntityType<WildMageServant>> WILD_MAGE_SERVANT =
            ENTITY_TYPES.register("wild_mage_servant", () -> EntityType.Builder
                    .<WildMageServant>of(WildMageServant::new, MobCategory.MISC)
                    .sized(0.7F, 2.9F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("wild_mage_servant"));

    public static final RegistryObject<EntityType<ReaperNecromancer>> REAPER_NECROMANCER =
            ENTITY_TYPES.register("reaper_necromancer", () -> EntityType.Builder
                    .<ReaperNecromancer>of(ReaperNecromancer::new, MobCategory.MONSTER)
                    .sized(1.2F, 3.0F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("reaper_necromancer"));

    public static final RegistryObject<EntityType<ReaperNecromancerServant>> REAPER_NECROMANCER_SERVANT =
            ENTITY_TYPES.register("reaper_necromancer_servant", () -> EntityType.Builder
                    .<ReaperNecromancerServant>of(ReaperNecromancerServant::new, MobCategory.MISC)
                    .sized(1.2F, 3.0F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("reaper_necromancer_servant"));

    public static final RegistryObject<EntityType<ReaperBladeEntity>> REAPER_BLADE =
            ENTITY_TYPES.register("reaper_blade", () -> EntityType.Builder
                    .<ReaperBladeEntity>of(ReaperBladeEntity::new, MobCategory.MISC)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("reaper_blade"));

    public static final RegistryObject<EntityType<ReaperSpinEntity>> REAPER_SPIN =
            ENTITY_TYPES.register("reaper_spin", () -> EntityType.Builder
                    .<ReaperSpinEntity>of(ReaperSpinEntity::new, MobCategory.MISC)
                    .sized(1.6F, 1.6F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("reaper_spin"));

    public static final RegistryObject<EntityType<SoulReaper>> SOUL_REAPER =
            ENTITY_TYPES.register("soul_reaper", () -> EntityType.Builder
                    .<SoulReaper>of(SoulReaper::new, MobCategory.MONSTER)
                    .sized(1.2F, 3.0F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("soul_reaper"));

    public static final RegistryObject<EntityType<SoulReaperServant>> SOUL_REAPER_SERVANT =
            ENTITY_TYPES.register("soul_reaper_servant", () -> EntityType.Builder
                    .<SoulReaperServant>of(SoulReaperServant::new, MobCategory.MISC)
                    .sized(1.2F, 3.0F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("soul_reaper_servant"));

    public static final RegistryObject<EntityType<SoulCoffin>> SOUL_COFFIN =
            ENTITY_TYPES.register("soul_coffin", () -> EntityType.Builder
                    .<SoulCoffin>of(SoulCoffin::new, MobCategory.MISC)
                    .sized(0.8F, 1.7F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("soul_coffin"));

    public static final RegistryObject<EntityType<ScaledSummonCircle>> SCALED_SUMMON_CIRCLE =
            ENTITY_TYPES.register("scaled_summon_circle", () -> EntityType.Builder
                    .<ScaledSummonCircle>of(ScaledSummonCircle::new, MobCategory.MISC)
                    .sized(1.0F, 0.1F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("scaled_summon_circle"));

    public static final RegistryObject<EntityType<GreenSmith>> GREEN_SMITH =
            ENTITY_TYPES.register("green_smith", () -> EntityType.Builder
                    .<GreenSmith>of(GreenSmith::new, MobCategory.MONSTER)
                    .sized(0.75F, 2.8875F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("green_smith"));

    public static final RegistryObject<EntityType<GreenSmithServant>> GREEN_SMITH_SERVANT =
            ENTITY_TYPES.register("green_smith_servant", () -> EntityType.Builder
                    .<GreenSmithServant>of(GreenSmithServant::new, MobCategory.MISC)
                    .sized(0.75F, 2.8875F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("green_smith_servant"));

    public static final RegistryObject<EntityType<Ifrit>> IFRIT =
            ENTITY_TYPES.register("ifrit", () -> EntityType.Builder
                    .<Ifrit>of(Ifrit::new, MobCategory.MONSTER)
                    .sized(1.7F, 5.4F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("ifrit"));

    public static final RegistryObject<EntityType<IfritClone>> IFRIT_CLONE =
            ENTITY_TYPES.register("ifrit_clone", () -> EntityType.Builder
                    .<IfritClone>of(IfritClone::new, MobCategory.MISC)
                    .sized(1.7F, 5.4F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("ifrit_clone"));

    public static final RegistryObject<EntityType<GreenBoltEntity>> GREEN_BOLT =
            ENTITY_TYPES.register("green_bolt", () -> EntityType.Builder
                    .<GreenBoltEntity>of(GreenBoltEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("green_bolt"));

    public static final RegistryObject<EntityType<GreenFireEntity>> GREEN_FIRE =
            ENTITY_TYPES.register("green_fire", () -> EntityType.Builder
                    .<GreenFireEntity>of(GreenFireEntity::new, MobCategory.MISC)
                    .sized(0.8F, 1.0F)
                    .clientTrackingRange(6)
                    .updateInterval(20)
                    .build("green_fire"));

    public static final RegistryObject<EntityType<GreenSmithLaserBeam>> GREEN_SMITH_LASER =
            ENTITY_TYPES.register("green_smith_laser", () -> EntityType.Builder
                    .<GreenSmithLaserBeam>of(GreenSmithLaserBeam::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .noSave()
                    .build("green_smith_laser"));

    public static final RegistryObject<EntityType<NamelessLaserBeam>> NAMELESS_LASER =
            ENTITY_TYPES.register("nameless_laser", () -> EntityType.Builder
                    .<NamelessLaserBeam>of(NamelessLaserBeam::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .noSave()
                    .build("nameless_laser"));

        public static final RegistryObject<EntityType<PrismarineArrowEntity>> PRISMARINE_ARROW =
            ENTITY_TYPES.register("prismarine_arrow", () -> EntityType.Builder
                    .<PrismarineArrowEntity>of(PrismarineArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("prismarine_arrow"));

    public static final RegistryObject<EntityType<PrismarineBladeSlashEntity>> PRISMARINE_BLADE_SLASH =
            ENTITY_TYPES.register("prismarine_blade_slash", () -> EntityType.Builder
                    .<PrismarineBladeSlashEntity>of(PrismarineBladeSlashEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.6F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .build("prismarine_blade_slash"));

    public static final RegistryObject<EntityType<WarlordHellfireSlash>> WARLORD_HELLFIRE_SLASH =
            ENTITY_TYPES.register("warlord_hellfire_slash", () -> EntityType.Builder
                    .<WarlordHellfireSlash>of(WarlordHellfireSlash::new, MobCategory.MISC)
                    .sized(2.4F, 0.5F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("warlord_hellfire_slash"));

    public static final RegistryObject<EntityType<WarlordBladeTrail>> WARLORD_BLADE_TRAIL =
            ENTITY_TYPES.register("warlord_blade_trail", () -> EntityType.Builder
                    .<WarlordBladeTrail>of(WarlordBladeTrail::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("warlord_blade_trail"));

    public static final RegistryObject<EntityType<SedimentGuardServant>> SEDIMENT_GUARD_SERVANT =
            JEROTES_ENTITY_TYPES.register("sediment_guard_servant", () -> EntityType.Builder
                    .<SedimentGuardServant>of(SedimentGuardServant::new, MobCategory.MISC)
                    .sized(0.65F, 1.95F)
                    .clientTrackingRange(10)
                    .build("sediment_guard_servant"));

    public static final RegistryObject<EntityType<SedimentSoulStabilizerServant>>
            SEDIMENT_SOUL_STABILIZER_SERVANT =
            JEROTES_ENTITY_TYPES.register("sediment_soul_stabilizer_servant", () -> EntityType.Builder
                    .<SedimentSoulStabilizerServant>of(SedimentSoulStabilizerServant::new, MobCategory.MISC)
                    .sized(0.65F, 1.95F)
                    .clientTrackingRange(10)
                    .build("sediment_soul_stabilizer_servant"));

    public static final RegistryObject<EntityType<HeavyArmoredSedimentGuardServant>>
            HEAVY_ARMORED_SEDIMENT_GUARD_SERVANT =
            JEROTES_ENTITY_TYPES.register("heavy_armored_sediment_guard_servant", () -> EntityType.Builder
                    .<HeavyArmoredSedimentGuardServant>of(HeavyArmoredSedimentGuardServant::new, MobCategory.MISC)
                    .sized(0.65F, 1.95F)
                    .clientTrackingRange(10)
                    .build("heavy_armored_sediment_guard_servant"));

    public static final RegistryObject<EntityType<SedimentMissileEntity>> SEDIMENT_MISSILE =
            JEROTES_ENTITY_TYPES.register("sediment_missile", () -> EntityType.Builder
                    .<SedimentMissileEntity>of(SedimentMissileEntity::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .clientTrackingRange(4)
                    .updateInterval(2)
                    .build("sediment_missile"));

    public static final RegistryObject<EntityType<SedimentGuardArrow>> SEDIMENT_GUARD_ARROW =
            JEROTES_ENTITY_TYPES.register("sediment_guard_arrow", () -> EntityType.Builder
                    .<SedimentGuardArrow>of(SedimentGuardArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("sediment_guard_arrow"));

    public static final RegistryObject<EntityType<AncientSecondRounderArrowEntity>>
            ANCIENT_SECOND_ROUNDER_ARROW =
            JEROTES_ENTITY_TYPES.register("ancient_second_rounder_arrow", () -> EntityType.Builder
                    .<AncientSecondRounderArrowEntity>of(AncientSecondRounderArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("ancient_second_rounder_arrow"));

    public static final RegistryObject<EntityType<SedimentLordServant>> SEDIMENT_LORD_SERVANT =
            JEROTES_ENTITY_TYPES.register("sediment_lord_servant", () -> EntityType.Builder
                    .<SedimentLordServant>of(SedimentLordServant::new, MobCategory.MISC)
                    .sized(0.65F, 1.95F)
                    .clientTrackingRange(10)
                    .build("sediment_lord_servant"));

    public static final RegistryObject<EntityType<CorrosiverServant>> CORROSIVER_SERVANT =
            JEROTES_ENTITY_TYPES.register("corrosiver_servant", () -> EntityType.Builder
                    .<CorrosiverServant>of(CorrosiverServant::new, MobCategory.MISC)
                    .sized(2.2F, 3.5F)
                    .clientTrackingRange(32)
                    .updateInterval(2)
                    .build("corrosiver_servant"));

    public static final RegistryObject<EntityType<GemstoneMalignasaurBombEntity>> GEMSTONE_MALIGNASAUR_BOMB =
            JEROTES_ENTITY_TYPES.register("gemstone_malignasaur_bomb", () -> EntityType.Builder
                    .<GemstoneMalignasaurBombEntity>of(GemstoneMalignasaurBombEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(1)
                    .build("gemstone_malignasaur_bomb"));

    public static final RegistryObject<EntityType<GemstoneMalignasaurBreathEntity>> GEMSTONE_MALIGNASAUR_BREATH =
            JEROTES_ENTITY_TYPES.register("gemstone_malignasaur_breath", () -> EntityType.Builder
                    .<GemstoneMalignasaurBreathEntity>of(GemstoneMalignasaurBreathEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("gemstone_malignasaur_breath"));

    public static final RegistryObject<EntityType<GemstoneMalignasaurBreathAreaServantEntity>> GEMSTONE_MALIGNASAUR_BREATH_AREA =
            JEROTES_ENTITY_TYPES.register("gemstone_malignasaur_breath_area", () -> EntityType.Builder
                    .<GemstoneMalignasaurBreathAreaServantEntity>of(GemstoneMalignasaurBreathAreaServantEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .noSummon()
                    .build("gemstone_malignasaur_breath_area"));

    public static final RegistryObject<EntityType<GemstoneMalignasaurWhirlpoolEntity>> GEMSTONE_MALIGNASAUR_WHIRLPOOL =
            JEROTES_ENTITY_TYPES.register("gemstone_malignasaur_whirlpool", () -> EntityType.Builder
                    .<GemstoneMalignasaurWhirlpoolEntity>of(GemstoneMalignasaurWhirlpoolEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("gemstone_malignasaur_whirlpool"));

    public static final RegistryObject<EntityType<LightningWormServant>> LIGHTNING_WORM_SERVANT =
            JEROTES_ENTITY_TYPES.register("lightning_worm_servant", () -> EntityType.Builder
                    .<LightningWormServant>of(LightningWormServant::new, MobCategory.MONSTER)
                    .sized(1.5F, 1.5F)
                    .clientTrackingRange(16)
                    .updateInterval(2)
                    .build("lightning_worm_servant"));

    public static final RegistryObject<EntityType<LightningWormPartEntity>> LIGHTNING_WORM_PART =
            JEROTES_ENTITY_TYPES.register("lightning_worm_part", () -> EntityType.Builder
                    .<LightningWormPartEntity>of(LightningWormPartEntity::new, MobCategory.MONSTER)
                    .sized(1.5F, 1.5F)
                    .clientTrackingRange(16)
                    .updateInterval(2)
                    .build("lightning_worm_part"));

    public static final RegistryObject<EntityType<LightningWormThunderstormEntity>> LIGHTNING_WORM_THUNDERSTORM =
            JEROTES_ENTITY_TYPES.register("lightning_worm_thunderstorm", () -> EntityType.Builder
                    .<LightningWormThunderstormEntity>of(LightningWormThunderstormEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("lightning_worm_thunderstorm"));

    public static final RegistryObject<EntityType<LightningWormChainLightningEntity>> LIGHTNING_WORM_CHAIN_LIGHTNING =
            JEROTES_ENTITY_TYPES.register("lightning_worm_chain_lightning", () -> EntityType.Builder
                    .<LightningWormChainLightningEntity>of(LightningWormChainLightningEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("lightning_worm_chain_lightning"));

    public static final RegistryObject<EntityType<BitterColdWindServant>> BITTER_COLD_WIND_SERVANT =
            JEROTES_ENTITY_TYPES.register("bitter_cold_wind_servant", () -> EntityType.Builder
                    .<BitterColdWindServant>of(BitterColdWindServant::new, MobCategory.CREATURE)
                    .sized(1.8F, 2.1F)
                    .clientTrackingRange(32)
                    .updateInterval(2)
                    .build("bitter_cold_wind_servant"));

    public static final RegistryObject<EntityType<FlamoarServant>> FLAMOAR_SERVANT =
            JEROTES_ENTITY_TYPES.register("flamoar_servant", () -> EntityType.Builder
                    .<FlamoarServant>of(FlamoarServant::new, MobCategory.MISC)
                    .sized(1.5F, 3.25F)
                    .fireImmune()
                    .clientTrackingRange(32)
                    .updateInterval(2)
                    .build("flamoar_servant"));

    public static final RegistryObject<EntityType<CorrosiverBreathServantEntity>> CORROSIVER_BREATH_SERVANT =
            JEROTES_ENTITY_TYPES.register("corrosiver_breath_servant", () -> EntityType.Builder
                    .<CorrosiverBreathServantEntity>of(CorrosiverBreathServantEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("corrosiver_breath_servant"));

    public static final RegistryObject<EntityType<CorrosiverBreathAreaServantEntity>> CORROSIVER_BREATH_AREA_SERVANT =
            JEROTES_ENTITY_TYPES.register("corrosiver_breath_area_servant", () -> EntityType.Builder
                    .<CorrosiverBreathAreaServantEntity>of(CorrosiverBreathAreaServantEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("corrosiver_breath_area_servant"));

    public static final RegistryObject<EntityType<CorrosiveIceSpikeServantEntity>> CORROSIVE_ICE_SPIKE_SERVANT =
            JEROTES_ENTITY_TYPES.register("corrosive_ice_spike_servant", () -> EntityType.Builder
                    .<CorrosiveIceSpikeServantEntity>of(CorrosiveIceSpikeServantEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("corrosive_ice_spike_servant"));

    public static final RegistryObject<EntityType<CorrosiveSnowCloudServantEntity>> CORROSIVE_SNOW_CLOUD_SERVANT =
            JEROTES_ENTITY_TYPES.register("corrosive_snow_cloud_servant", () -> EntityType.Builder
                    .<CorrosiveSnowCloudServantEntity>of(CorrosiveSnowCloudServantEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(8)
                    .updateInterval(4)
                    .build("corrosive_snow_cloud_servant"));

    public static final RegistryObject<EntityType<BulgeServant>> BULGE_SERVANT =
            JEROTES_ENTITY_TYPES.register("bulge_servant", () -> EntityType.Builder
                    .<BulgeServant>of(BulgeServant::new, MobCategory.MISC)
                    .sized(0.6F, 1.5F)
                    .clientTrackingRange(10)
                    .build("bulge_servant"));

    public static final RegistryObject<EntityType<SeaWaveServant>> SEA_WAVE_SERVANT =
            JEROTES_ENTITY_TYPES.register("sea_wave_servant", () -> EntityType.Builder
                    .<SeaWaveServant>of(SeaWaveServant::new, MobCategory.MISC)
                    .sized(0.9F, 2.25F)
                    .clientTrackingRange(10)
                    .build("sea_wave_servant"));

    public static final RegistryObject<EntityType<PrimordierServant>> PRIMORDIER_SERVANT =
            JEROTES_ENTITY_TYPES.register("primordier_servant", () -> EntityType.Builder
                    .<PrimordierServant>of(PrimordierServant::new, MobCategory.MONSTER)
                    .sized(3.5F, 5.5F)
                    .immuneTo(net.minecraft.world.level.block.Blocks.POWDER_SNOW)
                    .clientTrackingRange(32)
                    .build("primordier_servant"));

    public static final RegistryObject<EntityType<FrostYetiServant>> FROST_YETI_SERVANT =
            JEROTES_ENTITY_TYPES.register("frost_yeti_servant", () -> EntityType.Builder
                    .<FrostYetiServant>of(FrostYetiServant::new, MobCategory.MONSTER)
                    .sized(1.5F, 3.375F)
                    .immuneTo(net.minecraft.world.level.block.Blocks.POWDER_SNOW)
                    .clientTrackingRange(32)
                    .build("frost_yeti_servant"));

    public static final RegistryObject<EntityType<BulgeBulletServant>> BULGE_BULLET_SERVANT =
            JEROTES_ENTITY_TYPES.register("bulge_bullet_servant", () -> EntityType.Builder
                    .<BulgeBulletServant>of(BulgeBulletServant::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(2)
                    .build("bulge_bullet_servant"));

    public static final RegistryObject<EntityType<DroughtWildfireServant>> DROUGHT_WILDFIRE_SERVANT =
            JEROTES_ENTITY_TYPES.register("drought_wildfire_servant", () -> EntityType.Builder
                    .<DroughtWildfireServant>of(DroughtWildfireServant::new, MobCategory.MISC)
                    .sized(0.9F, 2.25F)
                    .clientTrackingRange(10)
                    .build("drought_wildfire_servant"));

    public static final RegistryObject<EntityType<DroughtBlazeServant>> DROUGHT_BLAZE_SERVANT =
            JEROTES_ENTITY_TYPES.register("drought_blaze_servant", () -> EntityType.Builder
                    .<DroughtBlazeServant>of(DroughtBlazeServant::new, MobCategory.MISC)
                    .sized(0.6F, 1.8F)
                    .clientTrackingRange(10)
                    .build("drought_blaze_servant"));

    public static final RegistryObject<EntityType<DroughtFireballServantEntity>> DROUGHT_FIREBALL_SERVANT =
            JEROTES_ENTITY_TYPES.register("drought_fireball_servant", () -> EntityType.Builder
                    .<DroughtFireballServantEntity>of(DroughtFireballServantEntity::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .clientTrackingRange(4)
                    .updateInterval(2)
                    .build("drought_fireball_servant"));

    public static final RegistryObject<EntityType<SecondRounderZombieServant>> SECOND_ROUNDER_ZOMBIE_SERVANT =
            JEROTES_ENTITY_TYPES.register("second_rounder_zombie_servant", () -> EntityType.Builder
                    .<SecondRounderZombieServant>of(SecondRounderZombieServant::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .build("second_rounder_zombie_servant"));

    public static final RegistryObject<EntityType<SecondRounderSkeletonServant>> SECOND_ROUNDER_SKELETON_SERVANT =
            JEROTES_ENTITY_TYPES.register("second_rounder_skeleton_servant", () -> EntityType.Builder
                    .<SecondRounderSkeletonServant>of(SecondRounderSkeletonServant::new, MobCategory.MISC)
                    .sized(0.6F, 1.99F)
                    .clientTrackingRange(10)
                    .build("second_rounder_skeleton_servant"));

    public static final RegistryObject<EntityType<SecondRounderSpiderServant>> SECOND_ROUNDER_SPIDER_SERVANT =
            JEROTES_ENTITY_TYPES.register("second_rounder_spider_servant", () -> EntityType.Builder
                    .<SecondRounderSpiderServant>of(SecondRounderSpiderServant::new, MobCategory.MISC)
                    .sized(1.4F, 0.9F)
                    .clientTrackingRange(10)
                    .build("second_rounder_spider_servant"));

    public static final RegistryObject<EntityType<EnderStowawayServant>> ENDER_STOWAWAY_SERVANT =
            JEROTES_ENTITY_TYPES.register("ender_stowaway_servant", () -> EntityType.Builder
                    .<EnderStowawayServant>of(EnderStowawayServant::new, MobCategory.MISC)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(10)
                    .build("ender_stowaway_servant"));

    public static final RegistryObject<EntityType<SerponColonialSoldier>> SERPON_COLONIAL_SOLDIER =
            JEROTES_ENTITY_TYPES.register("serpon_colonial_soldier", () -> EntityType.Builder
                    .<SerponColonialSoldier>of(SerponColonialSoldier::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("serpon_colonial_soldier"));

    public static final RegistryObject<EntityType<SerponJavelinEntity>> SERPON_JAVELIN =
            JEROTES_ENTITY_TYPES.register("serpon_javelin", () -> EntityType.Builder
                    .<SerponJavelinEntity>of(SerponJavelinEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("serpon_javelin"));

    public static final RegistryObject<EntityType<SerponDartEntity>> SERPON_DART =
            JEROTES_ENTITY_TYPES.register("serpon_dart", () -> EntityType.Builder
                    .<SerponDartEntity>of(SerponDartEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("serpon_dart"));

    public static final RegistryObject<EntityType<SerponFlamesServantEntity>> SERPON_FLAMES_SERVANT =
            JEROTES_ENTITY_TYPES.register("serpon_flames_servant", () -> EntityType.Builder
                    .<SerponFlamesServantEntity>of(SerponFlamesServantEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .fireImmune()
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build("serpon_flames_servant"));

    public static final RegistryObject<EntityType<SerponRioter>> SERPON_RIOTER =
            JEROTES_ENTITY_TYPES.register("serpon_rioter", () -> EntityType.Builder
                    .<SerponRioter>of(SerponRioter::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("serpon_rioter"));

    public static final RegistryObject<EntityType<SerponPioneer>> SERPON_PIONEER =
            JEROTES_ENTITY_TYPES.register("serpon_pioneer", () -> EntityType.Builder
                    .<SerponPioneer>of(SerponPioneer::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("serpon_pioneer"));

    public static final RegistryObject<EntityType<SerponFireApostle>> SERPON_FIRE_APOSTLE =
            JEROTES_ENTITY_TYPES.register("serpon_fire_apostle", () -> EntityType.Builder
                    .<SerponFireApostle>of(SerponFireApostle::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("serpon_fire_apostle"));

    public static final RegistryObject<EntityType<SerponIntruder>> SERPON_INTRUDER =
            JEROTES_ENTITY_TYPES.register("serpon_intruder", () -> EntityType.Builder
                    .<SerponIntruder>of(SerponIntruder::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("serpon_intruder"));

    public static final RegistryObject<EntityType<SerponFlameWizard>> SERPON_FLAME_WIZARD =
            JEROTES_ENTITY_TYPES.register("serpon_flame_wizard", () -> EntityType.Builder
                    .<SerponFlameWizard>of(SerponFlameWizard::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("serpon_flame_wizard"));

    public static final RegistryObject<EntityType<SerponFlamesEntity>> SERPON_FLAMES =
            JEROTES_ENTITY_TYPES.register("serpon_flames", () -> EntityType.Builder
                    .<SerponFlamesEntity>of(SerponFlamesEntity::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("serpon_flames"));

    public static final RegistryObject<EntityType<SerponFireballEntity>> SERPON_FIREBALL =
            JEROTES_ENTITY_TYPES.register("serpon_fireball", () -> EntityType.Builder
                    .<SerponFireballEntity>of(SerponFireballEntity::new, MobCategory.MISC)
                    .sized(0.3125F, 0.3125F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("serpon_fireball"));

    public static final RegistryObject<EntityType<SerponCombatExecuter>> SERPON_COMBAT_EXECUTER =
            JEROTES_ENTITY_TYPES.register("serpon_combat_executer", () -> EntityType.Builder
                    .<SerponCombatExecuter>of(SerponCombatExecuter::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("serpon_combat_executer"));

    public static final RegistryObject<EntityType<SerponSiroccoEntity>> SERPON_SIROCCO =
            JEROTES_ENTITY_TYPES.register("serpon_sirocco", () -> EntityType.Builder
                    .<SerponSiroccoEntity>of(SerponSiroccoEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("serpon_sirocco"));

    public static final RegistryObject<EntityType<SerpenperthMeteorEntity>> SERPENPERTH_METEOR =
            JEROTES_ENTITY_TYPES.register("serpenperth_meteor", () -> EntityType.Builder
                    .<SerpenperthMeteorEntity>of(SerpenperthMeteorEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build("serpenperth_meteor"));

    public static final RegistryObject<EntityType<FlungBlockEntity>> FLUNG_BLOCK =
            ENTITY_TYPES.register("flung_block", () -> EntityType.Builder
                    .<FlungBlockEntity>of(FlungBlockEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("flung_block"));

    public static final RegistryObject<EntityType<WarPythonServant>> WAR_PYTHON_SERVANT =
            JEROTES_ENTITY_TYPES.register("war_python", () -> EntityType.Builder
                    .<WarPythonServant>of(WarPythonServant::new, MobCategory.MISC)
                    .sized(0.6F, 0.6F)
                    .fireImmune()
                    .clientTrackingRange(16)
                    .build("war_python"));

    public static final RegistryObject<EntityType<WarPythonPartEntity>> WAR_PYTHON_PART =
            JEROTES_ENTITY_TYPES.register("war_python_part", () -> EntityType.Builder
                    .<WarPythonPartEntity>of(WarPythonPartEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(16)
                    .build("war_python_part"));

    public static final RegistryObject<EntityType<RoyalWarPythonServant>> ROYAL_WAR_PYTHON_SERVANT =
            JEROTES_ENTITY_TYPES.register("royal_war_python", () -> EntityType.Builder
                    .<RoyalWarPythonServant>of(RoyalWarPythonServant::new, MobCategory.MISC)
                    .sized(0.85F, 0.85F)
                    .fireImmune()
                    .clientTrackingRange(16)
                    .build("royal_war_python"));

    public static final RegistryObject<EntityType<WarPythonPartEntity>> ROYAL_WAR_PYTHON_PART =
            JEROTES_ENTITY_TYPES.register("royal_war_python_part", () -> EntityType.Builder
                    .<WarPythonPartEntity>of(WarPythonPartEntity::new, MobCategory.MISC)
                    .sized(0.7F, 0.7F)
                    .clientTrackingRange(16)
                    .build("royal_war_python_part"));

    public static final RegistryObject<EntityType<SecondRounderAnimatedArmorServant>> SECOND_ROUNDER_ANIMATED_ARMOR_SERVANT =
            JEROTES_ENTITY_TYPES.register("second_rounder_animated_armor_servant", () -> EntityType.Builder
                    .<SecondRounderAnimatedArmorServant>of(SecondRounderAnimatedArmorServant::new, MobCategory.MISC)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .build("second_rounder_animated_armor_servant"));

    public static final RegistryObject<EntityType<FlyingFireballServant>> FLYING_FIREBALL_SERVANT =
            JEROTES_ENTITY_TYPES.register("flying_fireball_servant", () -> EntityType.Builder
                    .<FlyingFireballServant>of(FlyingFireballServant::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.5F)
                    .fireImmune()
                    .clientTrackingRange(16)
                    .build("flying_fireball_servant"));

    public static final RegistryObject<EntityType<IronTeethServant>> IRON_TEETH_SERVANT =
            JEROTES_ENTITY_TYPES.register("iron_teeth_servant", () -> EntityType.Builder
                    .<IronTeethServant>of(IronTeethServant::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.5F)
                    .immuneTo(net.minecraft.world.level.block.Blocks.POWDER_SNOW)
                    .clientTrackingRange(16)
                    .build("iron_teeth_servant"));

    public static final RegistryObject<EntityType<SkeletonHorseServant>> SKELETON_HORSE_SERVANT =
            ENTITY_TYPES.register("skeleton_horse_servant", () -> EntityType.Builder
                    .<SkeletonHorseServant>of(SkeletonHorseServant::new, MobCategory.CREATURE)
                    .sized(1.3964844F, 1.6F)
                    .clientTrackingRange(10)
                    .build("skeleton_horse_servant"));

    public static final RegistryObject<EntityType<WitherSkeletonHorseServant>> WITHER_SKELETON_HORSE_SERVANT =
            ENTITY_TYPES.register("wither_skeleton_horse_servant", () -> EntityType.Builder
                    .<WitherSkeletonHorseServant>of(WitherSkeletonHorseServant::new, MobCategory.CREATURE)
                    .sized(1.3964844F * WitherSkeletonHorseServant.RENDER_SCALE,
                            1.6F * WitherSkeletonHorseServant.RENDER_SCALE)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("wither_skeleton_horse_servant"));

    public static final RegistryObject<EntityType<WitherWarlord>> WITHER_WARLORD =
            ENTITY_TYPES.register("wither_warlord", () -> EntityType.Builder
                    .<WitherWarlord>of(WitherWarlord::new, MobCategory.MONSTER)
                    .sized(1.0F, 4.0F)
                    .fireImmune()
                    .clientTrackingRange(12)
                    .build("wither_warlord"));

    public static final RegistryObject<EntityType<WitherWarlordServant>> WITHER_WARLORD_SERVANT =
            ENTITY_TYPES.register("wither_warlord_servant", () -> EntityType.Builder
                    .<WitherWarlordServant>of(WitherWarlordServant::new, MobCategory.MISC)
                    .sized(1.0F, 4.0F)
                    .fireImmune()
                    .clientTrackingRange(12)
                    .build("wither_warlord_servant"));

    public static final RegistryObject<EntityType<CryptNecromancerServant>> CRYPT_NECROMANCER_SERVANT =
            ENTITY_TYPES.register("crypt_necromancer_servant", () -> EntityType.Builder
                    .<CryptNecromancerServant>of(CryptNecromancerServant::new, MobCategory.MISC)
                    .sized(0.75F, 2.8875F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("crypt_necromancer_servant"));

    public static final RegistryObject<EntityType<CryptIceStorm>> CRYPT_ICE_STORM =
            ENTITY_TYPES.register("crypt_ice_storm", () -> EntityType.Builder
                    .<CryptIceStorm>of(CryptIceStorm::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("crypt_ice_storm"));

    public static final RegistryObject<EntityType<TrackingIceSpike>> TRACKING_ICE_SPIKE =
            ENTITY_TYPES.register("tracking_ice_spike", () -> EntityType.Builder
                    .<TrackingIceSpike>of(TrackingIceSpike::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .build("tracking_ice_spike"));

    public static final RegistryObject<EntityType<DoomFiveHeadShark>> DOOM_FIVE_HEAD_SHARK =
            ENTITY_TYPES.register("doom_five_head_shark", () -> EntityType.Builder
                    .<DoomFiveHeadShark>of(DoomFiveHeadShark::new, MobCategory.MONSTER)
                    .sized(1.75F, 1.125F)
                    .clientTrackingRange(10)
                    .build("doom_five_head_shark"));

    public static final RegistryObject<EntityType<Scorchback>> SCORCHBACK =
            ENTITY_TYPES.register("scorchback", () -> EntityType.Builder
                    .<Scorchback>of(Scorchback::new, MobCategory.MISC)
                    .sized(1.85F, 2.45F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
                    .build("scorchback"));

    public static final RegistryObject<EntityType<ScorchbackSpike>> SCORCHBACK_SPIKE =
            ENTITY_TYPES.register("scorchback_spike", () -> EntityType.Builder
                    .<ScorchbackSpike>of(ScorchbackSpike::new, MobCategory.MISC)
                    .sized(0.6F, 0.9F)
                    .clientTrackingRange(10)
                    .updateInterval(4)
                    .build("scorchback_spike"));

    public static final RegistryObject<EntityType<ScorchbackQuill>> SCORCHBACK_QUILL =
            ENTITY_TYPES.register("scorchback_quill", () -> EntityType.Builder
                    .<ScorchbackQuill>of(ScorchbackQuill::new, MobCategory.MISC)
                    .sized(0.35F, 0.35F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
                    .build("scorchback_quill"));

    private ModEntityTypes() {
    }
}
