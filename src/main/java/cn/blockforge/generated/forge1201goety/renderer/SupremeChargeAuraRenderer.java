package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.lang.reflect.Method;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.util.SupremeChargeAura;
import cn.blockforge.generated.goetysupreme.renderer.SupremeChargeAuraRenderer;

@OnlyIn(Dist.CLIENT)
public final class SupremeChargeAuraRenderer {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static Method renderMethod;
    private static boolean lookupDone;

    private SupremeChargeAuraRenderer() {
    }

    public static void render(LivingEntity entity, SupremeChargeAura aura, float partialTicks,
            PoseStack poseStack, MultiBufferSource buffer) {
        if (aura == null || aura.isSuppressed()) {
            return;
        }
        Object goetyAura = aura.goetyAura();
        if (goetyAura == null) {
            return;
        }
        Method method = renderMethod();
        if (method == null) {
            return;
        }
        try {
            method.invoke(null, entity, goetyAura, partialTicks, poseStack, buffer);
        } catch (Throwable throwable) {
            SupremeChargeAura.reportFailure(throwable);
        }
    }

    private static synchronized Method renderMethod() {
        if (lookupDone) {
            return renderMethod;
        }
        lookupDone = true;
        try {
            Class<?> auraClass = Class.forName(
                    "com.Polarice3.Goety.common.entities.util.ChargeAura");
            Class<?> rendererClass = Class.forName(
                    "com.Polarice3.Goety.client.render.ChargeAuraRenderer");
            renderMethod = rendererClass.getMethod("render", LivingEntity.class, auraClass,
                    float.class, PoseStack.class, MultiBufferSource.class);
            LOGGER.info("[goety_supreme] 冲锋穹顶直接调用 Goety ChargeAuraRenderer.render"
                    + "（不再自绘几何 / shader）");
        } catch (Throwable throwable) {
            renderMethod = null;
            SupremeChargeAura.reportFailure(throwable);
        }
        return renderMethod;
    }
}
