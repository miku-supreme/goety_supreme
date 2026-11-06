package cn.blockforge.generated.goetysupreme.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
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
import cn.blockforge.generated.goetysupreme.entity.MutantZombieServant;
import cn.blockforge.generated.goetysupreme.renderer.MutantZombieServantArmorLayer;
import cn.blockforge.generated.goetysupreme.model.MutantZombieServantModel;

public final class MutantZombieServantArmorLayer
        extends RenderLayer<MutantZombieServant, MutantZombieServantModel> {

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

    private static final BoneArmorRig.Box SKIRT_SRC = new BoneArmorRig.Box(0.0F, 4.0F, 0.0F, 9.0F, 8.0F, 1.0F);

    private static final float SKIRT_TOP = -3.0F;

    private static final float SKIRT_HEIGHT = 13.0F;

    private final Map<EquipmentSlot, BoneArmorRig> rigs = new EnumMap<>(EquipmentSlot.class);

    private final HumanoidArmorModel<LivingEntity> outer;
    private final HumanoidArmorModel<LivingEntity> inner;

    public MutantZombieServantArmorLayer(
            RenderLayerParent<MutantZombieServant, MutantZombieServantModel> parent,
            EntityRendererProvider.Context context) {
        super(parent);
        this.outer = new HumanoidArmorModel<>(
                context.getModelSet().bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR));
        this.inner = new HumanoidArmorModel<>(
                context.getModelSet().bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR));
        MutantZombieServantModel model = getParentModel();

        ModelPart[] head = model.chain("pelvis", "waist", "chest", "head");
        BoneArmorRig headRig = new BoneArmorRig();
        headRig.attach(this.outer.head, HEAD_OUT, HEAD_OUT, head);
        headRig.attach(this.outer.hat, HAT_OUT, HAT_OUT, head);
        this.rigs.put(EquipmentSlot.HEAD, headRig);

        ModelPart[] chest = model.chain("pelvis", "waist", "chest");
        ModelPart[] armR = model.chain("pelvis", "waist", "chest", "arm1");
        ModelPart[] armL = model.chain("pelvis", "waist", "chest", "arm2");
        BoneArmorRig chestRig = new BoneArmorRig();
        chestRig.attach(this.outer.body, BODY_OUT, new BoneArmorRig.Box(0.0F, -6.0F, 0.0F, 25.0F, 13.0F, 17.0F), chest);
        BoneArmorRig.Box upperArm = new BoneArmorRig.Box(0.0F, 8.0F, 0.0F, 6.8F, 16.8F, 6.8F);
        chestRig.attach(this.outer.rightArm, ARM_R_OUT, upperArm, armR);
        chestRig.attach(this.outer.leftArm, ARM_L_OUT, upperArm, armL);
        this.rigs.put(EquipmentSlot.CHEST, chestRig);

        BoneArmorRig legsRig = new BoneArmorRig();
        legsRig.attach(this.inner.body, BODY_IN,
                BoneArmorRig.fitVisibleBand(BoneArmorRig.LEGGINGS_BODY_FROM, BoneArmorRig.LEGGINGS_BODY_TO,
                        -8.0F, 2.0F, 16.5F, 14.5F),
                model.chain("pelvis", "waist"));
        BoneArmorRig.Box thigh = BoneArmorRig.fitVisibleBand(
                BoneArmorRig.LEGGINGS_LEG_FROM, BoneArmorRig.LEGGINGS_LEG_TO,
                -0.5F, 11.5F, 7.2F, 7.2F);
        legsRig.attach(this.inner.rightLeg, LEG_IN, thigh, model.chain("pelvis", "leg1"));
        legsRig.attach(this.inner.leftLeg, LEG_IN, thigh, model.chain("pelvis", "leg2"));
        this.rigs.put(EquipmentSlot.LEGS, legsRig);

        BoneArmorRig feetRig = new BoneArmorRig();
        BoneArmorRig.Box boot = BoneArmorRig.fitVisibleBand(
                BoneArmorRig.BOOT_FROM, BoneArmorRig.BOOT_TO,
                -0.5F, 8.5F, 7.2F, 7.2F);
        feetRig.attach(this.outer.rightLeg, LEG_R_OUT, boot, model.chain("pelvis", "leg1", "fore_leg1"));
        feetRig.attach(this.outer.leftLeg, LEG_R_OUT, boot, model.chain("pelvis", "leg2", "fore_leg2"));
        this.rigs.put(EquipmentSlot.FEET, feetRig);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffer, int light, MutantZombieServant servant,
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

    private BoneArmorRig rigFor(MutantZombieServant servant, ItemStack stack, EquipmentSlot slot) {
        HumanoidArmorModel<LivingEntity> fallback = slot == EquipmentSlot.LEGS ? this.inner : this.outer;
        HumanoidModel<?> armorModel = ForgeHooksClient.getArmorModel(servant, stack, slot, fallback)
                instanceof HumanoidModel<?> humanoid ? humanoid : null;
        if (armorModel == null || armorModel == fallback) {
            return this.rigs.get(slot);
        }
        return customRig(armorModel, slot);
    }

    private BoneArmorRig customRig(HumanoidModel<?> armor, EquipmentSlot slot) {
        MutantZombieServantModel model = getParentModel();
        BoneArmorRig rig = new BoneArmorRig();
        switch (slot) {
            case HEAD -> rig.attach(armor.head, HEAD_OUT, HEAD_OUT,
                    model.chain("pelvis", "waist", "chest", "head"));
            case CHEST -> {
                ModelPart[] chest = model.chain("pelvis", "waist", "chest");
                BoneArmorRig.Box bodyTarget = new BoneArmorRig.Box(0.0F, -6.0F, 0.0F, 25.0F, 13.0F, 17.0F);
                ModelPart skirt = skirtPart(armor.body);
                if (skirt == null) {
                    rig.attach(armor.body, BODY_OUT, bodyTarget, chest);
                } else {
                    rig.attachHiding(armor.body, BODY_OUT, bodyTarget, new ModelPart[] {skirt}, chest);
                    rig.attach(skirt, SKIRT_SRC, skirtTarget(bodyTarget), chest);
                }
                BoneArmorRig.Box arm = new BoneArmorRig.Box(0.0F, 8.0F, 0.0F, 6.8F, 16.8F, 6.8F);
                rig.attach(armor.rightArm, ARM_R_OUT, arm, model.chain("pelvis", "waist", "chest", "arm1"));
                rig.attach(armor.leftArm, ARM_L_OUT, arm, model.chain("pelvis", "waist", "chest", "arm2"));
            }
            case LEGS -> {
                rig.attach(armor.body, BODY_IN, new BoneArmorRig.Box(0.0F, -3.0F, 0.0F, 16.5F, 10.0F, 14.5F),
                        model.chain("pelvis", "waist"));
                BoneArmorRig.Box thigh = new BoneArmorRig.Box(0.0F, 5.5F, 0.0F, 7.2F, 12.0F, 7.2F);
                rig.attach(armor.rightLeg, LEG_IN, thigh, model.chain("pelvis", "leg1"));
                rig.attach(armor.leftLeg, LEG_IN, thigh, model.chain("pelvis", "leg2"));
            }
            case FEET -> {
                BoneArmorRig.Box boot = new BoneArmorRig.Box(0.0F, 4.0F, 0.0F, 7.2F, 9.0F, 7.2F);
                rig.attach(armor.rightLeg, LEG_R_OUT, boot, model.chain("pelvis", "leg1", "fore_leg1"));
                rig.attach(armor.leftLeg, LEG_R_OUT, boot, model.chain("pelvis", "leg2", "fore_leg2"));
            }
            default -> {
                return null;
            }
        }
        return rig;
    }

    private static ModelPart skirtPart(ModelPart body) {
        if (!body.hasChild("cloth")) {
            return null;
        }
        ModelPart cloth = body.getChild("cloth");
        if (!cloth.hasChild("cape")) {
            return null;
        }
        ModelPart cape = cloth.getChild("cape");
        return cape.hasChild("cape1") ? cape.getChild("cape1") : null;
    }

    private static BoneArmorRig.Box skirtTarget(BoneArmorRig.Box bodyTarget) {
        float widthScale = bodyTarget.w() / BODY_OUT.w();
        float depthScale = bodyTarget.d() / BODY_OUT.d();
        float z = bodyTarget.z() + (BODY_OUT.d() / 2.0F + 0.5F) * depthScale + 0.8F;
        return new BoneArmorRig.Box(0.0F, SKIRT_TOP + SKIRT_HEIGHT / 2.0F, z,
                SKIRT_SRC.w() * widthScale, SKIRT_HEIGHT, SKIRT_SRC.d());
    }
}
