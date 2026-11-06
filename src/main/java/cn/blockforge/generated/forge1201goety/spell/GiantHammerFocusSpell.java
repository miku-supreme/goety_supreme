package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.utils.WandUtil;
import fuzs.mutantmonsters.core.SeismicWave;
import fuzs.mutantmonsters.init.ModRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.GiantHammerFocusConfig;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.spell.GiantHammerFocusSpell;
import cn.blockforge.generated.goetysupreme.util.GiantHammerWaveDamage;

public final class GiantHammerFocusSpell extends Spell {

    private static final double WAVE_START_OFFSET = 1.5D;

    private static final int POTENCY_GRACE_TICKS = 20;

    @Override
    public int defaultSoulCost() {
        return GiantHammerFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return 0;
    }

    @Override
    public int defaultSpellCooldown() {
        return GiantHammerFocusConfig.COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.GEOMANCY;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        return List.of(ModEnchantments.POTENCY.get());
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        level.playSound(null, caster.blockPosition(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.BLOCKS, 0.8F, caster.getRandom().nextFloat() * 0.4F + 0.8F);

        Vec3 flat = horizontalLook(caster);
        double range = GiantHammerFocusConfig.RANGE.get().doubleValue();
        int waveTicks;
        if (isGeoStaff(wandStack)) {
            double angle = Math.toRadians(GiantHammerFocusConfig.SPLIT_ANGLE.get());
            List<SeismicWave> left = buildSeismicWave(level, caster, rotateY(flat, angle), range);
            List<SeismicWave> right = buildSeismicWave(level, caster, rotateY(flat, -angle), range);
            int firstDelay = enqueueSeismicWaves(caster, left, 0);
            enqueueSeismicWaves(caster, right, firstDelay);
            waveTicks = firstDelay + right.size();
        } else {
            List<SeismicWave> line = buildSeismicWave(level, caster, flat, range);
            enqueueSeismicWaves(caster, line, 0);
            waveTicks = line.size();
        }
        GiantHammerWaveDamage.armPotency(caster, potencyLevel(caster, stats), waveTicks + POTENCY_GRACE_TICKS);
    }

    private static int potencyLevel(LivingEntity caster, SpellStat stats) {
        int attribute = stats == null ? 0 : Math.max(0, stats.getPotency());
        return attribute + Math.max(0, WandUtil.getPotencyLevel(caster));
    }

    private static Vec3 horizontalLook(LivingEntity caster) {
        Vec3 look = caster.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0D, look.z);
        if (flat.lengthSqr() < 1.0E-6D) {
            float yawRad = caster.getYRot() * Mth.DEG_TO_RAD;
            return new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
        }
        return flat.normalize();
    }

    private static Vec3 rotateY(Vec3 dir, double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        return new Vec3(dir.x * cos - dir.z * sin, 0.0D, dir.x * sin + dir.z * cos).normalize();
    }

    private static List<SeismicWave> buildSeismicWave(ServerLevel level, LivingEntity caster, Vec3 dir, double range) {
        Vec3 pos = caster.position();
        int fromX = Mth.floor(pos.x + dir.x * WAVE_START_OFFSET);
        int fromZ = Mth.floor(pos.z + dir.z * WAVE_START_OFFSET);
        int y = Mth.floor(caster.getBoundingBox().minY);
        int toX = Mth.floor(pos.x + dir.x * range);
        int toZ = Mth.floor(pos.z + dir.z * range);
        List<SeismicWave> waves = new ArrayList<>();
        SeismicWave.createWaves(level, waves, fromX, fromZ, toX, toZ, y);
        return waves;
    }

    private static int enqueueSeismicWaves(LivingEntity caster, List<SeismicWave> waves, int delay) {
        if (waves.isEmpty()) {
            return 0;
        }
        if (delay <= 0) {
            pushToQueue(caster, waves);
        } else {
            PENDING_SINK.add(new PendingWave(caster, waves, delay));
        }
        return waves.size();
    }

    private static void pushToQueue(LivingEntity caster, List<SeismicWave> waves) {
        if (caster.isRemoved()) {
            return;
        }
        ModRegistry.SEISMIC_WAVES_CAPABILITY.maybeGet(caster)
                .ifPresent(component -> component.seismicWaves().addAll(waves));
    }

    private static final java.util.ArrayDeque<PendingWave> PENDING_SINK = new java.util.ArrayDeque<>();

    private static final class PendingWave {
        private final LivingEntity caster;
        private final List<SeismicWave> waves;
        private int ticksLeft;

        private PendingWave(LivingEntity caster, List<SeismicWave> waves, int ticksLeft) {
            this.caster = caster;
            this.waves = waves;
            this.ticksLeft = ticksLeft;
        }
    }

    @net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
    public static final class WaveQueueSink {
        private WaveQueueSink() {
        }

        @net.minecraftforge.eventbus.api.SubscribeEvent
        public static void onServerTickEnd(net.minecraftforge.event.TickEvent.ServerTickEvent event) {
            if (event.phase != net.minecraftforge.event.TickEvent.Phase.END || PENDING_SINK.isEmpty()) {
                return;
            }
            for (java.util.Iterator<PendingWave> it = PENDING_SINK.iterator(); it.hasNext(); ) {
                PendingWave pending = it.next();
                if (--pending.ticksLeft <= 0 || pending.caster.isRemoved()) {
                    pushToQueue(pending.caster, pending.waves);
                    it.remove();
                }
            }
        }
    }

    private boolean isGeoStaff(ItemStack wandStack) {
        return !wandStack.isEmpty() && wandStack.is(ModItems.GEO_STAFF.get());
    }
}
