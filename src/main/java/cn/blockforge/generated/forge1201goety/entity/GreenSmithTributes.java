package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.client.particles.CircleExplodeParticleOption;
import com.Polarice3.Goety.client.particles.GatherTrailParticleOption;
import com.Polarice3.Goety.client.particles.RisingCircleParticleOption;
import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.client.particles.SoulShockwaveParticleOption;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.PhantomServant;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.common.entities.projectiles.HauntedSkullProjectile;
import com.Polarice3.Goety.common.entities.projectiles.MonsoonCloud;
import com.Polarice3.Goety.common.entities.projectiles.NecroBolt;
import com.Polarice3.Goety.common.entities.projectiles.PoisonQuill;
import com.Polarice3.Goety.common.entities.projectiles.SteamMissile;
import com.Polarice3.Goety.common.entities.projectiles.SurgingOrb;
import com.Polarice3.Goety.common.entities.util.MagicLightningTrap;
import com.Polarice3.Goety.common.entities.util.TridentStorm;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.spells.wild.BlossomSpell;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.server.SThunderBoltPacket;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmithGoals;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.util.CastQueue;
import cn.blockforge.generated.goetysupreme.util.ExecutionDamage;
import cn.blockforge.generated.goetysupreme.util.ExtraSummonWindup;
import cn.blockforge.generated.goetysupreme.renderer.FieldShieldAuraParticle;
import cn.blockforge.generated.goetysupreme.util.FocusSlotRules;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import cn.blockforge.generated.goetysupreme.entity.GreenFireEntity;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithServant;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSoulJarGuard;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithStaffFxLayer;
import cn.blockforge.generated.goetysupreme.util.GreenSmithSunImmunity;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.compat.OminousCompat;
import cn.blockforge.generated.goetysupreme.entity.ReaperSpinEntity;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.compat.SummonCapUtil;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.api.UndeadCapeBearer;
import cn.blockforge.generated.goetysupreme.renderer.WindBlastFx;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.mixin.GoetyNecromancerSoulJarMixin;
import cn.blockforge.generated.goetysupreme.mixin.ReviveServantItemSoulJarGuardMixin;

public final class GreenSmithTributes {

    private GreenSmithTributes() {
    }

