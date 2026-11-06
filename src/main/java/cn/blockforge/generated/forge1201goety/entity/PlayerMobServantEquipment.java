package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;

final class PlayerMobServantEquipment {

    private final PlayerMobServant mob;
    private boolean equipmentConfigured;

    PlayerMobServantEquipment(PlayerMobServant mob) {
        this.mob = mob;
    }

    boolean isConfigured() {
        return this.equipmentConfigured;
    }

    void populateDefaultWeapons(RandomSource random, DifficultyInstance difficulty) {
        if (this.equipmentConfigured) {
            return;
        }
        this.equipmentConfigured = true;
        this.populateConfiguredWeapons(random, difficulty);
    }

    void applyRingArmorIfMissing(RandomSource random) {
        if (this.mob.getTrueOwner() == null || RingServantGear.wearsAnyArmor(this.mob)) {
            return;
        }
        RingServantGear.rollRingArmorOnly(this.mob, random);
    }

    private void populateConfiguredWeapons(RandomSource random, DifficultyInstance difficulty) {
        Player owner = RingServantGear.ownerChainPlayer(this.mob);
        boolean ringWorn = RingServantGear.handles(RingServantGear.ringOf(owner));
        boolean mainhandSpawned = false;
        if (PlayerMobServantConfig.FORCE_ITEMS_SPAWN.get()
                || random.nextFloat() < mainhandSpawnChance(difficulty)) {
            PlayerMobItemChoice choice = this.chooseWeightedItem(random, PlayerMobServantConfig.mainhandItems());
            if (choice != null) {
                ItemStack chosen = choice.stack();
                if (ringWorn) {
                    if (chosen.getItem() instanceof ProjectileWeaponItem) {
                        this.equipConfiguredItem(EquipmentSlot.MAINHAND, chosen);
                        mainhandSpawned = true;
                    } else {
                        ItemStack sword = RingServantGear.rollRingMeleeWeapon(random, owner);
                        if (!sword.isEmpty()) {
                            this.equipConfiguredItem(EquipmentSlot.MAINHAND, sword);
                            mainhandSpawned = true;
                        }
                    }
                } else {
                    this.equipConfiguredItem(EquipmentSlot.MAINHAND, chosen);
                    mainhandSpawned = true;
                }
            }
        } else if (ringWorn) {
            ItemStack sword = RingServantGear.rollRingMeleeWeapon(random, owner);
            if (!sword.isEmpty()) {
                this.equipConfiguredItem(EquipmentSlot.MAINHAND, sword);
                mainhandSpawned = true;
            }
        }

        if (ringWorn && carriesMelee(this.mob.getMainHandItem())) {
            this.equipConfiguredItem(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
            return;
        }
        this.populateConfiguredOffhand(random, difficulty, mainhandSpawned);
    }

    private static boolean carriesMelee(ItemStack stack) {
        return PlayerMobServant.isMeleeWeapon(stack) || PlayerMobServant.isTridentLike(stack);
    }

    private static float mainhandSpawnChance(DifficultyInstance difficulty) {
        return difficulty.getDifficulty() == Difficulty.HARD ? 0.5F : 0.1F;
    }

    private void populateConfiguredOffhand(RandomSource random, DifficultyInstance difficulty,
            boolean mainhandSpawned) {
        if (!mainhandSpawned || this.mob.getMainHandItem().getItem() instanceof ProjectileWeaponItem
                || difficulty.getDifficulty().getId()
                < PlayerMobServantConfig.OFFHAND_SPAWN_DIFFICULTY.get().getId()) {
            return;
        }

        double chance = PlayerMobServantConfig.OFFHAND_SPAWN_CHANCE.get();
        if (chance < 0.0D || random.nextDouble() >= chance) {
            return;
        }

        PlayerMobItemChoice choice = this.chooseWeightedItem(random, PlayerMobServantConfig.offhandItems());
        if (choice != null) {
            this.equipConfiguredItem(EquipmentSlot.OFFHAND, choice.stack());
        }
    }

    private PlayerMobItemChoice chooseWeightedItem(RandomSource random, List<? extends String> entries) {
        List<PlayerMobItemChoice> choices = new ArrayList<>();
        long totalWeight = 0L;
        for (String entry : entries) {
            PlayerMobServantConfig.WeightedItem weighted = PlayerMobServantConfig.parseWeightedItem(entry);
            PlayerMobItemChoice choice = resolveChoice(weighted);
            if (choice == null) {
                continue;
            }
            choices.add(choice);
            totalWeight = totalWeight > Long.MAX_VALUE - choice.weight()
                    ? Long.MAX_VALUE : totalWeight + choice.weight();
        }
        if (choices.isEmpty() || totalWeight <= 0L) {
            return null;
        }

        long selected = Math.floorMod(random.nextLong(), totalWeight);
        for (PlayerMobItemChoice choice : choices) {
            selected -= choice.weight();
            if (selected < 0L) {
                return new PlayerMobItemChoice(choice.stack().copy(), choice.weight());
            }
        }
        return null;
    }

    private static PlayerMobItemChoice resolveChoice(PlayerMobServantConfig.WeightedItem weighted) {
        if (weighted == null) {
            return null;
        }
        ResourceLocation location = ResourceLocation.tryParse(weighted.itemId());
        if (location == null || !BuiltInRegistries.ITEM.containsKey(location)) {
            return null;
        }
        Item item = BuiltInRegistries.ITEM.get(location);
        ItemStack stack = item == Items.AIR
                ? ItemStack.EMPTY : new ItemStack(item);
        return new PlayerMobItemChoice(stack, weighted.weight());
    }

    private void equipConfiguredItem(EquipmentSlot slot, ItemStack stack) {
        this.mob.setItemSlot(slot, stack.copy());
        this.mob.setDropChance(slot, 0.0F);
    }
}
