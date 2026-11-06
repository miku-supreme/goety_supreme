package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import cn.blockforge.generated.goetysupreme.renderer.ClientIfritClawTrail;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.renderer.IfritClawTrail;
import cn.blockforge.generated.goetysupreme.renderer.WarlordSaberTrail;

public final class IfritClawTrail {

    public static void tick(Ifrit ifrit) {
        if (!ifrit.level().isClientSide) {
            return;
        }
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> ClientIfritClawTrail.clientTick(ifrit));
    }

    private IfritClawTrail() {
    }
}
