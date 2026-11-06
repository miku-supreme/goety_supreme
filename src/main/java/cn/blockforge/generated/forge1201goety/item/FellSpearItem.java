package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.common.ForgeMod;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.ai.FellSpearAttackGoal;
import cn.blockforge.generated.goetysupreme.event.FellSpearEvents;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearFirstPersonPose;
import cn.blockforge.generated.goetysupreme.api.FellSpearHitFeedback;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.api.FellSpearKnownMovement;
import cn.blockforge.generated.goetysupreme.api.FellSpearPlayerLike;
import cn.blockforge.generated.goetysupreme.renderer.FellSpearPose;
import cn.blockforge.generated.goetysupreme.network.FellSpearStabPacket;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.mixin.HumanoidFellSpearPoseMixin;
import cn.blockforge.generated.goetysupreme.mixin.LivingEntityFellSpearHitFeedbackMixin;
import cn.blockforge.generated.goetysupreme.mixin.LivingEntityFellSpearSwingMixin;
import cn.blockforge.generated.goetysupreme.mixin.MinecraftFellSpearAttackMixin;
import cn.blockforge.generated.goetysupreme.mixin.ServerGamePacketListenerFellSpearMixin;

public class FellSpearItem extends SwordItem {

    private static final float ATTACK_DAMAGE = 4.0F;

    private static final float PLAYER_BASE_ATTACK = 1.0F;

    private static final float TIER_DAMAGE_BONUS = 3.0F;

    private static final float ATTACK_SPEED = -2.95F;

    private static final int SWING_DURATION_TICKS = 19;

    private static final int DURABILITY = 256;

    private static final int BUSTED_TICKS = 100;

    private final boolean busted;

    private static final int CHARGE_DELAY_TICKS = 12;

    private static final int DAMAGE_WINDOW_TICKS = 225;

    private static final int KNOCKBACK_WINDOW_TICKS = 135;

    private static final int DISMOUNT_WINDOW_TICKS = 50;

    private static final int USE_DURATION = 72000;

    private static final double MIN_RANGE = 2.0D;

    private static final double MAX_RANGE = 4.5D;

    private static final double MIN_CREATIVE_RANGE = 2.0D;

    private static final double MAX_CREATIVE_RANGE = 6.5D;

    private static final double MOB_RANGE_FACTOR = 0.5D;

    private static final double CHARGE_HITBOX_MARGIN = 0.25D;

    private static final double STAB_HITBOX_MARGIN = 0.125D;

    private static final double SWEEP_PADDING = 1.0D;

    private static final double DAMAGE_MIN_RELATIVE_SPEED = 4.6D;

    private static final double KNOCKBACK_MIN_SPEED = 5.1D;

    private static final double DISMOUNT_MIN_SPEED = 8.0D;

    private static final double SPEED_DAMAGE_FACTOR = 0.95D;

    private static final double SPEED_DAMAGE_PER_ATTACK = 0.1D;

    private static final float PLAYER_SPEED_FACTOR = 1.0F;

    private static final float PLAYER_LIKE_SPEED_FACTOR = 0.4F;

    private static final float MOB_SPEED_FACTOR = 0.2F;

    private static final double BASE_EXTRA_KNOCKBACK = 0.4D;

    private static final double KNOCKBACK_HALF = 0.5D;

    private static final float MIN_ATTACK_CHARGE = 0.9F;

    private static final double TICKS_PER_SECOND = 20.0D;

    private static final Tier TIER = new Tier() {
        @Override
        public int getUses() {
            return 1280;
        }

        @Override
        public float getSpeed() {
            return 8.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return TIER_DAMAGE_BONUS;
        }

        @Override
        public int getLevel() {
            return 3;
        }

        @Override
        public int getEnchantmentValue() {
            return 22;
        }

        @Override
        public Ingredient getRepairIngredient() {
            Item ingot = ForgeRegistries.ITEMS.getValue(
                    ResourceLocation.fromNamespaceAndPath("goety", "cursed_ingot"));
            return ingot == null ? Ingredient.EMPTY : Ingredient.of(ingot);
        }

        @Override
        public TagKey<Block> getTag() {
            return BlockTags.NEEDS_DIAMOND_TOOL;
        }
    };

