package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.GeneratedMod;
import com.Polarice3.Goety.client.audio.PostBossMusic;
import com.Polarice3.Goety.common.entities.hostile.Wight;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.renderer.SoulReaperFogClient;
import cn.blockforge.generated.goetysupreme.mixin.GoetyGuardianAttackRhythmMixin;

@Mixin(value = PostBossMusic.class, remap = false)
public abstract class GoetyPostBossMusicMixin {

    @Shadow
    @Final
    public Mob mobEntity;

    @Inject(method = "m_7767_", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$skipSacrificeEnding(CallbackInfoReturnable<Boolean> callback) {
        if (!(this.mobEntity instanceof Wight)) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null
                && minecraft.player.hasEffect(GeneratedMod.SOUL_REAPER_OMEN.get())) {
            callback.setReturnValue(false);
        }
    }
}
