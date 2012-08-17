package numen.algorithms;

import min3d.vos.Number3d;

//Quaternions.java
//@author Joel Ross

/***
A utility class of static method for using and manipulating Quaternions
This way we have a class of our own methods instead of relying on Java3D's
Quaternions are represented as float[] objects, with element order {x,y,z,w}

NOTE: Optimizations demand that all quaternions given as arguments be unit quaternions.
     But don't worry, because it should be anyway (based on the game).
***/

public class Quaternions
{
	public static final int X = 0; //for readable indexing
	public static final int Y = 1;
	public static final int Z = 2;
	public static final int W = 3;

	//multiply (compose) two specified Quaternions and return the result
	public static float[] mul(float[] q1, float[] q2)
	{
		//simplified quaternion multiplication
		float[] q = new float[] {
			q1[W]*q2[X] + q1[X]*q2[W] + q1[Y]*q2[Z] - q1[Z]*q2[Y],
			q1[W]*q2[Y] + q1[Y]*q2[W] + q1[Z]*q2[X] - q1[X]*q2[Z],
			q1[W]*q2[Z] + q1[Z]*q2[W] + q1[X]*q2[Y] - q1[Y]*q2[X],
			q1[W]*q2[W] - q1[X]*q2[X] - q1[Y]*q2[Y] - q1[Z]*q2[Z]};

		normalize(q); //normalize before we return, to combat roundoff error

		return q;

	}

	//returns the inverse of the given unit quaternion
	public static float[] inverse(float[] q)
	{
		return new float[] {-1*q[X], -1*q[Y], -1*q[Z], q[W]};
	}

	//normalizes the given Quaternion (sets its length to 1)
	public static void normalize(float[] q)
	{
		double len = Math.sqrt(q[X]*q[X] + q[Y]*q[Y] + q[Z]*q[Z] + q[W]*q[W]);
		q[X] = (float)(q[X]/len);
		q[Y] = (float)(q[Y]/len);
		q[Z] = (float)(q[Z]/len);
		q[W] = (float)(q[W]/len);
	}

	//returns the length of the quaternion (in double precision)
	public static double getLength(float[] q)
	{
		return Math.sqrt(q[X]*q[X] + q[Y]*q[Y] + q[Z]*q[Z] + q[W]*q[W]);
	}

	//rotates the given point by the given UNIT Quaternion, and returns the new point
	public static float[] rotatePoint(float[] p, float[] q)
	{
		/* The explicit formula
		float[] qc = new float[] {-q[X],-q[Y],-q[Z],q[W]}; //make the conjugate
		float[] vq = new float[] {p[X],p[Y],p[Z],0}; //make vector into a Quaternion
		return mul(mul(q,vq),qc); //multiply
		*/

		float x2 = q[X]*q[X];
		float y2 = q[Y]*q[Y];
		float z2 = q[Z]*q[Z];
		float xy = q[X]*q[Y];
		float yz = q[Y]*q[Z];
		float xz = q[X]*q[Z];
		float wx = q[W]*q[X];
		float wy = q[W]*q[Y];
		float wz = q[W]*q[Z];

		//direct assignment a la rotation matrix
		return new float[] {
			p[X]*(1-2*(y2 + z2)) + 2*(p[Y]*(xy - wz) + p[Z]*(xz + wy)),
			p[Y]*(1-2*(x2 + z2)) + 2*(p[X]*(xy + wz) + p[Z]*(yz - wx)),
			p[Z]*(1-2*(x2 + y2)) + 2*(p[X]*(xz - wy) + p[Y]*(yz + wx))};
	}

	//rotates the given point by the given UNIT Quaternion, and returns the new point
	public static Number3d rotatePoint(Number3d point, float[] q)
	{
		/* The explicit formula
		float[] qc = new float[] {-q[X],-q[Y],-q[Z],q[W]}; //make the conjugate
		float[] vq = new float[] {p[X],p[Y],p[Z],0}; //make vector into a Quaternion
		return mul(mul(q,vq),qc); //multiply
		*/
		float[] p = {point.x, point.y, point.z};
		
		float x2 = q[X]*q[X];
		float y2 = q[Y]*q[Y];
		float z2 = q[Z]*q[Z];
		float xy = q[X]*q[Y];
		float yz = q[Y]*q[Z];
		float xz = q[X]*q[Z];
		float wx = q[W]*q[X];
		float wy = q[W]*q[Y];
		float wz = q[W]*q[Z];

		//direct assignment a la rotation matrix
		/*return new float[] {
			p[X]*(1-2*(y2 + z2)) + 2*(p[Y]*(xy - wz) + p[Z]*(xz + wy)),
			p[Y]*(1-2*(x2 + z2)) + 2*(p[X]*(xy + wz) + p[Z]*(yz - wx)),
			p[Z]*(1-2*(x2 + y2)) + 2*(p[X]*(xz - wy) + p[Y]*(yz + wx))};*/
		
		return new Number3d(
				p[X]*(1-2*(y2 + z2)) + 2*(p[Y]*(xy - wz) + p[Z]*(xz + wy)), 
				p[Y]*(1-2*(x2 + z2)) + 2*(p[X]*(xy + wz) + p[Z]*(yz - wx)), 
				p[Z]*(1-2*(x2 + y2)) + 2*(p[X]*(xz - wy) + p[Y]*(yz + wx)));
	}
	
