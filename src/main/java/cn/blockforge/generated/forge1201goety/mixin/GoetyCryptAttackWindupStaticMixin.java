package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import java.lang.reflect.Field;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import cn.blockforge.generated.goetysupreme.model.CryptNecromancerModel;
import cn.blockforge.generated.goetysupreme.mixin.GoetyCryptAttackWindupMixin;

@Pseudo
@Mixin(targets = "com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer$NecromancerRangedGoal",
        remap = false)
public abstract class GoetyCryptAttackWindupStaticMixin {

    @Shadow @Final private AbstractNecromancer mob;

    @Unique private static int goetySupreme$animationStart = Integer.MIN_VALUE;

    @Redirect(method = "m_8037_",
            at = @At(value = "FIELD",
                    target = "Lcom/Polarice3/Goety/common/entities/neutral/AbstractNecromancer$NecromancerRangedGoal;ANIMATION_START:I",
                    opcode = Opcodes.GETSTATIC),
            require = 0)
    private int goetySupreme$cryptWindup() {
        return this.mob instanceof CryptNecromancerServant crypt
                ? crypt.getCryptAttackWindupTicks() : this.goetySupreme$animationStart();
    }

    @Unique
    private int goetySupreme$animationStart() {
        if (goetySupreme$animationStart == Integer.MIN_VALUE) {
            int value = 5;
            try {
                Field field = this.getClass().getField("ANIMATION_START");
                value = field.getInt(null);
            } catch (Throwable ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
            }
            goetySupreme$animationStart = value;
        }
        return goetySupreme$animationStart;
    }
}
