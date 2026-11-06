package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.ai.JerotesRangedWeaponGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SerponDartEntity;

public class SerponDartEntity extends AbstractArrow {

    public static final float BASE_DAMAGE = 4.0F;

    public SerponDartEntity(EntityType<? extends SerponDartEntity> entityType, Level level) {
        super(entityType, level);
        setBaseDamage(BASE_DAMAGE);
        this.pickup = Pickup.CREATIVE_ONLY;
    }

    public SerponDartEntity(Level level, LivingEntity shooter) {
        super(ModEntityTypes.SERPON_DART.get(), shooter, level);
        setBaseDamage(BASE_DAMAGE);
        this.pickup = Pickup.CREATIVE_ONLY;
        setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
    }

    @Override
    public ItemStack getPickupItem() {
        return AbstractJerotesServant.jerotesItem("serpon_dart");
    }

    @Override
    public boolean canHitEntity(Entity target) {
        return !(getOwner() instanceof AbstractJerotesServant servant && servant.isAlliedTo(target))
                && super.canHitEntity(target);
    }
}
