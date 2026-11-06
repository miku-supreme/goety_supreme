package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.entity.Scorchback;
import cn.blockforge.generated.goetysupreme.util.ScorchbackCombatMath;
import cn.blockforge.generated.goetysupreme.entity.ScorchbackQuill;

public class ScorchbackQuill extends Entity implements net.minecraftforge.entity.IEntityAdditionalSpawnData {
    public static final int FUSE_TICKS = 20;
    private Vec3 aim = Vec3.ZERO;
    private long turnAt;
    private boolean diving;
    private static final float HIT_DAMAGE = 10.0F;
    private static final float BLAST_DAMAGE = 5.0F;
    private static final double BLAST_RADIUS = 1.0D;
    private static final int DEFAULT_LIFE = 200;
    private static final EntityDataAccessor<Integer> DATA_FUSE =
            SynchedEntityData.defineId(ScorchbackQuill.class, EntityDataSerializers.INT);
    private static final DustParticleOptions EMBER =
            new DustParticleOptions(new Vector3f(1.0F, 0.19F, 0.035F), 0.7F);

    private int life = DEFAULT_LIFE;
    private final List<Vec3> trailPositions = new ArrayList<>();

    public List<Vec3> trailPositions() {
        return this.trailPositions;
    }

    @Nullable
    private UUID ownerId;

