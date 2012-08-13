package min3d.core;

import android.util.Log;
import min3d.vos.Number3d;

public class BoundingBox{

	//8 vertex
	/*
	protected Number3d mmm;
	protected Number3d mmM;
	protected Number3d mMm;
	protected Number3d mMM;
	protected Number3d Mmm;
	protected Number3d MmM;
	protected Number3d MMm;
	protected Number3d MMM;
	*/
	private Number3d Max;
	private Number3d Min;
	
	public BoundingBox(Number3d number) {
		this.Min = new Number3d(number.x, number.y, number.z);
		this.Max = new Number3d(number.x, number.y, number.z);
	}
	
	public boolean existsCollition(Object3d obj, Number3d positionNew, Number3d targetNew){
		//logAllPoints(obj, positionNew);

		if(positionNew != null)
			if(Min.x < positionNew.x && positionNew.x < Max.x && 
				Min.z < positionNew.z && positionNew.z < Max.z) return true;

		if(targetNew != null)
			if(Min.x < targetNew.x && targetNew.x < Max.x && 
					Min.z < targetNew.z && targetNew.z < Max.z) return true;
		
		return false;
	}
	
	/*public boolean existsCollition(Object3d obj, Number3d targetNew){
	
		if(Min.x < targetNew.x && targetNew.x < Max.x && 
				Min.z < targetNew.z && targetNew.z < Max.z) return true;
		
		return false;
	}*/
	
	public void positionPlus(float x, float y, float z){
		
		Min.x += x;
		Max.x += x;
		Min.y += y;
		Max.y += y;
		Min.z += z;
		Max.z += z;
	}
	
	public void rotationPlus(float x, float y, float z){
		
		Min.x += x;
		Max.x += x;
		Min.y += y;
		Max.y += y;
		Min.z += z;
		Max.z += z;
	}
	
	public void scalePlus(float x, float y, float z){
		
		/*Min.x += x;
		Max.x += x;
		Min.y += y;
		Max.y += y;
		Min.z += z;
		Max.z += z;*/
	}
	
	public void logAllPoints(Object3d obj, Number3d positionNew){
		Log.d("MinX", String.valueOf(Min.x));
		Log.d("MinY", String.valueOf(Min.y));
		Log.d("MinZ", String.valueOf(Min.z));
		Log.d("MaxX", String.valueOf(Max.x));
		Log.d("MaxY", String.valueOf(Max.y));
		Log.d("MaxZ", String.valueOf(Max.z));
		Log.d("OBJX", String.valueOf(obj.position().x));
		Log.d("OBJY", String.valueOf(obj.position().y));
		Log.d("OBJZ", String.valueOf(obj.position().z));
		Log.d("CAMX", String.valueOf(positionNew.x));
		Log.d("CAMY", String.valueOf(positionNew.y));
		Log.d("CAMZ", String.valueOf(positionNew.z));
	}
		
	public void addVertex(Number3d number){
		//Log.d("VERTEXADD", ".");
		if(number.x < Min.x) Min.x = number.x;
		else if(number.x > Max.x) Max.x = number.x;
		if(number.y < Min.y) Min.y = number.y;
		else if(number.y > Max.y) Max.y = number.y;
		if(number.z < Min.z) Min.z = number.z;
		else if(number.z > Max.z) Max.z = number.z;
	}
	/*
	public void createBox(){
		this.mmm = new Number3d(Min.x, Min.y, Min.z);
		this.mmM = new Number3d(Min.x, Min.y, Max.z);
		this.mMm = new Number3d(Min.x, Max.y, Min.z);
		this.mMM = new Number3d(Min.x, Max.y, Max.z);
		this.Mmm = new Number3d(Max.x, Min.y, Min.z);
		this.MmM = new Number3d(Max.x, Min.y, Max.z);
		this.MMm = new Number3d(Max.x, Max.y, Min.z);
		this.MMM = new Number3d(Max.x, Max.y, Max.z);
	}*/
}
