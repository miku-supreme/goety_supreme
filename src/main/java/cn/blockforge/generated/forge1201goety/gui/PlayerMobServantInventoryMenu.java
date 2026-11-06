package cn.blockforge.generated.goetysupreme.gui;

import java.util.function.Predicate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.util.GoetyStaffGuard;
import cn.blockforge.generated.goetysupreme.util.ModMenus;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.gui.PlayerMobServantInventoryMenu;

public final class PlayerMobServantInventoryMenu extends AbstractContainerMenu {

    public static final int STORAGE_SIZE = 10;

    public static final int ARCANE_STORAGE_SIZE = 15;

    public static final int STORAGE_X = 70;
    public static final int STORAGE_Y = 22;
    public static final int ARMOR_X = 8;
    public static final int HAND_X = 70;

    private static final int STORAGE_COLUMNS = 5;

    private final Inventory playerInventory;
    @Nullable
    private final PlayerMobServant servant;

    private final int storageSize;
    private final int slotArmorFirst;
    private final int slotHandFirst;
    private final int slotPlayerFirst;

    public PlayerMobServantInventoryMenu(int windowId, Inventory playerInventory, PlayerMobServant servant) {
        super(ModMenus.PLAYER_MOB_INVENTORY.get(), windowId);
        this.playerInventory = playerInventory;
        this.servant = servant;
        this.storageSize = servant == null ? STORAGE_SIZE : servant.getActiveStorageSize();
        SimpleContainer storage = servant == null
                ? new SimpleContainer(ARCANE_STORAGE_SIZE)
                : servant.getServantStorage();
        EquipmentSlot[] armorOrder = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS,
                EquipmentSlot.FEET};

