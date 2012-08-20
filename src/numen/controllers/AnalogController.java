package numen.controllers;

import numen.algorithms.Geo3dAlgorithms;
import min3d.core.Scene;
import min3d.vos.Number3d;
import android.view.MotionEvent;

/**Agustin Gandara - Numen Library**/

public class AnalogController {

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
	private final float degsens = 1.9f;
	private final float movsens = 0.12f;
	private final float spercent = 0.40f;
	private final int degylimit = 25;
	private final float areapropheigth = 0.4f;
	//Stage variables
	private Scene scene;
	private int width;
	
	public AnalogController(Scene scene, int width, int height) {

		//Variables
		this.area = height*areapropheigth;
		this.width = width;
		this.scene = scene;
	}
	
	public void onUpdate(){

		this.onUpdateControlRotation();
		this.onUpdateControlPosition();
	}
	
	public void onTouchEvent(MotionEvent event){

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
		if(event.getX(pointer) < width/2){
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
			//Analogic values
			Number3d plus = new Number3d(0, 0, 0);
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
			
			plus.x = Geo3dAlgorithms.rotationXAngle(degx);//(float)Math.cos(degx*Utils.DEG);
			plus.z = Geo3dAlgorithms.rotationZAngle(degx);//(float)Math.sin(degx*Utils.DEG);
			plus.y = Geo3dAlgorithms.rotationYAngle(degy);//(float)Math.sin(degy*Utils.DEG);
			//scene.camera().target.x = scene.camera().position.x + (float)Math.cos(degx*Utils.DEG);
			//scene.camera().target.z = scene.camera().position.z + (float)Math.sin(degx*Utils.DEG);
			//scene.camera().target.y = scene.camera().position.y + (float)Math.sin(degy*Utils.DEG);
			//Plusses
			scene.cameraRotationPlus(plus);
		}
	}
	
	public void onUpdateControlPosition(){
		
		if(this.touchposition){
			//Analogic values
			Number3d plus = new Number3d(0, 0, 0);
			float variationx = 0;
			float variationy = 0;
			//float trigonometry;
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
			//trigonometry = variationy * (float)Math.cos(degx*Utils.DEG);
			plus.x += Geo3dAlgorithms.positionXFowardBack(variationy, degx);//trigonometry;
			//scene.camera().position.x += trigonometry;
			//scene.camera().target.x += trigonometry;
			//trigonometry = variationy * (float)Math.sin(degx*Utils.DEG);
			plus.z += Geo3dAlgorithms.positionZFowardBack(variationy, degx);//trigonometry;
			//scene.camera().position.z += trigonometry;
			//scene.camera().target.z += trigonometry;
			
			//Update position left/rigth
			//trigonometry = variationx * (float)Math.cos((degx*Utils.DEG)-90);
			plus.x += Geo3dAlgorithms.positionXLeftRight(variationx, degx);//trigonometry;
			//scene.camera().position.x += trigonometry;
			//scene.camera().target.x += trigonometry;
			//trigonometry = variationx * (float)Math.sin((degx*Utils.DEG)-90);
			plus.z += Geo3dAlgorithms.positionZLeftRight(variationx, degx);//trigonometry;
			//scene.camera().position.z += trigonometry;
			//scene.camera().target.z += trigonometry;
			
			//Plusses
			scene.cameraPositionPlus(plus);
			//scene.camera().positionPlus(plus);
			//scene.camera().targetPlus(plus);
		}
	}
}
