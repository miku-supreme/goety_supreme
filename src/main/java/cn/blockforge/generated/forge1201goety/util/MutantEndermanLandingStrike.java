package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.MutantEndermanLandingStrike;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class MutantEndermanLandingStrike {

    private static final String DAMAGE_TAG = "goety_supreme:telesmash_landing_damage";
    private static final String EXPIRES_TAG = "goety_supreme:telesmash_landing_expires";

    private static final int MAX_LIFETIME_TICKS = 200;

    private MutantEndermanLandingStrike() {
    }

    public static void mark(LivingEntity target, int damage) {
        if (damage <= 0 || target == null || !(target.level() instanceof ServerLevel level)) {
            return;
        }
        CompoundTag data = target.getPersistentData();
        data.putFloat(DAMAGE_TAG, damage);
        data.putLong(EXPIRES_TAG, level.getGameTime() + MAX_LIFETIME_TICKS);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity.level() instanceof ServerLevel level)) {
            return;
        }
        CompoundTag data = entity.getPersistentData();
        if (!data.contains(DAMAGE_TAG)) {
            return;
        }
        if (!entity.isAlive() || entity.isRemoved()) {
            clear(data);
            return;
        }
        if (level.getGameTime() > data.getLong(EXPIRES_TAG)) {
            clear(data);
            return;
        }
        if (entity.isInWater() || entity.isInLava() || entity.isPassenger()) {
            clear(data);
            return;
        }
        if (!entity.onGround()) {
            return;
        }
        float damage = data.getFloat(DAMAGE_TAG);
        clear(data);
        if (damage <= 0.0F) {
            return;
        }
        entity.invulnerableTime = 0;
        entity.hurt(level.damageSources().fall(), damage);
    }

    private static void clear(CompoundTag data) {
        data.remove(DAMAGE_TAG);
        data.remove(EXPIRES_TAG);
    }
}
