package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import cn.blockforge.generated.goetysupreme.entity.BoneScoutArrow;
import cn.blockforge.generated.goetysupreme.entity.GoetySkeletonServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServantArrow;

public class BoneScoutArrow extends Arrow {

    public BoneScoutArrow(EntityType<? extends BoneScoutArrow> entityType, Level level) {
        super(entityType, level);
    }

    public BoneScoutArrow(Level level, LivingEntity shooter) {
        super(ModEntityTypes.BONE_SCOUT_ARROW.get(), level);
        setOwner(shooter);
        setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
    }

    @Override
    public boolean canHitEntity(Entity target) {
        return !(getOwner() instanceof GoetySkeletonServant servant && servant.isAlliedTo(target))
                && super.canHitEntity(target);
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        if (!level().isClientSide() && result.getEntity() instanceof LivingEntity target) {
            target.invulnerableTime = 0;
        }
        super.onHitEntity(result);
    }
}
