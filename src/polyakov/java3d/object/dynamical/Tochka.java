package polyakov.java3d.object.dynamical;

import polyakov.java3d.object.dunamic.AnimXYZ;
import polyakov.java3d.object.statical.TochkaStatical;
import polyakov.java3d.object.dunamic.DunamicObject;
import polyakov.java3d.object.dunamic.AnimText;
import polyakov.java3d.file.FileJava3D;

import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Alex1
 * Date: 18.11.2006
 * Time: 21:35:57
 * Точка
 * ├анимация перемещения
 * ├диаметр
 * ├цвет
 * ├анимация текста
 * └цвет текста
 */
// сохранение загрузка диаметр color
public class Tochka extends TochkaStatical implements FileJava3D, DunamicObject
{
	public AnimXYZ mov;				// Анимация перемещения
	public AnimText textA;			// Анимация текста

	public Tochka(Telo telo, int id, double x, double y, double z)
	{
		super(telo, id, x, y, z);
		mov = new AnimXYZ(x, y, z);
		textA = new AnimText("");
	}

	public Tochka(Telo telo, int id)
	{
		this(telo, id, 0, 0, 0);
	}

	public void load(DataInputStream in) throws IOException
	{
		d = in.readInt();
		color = in.readInt();
		mov.load(in);
		colorText = in.readInt();
		textA.load(in);
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(d);
		out.writeInt(color);
		mov.save(out);
		out.writeInt(colorText);
		textA.save(out);
	}

	// переместить точку в указаный момент
	public void setKadr(double kadr)
	{
		// найти текст
		textA.setKadr(kadr);
		// установит полученые значения
		text = textA.value;
		// наити координаты
		mov.setKadr(kadr);
		// установит полученые значения
		x = mov.x.value;
		y = mov.y.value;
		z = mov.z.value;
		// получить положение точек относительно мировой СК
		// поворот вокруг оси X
		double temp = y * telo.cosX + z * telo.sinX;
		z = z * telo.cosX - y * telo.sinX;
		y = temp;
		// поворот вокруг оси Y
		temp = x * telo.cosY + z * telo.sinY;
		z = z * telo.cosY - x * telo.sinY;
		x = temp;
		// поворот вокруг оси Z
		temp = x * telo.cosZ + y * telo.sinZ;
		y = y * telo.cosZ - x * telo.sinZ;
		x = temp;
		// перенос
		x += telo.x;
		y += telo.y;
		z += telo.z;
	}
}
