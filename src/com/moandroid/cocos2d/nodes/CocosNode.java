package com.moandroid.cocos2d.nodes;

import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.PointF;
import android.view.MotionEvent;

import com.moandroid.cocos2d.Camera;
import com.moandroid.cocos2d.action.Action;
import com.moandroid.cocos2d.action.Action.CocosActionTag;
import com.moandroid.cocos2d.effects.GridBase;
import com.moandroid.cocos2d.types.AffineTransform;
import com.moandroid.cocos2d.types.ccSelector;
import com.moandroid.cocos2d.types.ccSize;
import com.moandroid.cocos2d.types.ccTime;

public class CocosNode {
	public float rotation;
	
	public float getRotation(){
		return rotation;
	}
	
	public void setRotation(float rotation){
		this.rotation = rotation;
	}
	
	public float scaleX;
	public float getScaleX(){
		return scaleX;
	}
	public void setScaleX(float scaleX){
		this.scaleX = scaleX;
	}
	
	public float scaleY;
	public float getScaleY(){
		return scaleY;
	}
	public void setScaleY(float scaleY){
		this.scaleY = scaleY;
	}
	
	public PointF position;
	public PointF getPosition(){
		return position;
	}
	public void SetPosition(PointF position){
		this.position = position;
	}
	
	private float parallaxRatioX;
	public float getParallaxRatioX(){
		if(parallaxRatioX == parallaxRatioY){
			return parallaxRatioX;
		}
		else
			System.out.print("CocosNode parallaxRatio Error: " +
					"parallaxRatioX is different from parallaxRatioY");
		return 0;
	}
	public void setParallaxRatioX(float parallaxRatioX){
		this.parallaxRatioX = this.parallaxRatioY = parallaxRatioX;
	}
	
	public float parallaxRatioY;
	public float getParallaxRatioY(){
		return parallaxRatioY;
	}
	public void setParallaxRatioY(float parallaxRationY){
		this.parallaxRatioY = parallaxRationY;
	}

	
	private Camera camera;
	public Camera getCamera(){
		if(camera == null)
			camera = new Camera();
		return camera;
	}
	
	public GridBase grid;
	public GridBase getGrid(){
		return grid;
	}
	
	public void setGrid(GridBase grid){
		this.grid = grid;
	}
	
	public boolean visible;
	public boolean getVisible(){
		return visible;
	}
	public void setVisible(boolean isVisible){
		this.visible = isVisible;
	}
	
	public PointF transformAnchor;
	public PointF getTransformAnchor(){
		return transformAnchor;
	}
	public void setTransformAnchor(PointF transformAnchor){
		this.transformAnchor = transformAnchor;
	}
	
	public CocosNode parent;
	public CocosNode getParent(){
		return parent;
	}
	public void setParent(CocosNode parent){
		this.parent = parent;
	}
	public boolean relativeTransformAnchor;
	public CocosNodeTag tag;
	
	private int zOrder;
	public int getZOrder(){
		return zOrder;
	}
	
	
	
	private ArrayList<CocosNode> children;
	public ArrayList<CocosNode> getChildren(){
		return children;
	}
	boolean isRunning;
	
	
	ArrayList<Object> actions;
	ArrayList<Object> actionsToRemove;
	ArrayList<Object> actionsToAdd;
	HashMap<String,Object> scheduledSelectors;
	
	public static CocosNode node(){
		return new CocosNode();
	}
	
	public CocosNode(){
		isRunning = false;
		position = new PointF(0,0);
		rotation = 0.0f;
		scaleX = 1.0f;
		scaleY = 1.0f;
		parallaxRatioX = 1.0f;
		parallaxRatioY = 1.0f;
		grid = null;
		visible = true;
		transformAnchor = new PointF(0,0);
		tag = CocosNodeTag.kCocosNodeTagInvalid;
		zOrder = 0;
		camera = null;
		children = null;
		actions = null;
		actionsToRemove = null;
		actionsToAdd = null;
		scheduledSelectors = null;
		relativeTransformAnchor = true;
	}
	
	public void onEnter(){
		for(int i=0;i<children.size();i++){
			children.get(i).onEnter();
		}
		this.activateTimers();
		isRunning = true;
	}
	
	public void onExit(){
		this.deactivateTimers();
		isRunning = false;
		for(int i=0; i<children.size();i++){
			children.get(i).onExit();
		}
	}
		
	public CocosNode addChild(CocosNode child, int z, CocosNodeTag tag){
		assert child != null;
		assert child.parent == null;
		if(children == null){
			childrenAlloc();
		}
		this.insertChild(child, z);
		child.tag = tag;
		child.setParent(this);
		if(isRunning){
			child.onEnter();
		}
		return this;
	}
	
