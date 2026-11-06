package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancerServant;
import cn.blockforge.generated.goetysupreme.item.ReaperSoulJarItem;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;

public class ReaperNecromancerServant extends AbstractReaperNecromancer {

    public ReaperNecromancerServant(EntityType<? extends ReaperNecromancerServant> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.followGoal();
        this.targetRetaliateGoal();
        this.ownerHurtGoals();
        this.targetSelector.addGoal(2, new SummonTargetGoal(this, true, true));
    }

    @Override
    public void die(DamageSource cause) {
        this.reaperSoulJar();
        super.die(cause);
    }

    @Override
    public int getSummonLimit(LivingEntity owner) {
        return ServantLimits.limitFor(getType());
    }

    private void reaperSoulJar() {
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
        ItemStack jar = new ItemStack(GeneratedMod.REAPER_SOUL_JAR.get());
        ReviveServantItem.setOwnerName(player, jar);
        ReviveServantItem.setSummon(this, jar);
        ReviveCooldown.grantOnDelivery(player, jar);
        if (!player.getInventory().add(jar)) {
            player.drop(jar, false, true);
        }
    }
}
