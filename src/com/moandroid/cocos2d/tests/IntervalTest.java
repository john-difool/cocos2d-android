package com.moandroid.cocos2d.tests;

import com.moandroid.cocos2d.nodes.Label;
import com.moandroid.cocos2d.nodes.Layer;
import com.moandroid.cocos2d.types.ccTime;

import android.app.Activity;
import android.os.Bundle;

public class IntervalTest extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	private class Layer1 extends Layer{
		private Label label1;
		private Label lable2;
		private Label label3;
		private ccTime time1;
		private ccTime time2;
		private ccTime time3;
		public Layer1(){
			
		}
		public void step1(ccTime dt){
			
		}
		public void step2(ccTime dt){
			
		}
		public void step3(ccTime dt){
			
		}
	}
}
