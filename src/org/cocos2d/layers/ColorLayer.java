package org.cocos2d.layers;

import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCColor4B;
import org.cocos2d.types.CCMacros;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class ColorLayer extends Layer implements CocosNode.CocosNodeRGBA, CocosNode.CocosNodeSize {
    protected CCColor3B color_;
    protected int opacity_;

    private FloatBuffer squareVertices;
    private ByteBuffer squareColors;


    public static ColorLayer node(CCColor4B color) {
        return new ColorLayer(color, Director.sharedDirector().winSize().width, Director.sharedDirector().winSize().height);
    }

    public static ColorLayer layer(CCColor4B color, float w, float h) {
        return new ColorLayer(color, w, h);
    }

    public ColorLayer(CCColor4B color) {
        this(color, Director.sharedDirector().winSize().width, Director.sharedDirector().winSize().height);
    }

    protected ColorLayer(CCColor4B color, float w, float h) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * 4);
        vbb.order(ByteOrder.nativeOrder());
        squareVertices = vbb.asFloatBuffer();

        squareColors = ByteBuffer.allocateDirect(4 * 4);

        color_ = new CCColor3B(color.r, color.g, color.b);
        opacity_ = color.a;

        updateColor();
        initWidthAndWeight(w, h);
    }

    private void updateColor() {
        for (int i = 0; i < squareColors.limit(); i++) {
            if (i % 4 == 0)
                squareColors.put(i, (byte) color_.r);
            else if (i % 4 == 1)
                squareColors.put(i, (byte) color_.g);
            else if (i % 4 == 2)
                squareColors.put(i, (byte) color_.b);
            else
                squareColors.put(i, (byte) opacity_);
        }
    }

    private void initWidthAndWeight(float w, float h) {
        for (int i = 0; i < (4 * 2); i++)
            squareVertices.put(i, 0);
        squareVertices.put(2, w);
        squareVertices.put(5, h);
        squareVertices.put(6, w);
        squareVertices.put(7, h);
    }


    @Override
    public void draw(GL10 gl) {

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);


        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, squareVertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, squareColors);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        if (opacity_ != 255)
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);


        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        if (opacity_ != 255)
            gl.glBlendFunc(CCMacros.CC_BLEND_SRC, CCMacros.CC_BLEND_DST);

        // Clear the vertex and color arrays
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

    }


    public CCColor3B getColor() {
        return new CCColor3B(color_.r, color_.g, color_.b);
    }

    // Color Protocol
    public void setColor(CCColor3B color) {
        color_.r = color.r;
        color_.g = color.g;
        color_.b = color.b;
        updateColor();
    }

    // Opacity Protocol

    public void setOpacity(int o) {
        opacity_ = o;
        updateColor();
    }

    public int getOpacity() {
        return opacity_;
    }

    // Size protocol

    @Override
    public float getWidth() {
        return squareVertices.get(2);
    }

    @Override
    public float getHeight() {
        return squareVertices.get(5);
    }

    @Override
    public void setContentSize(float w, float h) {
        squareVertices.put(2, w);
        squareVertices.put(5, h);
        squareVertices.put(6, w);
        squareVertices.put(7, h);

        super.setContentSize(w, h);
    }

    public void changeWidthAndHeight(float w, float h) {
        setContentSize(w, h);
    }

    public void changeWidth(float w) {
        setContentSize(w, getHeight());
    }

    public void changeHeight(float h) {
        setContentSize(getWidth(), h);
    }


    static class ColorLayerInitException extends RuntimeException {
        public ColorLayerInitException(String reason) {
            super(reason);
        }
    }
}

