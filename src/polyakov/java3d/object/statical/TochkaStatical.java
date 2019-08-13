package polyakov.java3d.object.statical;

import polyakov.java3d.object.dunamic.AnimXYZ;
import polyakov.java3d.object.dynamical.Tochka;
import polyakov.java3d.object.dynamical.Telo;

/**
 * Created by IntelliJ IDEA.
 * User: Alex1
 * Date: 18.11.2006
 * Time: 21:35:57
 * состояние точки в определенный момент времени
 */
public abstract class TochkaStatical extends ObjectStatical
{
	public double x, y, z;			// координаты
	public String text;				// надпись
	public int d;					// диаметр
	public int color;				// цвет
	public int colorText;			// цвет текста

	public int kx, ky;				// экранные координаты
	public float kz;				// удаленость от камеры
	public boolean kVis;			// видимость точки для камеры
	public double nx, ny, nz;		// нормаль


	protected Telo telo;			// тело к которому пренадлежит точка

	public TochkaStatical(Telo telo, int id, double x, double y, double z)
	{
		super(id);
		this.telo = telo;
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		text = null;
		d = 1;
		color = 0xffffffff;
		colorText = 0xffffffff;
	}

	// удалить обект
	public void remove()
	{
		// удалить грани где встречается точка
		for (int i = 0; i < telo.mglen; i++)
		{
			if (telo.mg[i].a == this || telo.mg[i].b == this || telo.mg[i].c == this)
			{
				telo.mg[i].remove();
				i--;
			}
		}
		// удалить ребра где встречается точка
		for (int i = 0; i < telo.mrlen; i++)
		{
			if (telo.mr[i].a == this || telo.mr[i].b == this)
			{
				telo.mr[i].remove();
				i--;
			}
		}
		// удаление самой точки
		Tochka movTochka = telo.mp[telo.mplen - 1];
		movTochka.id = id;
		telo.mp[id] = movTochka;
		telo.mplen--;
	}
}