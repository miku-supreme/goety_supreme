package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.client.particles.GatherTrailParticleOption;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.SummonSpell;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import com.Polarice3.Goety.utils.WandUtil;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeFociConfig;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.SakuraSpiritGear;
import cn.blockforge.generated.goetysupreme.entity.SakuraSpiritServant;
import cn.blockforge.generated.goetysupreme.spell.SakuraSpiritSummonSpell;

public final class SakuraSpiritSummonSpell extends SummonSpell {

    private static final double COUNT_RADIUS = 256.0D;

    private static final int GATHER_COLOR = 16758213;

    private static final int SUMMON_COLOR_A = 16758213;
    private static final int SUMMON_COLOR_B = 16761035;

    private static final UUID KEEPER_UUID_A = UUID.fromString("85f1b302-9b3b-4a55-a4d2-cfffec87061a");
    private static final UUID KEEPER_UUID_B = UUID.fromString("c3783bb8-677a-40f1-9955-e04a2ba3f402");

    private static final String[][] KEEPER_ARMOR_SETS = {
            {"pasterdream:dyedream_armor_helmet", "pasterdream:dyedream_armor_chestplate",
                    "pasterdream:dyedream_armor_leggings", "pasterdream:dyedream_armor_boots"},
            {"cherry_tools:cherry_armor_helmet", "cherry_tools:cherry_armor_chestplate",
                    "cherry_tools:cherry_armor_leggings", "cherry_tools:cherry_armor_boots"},
            {"justrosegold:rose_gold_helmet", "justrosegold:rose_gold_chestplate",
                    "justrosegold:rose_gold_leggings", "justrosegold:rose_gold_boots"},
    };
    private static final String[][] KEEPER_WEAPONS = {
            {"pasterdream:dyedream_sword", "pasterdream:dyedream_axe"},
            {"jerotesvillage:meror_metal_sword", "jerotesvillage:meror_metal_axe"},
            {"justrosegold:rose_gold_sword", "justrosegold:rose_gold_axe"},
    };
    private static final String[][] KEEPER_WEAPON_ENCHANTS = {
            {"fancyenchantments:advanced_sharpness", "5"},
            {"sharpness", "5"},
            {"x_enchantment:ignored_armor", "2"},
            {"l2complements:durable_armor", "2"},
            {"binding_curse", "1"},
            {"vanishing_curse", "1"},
            {"fancyenchantments:purifying", "3"},
            {"goety_revelation:reality_piercer", "5"},
    };
    private static final String[][] KEEPER_ARMOR_ENCHANTS = {
            {"fancyenchantments:advanced_protection", "2"},
            {"protection", "1"},
            {"l2complements:durable_armor", "2"},
            {"binding_curse", "1"},
            {"vanishing_curse", "1"},
    };
    private static final String[][] KEEPER_EFFECTS = {
            {"minecraft:resistance", "1"},
            {"jerotes:magic_absorption", "1"},
            {"more_potion_effects:huge_force", "1"},
            {"potioncore:magic_shield", "1"},
            {"more_potion_effects:magic_shield", "1"},
            {"goetyawaken:enchantment_sharpness", "3"},
    };

    @Override
    public int defaultSoulCost() {
        return SakuraSpiritFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return SakuraSpiritFocusConfig.CAST_DURATION.get();
    }

    @Override
    public int defaultSpellCooldown() {
        return SakuraSpiritFocusConfig.COOLDOWN.get();
    }

    @Override
    public int SummonDownDuration() {
        return SakuraSpiritFocusConfig.SUMMON_DOWN_DURATION.get();
    }

    @Override
    public int summonLimit() {
        return SakuraSpiritConfig.SUMMON_LIMIT.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.WILD;
    }

    @Override
    public Predicate<LivingEntity> summonPredicate() {
        return entity -> entity instanceof SakuraSpiritServant;
    }

    @Override
    public void useParticle(Level level, LivingEntity caster, ItemStack wandStack) {
        if (level instanceof ServerLevel serverLevel) {
            ServerParticleUtil.gatheringParticles(
                    new GatherTrailParticleOption(new ColorUtil(GATHER_COLOR),
                            caster.position().add(0.0D, 2.0D, 0.0D)),
                    caster, serverLevel, 3);
        }
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new java.util.ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        enchantments.add(ModEnchantments.DURATION.get());
        return enchantments;
    }

