package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.api.RagingSkillHolder;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import com.Polarice3.Goety.common.entities.ally.golem.RedstoneMonstrosity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class RagingWipeGrabRideMixin {

    private static final double HOLD_FORWARD = 5.4D;

    @Inject(method = "m_19956_", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$holdGrabbedVictim(Entity passenger, Entity.MoveFunction moveFunction,
            CallbackInfo callback) {
        if (!(((Object) this) instanceof RedstoneMonstrosity self)) {
            return;
        }
        if (!(self instanceof RagingStarServant servant) || !servant.isRagingStarred()) {
            return;
        }
        if (!(self instanceof RagingSkillHolder holder)
                || holder.getRagingSkillId() != RagingSkillHolder.RAGING_SKILL_WIPE
                || passenger.getId() != holder.getRagingGrabId()
                || !self.hasPassenger(passenger)) {
            return;
        }
        Vec3 look = self.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0D, look.z);
        flat = flat.lengthSqr() < 1.0E-6D ? new Vec3(0.0D, 0.0D, 1.0D) : flat.normalize();
        int tick = holder.getRagingSkillTick();
        double height = RagingSkillHolder.wipeHoldHeight(tick);
        moveFunction.accept(passenger,
                self.getX() + flat.x * HOLD_FORWARD,
                self.getY() + height,
                self.getZ() + flat.z * HOLD_FORWARD);
        callback.cancel();
    }
}
