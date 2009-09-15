package com.moandroid.cocos2d.renderer;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.opengles.GL10;

import com.moandroid.cocos2d.nodes.Scene;
import com.moandroid.cocos2d.opengles.GLSurfaceView;

public class Director implements GLSurfaceView.Renderer{

	static Director _sharedDirector;
	
	public static Director sharedDirector(){
		synchronized(Director.class){
			if(_sharedDirector == null){
				_sharedDirector = new Director();
			}
			return _sharedDirector;
		}
	}

	public void useFaseDirector(){
		try{
			assert _sharedDirector == null;
		}catch(AssertionError ae){
			 System.out.println("A Director was alloced.To use Fast Director this" +
			 		" must be the first call to Director"); 
		}
		FastDirector.sharedDirector();
	}
	
	public Director() {
		
	}
	
	public void setPixelFormat(){
		
	}
	
	public void setDepthBufferFormat(){
		
	}
	
	public boolean detach(){
		return true;
	}
	
	public boolean attachInWindow(){
		return true;
	}
	
	public boolean attachInView(){
		return true;
	}
	
	public void winSize(){
		
	}
	
	public void displaySize(){
		
	}
	
	public boolean landscape(){
		return true;
	}
	
	public void setLandscape(boolean on){
		
	}
	
	public void convertCoordinate(){
		
	}
	
	public void runWithScene(Scene sc){
		
	}
	
	public void pushScene(Scene sc){
		
	}
	
	public void popScene(){
		
	}
	
	public void replaceScene(Scene sc){
		
	}
	
	public void end(){
		
	}
	
	public void pause(){
		
	}
	
	public void resume(){
		
	}
	
	public void stopAnimation(){
		
	}
	
	public void startAnimation(){
		
	}
	
	public void addEventHandler(){
		
	}
	
	public void removeEventHandler(){
		
	}
	
	public void setAlphaBlending(boolean on){
		
	}
	
	public void setDepthTest(boolean on){
		
	}
	
	public void setTexture2D(boolean on){
		
	}
	
	public void setDefaultProjection(){
		
	}
	
	public void set2Dprojection(){
		
	}
	
	public void set3Dprojection(){
		
	}
	
	public void drawFrame(GL10 gl) {

	}
	
	public int[] getConfigSpec() {
		if (mTranslucentBackground) {
	      // We want a depth buffer and an alpha buffer
                int[] configSpec = {
                        EGL10.EGL_RED_SIZE,      8,
                        EGL10.EGL_GREEN_SIZE,    8,
                        EGL10.EGL_BLUE_SIZE,     8,
                        EGL10.EGL_ALPHA_SIZE,    8,
                        EGL10.EGL_DEPTH_SIZE,   16,
                        EGL10.EGL_NONE
                };
                return configSpec;
        }
		else{
	        // We want a depth buffer, don't care about the
	        // details of the color buffer.
	        int[] configSpec = {
	                EGL10.EGL_DEPTH_SIZE,   16,
	                EGL10.EGL_NONE
	        };
        return configSpec;
        }
    }

    public void sizeChanged(GL10 gl, int width, int height) {
         gl.glViewport(0, 0, width, height);

         /*
          * Set our projection matrix. This doesn't have to be done
          * each time we draw, but usually a new projection needs to
          * be set when the viewport is resized.
          */

         float ratio = (float) width / height;
         gl.glMatrixMode(GL10.GL_PROJECTION);
         gl.glLoadIdentity();
         gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    public void surfaceCreated(GL10 gl) {
        /*
         * By default, OpenGL enables features that improve quality
         * but reduce performance. One might want to tweak that
         * especially on software renderer.
         */
        gl.glDisable(GL10.GL_DITHER);

        /*
         * Some one-time OpenGL initialization can be made here
         * probably based on features of this particular context
         */
         gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                 GL10.GL_FASTEST);

         if (mTranslucentBackground) {
             gl.glClearColor(0,0,0,0);
         } else {
             gl.glClearColor(1,1,1,1);
         }
         gl.glEnable(GL10.GL_CULL_FACE);
         gl.glShadeModel(GL10.GL_SMOOTH);
         gl.glEnable(GL10.GL_DEPTH_TEST);
    }
    private boolean mTranslucentBackground;
 
    private boolean isOpenGLAttached(){
    	return true;
    }
    
    private boolean initOpenGLviewWithView(){
    	return false;
    }
    
    private boolean initGLDefaultValues(){
    	return true;
    }
    
    private void mainLoop(){
    }
    
    private void setNextScene(){
    }
    
    private void applyLandscape(){
    }
    
    private void showFPS(){
    }
    
    private void calculateDeltaTime(){
    }
}

