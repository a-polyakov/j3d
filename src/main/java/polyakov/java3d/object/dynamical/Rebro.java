package polyakov.java3d.object.dynamical;

import polyakov.java3d.object.dunamic.DunamicObject;
import polyakov.java3d.object.statical.ObjectStatical;
import polyakov.java3d.file.FileJava3D;

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
public class Rebro extends ObjectStatical implements FileJava3D, DunamicObject
{
	// статическая часть
	public Tochka a, b;			// точки
	public int d;				// ширина
	public int color;			// цвет

	protected Telo telo;		// тело к которому пренадлежит ребро

	// динамическая часть
	// пусто

	public Rebro(Telo telo, int id, Tochka a, Tochka b, int d, int color)
	{
		super(id);
		// статическая часть
		this.telo = telo;
		this.a = a;
		this.b = b;
		this.d = d;
		this.color = color;
		// динамическая часть
	}

	public Rebro(Telo telo, int id, Tochka a, Tochka b)
	{
		this(telo, id, a, b, 1, 0xFF000000); // чёрный
	}

	public Rebro(Telo telo, int id)
	{
		this(telo, id, null, null);
	}

	// удалить обект
	public void remove()
	{
		Rebro movRebro = telo.mr[telo.mrlen - 1];
		movRebro.id = id;
		telo.mr[id] = movRebro;
		telo.mrlen--;
	}

	public void load(DataInputStream in) throws IOException
	{
		a = telo.mp[in.readInt()];
		b = telo.mp[in.readInt()];
		d = in.readInt();
		color = in.readInt();
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(a.id);
		out.writeInt(b.id);
		out.writeInt(d);
		out.writeInt(color);
	}

	// переместить точку в указаный момент
	public void setKadr(double kadr)
	{
		// нет никакой анимации
	}
	// тело к которому пренадлежит ребро
	public Telo getRootTelo()
	{
		return telo;
	}
}
