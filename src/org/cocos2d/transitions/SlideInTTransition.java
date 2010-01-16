package org.cocos2d.transitions;

import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.types.CCSize;

/**
 * SlideInT Transition.
 * Slide in the incoming scene from the top border.
 */
public class SlideInTTransition extends SlideInLTransition {

    public SlideInTTransition(float t, Scene s) {
        super(t, s);
    }

    /**
     * initializes the scenes
     */
    protected void initScenes() {
        CCSize s = Director.sharedDirector().winSize();
        inScene.setPosition(0, -s.height);
    }
}
