package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.util.MagicLightningTrap;
import com.Polarice3.Goety.common.network.ModNetwork;
import com.Polarice3.Goety.common.network.server.SThunderBoltPacket;
import com.Polarice3.Goety.utils.ColorUtil;
import cn.blockforge.generated.goetysupreme.entity.GreenFireEntity;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

/** 绿豆阴火领域的落雷与绿火生成，从 AbstractGreenSmith 抽出的纯表现/落雷部分。 */
public final class GreenSmithFieldSkills {

    private GreenSmithFieldSkills() {
    }

    public static void strikeLightning(AbstractGreenSmith smith, ServerLevel server,
            double x, double y, double z, boolean beam) {
        if (beam) {
            ModNetwork.sendToALL(new SThunderBoltPacket(
                    smith.getEyePosition(),
                    new Vec3(x, y, z),
                    new ColorUtil(AbstractGreenSmith.GREEN_LIGHTNING_COLOR),
                    10));
        }
        MagicLightningTrap trap = new MagicLightningTrap(server, x, y, z);
        trap.setOwner(smith);
        trap.setColor(AbstractGreenSmith.GREEN_LIGHTNING_COLOR);
        trap.setDuration(AbstractGreenSmith.LIGHTNING_TRAP_DURATION);
        trap.setRadius(AbstractGreenSmith.LIGHTNING_RADIUS);
        trap.setDamage(AbstractGreenSmith.LIGHTNING_DAMAGE + smith.soulJarDamageBonus());
        server.addFreshEntity(trap);
        server.sendParticles(GreenSmithFx.necroFire(), x, y, z, 24, 0.4D, 0.3D, 0.4D, 0.03D);
        smith.playGoetySound("thunder_strike_fast", 1.4F, 1.15F);
    }

    public static void spawnGreenFire(AbstractGreenSmith smith, ServerLevel server,
            double x, double y, double z) {
        AABB nearby = new AABB(x - 0.75D, y - 0.5D, z - 0.75D, x + 0.75D, y + 0.5D, z + 0.75D);
        if (!server.getEntitiesOfClass(GreenFireEntity.class, nearby).isEmpty()) {
            return;
        }
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(AbstractGreenSmith.GREEN_FIRE_ID);
        if (type == null) {
            return;
        }
        Entity fire = type.create(server);
        if (!(fire instanceof GreenFireEntity green)) {
            return;
        }
        green.setOwner(smith);
        green.moveTo(x, y, z, 0.0F, 0.0F);
        server.addFreshEntity(green);
    }



    public static void tickField(AbstractGreenSmith smith) {
        if (smith.fieldTicks <= 0) {
            return;
        }
        --smith.fieldTicks;
        smith.getNavigation().stop();
        smith.setDeltaMovement(0.0D, Math.min(0.0D, smith.getDeltaMovement().y), 0.0D);
        smith.setYRot(smith.fieldYaw);
        smith.setXRot(smith.fieldPitch);
        smith.yBodyRot = smith.fieldYaw;
        smith.yBodyRotO = smith.fieldYaw;
        smith.yHeadRot = smith.fieldYaw;
        smith.yHeadRotO = smith.fieldYaw;
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        if (smith.fieldTicks <= 0) {
            smith.setFieldActive(false);
            smith.fieldCooldown = AbstractGreenSmith.FIELD_COOLDOWN;
            return;
        }
        tickFieldShield(smith, server);
        if (++smith.fieldStrikeTimer >= 10) {
            smith.fieldStrikeTimer = 0;
            fieldStrike(smith, server);
        }
        if (++smith.fieldBoltTimer >= AbstractGreenSmith.FIELD_BOLT_INTERVAL) {
            smith.fieldBoltTimer = 0;
            fieldDirectBolts(smith, server);
        }
    }

