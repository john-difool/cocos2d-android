package org.cocos2d.nodes;

public class Scene extends CocosNode {

    public static Scene node() {
        return new Scene();
    }

    protected Scene() {
        setRelativeAnchorPoint(false);

        setTransformAnchor(Director.sharedDirector().winSize().width / 2,
                Director.sharedDirector().winSize().height / 2);
    }
}