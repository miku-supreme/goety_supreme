package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.IronGolemServant;

public class IronGolemServant extends AbstractGolemServant {

    public static final double VANILLA_WANDER_SPEED = 0.6D;

    public IronGolemServant(EntityType<? extends IronGolemServant> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return baseAttributes(GolemServantConfig.IRON);
    }

    @Override
    public GolemServantConfig.Section configSection() {
        return GolemServantConfig.IRON;
    }

    @Override
    public double wanderSpeed() {
        return VANILLA_WANDER_SPEED;
    }

    @Override
    public int attackSwingAnimation() {
        return ATTACK_SWING;
    }

    @Override
    public void applyMeleeHitKnockback(Entity target) {
        applyVanillaGolemKnockback(target);
    }
}
