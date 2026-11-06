package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.particles.FoggyCloudParticleOption;
import com.Polarice3.Goety.common.entities.neutral.AbstractCairnNecromancer;
import com.Polarice3.Goety.common.entities.projectiles.HauntedSkullProjectile;
import com.Polarice3.Goety.common.entities.projectiles.IceSpike;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import com.Polarice3.Goety.common.items.revive.SoulJar;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.ai.CryptNecromancerSpellGoal;
import cn.blockforge.generated.goetysupreme.entity.CryptIceStorm;
import cn.blockforge.generated.goetysupreme.util.CryptNecromancerConversion;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.item.CryptSoulJarItem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.NecromancerSummonExtras;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.entity.TrackingIceSpike;

public class CryptNecromancerServant extends AbstractCairnNecromancer {

    public static final float RANGED_STANDOFF = 14.0F;
    public static final float AVOID_DISTANCE = 6.0F;
    public static final int AVOID_PATH_RADIUS = 8;

    public static final int MAX_CRYPT_LEVEL = 3;

    private static final double[] HEALTH_BY_LEVEL = {220.0D, 270.0D, 320.0D, 550.0D};

    private static final double BASE_ICE_DAMAGE = 3.0D;

    private static final double BASE_SKULL_DAMAGE = 5.0D;

    private static final float HITBOX_WIDTH = 0.75F;
    private static final float HITBOX_HEIGHT = 2.8875F;

    private static final float SIZE_PER_LEVEL = 0.15F;

    private int cryptSkillCool;

    private int blizzardTicks;

    private static final int BLIZZARD_DURATION_TICKS = 100;

    private static final int BLIZZARD_BATCH_INTERVAL = 4;

    @Nullable
    private Vec3 blizzardAnchor;

    private static final FoggyCloudParticleOption FROST_CLOUD =
            new FoggyCloudParticleOption(new ColorUtil(MapColor.SNOW.col), 1.5F, 6);

