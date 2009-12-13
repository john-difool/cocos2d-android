package com.moandroid.cocos2d.interfaces;

import com.moandroid.cocos2d.opengles.Texture2D;


//Corresponds to CCProtocols.h => CCTextureProtocol  
public interface Texture extends BlendFunc{

	public Texture2D getTexture();
	public void setTexture(Texture2D texture);
	
}
