package cn.blockforge.generated.goetysupreme.mixin;

import fuzs.mutantmonsters.init.ModRegistry;
import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import fuzs.mutantmonsters.world.entity.projectile.ThrowableBlock;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.block.SpellIceShardBlock;

@Mixin(value = ThrowableBlock.class, remap = false)
public abstract class MutantThrowableBlockSoundMixin {

    @Inject(
            method = "<init>(Lfuzs/mutantmonsters/world/entity/mutant/MutantSnowGolem;)V",
            at = @At("RETURN"))
    private void goetySupreme$playThrowSound(MutantSnowGolem golem, CallbackInfo ci) {
        ThrowableBlock self = (ThrowableBlock) (Object) this;
        if (self.getClass() != ThrowableBlock.class || self.level().isClientSide()) {
            return;
        }
        self.level().playSound(null, self.getX(), self.getY(), self.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 1.0F,
                0.8F + self.level().getRandom().nextFloat() * 0.2F);
    }

    @Redirect(
            method = "m_6532_",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/phys/AABB;m_82377_(DDD)Lnet/minecraft/world/phys/AABB;"),
            remap = false,
            require = 0)
    private AABB goetySupreme$verticalSplash(AABB box, double x, double y, double z) {
        ThrowableBlock self = (ThrowableBlock) (Object) this;
        if (self.getOwnerType() == ModRegistry.MUTANT_SNOW_GOLEM_ENTITY_TYPE.get()) {
            return box.inflate(x, Math.max(y, x), z);
        }
        return box.inflate(x, y, z);
    }

    @Redirect(
            method = "m_6532_",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;m_20280_(Lnet/minecraft/world/entity/Entity;)D"),
            remap = false,
            require = 0)
    private double goetySupreme$bodyCenterDistance(Entity self, Entity other) {
        if (self instanceof ThrowableBlock block
                && block.getOwnerType() == ModRegistry.MUTANT_SNOW_GOLEM_ENTITY_TYPE.get()) {
            Vec3 center = other.getBoundingBox().getCenter();
            return self.distanceToSqr(center.x, center.y, center.z);
        }
        return self.distanceToSqr(other);
    }
}
