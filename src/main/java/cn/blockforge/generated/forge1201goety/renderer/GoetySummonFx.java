package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;
import cn.blockforge.generated.goetysupreme.renderer.GoetySummonFx;

public final class GoetySummonFx {

    private static final String DONE_TAG = "GoetySupremeSummonFx";

    private static final int SOUL_FROM = 0x9DB9FF;
    private static final int SOUL_TO = 0xFFFFFF;

    private record Palette(int ray, int smokeFrom, int smokeTo) {
    }

    private static final Palette FIRE = new Palette(0xFF6A00, 0xFFA300, 0x8A1E00);

    private static final Palette FROST = new Palette(0x8FE0FF, 0xD8F4FF, 0x2A6FA8);

    private static final Palette VOID = new Palette(0xC46BFF, 0xE0B0FF, 0x4A1E6A);

    private static final Palette SEA = new Palette(0x3FB6E8, 0xA8E8F8, 0x14496E);

    private static final Palette GEO = new Palette(0xD8A54A, 0xFFD98A, 0x5A3A12);

    private static final Palette WILD = new Palette(0x6FCF4F, 0xB8F09A, 0x2A5A18);

    private static final Palette NECRO = new Palette(0x9DB9FF, 0xC8D8FF, 0x3A4A8A);

    private static final Palette SAKURA = new Palette(0xFFA8C8, 0xFFD0E0, 0xA83A6A);

    private static final Palette STEEL = new Palette(0xB8D8E8, 0xE8F4FF, 0x4A5A6A);

    private static final Palette WITHER = new Palette(0x8A6FD0, 0xC0A8F0, 0x2A1A3A);

    private GoetySummonFx() {
    }

    public static void play(ServerLevel level, Entity summoned) {
        if (level == null || summoned == null) {
            return;
        }
        if (summoned.getPersistentData().getBoolean(DONE_TAG)) {
            return;
        }
        summoned.getPersistentData().putBoolean(DONE_TAG, true);
        Palette palette = paletteFor(summoned);
        if (palette != null) {
            ServerParticleUtil.summonUndeadParticles(level, summoned,
                    new ColorUtil(palette.ray()), palette.smokeFrom(), palette.smokeTo());
        } else if (summoned instanceof LivingEntity living && living.getMobType() == MobType.UNDEAD) {
            ServerParticleUtil.summonUndeadParticles(level, summoned);
        } else {
            ServerParticleUtil.summonUndeadParticles(level, summoned,
                    new ColorUtil(SOUL_FROM), SOUL_FROM, SOUL_TO);
        }
    }

    public static void playAt(ServerLevel level, double x, double y, double z) {
        if (level == null) {
            return;
        }
        ServerParticleUtil.sendGodRay(level, x, y, z, new ColorUtil(SOUL_FROM));
    }

    private static Palette paletteFor(Entity summoned) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(summoned.getType());
        if (id == null) {
            return null;
        }
        String path = id.getPath().toLowerCase(Locale.ROOT);
        if (hasAny(path, "blaze", "flame", "flamoar", "fire", "ignis", "wildfire")) {
            return FIRE;
        }
        if (hasAny(path, "ice", "frost", "snow", "frozen", "cold")) {
            return FROST;
        }
        if (path.contains("serpon")) {
            return WILD;
        }
        if (path.contains("sakura")) {
            return SAKURA;
        }
        if (hasAny(path, "enderman", "void", "abyss", "shade")) {
            return VOID;
        }
        if (hasAny(path, "shark", "sea", "wave", "bulge", "ocean", "tide")) {
            return SEA;
        }
        if (hasAny(path, "golem", "iron", "sediment", "geo", "stone", "rock")) {
            return GEO;
        }
        if (hasAny(path, "second_rounder", "animated", "osseous", "rotting")) {
            return STEEL;
        }
        if (path.contains("wither")) {
            return WITHER;
        }
        if (hasAny(path, "haunted", "reaper", "necro", "wraith", "skull", "spirit",
                "skeleton", "zombie", "ghost", "phantom", "vex")) {
            return NECRO;
        }
        return null;
    }

    private static boolean hasAny(String path, String... needles) {
        for (String needle : needles) {
            if (path.contains(needle)) {
                return true;
            }
        }
        return false;
    }
}
