package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.config.CataclysmConfigValues;
import cn.blockforge.generated.goetysupreme.api.SummonPotencyAccess;
import java.lang.reflect.Field;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import cn.blockforge.generated.goetysupreme.mixin.GoetySupremeMixinPlugin;

@Mixin(targets = "com.Polarice3.goety_cataclysm.common.entities.ally.acropolis.CindariaServant$MagicAttackGoal",
        remap = false)
public abstract class CataclysmCindariaSpearDamageMixin {

    @ModifyArg(
            method = "m_8037_",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Water_Spear_Entity;"
                            + "<init>(Lnet/minecraft/world/entity/LivingEntity;"
                            + "Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/Level;FD)V",
                    remap = false),
            index = 3,
            remap = false,
            require = 0)
    private float goetySupreme$configuredWaterSpearDamage(float hardcoded) {
        Double configured = CataclysmConfigValues.value("CindariaRangeDamage");
        float base = configured == null ? hardcoded : (float) configured.doubleValue();
        return base + this.goetySupreme$potency();
    }

    private int goetySupreme$potency() {
        for (Class<?> type = this.getClass(); type != null; type = type.getSuperclass()) {
            try {
                Field field = type.getDeclaredField("entity");
                field.setAccessible(true);
                Object entity = field.get(this);
                if (entity instanceof SummonPotencyAccess access) {
                    return access.goetySupreme$potency();
                }
                return 0;
            } catch (NoSuchFieldException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
            } catch (ReflectiveOperationException | RuntimeException ignored) {
                return 0;
            }
        }
        return 0;
    }
}
