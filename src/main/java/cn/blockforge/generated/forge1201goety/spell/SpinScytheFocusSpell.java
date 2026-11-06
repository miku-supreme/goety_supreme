package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.config.SpinScytheFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;
import cn.blockforge.generated.goetysupreme.spell.SpinScytheFocusSpell;

public final class SpinScytheFocusSpell extends Spell {

    private static final double FLAT_SHOT_MIN_DISTANCE = 6.0D;

    private static final double FLAT_SHOT_MAX_HEIGHT_DIFF = 2.5D;

    @Override
    public int defaultSoulCost() {
        return SpinScytheFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return SpinScytheFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return SpinScytheFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.NECROMANCY;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.VELOCITY.get());
        enchantments.add(ModEnchantments.RANGE.get());
        enchantments.add(ModEnchantments.RADIUS.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        int count = scytheCount(wandStack);
        int velocity = focusEnchantmentLevel(wandStack, ModEnchantments.VELOCITY.get());
        int range = focusEnchantmentLevel(wandStack, ModEnchantments.RANGE.get());
        int radius = focusEnchantmentLevel(wandStack, ModEnchantments.RADIUS.get());
        int potency = (stats == null ? 0 : Math.max(0, stats.getPotency()))
                + focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());

        LivingEntity target = resolveAimTarget(caster);
        Vec3 aim;
        if (target != null) {
            double eyeY = caster.getEyeY();
            double targetY = target.getY() + target.getBbHeight() * 0.5D;
            double horizontal = Math.sqrt(Math.pow(target.getX() - caster.getX(), 2.0D)
                    + Math.pow(target.getZ() - caster.getZ(), 2.0D));
            if (horizontal >= FLAT_SHOT_MIN_DISTANCE
                    && Math.abs(targetY - eyeY) <= FLAT_SHOT_MAX_HEIGHT_DIFF) {
                targetY = eyeY;
            }
            aim = new Vec3(target.getX(), targetY, target.getZ());
        } else {
            aim = caster.position().add(caster.getLookAngle().scale(4.0D));
        }
        Vec3 dir = aim.subtract(caster.getEyePosition());
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = caster.getLookAngle();
        }
        dir = dir.normalize();

        double speed = SpinScytheFocusConfig.BASE_SPEED.get()
                * (1.0D + Math.max(0, velocity) * SpinScytheFocusConfig.VELOCITY_PER_LEVEL.get());
        int outbound = SpinScytheFocusConfig.OUTBOUND_TICKS.get()
                + Math.max(0, range) * SpinScytheFocusConfig.RANGE_TICKS_PER_LEVEL.get();
        float widthScale = (float) (1.0D + Math.max(0, radius) * SpinScytheFocusConfig.RADIUS_PER_LEVEL.get());
        float damage = (float) (SpinScytheFocusConfig.BASE_DAMAGE.get() + Math.max(0, potency));
        double spread = SpinScytheFocusConfig.SPREAD_ANGLE.get();

        for (int i = 0; i < count; i++) {
            double yawOffset = count > 1 ? (i - (count - 1) / 2.0D) * spread : 0.0D;
            Vec3 shotDir = yawOffset == 0.0D ? dir : rotateY(dir, yawOffset);
            ReaperSpinEntity spin = new ReaperSpinEntity(level, caster);
            spin.setPos(caster.getX() + shotDir.x * 1.2D, caster.getEyeY() - 0.2D + shotDir.y * 1.2D,
                    caster.getZ() + shotDir.z * 1.2D);
            spin.setSpeed(speed);
            spin.setDeltaMovement(shotDir.scale(speed));
            spin.setDamage(damage);
            spin.setWidthScale(widthScale);
            spin.setOutboundTicks(outbound);
            level.addFreshEntity(spin);
        }
        level.sendParticles(ParticleTypes.SOUL, caster.getX(), caster.getEyeY(), caster.getZ(),
                8, 0.4D, 0.4D, 0.4D, 0.02D);
        level.playSound(null, caster.getX(), caster.getY(0.7D), caster.getZ(),
                SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.0F, 0.8F);
    }

    @Nullable
    private static LivingEntity resolveAimTarget(LivingEntity caster) {
        if (caster instanceof Mob mob) {
            LivingEntity current = mob.getTarget();
            if (isValidAimTarget(caster, current)) {
                return current;
            }
        }
        LivingEntity last = caster.getLastHurtMob();
        return isValidAimTarget(caster, last) ? last : null;
    }

    private static boolean isValidAimTarget(LivingEntity caster, @Nullable LivingEntity target) {
        return target != null && target.isAlive() && target != caster
                && !caster.isAlliedTo(target) && !target.isAlliedTo(caster);
    }

    private enum StaffTier { PLAIN, NECRO, NAMELESS }

    private static StaffTier staffTier(ItemStack stack) {
        if (isGoetyItem(stack, "nameless_staff")) {
            return StaffTier.NAMELESS;
        }
        if (isGoetyItem(stack, "necro_staff")) {
            return StaffTier.NECRO;
        }
        return StaffTier.PLAIN;
    }

    private static boolean isGoetyItem(ItemStack stack, String path) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        var key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return key != null && key.getNamespace().equals("goety") && key.getPath().equals(path);
    }

    private static int scytheCount(ItemStack wandStack) {
        return switch (staffTier(wandStack)) {
            case NAMELESS -> Math.max(1, SpinScytheFocusConfig.NAMELESS_STAFF_COUNT.get());
            case NECRO -> Math.max(1, SpinScytheFocusConfig.NECRO_STAFF_COUNT.get());
            case PLAIN -> Math.max(1, SpinScytheFocusConfig.PLAIN_STAFF_COUNT.get());
        };
    }

    private static Vec3 rotateY(Vec3 dir, double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        return new Vec3(dir.x * cos - dir.z * sin, dir.y, dir.x * sin + dir.z * cos).normalize();
    }

    private static int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        ItemStack focus = com.Polarice3.Goety.api.items.magic.IWand.getFocus(wandStack);
        if (focus == null || focus.isEmpty() || enchantment == null) {
            return 0;
        }
        return focus.getEnchantmentLevel(enchantment);
    }
}