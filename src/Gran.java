import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 18.11.2006
 * Time: 21:39:03
 * грань
 * ├3 точки на которых строится грань
 * ├нормаль к грани
 * ├марериал
 * ├текстурные координаты каджой из вершин
 * └растояние до камеры
 */
// загрузка сохранение
public class Gran
{
	public int a, b, c;						// точки
	public double nx, ny, nz;				// нормаль
	public int material;					// материал
	public double au, av, bu, bv, cu, cv;	// координата текстуры (U по горизонтали, V по вертикали)
	public double z;						// растояние до камеры

	public Gran()
	{
		a = -1;
		b = -1;
		c = -1;
		nx = 0;
		ny = 0;
		nz = 0;
		material = -1;
		z = -1;
	}

	public Gran(int a, int b, int c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		nx = 0;
		ny = 0;
		nz = 0;
		material = -1;
		z = -1;
	}
	public Gran(DataInputStream in) throws IOException
	{
		a=in.readInt();
		b=in.readInt();
		c=in.readInt();
		material=in.readInt();
		au=in.readDouble();
		av=in.readDouble();
		bu=in.readDouble();
		bv=in.readDouble();
		cu=in.readDouble();
		cv=in.readDouble();
	}

	public void Set(int a, int b, int c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(a);
		out.writeInt(b);
		out.writeInt(c);
		out.writeInt(material);
		out.writeDouble(au);
		out.writeDouble(av);
		out.writeDouble(bu);
		out.writeDouble(bv);
		out.writeDouble(cu);
		out.writeDouble(cv);
	}
}
