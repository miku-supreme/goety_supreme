package cn.blockforge.generated.goetysupreme.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.config.GiantHammerFocusConfig;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.spell.GiantHammerFocusSpell;
import cn.blockforge.generated.goetysupreme.util.GiantHammerWaveDamage;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class GiantHammerWaveDamage {

    private static final ResourceKey<DamageType> PLAYER_SEISMIC_WAVE =
            ResourceKey.create(Registries.DAMAGE_TYPE,
                    ResourceLocation.fromNamespaceAndPath("mutantmonsters", "player_seismic_wave"));

    private static final Map<UUID, PotencyMark> POTENCY_MARKS = new HashMap<>();

    private GiantHammerWaveDamage() {
    }

    public static void armPotency(LivingEntity caster, int potency, int durationTicks) {
        if (caster == null) {
            return;
        }
        UUID id = caster.getUUID();
        if (potency <= 0) {
            POTENCY_MARKS.remove(id);
            return;
        }
        long expireTick = caster.level().getGameTime() + Math.max(1, durationTicks);
        POTENCY_MARKS.put(id, new PotencyMark(potency, expireTick));
    }

    private static int activePotency(Entity source, long gameTime) {
        if (!(source instanceof LivingEntity living)) {
            return 0;
        }
        UUID id = living.getUUID();
        PotencyMark mark = POTENCY_MARKS.get(id);
        if (mark == null) {
            return 0;
        }
        if (mark.expireTick < gameTime) {
            POTENCY_MARKS.remove(id);
            return 0;
        }
        return mark.potency;
    }

    @SubscribeEvent
    public static void onSeismicWaveHit(LivingHurtEvent event) {
        if (event.getAmount() <= 0.0F || !event.getSource().is(PLAYER_SEISMIC_WAVE)) {
            return;
        }
        double base = GiantHammerFocusConfig.WAVE_DAMAGE.get().doubleValue();
        double variance = Math.max(0.0D, GiantHammerFocusConfig.WAVE_DAMAGE_VARIANCE.get().doubleValue());
        int potency = activePotency(event.getSource().getEntity(),
                event.getEntity().level().getGameTime());
        if (potency > 0) {
            base += potency * GiantHammerFocusConfig.POTENCY_DAMAGE_BONUS.get().doubleValue();
        }
        float amount = (float) (base + (variance > 0.0D
                ? event.getEntity().getRandom().nextDouble() * variance : 0.0D));
        event.setAmount(Math.max(0.0F, amount));
    }

    private static final class PotencyMark {
        private final int potency;
        private final long expireTick;

        private PotencyMark(int potency, long expireTick) {
            this.potency = potency;
            this.expireTick = expireTick;
        }
    }
}
