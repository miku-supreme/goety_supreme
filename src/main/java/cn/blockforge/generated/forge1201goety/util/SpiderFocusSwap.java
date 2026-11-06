package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderSpiderServant;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.util.SpiderFocusSwap;

public final class SpiderFocusSwap {

    private static final ResourceLocation PLAIN_SPIDER_SERVANT = ResourceLocation.fromNamespaceAndPath("goety", "spider_servant");

    private SpiderFocusSwap() {
    }

    public static boolean addOrSwap(ServerLevel level, Entity entity) {
        if (!(entity instanceof LivingEntity living) || !isPlainSpiderServant(living)) {
            return level.addFreshEntity(entity);
        }
        if (!JerotesVillageCompat.isJerotesVillageLoaded()) {
            return level.addFreshEntity(entity);
        }
        EntityType<SecondRounderSpiderServant> type = ModEntityTypes.SECOND_ROUNDER_SPIDER_SERVANT.get();
        if (type == null) {
            return level.addFreshEntity(entity);
        }
        LivingEntity owner = living instanceof IOwned owned ? owned.getTrueOwner() : null;
        if (owner != null
                && ServantLimits.countOwned(level, owner.getUUID(), t -> t == type) >= ServantLimits.limitFor(type)) {
            return level.addFreshEntity(entity);
        }
        SecondRounderSpiderServant replacement = type.create(level);
        if (replacement == null) {
            return level.addFreshEntity(entity);
        }
        copyState(level, living, replacement);
        living.discard();
        return level.addFreshEntity(replacement);
    }

    private static boolean isPlainSpiderServant(Entity entity) {
        return PLAIN_SPIDER_SERVANT.equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()));
    }

    private static void copyState(ServerLevel level, LivingEntity from, SecondRounderSpiderServant to) {
        to.moveTo(from.getX(), from.getY(), from.getZ(), from.getYRot(), from.getXRot());
        to.yBodyRot = from.yBodyRot;
        to.setYHeadRot(from.getYHeadRot());
        to.setDeltaMovement(from.getDeltaMovement());
        if (from instanceof IOwned source && source.getTrueOwner() != null) {
            to.setTrueOwner(source.getTrueOwner());
        }
        to.setPersistenceRequired();
        to.finalizeSpawn(level, level.getCurrentDifficultyAt(from.blockPosition()),
                MobSpawnType.MOB_SUMMONED, null, null);
        if (from instanceof IOwned source) {
            if (source.getTrueOwner() != null) {
                to.setTrueOwner(source.getTrueOwner());
            }
            if (source.isLimitedLife()) {
                to.setLimitedLife(source.getLifespan());
            }
        }
        to.setHealth(Math.min(to.getMaxHealth(), from.getHealth()));
        if (from instanceof Mob mob && mob.getTarget() != null) {
            to.setTarget(mob.getTarget());
        }
        for (MobEffectInstance effect : from.getActiveEffects()) {
            to.addEffect(new MobEffectInstance(effect));
        }
    }
}
