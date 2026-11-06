package cn.blockforge.generated.goetysupreme.compat;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import cn.blockforge.generated.goetysupreme.entity.AbominableSnowGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.AbominableSnowGolemServantRenderer;
import cn.blockforge.generated.goetysupreme.event.ClientModEvents;
import cn.blockforge.generated.goetysupreme.entity.EndersoulCloneServant;
import cn.blockforge.generated.goetysupreme.renderer.EndersoulCloneServantRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompatManager;
import cn.blockforge.generated.goetysupreme.event.ModEntityEvents;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantEndermanServantRenderer;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompatManager;
import cn.blockforge.generated.goetysupreme.event.MutantMonstersEvents;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantSkeletonServantArrowRenderer;
import cn.blockforge.generated.goetysupreme.renderer.MutantSkeletonServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantSnowGolemServantRenderer;
import cn.blockforge.generated.goetysupreme.model.MutantSnowGolemShoulderArmorModel;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieServantRenderer;

public final class MutantMonstersCompatManager {

    private MutantMonstersCompatManager() {
    }

    public static void init(IEventBus modEventBus) {
        ModEntityTypes.MUTANT_ENTITY_TYPES.register(modEventBus);
        GeneratedMod.MUTANT_ITEMS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(MutantMonstersEvents.class);
    }

    public static void setCustomAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.MUTANT_ZOMBIE_SERVANT.get(),
                MutantZombieServant.registerAttributes().build());
        event.put(ModEntityTypes.MUTANT_SNOW_GOLEM_SERVANT.get(),
                MutantSnowGolemServant.registerAttributes().build());
        event.put(ModEntityTypes.MUTANT_SKELETON_SERVANT.get(),
                MutantSkeletonServant.registerAttributes().build());
        event.put(ModEntityTypes.MUTANT_ENDERMAN_SERVANT.get(),
                MutantEndermanServant.registerAttributes().build());
        event.put(ModEntityTypes.ENDERSOUL_CLONE_SERVANT.get(),
                EndersoulCloneServant.registerAttributes().build());
        event.put(ModEntityTypes.ABOMINABLE_SNOW_GOLEM_SERVANT.get(),
                AbominableSnowGolemServant.registerAttributes().build());
    }

    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                MutantSnowGolemShoulderArmorModel.LAYER,
                MutantSnowGolemShoulderArmorModel::createBodyLayer);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.MUTANT_ZOMBIE_SERVANT.get(),
                MutantZombieServantRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.MUTANT_SNOW_GOLEM_SERVANT.get(),
                MutantSnowGolemServantRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.MUTANT_SKELETON_SERVANT.get(),
                MutantSkeletonServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.MUTANT_SKELETON_ARROW.get(),
                MutantSkeletonServantArrowRenderer::new);
        event.registerEntityRenderer((net.minecraft.world.entity.EntityType) ModEntityTypes.MUTANT_ENDERMAN_SERVANT.get(),
                MutantEndermanServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ENDERSOUL_CLONE_SERVANT.get(),
                EndersoulCloneServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ENDERSOUL_FRAGMENT_SERVANT.get(),
                fuzs.mutantmonsters.client.renderer.entity.EndersoulFragmentRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.MUTANT_ENDERMAN_THROWN_BLOCK.get(),
                fuzs.mutantmonsters.client.renderer.entity.ThrowableBlockRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ENDER_HAND_HELD_BLOCK.get(),
                fuzs.mutantmonsters.client.renderer.entity.ThrowableBlockRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ABOMINABLE_SNOW_GOLEM_SERVANT.get(),
                AbominableSnowGolemServantRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ABOMINABLE_SNOW_GOLEM_SNOW_BLOCK.get(),
                fuzs.mutantmonsters.client.renderer.entity.ThrowableBlockRenderer::new);
    }
}
