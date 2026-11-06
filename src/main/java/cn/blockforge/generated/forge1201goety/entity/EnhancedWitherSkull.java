package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.entity.EnhancedWitherSkull;
import cn.blockforge.generated.goetysupreme.renderer.EnhancedWitherSkullRenderer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.ServantWitherSkull;
import cn.blockforge.generated.goetysupreme.util.WitherServantFriendlyFire;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public class EnhancedWitherSkull extends ServantWitherSkull {

    public static final double SPEED_POWER = 0.2D;

    public static final float EXPLOSION_POWER = 2.0F;

    private float explosionPower = EXPLOSION_POWER;

    public EnhancedWitherSkull(EntityType<? extends WitherSkull> type, Level level) {
        super(type, level);
    }

    public EnhancedWitherSkull(Level level, LivingEntity owner, Vec3 dir) {
        this(ModEntityTypes.ENHANCED_WITHER_SKULL.get(), level);
        setOwner(owner);
        setRot(owner.getYRot(), owner.getXRot());
        double length = dir.length();
        if (length > 1.0E-6D) {
            xPower = dir.x / length * SPEED_POWER;
            yPower = dir.y / length * SPEED_POWER;
            zPower = dir.z / length * SPEED_POWER;
        }
    }

    public void setExplosionPower(float power) {
        this.explosionPower = Math.max(0.0F, power);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("ExplosionPower", this.explosionPower);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.explosionPower = tag.contains("ExplosionPower")
                ? tag.getFloat("ExplosionPower") : EXPLOSION_POWER;
    }

    @Override
    public float explosionPower() {
        return this.explosionPower;
    }

    @Override
    public void spawnImpactCloud() {
        spawnCloud(300, 2);
    }

    @SubscribeEvent
    public static void doubleEnhancedSkullDamage(LivingHurtEvent event) {
        if (event.getAmount() <= 0.0F) {
            return;
        }
        DamageSource source = event.getSource();
        Entity direct = source.getDirectEntity();
        Entity causing = source.getEntity();
        boolean projectileHit = direct instanceof EnhancedWitherSkull;
        boolean explosionHit = causing instanceof EnhancedWitherSkull && source.is(DamageTypes.EXPLOSION);
        if (projectileHit || explosionHit) {
            event.setAmount(event.getAmount() * 2.0F);
        }
    }
}
