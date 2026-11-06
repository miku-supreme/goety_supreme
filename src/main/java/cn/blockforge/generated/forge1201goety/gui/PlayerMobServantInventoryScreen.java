package cn.blockforge.generated.goetysupreme.gui;

import java.util.Locale;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.gui.PlayerMobServantInventoryMenu;
import cn.blockforge.generated.goetysupreme.gui.PlayerMobServantInventoryScreen;

public final class PlayerMobServantInventoryScreen
        extends AbstractContainerScreen<PlayerMobServantInventoryMenu> {
    private static final ResourceLocation ICONS =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/icons.png");

    private static final ResourceLocation EMPTY_HELMET = slotIcon("empty_armor_slot_helmet");
    private static final ResourceLocation EMPTY_CHESTPLATE = slotIcon("empty_armor_slot_chestplate");
    private static final ResourceLocation EMPTY_LEGGINGS = slotIcon("empty_armor_slot_leggings");
    private static final ResourceLocation EMPTY_BOOTS = slotIcon("empty_armor_slot_boots");
    private static final ResourceLocation EMPTY_SHIELD = slotIcon("empty_armor_slot_shield");
    private static final ResourceLocation EMPTY_SWORD = slotIcon("empty_slot_sword");

    private static ResourceLocation slotIcon(String name) {
        return ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                "textures/gui/sprites/item/" + name + ".png");
    }

    private final Component storageLabel =
            Component.translatable("container.goety_supreme.player_mob_inventory.storage");
    private final Component armorLabel =
            Component.translatable("container.goety_supreme.player_mob_inventory.armor");
    private final Component handsLabel =
            Component.translatable("container.goety_supreme.player_mob_inventory.hands");

    public PlayerMobServantInventoryScreen(PlayerMobServantInventoryMenu menu, Inventory playerInventory,
            Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = menu.getImageHeight();
        this.inventoryLabelY = menu.getInventoryLabelY();
        this.titleLabelY = 6;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int left = this.leftPos;
        int top = this.topPos;
        drawPanel(graphics, left, top, this.imageWidth, this.imageHeight, 0xFFC6C6C6);

        drawSlotGrid(graphics, left + PlayerMobServantInventoryMenu.STORAGE_X,
                top + PlayerMobServantInventoryMenu.STORAGE_Y, 5, this.menu.getStorageRows());
        drawSlotGrid(graphics, left + PlayerMobServantInventoryMenu.ARMOR_X,
                top + PlayerMobServantInventoryMenu.STORAGE_Y, 1, 4);
        drawSlotGrid(graphics, left + PlayerMobServantInventoryMenu.HAND_X,
                top + this.menu.getHandY(), 2, 1);
        drawSlotGrid(graphics, left + 8, top + this.menu.getPlayerInvY(), 9, 3);
        drawSlotGrid(graphics, left + 8, top + this.menu.getHotbarY(), 9, 1);
        renderSlotIcons(graphics, left, top);
        drawPanel(graphics, left + 33, top + 18, 32, 76, 0xFF707070);
        renderServantPreview(graphics, left + 49, top + 23, mouseX, mouseY);
        renderHungerBar(graphics, left + 78, top + this.menu.getHandY() + 20);
    }

    private static void drawSlotGrid(GuiGraphics graphics, int x, int y, int columns, int rows) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                drawSlot(graphics, x + column * 18 - 1, y + row * 18 - 1);
            }
        }
    }

    private static void drawSlot(GuiGraphics graphics, int x, int y) {
        graphics.fill(x, y, x + 18, y + 1, 0xFF373737);
        graphics.fill(x, y, x + 1, y + 18, 0xFF373737);
        graphics.fill(x + 1, y + 1, x + 17, y + 17, 0xFF8B8B8B);
        graphics.fill(x + 17, y + 1, x + 18, y + 17, 0xFFFFFFFF);
        graphics.fill(x + 1, y + 17, x + 18, y + 18, 0xFFFFFFFF);
        graphics.fill(x + 17, y, x + 18, y + 1, 0xFF8B8B8B);
        graphics.fill(x, y + 17, x + 1, y + 18, 0xFF8B8B8B);
    }

    private void renderSlotIcons(GuiGraphics graphics, int left, int top) {
        ResourceLocation[] armorIcons = { EMPTY_HELMET, EMPTY_CHESTPLATE, EMPTY_LEGGINGS, EMPTY_BOOTS };
        for (int i = 0; i < armorIcons.length; i++) {
            if (isMenuSlotEmpty(this.menu.getSlotArmorFirst() + i)) {
                blitSlotIcon(graphics,
                        left + PlayerMobServantInventoryMenu.ARMOR_X,
                        top + PlayerMobServantInventoryMenu.STORAGE_Y + i * 18,
                        armorIcons[i]);
            }
        }
        if (isMenuSlotEmpty(this.menu.getSlotHandFirst())) {
            blitSlotIcon(graphics,
                    left + PlayerMobServantInventoryMenu.HAND_X,
                    top + this.menu.getHandY(), EMPTY_SWORD);
        }
        if (isMenuSlotEmpty(this.menu.getSlotHandFirst() + 1)) {
            blitSlotIcon(graphics,
                    left + PlayerMobServantInventoryMenu.HAND_X + 18,
                    top + this.menu.getHandY(), EMPTY_SHIELD);
        }
    }

    private boolean isMenuSlotEmpty(int menuIndex) {
        return menuIndex >= this.menu.slots.size() || this.menu.slots.get(menuIndex).getItem().isEmpty();
    }

    private static void blitSlotIcon(GuiGraphics graphics, int x, int y, ResourceLocation icon) {
        graphics.blit(icon, x, y, 0.0F, 0.0F, 16, 16, 16, 16);
    }

    private static void drawPanel(GuiGraphics graphics, int x, int y, int width, int height, int color) {
        graphics.fill(x, y, x + width, y + 1, 0xFFFFFFFF);
        graphics.fill(x, y, x + 1, y + height, 0xFFFFFFFF);
        graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, color);
        graphics.fill(x + width - 1, y, x + width, y + height, 0xFF555555);
        graphics.fill(x, y + height - 1, x + width, y + height, 0xFF555555);
    }

    private void renderServantPreview(GuiGraphics graphics, int x, int y, int mouseX, int mouseY) {
        PlayerMobServant servant = this.menu.getServant();
        if (servant != null) {
            InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, x, y + 65, 27,
                    x - mouseX, y + 32.0F - mouseY, servant);
        }
    }

    private void renderHungerBar(GuiGraphics graphics, int x, int y) {
        @Nullable
        PlayerMobServant servant = this.menu.getServant();
        int foodLevel = servant == null ? 20 : servant.getGuiFoodLevel();
        for (int i = 0; i < 10; i++) {
            int sx = x + i * 8;
            graphics.blit(ICONS, sx, y, 16, 27, 9, 9);
            if (foodLevel >= (i + 1) * 2) {
                graphics.blit(ICONS, sx, y, 52, 27, 9, 9);
            } else if (foodLevel == (i + 1) * 2 - 1) {
                graphics.blit(ICONS, sx, y, 61, 27, 9, 9);
            }
        }
    }

    @Override
    public void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
        graphics.drawString(this.font, armorLabel, 31, 84, 0x404040, false);
        graphics.drawString(this.font, storageLabel, 70, 9, 0x404040, false);
        graphics.drawString(this.font, handsLabel, 112, this.menu.getHandY() + 3, 0x404040, false);
        @Nullable
        PlayerMobServant servant = this.menu.getServant();
        if (servant != null) {
            Component saturation = Component.translatable(
                    "container.goety_supreme.player_mob_inventory.saturation",
                    String.format(Locale.ROOT, "%.1f", servant.getGuiSaturation()));
            graphics.drawString(this.font, saturation, 112, this.menu.getHandY() + 14, 0x404040, false);
        }
    }
}
