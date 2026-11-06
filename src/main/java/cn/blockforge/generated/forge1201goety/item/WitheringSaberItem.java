package cn.blockforge.generated.goetysupreme.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import cn.blockforge.generated.goetysupreme.item.HellfireSaberItem;
import cn.blockforge.generated.goetysupreme.item.WitheringSaberItem;

public final class WitheringSaberItem extends SwordItem {

    private static final int WITHER_TICKS = 20 * 5;

    private static final double AURA_RADIUS = 12.0D;

    private static final float HEAL_PER_TARGET = 0.5F;

    private static final float HEAL_CAP = 1.5F;

    public static final Tier TIER = new Tier() {
        @Override
        public int getUses() {
            return 2031;
        }

        @Override
        public float getSpeed() {
            return 1.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 6.0F;
        }

        @Override
        public int getLevel() {
            return 4;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public TagKey<Block> getTag() {
            return BlockTags.NEEDS_DIAMOND_TOOL;
        }
    };

    public WitheringSaberItem() {
        super(TIER, 3, -3.1F, new Item.Properties().rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, WITHER_TICKS, 0), attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }
        if (player.getMainHandItem() != stack && player.getOffhandItem() != stack) {
            return;
        }
        if (player.tickCount % 20 != 0) {
            return;
        }
        int withered = countWitheredNearby(player);
        if (withered > 0) {
            player.heal(Math.min(HEAL_CAP, HEAL_PER_TARGET * withered));
        }
    }

    private static int countWitheredNearby(Player player) {
        AABB area = player.getBoundingBox().inflate(AURA_RADIUS);
        int count = 0;
        for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (other != player && other.isAlive() && other.hasEffect(MobEffects.WITHER)) {
                count++;
            }
        }
        return count;
    }
}
