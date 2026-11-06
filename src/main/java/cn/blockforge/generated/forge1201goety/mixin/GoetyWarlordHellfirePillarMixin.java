package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import com.Polarice3.Goety.common.entities.util.FirePillar;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import cn.blockforge.generated.goetysupreme.item.HellfireSaberItem;

@Mixin(value = FirePillar.class, remap = false)
public abstract class GoetyWarlordHellfirePillarMixin {

    @Redirect(method = "m_8119_",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;"
                            + "m_6469_(Lnet/minecraft/world/damagesource/DamageSource;F)Z"),
            require = 0)
    private boolean goetySupreme$muteWarlordPillarDamage(LivingEntity victim, DamageSource source,
            float amount) {
        FirePillar self = (FirePillar) (Object) this;
        if (self.getPersistentData().getBoolean(AbstractWitherWarlord.HELLFIRE_PILLAR_TAG)) {
            return false;
        }
        return victim.hurt(source, amount);
    }
}
