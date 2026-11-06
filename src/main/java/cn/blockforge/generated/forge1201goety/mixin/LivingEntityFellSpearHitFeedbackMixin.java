package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.api.FellSpearHitFeedback;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class, remap = false)
public abstract class LivingEntityFellSpearHitFeedbackMixin implements FellSpearHitFeedback {

    @Unique
    private long goetySupreme$lastKineticHitTime = -1L;

    @Inject(method = {"m_7822_", "handleEntityEvent"}, at = @At("HEAD"), require = 0)
    private void goetySupreme$recordKineticHit(byte id, CallbackInfo callback) {
        if (id != 2) {
            return;
        }
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.level().isClientSide) {
            this.goetySupreme$markKineticHit();
        }
    }

    @Override
    public void goetySupreme$markKineticHit() {
        LivingEntity self = (LivingEntity) (Object) this;
        long now = self.level().getGameTime();
        if (now - this.goetySupreme$lastKineticHitTime <= 10L) {
            return;
        }
        this.goetySupreme$lastKineticHitTime = now;
    }

    @Override
    public float goetySupreme$ticksSinceKineticHit(float partialTick) {
        if (this.goetySupreme$lastKineticHitTime < 0L) {
            return 0.0F;
        }
        LivingEntity self = (LivingEntity) (Object) this;
        return (float) (self.level().getGameTime() - this.goetySupreme$lastKineticHitTime)
                + partialTick;
    }
}
