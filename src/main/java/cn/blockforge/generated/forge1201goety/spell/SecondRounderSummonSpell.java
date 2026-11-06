package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
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
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SecondRounderFocusConfig;
import cn.blockforge.generated.goetysupreme.config.ServantCapConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSkeletonServant;
import cn.blockforge.generated.goetysupreme.spell.SecondRounderSummonSpell;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderZombieServant;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class SecondRounderSummonSpell extends SupremeSummonSpell {

    public enum Kind {
        ROTTING,
        OSSEOUS
    }

    public static final String JEROTES_VILLAGE_MOD_ID = JerotesVillageCompat.JEROTES_VILLAGE_MOD_ID;

    private final Kind kind;

    public SecondRounderSummonSpell(Kind kind) {
        this.kind = kind;
    }

    public Kind kind() {
        return kind;
    }

    private SecondRounderFocusConfig.Values values() {
        return kind == Kind.ROTTING ? SecondRounderFocusConfig.ROTTING : SecondRounderFocusConfig.OSSEOUS;
    }

    @Override
    public int defaultSoulCost() {
        return values().soulCost.get();
    }

    @Override
    public int defaultCastDuration() {
        return values().castDuration.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return values().cooldown.get();
    }

    @Override
    public int SummonDownDuration() {
        return values().summonDownTicks.get();
    }

    @Override
    public SoundEvent CastingSound() {
        return ModSounds.PREPARE_SUMMON.get();
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
        return entity -> kind == Kind.ROTTING
                ? entity instanceof SecondRounderZombieServant
                : entity instanceof SecondRounderSkeletonServant;
    }

    @Override
    public int summonLimit() {
        return kind == Kind.ROTTING
                ? ServantCapConfig.JEROTES_SECOND_ROUNDER_ZOMBIE.get()
                : ServantCapConfig.JEROTES_SECOND_ROUNDER_SKELETON.get();
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
        int durationMultiplier = Math.max(1, effectiveStats.getDuration()
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get())));

        for (int i = 0; i < count; i++) {
            AbstractJerotesServant servant = createServant(level);
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
            servant.setYBodyRot(caster.yBodyRot);
            servant.setYHeadRot(caster.getYRot());
            servant.setYRot(caster.getYRot());
            servant.setXRot(0.0F);
            servant.setLimitedLife(lifespanFor(level, durationMultiplier));
            servant.setPersistenceRequired();
            servant.setSummonPotency(potencyLevel);
            buffSummon(caster, servant, potencyLevel);
            SummonSap(caster, servant);
            setInitialTarget(servant, caster);

            if (level.addFreshEntity(servant)) {
                summonAdvancement(caster, servant);
                GoetySummonFx.play(level, servant);
            } else {
                servant.discard();
            }
        }
        SummonDown(caster);
        SoundUtil.playNecromancerSummon(caster);
    }

    private AbstractJerotesServant createServant(ServerLevel level) {
        return switch (kind) {
            case ROTTING -> ModEntityTypes.SECOND_ROUNDER_ZOMBIE_SERVANT.get().create(level);
            case OSSEOUS -> ModEntityTypes.SECOND_ROUNDER_SKELETON_SERVANT.get().create(level);
        };
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        if (isGoetyItem(wandStack, "nameless_staff")) {
            return 7;
        }
        if (rightStaff(wandStack)) {
            return 2 + caster.getRandom().nextInt(4);
        }
        return 1;
    }

    private static boolean isGoetyItem(ItemStack stack, String path) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        var key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        return key != null && key.getNamespace().equals("goety") && key.getPath().equals(path);
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

    private void setInitialTarget(AbstractJerotesServant servant, LivingEntity caster) {
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
}
