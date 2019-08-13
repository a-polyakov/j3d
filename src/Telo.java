import java.util.Arrays;
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
 */
//анимация
//├>траекрория движения
//├>траекрория вращения
//├>масштабирование
//└видимость
// сохранение загрузка анимация
public class Telo
{
	public static double GRADUSOVvRADIANE = 180 / Math.PI;	// градусов в одном радиане

	public String name;
	public double x, y, z;						// Координаты
	public double rx, ry, rz;					// Угол поворота
	public double mashtab;						// Масштаб
	public boolean vis;							// видимость
	int aKey[], aRotKey[], aZoomKey[], aVisKey[];	// ключи анимации
	double ax[], ay[], az[];					// значение ключей перемещения
	double arx[], ary[], arz[];					// значение ключей поворота
	double aZoom[];							// значение ключей масштабирования
	boolean aVis[];								// значение ключей видимости
	public Tochka mp[];							// массив точек
	public Rebro mr[];							// массив ребер
	public Gran mg[];							// массив граней

	Telo(String name)
	{
		this.name = name;
		x = 0;
		y = 0;
		z = 0;
		rx = 0;
		ry = 0;
		rz = 0;
		mashtab = 1;
		vis = true;
		aKey = new int[]{0};
		aRotKey = new int[]{0};
		aZoomKey = new int[]{0};
		aVisKey = new int[]{0};
		ax = new double[]{0};
		ay = new double[]{0};
		az = new double[]{0};
		arx = new double[]{0};
		ary = new double[]{0};
		arz = new double[]{0};
		aZoom = new double[]{1};
		aVis = new boolean[]{true};
		mp = null;
		mr = null;
		mg = null;
	}

	Telo(DataInputStream in) throws IOException
	{
		int i,l;
		name=in.readUTF();
		l=in.readInt();
		aKey=new int[l];
		ax=new double[l];
		ay=new double[l];
		az=new double[l];
		for (i=0;i<l;i++)
		{
			aKey[i]=in.readInt();
			ax[i]=in.readDouble();
			ay[i]=in.readDouble();
			az[i]=in.readDouble();
		}
		l=in.readInt();
		aRotKey=new int[l];
		arx=new double[l];
		ary=new double[l];
		arz=new double[l];
		for (i=0;i<l;i++)
		{
			aRotKey[i]=in.readInt();
			arx[i]=in.readDouble();
			ary[i]=in.readDouble();
			arz[i]=in.readDouble();
		}
		l=in.readInt();
		aZoomKey=new int[l];
		aZoom=new double[l];
		for (i=0;i<l;i++)
		{
			aZoomKey[i]=in.readInt();
			aZoom[i]=in.readDouble();
		}
		l=in.readInt();
		aVisKey=new int[l];
		aVis=new boolean[l];
		for (i=0;i<l;i++)
		{
			aVisKey[i]=in.readInt();
			aVis[i]=in.readBoolean();
		}
		l=in.readInt();
		if (l>0)
		{
			mp=new Tochka[l];
			for (i=0;i<l;i++)
				mp[i]=new Tochka(in);
		}else
			mp=null;
		l=in.readInt();
		if (l>0)
		{
			mr=new Rebro[l];
			for (i=0;i<l;i++)
				mr[i]=new Rebro(in);
		}else
			mr=null;
		l=in.readInt();
		if (l>0)
		{
			mg=new Gran[l];
			for (i=0;i<l;i++)
				mg[i]=new Gran(in);
		}else
			mg=null;
	}

	// Задать количество точек, ребер, граней
	private void setPRG(int kp, int kr, int kg)
	{
		mp = new Tochka[kp];
		mr = new Rebro[kr];
		mg = new Gran[kg];
	}

	// задать материал
	public void setMaterial(int m)
	{
		if (m>=0 && mg != null)
			for (int i = 0; i < mg.length; i++)
				mg[i].material = m;
	}

