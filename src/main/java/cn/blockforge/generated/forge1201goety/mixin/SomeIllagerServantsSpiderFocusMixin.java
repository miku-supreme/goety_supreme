package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.SpiderFocusSwap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import cn.blockforge.generated.goetysupreme.mixin.GoetyOminousSpiderFocusMixin;

@Pseudo
@Mixin(targets = "com.qiuyue.someillagerservants.common.magic.spells.SpiderSpell", remap = false)
public abstract class SomeIllagerServantsSpiderFocusMixin {

    @Redirect(method = "SpellResult", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;m_7967_(Lnet/minecraft/world/entity/Entity;)Z"),
            require = 0)
    private boolean goetySupreme$secondRounderSpiderFocus(ServerLevel level, Entity entity) {
        return SpiderFocusSwap.addOrSwap(level, entity);
    }
}