    @Override
    public boolean conditionsMet(ServerLevel level, LivingEntity caster) {
        if (isShifting(caster)) {
            return true;
        }
        LivingEntity owner = summonOwner(caster);
        if (owner == null) {
            return false;
        }
        if (countSummoned(level, caster, owner) >= summonLimit()) {
            if (caster instanceof Player player) {
                player.displayClientMessage(Component.translatable("info.goety.summon.limit"), true);
            }
            return false;
        }
        return true;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        this.commonResult(level, caster);
        if (isShifting(caster)) {
            return;
        }
        LivingEntity summonOwner = summonOwner(caster);
        if (summonOwner == null) {
            return;
        }
        ItemStack main = caster.getMainHandItem();
        ItemStack off = caster.getOffhandItem();
        boolean holdsWand = main.getItem() instanceof IWand || off.getItem() instanceof IWand;
        if (!holdsWand) {
            return;
        }
        boolean rightStaff = this.rightStaff(main) || this.rightStaff(off);
        int spawnCount = rightStaff
                ? SupremeFociConfig.randomSummonCount(summonOwner,
                        SakuraSpiritFocusConfig.STAFF_SUMMON_COUNT_MIN,
                        SakuraSpiritFocusConfig.STAFF_SUMMON_COUNT_MAX)
                : SupremeFociConfig.randomSummonCount(summonOwner,
                        SakuraSpiritFocusConfig.SUMMON_COUNT_MIN,
                        SakuraSpiritFocusConfig.SUMMON_COUNT_MAX);
        int potency = stats.getPotency();
        if (WandUtil.enchantedFocus(caster)) {
            potency += WandUtil.getPotencyLevel(caster);
        }
        int durationLevel = Math.max(0, stats.getDuration() - defaultStats().getDuration())
                + Math.max(0, focusEnchantmentLevel(wandStack, ModEnchantments.DURATION.get()));
        boolean keeper = KEEPER_UUID_A.equals(summonOwner.getUUID())
                || KEEPER_UUID_B.equals(summonOwner.getUUID());
        for (int i = 0; i < spawnCount; i++) {
            SakuraSpiritServant spirit = (SakuraSpiritServant) ModEntityTypes.SAKURA_SPIRIT_SERVANT.get().create(level);
            if (spirit == null) {
                continue;
            }
            BlockPos spawnPos = BlockFinder.SummonRadius(caster.blockPosition(), spirit, level);
            Vec3 bottom = Vec3.atBottomCenterOf(spawnPos);
            spirit.setTrueOwner(summonOwner);
            spirit.setHostile(false);
            spirit.setNatural(false);
            if (!keeper) {
                spirit.populateDefaultEquipmentSlots(level.getRandom(),
                        level.getCurrentDifficultyAt(spirit.blockPosition()));
                for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.HEAD,
                        EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
                    SakuraSpiritGear.enchantRandomly(spirit.getItemBySlot(slot), level.getRandom());
                }
            } else {
                empowerKeeperSpirit(spirit);
                dressKeeperSpirit(spirit, level);
            }
            spirit.setPos(bottom.x, bottom.y, bottom.z);
            MobUtil.moveDownToGround(spirit);
            spirit.setPersistenceRequired();
            spirit.setLimitedLife(lifespanFor(level, durationLevel));
            this.buffSummon(caster, spirit, potency);
            this.SummonSap(caster, spirit);
            this.setTarget(caster, spirit);
            level.addFreshEntity(spirit);
            ServerParticleUtil.summonUndeadParticles(level, spirit,
                    new ColorUtil(1.0F, 0.7F, 0.8F, 1.0F), SUMMON_COLOR_A, SUMMON_COLOR_B);
            this.playSound(level, spirit, SoundEvents.AMETHYST_BLOCK_CHIME,
                    0.3F + level.random.nextFloat() * 0.2F, 1.0F);
        }
        this.commonResultHit(level, caster);
        this.SummonDown(caster);
        this.playSound(level, caster, SoundEvents.AMETHYST_BLOCK_RESONATE, 0.5F, 1.0F);
    }

    @Nullable
    private static LivingEntity summonOwner(LivingEntity caster) {
        if (caster instanceof Player player) {
            return player;
        }
        if (caster instanceof PlayerMobServant servant) {
            LivingEntity owner = servant.getMasterOwner();
            return owner != null ? owner : servant.getTrueOwner();
        }
        return null;
    }

    private int countSummoned(ServerLevel level, LivingEntity caster, LivingEntity owner) {
        return level.getEntitiesOfClass(SakuraSpiritServant.class,
                AABB.ofSize(caster.position(), COUNT_RADIUS, COUNT_RADIUS, COUNT_RADIUS),
                servant -> owner.getUUID().equals(servant.getOwnerId())).size();
    }

    private int lifespanFor(ServerLevel level, int durationLevel) {
        int random = SakuraSpiritConfig.SUMMON_LIFESPAN_RANDOM.get();
        long base = SakuraSpiritConfig.SUMMON_LIFESPAN.get().longValue()
                + (random <= 0 ? 0L : level.random.nextInt(random));
        long lifespan = base + Math.max(0L, (long) durationLevel) * SakuraSpiritConfig.SUMMON_DURATION_BONUS.get();
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1L, lifespan));
    }

    private int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        if (wandStack == null || wandStack.isEmpty() || !(wandStack.getItem() instanceof IWand)
                || enchantment == null) {
            return 0;
        }
        ItemStack focus = IWand.getFocus(wandStack);
        return focus == null ? 0 : focus.getEnchantmentLevel(enchantment);
    }

    private static void empowerKeeperSpirit(SakuraSpiritServant spirit) {
        setAttribute(spirit, Attributes.MAX_HEALTH, 35.0D);
        setAttribute(spirit, Attributes.ATTACK_DAMAGE, 7.5D);
        setAttribute(spirit, Attributes.KNOCKBACK_RESISTANCE, 0.4D);
        setAttribute(spirit, Attributes.ARMOR, 8.0D);
        setAttribute(spirit, Attributes.ARMOR_TOUGHNESS, 4.0D);
        spirit.setHealth(35.0F);
    }

    private static void setAttribute(LivingEntity entity,
            net.minecraft.world.entity.ai.attributes.Attribute attribute, double value) {
        AttributeInstance instance = entity.getAttribute(attribute);
        if (instance != null) {
            instance.setBaseValue(value);
        }
    }

    private static void dressKeeperSpirit(SakuraSpiritServant spirit, ServerLevel level) {
        java.util.Random random = new java.util.Random();
        int setIndex = random.nextInt(KEEPER_ARMOR_SETS.length);
        String[] armor = KEEPER_ARMOR_SETS[setIndex];
        String weapon = KEEPER_WEAPONS[setIndex][random.nextInt(4) < 3 ? 0 : 1];
        equipKeeperItem(spirit, EquipmentSlot.HEAD, armor[0]);
        equipKeeperItem(spirit, EquipmentSlot.CHEST, armor[1]);
        equipKeeperItem(spirit, EquipmentSlot.LEGS, armor[2]);
        equipKeeperItem(spirit, EquipmentSlot.FEET, armor[3]);
        equipKeeperItem(spirit, EquipmentSlot.MAINHAND, weapon);
        for (String[] effectEntry : KEEPER_EFFECTS) {
            MobEffect effect = lookupEffect(effectEntry[0]);
            if (effect != null) {
                spirit.addEffect(new MobEffectInstance(effect, -1,
                        Integer.parseInt(effectEntry[1]) - 1, false, true));
            }
        }
    }

    private static void equipKeeperItem(SakuraSpiritServant spirit, EquipmentSlot slot, String itemId) {
        ResourceLocation id = ResourceLocation.tryParse(itemId);
        if (id == null) {
            return;
        }
        Item item = ForgeRegistries.ITEMS.getValue(id);
        if (item == null || item == Items.AIR) {
            return;
        }
        ItemStack stack = new ItemStack(item);
        stack.getOrCreateTag().putBoolean("Unbreakable", true);
        if (slot == EquipmentSlot.MAINHAND) {
            for (String[] enchantEntry : KEEPER_WEAPON_ENCHANTS) {
                applyEnchant(stack, enchantEntry[0], Integer.parseInt(enchantEntry[1]));
            }
        } else if (slot.getType() == EquipmentSlot.Type.ARMOR) {
            for (String[] enchantEntry : KEEPER_ARMOR_ENCHANTS) {
                applyEnchant(stack, enchantEntry[0], Integer.parseInt(enchantEntry[1]));
            }
            if (slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET) {
                SakuraSpiritGear.enchantConcealment(stack);
            }
        }
        spirit.setItemSlot(slot, stack);
    }

    private static void applyEnchant(ItemStack stack, String enchantmentId, int level) {
        SakuraSpiritGear.enchantByLookup(stack, enchantmentId, level);
    }

    @Nullable
    private static MobEffect lookupEffect(String id) {
        ResourceLocation location = ResourceLocation.tryParse(id);
        if (location == null) {
            return null;
        }
        return ForgeRegistries.MOB_EFFECTS.getValue(location);
    }
}