	// +-перевести тело в указаный момент
	public void setKadr(double kadr)
	{
		// точки
		int i;
		if (mp!=null)
		for (i = 0; i < mp.length; i++)
			mp[i].setKadr(kadr);
		// тело
		boolean flag = true;
		// определить координат
		if (kadr == 0 && aKey.length == 1)
		{	// начальное состояние, отсутствие анимации
			x = ax[0];
			y = ay[0];
			z = az[0];
		} else
		{	// промежуточное состояние
			for (i = 1; i < aKey.length && flag; i++)
				if ((int) kadr == aKey[i])
				{	// ключевой кадр
					x = ax[i];
					y = ay[i];
					z = az[i];
					flag = false;
				} else if (kadr < aKey[i])
				{	// между двумя ключами
					x = ax[i - 1] + (ax[i] - ax[i - 1]) * (kadr - aKey[i - 1]) / (aKey[i] - aKey[i - 1]);
					y = ay[i - 1] + (ay[i] - ay[i - 1]) * (kadr - aKey[i - 1]) / (aKey[i] - aKey[i - 1]);
					z = az[i - 1] + (az[i] - az[i - 1]) * (kadr - aKey[i - 1]) / (aKey[i] - aKey[i - 1]);
					flag = false;
				}
			if (flag)
			{	// за последним ключом
				i = aKey.length - 1;
				x = ax[i];
				y = ay[i];
				z = az[i];
			}
		}
		flag = true;
		// определить углов
		if (kadr == 0 && aRotKey.length == 1)
		{	// начальное состояние, отсутствие анимации
			rx = arx[0];
			ry = ary[0];
			rz = arz[0];
		} else
		{	// промежуточное состояние
			for (i = 1; i < aRotKey.length && flag; i++)
				if ((int) kadr == aRotKey[i])
				{	// ключевой кадр
					rx = arx[i];
					ry = ary[i];
					rz = arz[i];
					flag = false;
				} else if (kadr < aRotKey[i])
				{	// между двумя ключами
					rx = arx[i - 1] + (arx[i] - arx[i - 1]) * (kadr - aRotKey[i - 1]) / (aRotKey[i] - aRotKey[i - 1]);
					ry = ary[i - 1] + (ary[i] - ary[i - 1]) * (kadr - aRotKey[i - 1]) / (aRotKey[i] - aRotKey[i - 1]);
					rz = arz[i - 1] + (arz[i] - arz[i - 1]) * (kadr - aRotKey[i - 1]) / (aRotKey[i] - aRotKey[i - 1]);
					flag = false;
				}
			if (flag)
			{	// за последним ключом
				i = aRotKey.length - 1;
				rx = arx[i];
				ry = ary[i];
				rz = arz[i];
			}
		}
		// определить масштаба
		flag = true;
		// определить углов
		if (kadr == 0 && aZoomKey.length == 1)
		{	// начальное состояние, отсутствие анимации
			mashtab = aZoom[0];
		} else
		{	// промежуточное состояние
			for (i = 1; i < aZoomKey.length && flag; i++)
				if ((int) kadr == aZoomKey[i])
				{	// ключевой кадр
					mashtab = aZoom[i];
					flag = false;
				} else if (kadr < aZoomKey[i])
				{	// между двумя ключами
					mashtab = aZoom[i - 1] + (aZoom[i] - aZoom[i - 1]) * (kadr - aZoomKey[i - 1]) / (aZoomKey[i] - aZoomKey[i - 1]);
					flag = false;
				}
			if (flag)
			{	// за последним ключом
				i = aZoomKey.length - 1;
				mashtab = aZoom[i];
			}
		}
		// определить видимость
		if (kadr == 0 && aVisKey.length == 1)
			// начальное состояние, отсутствие анимации
			vis = aVis[0];
		else
		{	// промежуточное состояние
			for (i = aVisKey.length - 1; i > 0 && kadr < aVisKey[i]; i--) ;
			if (i == 0)
				// между начальным состоянием и первым ключем
				vis = aVis[0];
			else
				// ключ или дальше
				vis = aVis[i];
		}
	}

	// добавить точку
	public void addTochka()
	{
		if (mp != null)
		{
			int i = mp.length;
			mp = Arrays.copyOf(mp, i + 1);
			mp[i] = new Tochka();
		} else
			mp = new Tochka[]{new Tochka()};
	}

	// удалить точку
	public void delTochka(int id)
	{
		if (mp != null && mp.length > id)
		{
			int i, j=mp.length -1;
			// удалить грани где встречается точка
			if (mg != null)
				for (i = 0; i < mg.length; i++)
				{
					if (mg[i].a == id || mg[i].b == id || mg[i].c == id)
					{
						delGran(i);
						i--;
					}
					else
					{
					if (mg[i].a == j)
						mg[i].a=id;
					if (mg[i].b == j)
						mg[i].b=id;
					if (mg[i].c == j)
						mg[i].c=id;
					}
				}
			// удалить ребра где встречается точка
			if (mr != null)
				for (i = 0; i < mr.length; i++)
				{
					if (mr[i].a == id || mr[i].b == id)
					{
						delRebro(i);
						i--;
					}
					else
					{
					if (mr[i].a == j)
						mr[i].a=id;
					if (mr[i].b == j)
						mr[i].b=id;
					}
				}
			// удалить точку
			mp[id] = mp[j];
			mp = Arrays.copyOf(mp, j);
		}
	}

