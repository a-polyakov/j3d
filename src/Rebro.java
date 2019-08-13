import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 02.12.2006
 * Time: 2:53:05
 * Ребро
 * ├две точки
 * ├ширина
 * └цвет
 */
// диаметр
public class Rebro
{
	public int a, b;	// точки
	public int d;		// ширина
	public int color;	// цвет

	public Rebro()
	{
		a = -1;
		b = -1;
		d = 1;
		color = 0xFF000000;	// чёрный
	}

	public Rebro(int a, int b)
	{
		this.a = a;
		this.b = b;
		d = 1;
		color = 0xFF000000;
	}
	public Rebro(Rebro rebro, int t)
	{
		a=rebro.a+t;
		b=rebro.b+t;
		d=rebro.d;
		color=rebro.color;
	}

	public Rebro(DataInputStream in) throws IOException
	{
		a = in.readInt();
		b = in.readInt();
		d = in.readInt();
		color = in.readInt();
	}

	public void Set(int a, int b)
	{
		this.a = a;
		this.b = b;
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(a);
		out.writeInt(b);
		out.writeInt(d);
		out.writeInt(color);
	}
}
