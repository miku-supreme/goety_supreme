package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.MutantZombieSlamArea;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieSlamFx;
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.api.RoarGuaranteeTarget;
import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.init.ModRegistry;
import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.event.MutantMonstersEvents;

@Mixin(value = MutantZombie.class, remap = false)
public abstract class MutantZombieMixin implements RoarGuaranteeTarget {

    private static final int GOETY_SUPREME_SLAM_IMPACT_TICK = 12;

    @Inject(method = "canHarm", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$allowMutantServantCombat(Entity target,
            CallbackInfoReturnable<Boolean> callback) {
        MutantZombie self = (MutantZombie) (Object) this;
        if (target instanceof MutantZombieServant servant
                && !self.isAlliedTo(servant)
                && !servant.isAlliedTo(self)) {
            callback.setReturnValue(true);
        }
    }

    @Inject(method = "m_8119_", at = @At("HEAD"), require = 0)
    private void goetySupreme$slamImpactArea(CallbackInfo callback) {
        MutantZombie self = (MutantZombie) (Object) this;
        if (self.level().isClientSide()) {
            return;
        }
        if (self.getAnimation() != MutantZombie.SLAM_GROUND_ANIMATION
                || self.getAnimationTick() != GOETY_SUPREME_SLAM_IMPACT_TICK) {
            return;
        }
        LivingEntity target = self.getTarget();
        if (target == null) {
            return;
        }
        double dx = target.getX() - self.getX();
        double dz = target.getZ() - self.getZ();
        double length = Math.sqrt(dx * dx + dz * dz);
        if (length < 1.0E-4D) {
            length = 1.0D;
        }
        int startX = Mth.floor(self.getX() + dx / length * 2.0D);
        int startZ = Mth.floor(self.getZ() + dz / length * 2.0D);
        int y = Mth.floor(self.getBoundingBox().minY);
        if (self.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            MutantZombieSlamFx.impactBurst(serverLevel, startX + 0.5D, y + 0.1D, startZ + 0.5D,
                    MutantZombieSlamArea.RADIUS);
        }
        DamageSource damageSource = ModDamageSource.ownedDamageSource(self.level(),
                ModRegistry.EFFECTS_BYPASSING_MOB_ATTACK_DAMAGE_TYPE, self, self);
        for (LivingEntity victim : MutantZombieSlamArea.victims(self, startX + 0.5D, startZ + 0.5D, y,
                self::canAttack)) {
            float damage = MutantZombieSlamArea.rollDamage(self.getRandom());
            if (victim.hurt(damageSource, damage) && self.getRandom().nextInt(5) == 0) {
                victim.addEffect(new MobEffectInstance(MobEffects.HUNGER, 160, 1));
            }
            MutantZombieSlamArea.pushAway(victim, self.getX(), self.getZ(), 0.3D, 0.04D);
        }
    }

    @Override
    public GoalSelector goetySupreme$getGoalSelector() {
        return ((Mob) (Object) this).goalSelector;
    }
}
