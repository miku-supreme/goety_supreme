package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import com.Polarice3.Goety.common.entities.projectiles.NecroBolt;
import com.Polarice3.Goety.common.entities.util.MagicLightningTrap;
import com.Polarice3.Goety.common.research.ResearchList;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.SEHelper;
import com.Polarice3.Goety.utils.ServantUtil;
import com.Polarice3.Goety.utils.SoundUtil;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class NamelessBeam extends NecroBolt {

    public static final String ENTITY_ID = "nameless_beam";
    public static final double MAX_RANGE = 40.0D;
    public static final float DAMAGE = 12.0F;
    private static final String CONVERT_TAG = "GoetySupremeNamelessBeamConvert";
    private static final int CONVERT_CAP = 8;
    public static final float DRAIN = 3.0F;
    public static final float LIGHTNING_DAMAGE = 8.0F;
    public static final int NAMELESS_COLOR = 11009086;
    public static final int TRAP_DURATION = 40;
    private static final double HOMING_KEEP = 0.8D;
    private static final double HOMING_PULL = 0.2D;

    private final Set<UUID> pierced = new HashSet<>();
    private Vec3 launchPos = Vec3.ZERO;
    private boolean homingLost;
    private double closestDistSqr = Double.MAX_VALUE;
    private LivingEntity trackedTarget;

    public NamelessBeam(EntityType<? extends NamelessBeam> type, Level level) {
        super(type, level);
    }

    @SuppressWarnings("unchecked")
    public static NamelessBeam create(Level level, LivingEntity owner, double xPower, double yPower,
            double zPower) {
        if (!(level instanceof ServerLevel)) {
            return null;
        }
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath("goety_supreme", ENTITY_ID));
        if (type == null) {
            return null;
        }
        NamelessBeam beam = new NamelessBeam((EntityType<? extends NamelessBeam>) type, level);
        beam.setOwner(owner);
        beam.moveTo(owner.getX(), owner.getEyeY() - 0.2D, owner.getZ(), owner.getYRot(), owner.getXRot());
        beam.launchPos = beam.position();
        beam.setDeltaMovement(xPower, yPower, zPower);
        SoundUtil.playNecroBolt(owner);
        return beam;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            return;
        }
        if (this.position().distanceTo(this.launchPos) > MAX_RANGE) {
            this.discard();
            return;
        }
        this.steer();
    }

    private void steer() {
        if (this.homingLost) {
            Vec3 motion = this.getDeltaMovement();
            if (motion.lengthSqr() < 1.0E-6D) {
                motion = Vec3.directionFromRotation(this.getXRot(), this.getYRot()).scale(0.6D);
            } else {
                motion = motion.scale(1.0D / 0.9D);
            }
            this.setDeltaMovement(motion);
            return;
        }
        if (!(this.getOwner() instanceof LivingEntity owner)) {
            this.homingLost = true;
            return;
        }
        Entity target = owner.getLastHurtMob();
        if (!(target instanceof LivingEntity living) || !living.isAlive()) {
            this.homingLost = true;
            return;
        }
        if (living != this.trackedTarget) {
            this.trackedTarget = living;
            this.closestDistSqr = Double.MAX_VALUE;
        }
        double distSqr = this.distanceToSqr(living);
        double threshold = living.getBbWidth();
        if (this.closestDistSqr < Double.MAX_VALUE && distSqr > this.closestDistSqr
                && this.closestDistSqr < threshold * threshold) {
            this.homingLost = true;
            return;
        }
        this.closestDistSqr = Math.min(this.closestDistSqr, distSqr);
        Vec3 motion = this.getDeltaMovement();
        double speed = Math.max(motion.length(), 0.6D);
        Vec3 aim = living.position()
                .add(0.0D, living.getBbHeight() * 0.5D, 0.0D)
                .subtract(this.position());
        if (aim.lengthSqr() < 1.0E-4D) {
            return;
        }
        this.setDeltaMovement(motion.scale(HOMING_KEEP).add(aim.normalize().scale(speed * HOMING_PULL)));
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity hit = result.getEntity();
        if (!(hit instanceof LivingEntity victim) || this.level().isClientSide()) {
            return;
        }
        if (!this.pierced.add(victim.getUUID()) || !this.canHitEntity(victim)) {
            return;
        }
        this.homingLost = true;
        LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
        boolean hurt = victim.hurt(ModDamageSource.indirectSoul(this, owner), DAMAGE);
        if (hurt && owner != null) {
            owner.heal(DRAIN);
        }
        if (!victim.isAlive()) {
            this.convert(victim, owner);
        }
    }

    private void convert(LivingEntity victim, LivingEntity owner) {
        if (owner == null) {
            return;
        }
        if (this.level() instanceof ServerLevel server) {
            int existing = 0;
            for (net.minecraft.world.entity.Entity entity : server.getAllEntities()) {
                if (entity instanceof com.Polarice3.Goety.common.entities.ally.Summoned summoned
                        && summoned.getTrueOwner() == owner
                        && summoned.getPersistentData().getBoolean(CONVERT_TAG)) {
                    if (++existing >= CONVERT_CAP) {
                        return;
                    }
                }
            }
        }
        if (victim instanceof Zombie) {
            ServantUtil.convertZombies(victim, owner, true);
            victim.getPersistentData().putBoolean(CONVERT_TAG, true);
            return;
        }
        if (victim instanceof AbstractSkeleton) {
            boolean bygone = owner instanceof Player player
                    && SEHelper.hasResearch(player, ResearchList.BYGONE);
            ServantUtil.convertSkeletons(victim, owner, bygone, true);
            victim.getPersistentData().putBoolean(CONVERT_TAG, true);
            return;
        }
        if (victim instanceof Mob mob) {
            if (!ServantUtil.convertUndead(mob, owner, true, true)) {
                ServantUtil.infect(mob, owner, true, true);
            }
            victim.getPersistentData().putBoolean(CONVERT_TAG, true);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!this.level().isClientSide()) {
            this.spawnLightningTrap();
        }
        super.onHitBlock(result);
    }

    private void spawnLightningTrap() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
        MagicLightningTrap trap = new MagicLightningTrap(server, this.getX(), this.getY(), this.getZ());
        trap.setColor(NAMELESS_COLOR);
        if (owner != null) {
            trap.setOwner(owner);
        }
        trap.setDuration(TRAP_DURATION);
        trap.setDamage(LIGHTNING_DAMAGE);
        server.addFreshEntity(trap);
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        server.sendParticles(GreenSmithFx.necroFire(), x, y, z, 24, 0.4D, 0.3D, 0.4D, 0.03D);
        if (owner != null) {
            for (int[] off : new int[][]{{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                AbstractGreenSmith.spawnGreenFireFor(server, owner, x + off[0], y, z + off[1]);
            }
        }
    }
}
