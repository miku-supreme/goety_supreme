package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal;
import cn.blockforge.generated.goetysupreme.util.GoetyStaffGuard;
import cn.blockforge.generated.goetysupreme.util.PlayerMobWandCasting;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

final class PlayerMobWandMode {

    private static final int WEAKENING_INTERLUDE_LIMIT_TICKS = 80;
    private static final int HEAL_INTERLUDE_LIMIT_TICKS = 80;

    private final PlayerMobServant mob;

    PlayerMobWandMode(PlayerMobServant mob) {
        this.mob = mob;
    }

    public boolean isMeleeWandMode() {
        return mob.meleeWandMode;
    }

    public boolean isWeakeningInterlude() {
        return mob.weakeningInterlude;
    }

    public boolean isHealInterlude() {
        return mob.healInterlude;
    }

    boolean isSupportOnlyWandSetup() {
        if (!GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem()) || !PlayerMobServant.isMeleeWeapon(mob.getMainHandItem())) {
            return false;
        }
        return PlayerMobWandCasting.isSupportOnlyCatalog(mob, mob.getOffhandItem());
    }

    void enterMeleeWandMode() {
        if (mob.meleeWandMode) {
            return;
        }
        mob.meleeWandMode = true;
        if (GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            int shieldSlot = mob.storage.findStorageIndex(PlayerMobServant::isShieldItem);
            if (shieldSlot >= 0) {
                mob.storage.swapStorageWithEquipment(shieldSlot, EquipmentSlot.OFFHAND);
                mob.meleeWandStashed = true;
            }
        }
        mob.stopWandShield();
        mob.setCastingHand(-1);
    }

    void leaveMeleeWandMode() {
        if (!mob.meleeWandMode) {
            return;
        }
        if (mob.healInterlude) {
            finishHealInterlude();
        }
        if (mob.weakeningInterlude) {
            finishWeakeningInterlude();
        }
        if (mob.meleeWandStashed) {
            int wandSlot = mob.storage.findStorageIndex(GoetyStaffGuard::isGoetyStaff);
            if (wandSlot >= 0) {
                mob.storage.swapStorageWithEquipment(wandSlot, EquipmentSlot.OFFHAND);
            }
            mob.meleeWandStashed = false;
        }
        mob.meleeWandMode = false;
    }

    void tickMeleeWandMode() {
        if (mob.meleeWandMode && mob.getTarget() == null) {
            this.leaveMeleeWandMode();
        }
    }

    void tickHealInterlude() {
        if (mob.healInterlude) {
            if (!mob.isCastingWand() && (mob.fleeEatActive || mob.eatingBite || mob.isUsingFood())) {
                finishHealInterlude();
                return;
            }
            if (--mob.healInterludeTicks <= 0 && !mob.isCastingWand()) {
                finishHealInterlude();
            }
            return;
        }
        if (mob.weakeningInterlude) {
            return;
        }
        if (mob.meleeWandStashed && GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            finishHealInterlude();
            return;
        }
        if ((mob.tickCount & 1) != 0 || !mob.hasCastableHeal()) {
            return;
        }
        if (mob.eatingBite || mob.fleeEatActive || mob.canStartEatBite()) {
            return;
        }
        if (this.canHealInCurrentLayout()) {
            this.startHealInterlude();
        }
    }

    private boolean canHealInCurrentLayout() {
        return mob.meleeWandMode
                || GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())
                || (!GoetyStaffGuard.isGoetyStaff(mob.getMainHandItem())
                        && mob.storage.findStorageIndex(GoetyStaffGuard::isGoetyStaff) >= 0);
    }

    void startHealInterlude() {
        if (mob.meleeWandMode && mob.meleeWandStashed) {
            int wandSlot = mob.storage.findStorageIndex(GoetyStaffGuard::isGoetyStaff);
            if (wandSlot < 0) {
                return;
            }
            mob.storage.swapStorageWithEquipment(wandSlot, EquipmentSlot.OFFHAND);
        } else if (!GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            int wandSlot = mob.storage.findStorageIndex(GoetyStaffGuard::isGoetyStaff);
            if (wandSlot < 0) {
                return;
            }
            mob.storage.swapEquipmentWithStorage(EquipmentSlot.OFFHAND, wandSlot);
            mob.healWandSwapSlot = wandSlot;
        }
        if (!GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            return;
        }
        mob.healInterlude = true;
        mob.healInterludeTicks = HEAL_INTERLUDE_LIMIT_TICKS;
    }

    public void finishHealInterlude() {
        mob.healInterlude = false;
        mob.healInterludeTicks = 0;
        mob.stopWandShield();
        if (mob.meleeWandStashed && GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            int shieldSlot = mob.storage.findStorageIndex(PlayerMobServant::isShieldItem);
            if (shieldSlot >= 0) {
                mob.storage.swapStorageWithEquipment(shieldSlot, EquipmentSlot.OFFHAND);
            }
        } else if (mob.healWandSwapSlot >= 0 && GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            mob.storage.swapEquipmentWithStorage(EquipmentSlot.OFFHAND, mob.healWandSwapSlot);
        }
        mob.healWandSwapSlot = -1;
    }

    public boolean hasCastableHeal() {
        return PlayerMobWandSpellGoal.canCastHealNow(mob);
    }

    void tickWeakeningInterlude() {
        if (mob.weakeningInterlude) {
            if (--mob.weakeningInterludeTicks <= 0 && !mob.isCastingWand()) {
                finishWeakeningInterlude();
            }
            return;
        }
        if (mob.healInterlude) {
            return;
        }
        if (mob.meleeWandStashed && GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            finishWeakeningInterlude();
            return;
        }
        if (mob.meleeWandMode && (mob.tickCount & 3) == 0 && this.hasReadyWeakening()) {
            this.startWeakeningInterlude();
        }
    }

    void startWeakeningInterlude() {
        if (mob.meleeWandMode && mob.meleeWandStashed) {
            int wandSlot = mob.storage.findStorageIndex(GoetyStaffGuard::isGoetyStaff);
            if (wandSlot < 0) {
                return;
            }
            mob.storage.swapStorageWithEquipment(wandSlot, EquipmentSlot.OFFHAND);
        }
        if (!GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            return;
        }
        mob.weakeningInterlude = true;
        mob.weakeningInterludeTicks = WEAKENING_INTERLUDE_LIMIT_TICKS;
    }

    public void finishWeakeningInterlude() {
        mob.weakeningInterlude = false;
        mob.weakeningInterludeTicks = 0;
        mob.stopWandShield();
        if (mob.meleeWandStashed && GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())) {
            int shieldSlot = mob.storage.findStorageIndex(PlayerMobServant::isShieldItem);
            if (shieldSlot >= 0) {
                mob.storage.swapStorageWithEquipment(shieldSlot, EquipmentSlot.OFFHAND);
            }
        }
    }

    private boolean hasReadyWeakening() {
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive() || !PlayerMobWandCasting.weakenNeeded(target)) {
            return false;
        }
        ItemStack wand = GoetyStaffGuard.isGoetyStaff(mob.getOffhandItem())
                ? mob.getOffhandItem() : meleeWandStack();
        if (wand.isEmpty()) {
            return false;
        }
        for (PlayerMobWandCasting.FocusEntry entry : PlayerMobWandCasting.catalog(mob, wand)) {
            if (entry.isWeakening() && !mob.isWandFocusOnCooldown(entry.itemId())
                    && mob.distanceTo(target) <= PlayerMobWandCasting.focusRadius(mob, entry)) {
                return true;
            }
        }
        return false;
    }

    public ItemStack meleeWandStack() {
        int slot = mob.storage.findStorageIndex(GoetyStaffGuard::isGoetyStaff);
        return slot < 0 ? ItemStack.EMPTY : mob.servantStorage.getItem(slot);
    }
}
