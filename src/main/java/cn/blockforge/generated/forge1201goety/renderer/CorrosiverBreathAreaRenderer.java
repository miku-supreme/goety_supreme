package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import cn.blockforge.generated.goetysupreme.renderer.CorrosiverBreathAreaRenderer;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverBreathAreaServantEntity;
import cn.blockforge.generated.goetysupreme.renderer.SprayBeamRenderer;
import cn.blockforge.generated.goetysupreme.entity.BreathAreaEntity;

public final class CorrosiverBreathAreaRenderer
        extends SprayBeamRenderer<CorrosiverBreathAreaServantEntity> {

    public CorrosiverBreathAreaRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Entity beamCaster(CorrosiverBreathAreaServantEntity entity) {
        return entity.getOwner();
    }

    @Override
    public float beamLife(CorrosiverBreathAreaServantEntity entity, float partialTick) {
        return (float) entity.getLifeTick() + partialTick;
    }

    @Override
    public int beamMaxLife(CorrosiverBreathAreaServantEntity entity) {
        return entity.getMaxLife();
    }

    @Override
    public int circleStart(CorrosiverBreathAreaServantEntity entity) {
        return entity.getCircleStart();
    }

    @Override
    public int circleStop(CorrosiverBreathAreaServantEntity entity) {
        return entity.getCircleStop();
    }

    @Override
    public int stripeStart(CorrosiverBreathAreaServantEntity entity) {
        return entity.getBallStopStripeStart();
    }

    @Override
    public int stripeStop(CorrosiverBreathAreaServantEntity entity) {
        return entity.getStripeStop();
    }

    @Override
    public float maxReach(CorrosiverBreathAreaServantEntity entity) {
        return entity.getMaxReach();
    }

    @Override
    public float spellAngle(CorrosiverBreathAreaServantEntity entity) {
        return entity.getSpellAngle();
    }

    @Override
    public int colorI(CorrosiverBreathAreaServantEntity entity) {
        return entity.beamLightI();
    }

    @Override
    public int colorII(CorrosiverBreathAreaServantEntity entity) {
        return entity.beamLightII();
    }

    @Override
    public int colorIII(CorrosiverBreathAreaServantEntity entity) {
        return entity.beamLightIII();
    }

    @Override
    public int stripCount(CorrosiverBreathAreaServantEntity entity) {
        return entity.getStripCount();
    }

    @Override
    public float innerRadius(CorrosiverBreathAreaServantEntity entity) {
        return entity.getInnerCircleRadius();
    }

    @Override
    public float outerRadius(CorrosiverBreathAreaServantEntity entity) {
        return entity.getOuterCircleRadius();
    }

    @Override
    public float ringSpawnRadiusMult(CorrosiverBreathAreaServantEntity entity) {
        return entity.getRingSpawnRadiusMult();
    }

    @Override
    public float pointRadiusMul(CorrosiverBreathAreaServantEntity entity) {
        return entity.getPointRadiusMul();
    }

    @Override
    public boolean drawCone(CorrosiverBreathAreaServantEntity entity) {
        return false;
    }
}
