package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import cn.blockforge.generated.goetysupreme.model.SakuraSpiritModel;
import cn.blockforge.generated.goetysupreme.renderer.SakuraSpiritRenderer;

public class SakuraSpiritRenderer<T extends Mob> extends HumanoidMobRenderer<T, HumanoidModel<T>> {
    private static final ResourceLocation ZOMBIE_TEXTURE =
            ResourceLocation.parse("textures/entity/zombie/zombie.png");

    @SuppressWarnings({"unchecked", "rawtypes"})
    public SakuraSpiritRenderer(EntityRendererProvider.Context context) {
        super(context, (HumanoidModel<T>) (HumanoidModel) new SakuraSpiritModel(context.bakeLayer(ModelLayers.ZOMBIE)),
                0.25F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel(context.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)),
                new HumanoidModel(context.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return ZOMBIE_TEXTURE;
    }

    @Override
    public void scale(T entity, PoseStack poseStack, float partialTick) {
        float tick = entity.tickCount + partialTick;
        poseStack.translate(0.0F, Mth.sin(tick * 0.2F) * 0.15F, 0.0F);
    }
}
