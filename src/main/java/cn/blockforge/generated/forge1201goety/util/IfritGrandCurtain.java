package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.projectiles.HellBlast;
import com.Polarice3.Goety.common.entities.util.FirePillar;
import com.Polarice3.Goety.common.entities.util.MagicLightningTrap;
import com.Polarice3.Goety.utils.ModDamageSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.util.IfritGrandCurtain;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.mixin.GoetyWarlordHellfirePillarMixin;

public final class IfritGrandCurtain {

    public static final int TONGUE_TICKS = 100;

    public static final int AURA_TICKS = 300;

    public static final int REFRESH_TICKS = 20;

    public static final int DEBUFF_TICKS = 100;

    public static final int REPEAT_INTERVAL = 600;

    public static final int REPEAT_WINDOW = 200;

    public static final int STORM_TICKS = 300;

    public static final int STORM_PERIOD = 20;

    public static final int STORM_STRIKES = 3;

    public static final int STORM_TRAP_DURATION = 20;

    public static final float STORM_TRAP_RADIUS = 2.5F;

    public static final double RADIUS = 40.0D;

    public static final int PILLAR_DURATION = 180;

    public static final float PILLAR_DAMAGE = 10.0F;

    public static final int PILLAR_DAMAGE_PERIOD = 20;

    public static final double PILLAR_HIT_RADIUS = 1.8D;

    public static final double[] TONGUE_RADII = {10.0D, 12.0D, 15.0D, 20.0D};

    public static final double TONGUE_SPIN = 0.05D;

    public static final float TONGUE_DAMAGE = 2.0F;

    public static final int METEOR_PERIOD = 24;
    public static final int METEOR_COUNT = 3;
    public static final int HELLBLAST_COUNT = 2;
    public static final double RAIN_MIN_HEIGHT = 16.0D;
    public static final double RAIN_HEIGHT_SPAN = 8.0D;

    public static final double WEATHER_RADIUS = 26.0D;

    public static final int BATTLE_GRACE = 100;

    public static final int LIGHTNING_COLOR = 0xFF7A18;

    private final Ifrit owner;

    private Vec3 origin = Vec3.ZERO;

    private int age = -1;

    private final List<FirePillar> pillars = new ArrayList<>();

    private final List<PendingStrike> pendingStrikes = new ArrayList<>();

    private final List<HellBlast> blasts = new ArrayList<>();

    private int lastBattleTick;

    private final Set<Integer> pillarVictims = new HashSet<>();

    private final Set<Integer> tongueVictims = new HashSet<>();

    IfritGrandCurtain(Ifrit owner) {
        this.owner = owner;
    }

    private record PendingStrike(double x, double y, double z, int atAge) {
    }

    public boolean isActive() {
        return this.age >= 0;
    }

    public int age() {
        return this.age;
    }

    public void begin() {
        this.origin = this.owner.position();
        this.age = 0;
        this.pillars.clear();
        this.pendingStrikes.clear();
        this.blasts.clear();
        this.lastBattleTick = 0;
        this.pillarVictims.clear();
        this.tongueVictims.clear();
    }

    public void finish() {
        for (HellBlast blast : this.blasts) {
            if (blast.isAlive()) {
                blast.discard();
            }
        }
        this.blasts.clear();
        for (FirePillar pillar : this.pillars) {
            if (pillar.isAlive()) {
                pillar.discard();
            }
        }
        this.pillars.clear();
        this.pendingStrikes.clear();
        this.owner.discardCurtainMeteors();
    }

    public void tick() {
        if (this.age < 0 || !this.owner.isAlive()) {
            return;
        }
        if (!(this.owner.level() instanceof ServerLevel server)) {
            return;
        }
        this.updateBattleState();
        this.tickAura(server);
        this.tickStorm(server);
        this.tickPendingStrikes(server);
        this.tickTongues(server);
        this.tickWeather(server);
        this.tickPillars(server);
        this.age++;
    }

