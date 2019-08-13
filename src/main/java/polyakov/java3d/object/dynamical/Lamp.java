package polyakov.java3d.object.dynamical;

import polyakov.java3d.file.FileJava3D;
import polyakov.java3d.object.statical.ObjectStatical;
import polyakov.java3d.object.dunamic.AnimXYZ;
import polyakov.java3d.object.dunamic.DunamicObject;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 01.11.2006
 * Time: 7:07:33
 * точечный источник света
 * Lamp
 * ├масив ламп
 * └анимация
 * ├анимация положения
 * └анимация освещения
 */
// усложнить освещение
// анимация
public class Lamp extends ObjectStatical implements FileJava3D, DunamicObject
{
	// статическая часть
	public double x, y, z;		// координаты
	public int irgb;			// IRGB интенсивность и цвет источника света

	private Scen scen;			// сцена к которой пренадлежит источник света

	// динамическая часть
	public AnimXYZ mov;			// координаты

	public Lamp(Scen scen, int id, String name, double x, double y, double z, int irgb)
	{
		super(id, name);
		// статическая часть
		this.scen=scen;
		this.x = x;
		this.y = y;
		this.z = z;
		this.irgb = irgb;
		// динамическая часть
		mov =new AnimXYZ(x,y,z);
	}

	public Lamp(Scen scen,int id)
	{
		this(scen, id, "Lamp", 5, 5, 5, 0xffffffff);
	}

	// удалить обект
	public void remove()
	{
		Lamp movObject=scen.mLamp[scen.mLampLen - 1];
		movObject.id=id;
		scen.mLamp[id]=movObject;
		scen.mLampLen--;
	}

	public void load(DataInputStream in) throws IOException
	{
		name = in.readUTF();
		mov.load(in);
		irgb = in.readInt();
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(name);
		mov.save(out);
		out.writeInt(irgb);
	}

	// !!!переместить обект в указаный момент времени
	public void setKadr(double kadr)
	{
		// пересщитать параметры
		mov.setKadr(kadr);
		// установить
		x= mov.x.value;
		y= mov.y.value;
		z= mov.z.value;
	}

}
