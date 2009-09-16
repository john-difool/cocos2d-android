package com.moandroid.cocos2d.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.moandroid.cocos2d.Camera;
import com.moandroid.cocos2d.action.Action;
import com.moandroid.cocos2d.effects.GridBase;
import com.moandroid.cocos2d.types.AffineTransform;
import com.moandroid.cocos2d.types.ccSelector;
import com.moandroid.cocos2d.types.ccSize;
import com.moandroid.cocos2d.types.ccTime;

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
	
	public void removeChild(CocosNode node, boolean cleanup){
		
	}
	
	public void removeChildrenWithCleanup(boolean cleanup){
		
	}
	
	public CocosNode getChildByTag(int tag){
		return null;
	}
	
	public void reorderChild(CocosNode child,int zOrder){
		
	}
	
	public void draw(){
		
	}
	
	public void visit(){
		
	}
	
	public void transform(){
		
	}
	
	public void transformAncestors(){
		
	}
	
	public Action runAction(Action action){
		return null;
	}
	
	public void stopAllActions(){
		
	}
	
	public void stopAction(Action action){
		
	}
	
	public void stopActionByTag(int tag){
		
	}
	
	public Action getActionByTag(int tag){
		return null;
	}
	
	public int numberOfRunningActions(){
		return 0;
	}
	
	public void schedule(ccSelector s){
		
	}
	
	public void schedual(ccSelector s, ccTime seconds){
		
	}
	
	public void unschedule(ccSelector s){
		
	}
	
	public AffineTransform nodeToWorldTransform(){
		return null;
	}
	
	public AffineTransform worldToNodeTransform(){
		return null;
	}
	
	public PointF convertToNodeSpace(PointF worldPoint){
		return null;
	}
	
	public PointF convertToWorldSpace(PointF nodePoint){
		return null;
	}
	
	public PointF convertToWorldSpaceAR(PointF nodePoint){
		return null;
	}
	
	public PointF convertTouchToNodeSpace(MotionEvent event){
		return null;
	}
	
	public PointF convertTouchToNodeSpaceAR(MotionEvent event){
		return null;
	}
	
	public interface CocosNodeOpacity {
		public byte opacity();
		public void setOpacity(byte opacity);
	}
	
	public interface CocosNodeSize{
		public ccSize contentSize();
	}
	
	public interface CocosNodeRGB{
		public void setRGB(byte r, byte g, byte b);
		public byte r();
		public byte g();
		public byte b();
	}
	
	public interface CocosAnimation{
		public ArrayList<Object> frames();
		public float delay();
		public String name();
	}
	
	public interface CocosNodeFrames{
		public void setDisplayFrame(Object newFrame);
		public void setDisplayFrame(String animationName, int frameIndex);
		public boolean isFrameDisplayed(Object frame);
		public Object displayFrame();
		public CocosAnimation animationByName(String animationName);
		public void addAnimation(CocosAnimation animation);
		public void setAutoCenterFrames(boolean autoCenterFrames);
	}
	
	private void step_(ccTime dt){
		
	}
	
	private void activateTimers(){
		
	}
	
	private void deactivateTimers(){
		
	}
}
