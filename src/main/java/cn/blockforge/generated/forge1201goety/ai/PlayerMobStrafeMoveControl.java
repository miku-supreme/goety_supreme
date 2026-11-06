package cn.blockforge.generated.goetysupreme.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobStrafeMoveControl;

public final class PlayerMobStrafeMoveControl extends MoveControl {

    public PlayerMobStrafeMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        if (this.operation != MoveControl.Operation.STRAFE) {
            super.tick();
            return;
        }
        float speed = (float) this.speedModifier
                * (float) this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
        this.mob.setSpeed(speed);
        this.mob.setZza(this.strafeForwards);
        this.mob.setXxa(this.strafeRight);
        this.operation = MoveControl.Operation.WAIT;
    }
}
