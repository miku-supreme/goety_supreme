package cn.blockforge.generated.goetysupreme.compat;

import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.block.AbominableSnowGolemSnowBlock;
import cn.blockforge.generated.goetysupreme.entity.EndersoulFragmentServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompat;
import cn.blockforge.generated.goetysupreme.compat.MutantMonstersCompatManager;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServantArrow;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;

public final class MutantMonstersCompat {

    public static final String MUTANT_MONSTERS_MOD_ID = "mutantmonsters";

    private static Boolean mutantMonstersLoaded = null;

    private MutantMonstersCompat() {
    }

    public static boolean isLoaded() {
        if (mutantMonstersLoaded == null) {
            mutantMonstersLoaded = ModList.get().isLoaded(MUTANT_MONSTERS_MOD_ID);
        }
        return mutantMonstersLoaded;
    }
}
