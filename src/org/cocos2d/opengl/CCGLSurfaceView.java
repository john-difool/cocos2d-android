package org.cocos2d.opengl;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import org.cocos2d.nodes.Director;

public class CCGLSurfaceView extends GLSurfaceView {

    private static final String LOG_TAG = CCGLSurfaceView.class.getSimpleName();
    private Director mRenderer;
    public Display frame;

    public CCGLSurfaceView(Context context) {
        super(context);

//        setGLWrapper(new GLDebugWrapper());

//        mRenderer = new CCGLImageRenderer(context, bitmap);

//        GLSurfaceView.Renderer renderer = new CCGLSpriteRenderer(context, "background1.png");
//        setRenderer(renderer);

//        mRenderer = new CCGLSquareRenderer(context);

        mRenderer = Director.sharedDirector();
        Director.me = (Activity) context;

        setRenderer(mRenderer);

        WindowManager w = ((Activity) context).getWindowManager();
        frame = w.getDefaultDisplay();

        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_CANCEL:
                mRenderer.touchesCancelled(event);
                break;
            case MotionEvent.ACTION_DOWN:
                mRenderer.touchesBegan(event);
                break;
            case MotionEvent.ACTION_MOVE:
                mRenderer.touchesMoved(event);
                break;
            case MotionEvent.ACTION_UP:
                mRenderer.touchesEnded(event);
                break;
        }

        synchronized (Director.sharedDirector()) {
            try {
                Director.sharedDirector().wait(20L);
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
        return true;
    }

}
