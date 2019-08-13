package polyakov.java3d.object.dunamic;

import polyakov.java3d.file.FileJava3D;
import polyakov.java3d.object.ScenAnim;

import java.util.Arrays;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.03.2010
 * Time: 15:55:01
 * Анимациея по 3 координатам
 */
public class AnimXYZ implements ScenAnim, FileJava3D
{
	public AnimDouble x, y, z;			// динамические координаты

	public AnimXYZ(int n)
	{
		x = new AnimDouble(n);
		y = new AnimDouble(n);
		z = new AnimDouble(n);
	}

	public AnimXYZ(double x, double y, double z)
	{
		this.x = new AnimDouble(x);
		this.y = new AnimDouble(y);
		this.z = new AnimDouble(z);
	}

	public void setCountKey(int n)
	{
		x.setCountKey(n);
		y.setCountKey(n);
		z.setCountKey(n);
	}

	public void setKey(int key, double x, double y, double z)
	{
		this.x.setKey(key, x);
		this.y.setKey(key, y);
		this.z.setKey(key, z);
	}

	public void moveKey(int index, int key, double x, double y, double z)
	{
		this.x.moveKey(index,key, x);
		this.y.moveKey(index,key, y);
		this.z.moveKey(index,key, z);
	}

	// определить состояние для указанного кадра
	public void setKadr(double kadr)
	{
		x.setKadr(kadr);
		y.setKadr(kadr);
		z.setKadr(kadr);
	}

	public void load(DataInputStream in) throws IOException
	{
		x.load(in);
		y.load(in);
		z.load(in);
	}

	public void save(DataOutputStream out) throws IOException
	{
		x.save(out);
		y.save(out);
		z.save(out);
	}
}