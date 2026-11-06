package cn.blockforge.generated.goetysupreme.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.compat.MutantZombieServantJeiPlugin;

@JeiPlugin
public final class MutantZombieServantJeiPlugin implements IModPlugin {
    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "jei_plugin");

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Component[] behavior = {
                Component.translatable("info.goety_supreme.servant.behavior"),
                Component.translatable("info.goety_supreme.servant.follow"),
                Component.translatable("info.goety_supreme.servant.wander"),
                Component.translatable("info.goety_supreme.servant.staying"),
                Component.translatable("info.goety_supreme.servant.guard")};
        registration.addItemStackInfo(new ItemStack(GeneratedMod.GOETY_ZOMBIE_SERVANT_SPAWN_EGG.get()), behavior);
        if (MutantMonstersCompat.isLoaded()) {
            registration.addItemStackInfo(
                    new ItemStack(GeneratedMod.MUTANT_ZOMBIE_SERVANT_SPAWN_EGG.get()), behavior);
            registration.addItemStackInfo(
                    new ItemStack(GeneratedMod.MUTANT_SNOW_GOLEM_SERVANT_SPAWN_EGG.get()), behavior);
        }
    }

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }
}
