package com.moandroid.cocos2d.types;

public class ccGridSize{
    
	public static ccGridSize ccg(int x, int y){
		return new ccGridSize(x,y);
	}
	
	public int x;
    public int y;
    
    public ccGridSize(int x, int y){
        this.x = x;
        this.y = y;
    }
}