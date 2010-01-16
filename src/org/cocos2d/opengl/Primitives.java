package org.cocos2d.opengl;

import android.util.FloatMath;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCRect;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Primitives {

    public static void drawPoint(GL10 gl, CCPoint point) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * 1);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertices = vbb.asFloatBuffer();

        vertices.put(point.x);
        vertices.put(point.y);
        vertices.position(0);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDrawArrays(GL10.GL_POINTS, 0, 1);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    public static void drawPoints(GL10 gl, CCPoint points[], int numberOfPoints) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * numberOfPoints);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertices = vbb.asFloatBuffer();

        for (int i = 0; i < numberOfPoints; i++) {
            vertices.put(points[i].x);
            vertices.put(points[i].y);
        }
        vertices.position(0);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDrawArrays(GL10.GL_POINTS, 0, numberOfPoints);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }


    public static void drawLine(GL10 gl, CCPoint origin, CCPoint destination) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * 2);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertices = vbb.asFloatBuffer();

        vertices.put(origin.x);
        vertices.put(origin.y);
        vertices.put(destination.x);
        vertices.put(destination.y);
        vertices.position(0);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDrawArrays(GL10.GL_LINES, 0, 2);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    public static void drawRect(GL10 gl, CCRect rect) {
        CCPoint[] poli = new CCPoint[4];

        poli[0] = CCPoint.ccp(rect.origin.x, rect.origin.y);
        poli[1] = CCPoint.ccp(rect.origin.x + rect.size.width, rect.origin.y);
        poli[2] = CCPoint.ccp(rect.origin.x + rect.size.width, rect.origin.y + rect.size.height);
        poli[3] = CCPoint.ccp(rect.origin.x, rect.origin.y + rect.size.height);

        drawPoly(gl, poli, poli.length, true);
    }

    public static void drawPoly(GL10 gl, CCPoint poli[], int numberOfPoints, boolean closePolygon) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * numberOfPoints);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertices = vbb.asFloatBuffer();

        for (int i = 0; i < numberOfPoints; i++) {
            vertices.put(poli[i].x);
            vertices.put(poli[i].y);
        }
        vertices.position(0);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        if (closePolygon)
            gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, numberOfPoints);
        else
            gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, numberOfPoints);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    public static void drawCircle(GL10 gl, CCPoint center, float r, float a, int segments, boolean drawLineToCenter) {

        ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * (segments + 2));
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertices = vbb.asFloatBuffer();

        int additionalSegment = 1;

        if (drawLineToCenter)
            additionalSegment++;

        final float coef = 2.0f * (float) Math.PI / segments;

        for (int i = 0; i <= segments; i++) {
            float rads = i * coef;
            float j = (float) (r * FloatMath.cos(rads + a) + center.x);
            float k = (float) (r * FloatMath.sin(rads + a) + center.y);

            vertices.put(j);
            vertices.put(k);
        }
        vertices.put(center.x);
        vertices.put(center.y);
        vertices.position(0);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, segments + additionalSegment);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

}
