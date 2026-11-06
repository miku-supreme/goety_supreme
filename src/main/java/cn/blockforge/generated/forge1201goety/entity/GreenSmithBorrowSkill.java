package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ModEntityType;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.common.magic.spells.wild.BlossomSpell;
import com.Polarice3.Goety.common.magic.SpellStat;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import com.Polarice3.Goety.common.entities.projectiles.HauntedSkullProjectile;
import com.Polarice3.Goety.common.entities.util.TridentStorm;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import javax.annotation.Nullable;

/** 绿豆阴司借力（长矛风暴/荆棘球/飞镰/缠魂飞颅），从 AbstractGreenSmith 抽出的施法逻辑。 */
public final class GreenSmithBorrowSkill {

    private GreenSmithBorrowSkill() {
    }

    public static void castBorrowedSkill(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        LivingEntity target = smith.getTarget();
        switch (smith.borrowedSkill) {
            case 0 -> borrowTridentStorm(smith, server, target);
            case 1 -> borrowThornBall(smith, server, target);
            case 2 -> borrowSpinScythe(smith, server, target);
            default -> borrowHauntedSkulls(smith, server, target);
        }
    }

    private static float effectiveCastElapsed(AbstractGreenSmith smith) {
        if (smith.level() != null && smith.level().isClientSide) {
            int anim = smith.getCastAnimation();
            if (anim == AbstractGreenSmith.CAST_NONE) {
                return 0.0F;
            }
            return Math.max(0.0F, AbstractGreenSmith.CAST_LENGTHS[anim] - smith.getCastAnimationTick());
        }
        return smith.castElapsed;
    }

    public static Vec3 chargeStaffTip(AbstractGreenSmith smith) {
        double seconds = effectiveCastElapsed(smith) / 20.0D;
        double[] from = AbstractGreenSmith.CHARGE_STAFF_TIP[0];
        double[] to = AbstractGreenSmith.CHARGE_STAFF_TIP[AbstractGreenSmith.CHARGE_STAFF_TIP.length - 1];
        for (int i = 0; i < AbstractGreenSmith.CHARGE_STAFF_TIP.length - 1; i++) {
            if (seconds >= AbstractGreenSmith.CHARGE_STAFF_TIP[i][0] && seconds <= AbstractGreenSmith.CHARGE_STAFF_TIP[i + 1][0]) {
                from = AbstractGreenSmith.CHARGE_STAFF_TIP[i];
                to = AbstractGreenSmith.CHARGE_STAFF_TIP[i + 1];
                break;
            }
        }
        double span = to[0] - from[0];
        double f = span <= 1.0E-6D ? 0.0D : Mth.clamp((seconds - from[0]) / span, 0.0D, 1.0D);
        double forward = Mth.lerp(f, from[1], to[1]);
        double lateral = Mth.lerp(f, from[2], to[2]);
        double height = Mth.lerp(f, from[3], to[3]);
        double yaw = Math.toRadians(smith.yBodyRot);
        double cos = Math.cos(yaw);
        double sin = Math.sin(yaw);
        return new Vec3(smith.getX() - sin * forward + cos * lateral,
                smith.getY() + height,
                smith.getZ() + cos * forward + sin * lateral);
    }

    public static void tickBorrowCharge(AbstractGreenSmith smith) {
        if (!(smith.level() instanceof ServerLevel server)) {
            return;
        }
        if (smith.castElapsed == 1) {
            smith.playGoetySound("prepare_spell", 1.2F, 0.75F);
        }
        Vec3 tip = chargeStaffTip(smith);
        int count = 4 + smith.castElapsed / 3;
        ParticleOptions primary;
        ParticleOptions secondary;
        switch (smith.borrowedSkill) {
            case 0 -> {
                primary = ParticleTypes.BUBBLE;
                secondary = ParticleTypes.ELECTRIC_SPARK;
            }
            case 1 -> {
                primary = ParticleTypes.HAPPY_VILLAGER;
                secondary = ParticleTypes.SNEEZE;
            }
            case 2 -> {
                primary = ParticleTypes.SQUID_INK;
                secondary = ParticleTypes.SOUL;
            }
            default -> {
                primary = ParticleTypes.WHITE_ASH;
                secondary = ParticleTypes.END_ROD;
            }
        }
        server.sendParticles(primary, tip.x, tip.y, tip.z,
                count, 0.16D, 0.16D, 0.16D, 0.02D);
        server.sendParticles(secondary, tip.x, tip.y, tip.z,
                Math.max(2, count / 2), 0.18D, 0.18D, 0.18D, 0.02D);
    }

