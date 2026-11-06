package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractBulgeServant;
import cn.blockforge.generated.goetysupreme.entity.BulgeBulletServant;
import cn.blockforge.generated.goetysupreme.entity.BulgeServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.SeaWaveServant;
import cn.blockforge.generated.goetysupreme.util.ServantFeedHeal;

public class BulgeServant extends AbstractBulgeServant {

    public static final int SPELL_LEVEL = 1;

    public BulgeServant(EntityType<? extends BulgeServant> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isSpectator()) {
            return InteractionResult.PASS;
        }
        ItemStack held = player.getItemInHand(hand);
        if (isAlive() && player == getTrueOwner()) {
            float heal = bulgeFeedHeal(held);
            if (heal > 0.0F && ServantFeedHeal.feed(this, player, held, heal)) {
                return InteractionResult.sidedSuccess(level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    private static float bulgeFeedHeal(ItemStack held) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(held.getItem());
        if (id == null || !"jerotesvillage".equals(id.getNamespace())) {
            return 0.0F;
        }
        if ("bulge_powder".equals(id.getPath())) {
            return 1.0F;
        }
        if ("bulge_rod".equals(id.getPath())) {
            return 4.0F;
        }
        return 0.0F;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/elemental/bulge.png";
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.BULGE;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new BulgeFloatGoal(this, 0.35F));
        this.goalSelector.addGoal(2, new BulgeRangedAttackGoal(this, 1.0D, rangedAttackInterval(), rangedAttackRadius()));
        this.goalSelector.addGoal(3, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public float rangedAttackRadius() {
        return 20.0F;
    }

    @Override
    public int rangedAttackInterval() {
        return 60;
    }

    public int spellLevel() {
        return SPELL_LEVEL;
    }

    public float spellAccuracy() {
        return Math.max(0, 3 - spellLevel());
    }

    @Override
    public void fireBullet(LivingEntity target) {
        BulgeBulletServant bullet = new BulgeBulletServant(ModEntityTypes.BULGE_BULLET_SERVANT.get(), this, level(), spellLevel());
        bullet.setDamageBonus(getSummonPotency());
        double muzzleY = getY(0.7D) - bullet.getBbHeight() / 2.0D;
        bullet.setPos(getX(), muzzleY, getZ());
        Vec3 direction = target.getBoundingBox().getCenter().subtract(bullet.position());
        bullet.shoot(direction.x, direction.y, direction.z, 1.0F, spellAccuracy());
        level().addFreshEntity(bullet);
        if (!isSilent()) {
            playSound(jerotesSound("magic_bulge_bullet", SoundEvents.SNOWBALL_THROW), 1.0F, 1.0F);
        }
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(jerotesParticle("bulge_bullet_display", ParticleTypes.BUBBLE),
                    getX(), getBoundingBox().maxY + 0.5D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        showSpellLevelDisplay(spellLevel());
    }
}
