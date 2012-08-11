package min3d.vos;


/**
 * Encapsulates camera-related properties, including view frustrum.
 */
public class CameraVo
{
	public Number3d position = new Number3d(0,0, 5); // ... note, not 'managed'
	public Number3d target = new Number3d(0,0,0);
	public Number3d upAxis = new Number3d(0,1,0);
	
	public FrustumManaged frustum = new FrustumManaged(null);
	
	public CameraVo(){
		
	}

	public void positionPlus(Number3d positionPlus){
		position.x += positionPlus.x;
		position.z += positionPlus.z;
		position.y += positionPlus.y;
	}
	
	public void targetPlus(Number3d targetPlus){
		target.x += targetPlus.x;
		target.z += targetPlus.z;
		target.y += targetPlus.y;
	}
}
