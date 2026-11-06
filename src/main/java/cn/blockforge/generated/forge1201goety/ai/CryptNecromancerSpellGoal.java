package cn.blockforge.generated.goetysupreme.ai;

import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer.NecromancerSpellType;
import com.Polarice3.Goety.init.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;

public class CryptNecromancerSpellGoal extends Goal {

    private static final int WARM_UP_TICKS = 40;

    private static final int TOTAL_TICKS = WARM_UP_TICKS + 20;

    private static final int COOLDOWN_TICKS = 200;

    private static final int CAST_TICK = TOTAL_TICKS - 20;

    private static final float STORM_CHANCE = 0.5F;

    private final CryptNecromancerServant necromancer;
    private int spellTime;
    private boolean storm;

    public CryptNecromancerSpellGoal(CryptNecromancerServant necromancer) {
        this.necromancer = necromancer;
    }

    @Override
    public boolean canUse() {
        if (this.necromancer.cryptSkillCool() > 0) {
            return false;
        }
        if (this.necromancer.isSpellCasting()) {
            return false;
        }
        LivingEntity target = this.necromancer.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return this.spellTime > 0;
    }

    @Override
    public void start() {
        this.storm = this.necromancer.getRandom().nextFloat() < STORM_CHANCE;
        this.spellTime = TOTAL_TICKS;
        this.necromancer.getNavigation().stop();
        this.necromancer.setSpellCasting(true);
        this.necromancer.setNecromancerSpellType(NecromancerSpellType.CLOUD);
        this.necromancer.setAnimationState(AbstractNecromancer.SPELL_ANIM);
        this.necromancer.playSound(ModSounds.FROST_PREPARE_SPELL.get(), 1.5F, 1.0F);
    }

    @Override
    public void stop() {
        this.spellTime = 0;
        this.necromancer.setSpellCasting(false);
        this.necromancer.setNecromancerSpellType(NecromancerSpellType.NONE);
        if (this.necromancer.isCurrentAnimation(AbstractNecromancer.SPELL_ANIM)) {
            this.necromancer.setAnimationState(AbstractNecromancer.IDLE);
        }
        this.necromancer.setCryptSkillCool(COOLDOWN_TICKS);
    }

    @Override
    public void tick() {
        LivingEntity target = this.necromancer.getTarget();
        if (target == null || !target.isAlive()) {
            this.spellTime = 0;
            return;
        }
        --this.spellTime;
        if (this.spellTime == CAST_TICK && this.necromancer.level() instanceof ServerLevel server) {
            if (this.storm) {
                this.necromancer.castIceStorm(server, target);
            } else {
                this.necromancer.castBlizzard(server, target);
            }
            this.necromancer.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.5F, 0.8F);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
