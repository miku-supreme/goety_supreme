package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.SoundUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SedimentFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeFociConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.HeavyArmoredSedimentGuardServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.RingServantGear;
import cn.blockforge.generated.goetysupreme.entity.SedimentGuardServant;
import cn.blockforge.generated.goetysupreme.entity.SedimentSoulStabilizerServant;
import cn.blockforge.generated.goetysupreme.spell.SedimentSummonSpell;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public class SedimentSummonSpell extends SupremeSummonSpell {

    public enum Kind {
        GUARD,
        SOUL_STABILIZER,
        HEAVY_GUARD,
        BULGE
    }

    public static final String JEROTES_VILLAGE_MOD_ID = JerotesVillageCompat.JEROTES_VILLAGE_MOD_ID;

    private final Kind kind;

    public SedimentSummonSpell(Kind kind) {
        this.kind = kind;
    }

    public Kind kind() {
        return kind;
    }

    private SedimentFocusConfig.Section section() {
        return switch (kind) {
            case GUARD -> SedimentFocusConfig.GUARD;
            case SOUL_STABILIZER -> SedimentFocusConfig.SOUL_STABILIZER;
            case HEAVY_GUARD -> SedimentFocusConfig.HEAVY_GUARD;
            case BULGE -> SedimentFocusConfig.BULGE;
        };
    }

    @Override
    public int defaultSoulCost() {
        return section().soulCost.get();
    }

    @Override
    public int defaultCastDuration() {
        return section().castDuration.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return section().cooldown.get();
    }

    @Override
    public int SummonDownDuration() {
        return section().summonDownTicks.get();
    }

    @Override
    public SoundEvent CastingSound() {
        return switch (kind) {
            case GUARD, HEAVY_GUARD -> null;
            case SOUL_STABILIZER -> ModSounds.PREPARE_SUMMON.get();
            case BULGE -> ModSounds.ABYSS_PREPARE_SPELL.get();
        };
    }

    @Override
    public SoundEvent loopSound(LivingEntity caster) {
        return kind == Kind.GUARD || kind == Kind.HEAVY_GUARD ? ModSounds.VANGUARD_SPELL.get() : null;
    }

    @Override
    public SpellType getSpellType() {
        return kind == Kind.BULGE ? SpellType.ABYSS : SpellType.NECROMANCY;
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
        return entity -> switch (kind) {
            case GUARD -> entity instanceof SedimentGuardServant;
            case SOUL_STABILIZER -> entity instanceof SedimentSoulStabilizerServant;
            case HEAVY_GUARD -> entity instanceof HeavyArmoredSedimentGuardServant;
            case BULGE -> entity instanceof BulgeServant;
        };
    }

    @Override
    public int summonLimit() {
        return section().summonLimit.get();
    }

    @Override
    public void gatherHitImpl(ServerLevel level, LivingEntity caster) {
        if (kind == Kind.SOUL_STABILIZER) {
            super.gatherHitImpl(level, caster);
            return;
        }
        if (kind == Kind.BULGE) {
            int particles = caster.getRandom().nextInt(35) + 10;
            for (int i = 0; i < particles; i++) {
                level.sendParticles(ParticleTypes.POOF, caster.getX(), caster.getEyeY(), caster.getZ(),
                        1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            playSound(level, caster, ModSounds.DROWNED_NECROMANCER_SUMMON.get());
            return;
        }
        int particles = caster.getRandom().nextInt(35) + 10;
        for (int i = 0; i < particles; i++) {
            level.sendParticles(ModParticleTypes.LICH.get(), caster.getX(), caster.getY(0.5D),
                    caster.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        playSound(level, caster, ModSounds.VANGUARD_SUMMON.get());
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        if (!JerotesVillageCompat.isJerotesVillageLoaded()) {
            return;
        }
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int count = summonCount(caster, wandStack);
        int potencyLevel = Math.max(0, effectiveStats.getPotency())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get()));
        int durationLevel = Math.max(0, effectiveStats.getDuration() - defaultStats().getDuration())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get()));
        StaffTier tier = staffTier(wandStack);

        int spawned = 0;
        for (int i = 0; i < count; i++) {
            AbstractJerotesServant servant = createServant(level);
            if (servant == null) {
                continue;
            }
            BlockPos spawnPos = findSpawnPosition(level, caster, servant, i, count, tier);
            servant.moveTo(spawnPos, caster.getYRot(), 0.0F);
            MobUtil.moveDownToGround(servant);
            servant.setTrueOwner(caster);
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos),
                    MobSpawnType.MOB_SUMMONED, null, null);
            servant.setTrueOwner(caster);
            servant.setYBodyRot(caster.yBodyRot);
            servant.setYHeadRot(caster.getYRot());
            servant.setYRot(caster.getYRot());
            servant.setXRot(0.0F);
            servant.setLimitedLife(lifespanFor(durationLevel));
            servant.setPersistenceRequired();
            servant.setSummonPotency(potencyLevel);
            if (servant instanceof SedimentGuardServant guard
                    && RingServantGear.casterWearsNewRing(caster)) {
                guard.equipGuaranteedShield();
            }
            buffSummon(caster, servant, potencyLevel);
            setInitialTarget(servant, caster);

            if (level.addFreshEntity(servant)) {
                ++spawned;
                summonAdvancement(caster, servant);
                GoetySummonFx.play(level, servant);
                level.sendParticles(ParticleTypes.SOUL, servant.getX(), servant.getY(1.1D),
                        servant.getZ(), 18, 0.45D, 0.7D, 0.45D, 0.02D);
                if ((kind == Kind.GUARD || kind == Kind.HEAVY_GUARD)
                        && tier == StaffTier.NAMELESS) {
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
        if (spawned > 0) {
            SummonDown(caster);
            switch (kind) {
                case SOUL_STABILIZER -> SoundUtil.playNecromancerSummon(caster);
                case GUARD, HEAVY_GUARD -> level.playSound(null, caster.getX(), caster.getY(),
                        caster.getZ(), ModSounds.VANGUARD_SUMMON.get(), caster.getSoundSource(),
                        1.0F, 1.0F);
                case BULGE -> level.playSound(null, caster.getX(), caster.getY(),
                        caster.getZ(), ModSounds.DROWNED_NECROMANCER_SUMMON.get(),
                        caster.getSoundSource(), 1.0F, 1.0F);
                default -> {
                }
            }
        }
    }

    private AbstractJerotesServant createServant(ServerLevel level) {
        return switch (kind) {
            case GUARD -> ModEntityTypes.SEDIMENT_GUARD_SERVANT.get().create(level);
            case SOUL_STABILIZER -> ModEntityTypes.SEDIMENT_SOUL_STABILIZER_SERVANT.get().create(level);
            case HEAVY_GUARD -> ModEntityTypes.HEAVY_ARMORED_SEDIMENT_GUARD_SERVANT.get().create(level);
            case BULGE -> ModEntityTypes.BULGE_SERVANT.get().create(level);
        };
    }

    private enum StaffTier { PLAIN, NECRO, ABYSS, NAMELESS }

    private static StaffTier staffTier(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return StaffTier.PLAIN;
        }
        var key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (key != null && key.getNamespace().equals("goety") && key.getPath().equals("nameless_staff")) {
            return StaffTier.NAMELESS;
        }
        if (key != null && key.getNamespace().equals("goety") && key.getPath().equals("abyss_staff")) {
            return StaffTier.ABYSS;
        }
        if (key != null && key.getNamespace().equals("goety") && key.getPath().equals("necro_staff")) {
            return StaffTier.NECRO;
        }
        return StaffTier.PLAIN;
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        StaffTier tier = staffTier(wandStack);
        return switch (kind) {
            case GUARD -> switch (tier) {
                case PLAIN -> 1;
                case ABYSS -> 2;
                case NECRO -> 2 + caster.getRandom().nextInt(6);
                case NAMELESS -> 7;
            };
            case SOUL_STABILIZER -> switch (tier) {
                case PLAIN -> 1;
                case ABYSS -> 2;
                case NECRO, NAMELESS -> 2 + caster.getRandom().nextInt(4);
            };
            case HEAVY_GUARD -> switch (tier) {
                case PLAIN -> 1;
                case ABYSS -> 2;
                case NECRO -> 2 + caster.getRandom().nextInt(2);
                case NAMELESS -> 3 + caster.getRandom().nextInt(2);
            };
            case BULGE -> switch (tier) {
                case ABYSS -> blazeFocusStaffCount(caster);
                default -> SupremeFociConfig.randomSummonCount(caster,
                        section().summonCountMin, section().summonCountMax);
            };
        };
    }

    public static int blazeFocusStaffCount(LivingEntity caster) {
        return 2 + caster.getRandom().nextInt(4);
    }

    private int lifespanFor(int durationLevel) {
        long lifespan = section().lifespanTicks.get().longValue()
                + Math.max(0L, (long) durationLevel) * section().durationBonusTicks.get();
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

    private void setInitialTarget(AbstractJerotesServant servant, LivingEntity caster) {
        LivingEntity target = caster instanceof Mob mob ? mob.getTarget() : caster.getLastHurtMob();
        if (target != null && target.isAlive() && !servant.isAlliedTo(target)) {
            servant.setTarget(target);
        }
    }

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, Mob servant,
            int index, int count, StaffTier tier) {
        if (kind == Kind.GUARD && tier == StaffTier.NAMELESS) {
            return vanguardLinePosition(level, caster, servant, index);
        }
        if (kind == Kind.BULGE || count > 4) {
            BlockPos origin = caster.blockPosition();
            BlockPos candidate = BlockFinder.SummonRadius(origin, servant, level, 3 + index, 8);
            return candidate == null ? origin.offset(index + 2, 0, 3) : candidate;
        }
        int outward = kind == Kind.HEAVY_GUARD ? 0 : 1;
        return tidySpawnPosition(level, caster, servant, index, count, outward);
    }

    private BlockPos vanguardLinePosition(ServerLevel level, LivingEntity caster, Mob servant, int index) {
        Direction direction = caster.getDirection();
        int stepX = direction.getStepX();
        int stepZ = direction.getStepZ();
        int offset = index - 3;
        double x = caster.getX() + 2.0D * stepX + offset * stepZ;
        double z = caster.getZ() + 2.0D * stepZ + offset * stepX;
        double y = caster.getY();
        if (level.noCollision(servant, servant.getBoundingBox().move(x, y, z))) {
            return BlockPos.containing(x, y, z);
        }
        BlockPos fallback = BlockFinder.SummonRadius(caster.blockPosition(), servant, level);
        return fallback == null ? caster.blockPosition() : fallback;
    }

    private BlockPos tidySpawnPosition(ServerLevel level, LivingEntity caster, Mob servant,
            int index, int count, int outward) {
        Direction direction = caster.getDirection();
        int stepX = direction.getStepX();
        int stepZ = direction.getStepZ();
        int forward = 2 + outward;
        int side = 1 + outward;
        int back = 2 + outward;
        int dx;
        int dz;
        switch (count) {
            case 1 -> {
                dx = forward * stepX;
                dz = forward * stepZ;
            }
            case 2 -> {
                int sign = index == 0 ? -1 : 1;
                dx = sign * side * stepZ;
                dz = sign * side * stepX;
            }
            case 3 -> {
                switch (index) {
                    case 0 -> {
                        dx = forward * stepX;
                        dz = forward * stepZ;
                    }
                    case 1 -> {
                        dx = -side * stepZ;
                        dz = -side * stepX;
                    }
                    default -> {
                        dx = side * stepZ;
                        dz = side * stepX;
                    }
                }
            }
            default -> {
                switch (index) {
                    case 0 -> {
                        dx = forward * stepX;
                        dz = forward * stepZ;
                    }
                    case 1 -> {
                        dx = -side * stepZ;
                        dz = -side * stepX;
                    }
                    case 2 -> {
                        dx = side * stepZ;
                        dz = side * stepX;
                    }
                    default -> {
                        dx = -back * stepX;
                        dz = -back * stepZ;
                    }
                }
            }
        }
        double x = caster.getX() + dx;
        double z = caster.getZ() + dz;
        double y = MinionGroundPlacement.groundFeetY(level, x, z, caster.getY(),
                MinionGroundPlacement.SKELETON_CLEARANCE);
        return BlockPos.containing(x, y, z);
    }
}
