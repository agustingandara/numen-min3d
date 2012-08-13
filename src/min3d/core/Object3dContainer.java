package min3d.core;

import java.util.ArrayList;

import min3d.interfaces.IObject3dContainer;
import min3d.vos.Number3d;

public class Object3dContainer extends Object3d implements IObject3dContainer
{
	public ArrayList<Object3d> children = new ArrayList<Object3d>();

	public Object3dContainer()
	{
		super(0, 0, false, false, false);
	}
	/**
	 * Adds container functionality to Object3d.
	 * 
	 * Subclass Object3dContainer instead of Object3d if you
	 * believe you may want to add children to that object. 
	 */
	public Object3dContainer(int $maxVerts, int $maxFaces)
	{
		super($maxVerts, $maxFaces, true,true,true);
	}

	public Object3dContainer(int $maxVerts, int $maxFaces,  Boolean $useUvs, Boolean $useNormals, Boolean $useVertexColors)
	{
		super($maxVerts, $maxFaces, $useUvs,$useNormals,$useVertexColors);
	}
	
	/**
	 * This constructor is convenient for cloning purposes 
	 */
	public Object3dContainer(Vertices $vertices, FacesBufferedList $faces, TextureList $textures)
	{
		super($vertices, $faces, $textures);
	}
	
	public void addChild(Object3d $o)
	{
		children.add($o);
		
		$o.parent(this);
		$o.scene(this.scene());
	}
	
	public void addChildAt(Object3d $o, int $index) 
	{
		children.add($index, $o);
		
		$o.parent(this);
		$o.scene(this.scene());
	}

	public boolean removeChild(Object3d $o)
	{
		boolean b = children.remove($o);
		
		if (b) {
			$o.parent(null);
			$o.scene(null);
		}
		return b;
	}
	
	public Object3d removeChildAt(int $index) 
	{
		Object3d o = children.remove($index);
		if (o != null) {
			o.parent(null);
			o.scene(null);
		}
		return o;
	}
	
	public Object3d getChildAt(int $index) 
	{
		return children.get($index);
	}
	
	/**
	 * X/Y/Z position of object. 
	 */
	
	public void positionPlus(float x, float y, float z){
		
		_position.x += x;
		_position.y += y;
		_position.z += z;
		//Dinamic collitions - Plus funciton into object Container
		for(int i = 0; i< this.numChildren();i++){
			this.getChildAt(i).boundingBox.positionPlus(x, y, z);
		}
	}
	
	public void positionPlus(Number3d plus){
		
		_position.x += plus.x;
		_position.y += plus.y;
		_position.z += plus.z;
		//Dinamic collitions - Plus funciton into object Container
		for(int i = 0; i< this.numChildren();i++){
			this.getChildAt(i).boundingBox.positionPlus(plus.x, plus.y, plus.z);
		}
	}
	
	/**
	 * X/Y/Z euler rotation of object, using Euler angles.
	 * Units should be in degrees, to match OpenGL usage. 
	 */
	public void rotationPlus(float x, float y, float z){
		_rotation.x += x;
		_rotation.y += y;
		_rotation.z += z;
		//Dinamic collitions - Plus funciton into object Container
		for(int i = 0; i< this.numChildren();i++){
			this.getChildAt(i).boundingBox.rotationPlus(x, y, z);
		}
	}
	
	public void rotationPlus(Number3d plus){
		_rotation.x += plus.x;
		_rotation.y += plus.y;
		_rotation.z += plus.z;
		//Dinamic collitions - Plus funciton into object Container
		for(int i = 0; i< this.numChildren();i++){
			this.getChildAt(i).boundingBox.rotationPlus(plus.x, plus.y, plus.z);
		}
	}
	
	/**
	 * X/Y/Z scale of object.
	 */
	
	public void scalePlus(float x, float y, float z){
		_scale.x += x;
		_scale.y += y;
		_scale.z += z;
		//Dinamic collitions - Plus funciton into object Container
		for(int i = 0; i< this.numChildren();i++){
			this.getChildAt(i).boundingBox.scalePlus(x, y, z);
		}
	}
	
	public void scalePlus(Number3d plus){
		_scale.x += plus.x;
		_scale.y += plus.y;
		_scale.z += plus.z;
		//Dinamic collitions - Plus funciton into object Container
		for(int i = 0; i< this.numChildren();i++){
			this.getChildAt(i).boundingBox.scalePlus(plus.x, plus.y, plus.z);
		}
	}
	
	/**
	 * TODO: Use better lookup 
	 */
	public Object3d getChildByName(String $name)
	{
		for (int i = 0; i < children.size(); i++)
		{
			if (children.get(i).name().equals($name)) return children.get(i); 
		}
		return null;
	}

	public int getChildIndexOf(Object3d $o) 
	{
		return children.indexOf($o);		
	}


	public int numChildren() 
	{
		return children.size();
	}
	
	/*package-private*/ 
	ArrayList<Object3d> children()
	{
		return children;
	}
	
	public Object3dContainer clone()
	{
		Vertices v = _vertices.clone();
		FacesBufferedList f = _faces.clone();

		Object3dContainer clone = new Object3dContainer(v, f, _textures);
		
		clone.position().x = position().x;
		clone.position().y = position().y;
		clone.position().z = position().z;
		
		clone.rotation().x = rotation().x;
		clone.rotation().y = rotation().y;
		clone.rotation().z = rotation().z;
		
		clone.scale().x = scale().x;
		clone.scale().y = scale().y;
		clone.scale().z = scale().z;
		
		for(int i = 0; i< this.numChildren();i++)
		{
			 clone.addChild(this.getChildAt(i));
		}
		 
		return clone;
	}

}
