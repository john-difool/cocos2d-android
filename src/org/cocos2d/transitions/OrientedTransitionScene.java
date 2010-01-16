package org.cocos2d.transitions;

import org.cocos2d.nodes.Scene;

/**
 * A Transition that supports orientation like.
 * Possible orientation: LeftOver, RightOver, UpOver, DownOver
 */
public class OrientedTransitionScene extends TransitionScene {
    int orientation;

    /**
     * creates a base transition with duration and incoming scene
     */
    public OrientedTransitionScene() {
        throw new RuntimeException("Don't call this!!!");
    }

    /**
     * initializes a transition with duration and incoming scene
     */
    public OrientedTransitionScene(float t, Scene s, int o) {
        super(t, s);
        orientation = o;
    }
}
