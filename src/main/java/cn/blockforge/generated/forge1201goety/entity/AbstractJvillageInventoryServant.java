package cn.blockforge.generated.goetysupreme.entity;

import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.util.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractJvillageInventoryServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public abstract class AbstractJvillageInventoryServant extends AbstractJerotesServant
        implements InventoryEntity {

    public static final int INVENTORY_COUNT = 8;

    private static final EntityDataAccessor<Boolean> DATA_CAN_CHANGE_INVENTORY =
            SynchedEntityData.defineId(AbstractJvillageInventoryServant.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CAN_CHANGE_MELEE_OR_RANGE =
            SynchedEntityData.defineId(AbstractJvillageInventoryServant.class, EntityDataSerializers.BOOLEAN);

    private final SimpleContainer servantInventory = new SimpleContainer(INVENTORY_COUNT);

    private boolean inventoryDropped;

    public AbstractJvillageInventoryServant(
            EntityType<? extends AbstractJvillageInventoryServant> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_CAN_CHANGE_INVENTORY, Boolean.TRUE);
        entityData.define(DATA_CAN_CHANGE_MELEE_OR_RANGE, Boolean.TRUE);
    }

    @Override
    public SimpleContainer mobInventory() {
        return servantInventory;
    }

    @Override
    public int inventoryCount() {
        return INVENTORY_COUNT;
    }

    @Override
    public boolean isCanChangeInventory() {
        return entityData.get(DATA_CAN_CHANGE_INVENTORY);
    }

    @Override
    public void setCanChangeInventory(boolean value) {
        entityData.set(DATA_CAN_CHANGE_INVENTORY, value);
    }

    @Override
    public boolean isCanChangeMeleeOrRange() {
        return entityData.get(DATA_CAN_CHANGE_MELEE_OR_RANGE);
    }

    @Override
    public void setCanChangeMeleeOrRange(boolean value) {
        entityData.set(DATA_CAN_CHANGE_MELEE_OR_RANGE, value);
    }

    public boolean openServantInventory(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND || !player.isSecondaryUseActive()
                || !player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty()
                || !isAlive()) {
            return false;
        }
        if (!level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
            Main.openMobInventoryGui(serverPlayer, this, false, true,
                    true, true, true, true, true, true);
        }
        return true;
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!level().isClientSide() && !inventoryDropped) {
            inventoryDropped = true;
            Containers.dropContents(level(), this, servantInventory);
            servantInventory.clearContent();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("ServantInventory", servantInventory.createTag());
        tag.putBoolean("CanChangeInventory", isCanChangeInventory());
        tag.putBoolean("CanChangeMeleeOrRange", isCanChangeMeleeOrRange());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("ServantInventory")) {
            servantInventory.fromTag(tag.getList("ServantInventory", Tag.TAG_COMPOUND));
        }
        if (tag.contains("CanChangeInventory")) {
            setCanChangeInventory(tag.getBoolean("CanChangeInventory"));
        }
        if (tag.contains("CanChangeMeleeOrRange")) {
            setCanChangeMeleeOrRange(tag.getBoolean("CanChangeMeleeOrRange"));
        }
    }
}
