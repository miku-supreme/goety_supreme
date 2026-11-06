package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.items.magic.IFocus;
import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.ISpell;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.SummonSpell;
import com.Polarice3.Goety.init.ModAttributes;
import com.Polarice3.Goety.utils.MobUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import cn.blockforge.generated.goetysupreme.ai.PlayerMobWandSpellGoal;
import cn.blockforge.generated.goetysupreme.spell.EndermanHandFocusSpell;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.PlayerMobWandCasting;
import cn.blockforge.generated.goetysupreme.util.TargetImmunity;

public final class PlayerMobWandCasting {

    private static final int BUFF_REFRESH_TICKS = 200;

    private static final float SELF_HEAL_RATIO = 0.75F;
    private static final float ALLY_HEAL_RATIO = 0.5F;

    private static final Set<String> AOE_SPELLS = Set.of(
            "ShockwaveSpell", "SonicBoomSpell", "FangSpell", "TeethSpell", "FeastSpell", "SpikeSpell",
            "QuakingSpell", "EarthFistSpell", "EruptionSpell", "WarStompSpell", "ScatterSpell",
            "RotationSpell", "PulverizeSpell", "SmackStoneSpell",
            "FireBlastSpell", "LavaballSpell", "MagmaSpell", "MeteorShowerSpell", "FireballSpell",
            "IgniteSpell", "BombardmentSpell", "FlameStrikeSpell",
            "BlizzardSpell", "IceStormSpell", "HailSpell", "FrostNovaSpell", "IceBouquetSpell",
            "ThunderstormSpell", "MonsoonSpell", "DischargeSpell", "LightningSpell", "ThunderboltSpell",
            "CycloneSpell", "WhirlwindSpell", "RazorWindSpell", "UpdraftSpell", "WindBlastSpell",
            "WindHornSpell", "BlossomSpell", "OvergrowthSpell", "EntanglingSpell", "SwarmSpell",
            "TidalSpell", "GulfTentacleSpell", "WaterJetSpell", "TridentStormSpell", "GuardianSpell",
            "WeakeningSpell", "CorruptedBeamSpell", "HauntedSkullSpell", "VoidRiftSpell",
            "VoidShockSpell", "VoidBombSpell", "BioMineSpell", "PrismaBeamSpell", "ShockingSpell");

    private static final Set<String> SELF_BUFF_SPELLS = Set.of(
            "IronHideSpell", "BulwarkSpell", "WardingSpell", "IllusionSpell", "FeastSpell",
            "CushionSpell", "FlyingSpell", "ChillHideSpell");

    private static final Set<String> DISPLACEMENT_SPELLS = Set.of(
            "BlinkSpell", "LaunchSpell", "BoltingSpell", "EndermanHandFocusSpell");

    private static final Set<String> SELF_CENTERED_AOE_SPELLS = Set.of(
            "WeakeningSpell", "WindHornSpell", "WhirlwindSpell", "ShockwaveSpell", "FrostNovaSpell",
            "DischargeSpell", "FireBlastSpell", "WarStompSpell", "FeastSpell", "TridentStormSpell",
            "TemblorClapSpell",
            "EMPSpell", "AmethystClusterSpell", "EarthShakeSpell", "DesertCrushSpell",
            "SandstormSpell", "ThunderRageSpell", "BattlefieldSpell",
            "HogChargeSpell");

    private static final Set<String> CONE_SPELLS = Set.of(
            "FireBreathSpell", "FrostBreathSpell", "WindBlastSpell", "SonicBoomSpell",
            "AshenBreathSpell", "VoidRuneSpell",
            "WitherBreathSpell");

