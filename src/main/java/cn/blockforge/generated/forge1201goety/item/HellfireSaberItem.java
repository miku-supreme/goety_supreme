package cn.blockforge.generated.goetysupreme.item;

import com.Polarice3.Goety.common.entities.util.FirePillar;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.event.ClientModEvents;
import cn.blockforge.generated.goetysupreme.event.HellfireSaberEvents;
import cn.blockforge.generated.goetysupreme.item.HellfireSaberItem;
import cn.blockforge.generated.goetysupreme.item.InfernalCredentialItem;
import cn.blockforge.generated.goetysupreme.entity.WarlordHellfireSlash;
import cn.blockforge.generated.goetysupreme.item.WitheringSaberItem;

public final class HellfireSaberItem extends SwordItem {

    private static final int WITHER_TICKS = 20 * 5;

    private static final double AURA_RADIUS = 12.0D;

    private static final float HEAL_PER_TARGET = 0.5F;

    private static final float HEAL_CAP = 1.5F;

    public static final float SLASH_WIDTH_SCALE = 0.5F;

    private static final int SLASH_COOLDOWN = 160;

    private static final double SLASH_AIM_COS = 0.92D;

    private static final double SLASH_AIM_RANGE = 20.0D;

    private static final int GUARD_TICKS = 15;

    private static final int GUARD_COOLDOWN = 15 * 20;

    private static final int PARRY_WINDOW = 20;

    private static final int DASH_TICKS = 6;

    private static final double DASH_SPEED = 0.55D;

    private static final float DASH_DAMAGE = 15.0F;

    private static final float DASH_MAX_HEALTH_RATIO = 0.05F;

    private static final int BLAZE_INVULN_TICKS = 20;

    private static final int BLAZE_TICKS = 100;

    private static final int BLAZE_INTERVAL = 20;

    private static final float BLAZE_DAMAGE = 10.0F;

    private static final double BLAZE_RADIUS = 3.5D;

    private static final int BLAZE_PILLAR_RINGS = 3;

    private static final int BURN_HEX_TICKS = 100;

    private static final double REACH_BONUS = 0.5D;

    private static final UUID REACH_MODIFIER_UUID =
            UUID.fromString("3f1d0c8a-7b64-4e21-9a5d-2c6f0b8e4d17");

    private static final String KEY_GUARD = "HellfireGuard";
    private static final String KEY_PARRY = "HellfireParry";
    private static final String KEY_GUARD_READY = "HellfireGuardReady";
    private static final String KEY_DASH = "HellfireDash";
    private static final String KEY_BLAZE = "HellfireBlaze";
    private static final String KEY_SLASH_READY = "HellfireSlashReady";
    private static final String KEY_BLAZE_INVULN = "HellfireBlazeInvuln";

    public HellfireSaberItem() {
        super(WitheringSaberItem.TIER, 5, -2.8F,
                new Item.Properties().rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            target.addEffect(new MobEffectInstance(MobEffects.WITHER, WITHER_TICKS, 0), attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResultHolder.pass(stack);
        }
        long now = level.getGameTime();
        if (level.isClientSide) {
            if (parryUntil(stack) <= now && guardUntil(stack) <= now) {
                if (guardReady(stack) > now) {
                    return InteractionResultHolder.pass(stack);
                }
                player.startUsingItem(hand);
            }
            return InteractionResultHolder.success(stack);
        }
        if (parryUntil(stack) > now) {
            setParry(stack, 0L);
            startBlaze(player, stack, level);
            restoreGuardCooldown(player, stack, now);
            cosmeticSwing(player, hand);
            return InteractionResultHolder.success(stack);
        }
        if (guardUntil(stack) > now) {
            return InteractionResultHolder.success(stack);
        }
        if (guardReady(stack) > now) {
            return InteractionResultHolder.pass(stack);
        }
        setGuard(stack, now + GUARD_TICKS);
        setGuardReady(stack, now + GUARD_COOLDOWN);
        player.startUsingItem(hand);
        level.playSound(null, player.getX(), player.getY() + 1.0D, player.getZ(),
                SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 0.7F);
        if (level instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CRIT,
                    player.getX(), player.getY(1.2D), player.getZ(), 10, 0.6D, 0.8D, 0.6D, 0.05D);
        }
        cosmeticSwing(player, hand);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return GUARD_TICKS;
    }