    public CryptNecromancerServant(EntityType<? extends CryptNecromancerServant> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, HEALTH_BY_LEVEL[0])
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, BASE_ICE_DAMAGE);
    }

    @Override
    public void setConfigurableAttributes() {
        MobUtil.setBaseAttributes(this.getAttribute(Attributes.ARMOR), 8.0D);
        MobUtil.setBaseAttributes(this.getAttribute(Attributes.FOLLOW_RANGE), 32.0D);
    }

    @Override
    public void setNecroLevel(int level) {
        int clamped = Mth.clamp(level, 0, MAX_CRYPT_LEVEL);
        this.entityData.set(AbstractCairnNecromancer.LEVEL, clamped);
        AttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(HEALTH_BY_LEVEL[clamped]);
        }
        AttributeInstance attack = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null) {
            attack.setBaseValue(BASE_ICE_DAMAGE + clamped);
        }
        this.reapplyPosition();
        this.refreshDimensions();
    }

    private static double healthForLevel(int level) {
        return HEALTH_BY_LEVEL[Mth.clamp(level, 0, MAX_CRYPT_LEVEL)];
    }

    @NotNull
    @Override
    public EntityDimensions getDimensions(@NotNull Pose pose) {
        float scale = 1.0F + SIZE_PER_LEVEL * Math.max(0, this.getNecroLevel());
        return EntityDimensions.scalable(HITBOX_WIDTH * scale, HITBOX_HEIGHT * scale);
    }

    @Override
    public float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return 2.5F * (1.0F + SIZE_PER_LEVEL * Math.max(0, this.getNecroLevel()));
    }

    @Override
    public void projectileGoal(int priority) {
        this.goalSelector.addGoal(priority, new NecromancerRangedGoal(this, 1.0D, 20, RANGED_STANDOFF));
    }

    @Override
    public void avoidGoal(int priority) {
        this.goalSelector.addGoal(priority,
                com.Polarice3.Goety.common.entities.ai.AvoidTargetGoal.AvoidRadiusGoal.newGoal(
                        this, AVOID_DISTANCE, AVOID_PATH_RADIUS, 1.0D, 1.2D));
    }

    public int getCryptAttackWindupTicks() {
        int speed = Mth.floor(Math.max(getAttackSpeed(), 1.0F));
        return Math.max(1, 10 / speed);
    }

    @Override
    public void summonSpells(int priority) {
        this.goalSelector.addGoal(priority, new CryptNecromancerSpellGoal(this));
        super.summonSpells(priority + 1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.cryptSkillCool > 0) {
            --this.cryptSkillCool;
        }
        if (this.blizzardTicks > 0) {
            --this.blizzardTicks;
            if (!this.level().isClientSide && this.level() instanceof ServerLevel server) {
                this.snowBlizzard(server);
                if (this.blizzardTicks % BLIZZARD_BATCH_INTERVAL == 0) {
                    this.rainBlizzardBatch(server, 1 + Math.max(0, this.getNecroLevel()) / 2);
                }
            }
        }
    }

    public int cryptSkillCool() {
        return this.cryptSkillCool;
    }

    public void setCryptSkillCool(int ticks) {
        this.cryptSkillCool = Math.max(0, ticks);
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity target, float distanceFactor) {
        int level = Math.max(0, this.getNecroLevel());
        int skulls = switch (level) {
            case 0 -> 1;
            case 1 -> 2;
            case 2 -> 2;
            default -> 3;
        };
        int spikes = switch (level) {
            case 0, 1 -> 0;
            case 2 -> 1;
            default -> 2;
        };
        for (int i = 0; i < skulls; ++i) {
            this.shootSkull(target, spreadOffset(i, skulls));
        }
        for (int i = 0; i < spikes; ++i) {
            this.shootTrackingSpike(target, spreadOffset(i, spikes));
        }
        if (spikes > 0) {
            this.playSound(ModSounds.ICE_SPIKE_CAST.get(), 1.0F, 1.0F);
        }
        this.swing(InteractionHand.MAIN_HAND);
    }

    private static double spreadOffset(int index, int count) {
        if (count <= 1) {
            return 0.0D;
        }
        return (index - (count - 1) / 2.0D) * 0.12D;
    }

    private Vec3 staffSpawnPoint(Vec3 aim) {
        return new Vec3(this.getX() + aim.x * 0.5D, this.getEyeY() - 0.2D,
                this.getZ() + aim.z * 0.5D);
    }

    private void shootSkull(LivingEntity target, double spread) {
        Vec3 base = new Vec3(target.getX() - this.getX(), target.getEyeY() - this.getEyeY(),
                target.getZ() - this.getZ()).normalize();
        double dx = base.x * Math.cos(spread) - base.z * Math.sin(spread);
        double dz = base.x * Math.sin(spread) + base.z * Math.cos(spread);
        double speed = 1.2D;
        HauntedSkullProjectile skull = new HauntedSkullProjectile(this,
                dx * speed, base.y * speed, dz * speed, this.level());
        Vec3 spawn = this.staffSpawnPoint(base);
        skull.setPos(spawn.x, spawn.y, spawn.z);
        if (GoetyCompat.hasSkullSetDamage()) {
            skull.setDamage(BASE_SKULL_DAMAGE + Math.max(0, this.getNecroLevel()));
        }
        this.playSound(ModSounds.SKULL_LORD_SHOOT.get(), 1.0F, 1.1F);
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.WHITE_ASH, skull.getX(), skull.getY(), skull.getZ(),
                    6, 0.15D, 0.15D, 0.15D, 0.02D);
        }
        this.level().addFreshEntity(skull);
    }

    private void shootTrackingSpike(LivingEntity target, double spread) {
        Vec3 base = new Vec3(target.getX() - this.getX(), target.getEyeY() - this.getEyeY(),
                target.getZ() - this.getZ()).normalize();
        double dx = base.x * Math.cos(spread) - base.z * Math.sin(spread);
        double dz = base.x * Math.sin(spread) + base.z * Math.cos(spread);
        TrackingIceSpike spike = new TrackingIceSpike(ModEntityTypes.TRACKING_ICE_SPIKE.get(), this.level());
        spike.setOwner(this);
        Vec3 spawn = this.staffSpawnPoint(base);
        spike.setPos(spawn.x, spawn.y, spawn.z);
        spike.shoot(dx, base.y, dz, 1.6F, 1.0F);
        this.level().addFreshEntity(spike);
    }

    public void castBlizzard(ServerLevel server, LivingEntity target) {
        this.blizzardTicks = BLIZZARD_DURATION_TICKS;
        this.blizzardAnchor = target.position();
        this.rainBlizzardBatch(server, 4 + Math.max(0, this.getNecroLevel()) * 2);
        this.playSound(ModSounds.ICE_SPIKE_CAST.get(), 2.0F, 1.0F);
    }

    private void rainBlizzardBatch(ServerLevel server, int count) {
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive()) {
            this.blizzardAnchor = target.position();
        }
        Vec3 location = this.blizzardAnchor;
        if (location == null) {
            return;
        }
        BlockPos cursor = BlockPos.containing(location.x, location.y + 1.5D, location.z);
        int maxHeight = 15;
        for (int i = 0; cursor.getY() < server.getMaxBuildHeight() && server.isEmptyBlock(cursor) && i < maxHeight; ++i) {
            cursor = cursor.above();
        }
        Vec3 anchor = cursor.getCenter();
        for (int j = 0; j < count; ++j) {
            IceSpike spike = new IceSpike(this, server);
            Vec3 pos = anchor.add(
                    server.random.nextFloat() * 8.0F - 4.0F,
                    server.random.nextFloat() * 3.0F - 1.0F,
                    server.random.nextFloat() * 8.0F - 4.0F);
            spike.setRain(true);
            spike.setPos(pos);
            Vec3 dir = location.subtract(pos);
            spike.shoot(dir.x, dir.y, dir.z, 1.2F + server.random.nextFloat() * 0.6F, 20.0F);
            server.addFreshEntity(spike);
            server.sendParticles(FROST_CLOUD, pos.x, pos.y, pos.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            server.sendParticles(ParticleTypes.SNOWFLAKE, pos.x, pos.y, pos.z,
                    8, 0.5D, 0.5D, 0.5D, 0.02D);
        }
    }

    private void snowBlizzard(ServerLevel server) {
        Vec3 location = this.blizzardAnchor;
        if (location == null) {
            return;
        }
        server.sendParticles(ParticleTypes.SNOWFLAKE,
                location.x, location.y + 6.0D, location.z, 12, 4.0D, 3.5D, 4.0D, 0.05D);
        if (this.blizzardTicks % 5 == 0) {
            server.sendParticles(FROST_CLOUD,
                    location.x, location.y + 2.0D, location.z, 1, 2.0D, 1.0D, 2.0D, 0.0D);
        }
    }

    public void castIceStorm(ServerLevel server, LivingEntity target) {
        int level = Math.max(0, this.getNecroLevel());
        Vec3 dir = new Vec3(target.getX() - this.getX(), target.getEyeY() - this.getEyeY(),
                target.getZ() - this.getZ()).normalize();
        CryptIceStorm storm = new CryptIceStorm(ModEntityTypes.CRYPT_ICE_STORM.get(), server);
        storm.setOwner(this);
        storm.setPos(this.getX() + dir.x * 1.2D, this.getEyeY() + 0.2D, this.getZ() + dir.z * 1.2D);
        storm.setExtraDamage(level);
        storm.setDuration(level);
        storm.setRange(1 + level);
        storm.setSize(2.0F + level);
        double power = 0.16D;
        storm.xPower = dir.x * power;
        storm.yPower = dir.y * power;
        storm.zPower = dir.z * power;
        server.addFreshEntity(storm);
        this.playSound(ModSounds.SKULL_LORD_FLY.get(), 1.4F, 0.9F);
    }

    @Override
    public InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.getTrueOwner() == player && stack.is(ModItems.SOUL_JAR.get())) {
            if (!SoulJar.isCairn(stack)) {
                return InteractionResult.PASS;
            }
            if (!this.level().isClientSide) {
                if (this.getNecroLevel() < MAX_CRYPT_LEVEL) {
                    this.setNecroLevel(this.getNecroLevel() + 1);
                }
                this.setHealth(this.getMaxHealth());
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                if (this.level() instanceof ServerLevel server) {
                    server.sendParticles(ParticleTypes.SOUL, this.getX(), this.getY(0.8D), this.getZ(),
                            24, 0.4D, 0.7D, 0.4D, 0.02D);
                }
                this.playLaughSound();
                player.swing(hand);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void soulJar() {
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
        ItemStack jar = new ItemStack(GeneratedMod.CRYPT_SOUL_JAR.get());
        ReviveServantItem.setOwnerName(player, jar);
        ReviveServantItem.setSummon(this, jar);
        ReviveCooldown.grantOnDelivery(player, jar);
        if (!player.getInventory().add(jar)) {
            player.drop(jar, false, true);
        }
    }

    public void applyOwner(Player player) {
        this.setTrueOwner(player);
        this.setPersistenceRequired();
        this.setHasLifespan(false);
        this.setNecroLevel(this.getNecroLevel());
        this.setHealth(this.getMaxHealth());
    }
}
