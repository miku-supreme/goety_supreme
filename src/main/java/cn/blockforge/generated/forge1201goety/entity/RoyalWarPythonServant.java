package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.RoyalWarPythonServant;
import cn.blockforge.generated.goetysupreme.entity.WarPythonPartEntity;
import cn.blockforge.generated.goetysupreme.entity.WarPythonServant;

public class RoyalWarPythonServant extends WarPythonServant {

    public RoyalWarPythonServant(EntityType<? extends RoyalWarPythonServant> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FLYING_SPEED, 1.0D)
                .add(Attributes.MAX_HEALTH, 65.0D)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.4D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/royal_war_python.png";
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.ROYAL_WAR_PYTHON;
    }

    @Override
    public EntityType<WarPythonPartEntity> partType() {
        return ModEntityTypes.ROYAL_WAR_PYTHON_PART.get();
    }

    @Override
    public float partRadius() {
        return 0.48F;
    }
}
