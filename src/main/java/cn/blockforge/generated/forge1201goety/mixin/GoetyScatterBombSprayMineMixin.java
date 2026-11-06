package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.RagingStarSupport;
import com.Polarice3.Goety.common.entities.projectiles.ScatterBomb;
import com.Polarice3.Goety.common.entities.projectiles.ScatterMine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ScatterBomb.class, remap = false)
public abstract class GoetyScatterBombSprayMineMixin {

    @Inject(method = "m_6532_", at = @At("HEAD"), require = 0)
    private void goetySupreme$spawnMineOnLanding(HitResult hit, CallbackInfo callback) {
        ScatterBomb self = (ScatterBomb) (Object) this;
        if (self.level().isClientSide()) {
            return;
        }
        if (!self.getPersistentData().getBoolean(RagingStarSupport.SPRAY_MINE)) {
            return;
        }
        Entity ownerEntity = self.getOwner();
        if (!(ownerEntity instanceof LivingEntity owner)) {
            return;
        }
        Level level = self.level();
        BlockPos pos = BlockPos.containing(self.getX(), self.getY(), self.getZ());
        if (!level.getEntitiesOfClass(ScatterMine.class, new AABB(pos)).isEmpty()) {
            return;
        }
        ScatterMine mine = new ScatterMine(level, owner, Vec3.atLowerCornerOf(pos));
        level.addFreshEntity(mine);
    }
}
