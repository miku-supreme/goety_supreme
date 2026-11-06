package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import cn.blockforge.generated.goetysupreme.entity.FlamoarServant;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;

public final class ServantFeedHeal {

    private static final TagKey<net.minecraft.world.item.Item> BLAZE_RODS =
            ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "rods/blaze"));

    public static boolean feed(Mob mob, Player player, ItemStack held, float amount) {
        if (amount <= 0.0F || mob.getHealth() >= mob.getMaxHealth()) {
            return false;
        }
        if (!mob.level().isClientSide()) {
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
            mob.heal(amount);
            mob.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            spawnHealParticles(mob);
        }
        return true;
    }

    public static boolean tryBlazeFeed(Mob mob, Player player, ItemStack held) {
        if (held.is(Items.BLAZE_POWDER)) {
            return feedBlaze(mob, player, held, 1.0F, false);
        }
        if (held.is(BLAZE_RODS)) {
            return feedBlaze(mob, player, held, 4.0F, true);
        }
        return false;
    }

    public static boolean tryFireChargeFeed(Mob mob, Player player, ItemStack held) {
        if (!held.is(Items.FIRE_CHARGE)) {
            return false;
        }
        return feedBlaze(mob, player, held, 4.0F, true);
    }

    private static boolean feedBlaze(Mob mob, Player player, ItemStack held, float amount, boolean rod) {
        if (mob.getHealth() >= mob.getMaxHealth()) {
            return false;
        }
        if (!mob.level().isClientSide()) {
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }
            mob.heal(amount);
            mob.playSound(SoundEvents.BLAZE_BURN, 1.0F, rod ? 1.0F : 1.25F);
            spawnHealParticles(mob);
        }
        return true;
    }

    private static void spawnHealParticles(Mob mob) {
        if (mob.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                    mob.getRandomX(1.0D), mob.getY() + 0.5D, mob.getRandomZ(1.0D),
                    7, 0.02D, 0.02D, 0.02D, 0.5D);
        }
    }

    private ServantFeedHeal() {
    }
}
