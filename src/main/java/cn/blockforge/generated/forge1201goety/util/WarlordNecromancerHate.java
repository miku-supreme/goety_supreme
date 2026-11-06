package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.common.entities.neutral.AbstractWitherNecromancer;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWildMage;
import cn.blockforge.generated.goetysupreme.util.WarlordNecromancerHate;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordServant;

public final class WarlordNecromancerHate {

    private WarlordNecromancerHate() {
    }

    public static boolean isNecromancerKind(LivingEntity entity) {
        if (entity instanceof AbstractWitherNecromancer) {
            return false;
        }
        return entity instanceof AbstractNecromancer
                || entity instanceof AbstractReaperNecromancer
                || entity instanceof AbstractWildMage;
    }
}
