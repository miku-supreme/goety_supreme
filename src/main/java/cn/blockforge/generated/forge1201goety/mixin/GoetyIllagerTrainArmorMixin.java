package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.mixin.GoetyForgeRingArmorMixin;

@Mixin(targets = "com.Polarice3.Goety.common.entities.ally.illager.raider.RaiderServant", remap = false)
public abstract class GoetyIllagerTrainArmorMixin {

    @Inject(method = "m_21406_", at = @At("RETURN"), require = 1)
    private void goetySupreme$ringArmorForTrainedIllager(EntityType<?> type, boolean keepEquipment,
            CallbackInfoReturnable<Mob> callback) {
        Mob converted = callback.getReturnValue();
        if (!(converted instanceof Summoned newServant)
                || !RingServantGear.isIllagerServant(newServant)) {
            return;
        }
        Summoned source = (Summoned) (Object) this;
        Player owner = RingServantGear.ringOwner(source);
        if (owner == null || !RingServantGear.handles(RingServantGear.ringOf(owner))) {
            return;
        }
        newServant.setTrueOwner(owner);
        RingServantGear.rollRingArmorOnly(newServant, newServant.getRandom());
    }
}
