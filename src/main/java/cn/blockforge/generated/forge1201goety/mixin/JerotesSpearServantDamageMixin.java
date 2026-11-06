package cn.blockforge.generated.goetysupreme.mixin;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.jerotes.jerotes.item.Tool.ItemToolBaseSpearBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import cn.blockforge.generated.goetysupreme.mixin.GoetySupremeMixinPlugin;

@Mixin(value = ItemToolBaseSpearBase.class, remap = false)
public abstract class JerotesSpearServantDamageMixin {

    @ModifyVariable(method = "stabAttack", at = @At("HEAD"), argsOnly = true, ordinal = 0,
            remap = false, require = 0)
    private float goetySupreme$servantSpearTotalDamage(float damage, EquipmentSlot slot, Entity target,
            float originalDamage, boolean allowHurt, boolean allowKnockback, boolean allowDismount,
            LivingEntity attacker) {
        if (!(attacker instanceof Summoned)) {
            return damage;
        }
        return Math.max(damage, (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE));
    }
}