    public ScorchbackQuill(EntityType<?> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public void launch(Scorchback owner, Vec3 origin, Vec3 target, int volley, int shot) {
        this.ownerId = owner.getUUID();
        this.aim = target;
        this.turnAt = this.level().getGameTime() + ScorchbackCombatMath.ASCENT_TICKS;
        this.diving = false;
        this.setPos(origin);
        this.setDeltaMovement(ScorchbackCombatMath.ascentVelocity(owner.getActionYaw(), volley, shot));
        this.faceVelocity(this.getDeltaMovement());
        this.setOldPosAndRot();
    }

    public void plant(Scorchback owner, Vec3 ground, float yaw, int lane) {
        this.ownerId = owner.getUUID();
        this.setPos(ground.add(0.0D, -0.04D, 0.0D));
        this.setYRot(yaw + (lane - 2) * 14.0F);
        this.setXRot(68.0F + (lane & 1) * 9.0F);
        this.setDeltaMovement(Vec3.ZERO);
        this.entityData.set(DATA_FUSE, FUSE_TICKS);
        this.setOldPosAndRot();
    }

    @Override
    public void writeSpawnData(net.minecraft.network.FriendlyByteBuf buffer) {
        buffer.writeDouble(this.aim.x);
        buffer.writeDouble(this.aim.y);
        buffer.writeDouble(this.aim.z);
        buffer.writeLong(this.turnAt);
        buffer.writeBoolean(this.diving);
        buffer.writeInt(this.getFuse());
    }

    @Override
    public void readSpawnData(net.minecraft.network.FriendlyByteBuf buffer) {
        this.aim = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        this.turnAt = buffer.readLong();
        this.diving = buffer.readBoolean();
        this.entityData.set(DATA_FUSE, buffer.readInt());
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(DATA_FUSE, -1);
    }

    public int getFuse() {
        return this.entityData.get(DATA_FUSE);
    }

    public boolean isEmbedded() {
        return this.getFuse() >= 0;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return net.minecraftforge.network.NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        this.entityData.set(DATA_FUSE, tag.contains("ScorchbackQuillFuse")
                ? Mth.clamp(tag.getInt("ScorchbackQuillFuse"), -1, FUSE_TICKS) : -1);
        this.life = tag.contains("ScorchbackQuillLife") ? tag.getInt("ScorchbackQuillLife") : DEFAULT_LIFE;
        this.ownerId = tag.hasUUID("ScorchbackQuillOwner") ? tag.getUUID("ScorchbackQuillOwner") : null;
        this.aim = new Vec3(tag.getDouble("AimX"), tag.getDouble("AimY"), tag.getDouble("AimZ"));
        this.turnAt = tag.getLong("TurnAt");
        this.diving = !tag.contains("TurnAt") || tag.getBoolean("Diving");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("ScorchbackQuillFuse", this.getFuse());
        tag.putInt("ScorchbackQuillLife", this.life);
        tag.putDouble("AimX", this.aim.x);
        tag.putDouble("AimY", this.aim.y);
        tag.putDouble("AimZ", this.aim.z);
        tag.putLong("TurnAt", this.turnAt);
        tag.putBoolean("Diving", this.diving);
        if (this.ownerId != null) {
            tag.putUUID("ScorchbackQuillOwner", this.ownerId);
        }
    }

    @Override
    public void tick() {
        super.tick();
        boolean client = this.level().isClientSide();
        if (!client && --this.life <= 0) {
            this.discard();
            return;
        }
        if (this.isEmbedded()) {
            this.trailPositions.clear();
            this.setDeltaMovement(Vec3.ZERO);
            if (!client) {
                int fuse = this.getFuse() - 1;
                this.entityData.set(DATA_FUSE, fuse);
                if (fuse <= 0) {
                    this.blast();
                }
            } else {
                this.embeddedParticles();
            }
            return;
        }
        Scorchback owner = client ? null : this.resolveOwner();
        if (!client && owner == null) {
            this.discard();
            return;
        }
        Vec3 from = this.position();
        if (client) {
            if (this.trailPositions.size() >= 8) {
                this.trailPositions.remove(0);
            }
            this.trailPositions.add(from);
        }
        if (!this.diving && this.level().getGameTime() > this.turnAt) {
            this.diving = true;
            this.setDeltaMovement(ScorchbackCombatMath.diveVelocity(from, this.aim));
            this.hasImpulse = true;
        }
        Vec3 velocity = this.getDeltaMovement();
        Vec3 to = from.add(velocity);
        this.setDeltaMovement(velocity);
        this.faceVelocity(velocity);
        BlockHitResult block = this.level().clip(new ClipContext(from, to,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        Vec3 end = block.getType() == HitResult.Type.MISS ? to : block.getLocation();
        if (!client) {
            EntityHitResult hit = ProjectileUtil.getEntityHitResult(this.level(), this, from, end,
                    this.getBoundingBox().expandTowards(velocity).inflate(0.25D),
                    entity -> entity instanceof LivingEntity living && owner.canAttackTarget(living), 0.15F);
            if (hit != null) {
                this.setPos(hit.getLocation());
                this.hitLiving(owner, (LivingEntity) hit.getEntity());
                return;
            }
        }
        if (block.getType() != HitResult.Type.MISS) {
            this.setPos(end.add(velocity.normalize().scale(client ? -0.002D : 0.06D)));
            if (!client) {
                this.setDeltaMovement(Vec3.ZERO);
                this.entityData.set(DATA_FUSE, FUSE_TICKS);
                this.hasImpulse = true;
                this.impactFx(block);
            }
            return;
        }
        this.setPos(to);
        if (client) {
            for (int i = 0; i < 3; i++) {
                Vec3 p = from.lerp(to, i / 3.0D);
                this.level().addParticle(EMBER, p.x, p.y, p.z, 0.0D, 0.0D, 0.0D);
            }
            if ((this.tickCount & 1) == 0) {
                this.level().addParticle(ParticleTypes.SMALL_FLAME, from.x, from.y, from.z,
                        0.0D, 0.005D, 0.0D);
            }
        }
    }

    private void faceVelocity(Vec3 velocity) {
        if (velocity.lengthSqr() < 1.0E-8D) {
            return;
        }
        this.setYRot((float) (-Mth.atan2(velocity.x, velocity.z) * Mth.RAD_TO_DEG));
        this.setXRot((float) (-Mth.atan2(velocity.y, velocity.horizontalDistance()) * Mth.RAD_TO_DEG));
    }

    private void hitLiving(Scorchback owner, LivingEntity victim) {
        victim.hurt(this.damageSources().mobAttack(owner), HIT_DAMAGE);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(),
                    9, 0.16D, 0.16D, 0.16D, 0.12D);
            server.sendParticles(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(),
                    5, 0.12D, 0.12D, 0.12D, 0.015D);
        }
        this.playSound(SoundEvents.ARROW_HIT, 0.65F, 1.25F);
        if (!this.level().isClientSide()) {
            victim.invulnerableTime = 0;
            this.blast();
            return;
        }
        this.discard();
    }

    private void impactFx(BlockHitResult hit) {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        server.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK,
                        this.level().getBlockState(hit.getBlockPos())),
                hit.getLocation().x, hit.getLocation().y + 0.04D, hit.getLocation().z,
                8, 0.13D, 0.08D, 0.13D, 0.06D);
        server.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(),
                5, 0.06D, 0.06D, 0.06D, 0.02D);
        this.playSound(SoundEvents.ARROW_HIT, 0.7F, 0.8F);
        this.playSound(SoundEvents.FIRECHARGE_USE, 0.3F, 1.5F);
    }

    private void embeddedParticles() {
        int fuse = this.getFuse();
        if (fuse < 8 || (this.tickCount & 1) == 0) {
            this.level().addParticle(ParticleTypes.SMALL_FLAME,
                    this.getX(), this.getY() + 0.12D, this.getZ(), 0.0D, 0.025D, 0.0D);
        }
        if (this.tickCount % 4 == 0) {
            this.level().addParticle(ParticleTypes.SMOKE,
                    this.getX(), this.getY() + 0.2D, this.getZ(), 0.0D, 0.02D, 0.0D);
        }
    }

    private void blast() {
        Scorchback owner = this.resolveOwner();
        if (owner != null) {
            AABB area = this.getBoundingBox().inflate(BLAST_RADIUS);
            for (LivingEntity victim : this.level().getEntitiesOfClass(LivingEntity.class, area)) {
                if (!owner.canAttackTarget(victim)
                        || victim.getBoundingBox().distanceToSqr(this.position()) > BLAST_RADIUS * BLAST_RADIUS) {
                    continue;
                }
                Vec3 center = victim.getBoundingBox().getCenter();
                Vec3 start = this.position().subtract(this.getLookAngle().scale(0.15D));
                if (this.level().clip(new ClipContext(start, center, ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE, this)).getType() != HitResult.Type.MISS) {
                    continue;
                }
                victim.hurt(this.damageSources().explosion(this, owner), BLAST_DAMAGE);
            }
        }
        if (this.level() instanceof ServerLevel server) {
            ColorUtil color = new ColorUtil(0xF04B24);
            double y = this.getY() + 0.12D;
            server.sendParticles(new SmashParticleOption(color, 1.2F, 7),
                    this.getX(), y, this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            server.sendParticles(new SmashParticleOption(color, 2.0F, 10),
                    this.getX(), y, this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            ServerParticleUtil.windShockwaveParticle(server, color, 1.6F, 0.0F, 10,
                    new Vec3(this.getX(), y, this.getZ()));
            for (int i = 0; i < 12; i++) {
                double angle = i * Math.PI / 6.0D;
                server.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() + 0.1D, this.getZ(),
                        0, Math.cos(angle) * 0.12D, 0.04D, Math.sin(angle) * 0.12D, 1.0D);
            }
            server.sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.2D, this.getZ(),
                    8, 0.25D, 0.15D, 0.25D, 0.025D);
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 0.6F, 1.4F);
        this.discard();
    }

    @Nullable
    private Scorchback resolveOwner() {
        if (this.ownerId == null || !(this.level() instanceof ServerLevel server)) {
            return null;
        }
        Entity entity = server.getEntity(this.ownerId);
        return entity instanceof Scorchback scorchback && scorchback.isAlive() ? scorchback : null;
    }

    @Override public boolean isPickable() { return false; }
    @Override public boolean canBeCollidedWith() { return false; }
    @Override public boolean isPushable() { return false; }
    @Override public boolean isAttackable() { return false; }
    @Override public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) { return false; }
    @Override public void push(Entity entity) { }
}
