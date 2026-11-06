package cn.blockforge.generated.goetysupreme.mixin;

import com.Polarice3.Goety.common.entities.projectiles.ScatterBomb;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.GoetyScatterBombScaleMixin;

@Mixin(value = ScatterBomb.class, remap = false)
public abstract class GoetyScatterBombAntiairRadiusMixin {

    @Inject(method = "m_8119_", at = @At("HEAD"), require = 0)
    private void goetySupreme$enlargeAntiairRadius(CallbackInfo callback) {
        ScatterBomb self = (ScatterBomb) (Object) this;
        if (!self.isNoGravity()) {
            return;
        }
        EntityDimensions base = self.getDimensions(self.getPose());
        EntityDimensions scaled = base.scale(1.5F);
        AABB box = scaled.makeBoundingBox(self.position());
        self.setBoundingBox(box.move(0.0D, -(scaled.height - base.height) * 0.5D, 0.0D));
    }
}
