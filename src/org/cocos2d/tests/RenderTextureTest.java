package org.cocos2d.tests;

import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.RenderTexture;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.nodes.TextureManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import org.cocos2d.actions.ActionManager;
import org.cocos2d.actions.base.RepeatForever;
import org.cocos2d.actions.interval.*;
import org.cocos2d.layers.ColorLayer;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.*;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.tests.ClickAndMoveTest.MainLayer;
import org.cocos2d.types.CCColor4B;
import org.cocos2d.types.CCMacros;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;
import org.cocos2d.events.TouchDispatcher;

public class RenderTextureTest extends Activity {
    private static final String LOG_TAG = RenderTextureTest.class.getSimpleName();

    private static final boolean DEBUG = true;

    private CCGLSurfaceView mGLSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mGLSurfaceView = new CCGLSurfaceView(this);
        setContentView(mGLSurfaceView);
    }

    @Override
    public void onStart() {
        super.onStart();

        // attach the OpenGL view to a window
        Director.sharedDirector().attachInView(mGLSurfaceView);

        // set landscape mode
        Director.sharedDirector().setLandscape(false);

        // show FPS
        Director.sharedDirector().setDisplayFPS(true);

        // frames per second
        Director.sharedDirector().setAnimationInterval(1.0f / 60);

        Scene scene = Scene.node();
        scene.addChild(new RenderTextureLayer(), 2);

        // Make the Scene active
        Director.sharedDirector().runWithScene(scene);

    }

    @Override
    public void onPause() {
        super.onPause();

        Director.sharedDirector().pause();
    }

    @Override
    public void onResume() {
        super.onResume();

        Director.sharedDirector().resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ActionManager.sharedManager().removeAllActions();
        TextureManager.sharedTextureManager().removeAllTextures();
    }
    
    static class RenderTextureLayer extends Layer {
        static final int kTagSprite = 1;
    	RenderTexture target;
    	Sprite brush;

        public RenderTextureLayer() {
            CCSize s = Director.sharedDirector().winSize();
            Label label = Label.label("Render Texture Test", "DroidSans", 18);
            label.setPosition(s.width / 2, s.height - 30);
            addChild(label);
            
            target = RenderTexture.renderTexture((int)s.width, (int)s.height);
            addChild(target, 1);
            
            brush = Sprite.sprite("stars.png");
            brush.setOpacity(20);
            isTouchEnabled_ = true;
        }

        CCPoint previousLocation = CCPoint.zero();
        
        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            CCPoint convertedLocation = Director.sharedDirector().convertCoordinate(event.getX(), event.getY());
            CCPoint start = convertedLocation;
            CCPoint end = previousLocation;
            
         // begin drawing to the render texture
        	target.begin();

        	// for extra points, we'll draw this smoothly from the last position and vary the sprite's
        	// scale/rotation/offset
        	float distance = CCPoint.ccpDistance(start, end);
        	if (distance > 1)
        	{
        		int d = (int)distance;
        		for (int i = 0; i < d; i++)
        		{
        			float difx = end.x - start.x;
        			float dify = end.y - start.y;
        			float delta = (float)i / distance;
        			brush.setPosition(start.x + (difx * delta), start.y + (dify * delta));
        			brush.setRotation((float)(Math.random()*360));
        			float r = ((float)(Math.random()*50)/50.f) + 0.25f;
        			brush.setScale(r);
        			// Call visit to draw the brush, don't call draw!
        			//brush.visit();
        		}
        	}
        	// finish drawing and return context back to the screen
        	target.end();

        	previousLocation = convertedLocation;
            return TouchDispatcher.kEventHandled;
        }

    }
}
