package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import cn.blockforge.generated.goetysupreme.entity.GoetyZombieServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.event.ServantHandler;
import cn.blockforge.generated.goetysupreme.util.ZombieServantVariants;

public final class ZombieServantVariants {

    private ZombieServantVariants() {
    }

    public static Mob createFor(ServerLevel level, BlockPos pos) {
        GoetyZombieServant plain = ModEntityTypes.GOETY_ZOMBIE_SERVANT.get().create(level);
        if (plain == null) {
            return null;
        }
        EntityType<?> variant = plain.getVariant(level, pos);
        if (variant == null || isPlainGoetyZombie(variant)) {
            return plain;
        }
        Entity created = variant.create(level);
        return created instanceof Mob mob ? mob : plain;
    }

    private static boolean isPlainGoetyZombie(EntityType<?> type) {
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        return id != null && "goety".equals(id.getNamespace()) && "zombie_servant".equals(id.getPath());
    }
}
