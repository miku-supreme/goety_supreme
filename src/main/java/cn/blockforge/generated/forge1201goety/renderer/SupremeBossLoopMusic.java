package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import cn.blockforge.generated.goetysupreme.renderer.SupremeBossLoopMusic;
import cn.blockforge.generated.goetysupreme.util.SupremeBossMusic;

@OnlyIn(Dist.CLIENT)
public final class SupremeBossLoopMusic extends AbstractTickableSoundInstance {

    private static final int FADE_TICKS = 40;

    private static final int FADE_DELAY_IDLE = 80;

    private static final int FADE_DELAY_ENGAGED = 60;

    private final Mob boss;
    private final float trueVolume;
    private int fadeTimer;
    private int timeUntilFade = FADE_DELAY_IDLE;
    private int ticksExisted;

    private boolean retiring;

    public SupremeBossLoopMusic(SoundEvent music, Mob boss, float volume, float pitch) {
        super(music, SoundSource.RECORDS, SoundInstance.createUnseededRandom());
        this.boss = boss;
        this.trueVolume = volume;
        this.x = boss.getX();
        this.y = boss.getY();
        this.z = boss.getZ();
        this.looping = true;
        this.delay = 0;
        this.pitch = pitch;
        this.fadeTimer = FADE_TICKS / 2;
        this.volume = volume * (this.fadeTimer / (float) FADE_TICKS);
    }

    @Override
    public boolean isLooping() {
        return !this.retiring && SupremeBossMusic.currentMusic() == this;
    }

    public boolean matches(SoundEvent music) {
        return this.getLocation().equals(music.getLocation());
    }

    public boolean belongsTo(Mob boss) {
        return this.boss == boss;
    }

    public void retire() {
        this.retiring = true;
        this.timeUntilFade = 0;
    }

    @Override
    public void tick() {
        boolean engaged = !this.retiring && SupremeBossMusic.isEngaged(this.boss);

        if (engaged) {
            if (this.fadeTimer < FADE_TICKS) {
                this.fadeTimer++;
            }
            this.timeUntilFade = FADE_DELAY_ENGAGED;
        } else {
            if (this.boss.isDeadOrDying()) {
                this.timeUntilFade = 0;
            }
            if (this.timeUntilFade > 0) {
                this.timeUntilFade--;
            } else if (this.fadeTimer > 0) {
                this.fadeTimer--;
            }
        }

        this.x = this.boss.getX();
        this.y = this.boss.getY();
        this.z = this.boss.getZ();
        this.volume = this.trueVolume * (this.fadeTimer / (float) FADE_TICKS);

        if (!engaged && this.fadeTimer <= 0) {
            SupremeBossMusic.clear(this);
            this.stop();
            return;
        }

        if (this.ticksExisted % 100 == 0) {
            Minecraft.getInstance().getMusicManager().stopPlaying();
        }
        this.ticksExisted++;
    }
}
