package cn.blockforge.generated.goetysupreme.entity;

import java.util.Map;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.entity.ServantThrownTrident;

public final class ServantThrownTrident extends ThrownTrident {

    private static final int GROUND_DESPAWN_TICKS = 200;
    private static final EntityDataAccessor<ItemStack> RENDER_ITEM =
            SynchedEntityData.defineId(ServantThrownTrident.class, EntityDataSerializers.ITEM_STACK);
    private ItemStack renderItem = ItemStack.EMPTY;

    public ServantThrownTrident(EntityType<? extends ThrownTrident> type, Level level) {
        super(type, level);
    }

    public ServantThrownTrident(Level level, LivingEntity owner, ItemStack tridentStack) {
        this(ModEntityTypes.SERVANT_THROWN_TRIDENT.get(), level);
        setOwner(owner);
        ItemStack copy = withoutLoyalty(tridentStack);
        renderItem = copy.copy();
        entityData.set(RENDER_ITEM, renderItem.copy());
        this.pickup = AbstractArrow.Pickup.DISALLOWED;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(RENDER_ITEM, ItemStack.EMPTY);
    }

    public ItemStack getRenderItem() {
        return renderItem.isEmpty() ? entityData.get(RENDER_ITEM) : renderItem;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (!getRenderItem().isEmpty()) {
            tag.put("RenderItem", getRenderItem().save(new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("RenderItem", CompoundTag.TAG_COMPOUND)) {
            renderItem = ItemStack.of(tag.getCompound("RenderItem"));
            entityData.set(RENDER_ITEM, renderItem.copy());
        }
    }

    private static ItemStack withoutLoyalty(ItemStack stack) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        if (!enchantments.containsKey(Enchantments.LOYALTY)) {
            return stack;
        }
        enchantments.remove(Enchantments.LOYALTY);
        ItemStack stripped = stack.copy();
        EnchantmentHelper.setEnchantments(enchantments, stripped);
        return stripped;
    }

    @Override
    public boolean canHitEntity(Entity target) {
        if (getOwner() instanceof PlayerMobServant servant && servant.isAlliedTo(target)) {
            return false;
        }
        if (getOwner() instanceof AbstractJerotesServant servant && servant.isAlliedTo(target)) {
            return false;
        }
        return super.canHitEntity(target);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && onGround()) {
            Vec3 motion = getDeltaMovement();
            if (motion.horizontalDistanceSqr() > 1.0E-4D) {
                setDeltaMovement(0.0D, motion.y, 0.0D);
            }
        }
    }

    @Override
    public void tickDespawn() {
        if (inGroundTime >= GROUND_DESPAWN_TICKS) {
            discard();
            return;
        }
        super.tickDespawn();
    }
}
