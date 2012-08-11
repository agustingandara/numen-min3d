package min3d.core;

import android.util.Log;
import min3d.vos.Number3d;

public class ColBoundsBox{

	//8 vertex
	protected Number3d mmm;
	protected Number3d mmM;
	protected Number3d mMm;
	protected Number3d mMM;
	protected Number3d Mmm;
	protected Number3d MmM;
	protected Number3d MMm;
	protected Number3d MMM;

	public ColBoundsBox(Number3d number) {
		this.mmm = new Number3d(number.x, number.y, number.z);
		this.mmM = new Number3d(number.x, number.y, number.z);
		this.mMm = new Number3d(number.x, number.y, number.z);
		this.mMM = new Number3d(number.x, number.y, number.z);
		this.Mmm = new Number3d(number.x, number.y, number.z);
		this.MmM = new Number3d(number.x, number.y, number.z);
		this.MMm = new Number3d(number.x, number.y, number.z);
		this.MMM = new Number3d(number.x, number.y, number.z);
	}
	
	public boolean existsCollition(Number3d positionNew, Number3d targetNew){
		
		return false;
	}
	
	public void logAllPoints(){
		Log.d("mmmX", String.valueOf(mmm.x));
		Log.d("mmmY", String.valueOf(mmm.y));
		Log.d("mmmZ", String.valueOf(mmm.z));
		Log.d("MMMX", String.valueOf(MMM.x));
		Log.d("MMMY", String.valueOf(MMM.y));
		Log.d("MMMZ", String.valueOf(MMM.z));
	}
	
	public void addVertex(Number3d number){
		//Log.d("VERTEXADD", ".");
		if(number.x < mmm.x && number.y < mmm.y && number.z <  mmm.z){
			Log.d("VERTEXADD", "mmm");
			this.mmm = new Number3d(number.x, number.y, number.z);
		}
		if(number.x < mmM.x && number.y < mmM.y && number.z >  mmM.z)
			this.mmM = new Number3d(number.x, number.y, number.z);
		if(number.x < mMm.x && number.y > mMm.y && number.z <  mMm.z)
			this.mMm = new Number3d(number.x, number.y, number.z);
		if(number.x < mMM.x && number.y > mMM.y && number.z >  mMM.z)
			this.mMM = new Number3d(number.x, number.y, number.z);
		if(number.x > Mmm.x && number.y < Mmm.y && number.z <  Mmm.z)
			this.Mmm = new Number3d(number.x, number.y, number.z);
		if(number.x > MmM.x && number.y < MmM.y && number.z >  MmM.z)
			this.MmM = new Number3d(number.x, number.y, number.z);
		if(number.x > MMm.x && number.y > MMm.y && number.z <  MMm.z)
			this.MMm = new Number3d(number.x, number.y, number.z);
		if(number.x > MMM.x && number.y > MMM.y && number.z >  MMM.z){
			Log.d("VERTEXADD", "MMM");
			this.MMM = new Number3d(number.x, number.y, number.z);
		}
	}
}
