package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.model.PlayerMobServantModel;
import cn.blockforge.generated.goetysupreme.renderer.PlayerMobServantRenderer;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinFolder;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinLayers;

public final class PlayerMobServantRenderer
        extends HumanoidMobRenderer<PlayerMobServant, PlayerModel<PlayerMobServant>> {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Set<String> LOGGED_SKIN_MISSES = ConcurrentHashMap.newKeySet();

    private static final ArmPose EATING = ArmPose.create("goety_supreme_eating", false,
            (model, entity, arm) -> {
                float chew = Mth.sin((entity.getTicksUsingItem() + entity.tickCount) * 0.45F) * 0.14F;
                ModelPart armPart = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
                armPart.xRot = -1.55F + chew;
                armPart.yRot = arm == HumanoidArm.RIGHT ? 0.45F : -0.45F;
                armPart.zRot = arm == HumanoidArm.RIGHT ? -0.35F : 0.35F;
            });

    private static final ArmPose WAND_CAST = ArmPose.create("goety_supreme_wand_cast", false,
            (model, entity, arm) -> {
                ModelPart armPart = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
                float sway = Mth.cos((entity.tickCount % 40) * 0.6662F) * 0.25F;
                armPart.xRot -= (float) Math.toRadians(105.0D);
                armPart.yRot = arm == HumanoidArm.RIGHT ? sway : -sway;
            });

    private final PlayerModel<PlayerMobServant> previewModel;

    public PlayerMobServantRenderer(EntityRendererProvider.Context context) {
        this(context, PlayerMobServantConfig.useSlimArms());
    }

    private PlayerMobServantRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, new PlayerMobServantModel(
                context.bakeLayer(PlayerMobServantModel.layer(slim, useGrownShell())),
                slim), 0.5F);

        this.previewModel = new PlayerMobServantModel(
                context.bakeLayer(PlayerMobServantModel.previewLayer(slim)), slim);

        addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(armorLayer(slim, true))),
                new HumanoidArmorModel<>(context.bakeLayer(armorLayer(slim, false))),
                context.getModelManager()));
        addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        addLayer(new ArrowLayer<>(context, this));
        addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
    }

    private static ModelLayerLocation armorLayer(boolean slim, boolean inner) {
        if (inner) {
            return slim ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR;
        }
        return slim ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR;
    }

    private static boolean useGrownShell() {
        return PlayerMobServantConfig.use3DSkinLayers() && !PlayerMobServantSkinLayers.isAvailable();
    }

    @Override
    public void render(PlayerMobServant servant, float entityYaw, float partialTick, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        boolean skinLayersActive = PlayerMobServantConfig.use3DSkinLayers()
                && PlayerMobServantSkinLayers.isAvailable();
        boolean preview = skinLayersActive && PlayerMobServantSkinLayers.isPreviewLike(servant);
        PlayerModel<PlayerMobServant> worldModel = getModel();
        PlayerModel<PlayerMobServant> playerModel = worldModel;
        if (preview) {
            this.model = previewModel;
            playerModel = previewModel;
        }
        playerModel.crouching = servant.isCrouching();
        playerModel.leftArmPose = armPose(servant.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.OFFHAND),
                servant, HumanoidArm.LEFT);
        playerModel.rightArmPose = armPose(servant.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND),
                servant, HumanoidArm.RIGHT);
        if (skinLayersActive) {
            PlayerMobServantSkinLayers.apply(playerModel, servant, getTextureLocation(servant),
                    PlayerMobServantConfig.useSlimArms(), preview);
        }
        try {
            super.render(servant, entityYaw, partialTick, poseStack, buffer, packedLight);
        } finally {
            if (skinLayersActive) {
                PlayerMobServantSkinLayers.clear(playerModel);
            }
            if (preview) {
                this.model = worldModel;
            }
        }
    }

    private static net.minecraft.client.model.HumanoidModel.ArmPose armPose(ItemStack stack,
            PlayerMobServant servant, HumanoidArm arm) {
        if (stack.isEmpty()) {
            return net.minecraft.client.model.HumanoidModel.ArmPose.EMPTY;
        }
        if (stack.isEdible() && servant.isUsingFood() && isUsingArm(servant, arm)) {
            return EATING;
        }
        if (servant.isCastingWand() && isCastingArm(servant, arm)) {
            return WAND_CAST;
        }
        if (stack.getItem() instanceof BowItem && servant.isUsingItem() && isUsingArm(servant, arm)) {
            return net.minecraft.client.model.HumanoidModel.ArmPose.BOW_AND_ARROW;
        }
        if (stack.getItem() instanceof CrossbowItem) {
            return servant.isChargingCrossbow()
                    ? net.minecraft.client.model.HumanoidModel.ArmPose.CROSSBOW_CHARGE
                    : net.minecraft.client.model.HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
        if (PlayerMobServant.isTridentLike(stack)
                && (servant.isTridentRangedEngagement() || servant.isUsingTridentAim())) {
            return net.minecraft.client.model.HumanoidModel.ArmPose.THROW_SPEAR;
        }
        if (stack.getItem() instanceof ShieldItem && servant.isUsingShieldAnyHand()
                && isUsingArm(servant, arm)) {
            return net.minecraft.client.model.HumanoidModel.ArmPose.BLOCK;
        }
        if (servant.isUsingTwoHandedBlock() && isUsingArm(servant, arm)) {
            return net.minecraft.client.model.HumanoidModel.ArmPose.BLOCK;
        }
        return net.minecraft.client.model.HumanoidModel.ArmPose.ITEM;
    }

    private static boolean isUsingArm(PlayerMobServant servant, HumanoidArm arm) {
        InteractionHand usedHand = servant.getUsedItemHand();
        HumanoidArm usedArm = usedHand == InteractionHand.MAIN_HAND
                ? servant.getMainArm()
                : servant.getMainArm().getOpposite();
        return arm == usedArm;
    }

    private static boolean isCastingArm(PlayerMobServant servant, HumanoidArm arm) {
        int hand = servant.getCastingHand();
        if (hand < 0) {
            return false;
        }
        HumanoidArm castingArm = hand == 0
                ? servant.getMainArm()
                : servant.getMainArm().getOpposite();
        return arm == castingArm;
    }

    @Override
    public Vec3 getRenderOffset(PlayerMobServant servant, float partialTick) {
        return servant.isCrouching() ? new Vec3(0.0D, -0.125D, 0.0D)
                : super.getRenderOffset(servant, partialTick);
    }

    @Override
    public void scale(PlayerMobServant servant, PoseStack poseStack, float partialTick) {
        poseStack.scale(1.0F, 1.0F, 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(PlayerMobServant servant) {
        if (PlayerMobServantConfig.COPY_OWNER_SKIN.get()) {
            UUID ownerId = servant.getOwnerId();
            if (ownerId != null && Minecraft.getInstance().getConnection() != null) {
                var playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(ownerId);
                if (playerInfo != null && playerInfo.isSkinLoaded()) {
                    return playerInfo.getSkinLocation();
                }
            }
        }

        ResourceLocation namedSkin = resolveNamedSkin(servant);
        if (namedSkin != null) {
            return namedSkin;
        }
        ResourceLocation folderDefault = PlayerMobServantSkinFolder.defaultTexture();
        if (folderDefault != null) {
            return folderDefault;
        }
        logSkinMissOnce(servant);
        return PlayerMobServantConfig.skinLocation();
    }

    private static ResourceLocation resolveNamedSkin(PlayerMobServant servant) {
        String mobName = servant.getMobName();
        ResourceLocation hit = findNamedSkin(mobName);
        if (hit != null) {
            return hit;
        }
        if (servant.isFixedCustomName()) {
            return null;
        }
        Component customName = servant.getCustomName();
        if (customName != null) {
            String matched = PlayerMobServantConfig.matchMobName(customName.getString());
            if (matched != null && !matched.equals(mobName)) {
                return findNamedSkin(matched);
            }
        }
        return null;
    }

    private static ResourceLocation findNamedSkin(String name) {
        ResourceLocation folderSkin = PlayerMobServantSkinFolder.textureFor(name);
        if (folderSkin != null) {
            return folderSkin;
        }
        List<ResourceLocation> candidates = PlayerMobServantConfig.mobSkinCandidates(name);
        if (candidates.isEmpty()) {
            return null;
        }
        ResourceManager resources = Minecraft.getInstance().getResourceManager();
        for (ResourceLocation candidate : candidates) {
            if (resources.getResource(candidate).isPresent()) {
                return candidate;
            }
        }
        return null;
    }

    private static void logSkinMissOnce(PlayerMobServant servant) {
        String mobName = servant.getMobName();
        Component customName = servant.getCustomName();
        String customNameText = customName == null ? "" : customName.getString();
        if (mobName.isEmpty() && customNameText.isEmpty()) {
            return;
        }
        String resolvedName = mobName.isEmpty()
                ? PlayerMobServantConfig.matchMobName(customNameText) : mobName;
        List<ResourceLocation> candidates = PlayerMobServantConfig.mobSkinCandidates(resolvedName);
        if (candidates.isEmpty()) {
            return;
        }
        if (!LOGGED_SKIN_MISSES.add(mobName + '\u0000' + customNameText)) {
            return;
        }
        LOGGER.warn("[goety_supreme] 玩家生物仆从皮肤未命中：mobName='{}' customName='{}'，"
                        + "按顺序查过 {}；资源包里若确有这张皮肤，请把它的实际路径反馈给我们。",
                mobName, customNameText, candidates);
    }
}