    public static InteractionResult mobInteract(AbstractGreenSmith smith, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (smith.getTrueOwner() == player && stack.is(GeneratedMod.GREEN_SMITH_SOUL_JAR.get())
                && smith.soulJarUpgrades < AbstractGreenSmith.MAX_SOUL_JAR_UPGRADES) {
            if (!smith.level().isClientSide) {
                smith.applySoulJarUpgrade(player, stack);
            }
            return InteractionResult.sidedSuccess(smith.level().isClientSide);
        }
        if (smith.getTrueOwner() == player && smith.isAlive() && stack.is(Items.BONE)
                && ServantFeedHeal.feed(smith, player, stack, 2.0F)) {
            return InteractionResult.sidedSuccess(smith.level().isClientSide);
        }
        if (smith.getTrueOwner() == player) {
            if (FocusSlotRules.tryFeedCheatSheet(smith, player, stack, smith.cheatSheets,
                    GreenSmithDefense.getHurtSound(smith, smith.damageSources().generic()),
                    value -> smith.cheatSheets = value)) {
                return InteractionResult.sidedSuccess(smith.level().isClientSide);
            }
            if (!smith.spookyFocusGiven && isItem(stack, AbstractGreenSmith.SPOOKY_FOCUS_ID)) {
                if (!smith.level().isClientSide) {
                    if (grantedFocusCount(smith) >= focusSlots(smith)) {
                        refuseSummon(smith);
                    } else {
                        applyFocusGift(smith, player, stack, true);
                    }
                }
                return InteractionResult.sidedSuccess(smith.level().isClientSide);
            }
            if (!smith.reapingFocusGiven && isItem(stack, AbstractGreenSmith.REAPING_FOCUS_ID)) {
                if (!smith.level().isClientSide) {
                    if (grantedFocusCount(smith) >= focusSlots(smith)) {
                        refuseSummon(smith);
                    } else {
                        applyFocusGift(smith, player, stack, false);
                    }
                }
                return InteractionResult.sidedSuccess(smith.level().isClientSide);
            }
            if (!smith.phantasmFocusGiven && isItem(stack, AbstractGreenSmith.PHANTASM_FOCUS_ID)) {
                if (!smith.level().isClientSide) {
                    if (grantedFocusCount(smith) >= focusSlots(smith)) {
                        refuseSummon(smith);
                    } else {
                        applyPhantasmFocusGift(smith, player, stack);
                    }
                }
                return InteractionResult.sidedSuccess(smith.level().isClientSide);
            }
            ResourceLocation hauntFocus = OminousCompat.item(AbstractGreenSmith.HAUNT_FOCUS_PATH);
            if (!smith.hauntFocusGiven && hauntFocus != null && isItem(stack, hauntFocus)) {
                if (!smith.level().isClientSide) {
                    if (grantedFocusCount(smith) >= focusSlots(smith)) {
                        refuseSummon(smith);
                    } else {
                        applyHauntFocusGift(smith, player, stack);
                    }
                }
                return InteractionResult.sidedSuccess(smith.level().isClientSide);
            }
            if (!smith.boneDruidFocusGiven && isItem(stack, AbstractGreenSmith.BONE_DRUID_FOCUS_ID)) {
                if (!smith.level().isClientSide) {
                    if (grantedFocusCount(smith) >= focusSlots(smith)) {
                        refuseSummon(smith);
                    } else {
                        applyBoneDruidFocusGift(smith, player, stack);
                    }
                }
                return InteractionResult.sidedSuccess(smith.level().isClientSide);
            }
            if (!smith.vexFocusGiven && isItem(stack, AbstractGreenSmith.VEXING_FOCUS_ID)
                    && net.minecraftforge.fml.ModList.get().isLoaded("goetytwilight")
                    && ForgeRegistries.ENTITY_TYPES.containsKey(AbstractGreenSmith.TWILIGHT_WRAITH_ID)) {
                if (!smith.level().isClientSide) {
                    if (grantedFocusCount(smith) >= focusSlots(smith)) {
                        refuseSummon(smith);
                    } else {
                        applyVexFocusGift(smith, player, stack);
                    }
                }
                return InteractionResult.sidedSuccess(smith.level().isClientSide);
            }
            ResourceLocation redstoneFocus = OminousCompat.item(AbstractGreenSmith.REDSTONE_FOCUS_PATH);
            if (!smith.redstoneFocusGiven && redstoneFocus != null && isItem(stack, redstoneFocus)) {
                if (!smith.level().isClientSide) {
                    if (grantedFocusCount(smith) >= focusSlots(smith)) {
                        refuseSummon(smith);
                    } else {
                        applyRedstoneFocusGift(smith, player, stack);
                    }
                }
                return InteractionResult.sidedSuccess(smith.level().isClientSide);
            }
            if (!smith.draugrSoulJarGiven && AbstractGreenSmith.cataclysmLoaded() && isDraugrSoulJar(stack)) {
                if (!smith.level().isClientSide) {
                    smith.applyDraugrSoulJar(player, stack);
                }
                return InteractionResult.sidedSuccess(smith.level().isClientSide);
            }
            LivingEntity stored = storedNecromancer(smith, stack);
            if (stored != null) {
                if (smith.level().isClientSide) {
                    return InteractionResult.sidedSuccess(true);
                }
                if (isSmallBossNecromancer(stored)) {
                    ResourceLocation storedType = ForgeRegistries.ENTITY_TYPES.getKey(stored.getType());
                    if (storedType != null && smith.smallBossSoulJarTypes.contains(storedType)) {
                        return InteractionResult.sidedSuccess(false);
                    }
                    smith.applySmallBossSoulJar(player, stack, stored);
                    return InteractionResult.sidedSuccess(false);
                }
            }
        }
        if (smith.getTrueOwner() == player && player.getMainHandItem().isEmpty() && smith.isAlive()) {
            if (!smith.level().isClientSide) {
                if (!smith.isCasting() && smith.summonCooldown <= 0) {
                    smith.summonCooldown = AbstractGreenSmith.SUMMON_COOLDOWN;
                    smith.startCast(AbstractGreenSmith.CAST_SUMMON);
                    smith.directSummonToMaster = true;
                } else {
                    refuseSummon(smith);
                }
            }
            return InteractionResult.sidedSuccess(smith.level().isClientSide);
        }
        return smith.superMobInteract(player, hand);
    }

