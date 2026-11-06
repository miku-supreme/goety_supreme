package cn.blockforge.generated.goetysupreme.compat;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageIceRockProjectile;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public final class JvillageIceRockProjectile {

    private static final ResourceLocation ICE_ROCK_THROW =
            ResourceLocation.fromNamespaceAndPath("jerotesvillage", "ice_rock_throw");

    private JvillageIceRockProjectile() {
    }

    @Nullable
    public static Projectile spawn(Level level, Mob caster, LivingEntity target,
                                   int spellLevelDamage, int spellLevelFreezeTime,
                                   float speed, float accuracy) {
        if (!JerotesVillageCompat.isJerotesVillageLoaded()) {
            return null;
        }
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(ICE_ROCK_THROW);
        if (type == null) {
            return null;
        }
        Projectile projectile;
        try {
            Entity entity = type.create(level);
            if (!(entity instanceof Projectile spawn)) {
                return null;
            }
            CompoundTag spellTag = new CompoundTag();
            spellTag.putUUID("UUID", UUID.randomUUID());
            spellTag.putInt("SpellLevelDamage", spellLevelDamage);
            spellTag.putInt("SpellLevelFreezeTime", spellLevelFreezeTime);
            spawn.load(spellTag);
            projectile = spawn;
        } catch (Throwable absent) {
            return null;
        }
        caster.getLookControl().setLookAt(target, 360.0F, 360.0F);
        caster.lookAt(target, 360.0F, 360.0F);
        projectile.setPos(caster.getX(), caster.getEyeY() - 0.1D, caster.getZ());
        projectile.setOwner(caster);
        projectile.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0.0F, speed, accuracy);
        return projectile;
    }
}
