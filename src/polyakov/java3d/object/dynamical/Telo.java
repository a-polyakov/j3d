package polyakov.java3d.object.dynamical;

import polyakov.java3d.object.dunamic.AnimXYZ;
import polyakov.java3d.object.dunamic.AnimDouble;
import polyakov.java3d.object.dunamic.AnimBoolean;
import polyakov.java3d.object.dunamic.DunamicObject;
import polyakov.java3d.object.statical.ObjectStatical;
import polyakov.java3d.file.FileJava3D;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 18.11.2006
 * Time: 21:47:08
 * Тело
 * ├СК
 * ├точки
 * ├ребра
 * └грани
 * <p/>
 * //анимация
 * //├>траекрория движения
 * //├>траекрория вращения
 * //├>масштабирование
 * //└видимость
 * // сохранение загрузка анимация
 * !!!
 * проверка что обект с таким именем есть
 */


public class Telo extends ObjectStatical implements FileJava3D, DunamicObject
{
	// статическая часть
	public double x, y, z;			// положение обекта
	public double rx, ry, rz;		// Угол поворота
	public double mashtab;			// Масштаб
	public boolean vis;				// видимость
	public Tochka mp[];				// массив точек
	public int mplen;				// количество точек
	public Rebro mr[];				// массив ребер
	public int mrlen;				// количество ребер
	public Gran mg[];				// массив граней
	public int mglen;				// количество граней

	protected Scen scen;			// сцена к которой пренадлежит тело



	// динамическая часть
	public AnimXYZ mov;							// Анимация перемещения
	public AnimXYZ rot;							// Анимация вращения
	public AnimDouble mashtabA;					// Анимация масштабирования
	public AnimBoolean visA;					// Анимация видимости

	public Telo(Scen scen, int id, String name, int coutPoint, int countReber, int countGran)
	{
		// статическая часть
		super(id, name);
		this.scen = scen;
		this.name = name;
		x = 0;
		y = 0;
		z = 0;
		rx = 0;
		ry = 0;
		rz = 0;
		mashtab = 1;
		vis = true;
		mplen = 0;
		mp = new Tochka[coutPoint];
		mrlen = 0;
		mr = new Rebro[countReber];
		mglen = 0;
		mg = new Gran[countGran];
		// динамическая часть
		mov = new AnimXYZ(0, 0, 0);
		rot = new AnimXYZ(0, 0, 0);
		mashtabA = new AnimDouble(1);
		visA = new AnimBoolean(true);
	}

	public Telo(Scen scen, int id, String name)
	{
		this(scen, id, name, 4, 4, 2);
	}

	public Telo(Scen scen, int id)
	{
		this(scen, id, "Telo", 4, 4, 2);
	}




	// удалить обект
	public void remove()
	{
		Telo movTelo = scen.mTel[scen.mTelLen - 1];
		movTelo.id = id;
		scen.mTel[id] = movTelo;
		scen.mTelLen--;
	}

	public void load(DataInputStream in) throws IOException
	{
		name = in.readUTF();
		mov.load(in);
		rot.load(in);
		mashtabA.load(in);
		visA.load(in);
		mplen = in.readInt();
		if (mplen < 0)
			mplen = 0;
		mp = new Tochka[mplen];
		for (int i = 0; i < mplen; i++)
		{
			mp[i] = new Tochka(this, i);
			mp[i].load(in);
		}

		mrlen = in.readInt();
		if (mrlen < 0)
			mrlen = 0;
		mr = new Rebro[mrlen];
		for (int i = 0; i < mrlen; i++)
		{
			mr[i] = new Rebro(this, i);
			mr[i].load(in);
		}

		mglen = in.readInt();
		if (mglen < 0)
			mglen = 0;
		mg = new Gran[mglen];
		for (int i = 0; i < mglen; i++)
		{
			mg[i] = new Gran(this, i);
			mg[i].load(in);
		}
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(name);
		mov.save(out);
		rot.save(out);
		mashtabA.save(out);
		visA.save(out);
		out.writeInt(mplen);
		for (int i = 0; i < mplen; i++)
			mp[i].save(out);
		out.writeInt(mrlen);
		for (int i = 0; i < mrlen; i++)
			mr[i].save(out);
		out.writeInt(mglen);
		for (int i = 0; i < mglen; i++)
			mg[i].save(out);
	}

