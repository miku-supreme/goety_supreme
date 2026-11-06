package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.client.render.IceSpikeRenderer;
import com.Polarice3.Goety.client.render.NecroBoltRenderer;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.golem.RedstoneMonstrosity;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import cn.blockforge.generated.goetysupreme.model.AbominableSnowGolemModel;
import cn.blockforge.generated.goetysupreme.renderer.AncientSecondRounderArrowRenderer;
import cn.blockforge.generated.goetysupreme.renderer.AstralSkullParticle;
import cn.blockforge.generated.goetysupreme.model.BitterColdWindModel;
import cn.blockforge.generated.goetysupreme.renderer.BitterColdWindRenderer;
import cn.blockforge.generated.goetysupreme.renderer.BoneScoutArrowRenderer;
import cn.blockforge.generated.goetysupreme.renderer.BulgeBulletServantRenderer;
import cn.blockforge.generated.goetysupreme.model.BulgeServantModel;
import cn.blockforge.generated.goetysupreme.renderer.BulgeServantRenderer;
import cn.blockforge.generated.goetysupreme.event.ClientModEvents;
import cn.blockforge.generated.goetysupreme.renderer.CorrosiverBreathAreaRenderer;
import cn.blockforge.generated.goetysupreme.model.CorrosiverServantModel;
import cn.blockforge.generated.goetysupreme.renderer.CorrosiverServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.CorruptedLaserParticle;
import cn.blockforge.generated.goetysupreme.renderer.CryptIceStormRenderer;
import cn.blockforge.generated.goetysupreme.model.CryptNecromancerModel;
import cn.blockforge.generated.goetysupreme.renderer.CryptNecromancerRenderer;
import cn.blockforge.generated.goetysupreme.model.DoomFiveHeadSharkModel;
import cn.blockforge.generated.goetysupreme.renderer.DoomFiveHeadSharkRenderer;
import cn.blockforge.generated.goetysupreme.renderer.DroughtBlazeServantRenderer;
import cn.blockforge.generated.goetysupreme.model.DroughtFireballModel;
import cn.blockforge.generated.goetysupreme.renderer.DroughtFireballServantRenderer;
import cn.blockforge.generated.goetysupreme.model.DroughtWildfireServantModel;
import cn.blockforge.generated.goetysupreme.renderer.DroughtWildfireServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.EndermanServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.EnderStowawayServantRenderer;
import cn.blockforge.generated.goetysupreme.model.EngenderGolemServantModel;
import cn.blockforge.generated.goetysupreme.renderer.EnhancedWitherSkullRenderer;
import cn.blockforge.generated.goetysupreme.renderer.FieldShieldAuraParticle;
import cn.blockforge.generated.goetysupreme.util.FieldShieldAuraProvider;
import cn.blockforge.generated.goetysupreme.model.FlamoarServantModel;
import cn.blockforge.generated.goetysupreme.renderer.FlamoarServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.FlungBlockRenderer;
import cn.blockforge.generated.goetysupreme.model.FlyingFireballServantModel;
import cn.blockforge.generated.goetysupreme.renderer.FlyingFireballServantRenderer;
import cn.blockforge.generated.goetysupreme.model.FrostYetiServantModel;
import cn.blockforge.generated.goetysupreme.renderer.FrostYetiServantRenderer;
import cn.blockforge.generated.goetysupreme.model.FurnaceGolemModel;
import cn.blockforge.generated.goetysupreme.renderer.FurnaceGolemRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GoetySkeletonServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.GoetyZombieServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.GreenFireRenderer;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;
import cn.blockforge.generated.goetysupreme.renderer.NamelessLaserRenderer;
import cn.blockforge.generated.goetysupreme.model.GreenSmithModel;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithRenderer;
import cn.blockforge.generated.goetysupreme.util.GuiItemRenderContext;
import cn.blockforge.generated.goetysupreme.entity.HeavyArmoredSedimentGuardServant;
import cn.blockforge.generated.goetysupreme.model.HorseServantModel;
import cn.blockforge.generated.goetysupreme.model.IceGolemServantModel;
import cn.blockforge.generated.goetysupreme.renderer.IceGolemServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.IfritClone;
import cn.blockforge.generated.goetysupreme.model.IfritModel;
import cn.blockforge.generated.goetysupreme.renderer.IfritRenderer;
import cn.blockforge.generated.goetysupreme.renderer.InvisibleRenderer;
import cn.blockforge.generated.goetysupreme.renderer.IronGolemServantRenderer;
import cn.blockforge.generated.goetysupreme.model.IronTeethServantModel;
import cn.blockforge.generated.goetysupreme.renderer.IronTeethServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.JerotesShootRenderer;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.renderer.LightningBoltBeamRenderer;
import cn.blockforge.generated.goetysupreme.model.LightningWormBodyModel;
import cn.blockforge.generated.goetysupreme.renderer.LightningWormPartRenderer;
import cn.blockforge.generated.goetysupreme.model.LightningWormServantModel;
import cn.blockforge.generated.goetysupreme.renderer.LightningWormServantRenderer;
import cn.blockforge.generated.goetysupreme.model.MagmaGolemModel;
import cn.blockforge.generated.goetysupreme.renderer.MagmaGolemRenderer;
import cn.blockforge.generated.goetysupreme.renderer.MalignasaurBombRenderer;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.ModMenus;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompatManager;
import cn.blockforge.generated.goetysupreme.renderer.OceanCurrentBreathAreaRenderer;
import cn.blockforge.generated.goetysupreme.gui.PlayerMobServantInventoryScreen;
import cn.blockforge.generated.goetysupreme.model.PlayerMobServantModel;
import cn.blockforge.generated.goetysupreme.renderer.PlayerMobServantRenderer;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinFolder;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinLayers;
import cn.blockforge.generated.goetysupreme.model.PrimordierServantModel;
import cn.blockforge.generated.goetysupreme.renderer.PrimordierServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineArrowRenderer;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineBladeSlashRenderer;
import cn.blockforge.generated.goetysupreme.model.PrismarineSoldierModel;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineSoldierRenderer;
import cn.blockforge.generated.goetysupreme.model.PrisonGolemModel;
import cn.blockforge.generated.goetysupreme.renderer.PrisonGolemRenderer;
import cn.blockforge.generated.goetysupreme.model.RagingMonstrosityModel;
import cn.blockforge.generated.goetysupreme.renderer.RagingMonstrosityRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ReaperBladeRenderer;
import cn.blockforge.generated.goetysupreme.model.ReaperNecromancerModel;
import cn.blockforge.generated.goetysupreme.renderer.ReaperNecromancerRenderer;
import cn.blockforge.generated.goetysupreme.model.ReaperScytheModel;
import cn.blockforge.generated.goetysupreme.renderer.ReaperSpinRenderer;
import cn.blockforge.generated.goetysupreme.renderer.RoarWaveParticle;
import cn.blockforge.generated.goetysupreme.renderer.RoyalWarPythonPartRenderer;
import cn.blockforge.generated.goetysupreme.renderer.RoyalWarPythonRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SakuraSpiritRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SakuraSpiritSlashRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ScaledSummonCircleRenderer;
import cn.blockforge.generated.goetysupreme.model.ScorchbackModel;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackQuillRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ScorchbackSpikeRenderer;
import cn.blockforge.generated.goetysupreme.model.SeaWaveServantModel;
import cn.blockforge.generated.goetysupreme.renderer.SecondRounderAnimatedArmorServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SecondRounderSkeletonServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SecondRounderSpiderServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SecondRounderZombieServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SedimentGuardArrowRenderer;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardServant;
import cn.blockforge.generated.goetysupreme.renderer.SedimentLordServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SedimentMissileRenderer;
import cn.blockforge.generated.goetysupreme.model.SedimentServantModel;
import cn.blockforge.generated.goetysupreme.renderer.SedimentServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.SedimentSoulStabilizerServant;
import cn.blockforge.generated.goetysupreme.renderer.SerpenperthMeteorRenderer;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierModel;
import cn.blockforge.generated.goetysupreme.model.SerponColonialSoldierOtherModel;
import cn.blockforge.generated.goetysupreme.model.SerponCombatExecuterModel;
import cn.blockforge.generated.goetysupreme.renderer.SerponCombatExecuterRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SerponDartRenderer;
import cn.blockforge.generated.goetysupreme.model.SerponFireApostleModel;
import cn.blockforge.generated.goetysupreme.renderer.SerponFireApostleRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SerponFireballRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SerponFlamesRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SerponFlamesServantRenderer;
import cn.blockforge.generated.goetysupreme.model.SerponJavelinModel;
import cn.blockforge.generated.goetysupreme.renderer.SerponJavelinRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SerponRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SerponSiroccoRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ServantTridentRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ServantWitherSkullRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SkeletonHorseServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SkullCloudSmokeParticle;
import cn.blockforge.generated.goetysupreme.renderer.SnowGolemServantRenderer;
import cn.blockforge.generated.goetysupreme.model.SoulCoffinModel;
import cn.blockforge.generated.goetysupreme.renderer.SoulCoffinRenderer;
import cn.blockforge.generated.goetysupreme.model.SoulReaperModel;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperRenderer;
import cn.blockforge.generated.goetysupreme.renderer.TintedTotemEffectParticle;
import cn.blockforge.generated.goetysupreme.renderer.WarlordBladeTrailRenderer;
import cn.blockforge.generated.goetysupreme.renderer.WarlordHellfireSlashRenderer;
import cn.blockforge.generated.goetysupreme.model.WarPythonBodyModel;
import cn.blockforge.generated.goetysupreme.model.WarPythonModel;
import cn.blockforge.generated.goetysupreme.renderer.WarPythonPartRenderer;
import cn.blockforge.generated.goetysupreme.renderer.WarPythonRenderer;
import cn.blockforge.generated.goetysupreme.model.WildMageModel;
import cn.blockforge.generated.goetysupreme.renderer.WildMageRenderer;
import cn.blockforge.generated.goetysupreme.model.WitherServantModel;
import cn.blockforge.generated.goetysupreme.model.NamelessWitherModel;
import cn.blockforge.generated.goetysupreme.renderer.NamelessWitherRenderer;
import cn.blockforge.generated.goetysupreme.renderer.WitherServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.WitherSkeletonHorseServantRenderer;
import cn.blockforge.generated.goetysupreme.item.WitherWarlordHelmetItem;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordHelmetLayer;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordHelmetModel;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;
import cn.blockforge.generated.goetysupreme.renderer.WitherWarlordRenderer;
import cn.blockforge.generated.goetysupreme.renderer.WitherWarlordServantRenderer;
import cn.blockforge.generated.goetysupreme.mixin.GuiItemRenderContextMixin;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

    public static final net.minecraft.client.KeyMapping INFERNAL_MASK_SUMMON =
            new net.minecraft.client.KeyMapping("key.goety_supreme.infernal_mask_summon",
                    com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM,
                    org.lwjgl.glfw.GLFW.GLFW_KEY_G, "key.categories.goety_supreme");

    private static final net.minecraft.resources.ResourceLocation GREEN_SMITH_TEXTURE =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/green_smith.png");

    private ClientModEvents() {
    }

    @SubscribeEvent
    public static void registerScreens(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.PLAYER_MOB_INVENTORY.get(),
                    PlayerMobServantInventoryScreen::new);
            PlayerMobServantSkinFolder.ensureFolder();
        });
    }

    @SubscribeEvent
    public static void registerItemProperties(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(
                    GeneratedMod.HELLFIRE_SABER.get(),
                    ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "guarding"),
                    (stack, level, entity, seed) ->
                            entity != null && entity.isUsingItem() && entity.getUseItem() == stack
                                    ? 1.0F : 0.0F);
            registerGuiIcon(GeneratedMod.HELLFIRE_SABER.get());
            registerGuiIcon(GeneratedMod.WITHERING_SABER.get());
            registerBowPullProperties();
        });
    }

    private static void registerBowPullProperties() {
        ItemProperties.register(GeneratedMod.FELL_BOW.get(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "pull"),
                (stack, level, entity, seed) -> {
                    if (entity == null) {
                        return 0.0F;
                    }
                    return entity.getUseItem() != stack ? 0.0F
                            : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
                });
        ItemProperties.register(GeneratedMod.FELL_BOW.get(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "pulling"),
                (stack, level, entity, seed) ->
                        entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
        ItemProperties.register(GeneratedMod.FELL_CROSSBOW.get(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "pull"),
                (stack, level, entity, seed) -> {
                    if (entity == null) {
                        return 0.0F;
                    }
                    return CrossbowItem.isCharged(stack) ? 0.0F
                            : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks())
                                    / (float) CrossbowItem.getChargeDuration(stack);
                });
        ItemProperties.register(GeneratedMod.FELL_CROSSBOW.get(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "pulling"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem()
                        && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(GeneratedMod.FELL_CROSSBOW.get(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "charged"),
                (stack, level, entity, seed) ->
                        entity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
    }

    private static void registerGuiIcon(net.minecraft.world.item.Item item) {
        ItemProperties.register(
                item,
                ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "gui"),
                (stack, level, entity, seed) -> GuiItemRenderContext.isGuiItemRender() ? 1.0F : 0.0F);
    }

    @SubscribeEvent
    public static void registerParticleProviders(
            net.minecraftforge.client.event.RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GeneratedMod.FIELD_SHIELD_AURA.get(), FieldShieldAuraProvider::new);
        event.registerSpriteSet(GeneratedMod.WILD_TOTEM_EFFECT.get(),
                net.minecraft.client.particle.SpellParticle.Provider::new);
        event.registerSpriteSet(GeneratedMod.GREEN_TOTEM_EFFECT.get(),
                net.minecraft.client.particle.SpellParticle.Provider::new);
        event.registerSpriteSet(GeneratedMod.SOUL_TOTEM_EFFECT.get(),
                net.minecraft.client.particle.SpellParticle.Provider::new);
        event.registerSpriteSet(GeneratedMod.TINTED_TOTEM_EFFECT.get(), TintedTotemEffectParticle.Provider::new);
        event.registerSpriteSet(GeneratedMod.CORRUPTED_LASER_SPARK.get(),
                CorruptedLaserParticle.SparkProvider::new);
        event.registerSpriteSet(GeneratedMod.CORRUPTED_LASER_GLOW.get(),
                CorruptedLaserParticle.GlowProvider::new);
        event.registerSpriteSet(GeneratedMod.NAMELESS_LASER_GLOW.get(),
                CorruptedLaserParticle.GlowProvider::new);
        event.registerSpriteSet(GeneratedMod.NAMELESS_GATHER.get(),
                CorruptedLaserParticle.GlowProvider::new);
        event.registerSpriteSet(GeneratedMod.NAMELESS_SOUL.get(),
                net.minecraft.client.particle.SoulParticle.Provider::new);
        event.registerSpriteSet(GeneratedMod.NAMELESS_FLAME.get(),
                net.minecraft.client.particle.SoulParticle.Provider::new);
        event.registerSpriteSet(GeneratedMod.ROAR_WAVE.get(), RoarWaveParticle.Provider::new);
        event.registerSpriteSet(GeneratedMod.ASTRAL_SKULL_SPARK.get(), AstralSkullParticle.Provider::new);
        event.registerSpriteSet(GeneratedMod.SKULL_CLOUD_SMOKE.get(), SkullCloudSmokeParticle.Provider::new);
        event.registerSpecial(GeneratedMod.SUPREME_SOUL_EMITTER.get(),
                new cn.blockforge.generated.goetysupreme.client.particles.SupremeSoulEmitterParticle.Provider());
        event.registerSpecial(GeneratedMod.SUPREME_SOUL_DUST.get(),
                new cn.blockforge.generated.goetysupreme.client.particles.SupremeSoulDustParticle.Provider());
        event.registerSpecial(GeneratedMod.SUPREME_SOUL_SPARK.get(),
                new cn.blockforge.generated.goetysupreme.client.particles.SupremeSoulPixelParticle.SparkProvider());
        event.registerSpecial(GeneratedMod.SUPREME_SOUL_BURST.get(),
                new cn.blockforge.generated.goetysupreme.client.particles.SupremeSoulLeaderParticle.Provider(
                        cn.blockforge.generated.goetysupreme.client.particles.SupremeSoulLeaderParticle.Behaviour.BURST));
        event.registerSpecial(GeneratedMod.SUPREME_SOUL_STREAK.get(),
                new cn.blockforge.generated.goetysupreme.client.particles.SupremeSoulPixelParticle.Provider(
                        cn.blockforge.generated.goetysupreme.client.particles.SupremeSoulPixelParticle.Mode.STREAK));
        event.registerSpecial(GeneratedMod.SUPREME_SOUL_LAVA.get(),
                new cn.blockforge.generated.goetysupreme.client.particles.SupremeEruptionParticle.Provider(1.0F, 1.0F, 1.0F));
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                PlayerMobServantModel.LAYER_3D,
                () -> PlayerMobServantModel.create3DSkinLayer(false));
        event.registerLayerDefinition(
                PlayerMobServantModel.LAYER_SLIM_3D,
                () -> PlayerMobServantModel.create3DSkinLayer(true));
        event.registerLayerDefinition(
                PlayerMobServantModel.LAYER_PREVIEW,
                () -> PlayerMobServantModel.createPreviewLayer(false));
        event.registerLayerDefinition(
                PlayerMobServantModel.LAYER_SLIM_PREVIEW,
                () -> PlayerMobServantModel.createPreviewLayer(true));
        event.registerLayerDefinition(
                HorseServantModel.LAYER,
                HorseServantModel::createBodyLayer);
        event.registerLayerDefinition(
                FurnaceGolemModel.LAYER,
                FurnaceGolemModel::createBodyLayer);
        event.registerLayerDefinition(
                RagingMonstrosityModel.LAYER,
                RagingMonstrosityModel::createBodyLayer);
        event.registerLayerDefinition(
                WildMageModel.LAYER_LOCATION,
                WildMageModel::createBodyLayer);
        event.registerLayerDefinition(
                ReaperNecromancerModel.LAYER_LOCATION,
                ReaperNecromancerModel::createBodyLayer);
        event.registerLayerDefinition(
                ReaperScytheModel.LAYER_LOCATION,
                ReaperScytheModel::createBodyLayer);
        event.registerLayerDefinition(
                SoulReaperModel.LAYER_LOCATION,
                SoulReaperModel::createBodyLayer);
        event.registerLayerDefinition(
                SoulCoffinModel.LAYER_LOCATION,
                SoulCoffinModel::createBodyLayer);
        event.registerLayerDefinition(
                CryptNecromancerModel.LAYER_LOCATION,
                CryptNecromancerModel::createBodyLayer);
        event.registerLayerDefinition(
                GreenSmithModel.LAYER_LOCATION,
                GreenSmithModel::createBodyLayer);
        event.registerLayerDefinition(
                DoomFiveHeadSharkModel.LAYER,
                DoomFiveHeadSharkModel::createBodyLayer);
        event.registerLayerDefinition(
                ScorchbackModel.LAYER,
                ScorchbackModel::createBodyLayer);
        event.registerLayerDefinition(
                IfritModel.LAYER,
                IfritModel::createBodyLayer);
        event.registerLayerDefinition(
                MagmaGolemModel.LAYER,
                MagmaGolemModel::createBodyLayer);
        event.registerLayerDefinition(
                PrisonGolemModel.LAYER,
                PrisonGolemModel::createBodyLayer);
        event.registerLayerDefinition(
                WitherWarlordModel.LAYER,
                WitherWarlordModel::createBodyLayer);
        event.registerLayerDefinition(
                WitherWarlordHelmetModel.LAYER,
                WitherWarlordHelmetModel::createBodyLayer);
        event.registerLayerDefinition(
                EngenderGolemServantModel.LAYER,
                EngenderGolemServantModel::createBodyLayer);
        event.registerLayerDefinition(
                IceGolemServantModel.LAYER_LOCATION,
                IceGolemServantModel::createBodyLayer);
        event.registerLayerDefinition(
                AbominableSnowGolemModel.LAYER,
                AbominableSnowGolemModel::createBodyLayer);
        event.registerLayerDefinition(
                WitherServantModel.LAYER,
                WitherServantModel::createBodyLayer);
            event.registerLayerDefinition(
                NamelessWitherModel.LAYER,
                NamelessWitherModel::createBodyLayer);
        event.registerLayerDefinition(
                PrismarineSoldierModel.LAYER,
                PrismarineSoldierModel::createBodyLayer);
        if (JerotesVillageCompat.isJerotesVillageLoaded()) {
            event.registerLayerDefinition(
                    SedimentServantModel.LAYER,
                    SedimentServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    BulgeServantModel.LAYER,
                    BulgeServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    SeaWaveServantModel.LAYER,
                    SeaWaveServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    DroughtWildfireServantModel.LAYER,
                    DroughtWildfireServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    DroughtFireballModel.LAYER_LOCATION,
                    DroughtFireballModel::createBodyLayer);
            event.registerLayerDefinition(
                    SerponColonialSoldierModel.LAYER,
                    SerponColonialSoldierModel::createBodyLayer);
            event.registerLayerDefinition(
                    SerponColonialSoldierOtherModel.LAYER,
                    SerponColonialSoldierOtherModel::createBodyLayer);
            event.registerLayerDefinition(
                    SerponCombatExecuterModel.LAYER,
                    SerponCombatExecuterModel::createBodyLayer);
            event.registerLayerDefinition(
                    SerponFireApostleModel.LAYER,
                    SerponFireApostleModel::createBodyLayer);
            event.registerLayerDefinition(
                    SerponJavelinModel.LAYER,
                    SerponJavelinModel::createBodyLayer);
            event.registerLayerDefinition(
                    WarPythonModel.LAYER_LOCATION,
                    WarPythonModel::createBodyLayer);
            event.registerLayerDefinition(
                    WarPythonBodyModel.LAYER_LOCATION,
                    WarPythonBodyModel::createBodyLayer);
            event.registerLayerDefinition(
                    CorrosiverServantModel.LAYER_LOCATION,
                    CorrosiverServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    LightningWormServantModel.LAYER_LOCATION,
                    LightningWormServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    LightningWormBodyModel.LAYER_LOCATION,
                    LightningWormBodyModel::createBodyLayer);
            event.registerLayerDefinition(
                    BitterColdWindModel.LAYER,
                    BitterColdWindModel::createBodyLayer);
            event.registerLayerDefinition(
                    FlamoarServantModel.LAYER_LOCATION,
                    FlamoarServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    FlamoarServantModel.LADY_LAYER_LOCATION,
                    FlamoarServantModel::createLadyBodyLayer);
            event.registerLayerDefinition(
                    PrimordierServantModel.LAYER_LOCATION,
                    PrimordierServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    FrostYetiServantModel.LAYER_LOCATION,
                    FrostYetiServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    FlyingFireballServantModel.LAYER,
                    FlyingFireballServantModel::createBodyLayer);
            event.registerLayerDefinition(
                    IronTeethServantModel.LAYER,
                    IronTeethServantModel::createBodyLayer);
        }
        if (MutantMonstersCompat.isLoaded()) {
            MutantMonstersCompatManager.registerLayerDefinitions(event);
        }
    }

    @SubscribeEvent
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.SNOW_GOLEM_SERVANT.get(),
                SnowGolemServantRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.IRON_GOLEM_SERVANT.get(),
                IronGolemServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SKELETON_HORSE_SERVANT.get(),
                SkeletonHorseServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.WITHER_SKELETON_HORSE_SERVANT.get(),
                WitherSkeletonHorseServantRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.ENDERMAN_SERVANT.get(),
                EndermanServantRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.GOETY_ZOMBIE_SERVANT.get(),
                GoetyZombieServantRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.GOETY_SKELETON_SERVANT.get(),
                GoetySkeletonServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BONE_SCOUT_ARROW.get(), BoneScoutArrowRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.PLAYER_MOB_SERVANT.get(),
                PlayerMobServantRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.SAKURA_SPIRIT.get(),
                SakuraSpiritRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.SAKURA_SPIRIT_SERVANT.get(),
                SakuraSpiritRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.SAKURA_SPIRIT_SLASH.get(),
                SakuraSpiritSlashRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.WITHER_SERVANT.get(), WitherServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.NAMELESS_WITHER.get(), NamelessWitherRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.NAMELESS_BEAM.get(), NecroBoltRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SERVANT_WITHER_SKULL.get(),
                ServantWitherSkullRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ENHANCED_WITHER_SKULL.get(),
                EnhancedWitherSkullRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SERVANT_THROWN_TRIDENT.get(),
                ServantTridentRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.FURNACE_GOLEM.get(), FurnaceGolemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.MAGMA_GOLEM.get(), MagmaGolemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.MAGMA_FIREBALL.get(),
                context -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(context, 1.5F, true));
        event.registerEntityRenderer(ModEntityTypes.PRISON_GOLEM.get(), PrisonGolemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.WITHER_WARLORD.get(), WitherWarlordRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.WITHER_WARLORD_SERVANT.get(),
                WitherWarlordServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ICE_GOLEM_SERVANT.get(), IceGolemServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.HARMFUL_SNOWBALL.get(),
                context -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(context, 1.0F, true));
        event.registerEntityRenderer(ModEntityTypes.PRISMARINE_SOLDIER.get(),
                PrismarineSoldierRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.WILD_MAGE.get(),
                (net.minecraft.client.renderer.entity.EntityRendererProvider) context -> new WildMageRenderer(context));
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.WILD_MAGE_SERVANT.get(),
                (net.minecraft.client.renderer.entity.EntityRendererProvider) context -> new WildMageRenderer(context));
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.REAPER_NECROMANCER.get(),
                (net.minecraft.client.renderer.entity.EntityRendererProvider) context -> new ReaperNecromancerRenderer(context));
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.REAPER_NECROMANCER_SERVANT.get(),
                (net.minecraft.client.renderer.entity.EntityRendererProvider) context -> new ReaperNecromancerRenderer(context));
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.SOUL_REAPER.get(),
                (net.minecraft.client.renderer.entity.EntityRendererProvider) context -> new SoulReaperRenderer(context));
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.SOUL_REAPER_SERVANT.get(),
                (net.minecraft.client.renderer.entity.EntityRendererProvider) context -> new SoulReaperRenderer(context));
        event.registerEntityRenderer(ModEntityTypes.SOUL_COFFIN.get(), SoulCoffinRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SCALED_SUMMON_CIRCLE.get(),
                ScaledSummonCircleRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CRYPT_NECROMANCER_SERVANT.get(),
                CryptNecromancerRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CRYPT_ICE_STORM.get(),
                CryptIceStormRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.TRACKING_ICE_SPIKE.get(),
                (net.minecraft.client.renderer.entity.EntityRendererProvider) context -> new IceSpikeRenderer(context));
        event.registerEntityRenderer(ModEntityTypes.REAPER_BLADE.get(),
                ReaperBladeRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.REAPER_SPIN.get(),
                ReaperSpinRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.GREEN_SMITH.get(),
                GreenSmithRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.GREEN_SMITH_SERVANT.get(),
                GreenSmithRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.DOOM_FIVE_HEAD_SHARK.get(),
                DoomFiveHeadSharkRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.IFRIT.get(),
                IfritRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.IFRIT_CLONE.get(),
                context -> (net.minecraft.client.renderer.entity.EntityRenderer<IfritClone>)
                        (net.minecraft.client.renderer.entity.EntityRenderer<?>) new IfritRenderer(context));
        event.registerEntityRenderer(ModEntityTypes.SCORCHBACK.get(),
                ScorchbackRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SCORCHBACK_SPIKE.get(),
                ScorchbackSpikeRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SCORCHBACK_QUILL.get(),
                ScorchbackQuillRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.GREEN_BOLT.get(),
                context -> new InvisibleRenderer<>(context, GREEN_SMITH_TEXTURE));
        event.registerEntityRenderer(ModEntityTypes.GREEN_FIRE.get(),
                GreenFireRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.GREEN_SMITH_LASER.get(),
                GreenSmithLaserRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.NAMELESS_LASER.get(),
                NamelessLaserRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.PRISMARINE_ARROW.get(),
                PrismarineArrowRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.PRISMARINE_BLADE_SLASH.get(),
                PrismarineBladeSlashRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.WARLORD_HELLFIRE_SLASH.get(),
                WarlordHellfireSlashRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.WARLORD_BLADE_TRAIL.get(),
                WarlordBladeTrailRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.FLUNG_BLOCK.get(),
                FlungBlockRenderer::new);
        if (JerotesVillageCompat.isJerotesVillageLoaded()) {
            event.registerEntityRenderer(ModEntityTypes.SEDIMENT_GUARD_SERVANT.get(),
                    context -> new SedimentServantRenderer<SedimentGuardServant>(context));
            event.registerEntityRenderer(ModEntityTypes.SEDIMENT_SOUL_STABILIZER_SERVANT.get(),
                    context -> new SedimentServantRenderer<SedimentSoulStabilizerServant>(context));
            event.registerEntityRenderer(ModEntityTypes.HEAVY_ARMORED_SEDIMENT_GUARD_SERVANT.get(),
                    context -> new SedimentServantRenderer<HeavyArmoredSedimentGuardServant>(context));
            event.registerEntityRenderer(ModEntityTypes.SEDIMENT_LORD_SERVANT.get(),
                    SedimentLordServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SEDIMENT_MISSILE.get(),
                    SedimentMissileRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SEDIMENT_GUARD_ARROW.get(),
                    SedimentGuardArrowRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.ANCIENT_SECOND_ROUNDER_ARROW.get(),
                    AncientSecondRounderArrowRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.BULGE_SERVANT.get(),
                    BulgeServantRenderer.Bulge::new);
            event.registerEntityRenderer(ModEntityTypes.SEA_WAVE_SERVANT.get(),
                    BulgeServantRenderer.SeaWave::new);
            event.registerEntityRenderer(ModEntityTypes.BULGE_BULLET_SERVANT.get(),
                    BulgeBulletServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.DROUGHT_WILDFIRE_SERVANT.get(),
                    DroughtWildfireServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.DROUGHT_BLAZE_SERVANT.get(),
                    DroughtBlazeServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.DROUGHT_FIREBALL_SERVANT.get(),
                    DroughtFireballServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SECOND_ROUNDER_ZOMBIE_SERVANT.get(),
                    SecondRounderZombieServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SECOND_ROUNDER_SKELETON_SERVANT.get(),
                    SecondRounderSkeletonServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SECOND_ROUNDER_SPIDER_SERVANT.get(),
                    SecondRounderSpiderServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.ENDER_STOWAWAY_SERVANT.get(),
                    EnderStowawayServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SERPON_COLONIAL_SOLDIER.get(),
                    context -> new SerponRenderer<>(context,
                            serponTexture("serpon_colonial_soldier_other"),
                            serponTexture("serpon_colonial_soldier_glow")));
            event.registerEntityRenderer(ModEntityTypes.SERPON_RIOTER.get(),
                    context -> new SerponRenderer<>(context,
                            serponTexture("serpon_rioter_other"),
                            serponTexture("serpon_rioter_glow")));
            event.registerEntityRenderer(ModEntityTypes.SERPON_PIONEER.get(),
                    context -> new SerponRenderer<>(context,
                            serponTexture("serpon_pioneer_other"),
                            serponTexture("serpon_pioneer_glow")));
            event.registerEntityRenderer(ModEntityTypes.SERPON_FIRE_APOSTLE.get(),
                    SerponFireApostleRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SERPON_INTRUDER.get(),
                    context -> new SerponRenderer<>(context,
                            serponTexture("serpon_intruder_other"),
                            serponTexture("serpon_intruder_glow")));
            event.registerEntityRenderer(ModEntityTypes.SERPON_FLAME_WIZARD.get(),
                    context -> new SerponRenderer<>(context,
                            serponTexture("serpon_flame_wizard_other"),
                            serponTexture("serpon_flame_wizard_glow")));
            event.registerEntityRenderer(ModEntityTypes.SERPON_FLAMES.get(),
                    SerponFlamesRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SERPON_FIREBALL.get(),
                    SerponFireballRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.WAR_PYTHON_SERVANT.get(),
                    WarPythonRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.WAR_PYTHON_PART.get(),
                    WarPythonPartRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.ROYAL_WAR_PYTHON_SERVANT.get(),
                    RoyalWarPythonRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.ROYAL_WAR_PYTHON_PART.get(),
                    RoyalWarPythonPartRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SERPON_COMBAT_EXECUTER.get(),
                    SerponCombatExecuterRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SECOND_ROUNDER_ANIMATED_ARMOR_SERVANT.get(),
                    SecondRounderAnimatedArmorServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SERPON_JAVELIN.get(),
                    SerponJavelinRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SERPON_DART.get(),
                    SerponDartRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SERPON_FLAMES_SERVANT.get(),
                    SerponFlamesServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SERPON_SIROCCO.get(),
                    SerponSiroccoRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SERPENPERTH_METEOR.get(),
                    SerpenperthMeteorRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.CORROSIVER_SERVANT.get(),
                    CorrosiverServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.GEMSTONE_MALIGNASAUR_BOMB.get(),
                    MalignasaurBombRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.GEMSTONE_MALIGNASAUR_BREATH.get(),
                    context -> new JerotesShootRenderer<>(context, "ocean_current_breath"));
            event.registerEntityRenderer(ModEntityTypes.GEMSTONE_MALIGNASAUR_WHIRLPOOL.get(),
                    context -> new JerotesShootRenderer<>(context, "whirlpool"));
            event.registerEntityRenderer(ModEntityTypes.LIGHTNING_WORM_SERVANT.get(),
                    LightningWormServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.LIGHTNING_WORM_PART.get(),
                    LightningWormPartRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.LIGHTNING_WORM_THUNDERSTORM.get(),
                    context -> new LightningBoltBeamRenderer<>(context, 80, 0.3F));
            event.registerEntityRenderer(ModEntityTypes.LIGHTNING_WORM_CHAIN_LIGHTNING.get(),
                    context -> new LightningBoltBeamRenderer<>(context, 40, 0.3F));
            event.registerEntityRenderer(ModEntityTypes.BITTER_COLD_WIND_SERVANT.get(),
                    BitterColdWindRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.FLAMOAR_SERVANT.get(),
                    FlamoarServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.PRIMORDIER_SERVANT.get(),
                    PrimordierServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.FROST_YETI_SERVANT.get(),
                    FrostYetiServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.FLYING_FIREBALL_SERVANT.get(),
                    FlyingFireballServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.IRON_TEETH_SERVANT.get(),
                    IronTeethServantRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.CORROSIVER_BREATH_SERVANT.get(),
                    context -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(context, 1.0F, true));
            event.registerEntityRenderer(ModEntityTypes.CORROSIVE_ICE_SPIKE_SERVANT.get(),
                    context -> new net.minecraft.client.renderer.entity.ThrownItemRenderer<>(context, 1.0F, true));
            event.registerEntityRenderer(ModEntityTypes.CORROSIVER_BREATH_AREA_SERVANT.get(),
                    CorrosiverBreathAreaRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.CORROSIVE_SNOW_CLOUD_SERVANT.get(),
                    net.minecraft.client.renderer.entity.NoopRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.GEMSTONE_MALIGNASAUR_BREATH_AREA.get(),
                    OceanCurrentBreathAreaRenderer::new);
        }
        if (MutantMonstersCompat.isLoaded()) {
            MutantMonstersCompatManager.registerRenderers(event);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRagingMonstrosityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        net.minecraft.world.entity.EntityType<RedstoneMonstrosity> type =
                ModEntityType.REDSTONE_MONSTROSITY.get();
        event.registerEntityRenderer(type, context -> new RagingMonstrosityRenderer(context));
    }

    private static ResourceLocation serponTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath("jerotesvillage",
                "textures/entity/serpon/" + name + ".png");
    }
}