	// -добавить ребро по двум выбраным точкам
	public void addRebro(int a, int b)
	{
		if (mp != null)
			if (a < mp.length && b < mp.length)
			{   // точки указаны правильно
				int i;
				boolean flag = true; // такого ретра нет
				if (mr != null)
				{
					// проверка на повтор
					for (i = 0; i < mr.length; i++)
						if ((mr[i].a == a && mr[i].b == b) || (mr[i].a == b && mr[i].b == a))
							flag = false;	// такой уже есть
					if (flag)
					{
						mr = Arrays.copyOf(mr, mr.length + 1);
						mr[mr.length - 1] = new Rebro(a, b);
					}
				} else
				{
					mr = new Rebro[1];
					mr[0] = new Rebro(a, b);
				}
			}
	}

	// удалить ребро
	public void delRebro(int id)
	{
		if (mr != null && mr.length > id)
		{
			mr[id] = mr[mr.length - 1];
			mr = Arrays.copyOf(mr, mr.length - 1);
		}
	}

	// добавить грань на трёх точках
	public void addGran(int a, int b, int c)
	{
		if (mg != null)
		{
			int i = mg.length;
			mg = Arrays.copyOf(mg, i + 1);
			mg[i] = new Gran(a, b, c);
		} else
			mg = new Gran[]{new Gran(a, b, c)};
	}

	// удалить грань
	public void delGran(int id)
	{
		if (mg != null && mg.length > id)
		{
			mg[id] = mg[mg.length - 1];
			mg = Arrays.copyOf(mg, mg.length - 1);
		}
	}

	// изменить количество ключей анимации перемещения
	public void setAnimMov(int n)
	{
		if (n>0 && aKey.length!=n)
		{
			aKey = Arrays.copyOf(aKey, n);
			ax =Arrays.copyOf(ax, n);
			ay =Arrays.copyOf(ay, n);
			az =Arrays.copyOf(az, n);
		}
	}
	// изменить количество ключей анимации видимости
	public void setAnimRot(int n)
	{
		if (n>0 && aRotKey.length!=n)
		{
			aRotKey = Arrays.copyOf(aRotKey, n);
			arx =Arrays.copyOf(arx, n);
			ary =Arrays.copyOf(ary, n);
			arz =Arrays.copyOf(arz, n);
		}
	}
	// изменить количество ключей анимации видимости
	public void setAnimZoom(int n)
	{
		if (n>0 && aZoomKey.length!=n)
		{
		aZoomKey = Arrays.copyOf(aZoomKey, n);
		aZoom =Arrays.copyOf(aZoom, n);
		}
	}
	// изменить количество ключей анимации видимости
	public void setAnimVis(int n)
	{
		if (n>0 && aVisKey.length!=n)
		{
		aVisKey = Arrays.copyOf(aVisKey, n);
		aVis =Arrays.copyOf(aVis, n);
		}
	}

	// создать параллелипипет
	public void Box(double a, double b, double c)
	{
		setPRG(8, 12, 12);
		mp[0] = new Tochka(0, 0, 0);
		mp[1] = new Tochka(0, 0, c);
		mp[2] = new Tochka(0, b, 0);
		mp[3] = new Tochka(0, b, c);
		mp[4] = new Tochka(a, 0, 0);
		mp[5] = new Tochka(a, 0, c);
		mp[6] = new Tochka(a, b, 0);
		mp[7] = new Tochka(a, b, c);
		mr[0] = new Rebro(0, 1);
		mr[1] = new Rebro(0, 2);
		mr[2] = new Rebro(0, 4);
		mr[3] = new Rebro(3, 7);
		mr[4] = new Rebro(1, 3);
		mr[5] = new Rebro(1, 5);
		mr[6] = new Rebro(2, 3);
		mr[7] = new Rebro(2, 6);
		mr[8] = new Rebro(4, 5);
		mr[9] = new Rebro(4, 6);
		mr[10] = new Rebro(5, 7);
		mr[11] = new Rebro(6, 7);
		mg[0] = new Gran(0, 2, 1);
		mg[1] = new Gran(1, 2, 3);
		mg[2] = new Gran(4, 5, 6);
		mg[3] = new Gran(5, 7, 6);
		mg[4] = new Gran(0, 1, 4);
		mg[5] = new Gran(1, 5, 4);
		mg[6] = new Gran(0, 2, 4);
		mg[7] = new Gran(2, 6, 4);
		mg[8] = new Gran(2, 6, 3);
		mg[9] = new Gran(3, 6, 7);
		mg[10] = new Gran(1, 5, 3);
		mg[11] = new Gran(3, 5, 7);
	}

	public void Piramid(int n, double r, double h)
	{
		setPRG(n + 1, n + n, n + n - 2);
		mp[0] = new Tochka(0, 0, h);
		float t = 2 * (float) Math.PI / n;
		int i, j;
		for (i = 1; i <= n; i++)
			mp[i] = new Tochka(r * (float) Math.sin(i * t), r * (float) Math.cos(i * t), 0);
		for (i = 0; i < n; i++)
			mr[i] = new Rebro(0, i + 1);
		for (i = 0; i < n - 1; i++)
			mr[n + i] = new Rebro(i + 1, i + 2);
		mr[n + n - 1].Set(1, n);
		for (i = 0; i < n - 1; i++)
			mg[i] = new Gran(0, 1 + i, 2 + i);
		mg[i] = new Gran(0, 1, n);
		for (i = n, j = 2; i < n + n - 2; i++, j++)
			mg[i] = new Gran(1, j, j + 1);
	}

