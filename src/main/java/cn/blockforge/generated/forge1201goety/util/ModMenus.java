package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModMenus;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.gui.PlayerMobServantInventoryMenu;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, GeneratedMod.MOD_ID);

    public static final RegistryObject<MenuType<PlayerMobServantInventoryMenu>> PLAYER_MOB_INVENTORY =
            MENU_TYPES.register("player_mob_inventory",
                    () -> IForgeMenuType.create((int windowId, Inventory playerInventory, FriendlyByteBuf buf) ->
                            new PlayerMobServantInventoryMenu(windowId, playerInventory, buf)));

    private ModMenus() {
    }
}
