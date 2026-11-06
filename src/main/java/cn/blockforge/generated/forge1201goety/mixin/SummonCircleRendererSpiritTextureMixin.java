package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import com.Polarice3.Goety.client.render.SummonCircleRenderer;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.common.entities.util.SummonCircle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import cn.blockforge.generated.goetysupreme.util.ExtraSummonWindup;

@Mixin(value = SummonCircleRenderer.class, remap = false)
public abstract class SummonCircleRendererSpiritTextureMixin {

    private static final ResourceLocation GOETY_SUPREME_SPIRIT_CIRCLE =
            ResourceLocation.fromNamespaceAndPath("goety",
                    "textures/entity/summon_circle_spirit.png");

    @Inject(method = "getTextureLocation", at = @At("RETURN"), cancellable = true, require = 0)
    private void goetySupreme$spiritTextureForNecromancer(SummonCircle circle,
            CallbackInfoReturnable<ResourceLocation> callback) {
        LivingEntity owner = circle.getTrueOwner();
        if (owner instanceof AbstractNecromancer
                || owner instanceof AbstractReaperNecromancer
                || owner instanceof AbstractGreenSmith) {
            callback.setReturnValue(GOETY_SUPREME_SPIRIT_CIRCLE);
        }
    }
}
