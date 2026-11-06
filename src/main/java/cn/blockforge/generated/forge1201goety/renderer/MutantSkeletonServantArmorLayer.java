package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import cn.blockforge.generated.goetysupreme.util.BoneArmorRig;
import cn.blockforge.generated.goetysupreme.entity.MutantSkeletonServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantSkeletonServantArmorLayer;
import cn.blockforge.generated.goetysupreme.model.MutantSkeletonServantModel;

public final class MutantSkeletonServantArmorLayer
        extends RenderLayer<MutantSkeletonServant, MutantSkeletonServantModel> {

    private static final EquipmentSlot[] SLOTS = {
        EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HEAD
    };

    private static final BoneArmorRig.Box HEAD_OUT = new BoneArmorRig.Box(0.0F, -4.0F, 0.0F, 10.0F, 10.0F, 10.0F);
    private static final BoneArmorRig.Box HAT_OUT = new BoneArmorRig.Box(0.0F, -4.0F, 0.0F, 11.0F, 11.0F, 11.0F);
    private static final BoneArmorRig.Box BODY_OUT = new BoneArmorRig.Box(0.0F, 6.0F, 0.0F, 10.0F, 14.0F, 6.0F);
    private static final BoneArmorRig.Box BODY_IN = new BoneArmorRig.Box(0.0F, 6.0F, 0.0F, 9.0F, 13.0F, 5.0F);
    private static final BoneArmorRig.Box ARM_R_OUT = new BoneArmorRig.Box(-1.0F, 4.0F, 0.0F, 6.0F, 14.0F, 6.0F);
    private static final BoneArmorRig.Box ARM_L_OUT = new BoneArmorRig.Box(1.0F, 4.0F, 0.0F, 6.0F, 14.0F, 6.0F);
    private static final BoneArmorRig.Box LEG_IN = new BoneArmorRig.Box(0.0F, 6.0F, 0.0F, 4.8F, 12.8F, 4.8F);
    private static final BoneArmorRig.Box LEG_R_OUT = new BoneArmorRig.Box(0.0F, 6.0F, 0.0F, 5.8F, 13.8F, 5.8F);

    private static final String[] SPINE_TO_SHOULDER_R =
            {"base", "pelvis", "waist", "middle1", "middle2", "middle3", "shoulder1"};
    private static final String[] SPINE_TO_SHOULDER_L =
            {"base", "pelvis", "waist", "middle1", "middle2", "middle3", "shoulder2"};

    private final Map<EquipmentSlot, BoneArmorRig> rigs = new EnumMap<>(EquipmentSlot.class);

    private final HumanoidArmorModel<LivingEntity> outer;
    private final HumanoidArmorModel<LivingEntity> inner;

    public MutantSkeletonServantArmorLayer(
            RenderLayerParent<MutantSkeletonServant, MutantSkeletonServantModel> parent,
            EntityRendererProvider.Context context) {
        super(parent);
        this.outer = new HumanoidArmorModel<>(
                context.getModelSet().bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR));
        this.inner = new HumanoidArmorModel<>(
                context.getModelSet().bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR));
        MutantSkeletonServantModel model = getParentModel();

        BoneArmorRig headRig = new BoneArmorRig();
        String[] head = {"base", "pelvis", "waist", "middle1", "middle2", "middle3", "neck", "head", "inner_head"};
        headRig.attach(this.outer.head, HEAD_OUT, HEAD_OUT, model.chain(head));
        headRig.attach(this.outer.hat, HAT_OUT, HAT_OUT, model.chain(head));
        this.rigs.put(EquipmentSlot.HEAD, headRig);

        BoneArmorRig chestRig = new BoneArmorRig();
        chestRig.attach(this.outer.body, BODY_OUT, new BoneArmorRig.Box(0.0F, -2.0F, 0.0F, 8.0F, 16.0F, 5.5F),
                model.chain("base", "pelvis", "waist", "middle1", "middle2"));
        BoneArmorRig.Box pauldron = new BoneArmorRig.Box(0.0F, -1.5F, 0.0F, 9.0F, 7.0F, 7.0F);
        chestRig.attach(this.outer.rightArm, ARM_R_OUT, pauldron, model.chain(SPINE_TO_SHOULDER_R));
        chestRig.attach(this.outer.leftArm, ARM_L_OUT, pauldron, model.chain(SPINE_TO_SHOULDER_L));
        BoneArmorRig.Box upperArm = new BoneArmorRig.Box(0.0F, 6.0F, 0.0F, 6.0F, 14.0F, 6.0F);
        BoneArmorRig.Box foreArm = new BoneArmorRig.Box(0.0F, 7.0F, 0.0F, 6.0F, 14.0F, 6.0F);
        String[] armR = concat(SPINE_TO_SHOULDER_R, "arm1");
        String[] armL = concat(SPINE_TO_SHOULDER_L, "arm2");
        chestRig.attach(this.outer.rightArm, ARM_R_OUT, upperArm, model.chain(armR));
        chestRig.attach(this.outer.leftArm, ARM_L_OUT, upperArm, model.chain(armL));
        chestRig.attach(this.outer.rightArm, ARM_R_OUT, foreArm,
                model.chain(concat(armR, "inner_arm1", "fore_arm1", "inner_fore_arm1")));
        chestRig.attach(this.outer.leftArm, ARM_L_OUT, foreArm,
                model.chain(concat(armL, "inner_arm2", "fore_arm2", "inner_fore_arm2")));
        this.rigs.put(EquipmentSlot.CHEST, chestRig);

        BoneArmorRig legsRig = new BoneArmorRig();
        legsRig.attach(this.inner.body, BODY_IN,
                BoneArmorRig.fitVisibleBand(BoneArmorRig.LEGGINGS_BODY_FROM, BoneArmorRig.LEGGINGS_BODY_TO,
                        -8.0F, 2.0F, 9.6F, 7.6F),
                model.chain("base", "pelvis"));
        BoneArmorRig.Box thigh = BoneArmorRig.fitVisibleBand(
                BoneArmorRig.LEGGINGS_LEG_FROM, BoneArmorRig.LEGGINGS_LEG_TO,
                -0.5F, 12.5F, 5.6F, 5.6F);
        legsRig.attach(this.inner.rightLeg, LEG_IN, thigh, model.chain("base", "pelvis", "leg1", "inner_leg1"));
        legsRig.attach(this.inner.leftLeg, LEG_IN, thigh, model.chain("base", "pelvis", "leg2", "inner_leg2"));
        this.rigs.put(EquipmentSlot.LEGS, legsRig);

        BoneArmorRig feetRig = new BoneArmorRig();
        BoneArmorRig.Box boot = BoneArmorRig.fitVisibleBand(
                BoneArmorRig.BOOT_FROM, BoneArmorRig.BOOT_TO,
                -0.5F, 12.5F, 5.6F, 5.6F);
        feetRig.attach(this.outer.rightLeg, LEG_R_OUT, boot,
                model.chain("base", "pelvis", "leg1", "inner_leg1", "fore_leg1", "inner_fore_leg1"));
        feetRig.attach(this.outer.leftLeg, LEG_R_OUT, boot,
                model.chain("base", "pelvis", "leg2", "inner_leg2", "fore_leg2", "inner_fore_leg2"));
        this.rigs.put(EquipmentSlot.FEET, feetRig);
    }

    private static String[] concat(String[] prefix, String... extra) {
        String[] result = new String[prefix.length + extra.length];
        System.arraycopy(prefix, 0, result, 0, prefix.length);
        System.arraycopy(extra, 0, result, prefix.length, extra.length);
        return result;
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int light, MutantSkeletonServant servant,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {
        for (EquipmentSlot slot : SLOTS) {
            ItemStack stack = servant.getItemBySlot(slot);
            if (!(stack.getItem() instanceof ArmorItem armorItem) || armorItem.getEquipmentSlot() != slot) {
                continue;
            }
            BoneArmorRig rig = this.rigFor(servant, stack, slot);
            if (rig == null) {
                continue;
            }
            VertexConsumer consumer = buffer.getBuffer(RenderType.armorCutoutNoCull(
                    ResourceLocation.parse(BoneArmorRig.armorTexture(servant, stack, slot, null))));
            float[] dye = BoneArmorRig.dyeColor(stack);
            if (dye != null) {
                rig.render(pose, consumer, light, dye[0], dye[1], dye[2]);
                VertexConsumer overlay = buffer.getBuffer(RenderType.armorCutoutNoCull(
                        ResourceLocation.parse(BoneArmorRig.armorTexture(servant, stack, slot, "overlay"))));
                rig.render(pose, overlay, light, 1.0F, 1.0F, 1.0F);
            } else {
                rig.render(pose, consumer, light, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    private BoneArmorRig rigFor(MutantSkeletonServant servant, ItemStack stack, EquipmentSlot slot) {
        HumanoidArmorModel<LivingEntity> fallback = slot == EquipmentSlot.LEGS ? this.inner : this.outer;
        HumanoidModel<?> armorModel = ForgeHooksClient.getArmorModel(servant, stack, slot, fallback)
                instanceof HumanoidModel<?> humanoid ? humanoid : null;
        if (armorModel == null || armorModel == fallback) {
            return this.rigs.get(slot);
        }
        return customRig(armorModel, slot);
    }

    private BoneArmorRig customRig(HumanoidModel<?> armor, EquipmentSlot slot) {
        MutantSkeletonServantModel model = getParentModel();
        BoneArmorRig rig = new BoneArmorRig();
        switch (slot) {
            case HEAD -> rig.attach(armor.head, HEAD_OUT, HEAD_OUT, model.chain(
                    "base", "pelvis", "waist", "middle1", "middle2", "middle3", "neck", "head", "inner_head"));
            case CHEST -> {
                rig.attach(armor.body, BODY_OUT, new BoneArmorRig.Box(0.0F, -2.0F, 0.0F, 8.0F, 16.0F, 5.5F),
                        model.chain("base", "pelvis", "waist", "middle1", "middle2"));
                BoneArmorRig.Box arm = new BoneArmorRig.Box(0.0F, 6.0F, 0.0F, 6.0F, 14.0F, 6.0F);
                rig.attach(armor.rightArm, ARM_R_OUT, arm, model.chain(concat(SPINE_TO_SHOULDER_R, "arm1")));
                rig.attach(armor.leftArm, ARM_L_OUT, arm, model.chain(concat(SPINE_TO_SHOULDER_L, "arm2")));
            }
            case LEGS -> {
                rig.attach(armor.body, BODY_IN, new BoneArmorRig.Box(0.0F, -3.0F, 0.0F, 9.6F, 10.0F, 7.6F),
                        model.chain("base", "pelvis"));
                BoneArmorRig.Box thigh = new BoneArmorRig.Box(0.0F, 6.0F, 0.0F, 5.6F, 13.0F, 5.6F);
                rig.attach(armor.rightLeg, LEG_IN, thigh, model.chain("base", "pelvis", "leg1", "inner_leg1"));
                rig.attach(armor.leftLeg, LEG_IN, thigh, model.chain("base", "pelvis", "leg2", "inner_leg2"));
            }
            case FEET -> {
                BoneArmorRig.Box boot = new BoneArmorRig.Box(0.0F, 6.0F, 0.0F, 5.6F, 13.0F, 5.6F);
                rig.attach(armor.rightLeg, LEG_R_OUT, boot,
                        model.chain("base", "pelvis", "leg1", "inner_leg1", "fore_leg1", "inner_fore_leg1"));
                rig.attach(armor.leftLeg, LEG_R_OUT, boot,
                        model.chain("base", "pelvis", "leg2", "inner_leg2", "fore_leg2", "inner_fore_leg2"));
            }
            default -> {
                return null;
            }
        }
        return rig;
    }
}
