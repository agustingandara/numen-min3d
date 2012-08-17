package numen.algorithms;

import min3d.Utils;

public class Algorithms {

	//Scale Point Module
	public static float scale(float moduleDimensionOne, float scale){
		return  moduleDimensionOne*scale;
	}
	
	/**Algorithms of two points**/
	//Rotation of point two
	//Reference point one
	public static float rotationXAngle(float degx){
		return (float)Math.cos(degx*Utils.DEG);
	}
	
	public static float rotationYAngle(float degy){
		return (float)Math.sin(degy*Utils.DEG);
	}
	
	public static float rotationZAngle(float degz){
		return (float)Math.sin(degz*Utils.DEG);
	}
	
	//Movement of point one and two foward/backward by their direction
	public static float positionXFowardBack(float variation, float degx){
		return variation * (float)Math.cos(degx*Utils.DEG);
	}
	
	public static float positionZFowardBack(float variation, float degz){
		return variation * (float)Math.sin(degz*Utils.DEG);
	}

	//Movement of point one and two by their direction's sides
	public static float positionXLeftRight(float variation, float degx){
		return variation * (float)Math.cos((degx*Utils.DEG)-90);
	}
	
	public static float positionZLeftRight(float variation, float degx){
		return variation * (float)Math.sin((degx*Utils.DEG)-90);
	}
}
