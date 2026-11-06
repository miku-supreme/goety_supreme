package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.util.SummonApostle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.block.IfritArenaCoreBlock;
import cn.blockforge.generated.goetysupreme.entity.IfritArenaCoreBlockEntity;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public final class IfritArenaCoreBlockEntity extends BlockEntity {

    private static final double TRIGGER_RADIUS = 10.0D;

    private static final int RITUAL_TICKS = 60;

    private static final int SCAN_INTERVAL = 10;

    private static final double EFFECT_SEARCH_RADIUS = 32.0D;

    private boolean consumed;

    private int ritualTicks = -1;

    private int scanCooldown;

    public IfritArenaCoreBlockEntity(BlockPos pos, BlockState state) {
        super(GeneratedMod.IFRIT_ARENA_CORE_ENTITY.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state,
            IfritArenaCoreBlockEntity be) {
        be.tick(level, pos);
    }

    private void tick(Level level, BlockPos pos) {
        if (this.consumed || !(level instanceof ServerLevel server)) {
            return;
        }
        if (this.ritualTicks >= 0) {
            if (this.ritualTicks == 0) {
                this.finish(server, pos);
            } else {
                --this.ritualTicks;
            }
            return;
        }
        if (--this.scanCooldown > 0) {
            return;
        }
        this.scanCooldown = SCAN_INTERVAL;
        Player player = server.getNearestPlayer(
                pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, TRIGGER_RADIUS, false);
        if (player != null && !player.isSpectator()) {
            this.start(server, pos);
        }
    }

    private void start(ServerLevel server, BlockPos pos) {
        this.ritualTicks = RITUAL_TICKS;
        this.setChanged();
        double cx = pos.getX() + 0.5D;
        double cy = pos.getY() + 0.05D;
        double cz = pos.getZ() + 0.5D;
        SummonApostle effect = new SummonApostle(ModEntityType.SUMMON_APOSTLE.get(), server);
        effect.moveTo(cx, cy, cz, 0.0F, 0.0F);
        effect.setNoGravity(true);
        server.addFreshEntity(effect);
        server.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.HOSTILE, 2.0F, 0.6F);
    }

    private void finish(ServerLevel server, BlockPos pos) {
        this.ritualTicks = -1;
        this.consumed = true;
        this.setChanged();
        double cx = pos.getX() + 0.5D;
        double cy = pos.getY() + 0.05D;
        double cz = pos.getZ() + 0.5D;
        AABB area = new AABB(pos).inflate(EFFECT_SEARCH_RADIUS);
        for (SummonApostle effect : server.getEntitiesOfClass(SummonApostle.class, area)) {
            effect.discard();
        }
        server.sendParticles(ParticleTypes.FLAME, cx, cy + 1.0D, cz, 640, 1.8D, 1.8D, 1.8D, 0.45D);
        server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, cx, cy + 1.0D, cz, 320, 1.5D, 1.6D, 1.5D, 0.28D);
        server.sendParticles(ParticleTypes.LAVA, cx, cy + 0.6D, cz, 160, 2.0D, 1.1D, 2.0D, 0.0D);
        server.sendParticles(ParticleTypes.EXPLOSION_EMITTER, cx, cy + 1.0D, cz, 5, 2.8D, 1.5D, 2.8D, 0.0D);
        server.sendParticles(ParticleTypes.LARGE_SMOKE, cx, cy + 1.4D, cz, 140, 2.2D, 1.8D, 2.2D, 0.06D);
        for (int arm = 0; arm < 3; arm++) {
            double phase = arm * (Math.PI * 2.0D / 3.0D);
            for (int step = 0; step < 96; step++) {
                double t = step / 96.0D;
                double angle = phase + t * Math.PI * 6.0D;
                double radius = 0.5D + t * 2.8D;
                double x = cx + Math.cos(angle) * radius;
                double z = cz + Math.sin(angle) * radius;
                double y = cy + 0.2D + t * 5.5D;
                server.sendParticles(ParticleTypes.FLAME, x, y, z, 2, 0.06D, 0.06D, 0.06D, 0.0D);
                if (step % 3 == 0) {
                    server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 1, 0.06D, 0.06D, 0.06D, 0.0D);
                }
            }
        }
        for (int ring = 1; ring <= 12; ring++) {
            int points = ring * 12;
            double radius = ring * 1.4D;
            for (int i = 0; i < points; i++) {
                double angle = (Math.PI * 2.0D / points) * i;
                double x = cx + Math.cos(angle) * radius;
                double z = cz + Math.sin(angle) * radius;
                server.sendParticles(ParticleTypes.FLAME, x, cy + 0.3D + ring * 0.12D, z, 2,
                        0.0D, 0.2D, 0.0D, 0.03D);
            }
        }
        Ifrit ifrit = ModEntityTypes.IFRIT.get().create(server);
        if (ifrit != null) {
            ifrit.moveTo(cx, pos.getY(), cz, server.random.nextFloat() * 360.0F, 0.0F);
            ifrit.finalizeSpawn(server, server.getCurrentDifficultyAt(pos),
                    MobSpawnType.STRUCTURE, null, null);
            server.addFreshEntity(ifrit);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("IfritArenaConsumed", this.consumed);
        tag.putInt("IfritArenaRitualTicks", this.ritualTicks);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.consumed = tag.getBoolean("IfritArenaConsumed");
        this.ritualTicks = this.consumed ? -1
                : (tag.contains("IfritArenaRitualTicks") ? tag.getInt("IfritArenaRitualTicks") : -1);
    }
}
