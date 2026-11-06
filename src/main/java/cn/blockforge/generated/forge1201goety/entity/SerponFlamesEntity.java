package cn.blockforge.generated.goetysupreme.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.renderer.JerotesParticleFx;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SerponFlamesEntity;
import cn.blockforge.generated.goetysupreme.entity.SerponFlameWizard;

public class SerponFlamesEntity extends Projectile {

    public static final int MAX_LIFE = 20;

    public static final int USE_LIFE = 10;

    public static final double REACH = 9.0D;

    public static final float SPELL_ANGLE = 22.5F;

    public static final int STRIPE_START = 3;

    public static final int PARTICLE_COUNT = 4;

    private static final int BOLT_COLOR_I = 0xFF8114;
    private static final int BOLT_COLOR_II = 0xFFE2CB;
    private static final int BOLT_COLOR_III = 0xFF1C14;

    private static final EntityDataAccessor<Float> DATA_SPELL_ANGLE =
            SynchedEntityData.defineId(SerponFlamesEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_REACH =
            SynchedEntityData.defineId(SerponFlamesEntity.class, EntityDataSerializers.FLOAT);

    private int life;
    private int spellLevel = 4;

    private float damageBonus;

    public SerponFlamesEntity(EntityType<? extends SerponFlamesEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public SerponFlamesEntity(Level level, LivingEntity owner, int spellLevel) {
        this(ModEntityTypes.SERPON_FLAMES.get(), level);
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

    public void setSpellAngle(float angle) {
        entityData.set(DATA_SPELL_ANGLE, Math.max(0.0F, angle));
    }

    public float getSpellAngle() {
        return entityData.get(DATA_SPELL_ANGLE);
    }

    public void setReach(double reach) {
        entityData.set(DATA_REACH, (float) Math.max(0.5D, reach));
    }

    public double getReach() {
        return entityData.get(DATA_REACH);
    }

    private double angleScale() {
        return getSpellAngle() / (double) SPELL_ANGLE;
    }

    @Override
    public void defineSynchedData() {
        entityData.define(DATA_SPELL_ANGLE, SPELL_ANGLE);
        entityData.define(DATA_REACH, (float) REACH);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            spawnConeParticles();
            return;
        }
        Entity owner = getOwner();
        if (owner == null || owner.isRemoved()) {
            discard();
            return;
        }
        if (++life > MAX_LIFE) {
            discard();
            return;
        }
        setDeltaMovement(Vec3.ZERO);
        setPos(owner.getX(), owner.getY(0.5D), owner.getZ());
        if (life == USE_LIFE) {
            coneAttack(owner);
        }
        if (life == STRIPE_START && level() instanceof ServerLevel server
                && owner instanceof LivingEntity caster) {
            JerotesParticleFx.sprayBoltFan(server, caster, (float) getReach(), getSpellAngle(),
                    BOLT_COLOR_I, BOLT_COLOR_II, BOLT_COLOR_III);
        }
    }

    private void spawnConeParticles() {
        Entity owner = getOwner();
        if (owner == null) {
            return;
        }
        Vec3 casterPos = owner.position();
        Vec3 forward = owner.getViewVector(1.0F);
        if (forward.lengthSqr() < 1.0E-6D) {
            forward = new Vec3(0.0D, 0.0D, 1.0D);
        }
        forward = forward.normalize();
        Vec3 worldUp = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 right = forward.cross(worldUp);
        if (right.lengthSqr() < 1.0E-6D) {
            right = new Vec3(1.0D, 0.0D, 0.0D);
        }
        right = right.normalize();
        Vec3 up = right.cross(forward).normalize();
        Vec3 origin = casterPos.add(0.0D, owner.getBbHeight() * 0.7D, 0.0D)
                .add(forward.scale(0.4D));
        float range = (float) getReach();
        float halfAngleRad = (float) Math.toRadians(SPELL_ANGLE * 0.5D * angleScale());
        float baseRadius = range * (float) Math.tan(halfAngleRad);
        float progress = Mth.clamp((float) this.tickCount / (float) USE_LIFE, 0.0F, 1.0F);
        float speedScale = 0.02F + this.spellLevel * 0.005F;
        for (int i = 0; i < PARTICLE_COUNT; ++i) {
            float depth = Math.min(1.0F, Math.max(0.0F, progress + (random.nextFloat() - 0.5F) * 0.3F));
            float actualDist = depth * range;
            float radiusAtDepth = baseRadius * depth;
            float angleRad = random.nextFloat() * 2.0F * (float) Math.PI;
            float distFromCenter = radiusAtDepth * (float) Math.sqrt(random.nextFloat());
            Vec3 radialDir = right.scale(Math.cos(angleRad)).add(up.scale(Math.sin(angleRad)));
            Vec3 point = origin.add(forward.scale(actualDist)).add(radialDir.scale(distFromCenter));
            level().addParticle(ParticleTypes.FLAME, point.x, point.y, point.z,
                    (random.nextFloat() - 0.5F) * speedScale,
                    (random.nextFloat() - 0.5F) * speedScale * 0.5F,
                    (random.nextFloat() - 0.5F) * speedScale);
        }
        level().addParticle(ParticleTypes.SMOKE, origin.x, origin.y, origin.z, 0.0D, 0.02D, 0.0D);
    }

    private void coneAttack(Entity owner) {
        if (!(owner instanceof LivingEntity caster)) {
            return;
        }
        handleBlockInteraction(caster);
        Vec3 eye = caster.getEyePosition();
        Vec3 look = caster.getViewVector(1.0F);
        if (look.lengthSqr() < 1.0E-6D) {
            return;
        }
        look = look.normalize();
        double cosLimit = Math.cos(Math.toRadians(getSpellAngle()));
        List<Entity> candidates = level().getEntitiesOfClass(Entity.class,
                getBoundingBox().inflate(getReach()));
        Set<Entity> alreadyHit = new HashSet<>();
        for (Entity entity : candidates) {
            LivingEntity target;
            if (entity instanceof LivingEntity living) {
                target = living;
            } else if (entity instanceof PartEntity<?> part && part.getParent() instanceof LivingEntity parent) {
                target = parent;
            } else {
                continue;
            }
            if (!alreadyHit.add(target)) {
                continue;
            }
            if (target == caster || caster.isAlliedTo(target)) {
                continue;
            }
            if (caster instanceof Mob mob && !mob.canAttack(target)) {
                continue;
            }
            if (caster.distanceTo(target) > getReach()) {
                continue;
            }
            Vec3 to = target.position().subtract(eye);
            double distance = to.length();
            if (distance <= 1.0E-6D) {
                applyDamage(target, caster);
                continue;
            }
            if (look.dot(to.scale(1.0D / distance)) < cosLimit) {
                continue;
            }
            if (!caster.hasLineOfSight(target)) {
                continue;
            }
            applyDamage(target, caster);
        }
    }

    private void applyDamage(LivingEntity target, LivingEntity caster) {
        DamageSource source = JerotesMagicDamage.bypassesCooldownFire(this, caster);
        target.invulnerableTime = 0;
        float damage = rollDice(6 + (spellLevel - 3), 6) / 30.0F + damageBonus;
        if (!target.hurt(source, damage)) {
            target.hurt(damageSources().indirectMagic(this, caster),
                    rollDice(6 + (spellLevel - 3), 6) / 30.0F + damageBonus);
        } else {
            target.setSecondsOnFire(6 + (spellLevel - 1) * 3);
        }
    }

    private void handleBlockInteraction(LivingEntity caster) {
        if (level().isClientSide) {
            return;
        }
        if (caster instanceof Mob && !level().getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            return;
        }
        Vec3 origin = caster.getEyePosition();
        Vec3 forward = caster.getViewVector(1.0F);
        if (forward.lengthSqr() < 1.0E-6D) {
            return;
        }
        forward = forward.normalize();
        Vec3 worldUp = new Vec3(0.0D, 1.0D, 0.0D);
        Vec3 right = forward.cross(worldUp);
        if (right.lengthSqr() < 1.0E-6D) {
            right = new Vec3(1.0D, 0.0D, 0.0D);
        }
        right = right.normalize();
        Vec3 up = right.cross(forward).normalize();
        double range = getReach();
        double halfAngleRad = Math.toRadians(SPELL_ANGLE * 0.5D * angleScale());
        double tanHalfAngle = Math.tan(halfAngleRad);
        int steps = Mth.floor(range * 2.0D);
        Set<BlockPos> checked = new HashSet<>();
        for (int i = 1; i <= steps; ++i) {
            double distance = range * (double) i / (double) steps;
            double radius = distance * tanHalfAngle;
            int radialSteps = Math.max(1, Mth.floor(radius * 2.0D));
            for (int x = -radialSteps; x <= radialSteps; ++x) {
                for (int y = -radialSteps; y <= radialSteps; ++y) {
                    double offsetX = (double) x * 0.5D;
                    double offsetY = (double) y * 0.5D;
                    if (offsetX * offsetX + offsetY * offsetY > radius * radius) {
                        continue;
                    }
                    Vec3 point = origin.add(forward.scale(distance))
                            .add(right.scale(offsetX))
                            .add(up.scale(offsetY));
                    BlockHitResult hit = level().clip(new ClipContext(origin, point,
                            ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, caster));
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        BlockPos firePos = hit.getBlockPos().relative(hit.getDirection());
                        if (checked.add(firePos)) {
                            extinguishFire(firePos);
                            freezeBlock(firePos);
                        }
                    } else {
                        BlockPos targetPos = BlockPos.containing(point);
                        if (checked.add(targetPos)) {
                            extinguishFire(targetPos);
                            freezeBlock(targetPos);
                        }
                    }
                }
            }
        }
    }

    private void extinguishFire(BlockPos pos) {
        BlockState state = level().getBlockState(pos);
        if (state.is(Blocks.FIRE) || state.is(Blocks.SOUL_FIRE)) {
            level().removeBlock(pos, false);
            return;
        }
        if (state.hasProperty(CampfireBlock.LIT) && state.getValue(CampfireBlock.LIT)) {
            level().setBlock(pos, state.setValue(CampfireBlock.LIT, Boolean.FALSE), 3);
            return;
        }
        if (state.hasProperty(AbstractCandleBlock.LIT) && state.getValue(AbstractCandleBlock.LIT)) {
            level().setBlock(pos, state.setValue(AbstractCandleBlock.LIT, Boolean.FALSE), 3);
        }
    }

    private void freezeBlock(BlockPos pos) {
        BlockState state = level().getBlockState(pos);
        if (state.getFluidState().getType() == Fluids.WATER && state.getFluidState().isSource()) {
            level().setBlock(pos, Blocks.FROSTED_ICE.defaultBlockState(), 3);
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
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.life = tag.getInt("Life");
        this.spellLevel = tag.getInt("SpellLevel");
    }
}
