package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.projectiles.CorruptedBeam;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import cn.blockforge.generated.goetysupreme.renderer.ClientCorruptedLaserParticles;
import cn.blockforge.generated.goetysupreme.util.CorruptedLaserParticles;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;

public final class CorruptedLaserParticles {

    public static void tick(CorruptedBeam beam) {
        if (!beam.level().isClientSide) {
            return;
        }
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> ClientCorruptedLaserParticles.tickClient(beam));
    }

    private CorruptedLaserParticles() {
    }
}
