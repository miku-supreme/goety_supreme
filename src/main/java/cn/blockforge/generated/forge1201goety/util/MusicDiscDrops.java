package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.util.MusicDiscDrops;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.WildMage;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;

public final class MusicDiscDrops {

    private static final float BASE_CHANCE = 0.05F;

    private static final float LOOTING_BONUS = 0.02F;

    private static final float MAX_CHANCE = 0.15F;

    private MusicDiscDrops() {
    }

    public static void roll(LivingEntity dying, Item disc, int looting) {
        if (dying == null || disc == null || dying.level().isClientSide()) {
            return;
        }
        float chance = Math.min(MAX_CHANCE, BASE_CHANCE + LOOTING_BONUS * Math.max(0, looting));
        if (dying.getRandom().nextFloat() < chance) {
            dying.spawnAtLocation(new ItemStack(disc));
        }
    }
}