    public static void borrowTridentStorm(AbstractGreenSmith smith, ServerLevel server, @Nullable LivingEntity target) {
        LivingEntity anchor = target != null && target.isAlive() ? target : smith.getTarget();
        if (anchor == null) {
            resetTridentBurst(smith);
            return;
        }
        smith.tridentTarget = anchor.getUUID();
        smith.tridentBurstRemaining = AbstractGreenSmith.BORROW_TRIDENT_COUNT;
        smith.tridentBurstTimer = 0; // 第一根下一刻就放，之后每 AbstractGreenSmith.TRIDENT_BURST_INTERVAL 刻一根。
        Vec3 center = anchor.position();
        server.sendParticles(ParticleTypes.BUBBLE, center.x, center.y + 1.0D, center.z,
                50, 3.0D, 0.8D, 3.0D, 0.04D);
        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, center.x, center.y + 1.0D, center.z,
                40, 3.0D, 0.8D, 3.0D, 0.08D);
        smith.playGoetySound("thunderbolt", 1.3F, 0.85F);
    }

    static void tickTridentBurst(AbstractGreenSmith smith) {
        if (smith.tridentBurstRemaining <= 0 || !(smith.level() instanceof ServerLevel server)) {
            return;
        }
        if (--smith.tridentBurstTimer > 0) {
            return;
        }
        smith.tridentBurstTimer = AbstractGreenSmith.TRIDENT_BURST_INTERVAL;
        LivingEntity target = smith.tridentTarget != null
                && server.getEntity(smith.tridentTarget) instanceof LivingEntity living && living.isAlive()
                ? living : smith.getTarget();
        if (target == null) {
            resetTridentBurst(smith);
            return;
        }
        smith.tridentTarget = target.getUUID();
        Vec3 center = target.position();
        TridentStorm trident = new TridentStorm(ModEntityType.TRIDENT_STORM.get(), server);
        trident.setOwner(smith);
        trident.setPos(center.x, center.y + 0.5D, center.z);
        trident.setWarmUp(AbstractGreenSmith.TRIDENT_STORM_WARMUP);
        trident.setExtraDamage(smith.soulJarDamageBonus());
        trident.delay = 0; // 去掉随机的 delay，让每根都在 warmUp 后精准爆炸，保持 0.25 秒间隔。
        MobUtil.moveDownToGround(trident);
        server.addFreshEntity(trident);
        server.sendParticles(ParticleTypes.BUBBLE, center.x, center.y + 1.0D, center.z,
                30, 0.8D, 0.5D, 0.8D, 0.04D);
        server.sendParticles(ParticleTypes.ELECTRIC_SPARK, center.x, center.y + 1.0D, center.z,
                20, 0.8D, 0.5D, 0.8D, 0.06D);
        if (--smith.tridentBurstRemaining <= 0) {
            smith.tridentTarget = null;
        }
    }

    static void resetTridentBurst(AbstractGreenSmith smith) {
        smith.tridentBurstRemaining = 0;
        smith.tridentBurstTimer = 0;
        smith.tridentTarget = null;
    }

    public static void borrowThornBall(AbstractGreenSmith smith, ServerLevel server, @Nullable LivingEntity target) {
        if (target != null && target.isAlive()) {
            smith.getLookControl().setLookAt(target, 360.0F, 360.0F);
            smith.lookAt(target, 360.0F, 360.0F);
        }
        float baseYaw = smith.getYRot();
        float basePitch = smith.getXRot();
        BlossomSpell blossom = new BlossomSpell();
        SpellStat thornStats = blossom.defaultStats();
        thornStats.increaseVelocity(thornStats.getVelocity()
                * smith.effectiveSoulJarUpgrades() * AbstractGreenSmith.SOUL_JAR_PROJECTILE_SPEED_PER_LEVEL);
        int thornLevels = smith.effectiveSoulJarUpgrades();
        thornStats.increasePotency(thornLevels * AbstractGreenSmith.SOUL_JAR_THORN_DAMAGE_PER_LEVEL);
        thornStats.increaseRadius(thornLevels * AbstractGreenSmith.SOUL_JAR_THORN_RADIUS_PER_LEVEL);
        for (int i = 0; i < AbstractGreenSmith.BORROW_THORN_BALL_COUNT; i++) {
            double spread = (i - (AbstractGreenSmith.BORROW_THORN_BALL_COUNT - 1) / 2.0D) * AbstractGreenSmith.BORROW_THORN_BALL_SPREAD;
            smith.setYRot(baseYaw + (float) Math.toDegrees(spread));
            smith.setXRot(basePitch);
            smith.yBodyRot = smith.getYRot();
            smith.yHeadRot = smith.getYRot();
            blossom.SpellResult(server, smith, ItemStack.EMPTY, thornStats);
        }
        smith.setYRot(baseYaw);
        smith.setXRot(basePitch);
        smith.yBodyRot = baseYaw;
        smith.yHeadRot = baseYaw;
        server.sendParticles(ParticleTypes.HAPPY_VILLAGER, smith.getX(), smith.getEyeY(), smith.getZ(),
                30, 0.6D, 0.5D, 0.6D, 0.04D);
        server.sendParticles(ParticleTypes.SNEEZE, smith.getX(), smith.getEyeY(), smith.getZ(),
                20, 0.6D, 0.5D, 0.6D, 0.04D);
        smith.playGoetySound("poison_quill_vine_burst", 1.2F, 1.0F);
    }

    public static void borrowSpinScythe(AbstractGreenSmith smith, ServerLevel server, @Nullable LivingEntity target) {
        Vec3 aim = target != null
                ? new Vec3(target.getX(), target.getY() + target.getBbHeight() * 0.5D, target.getZ())
                : smith.position().add(smith.getLookAngle().scale(4.0D));
        Vec3 dir = aim.subtract(smith.getEyePosition());
        if (dir.lengthSqr() < 1.0E-4D) {
            dir = smith.getLookAngle();
        }
        dir = dir.normalize();
        float scytheScale = smith.soulJarProjectileSpeedScale();
        for (int i = 0; i < AbstractGreenSmith.BORROW_SCYTHE_COUNT; i++) {
            double spread = (i - (AbstractGreenSmith.BORROW_SCYTHE_COUNT - 1) / 2.0D) * 0.22D;
            double dx = dir.x * Math.cos(spread) - dir.z * Math.sin(spread);
            double dz = dir.x * Math.sin(spread) + dir.z * Math.cos(spread);
            Vec3 flight = new Vec3(dx, dir.y, dz).normalize();
            ReaperSpinEntity spin = new ReaperSpinEntity(server, smith);
            spin.setPos(smith.getX() + dx * 1.2D, smith.getEyeY() - 0.2D + dir.y * 1.2D,
                    smith.getZ() + dz * 1.2D);
            spin.setSpeed(AbstractReaperNecromancer.SPIN_SPEED * scytheScale);
            spin.setDeltaMovement(flight.scale(AbstractReaperNecromancer.SPIN_SPEED * scytheScale));
            spin.setWidthScale(scytheScale);
            spin.setDamage(AbstractReaperNecromancer.SPIN_DAMAGE + smith.soulJarDamageBonus());
            server.addFreshEntity(spin);
            server.sendParticles(ParticleTypes.SQUID_INK, spin.getX(), spin.getY(), spin.getZ(),
                    10, 0.3D, 0.3D, 0.3D, 0.02D);
            server.sendParticles(ParticleTypes.SOUL, spin.getX(), spin.getY(), spin.getZ(),
                    6, 0.3D, 0.3D, 0.3D, 0.02D);
        }
        smith.playGoetySound("wither_shoot", 1.1F, 0.85F);
    }

    public static void borrowHauntedSkulls(AbstractGreenSmith smith, ServerLevel server, @Nullable LivingEntity target) {
        LivingEntity anchor = target != null && target.isAlive() ? target : smith.getTarget();
        if (anchor == null) {
            smith.resetSkullBurst();
            return;
        }
        smith.skullTarget = anchor.getUUID();
        smith.skullBurstRemaining = AbstractGreenSmith.SKULL_BURST_BATCHES;
        smith.skullBurstTimer = 0; // 第一批下一刻就放，之后每 AbstractGreenSmith.SKULL_BURST_INTERVAL 刻一批。
        server.sendParticles(ParticleTypes.WHITE_ASH, smith.getX(), smith.getEyeY() + 0.5D, smith.getZ(),
                30, 0.6D, 0.5D, 0.6D, 0.03D);
        smith.playGoetySound("skull_lord_shoot", 1.3F, 1.0F);
    }

}
