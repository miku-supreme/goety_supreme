package cn.blockforge.generated.goetysupreme.util;

import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.event.WitherServantClientEvents;

public final class WitherServantClientControls {
    public static volatile boolean ascendHeld;
    public static volatile boolean descendHeld;

    private WitherServantClientControls() {
    }

    public static void releaseAll() {
        ascendHeld = false;
        descendHeld = false;
    }
}
