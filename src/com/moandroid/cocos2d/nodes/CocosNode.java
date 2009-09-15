package com.moandroid.cocos2d.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.PointF;

import com.moandroid.cocos2d.Camera;
import com.moandroid.cocos2d.effects.GridBase;

public class CocosNode {
	public float rotation;
	public float scaleX;
	public float scaleY;
	public PointF position;
	public float parallaxRationX;
	public float parallaxRationY;
	public boolean visible;
	public Camera camera;
	GridBase grid;
	public int zOrder;
	public boolean relativeTransformAnchor;
	public PointF transformAnchor;
	public ArrayList<Object> children;
	boolean isRuning;
	public CocosNode parent;
	public int tag;
	ArrayList<Object> actions;
	ArrayList<Object> actionsToRemove;
	ArrayList<Object> actionsToAdd;
	HashMap<String,Object> scheduledSelectors;
	
	public static CocosNode node(){
		return null;
	}
	
	public CocosNode(){
		
	}
	
	public void onEnter(){
	
	}
	
	public void onExit(){
		
	}
	
	public CocosNode addChild(CocosNode node){
		return null;
	}
	
	public CocosNode addChild(CocosNode node, int z){
		return null;
	}
	
	public CocosNode addChild(CocosNode node, int z, int tag){
		return null;
	}
	
	public CocosNode addChild(CocosNode node, int z, PointF parallaxRatio){
		return null;
	}
}
