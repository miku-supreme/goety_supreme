package cn.blockforge.generated.goetysupreme.util;

import java.util.function.IntConsumer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractWildMage;
import cn.blockforge.generated.goetysupreme.util.FocusSlotRules;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public final class FocusSlotRules {

    public static final int NECROMANCER_BASE_SLOTS = 1;

    public static final int GREEN_SMITH_BASE_SLOTS = 3;

    public static final int SLOTS_PER_CHEAT_SHEET = 2;

    public static final int MAX_CHEAT_SHEETS = 2;

    public static final String CHEAT_SHEETS_TAG = "GoetySupremeCheatSheets";

    private FocusSlotRules() {
    }

    public static int maxSlots(int baseSlots, int sheets) {
        int used = Math.max(0, Math.min(sheets, MAX_CHEAT_SHEETS));
        return Math.max(0, baseSlots) + used * SLOTS_PER_CHEAT_SHEET;
    }

    public static boolean tryFeedCheatSheet(Mob mob, Player player, ItemStack stack, int sheets,
            SoundEvent hurtSound, IntConsumer setSheets) {
        return tryFeedSlotItem(mob, player, stack, GeneratedMod.FORBIDDEN_CHEAT_SHEET.get(),
                sheets, hurtSound, setSheets);
    }

    public static boolean tryFeedSlotItem(Mob mob, Player player, ItemStack stack, Item expected,
            int sheets, SoundEvent hurtSound, IntConsumer setSheets) {
        if (stack.isEmpty() || expected == null || !stack.is(expected)) {
            return false;
        }
        if (mob.level().isClientSide()) {
            return true;
        }
        if (sheets >= MAX_CHEAT_SHEETS) {
            refuse(mob, hurtSound);
            return true;
        }
        setSheets.accept(Math.min(MAX_CHEAT_SHEETS, sheets + 1));
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (mob.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, mob.getX(), mob.getY(0.6D), mob.getZ(),
                    20, mob.getBbWidth() * 0.6D, mob.getBbHeight() * 0.4D,
                    mob.getBbWidth() * 0.6D, 0.03D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, mob.getX(), mob.getY(1.0D), mob.getZ(),
                    12, mob.getBbWidth() * 0.5D, mob.getBbHeight() * 0.3D,
                    mob.getBbWidth() * 0.5D, 0.02D);
        }
        mob.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 1.0F);
        mob.playSound(SoundEvents.RESPAWN_ANCHOR_CHARGE, 1.0F, 1.0F);
        return true;
    }

    public static void refuse(Mob mob, SoundEvent hurtSound) {
        if (mob.level().isClientSide()) {
            return;
        }
        if (hurtSound != null) {
            mob.playSound(hurtSound, 1.0F, 1.0F);
        }
        mob.level().broadcastEntityEvent(mob, (byte) 9);
    }
}
