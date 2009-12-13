package com.moandroid.cocos2d.nodes;

import java.lang.reflect.Method;

import com.moandroid.cocos2d.interfaces.RGBA;
import com.moandroid.cocos2d.interfaces.Texture;
import com.moandroid.cocos2d.opengles.Texture2D;
import com.moandroid.cocos2d.types.ccRGBB;

public class TextureNode extends CocosNode implements RGBA, Texture{

	private Texture2D texture;
	private Method blendFunc;
	private char opacity;
	private ccRGBB color;
	
	@Override
	public ccRGBB getColor() {
		return color;
	}

	@Override
	public char getOpacity() {
		return opacity;
	}

	@Override
	public void setColor(ccRGBB color) {
		this.color = color;
	}

	@Override
	public Texture2D getTexture() {
		return texture;
	}

	@Override
	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}

	@Override
	public Method getBlendFunc() {
		return blendFunc;
	}

	@Override
	public void setBlendFunc(Method blendFunc) {
		// TODO: We should verify that this method is valid
		this.blendFunc = blendFunc;
	}
	
}
