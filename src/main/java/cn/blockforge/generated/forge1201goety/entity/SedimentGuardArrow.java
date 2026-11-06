package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardArrow;

public class SedimentGuardArrow extends Arrow {

    public SedimentGuardArrow(EntityType<? extends SedimentGuardArrow> entityType, Level level) {
        super(entityType, level);
    }

    public SedimentGuardArrow(Level level, LivingEntity shooter) {
        super(ModEntityTypes.SEDIMENT_GUARD_ARROW.get(), level);
        setOwner(shooter);
        setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
    }

    @Override
    public boolean canHitEntity(Entity target) {
        return !(getOwner() instanceof AbstractSedimentServant servant && servant.isAlliedTo(target))
                && super.canHitEntity(target);
    }
}