	public void Gran(double r)
	{
		setPRG(3, 3, 1);
		mp[0] = new Tochka(0, 0, 0);
		mp[1] = new Tochka(r, 0, 0);
		mp[2] = new Tochka(0, r, 0);
		mr[0] = new Rebro(0, 1);
		mr[1] = new Rebro(0, 2);
		mr[2] = new Rebro(1, 2);
		mg[0] = new Gran(0, 1, 2);
	}

	public void SK()
	{
		setPRG(4, 3, 0);
		mp[0] = new Tochka(0, 0, 0);
		mp[1] = new Tochka(10, 0, 0);
		mp[2] = new Tochka(0, 10, 0);
		mp[3] = new Tochka(0, 0, 10);
		mr[0] = new Rebro(0, 1);
		mr[1] = new Rebro(0, 2);
		mr[2] = new Rebro(0, 3);
	}

	// получить положение точек относительно мировой СК
	public Point3D[] getTochka(boolean vtochek, boolean vtext)
	{
		if (mp!=null)
		{
		Point3D[] mT = new Point3D[mp.length];
		double tx, ty, tz;
		double x2, y2;
		double cosX = Math.cos(rx / GRADUSOVvRADIANE);
		double sinX = Math.sin(rx / GRADUSOVvRADIANE);
		double cosY = Math.cos(ry / GRADUSOVvRADIANE);
		double sinY = Math.sin(ry / GRADUSOVvRADIANE);
		double cosZ = Math.cos(rz / GRADUSOVvRADIANE);
		double sinZ = Math.sin(rz / GRADUSOVvRADIANE);
		int i;
		for (i = 0; i < mT.length; i++)
		{
			tx = mp[i].x;
			ty = mp[i].y;
			tz = mp[i].z;
			// поворот
			// поворот вокруг оси X
			y2 = ty * cosX + tz * sinX;
			tz = tz * cosX - ty * sinX;
			ty = y2;
			// поворот вокруг оси Y
			x2 = tx * cosY + tz * sinY;
			tz = tz * cosY - tx * sinY;
			tx = x2;
			// поворот вокруг оси Z
			x2 = tx * cosZ + ty * sinZ;
			ty = ty * cosZ - tx * sinZ;
			tx = x2;
			// перенос
			tx += x;
			ty += y;
			tz += z;
			mT[i] = new Point3D(tx, ty, tz);
			if (vtochek)
			{ // переписать точку
				mT[i].color = mp[i].color;
				mT[i].d = mp[i].d;
			}
			if (vtext)
			{ // переписать надписи к точкам
				mT[i].text = mp[i].text;
				mT[i].colorText = mp[i].colorText;
			}
		}
		return mT;
		}
		else
		return null;
	}
	public void save(DataOutputStream out) throws IOException
	{
		int i,l;
		out.writeUTF(name);
		l=aKey.length;
		out.writeInt(l);
		for (i=0;i<l;i++)
		{
			out.writeInt(aKey[i]);
			out.writeDouble(ax[i]);
			out.writeDouble(ay[i]);
			out.writeDouble(az[i]);
		}
		l=aRotKey.length;
		out.writeInt(l);
		for (i=0;i<l;i++)
		{
			out.writeInt(aRotKey[i]);
			out.writeDouble(arx[i]);
			out.writeDouble(ary[i]);
			out.writeDouble(arz[i]);
		}
		l=aZoomKey.length;
		out.writeInt(l);
		for (i=0;i<l;i++)
		{
			out.writeInt(aZoomKey[i]);
			out.writeDouble(aZoom[i]);
		}
		l=aVisKey.length;
		out.writeInt(l);
		for (i=0;i<l;i++)
		{
			out.writeInt(aVisKey[i]);
			out.writeBoolean(aVis[i]);
		}
		if (mp!=null)
		{
			l=mp.length;
			out.writeInt(l);
			for (i=0;i<l;i++)
				mp[i].save(out);
		}else
			out.writeInt(0);
		if (mr!=null)
		{
			l=mr.length;
			out.writeInt(l);
			for (i=0;i<l;i++)
				mr[i].save(out);
		}else
			out.writeInt(0);
		if (mg!=null)
		{
			l=mg.length;
			out.writeInt(l);
			for (i=0;i<l;i++)
				mg[i].save(out);
		}else
			out.writeInt(0);
	}
}
