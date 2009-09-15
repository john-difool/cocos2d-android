package com.moandroid.cocos2d.types;

public class ccTime{
    public float time;
    
    public ccTime(float time){
        this.time = time;
    }
    
    public ccTime multiply(ccTime dt){
    	this.time *= dt.time;
    	return this;
    }
    
    public ccTime multiply(float dt){
    	this.time *= dt;
    	return this;
    }
}