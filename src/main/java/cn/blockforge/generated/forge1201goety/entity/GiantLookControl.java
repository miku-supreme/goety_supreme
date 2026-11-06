package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.LookControl;

final class GiantLookControl extends LookControl {

    private final PrimordierServant servant;
    private final int maxYRotFromCenter;
    private final float speed;

    GiantLookControl(PrimordierServant mob, int maxYRotFromCenter) {
        this(mob, maxYRotFromCenter, 1.5F);
    }

    GiantLookControl(PrimordierServant mob, int maxYRotFromCenter, float speed) {
        super(mob);
        this.servant = mob;
        this.maxYRotFromCenter = maxYRotFromCenter;
        this.speed = speed;
    }

    @Override
    public void tick() {
        if (this.servant.stopLookTime()) {
            return;
        }
        if (this.lookAtCooldown > 0) {
            --this.lookAtCooldown;
            this.getYRotD().ifPresent(yaw ->
                    this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, yaw, this.yMaxRotSpeed));
            this.getXRotD().ifPresent(pitch ->
                    this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), pitch, this.xMaxRotAngle)));
        } else {
            if (this.mob.getNavigation().isDone()) {
                this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0.0F, 5.0F));
            }
            this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
        }
        float offset = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
        if (offset < (float) (-this.maxYRotFromCenter)) {
            this.mob.yBodyRot -= this.speed;
        } else if (offset > (float) this.maxYRotFromCenter) {
            this.mob.yBodyRot += this.speed;
        }
    }
}