    private static final Set<String> RANGED_SPELLS = Set.of(
            "ArrowRainSpell", "BioMineSpell", "BlizzardSpell", "BurrowingSpell", "EarthFistSpell",
            "ElectroOrbSpell", "EntanglingSpell", "EruptionSpell", "FangSpell", "FlameStrikeSpell",
            "GulfTentacleSpell", "HailSpell", "IceBouquetSpell", "IceChunkSpell",
            "LeechingSpell", "LightningSpell", "MeteorShowerSpell", "MonsoonSpell", "OvergrowthSpell",
            "QuakingSpell", "ShockingSpell", "SpikeSpell", "SwarmSpell", "TeethSpell",
            "ThunderboltSpell", "TidalSpell", "UpdraftSpell", "VoidRiftSpell", "WaterJetSpell",
            "BarricadeSpell",
            "StormSerpentSpell",
            "RunePrisonSpell");

    private static final Map<String, Double> SELF_CENTERED_FIXED_RADIUS = Map.ofEntries(
            Map.entry("TridentStormSpell", 8.0D),
            Map.entry("EMPSpell", 5.0D),
            Map.entry("AmethystClusterSpell", 10.0D),
            Map.entry("EarthShakeSpell", 15.0D),
            Map.entry("DesertCrushSpell", 12.0D),
            Map.entry("SandstormSpell", 8.0D),
            Map.entry("HogChargeSpell", 6.0D),
            Map.entry("ThunderRageSpell", 18.0D),
            Map.entry("BattlefieldSpell", 12.0D));

    private static final Map<String, Double> RANGED_FIXED_RANGE = Map.ofEntries(
            Map.entry("StormSerpentSpell", 8.0D),
            Map.entry("QuakingSpell", 15.0D),
            Map.entry("LeechingSpell", 20.0D),
            Map.entry("MeteorShowerSpell", 20.0D));

    private static final Set<String> SNEAK_SELF_CENTERED_SPELLS = Set.of("VoidRuneSpell", "TeethSpell");

    private static final Set<String> CLOSE_RANGE_SELF_CENTERED_SPELLS = Set.of(
            "DesertCrushSpell", "EarthShakeSpell", "AmethystClusterSpell");

    private static final Set<String> CLOSE_RANGE_WINDUP_SPELLS = Set.of("FlameStrikeSpell");

    public static final String INVASION_NS = "goety_invasion";
    public static final String INVOKER_FANGS_FOCUS = "invoker_fangs_focus";
    public static final String ILL_NECROMANCER_SHIELD_FOCUS = "ill_necromancer_shield_focus";

    private static final Set<String> UTILITY_SPELLS = Set.of(
            "EnderChestSpell", "CraftingSpell", "GlowLightSpell", "SoulLightSpell", "IlluminateSpell",
            "CommandSpell", "CallSpell", "TroopSpell", "RecallSpell", "SensingSpell",
            "FlyingSpell", "CushionSpell", "BlinkSpell", "EndWalkSpell", "TunnelSpell",
            "GrappleSpell", "TelekinesisSpell", "BanishSpell");

    private PlayerMobWandCasting() {
    }

    public record FocusEntry(ItemStack stack, ISpell spell, FocusSource source, boolean fromWand,
            int boundPackSlot) {

        public FocusEntry(ItemStack stack, ISpell spell, FocusSource source, boolean fromWand) {
            this(stack, spell, source, fromWand, -1);
        }

        public boolean isBoundPackFocus() {
            return boundPackSlot >= 0;
        }

        public Item item() {
            return stack.getItem();
        }

        public String itemId() {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item());
            return id == null ? "" : id.toString();
        }

        public boolean isSummon() {
            return spell instanceof SummonSpell;
        }

        public boolean isHeal() {
            return "SoulHealSpell".equals(spellName(spell));
        }

        public boolean isChill() {
            return "ChillHideSpell".equals(spellName(spell));
        }

        public boolean isWeakening() {
            return "WeakeningSpell".equals(spellName(spell));
        }

        public boolean isCharge() {
            return "ChargeSpell".equals(spellName(spell));
        }

        public boolean isSelfBuff() {
            return SELF_BUFF_SPELLS.contains(spellName(spell));
        }

        public boolean isDisplacement() {
            return DISPLACEMENT_SPELLS.contains(spellName(spell));
        }

