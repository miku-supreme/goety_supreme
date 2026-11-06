package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.render.ZombieServantRenderer;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.ZombieServant;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.entity.BerserkServantArmorLayer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.renderer.GoetyZombieServantRenderer;
import cn.blockforge.generated.goetysupreme.entity.RagingStarChestLayer;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.entity.ZombieServantOuterLayer;

public final class GoetyZombieServantRenderer extends ZombieServantRenderer {

    private static final ResourceLocation BERSERK_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/zombie_servant_berserk.png");

    private static final ResourceLocation BUTCHER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID,
                    "textures/entity/zombie_servant_butcher.png");

    public GoetyZombieServantRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new ZombieServantOuterLayer<>(this));
        this.addLayer(new BerserkServantArmorLayer<>(this, context.getModelSet()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new RagingStarChestLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ZombieServant servant) {
        if (servant instanceof GoetyZombieServant zombieServant && zombieServant.hasBerserkTexture()) {
            return BERSERK_TEXTURE;
        }
        if (!(servant instanceof RagingStarServant star) || !star.isRagingStarred()) {
            return super.getTextureLocation(servant);
        }
        return BUTCHER_TEXTURE;
    }

    @Override
    public void scale(ZombieServant entity, PoseStack poseStack, float partialTick) {
        super.scale(entity, poseStack, partialTick);
        float scale = 1.0F;
        if (entity instanceof GoetyZombieServant zombie && zombie.isRagingStarred()) {
            scale *= GoetyZombieServant.starSizeScale();
        }
        if (entity instanceof GoetyZombieServant zombie && zombie.hasBerserkTexture()) {
            scale *= GoetyZombieServant.berserkSizeScale();
        }
        if (scale != 1.0F) {
            poseStack.scale(scale, scale, scale);
        }
        this.shadowRadius = 0.5F * scale;
    }
}