    public FellSpearItem() {
        this(TIER, ATTACK_DAMAGE, DURABILITY, false, true);
    }

    public FellSpearItem(Tier tier, float attackDamage, int durability) {
        this(tier, attackDamage, durability, false);
    }

    public FellSpearItem(Tier tier, float attackDamage, int durability, boolean fireResistant) {
        this(tier, attackDamage, durability, fireResistant, false);
    }

    private FellSpearItem(Tier tier, float attackDamage, int durability, boolean fireResistant,
            boolean busted) {
        super(tier, Math.round(attackDamage - PLAYER_BASE_ATTACK - tier.getAttackDamageBonus()),
                ATTACK_SPEED, properties(durability, fireResistant));
        this.busted = busted;
    }

    public static boolean grantsBusted(ItemStack stack) {
        return stack != null && !stack.isEmpty()
                && stack.getItem() instanceof FellSpearItem spear && spear.busted;
    }

    private static Item.Properties properties(int durability, boolean fireResistant) {
        Item.Properties props = new Item.Properties().durability(durability);
        return fireResistant ? props.fireResistant() : props;
    }

    public static int chargeDelayTicks() {
        return CHARGE_DELAY_TICKS;
    }

    public static boolean isSpear(ItemStack stack) {
        return stack != null && !stack.isEmpty() && stack.getItem() instanceof FellSpearItem;
    }

    public static int damageWindowTicks() {
        return DAMAGE_WINDOW_TICKS;
    }

    public static int knockbackWindowTicks() {
        return KNOCKBACK_WINDOW_TICKS;
    }

    public static int dismountWindowTicks() {
        return DISMOUNT_WINDOW_TICKS;
    }

