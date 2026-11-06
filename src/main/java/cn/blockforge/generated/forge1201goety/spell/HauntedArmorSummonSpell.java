package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.common.entities.ally.undead.HauntedArmorServant;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.MobUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.HauntedArmorFocusConfig;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.util.HauntedArmorDressing;
import cn.blockforge.generated.goetysupreme.spell.HauntedArmorSummonSpell;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class HauntedArmorSummonSpell extends SupremeSummonSpell {

    private static final int VANGUARD_LINE_HALF = 3;

    @Override
    public int defaultSoulCost() {
        return HauntedArmorFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return HauntedArmorFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return HauntedArmorFocusConfig.COOLDOWN.get();
    }

    @Override
    public int SummonDownDuration() {
        return HauntedArmorFocusConfig.SUMMON_DOWN_TICKS.get();
    }

    @Override
    public SoundEvent CastingSound() {
        return null;
    }

    @Override
    public SoundEvent loopSound(LivingEntity caster) {
        return ModSounds.VANGUARD_SPELL.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.NECROMANCY;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.DURATION.get());
        return enchantments;
    }

    @Override
    public Predicate<LivingEntity> summonPredicate() {
        return entity -> entity instanceof HauntedArmorServant;
    }

    @Override
    public int summonLimit() {
        return HauntedArmorFocusConfig.SUMMON_LIMIT.get();
    }

    @Override
    public void gatherHitImpl(ServerLevel level, LivingEntity caster) {
        int particles = caster.getRandom().nextInt(35) + 10;
        for (int i = 0; i < particles; i++) {
            level.sendParticles(ModParticleTypes.LICH.get(), caster.getX(), caster.getY(0.5D),
                    caster.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        playSound(level, caster, ModSounds.VANGUARD_SUMMON.get());
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int count = summonCount(caster, wandStack);
        boolean namelessLine = isNamelessStaff(wandStack);
        int potencyLevel = Math.max(0, effectiveStats.getPotency())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get()));
        int durationMultiplier = Math.max(1, effectiveStats.getDuration()
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get())));

        for (int i = 0; i < count; i++) {
            HauntedArmorServant servant = newHauntedArmorServant(level);
            if (servant == null) {
                continue;
            }
            BlockPos spawnPos = findSpawnPosition(level, caster, servant, i, namelessLine);
            servant.moveTo(spawnPos, caster.getYRot(), 0.0F);
            MobUtil.moveDownToGround(servant);
            servant.setTrueOwner(caster);
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos),
                    MobSpawnType.MOB_SUMMONED, null, null);
            servant.setTrueOwner(caster);
            HauntedArmorDressing.dressAsCursedKnight(servant, caster, wandStack);
            servant.setYBodyRot(caster.yBodyRot);
            servant.setYHeadRot(caster.getYRot());
            servant.setYRot(caster.getYRot());
            servant.setXRot(0.0F);
            servant.setLimitedLife(lifespanFor(level, durationMultiplier));
            servant.setPersistenceRequired();
            buffSummon(caster, servant, potencyLevel);
            SummonSap(caster, servant);
            setInitialTarget(servant, caster);

            if (level.addFreshEntity(servant)) {
                summonAdvancement(caster, servant);
                GoetySummonFx.play(level, servant);
                level.sendParticles(ParticleTypes.SOUL, servant.getX(), servant.getY(1.1D),
                        servant.getZ(), 18, 0.45D, 0.7D, 0.45D, 0.02D);
                if (namelessLine) {
                    float volume = 0.25F + servant.getRandom().nextFloat() / 2.0F;
                    level.playSound(null, servant.getX(), servant.getY(), servant.getZ(),
                            ModSounds.SOUL_EXPLODE.get(), SoundSource.PLAYERS, volume, 1.0F);
                    level.playSound(null, servant.getX(), servant.getY(), servant.getZ(),
                            SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, volume, 1.0F);
                }
            } else {
                servant.discard();
            }
        }
        SummonDown(caster);
        level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                ModSounds.VANGUARD_SUMMON.get(), caster.getSoundSource(), 1.0F, 1.0F);
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        if (isNamelessStaff(wandStack)) {
            return 1 + 2 * VANGUARD_LINE_HALF;
        }
        if (rightStaff(wandStack)) {
            return 2 + caster.getRandom().nextInt(6);
        }
        return 1;
    }

    private static boolean isGoetyItem(ItemStack stack, String path) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return key != null && key.getNamespace().equals("goety") && key.getPath().equals(path);
    }

    private static boolean isNamelessStaff(ItemStack stack) {
        return isGoetyItem(stack, "nameless_staff");
    }

    private static HauntedArmorServant newHauntedArmorServant(ServerLevel level) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", "haunted_armor_servant"));
        Entity created = type == null ? null : type.create(level);
        if (created instanceof HauntedArmorServant servant) {
            return servant;
        }
        if (created != null) {
            created.discard();
        }
        return null;
    }

    private int lifespanFor(ServerLevel level, int durationMultiplier) {
        long lifespan = (long) MobUtil.getSummonLifespan(level) * Math.max(1, durationMultiplier);
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1L, lifespan));
    }

    private int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        ItemStack focus = focusStack(wandStack);
        return focus.isEmpty() || enchantment == null ? 0 : focus.getEnchantmentLevel(enchantment);
    }

    private ItemStack focusStack(ItemStack wandStack) {
        if (wandStack == null || wandStack.isEmpty() || !(wandStack.getItem() instanceof IWand)) {
            return ItemStack.EMPTY;
        }
        ItemStack focus = IWand.getFocus(wandStack);
        return focus == null ? ItemStack.EMPTY : focus;
    }

    private void setInitialTarget(HauntedArmorServant servant, LivingEntity caster) {
        LivingEntity target = caster instanceof Mob mob ? mob.getTarget() : caster.getLastHurtMob();
        if (target != null && target.isAlive() && !servant.isAlliedTo(target)) {
            servant.setTarget(target);
        }
    }

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, Mob servant,
            int index, boolean namelessLine) {
        if (namelessLine) {
            Direction direction = caster.getDirection();
            int stepX = direction.getStepX();
            int stepZ = direction.getStepZ();
            int offset = index - VANGUARD_LINE_HALF;
            double x = caster.getX() + 2.0D * stepX + offset * stepZ;
            double z = caster.getZ() + 2.0D * stepZ + offset * stepX;
            double y = caster.getY();
            if (level.noCollision(servant, servant.getBoundingBox().move(x, y, z))) {
                return BlockPos.containing(x, y, z);
            }
            BlockPos fallback = BlockFinder.SummonRadius(caster.blockPosition(), servant, level);
            return fallback == null ? caster.blockPosition() : fallback;
        }
        BlockPos candidate = BlockFinder.SummonRadius(caster.blockPosition(), servant, level);
        return candidate == null ? caster.blockPosition() : candidate;
    }
}
