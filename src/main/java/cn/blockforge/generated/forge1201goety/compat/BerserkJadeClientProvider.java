package cn.blockforge.generated.goetysupreme.compat;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import cn.blockforge.generated.goetysupreme.compat.BerserkJadeClientProvider;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentLordServant;

public enum BerserkJadeClientProvider implements IEntityComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "berserk_state");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        boolean berserk = accessor.getEntity() instanceof MutantZombieServant zombie && zombie.isBerserk()
                || accessor.getEntity() instanceof SedimentLordServant lord && lord.isBerserk();
        if (berserk) {
            tooltip.add(Component.literal("狂暴：on"));
        }
    }
}
