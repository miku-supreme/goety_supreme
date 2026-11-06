package cn.blockforge.generated.goetysupreme.util;

public final class CastQueue {

    public static final int EMPTY = -1;

    private int animation = EMPTY;
    private int effect;
    private boolean flag;

    public boolean isEmpty() {
        return this.animation == EMPTY;
    }

    public void offer(int animation, int effect, boolean flag) {
        this.animation = animation;
        this.effect = effect;
        this.flag = flag;
    }

    public int animation() {
        return this.animation;
    }

    public int effect() {
        return this.effect;
    }

    public boolean flag() {
        return this.flag;
    }

    public void clear() {
        this.animation = EMPTY;
        this.effect = 0;
        this.flag = false;
    }
}
