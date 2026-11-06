package cn.blockforge.generated.goetysupreme.entity;

import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.util.ProjectileIntercept;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public final class NamelessClone {

    public static final int LIFETIME_TICKS = 100;

    public static final int VOLLEY_COUNT = 3;

    public static final int VOLLEY_INTERVAL = 20;

    public static final double SIDE_DISTANCE = 6.0D;

    public static final double SIDE_DRIFT = 2.0D;

    public static final double DIAMOND_HEIGHT = 3.0D;

    public static final double DIAMOND_RADIUS = 6.0D;

    public static final double ORBIT_AMPLITUDE = 0.35D;

    public static final float SKULL_DIRECT_DAMAGE = 12.0F;

    public static final double SKULL_POWER = 0.1D;

    public static final double SKULL_INERTIA = 0.95D;

    public static final double SIDE_HEAD_OFFSET = 1.3D;

    public static final double MAIN_HEAD_HEIGHT = 3.0D;

    public static final double SIDE_HEAD_HEIGHT = 2.2D;

    private static final double BODY_SEARCH_RADIUS = 24.0D;

    private static final Map<UUID, CloneState> STATES = new ConcurrentHashMap<>();

    private NamelessClone() {
    }

    public static void spawnSidePair(NamelessWither body) {
        if (!(body.level() instanceof ServerLevel level) || body.isClone()) {
            return;
        }
        prune(level);
        LivingEntity owner = body.getTrueOwner();
        float yaw = body.yBodyRot * Mth.DEG_TO_RAD;
        for (int slot = 0; slot < 2; slot++) {
            NamelessWither clone = NamelessWither.createClone(level, owner != null ? owner : body, slot);
            if (clone == null) {
                continue;
            }
            double sideAngle = yaw + (slot == 0 ? Math.PI / 2.0D : -Math.PI / 2.0D);
            double fwd = (body.getRandom().nextDouble() * 2.0D - 1.0D) * SIDE_DRIFT;
            double offX = Math.cos(sideAngle) * SIDE_DISTANCE + Math.cos(yaw) * fwd;
            double offZ = Math.sin(sideAngle) * SIDE_DISTANCE + Math.sin(yaw) * fwd;
            clone.setPos(body.getX() + offX, body.getY(), body.getZ() + offZ);
            clone.setYRot(body.getYRot());
            clone.setYBodyRot(body.getYRot());
            clone.setYHeadRot(body.getYRot());
            STATES.put(clone.getUUID(), new CloneState(body.getUUID(), 0, 0.0D, offX, offZ, null));
            level.addFreshEntity(clone);
            spawnFx(level, body, clone);
        }
    }

    public static void spawnDiamond(NamelessWither body) {
        if (!(body.level() instanceof ServerLevel level) || body.isClone()) {
            return;
        }
        prune(level);
        LivingEntity target = body.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }
        LivingEntity owner = body.getTrueOwner();
        for (int slot = 0; slot < 4; slot++) {
            NamelessWither clone = NamelessWither.createClone(level, owner != null ? owner : body, slot);
            if (clone == null) {
                continue;
            }
            double baseAngle = Math.PI / 4.0D + Math.PI / 2.0D * slot;
            double x = target.getX() + Math.cos(baseAngle) * DIAMOND_RADIUS;
            double y = target.getY() + target.getBbHeight() + DIAMOND_HEIGHT;
            double z = target.getZ() + Math.sin(baseAngle) * DIAMOND_RADIUS;
            clone.setPos(x, y, z);
            clone.setYRot(body.getYRot());
            clone.setYBodyRot(body.getYRot());
            clone.setYHeadRot(body.getYRot());
            STATES.put(clone.getUUID(),
                    new CloneState(body.getUUID(), 1, baseAngle, 0.0D, 0.0D, target.getUUID()));
            level.addFreshEntity(clone);
            spawnFx(level, body, clone);
        }
    }

    private static void spawnFx(ServerLevel level, NamelessWither body, NamelessWither clone) {
        body.playSound(GreenSmithFx.sound("wind_blast"), 1.4F, 0.8F);
        level.sendParticles(GreenSmithFx.necroFire(),
                clone.getX(), clone.getY() + 1.0D, clone.getZ(),
                12, 0.3D, 0.5D, 0.3D, 0.02D);
    }

    public static void tick(NamelessWither clone) {
        if (!(clone.level() instanceof ServerLevel level)) {
            return;
        }
        if (!clone.isAlive()) {
            STATES.remove(clone.getUUID());
            return;
        }
        CloneState state = stateOf(clone);
        NamelessWither body = bodyOf(level, state, clone);
        if (body == null || !body.isAlive()) {
            dismiss(clone);
            return;
        }
        ++state.age;
        clone.setDeltaMovement(Vec3.ZERO);
        LivingEntity boundTarget = null;
        if (state.targetUuid != null) {
            Entity bound = level.getEntity(state.targetUuid);
            boundTarget = bound instanceof LivingEntity living && living.isAlive() ? living : null;
        }
        if (state.kind == 0) {
            clone.setPos(body.getX() + state.offX, body.getY(), body.getZ() + state.offZ);
        } else {
            // 菱形公转分身只跟随生成时绑定的目标；目标死亡后消失，不再转移到新目标
            if (boundTarget == null) {
                dismiss(clone);
                return;
            }
            double swing = Math.sin(level.getGameTime() * 0.02D) * ORBIT_AMPLITUDE;
            double angle = state.baseAngle + swing;
            clone.setPos(boundTarget.getX() + Math.cos(angle) * DIAMOND_RADIUS,
                    boundTarget.getY() + boundTarget.getBbHeight() + DIAMOND_HEIGHT,
                    boundTarget.getZ() + Math.sin(angle) * DIAMOND_RADIUS);
        }
        LivingEntity target = body.getTarget();
        if (target != null && target.isAlive()) {
            clone.setTarget(target);
            face(clone, target);
        }
        if (state.age >= LIFETIME_TICKS) {
            dismiss(clone);
            return;
        }
        if (--state.timer > 0) {
            return;
        }
        state.timer = VOLLEY_INTERVAL;
        if (target == null || !target.isAlive() || clone.getCloneSlot() != 0 || state.volleys >= VOLLEY_COUNT) {
            return;
        }
        if (state.kind == 0) {
            fireSideRound(body, target);
        } else {
            fireDiamondRound(body, target);
        }
        ++state.volleys;
        if (state.volleys >= VOLLEY_COUNT) {
            dismissKind(body, state.kind);
        }
    }

    private static void fireSideRound(NamelessWither body, LivingEntity target) {
        List<NamelessWither> sides = kindClones(body, 0);
        if (sides.isEmpty()) {
            return;
        }
        List<NamelessWither> shuffled = new ArrayList<>(sides);
        Collections.shuffle(shuffled, new Random(body.getRandom().nextLong()));
        if (shuffled.size() >= 2) {
            launchSkull(shuffled.get(0), body, 0, target);
            launchBeam(shuffled.get(1), body, 0, target);
        } else if (body.getRandom().nextBoolean()) {
            launchSkull(shuffled.get(0), body, 0, target);
        } else {
            launchBeam(shuffled.get(0), body, 0, target);
        }
    }

    private static void fireDiamondRound(NamelessWither body, LivingEntity target) {
        List<NamelessWither> diamonds = kindClones(body, 1);
        if (diamonds.isEmpty()) {
            return;
        }
        List<String> kinds = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            kinds.add("skull");
        }
        for (int i = 0; i < 2; i++) {
            kinds.add("beam");
        }
        Collections.shuffle(kinds, new Random(body.getRandom().nextLong()));
        for (int i = 0; i < diamonds.size() && i < kinds.size(); i++) {
            if ("skull".equals(kinds.get(i))) {
                launchSkull(diamonds.get(i), body, 0, target);
            } else {
                launchBeam(diamonds.get(i), body, 0, target);
            }
        }
    }

    private static List<NamelessWither> kindClones(NamelessWither body, int kind) {
        List<NamelessWither> result = new ArrayList<>();
        if (!(body.level() instanceof ServerLevel level)) {
            return result;
        }
        for (NamelessWither clone : livingClones(level, body)) {
            CloneState state = STATES.get(clone.getUUID());
            if (state != null && state.kind == kind) {
                result.add(clone);
            }
        }
        return result;
    }

    public static void launchSkull(NamelessWither clone, LivingEntity owner, int head, LivingEntity target) {
        Vec3 spawn = headPosition(clone, head);
        Vec3 aim = ProjectileIntercept.solveForTarget(spawn, target, SKULL_POWER, SKULL_INERTIA);
        Vec3 dir = aim.subtract(spawn);
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = new Vec3(0.0D, 0.0D, 1.0D);
        }
        ServantWitherSkull skull = new ServantWitherSkull(clone.level(), owner, dir.x, dir.y, dir.z);
        skull.setMainHeadShot(false);
        skull.setDirectDamage(SKULL_DIRECT_DAMAGE);
        skull.setPosRaw(spawn.x, spawn.y, spawn.z);
        clone.level().addFreshEntity(skull);
    }

    public static void launchBeam(NamelessWither clone, LivingEntity owner, int head, LivingEntity target) {
        Vec3 spawn = headPosition(clone, head);
        Vec3 dir = new Vec3(target.getX() - spawn.x, target.getEyeY() - spawn.y, target.getZ() - spawn.z);
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = clone.getLookAngle();
        }
        dir = dir.normalize();
        NamelessBeam beam = NamelessBeam.create(clone.level(), clone, dir.x, dir.y, dir.z);
        if (beam == null) {
            return;
        }
        beam.setOwner(owner);
        beam.setPos(spawn.x, spawn.y, spawn.z);
        clone.level().addFreshEntity(beam);
    }

    public static void dismiss(NamelessWither clone) {
        NamelessWither body = clone.level() instanceof ServerLevel level ? bodyByUuid(level, clone) : null;
        if (clone.level() instanceof ServerLevel server) {
            // 消失特效照绿豆分身（GreenSmithClones.dismissClone:509-511）
            server.sendParticles(GreenSmithFx.lich(),
                    clone.getX(), clone.getY() + clone.getBbHeight() * 0.5D, clone.getZ(),
                    30, 0.5D, 0.8D, 0.5D, 0.03D);
        }
        STATES.remove(clone.getUUID());
        if (body != null) {
            body.playSound(GreenSmithFx.sound("lich_teleport_out"), 1.0F, 0.9F);
        }
        clone.discard();
    }

    public static void dismissAll(NamelessWither body) {
        if (!(body.level() instanceof ServerLevel level)) {
            return;
        }
        for (NamelessWither clone : livingClones(level, body)) {
            STATES.remove(clone.getUUID());
            clone.discard();
        }
        body.playSound(GreenSmithFx.sound("lich_teleport_out"), 1.0F, 0.9F);
    }

    private static void dismissKind(NamelessWither body, int kind) {
        if (!(body.level() instanceof ServerLevel level)) {
            return;
        }
        for (NamelessWither clone : livingClones(level, body)) {
            CloneState state = STATES.get(clone.getUUID());
            if (state != null && state.kind == kind) {
                STATES.remove(clone.getUUID());
                clone.discard();
            }
        }
        body.playSound(GreenSmithFx.sound("lich_teleport_out"), 1.0F, 0.9F);
    }

    public static List<NamelessWither> livingClones(ServerLevel level, NamelessWither body) {
        List<NamelessWither> clones = new ArrayList<>();
        for (Entity entity : level.getEntitiesOfClass(NamelessWither.class,
                body.getBoundingBox().inflate(BODY_SEARCH_RADIUS))) {
            if (entity instanceof NamelessWither wither && wither.isClone() && wither.isAlive()
                    && belongsTo(wither, body)) {
                clones.add(wither);
            }
        }
        return clones;
    }

    public static void prune(ServerLevel level) {
        STATES.entrySet().removeIf(entry -> {
            Entity entity = level.getEntity(entry.getKey());
            return !(entity instanceof NamelessWither wither) || !wither.isAlive() || !wither.isClone();
        });
    }

    public static Vec3 headPosition(NamelessWither clone, int head) {
        return new Vec3(headX(clone, head), headY(clone, head), headZ(clone, head));
    }

    private static boolean belongsTo(NamelessWither clone, NamelessWither body) {
        CloneState state = STATES.get(clone.getUUID());
        return state == null || state.bodyUuid == null || state.bodyUuid.equals(body.getUUID());
    }

    private static CloneState stateOf(NamelessWither clone) {
        CloneState state = STATES.get(clone.getUUID());
        if (state == null) {
            state = new CloneState(null, 0, 0.0D, 0.0D, 0.0D, null);
            STATES.put(clone.getUUID(), state);
        }
        return state;
    }

    @Nullable
    private static NamelessWither bodyOf(ServerLevel level, CloneState state, NamelessWither clone) {
        if (state.bodyUuid != null && level.getEntity(state.bodyUuid) instanceof NamelessWither body
                && body.isAlive()) {
            return body;
        }
        for (Entity entity : level.getEntitiesOfClass(NamelessWither.class,
                clone.getBoundingBox().inflate(BODY_SEARCH_RADIUS))) {
            if (entity instanceof NamelessWither wither && !wither.isClone() && wither.isAlive()) {
                state.bodyUuid = wither.getUUID();
                return wither;
            }
        }
        return null;
    }

    @Nullable
    private static NamelessWither bodyByUuid(ServerLevel level, NamelessWither clone) {
        CloneState state = STATES.get(clone.getUUID());
        if (state != null && state.bodyUuid != null
                && level.getEntity(state.bodyUuid) instanceof NamelessWither body && body.isAlive()) {
            return body;
        }
        return null;
    }

    private static double headX(NamelessWither clone, int head) {
        if (head <= 0) {
            return clone.getX();
        }
        float f = (clone.yBodyRot + 180.0F * (head - 1)) * Mth.DEG_TO_RAD;
        return clone.getX() + Mth.cos(f) * SIDE_HEAD_OFFSET;
    }

    private static double headY(NamelessWither clone, int head) {
        return head <= 0 ? clone.getY() + MAIN_HEAD_HEIGHT : clone.getY() + SIDE_HEAD_HEIGHT;
    }

    private static double headZ(NamelessWither clone, int head) {
        if (head <= 0) {
            return clone.getZ();
        }
        float f = (clone.yBodyRot + 180.0F * (head - 1)) * Mth.DEG_TO_RAD;
        return clone.getZ() + Mth.sin(f) * SIDE_HEAD_OFFSET;
    }

    private static void face(NamelessWither clone, LivingEntity target) {
        Vec3 eye = clone.getEyePosition();
        double dx = target.getX() - eye.x;
        double dy = target.getEyeY() - eye.y;
        double dz = target.getZ() - eye.z;
        float yaw = (float) (Mth.atan2(dz, dx) * Mth.RAD_TO_DEG) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * Mth.RAD_TO_DEG));
        clone.setYRot(yaw);
        clone.setYBodyRot(yaw);
        clone.setYHeadRot(yaw);
        clone.setXRot(pitch);
    }

    private static final class CloneState {

        @Nullable
        private UUID bodyUuid;
        private final int kind;
        private final double baseAngle;
        private final double offX;
        private final double offZ;
        @Nullable
        private final UUID targetUuid;
        private int timer = VOLLEY_INTERVAL;
        private int age;
        private int volleys;

        private CloneState(@Nullable UUID bodyUuid, int kind, double baseAngle, double offX, double offZ,
                @Nullable UUID targetUuid) {
            this.bodyUuid = bodyUuid;
            this.kind = kind;
            this.baseAngle = baseAngle;
            this.offX = offX;
            this.offZ = offZ;
            this.targetUuid = targetUuid;
        }
    }
}
