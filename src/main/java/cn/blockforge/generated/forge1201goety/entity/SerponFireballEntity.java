package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SerponFireballEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlameWizard;

public class SerponFireballEntity extends Projectile {

    public static final int MAX_LIFE = 90;

    private static final int FIRE_RADIUS = 2;

    private static final double ACCELERATION = 0.1D;

    private int life;
    private int spellLevel = 4;

    private float damageBonus;

    private double ax;
    private double ay;
    private double az;

    public SerponFireballEntity(EntityType<? extends SerponFireballEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public SerponFireballEntity(Level level, LivingEntity owner, int spellLevel) {
        this(ModEntityTypes.SERPON_FIREBALL.get(), level);
        this.spellLevel = spellLevel;
        setOwner(owner);
    }

    public void setDamageBonus(float damageBonus) {
        this.damageBonus = Math.max(0.0F, damageBonus);
    }

    public float getDamageBonus() {
        return damageBonus;
    }

    public int getSpellLevel() {
        return spellLevel;
    }

    public void shootTowards(Vec3 direction, float velocity) {
        Vec3 dir = direction.normalize();
        setDeltaMovement(dir.scale(velocity));
        this.ax = dir.x * ACCELERATION;
        this.ay = dir.y * ACCELERATION;
        this.az = dir.z * ACCELERATION;
    }

    @Override
    public void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            spawnParticles();
            return;
        }
        Entity owner = getOwner();
        if (owner != null && owner.isRemoved()) {
            discard();
            return;
        }
        if (++life >= MAX_LIFE) {
            explode();
            discard();
            return;
        }
        Vec3 motion = getDeltaMovement().add(ax, ay, az);
        Vec3 next = position().add(motion);
        BlockHitResult blockHit = level().clip(new ClipContext(
                position(), next, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (blockHit.getType() != HitResult.Type.MISS) {
            onHitBlock(blockHit);
            return;
        }
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(level(), this, position(), next,
                getBoundingBox().expandTowards(motion).inflate(1.0D), this::canHitEntity);
        if (entityHit != null) {
            onHitEntity(entityHit);
            return;
        }
        setDeltaMovement(motion);
        setPos(next);
    }

    private void spawnParticles() {
        for (int i = 0; i < 3; ++i) {
            level().addParticle(ParticleTypes.FLAME,
                    getRandomX(0.3D), getRandomY(), getRandomZ(0.3D), 0.0D, 0.0D, 0.0D);
        }
        level().addParticle(ParticleTypes.LAVA,
                getRandomX(0.2D), getRandomY(), getRandomZ(0.2D), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public boolean canHitEntity(Entity entity) {
        Entity owner = getOwner();
        if (entity == owner) {
            return false;
        }
        if (owner instanceof LivingEntity living && living.isAlliedTo(entity)) {
            return false;
        }
        if (owner instanceof Mob mob && entity instanceof LivingEntity target && !mob.canAttack(target)) {
            return false;
        }
        return entity.isAlive() && entity.isPickable();
    }

    @Override
    public void onHitEntity(EntityHitResult hit) {
        Entity entity = hit.getEntity();
        Entity owner = getOwner();
        if (entity instanceof LivingEntity target && owner instanceof LivingEntity caster) {
            DamageSource source = JerotesMagicDamage.fireMagic(this, caster);
            if (!target.hurt(source, fireDamage())) {
                target.hurt(damageSources().explosion(this, caster), fireDamage());
            } else {
                target.setSecondsOnFire(fireSeconds());
            }
        }
        explode();
        discard();
    }

    @Override
    public void onHitBlock(BlockHitResult hit) {
        BlockPos center = hit.getBlockPos();
        boolean fireTick = level().getGameRules().getBoolean(GameRules.RULE_DOFIRETICK);
        for (int dx = -FIRE_RADIUS; dx <= FIRE_RADIUS; ++dx) {
            for (int dy = -FIRE_RADIUS; dy <= FIRE_RADIUS; ++dy) {
                for (int dz = -FIRE_RADIUS; dz <= FIRE_RADIUS; ++dz) {
                    if (dx * dx + dy * dy + dz * dz > FIRE_RADIUS * FIRE_RADIUS) {
                        continue;
                    }
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (fireTick && BaseFireBlock.canBePlacedAt(level(), pos, Direction.UP)) {
                        level().setBlockAndUpdate(pos, BaseFireBlock.getState(level(), pos));
                    }
                    igniteBlock(pos);
                }
            }
        }
        discard();
    }

    private void igniteBlock(BlockPos pos) {
        if (!level().getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            return;
        }
        BlockState state = level().getBlockState(pos);
        if (state.hasProperty(CampfireBlock.LIT) && !state.getValue(CampfireBlock.LIT)) {
            level().setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, Boolean.TRUE));
            return;
        }
        if (state.hasProperty(AbstractCandleBlock.LIT) && !state.getValue(AbstractCandleBlock.LIT)) {
            level().setBlockAndUpdate(pos, state.setValue(AbstractCandleBlock.LIT, Boolean.TRUE));
        }
    }

    private float fireDamage() {
        return rollDice(5 + (spellLevel - 3), 8) + spellLevel + damageBonus;
    }

    private int fireSeconds() {
        return 6 + (spellLevel - 1) * 3;
    }

    private float explodePower() {
        return 0.5F + spellLevel * 0.25F;
    }

    private void explode() {
        if (level() instanceof ServerLevel server) {
            server.explode(this, getX(), getY(), getZ(), explodePower(), Level.ExplosionInteraction.NONE);
        }
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
        tag.putInt("Life", life);
        tag.putInt("SpellLevel", spellLevel);
        tag.putDouble("AccelX", ax);
        tag.putDouble("AccelY", ay);
        tag.putDouble("AccelZ", az);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.life = tag.getInt("Life");
        this.spellLevel = tag.getInt("SpellLevel");
        this.ax = tag.getDouble("AccelX");
        this.ay = tag.getDouble("AccelY");
        this.az = tag.getDouble("AccelZ");
    }
}
