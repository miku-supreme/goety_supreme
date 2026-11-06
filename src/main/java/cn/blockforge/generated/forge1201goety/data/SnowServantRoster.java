package cn.blockforge.generated.goetysupreme.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import cn.blockforge.generated.goetysupreme.data.SnowServantRoster;

public final class SnowServantRoster extends SavedData {
    private final Map<UUID, UUID> owners = new HashMap<>();

    public static SnowServantRoster get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
                SnowServantRoster::load, SnowServantRoster::new, "goety_supreme_snow_servants");
    }

    public static SnowServantRoster load(CompoundTag tag) {
        SnowServantRoster roster = new SnowServantRoster();
        ListTag entries = tag.getList("Servants", 10);
        for (int i = 0; i < entries.size(); i++) {
            CompoundTag entry = entries.getCompound(i);
            if (entry.hasUUID("Servant") && entry.hasUUID("Owner")) {
                roster.owners.put(entry.getUUID("Servant"), entry.getUUID("Owner"));
            }
        }
        return roster;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag entries = new ListTag();
        owners.forEach((servant, owner) -> {
            CompoundTag entry = new CompoundTag();
            entry.putUUID("Servant", servant);
            entry.putUUID("Owner", owner);
            entries.add(entry);
        });
        tag.put("Servants", entries);
        return tag;
    }

    public void track(UUID servant, UUID owner) {
        if (owner == null) {
            forget(servant);
        } else if (!Objects.equals(owners.put(servant, owner), owner)) {
            setDirty();
        }
    }

    public void forget(UUID servant) {
        if (owners.remove(servant) != null) setDirty();
    }

    public int count(UUID owner) {
        return (int) owners.values().stream().filter(owner::equals).count();
    }
}
