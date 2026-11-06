package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.util.SummonCircle;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.ScaledSummonCircle;

public class ScaledSummonCircle extends SummonCircle {

    private float visualWidth = 1.0F;
    private boolean godRaySent;
    private boolean spawnLogged;

    public ScaledSummonCircle(EntityType<? extends ScaledSummonCircle> type, Level level) {
        super(type, level);
    }

    public ScaledSummonCircle(Level level, Vec3 pos, Entity entity, boolean preMade, boolean noPos,
            @Nullable LivingEntity owner) {
        this(ModEntityTypes.SCALED_SUMMON_CIRCLE.get(), level);
        this.setPos(pos.x, pos.y, pos.z);
        this.entity = entity;
        this.preMade = preMade;
        this.noPos = noPos;
        this.setTrueOwner(owner);
        if (entity != null) {
            this.visualWidth = entity.getBbWidth();
        }
    }

    public float visualWidth() {
        return this.visualWidth;
    }

    public float visualScale() {
        return Math.max(0.8F, this.visualWidth * 1.2F);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            LivingEntity owner = this.getTrueOwner();
            // 法阵生成中主人死亡/被收进方匣：主人顺延给主人的主人，骷髅照常转给玩家
            if (owner != null && !owner.isAlive()
                    && owner instanceof com.Polarice3.Goety.common.entities.ally.Summoned) {
                LivingEntity next = ((com.Polarice3.Goety.common.entities.ally.Summoned) owner).getTrueOwner();
                if (next != null && next.isAlive()) {
                    this.setTrueOwner(next);
                }
            }
        }
        super.tick();
        if (!this.level().isClientSide && !this.godRaySent
                && this.tickCount >= this.getLifeSpan() && this.level() instanceof ServerLevel server) {
            this.godRaySent = true;
            ServerParticleUtil.sendGodRay(server, this.getX(), this.getY(), this.getZ(),
                    new ColorUtil(0x8FE6DF));
        }
        if (!this.level().isClientSide && !this.spawnLogged && this.tickCount >= this.getLifeSpan()) {
            this.spawnLogged = true;
            com.mojang.logging.LogUtils.getLogger().info(
                    "[wildmage][large] circle done entity={} alive={} removed={}",
                    this.entity == null ? "null" : this.entity.getType(),
                    this.entity != null && this.entity.isAlive(),
                    this.entity == null || this.entity.isRemoved());
        }
    }
}
