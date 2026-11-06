package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.ai.SummonTargetGoal;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SoulReaperConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractSoulReaper;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancerServant;
import cn.blockforge.generated.goetysupreme.item.ReaperSoulJarItem;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.entity.SoulReaperServant;

public class SoulReaperServant extends AbstractSoulReaper {

    private static final ResourceLocation SHADOW_ESSENCE =
            ResourceLocation.fromNamespaceAndPath("goety", "shadow_essence");

    private static final ResourceLocation FORBIDDEN_FRAGMENT =
            ResourceLocation.fromNamespaceAndPath("goety", "forbidden_fragment");

    private int noTargetTicks;

    public SoulReaperServant(EntityType<? extends SoulReaperServant> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        if (this.isCasting()) {
            this.noTargetTicks = 0;
            return;
        }
        LivingEntity coffinTarget = this.getCoffin() == null ? null : this.getCoffin().getTarget();
        boolean noTarget = this.getTarget() == null && coffinTarget == null;
        if (noTarget) {
            if (++this.noTargetTicks >= 600) {
                this.noTargetTicks = 0;
                this.dismissCoffin();
                com.mojang.logging.LogUtils.getLogger().info("[soulreaper] coffin stowed after 600 idle ticks");
            }
        } else {
            this.noTargetTicks = 0;
        }
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
        this.soulReaperSoulJar();
        super.die(cause);
    }

    private void soulReaperSoulJar() {
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

    @Override
    public boolean isServantForm() {
        return true;
    }

    @Override
    public void tryKill(Player player) {
        if (this.level().isClientSide()) {
            return;
        }
        if (this.getKillChance() <= 0) {
            this.warnKill(player);
            return;
        }
        this.hurt(com.Polarice3.Goety.utils.ModDamageSource.getDamageSource(
                this.level(), com.Polarice3.Goety.utils.ModDamageSource.DISMISSED), Float.MAX_VALUE);
    }

    @Override
    public int soulPowerTier() {
        if (this.getTrueOwner() instanceof Player owner) {
            return soulPowerTierFor(SEHelper.getSESouls(owner));
        }
        return 0;
    }

    @Override
    public float soulPowerDamageMultiplier() {
        return 1.0F + this.soulPowerTier()
                * (float) (double) SoulReaperConfig.SERVANT_SOUL_DAMAGE_PERCENT_PER_1000.get();
    }

    @Override
    public void refreshGrowthAttributes() {
        int growth = this.getGrowthLevel();
        AttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            double value = SoulReaperConfig.servantHealthForLevel(growth);
            if (health.getBaseValue() != value) {
                health.setBaseValue(value);
            }
        }
        AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            double value = BASE_MOVEMENT_SPEED
                    + growth * SoulReaperConfig.SERVANT_MOVEMENT_SPEED_PER_LEVEL.get();
            if (speed.getBaseValue() != value) {
                speed.setBaseValue(value);
            }
        }
    }

    @Override
    public float servantLifestealRatio() {
        return (float) (double) SoulReaperConfig.SERVANT_LIFESTEAL_RATIO.get();
    }

    @Override
    public int getSummonLimit(LivingEntity owner) {
        return ServantLimits.limitFor(getType());
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.isServantForm() || player != this.getTrueOwner()) {
            return super.mobInteract(player, hand);
        }
        ItemStack stack = player.getItemInHand(hand);
        if (ReaperSoulJarItem.holdsSoulReaper(stack, this.level())) {
            return super.mobInteract(player, hand);
        }
        int growth = this.getGrowthLevel();
        boolean maxed = growth >= MAX_GROWTH_LEVEL;
        boolean accepted = maxed
                ? stack.is(GeneratedMod.REAPER_SOUL_JAR.get())
                : matchesGrowthItem(stack, growth);
        if (!accepted) {
            return super.mobInteract(player, hand);
        }
        if (!this.level().isClientSide) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (maxed) {
                this.setHealth(this.getMaxHealth());
                this.playGrowthLaugh();
                if (this.level() instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.SOUL, this.getX(),
                            this.getY(0.8D), this.getZ(), 24, 0.4D, 0.7D, 0.4D, 0.02D);
                }
            } else {
                this.levelUp();
            }
            player.swing(hand);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    private boolean matchesGrowthItem(ItemStack stack, int currentLevel) {
        return switch (currentLevel) {
            case 0 -> matches(stack, SHADOW_ESSENCE);
            case 1 -> matches(stack, FORBIDDEN_FRAGMENT);
            case 2 -> stack.is(GeneratedMod.REAPER_SOUL_JAR.get());
            default -> false;
        };
    }

    private boolean matches(ItemStack stack, ResourceLocation id) {
        Item item = ForgeRegistries.ITEMS.getValue(id);
        return item != null && stack.is(item);
    }
}
