package numen.main;

import javax.microedition.khronos.opengles.GL10;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import min3d.Utils;
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
	
	//Controller
	//private int controller;
	private float degx;
	private float degy;
	private float area;
	private int rotationpointer;
	private int positionpointer;
	//Controller variables rotation
	private float dxinit;
	private float dx;
	private float dyinit;
	private float dy;
	private boolean touchrotation;
	//Controller variables position
	private float dxSecondaryinit;
	private float dxSecondary;
	private float dySecondaryinit;
	private float dySecondary;
	private boolean touchposition;
	//Controllers constant
	private final float degsens = 1.2f;
	private final float movsens = 0.12f;
	private final float spercent = 0.40f;
	private final int degylimit = 30;
	private final float areapropheigth = 0.4f;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Landscape Activity
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//this.controller = -1;
	}
	
	//Initialize
	public void initScene(GL10 gl) {
		super.initScene();
		

		//Variables
		this.area = getHeight()*areapropheigth;
		
		//Light
		scene.lights().add(new Light());
		
		//Skybox
		skybox = new SkyBox(5.0f, 2);
		skybox.addTexture(SkyBox.Face.North, 	R.drawable.side, 		"north");
		skybox.addTexture(SkyBox.Face.East, 	R.drawable.side, 		"east");
		skybox.addTexture(SkyBox.Face.South, 	R.drawable.side, 		"south");
		skybox.addTexture(SkyBox.Face.West, 	R.drawable.side, 		"west");
		skybox.addTexture(SkyBox.Face.Up,		R.drawable.top, 		"up");
		skybox.addTexture(SkyBox.Face.Down, 	R.drawable.bottom, 		"down");
		skybox.scale().y = 0.5f;
		skybox.scale().x = 4f;
		skybox.scale().z = 4f;
		scene.addChild(skybox);
				
		IParser parser = Parser.createParser(Parser.Type.OBJ,
				getResources(), "numen.main:raw/camaro_obj", true);
		parser.parse();

		object = parser.getParsedObject();
		object.position().y = -1.3f;
		object.rotation().x = -90;
		scene.addChild(object);
	}
	
	//Update
	public void onUpdateScene() {
		super.onUpdateScene();
		this.onUpdateControlRotation();
		this.onUpdateControlPosition();
	}
	
	//Controllers
	public boolean onTouchEvent(MotionEvent event) {
		//Controller detection	MULTI-TOUCH
		//Detect First Touch
		this.setTouchController(event, 0);
		//Detect Second Touch
		if(event.getPointerCount() > 1)	this.setTouchController(event, 1);
		//Call touch controller event
		if(this.positionpointer != -1)
			this.onTouchEventPosition(getAction(event, positionpointer), event.getX(positionpointer), event.getY(positionpointer));
		if(this.rotationpointer != -1)
			this.onTouchEventRotation(getAction(event, rotationpointer), event.getX(rotationpointer), event.getY(rotationpointer));
		
		return super.onTouchEvent(event);
	}
	
	public int getAction(MotionEvent event, int pointer){
		int action = event.getAction();
		if(pointer == 1){
			if((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_1_UP)
				action = MotionEvent.ACTION_UP;
			if((action & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_1_DOWN)
				action = MotionEvent.ACTION_DOWN;
		}
		return action;
	}
	
	public void setTouchController(MotionEvent event, int pointer){
		if(event.getX(pointer) < getWidth()/2){
			//Log.d("POSITION", String.valueOf(pointer));
			this.positionpointer = pointer;
		}else{
			//Log.d("ROTATION", String.valueOf(pointer));
			this.rotationpointer = pointer;
		}
	}
	
	public void disableControllers(){
		this.touchrotation = false;
		this.rotationpointer = -1;
		this.touchposition = false;
		this.positionpointer = -1;
	}
	
//	public boolean onTouchEvent(MotionEvent event) {
//		//Controller detection	SIMPLE-TOUCH
//		if(this.controller == -1){
//			if(event.getX() < getWidth()/2)	this.controller = 1;
//			else this.controller = 2;
//		}
//		if(this.controller == 1)	this.onTouchEventPosition(event.getAction(), event.getX(), event.getY());
//		if(this.controller ==2) 	this.onTouchEventRotation(event.getAction(), event.getX(), event.getY());
//	}
	
	public void onTouchEventPosition(int action, float x, float y){
		
		if(action == MotionEvent.ACTION_UP){
			this.disableControllers();
			//this.touchposition = false;
			//this.positionpointer = -1;
			//this.controller = -1;
		}else{
			if(!touchposition){
				this.dxSecondaryinit = x;
				this.dySecondaryinit = y;
				this.touchposition = true;
			}
			this.dxSecondary = x - dxSecondaryinit + (area/2);
			this.dySecondary = y - dySecondaryinit + (area/2);
		}
	}
	
	public void onTouchEventRotation(int action, float x, float y){
		
		if(action == MotionEvent.ACTION_UP){
			this.disableControllers();
			//this.touchrotation = false;
			//this.rotationpointer = -1;
			//this.controller = -1;
		}else{
			if(!touchrotation){
				this.dxinit = x;
				this.dyinit = y;
				this.touchrotation = true;
			}
			this.dx = x - dxinit + (area/2);
			this.dy = y - dyinit + (area/2);
		}
	}
	
	public void onUpdateControlRotation(){
		
		if(this.touchrotation){
			//Analogic update X values
			if(this.dx < area*spercent){ 
				if(this.dx < 0) this.dx = 0;
				this.degx -= (degsens/(area*spercent))*((area*spercent)-this.dx);
			}else if(this.dx > area*(1-spercent)) {
				if(this.dx > area) this.dx = area;
				this.degx += (degsens/(area*spercent))*(this.dx-(area*(1-spercent)));
			}
			//Analogic update Y values
			if(this.dy < area*spercent && this.degy < degylimit) {
				if(this.dy < 0) this.dy = 0;
				this.degy += (degsens/(area*spercent))*((area*spercent)-this.dy);
			}else if(this.dy > area*(1-spercent) && this.degy > -degylimit) {
				if(this.dy > area) this.dy = area;
				this.degy -= (degsens/(area*spercent))*(this.dy-(area*(1-spercent)));
			}
			scene.camera().target.x = scene.camera().position.x + (float)Math.cos(degx*Utils.DEG);
			scene.camera().target.z = scene.camera().position.z + (float)Math.sin(degx*Utils.DEG);
			scene.camera().target.y = scene.camera().position.y + (float)Math.sin(degy*Utils.DEG);
		}
	}
	
	public void onUpdateControlPosition(){
		
		if(this.touchposition){
			//Analogic values
			float variationx = 0;
			float variationy = 0;
			float trigonometry;
			//Analogic update X values
			if(this.dxSecondary < area*spercent){ 
				if(this.dxSecondary < 0) this.dxSecondary = 0;
				variationx = (movsens/(area*spercent))*((area*spercent)-this.dxSecondary);
			}else if(this.dxSecondary > area*(1-spercent)) {
				if(this.dxSecondary > area) this.dxSecondary = area;
				variationx = -(movsens/(area*spercent))*(this.dxSecondary-(area*(1-spercent)));
			}
			//Analogic update Y values
			if(this.dySecondary < area*spercent && this.degy > - degylimit) {
				if(this.dySecondary < 0) this.dySecondary = 0;
				variationy = (movsens/(area*spercent))*((area*spercent)-this.dySecondary);
			}else if(this.dySecondary > area*(1-spercent) && this.degy < degylimit) {
				if(this.dySecondary > area) this.dySecondary = area;
				variationy = -(movsens/(area*spercent))*(this.dySecondary-(area*(1-spercent)));
			}
			//Update position up/down
			trigonometry = variationy * (float)Math.cos(degx*Utils.DEG);
			scene.camera().position.x += trigonometry;
			scene.camera().target.x += trigonometry;
			trigonometry = variationy * (float)Math.sin(degx*Utils.DEG);
			scene.camera().position.z += trigonometry;
			scene.camera().target.z += trigonometry;
			//Update position left/rigth
			trigonometry = variationx * (float)Math.cos((degx*Utils.DEG)-90);
			scene.camera().position.x += trigonometry;
			scene.camera().target.x += trigonometry;
			trigonometry = variationx * (float)Math.sin((degx*Utils.DEG)-90);
			scene.camera().position.z += trigonometry;
			scene.camera().target.z += trigonometry;
		}
	}
}