package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.utils.ServantUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.event.ForgeEventFactory;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SecondRounderZombieServant;
import cn.blockforge.generated.goetysupreme.util.ZombieServantConversion;

public final class ZombieServantConversion {

    private static final ResourceLocation SECOND_ROUNDER_ZOMBIE =
            ResourceLocation.fromNamespaceAndPath("jerotesvillage", "second_rounder_zombie");

    private ZombieServantConversion() {
    }

    public static void convert(ServerLevel level, LivingEntity victim, LivingEntity owner) {
        if (isSecondRounderZombie(victim)) {
            convertSecondRounder(level, victim, owner);
            return;
        }
        ServantUtil.convertZombies(victim, owner, false);
    }

    private static void convertSecondRounder(ServerLevel level, LivingEntity victim, LivingEntity owner) {
        if (!JerotesVillageCompat.isJerotesVillageLoaded()) {
            return;
        }
        if (!(victim instanceof Mob mob)) {
            return;
        }
        EntityType<SecondRounderZombieServant> type = ModEntityTypes.SECOND_ROUNDER_ZOMBIE_SERVANT.get();
        if (type == null || !ForgeEventFactory.canLivingConvert(victim, type, timer -> {
        })) {
            return;
        }
        SecondRounderZombieServant servant = mob.convertTo(type, true);
        if (servant == null) {
            return;
        }
        if (owner != null) {
            servant.setTrueOwner(owner);
        }
        servant.finalizeSpawn(level, level.getCurrentDifficultyAt(servant.blockPosition()),
                MobSpawnType.CONVERSION, null, null);
        servant.setLimitedLife(10 * (15 + level.random.nextInt(45)));
        ForgeEventFactory.onLivingConvert(victim, servant);
        level.levelEvent(null, 1026, servant.blockPosition(), 0);
    }

    private static boolean isSecondRounderZombie(LivingEntity entity) {
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return SECOND_ROUNDER_ZOMBIE.equals(id);
    }
}
