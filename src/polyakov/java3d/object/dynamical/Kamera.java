package polyakov.java3d.object.dynamical;

import polyakov.java3d.object.dunamic.AnimXYZ;
import polyakov.java3d.object.dunamic.DunamicObject;
import polyakov.java3d.object.statical.ScenStatical;
import polyakov.java3d.object.statical.KameraStatical;
import polyakov.java3d.file.FileJava3D;

import java.awt.*;
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
	public AnimXYZ kamera;	// положение камеры
	public AnimXYZ target;	// положение мишени

	public Kamera(ScenStatical scen, int id, String name)
	{
		super(scen, id, name);
		kamera=new AnimXYZ(100,100,100);
		target=new AnimXYZ(0,0,0);
	}
	public Kamera(ScenStatical scen, int id)
	{
		this(scen, id, "Kamera");
	}

	public void Top()
	{
		kamera.setKey(0, 0,0,100);
		target.setKey(0,0,0,0);
		a = 0;
	}

	public void Bottom()
	{
		kamera.setKey(0, 0,0,-100);
		target.setKey(0,0,0,0);
		a = 180;
	}

	public void Front()
	{
		kamera.setKey(0, 0,-100,0);
		target.setKey(0,0,0,0);
		a = 0;
	}

	public void Back()
	{
		kamera.setKey(0, 0,100,0);
		target.setKey(0,0,0,0);
		a = 180;
	}

	public void Left()
	{
		kamera.setKey(0, -100,0,0);
		target.setKey(0,0,0,0);
		a = -90;
	}

	public void Right()
	{
		kamera.setKey(0, 100,0,0);
		target.setKey(0,0,0,0);
		a = 90;
	}



	public void save(DataOutputStream out) throws IOException
	{
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeDouble(nx);
		out.writeDouble(ny);
		out.writeDouble(nz);
		out.writeDouble(a);
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
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		nx = in.readDouble();
		ny = in.readDouble();
		nz = in.readDouble();
		a = in.readDouble();
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
		// пересщитать параметры
		kamera.setKadr(kadr);
		target.setKadr(kadr);
		// установить
		x=kamera.x.value;
		y=kamera.y.value;
		z=kamera.z.value;
		nx=target.x.value;
		ny=target.y.value;
		nz=target.z.value;
	}
}
