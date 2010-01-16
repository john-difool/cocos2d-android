package org.cocos2d.types;

public class CCColorF {
    public float r;
    public float g;
    public float b;
    public float a;

    public CCColorF() {
        r = g = b = a = 1.0f;
    }

    public CCColorF(float rr, float gg, float bb, float aa) {
        r = rr;
        g = gg;
        b = bb;
        a = aa;
    }

    public float[] ccColorF() {
        return new float[]{r, g, b, a};
    }

    public String toString() {
        return "< r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + " >";
    }

}
