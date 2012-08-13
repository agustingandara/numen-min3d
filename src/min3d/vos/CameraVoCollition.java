package min3d.vos;

import java.util.ArrayList;
import min3d.core.Object3d;
import min3d.core.Object3dContainer;

/**
 * Encapsulates camera-related properties, including view frustrum.
 */
public class CameraVoCollition extends CameraVo
{
	ArrayList<Object3d> children;
	
	public CameraVoCollition(ArrayList<Object3d> children){
		this.children = children;
	}
	
	public void cameraPositionPlus(Number3d positionPlus) {
		
		//New camera movement
		Number3d positionNew = new Number3d(0,0,0);
		positionNew.x = position.x + positionPlus.x;
		positionNew.y = position.y + positionPlus.y;
		positionNew.z = position.z + positionPlus.z;
		Number3d targetNew = new Number3d(0,0,0);
		targetNew.x = target.x + positionPlus.x;
		targetNew.y = target.y + positionPlus.y;
		targetNew.z = target.z + positionPlus.z;
		
		if(!existsCollition(positionNew, targetNew)) {
			super.positionPlus(positionPlus);
			super.targetPlus(positionPlus);
		}
	}
	
	public void cameraRotationPlus(Number3d plusTarget){

		//New camera movement
		/*Number3d positionNew = new Number3d(0,0,0);
		positionNew.x = position.x + plusPosition.x;
		positionNew.y = position.y + plusPosition.y;
		positionNew.z = position.z + plusPosition.z;*/
		Number3d targetNew = new Number3d(0,0,0);
		targetNew.x = position.x + plusTarget.x;
		targetNew.y = position.y + plusTarget.y;
		targetNew.z = position.z + plusTarget.z;
				
		if(!existsCollition(null, targetNew)) super.targetIqual(targetNew);
	}	
	
	private boolean existsCollition(Number3d positionNew, Number3d targetNew){
		
		boolean colision = false;
		
		for (Object3d obj : children){
			if(obj.isInternalObject){
				//Log.d("VERTEX", "INTERNAL");
				if(obj instanceof Object3dContainer){
					for (Object3d subObj : ((Object3dContainer) obj).children){
						if(subObj.boundingBox.existsCollition(subObj, positionNew, targetNew)){
							colision = true;
							break;
						}
					}
				}else if(obj instanceof Object3d){
					if(obj.boundingBox.existsCollition(obj, positionNew, targetNew)) {
						colision = true;
						break;
					}
				}
			}
		}
		return colision;
	}
	
	/*
	private boolean existsCollition(Object3d obj, Number3d positionNew, Number3d targetNew){
		if(obj.vertexLimits != null)
			if(obj.vertexLimits.existsCollition(obj, positionNew, targetNew)) return true;
				
		return false;
	}*/
}