    private void tickAura(ServerLevel server) {
        boolean apply;
        boolean burst;
        if (this.age < AURA_TICKS) {
            apply = this.age % REFRESH_TICKS == 0;
            burst = false;
        } else {
            int since = (this.age - AURA_TICKS - REPEAT_INTERVAL) % REPEAT_INTERVAL;
            boolean inWindow = this.age >= AURA_TICKS + REPEAT_INTERVAL && since < REPEAT_WINDOW;
            apply = inWindow && since % REFRESH_TICKS == 0;
            burst = apply;
        }
        if (apply) {
            this.applyAura(server, burst);
        }
    }

    private void applyAura(ServerLevel server, boolean burst) {
        MobEffect flammable = goetyEffect("flammable");
        MobEffect burnHex = goetyEffect("burn_hex");
        if (flammable != null || burnHex != null) {
            for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class,
                    this.owner.getBoundingBox().inflate(RADIUS))) {
                if (!isAuraTarget(victim)) {
                    continue;
                }
                if (flammable != null) {
                    victim.addEffect(new MobEffectInstance(flammable, DEBUFF_TICKS, 0, false, true),
                            this.owner);
                }
                if (burnHex != null) {
                    victim.addEffect(new MobEffectInstance(burnHex, DEBUFF_TICKS, 0, false, true),
                            this.owner);
                }
            }
        }
        if (burst) {
            server.sendParticles(ParticleTypes.FLAME, this.owner.getX(),
                    this.owner.getY() + 1.2D, this.owner.getZ(),
                    60, 1.6D, 1.3D, 1.6D, 0.12D);
            server.sendParticles(ParticleTypes.LAVA, this.owner.getX(),
                    this.owner.getY() + 1.0D, this.owner.getZ(),
                    18, 1.2D, 0.9D, 1.2D, 0.0D);
        }
    }

    private boolean isAuraTarget(LivingEntity victim) {
        if (victim == this.owner || victim instanceof Ifrit || !victim.isAlive()) {
            return false;
        }
        return !(victim instanceof Player player)
                || (!player.isCreative() && !player.isSpectator());
    }

    private void tickStorm(ServerLevel server) {
        if (this.age < STORM_TICKS && this.age % STORM_PERIOD == 0) {
            this.strikeStorm(server);
        }
    }

    private void strikeStorm(ServerLevel server) {
        for (int i = 0; i < STORM_STRIKES; i++) {
            double angle = this.owner.getRandom().nextDouble() * Math.PI * 2.0D;
            double distance = Math.sqrt(this.owner.getRandom().nextDouble()) * RADIUS;
            double x = this.owner.getX() + Math.cos(angle) * distance;
            double z = this.owner.getZ() + Math.sin(angle) * distance;
            double ground = groundHeightAt(server, x, z, this.owner.getY() + 1.5D);
            MagicLightningTrap trap = new MagicLightningTrap(server, x, ground, z);
            trap.setOwner(this.owner);
            trap.setColor(LIGHTNING_COLOR);
            trap.setDuration(STORM_TRAP_DURATION);
            trap.setRadius(STORM_TRAP_RADIUS);
            trap.setDamage(0.0F);
            server.addFreshEntity(trap);
            this.pendingStrikes.add(new PendingStrike(x, ground, z, this.age + STORM_TRAP_DURATION));
        }
        this.owner.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 2.0F, 0.7F);
    }

    private void tickPendingStrikes(ServerLevel server) {
        if (this.pendingStrikes.isEmpty()) {
            return;
        }
        Iterator<PendingStrike> iterator = this.pendingStrikes.iterator();
        while (iterator.hasNext()) {
            PendingStrike strike = iterator.next();
            if (this.age >= strike.atAge()) {
                iterator.remove();
                this.spawnHellfirePillar(server, strike.x(), strike.y(), strike.z());
            }
        }
    }

    private void spawnHellfirePillar(ServerLevel server, double x, double y, double z) {
        FirePillar pillar = new FirePillar(server, x, y, z);
        pillar.getPersistentData().putBoolean(AbstractWitherWarlord.HELLFIRE_PILLAR_TAG, true);
        pillar.setOwner(this.owner);
        pillar.setDuration(PILLAR_DURATION);
        if (server.addFreshEntity(pillar)) {
            this.pillars.add(pillar);
        }
    }

    private void tickPillars(ServerLevel server) {
        if (this.pillars.isEmpty()) {
            return;
        }
        this.pillars.removeIf(pillar -> pillar.isRemoved() || !pillar.isAlive());
        if (this.pillars.isEmpty() || this.age % PILLAR_DAMAGE_PERIOD != 0) {
            return;
        }
        this.pillarVictims.clear();
        for (FirePillar pillar : this.pillars) {
            AABB box = pillar.getBoundingBox().inflate(PILLAR_HIT_RADIUS, 3.0D, PILLAR_HIT_RADIUS);
            for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, box)) {
                if (!isAuraTarget(victim) || !this.pillarVictims.add(victim.getId())) {
                    continue;
                }
                if (victim.distanceToSqr(pillar.position()) > PILLAR_HIT_RADIUS * PILLAR_HIT_RADIUS) {
                    continue;
                }
                applyHellfire(victim, PILLAR_DAMAGE);
            }
        }
    }

    private void tickTongues(ServerLevel server) {
        if (this.age >= TONGUE_TICKS) {
            return;
        }
        if (this.age % REFRESH_TICKS == 0) {
            this.tongueVictims.clear();
        }
        for (int i = 0; i < TONGUE_RADII.length; i++) {
            double radius = TONGUE_RADII[i];
            double direction = (i % 2 == 0) ? 1.0D : -1.0D;
            double base = i * (Math.PI / 2.0D) + direction * this.age * TONGUE_SPIN;
            double headX = this.origin.x + Math.cos(base) * radius;
            double headZ = this.origin.z + Math.sin(base) * radius;
            for (int tail = 0; tail < 6; tail++) {
                double angle = base - direction * tail * 0.055D;
                double px = this.origin.x + Math.cos(angle) * radius;
                double pz = this.origin.z + Math.sin(angle) * radius;
                double py = groundHeightAt(server, px, pz, this.origin.y + 1.5D) + 0.35D;
                server.sendParticles(ParticleTypes.FLAME, px, py, pz,
                        tail == 0 ? 6 : 3, 0.18D, 0.28D, 0.18D, 0.01D);
                if (tail == 0) {
                    server.sendParticles(ParticleTypes.LAVA, px, py, pz, 1, 0.1D, 0.1D, 0.1D, 0.0D);
                }
            }
            double py = groundHeightAt(server, headX, headZ, this.origin.y + 1.5D);
            AABB box = new AABB(headX - 1.6D, py - 2.0D, headZ - 1.6D,
                    headX + 1.6D, py + 3.5D, headZ + 1.6D);
            for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, box)) {
                if (!isAuraTarget(victim) || !this.tongueVictims.add(victim.getId())) {
                    continue;
                }
                victim.setSecondsOnFire(5);
                applyHellfire(victim, TONGUE_DAMAGE);
            }
        }
    }

    private void tickWeather(ServerLevel server) {
        if (battleActive()) {
            for (int i = 0; i < 3; i++) {
                double angle = this.owner.getRandom().nextDouble() * Math.PI * 2.0D;
                double distance = Math.sqrt(this.owner.getRandom().nextDouble()) * WEATHER_RADIUS;
                double x = this.owner.getX() + Math.cos(angle) * distance;
                double z = this.owner.getZ() + Math.sin(angle) * distance;
                server.sendParticles(ParticleTypes.CRIMSON_SPORE, x,
                        this.owner.getY() + 8.0D + this.owner.getRandom().nextDouble() * 10.0D, z,
                        1, 0.4D, 0.2D, 0.4D, 0.005D);
            }
        }
        this.blasts.removeIf(blast -> blast.isRemoved() || !blast.isAlive());
        if (!battleActive() || this.age % METEOR_PERIOD != 0) {
            return;
        }
        List<Vec3> wave = new ArrayList<>();
        for (int i = 0; i < METEOR_COUNT + HELLBLAST_COUNT; i++) {
            Vec3 spawn = pickRainSpawn(server, wave);
            if (spawn == null) continue; // 未加载地形或世界顶端无净空时不强行生成。
            wave.add(spawn);
            if (i < METEOR_COUNT) {
                this.owner.spawnCurtainMeteor(spawn.x, spawn.y, spawn.z);
            } else {
                HellBlast blast = new HellBlast(spawn.x, spawn.y, spawn.z, 0.0D, -1.0D, 0.0D,
                        this.owner.level());
                blast.setOwner(this.owner);
                blast.setDamage(0.0F);
                blast.setRadius(2.0F);
                blast.setFiery(0);
                if (server.addFreshEntity(blast)) this.blasts.add(blast);
            }
        }
    }

    private Vec3 pickRainSpawn(ServerLevel server, List<Vec3> wave) {
        for (int attempt = 0; attempt < 32; attempt++) {
            double angle = this.owner.getRandom().nextDouble() * Math.PI * 2.0D;
            double distance = Math.sqrt(this.owner.getRandom().nextDouble()) * WEATHER_RADIUS;
            double x = this.owner.getX() + Math.cos(angle) * distance;
            double z = this.owner.getZ() + Math.sin(angle) * distance;
            if (!server.hasChunkAt(BlockPos.containing(x, this.owner.getY(), z))) continue;
            boolean overlaps = false;
            for (Vec3 other : wave) {
                double dx = x - other.x;
                double dz = z - other.z;
                if (dx * dx + dz * dz < 16.0D) { overlaps = true; break; }
            }
            if (overlaps) continue;
            double ground = MinionGroundPlacement.groundFeetYUnderWater(server, x, z, this.owner.getY(), 1);
            if (!MinionGroundPlacement.isStandableUnderWater(server, x, z, Mth.floor(ground), 1)) continue;
            double y = ground + RAIN_MIN_HEIGHT + this.owner.getRandom().nextDouble() * RAIN_HEIGHT_SPAN;
            if (y >= server.getMaxBuildHeight() - 1) continue;
            BlockPos spawnPos = BlockPos.containing(x, y, z);
            if (!server.isEmptyBlock(spawnPos) || !server.isEmptyBlock(spawnPos.above())) continue;
            return new Vec3(x, y, z);
        }
        return null;
    }

    private void updateBattleState() {
        LivingEntity target = this.owner.getTarget();
        if (target != null && target.isAlive()) {
            this.lastBattleTick = this.age;
        }
    }

    private boolean battleActive() {
        if (this.age < STORM_TICKS) {
            return true;
        }
        return this.age - this.lastBattleTick <= BATTLE_GRACE;
    }

    private void applyHellfire(LivingEntity victim, float amount) {
        int invulnerable = victim.invulnerableTime;
        victim.invulnerableTime = 0;
        victim.hurt(ModDamageSource.hellfire(this.owner, this.owner), amount);
        victim.invulnerableTime = invulnerable;
    }

    private static double groundHeightAt(ServerLevel server, double x, double z, double refY) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(
                Mth.floor(x), Mth.floor(refY), Mth.floor(z));
        for (int i = 0; i < 8; i++) {
            if (!server.getBlockState(pos).isAir()) {
                return pos.getY() + 1.0D;
            }
            pos.move(0, -1, 0);
        }
        return refY;
    }

    private static MobEffect goetyEffect(String path) {
        return ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", path));
    }

    static void tickHook(Ifrit mob) {
        if (!mob.grandCurtainUsed && mob.isAlive() && mob.isPhaseTwo()) {
            mob.grandCurtainUsed = true;
            mob.grandCurtain = new IfritGrandCurtain(mob);
            mob.grandCurtain.begin();
            IfritCombo.startAction(mob, IfritCombo.Action.GRAND_CURTAIN);
        }
        if (mob.grandCurtain != null) {
            mob.grandCurtain.tick();
        }
    }

    static void endHook(Ifrit mob) {
        if (mob.grandCurtain != null) {
            mob.grandCurtain.finish();
            mob.grandCurtain = null;
        } else {
            IfritMeteors.discard(mob);
        }
    }
}
