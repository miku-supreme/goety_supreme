package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.MobUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.MountFocusConfig;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.HorseServantArmorLayer;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.MountSummonSpell;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.entity.SkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;

public final class MountSummonSpell extends SupremeSummonSpell {

    public enum Kind {
        SKELETON,
        WITHER
    }

    private final Kind kind;

    public MountSummonSpell(Kind kind) {
        this.kind = kind;
    }

    @Override
    public int defaultSoulCost() {
        return config().soulCost.get();
    }

    @Override
    public int defaultCastDuration() {
        return config().castDuration.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return config().cooldown.get();
    }

    @Override
    public int SummonDownDuration() {
        return config().summonDownTicks.get();
    }

    @Override
    public SpellType getSpellType() {
        return kind == Kind.SKELETON ? SpellType.NECROMANCY : SpellType.NETHER;
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
        if (kind == Kind.WITHER) {
            return entity -> entity instanceof WitherSkeletonHorseServant;
        }
        return entity -> entity instanceof SkeletonHorseServant
                && !(entity instanceof WitherSkeletonHorseServant);
    }

    @Override
    public int summonLimit() {
        return config().summonLimit.get();
    }

    @Override
    public void summonGatherParticles(ServerLevel level, LivingEntity caster) {
        level.sendParticles(ParticleTypes.SOUL, caster.getX(), caster.getY(0.5D), caster.getZ(),
                16, 0.45D, 0.6D, 0.45D, 0.02D);
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int potencyLevel = Math.max(0, effectiveStats.getPotency())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get()));
        int durationLevel = Math.max(0, effectiveStats.getDuration() - defaultStats().getDuration())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get()));

        int count = summonCount(caster, wandStack);
        int spawned = 0;
        for (int i = 0; i < count; i++) {
            SkeletonHorseServant servant = createMount(level);
            if (servant == null) {
                continue;
            }
            BlockPos spawnPos = findSpawnPosition(level, caster, servant, i);
            servant.moveTo(spawnPos, caster.getYRot(), 0.0F);
            MobUtil.moveDownToGround(servant);
            servant.setTrueOwner(caster);
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos),
                    MobSpawnType.MOB_SUMMONED, null, null);
            servant.setTrueOwner(caster);
            servant.setLimitedLife(lifespanFor(durationLevel));
            servant.setPersistenceRequired();
            applyNetheriteArmorIfNetherForged(servant, caster, wandStack);
            if (!servant.hasNetheriteArmor() && servant.getHorseArmor().isEmpty()) {
                ItemStack horseArmor = RingServantGear.rollHorseArmor(servant, servant.getRandom());
                if (!horseArmor.isEmpty()) {
                    servant.setHorseArmor(horseArmor);
                }
            }
            buffSummon(caster, servant, potencyLevel);
            setInitialTarget(servant, caster);

            if (level.addFreshEntity(servant)) {
                ++spawned;
                summonAdvancement(caster, servant);
                GoetySummonFx.play(level, servant);
                level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, servant.getX(), servant.getY(0.8D),
                        servant.getZ(), 14, 0.35D, 0.5D, 0.35D, 0.01D);
                level.playSound(null, servant.getX(), servant.getY(0.7D), servant.getZ(),
                        SoundEvents.SKELETON_HORSE_AMBIENT, net.minecraft.sounds.SoundSource.PLAYERS,
                        0.8F, 1.1F + servant.getRandom().nextFloat() * 0.2F);
            } else {
                servant.discard();
            }
        }
        if (spawned > 0) {
            SummonDown(caster);
        }
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        if (kind != Kind.SKELETON) {
            return 1;
        }
        if (isGoetyItem(wandStack, "nameless_staff")) {
            return 3;
        }
        if (isGoetyItem(wandStack, "necro_staff")) {
            return 2;
        }
        return 1;
    }

    private static boolean isGoetyItem(ItemStack stack, String path) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        var key = net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(stack.getItem());
        return key != null && key.getNamespace().equals("goety") && key.getPath().equals(path);
    }

    private SkeletonHorseServant createMount(ServerLevel level) {
        return kind == Kind.WITHER
                ? ModEntityTypes.WITHER_SKELETON_HORSE_SERVANT.get().create(level)
                : ModEntityTypes.SKELETON_HORSE_SERVANT.get().create(level);
    }

    private void applyNetheriteArmorIfNetherForged(SkeletonHorseServant servant, LivingEntity caster,
            ItemStack wandStack) {
        if (kind != Kind.WITHER || !(servant instanceof WitherSkeletonHorseServant wither)) {
            return;
        }
        if (rightStaff(wandStack) && wearsNetherSet(caster)) {
            wither.setNetheriteArmor(true);
        }
    }

    private static boolean wearsNetherSet(LivingEntity owner) {
        return owner != null && CuriosFinder.hasNetherSet(owner);
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

    private int lifespanFor(int durationLevel) {
        long lifespan = config().lifespanTicks.get().longValue()
                + Math.max(0L, (long) durationLevel) * config().durationBonusTicks.get();
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1L, lifespan));
    }

    private void setInitialTarget(SkeletonHorseServant servant, LivingEntity caster) {
        LivingEntity target = caster instanceof Mob mob ? mob.getTarget() : caster.getLastHurtMob();
        if (target != null && target.isAlive() && !servant.isAlliedTo(target)) {
            servant.setTarget(target);
        }
    }

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, Mob servant, int index) {
        BlockPos origin = caster.blockPosition();
        BlockPos candidate = BlockFinder.SummonRadius(origin, servant, level, 3 + index, 8);
        return candidate == null ? origin.offset(index + 1, 0, 2) : candidate;
    }

    private MountFocusConfig.Section config() {
        return kind == Kind.WITHER ? MountFocusConfig.WITHER : MountFocusConfig.SKELETON;
    }
}
