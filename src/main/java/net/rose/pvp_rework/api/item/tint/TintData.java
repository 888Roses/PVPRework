package net.rose.pvp_rework.api.item.tint;

public class TintData {
    private final boolean hasTint;
    private final int tint;

    public TintData(boolean hasTint, int tint) {
        this.hasTint = hasTint;
        this.tint = tint;
    }

    public static TintData withTint(int tint) {
        return new TintData(true, tint);
    }

    public static TintData noTint() {
        return new TintData(false, 0x00000000);
    }

    public int getTint() {
        return this.tint;
    }

    public boolean hasTint() {
        return this.hasTint;
    }
}
