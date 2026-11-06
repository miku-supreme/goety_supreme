package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.ForgeHooksClient;
import cn.blockforge.generated.goetysupreme.model.AbominableSnowGolemModel;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.model.EngenderGolemServantModel;
import cn.blockforge.generated.goetysupreme.entity.FrostGolemPumpkinLayer;
import cn.blockforge.generated.goetysupreme.api.HeadProvider;

public final class FrostGolemPumpkinLayer<T extends AbstractFrostGolemServant,
        M extends EntityModel<T> & HeadProvider>
        extends RenderLayer<T, M> {

    public static final int GLOW_SHEET = 64;

    public static final int MASK_U = 16;
    public static final int MASK_V = 16;

    public static final float MASK_FACE = 16.0F;

    public static final float FACE_PUSH = 0.002F;

    public static final int VERTEX_INTS = 8;

    public static final int UV_OFFSET = 4;

    private static final ResourceLocation CARVED_SPRITE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "block/carved_pumpkin");

    private static final long RANDOM_SEED = 42L;

    private static final Map<ResourceLocation, RenderType> GLOW_TYPES = new ConcurrentHashMap<>();

    private static final RenderType CARVED_BASE_TYPE =
            RenderType.entityCutoutNoCull(InventoryMenu.BLOCK_ATLAS);

    private final ItemRenderer itemRenderer;

    public FrostGolemPumpkinLayer(RenderLayerParent<T, M> parent,
            EntityRendererProvider.Context context) {
        super(parent);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int light, T servant,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float headYaw, float headPitch) {
        if (!servant.hasPumpkin() || servant.isInvisible()) {
            return;
        }
        poseStack.pushPose();
        getParentModel().translateHead(poseStack);
        poseStack.translate(0.0F, -0.25F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.scale(0.55F, -0.55F, -0.55F);
        int overlay = LivingEntityRenderer.getOverlayCoords(servant, 0.0F);
        ItemStack pumpkin = new ItemStack(Blocks.CARVED_PUMPKIN);
        this.itemRenderer.renderStatic(servant, pumpkin,
                ItemDisplayContext.HEAD, false, poseStack, carvedBaseBuffers(buffers),
                servant.level(), light, overlay, servant.getId());
        flushCarvedBase(buffers);
        ResourceLocation glow = servant.pumpkinGlowTexture();
        if (glow != null) {
            renderPumpkinGlow(poseStack, buffers, servant, pumpkin, glow, ageInTicks);
        }
        poseStack.popPose();
    }

    private static MultiBufferSource carvedBaseBuffers(MultiBufferSource buffers) {
        return renderType -> buffers.getBuffer(CARVED_BASE_TYPE);
    }

    private static void flushCarvedBase(MultiBufferSource buffers) {
        if (buffers instanceof MultiBufferSource.BufferSource bufferSource) {
            bufferSource.endBatch(CARVED_BASE_TYPE);
        }
    }

    private void renderPumpkinGlow(PoseStack poseStack, MultiBufferSource buffers, T servant,
            ItemStack pumpkin, ResourceLocation glow, float ageInTicks) {
        BakedModel model = this.itemRenderer.getModel(pumpkin, servant.level(), servant,
                servant.getId());
        VertexConsumer out = buffers.getBuffer(GLOW_TYPES.computeIfAbsent(glow, RenderType::eyes));
        float glowPulse = pumpkinGlowPulse(servant.pumpkinGlowStyle(), ageInTicks);
        poseStack.pushPose();
        ForgeHooksClient.handleCameraTransforms(poseStack, model, ItemDisplayContext.HEAD, false);
        poseStack.translate(-0.5F, -0.5F, -0.5F);
        PoseStack.Pose pose = poseStack.last();
        RandomSource random = RandomSource.create(RANDOM_SEED);
        for (Direction face : Direction.values()) {
            random.setSeed(RANDOM_SEED);
            for (BakedQuad quad : model.getQuads(null, face, random)) {
                if (!isCarvedFace(quad)) {
                    continue;
                }
                emitCarvedFace(out, pose, quad, glowPulse);
            }
        }
        poseStack.popPose();
    }

    public static float pumpkinGlowPulse(AbstractFrostGolemServant.PumpkinGlowStyle style,
            float ageInTicks) {
        return 0.95F + 0.05F * Mth.cos(ageInTicks * 0.15F);
    }

    private static boolean isCarvedFace(BakedQuad quad) {
        TextureAtlasSprite sprite = quad.getSprite();
        return sprite != null && CARVED_SPRITE.equals(sprite.contents().name());
    }

    private static void emitCarvedFace(VertexConsumer out, PoseStack.Pose pose, BakedQuad quad,
            float glowPulse) {
        TextureAtlasSprite sprite = quad.getSprite();
        float du = sprite.getU1() - sprite.getU0();
        float dv = sprite.getV1() - sprite.getV0();
        if (du == 0.0F || dv == 0.0F) {
            return;
        }
        float invDu = 1.0F / du;
        float invDv = 1.0F / dv;
        Direction face = quad.getDirection();
        float pushX = face.getStepX() * FACE_PUSH;
        float pushY = face.getStepY() * FACE_PUSH;
        float pushZ = face.getStepZ() * FACE_PUSH;
        int[] data = quad.getVertices();
        for (int pass = 0; pass < 2; ++pass) {
            boolean reversed = pass == 1;
            for (int corner = 0; corner < 4; ++corner) {
                int base = (reversed ? 3 - corner : corner) * VERTEX_INTS;
                float x = Float.intBitsToFloat(data[base]) + pushX;
                float y = Float.intBitsToFloat(data[base + 1]) + pushY;
                float z = Float.intBitsToFloat(data[base + 2]) + pushZ;
                float s = (Float.intBitsToFloat(data[base + UV_OFFSET]) - sprite.getU0()) * invDu;
                float t = (Float.intBitsToFloat(data[base + UV_OFFSET + 1]) - sprite.getV0()) * invDv;
                out.vertex(pose.pose(), x, y, z);
                out.color(glowPulse, glowPulse, glowPulse, 1.0F);
                out.uv((MASK_U + s * MASK_FACE) / GLOW_SHEET,
                        (MASK_V + t * MASK_FACE) / GLOW_SHEET);
                out.overlayCoords(OverlayTexture.NO_OVERLAY);
                out.uv2(LightTexture.FULL_BRIGHT);
                out.normal(pose.normal(), face.getStepX(), face.getStepY(), face.getStepZ());
                out.endVertex();
            }
        }
    }
}
