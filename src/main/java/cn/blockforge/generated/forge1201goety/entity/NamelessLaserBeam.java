package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.projectiles.CorruptedBeam;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.util.CorruptedLaserParticles;

public class NamelessLaserBeam extends CorruptedBeam {

    public NamelessLaserBeam(EntityType<NamelessLaserBeam> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            CorruptedLaserParticles.tick(this);
        }
    }
}
