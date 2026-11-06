package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import com.Polarice3.Goety.common.entities.projectiles.ScatterBomb;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScatterBomb.class, remap = false)
public abstract class GoetyScatterBombHomingMixin {

    @Inject(method = "m_8119_", at = @At("HEAD"), require = 0)
    private void goetySupreme$steerAntiairBomb(CallbackInfo callback) {
        ScatterBomb self = (ScatterBomb) (Object) this;
        if (self.level().isClientSide()) {
            return;
        }
        CompoundTag data = self.getPersistentData();
        long expire = data.getLong(RagingStarSupport.ANTIAIR_EXPIRE);
        if (expire > 0L && self.level().getGameTime() >= expire) {
            if (self.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.POOF,
                        self.getX(), self.getY(), self.getZ(), 8, 0.15D, 0.15D, 0.15D, 0.02D);
            }
            self.discard();
            return;
        }
        if (!data.getBoolean(RagingStarSupport.ANTIAIR_HOMING)) {
            return;
        }
        Entity target = self.level().getEntity(data.getInt(RagingStarSupport.ANTIAIR_TARGET));
        if (!(target instanceof LivingEntity living) || !living.isAlive()) {
            data.putBoolean(RagingStarSupport.ANTIAIR_HOMING, false);
            Vec3 motion = self.getDeltaMovement();
            if (motion.lengthSqr() < 1.0E-6D) {
                motion = Vec3.directionFromRotation(self.getXRot(), self.getYRot()).scale(0.6D);
            } else {
                motion = motion.scale(1.0D / 0.95D);
            }
            self.setDeltaMovement(motion);
            return;
        }
        Vec3 motion = self.getDeltaMovement();
        double speed = Math.max(motion.length(), 0.6D);
        Vec3 aim = living.position()
                .add(0.0D, living.getBbHeight() * 0.5D, 0.0D)
                .subtract(self.position());
        if (aim.lengthSqr() < 1.0E-4D) {
            return;
        }
        Vec3 steered = motion.scale(0.8D).add(aim.normalize().scale(speed * 0.2D));
        self.setDeltaMovement(steered);
    }
}
