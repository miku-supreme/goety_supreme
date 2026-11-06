package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import cn.blockforge.generated.goetysupreme.mixin.GoetySupremeMixinPlugin;

@Mixin(targets = "snownee.jade.addon.vanilla.AnimalOwnerProvider", remap = false)
public abstract class SerponOwnerJadeMixin {

    @Inject(method = "appendTooltip(Lsnownee/jade/api/ITooltip;Lsnownee/jade/api/EntityAccessor;"
                    + "Lsnownee/jade/api/config/IPluginConfig;)V",
            at = @At("HEAD"), cancellable = true)
    private void goetySupreme$serponEmployerLabel(ITooltip tooltip, EntityAccessor accessor,
            IPluginConfig config, CallbackInfo ci) {
        Entity entity = accessor.getEntity();
        if (!(entity instanceof AbstractSerpon)) {
            return;
        }
        CompoundTag data = accessor.getServerData();
        if (!data.contains("OwnerName", 8)) {
            return;
        }
        String name = data.getString("OwnerName");
        if (name.isEmpty()) {
            return;
        }
        tooltip.add(Component.translatable("jade.goety_supreme.serpon_employer", name));
        ci.cancel();
    }
}