    public static void refuseSummon(AbstractGreenSmith smith) {
        smith.playSound(GreenSmithDefense.getHurtSound(smith, smith.damageSources().generic()), 1.0F, 1.0F);
        smith.level().broadcastEntityEvent(smith, AbstractGreenSmith.REFUSE_SHAKE_EVENT);
    }

    public static int focusSlots(AbstractGreenSmith smith) {
        return FocusSlotRules.maxSlots(FocusSlotRules.GREEN_SMITH_BASE_SLOTS, smith.cheatSheets);
    }

    public static int grantedFocusCount(AbstractGreenSmith smith) {
        int count = 0;
        if (smith.spookyFocusGiven) {
            ++count;
        }
        if (smith.reapingFocusGiven) {
            ++count;
        }
        if (smith.phantasmFocusGiven) {
            ++count;
        }
        if (smith.hauntFocusGiven) {
            ++count;
        }
        if (smith.boneDruidFocusGiven) {
            ++count;
        }
        if (smith.vexFocusGiven) {
            ++count;
        }
        if (smith.redstoneFocusGiven) {
            ++count;
        }
        return count;
    }

    public static LivingEntity summonOwner(AbstractGreenSmith smith) {
        LivingEntity owner = smith.getTrueOwner();
        return smith.directSummonToMaster && owner != null ? owner : smith;
    }

    public static boolean isItem(ItemStack stack, ResourceLocation id) {
        net.minecraft.world.item.Item item = ForgeRegistries.ITEMS.getValue(id);
        return item != null && stack.is(item);
    }

    public static boolean isDraugrSoulJar(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (key == null) {
            return false;
        }
        String path = key.getPath();
        return path.contains("draugr") && (path.contains("soul_jar") || path.contains("souljar"));
    }

