package org.cocos2d.transitions;

import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;

/**
 * SlideInB Transition.
 * Slide in the incoming scene from the bottom border.
 */
public class SlideInBTransition extends SlideInLTransition {

    public SlideInBTransition(float t, Scene s) {
        super(t, s);
    }

    /**
     * initializes the scenes
     */
    protected void initScenes() {
        inScene.setPosition(0, Director.sharedDirector().winSize().height);
    }
}