	public CocosNode addChild(CocosNode child, int z, PointF parallaxRatio){
		assert child != null;
		child.parallaxRatioX = parallaxRatio.x;
		child.parallaxRatioY = parallaxRatio.y;
		return addChild(child,z,child.tag);
	}
	
	public CocosNode addChild(CocosNode child, int z){
		assert child != null;
		return addChild(child,z,child.tag);
	}
	
	public CocosNode addChild(CocosNode child){
		assert child != null;
		return addChild(child,child.zOrder,child.tag);
	}
	
	public void removeChild(CocosNode child, boolean cleanup){
		if(child == null)
			return;
		if(children.contains(child));
			detachChild(child,cleanup);
	}
	public void removChildByTag(CocosNodeTag tag,boolean cleanup){
		assert tag != CocosNodeTag.kCocosNodeTagInvalid;
		CocosNode child = getChildByTag(tag);
		if(child == null)
			System.out.print("removeChildByTag:child not fount!");
		else
			removeChild(child,cleanup);
	}
	
	public void removeAllChildrenWithCleanup(boolean cleanup){
		for(int i=0;i<children.size();i++){
			CocosNode current = children.get(i);
			current.cleanup();
			current.setParent(null);
			if(isRunning){
				current.onExit();
			}		
		}
		for(int i=children.size()-1;i>=0;i--){
			children.remove(i);
		}
	}
	
	public CocosNode getChildByTag(CocosNodeTag tag){
		assert tag != CocosNodeTag.kCocosNodeTagInvalid: "Invalid tag";
		for(int i=0; i<children.size(); i++){
			CocosNode current = children.get(i);
			if(current.tag == tag){
				return current;
			}
		}
		return null;
	}
	
	private PointF absolutePosition(){
		PointF  ret = new PointF(position.x, position.y);
		CocosNode cn = this;
		while(cn.parent != null){
			cn = cn.parent;
			ret.x+=cn.position.x;
			ret.y+=cn.position.y;
		}
		return ret;
	}
	
	public void reorderChild(CocosNode child,int zOrder){
		assert child != null:"Child must be non-nil";
		this.insertChild(child, zOrder);
		children.remove(child);
	}
	
	public void draw(){
		
	}
	
	public void visit(GL10 gl){
		if(visible == false){
			return;
		}
		gl.glPushMatrix();
		if(grid != null && grid.active){
			grid.beforeDraw();
			this.transformAncestors(gl);
		}
		
		this.transform(gl);
		for(int i=0;i<children.size();i++){
			CocosNode current = children.get(i);
			if(current.zOrder <0){
				current.visit(gl);
			}
			else
				break;
		}
		this.draw();
		
		for(int i=0;i<children.size();i++){
			CocosNode current = children.get(i);
			if(current.zOrder >= 0){
				current.visit(gl);
			}
		}
		
		if(grid != null && grid.active){
			grid.afterDraw(this.camera);
		}
		gl.glPopMatrix();
	}
	
	public void transform(GL10 gl){
		if((grid!=null && grid.active)== false)
			camera.locate();
		float parallaxOffsetX = 0;
		float parallaxOffsetY = 0;
		if((parallaxRatioX != 1.0f || parallaxRatioY !=1.0) && parent!=null){
			parallaxOffsetX = -parent.position.x + parent.position.x*parallaxRatioX;
			parallaxOffsetY = -parent.position.y + parent.position.y*parallaxRatioY;
		}
		if(relativeTransformAnchor &&
				(transformAnchor.x !=0 || transformAnchor.y !=0)){
			gl.glTranslatef(-transformAnchor.x+parallaxOffsetX, 
					-transformAnchor.y+parallaxOffsetY, 0);
		}
		if(transformAnchor.x !=0 || transformAnchor.y !=0){
			gl.glTranslatef(position.x+transformAnchor.x+parallaxOffsetX,
					position.y+transformAnchor.y+parallaxOffsetY, 0);
		}else if(position.x !=0 || position.y !=0 || parallaxOffsetX !=0 || parallaxOffsetY !=0){
			gl.glTranslatef(position.x+parallaxOffsetX, position.y+parallaxOffsetY, 0);
		}
		
		if(rotation != 0.0f){
			gl.glRotatef(-rotation, 0.0f, 0.0f, 1.0f);
		}
		if(scaleX != 1.0f || scaleY != 1.0f){
			gl.glScalef(scaleX, scaleY, 1.0f);
		}
		if(transformAnchor.x !=0 || transformAnchor.y != 0.0f){
			gl.glTranslatef(-transformAnchor.x+parallaxOffsetX,
					-transformAnchor.y+parallaxOffsetY, 0);
		}
	}
	
