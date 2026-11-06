package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithServant;
import cn.blockforge.generated.goetysupreme.item.GreenSmithSoulJarItem;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;

public class GreenSmithServant extends AbstractGreenSmith {

    public static final float FLEE_WARLORD_DISTANCE = 6.0F;

    public GreenSmithServant(EntityType<? extends GreenSmithServant> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.followGoal();
        this.targetRetaliateGoal();
        this.ownerHurtGoals();
        this.targetSelector.addGoal(2, new SummonTargetGoal(this, true, true));
        this.goalSelector.addGoal(0, new FleeWarlordServantGoal(this));
    }

    @Override
    public int getSummonLimit(LivingEntity owner) {
        return ServantLimits.limitFor(getType());
    }

    private static final class FleeWarlordServantGoal extends AvoidEntityGoal<AbstractWitherWarlord> {

        private final GreenSmithServant smith;

        FleeWarlordServantGoal(GreenSmithServant smith) {
            super(smith, AbstractWitherWarlord.class, FLEE_WARLORD_DISTANCE, 1.1D, 1.3D);
            this.smith = smith;
        }

        @Override
        public boolean canUse() {
            return !this.smith.isClone() && this.isSmithIdle() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return this.isSmithIdle() && super.canContinueToUse();
        }

        private boolean isSmithIdle() {
            LivingEntity target = this.smith.getTarget();
            return (target == null || !target.isAlive()) && !this.smith.isCasting();
        }

        @Override
        public void start() {
            super.start();
            this.smith.setFleeingWarlord(true);
        }

        @Override
        public void stop() {
            super.stop();
            this.smith.setFleeingWarlord(false);
        }
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        this.greenSmithSoulJar();
    }

    @Override
    public void dropGreenSmithLoot(int looting) {
    }

    private void greenSmithSoulJar() {
        if (this.isClone()) {
            return;
        }
        if (this.level().isClientSide) {
            return;
        }
        if (!(this.getTrueOwner() instanceof Player player)) {
            return;
        }
        ItemStack emptyJar = ItemStack.EMPTY;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.EMPTY_SOUL_JAR.get())) {
                emptyJar = stack;
                break;
            }
        }
        if (emptyJar.isEmpty()) {
            return;
        }
        if (!player.getAbilities().instabuild) {
            emptyJar.shrink(1);
        }
        ItemStack jar = new ItemStack(GeneratedMod.GREEN_SMITH_SOUL_JAR.get());
        ReviveServantItem.setOwnerName(player, jar);
        ReviveServantItem.setSummon(this, jar);
        ReviveCooldown.grantOnDelivery(player, jar);
        if (!player.getInventory().add(jar)) {
            player.drop(jar, false, true);
        }
    }
}
