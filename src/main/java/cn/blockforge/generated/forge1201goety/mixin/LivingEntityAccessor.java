package cn.blockforge.generated.goetysupreme.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.mixin.ItemStackHurtClampMixin;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Invoker("m_21161_")
    float goetySupreme$invokeGetDamageAfterArmorAbsorb(DamageSource source, float amount);

    @Invoker("m_6515_")
    float goetySupreme$invokeGetDamageAfterMagicAbsorb(DamageSource source, float amount);
}
