package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.GuiItemRenderContext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.mixin.ItemStackHurtClampMixin;

@Mixin(GuiGraphics.class)
public abstract class GuiItemRenderContextMixin {

    @Inject(method = "m_280405_", at = @At("HEAD"))
    private void goetySupreme$enterGuiItemRender(LivingEntity entity, Level level, ItemStack stack,
            int x, int y, int seed, int alpha, CallbackInfo callback) {
        GuiItemRenderContext.enter();
    }

    @Inject(method = "m_280405_", at = @At("RETURN"))
    private void goetySupreme$exitGuiItemRender(LivingEntity entity, Level level, ItemStack stack,
            int x, int y, int seed, int alpha, CallbackInfo callback) {
        GuiItemRenderContext.exit();
    }
}
