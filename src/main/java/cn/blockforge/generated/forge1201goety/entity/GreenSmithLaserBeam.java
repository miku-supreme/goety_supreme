package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.projectiles.CorruptedBeam;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.event.CorruptedFocusLaserEvents;
import cn.blockforge.generated.goetysupreme.util.CorruptedLaserParticles;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;

public class GreenSmithLaserBeam extends CorruptedBeam {

    private static final double STAFF_FORWARD = 1.494D;
    private static final double STAFF_LATERAL = 0.373D;
    private static final double STAFF_HEIGHT = 1.208D;

    public GreenSmithLaserBeam(EntityType<GreenSmithLaserBeam> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    @Override
    public Vec3 getOffsetVector(LivingEntity owner, float partialTick) {
        if (!(owner instanceof AbstractGreenSmith)) {
            return super.getOffsetVector(owner, partialTick);
        }
        double yaw = Math.toRadians(owner.yBodyRot);
        double cos = Math.cos(yaw);
        double sin = Math.sin(yaw);
        double x = -Math.sin(yaw) * STAFF_FORWARD + cos * STAFF_LATERAL;
        double z = Math.cos(yaw) * STAFF_FORWARD + sin * STAFF_LATERAL;
        return new Vec3(x, STAFF_HEIGHT, z);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            CorruptedLaserParticles.tick(this);
        }
    }
}
