package cn.blockforge.generated.goetysupreme.client.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class SupremeEffectOption implements ParticleOptions {
    private final ParticleType<SupremeEffectOption> type;
    public final float size;
    public final int life;
    public final float a, b, c;
    public final boolean alternate;

    public SupremeEffectOption(ParticleType<SupremeEffectOption> type, float size, int life, float a, float b, float c, boolean alternate) {
        this.type = type;
        this.size = size;
        this.life = life;
        this.a = a;
        this.b = b;
        this.c = c;
        this.alternate = alternate;
    }

    public SupremeEffectOption(ParticleType<SupremeEffectOption> type, float size, int life, float a, float b, float c) {
        this(type, size, life, a, b, c, false);
    }

    public static Codec<SupremeEffectOption> codec(ParticleType<SupremeEffectOption> type) {
        return RecordCodecBuilder.create(instance -> instance.group(
                Codec.FLOAT.fieldOf("size").forGetter(o -> o.size),
                Codec.INT.fieldOf("life").forGetter(o -> o.life),
                Codec.FLOAT.fieldOf("a").forGetter(o -> o.a),
                Codec.FLOAT.fieldOf("b").forGetter(o -> o.b),
                Codec.FLOAT.fieldOf("c").forGetter(o -> o.c),
                Codec.BOOL.fieldOf("alternate").forGetter(o -> o.alternate)
        ).apply(instance, (size, life, a, b, c, alternate) -> new SupremeEffectOption(type, size, life, a, b, c, alternate)));
    }

    public static ParticleType<SupremeEffectOption> createType() {
        return new ParticleType<>(true, DESERIALIZER) {
            private final Codec<SupremeEffectOption> codec = SupremeEffectOption.codec(this);

            @Override
            public @NotNull Codec<SupremeEffectOption> codec() {
                return this.codec;
            }
        };
    }

    @NotNull
    @Override
    public ParticleType<SupremeEffectOption> getType() {
        return this.type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.size);
        buf.writeVarInt(this.life);
        buf.writeFloat(this.a);
        buf.writeFloat(this.b);
        buf.writeFloat(this.c);
        buf.writeBoolean(this.alternate);
    }

    @NotNull
    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.3f %d %.3f %.3f %.3f %b",
                BuiltInRegistries.PARTICLE_TYPE.getKey(this.type), this.size, this.life, this.a, this.b, this.c, this.alternate);
    }

    @SuppressWarnings("deprecation")
    public static final Deserializer<SupremeEffectOption> DESERIALIZER = new Deserializer<>() {
        @NotNull
        @Override
        public SupremeEffectOption fromCommand(@NotNull ParticleType<SupremeEffectOption> type, @NotNull StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float size = reader.readFloat();
            reader.expect(' ');
            int life = reader.readInt();
            reader.expect(' ');
            float a = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            float c = reader.readFloat();
            reader.expect(' ');
            boolean alternate = reader.readBoolean();
            return new SupremeEffectOption(type, size, life, a, b, c, alternate);
        }

        @NotNull
        @Override
        public SupremeEffectOption fromNetwork(@NotNull ParticleType<SupremeEffectOption> type, FriendlyByteBuf buf) {
            return new SupremeEffectOption(type, buf.readFloat(), buf.readVarInt(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readBoolean());
        }
    };
}
