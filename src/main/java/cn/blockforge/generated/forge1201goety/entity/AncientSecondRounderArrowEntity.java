package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AncientSecondRounderArrowEntity;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardArrow;

public class AncientSecondRounderArrowEntity extends Arrow {

    public static final double BASE_DAMAGE = 2.5D;

    public AncientSecondRounderArrowEntity(EntityType<? extends AncientSecondRounderArrowEntity> entityType,
            Level level) {
        super(entityType, level);
        setBaseDamage(BASE_DAMAGE);
    }

    public AncientSecondRounderArrowEntity(Level level, LivingEntity shooter) {
        super(ModEntityTypes.ANCIENT_SECOND_ROUNDER_ARROW.get(), level);
        setOwner(shooter);
        setBaseDamage(BASE_DAMAGE);
        setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
    }

    @Override
    public boolean canHitEntity(Entity target) {
        return !(getOwner() instanceof AbstractJerotesServant servant && servant.isAlliedTo(target))
                && super.canHitEntity(target);
    }
}
