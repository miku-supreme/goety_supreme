package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.SupremeCreativeTab;

public final class SupremeCreativeTab {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GeneratedMod.MOD_ID);

    private static final ResourceKey<CreativeModeTab> GOETY_SERVANTS = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath("goety", "goety_servants"));

    public static final RegistryObject<CreativeModeTab> SUPREME_TAB = TABS.register("goety_supreme",
            SupremeCreativeTab::buildTab);

    private SupremeCreativeTab() {
    }

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }

    private static CreativeModeTab buildTab() {
        try {
            return CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + GeneratedMod.MOD_ID + ".goety_supreme"))
                    .icon(() -> new ItemStack(GeneratedMod.RAGING_STAR.get()))
                    .displayItems((params, output) -> params.holders()
                            .lookupOrThrow(Registries.ITEM)
                            .listElements()
                            .filter(holder -> GeneratedMod.MOD_ID
                                    .equals(holder.key().location().getNamespace()))
                            .forEach(holder -> output.accept(new ItemStack(holder.value()))))
                    .build();
        } catch (Throwable t) {
            return CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + GeneratedMod.MOD_ID + ".goety_supreme"))
                    .icon(() -> new ItemStack(GeneratedMod.RAGING_STAR.get()))
                    .displayItems((params, output) -> {
                    })
                    .build();
        }
    }
}
