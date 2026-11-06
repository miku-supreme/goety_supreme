package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.WitherSkeletonServant;
import com.Polarice3.Goety.common.entities.util.FirePillar;
import com.Polarice3.Goety.common.entities.util.ModFallingBlock;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SupremeMobsConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.AbstractWitherWarlordRenderer;
import cn.blockforge.generated.goetysupreme.renderer.ClientWarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.entity.EnhancedWitherSkull;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.item.HellfireSaberItem;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.ProjectileIntercept;
import cn.blockforge.generated.goetysupreme.util.RagingStarDamageGuard;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;
import cn.blockforge.generated.goetysupreme.util.SupremeBossHurt;
import cn.blockforge.generated.goetysupreme.util.SupremeBossMusic;
import cn.blockforge.generated.goetysupreme.api.UndeadCapeBearer;
import cn.blockforge.generated.goetysupreme.entity.WarlordHellfireSlash;
import cn.blockforge.generated.goetysupreme.renderer.WarlordSaberTrail;
import cn.blockforge.generated.goetysupreme.util.WarlordShieldBreak;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.entity.WitherSkeletonHorseServant;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordModel;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordServant;
import cn.blockforge.generated.goetysupreme.mixin.GoetyWarlordHellfirePillarMixin;

final class WarlordEliteSummon {

    private WarlordEliteSummon() {
    }

    static void tickElite(AbstractWitherWarlord warlord) {
        if (warlord.skillTick == 20) {
            warlord.summonMinions();
        }
        if (warlord.skillTick >= 30) {
            warlord.endSkill(60);
        }
    }

