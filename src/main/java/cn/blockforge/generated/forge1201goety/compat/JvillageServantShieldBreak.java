package cn.blockforge.generated.goetysupreme.compat;

import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractJvillageInventoryServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageServantShieldBreak;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderAnimatedArmorServant;
import cn.blockforge.generated.goetysupreme.util.WarlordShieldBreak;

public final class JvillageServantShieldBreak {

    private JvillageServantShieldBreak() {
    }

    public static boolean breakShield(LivingEntity victim, int disableTicks) {
        if (victim instanceof AbstractSedimentServant sediment) {
            sediment.disableShieldBreak(disableTicks);
            return true;
        }
        if (victim instanceof AbstractSerpon serpon) {
            serpon.disableShieldBreak(disableTicks);
            return true;
        }
        if (victim instanceof SecondRounderAnimatedArmorServant armor) {
            armor.warlordBreakShield(disableTicks);
            return true;
        }
        return false;
    }
}