        public boolean isTeleportDisplacement() {
            return "BlinkSpell".equals(spellName(spell))
                    || "EndermanHandFocusSpell".equals(spellName(spell));
        }

        public boolean isDashCharge() {
            return "BoltingSpell".equals(spellName(spell));
        }

        public boolean isSelfCenteredAoe() {
            return SELF_CENTERED_AOE_SPELLS.contains(spellName(spell));
        }

        public boolean isConeSpell() {
            return CONE_SPELLS.contains(spellName(spell));
        }

        public boolean isRangedSpell() {
            return RANGED_SPELLS.contains(spellName(spell));
        }

        public boolean isWindBlast() {
            return "WindBlastSpell".equals(spellName(spell));
        }

        public boolean isUtility() {
            return UTILITY_SPELLS.contains(spellName(spell));
        }

        public boolean isAoe() {
            return AOE_SPELLS.contains(spellName(spell));
        }

        public boolean isSneakSelfCentered() {
            return SNEAK_SELF_CENTERED_SPELLS.contains(spellName(spell));
        }

        public boolean isCloseRangeSelfCentered() {
            return CLOSE_RANGE_SELF_CENTERED_SPELLS.contains(spellName(spell));
        }

        public boolean isCloseRangeWindup() {
            return CLOSE_RANGE_WINDUP_SPELLS.contains(spellName(spell));
        }

        public boolean isNecroShield() {
            return isItem(INVASION_NS, ILL_NECROMANCER_SHIELD_FOCUS);
        }

