package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerNativeCapMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetySupremeMixinPlugin;

@Mixin(targets = "com.Polarice3.goety_cataclysm.common.entities.neutral.AbstractDraugrNecromancer$SummonServantSpell",
        remap = false)
public abstract class CataclysmDraugrNecromancerNativeCapMixin {

    @Unique
    private boolean goetySupreme$extraTopUpCast;

    @Redirect(method = "m_8036_", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;m_6443_(Ljava/lang/Class;"
                            + "Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"),
            require = 0)
    private List<LivingEntity> goetySupreme$countNativeSummonsOnly(Level level, Class<LivingEntity> type,
            AABB area, Predicate<LivingEntity> predicate) {
        return NecromancerSummonExtras.excludeExtraSummons(level.getEntitiesOfClass(type, area, predicate));
    }

    @Redirect(method = "castSpell", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;m_6443_(Ljava/lang/Class;"
                            + "Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"),
            require = 0)
    private List<LivingEntity> goetySupreme$countNativeSummonsOnlyOnCast(Level level,
            Class<LivingEntity> type, AABB area, Predicate<LivingEntity> predicate) {
        return NecromancerSummonExtras.excludeExtraSummons(level.getEntitiesOfClass(type, area, predicate));
    }

    @Inject(method = "m_8036_", at = @At("RETURN"), cancellable = true, require = 0)
    private void goetySupreme$keepSummoningForExtras(CallbackInfoReturnable<Boolean> callback) {
        if (Boolean.TRUE.equals(callback.getReturnValue())) {
            return;
        }
        AbstractNecromancer necro = NecromancerSummonExtras.outerNecromancer(this);
        if (NecromancerSummonExtras.canStartSummon(necro)
                && NecromancerSummonExtras.hasBelowCapExtra(necro)) {
            this.goetySupreme$extraTopUpCast = true;
            callback.setReturnValue(true);
        }
    }

    @Inject(method = "castSpell", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$skipNativeSpawnOnExtraTopUp(CallbackInfo callback) {
        if (this.goetySupreme$extraTopUpCast) {
            this.goetySupreme$extraTopUpCast = false;
            callback.cancel();
        }
    }
}
