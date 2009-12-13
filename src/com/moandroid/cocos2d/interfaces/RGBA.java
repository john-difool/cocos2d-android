package com.moandroid.cocos2d.interfaces;

import com.moandroid.cocos2d.types.ccRGBB;


//Corresponds to CCProtocols.h => CCRGBAProtocol 
public interface RGBA {
	public void setColor(ccRGBB color);
	public ccRGBB getColor();
	
	public char getOpacity();
	
}

