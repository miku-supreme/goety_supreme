package cn.blockforge.generated.goetysupreme;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import cn.blockforge.generated.goetysupreme.config.BitterColdWindFocusConfig;
import cn.blockforge.generated.goetysupreme.config.HauntedArmorFocusConfig;
import cn.blockforge.generated.goetysupreme.config.MountFocusConfig;
import cn.blockforge.generated.goetysupreme.config.PerformanceConfig;
import cn.blockforge.generated.goetysupreme.config.SoulReaperConfig;
import cn.blockforge.generated.goetysupreme.config.SpinScytheFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeFociConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeServantConfigs;
import cn.blockforge.generated.goetysupreme.debug.PlayerMobServantSkinCommand;
import cn.blockforge.generated.goetysupreme.ai.FellSpearAttackGoal;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.item.AbominableSnowGolemServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.spell.AnimatedIronFocus;
import cn.blockforge.generated.goetysupreme.item.ArcaneScrollItem;
import cn.blockforge.generated.goetysupreme.spell.AsphyxiaFocus;
import cn.blockforge.generated.goetysupreme.util.AstralSetDropin;
import cn.blockforge.generated.goetysupreme.renderer.AstralSkullParticle;
import cn.blockforge.generated.goetysupreme.spell.BitterColdWindFocus;
import cn.blockforge.generated.goetysupreme.item.BlazingEmberItem;
import cn.blockforge.generated.goetysupreme.spell.BlazingSpiritFocus;
import cn.blockforge.generated.goetysupreme.item.BrokenReaperScytheItem;
import cn.blockforge.generated.goetysupreme.item.BulgeServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.block.ConcentratedBoneChunkBlock;
import cn.blockforge.generated.goetysupreme.item.ConcentratedBoneChunkItem;
import cn.blockforge.generated.goetysupreme.spell.CorrosiveIceSpikeFocus;
import cn.blockforge.generated.goetysupreme.spell.CorrosiverBreathFocus;
import cn.blockforge.generated.goetysupreme.item.CorrosiverServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.renderer.CorruptedLaserParticle;
import cn.blockforge.generated.goetysupreme.item.CryptNecromancerServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.CryptSoulJarItem;
import cn.blockforge.generated.goetysupreme.item.DarkSpearItem;
import cn.blockforge.generated.goetysupreme.item.DracognathusRevivalHornItem;
import cn.blockforge.generated.goetysupreme.spell.DroughtBlazeFocus;
import cn.blockforge.generated.goetysupreme.item.DroughtBlazeServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.DroughtMissileFocus;
import cn.blockforge.generated.goetysupreme.item.DroughtWildfireServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.EndermanHandFocus;
import cn.blockforge.generated.goetysupreme.spell.EndermanServantFocus;
import cn.blockforge.generated.goetysupreme.item.EndermanServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.EnderStowawayServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.EntwinedLanternItem;
import cn.blockforge.generated.goetysupreme.effect.FamishedEffect;
import cn.blockforge.generated.goetysupreme.util.FellArrows;
import cn.blockforge.generated.goetysupreme.item.FellAxeItem;
import cn.blockforge.generated.goetysupreme.item.FellBowItem;
import cn.blockforge.generated.goetysupreme.item.FellCrossbowItem;
import cn.blockforge.generated.goetysupreme.item.FellHammerItem;
import cn.blockforge.generated.goetysupreme.event.FellShieldEvents;
import cn.blockforge.generated.goetysupreme.item.FellShieldItem;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.renderer.FieldShieldAuraParticle;
import cn.blockforge.generated.goetysupreme.item.FlamoarServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.FlyingFireballFocus;
import cn.blockforge.generated.goetysupreme.item.FlyingFireballServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.FrostbornFocus;
import cn.blockforge.generated.goetysupreme.spell.FrostSnowFocus;
import cn.blockforge.generated.goetysupreme.item.FrostYetiServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.FurnaceGolemServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.item.GhostNecromancerHornItem;
import cn.blockforge.generated.goetysupreme.spell.GiantHammerFocus;
import cn.blockforge.generated.goetysupreme.item.GoetySkeletonServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.item.GoetyZombieServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.GreenAppleItem;
import cn.blockforge.generated.goetysupreme.spell.GreenFireSkyFocus;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.item.GreenSmithServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.GreenSmithSoulJarItem;
import cn.blockforge.generated.goetysupreme.item.GreenSmithSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.HauntedArmorFocus;
import cn.blockforge.generated.goetysupreme.spell.HauntedArmorSummonSpell;
import cn.blockforge.generated.goetysupreme.item.HeavyArmoredSedimentGuardServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.HellfireSaberItem;
import cn.blockforge.generated.goetysupreme.spell.HotWindFocus;
import cn.blockforge.generated.goetysupreme.item.IceGolemServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.IceShardFocus;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.block.IfritArenaCoreBlock;
import cn.blockforge.generated.goetysupreme.entity.IfritArenaCoreBlockEntity;
import cn.blockforge.generated.goetysupreme.entity.IfritClone;
import cn.blockforge.generated.goetysupreme.item.IfritSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.ImmutableRingItem;
import cn.blockforge.generated.goetysupreme.item.InfernalCredentialItem;
import cn.blockforge.generated.goetysupreme.network.InfernalMaskNetwork;
import cn.blockforge.generated.goetysupreme.item.IronGolemServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.IronGuardFocus;
import cn.blockforge.generated.goetysupreme.spell.IronTeethFocus;
import cn.blockforge.generated.goetysupreme.item.IronTeethServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompatManager;
import cn.blockforge.generated.goetysupreme.item.LightningWormServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.MagmaGolemServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.effect.MalignasaurAsphyxiaMobEffect;
import cn.blockforge.generated.goetysupreme.spell.MalignasaurBombFocus;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;
import cn.blockforge.generated.goetysupreme.spell.MeteorFocus;
import cn.blockforge.generated.goetysupreme.block.MobilePrisonBlock;
import cn.blockforge.generated.goetysupreme.entity.MobilePrisonBlockEntity;
import cn.blockforge.generated.goetysupreme.block.MobilePrisonFrameBlock;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.ModMenus;
import cn.blockforge.generated.goetysupreme.network.ModNetwork;
import cn.blockforge.generated.goetysupreme.spell.MountFocus;
import cn.blockforge.generated.goetysupreme.spell.MountSummonSpell;
import cn.blockforge.generated.goetysupreme.item.MusicDiscItem;
import cn.blockforge.generated.goetysupreme.item.MutantEndermanServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompatManager;
import cn.blockforge.generated.goetysupreme.event.MutantMonstersEvents;
import cn.blockforge.generated.goetysupreme.item.MutantSkeletonServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.MutantSnowGolemServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.MutantZombieServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.OceanBreathFocus;
import cn.blockforge.generated.goetysupreme.block.OrnateSedimentLordCoffinBlock;
import cn.blockforge.generated.goetysupreme.item.OvergrowthCoreItem;
import cn.blockforge.generated.goetysupreme.spell.PikeRainFocus;
import cn.blockforge.generated.goetysupreme.spell.PlayerMobFocus;
import cn.blockforge.generated.goetysupreme.item.PlayerMobServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.PrimordierServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.util.PrismarineArmorMaterial;
import cn.blockforge.generated.goetysupreme.item.PrismarineArrowItem;
import cn.blockforge.generated.goetysupreme.item.PrismarineBowItem;
import cn.blockforge.generated.goetysupreme.block.PrismarineCoreBlock;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;
import cn.blockforge.generated.goetysupreme.item.PrismarineSoldierSpawnEgg;
import cn.blockforge.generated.goetysupreme.entity.PrisonGolem;
import cn.blockforge.generated.goetysupreme.item.PrisonGolemServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.RagingSharkFocus;
import cn.blockforge.generated.goetysupreme.item.RagingStarItem;
import cn.blockforge.generated.goetysupreme.item.ReaperNecromancerServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.ReaperNecromancerSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.ReaperSoulJarItem;
import cn.blockforge.generated.goetysupreme.block.ReinforcedBlastFurnaceBlock;
import cn.blockforge.generated.goetysupreme.entity.ReinforcedBlastFurnaceBlockEntity;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.renderer.RoarWaveParticle;
import cn.blockforge.generated.goetysupreme.item.RoyalWarPythonServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.SakuraSpiritFocus;
import cn.blockforge.generated.goetysupreme.item.SakuraSpiritSpawnEgg;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.block.ScorchbackEggBlock;
import cn.blockforge.generated.goetysupreme.item.ScorchbackSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SeaWaveServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SecondRounderAnimatedArmorServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.SecondRounderFocus;
import cn.blockforge.generated.goetysupreme.item.SecondRounderSkeletonServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SecondRounderSpiderServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.SecondRounderSummonSpell;
import cn.blockforge.generated.goetysupreme.item.SecondRounderZombieServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.SedimentFocus;
import cn.blockforge.generated.goetysupreme.item.SedimentGuardServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SedimentLordServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SedimentLordTotemItem;
import cn.blockforge.generated.goetysupreme.spell.SedimentMissileFocus;
import cn.blockforge.generated.goetysupreme.item.SedimentSoulStabilizerServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.SedimentSummonSpell;
import cn.blockforge.generated.goetysupreme.spell.SerponBlazingFocus;
import cn.blockforge.generated.goetysupreme.item.SerponColonialSoldierSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SerponCombatExecuterSpawnEgg;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;
import cn.blockforge.generated.goetysupreme.item.SerponFireApostleSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SerponFlameWizardSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.SerponFocus;
import cn.blockforge.generated.goetysupreme.item.SerponIntruderSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SerponPioneerSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SerponRioterSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.SerponSummonSpell;
import cn.blockforge.generated.goetysupreme.item.ServantHelmItem;
import cn.blockforge.generated.goetysupreme.item.SkeletonHorseServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.SnowballFocus;
import cn.blockforge.generated.goetysupreme.item.SnowGolemServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.entity.SoulCoffin;
import cn.blockforge.generated.goetysupreme.effect.SoulReaperOmenEffect;
import cn.blockforge.generated.goetysupreme.item.SoulReaperServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.SoulReaperSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.SpinScytheFocus;
import cn.blockforge.generated.goetysupreme.spell.SpinScytheFocusSpell;
import cn.blockforge.generated.goetysupreme.util.SupremeBossMusic;
import cn.blockforge.generated.goetysupreme.util.SupremeCreativeTab;
import cn.blockforge.generated.goetysupreme.util.SupremeRituals;
import cn.blockforge.generated.goetysupreme.item.TemperedRingItem;
import cn.blockforge.generated.goetysupreme.util.TintedTotemEffectOption;
import cn.blockforge.generated.goetysupreme.client.particles.SupremeEffectOption;
import cn.blockforge.generated.goetysupreme.item.UniversalSoulJarItem;
import cn.blockforge.generated.goetysupreme.block.VitalityCarvedPumpkinBlock;
import cn.blockforge.generated.goetysupreme.util.VitalityPumpkinConstructions;
import cn.blockforge.generated.goetysupreme.block.VitalityPumpkinLanternBlock;
import cn.blockforge.generated.goetysupreme.spell.WarPythonFocus;
import cn.blockforge.generated.goetysupreme.item.WarPythonServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.spell.WarPythonSummonSpell;
import cn.blockforge.generated.goetysupreme.spell.WaveBoltFocus;
import cn.blockforge.generated.goetysupreme.spell.WhirlpoolFocus;
import cn.blockforge.generated.goetysupreme.item.WildMageServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.WildMageSoulJarItem;
import cn.blockforge.generated.goetysupreme.item.WildMageSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.WitheringSaberItem;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.item.WitherServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.NamelessWitherSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.WitherSkeletonHorseServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.item.WitherWarlordHelmetItem;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordHelmetModel;
import cn.blockforge.generated.goetysupreme.item.WitherWarlordServantSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.WitherWarlordSpawnEgg;
import cn.blockforge.generated.goetysupreme.item.InfernalMaskItem;
@Mod(GeneratedMod.MOD_ID)
public final class GeneratedMod {
    private static final org.slf4j.Logger LOGGER = com.mojang.logging.LogUtils.getLogger();
    public static final String MOD_ID = "goety_supreme";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final DeferredRegister<Item> JEROTES_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final DeferredRegister<Block> JEROTES_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    public static final DeferredRegister<Item> MUTANT_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    public static final DeferredRegister<net.minecraft.core.particles.ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);

    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> FIELD_SHIELD_AURA =
            registerSimpleParticle("field_shield_aura");

    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> WILD_TOTEM_EFFECT =
            registerSimpleParticle("wild_totem_effect");
    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> GREEN_TOTEM_EFFECT =
            registerSimpleParticle("green_totem_effect");
    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> SOUL_TOTEM_EFFECT =
            registerSimpleParticle("soul_totem_effect");

    public static final RegistryObject<net.minecraft.core.particles.ParticleType<TintedTotemEffectOption>>
            TINTED_TOTEM_EFFECT = PARTICLE_TYPES.register("tinted_totem_effect",
                    () -> new net.minecraft.core.particles.ParticleType<>(false,
                            TintedTotemEffectOption.DESERIALIZER) {
                        @Override
                        public com.mojang.serialization.Codec<TintedTotemEffectOption> codec() {
                            return TintedTotemEffectOption.CODEC;
                        }
                    });

    public static final RegistryObject<net.minecraft.core.particles.ParticleType<SupremeEffectOption>>
            SUPREME_SOUL_EMITTER = PARTICLE_TYPES.register("supreme_soul_emitter", SupremeEffectOption::createType);
    public static final RegistryObject<net.minecraft.core.particles.ParticleType<SupremeEffectOption>>
            SUPREME_SOUL_DUST = PARTICLE_TYPES.register("supreme_soul_dust", SupremeEffectOption::createType);
    public static final RegistryObject<net.minecraft.core.particles.ParticleType<SupremeEffectOption>>
            SUPREME_SOUL_SPARK = PARTICLE_TYPES.register("supreme_soul_spark", SupremeEffectOption::createType);
    public static final RegistryObject<net.minecraft.core.particles.ParticleType<SupremeEffectOption>>
            SUPREME_SOUL_BURST = PARTICLE_TYPES.register("supreme_soul_burst", SupremeEffectOption::createType);
    public static final RegistryObject<net.minecraft.core.particles.ParticleType<SupremeEffectOption>>
            SUPREME_SOUL_STREAK = PARTICLE_TYPES.register("supreme_soul_streak", SupremeEffectOption::createType);
    public static final RegistryObject<net.minecraft.core.particles.ParticleType<SupremeEffectOption>>
            SUPREME_SOUL_LAVA = PARTICLE_TYPES.register("supreme_soul_lava", SupremeEffectOption::createType);

    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> CORRUPTED_LASER_SPARK =
            registerSimpleParticle("corrupted_laser_spark");
    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> CORRUPTED_LASER_GLOW =
            registerSimpleParticle("corrupted_laser_glow");
    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> NAMELESS_LASER_GLOW =
            registerSimpleParticle("nameless_laser_glow");
    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> NAMELESS_GATHER =
            registerSimpleParticle("nameless_gather");
    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> NAMELESS_SOUL =
            registerSimpleParticle("nameless_soul");
    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> NAMELESS_FLAME =
            registerSimpleParticle("nameless_flame");

    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> ROAR_WAVE =
            registerSimpleParticle("roar_wave");

    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> ASTRAL_SKULL_SPARK =
            registerSimpleParticle("astral_skull_spark");

    public static final RegistryObject<net.minecraft.core.particles.SimpleParticleType> SKULL_CLOUD_SMOKE =
            registerSimpleParticle("skull_cloud_smoke");

    @SuppressWarnings("unchecked")
    private static RegistryObject<net.minecraft.core.particles.SimpleParticleType> registerSimpleParticle(String name) {
        return (RegistryObject<net.minecraft.core.particles.SimpleParticleType>) (RegistryObject<?>) PARTICLE_TYPES
                .register(name, () -> new net.minecraft.core.particles.SimpleParticleType(false));
    }

    public static final RegistryObject<SoundEvent> WITHER_POWER = SOUNDS.register("wither_power",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "wither_power")));

    public static final RegistryObject<SoundEvent> WITHER_THEME = SOUNDS.register("wither_theme",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "wither_theme")));

    public static final RegistryObject<SoundEvent> POLARGEIST_THEME = SOUNDS.register("polargeist_theme",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "polargeist_theme")));

    public static final RegistryObject<SoundEvent> CANT_LET_GO_THEME = SOUNDS.register("cant_let_go_theme",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "cant_let_go_theme")));

    public static final RegistryObject<SoundEvent> GREEN_SMITH_THEME = SOUNDS.register("green_smith_theme",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "green_smith_theme")));

    public static final RegistryObject<SoundEvent> WARLORD_THEME = SOUNDS.register("warlord_theme",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "warlord_theme")));

    public static final RegistryObject<SoundEvent> WARLORD_PHASE2_THEME = SOUNDS.register("warlord_phase2_theme",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "warlord_phase2_theme")));

    public static final RegistryObject<SoundEvent> WARLORD_CHARGE_HORN = SOUNDS.register("warlord_charge_horn",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "warlord_charge_horn")));

    public static final RegistryObject<SoundEvent> PRISON_GOLEM_AMBIENT = SOUNDS.register("prison_golem_ambient",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "prison_golem_ambient")));

    public static final RegistryObject<SoundEvent> PRISON_GOLEM_HURT = SOUNDS.register("prison_golem_hurt",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "prison_golem_hurt")));

    public static final RegistryObject<SoundEvent> PRISON_GOLEM_STEP = SOUNDS.register("prison_golem_step",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "prison_golem_step")));

    public static final RegistryObject<SoundEvent> PRISON_GOLEM_DEATH = SOUNDS.register("prison_golem_death",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "prison_golem_death")));

    public static final RegistryObject<SoundEvent> ICE_GOLEM_AMBIENT = SOUNDS.register("ice_golem_ambient",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ice_golem_ambient")));

    public static final RegistryObject<SoundEvent> ICE_GOLEM_HURT = SOUNDS.register("ice_golem_hurt",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ice_golem_hurt")));

    public static final RegistryObject<SoundEvent> ICE_GOLEM_DEATH = SOUNDS.register("ice_golem_death",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ice_golem_death")));

    public static final RegistryObject<SoundEvent> ICE_GOLEM_ATTACK = SOUNDS.register("ice_golem_attack",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ice_golem_attack")));

    public static final RegistryObject<SoundEvent> ICE_GOLEM_STEP = SOUNDS.register("ice_golem_step",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ice_golem_step")));

    public static final RegistryObject<SoundEvent> SCORCHBACK_STEP = SOUNDS.register("scorchback_step",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "scorchback_step")));

    public static final RegistryObject<SoundEvent> SCORCHBACK_ATTACK = SOUNDS.register("scorchback_attack",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "scorchback_attack")));

    public static final RegistryObject<SoundEvent> SCORCHBACK_DEATH = SOUNDS.register("scorchback_death",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "scorchback_death")));

    public static final RegistryObject<SoundEvent> IFRIT_THEME = SOUNDS.register("ifrit_theme",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ifrit_theme")));

    public static final RegistryObject<SoundEvent> MAGMA_GOLEM_AMBIENT = SOUNDS.register("magma_golem_ambient",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "magma_golem_ambient")));

    public static final RegistryObject<SoundEvent> MAGMA_GOLEM_HURT = SOUNDS.register("magma_golem_hurt",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "magma_golem_hurt")));

    public static final RegistryObject<SoundEvent> MAGMA_GOLEM_DEATH = SOUNDS.register("magma_golem_death",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "magma_golem_death")));

    public static final RegistryObject<SoundEvent> MAGMA_GOLEM_ATTACK = SOUNDS.register("magma_golem_attack",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "magma_golem_attack")));

    public static final RegistryObject<SoundEvent> MAGMA_GOLEM_STEP = SOUNDS.register("magma_golem_step",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "magma_golem_step")));

    public static final RegistryObject<SoundEvent> PRISMARINE_SOLDIER_AMBIENT =
            SOUNDS.register("prismarine_soldier_ambient",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(MOD_ID, "prismarine_soldier_ambient")));

    public static final RegistryObject<SoundEvent> PRISMARINE_SOLDIER_HURT =
            SOUNDS.register("prismarine_soldier_hurt",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(MOD_ID, "prismarine_soldier_hurt")));

    public static final RegistryObject<SoundEvent> PRISMARINE_SOLDIER_DEATH =
            SOUNDS.register("prismarine_soldier_death",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(MOD_ID, "prismarine_soldier_death")));

    public static final RegistryObject<SoundEvent> PRISMARINE_SOLDIER_STEP =
            SOUNDS.register("prismarine_soldier_step",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(MOD_ID, "prismarine_soldier_step")));

    public static final RegistryObject<SoundEvent> FURNACE_FIRE_BREATH = SOUNDS.register("furnace_fire_breath",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "furnace_fire_breath")));

    public static final RegistryObject<SoundEvent> REAPER_SNAP = SOUNDS.register("reaper_snap",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "reaper_snap")));

    public static final RegistryObject<SoundEvent> REAPER_AMBIENT_DULL = SOUNDS.register("reaper_ambient_dull",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "reaper_ambient_dull")));
    public static final RegistryObject<SoundEvent> REAPER_HURT_DULL = SOUNDS.register("reaper_hurt_dull",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "reaper_hurt_dull")));
    public static final RegistryObject<SoundEvent> REAPER_DEATH_DULL = SOUNDS.register("reaper_death_dull",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "reaper_death_dull")));
    public static final RegistryObject<SoundEvent> REAPER_SWING_DULL = SOUNDS.register("reaper_swing_dull",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "reaper_swing_dull")));

    public static final RegistryObject<SoundEvent> ENGENDER_HERO = SOUNDS.register("engender_hero",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "engender_hero")));

    public static final RegistryObject<SoundEvent> ENGENDER_ZOMBIE_SPECIAL = SOUNDS.register("engender_zombie_special",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "engender_zombie_special")));

    public static final RegistryObject<SoundEvent> GREEN_SMITH_AMBIENT = greenSmithSound("green_smith_ambient");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_HURT = greenSmithSound("green_smith_hurt");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_DEATH = greenSmithSound("green_smith_death");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_STEP = greenSmithSound("green_smith_step");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_TELEPORT_OUT =
            greenSmithSound("green_smith_teleport_out");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_TELEPORT_IN =
            greenSmithSound("green_smith_teleport_in");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_LAUGH = greenSmithSound("green_smith_laugh");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_CAST = greenSmithSound("green_smith_cast");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_THUNDERBOLT =
            greenSmithSound("green_smith_thunderbolt");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_THUNDER_STRIKE =
            greenSmithSound("green_smith_thunder_strike");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_THUNDER_STRIKE_EPIC =
            greenSmithSound("green_smith_thunder_strike_epic");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_HELL_BOLT =
            greenSmithSound("green_smith_hell_bolt");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_BEAM_START =
            greenSmithSound("green_smith_beam_start");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_BEAM_LOOP =
            greenSmithSound("green_smith_beam_loop");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_SOUL_EAT =
            greenSmithSound("green_smith_soul_eat");

    public static final RegistryObject<SoundEvent> GREEN_SMITH_WIND_BLAST =
            greenSmithSound("green_smith_wind_blast");

    public static final RegistryObject<SoundEvent> IFRIT_BASIC_ATTACK = SOUNDS.register("ifrit_basic_attack",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ifrit_basic_attack")));

    public static final RegistryObject<SoundEvent> IFRIT_DASH = SOUNDS.register("ifrit_dash",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ifrit_dash")));

    public static final RegistryObject<SoundEvent> IFRIT_STEP = SOUNDS.register("ifrit_step",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ifrit_step")));

    public static final RegistryObject<SoundEvent> IFRIT_MAGMA_JUMP = SOUNDS.register("ifrit_magma_jump",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ifrit_magma_jump")));

    public static final RegistryObject<SoundEvent> IFRIT_SWORD_SABER = SOUNDS.register("ifrit_sword_saber",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ifrit_sword_saber")));

    public static final RegistryObject<SoundEvent> IFRIT_POWERFUL_BLOW = SOUNDS.register("ifrit_powerful_blow",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ifrit_powerful_blow")));

    public static final RegistryObject<SoundEvent> FELL_SPEAR_USE = SOUNDS.register("fell_spear_use",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "fell_spear_use")));
    public static final RegistryObject<SoundEvent> FELL_SPEAR_HIT = SOUNDS.register("fell_spear_hit",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "fell_spear_hit")));
    public static final RegistryObject<SoundEvent> FELL_SPEAR_ATTACK = SOUNDS.register("fell_spear_attack",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "fell_spear_attack")));

    public static final RegistryObject<SoundEvent> SOUL_COFFIN_DRAIN = SOUNDS.register("soul_coffin_drain",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "soul_coffin_drain")));

    public static final RegistryObject<SoundEvent> SOUL_COFFIN_TELEPORT = SOUNDS.register("soul_coffin_teleport",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "soul_coffin_teleport")));

    public static final RegistryObject<SoundEvent> SOUL_COFFIN_BREAK = SOUNDS.register("soul_coffin_break",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "soul_coffin_break")));

    private static RegistryObject<SoundEvent> greenSmithSound(String name) {
        return SOUNDS.register(name,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, name)));
    }
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
    public static final RegistryObject<MobEffect> FAMISHED =
            MOB_EFFECTS.register("famished", FamishedEffect::new);

    public static final RegistryObject<MobEffect> SOUL_REAPER_OMEN =
            MOB_EFFECTS.register("soul_reaper_omen", SoulReaperOmenEffect::new);

    public static final RegistryObject<MobEffect> MALIGNASAUR_ASPHYXIA =
            MOB_EFFECTS.register("malignasaur_asphyxia", MalignasaurAsphyxiaMobEffect::new);
    public static final RegistryObject<Block> CONCENTRATED_BONE_CHUNK_BLOCK = BLOCKS.register(
            "concentrated_bone_chunk", ConcentratedBoneChunkBlock::new);
    public static final RegistryObject<Block> VITALITY_CARVED_PUMPKIN_BLOCK = BLOCKS.register(
            "vitality_carved_pumpkin", VitalityCarvedPumpkinBlock::new);
    public static final RegistryObject<Block> VITALITY_PUMPKIN_LANTERN_BLOCK = BLOCKS.register(
            "vitality_pumpkin_lantern", VitalityPumpkinLanternBlock::new);
    public static final RegistryObject<Item> VITALITY_CARVED_PUMPKIN = ITEMS.register(
            "vitality_carved_pumpkin",
            () -> new net.minecraft.world.item.BlockItem(VITALITY_CARVED_PUMPKIN_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> VITALITY_PUMPKIN_LANTERN = ITEMS.register(
            "vitality_pumpkin_lantern",
            () -> new net.minecraft.world.item.BlockItem(VITALITY_PUMPKIN_LANTERN_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENTWINED_LANTERN = ITEMS.register(
            "entwined_lantern", EntwinedLanternItem::new);
    public static final RegistryObject<Item> SERPON_HORN = ITEMS.register(
            "serpon_horn",
            () -> new cn.blockforge.generated.goetysupreme.item.SerponHornItem(
                    new Item.Properties().stacksTo(1).durability(16)));
    public static final RegistryObject<Block> REINFORCED_BLAST_FURNACE_BLOCK = BLOCKS.register(
            "reinforced_blast_furnace", ReinforcedBlastFurnaceBlock::new);
    public static final RegistryObject<Item> REINFORCED_BLAST_FURNACE = ITEMS.register(
            "reinforced_blast_furnace",
            () -> new net.minecraft.world.item.BlockItem(REINFORCED_BLAST_FURNACE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<ReinforcedBlastFurnaceBlockEntity>> REINFORCED_BLAST_FURNACE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("reinforced_blast_furnace",
                    () -> BlockEntityType.Builder.of(ReinforcedBlastFurnaceBlockEntity::new,
                            REINFORCED_BLAST_FURNACE_BLOCK.get()).build(null));
    public static final RegistryObject<Item> MUTANT_ZOMBIE_SERVANT_SPAWN_EGG = MUTANT_ITEMS.register(
            "mutant_zombie_servant_spawn_egg", () -> new MutantZombieServantSpawnEgg());
    public static final RegistryObject<Item> MUTANT_SNOW_GOLEM_SERVANT_SPAWN_EGG = MUTANT_ITEMS.register(
            "mutant_snow_golem_servant_spawn_egg", () -> new MutantSnowGolemServantSpawnEgg());
    public static final RegistryObject<Item> SNOW_GOLEM_SERVANT_SPAWN_EGG = ITEMS.register(
            "snow_golem_servant_spawn_egg", SnowGolemServantSpawnEgg::new);
    public static final RegistryObject<Item> IRON_GOLEM_SERVANT_SPAWN_EGG = ITEMS.register(
            "iron_golem_servant_spawn_egg", IronGolemServantSpawnEgg::new);
    public static final RegistryObject<Item> SKELETON_HORSE_SERVANT_SPAWN_EGG = ITEMS.register(
            "skeleton_horse_servant_spawn_egg", SkeletonHorseServantSpawnEgg::new);
    public static final RegistryObject<Item> WITHER_SKELETON_HORSE_SERVANT_SPAWN_EGG = ITEMS.register(
            "wither_skeleton_horse_servant_spawn_egg", WitherSkeletonHorseServantSpawnEgg::new);
    public static final RegistryObject<Item> ENDERMAN_SERVANT_SPAWN_EGG = ITEMS.register(
            "enderman_servant_spawn_egg", EndermanServantSpawnEgg::new);
    public static final RegistryObject<Item> MUTANT_SKELETON_SERVANT_SPAWN_EGG = MUTANT_ITEMS.register(
            "mutant_skeleton_servant_spawn_egg", () -> new MutantSkeletonServantSpawnEgg());
    public static final RegistryObject<Item> MUTANT_ENDERMAN_SERVANT_SPAWN_EGG = MUTANT_ITEMS.register(
            "mutant_enderman_servant_spawn_egg", () -> new MutantEndermanServantSpawnEgg());
    public static final RegistryObject<Item> CONCENTRATED_BONE_CHUNK = ITEMS.register(
            "concentrated_bone_chunk", ConcentratedBoneChunkItem::new);
    public static final RegistryObject<Item> BERSERK_ESSENCE = ITEMS.register(
            "berserk_essence", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> INFERNAL_MASK = ITEMS.register(
            "infernal_mask", InfernalMaskItem::new);

    public static final RegistryObject<Item> BLAZING_EMBER = ITEMS.register(
            "blazing_ember", BlazingEmberItem::new);
    public static final RegistryObject<Item> GOETY_ZOMBIE_SERVANT_SPAWN_EGG = ITEMS.register(
            "zombie_servant_spawn_egg", GoetyZombieServantSpawnEgg::new);
    public static final RegistryObject<Item> GOETY_SKELETON_SERVANT_SPAWN_EGG = ITEMS.register(
            "skeleton_servant_spawn_egg", GoetySkeletonServantSpawnEgg::new);
    public static final RegistryObject<Item> PLAYER_MOB_SERVANT_SPAWN_EGG = ITEMS.register(
            "player_mob_servant_spawn_egg", PlayerMobServantSpawnEgg::new);
    public static final RegistryObject<Item> WITHER_SERVANT_SPAWN_EGG = ITEMS.register(
            "wither_servant_spawn_egg", WitherServantSpawnEgg::new);
    public static final RegistryObject<Item> NAMELESS_WITHER_SPAWN_EGG = ITEMS.register(
            "nameless_wither_spawn_egg", NamelessWitherSpawnEgg::new);
    public static final RegistryObject<Item> EVIL_STAR = ITEMS.register(
            "evil_star", () -> new Item(new Item.Properties().stacksTo(16).rarity(net.minecraft.world.item.Rarity.RARE)
                    .fireResistant()));
    public static final RegistryObject<Item> RAGING_STAR = ITEMS.register(
            "raging_star", RagingStarItem::new);
    private static final int DISC_COMPARATOR_OUTPUT = 14;

    public static final RegistryObject<Item> MUSIC_DISC_WITHER_THEME = ITEMS.register(
            "music_disc_wither_theme",
            () -> new MusicDiscItem(DISC_COMPARATOR_OUTPUT, WITHER_THEME,
                    musicDiscProperties(), 123 * 20));

    public static final RegistryObject<Item> MUSIC_DISC_POLARGEIST = ITEMS.register(
            "music_disc_polargeist",
            () -> new MusicDiscItem(DISC_COMPARATOR_OUTPUT, POLARGEIST_THEME,
                    musicDiscProperties(), 112 * 20));

    public static final RegistryObject<Item> MUSIC_DISC_CANT_LET_GO = ITEMS.register(
            "music_disc_cant_let_go",
            () -> new MusicDiscItem(DISC_COMPARATOR_OUTPUT, CANT_LET_GO_THEME,
                    musicDiscProperties(), 84 * 20));

    public static final RegistryObject<Item> MUSIC_DISC_GREEN_SMITH_THEME = ITEMS.register(
            "music_disc_green_smith_theme",
            () -> new MusicDiscItem(DISC_COMPARATOR_OUTPUT, GREEN_SMITH_THEME,
                    musicDiscProperties(), 129 * 20));

    public static final RegistryObject<Item> MUSIC_DISC_WARLORD_THEME = ITEMS.register(
            "music_disc_warlord_theme",
            () -> new MusicDiscItem(DISC_COMPARATOR_OUTPUT, WARLORD_THEME,
                    musicDiscProperties(), 141 * 20));
    private static Item.Properties musicDiscProperties() {
        return new Item.Properties().stacksTo(1)
                .rarity(net.minecraft.world.item.Rarity.EPIC)
                .fireResistant();
    }

    public static final RegistryObject<Item> OVERGROWTH_CORE = ITEMS.register(
            "overgrowth_core", OvergrowthCoreItem::new);
    public static final RegistryObject<Item> PLAYER_MOB_FOCUS = ITEMS.register(
            "player_mob_focus", PlayerMobFocus::new);
    public static final RegistryObject<Item> SAKURA_SPIRIT_SPAWN_EGG = ITEMS.register(
            "sakura_spirit_spawn_egg", SakuraSpiritSpawnEgg::new);
    public static final RegistryObject<Item> SAKURA_SPIRIT_FOCUS = ITEMS.register(
            "sakura_guardian_focus", SakuraSpiritFocus::new);
    public static final RegistryObject<Item> HAUNTED_ARMOR_FOCUS = ITEMS.register(
            "haunted_armor_focus", HauntedArmorFocus::new);
    public static final RegistryObject<Item> FROST_SNOW_FOCUS = ITEMS.register(
            "frost_snow_focus", FrostSnowFocus::new);
    public static final RegistryObject<Item> FROSTBORN_FOCUS = ITEMS.register(
            "frostborn_focus", FrostbornFocus::new);
    public static final RegistryObject<Item> RAGING_SHARK_FOCUS = ITEMS.register(
            "raging_shark_focus", RagingSharkFocus::new);
    public static final RegistryObject<Item> SPIN_SCYTHE_FOCUS = ITEMS.register(
            "spin_scythe_focus", SpinScytheFocus::new);
    public static final RegistryObject<Item> DROUGHT_BLAZE_FOCUS = JEROTES_ITEMS.register(
            "drought_blaze_focus", () -> new DroughtBlazeFocus());
    public static final RegistryObject<Item> BITTER_COLD_WIND_FOCUS = JEROTES_ITEMS.register(
            "bitter_cold_wind_focus", () -> new BitterColdWindFocus());
    public static final RegistryObject<Item> WILD_MAGE_SOUL_JAR = ITEMS.register(
            "wild_mage_soul_jar", WildMageSoulJarItem::new);
    public static final RegistryObject<Item> REAPER_SOUL_JAR = ITEMS.register(
            "reaper_soul_jar", ReaperSoulJarItem::new);
    public static final RegistryObject<Item> GREEN_SMITH_SOUL_JAR = ITEMS.register(
            "green_smith_soul_jar", GreenSmithSoulJarItem::new);
    public static final RegistryObject<Item> UNIVERSAL_SOUL_JAR = ITEMS.register(
            "universal_soul_jar", UniversalSoulJarItem::new);
    public static final RegistryObject<Item> CRYPT_SOUL_JAR = ITEMS.register(
            "crypt_soul_jar", CryptSoulJarItem::new);
    public static final RegistryObject<Item> CRYPT_NECROMANCER_SERVANT_SPAWN_EGG = ITEMS.register(
            "crypt_necromancer_servant_spawn_egg", CryptNecromancerServantSpawnEgg::new);
    public static final RegistryObject<Item> BROKEN_REAPER_SCYTHE = ITEMS.register(
            "broken_reaper_scythe", () -> new BrokenReaperScytheItem(new Item.Properties()));
    public static final RegistryObject<Item> GHOST_NECROMANCER_HORN = ITEMS.register(
            "ghost_necromancer_horn", GhostNecromancerHornItem::new);
    public static final RegistryObject<Item> GREEN_APPLE = ITEMS.register(
            "green_apple", GreenAppleItem::new);
    public static final RegistryObject<Item> TEMPERED_RING = ITEMS.register(
            "tempered_ring", TemperedRingItem::new);
    public static final RegistryObject<Item> IMMUTABLE_RING = ITEMS.register(
            "immutable_ring", ImmutableRingItem::new);
    public static final RegistryObject<Item> SEDIMENT_LORD_TOTEM = JEROTES_ITEMS.register(
            "sediment_lord_totem", () -> new SedimentLordTotemItem());
    public static final RegistryObject<Block> ORNATE_SEDIMENT_LORD_COFFIN_BLOCK = JEROTES_BLOCKS.register(
            "ornate_sediment_lord_coffin", () -> new OrnateSedimentLordCoffinBlock());
    public static final RegistryObject<Item> ORNATE_SEDIMENT_LORD_COFFIN = JEROTES_ITEMS.register(
            "ornate_sediment_lord_coffin",
            () -> new net.minecraft.world.item.BlockItem(ORNATE_SEDIMENT_LORD_COFFIN_BLOCK.get(),
                    new Item.Properties()));
    public static final RegistryObject<Item> DROUGHT_WILDFIRE_HELM = JEROTES_ITEMS.register(
            "drought_wildfire_helm", ServantHelmItem::droughtWildfire);
    public static final RegistryObject<Item> SEA_WAVE_HELM = JEROTES_ITEMS.register(
            "sea_wave_helm", ServantHelmItem::seaWave);
    public static final RegistryObject<Item> IRON_GUARD_FOCUS = ITEMS.register(
            "iron_guard_focus", IronGuardFocus::new);
    public static final RegistryObject<Item> SNOWBALL_FOCUS = ITEMS.register(
            "snowball_focus", SnowballFocus::new);
    public static final RegistryObject<Item> ICE_SHARD_FOCUS = MUTANT_ITEMS.register(
            "ice_shard_focus", () -> new IceShardFocus());
    public static final RegistryObject<Item> ENDERMAN_HAND_FOCUS = MUTANT_ITEMS.register(
            "enderman_hand_focus", () -> new EndermanHandFocus());
    public static final RegistryObject<Item> ENDERMAN_SERVANT_FOCUS = ITEMS.register(
            "enderman_servant_focus", EndermanServantFocus::new);
    public static final RegistryObject<Item> GIANT_HAMMER_FOCUS = MUTANT_ITEMS.register(
            "giant_hammer_focus", () -> new GiantHammerFocus());
    public static final RegistryObject<Item> SKELETON_MOUNT_FOCUS = ITEMS.register(
            "skeleton_mount_focus", () -> new MountFocus(MountSummonSpell.Kind.SKELETON));
    public static final RegistryObject<Item> WITHER_SKELETON_MOUNT_FOCUS = ITEMS.register(
            "wither_skeleton_mount_focus", () -> new MountFocus(MountSummonSpell.Kind.WITHER));
    public static final RegistryObject<Item> FURNACE_GOLEM_SPAWN_EGG = ITEMS.register(
            "furnace_golem_spawn_egg", FurnaceGolemServantSpawnEgg::new);
    public static final RegistryObject<Item> MAGMA_GOLEM_SPAWN_EGG = ITEMS.register(
            "magma_golem_spawn_egg", MagmaGolemServantSpawnEgg::new);
    public static final RegistryObject<Item> PRISON_GOLEM_SPAWN_EGG = ITEMS.register(
            "prison_golem_spawn_egg", PrisonGolemServantSpawnEgg::new);
    public static final RegistryObject<Item> WITHER_WARLORD_SPAWN_EGG = ITEMS.register(
            "wither_warlord_spawn_egg", WitherWarlordSpawnEgg::new);
    public static final RegistryObject<Item> WITHER_WARLORD_SERVANT_SPAWN_EGG = ITEMS.register(
            "wither_warlord_servant_spawn_egg", WitherWarlordServantSpawnEgg::new);
    public static final RegistryObject<Item> WITHER_WARLORD_HELMET = ITEMS.register(
            "wither_warlord_helmet", WitherWarlordHelmetItem::new);
    public static final RegistryObject<Item> WITHERING_SABER = ITEMS.register(
            "withering_saber", WitheringSaberItem::new);
    public static final RegistryObject<Item> HELLFIRE_SABER = ITEMS.register(
            "hellfire_saber", HellfireSaberItem::new);
    public static final RegistryObject<Item> FELL_AXE = ITEMS.register(
            "fell_axe", FellAxeItem::new);
    public static final RegistryObject<Item> FELL_BOW = ITEMS.register(
            "fell_bow", () -> new FellBowItem(new Item.Properties().durability(384 * 2)));
    public static final RegistryObject<Item> FELL_CROSSBOW = ITEMS.register(
            "fell_crossbow", () -> new FellCrossbowItem(new Item.Properties().durability(465 * 2)));
    public static final RegistryObject<Item> FELL_SPEAR = ITEMS.register(
            "fell_spear", FellSpearItem::new);
    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register(
            "iron_spear", () -> new FellSpearItem(Tiers.IRON, 3.0F, Tiers.IRON.getUses()));
    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register(
            "diamond_spear", () -> new FellSpearItem(Tiers.DIAMOND, 4.0F, Tiers.DIAMOND.getUses()));
    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register(
            "netherite_spear",
            () -> new FellSpearItem(Tiers.NETHERITE, 5.0F, Tiers.NETHERITE.getUses(), true));
    public static final RegistryObject<Item> DARK_SPEAR = ITEMS.register(
            "dark_spear", DarkSpearItem::new);
    public static final RegistryObject<Item> FELL_HAMMER = ITEMS.register(
            "fell_hammer", FellHammerItem::new);
    public static final RegistryObject<Item> FELL_SHIELD = ITEMS.register(
            "fell_shield", FellShieldItem::new);
    public static final RegistryObject<Item> INFERNAL_CREDENTIAL = ITEMS.register(
            "infernal_credential", InfernalCredentialItem::new);
    public static final RegistryObject<Item> ICE_GOLEM_SERVANT_SPAWN_EGG = ITEMS.register(
            "ice_golem_servant_spawn_egg", IceGolemServantSpawnEgg::new);
    public static final RegistryObject<Item> ABOMINABLE_SNOW_GOLEM_SERVANT_SPAWN_EGG = MUTANT_ITEMS.register(
            "abominable_snow_golem_servant_spawn_egg", () -> new AbominableSnowGolemServantSpawnEgg());
    public static final RegistryObject<Block> MOBILE_PRISON_BLOCK = BLOCKS.register(
            "mobile_prison", MobilePrisonBlock::new);
    public static final RegistryObject<Block> MOBILE_PRISON_FRAME_BLOCK = BLOCKS.register(
            "mobile_prison_frame", MobilePrisonFrameBlock::new);
    public static final RegistryObject<Item> MOBILE_PRISON = ITEMS.register(
            "mobile_prison",
            () -> new net.minecraft.world.item.BlockItem(MOBILE_PRISON_BLOCK.get(),
                    new Item.Properties().rarity(net.minecraft.world.item.Rarity.RARE)));
    public static final RegistryObject<BlockEntityType<MobilePrisonBlockEntity>> MOBILE_PRISON_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("mobile_prison",
                    () -> BlockEntityType.Builder.of(MobilePrisonBlockEntity::new,
                            MOBILE_PRISON_BLOCK.get()).build(null));
    public static final RegistryObject<Block> IFRIT_ARENA_CORE_BLOCK = BLOCKS.register(
            "ifrit_arena_core", IfritArenaCoreBlock::new);
    public static final RegistryObject<BlockEntityType<IfritArenaCoreBlockEntity>> IFRIT_ARENA_CORE_ENTITY =
            BLOCK_ENTITY_TYPES.register("ifrit_arena_core",
                    () -> BlockEntityType.Builder.of(IfritArenaCoreBlockEntity::new,
                            IFRIT_ARENA_CORE_BLOCK.get()).build(null));
    public static final RegistryObject<Block> SCORCHBACK_EGG_BLOCK = BLOCKS.register(
            "scorchback_egg", ScorchbackEggBlock::new);
    public static final RegistryObject<Block> PRISMARINE_CORE_BLOCK = BLOCKS.register(
            "prismarine_core", PrismarineCoreBlock::new);
    public static final RegistryObject<Item> PRISMARINE_CORE = ITEMS.register(
            "prismarine_core",
            () -> new net.minecraft.world.item.BlockItem(PRISMARINE_CORE_BLOCK.get(),
                    new Item.Properties()));
    public static final RegistryObject<Item> PRISMARINE_SWORD = ITEMS.register(
            "prismarine_sword",
            () -> new net.minecraft.world.item.SwordItem(net.minecraft.world.item.Tiers.DIAMOND,
                    3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> PRISMARINE_AXE = ITEMS.register(
            "prismarine_axe",
            () -> new net.minecraft.world.item.AxeItem(net.minecraft.world.item.Tiers.DIAMOND,
                    5.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> PRISMARINE_BOW = ITEMS.register(
            "prismarine_bow", () -> new PrismarineBowItem(new Item.Properties().durability(384)));
    public static final RegistryObject<Item> PRISMARINE_ARROW = ITEMS.register(
            "prismarine_arrow", () -> new PrismarineArrowItem(new Item.Properties()));
    public static final RegistryObject<Item> PRISMARINE_HELMET = ITEMS.register(
            "prismarine_helmet",
            () -> new net.minecraft.world.item.ArmorItem(PrismarineArmorMaterial.PRISMARINE,
                    net.minecraft.world.item.ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> PRISMARINE_CHESTPLATE = ITEMS.register(
            "prismarine_chestplate",
            () -> new net.minecraft.world.item.ArmorItem(PrismarineArmorMaterial.PRISMARINE,
                    net.minecraft.world.item.ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> PRISMARINE_LEGGINGS = ITEMS.register(
            "prismarine_leggings",
            () -> new net.minecraft.world.item.ArmorItem(PrismarineArmorMaterial.PRISMARINE,
                    net.minecraft.world.item.ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> PRISMARINE_BOOTS = ITEMS.register(
            "prismarine_boots",
            () -> new net.minecraft.world.item.ArmorItem(PrismarineArmorMaterial.PRISMARINE,
                    net.minecraft.world.item.ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> PRISMARINE_SOLDIER_SPAWN_EGG = ITEMS.register(
            "prismarine_soldier_spawn_egg", PrismarineSoldierSpawnEgg::new);
    public static final RegistryObject<Item> WILD_MAGE_SPAWN_EGG = ITEMS.register(
            "wild_mage_spawn_egg", WildMageSpawnEgg::new);
    public static final RegistryObject<Item> WILD_MAGE_SERVANT_SPAWN_EGG = ITEMS.register(
            "wild_mage_servant_spawn_egg", WildMageServantSpawnEgg::new);
    public static final RegistryObject<Item> REAPER_NECROMANCER_SPAWN_EGG = ITEMS.register(
            "reaper_necromancer_spawn_egg", ReaperNecromancerSpawnEgg::new);
    public static final RegistryObject<Item> REAPER_NECROMANCER_SERVANT_SPAWN_EGG = ITEMS.register(
            "reaper_necromancer_servant_spawn_egg", ReaperNecromancerServantSpawnEgg::new);
    public static final RegistryObject<Item> COFFIN_SHARD = ITEMS.register(
            "coffin_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_REAPER_SPAWN_EGG = ITEMS.register(
            "soul_reaper_spawn_egg", SoulReaperSpawnEgg::new);
    public static final RegistryObject<Item> SOUL_REAPER_SERVANT_SPAWN_EGG = ITEMS.register(
            "soul_reaper_servant_spawn_egg", SoulReaperServantSpawnEgg::new);
    public static final RegistryObject<Item> GREEN_SMITH_SPAWN_EGG = ITEMS.register(
            "green_smith_spawn_egg", GreenSmithSpawnEgg::new);
    public static final RegistryObject<Item> GREEN_SMITH_SERVANT_SPAWN_EGG = ITEMS.register(
            "green_smith_servant_spawn_egg", GreenSmithServantSpawnEgg::new);
    public static final RegistryObject<Item> IFRIT_SPAWN_EGG = ITEMS.register(
            "ifrit_spawn_egg", IfritSpawnEgg::new);
    public static final RegistryObject<Item> SCORCHBACK_SPAWN_EGG = ITEMS.register(
            "scorchback_spawn_egg", ScorchbackSpawnEgg::new);
    public static final RegistryObject<Item> SCORCHBACK_EGG = ITEMS.register(
            "scorchback_egg", () -> new net.minecraft.world.item.BlockItem(SCORCHBACK_EGG_BLOCK.get(),
                    new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_SCROLL = ITEMS.register(
            "arcane_scroll", ArcaneScrollItem::new);
    public static final RegistryObject<Item> FORBIDDEN_CHEAT_SHEET = ITEMS.register(
            "forbidden_cheat_sheet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SEDIMENT_GUARD_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "sediment_guard_servant_spawn_egg", () -> new SedimentGuardServantSpawnEgg());
    public static final RegistryObject<Item> SEDIMENT_SOUL_STABILIZER_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "sediment_soul_stabilizer_servant_spawn_egg", () -> new SedimentSoulStabilizerServantSpawnEgg());
    public static final RegistryObject<Item> HEAVY_ARMORED_SEDIMENT_GUARD_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "heavy_armored_sediment_guard_servant_spawn_egg",
            () -> new HeavyArmoredSedimentGuardServantSpawnEgg());
    public static final RegistryObject<Item> SEDIMENT_LORD_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "sediment_lord_servant_spawn_egg", () -> new SedimentLordServantSpawnEgg());
    public static final RegistryObject<Item> CORROSIVER_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "corrosiver_servant_spawn_egg", () -> new CorrosiverServantSpawnEgg());
    public static final RegistryObject<Item> LIGHTNING_WORM_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "lightning_worm_servant_spawn_egg", () -> new LightningWormServantSpawnEgg());
    public static final RegistryObject<Item> DRACOGNATHUS_REVIVAL_HORN = JEROTES_ITEMS.register(
            "dracognathus_revival_horn", DracognathusRevivalHornItem::new);
    public static final RegistryObject<Item> FLAMOAR_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "flamoar_servant_spawn_egg", () -> new FlamoarServantSpawnEgg());
    public static final RegistryObject<Item> PRIMORDIER_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "primordier_servant_spawn_egg", () -> new PrimordierServantSpawnEgg());
    public static final RegistryObject<Item> FROST_YETI_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "frost_yeti_servant_spawn_egg", () -> new FrostYetiServantSpawnEgg());
    public static final RegistryObject<Item> FLYING_FIREBALL_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "flying_fireball_servant_spawn_egg", () -> new FlyingFireballServantSpawnEgg());
    public static final RegistryObject<Item> IRON_TEETH_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "iron_teeth_servant_spawn_egg", () -> new IronTeethServantSpawnEgg());
    public static final RegistryObject<Item> BULGE_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "bulge_servant_spawn_egg", () -> new BulgeServantSpawnEgg());
    public static final RegistryObject<Item> SEA_WAVE_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "sea_wave_servant_spawn_egg", () -> new SeaWaveServantSpawnEgg());
    public static final RegistryObject<Item> DROUGHT_WILDFIRE_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "drought_wildfire_servant_spawn_egg", () -> new DroughtWildfireServantSpawnEgg());
    public static final RegistryObject<Item> DROUGHT_BLAZE_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "drought_blaze_servant_spawn_egg", () -> new DroughtBlazeServantSpawnEgg());
    public static final RegistryObject<Item> SECOND_ROUNDER_ZOMBIE_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "second_rounder_zombie_servant_spawn_egg", () -> new SecondRounderZombieServantSpawnEgg());
    public static final RegistryObject<Item> SECOND_ROUNDER_SKELETON_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "second_rounder_skeleton_servant_spawn_egg", () -> new SecondRounderSkeletonServantSpawnEgg());
    public static final RegistryObject<Item> SECOND_ROUNDER_SPIDER_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "second_rounder_spider_servant_spawn_egg", () -> new SecondRounderSpiderServantSpawnEgg());
    public static final RegistryObject<Item> ENDER_STOWAWAY_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "ender_stowaway_servant_spawn_egg", () -> new EnderStowawayServantSpawnEgg());
    public static final RegistryObject<Item> SERPON_COLONIAL_SOLDIER_SPAWN_EGG = JEROTES_ITEMS.register(
            "serpon_colonial_soldier_spawn_egg", () -> new SerponColonialSoldierSpawnEgg());
    public static final RegistryObject<Item> SERPON_RIOTER_SPAWN_EGG = JEROTES_ITEMS.register(
            "serpon_rioter_spawn_egg", () -> new SerponRioterSpawnEgg());
    public static final RegistryObject<Item> SERPON_PIONEER_SPAWN_EGG = JEROTES_ITEMS.register(
            "serpon_pioneer_spawn_egg", () -> new SerponPioneerSpawnEgg());
    public static final RegistryObject<Item> SERPON_FIRE_APOSTLE_SPAWN_EGG = JEROTES_ITEMS.register(
            "serpon_fire_apostle_spawn_egg", () -> new SerponFireApostleSpawnEgg());
    public static final RegistryObject<Item> SERPON_INTRUDER_SPAWN_EGG = JEROTES_ITEMS.register(
            "serpon_intruder_spawn_egg", () -> new SerponIntruderSpawnEgg());
    public static final RegistryObject<Item> SERPON_FLAME_WIZARD_SPAWN_EGG = JEROTES_ITEMS.register(
            "serpon_flame_wizard_spawn_egg", () -> new SerponFlameWizardSpawnEgg());
    public static final RegistryObject<Item> WAR_PYTHON_SPAWN_EGG = JEROTES_ITEMS.register(
            "war_python_spawn_egg", () -> new WarPythonServantSpawnEgg());
    public static final RegistryObject<Item> ROYAL_WAR_PYTHON_SPAWN_EGG = JEROTES_ITEMS.register(
            "royal_war_python_spawn_egg", () -> new RoyalWarPythonServantSpawnEgg());

    public static final RegistryObject<Item> SERPON_COMBAT_EXECUTER_SPAWN_EGG = JEROTES_ITEMS.register(
            "serpon_combat_executer_spawn_egg", () -> new SerponCombatExecuterSpawnEgg());
    public static final RegistryObject<Item> SECOND_ROUNDER_ANIMATED_ARMOR_SERVANT_SPAWN_EGG = JEROTES_ITEMS.register(
            "second_rounder_animated_armor_servant_spawn_egg",
            () -> new SecondRounderAnimatedArmorServantSpawnEgg());
    public static final RegistryObject<Item> SEDIMENT_GUARD_FOCUS = JEROTES_ITEMS.register(
            "sediment_guard_focus", () -> new SedimentFocus(SedimentSummonSpell.Kind.GUARD));
    public static final RegistryObject<Item> SEDIMENT_SOUL_STABILIZER_FOCUS = JEROTES_ITEMS.register(
            "sediment_soul_stabilizer_focus",
            () -> new SedimentFocus(SedimentSummonSpell.Kind.SOUL_STABILIZER));
    public static final RegistryObject<Item> HEAVY_ARMORED_SEDIMENT_GUARD_FOCUS = JEROTES_ITEMS.register(
            "heavy_armored_sediment_guard_focus",
            () -> new SedimentFocus(SedimentSummonSpell.Kind.HEAVY_GUARD));
    public static final RegistryObject<Item> BULGE_FOCUS = JEROTES_ITEMS.register(
            "bulge_focus", () -> new SedimentFocus(SedimentSummonSpell.Kind.BULGE));
    public static final RegistryObject<Item> SECOND_ROUNDER_ROTTING_FOCUS = JEROTES_ITEMS.register(
            "second_rounder_rotting_focus",
            () -> new SecondRounderFocus(SecondRounderSummonSpell.Kind.ROTTING));
    public static final RegistryObject<Item> SECOND_ROUNDER_OSSEOUS_FOCUS = JEROTES_ITEMS.register(
            "second_rounder_osseous_focus",
            () -> new SecondRounderFocus(SecondRounderSummonSpell.Kind.OSSEOUS));
    public static final RegistryObject<Item> WAVE_BOLT_FOCUS = JEROTES_ITEMS.register(
            "wave_bolt_focus", () -> new WaveBoltFocus());
    public static final RegistryObject<Item> DROUGHT_MISSILE_FOCUS = JEROTES_ITEMS.register(
            "drought_missile_focus", () -> new DroughtMissileFocus());
    public static final RegistryObject<Item> SEDIMENT_MISSILE_FOCUS = JEROTES_ITEMS.register(
            "sediment_missile_focus", () -> new SedimentMissileFocus());
    public static final RegistryObject<Item> SERPON_HEAD_FOCUS = JEROTES_ITEMS.register(
            "serpon_head_focus", () -> new SerponFocus(SerponSummonSpell.Kind.HEAD));
    public static final RegistryObject<Item> SERPON_CROWN_FOCUS = JEROTES_ITEMS.register(
            "serpon_crown_focus", () -> new SerponFocus(SerponSummonSpell.Kind.CROWN));
    public static final RegistryObject<Item> SERPON_FLAME_FOCUS = JEROTES_ITEMS.register(
            "serpon_flame_focus", () -> new SerponFocus(SerponSummonSpell.Kind.FLAME));
    public static final RegistryObject<Item> WAR_PYTHON_FOCUS = JEROTES_ITEMS.register(
            "war_python_focus", WarPythonFocus::new);
    public static final RegistryObject<Item> SERPON_BLAZING_FOCUS = JEROTES_ITEMS.register(
            "serpon_blazing_focus", () -> new SerponBlazingFocus());
    public static final RegistryObject<Item> CORROSIVER_BREATH_FOCUS = JEROTES_ITEMS.register(
            "corrosiver_breath_focus", () -> new CorrosiverBreathFocus());
    public static final RegistryObject<Item> ANIMATED_IRON_FOCUS = JEROTES_ITEMS.register(
            "animated_iron_focus", () -> new AnimatedIronFocus());
    public static final RegistryObject<Item> CORROSIVE_ICE_SPIKE_FOCUS = JEROTES_ITEMS.register(
            "corrosive_ice_spike_focus", () -> new CorrosiveIceSpikeFocus());
    public static final RegistryObject<Item> HOT_WIND_FOCUS = JEROTES_ITEMS.register(
            "hot_wind_focus", () -> new HotWindFocus());
    public static final RegistryObject<Item> GREEN_FIRE_SKY_FOCUS = ITEMS.register(
            "green_fire_sky_focus", () -> new GreenFireSkyFocus());
    public static final RegistryObject<Item> METEOR_FOCUS = JEROTES_ITEMS.register(
            "meteor_focus", () -> new MeteorFocus());
    public static final RegistryObject<Item> PIKE_RAIN_FOCUS = JEROTES_ITEMS.register(
            "pike_rain_focus", () -> new PikeRainFocus());
    public static final RegistryObject<Item> OCEAN_BREATH_FOCUS = JEROTES_ITEMS.register(
            "ocean_breath_focus", () -> new OceanBreathFocus());
    public static final RegistryObject<Item> WHIRLPOOL_FOCUS = JEROTES_ITEMS.register(
            "whirlpool_focus", () -> new WhirlpoolFocus());
    public static final RegistryObject<Item> ASPHYXIA_FOCUS = JEROTES_ITEMS.register(
            "asphyxia_focus", () -> new AsphyxiaFocus());
    public static final RegistryObject<Item> MALIGNASAUR_BOMB_FOCUS = JEROTES_ITEMS.register(
            "malignasaur_bomb_focus", () -> new MalignasaurBombFocus());
    public static final RegistryObject<Item> IRON_TEETH_FOCUS = JEROTES_ITEMS.register(
            "iron_teeth_focus", () -> new IronTeethFocus());
    public static final RegistryObject<Item> FLYING_FIREBALL_FOCUS = JEROTES_ITEMS.register(
            "flying_fireball_focus", () -> new FlyingFireballFocus());

    public static final RegistryObject<Item> BLAZING_SPIRIT_FOCUS = JEROTES_ITEMS.register(
            "blazing_spirit_focus", () -> new BlazingSpiritFocus());
    public GeneratedMod() {
        consolidateConfigs();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SupremeServantConfigs.SPEC,
                "goety_supreme/servants.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SupremeFociConfig.SPEC,
                "goety_supreme/focus.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SupremeMobsConfig.SPEC,
                "goety_supreme/mobs.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SoulReaperConfig.SPEC,
                "goety_supreme/soul_reaper.toml");
        if (FMLEnvironment.dist.isClient()) {
            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PerformanceConfig.SPEC,
                    "goety_supreme/performance.toml");
        }
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModNetwork.init();
        InfernalMaskNetwork.register();
        BLOCKS.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        ModEntityTypes.ENTITY_TYPES.register(bus);
        if (JerotesVillageCompat.isJerotesVillageLoaded()) {
            LOGGER.info("[goety_supreme] 已检测到 jerotesvillage：J 村联动仆从 / 聚晶 / 刷怪蛋正常注册。");
            JerotesVillageCompatManager.init(bus);
        } else {
            LOGGER.warn("[goety_supreme] 未检测到 jerotesvillage 前置：J 村联动内容（蛇龙 / 沉降领主 / 二轮等）"
                    + "一律不注册。如果存档用过这些方块 / 实体 / 聚晶，必须换到同时装了 jerotes + jerotesvillage"
                    + "（且版本与建档时一致）的实例再打开，否则世界会因注册表缺项打不开。");
        }
        if (MutantMonstersCompat.isLoaded()) {
            LOGGER.info("[goety_supreme] 已检测到 mutantmonsters：突变生物仆从 / 聚晶 / 刷怪蛋正常注册。");
            MutantMonstersCompatManager.init(bus);
        } else {
            LOGGER.warn("[goety_supreme] 未检测到 mutantmonsters 前置：突变生物内容（突变僵尸 / 雪傀儡 / 骷髅 /"
                    + " 末影人四仆从、可憎雪傀儡、巨锤 / 碎冰 / 影移三聚晶与它们的刷怪蛋）一律不注册。"
                    + "模组其余内容不受影响；如果存档里已经存在这些实体 / 物品，必须换回装了 Mutant Monsters"
                    + " 的实例再打开，否则世界会因注册表缺项打不开。");
        }
        ITEMS.register(bus);
        SOUNDS.register(bus);
        PARTICLE_TYPES.register(bus);
        MOB_EFFECTS.register(bus);
        ModMenus.MENU_TYPES.register(bus);
        AstralSetDropin.register(bus);
        SupremeCreativeTab.register(bus);
        SupremeRituals.RITUALS.register(bus);        // 注意：MutantMonstersEvents 处理的是 EntityJoinLevelEvent（游戏总线事件，非
    }

    private static void consolidateConfigs() {
        java.nio.file.Path configDir = net.minecraftforge.fml.loading.FMLPaths.CONFIGDIR.get();
        java.nio.file.Path folder = configDir.resolve("goety_supreme");
        mergeInto(folder.resolve("servants.toml"), folder, configDir, new String[][] {
                {"mutant_zombie_servant.toml", "goety_supreme-common.toml"},
                {"mutant_snow_golem_servant.toml", "goety_supreme-snow_golem_servant.toml"},
                {"mutant_skeleton_servant.toml", "goety_supreme-skeleton_servant.toml"},
                {"mutant_enderman_servant.toml", null},
                {"player_mob_servant.toml", "goety_supreme-player_mob_servant.toml"},
                {"guardian_servant.toml", null},
                {"sakura_spirit.toml", null},
        });
        mergeInto(folder.resolve("focus.toml"), folder, configDir, new String[][] {
                {"player_mob_focus.toml", "goety_supreme-player_mob_focus.toml"},
        });
    }

    private static void mergeInto(java.nio.file.Path target, java.nio.file.Path folder,
            java.nio.file.Path legacyDir, String[][] sources) {
        try {
            if (java.nio.file.Files.exists(target)) {
                return;
            }
            StringBuilder body = new StringBuilder();
            java.util.List<java.nio.file.Path> consumed = new java.util.ArrayList<>();
            for (String[] source : sources) {
                java.nio.file.Path inFolder = folder.resolve(source[0]);
                if (java.nio.file.Files.isRegularFile(inFolder)) {
                    body.append(java.nio.file.Files.readString(inFolder)).append('\n');
                    consumed.add(inFolder);
                    continue;
                }
                if (source[1] != null) {
                    java.nio.file.Path inRoot = legacyDir.resolve(source[1]);
                    if (java.nio.file.Files.isRegularFile(inRoot)) {
                        body.append(java.nio.file.Files.readString(inRoot)).append('\n');
                        consumed.add(inRoot);
                    }
                }
            }
            if (body.isEmpty()) {
                return;
            }
            java.nio.file.Files.createDirectories(target.getParent());
            java.nio.file.Files.writeString(target, body.toString());
            for (java.nio.file.Path old : consumed) {
                java.nio.file.Files.deleteIfExists(old);
            }
        } catch (Exception ignored) {
        }
    }
}
