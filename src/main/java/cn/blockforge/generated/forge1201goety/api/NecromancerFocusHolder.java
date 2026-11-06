package cn.blockforge.generated.goetysupreme.api;

import java.util.Set;
import cn.blockforge.generated.goetysupreme.util.FocusSlotRules;
import cn.blockforge.generated.goetysupreme.api.NecromancerFocusHolder;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerNativeCapMixin;

public interface NecromancerFocusHolder {

    Set<String> goetySupreme$necromancerFocusGrants();

    int goetySupreme$extraSummonMisses();

    void goetySupreme$setExtraSummonMisses(int misses);

    boolean goetySupreme$extraTopUpPending();

    void goetySupreme$setExtraTopUpPending(boolean pending);

    int goetySupreme$cheatSheets();

    void goetySupreme$setCheatSheets(int sheets);
}
