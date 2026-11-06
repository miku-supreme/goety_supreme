package cn.blockforge.generated.goetysupreme.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.config.MutantEndermanServantConfig;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.data.MutantEndermanWatchlings;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class MutantEndermanWatchlings extends SavedData {
    public static final String SUMMONER_TAG = "GoetySupremeWatchlingSummoner";
    private final Map<UUID, Set<UUID>> summons = new HashMap<>();

    public static MutantEndermanWatchlings get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
                MutantEndermanWatchlings::load, MutantEndermanWatchlings::new, "goety_supreme_watchlings");
    }

    public int count(UUID summoner) {
        return summons.getOrDefault(summoner, Set.of()).size();
    }

    public boolean register(UUID summoner, UUID watchling) {
        Set<UUID> children = summons.computeIfAbsent(summoner, key -> new HashSet<>());
        if (children.contains(watchling)) return true;
        if (children.size() >= MutantEndermanServantConfig.WATCHLING_CAP.get()) return false;
        children.add(watchling);
        setDirty();
        return true;
    }

    public void release(UUID summoner, UUID watchling) {
        Set<UUID> children = summons.get(summoner);
        if (children != null && children.remove(watchling)) {
            if (children.isEmpty()) summons.remove(summoner);
            setDirty();
        }
    }

    public static MutantEndermanWatchlings load(CompoundTag tag) {
        MutantEndermanWatchlings data = new MutantEndermanWatchlings();
        ListTag list = tag.getList("Summons", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.getCompound(i);
            if (entry.hasUUID("Summoner") && entry.hasUUID("Watchling")) {
                data.summons.computeIfAbsent(entry.getUUID("Summoner"), key -> new HashSet<>())
                        .add(entry.getUUID("Watchling"));
            }
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        summons.forEach((summoner, children) -> children.forEach(watchling -> {
            CompoundTag entry = new CompoundTag();
            entry.putUUID("Summoner", summoner);
            entry.putUUID("Watchling", watchling);
            list.add(entry);
        }));
        tag.put("Summons", list);
        return tag;
    }

    @SubscribeEvent
    public static void onJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (event.getLevel() instanceof ServerLevel level && entity.getPersistentData().hasUUID(SUMMONER_TAG)
                && !get(level).register(entity.getPersistentData().getUUID(SUMMONER_TAG), entity.getUUID())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLeave(EntityLeaveLevelEvent event) {
        Entity entity = event.getEntity();
        if (event.getLevel() instanceof ServerLevel level && entity.getPersistentData().hasUUID(SUMMONER_TAG)
                && entity.getRemovalReason() != null && entity.getRemovalReason().shouldDestroy()) {
            get(level).release(entity.getPersistentData().getUUID(SUMMONER_TAG), entity.getUUID());
        }
    }
}
