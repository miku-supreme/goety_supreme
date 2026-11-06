package cn.blockforge.generated.goetysupreme.util;

import cn.blockforge.generated.goetysupreme.mixin.GuiItemRenderContextMixin;

public final class GuiItemRenderContext {

    private static int depth;

    private GuiItemRenderContext() {
    }

    public static void enter() {
        depth++;
    }

    public static void exit() {
        if (depth > 0) {
            depth--;
        }
    }

    public static boolean isGuiItemRender() {
        return depth > 0;
    }
}
