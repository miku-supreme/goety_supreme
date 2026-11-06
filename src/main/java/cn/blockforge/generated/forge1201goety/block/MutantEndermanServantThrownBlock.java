package cn.blockforge.generated.goetysupreme.block;

import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.world.entity.mutant.MutantEnderman;
import fuzs.mutantmonsters.world.entity.projectile.ThrowableBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import cn.blockforge.generated.goetysupreme.config.MutantEndermanServantConfig;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.block.MutantEndermanServantThrownBlock;

public final class MutantEndermanServantThrownBlock extends ThrowableBlock {
    public MutantEndermanServantThrownBlock(EntityType<? extends MutantEndermanServantThrownBlock> type,
            Level level) {
        super(type, level);
    }

    public MutantEndermanServantThrownBlock(MutantEnderman standIn, int arm, MutantEndermanServant servant) {
        this(ModEntityTypes.MUTANT_ENDERMAN_THROWN_BLOCK.get(), servant.level());
        ThrowableBlock trajectory = new ThrowableBlock(standIn, arm);
        CompoundTag state = new CompoundTag();
        trajectory.saveWithoutId(state);
        load(state);
        setUUID(java.util.UUID.randomUUID());
        moveTo(trajectory.getX(), trajectory.getY(), trajectory.getZ(), trajectory.getYRot(), trajectory.getXRot());
        setDeltaMovement(trajectory.getDeltaMovement());
        setOwner(servant);
    }

    private MutantEndermanServant servant() {
        return getOwner() instanceof MutantEndermanServant servant ? servant : null;
    }

    @Override
    public BlockState getBlockState() {
        BlockState state = super.getBlockState();
        return state == null ? Blocks.AIR.defaultBlockState() : state;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide() && (tickCount > 200 || tickCount > 20 && servant() == null)) {
            discard();
        }
    }

    @Override
    public boolean canHitEntity(Entity target) {
        MutantEndermanServant servant = servant();
        return servant != null && target != servant && !servant.isAlliedTo(target) && super.canHitEntity(target);
    }

    @Override
    public void onHit(HitResult result) {
        if (level().isClientSide()) {
            return;
        }
        MutantEndermanServant servant = servant();
        if (servant != null) {
            LivingEntity owner = servant.getTrueOwner() == null ? servant : servant.getTrueOwner();
            if (result instanceof EntityHitResult hit && canDamage(hit.getEntity())) {
                servant.hurtWithVoidEffect((LivingEntity) hit.getEntity(), ModDamageSource.summonAttack(servant, owner),
                        servant.scaleDamage(MutantEndermanServantConfig.RUBBLE_DAMAGE.get().floatValue()));
            }
            float damage = servant.scaleDamage(MutantEndermanServantConfig.RUBBLE_AREA_DAMAGE.get().floatValue());
            if (damage > 0.0F) {
                for (LivingEntity victim : level().getEntitiesOfClass(LivingEntity.class,
                        getBoundingBox().inflate(2.0D), this::canDamage)) {
                    servant.hurtWithVoidEffect(victim, ModDamageSource.summonAttack(servant, owner), damage);
                }
            }
        }
        level().broadcastEntityEvent(this, (byte) 3);
        BlockState state = getBlockState();
        if (!state.isAir()) {
            playSound(state.getSoundType().getBreakSound(), 0.8F,
                    0.8F + (random.nextFloat() - random.nextFloat()) * 0.2F);
        }
        discard();
    }

    private boolean canDamage(Entity target) {
        MutantEndermanServant servant = servant();
        return servant != null && target instanceof LivingEntity && target != servant && target.isAlive()
                && !target.isSpectator() && !servant.isAlliedTo(target);
    }
}
