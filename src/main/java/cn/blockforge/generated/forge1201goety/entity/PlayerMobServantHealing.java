package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.config.MobsConfig;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.MathHelper;
import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

final class PlayerMobServantHealing {

    private static final TagKey<EntityType<?>> NO_HEAL_SERVANTS =
            TagKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath("goety", "no_heal_servants"));

    private final PlayerMobServant mob;

    PlayerMobServantHealing(PlayerMobServant mob) {
        this.mob = mob;
    }

    void healServant() {
        LivingEntity owner = this.mob.getTrueOwner();
        if (owner == null) {
            return;
        }
        if (Boolean.TRUE.equals(MobsConfig.ServantOwnedServantPlayerBenefit.get())) {
            owner = this.mob.getMasterOwner();
        }
        if (!(owner instanceof Player player) || player.isSpectator()) {
            return;
        }

        if (this.hasAnyCrown(owner)) {
            this.mob.setHasLifespan(false);
        } else if (this.mob.getLifespan() > 0) {
            this.mob.setHasLifespan(true);
        }

        if (this.mob.level().isClientSide || this.mob.isDeadOrDying()
                || this.mob.getHealth() >= this.mob.getMaxHealth()
                || !this.hasAnyRobe(owner) || this.mob.getType().is(NO_HEAL_SERVANTS)) {
            return;
        }

        PlayerMobHealingProfile profile = this.healingProfile(owner);
        if (profile == null || !profile.enabled()) {
            return;
        }
        int intervalSeconds = Math.max(0, profile.intervalSeconds());
        int intervalTicks = MathHelper.secondsToTicks(intervalSeconds);
        if (this.mob.tickCount % (intervalTicks + 1) != 0
                || !SEHelper.getSoulsAmount(player, profile.soulCost())) {
            return;
        }

        this.mob.heal(profile.amount());
        SEHelper.decreaseSouls(player, profile.soulCost());
    }

    private boolean hasAnyCrown(LivingEntity owner) {
        return CuriosFinder.hasFrostCrown(owner)
                || CuriosFinder.hasWindCrown(owner)
                || CuriosFinder.hasStormCrown(owner)
                || CuriosFinder.hasWildCrown(owner)
                || CuriosFinder.hasAbyssCrown(owner)
                || CuriosFinder.hasVoidCrown(owner)
                || CuriosFinder.hasNetherCrown(owner)
                || CuriosFinder.hasUndeadCrown(owner)
                || CuriosFinder.hasNamelessCrown(owner)
                || CuriosFinder.hasUnholyHat(owner)
                || CuriosFinder.hasAmethystNecklace(owner);
    }

    private boolean hasAnyRobe(LivingEntity owner) {
        return CuriosFinder.hasDarkRobe(owner)
                || CuriosFinder.hasIllusionRobe(owner)
                || CuriosFinder.hasWitchRobe(owner)
                || CuriosFinder.hasWarlockRobe(owner)
                || CuriosFinder.hasWildRobe(owner)
                || CuriosFinder.hasGeoRobe(owner)
                || CuriosFinder.hasVoidRobe(owner)
                || CuriosFinder.hasNetherRobe(owner)
                || CuriosFinder.hasUnholyRobe(owner)
                || CuriosFinder.hasNamelessCape(owner)
                || CuriosFinder.hasUndeadCape(owner)
                || CuriosFinder.hasFrostRobes(owner)
                || CuriosFinder.hasWindyRobes(owner)
                || CuriosFinder.hasWindRobes(owner)
                || CuriosFinder.hasStormRobes(owner)
                || CuriosFinder.hasAbyssRobes(owner);
    }

    private PlayerMobHealingProfile healingProfile(LivingEntity owner) {
        if (CuriosFinder.hasAbyssRobes(owner)) {
            return new PlayerMobHealingProfile(Boolean.TRUE.equals(MobsConfig.WaterMinionHeal.get()),
                    MobsConfig.WaterMinionHealCost.get(), MobsConfig.WaterMinionHealTime.get(),
                    MobsConfig.WaterMinionHealAmount.get().floatValue());
        }
        if (CuriosFinder.hasWildRobe(owner)) {
            return new PlayerMobHealingProfile(Boolean.TRUE.equals(MobsConfig.NaturalMinionHeal.get()),
                    MobsConfig.NaturalMinionHealCost.get(), MobsConfig.NaturalMinionHealTime.get(),
                    MobsConfig.NaturalMinionHealAmount.get().floatValue());
        }
        if (CuriosFinder.hasGeoRobe(owner)) {
            return new PlayerMobHealingProfile(Boolean.TRUE.equals(MobsConfig.GeoMinionHeal.get()),
                    MobsConfig.GeoMinionHealCost.get(), MobsConfig.GeoMinionHealTime.get(),
                    MobsConfig.GeoMinionHealAmount.get().floatValue());
        }
        if (CuriosFinder.hasFrostRobes(owner)) {
            return new PlayerMobHealingProfile(Boolean.TRUE.equals(MobsConfig.FrostMinionHeal.get()),
                    MobsConfig.FrostMinionHealCost.get(), MobsConfig.FrostMinionHealTime.get(),
                    MobsConfig.FrostMinionHealAmount.get().floatValue());
        }
        if (CuriosFinder.hasWindyRobes(owner) || CuriosFinder.hasWindRobes(owner)) {
            return new PlayerMobHealingProfile(Boolean.TRUE.equals(MobsConfig.WindMinionHeal.get()),
                    MobsConfig.WindMinionHealCost.get(), MobsConfig.WindMinionHealTime.get(),
                    MobsConfig.WindMinionHealAmount.get().floatValue());
        }
        if (CuriosFinder.hasStormRobes(owner)) {
            return new PlayerMobHealingProfile(Boolean.TRUE.equals(MobsConfig.StormMinionHeal.get()),
                    MobsConfig.StormMinionHealCost.get(), MobsConfig.StormMinionHealTime.get(),
                    MobsConfig.StormMinionHealAmount.get().floatValue());
        }
        if (CuriosFinder.hasNetherRobe(owner) || CuriosFinder.hasUnholyRobe(owner)) {
            return new PlayerMobHealingProfile(Boolean.TRUE.equals(MobsConfig.NetherMinionHeal.get()),
                    MobsConfig.NetherMinionHealCost.get(), MobsConfig.NetherMinionHealTime.get(),
                    MobsConfig.NetherMinionHealAmount.get().floatValue());
        }
        if (CuriosFinder.hasVoidRobe(owner)) {
            return new PlayerMobHealingProfile(Boolean.TRUE.equals(MobsConfig.VoidMinionHeal.get()),
                    MobsConfig.VoidMinionHealCost.get(), MobsConfig.VoidMinionHealTime.get(),
                    MobsConfig.VoidMinionHealAmount.get().floatValue());
        }
        return new PlayerMobHealingProfile(Boolean.TRUE.equals(MobsConfig.UndeadMinionHeal.get()),
                MobsConfig.UndeadMinionHealCost.get(), MobsConfig.UndeadMinionHealTime.get(),
                MobsConfig.UndeadMinionHealAmount.get().floatValue());
    }
}
