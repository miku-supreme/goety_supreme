package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.item.FellAxeItem;
import cn.blockforge.generated.goetysupreme.item.WitheringSaberItem;

public final class FellAxeItem extends AxeItem {

    private static final float ATTACK_DAMAGE = 9.5F;

    private static final float PLAYER_BASE_ATTACK = 1.0F;

    private static final float TIER_DAMAGE_BONUS = 3.0F;

    private static final float ATTACK_SPEED = -2.4F;

    private static final int DURABILITY = 256;

    private static final int BUSTED_TICKS = 100;

    private static final Tier TIER = new Tier() {
        @Override
        public int getUses() {
            return 1280;
        }

        @Override
        public float getSpeed() {
            return 8.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return TIER_DAMAGE_BONUS;
        }

        @Override
        public int getLevel() {
            return 3;
        }

        @Override
        public int getEnchantmentValue() {
            return 22;
        }

        @Override
        public Ingredient getRepairIngredient() {
            Item ingot = ForgeRegistries.ITEMS.getValue(
                    ResourceLocation.fromNamespaceAndPath("goety", "cursed_ingot"));
            return ingot == null ? Ingredient.EMPTY : Ingredient.of(ingot);
        }

        @Override
        public TagKey<Block> getTag() {
            return BlockTags.NEEDS_DIAMOND_TOOL;
        }
    };

    public FellAxeItem() {
        super(TIER, ATTACK_DAMAGE - PLAYER_BASE_ATTACK - TIER_DAMAGE_BONUS, ATTACK_SPEED,
                new Item.Properties().durability(DURABILITY));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide && attacker.getRandom().nextBoolean()) {
            target.addEffect(new MobEffectInstance(GoetyEffects.BUSTED.get(), BUSTED_TICKS), attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