    private static void restoreGuardCooldown(Player player, ItemStack stack, long now) {
        long ready = guardReady(stack);
        if (ready > now && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            player.getCooldowns().addCooldown(stack.getItem(),
                    (int) Math.min(Integer.MAX_VALUE, ready - now));
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> modifiers = super.getDefaultAttributeModifiers(slot);
        if (slot != EquipmentSlot.MAINHAND) {
            return modifiers;
        }
        Multimap<Attribute, AttributeModifier> withReach = HashMultimap.create(modifiers);
        withReach.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(REACH_MODIFIER_UUID,
                "Hellfire saber reach", REACH_BONUS, AttributeModifier.Operation.ADDITION));
        return withReach;
    }

    private static boolean suppressSwing;

    private static void cosmeticSwing(Player player, InteractionHand hand) {
        suppressSwing = true;
        try {
            player.swing(hand, true);
        } finally {
            suppressSwing = false;
        }
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity.level().isClientSide || !(entity instanceof Player player)) {
            return false;
        }
        if (suppressSwing) {
            return false;
        }
        if (player.getMainHandItem() != stack) {
            return false;
        }
        Level level = player.level();
        long now = level.getGameTime();
        if (parryUntil(stack) > now) {
            setParry(stack, 0L);
            startDash(player, stack, level);
            return false;
        }
        if (!player.isShiftKeyDown()) {
            return false;
        }
        if (slashReady(stack) > now) {
            return false;
        }
        setSlashReady(stack, now + SLASH_COOLDOWN);
        fireSlash(player, level);
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }
        if (player.getMainHandItem() != stack) {
            return;
        }
        if (player.tickCount % 20 == 0) {
            int withered = countWitheredNearby(player);
            if (withered > 0) {
                player.heal(Math.min(HEAL_CAP, HEAL_PER_TARGET * withered));
            }
        }
        long now = level.getGameTime();
        long guard = guardUntil(stack);
        long parry = parryUntil(stack);
        if ((guard != 0L && guard <= now) || (parry != 0L && parry <= now)) {
            setGuard(stack, 0L);
            setParry(stack, 0L);
            restoreGuardCooldown(player, stack, now);
        }
        if (guardUntil(stack) > now) {
            if (level instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.CRIT,
                        player.getX(), player.getY(1.2D), player.getZ(), 6, 0.5D, 0.7D, 0.5D, 0.04D);
            }
        }
        if (dashTicks(stack) > 0) {
            tickDash(player, stack, level);
        }
        if (blazeTicks(stack) > 0) {
            tickBlaze(player, stack, level);
        }
    }

    private static int countWitheredNearby(Player player) {
        AABB area = player.getBoundingBox().inflate(AURA_RADIUS);
        int count = 0;
        for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, area)) {
            if (other != player && other.isAlive() && other.hasEffect(MobEffects.WITHER)) {
                count++;
            }
        }
        return count;
    }

    private static void fireSlash(Player player, Level level) {
        Vec3 look = player.getLookAngle();
        if (look.lengthSqr() < 1.0E-6D) {
            look = new Vec3(0.0D, 0.0D, 1.0D);
        }
        look = look.normalize();
        Vec3 origin = player.getEyePosition().add(look.scale(1.0D));
        LivingEntity aim = findAimTarget(player, look);
        Vec3 dir = look;
        if (aim != null) {
            Vec3 toTarget = aim.getEyePosition().subtract(origin);
            if (toTarget.lengthSqr() > 1.0E-4D) {
                dir = toTarget.normalize();
            }
        }
        WarlordHellfireSlash slash = new WarlordHellfireSlash(level, player);
        slash.setWidthScale(SLASH_WIDTH_SCALE);
        slash.setPos(origin.x, origin.y, origin.z);
        slash.shoot(dir.x, dir.y, dir.z, (float) WarlordHellfireSlash.SPEED, 0.0F);
        if (aim != null) {
            slash.setTrackingTarget(aim);
        }
        level.addFreshEntity(slash);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.6F, 0.5F);
    }

    private static LivingEntity findAimTarget(Player player, Vec3 look) {
        AABB box = player.getBoundingBox().inflate(SLASH_AIM_RANGE);
        LivingEntity best = null;
        double bestDist = Double.MAX_VALUE;
        for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, box)) {
            if (other == player || !other.isAlive() || player.isAlliedTo(other)) {
                continue;
            }
            Vec3 toTarget = other.getEyePosition().subtract(player.getEyePosition());
            double dist = toTarget.length();
            if (dist < 1.0E-4D || dist > SLASH_AIM_RANGE) {
                continue;
            }
            if (look.dot(toTarget.scale(1.0D / dist)) < SLASH_AIM_COS) {
                continue;
            }
            if (dist < bestDist) {
                bestDist = dist;
                best = other;
            }
        }
        return best;
    }

    private static void startDash(Player player, ItemStack stack, Level level) {
        setDash(stack, DASH_TICKS);
        Vec3 dir = facingHorizontal(player);
        player.setDeltaMovement(dir.x * DASH_SPEED, 0.12D, dir.z * DASH_SPEED);
        player.hurtMarked = true;
        player.fallDistance = 0.0F;
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.6F, 0.7F);
        if (level instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.FLAME,
                    player.getX(), player.getY(1.0D), player.getZ(), 30, 0.5D, 0.7D, 0.5D, 0.1D);
            server.sendParticles(ParticleTypes.LAVA,
                    player.getX(), player.getY(0.6D), player.getZ(), 6, 0.4D, 0.3D, 0.4D, 0.0D);
        }
    }

    private static void tickDash(Player player, ItemStack stack, Level level) {
        setDash(stack, Math.max(0, dashTicks(stack) - 1));
        Vec3 dir = facingHorizontal(player);
        player.setDeltaMovement(dir.x * DASH_SPEED, player.getDeltaMovement().y, dir.z * DASH_SPEED);
        player.hurtMarked = true;
        player.fallDistance = 0.0F;
        if (level instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.FLAME,
                    player.getX(), player.getY(0.9D), player.getZ(), 10, 0.4D, 0.5D, 0.4D, 0.05D);
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    player.getX(), player.getY(1.3D), player.getZ(), 6, 0.35D, 0.4D, 0.35D, 0.03D);
        }
        AABB box = player.getBoundingBox().inflate(1.2D);
        for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class, box)) {
            if (victim == player || !victim.isAlive() || player.isAlliedTo(victim)) {
                continue;
            }
            float damage = DASH_DAMAGE + DASH_MAX_HEALTH_RATIO * victim.getMaxHealth();
            victim.hurt(player.damageSources().playerAttack(player), damage);
            if (level instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.EXPLOSION,
                        victim.getX(), victim.getY(1.0D), victim.getZ(), 2, 0.3D, 0.3D, 0.3D, 0.0D);
                server.sendParticles(ParticleTypes.FLAME,
                        victim.getX(), victim.getY(1.0D), victim.getZ(), 24, 0.45D, 0.6D, 0.45D, 0.09D);
            }
        }
    }

    private static void startBlaze(Player player, ItemStack stack, Level level) {
        setBlaze(stack, BLAZE_TICKS);
        setBlazeInvuln(stack, level.getGameTime() + BLAZE_INVULN_TICKS);
        spawnDenseHellfirePillars(level, player, player.position(), BLAZE_RADIUS, BLAZE_TICKS);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 2.0F, 0.6F);
        if (level instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.END_ROD,
                    player.getX(), player.getY(1.0D), player.getZ(), 24, 0.5D, 0.9D, 0.5D, 0.02D);
        }
    }

    private static void tickBlaze(Player player, ItemStack stack, Level level) {
        int ticks = Math.max(0, blazeTicks(stack) - 1);
        setBlaze(stack, ticks);
        if (ticks % BLAZE_INTERVAL == 0) {
            applyBlazeDamage(player, level);
        }
    }

    private static void applyBlazeDamage(Player player, Level level) {
        MobEffect burnHex = goetyEffect("burn_hex");
        AABB box = player.getBoundingBox().inflate(BLAZE_RADIUS);
        for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class, box)) {
            if (victim == player || !victim.isAlive() || player.isAlliedTo(victim)) {
                continue;
            }
            int invulnerable = victim.invulnerableTime;
            victim.invulnerableTime = 0;
            victim.hurt(ModDamageSource.hellfire(player, player), BLAZE_DAMAGE);
            victim.invulnerableTime = invulnerable;
            if (burnHex != null) {
                victim.addEffect(new MobEffectInstance(burnHex, BURN_HEX_TICKS, 0, false, true), player);
            }
        }
    }

    private static void spawnDenseHellfirePillars(Level level, Player owner, Vec3 center,
            double radius, int duration) {
        if (!(level instanceof ServerLevel server)) {
            return;
        }
        spawnGoetyHellfirePillar(server, owner, center.x, center.y, center.z, duration);
        for (int ring = 1; ring <= BLAZE_PILLAR_RINGS; ring++) {
            double r = radius * ring / BLAZE_PILLAR_RINGS;
            int spokes = 6 * ring;
            for (int i = 0; i < spokes; i++) {
                double angle = Math.PI * 2.0D * i / spokes;
                spawnGoetyHellfirePillar(server, owner, center.x + Math.cos(angle) * r, center.y,
                        center.z + Math.sin(angle) * r, duration);
            }
        }
    }

    private static void spawnGoetyHellfirePillar(ServerLevel server, Player owner,
            double x, double y, double z, int duration) {
        FirePillar pillar = new FirePillar(server, x, y, z);
        pillar.setActivated(true);
        pillar.getPersistentData().putBoolean(AbstractWitherWarlord.HELLFIRE_PILLAR_TAG, true);
        pillar.setOwner(owner);
        pillar.setDuration(Math.max(20, duration));
        server.addFreshEntity(pillar);
    }

    private static MobEffect goetyEffect(String path) {
        return ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", path));
    }

    private static Vec3 facingHorizontal(Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 dir = new Vec3(look.x, 0.0D, look.z);
        if (dir.lengthSqr() < 1.0E-6D) {
            return new Vec3(0.0D, 0.0D, 1.0D);
        }
        return dir.normalize();
    }

    private static long guardUntil(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0L : tag.getLong(KEY_GUARD);
    }

    private static long parryUntil(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0L : tag.getLong(KEY_PARRY);
    }

    private static long guardReady(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0L : tag.getLong(KEY_GUARD_READY);
    }

    private static long slashReady(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0L : tag.getLong(KEY_SLASH_READY);
    }

    private static int dashTicks(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0 : tag.getInt(KEY_DASH);
    }

    private static int blazeTicks(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0 : tag.getInt(KEY_BLAZE);
    }

    private static void setGuard(ItemStack stack, long value) {
        stack.getOrCreateTag().putLong(KEY_GUARD, value);
    }

    private static void setParry(ItemStack stack, long value) {
        stack.getOrCreateTag().putLong(KEY_PARRY, value);
    }

    private static void setGuardReady(ItemStack stack, long value) {
        stack.getOrCreateTag().putLong(KEY_GUARD_READY, value);
    }

    private static void setSlashReady(ItemStack stack, long value) {
        stack.getOrCreateTag().putLong(KEY_SLASH_READY, value);
    }

    private static void setDash(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(KEY_DASH, value);
    }

    private static void setBlaze(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(KEY_BLAZE, value);
    }

    private static long blazeInvulnUntil(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0L : tag.getLong(KEY_BLAZE_INVULN);
    }

    private static void setBlazeInvuln(ItemStack stack, long value) {
        stack.getOrCreateTag().putLong(KEY_BLAZE_INVULN, value);
    }

    public static boolean isBlazeInvulnerable(Player player, ItemStack stack) {
        return blazeInvulnUntil(stack) > player.level().getGameTime();
    }

    public static void onParried(Player player, ItemStack stack) {
        long now = player.level().getGameTime();
        setGuard(stack, 0L);
        setParry(stack, now + PARRY_WINDOW);
        player.stopUsingItem();
        cosmeticSwing(player, InteractionHand.MAIN_HAND);
        player.level().playSound(null, player.getX(), player.getY() + 1.0D, player.getZ(),
                SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.4F, 0.6F);
        if (player.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CRIT,
                    player.getX(), player.getY(1.2D), player.getZ(), 20, 0.8D, 0.9D, 0.8D, 0.15D);
            server.sendParticles(ParticleTypes.FLAME,
                    player.getX(), player.getY(1.0D), player.getZ(), 12, 0.6D, 0.7D, 0.6D, 0.08D);
        }
    }

    public static boolean isGuarding(Player player, ItemStack stack) {
        return guardUntil(stack) > player.level().getGameTime();
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.copy();
    }
}
