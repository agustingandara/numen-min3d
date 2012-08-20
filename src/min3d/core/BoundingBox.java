package min3d.core;

import numen.algorithms.Geo3dAlgorithms;
import numen.algorithms.Quaternions;
import min3d.vos.Number3d;

/**Agustin Gandara - Numen Library**/

public class BoundingBox{

	//8 vertex
	protected Number3d[] vertex;
	/**Vertex array ORDER**//*
	protected Number3d mmm;
	protected Number3d mmM;
	protected Number3d mMm;
	protected Number3d mMM;
	protected Number3d Mmm;
	protected Number3d MmM;
	protected Number3d MMm;
	protected Number3d MMM;*/
	
	private Number3d center;
	private Number3d Max;
	private Number3d Min;
	private Number3d MaxUpdated;
	private Number3d MinUpdated;
	
	public BoundingBox(Number3d number) {
		this.center = new Number3d(number.x, number.y, number.z);
		this.Min = new Number3d(number.x, number.y, number.z);//= center.clone();
		this.Max = new Number3d(number.x, number.y, number.z);//= center.clone();
		this.MinUpdated = center.clone();
		this.MaxUpdated = center.clone();
		this.vertex = new Number3d[8];
		this.createBox();
	}
	
	public BoundingBox() {
		this.center = new Number3d(0, 0, 0);
		this.Min = center.clone();
		this.Max = center.clone();
		this.MinUpdated = center.clone();
		this.MaxUpdated = center.clone();
		this.vertex = new Number3d[8];
	}
	
	public void updateValues(Object3d obj){
		float[] euler = {obj.rotation().x, obj.rotation().z, obj.rotation().y};
		float[] quat = Quaternions.getQuatFromEuler(euler);
		//this.createBox();
		this.updateBox(obj.position(), quat, obj.scale());
	}
	
	public boolean existsCameraVoCollition(Number3d positionNew, Number3d targetNew){		
		
		//float[] euler = {obj.rotation().x, obj.rotation().z, obj.rotation().y};
		//float[] quat = Quaternions.getQuatFromEuler(euler);
		//this.createBox();
		//this.updateBox(obj.position(), quat, obj.scale());

		/*Log.d("minx-nup", String.valueOf(Min.x));
		Log.d("miny-nup", String.valueOf(Min.y));
		Log.d("minz-nup", String.valueOf(Min.z));
		Log.d("maxx-nup", String.valueOf(Max.x));
		Log.d("maxy-nup", String.valueOf(Max.y));
		Log.d("maxz-nup", String.valueOf(Max.z));
		
		Log.d("minx-up", String.valueOf(MinUpdated.x));
		Log.d("miny-up", String.valueOf(MinUpdated.y));
		Log.d("minz-up", String.valueOf(MinUpdated.z));
		Log.d("maxx-up", String.valueOf(MaxUpdated.x));
		Log.d("maxy-up", String.valueOf(MaxUpdated.y));
		Log.d("maxz-up", String.valueOf(MaxUpdated.z));*/
		
		if(positionNew != null)
			if(existsPointCollition(positionNew)) return true;

		if(targetNew != null)
			if(existsPointCollition(targetNew)) return true;
		
		return false;
	}
	
	public boolean existsPointCollition(Number3d point){
		
		if(MinUpdated.x < point.x && point.x < MaxUpdated.x &&
				MinUpdated.z < point.z && point.z < MaxUpdated.z) return true;
		
		return false;
	}
	
	public void addVertex(Number3d number){
		if(number.x < Min.x) Min.x = number.x;
		else if(number.x > Max.x) Max.x = number.x;
		if(number.y < Min.y) Min.y = number.y;
		else if(number.y > Max.y) Max.y = number.y;
		if(number.z < Min.z) Min.z = number.z;
		else if(number.z > Max.z) Max.z = number.z;

		this.createBox();
	}
	
	public void resetVertexToUpdate(Number3d number){
		this.MinUpdated = new Number3d(number.x, number.y, number.z);
		this.MaxUpdated = new Number3d(number.x, number.y, number.z);
	}
	
