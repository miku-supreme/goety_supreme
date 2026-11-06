package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.util.MutantEndermanLandingStrike;
import cn.blockforge.generated.goetysupreme.config.MutantEndermanServantConfig;
import cn.blockforge.generated.goetysupreme.util.MutantEndermanTeleSmashArea;
import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.init.ModRegistry;
import fuzs.mutantmonsters.world.entity.mutant.MutantEnderman;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;

@Mixin(value = MutantEnderman.class, remap = false)
public abstract class MutantEndermanTeleSmashMixin {

    private static final int GOETY_SUPREME_TELE_SMASH_IMPACT_TICK = 18;

    private static final ResourceLocation GOETY_SUPREME_ENDERSOUL_CLONE =
            ResourceLocation.fromNamespaceAndPath("mutantmonsters", "endersoul_clone");
    private static final ResourceLocation GOETY_SUPREME_MUTANT_ENDERMAN =
            ResourceLocation.fromNamespaceAndPath("mutantmonsters", "mutant_enderman");

    @Inject(method = "m_8107_", at = @At("HEAD"), require = 0)
    private void goetySupreme$frontSectorTeleSmash(CallbackInfo callback) {
        MutantEnderman self = (MutantEnderman) (Object) this;
        if (self.level().isClientSide() || self.isClone()) {
            return;
        }
        if (self.getAnimation() != MutantEnderman.TELESMASH_ANIMATION
                || self.getAnimationTick() != GOETY_SUPREME_TELE_SMASH_IMPACT_TICK) {
            return;
        }
        LivingEntity primary = self.getTarget();
        if (primary == null || !(self.level() instanceof ServerLevel server)) {
            return;
        }
        DamageSource damageSource = ModDamageSource.ownedDamageSource(self.level(),
                ModRegistry.PIERCING_MOB_ATTACK_DAMAGE_TYPE, self, self);
        float damage = MutantEndermanServantConfig.TELESMASH_DAMAGE.get().floatValue();
        int landingDamage = MutantEndermanServantConfig.TELESMASH_LANDING_DAMAGE.get();
        for (LivingEntity victim : MutantEndermanTeleSmashArea.victims(self, primary, this::goetySupreme$canHarm)) {
            if (victim == primary) {
                continue;
            }
            MutantEndermanTeleSmashArea.lift(server, victim, self.getRandom());
            if (victim.hurt(damageSource, damage)) {
                MutantEndermanLandingStrike.mark(victim, landingDamage);
            }
        }
    }

    private boolean goetySupreme$canHarm(LivingEntity victim) {
        MutantEnderman self = (MutantEnderman) (Object) this;
        if (victim == self || !victim.isAlive() || victim.isSpectator()) {
            return false;
        }
        if (victim.getType() == EntityType.ENDERMAN) {
            return false;
        }
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(victim.getType());
        if (GOETY_SUPREME_MUTANT_ENDERMAN.equals(id) || GOETY_SUPREME_ENDERSOUL_CLONE.equals(id)) {
            return false;
        }
        return !self.isAlliedTo(victim) && !victim.isAlliedTo(self);
    }
}
