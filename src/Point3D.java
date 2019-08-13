/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 23.04.2007
 * Time: 9:19:17
 * To change this template use File | Settings | File Templates.
 */
public class Point3D
{
	public double x, y, z;			// координаты
	public double nx, ny, nz;		// нормаль
	public String text;				// надпись
	public int d;					// диаметр
	public int color;				// цвет
	public int colorText;			// цвет текста

	Point3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		nx = 0;
		ny = 0;
		nz = 0;
		text = null;
		d = 1;
		color = 0xffffffff;
		colorText = 0xffffffff;
	}
}