    public static void tickFieldShield(AbstractGreenSmith smith, ServerLevel server) {
        if (smith.tickCount % AbstractGreenSmith.FIELD_SHIELD_WIND_INTERVAL == 0) {
            ParticleOptions wind = GreenSmithFx.chillHideWind(AbstractGreenSmith.FIELD_SHIELD_WIND_COLOR,
                    (float) (smith.getBbWidth() * 1.9D),
                    (float) (smith.getBbHeight() * 0.62D),
                    smith.getId());
            if (wind != null) {
                server.sendParticles(wind,
                        smith.getX(), smith.getY(), smith.getZ(),
                        1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        if (smith.tickCount % AbstractGreenSmith.FIELD_SHIELD_AURA_INTERVAL == 0) {
            server.sendParticles(GeneratedMod.FIELD_SHIELD_AURA.get(),
                    smith.getX(), smith.getY() + smith.getBbHeight() * 0.5D, smith.getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    public static void fieldStrike(AbstractGreenSmith smith, ServerLevel server) {
        LivingEntity target = smith.getTarget();
        Vec3 origin = target != null ? target.position() : smith.position();
        double x = origin.x + (smith.getRandom().nextDouble() * 2.0D - 1.0D) * 10.0D;
        double z = origin.z + (smith.getRandom().nextDouble() * 2.0D - 1.0D) * 10.0D;
        double y = smith.level().getHeightmapPos(
                net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING,
                net.minecraft.core.BlockPos.containing(x, origin.y, z)).getY();
        strikeLightning(smith, server, x, y, z, false);
        for (int[] off : new int[][]{{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
            spawnGreenFire(smith, server, x + off[0], y, z + off[1]);
        }
        if (++smith.fieldStrikeCount % 2 == 0) {
            fieldSummonSkeleton(smith, server, x, y, z);
        }
    }

    public static void fieldDirectBolts(AbstractGreenSmith smith, ServerLevel server) {
        List<LivingEntity> candidates = new ArrayList<>();
        for (LivingEntity entity : server.getEntitiesOfClass(LivingEntity.class,
                smith.getBoundingBox().inflate(AbstractGreenSmith.FIELD_BOLT_RANGE))) {
            if (entity == smith || !entity.isAlive() || smith.isMyAlly(entity)
                    || smith.isAlliedTo(entity) || entity.isAlliedTo(smith)) {
                continue;
            }
            candidates.add(entity);
        }
        if (candidates.isEmpty()) {
            return;
        }
        candidates.sort((a, b) -> Double.compare(smith.distanceToSqr(a), smith.distanceToSqr(b)));
        int count = Math.min(AbstractGreenSmith.FIELD_BOLT_TARGETS, candidates.size());
        Vec3 from = smith.getEyePosition();
        for (int i = 0; i < count; i++) {
            LivingEntity victim = candidates.get(i);
            Vec3 to = new Vec3(victim.getX(), victim.getY() + victim.getBbHeight() * 0.5D, victim.getZ());
            ModNetwork.sendToALL(new SThunderBoltPacket(
                    from, to, new ColorUtil(AbstractGreenSmith.GREEN_LIGHTNING_COLOR), 4));
            victim.hurt(server.damageSources().indirectMagic(smith, smith),
                    AbstractGreenSmith.LIGHTNING_DAMAGE + smith.soulJarDamageBonus());
            server.sendParticles(GreenSmithFx.necroFire(), to.x, to.y, to.z,
                    10, 0.25D, 0.25D, 0.25D, 0.02D);
            server.sendParticles(ParticleTypes.ELECTRIC_SPARK, to.x, to.y, to.z,
                    8, 0.25D, 0.25D, 0.25D, 0.04D);
        }
        smith.playGoetySound("thunder_strike_fast", 1.0F, 1.3F);
    }

    public static void fieldSummonSkeleton(AbstractGreenSmith smith, ServerLevel server,
            double x, double y, double z) {
        if (smith.countServantsOf(server, "skeleton") >= AbstractGreenSmith.MAX_SKELETONS) {
            return;
        }
        ResourceLocation variant = AbstractGreenSmith.SKELETON_SERVANT_VARIANTS[
                smith.getRandom().nextInt(AbstractGreenSmith.SKELETON_SERVANT_VARIANTS.length)];
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(variant);
        if (type == null) {
            return;
        }
        Entity entity = type.create(server);
        if (!(entity instanceof com.Polarice3.Goety.common.entities.ally.Summoned minion)) {
            return;
        }
        minion.setTrueOwner(smith);
        minion.moveTo(x, y, z, smith.getYRot(), 0.0F);
        minion.finalizeSpawn(server, server.getCurrentDifficultyAt(minion.blockPosition()),
                net.minecraft.world.entity.MobSpawnType.MOB_SUMMONED, null, null);
        minion.setLimitedLife(AbstractGreenSmith.FIELD_DURATION);
        minion.setPersistenceRequired();
        AbstractGreenSmith.markGreenSmithNecromancer(minion, smith);
        smith.applySoulJarSummonBuffs(minion);
        if (server.addFreshEntity(minion)) {
            smith.necromancers.add(minion.getUUID());
            GoetySummonFx.play(server, minion);
            server.sendParticles(GreenSmithFx.necroFire(), x, y + 0.3D, z,
                    20, 0.35D, 0.5D, 0.35D, 0.02D);
        }
    }
}
