package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.renderer.WarlordSaberTrail;

public final class WarlordSaberTrail {

    public static void tick(AbstractWitherWarlord warlord) {
        if (!warlord.level().isClientSide) {
            return;
        }
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> ClientWarlordSaberTrail.clientTick(warlord));
    }

    private WarlordSaberTrail() {
    }
}
