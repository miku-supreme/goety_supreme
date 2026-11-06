package cn.blockforge.generated.goetysupreme.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.TintedTotemEffectOption;
import cn.blockforge.generated.goetysupreme.renderer.TintedTotemEffectParticle;

public class TintedTotemEffectOption implements ParticleOptions {

    public static final Codec<TintedTotemEffectOption> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("red").forGetter(TintedTotemEffectOption::red),
            Codec.FLOAT.fieldOf("green").forGetter(TintedTotemEffectOption::green),
            Codec.FLOAT.fieldOf("blue").forGetter(TintedTotemEffectOption::blue)
    ).apply(instance, TintedTotemEffectOption::new));

    public static final Deserializer<TintedTotemEffectOption> DESERIALIZER = new Deserializer<>() {
        @Override
        public TintedTotemEffectOption fromCommand(ParticleType<TintedTotemEffectOption> type, StringReader reader)
                throws CommandSyntaxException {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            return new TintedTotemEffectOption(r, g, b);
        }

        @Override
        public TintedTotemEffectOption fromNetwork(ParticleType<TintedTotemEffectOption> type, FriendlyByteBuf buf) {
            return new TintedTotemEffectOption(buf.readFloat(), buf.readFloat(), buf.readFloat());
        }
    };

    private final float red;
    private final float green;
    private final float blue;

    public TintedTotemEffectOption(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public float red() { return red; }
    public float green() { return green; }
    public float blue() { return blue; }

    @Override
    public ParticleType<?> getType() {
        return GeneratedMod.TINTED_TOTEM_EFFECT.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.red);
        buf.writeFloat(this.green);
        buf.writeFloat(this.blue);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.3f %.3f %.3f",
                net.minecraft.core.registries.BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()),
                this.red, this.green, this.blue);
    }
}
