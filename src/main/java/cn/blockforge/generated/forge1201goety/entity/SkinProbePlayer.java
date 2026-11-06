package cn.blockforge.generated.goetysupreme.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.resources.ResourceLocation;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinLayers;
import cn.blockforge.generated.goetysupreme.entity.SkinProbePlayer;

public final class SkinProbePlayer extends RemotePlayer {

    private ResourceLocation skinTexture;

    public SkinProbePlayer(ClientLevel level, GameProfile profile) {
        super(level, profile);
    }

    public void setSkinTexture(ResourceLocation skinTexture) {
        this.skinTexture = skinTexture;
    }

    @Override
    public ResourceLocation getSkinTextureLocation() {
        return this.skinTexture == null ? super.getSkinTextureLocation() : this.skinTexture;
    }
}
