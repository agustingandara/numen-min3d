package numen.algorithms;

import min3d.Utils;

public class PositionRectAlgorithms {

	public static float rotationXAngle(float degx){
		return (float)Math.cos(degx*Utils.DEG);
	}
	
	public static float rotationYAngle(float degy){
		return (float)Math.sin(degy*Utils.DEG);
	}
	
	public static float rotationZAngle(float degz){
		return (float)Math.sin(degz*Utils.DEG);
	}
	
	public static float positionXFowardBack(float variation, float degx){
		return variation * (float)Math.cos(degx*Utils.DEG);
	}
	
	public static float positionZFowardBack(float variation, float degz){
		return variation * (float)Math.sin(degz*Utils.DEG);
	}
	
	public static float positionXLeftRight(float variation, float degx){
		return variation * (float)Math.cos((degx*Utils.DEG)-90);
	}
	
	public static float positionZLeftRight(float variation, float degx){
		return variation * (float)Math.sin((degx*Utils.DEG)-90);
	}
}
