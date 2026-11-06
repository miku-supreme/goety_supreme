package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.config.MutantZombieServantConfig;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;
import com.Polarice3.Goety.api.entities.ICustomAttributes;
import com.Polarice3.Goety.api.entities.IOwned;
import cn.blockforge.generated.goetysupreme.util.MutantInternals;
import cn.blockforge.generated.goetysupreme.util.ZombieServantVariants;
import fuzs.mutantmonsters.core.ZombieResurrection;
import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ZombieResurrection.class, remap = false)
public abstract class ZombieResurrectionMixin extends BlockPos {
    private ZombieResurrectionMixin() {
        super(0, 0, 0);
    }

    @Inject(method = "update", at = @At("HEAD"), cancellable = true, require = 0)
    private void goetySupreme$summonGoetyZombieServant(MutantZombie summoner,
            CallbackInfoReturnable<Boolean> callback) {
        if (!MutantZombieServantConfig.WILD_ROAR_SUMMON_SERVANTS.get()) {
            return;
        }
        Level level = summoner.level();
        BlockPos ground = this;
        BlockPos above = this.above();
        if (level.isEmptyBlock(ground) || !level.isEmptyBlock(above)) {
            callback.setReturnValue(false);
            return;
        }
        if (level instanceof ServerLevel pendingServer && summoner.getRandom().nextInt(15) == 0) {
            pendingServer.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, level.getBlockState(ground)),
                    above.getX() + 0.5D, above.getY() + 0.5D, above.getZ() + 0.5D,
                    3, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        int remainingTicks = MutantInternals.decrementResurrectionTick(this);
        if (remainingTicks == MutantInternals.TICK_UNAVAILABLE) {
            return;
        }
        if (remainingTicks > 0) {
            callback.setReturnValue(true);
            return;
        }
        callback.setReturnValue(false);
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        Mob reinforcement = ZombieServantVariants.createFor(serverLevel, above);
        if (reinforcement == null) {
            return;
        }
        reinforcement.moveTo(above.getX() + 0.5D, above.getY(), above.getZ() + 0.5D, summoner.getYRot(), 0.0F);
        if (reinforcement instanceof ICustomAttributes configurable) {
            configurable.setConfigurableAttributes();
        }
        reinforcement.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(above),
                MobSpawnType.MOB_SUMMONED, null, null);
        reinforcement.setHealth(reinforcement.getMaxHealth());
        if (reinforcement instanceof IOwned owned) {
            owned.setNatural(true);
            owned.setTrueOwner(summoner);
            owned.setHostile(true);
        }
        reinforcement.playAmbientSound();
        serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, level.getBlockState(ground)),
                above.getX() + 0.5D, above.getY() + 0.5D, above.getZ() + 0.5D, 12, 0.1D, 0.05D, 0.1D, 0.1D);
        serverLevel.sendParticles(ParticleTypes.POOF,
                above.getX() + 0.5D, above.getY() + 0.5D, above.getZ() + 0.5D, 8, 0.15D, 0.1D, 0.15D, 0.02D);
        LivingEntity summonTarget = summoner.getTarget();
        if (summonTarget != null && summonTarget.isAlive()) {
            reinforcement.setTarget(summonTarget);
        }
        serverLevel.addFreshEntity(reinforcement);
        GoetySummonFx.play(serverLevel, reinforcement);
        Team team = summoner.getTeam();
        if (team != null) {
            net.minecraft.world.scores.PlayerTeam playerTeam =
                    serverLevel.getScoreboard().getPlayersTeam(team.getName());
            if (playerTeam != null) {
                serverLevel.getScoreboard().addPlayerToTeam(reinforcement.getScoreboardName(), playerTeam);
            }
        }
    }
}
