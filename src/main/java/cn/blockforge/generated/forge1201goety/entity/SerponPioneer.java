package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BlockEntityType;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.entity.SerponPioneer;

public class SerponPioneer extends AbstractSerpon {

    @Override
    public boolean acceptsEquipment(net.minecraft.world.item.ItemStack stack) {
        return isAnyJerotesItem(stack, "serpon_chestplate", "serpon_leggings", "serpon_boots")
                || isAnyJerotesItem(stack, SERPON_MELEE_WEAPONS);
    }

    private static final double FOLLOW_RANGE = 32.0D;

    public SerponPioneer(EntityType<? extends AbstractSerpon> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.37D)
                .add(Attributes.FLYING_SPEED, 0.55D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/serpon/serpon_pioneer.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SERPON_PIONEER;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public double getAttackReachSqr(LivingEntity target) {
                return SerponPioneer.this.meleeReachSqr(target);
            }
        });
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        setItemSlot(EquipmentSlot.HEAD, serponCombatTeamBanner());
        equipJerotes(EquipmentSlot.CHEST, "serpon_chestplate");
        if (wearsRingDressedSerponArmor()) {
            equipRingSerponArmor(EquipmentSlot.LEGS, "serpon_leggings");
            equipRingSerponArmor(EquipmentSlot.FEET, "serpon_boots");
        }
        equipJerotes(EquipmentSlot.MAINHAND, "serpon_sword");
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            setDropChance(slot, 0.0F);
        }
    }

    public static ItemStack serponCombatTeamBanner() {
        ItemStack stack = new ItemStack(Items.YELLOW_BANNER);
        CompoundTag tag = new CompoundTag();
        ListTag patterns = new BannerPattern.Builder()
                .addPattern(BannerPatterns.CURLY_BORDER, DyeColor.RED)
                .addPattern(BannerPatterns.STRIPE_SMALL, DyeColor.ORANGE)
                .addPattern(BannerPatterns.TRIANGLES_BOTTOM, DyeColor.WHITE)
                .addPattern(BannerPatterns.TRIANGLES_TOP, DyeColor.WHITE)
                .addPattern(BannerPatterns.STRIPE_CENTER, DyeColor.YELLOW)
                .addPattern(BannerPatterns.FLOWER, DyeColor.RED)
                .addPattern(BannerPatterns.STRAIGHT_CROSS, DyeColor.YELLOW)
                .toListTag();
        tag.put("Patterns", patterns);
        BlockItem.setBlockEntityData(stack, BlockEntityType.BANNER, tag);
        stack.setHoverName(Component.translatable("block.goety_supreme.serpon_combat_team_banner")
                .withStyle(ChatFormatting.GOLD));
        return stack;
    }
}
