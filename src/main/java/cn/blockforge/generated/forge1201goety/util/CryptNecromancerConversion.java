package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.neutral.AbstractCairnNecromancer;
import com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import java.util.Set;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.util.CryptNecromancerConversion;
import cn.blockforge.generated.goetysupreme.entity.CryptNecromancerServant;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.api.NecromancerFocusHolder;

public final class CryptNecromancerConversion {

    private static final ResourceLocation FOCUS_ID =
            ResourceLocation.fromNamespaceAndPath("goety_supreme", "haunted_armor_focus");
    private static final ResourceLocation PITHOS_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "pithos");

    private static final String FOCUS_GRANT = "haunted_armor_focus";

    private static final String PITHOS_GRANT = "pithos";

    private CryptNecromancerConversion() {
    }

    public static boolean tryConvert(AbstractNecromancer self, Player player, ItemStack held) {
        if (!(self instanceof AbstractCairnNecromancer)) {
            return false;
        }
        if (self instanceof CryptNecromancerServant) {
            return false;
        }
        if (self.getTrueOwner() != player) {
            return false;
        }
        if (self.getNecroLevel() < AbstractNecromancer.MAX_LEVEL) {
            return false;
        }
        boolean heldFocus = matches(held, FOCUS_ID);
        boolean heldPithos = matches(held, PITHOS_ID);
        if (!heldFocus && !heldPithos) {
            return false;
        }
        if (!(self instanceof NecromancerFocusHolder holder)) {
            return false;
        }
        Set<String> grants = holder.goetySupreme$necromancerFocusGrants();
        if (grants == null) {
            return false;
        }
        String heldGrant = heldFocus ? FOCUS_GRANT : PITHOS_GRANT;
        if (grants.contains(heldGrant)) {
            return false;
        }
        if (!(self.level() instanceof ServerLevel server)) {
            return true;
        }
        grants.add(heldGrant);
        if (!player.getAbilities().instabuild) {
            held.shrink(1);
        }
        self.playLaughSound();
        ServerParticleUtil.summonUndeadParticles(server, self);
        if (!grants.contains(FOCUS_GRANT) || !grants.contains(PITHOS_GRANT)) {
            return true;
        }
        CryptNecromancerServant crypt = new CryptNecromancerServant(
                ModEntityTypes.CRYPT_NECROMANCER_SERVANT.get(), server);
        crypt.moveTo(self.getX(), self.getY(), self.getZ(), self.getYRot(), 0.0F);
        crypt.setNecroLevel(0);
        crypt.applyOwner(player);
        if (!server.addFreshEntity(crypt)) {
            return false;
        }
        self.discard();
        player.swing(InteractionHand.MAIN_HAND);
        server.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, crypt.getX(), crypt.getY(1.0D), crypt.getZ(),
                36, 0.5D, 0.9D, 0.5D, 0.03D);
        server.sendParticles(ParticleTypes.SCULK_SOUL, crypt.getX(), crypt.getY(1.0D), crypt.getZ(),
                24, 0.4D, 0.8D, 0.4D, 0.02D);
        server.playSound(null, crypt.getX(), crypt.getY(0.5D), crypt.getZ(),
                ModSounds.NECROMANCER_LAUGH.get(), SoundSource.HOSTILE, 1.2F, 0.85F);
        return true;
    }

    private static boolean matches(ItemStack stack, ResourceLocation id) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }
        return id.equals(ForgeRegistries.ITEMS.getKey(stack.getItem()));
    }
}
