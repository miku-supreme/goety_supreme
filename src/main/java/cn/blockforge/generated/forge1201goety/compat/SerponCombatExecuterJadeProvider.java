package cn.blockforge.generated.goetysupreme.compat;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.compat.SerponCombatExecuterJadeProvider;

public enum SerponCombatExecuterJadeProvider implements IEntityComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "serpon_combat_executer_stats");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof SerponCombatExecuter executer)) {
            return;
        }
        tooltip.add(Component.translatable("jade.goety_supreme.serpon_power",
                format(executer.syncedSerponPower())));
    }

    private static String format(double value) {
        if (Math.abs(value - Math.rint(value)) < 1.0E-4D) {
            return Integer.toString((int) Math.rint(value));
        }
        String s = String.format(java.util.Locale.ROOT, "%.2f", value);
        if (s.endsWith("0")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
