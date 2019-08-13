package polyakov.java3d.object.dynamical;

import polyakov.java3d.object.dunamic.DunamicObject;
import polyakov.java3d.object.statical.ObjectStatical;
import polyakov.java3d.file.FileJava3D;

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
 * └растояние до камеры(z буфер)
 */
// загрузка сохранение
public class Gran extends ObjectStatical implements FileJava3D, DunamicObject
{
	// статическая часть
	public Tochka a, b, c;					// точки
	public double nx, ny, nz;				// нормаль
	public Material material;				// материал
	public double au, av, bu, bv, cu, cv;	// координата текстуры (U по горизонтали, V по вертикали)

	public double kz;						// растояние до камеры

	protected Telo telo;					// тело к которому пренадлежит грань

	// динамическая часть
	// пусто
	public Gran(Telo telo,int id, Tochka a, Tochka b, Tochka c, Material material, double au, double av, double bu, double bv, double cu, double cv)
	{
		super(id);
		this.telo = telo;
		this.a = a;
		this.b = b;
		this.c = c;
		this.material = material;
		this.au = au;
		this.av = av;
		this.bu = bu;
		this.bv = bv;
		this.cu = cu;
		this.cv = cv;
		nx=0;
		ny=0;
		nz=0;
		kz=0;
	}

	public Gran(Telo telo,int id, Tochka a, Tochka b, Tochka c, Material material)
	{
		this(telo,id, a, b, c, material, .0, .0, .0, .0, .0, .0);
	}

	public Gran(Telo telo,int id, Tochka a, Tochka b, Tochka c)
	{
		this(telo,id, a, b, c, null);
	}

	public Gran(Telo telo,int id, int a, int b, int c)
	{
		this(telo,id, telo.mp[a], telo.mp[b], telo.mp[c]);
	}

	public Gran(Telo telo,int id)
	{
		this(telo,id, null, null, null);
	}

	// удалить обект
	public void remove()
	{
		Gran movGran=telo.mg[telo.mglen - 1];
		movGran.id=id;
		telo.mg[id]=movGran;
		telo.mglen--;
	}

	public void load(DataInputStream in) throws IOException
	{
		a = telo.mp[in.readInt()];
		b = telo.mp[in.readInt()];
		c = telo.mp[in.readInt()];
		int i=in.readInt();
		if (i>=0)
			material = telo.getScen().mMaterial[i];
		else
			material=null;
		au = in.readDouble();
		av = in.readDouble();
		bu = in.readDouble();
		bv = in.readDouble();
		cu = in.readDouble();
		cv = in.readDouble();
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(a.id);
		out.writeInt(b.id);
		out.writeInt(c.id);
		out.writeInt(material.id);
		out.writeDouble(au);
		out.writeDouble(av);
		out.writeDouble(bu);
		out.writeDouble(bv);
		out.writeDouble(cu);
		out.writeDouble(cv);
	}

	// переместить грань в указаный момент
	public void setKadr(double kadr)
	{
		// вызов setKadr для точек выполняется на уровне тела
		// найти нормаль к грани
		nx = (b.y - a.y) * (c.z - a.z) - (b.z - a.z) * (c.y - a.y);
		ny = (b.z - a.z) * (c.x - a.x) - (b.x - a.x) * (c.z - a.z);
		nz = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
		// нет никакой анимации
	}
	// тело к которому пренадлежит грань
	public Telo getRootTelo()
	{
		return telo;
	}
}
