package com.example.blowppybird2;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.transition.Scene;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

	
	
	CCDirector director;
	CCGLSurfaceView view;
	CCScene scene;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.director=CCDirector.sharedDirector();
        this.view=new CCGLSurfaceView(this);
        setContentView(view);
        
        this.director.attachInView(this.view);
        
        this.director.setAnimationInterval(1/60.0);
        this.director.setDisplayFPS(true);
       
        this.scene=CCScene.node();
        this.scene.addChild(new ActionLayer());
        this.director.runWithScene(this.scene);
    }
    


   
}
