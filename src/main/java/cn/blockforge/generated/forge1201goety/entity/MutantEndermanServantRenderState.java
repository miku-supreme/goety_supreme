package cn.blockforge.generated.goetysupreme.entity;

import fuzs.mutantmonsters.animation.Animation;
import fuzs.mutantmonsters.world.entity.mutant.MutantEnderman;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServant;
import cn.blockforge.generated.goetysupreme.entity.MutantEndermanServantRenderState;

public final class MutantEndermanServantRenderState extends MutantEnderman {
    private MutantEndermanServant servant;

    @SuppressWarnings("unchecked")
    public MutantEndermanServantRenderState(Level level) {
        super((EntityType<? extends MutantEnderman>) BuiltInRegistries.ENTITY_TYPE.get(
                ResourceLocation.fromNamespaceAndPath("mutantmonsters", "mutant_enderman")), level);
        setNoAi(true);
    }

    @Override
    public void registerGoals() {
    }

    public void bind(MutantEndermanServant source) {
        servant = source;
        setPos(source.getX(), source.getY(), source.getZ());
        xo = source.xo;
        yo = source.yo;
        zo = source.zo;
        xOld = source.xOld;
        yOld = source.yOld;
        zOld = source.zOld;
        setYRot(source.getYRot());
        setXRot(source.getXRot());
        yRotO = source.yRotO;
        xRotO = source.xRotO;
        yBodyRot = source.yBodyRot;
        yBodyRotO = source.yBodyRotO;
        yHeadRot = source.yHeadRot;
        yHeadRotO = source.yHeadRotO;
        tickCount = source.tickCount;
        hurtTime = source.hurtTime;
        deathTime = mappedDeathTick();
        hasTarget = source.getHasTargetTicks();
        attackAnim = source.attackAnim;
        oAttackAnim = source.oAttackAnim;
        setAggressive(source.isAggressive());
        setInvisible(source.isInvisible());
        setPose(source.getPose());
        setDeltaMovement(source.getDeltaMovement());
        setOnGround(source.onGround());
        walkAnimation.update(source.walkAnimation.position() - walkAnimation.position()
                - source.walkAnimation.speed(), 1.0F);
        walkAnimation.setSpeed(source.walkAnimation.speed(0.0F));
        walkAnimation.update(source.walkAnimation.speed(), 1.0F);
    }

    public boolean hasVoidEyeUpgrade() {
        return servant != null && servant.hasVoidEyeUpgrade() && servant.isAlive();
    }

    public boolean hasVoidEyeTexture() {
        return servant != null && servant.hasVoidEyeUpgrade();
    }

    private int mappedDeathTick() {
        if (servant == null) {
            return 0;
        }
        if (servant.getDeathDuration() <= 80) {
            return servant.deathTime * DEATH_ANIMATION.duration() / servant.getDeathDuration();
        }
        if (servant.deathTime <= 80) {
            return servant.deathTime;
        }
        return 80 + (servant.deathTime - 80) * (DEATH_ANIMATION.duration() - 80)
                / Math.max(1, servant.getDeathDuration() - 80);
    }

    @Override
    public Animation getAnimation() {
        return servant == null ? Animation.NONE : servant.getAnimation();
    }

    @Override
    public int getAnimationTick() {
        return servant == null ? 0 : servant.deathTime > 0 ? mappedDeathTick() : servant.getAnimationTick();
    }

    @Override
    public int getActiveArm() {
        return servant == null ? 0 : servant.getActiveArm();
    }

    @Override
    public int getHeldBlock(int arm) {
        return servant == null ? 0 : Block.getId(servant.getHeldBlock(arm));
    }

    @Override
    public int getHeldBlockTick(int arm) {
        return servant == null ? 0 : servant.getHeldBlockTick(arm);
    }

    @Override
    public float getArmScale(float partialTick) {
        return servant == null ? 0.0F : servant.getArmScale(partialTick);
    }

    @Override
    public boolean isClone() {
        return servant != null && servant.isClone();
    }

    @Override
    public Optional<BlockPos> getTeleportPosition() {
        return servant == null ? Optional.empty() : servant.getTeleportPosition();
    }

    @Override
    public boolean isInvisibleTo(Player player) {
        return servant == null ? super.isInvisibleTo(player) : servant.isInvisibleTo(player);
    }
}
