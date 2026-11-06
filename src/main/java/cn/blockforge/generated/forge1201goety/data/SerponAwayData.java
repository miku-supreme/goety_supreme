package cn.blockforge.generated.goetysupreme.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import cn.blockforge.generated.goetysupreme.data.SerponAwayData;

public final class SerponAwayData extends SavedData {

    public static final String DATA_NAME = "goety_supreme_serpon_away";

    private boolean serponAway;

    public boolean isSerponAway() {
        return serponAway;
    }

    public void setSerponAway(boolean value) {
        if (this.serponAway != value) {
            this.serponAway = value;
            setDirty();
        }
    }

    public static SerponAwayData get(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return serverLevel.getDataStorage().computeIfAbsent(
                    SerponAwayData::load, SerponAwayData::new, DATA_NAME);
        }
        return null;
    }

    public static SerponAwayData load(CompoundTag tag) {
        SerponAwayData data = new SerponAwayData();
        data.serponAway = tag.getBoolean("SerponAway");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putBoolean("SerponAway", serponAway);
        return tag;
    }
}
