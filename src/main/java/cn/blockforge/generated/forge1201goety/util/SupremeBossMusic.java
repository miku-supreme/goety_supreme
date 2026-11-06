package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.init.ModSounds;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.SoulReaper;
import cn.blockforge.generated.goetysupreme.renderer.SupremeBossLoopMusic;
import cn.blockforge.generated.goetysupreme.util.SupremeBossMusic;
import cn.blockforge.generated.goetysupreme.entity.WildMage;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT)
public final class SupremeBossMusic {

    private static final float APOSTLE_THEME_VOLUME = 1.0F;
    private static final float APOSTLE_THEME_PITCH = 1.0F;

    private static final double PRESENCE_RANGE = 64.0D;

    private static AbstractTickableSoundInstance bossMusic;

    private static final Set<UUID> PLAYED_POST_MUSIC = new HashSet<>();

    private SupremeBossMusic() {
    }

    public static AbstractTickableSoundInstance currentMusic() {
        return bossMusic;
    }

    public static void clear(AbstractTickableSoundInstance music) {
        if (bossMusic == music) {
            bossMusic = null;
        }
    }

    public static boolean isEngaged(Mob boss) {
        if (!boss.isAlive() || boss.isDeadOrDying() || boss.isRemoved()) {
            return false;
        }
        Player player = Minecraft.getInstance().player;
        return player != null && boss.level() == player.level()
                && boss.distanceToSqr(player) <= PRESENCE_RANGE * PRESENCE_RANGE;
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity.level() instanceof ClientLevel)) {
            return;
        }
        if (entity instanceof ReaperNecromancer reaper) {
            playIfPresent(reaper, GeneratedMod.CANT_LET_GO_THEME.get());
        } else if (entity instanceof WildMage mage) {
            playIfPresent(mage, GeneratedMod.POLARGEIST_THEME.get());
        } else if (entity instanceof GreenSmith smith) {
            playIfPresent(smith, GeneratedMod.GREEN_SMITH_THEME.get());
        } else if (entity instanceof WitherWarlord warlord) {
            playIfPresent(warlord, warlord.hasCataclysmed()
                    ? GeneratedMod.WARLORD_PHASE2_THEME.get()
                    : GeneratedMod.WARLORD_THEME.get());
        } else if (entity instanceof SoulReaper reaper) {
            playIfPresent(reaper, ModSounds.ENDERMAN_THEME.get());
            if (reaper.isDeadOrDying() && PLAYED_POST_MUSIC.add(reaper.getUUID())) {
                playPostMusic(reaper, ModSounds.ARENA_END.get());
            }
        } else if (entity instanceof Ifrit ifrit) {
            if (!ifrit.isClone()) {
                playIfPresent(ifrit, GeneratedMod.IFRIT_THEME.get());
            }
        }
    }

    private static void playIfPresent(Mob boss, SoundEvent music) {
        if (isEngaged(boss)) {
            playBossMusic(music, boss, APOSTLE_THEME_VOLUME, APOSTLE_THEME_PITCH);
        }
    }

    private static void playPostMusic(Mob boss, SoundEvent music) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            level.playLocalSound(boss.getX(), boss.getY(), boss.getZ(), music,
                    SoundSource.RECORDS, 1.0F, 1.0F, false);
        }
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        bossMusic = null;
        PLAYED_POST_MUSIC.clear();
    }

    private static void playBossMusic(SoundEvent music, Mob boss, float volume, float pitch) {
        Minecraft minecraft = Minecraft.getInstance();
        if (bossMusic instanceof SupremeBossLoopMusic loop
                && loop.belongsTo(boss) && !loop.matches(music)) {
            loop.retire();
            bossMusic = null;
        }
        if (bossMusic == null) {
            bossMusic = new SupremeBossLoopMusic(music, boss, volume, pitch);
        }
        if (!minecraft.getSoundManager().isActive(bossMusic)) {
            minecraft.getSoundManager().play(bossMusic);
        }
    }
}
