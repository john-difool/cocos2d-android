package org.cocos2d.tests;

import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.TextureManager;
import org.cocos2d.particlesystem.ParticleFire;
import org.cocos2d.particlesystem.ParticleSystem;

public class ParticleDemo extends Layer {

    static final int kTagLabelAtlas = 1;
    static final int kTagEmitter = 2;

    public void step(float dt) {
        ParticleSystem emitter = (ParticleSystem) getChild(kTagEmitter);
    }

    @Override
    public void onEnter() {
        super.onEnter();
        ParticleSystem emitter = ParticleFire.node();
        addChild(emitter, 0, kTagEmitter);

        emitter.setTexture(TextureManager.sharedTextureManager().addImage("fire.png"));
        emitter.setPosition(emitter.getPositionX(), 100);
    }
}