	//batch rotating method -- save some time by doing all assignments at once
	public static void rotatePoints(float[][] ps, float[] q)
	{
		float x2 = q[X]*q[X];
		float y2 = q[Y]*q[Y];
		float z2 = q[Z]*q[Z];
		float xy = q[X]*q[Y];
		float yz = q[Y]*q[Z];
		float xz = q[X]*q[Z];
		float wx = q[W]*q[X];
		float wy = q[W]*q[Y];
		float wz = q[W]*q[Z];

		float[] m = new float[] {
			1-2*(y2 + z2), 2*(xy - wz), 2*(xz + wy),
			2*(xy + wz), 1-2*(x2 + z2), 2*(yz - wx),
			2*(xz - wy), 2*(yz + wx), 1-2*(x2 + y2)}; //construct the rotation matrix

		for(int i=0; i<ps.length; i++) //run through the vectors
		{
			ps[i] = new float[] {
				m[0]*ps[i][X] + m[1]*ps[i][Y] + m[2]*ps[i][Z],
				m[3]*ps[i][X] + m[4]*ps[i][Y] + m[5]*ps[i][Z],
				m[6]*ps[i][X] + m[7]*ps[i][Y] + m[8]*ps[i][Z]}; //rotate by the matrix (faster)
		}
	}

	//returns a Quaternion from an array of Euler angles (XYZ order)
	public static float[] getQuatFromEuler(float[] e)
	{
		double sx = Math.sin(e[X]/2.0);
		double sy = Math.sin(e[Y]/2.0);
		double sz = Math.sin(e[Z]/2.0);
		double cx = Math.cos(e[X]/2.0);
		double cy = Math.cos(e[Y]/2.0);
		double cz = Math.cos(e[Z]/2.0);

		return new float[] {
			(float)(sx*cy*cz - cx*sy*sz),
			(float)(cx*sy*cz - sx*cy*sz),
			(float)(cx*cy*sz - sx*sy*cz),
			(float)(cx*cy*cz + sx*sy*sz)};
	}

	//returns a Quaternion from an array of Euler angles (XYZ order),
	// taking in double precision angles to avoid casting elsewhere
	public static float[] getQuatFromEuler(double[] e)
	{
		double sx = Math.sin(e[X]/2.0);
		double sy = Math.sin(e[Y]/2.0);
		double sz = Math.sin(e[Z]/2.0);
		double cx = Math.cos(e[X]/2.0);
		double cy = Math.cos(e[Y]/2.0);
		double cz = Math.cos(e[Z]/2.0);

		return new float[] {
			(float)(sx*cy*cz - cx*sy*sz),
			(float)(cx*sy*cz - sx*cy*sz),
			(float)(cx*cy*sz - sx*sy*cz),
			(float)(cx*cy*cz + sx*sy*sz)};
	}

	//returns a rotation matrix from the given Quaternion
	public static float[][] getMatrixFromQuat(float[] q)
	{
		float x2 = q[X]*q[X];
		float y2 = q[Y]*q[Y];
		float z2 = q[Z]*q[Z];
		float xy = q[X]*q[Y];
		float yz = q[Y]*q[Z];
		float xz = q[X]*q[Z];
		float wx = q[W]*q[X];
		float wy = q[W]*q[Y];
		float wz = q[W]*q[Z];

		//construct and return the rotation matrix
		return new float[][] {	{1-2*(y2 + z2), 2*(xy - wz), 2*(xz + wy)},
					{2*(xy + wz), 1-2*(x2 + z2), 2*(yz - wx)},
					{2*(xz - wy), 2*(yz + wx), 1-2*(x2 + y2)}};
	}

	//returns a String representation of the quaternion
	public static String toString(float[] q)
	{
		return "{"+q[X]+", "+q[Y]+", "+q[Z]+", "+q[W]+"}";
	}
}