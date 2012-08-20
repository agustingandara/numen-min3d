package numen.main;

import javax.microedition.khronos.opengles.GL10;

/**Agustin Gandara - Numen**/

import numen.controllers.AnalogController;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.objectPrimitives.SkyBox;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;

public class MainActivity extends RendererActivity  {

	//Skybox
	private SkyBox skybox;
	//Object 3DS
	private Object3dContainer object;
	//Controllers
	AnalogController controller;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Landscape Activity
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	
	//Initialize
	public void initScene(GL10 gl) {
		super.initScene();
		
		//Controller
		this.controller = new AnalogController(scene, getWidth(), getHeight());
		//Light
		this.scene.lights().add(new Light());
		
		//Skybox
		this.skybox = new SkyBox(5.0f, 2);
		this.skybox.addTexture(SkyBox.Face.North, 	R.drawable.side, 		"north");
		this.skybox.addTexture(SkyBox.Face.East, 	R.drawable.side, 		"east");
		this.skybox.addTexture(SkyBox.Face.South, 	R.drawable.side, 		"south");
		this.skybox.addTexture(SkyBox.Face.West, 	R.drawable.side, 		"west");
		this.skybox.addTexture(SkyBox.Face.Up,		R.drawable.top, 		"up");
		this.skybox.addTexture(SkyBox.Face.Down, 	R.drawable.bottom, 		"down");
		this.skybox.scale().y = 0.5f;
		this.skybox.scale().x = 4f;
		this.skybox.scale().z = 4f;
		this.scene.addChild(skybox);
				
		IParser parser = Parser.createParser(Parser.Type.OBJ,
				getResources(), "numen.main:raw/camaro_obj", true);
		parser.parse();

		//Objects
		this.object = parser.getParsedObject();
		this.object.positionPlus(1f, -1.3f, 0);
		//this.object.position().x += 1f;
		//this.object.position().y += -1.3f;
		this.object.rotationPlus(-90, 0, 45);
		//this.object.rotation().x += -90;
		//this.object.rotation().z += 45;
		this.scene.addChild(object);
	}
	
	//Update
	public void onUpdateScene() {
		super.onUpdateScene();
		this.controller.onUpdate();
	}
	
	//Controllers
	public boolean onTouchEvent(MotionEvent event) {
		this.controller.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}