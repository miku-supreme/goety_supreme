package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBreathAreaServantEntity;
import cn.blockforge.generated.goetysupreme.renderer.OceanCurrentBreathAreaRenderer;
import cn.blockforge.generated.goetysupreme.renderer.SprayBeamRenderer;
import cn.blockforge.generated.goetysupreme.entity.BreathAreaEntity;

public final class OceanCurrentBreathAreaRenderer
        extends SprayBeamRenderer<GemstoneMalignasaurBreathAreaServantEntity> {

    public OceanCurrentBreathAreaRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Entity beamCaster(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getOwner();
    }

    @Override
    public float beamLife(GemstoneMalignasaurBreathAreaServantEntity entity, float partialTick) {
        return (float) entity.getLifeTick() + partialTick;
    }

    @Override
    public int beamMaxLife(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getMaxLife();
    }

    @Override
    public int circleStart(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getCircleStart();
    }

    @Override
    public int circleStop(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getCircleStop();
    }

    @Override
    public int stripeStart(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getBallStopStripeStart();
    }

    @Override
    public int stripeStop(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getStripeStop();
    }

    @Override
    public float maxReach(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getMaxReach();
    }

    @Override
    public float spellAngle(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getSpellAngle();
    }

    @Override
    public int colorI(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.beamLightI();
    }

    @Override
    public int colorII(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.beamLightII();
    }

    @Override
    public int colorIII(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.beamLightIII();
    }

    @Override
    public int stripCount(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getStripCount();
    }

    @Override
    public float innerRadius(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getInnerCircleRadius();
    }

    @Override
    public float outerRadius(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getOuterCircleRadius();
    }

    @Override
    public float ringSpawnRadiusMult(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getRingSpawnRadiusMult();
    }

    @Override
    public float pointRadiusMul(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return entity.getPointRadiusMul();
    }

    @Override
    public boolean drawCone(GemstoneMalignasaurBreathAreaServantEntity entity) {
        return false;
    }
}
