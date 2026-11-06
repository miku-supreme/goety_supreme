package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.EntityEmissiveMaskLayer;
import cn.blockforge.generated.goetysupreme.entity.FurnaceGolem;
import cn.blockforge.generated.goetysupreme.model.FurnaceGolemModel;
import cn.blockforge.generated.goetysupreme.renderer.FurnaceGolemRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

public final class FurnaceGolemRenderer extends MobRenderer<FurnaceGolem, FurnaceGolemModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/furnace_golem.png");

    private static final ResourceLocation BLINK_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "textures/entity/furnace_golem_blink.png");

    private static final int BLINK_PERIOD = 160;
    private static final int BLINK_LENGTH = 8;
    private static final ResourceLocation[] BURN_TEXTURES = new ResourceLocation[FurnaceGolem.BURN_FRAMES];

    static {
        for (int i = 0; i < FurnaceGolem.BURN_FRAMES; ++i) {
            BURN_TEXTURES[i] = ResourceLocation.fromNamespaceAndPath(
                    GeneratedMod.MOD_ID, "textures/entity/furnace_golem_hot_" + i + ".png");
        }
    }

    public FurnaceGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new FurnaceGolemModel(context.bakeLayer(FurnaceGolemModel.LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(FurnaceGolem entity) {
        if (entity.isBurning()) {
            int frame = (int) (entity.tickCount / FurnaceGolem.BURN_FRAME_TICKS) % FurnaceGolem.BURN_FRAMES;
            if (frame < 0) {
                frame += FurnaceGolem.BURN_FRAMES;
            }
            return BURN_TEXTURES[frame];
        }
        long phase = (entity.tickCount + entity.getId() * 37L) % BLINK_PERIOD;
        return phase < BLINK_LENGTH ? BLINK_TEXTURE : TEXTURE;
    }
}
