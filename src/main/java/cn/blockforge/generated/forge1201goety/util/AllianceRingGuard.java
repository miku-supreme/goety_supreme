package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.util.AllianceRingGuard;

public final class AllianceRingGuard {

    private AllianceRingGuard() {
    }

    public static Boolean safeAllianceAnswer(IOwned self, Entity entity) {
        LivingEntity owner = self.getTrueOwner();
        if (owner == null || owner == (Object) self) {
            return null;
        }
        if (!(owner instanceof IOwned other) || other.getTrueOwner() != (Object) self) {
            return null;
        }
        if (entity == owner || entity == (Object) self) {
            return Boolean.TRUE;
        }
        if (entity instanceof LivingEntity living && self.isAllyWith(living)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