	public void transformAncestors(GL10 gl){
		if(this.parent != null){
			this.parent.transformAncestors(gl);
			this.parent.transform(gl);
		}
	}
	
	public Action runAction(Action action){
		assert action != null:"Argument must be non-mil";
		assert !actionsToAdd.contains(action):"Action already sheduled to run";
		if(actions.contains(action)){
			assert actionsToRemove.contains(action):"Action already running";
			actionsToRemove.remove(action);
			System.out.print("runAction:Action saved from removal");
			return action;
		}
		action.target = this;
		action.start();
		if(actionsToAdd==null){
			this.actionAlloc();
		}
		actionsToAdd.add(action);
		this.schedule(new ccSelector(this,"step_"));
		return action;
	}
	
	public void stopAllActions(){
		for(int i=actionsToAdd.size()-1;i>=0;i--){
			actionsToAdd.remove(i);
		}
		for(int i=actionsToRemove.size()-1;i>=0;i--){
			actionsToRemove.remove(i);
		}
		actionsToRemove.addAll(actions);
		
	}
	
	public void stopAction(Action action){
		if(actionsToAdd.contains(action)){
			actionsToAdd.remove(action);
		}else if(actions.contains(action)){
			if(!actionsToRemove.contains(action)){
				actionsToRemove.add(action);	
			}else{
				System.out.print("stopAction:Action already scheduled for removal!");
			}
		}else{
			System.out.print("stopAction:Action not found!");
		}
	}
	
	public void stopActionByTag(CocosActionTag tag){
		assert tag != CocosActionTag.kActionTagInvalid:"Invalid tag";
		
		for(int i=0;i<actionsToAdd.size();i++){
			Action a=(Action)actionsToAdd.get(i);
			if(a.tag == tag){
				actionsToAdd.remove(a);
				return;
			}
		}
		for(int i=0;i<actions.size();i++){
			Action a = (Action)actions.get(i);
			if(a.tag == tag && !actionsToRemove.contains(a)){
				actionsToRemove.add(a);
				return;
			}
		}
		System.out.print("stopActionByTag:Action not running or already scheduled for removal!");
	}
	
	public Action getActionByTag(CocosActionTag tag){
		assert tag != CocosActionTag.kActionTagInvalid:"Invalid tag";
		for(int i=0;i<actionsToRemove.size();i++){
			Action a = (Action)actionsToRemove.get(i);
			if(a.tag == tag){
				System.out.print("getActionByTag:Action unavailable,scheduled for removal!");
				return null;
			}
		}
		for(int i=0;i<actions.size();i++){
			Action a = (Action)actions.get(i);
			if(a.tag == tag){
				return a;
			}
		}
		for(int i=0;i<actionsToAdd.size();i++){
			Action a = (Action)actionsToAdd.get(i);
			if(a.tag == tag){
				return a;
			}
		}
		System.out.print("getActionByTag:Action not found");
		return null;
	}
	
	public int numberOfRunningActions(){
		return actionsToAdd.size()+actions.size();
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
	
	private void actionAlloc(){
		actions = new ArrayList<Object>();
		actionsToRemove = new ArrayList<Object>();
		actionsToAdd = new ArrayList<Object>();
	}
	
	private void childrenAlloc(){
		children = new ArrayList<CocosNode>();
	}
	
	private void timerAlloc(){
	
	}
	
	private void insertChild(CocosNode child,int z){
		boolean added = false;
		for(int i=0;i<children.size();i++){
			CocosNode current = children.get(i);
			if(current.zOrder > z){
				added = true;
				children.add(i, child);
				break;
			}
		}
		if(added==false)
			children.add(child);
		child._setZOrder(z);
	}
	
	private void _setZOrder(int z){
		zOrder = z;
	}
	
	private void detachChild(CocosNode child, boolean doCleanup){
		child.setParent(null);
		if(isRunning)
			child.onExit();
		if(doCleanup)
			child.cleanup();
		for(int i=children.size()-1 ;i>=0;i++){
			children.remove(i);
		}
	}
	
	public enum CocosNodeTag {
		kCocosNodeTagInvalid;
	}
	
	private void cleanup(){
		actions = null;
		actionsToRemove = null;
		actionsToAdd = null;
		scheduledSelectors = null;
		for(int i=0; i<children.size();i++){
			children.get(i).cleanup();
		}
	}
	
	public String toString(){
		return "<instance of "+this.getClass()+"|Tag = "+tag+">";
	}
	
	private float getScale(){
		if(scaleX == scaleY){
			return scaleX;
		}else{
			System.out.print("CocosNode scale error: scaleX is different from scaleY");
		}
		return 0;
	}
	
	private void setScale(float s){
		scaleX = scaleY = s;
	}
}
