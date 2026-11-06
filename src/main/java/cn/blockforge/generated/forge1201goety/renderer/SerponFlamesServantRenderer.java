package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;
import cn.blockforge.generated.goetysupreme.renderer.SerponFlamesRenderer;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesServantEntity;
import cn.blockforge.generated.goetysupreme.renderer.SerponFlamesServantRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SprayBeamRenderer;

public final class SerponFlamesServantRenderer extends SprayBeamRenderer<SerponFlamesServantEntity> {

    private static final int COLOR_I = 0xFF8114;
    private static final int COLOR_II = 0xFFE2CB;
    private static final int COLOR_III = 0xFF1C14;

    public SerponFlamesServantRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Entity beamCaster(SerponFlamesServantEntity entity) {
        return entity.getOwner();
    }

    @Override
    public float beamLife(SerponFlamesServantEntity entity, float partialTick) {
        return entity.tickCount + partialTick;
    }

    @Override
    public int beamMaxLife(SerponFlamesServantEntity entity) {
        return 20;
    }

    @Override
    public int circleStart(SerponFlamesServantEntity entity) {
        return 5;
    }

    @Override
    public int circleStop(SerponFlamesServantEntity entity) {
        return 10;
    }

    @Override
    public int stripeStart(SerponFlamesServantEntity entity) {
        return 3;
    }

    @Override
    public int stripeStop(SerponFlamesServantEntity entity) {
        return 15;
    }

    @Override
    public float maxReach(SerponFlamesServantEntity entity) {
        return 9.0F;
    }

    @Override
    public float spellAngle(SerponFlamesServantEntity entity) {
        return 22.5F;
    }

    @Override
    public int colorI(SerponFlamesServantEntity entity) {
        return COLOR_I;
    }

    @Override
    public int colorII(SerponFlamesServantEntity entity) {
        return COLOR_II;
    }

    @Override
    public int colorIII(SerponFlamesServantEntity entity) {
        return COLOR_III;
    }

    @Override
    public int stripCount(SerponFlamesServantEntity entity) {
        return 10;
    }

    @Override
    public float innerRadius(SerponFlamesServantEntity entity) {
        return 0.5F;
    }

    @Override
    public float outerRadius(SerponFlamesServantEntity entity) {
        return 0.25F;
    }

    @Override
    public float ringSpawnRadiusMult(SerponFlamesServantEntity entity) {
        return 1.6F;
    }

    @Override
    public float pointRadiusMul(SerponFlamesServantEntity entity) {
        return 0.08F;
    }

    @Override
    public double originHeightFactor(SerponFlamesServantEntity entity) {
        return 0.0D;
    }
}
