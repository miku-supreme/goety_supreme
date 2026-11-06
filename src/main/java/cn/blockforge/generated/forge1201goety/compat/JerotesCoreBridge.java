package cn.blockforge.generated.goetysupreme.compat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.compat.JerotesCoreBridge;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageSpellBridge;

public final class JerotesCoreBridge {

    private static final boolean LOADED = ModList.get().isLoaded("jerotes");

    private JerotesCoreBridge() {
    }

    public static boolean isLoaded() {
        return LOADED;
    }

    public static boolean savingSpellThrow(LivingEntity entity) {
        return entity != null && LOADED && Impl.savingSpellThrow(entity);
    }

    private static final class Impl {
        static boolean savingSpellThrow(LivingEntity entity) {
            return com.jerotes.jerotes.util.EntityAndItemFind.savingSpellThrow(entity);
        }
    }
}