	public void addVertexToUpdate(Number3d number){
		if(number.x < MinUpdated.x) MinUpdated.x = number.x;
		else if(number.x > MaxUpdated.x) MaxUpdated.x = number.x;
		if(number.y < MinUpdated.y) MinUpdated.y = number.y;
		else if(number.y > MaxUpdated.y) MaxUpdated.y = number.y;
		if(number.z < MinUpdated.z) MinUpdated.z = number.z;
		else if(number.z > MaxUpdated.z) MaxUpdated.z = number.z;
	}
	
	public void createBox(){
		
		this.vertex[0] = new Number3d(Min.x, Min.y, Min.z);
		this.vertex[1] = new Number3d(Min.x, Min.y, Max.z);
		this.vertex[2] = new Number3d(Min.x, Max.y, Min.z);
		this.vertex[3] = new Number3d(Min.x, Max.y, Max.z);
		this.vertex[4] = new Number3d(Max.x, Min.y, Min.z);
		this.vertex[5] = new Number3d(Max.x, Min.y, Max.z);
		this.vertex[6] = new Number3d(Max.x, Max.y, Min.z);
		this.vertex[7] = new Number3d(Max.x, Max.y, Max.z);
		/*this.mmm = new Number3d(Min.x, Min.y, Min.z);
		this.mmM = new Number3d(Min.x, Min.y, Max.z);
		this.mMm = new Number3d(Min.x, Max.y, Min.z);
		this.mMM = new Number3d(Min.x, Max.y, Max.z);
		this.Mmm = new Number3d(Max.x, Min.y, Min.z);
		this.MmM = new Number3d(Max.x, Min.y, Max.z);
		this.MMm = new Number3d(Max.x, Max.y, Min.z);
		this.MMM = new Number3d(Max.x, Max.y, Max.z);*/
	}
	
	public void updateBox(Number3d position, float[] rotation, Number3d scale){
		for(int i = 0 ; i< this.vertex.length ; i++)
			this.updatePoint(i, position, rotation, scale);
		/*
		this.mmm = this.updatePoint(this.mmm, position, rotation, scale);
		this.resetVertexToUpdate(this.mmm);
		this.mmM = this.updatePoint(this.mmM, position, rotation, scale);
		this.addVertexToUpdate(this.mmM);
		this.mMm = this.updatePoint(this.mMm, position, rotation, scale);
		this.addVertexToUpdate(this.mMm);
		this.mMM = this.updatePoint(this.mMM, position, rotation, scale);
		this.addVertexToUpdate(this.mMM);
		this.Mmm = this.updatePoint(this.Mmm, position, rotation, scale);
		this.addVertexToUpdate(this.Mmm);
		this.MmM = this.updatePoint(this.MmM, position, rotation, scale);
		this.addVertexToUpdate(this.MmM);
		this.MMm = this.updatePoint(this.MMm, position, rotation, scale);
		this.addVertexToUpdate(this.MMm);
		this.MMM = this.updatePoint(this.MMM, position, rotation, scale);
		this.addVertexToUpdate(this.MMM);
		*/
	}
	
	public void updatePoint(int i, Number3d position, float[] rotation, Number3d scale){
		
		//Rotation
		Number3d newPoint = Quaternions.rotatePoint(this.vertex[i], rotation);
		//Scale
		newPoint.x = Geo3dAlgorithms.scale(newPoint.x, scale.x);
		newPoint.y = Geo3dAlgorithms.scale(newPoint.y, scale.y);
		newPoint.z = Geo3dAlgorithms.scale(newPoint.z, scale.z);
		//Position
		newPoint.x += position.x;
		newPoint.y += position.y;
		newPoint.z += position.z;
		//SaveVertex
		//this.vertex[i] = newPoint;
		
		if(i == 0)	this.resetVertexToUpdate(newPoint);
		else this.addVertexToUpdate(newPoint);
	}
}
