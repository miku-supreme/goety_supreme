package cn.blockforge.generated.goetysupreme.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SerponSiroccoEntity;

public class SerponSiroccoEntity extends Projectile {

    public static final int MAX_LIFE = 40;

    private static final EntityDataAccessor<Integer> DATA_LIFE =
            SynchedEntityData.defineId(SerponSiroccoEntity.class, EntityDataSerializers.INT);

    private int spellLevelDamage = 6;

    private float damageOverride = -1.0F;

    private double radiusBonus;

    private int lifeBonus;

    private double powerX;
    private double powerY;
    private double powerZ;

    private final Set<UUID> hurtIds = new HashSet<>();

    public SerponSiroccoEntity(EntityType<? extends SerponSiroccoEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public SerponSiroccoEntity(Level level, LivingEntity owner, int spellLevelDamage) {
        this(ModEntityTypes.SERPON_SIROCCO.get(), level);
        this.spellLevelDamage = spellLevelDamage;
        setOwner(owner);
        Vec3 look = owner.getLookAngle();
        this.powerX = look.x * 0.1D;
        this.powerY = look.y * 0.1D;
        this.powerZ = look.z * 0.1D;
    }

    public int getSpellLevelDamage() {
        return spellLevelDamage;
    }

    public void setDamageOverride(float damage) {
        this.damageOverride = damage;
    }

    public void setRadiusBonus(double radiusBonus) {
        this.radiusBonus = Math.max(0.0D, radiusBonus);
    }

    public void setLifeBonus(int lifeBonus) {
        this.lifeBonus = Math.max(0, lifeBonus);
    }

    public int getLife() {
        return entityData.get(DATA_LIFE);
    }

    private void setLife(int value) {
        entityData.set(DATA_LIFE, value);
    }

    @Override
    public void defineSynchedData() {
        entityData.define(DATA_LIFE, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            return;
        }
        Entity owner = getOwner();
        if (owner != null && owner.isRemoved()) {
            discard();
            return;
        }
        int life = getLife();
        if (life >= MAX_LIFE + lifeBonus) {
            discard();
            return;
        }
        setLife(life + 1);
        spawnParticles();
        Vec3 motion = getDeltaMovement().add(powerX, powerY, powerZ);
        setDeltaMovement(motion);
        setPos(getX() + motion.x, getY() + motion.y, getZ() + motion.z);
        damageOverlapping();
    }

    private void spawnParticles() {
        if (!(level() instanceof ServerLevel server)) {
            return;
        }
        ParticleOptions sirocco = AbstractJerotesServant.jerotesParticle(
                "serpon_sirocco", ParticleTypes.CAMPFIRE_COSY_SMOKE);
        for (int i = 0; i < 3; ++i) {
            for (int i2 = 0; i2 < 2; ++i2) {
                server.sendParticles(sirocco,
                        getRandomX(0.75D), getY(this.random.nextDouble() * 0.5D) + 0.5D, getRandomZ(0.75D),
                        1, 0.0D, 0.0D, 0.0D, 0.0D);
                server.sendParticles(sirocco,
                        getRandomX(0.25D), getY((2.0D * this.random.nextDouble() - 1.0D) * 0.5D),
                        getRandomZ(0.25D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            server.sendParticles(ParticleTypes.FLAME,
                    getRandomX(0.75D), getY(this.random.nextDouble() * 0.5D) + 0.5D, getRandomZ(0.75D),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
            server.sendParticles(ParticleTypes.FLAME,
                    getRandomX(0.25D), getY((2.0D * this.random.nextDouble() - 1.0D) * 0.5D),
                    getRandomZ(0.25D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private void damageOverlapping() {
        Entity owner = getOwner();
        if (!(owner instanceof LivingEntity caster)) {
            return;
        }
        for (LivingEntity target : level().getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(0.5D + radiusBonus))) {
            if (target == caster) {
                continue;
            }
            if (hurtIds.contains(target.getUUID())) {
                continue;
            }
            if (caster instanceof Mob mob && !mob.canAttack(target)) {
                continue;
            }
            if (caster.isAlliedTo(target)) {
                continue;
            }
            hurtIds.add(target.getUUID());
            boolean hit = target.hurt(JerotesMagicDamage.fireMagic(this, caster), rollDamage());
            if (!hit) {
                hit = target.hurt(attackSource(DamageTypes.EXPLOSION, caster), rollDamage());
            }
            if (!hit) {
                target.hurt(attackSource(DamageTypes.FLY_INTO_WALL, caster), rollDamage());
            }
            target.setDeltaMovement((target.getX() - getX()) / 3.0D,
                    (target.getY() - getY()) / 3.0D, (target.getZ() - getZ()) / 3.0D);
            if (!isSilent()) {
                level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK,
                        SoundSource.HOSTILE, 10.0F, 1.0F);
            }
        }
    }

    private DamageSource attackSource(net.minecraft.resources.ResourceKey<net.minecraft.world.damagesource.DamageType> key,
            Entity causing) {
        var registry = level().registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE);
        return new DamageSource(registry.getHolderOrThrow(key), this, causing);
    }

    private float rollDamage() {
        return this.damageOverride >= 0.0F ? this.damageOverride : rollDice(spellLevelDamage + 1, 6);
    }

    private float rollDice(int count, int sides) {
        if (count <= 0 || sides <= 0) {
            return 0.0F;
        }
        int sum = 0;
        for (int i = 0; i < count; ++i) {
            sum += random.nextInt(sides) + 1;
        }
        return sum;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Life", getLife());
        tag.putInt("SpellLevelDamage", spellLevelDamage);
        tag.putDouble("RadiusBonus", radiusBonus);
        tag.putInt("LifeBonus", lifeBonus);
        tag.putDouble("PowerX", powerX);
        tag.putDouble("PowerY", powerY);
        tag.putDouble("PowerZ", powerZ);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setLife(tag.getInt("Life"));
        this.spellLevelDamage = tag.getInt("SpellLevelDamage");
        this.radiusBonus = tag.getDouble("RadiusBonus");
        this.lifeBonus = tag.getInt("LifeBonus");
        this.powerX = tag.getDouble("PowerX");
        this.powerY = tag.getDouble("PowerY");
        this.powerZ = tag.getDouble("PowerZ");
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
