package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import cn.blockforge.generated.goetysupreme.util.BallisticAim;
import cn.blockforge.generated.goetysupreme.event.ServantHandler;
import cn.blockforge.generated.goetysupreme.spell.SnowballFocusSpell;
import cn.blockforge.generated.goetysupreme.util.SnowballVolley;
import cn.blockforge.generated.goetysupreme.util.SpellSnowball;

public final class SpellSnowball extends net.minecraft.world.entity.projectile.Snowball {

    public static final double GRAVITY = 0.03D;

    public static final double DRAG = 0.99D;

    public static final double MIN_SPEED = 0.6D;

    private int potency;
    private float baseDamage;

    public SpellSnowball(Level level, LivingEntity owner, int potency, float baseDamage) {
        super(level, owner);
        this.potency = Math.max(0, potency);
        this.baseDamage = baseDamage;
    }

    @Override
    public float getGravity() {
        return (float) GRAVITY;
    }

    public int getPotency() {
        return potency;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
    }
}