	// !!!перевести тело в указаный момент
	public void setKadr(double kadr)
	{
		visA.setKadr(kadr);
		if (visA.value)
		{
			mov.setKadr(kadr);
			rot.setKadr(kadr);
			mashtabA.setKadr(kadr);
			// точки
			for (int i = 0; i < mplen; i++)
				mp[i].setKadr(kadr);
			// ребра
			for (int i = 0; i < mrlen; i++)
				mr[i].setKadr(kadr);
			// грани
			for (int i = 0; i < mglen; i++)
				mg[i].setKadr(kadr);
		}
	}


	// добавить точку
	public void addTochka(double x, double y, double z)
	{
		addTochka(new Tochka(this, mplen, x, y, z));
	}

	// добавить точку
	private void addTochka(Tochka tochka)
	{
		mplen++;
		if (mplen > mp.length)
		{
			mp = Arrays.copyOf(mp, mplen);
		}
		mp[mplen - 1] = tochka;
	}

	// добавить ребро по двум выбраным точкам
	public void addRebro(int a_id, int b_id)
	{
		addRebro(mp[a_id], mp[b_id]);
	}

	// добавить ребро по двум выбраным точкам
	public void addRebro(Tochka a, Tochka b)
	{
		if (a != b)
		{
			boolean flag = true; // такого ретра нет
			// проверка на повтор
			for (int i = 0; flag && i < mrlen; i++)
				if ((mr[i].a == a && mr[i].b == b) || (mr[i].a == b && mr[i].b == a))
					flag = false;	// такой уже есть
			if (flag)
			{
				addRebro(new Rebro(this, mrlen, a, b));
			}
		}
	}

	// добавить ребро
	private void addRebro(Rebro rebro)
	{
		mrlen++;
		if (mrlen > mr.length)
		{
			mr = Arrays.copyOf(mr, mrlen);
		}
		mr[mrlen - 1] = rebro;
	}

	// добавить грань на трёх точках
	public void addGran(int a_id, int b_id, int c_id)
	{
		addGran(mp[a_id], mp[b_id], mp[c_id]);
	}

	// добавить грань на трёх точках
	public void addGran(Tochka a, Tochka b, Tochka c)
	{
		if (a != b && a != c && b != c)
		{
			boolean flag = true; // такого грани нет
			// проверка на повтор
			for (int i = 0; flag && i < mglen; i++)
				if ((mg[i].a == a && mg[i].b == b && mg[i].c == c) ||
						(mr[i].a == b && mr[i].b == c && mg[i].c == a) ||
						(mr[i].a == c && mr[i].b == a && mg[i].c == b) ||
						(mg[i].a == a && mg[i].b == c && mg[i].c == b) ||
						(mr[i].a == b && mr[i].b == a && mg[i].c == c) ||
						(mr[i].a == c && mr[i].b == b && mg[i].c == a))
					flag = false;	// такой уже есть
			if (flag)
			{
				addGran(new Gran(this, mglen, a, b, c));
			}
		}
	}

	// добавить грань
	private void addGran(Gran gran)
	{
		mglen++;
		if (mglen > mg.length)
		{
			mg = Arrays.copyOf(mg, mglen);
		}
		mg[mglen - 1] = gran;
	}

	public Scen getScen()
	{
		return scen;
	}

	// задать материал
	public void setMaterial(Material m)
	{
		for (int i = 0; i < mglen; i++)
			mg[i].material = m;
	}

}
