package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.MobUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import cn.blockforge.generated.goetysupreme.config.PlayerMobFocusConfig;
import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.spell.PlayerMobFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.spell.SupremeSummonSpell;

public final class PlayerMobFocusSpell extends SupremeSummonSpell {

    public static final int BASE_LIFESPAN_TICKS = 66 * 20;

    public static final int DURATION_BONUS_PER_LEVEL_TICKS = 100 * 20;

    public static final net.minecraft.sounds.SoundEvent SUMMON_SOUND = SoundEvents.AMETHYST_BLOCK_BREAK;

    public static final net.minecraft.sounds.SoundEvent CASTING_SOUND = SoundEvents.EVOKER_CAST_SPELL;

    @Override
    public int defaultSoulCost() {
        return PlayerMobFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return PlayerMobFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return PlayerMobFocusConfig.COOLDOWN.get();
    }

    @Override
    public net.minecraft.sounds.SoundEvent CastingSound() {
        return CASTING_SOUND;
    }

    @Override
    public int SummonDownDuration() {
        return PlayerMobFocusConfig.SUMMON_DOWN_TICKS.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.NONE;
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
        return entity -> entity instanceof PlayerMobServant;
    }

    @Override
    public int summonLimit() {
        return PlayerMobFocusConfig.SUMMON_LIMIT.get();
    }

    @Override
    public void summonGatherParticles(ServerLevel level, LivingEntity caster) {
        level.sendParticles(ParticleTypes.WITCH, caster.getX(), caster.getY(0.5D), caster.getZ(),
                12, 0.35D, 0.5D, 0.35D, 0.08D);
    }

    private static ParticleOptions godRayParticle() {
        ParticleType<?> type = BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.fromNamespaceAndPath("goety", "god_ray"));
        return type instanceof ParticleOptions options ? options : ParticleTypes.END_ROD;
    }

    private static void sendGodRay(ServerLevel level, double x, double y, double z) {
        ParticleOptions godRay = godRayParticle();
        int rgb = ChatFormatting.LIGHT_PURPLE.getColor() == null ? 0xAA55AA : ChatFormatting.LIGHT_PURPLE.getColor();
        double r = ((rgb >> 16) & 0xFF) / 255.0D;
        double g = ((rgb >> 8) & 0xFF) / 255.0D;
        double b = (rgb & 0xFF) / 255.0D;
        for (ServerPlayer player : level.getPlayers(p -> true)) {
            level.sendParticles(player, godRay, true, x, y, z, 0, r, g, b, 1.0D);
        }
    }

    @Override
    public void summon(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        SpellStat effectiveStats = stats == null ? defaultStats() : stats;
        int count = summonCount(caster, wandStack);
        Component fixedFocusName = renamedFocusName(wandStack);
        int potencyLevel = Math.max(0, effectiveStats.getPotency())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get()));
        int durationLevel = Math.max(0, effectiveStats.getDuration() - defaultStats().getDuration())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get()));

        int spawned = 0;
        for (int i = 0; i < count; i++) {
            PlayerMobServant servant = ModEntityTypes.PLAYER_MOB_SERVANT.get().create(level);
            if (servant == null) {
                continue;
            }

            BlockPos spawnPos = findSpawnPosition(level, caster, servant, i);
            servant.moveTo(spawnPos, caster.getYRot(), 0.0F);
            MobUtil.moveDownToGround(servant);
            servant.setTrueOwner(caster);
            if (fixedFocusName != null) {
                servant.setFixedCustomName(fixedFocusName);
            } else if (caster instanceof Player player) {
                servant.setMobName(selectMobName(player, i));
            }
            servant.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), MobSpawnType.MOB_SUMMONED, null, null);
            servant.setTrueOwner(caster);
            if (fixedFocusName != null) {
                servant.setFixedCustomName(fixedFocusName);
            }
            servant.setLimitedLife(lifespanFor(caster, durationLevel));
            servant.setPersistenceRequired();
            servant.setSummonPotency(potencyLevel);
            buffSummon(caster, servant, potencyLevel);
            setInitialTarget(servant, caster);

            if (level.addFreshEntity(servant)) {
                ++spawned;
                summonAdvancement(caster, servant);
                sendGodRay(level, servant.getX(), servant.getY(), servant.getZ());
                level.playSound(null, servant.getX(), servant.getY(0.7D), servant.getZ(),
                        SUMMON_SOUND, net.minecraft.sounds.SoundSource.PLAYERS, 0.7F,
                        1.25F + servant.getRandom().nextFloat() * 0.35F);
            } else {
                servant.discard();
            }
        }
        if (spawned > 0) {
            SummonDown(caster);
        }
    }

    private int summonCount(LivingEntity caster, ItemStack wandStack) {
        if (wandStack.isEmpty() || wandStack.is(ModItems.DARK_WAND.get())) {
            return 1;
        }
        if (wandStack.getItem() instanceof IWand) {
            return 2 + caster.getRandom().nextInt(3);
        }
        return 1;
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

    private Component renamedFocusName(ItemStack wandStack) {
        ItemStack focus = focusStack(wandStack);
        if (focus.isEmpty() || !focus.is(GeneratedMod.PLAYER_MOB_FOCUS.get())
                || !focus.hasCustomHoverName()) {
            return null;
        }
        Component name = focus.getHoverName();
        return name.getString().trim().isEmpty() ? null : name;
    }

    private int lifespanFor(LivingEntity caster, int durationLevel) {
        long lifespan = (long) BASE_LIFESPAN_TICKS
                + Math.max(0L, (long) durationLevel) * DURATION_BONUS_PER_LEVEL_TICKS;
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1L, lifespan));
    }

    private void setInitialTarget(PlayerMobServant servant, LivingEntity caster) {
        LivingEntity target = caster instanceof Mob mob ? mob.getTarget() : caster.getLastHurtMob();
        if (target != null && target.isAlive() && !servant.isAlliedTo(target)) {
            servant.setTarget(target);
        }
    }

    private String selectMobName(Player player, int index) {
        List<String> names = PlayerMobServantConfig.mobNames();
        if (names.isEmpty()) {
            return "";
        }
        return names.get((player.getRandom().nextInt(names.size()) + index) % names.size());
    }

    private BlockPos findSpawnPosition(ServerLevel level, LivingEntity caster, Mob servant, int index) {
        BlockPos origin = caster.blockPosition();
        BlockPos candidate = BlockFinder.SummonRadius(origin, servant, level, 3 + index, 8);
        return candidate == null ? origin.offset(index + 1, 0, 2) : candidate;
    }
}