    static void summonMinions(AbstractWitherWarlord warlord) {
        warlord.playSound(SoundEvents.WITHER_SPAWN, 2.0F, 0.8F);
        if (!(warlord.level() instanceof ServerLevel server)) {
            return;
        }
        warlord.purgeRiderlessEliteHorses(server);
        int skeletons = warlord.countOwnedElite(server, WitherSkeletonServant.class);
        int horses = warlord.countOwnedElite(server, WitherSkeletonHorseServant.class);
        if (skeletons + horses > AbstractWitherWarlord.ELITE_TOTAL_REPLENISH_THRESHOLD) {
            return;
        }
        int room = Math.min(4, AbstractWitherWarlord.MAX_ELITE_SKELETONS - skeletons);
        if (room <= 0) {
            return;
        }
        ServerParticleUtil.gatheringParticles(ParticleTypes.SOUL, warlord, server);
        ServerParticleUtil.addParticlesAroundSelf(server, ParticleTypes.SOUL_FIRE_FLAME, warlord);
        ServerParticleUtil.circularParticles(server, ParticleTypes.SOUL_FIRE_FLAME,
                warlord.getX(), warlord.getY() + 0.2D, warlord.getZ(), 4.0F);
        server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                warlord.getX(), warlord.getY() + 0.5D, warlord.getZ(), 40, 1.5D, 0.5D, 1.5D, 0.06D);
        server.sendParticles(ParticleTypes.LARGE_SMOKE,
                warlord.getX(), warlord.getY() + 0.4D, warlord.getZ(), 24, 1.7D, 0.4D, 1.7D, 0.02D);
        int meleeIndex = 0;
        for (int i = 0; i < room; i++) {
            boolean archer = i < 2;
            double side = (i % 2 == 0) ? 1.0D : -1.0D;
            double angle = (warlord.getYRot() + side * (archer ? 60.0D : 110.0D)) * Mth.DEG_TO_RAD;
            double radius = archer ? 2.5D : 4.5D;
            double x = warlord.getX() - Mth.sin((float) angle) * radius;
            double z = warlord.getZ() + Mth.cos((float) angle) * radius;
            WitherSkeletonHorseServant horse =
                    ModEntityTypes.WITHER_SKELETON_HORSE_SERVANT.get().create(server);
            if (horse == null) {
                continue;
            }
            horse.moveTo(x, warlord.getY(), z, warlord.getYRot(), 0.0F);
            horse.setTrueOwner(warlord);
            horse.setPersistenceRequired();
            horse.finalizeSpawn(server, server.getCurrentDifficultyAt(horse.blockPosition()),
                    MobSpawnType.TRIGGERED, null, null);
            horse.setNetheriteArmor(true);
            server.addFreshEntity(horse);

            WitherSkeletonServant skeleton =
                    new WitherSkeletonServant(ModEntityType.WITHER_SKELETON_SERVANT.get(), server);
            skeleton.moveTo(x, warlord.getY(), z, warlord.getYRot(), 0.0F);
            skeleton.setTrueOwner(warlord);
            skeleton.setPersistenceRequired();
            skeleton.finalizeSpawn(server, server.getCurrentDifficultyAt(skeleton.blockPosition()),
                    MobSpawnType.TRIGGERED, null, null);
            ItemStack mainHand;
            if (archer) {
                mainHand = new ItemStack(Items.BOW);
            } else {
                boolean spearSlot = meleeIndex++ % 2 == 0;
                mainHand = spearSlot
                        ? new ItemStack(GeneratedMod.NETHERITE_SPEAR.get())
                        : new ItemStack(Items.NETHERITE_SWORD);
            }
            ItemStack helmet = new ItemStack(Items.NETHERITE_HELMET);
            ItemStack chest = new ItemStack(Items.NETHERITE_CHESTPLATE);
            ItemStack legs = new ItemStack(Items.NETHERITE_LEGGINGS);
            ItemStack feet = new ItemStack(Items.NETHERITE_BOOTS);
            if (warlord.isEnhanced()) {
                enchantEliteGear(warlord, helmet);
                enchantEliteGear(warlord, chest);
                enchantEliteGear(warlord, legs);
                enchantEliteGear(warlord, feet);
                enchantEliteGear(warlord, mainHand);
            }
            skeleton.setItemSlot(EquipmentSlot.HEAD, helmet);
            skeleton.setItemSlot(EquipmentSlot.CHEST, chest);
            skeleton.setItemSlot(EquipmentSlot.LEGS, legs);
            skeleton.setItemSlot(EquipmentSlot.FEET, feet);
            skeleton.setItemSlot(EquipmentSlot.MAINHAND, mainHand);
            for (EquipmentSlot slot : new EquipmentSlot[] {EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                    EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND}) {
                skeleton.setDropChance(slot, 0.0F);
            }
            LivingEntity target = warlord.getTarget();
            if (target != null) {
                skeleton.setTarget(target);
            }
            skeleton.startRiding(horse, true);
            server.addFreshEntity(skeleton);
            warlord.minions.add(skeleton.getUUID());
            ServerParticleUtil.summonUndeadParticles(server, horse);
            ServerParticleUtil.summonUndeadParticles(server, skeleton);
            server.sendParticles(ParticleTypes.SOUL, x, warlord.getY() + 0.8D, z,
                    24, 0.5D, 0.8D, 0.5D, 0.03D);
            server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, warlord.getY() + 0.5D, z,
                    16, 0.5D, 0.6D, 0.5D, 0.04D);
        }
        MobEffect shielding = AbstractWitherWarlord.goetyEffect("shielding");
        if (shielding != null) {
            warlord.addEffect(new MobEffectInstance(shielding, 20 * 60, 1, false, false));
        }
    }

    static void performBetrayalSkills(AbstractWitherWarlord warlord) {
        if (warlord.level().isClientSide()) {
            return;
        }
        warlord.setBladeState(2);
        warlord.bladeStateTicks = 0;
        warlord.summonMinions();
        warlord.beginSkill(AbstractWitherWarlord.SKILL_CHARGE);
    }

    static void enchantEliteGear(AbstractWitherWarlord warlord, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }
        EnchantmentHelper.enchantItem(warlord.getRandom(), stack, 5 + warlord.getRandom().nextInt(11), false);
    }

    static <T extends Summoned> int countOwnedElite(AbstractWitherWarlord warlord, ServerLevel server, Class<T> type) {
        int count = 0;
        for (T servant : server.getEntitiesOfClass(type,
                warlord.getBoundingBox().inflate(AbstractWitherWarlord.ELITE_COUNT_RADIUS))) {
            if (servant.getTrueOwner() == warlord) {
                count++;
            }
        }
        return count;
    }

    static void purgeRiderlessEliteHorses(AbstractWitherWarlord warlord, ServerLevel server) {
        for (WitherSkeletonHorseServant horse : server.getEntitiesOfClass(WitherSkeletonHorseServant.class,
                warlord.getBoundingBox().inflate(AbstractWitherWarlord.ELITE_COUNT_RADIUS))) {
            if (horse.getTrueOwner() != warlord) {
                continue;
            }
            if (horse.getFirstPassenger() instanceof WitherSkeletonServant) {
                continue; // 还驮着一只精兵，留着。
            }
            horse.discard();
        }
    }
}
