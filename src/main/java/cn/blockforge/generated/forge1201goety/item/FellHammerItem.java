package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.items.equipment.HammerItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import cn.blockforge.generated.goetysupreme.item.FellHammerItem;

public final class FellHammerItem extends HammerItem {

    private static final int BUSTED_TICKS = 100;

    public FellHammerItem() {
        super(Tiers.IRON);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (!attacker.level().isClientSide && target.isAlive()) {
            target.addEffect(new MobEffectInstance(GoetyEffects.BUSTED.get(), BUSTED_TICKS), attacker);
        }
        return result;
    }
}
