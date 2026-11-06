package cn.blockforge.generated.goetysupreme.compat;

import com.Polarice3.Goety.api.entities.IOwned;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.compat.SummonCapUtil;

public final class SummonCapUtil {

    public static final String DIRECT_SUMMON_TAG = "GoetySupremeDirectSummon";

    public static final String SUMMONER_TAG = "GoetySupremeDirectSummoner";

    private SummonCapUtil() {
    }

    public static void markDirectSummon(Entity entity) {
        if (entity != null) {
            entity.getPersistentData().putBoolean(DIRECT_SUMMON_TAG, true);
        }
    }

    public static void markDirectSummon(Entity entity, LivingEntity summoner) {
        markDirectSummon(entity);
        if (entity != null && summoner != null) {
            entity.getPersistentData().putUUID(SUMMONER_TAG, summoner.getUUID());
        }
    }

    public static boolean summonedBy(Entity entity, LivingEntity summoner) {
        if (entity == null || summoner == null) {
            return false;
        }
        CompoundTag data = entity.getPersistentData();
        if (data.hasUUID(SUMMONER_TAG)) {
            return summoner.getUUID().equals(data.getUUID(SUMMONER_TAG));
        }
        return data.getBoolean(DIRECT_SUMMON_TAG)
                && entity instanceof IOwned owned
                && owned.getTrueOwner() == summoner;
    }

    public static boolean isSlimeServant(ResourceLocation id) {
        return id != null && id.getPath().contains("slime");
    }

    public static boolean countsTowardCap(Entity entity, ResourceLocation id) {
        return !isSlimeServant(id)
                || entity.getPersistentData().getBoolean(DIRECT_SUMMON_TAG);
    }
}
