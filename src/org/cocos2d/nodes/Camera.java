package org.cocos2d.nodes;

import android.opengl.GLU;
import org.cocos2d.types.CCSize;
import org.cocos2d.utils.CCFormatter;

import javax.microedition.khronos.opengles.GL10;

public class Camera {
    private float eyeX;
    private float eyeY;
    private float eyeZ;

    private float centerX;
    private float centerY;
    private float centerZ;

    private float upX;
    private float upY;
    private float upZ;

    public boolean dirty;

    public boolean getDirty() {
        return dirty;
    }

    public void setDirty(boolean value) {
        dirty = value;
    }

    public Camera() {
        restore();
    }

    public String toString() {
        return new CCFormatter().format("<%s = %08X | center = (%.2f,%.2f,%.2f)>", this.getClass(), this, centerX, centerY, centerZ);
    }


    public void restore() {
        CCSize s = Director.sharedDirector().displaySize();

        eyeX = s.width / 2;
        eyeY = s.height / 2;
        eyeZ = Camera.getZEye();

        centerX = s.width / 2;
        centerY = s.height / 2;
        centerZ = 0.0f;

        upX = 0.0f;
        upY = 1.0f;
        upZ = 0.0f;

        dirty = false;
    }

    public void locate(GL10 gl) {
        if (dirty) {
            boolean landscape = Director.sharedDirector().landscape();

            gl.glLoadIdentity();

            if (landscape)
                gl.glRotatef(-90, 0, 0, 1);

            GLU.gluLookAt(gl, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);

            if (landscape) {
//           if (LANDSCAPE_LEFT)
                gl.glTranslatef(-80, 80, 0);
//            else
// TODO FIX ME
            }
        }
    }

    public static float getZEye() {
        CCSize s = Director.sharedDirector().displaySize();
        return (s.height / 1.1566f);
    }

    public void setEye(float x, float y, float z) {
        eyeX = x;
        eyeY = y;
        eyeZ = z;
        dirty = true;
    }

    public void setCenter(float x, float y, float z) {
        centerX = x;
        centerY = y;
        centerZ = z;
        dirty = true;
    }

    public void setUp(float x, float y, float z) {
        upX = x;
        upY = y;
        upZ = z;
        dirty = true;
    }

    public void getEye(float x[], float y[], float z[]) {
        x[0] = eyeX;
        y[0] = eyeY;
        z[0] = eyeZ;
    }

    public void getCenter(float x[], float y[], float z[]) {
        x[0] = centerX;
        y[0] = centerY;
        z[0] = centerZ;
    }

    public void getUp(float x[], float y[], float z[]) {
        x[0] = upX;
        y[0] = upY;
        z[0] = upZ;
    }

}
