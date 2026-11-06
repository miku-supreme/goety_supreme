package cn.blockforge.generated.goetysupreme.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import cn.blockforge.generated.goetysupreme.util.ScrollUvVertexConsumer;

public final class ScrollUvVertexConsumer implements VertexConsumer {

    private final VertexConsumer delegate;
    private final float uOffset;
    private final float vOffset;

    public ScrollUvVertexConsumer(VertexConsumer delegate, float uOffset) {
        this(delegate, uOffset, 0.0F);
    }

    public ScrollUvVertexConsumer(VertexConsumer delegate, float uOffset, float vOffset) {
        this.delegate = delegate;
        this.uOffset = uOffset;
        this.vOffset = vOffset;
    }

    @Override
    public VertexConsumer vertex(double x, double y, double z) {
        this.delegate.vertex(x, y, z);
        return this;
    }

    @Override
    public VertexConsumer color(int red, int green, int blue, int alpha) {
        this.delegate.color(red, green, blue, alpha);
        return this;
    }

    @Override
    public VertexConsumer uv(float u, float v) {
        this.delegate.uv(u + this.uOffset, v + this.vOffset);
        return this;
    }

    @Override
    public VertexConsumer overlayCoords(int u, int v) {
        this.delegate.overlayCoords(u, v);
        return this;
    }

    @Override
    public VertexConsumer uv2(int u, int v) {
        this.delegate.uv2(u, v);
        return this;
    }

    @Override
    public VertexConsumer normal(float x, float y, float z) {
        this.delegate.normal(x, y, z);
        return this;
    }

    @Override
    public void defaultColor(int red, int green, int blue, int alpha) {
        this.delegate.defaultColor(red, green, blue, alpha);
    }

    @Override
    public void unsetDefaultColor() {
        this.delegate.unsetDefaultColor();
    }

    @Override
    public void endVertex() {
        this.delegate.endVertex();
    }
}
