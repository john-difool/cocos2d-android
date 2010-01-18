package org.cocos2d.tests;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import org.cocos2d.actions.base.RepeatForever;
import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.menus.Menu;
import org.cocos2d.menus.MenuItemImage;
import org.cocos2d.nodes.*;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.opengl.Texture2D;
import org.cocos2d.types.CCSize;

public class ParallaxTest extends Activity {
    private static final String LOG_TAG = ParallaxTest.class.getSimpleName();
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


    static int sceneIdx = -1;
    static Class transitions[] = {
            Parallax1.class,
            Parallax2.class,
    };

    static Layer nextAction() {

        sceneIdx++;
        sceneIdx = sceneIdx % transitions.length;

        return restartAction();
    }

    static Layer backAction() {
        sceneIdx--;
        int total = transitions.length;
        if (sceneIdx < 0)
            sceneIdx += total;
        return restartAction();
    }

    static Layer restartAction() {
        try {
            Class c = transitions[sceneIdx];
            return (Layer) c.newInstance();
        } catch (Exception e) {
            return null;
        }
    }


    static class ParallaxDemo extends Layer {
        public ParallaxDemo() {

            CCSize s = Director.sharedDirector().winSize();


            MenuItemImage item1 = MenuItemImage.item("b1.png", "b2.png", this, "backCallback");
            MenuItemImage item2 = MenuItemImage.item("r1.png", "r2.png", this, "restartCallback");
            MenuItemImage item3 = MenuItemImage.item("f1.png", "f2.png", this, "nextCallback");

            Menu menu = Menu.menu(item1, item2, item3);
            menu.setPosition(0, 0);
            item1.setPosition(s.width / 2 - 100, 30);
            item2.setPosition(s.width / 2, 30);
            item3.setPosition(s.width / 2 + 100, 30);
            addChild(menu, 1);
        }


        public static void restartCallback() {
            Scene s = Scene.node();
            s.addChild(restartAction());
            Director.sharedDirector().replaceScene(s);
        }

        public void nextCallback() {
            Scene s = Scene.node();
            s.addChild(nextAction());
            Director.sharedDirector().replaceScene(s);
        }

        public void backCallback() {
            Scene s = Scene.node();
            s.addChild(backAction());
            Director.sharedDirector().replaceScene(s);
        }

        public String title() {
            return "No title";
        }
    }


    static class Parallax1 extends ParallaxDemo {
        public Parallax1() {
            // Top Layer, a simple image
            Sprite cocosImage = Sprite.sprite("powered.png");
            // scale the image (optional)
            cocosImage.scale(2.5f);
            // change the transform anchor point to 0,0 (optional)
            cocosImage.setAnchorPoint(0, 0);
            // position the image somewhere (optional)
            cocosImage.setPosition(200, 1000);

            // Aliased images
            Texture2D.saveTexParameters();
            Texture2D.setAliasTexParameters();

            // Middle layer: a Tile map atlas
            TileMapAtlas tilemap = new TileMapAtlas("tiles.png", "levelmap.tga", 16, 16);

            // change the transform anchor to 0,0 (optional)
            tilemap.setAnchorPoint(0, 0);
            // position the tilemap (optional)
            tilemap.setPosition(0, -200);

            Texture2D.restoreTexParameters();

            // background layer: another image
            Sprite background = Sprite.sprite("background.png");
            // scale the image (optional)
            background.scale(1.5f);
            // change the transform anchor point (optional)
            background.setAnchorPoint(0, 0);


            // create a void node, a parent node
            CocosNode voidNode = CocosNode.node();

            // NOW add the 3 layers to the 'void' node

            // background image is moved at a ratio of 0.4x, 0.5y
//            voidNode.addChild(background, -1, 0.4f, 0.5f);

            // tiles are moved at a ratio of 2.2x, 1.0y
//            voidNode.addChild(tilemap, 1, 2.2f, 1.0f);

            // top image is moved at a ratio of 3.0x, 2.5y
//            voidNode.addChild(cocosImage, 2, 3.0f, 2.5f);


            // now create some actions that will move the 'void' node
            // and the children of the 'void' node will move at different
            // speed, thus, simulation the 3D environment
            IntervalAction goUp = MoveBy.action(4, 0, -500);
            IntervalAction goDown = goUp.reverse();
            IntervalAction go = MoveBy.action(8, -1000, 0);
            IntervalAction goBack = go.reverse();
            IntervalAction seq = Sequence.actions(
                    goUp,
                    go,
                    goDown,
                    goBack);
            voidNode.runAction(RepeatForever.action(seq));

            addChild(voidNode);
        }

        public String title() {
            return "Parallax: Parent and 3 children";
        }
    }

    static class Parallax2 extends ParallaxDemo {
        public Parallax2() {
            // Aliased images
            Texture2D.saveTexParameters();
            Texture2D.setAliasTexParameters();

            // this node will be used as the parent (reference) for the parallax scroller
            TileMapAtlas tilemap = new TileMapAtlas("tiles.png", "levelmap.tga", 16, 16);

            Texture2D.restoreTexParameters();

            tilemap.setAnchorPoint(0, 0);
            tilemap.setPosition(0, -200);

            Sprite background = Sprite.sprite("background.png");
            background.scale(1.5f);
            background.setAnchorPoint(0, 0);

            // the parent contains data. The parent moves at (1,1)
            // while the child moves at the ratio of (0.4, 0.5)
//            tilemap.addChild(background, -1, 0.4f,0.5f);

            IntervalAction goUp = MoveBy.action(2, -1000, -500);
            IntervalAction goDown = goUp.reverse();
            IntervalAction seq = Sequence.actions(
                    goUp,
                    goDown);
            tilemap.runAction(RepeatForever.action(seq));

            addChild(tilemap);

        }

        public String title() {
            return "Parallax: Parent & child";
        }
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
        scene.addChild(nextAction());

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

        TextureManager.sharedTextureManager().removeAllTextures();
    }
}
