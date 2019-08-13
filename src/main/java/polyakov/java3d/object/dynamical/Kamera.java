package polyakov.java3d.object.dynamical;

import polyakov.java3d.object.dunamic.AnimXYZ;
import polyakov.java3d.object.dunamic.DunamicObject;
import polyakov.java3d.object.dunamic.AnimDouble;
import polyakov.java3d.object.statical.ScenStatical;
import polyakov.java3d.object.statical.KameraStatical;
import polyakov.java3d.file.FileJava3D;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 11.02.2007
 * Time: 16:05:02
 * камера
 */
// разбить рендер по функциям
public class Kamera extends KameraStatical implements FileJava3D, DunamicObject
{
	public AnimXYZ kamera;		// положение камеры
	public AnimXYZ target;		// положение мишени
	public AnimDouble angleA;	// поворот камеры вокруг оси

	public Kamera(ScenStatical scen, int id, String name)
	{
		super(scen, id, name);
		kamera = new AnimXYZ(100, 100, 100);
		target = new AnimXYZ(0, 0, 0);
		angleA = new AnimDouble(0.);
	}

	public Kamera(ScenStatical scen, int id)
	{
		this(scen, id, "Kamera");
	}

	public void Top()
	{
		kamera.moveKey(0, 0, 0, 0, 100);
		target.moveKey(0, 0, 0, 0, 0);
		angleA.moveKey(0, 0, 0);
	}

	public void Bottom()
	{
		kamera.moveKey(0, 0, 0, 0, -100);
		target.moveKey(0, 0, 0, 0, 0);
		angleA.moveKey(0, 0, 180);
	}

	public void Front()
	{
		kamera.moveKey(0, 0, 0, -100, 0);
		target.moveKey(0, 0, 0, 0, 0);
		angleA.moveKey(0, 0, 0);
	}

	public void Back()
	{
		kamera.moveKey(0, 0, 0, 100, 0);
		target.moveKey(0, 0, 0, 0, 0);
		angleA.moveKey(0, 0, 180);
	}

	public void Left()
	{
		kamera.moveKey(0, 0, -100, 0, 0);
		target.moveKey(0, 0, 0, 0, 0);
		angleA.moveKey(0, 0, -90);
	}

	public void Right()
	{
		kamera.moveKey(0, 0, 100, 0, 0);
		target.moveKey(0, 0, 0, 0, 0);
		angleA.moveKey(0, 0, 90);
	}

	public void clear()
	{
		kamera.setCountKey(1);
		kamera.moveKey(0, 0, 100, 0, 0);
		target.setCountKey(1);
		target.moveKey(0, 0, 0, 0, 0);
		angleA.setCountKey(1);
		angleA.moveKey(0, 0, 0);
	}

	public void save(DataOutputStream out) throws IOException
	{
		kamera.save(out);
		target.save(out);
		out.writeDouble(angle);
		out.writeDouble(d);
		out.writeDouble(me);
		out.writeBoolean(perspektiva);
		out.writeBoolean(vsetki);
		out.writeBoolean(vsk);
		out.writeBoolean(vkam);
		out.writeBoolean(vlamp);
		out.writeBoolean(vgran);
		out.writeBoolean(osveshenie);
		out.writeBoolean(vreber);
		out.writeBoolean(vtochek);
		out.writeBoolean(vtext);
	}

	public void load(DataInputStream in) throws IOException
	{
		kamera.load(in);
		target.load(in);
		angle = in.readDouble();
		d = in.readDouble();
		me = in.readDouble();
		perspektiva = in.readBoolean();
		vsetki = in.readBoolean();
		vsk = in.readBoolean();
		vkam = in.readBoolean();
		vlamp = in.readBoolean();
		vgran = in.readBoolean();
		osveshenie = in.readBoolean();
		vreber = in.readBoolean();
		vtochek = in.readBoolean();
		vtext = in.readBoolean();
	}

	// переместить грань в указаный момент
	public void setKadr(double kadr)
	{
		kamera.setKadr(kadr);
		x = kamera.x.value;
		y = kamera.y.value;
		z = kamera.z.value;
		target.setKadr(kadr);
		nx = target.x.value;
		ny = target.y.value;
		nz = target.z.value;
		angleA.setKadr(kadr);
		angle = angleA.value;
		setUgol();
	}
}
