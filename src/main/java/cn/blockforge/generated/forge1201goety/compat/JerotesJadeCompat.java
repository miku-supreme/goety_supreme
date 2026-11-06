package cn.blockforge.generated.goetysupreme.compat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snownee.jade.api.IWailaClientRegistration;
import cn.blockforge.generated.goetysupreme.entity.AbstractJvillageInventoryServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesJadeCompat;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.MagmaGolemJadePlugin;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.compat.SerponCombatExecuterJadeProvider;

public final class JerotesJadeCompat {

    private static final Logger LOGGER = LoggerFactory.getLogger("goety_supreme");

    private JerotesJadeCompat() {
    }

    public static void registerSerponCombatExecuter(IWailaClientRegistration registration) {
        if (!JerotesVillageCompat.isJerotesVillageLoaded()) {
            return;
        }
        try {
            registration.registerEntityComponent(SerponCombatExecuterJadeProvider.INSTANCE,
                    SerponCombatExecuter.class);
        } catch (Throwable t) {
            LOGGER.warn("[goety_supreme] 蛇龙执战者的玉（Jade）战力条目注册失败，"
                    + "通常是 J 村本体或前置核心 jerotes 缺失 / 版本不匹配；其余 Jade 内容不受影响：{}",
                    t.toString());
        }
    }
}
