package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.entity.SerponFireballEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlameWizard;

public class SerponFlameWizard extends AbstractSerpon {

    @Override
    public boolean acceptsEquipment(net.minecraft.world.item.ItemStack stack) {
        return isAnyJerotesItem(stack, "serpon_wizard_helmet", "serpon_wizard_chestplate",
                "serpon_wizard_leggings", "serpon_wizard_boots");
    }

    @Override
    public void buildOffers(net.minecraft.world.item.trading.MerchantOffers offers) {
        ItemStack alloy = jerotesStack("serpon_alloy_ingot", 1);
        if (alloy.isEmpty()) {
            return;
        }
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                new ItemStack(net.minecraft.world.item.Items.GOLD_INGOT, 3), alloy.copy(),
                12, 10, 0.0F), -1, false);
        for (String piece : new String[] {"serpon_wizard_helmet", "serpon_wizard_chestplate",
                "serpon_wizard_leggings", "serpon_wizard_boots"}) {
            addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                    jerotesStack("serpon_alloy_ingot", 16), jerotesStack(piece, 1), 12, 20, 0.0F),
                    -1, false);
        }
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 2), jerotesStack("serpon_helmet", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 5), jerotesStack("serpon_chestplate", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 3), jerotesStack("serpon_leggings", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 1), jerotesStack("serpon_boots", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 1), jerotesStack("serpon_sword", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 2), jerotesStack("serpon_spiked_shield", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 1), jerotesStack("serpon_blowpipe", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 1), jerotesStack("serpon_javelin", 1), 12, 20, 0.0F), -1, false);
        addTrade(offers, new net.minecraft.world.item.trading.MerchantOffer(
                jerotesStack("serpon_alloy_ingot", 1), jerotesStack("serpon_dagger", 1), 12, 20, 0.0F), -1, false);
    }

    private static final int SPELL_LEVEL = 4;

    private static final int BASE_SPELL_LEVEL = 3;

    private static final int NORMAL_SPELL_LEVEL = 3;

    private static final int MIN_SPELL_LEVEL = 2;

    private static final double FOLLOW_RANGE = 32.0D;

    private static final double FLAMES_RANGE_SQR = 8.0D;

    private static final double FIREBALL_RANGE = 36.0D;

    private static final int CAST_COOLDOWN = 60;

    private static final int FLAMES_CAST_TICKS = 59;

    private static final int FLAMES_INTERVAL = 2;

    private static final int SPELL_TICK_AFTER_CAST = 20;

    private int castCooldown;

    private int flamesCastTicks;

    private int flamesSpellLevel = SPELL_LEVEL;

    private LivingEntity flamesTarget;

    private int spellOrderIndex;

    private int comboCooldown;

    private int comboHits;

    private boolean comboFinisherPending;

    private static final double COMBO_ENGAGE_RANGE = 12.0D;

    private static final int COMBO_FINISHER_HITS = 3;

    public SerponFlameWizard(EntityType<? extends AbstractSerpon> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 30;
        setCombatStyle(4);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FLYING_SPEED, 0.5D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/serpon/serpon_flame_wizard.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SERPON_FLAME_WIZARD;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new FlameWizardComboGoal(this));
        this.goalSelector.addGoal(2, new FlameWizardSpellGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, true) {
            @Override
            public double getAttackReachSqr(LivingEntity target) {
                return SerponFlameWizard.this.meleeReachSqr(target);
            }
        });
    }

    public boolean holdsPolearm() {
        ItemStack stack = getMainHandItem();
        if (stack.isEmpty()) {
            return false;
        }
        if (stack.getItem() instanceof net.minecraft.world.item.TridentItem) {
            return true;
        }
        if (FellSpearItem.isSpear(stack)) {
            return true;
        }
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null) {
            return false;
        }
        String ns = id.getNamespace();
        if (!"jerotes".equals(ns) && !"jerotesvillage".equals(ns)) {
            return false;
        }
        String path = id.getPath();
        return path.contains("spear") || path.contains("pike")
                || path.contains("glaive") || path.contains("halberd");
    }

    public static boolean isSpearItem(ItemStack stack) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return id != null && id.getPath().contains("spear");
    }

    public int comboCooldown() {
        return comboCooldown;
    }

    public void setComboCooldown(int ticks) {
        this.comboCooldown = ticks;
    }

    public int addComboHit() {
        return ++this.comboHits;
    }

    public void resetComboHits() {
        this.comboHits = 0;
    }

    public boolean isComboFinisherPending() {
        return comboFinisherPending;
    }

    public void requestComboFinisher() {
        this.comboFinisherPending = true;
        this.comboHits = 0;
        this.castCooldown = 0;
    }

    public void clearComboFinisher() {
        this.comboFinisherPending = false;
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        equipJerotes(EquipmentSlot.HEAD, "serpon_wizard_helmet");
        equipJerotes(EquipmentSlot.CHEST, "serpon_wizard_chestplate");
        equipJerotes(EquipmentSlot.LEGS, "serpon_wizard_leggings");
        equipJerotes(EquipmentSlot.FEET, "serpon_wizard_boots");
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            setDropChance(slot, 0.0F);
        }
    }

    @Override
    public void tickExtra() {
        if (castCooldown > 0 && flamesCastTicks <= 0) {
            castCooldown--;
        }
        tickFlamesCast();
    }

    private void tickFlamesCast() {
        if (flamesCastTicks <= 0) {
            return;
        }
        if (flamesCastTicks % FLAMES_INTERVAL == 0) {
            LivingEntity target = flamesTarget;
            if (target == null || !target.isAlive()) {
                target = getTarget();
            }
            if (target != null && target.isAlive() && canAttack(target)) {
                spawnFlames(target, flamesSpellLevel);
                if (!level().isClientSide) {
                    swing(InteractionHand.MAIN_HAND, true);
                    playSound(jerotesSound("magic_serpon_flames", SoundEvents.BLAZE_SHOOT), 1.0F, 1.0F);
                }
            }
        }
        flamesCastTicks--;
    }

    public boolean isFireballOnly() {
        return getCustomName() != null
                && "Fireball".equals(ChatFormatting.stripFormatting(getCustomName().getString()));
    }

    public int castCooldown() {
        return castCooldown;
    }

    public void setCastCooldown(int ticks) {
        this.castCooldown = ticks;
    }

    public void castAt(LivingEntity target) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        int spellLevel = rollSpellLevel();
        if (chooseFlamesSpell(target)) {
            castFlamesSpell(target, spellLevel);
        } else {
            castFireballSpell(target, spellLevel);
        }
    }

    private int rollSpellLevel() {
        int range = SPELL_LEVEL - MIN_SPELL_LEVEL + 1;
        int[] weights = new int[range];
        int totalWeight = 0;
        for (int level = MIN_SPELL_LEVEL; level <= SPELL_LEVEL; ++level) {
            int weight = 1 + (SPELL_LEVEL - MIN_SPELL_LEVEL) - Math.abs(level - NORMAL_SPELL_LEVEL);
            if (weight < 1) {
                weight = 1;
            }
            weights[level - MIN_SPELL_LEVEL] = weight;
            totalWeight += weight;
        }
        int roll = totalWeight > 0 ? random.nextInt(totalWeight) : 0;
        int cumulative = 0;
        for (int i = 0; i < weights.length; ++i) {
            cumulative += weights[i];
            if (roll < cumulative) {
                return Math.max(BASE_SPELL_LEVEL, MIN_SPELL_LEVEL + i);
            }
        }
        return Math.max(BASE_SPELL_LEVEL, NORMAL_SPELL_LEVEL);
    }

    private boolean chooseFlamesSpell(LivingEntity target) {
        boolean flamesAvailable = !isFireballOnly()
                && distanceToSqr(target) <= FLAMES_RANGE_SQR;
        if (!flamesAvailable) {
            return false;
        }
        if (random.nextFloat() < 0.5F) {
            return random.nextBoolean();
        }
        boolean pick = (spellOrderIndex % 2) == 0;
        spellOrderIndex++;
        return pick;
    }

    private void castFireballSpell(LivingEntity target, int spellLevel) {
        castFeedback("magic_serpon_fireball", SoundEvents.GHAST_SHOOT,
                "serpon_fireball_display", ParticleTypes.FLAME);
        spawnFireball(target, spellLevel);
    }

    private void castFlamesSpell(LivingEntity target, int spellLevel) {
        castFeedback("magic_serpon_flames", SoundEvents.BLAZE_SHOOT,
                "serpon_flames_display", ParticleTypes.FLAME);
        spawnFlames(target, spellLevel);
        this.flamesTarget = target;
        this.flamesSpellLevel = spellLevel;
        this.flamesCastTicks = FLAMES_CAST_TICKS;
        setCastTicks(SPELL_TICK_AFTER_CAST);
    }

    public boolean isSpellHumanoidTwoHanded() {
        return true;
    }

    @Override
    public boolean supportsSpellAttack() {
        return false;
    }

    @Override
    public boolean wantsToPickUp(net.minecraft.world.item.ItemStack stack) {
        if (stack.canEquip(EquipmentSlot.HEAD, this)
                || stack.canEquip(EquipmentSlot.CHEST, this)
                || stack.canEquip(EquipmentSlot.LEGS, this)
                || stack.canEquip(EquipmentSlot.FEET, this)
                || stack.canEquip(EquipmentSlot.MAINHAND, this)
                || stack.canEquip(EquipmentSlot.OFFHAND, this)) {
            return false;
        }
        return super.wantsToPickUp(stack);
    }

    private void spawnFlames(LivingEntity target, int spellLevel) {
        if (level().isClientSide) {
            return;
        }
        if (target != null && target.isAlive()) {
            getLookControl().setLookAt(target, 360.0F, 360.0F);
            lookAt(target, 360.0F, 360.0F);
        }
        SerponFlamesEntity flames = new SerponFlamesEntity(level(), this, spellLevel);
        flames.setDamageBonus(getSummonPotency());
        flames.setPos(getX(), getY(0.7D) - flames.getBbHeight() / 2.0D, getZ());
        level().addFreshEntity(flames);
    }

    private void spawnFireball(LivingEntity target, int spellLevel) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        Vec3 dir = target.getEyePosition().subtract(getEyePosition());
        SerponFireballEntity fireball = new SerponFireballEntity(level(), this, spellLevel);
        fireball.setDamageBonus(getSummonPotency());
        fireball.setPos(getX(), getY(0.7D) - fireball.getBbHeight() / 2.0D, getZ());
        fireball.shootTowards(dir, 1.0F);
        level().addFreshEntity(fireball);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("CastCooldown", this.castCooldown);
        tag.putInt("FlamesCastTicks", this.flamesCastTicks);
        tag.putInt("FlamesSpellLevel", this.flamesSpellLevel);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.castCooldown = tag.getInt("CastCooldown");
        this.flamesCastTicks = tag.getInt("FlamesCastTicks");
        if (tag.contains("FlamesSpellLevel")) {
            this.flamesSpellLevel = tag.getInt("FlamesSpellLevel");
        }
    }

    private static final class FlameWizardSpellGoal extends Goal {
        private final SerponFlameWizard wizard;

        FlameWizardSpellGoal(SerponFlameWizard wizard) {
            this.wizard = wizard;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = wizard.getTarget();
            if (target == null || !target.isAlive() || !wizard.canAttack(target)) {
                return false;
            }
            if (wizard.holdsPolearm() && !wizard.isComboFinisherPending()) {
                return false;
            }
            return wizard.distanceToSqr(target) <= FOLLOW_RANGE * FOLLOW_RANGE;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = wizard.getTarget();
            if (target == null) {
                return;
            }
            wizard.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (wizard.isCasting()) {
                wizard.getNavigation().stop();
                return;
            }
            if (wizard.castCooldown() > 0) {
                return;
            }
            if (wizard.distanceToSqr(target) > FIREBALL_RANGE * FIREBALL_RANGE) {
                wizard.getNavigation().moveTo(target, 1.0D);
                return;
            }
            wizard.getNavigation().stop();
            wizard.castAt(target);
            wizard.setCastCooldown(CAST_COOLDOWN);
            wizard.clearComboFinisher();
        }
    }

    private static final class FlameWizardComboGoal extends Goal {
        private final SerponFlameWizard wizard;

        FlameWizardComboGoal(SerponFlameWizard wizard) {
            this.wizard = wizard;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = wizard.getTarget();
            if (target == null || !target.isAlive() || !wizard.canAttack(target)) {
                return false;
            }
            if (!wizard.holdsPolearm() || wizard.isCasting() || wizard.isComboFinisherPending()) {
                return false;
            }
            return wizard.distanceToSqr(target) <= COMBO_ENGAGE_RANGE * COMBO_ENGAGE_RANGE;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = wizard.getTarget();
            if (target == null) {
                return;
            }
            wizard.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (wizard.comboCooldown() > 0) {
                wizard.setComboCooldown(wizard.comboCooldown() - 1);
            }
            boolean spear = isSpearItem(wizard.getMainHandItem());
            double weaponReach = spear ? 3.5D : 4.5D;
            double reachSqr = Math.max(wizard.meleeReachSqr(target), weaponReach * weaponReach);
            if (wizard.distanceToSqr(target) > reachSqr) {
                wizard.getNavigation().moveTo(target, 1.0D);
                return;
            }
            wizard.getNavigation().stop();
            if (wizard.comboCooldown() > 0) {
                return;
            }
            if (spear) {
                thrust(target);
                wizard.setComboCooldown(20);
            } else {
                sweep(weaponReach);
                wizard.setComboCooldown(30);
            }
            if (wizard.addComboHit() >= COMBO_FINISHER_HITS) {
                wizard.requestComboFinisher();
            }
        }

        private void thrust(LivingEntity target) {
            wizard.swing(InteractionHand.MAIN_HAND);
            if (!wizard.level().isClientSide) {
                float damage = (float) (wizard.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5D);
                target.hurt(wizard.damageSources().mobAttack(wizard), damage);
            }
        }

        private void sweep(double reach) {
            wizard.swing(InteractionHand.MAIN_HAND);
            if (wizard.level().isClientSide) {
                return;
            }
            AABB box = wizard.getBoundingBox().inflate(reach, 1.0D, reach);
            Vec3 look = wizard.getLookAngle();
            for (LivingEntity victim : wizard.level().getEntitiesOfClass(LivingEntity.class, box)) {
                if (victim == wizard || !wizard.canAttack(victim)) {
                    continue;
                }
                if (wizard.distanceToSqr(victim) > reach * reach) {
                    continue;
                }
                Vec3 to = victim.position().subtract(wizard.position());
                if (look.dot(to.normalize()) < 0.5D) {
                    continue;
                }
                float damage = (float) (wizard.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.2D);
                victim.hurt(wizard.damageSources().mobAttack(wizard), damage);
            }
        }
    }
}