    public static void applyFocusGift(AbstractGreenSmith smith, Player player, ItemStack stack, boolean spooky) {
        if (spooky) {
            smith.spookyFocusGiven = true;
            smith.getEntityData().set(AbstractGreenSmith.DATA_SPOOKY_FOCUS, true);
        } else {
            smith.reapingFocusGiven = true;
            smith.getEntityData().set(AbstractGreenSmith.DATA_REAPING_FOCUS, true);
        }
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, smith.getX(), smith.getY(0.6D), smith.getZ(),
                    20, smith.getBbWidth() * 0.6D, smith.getBbHeight() * 0.4D,
                    smith.getBbWidth() * 0.6D, 0.03D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, smith.getX(), smith.getY(1.0D), smith.getZ(),
                    12, smith.getBbWidth() * 0.5D, smith.getBbHeight() * 0.3D,
                    smith.getBbWidth() * 0.5D, 0.02D);
        }
        smith.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, spooky ? 1.2F : 0.8F);
    }

    public static void applyPhantasmFocusGift(AbstractGreenSmith smith, Player player, ItemStack stack) {
        smith.phantasmFocusGiven = true;
        smith.getEntityData().set(AbstractGreenSmith.DATA_PHANTASM_FOCUS, true);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, smith.getX(), smith.getY(0.6D), smith.getZ(),
                    20, smith.getBbWidth() * 0.6D, smith.getBbHeight() * 0.4D,
                    smith.getBbWidth() * 0.6D, 0.03D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, smith.getX(), smith.getY(1.0D), smith.getZ(),
                    12, smith.getBbWidth() * 0.5D, smith.getBbHeight() * 0.3D,
                    smith.getBbWidth() * 0.5D, 0.02D);
        }
        smith.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 1.0F);
    }

    public static void applyHauntFocusGift(AbstractGreenSmith smith, Player player, ItemStack stack) {        smith.hauntFocusGiven = true;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, smith.getX(), smith.getY(0.6D), smith.getZ(),
                    20, smith.getBbWidth() * 0.6D, smith.getBbHeight() * 0.4D,
                    smith.getBbWidth() * 0.6D, 0.03D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, smith.getX(), smith.getY(1.0D), smith.getZ(),
                    12, smith.getBbWidth() * 0.5D, smith.getBbHeight() * 0.3D,
                    smith.getBbWidth() * 0.5D, 0.02D);
        }
        smith.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 1.4F);
    }

    public static void applyBoneDruidFocusGift(AbstractGreenSmith smith, Player player, ItemStack stack) {
        smith.boneDruidFocusGiven = true;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, smith.getX(), smith.getY(0.6D), smith.getZ(),
                    20, smith.getBbWidth() * 0.6D, smith.getBbHeight() * 0.4D,
                    smith.getBbWidth() * 0.6D, 0.03D);
            server.sendParticles(ParticleTypes.HAPPY_VILLAGER, smith.getX(), smith.getY(1.0D), smith.getZ(),
                    12, smith.getBbWidth() * 0.5D, smith.getBbHeight() * 0.3D,
                    smith.getBbWidth() * 0.5D, 0.02D);
        }
        smith.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 1.2F);
    }

    public static void applyVexFocusGift(AbstractGreenSmith smith, Player player, ItemStack stack) {
        smith.vexFocusGiven = true;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, smith.getX(), smith.getY(0.6D), smith.getZ(),
                    20, smith.getBbWidth() * 0.6D, smith.getBbHeight() * 0.4D,
                    smith.getBbWidth() * 0.6D, 0.03D);
        }
        smith.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 1.0F);
    }

    public static void applyRedstoneFocusGift(AbstractGreenSmith smith, Player player, ItemStack stack) {
        smith.redstoneFocusGiven = true;
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (smith.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.SOUL, smith.getX(), smith.getY(0.6D), smith.getZ(),
                    20, smith.getBbWidth() * 0.6D, smith.getBbHeight() * 0.4D,
                    smith.getBbWidth() * 0.6D, 0.03D);
        }
        smith.playSound(SoundEvents.SOUL_ESCAPE, 1.0F, 1.0F);
    }

    public static LivingEntity storedNecromancer(AbstractGreenSmith smith, ItemStack stack) {
        if (!(stack.getItem() instanceof ReviveServantItem)) {
            return null;
        }
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains("entity")) {
            return null;
        }
        CompoundTag entityTag = tag.getCompound("entity");
        if (!entityTag.contains("entity")
                || ResourceLocation.tryParse(entityTag.getString("entity")) == null) {
            return null;
        }
        Entity stored = ReviveServantItem.getSummon(stack, smith.level());
        return stored instanceof LivingEntity living ? living : null;
    }

    public static boolean addSmallBossType(AbstractGreenSmith smith, ResourceLocation type) {
        if (type == null || smith.smallBossSoulJarTypes.contains(type)
                || smith.smallBossSoulJarTypes.size() >= 32) {
            return false;
        }
        smith.smallBossSoulJarTypes.add(type);
        return true;
    }

    public static boolean isNecromancerLike(LivingEntity entity) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        return key != null && isNecromancerLike(entity, key);
    }

    public static boolean isNecromancerLike(LivingEntity entity, ResourceLocation key) {
        return key.getPath().contains("necromancer")
                || entity.getClass().getName().contains("Necromancer");
    }

    public static boolean isSmallBossNecromancer(LivingEntity necromancer) {
        if (necromancer == null || necromancer.getMaxHealth() < AbstractGreenSmith.SMALL_BOSS_MIN_HEALTH) {
            return false;
        }
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(necromancer.getType());
        if (key == null) {
            return false;
        }
        String namespace = key.getNamespace();
        String path = key.getPath();
        if ("goety_ladder".equals(namespace) && path.contains("void_necromancer")) {
            return false;
        }
        if (path.contains("wild_mage") || path.contains("wither")) {
            return false;
        }
        return isNecromancerLike(necromancer, key);
    }

    public static int getSoulJarUpgrades(AbstractGreenSmith smith) {
        return smith.soulJarUpgrades;
    }

    public static float getSoulJarSizeScale(AbstractGreenSmith smith) {
        return 1.0F + smith.soulJarUpgrades * 0.2F;
    }

    public static void setSoulJarUpgrades(AbstractGreenSmith smith, int value) {
        smith.soulJarUpgrades = value;
        smith.getEntityData().set(AbstractGreenSmith.DATA_SOUL_JAR_UPGRADES, value);
    }

    public static float soulJarProjectileSpeedScale(AbstractGreenSmith smith) {
        return 1.0F + smith.effectiveSoulJarUpgrades() * AbstractGreenSmith.SOUL_JAR_PROJECTILE_SPEED_PER_LEVEL;
    }

    public static boolean isSkeletonServant(LivingEntity entity) {
        for (Class<?> type = entity.getClass(); type != null; type = type.getSuperclass()) {
            String name = type.getName();
            if ("com.Polarice3.Goety.common.entities.ally.undead.skeleton.AbstractSkeletonServant"
                    .equals(name)
                    || "net.minecraft.world.entity.monster.AbstractSkeleton".equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void saveNecromancers(AbstractGreenSmith smith, CompoundTag tag) {
        smith.necromancers.removeIf(id -> id == null);
        tag.putInt("GreenNecroCount", smith.necromancers.size());
        for (int i = 0; i < smith.necromancers.size(); i++) {
            tag.putUUID("GreenNecro" + i, smith.necromancers.get(i));
        }
    }

    public static void loadNecromancers(AbstractGreenSmith smith, CompoundTag tag) {
        smith.necromancers.clear();
        int count = tag.getInt("GreenNecroCount");
        for (int i = 0; i < count; i++) {
            if (tag.hasUUID("GreenNecro" + i)) {
                smith.necromancers.add(tag.getUUID("GreenNecro" + i));
            }
        }
    }

    public static void saveFocusFamiliars(AbstractGreenSmith smith, CompoundTag tag) {
        saveRoster(tag, "GreenSpookyFamiliar", smith.spookyFamiliars);
        saveRoster(tag, "GreenReaperFamiliar", smith.reaperFamiliars);
        saveRoster(tag, "GreenPhantomFamiliar", smith.phantomFamiliars);
        saveRoster(tag, "GreenRedstoneFamiliar", smith.redstoneFamiliars);
    }

    public static void loadFocusFamiliars(AbstractGreenSmith smith, CompoundTag tag) {
        loadRoster(tag, "GreenSpookyFamiliar", smith.spookyFamiliars);
        loadRoster(tag, "GreenReaperFamiliar", smith.reaperFamiliars);
        loadRoster(tag, "GreenPhantomFamiliar", smith.phantomFamiliars);
        loadRoster(tag, "GreenRedstoneFamiliar", smith.redstoneFamiliars);
    }

    public static void saveRoster(CompoundTag tag, String prefix, List<UUID> roster) {
        roster.removeIf(id -> id == null);
        tag.putInt(prefix + "Count", roster.size());
        for (int i = 0; i < roster.size(); i++) {
            tag.putUUID(prefix + i, roster.get(i));
        }
    }

    public static void loadRoster(CompoundTag tag, String prefix, List<UUID> roster) {
        roster.clear();
        int count = tag.getInt(prefix + "Count");
        for (int i = 0; i < count; i++) {
            if (tag.hasUUID(prefix + i)) {
                roster.add(tag.getUUID(prefix + i));
            }
        }
    }

}
