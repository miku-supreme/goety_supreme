package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.util.ServantMoveModes;
import cn.blockforge.generated.goetysupreme.entity.SnowGolemServant;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

public final class ServantMoveModes {

    private ServantMoveModes() {
    }

    public static void cycle(Summoned servant, Player player) {
        if (!servant.isWandering() && !servant.isStaying() && !servant.isGuardingArea()) {
            servant.setBoundPos(null);
            servant.setWandering(true);
            servant.setStaying(false);
            player.displayClientMessage(
                    Component.translatable("info.goety.servant.wander", servant.getDisplayName()), true);
        } else if (!servant.isStaying() && !servant.isGuardingArea()) {
            servant.setBoundPos(null);
            servant.setWandering(false);
            servant.setStaying(true);
            player.displayClientMessage(
                    Component.translatable("info.goety.servant.staying", servant.getDisplayName()), true);
        } else if (!servant.isGuardingArea()) {
            servant.setBoundPos(servant.blockPosition());
            servant.setWandering(false);
            servant.setStaying(false);
            player.displayClientMessage(
                    Component.translatable("info.goety.servant.guard", servant.getDisplayName()), true);
        } else {
            servant.setBoundPos(null);
            servant.setWandering(false);
            servant.setStaying(false);
            player.displayClientMessage(
                    Component.translatable("info.goety.servant.follow", servant.getDisplayName()), true);
        }
        servant.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 1.0F, 1.0F);
    }
}
