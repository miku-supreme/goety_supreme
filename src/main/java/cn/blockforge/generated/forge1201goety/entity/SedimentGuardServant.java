package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.ai.JerotesRangedWeaponGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.AncientSecondRounderArrowEntity;
import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardServant;
import cn.blockforge.generated.goetysupreme.spell.SedimentSummonSpell;

public class SedimentGuardServant extends AbstractSedimentServant implements RangedAttackMob {

    public SedimentGuardServant(EntityType<? extends SedimentGuardServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
        setShieldLevel(1);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SEDIMENT_GUARD;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/sediment_guard.png";
    }

    @Override
    public EquipmentSlot replacementSlotFor(net.minecraft.world.item.ItemStack held) {
        if (held.isEmpty()) {
            return null;
        }
        if (held.is(GUARD_ARMOR)) {
            return armorSlotFor(held);
        }
        if (held.is(GUARD_SHIELD)) {
            return EquipmentSlot.OFFHAND;
        }
        if (held.is(GUARD_MELEE) || held.is(GUARD_BOW)) {
            return EquipmentSlot.MAINHAND;
        }
        if (JerotesRangedWeaponGoal.isThrowableAmmo(held)) {
            return EquipmentSlot.MAINHAND;
        }
        return null;
    }

    @Override
    public void registerCombatGoals() {
        registerSedimentRangedGoals(this);
        this.goalSelector.addGoal(2, new SedimentMeleeGoal(this, 1.1D, true));
    }

    @Override
    public boolean canRaiseShield() {
        return !holdsBow() && !holdsOtherRangedWeapon();
    }

    public void equipGuaranteedShield() {
        if (!(getMainHandItem().getItem() instanceof BowItem)) {
            equipJerotes(EquipmentSlot.OFFHAND, "ancient_second_rounder_shield");
        }
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        equipJerotes(EquipmentSlot.HEAD, "ancient_second_rounder_helmet");
        equipJerotes(EquipmentSlot.CHEST, "ancient_second_rounder_chestplate");
        equipJerotes(EquipmentSlot.LEGS, "ancient_second_rounder_leggings");
        equipJerotes(EquipmentSlot.FEET, "ancient_second_rounder_boots");
        float roll = random.nextFloat();
        if (RingSetBonuses.ancientSwordExcluded(this)) {
            while (roll > 0.40F && roll <= 0.85F) {
                roll = random.nextFloat();
            }
        }
        if (roll > 0.85F) {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_mace");
        } else if (roll > 0.40F) {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_sword");
        } else if (roll > 0.20F) {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_war_axe");
        } else if (roll > 0.15F) {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_warspear");
        } else {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_bow");
        }
        if (random.nextFloat() > 0.85F) {
            equipJerotes(EquipmentSlot.OFFHAND, "ancient_second_rounder_shield");
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        shootAncientArrow(target);
    }
}
