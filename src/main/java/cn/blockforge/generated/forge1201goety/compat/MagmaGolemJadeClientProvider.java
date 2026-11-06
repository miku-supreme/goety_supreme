package cn.blockforge.generated.goetysupreme.compat;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import cn.blockforge.generated.goetysupreme.entity.MagmaGolem;
import cn.blockforge.generated.goetysupreme.compat.MagmaGolemJadeClientProvider;

public enum MagmaGolemJadeClientProvider implements IEntityComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "magma_golem_recovery");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof MagmaGolem golem) || !golem.isPetrified()) {
            return;
        }
        int percent = Mth.clamp(golem.getRecoveryPercent(), 0, 100);
        tooltip.add(Component.translatable("info.goety_supreme.magma_golem.recovery", percent + "%"));
    }
}
