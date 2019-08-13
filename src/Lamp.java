import java.io.*;
import java.util.Vector;

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
public class Lamp
{
	public String name;
	public float x, y, z;	// координаты
	public int irgb;			// IRGB интенсивность и цвет источника света

	public Lamp()
	{
		name="Lamp";
		x = 5;
		y = 5;
		z = 5;
		irgb = 0xffffffff;
	}


	public Lamp(float x, float y, float z, int irgb)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.irgb = irgb;
	}
	public Lamp(DataInputStream in)
	{
		name="Lamp";
		x = 5;
		y = 5;
		z = 5;
		irgb = 0xffffffff;
	}

	// -перевести тело в указаный момент
	public void setKadr(double kadr)
	{

	}

	public void save(DataOutputStream out)
	{

	}
}
