package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.neutral.Wartling;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.MathHelper;
import java.util.function.Predicate;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantGoals;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;

final class PlayerMobServantStorage {

    private final PlayerMobServant mob;

    PlayerMobServantStorage(PlayerMobServant mob) {
        this.mob = mob;
    }

    public SimpleContainer getServantStorage() {
        return mob.servantStorage;
    }

    public void lockArcaneEquipmentDrops() {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            mob.setDropChance(slot, 0.0F);
        }
    }

    public int getActiveStorageSize() {
        return mob.hasArcaneScroll() ? PlayerMobServant.ARCANE_STORAGE_SIZE : PlayerMobServant.STORAGE_SIZE;
    }

    private static final int WARLOCK_EGG_INTERVAL = 60;

    void tickArcaneScroll() {
        if ((mob.tickCount & 3) == 0 && mob.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.ENCHANT, mob.getRandomX(0.9D),
                    mob.getY() + 0.3D + mob.getRandom().nextDouble() * mob.getBbHeight(),
                    mob.getRandomZ(0.9D), 3, 0.0D, 0.02D, 0.0D, 0.01D);
        }
        if (mob.tickCount % WARLOCK_EGG_INTERVAL == 0) {
            this.tryClearDebuffWithWartEgg();
        }
    }

    private void tryClearDebuffWithWartEgg() {
        LivingEntity owner = mob.getTrueOwner();
        if (owner == null || owner.isRemoved() || owner.level() != mob.level()) {
            return;
        }
        boolean robe = CuriosFinder.hasWarlockRobe(owner);
        if (!robe && !CuriosFinder.hasCurio(owner, ModItems.WARLOCK_SASH.get())) {
            return;
        }
        MobEffectInstance harmful = null;
        for (MobEffectInstance instance : mob.getActiveEffects()) {
            if (instance.getEffect().getCategory() == MobEffectCategory.HARMFUL
                    && !instance.getEffect().getCurativeItems().isEmpty()) {
                harmful = instance;
                break;
            }
        }
        if (harmful == null) {
            return;
        }
        int eggSlot = -1;
        for (int i = 0; i < mob.servantStorage.getContainerSize(); i++) {
            if (mob.servantStorage.getItem(i).is(ModItems.WARTFUL_EGG.get())) {
                eggSlot = i;
                break;
            }
        }
        if (eggSlot < 0 || !(mob.level() instanceof ServerLevel server)) {
            return;
        }
        mob.removeEffect(harmful.getEffect());
        Wartling wartling = new Wartling(ModEntityType.WARTLING.get(), server);
        wartling.setTrueOwner(mob);
        wartling.setLimitedLife(MathHelper.secondsToTicks(9));
        wartling.moveTo(mob.blockPosition(), mob.getYRot(), mob.getXRot());
        wartling.setStoredEffect(harmful);
        wartling.finalizeSpawn(server, server.getCurrentDifficultyAt(mob.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        server.addFreshEntity(wartling);
        if (!(robe && mob.getRandom().nextFloat() < 0.1F)) {
            mob.servantStorage.removeItem(eggSlot, 1);
        }
        mob.playSound(SoundEvents.GENERIC_DRINK, 0.8F, 0.8F + mob.getRandom().nextFloat() * 0.4F);
        if (mob.level() instanceof ServerLevel server2) {
            server2.sendParticles(ParticleTypes.WITCH, mob.getX(),
                    mob.getY() + mob.getBbHeight() * 0.5D, mob.getZ(),
                    12, 0.4D, 0.5D, 0.4D, 0.05D);
        }
    }

    public boolean addStackToStorage(ItemStack stack) {
        if (stack.isEmpty()) {
            return true;
        }
        int max = stack.getMaxStackSize();
        for (int i = 0; i < mob.servantStorage.getContainerSize() && !stack.isEmpty(); i++) {
            ItemStack current = mob.servantStorage.getItem(i);
            if (!current.isEmpty() && ItemStack.isSameItemSameTags(current, stack)
                    && current.getCount() < max) {
                int move = Math.min(max - current.getCount(), stack.getCount());
                current.grow(move);
                stack.shrink(move);
            }
        }
        for (int i = 0; i < mob.servantStorage.getContainerSize(); i++) {
            if (stack.isEmpty()) {
                return true;
            }
            if (mob.servantStorage.getItem(i).isEmpty()) {
                mob.servantStorage.setItem(i, stack.split(stack.getMaxStackSize()));
            }
        }
        return stack.isEmpty();
    }

    public boolean hasStorageRoom() {
        for (int i = 0; i < mob.servantStorage.getContainerSize(); i++) {
            ItemStack current = mob.servantStorage.getItem(i);
            if (current.isEmpty() || current.getCount() < current.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    public int storageRoomFor(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        int max = stack.getMaxStackSize();
        int room = 0;
        for (int i = 0; i < mob.servantStorage.getContainerSize(); i++) {
            ItemStack current = mob.servantStorage.getItem(i);
            if (current.isEmpty()) {
                room += max;
            } else if (ItemStack.isSameItemSameTags(current, stack) && current.getCount() < max) {
                room += max - current.getCount();
            }
        }
        return room;
    }

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET,
    };

    public void tryUpgradeGearFromStorage() {
        if (mob.level().isClientSide || mob.eatingBite) {
            return;
        }
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            int best = -1;
            double bestScore = 0.0D;
            for (int i = 0; i < mob.servantStorage.getContainerSize(); i++) {
                ItemStack candidate = mob.servantStorage.getItem(i);
                if (PlayerMobServantGoals.PlayerMobPickupGoal.armorSlotFor(candidate) != slot) {
                    continue;
                }
                double score = PlayerMobServantGoals.PlayerMobPickupGoal.armorScore(candidate, slot);
                if (score > bestScore) {
                    bestScore = score;
                    best = i;
                }
            }
            if (best < 0) {
                continue;
            }
            ItemStack worn = mob.getItemBySlot(slot);
            if (!worn.isEmpty() && bestScore <= PlayerMobServantGoals.PlayerMobPickupGoal.armorScore(worn, slot) + 0.01D) {
                continue;
            }
            swapStorageWithEquipment(best, slot);
        }
        upgradeWeaponFromStorage();
        equipShieldFromStorage();
        equipThrowableFromStorage();
    }

    private void equipThrowableFromStorage() {
        if (mob.isWandCasterSetup()) {
            return;
        }
        if (mob.isThrowableStack(mob.getMainHandItem()) || mob.isThrowableStack(mob.getOffhandItem())) {
            return;
        }
        int slot = -1;
        for (int i = 0; i < mob.servantStorage.getContainerSize(); i++) {
            if (mob.isThrowableStack(mob.servantStorage.getItem(i))) {
                slot = i;
                break;
            }
        }
        if (slot < 0) {
            return;
        }
        if (mob.getMainHandItem().isEmpty()) {
            swapStorageWithEquipment(slot, EquipmentSlot.MAINHAND);
        } else if (mob.getOffhandItem().isEmpty()) {
            swapStorageWithEquipment(slot, EquipmentSlot.OFFHAND);
        } else if (!mob.isThrowMeleeWeapon(mob.getMainHandItem())) {
            swapStorageWithEquipment(slot, EquipmentSlot.MAINHAND);
        }
    }

    private void upgradeWeaponFromStorage() {
        ItemStack held = mob.getMainHandItem();
        if (mob.isWandCasterSetup()) {
            return;
        }
        if (mob.isUsingItem() && !held.isEmpty()) {
            return;
        }
        int heldClass = PlayerMobServantGoals.PlayerMobPickupGoal.weaponClassOf(held);
        int best = -1;
        double bestScore = 0.0D;
        for (int i = 0; i < mob.servantStorage.getContainerSize(); i++) {
            ItemStack candidate = mob.servantStorage.getItem(i);
            int candidateClass = PlayerMobServantGoals.PlayerMobPickupGoal.weaponClassOf(candidate);
            if (candidateClass < 0) {
                continue;
            }
            if (heldClass >= 0 && candidateClass != heldClass) {
                continue;
            }
            double score = PlayerMobServantGoals.PlayerMobPickupGoal.weaponScore(candidate);
            if (score > bestScore) {
                bestScore = score;
                best = i;
            }
        }
        if (best < 0) {
            return;
        }
        if (heldClass >= 0
                && bestScore <= PlayerMobServantGoals.PlayerMobPickupGoal.weaponScore(held) + 0.01D) {
            return;
        }
        swapStorageWithEquipment(best, EquipmentSlot.MAINHAND);
    }

    void swapStorageWithEquipment(int storageIndex, EquipmentSlot slot) {
        ItemStack stored = mob.servantStorage.getItem(storageIndex);
        if (stored.isEmpty()) {
            return;
        }
        ItemStack moved = mob.servantStorage.removeItem(storageIndex, 1);
        ItemStack previous = mob.getItemBySlot(slot);
        mob.setItemSlot(slot, moved);
        mob.markEquipDropOnDeath(slot);
        if (!previous.isEmpty() && !RingServantGear.isRingGear(previous)) {
            mob.servantStorage.setItem(storageIndex, previous);
        }
        Equipable equipable = Equipable.get(moved);
        mob.playSound(equipable == null ? SoundEvents.ARMOR_EQUIP_GENERIC
                : equipable.getEquipSound(), 0.6F, 1.0F);
    }

    void swapEquipmentWithStorage(EquipmentSlot slot, int storageIndex) {
        ItemStack held = mob.getItemBySlot(slot).copy();
        ItemStack stored = mob.servantStorage.getItem(storageIndex).copy();
        mob.setItemSlot(slot, stored);
        mob.markEquipDropOnDeath(slot);
        mob.servantStorage.setItem(storageIndex, held);
    }

    int findStorageIndex(Predicate<ItemStack> predicate) {
        for (int i = 0; i < mob.servantStorage.getContainerSize(); i++) {
            if (predicate.test(mob.servantStorage.getItem(i))) {
                return i;
            }
        }
        return -1;
    }

    private void equipShieldFromStorage() {
        if (!mob.getOffhandItem().isEmpty() || mob.isTwoHandedBlockConfiguration()) {
            return;
        }
        int slot = findStorageIndex(PlayerMobServant::isShieldItem);
        if (slot >= 0) {
            swapStorageWithEquipment(slot, EquipmentSlot.OFFHAND);
        }
    }

}
