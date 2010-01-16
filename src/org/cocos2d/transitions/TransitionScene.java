package org.cocos2d.transitions;

import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;

public class TransitionScene extends Scene {

    protected static final int kSceneFade = 0xFADEFADE;


    /**
     * Orientation Type used by some transitions
     */
    public interface Orientation {
        /// An horizontal orientation where the Left is nearer
        public static int kOrientationLeftOver = 0;
        /// An horizontal orientation where the Right is nearer
        public static int kOrientationRightOver = 1;
        /// A vertical orientation where the Up is nearer
        public static int kOrientationUpOver = 0;
        /// A vertical orientation where the Bottom is nearer
        public static int kOrientationDownOver = 1;
    }

    /**
     * Base class for Transition scenes
     */
    protected Scene inScene;
    protected Scene outScene;
    protected float duration;

    /**
     * creates a base transition with duration and incoming scene
     */
    public static TransitionScene transition(float t, Scene s) {
        return new TransitionScene(t, s);
    }

    public TransitionScene() {
        throw new RuntimeException("Don't call this!!!");
    }

    /**
     * initializes a transition with duration and incoming scene
     */
    public TransitionScene(float t, Scene s) {
        assert s != null : "Argument scene must be non-null";

        duration = t;

        // Don't retain them, it will be reatined when added
        inScene = s;
        outScene = Director.sharedDirector().runningScene();

        if (inScene == outScene) {
            throw new TransitionWithInvalidSceneException("Incoming scene must be different from the outgoing scene");
        }

        // disable events while transitions
        Director.sharedDirector().setEventsEnabled(false);

        addScenes();

    }

    protected void addScenes() {
        // add both scenes
        addChild(inScene, 1);
        addChild(outScene, 0);
    }

    public void step(float dt) {

        unschedule("_cmd");

        Director.sharedDirector().replaceScene(inScene);

        // enable events while transitions
        Director.sharedDirector().setEventsEnabled(true);

        // issue #267
        outScene.setVisible(true);

        removeChild(inScene, false);
        removeChild(outScene, false);

        inScene = null;
        outScene = null;
    }

    /**
     * called after the transition finishes
     */
    public void finish() {
        /* clean up */
        inScene.setVisible(true);
        inScene.setPosition(0, 0);
        inScene.scale(1.0f);
        inScene.setRotation(0.0f);
        inScene.getCamera().restore();

        outScene.setVisible(false);
        outScene.setPosition(0, 0);
        outScene.scale(1.0f);
        outScene.setRotation(0.0f);
        outScene.getCamera().restore();

        //	inScene.stopAllActions();
        //	outScene.stopAllActions();

        schedule("step", 0);
    }

    /**
     * used by some transitions to hide the outter scene
     */
    public void hideOutShowIn() {
        inScene.setVisible(true);
        outScene.setVisible(false);
    }

}

class TransitionWithInvalidSceneException extends RuntimeException {
    public TransitionWithInvalidSceneException(String reason) {
        super(reason);
    }
}