package cn.blockforge.generated.goetysupreme.compat;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import cn.blockforge.generated.goetysupreme.compat.BerserkJadeClientProvider;
import cn.blockforge.generated.goetysupreme.compat.JerotesJadeCompat;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolem;
import cn.blockforge.generated.goetysupreme.compat.MagmaGolemJadeClientProvider;
import cn.blockforge.generated.goetysupreme.compat.MagmaGolemJadePlugin;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;

@WailaPlugin
public class MagmaGolemJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(MagmaGolemJadeClientProvider.INSTANCE, MagmaGolem.class);
        registration.registerEntityComponent(BerserkJadeClientProvider.INSTANCE,
                MutantZombieServant.class);
        registration.registerEntityComponent(BerserkJadeClientProvider.INSTANCE,
                SedimentLordServant.class);
        JerotesJadeCompat.registerSerponCombatExecuter(registration);
    }
}
