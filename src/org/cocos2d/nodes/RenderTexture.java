package org.cocos2d.nodes;

import org.cocos2d.opengl.Texture2D;

import org.cocos2d.types.CCSize;

import android.graphics.Bitmap;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11ExtensionPack;

import static javax.microedition.khronos.opengles.GL10.*;
import static javax.microedition.khronos.opengles.GL11ExtensionPack.*;

import java.nio.IntBuffer;
import java.io.*;


/**
 RenderTexture is a generic rendering target. To render things into it,
 simply construct a render target, call begin on it, call visit on any cocos
 scenes or objects to render them, and call end. For convenience, render texture
 adds a sprite as its display child with the results, so you can simply add
 the render texture to your scene and treat it like any other CocosNode.
 There are also functions for saving the render texture to disk in PNG or JPG format.
*/

public class RenderTexture extends CocosNode {
	
	public static enum  ImageFormat {
		kImageFormatJPG,
		kImageFormatPNG
	}
	
	private int fbo[], oldFBO[];
	private Texture2D texture;
	private Sprite sprite;

	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	

	public static RenderTexture renderTexture(int w, int h)
	{
	  return new RenderTexture(w, h);
	}

	public RenderTexture(int w, int h)
	{
		super();
		init(w, h);
	}
	
	private void init(int w, int h) {
		EGL10 egl = (EGL10)EGLContext.getEGL();
		GL10 gl = (GL10)egl.eglGetCurrentContext().getGL();
		
        GL11ExtensionPack gl11ep = (GL11ExtensionPack) gl;
        oldFBO = new int[1];

        gl11ep.glGetIntegerv(GL_FRAMEBUFFER_BINDING_OES, oldFBO, 0);
		// textures must be power of two squared
		int pow = 8;
		while (pow < w || pow < h) pow*=2;

		Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap data = Bitmap.createBitmap((int) pow, (int) pow, config);
		texture = new Texture2D(data, CCSize.make(w, h));
    
		// generate FBO
        fbo = new int[1];
		gl11ep.glGenFramebuffersOES(1, fbo, 0);
		gl11ep.glBindFramebufferOES(GL_FRAMEBUFFER_OES, fbo[0]);
    
		// associate texture with FBO
		gl11ep.glFramebufferTexture2DOES(GL_FRAMEBUFFER_OES, GL_COLOR_ATTACHMENT0_OES, GL_TEXTURE_2D, texture.name(), 0);
    
		// check if it worked (probably worth doing :) )
		int status = gl11ep.glCheckFramebufferStatusOES(GL_FRAMEBUFFER_OES);
		if (status != GL_FRAMEBUFFER_COMPLETE_OES)
		{
		  throw new RuntimeException("Could not attach texture to framebuffer");
		}
		sprite = new Sprite(texture);
		sprite.setScaleY(-1);
		addChild(sprite);
		gl11ep.glBindFramebufferOES(GL_FRAMEBUFFER_OES, oldFBO[0]);
	}

	public void begin()
	{
		EGL10 egl = (EGL10)EGLContext.getEGL();
		GL10 gl = (GL10)egl.eglGetCurrentContext().getGL();

		// Save the current matrix
		gl.glPushMatrix();

		// Calculate the adjustment ratios based on the old and new projections
		float widthRatio = Director.sharedDirector().winSize().width / texture.getWidth();
		float heightRatio = Director.sharedDirector().winSize().height / texture.getHeight();

		// Adjust the orthographic projection and viewport
		gl.glOrthof((float)-1.0 / widthRatio,  (float)1.0 / widthRatio, (float)-1.0 / heightRatio, (float)1.0 / heightRatio, -1,1);
		gl.glViewport(0, 0, (int)texture.getWidth(), (int)texture.getHeight());

        GL11ExtensionPack gl11ep = (GL11ExtensionPack) gl;
		gl11ep.glGetIntegerv(GL_FRAMEBUFFER_BINDING_OES, oldFBO, 0);
		gl11ep.glBindFramebufferOES(GL_FRAMEBUFFER_OES, fbo[0]);//Will direct drawing to the frame buffer created above
		gl.glDisable(GL_DITHER);
	}

	public void end()
	{
		EGL10 egl = (EGL10)EGLContext.getEGL();
		GL10 gl = (GL10)egl.eglGetCurrentContext().getGL();

        GL11ExtensionPack gl11ep = (GL11ExtensionPack) gl;
        gl11ep.glBindFramebufferOES(GL_FRAMEBUFFER_OES, oldFBO[0]);
        
		// Restore the original matrix and viewport
		gl.glPopMatrix();
		gl.glViewport(0, 0, (int)Director.sharedDirector().winSize().width, (int)Director.sharedDirector().winSize().height);
	}


	public void clear(float r, float g, float b, float a)
	{
		EGL10 egl = (EGL10)EGLContext.getEGL();
		GL10 gl = (GL10)egl.eglGetCurrentContext().getGL();
	
		begin();
		gl.glColorMask(true, true, true, true);
		gl.glClearColor(r, g, b, a);
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		gl.glColorMask(true, true, true, false);
		end();
	}

	public boolean saveBuffer(String name)
	{
		return saveBuffer(name, ImageFormat.kImageFormatJPG);
	}

	public boolean saveBuffer(String fileName, ImageFormat format)
	{
		Bitmap bitmap = getBitmapFromBuffer();

		try {
                FileOutputStream fos=new FileOutputStream(fileName);
                if (format == ImageFormat.kImageFormatJPG) 
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                else if (format == ImageFormat.kImageFormatPNG)
                	bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                try {
                        fos.flush();
                        fos.close();
                        return true;
                } catch (IOException e) {
                	return false;
                }               
        } catch (FileNotFoundException e) {
        	return false;
        }              
	}

	/* get buffer as Bitmap */
	private Bitmap getBitmapFromBuffer()
	{
		EGL10 egl = (EGL10)EGLContext.getEGL();
		GL10 gl = (GL10)egl.eglGetCurrentContext().getGL();
		
		int h = (int)texture.getWidth();
		int w = (int)texture.getHeight();
	  	  
        int b[] = new int[h*w];
        IntBuffer ib=IntBuffer.wrap(b);
        ib.position(0);

        int bt[] = new int[h*w];

		begin();
		gl.glReadPixels(0, 0, h, w, GL_RGBA,GL_UNSIGNED_BYTE, ib);
		end();

        for(int i = 0; i < h; i++)
        {
        	for(int j = 0; j < w; j++)
             {
                  int pix = b[i*w+j];
                  int pb = (pix >> 16) & 0xff;
                  int pr = (pix << 16) & 0x00ff0000;
                  bt[(h-i-1)*w+j] = (pix & 0xff00ff00) | pr | pb;
             }
        } 

        return Bitmap.createBitmap(new int[h*w], w, h, Bitmap.Config.ARGB_8888);
	}
}
