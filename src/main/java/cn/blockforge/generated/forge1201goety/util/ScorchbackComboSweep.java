package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.util.ScorchbackComboSweep;

public final class ScorchbackComboSweep {
    private static final int FIRST_TICK = 9;
    private static final Vec3[] TIPS = {
        new Vec3(-0.3050043D, 1.5872655D, 2.8521252D), // 9 刻
        new Vec3(-0.2487917D, 1.8060439D, 2.7438464D), // 10 刻
        new Vec3(0.4768748D, 1.9483515D, 2.6598540D), // 11 刻
        new Vec3(1.3691650D, 2.1996013D, 2.1788032D), // 12 刻
        new Vec3(1.7822779D, 2.1909098D, 1.8345570D), // 13 刻
        new Vec3(2.0656045D, 2.0634313D, 1.5830025D), // 14 刻
        new Vec3(2.2463464D, 1.8711204D, 1.4470335D), // 15 刻
        new Vec3(2.3726590D, 1.5580628D, 1.4461038D), // 16 刻
        new Vec3(2.4297532D, 1.2363709D, 1.5108498D), // 17 刻
        new Vec3(2.4425029D, 1.0760857D, 1.5463159D), // 18 刻
        new Vec3(2.4558496D, 1.0905661D, 1.5397218D), // 19 刻
        new Vec3(2.4692417D, 1.1558364D, 1.5201986D), // 20 刻
        new Vec3(2.4756542D, 1.2000756D, 1.5084576D), // 21 刻
        new Vec3(2.1171353D, 1.4149801D, 1.9597652D), // 22 刻
        new Vec3(0.1961549D, 2.0228359D, 2.6908629D), // 23 刻
        new Vec3(-1.3502304D, 2.1048966D, 2.2224055D), // 24 刻
        new Vec3(-1.6855698D, 2.0660103D, 1.9855244D), // 25 刻
        new Vec3(-1.9295496D, 2.0111210D, 1.7705827D), // 26 刻
        new Vec3(-2.0943977D, 1.9595443D, 1.5994834D), // 27 刻
        new Vec3(-2.2103365D, 1.9126150D, 1.4632260D), // 28 刻
        new Vec3(-2.2829051D, 1.8817818D, 1.3691309D), // 29 刻
        new Vec3(-2.3281235D, 1.8640827D, 1.3051011D), // 30 刻
        new Vec3(-2.3455053D, 1.8643086D, 1.2758185D), // 31 刻
    };

    public static Vec3 localPosition(float tick) {
        float index = Mth.clamp(tick - FIRST_TICK, 0.0F, TIPS.length - 1.0F);
        int left = (int) index;
        return TIPS[left].lerp(TIPS[Math.min(left + 1, TIPS.length - 1)], index - left);
    }

    private ScorchbackComboSweep() { }
}
