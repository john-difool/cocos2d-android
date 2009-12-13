package com.moandroid.cocos2d.interfaces;

import java.lang.reflect.Method;


// Corresponds to CCProtocols.h => CCBlendProtocol
public interface BlendFunc {
	void setBlendFunc(Method blendFunc);
	Method getBlendFunc();
}