        int rows = this.getStorageRows();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < STORAGE_COLUMNS; col++) {
                addSlot(new Slot(storage, row * STORAGE_COLUMNS + col,
                        STORAGE_X + col * 18, STORAGE_Y + row * 18));
            }
        }
        this.slotArmorFirst = this.storageSize;
        if (servant != null) {
            for (int i = 0; i < 4; i++) {
                EquipmentSlot slot = armorOrder[i];
                addSlot(new MobEquipSlot(servant, slot, ARMOR_X, STORAGE_Y + i * 18,
                        stack -> isArmorForSlot(stack, slot)));
            }
            addSlot(new MobEquipSlot(servant, EquipmentSlot.MAINHAND, HAND_X, this.getHandY(),
                    stack -> !isArmor(stack)));
            addSlot(new MobEquipSlot(servant, EquipmentSlot.OFFHAND, HAND_X + 18, this.getHandY(),
                    stack -> !isArmor(stack)));
        } else {
            SimpleContainer fallback = new SimpleContainer(6);
            for (int i = 0; i < 4; i++) {
                addSlot(new Slot(fallback, i, ARMOR_X, STORAGE_Y + i * 18));
            }
            addSlot(new Slot(fallback, 4, HAND_X, this.getHandY()));
            addSlot(new Slot(fallback, 5, HAND_X + 18, this.getHandY()));
        }
        this.slotHandFirst = this.slotArmorFirst + 4;
        this.slotPlayerFirst = this.slotHandFirst + 2;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        8 + col * 18, this.getPlayerInvY() + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, this.getHotbarY()));
        }
    }

    public int getStorageSize() {
        return this.storageSize;
    }

    public int getStorageRows() {
        return this.storageSize / STORAGE_COLUMNS;
    }

    public int getSlotArmorFirst() {
        return this.slotArmorFirst;
    }

    public int getSlotHandFirst() {
        return this.slotHandFirst;
    }

    public int getHandY() {
        return STORAGE_Y + this.getStorageRows() * 18 + 6;
    }

    public int getPlayerInvY() {
        return this.getHandY() + 40;
    }

    public int getHotbarY() {
        return this.getPlayerInvY() + 58;
    }

    public int getImageHeight() {
        return this.getHotbarY() + 26;
    }

    public int getInventoryLabelY() {
        return this.getPlayerInvY() - 8;
    }

    public PlayerMobServantInventoryMenu(int windowId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(windowId, playerInventory, resolveServant(playerInventory, buf.readInt()));
    }

    @Nullable
    private static PlayerMobServant resolveServant(Inventory playerInventory, int entityId) {
        Player player = playerInventory.player;
        if (player.level().isClientSide) {
            net.minecraft.world.entity.Entity entity = player.level().getEntity(entityId);
            return entity instanceof PlayerMobServant servant ? servant : null;
        }
        return null;
    }

    @Nullable
    public PlayerMobServant getServant() {
        return servant;
    }

    @Override
    public boolean stillValid(Player player) {
        if (servant == null) {
            return false;
        }
        return servant.isAlive() && !servant.isRemoved()
                && player.distanceToSqr(servant) <= 64.0D;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (!slot.hasItem()) {
            return result;
        }
        ItemStack stack = slot.getItem();
        result = stack.copy();
        if (index < this.slotPlayerFirst) {
            if (!moveItemStackTo(stack, this.slotPlayerFirst, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!tryMoveToServant(stack)) {
            return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return result;
    }

    private boolean tryMoveToServant(ItemStack stack) {
        if (servant != null) {
            if (GoetyStaffGuard.isGoetyStaff(stack)) {
                if (servant.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()
                        && moveItemStackTo(stack, this.slotHandFirst, this.slotHandFirst + 1, false)) {
                    return true;
                }
                if (servant.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty()
                        && moveItemStackTo(stack, this.slotHandFirst + 1, this.slotHandFirst + 2, false)) {
                    return true;
                }
                return moveItemStackTo(stack, 0, this.storageSize, false);
            }
            EquipmentSlot slot = armorSlotFor(stack);
            if (slot != null && servant.getItemBySlot(slot).isEmpty()) {
                int menuIndex = armorMenuIndex(slot);
                if (menuIndex >= 0 && moveItemStackTo(stack, menuIndex, menuIndex + 1, false)) {
                    return true;
                }
            }
        }
        return moveItemStackTo(stack, 0, this.storageSize, false);
    }

    private int armorMenuIndex(EquipmentSlot slot) {
        switch (slot) {
            case HEAD:
                return this.slotArmorFirst;
            case CHEST:
                return this.slotArmorFirst + 1;
            case LEGS:
                return this.slotArmorFirst + 2;
            case FEET:
                return this.slotArmorFirst + 3;
            default:
                return -1;
        }
    }

    @Nullable
    private static EquipmentSlot armorSlotFor(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ArmorItem armor)) {
            return null;
        }
        EquipmentSlot slot = armor.getEquipmentSlot();
        return slot.getType() == EquipmentSlot.Type.ARMOR ? slot : null;
    }

    private static boolean isArmorForSlot(ItemStack stack, EquipmentSlot target) {
        return target == armorSlotFor(stack);
    }

    private static boolean isArmor(ItemStack stack) {
        EquipmentSlot slot = armorSlotFor(stack);
        return slot != null;
    }

    private static final class MobEquipSlot extends Slot {
        private final PlayerMobServant servant;
        private final EquipmentSlot equipmentSlot;
        private final Predicate<ItemStack> allowed;

        private MobEquipSlot(PlayerMobServant servant, EquipmentSlot equipmentSlot, int x, int y,
                Predicate<ItemStack> allowed) {
            super(new SimpleContainer(1), 0, x, y);
            this.servant = servant;
            this.equipmentSlot = equipmentSlot;
            this.allowed = allowed;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return allowed.test(stack);
        }

        @Override
        public boolean mayPickup(Player player) {
            return true;
        }

        @Override
        public ItemStack getItem() {
            return servant.getItemBySlot(equipmentSlot);
        }

        @Override
        public void set(ItemStack stack) {
            servant.setItemSlot(equipmentSlot, stack);
            if (!stack.isEmpty()) {
                servant.markEquipDropOnDeath(equipmentSlot);
            }
            setChanged();
        }

        @Override
        public ItemStack remove(int amount) {
            ItemStack equipped = servant.getItemBySlot(equipmentSlot);
            if (equipped.isEmpty() || amount <= 0) {
                return ItemStack.EMPTY;
            }
            ItemStack removed = equipped.split(amount);
            servant.setItemSlot(equipmentSlot, equipped);
            return removed;
        }

        @Override
        public void setChanged() {
        }

        @Override
        public int getMaxStackSize() {
            return equipmentSlot.getType() == EquipmentSlot.Type.ARMOR ? 1 : 64;
        }
    }
}