    public static int swingDurationTicks() {
        return SWING_DURATION_TICKS;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return USE_DURATION;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction != ToolActions.SWORD_SWEEP && super.canPerformAction(stack, toolAction);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        if (!level.isClientSide) {
            playSound(level, player, GeneratedMod.FELL_SPEAR_USE.get(), 1.0F, 1.0F, true);
        }
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity user, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide) {
            return;
        }
        int charged = getUseDuration(stack) - remainingUseDuration;
        if (charged < CHARGE_DELAY_TICKS) {
            return;
        }
        int ticks = charged - CHARGE_DELAY_TICKS;
        if (chargeDamage(level, user, stack, ticks)) {
            playSound(level, user, GeneratedMod.FELL_SPEAR_HIT.get(), 1.0F,
                    0.95F + user.getRandom().nextFloat() * 0.1F, false);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();
        if (!level.isClientSide) {
            playSound(level, attacker, GeneratedMod.FELL_SPEAR_ATTACK.get(), 1.0F, 1.0F, true);
            playSound(level, attacker, GeneratedMod.FELL_SPEAR_HIT.get(), 1.0F, 1.0F, false);
            if (this.busted) {
                target.addEffect(new MobEffectInstance(GoetyEffects.BUSTED.get(), BUSTED_TICKS), attacker);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {
        consumer.accept(new net.minecraftforge.client.extensions.common.IClientItemExtensions() {

            @Override
            public boolean applyForgeHandTransform(com.mojang.blaze3d.vertex.PoseStack poseStack,
                    net.minecraft.client.player.LocalPlayer player, net.minecraft.world.entity.HumanoidArm arm,
                    ItemStack stack, float partialTick, float equipProcess, float swingProcess) {
                if (!(stack.getItem() instanceof FellSpearItem)) {
                    return false;
                }
                if (player.isUsingItem() && player.getUseItem() == stack) {
                    float hitFeedback = player instanceof FellSpearHitFeedback feedback
                            ? feedback.goetySupreme$ticksSinceKineticHit(partialTick) : 0.0F;
                    FellSpearFirstPersonPose.applyUse(poseStack, arm,
                            player.getTicksUsingItem() + partialTick - 1.0F, equipProcess, hitFeedback);
                    return true;
                }
                if (swingProcess > 0.0F) {
                    FellSpearFirstPersonPose.applyStabSwing(poseStack, arm, swingProcess);
                    return true;
                }
                FellSpearFirstPersonPose.applyIdle(poseStack, arm, equipProcess);
                return true;
            }
        });
    }

    public static void stabAttack(Player player, ItemStack stack) {
        Level level = player.level();
        if (level.isClientSide) {
            return;
        }
        if (!isSpear(stack)) {
            return;
        }
        if (player.getAttackStrengthScale(0.5F) < MIN_ATTACK_CHARGE) {
            return;
        }
        Vec3 look = player.getLookAngle();
        double closing = knownMovement(player).dot(look);
        List<EntityHitResult> hits = hitEntitiesAlong(level, player, look, closing,
                effectiveMinRange(player), effectiveMaxRange(player), STAB_HITBOX_MARGIN);
        if (hits.isEmpty()) {
            playSound(level, player, GeneratedMod.FELL_SPEAR_ATTACK.get(), 1.0F, 1.0F, true, true);
            player.swing(InteractionHand.MAIN_HAND);
            player.resetAttackStrengthTicker();
            return;
        }
        float base = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        double yaw = player.getYRot() * (Math.PI / 180.0D);
        double knockStrength = BASE_EXTRA_KNOCKBACK + KNOCKBACK_HALF
                * (player.getAttributeValue(Attributes.ATTACK_KNOCKBACK)
                        + EnchantmentHelper.getKnockbackBonus(player));
        DamageSource source = level.damageSources().playerAttack(player);
        boolean landed = false;
        for (EntityHitResult hit : hits) {
            Entity target = hit.getEntity();
            if (target == player || !canHit(player, target)) {
                continue;
            }
            float damage = base;
            if (target instanceof LivingEntity living) {
                damage += EnchantmentHelper.getDamageBonus(stack, living.getMobType());
            } else {
                damage += EnchantmentHelper.getDamageBonus(stack, MobType.UNDEFINED);
            }
            boolean hurt = target.hurt(source, damage);
            landed |= hurt;
            if (target instanceof LivingEntity living) {
                living.knockback(knockStrength, Mth.sin((float) yaw), -Mth.cos((float) yaw));
                if (hurt) {
                    EnchantmentHelper.doPostHurtEffects(living, player);
                    if (grantsBusted(stack)) {
                        living.addEffect(new MobEffectInstance(GoetyEffects.BUSTED.get(), BUSTED_TICKS), player);
                    }
                }
            } else {
                target.push(-Mth.sin((float) yaw) * knockStrength, 0.1D,
                        Mth.cos((float) yaw) * knockStrength);
            }
            if (hurt) {
                EnchantmentHelper.doPostDamageEffects(player, target);
                player.setLastHurtMob(target);
            }
            if (target.isPassenger()) {
                target.stopRiding();
            }
        }
        if (landed) {
            playSound(level, player, GeneratedMod.FELL_SPEAR_HIT.get(), 1.0F, 1.0F, false);
            stack.hurtAndBreak(1, player, broken -> broken.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        playSound(level, player, GeneratedMod.FELL_SPEAR_ATTACK.get(), 1.0F, 1.0F, true, true);
        player.swing(InteractionHand.MAIN_HAND);
        player.resetAttackStrengthTicker();
    }

    private static boolean chargeDamage(Level level, LivingEntity user, ItemStack stack, int ticks) {
        Vec3 look = user.getLookAngle();
        double speed = kineticMovement(spearMotionSource(user)).dot(look);
        double reachStretch = knownMovement(user).dot(look);
        boolean player = user instanceof Player;
        float factor = spearSpeedFactor(user);

        List<EntityHitResult> hits = hitEntitiesAlong(level, user, look, reachStretch,
                effectiveMinRange(user), effectiveMaxRange(user), CHARGE_HITBOX_MARGIN);
        if (hits.isEmpty()) {
            return false;
        }
        float attackDamage = (float) user.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
        float attackPower = (float) user.getAttributeValue(Attributes.ATTACK_DAMAGE);
        DamageSource source = player
                ? level.damageSources().playerAttack((Player) user)
                : level.damageSources().mobAttack(user);
        double yaw = user.getYRot() * (Math.PI / 180.0D);
        double knockStrength = BASE_EXTRA_KNOCKBACK + KNOCKBACK_HALF
                * (user.getAttributeValue(Attributes.ATTACK_KNOCKBACK)
                        + EnchantmentHelper.getKnockbackBonus(user));
        boolean landed = false;
        for (EntityHitResult hit : hits) {
            Entity target = hit.getEntity();
            if (!canHit(user, target)) {
                continue;
            }
            double relative = Math.max(0.0D, speed - kineticMovement(target).dot(look));
            boolean dealDamage = ticks <= DAMAGE_WINDOW_TICKS
                    && relative >= DAMAGE_MIN_RELATIVE_SPEED * factor;
            boolean knockback = ticks <= KNOCKBACK_WINDOW_TICKS
                    && speed >= KNOCKBACK_MIN_SPEED * factor;
            boolean dismount = ticks <= DISMOUNT_WINDOW_TICKS
                    && speed >= DISMOUNT_MIN_SPEED * factor;
            if (!dealDamage && !knockback && !dismount) {
                continue;
            }
            float damage = attackDamage
                    + (float) Mth.floor(relative * speedDamageFactor(attackPower));
            boolean hurt = dealDamage && target.hurt(source, damage);
            landed |= hurt;
            if (hurt && grantsBusted(stack) && target instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(GoetyEffects.BUSTED.get(), BUSTED_TICKS), user);
            }
            if (knockback) {
                if (target instanceof LivingEntity living) {
                    living.knockback(knockStrength, Mth.sin((float) yaw), -Mth.cos((float) yaw));
                } else {
                    target.push(-Mth.sin((float) yaw) * knockStrength, 0.1D,
                            Mth.cos((float) yaw) * knockStrength);
                }
            }
            if (dismount && target.isPassenger()) {
                target.stopRiding();
                landed = true;
            }
        }
        if (landed) {
            level.broadcastEntityEvent(user, (byte) 2);
        }
        if (landed && player && !stack.isEmpty()) {
            stack.hurtAndBreak(1, user, broken -> broken.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return landed;
    }

    private static double speedDamageFactor(float baseAttack) {
        return SPEED_DAMAGE_FACTOR * (1.0D + SPEED_DAMAGE_PER_ATTACK * baseAttack);
    }

    private static List<EntityHitResult> hitEntitiesAlong(Level level, LivingEntity user, Vec3 look,
            double reachStretch, double minRange, double maxRange, double margin) {
        Vec3 eye = user.getEyePosition();
        Vec3 start = eye.add(look.scale(minRange));
        Vec3 end = eye.add(look.scale(maxRange + Math.max(0.0D, reachStretch)));
        BlockHitResult blockHit = level.clip(new ClipContext(start, end,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, user));
        if (blockHit.getType() != HitResult.Type.MISS) {
            end = blockHit.getLocation();
            if (eye.distanceToSqr(end) < eye.distanceToSqr(start)) {
                return List.of();
            }
        }
        AABB sweep = AABB.ofSize(start, margin, margin, margin)
                .expandTowards(end.subtract(start))
                .inflate(SWEEP_PADDING);
        List<LivingEntity> candidates = level.getEntitiesOfClass(LivingEntity.class, sweep,
                target -> canHit(user, target));
        List<EntityHitResult> hits = new ArrayList<>();
        for (LivingEntity target : candidates) {
            AABB hitBox = target.getBoundingBox();
            if (hitBox.contains(start)) {
                hits.add(new EntityHitResult(target, start));
                continue;
            }
            Optional<Vec3> direct = hitBox.clip(start, end);
            if (direct.isPresent()) {
                hits.add(new EntityHitResult(target, direct.get()));
                continue;
            }
            if (margin <= 0.0D) {
                continue;
            }
            Optional<Vec3> expanded = hitBox.inflate(margin).clip(start, end);
            if (expanded.isEmpty()) {
                continue;
            }
            Vec3 point = expanded.get();
            Vec3 center = hitBox.getCenter();
            BlockHitResult los = level.clip(new ClipContext(point, center,
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, user));
            if (los.getType() != HitResult.Type.MISS) {
                center = los.getLocation();
            }
            hitBox.clip(point, center).ifPresent(refined -> hits.add(new EntityHitResult(target, refined)));
        }
        return hits;
    }

    private static double effectiveMinRange(LivingEntity user) {
        if (user instanceof Player player) {
            if (player.isSpectator()) {
                return 0.0D;
            }
            return player.isCreative() ? MIN_CREATIVE_RANGE : MIN_RANGE;
        }
        if (user instanceof FellSpearPlayerLike like && like.goetySupreme$useSpearAsPlayer()) {
            return MIN_RANGE;
        }
        return MIN_RANGE * MOB_RANGE_FACTOR;
    }

    private static double effectiveMaxRange(LivingEntity user) {
        boolean playerLike = user instanceof FellSpearPlayerLike like && like.goetySupreme$useSpearAsPlayer();
        double base;
        if (user instanceof Player player) {
            base = player.isCreative() ? MAX_CREATIVE_RANGE : MAX_RANGE;
        } else if (playerLike) {
            base = MAX_RANGE;
        } else {
            base = MAX_RANGE * MOB_RANGE_FACTOR;
        }
        return base + entityReachBonus(user);
    }

    private static double entityReachBonus(LivingEntity user) {
        var reach = user.getAttribute(ForgeMod.ENTITY_REACH.get());
        if (reach == null) {
            return 0.0D;
        }
        return Math.max(0.0D, reach.getValue() - 3.0D);
    }

    private static Vec3 kineticMovement(Entity entity) {
        Vec3 velocity = knownMovement(entity).scale(TICKS_PER_SECOND);
        return entity.onGround() ? new Vec3(velocity.x, 0.0D, velocity.z) : velocity;
    }

    private static Vec3 knownMovement(Entity entity) {
        if (entity instanceof FellSpearKnownMovement provider) {
            return provider.goetySupreme$spearMotion();
        }
        return entity.getDeltaMovement();
    }

    private static Entity spearMotionSource(LivingEntity user) {
        boolean playerLike = user instanceof FellSpearPlayerLike like && like.goetySupreme$useSpearAsPlayer();
        if (!(user instanceof Player) && !playerLike && user.isPassenger()) {
            return user.getRootVehicle();
        }
        return user;
    }

    private static float spearSpeedFactor(LivingEntity user) {
        if (user instanceof Player) {
            return PLAYER_SPEED_FACTOR;
        }
        if (user instanceof FellSpearPlayerLike like && like.goetySupreme$useSpearAsPlayer()) {
            return PLAYER_LIKE_SPEED_FACTOR;
        }
        return MOB_SPEED_FACTOR;
    }

    private static void playSound(Level level, LivingEntity user, net.minecraft.sounds.SoundEvent sound,
            float volume, float pitch, boolean playerSource) {
        playSound(level, user, sound, volume, pitch, playerSource, false);
    }

    private static void playSound(Level level, LivingEntity user, net.minecraft.sounds.SoundEvent sound,
            float volume, float pitch, boolean playerSource, boolean excludeUser) {
        SoundSource source = playerSource && user instanceof Player ? SoundSource.PLAYERS : user.getSoundSource();
        Player except = excludeUser && user instanceof Player player ? player : null;
        level.playSound(except, user.getX(), user.getY(), user.getZ(), sound, source, volume, pitch);
    }

    public static void playUseSound(LivingEntity user) {
        playSound(user.level(), user, GeneratedMod.FELL_SPEAR_USE.get(), 1.0F, 1.0F, true);
    }

    private static boolean canHit(LivingEntity user, Entity target) {
        if (target == user || !target.isAlive() || target.isSpectator() || target.isInvulnerable()) {
            return false;
        }
        if (target instanceof Player player && player.isSpectator()) {
            return false;
        }
        if (isFriendly(user, target)) {
            return false;
        }
        return !(target instanceof LivingEntity living) || user.canAttack(living);
    }

    private static boolean isFriendly(LivingEntity user, Entity target) {
        if (target == user) {
            return true;
        }
        if (user.isAlliedTo(target) || target.isAlliedTo(user)) {
            return true;
        }
        return ServantFaction.isSameFaction(user, target);
    }
}