        public boolean isItem(String namespace, String path) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item());
            return id != null && id.getNamespace().equals(namespace) && id.getPath().equals(path);
        }
    }

    public static boolean isItem(ItemStack stack, String namespace, String path) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return id != null && id.getNamespace().equals(namespace) && id.getPath().equals(path);
    }

    public static boolean isGoetyItem(ItemStack stack, String path) {
        return isItem(stack, "goety", path);
    }

    public static boolean isNamespace(ItemStack stack, String namespace) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return id != null && id.getNamespace().equals(namespace);
    }

    public interface FocusSource {
        ItemStack get();

        void set(ItemStack stack);

        static FocusSource of(Supplier<ItemStack> getter, Consumer<ItemStack> setter) {
            return new FocusSource() {
                @Override
                public ItemStack get() {
                    ItemStack stack = getter.get();
                    return stack == null ? ItemStack.EMPTY : stack;
                }

                @Override
                public void set(ItemStack stack) {
                    setter.accept(stack == null ? ItemStack.EMPTY : stack);
                }
            };
        }
    }

    private static String spellName(ISpell spell) {
        return spell == null ? "" : spell.getClass().getSimpleName();
    }

    public static List<FocusEntry> catalog(PlayerMobServant mob, ItemStack wand) {
        List<FocusEntry> entries = new ArrayList<>();
        if (wand == null || wand.isEmpty()) {
            return entries;
        }
        ItemStack wandFocus = IWand.getFocus(wand);
        ItemStack boundPack = boundPackInWand(wand, wandFocus);
        if (boundPack != null) {
            addBoundPackFoci(entries, boundPack);
        } else {
            addFocus(entries, wandFocus, null, true);
        }

        var storage = mob.getServantStorage();
        for (int i = 0; i < storage.getContainerSize(); i++) {
            int slot = i;
            ItemStack stack = storage.getItem(slot);
            if (addFocus(entries, stack, FocusSource.of(() -> storage.getItem(slot),
                    value -> storage.setItem(slot, value)), false)) {
                continue;
            }
            addContainerFoci(entries, stack);
        }
        return entries;
    }

    private static boolean addFocus(List<FocusEntry> entries, ItemStack stack, FocusSource source,
            boolean fromWand) {
        if (stack == null || stack.isEmpty() || !(stack.getItem() instanceof IFocus focus)) {
            return false;
        }
        ISpell spell = focus.getSpell();
        if (spell == null) {
            return false;
        }
        entries.add(new FocusEntry(stack, spell, source, fromWand));
        return true;
    }

    private static void addContainerFoci(List<FocusEntry> entries, ItemStack container) {
        if (container == null || container.isEmpty()) {
            return;
        }
        if (isBoundFocusPack(container)) {
            return;
        }
        IItemHandler handler = container.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
        if (handler == null) {
            return;
        }
        for (int i = 0; i < handler.getSlots(); i++) {
            int slot = i;
            ItemStack inner = handler.getStackInSlot(slot);
            addFocus(entries, inner, FocusSource.of(() -> handler.getStackInSlot(slot), value -> {
                ItemStack current = handler.getStackInSlot(slot);
                if (!current.isEmpty()) {
                    handler.extractItem(slot, current.getCount(), false);
                }
                if (value != null && !value.isEmpty()) {
                    handler.insertItem(slot, value, false);
                }
            }), false);
        }
    }

    private static IItemHandler wandHandler(ItemStack wand) {
        if (wand == null || wand.isEmpty()) {
            return null;
        }
        return wand.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
    }

    private static final String BOUND_FOCUS_PACK_NAMESPACE = "goeticlegacy";
    private static final String BOUND_FOCUS_PACK_PATH = "bound_focus_pack";

    private static final String BOUND_FOCUS_PACK_SELECTED_TAG = "SelectedSlot";

    public static boolean isBoundFocusPack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return id != null
                && BOUND_FOCUS_PACK_NAMESPACE.equals(id.getNamespace())
                && BOUND_FOCUS_PACK_PATH.equals(id.getPath());
    }

    private static ItemStack boundPackInWand(ItemStack wand, ItemStack wandFocus) {
        IItemHandler handler = wandHandler(wand);
        if (handler != null && handler.getSlots() > 0) {
            ItemStack slot0 = handler.getStackInSlot(0);
            if (isBoundFocusPack(slot0)) {
                return slot0;
            }
        }
        return isBoundFocusPack(wandFocus) ? wandFocus : null;
    }

    private static void addBoundPackFoci(List<FocusEntry> entries, ItemStack pack) {
        IItemHandler handler = pack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
        if (handler == null) {
            return;
        }
        int selected = boundFocusPackSelectedSlot(pack, handler.getSlots());
        for (int i = 0; i < handler.getSlots(); i++) {
            int slot = i;
            ItemStack inner = handler.getStackInSlot(slot);
            if (inner == null || inner.isEmpty() || !(inner.getItem() instanceof IFocus focus)) {
                continue;
            }
            ISpell spell = focus.getSpell();
            if (spell == null) {
                continue;
            }
            FocusSource source = FocusSource.of(() -> handler.getStackInSlot(slot), value -> {
            });
            entries.add(new FocusEntry(inner, spell, source, slot == selected, slot));
        }
    }

    private static int boundFocusPackSelectedSlot(ItemStack pack, int slots) {
        CompoundTag tag = pack.getTag();
        int selected = tag != null && tag.contains(BOUND_FOCUS_PACK_SELECTED_TAG)
                ? tag.getInt(BOUND_FOCUS_PACK_SELECTED_TAG) : -1;
        return selected >= 0 && selected < slots ? selected : -1;
    }

    public static ItemStack boundFocusPackSelected(ItemStack pack) {
        if (!isBoundFocusPack(pack)) {
            return ItemStack.EMPTY;
        }
        IItemHandler handler = pack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
        if (handler == null) {
            return ItemStack.EMPTY;
        }
        int selected = boundFocusPackSelectedSlot(pack, handler.getSlots());
        if (selected < 0) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = handler.getStackInSlot(selected);
        return stack == null ? ItemStack.EMPTY : stack;
    }

    private static boolean setBoundFocusPackSelected(ItemStack pack, int slot) {
        if (!isBoundFocusPack(pack) || slot < 0) {
            return false;
        }
        IItemHandler handler = pack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
        if (handler == null || slot >= handler.getSlots()) {
            return false;
        }
        pack.getOrCreateTag().putInt(BOUND_FOCUS_PACK_SELECTED_TAG, slot);
        return true;
    }

    public static void equipFocus(ItemStack wand, FocusEntry entry) {
        if (entry == null || entry.fromWand()) {
            return;
        }
        IItemHandler handler = wandHandler(wand);
        if (handler == null || handler.getSlots() <= 0) {
            return;
        }
        if (entry.isBoundPackFocus()) {
            ItemStack pack = handler.getStackInSlot(0);
            if (isBoundFocusPack(pack) && setBoundFocusPackSelected(pack, entry.boundPackSlot())) {
                return;
            }
        }
        ItemStack chosen = entry.source().get();
        if (chosen.isEmpty()) {
            return;
        }
        ItemStack current = handler.getStackInSlot(0);
        if (isBoundFocusPack(current)) {
            return;
        }
        if (!current.isEmpty() && ItemStack.isSameItemSameTags(current, chosen)) {
            return;
        }
        ItemStack old = current.copy();
        entry.source().set(ItemStack.EMPTY);
        if (!old.isEmpty()) {
            handler.extractItem(0, old.getCount(), false);
        }
        ItemStack remainder = handler.insertItem(0, chosen.copyWithCount(1), false);
        if (!remainder.isEmpty()) {
            entry.source().set(chosen);
            if (!old.isEmpty()) {
                handler.insertItem(0, old, false);
            }
            return;
        }
        if (!old.isEmpty()) {
            entry.source().set(old);
        }
    }

    public static boolean isBuffFocus(PlayerMobServant mob, FocusEntry entry) {
        return entry != null && !entry.isWeakening() && !entry.isCharge()
                && (entry.isSelfBuff() || !mob.getWandBuffEffects(entry.itemId()).isEmpty());
    }

    public static boolean isSupportOnlyCatalog(PlayerMobServant mob, ItemStack wand) {
        List<FocusEntry> entries = catalog(mob, wand);
        if (entries.isEmpty()) {
            return false;
        }
        for (FocusEntry entry : entries) {
            if (!(entry.isHeal() || entry.isChill() || entry.isWeakening() || entry.isCharge()
                    || entry.isDisplacement() || isBuffFocus(mob, entry))) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasUsableSupportFocus(PlayerMobServant mob, ItemStack wand) {
        for (FocusEntry entry : catalog(mob, wand)) {
            if (mob.isWandFocusOnCooldown(entry.itemId())) {
                continue;
            }
            if (entry.isHeal()) {
                if (healNeeded(mob, entry)) {
                    return true;
                }
            } else if (entry.isChill() || isBuffFocus(mob, entry)) {
                if (buffNeeded(mob, entry)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean selfHealNeeded(PlayerMobServant mob) {
        return mob.getHealth() < mob.getMaxHealth() * SELF_HEAL_RATIO;
    }

    public static boolean healNeeded(PlayerMobServant mob, FocusEntry entry) {
        if (entry == null) {
            return false;
        }
        if (mob.getHealth() < mob.getMaxHealth() * SELF_HEAL_RATIO) {
            return true;
        }
        return allyHealNeeded(mob, entry);
    }

    public static boolean allyHealNeeded(PlayerMobServant mob, FocusEntry entry) {
        if (entry == null) {
            return false;
        }
        double reach = focusRadius(mob, entry) * 8.0D;
        if (reach <= 0.0D) {
            return false;
        }
        for (LivingEntity ally : mob.level().getEntitiesOfClass(LivingEntity.class,
                mob.getBoundingBox().inflate(reach))) {
            if (!ally.isAlive() || ally == mob || !isHealAlly(mob, ally)) {
                continue;
            }
            if (ally.getHealth() >= ally.getMaxHealth() * ALLY_HEAL_RATIO) {
                continue;
            }
            if (mob.distanceTo(ally) <= reach) {
                return true;
            }
        }
        return false;
    }

    private static boolean isHealAlly(PlayerMobServant mob, LivingEntity entity) {
        if (entity == null || !entity.isAlive()) {
            return false;
        }
        if (entity == mob) {
            return true;
        }
        LivingEntity master = mob.getMasterOwner();
        if (master == null) {
            master = mob.getTrueOwner();
        }
        if (entity == master) {
            return true;
        }
        return MobUtil.areAllies(mob, entity) || MobUtil.getOwner(entity) == mob;
    }

    public static boolean buffNeeded(PlayerMobServant mob, FocusEntry entry) {
        List<String> tracked = mob.getWandBuffEffects(entry.itemId());
        if (tracked.isEmpty()) {
            return true;
        }
        for (String id : tracked) {
            MobEffectInstance instance = effectById(mob, id);
            if (instance == null || instance.getDuration() <= BUFF_REFRESH_TICKS) {
                return true;
            }
        }
        return false;
    }

    private static MobEffectInstance effectById(PlayerMobServant mob, String id) {
        ResourceLocation wanted = ResourceLocation.tryParse(id);
        if (wanted == null) {
            return null;
        }
        for (MobEffectInstance instance : mob.getActiveEffects()) {
            if (wanted.equals(BuiltInRegistries.MOB_EFFECT.getKey(instance.getEffect()))) {
                return instance;
            }
        }
        return null;
    }

    public static boolean weakenNeeded(LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return false;
        }
        boolean hasSapped = GoetyEffects.SAPPED != null && target.hasEffect(GoetyEffects.SAPPED.get());
        if (hasSapped || target.hasEffect(MobEffects.WEAKNESS)) {
            return false;
        }
        return TargetImmunity.weakeningLands(target);
    }

    public static double focusRadius(PlayerMobServant mob, FocusEntry entry) {
        double base = entry.spell().defaultStats().getRadius();
        Double fixed = SELF_CENTERED_FIXED_RADIUS.get(spellName(entry.spell()));
        if (fixed != null) {
            base = fixed;
        }
        int enchant = entry.stack().getEnchantmentLevel(ModEnchantments.RADIUS.get());
        LivingEntity master = mob.getMasterOwner();
        if (master == null) {
            master = mob.getTrueOwner();
        }
        double attribute = master == null ? 0.0D : ModAttributes.getRadius(master);
        return base + attribute + enchant;
    }

    public static double focusRange(PlayerMobServant mob, FocusEntry entry) {
        String name = spellName(entry.spell());
        double base = entry.spell().defaultStats().getRange();
        Double fixed = RANGED_FIXED_RANGE.get(name);
        if (fixed != null) {
            base = fixed;
        }
        int enchant = entry.stack().getEnchantmentLevel(ModEnchantments.RANGE.get());
        LivingEntity master = mob.getMasterOwner();
        if (master == null) {
            master = mob.getTrueOwner();
        }
        double attribute = master == null ? 0.0D : ModAttributes.getRange(master);
        return base + attribute + (fixed != null ? enchant : enchant * 2);
    }

    public static double longestCastReach(PlayerMobServant mob, ItemStack wand) {
        double best = 0.0D;
        if (wand == null || wand.isEmpty()) {
            return best;
        }
        for (FocusEntry entry : catalog(mob, wand)) {
            if (entry.isDisplacement() || entry.isUtility()
                    || !(entry.isRangedSpell() || entry.isConeSpell())) {
                continue;
            }
            double reach = focusRange(mob, entry);
            if (reach > best) {
                best = reach;
            }
        }
        return best;
    }

    public static com.Polarice3.Goety.common.magic.SpellStat buildStats(ISpell spell,
            LivingEntity master) {
        var stats = spell.defaultStats();
        if (master == null) {
            return stats;
        }
        stats.increasePotency(ModAttributes.getPotency(master, spell));
        stats.increaseDuration(ModAttributes.getDuration(master));
        stats.increaseRange(ModAttributes.getRange(master));
        stats.increaseRadius(ModAttributes.getRadius(master));
        stats.increaseBurning(ModAttributes.getBurning(master));
        stats.increaseVelocity(ModAttributes.getVelocity(master));
        return stats;
    }
}
