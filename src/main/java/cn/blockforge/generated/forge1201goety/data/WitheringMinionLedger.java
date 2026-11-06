package cn.blockforge.generated.goetysupreme.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import cn.blockforge.generated.goetysupreme.util.WitheringMinionControl;
import cn.blockforge.generated.goetysupreme.data.WitheringMinionLedger;

public final class WitheringMinionLedger extends SavedData {
    private final Map<UUID, UUID> owners = new HashMap<>();

    public static WitheringMinionLedger get(ServerLevel level) {
        return level.getServer().overworld().getDataStorage().computeIfAbsent(
                WitheringMinionLedger::load, WitheringMinionLedger::new, "goety_supreme_withering_minions");
    }

    private static WitheringMinionLedger load(CompoundTag tag) {
        WitheringMinionLedger ledger = new WitheringMinionLedger();
        ListTag entries = tag.getList("Minions", 10);
        for (int i = 0; i < entries.size(); i++) {
            CompoundTag entry = entries.getCompound(i);
            if (entry.hasUUID("Minion") && entry.hasUUID("Owner")) {
                ledger.owners.put(entry.getUUID("Minion"), entry.getUUID("Owner"));
            }
        }
        return ledger;
    }

    public int count(UUID owner) {
        return (int) owners.values().stream().filter(owner::equals).count();
    }

    public boolean register(UUID minion, UUID owner) {
        if (owner.equals(owners.get(minion))) return true;
        if (count(owner) >= WitheringMinionControl.LIMIT) return false;
        owners.put(minion, owner);
        setDirty();
        return true;
    }

    public void release(UUID minion) {
        if (owners.remove(minion) != null) setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag entries = new ListTag();
        owners.forEach((minion, owner) -> {
            CompoundTag entry = new CompoundTag();
            entry.putUUID("Minion", minion);
            entry.putUUID("Owner", owner);
            entries.add(entry);
        });
        tag.put("Minions", entries);
        return tag;
    }
}
