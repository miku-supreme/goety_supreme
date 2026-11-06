package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverBreathServantEntity;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.entity.CorrosiveSnowCloudServantEntity;
import cn.blockforge.generated.goetysupreme.compat.JerotesCoreBridge;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class CorrosiverBreathServantEntity extends ThrowableItemProjectile {

    private static final int MAX_LIFE = 30;

    private int spellLevel = 5;

    private final Set<UUID> piercedTargets = new HashSet<>();

    public CorrosiverBreathServantEntity(EntityType<? extends CorrosiverBreathServantEntity> type, Level level) {
        super(type, level);
    }

    public CorrosiverBreathServantEntity(Level level, LivingEntity shooter) {
        super(ModEntityTypes.CORROSIVER_BREATH_SERVANT.get(), shooter, level);
    }

    public void setSpellLevel(int level) {
        this.spellLevel = level;
    }

    @Override
    public Item getDefaultItem() {
        ItemStack stack = AbstractJerotesServant.jerotesItem("corrosiver_breath");
        return stack.isEmpty() ? Items.SNOWBALL : stack.getItem();
    }

    @Override
    public float getGravity() {
        return 0.0F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            ParticleOptions trailParticle = breathParticle();
            if (trailParticle != null) {
                double spread = (double) this.getBbWidth() * ((double) this.tickCount * 0.1D);
                for (int i = 0; i < 5 + this.tickCount; ++i) {
                    this.level().addParticle(trailParticle,
                            this.getX() + (this.random.nextDouble() - 0.5D) * spread,
                            this.getY() + (this.random.nextDouble() - 0.5D) * spread,
                            this.getZ() + (this.random.nextDouble() - 0.5D) * spread,
                            0.0D, 0.0D, 0.0D);
                }
            }
            return;
        }
        if (this.tickCount > MAX_LIFE) {
            this.discard();
        }
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level().isClientSide) {
            return;
        }
        Entity owner = this.getOwner();
        if (result.getEntity() instanceof LivingEntity target) {
            if (target == owner || (owner != null && owner.isAlliedTo(target))) {
                return;
            }
            if (!piercedTargets.add(target.getUUID())) {
                return;
            }
            target.invulnerableTime = 0;
            boolean saved = JerotesCoreBridge.savingSpellThrow(target);
            boolean hit;
            if (saved) {
                hit = target.hurt(JerotesMagicDamage.corrosion(this, owner),
                        rollDice(this.spellLevel * 2, 6) / 2.0F * CorrosiverServant.ownerSkillScale(owner));
            } else {
                hit = target.hurt(JerotesMagicDamage.corrosion(this, owner),
                        rollDice(this.spellLevel * 2, 6) * CorrosiverServant.ownerSkillScale(owner));
                if (hit) {
                    int duration = 20 * this.spellLevel;
                    int amplifier = Math.max(0, this.spellLevel - 1);
                    applyEffect(target, "corrosive", MobEffects.POISON, duration, amplifier);
                    applyEffect(target, "weakness", MobEffects.WEAKNESS, duration, amplifier);
                    applyEffect(target, "slowness", MobEffects.MOVEMENT_SLOWDOWN, duration, amplifier);
                }
            }
            if (hit && !this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        AbstractJerotesServant.jerotesCoreSound("spell", SoundEvents.WITCH_THROW),
                        SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (this.level().isClientSide) {
            return;
        }
        BlockState state = this.level().getBlockState(result.getBlockPos());
        if (state.is(Blocks.WATER)) {
            return;
        }
        if (this.level() instanceof ServerLevel server) {
            AreaEffectCloud cloud = new AreaEffectCloud(server, this.getX(), this.getY(), this.getZ());
            Entity owner = this.getOwner();
            if (owner instanceof LivingEntity living) {
                cloud.setOwner(living);
            }
            cloud.setRadius(1.0F);
            cloud.setDuration(20 * this.spellLevel);
            cloud.setParticle(cloudParticle());
            cloud.addEffect(new MobEffectInstance(corrosiveEffect(),
                    20 * this.spellLevel, Math.max(0, this.spellLevel - 1)));
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        AbstractJerotesServant.jerotesCoreSound("breath", SoundEvents.RAVAGER_ROAR),
                        SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
            server.addFreshEntity(cloud);
        }
        this.discard();
    }

    public static float rollDice(int count, int sides) {
        float total = 0.0F;
        for (int i = 0; i < count; ++i) {
            total += 1 + (float) Math.floor(Math.random() * sides);
        }
        return total;
    }

    private static void applyEffect(LivingEntity target, String path, MobEffect fallback,
            int duration, int amplifier) {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotes", path));
        target.addEffect(new MobEffectInstance(effect == null ? fallback : effect,
                duration, amplifier, false, effect != null));
    }

    private static ParticleOptions breathParticle() {
        Object type = ForgeRegistries.PARTICLE_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", "corrosiver_breath"));
        return type instanceof net.minecraft.core.particles.SimpleParticleType simple
                ? simple : ParticleTypes.SNOWFLAKE;
    }

    private static ParticleOptions cloudParticle() {
        Object type = ForgeRegistries.PARTICLE_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", "corrosiver_breath_fog"));
        return type instanceof net.minecraft.core.particles.SimpleParticleType simple
                ? simple : ParticleTypes.SNOWFLAKE;
    }

    private static MobEffect corrosiveEffect() {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotes", "corrosive"));
        return effect == null ? MobEffects.POISON : effect;
    }
}
