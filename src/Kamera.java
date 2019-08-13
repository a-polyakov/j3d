import java.awt.*;
import java.awt.image.MemoryImageSource;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 11.02.2007
 * Time: 16:05:02
 * камера
 */
// разбить рендер по функциям
public class Kamera
{
	public static Toolkit tool = Toolkit.getDefaultToolkit();
	private static int BGCOLOR = 0xffeeeeee;
	public static double GRADUSOVvRADIANE = 180 / Math.PI;	// градусов в одном радиане
	public static Font textFont = new Font(Font.MONOSPACED, Font.BOLD, 20);

	public Scen scen;
	public double x, y, z;		// положение камеры
	public double nx, ny, nz;	// положение мишени
	public double a;			// поворот камеры вокруг оси
	public double d, me;		// минимльное растояние до экрана, масштаб экрана

	public boolean perspektiva;	// перспективное проецирование
	public boolean vsetki;		// видимость сетки
	public boolean vsk;			// видимость системы координат
	public boolean vkam;		// видимость камер в сцене
	public boolean vlamp;		// видимость источников света в сцене
	public boolean vgran;		// видимость граней
	public boolean osveshenie;	// учет освещения
	public boolean vreber;		// видимость ребер
	public boolean vtochek;		// видимость точек
	public boolean vtext;		// видимость надписей

	private int image[];		// массив точек изображения
	private float imageZ[];		// Z буфер изображения
	private double cosZ, sinZ, cosY, sinY, cosX, sinX;
	public Image pic;
	public int height;			// высота
	public int width;			// ширина

	private Point3D[] mt;
	private Rebro[] mr;
	private SortGran mg;

	public Kamera(Scen scen)
	{
		this.scen = scen;
		x = 100;
		y = 100;
		z = 100;
		nx = 0;
		ny = 0;
		nz = 0;
		a = 144;
		d = 2;
		me = 1;
		perspektiva = false;
		vsetki = false;
		vsk = false;
		vgran = false;
		osveshenie = false;
		vreber = true;
		vtochek = false;
		vtext = true;
	}

	public void setSize(Dimension size)
	{
		if (width != size.width || height != size.height)
		{
			width = size.width;
			height = size.height;
			image = new int[height * width];
			imageZ = new float[height * width];
		}
		for (int i = 0; i < imageZ.length; i++)
		{
			image[i] = BGCOLOR;
			imageZ[i] = -1000000;
		}
	}

	public void Top()
	{
		x = 0;
		y = 0;
		z = 100;
		nx = 0;
		ny = 0;
		nz = 0;
		a = 0;
	}

	public void Bottom()
	{
		x = 0;
		y = 0;
		z = -100;
		nx = 0;
		ny = 0;
		nz = 0;
		a = 180;
	}

	public void Front()
	{
		x = 0;
		y = -100;
		z = 0;
		nx = 0;
		ny = 0;
		nz = 0;
		a = 0;
	}

	public void Back()
	{
		x = 0;
		y = 100;
		z = 0;
		nx = 0;
		ny = 0;
		nz = 0;
		a = 180;
	}

	public void Left()
	{
		x = -100;
		y = 0;
		z = 0;
		nx = 0;
		ny = 0;
		nz = 0;
		a = -90;
	}

	public void Right()
	{
		x = 100;
		y = 0;
		z = 0;
		nx = 0;
		ny = 0;
		nz = 0;
		a = 90;
	}

	// найти углы камеры
	private void setUgol()
	{
		double gip;
		double tx = nx - x, ty = ny - y, tz = nz - z;
		cosZ = Math.cos(a / GRADUSOVvRADIANE);
		sinZ = Math.sin(a / GRADUSOVvRADIANE);
		if (tx != 0 || ty != 0)
		{
			gip = Math.sqrt(tx * tx + tz * tz);	// гипотенуза
			if (gip == 0)
			{
				cosY = 1;
				sinY = 0;
			} else if (tz < 0)
			{
				cosY = -tx / gip;
				sinY = tz / gip;
			} else
			{
				cosY = tz / gip;
				sinY = tx / gip;
			}
			//tz = tz * cosY + ty * sinY;
			gip = Math.sqrt(ty * ty + tz * tz);	// гипотенуза
			if (gip == 0)
			{
				cosX = 1;
				sinX = 0;
			} else if (tz < 0)
			{
				cosX = -ty / gip;
				sinX = tz / gip;
			} else
			{
				cosX = tz / gip;
				sinX = ty / gip;
			}
		} else if (tz < 0)
		{
			cosY = 1;
			sinY = 0;
			cosX = 1;
			sinX = 0;
		} else
		{
			cosY = -1;
			sinY = 0;
			cosX = 1;
			sinX = 0;
		}

	}

	// видимая камерой сцена
	// перспектва ск -сетка -надписи -точки -грани -освещение
	public synchronized void render(Graphics g)
	{
		int i, j, s = 0, end;
		int xaInt, yaInt,
				xbInt, ybInt,
				xcInt, ycInt;	// координаты на экране
		double tx = nx - x, ty = ny - y, tz = nz - z;
		double x2, y2, z2;
		double zEkr = Math.sqrt(tx * tx + ty * ty + tz * tz);	// растояние до экрана
		setUgol();

		if (scen.mTel != null)
		{
			Point3D[] tmT;
			Gran tGran;
			// переписать точки обьектов
			// общее количество точек в сцене
			for (i = 0; i < scen.mTel.length; i++)
				if (scen.mTel[i].vis && scen.mTel[i].mp != null)
					s += scen.mTel[i].mp.length;
			mt = new Point3D[s];
			s = 0;
			for (i = 0; i < scen.mTel.length; i++)
				if (scen.mTel[i].vis && scen.mTel[i].mp != null)
				{	// получить глобальные координаты точек
					tmT = scen.mTel[i].getTochka(vtochek, vtext);

					for (j = 0; j < tmT.length; j++, s++)
					{	// пересчет координат
						tx = tmT[j].x + nx;
						ty = tmT[j].y + ny;
						tz = tmT[j].z + nz;
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
						if (perspektiva)
						{	// перспективное проецирование
							if (tz != zEkr)
							{
								if (tz > zEkr - d)
									tz = zEkr - d;
								tx = tx * zEkr / (zEkr - tz);
								ty = ty * zEkr / (zEkr - tz);
							}
						}	// паралельное проецирование x,y не изменяются
						// экранные координаты
						tmT[j].x = tx * me + width / 2;
						tmT[j].y = -ty * me + height / 2;
						tmT[j].z = tz;
						mt[s] = tmT[j];
					}
				}
			if (vreber)
			{   // переписать ребра обьектов
				// общее количество ребер в сцене
				s = 0;
				for (i = 0; i < scen.mTel.length; i++)
					if (scen.mTel[i].vis && scen.mTel[i].mp != null && scen.mTel[i].mr != null)
						s += scen.mTel[i].mr.length;
				mr = new Rebro[s];
				s = 0;
				end = 0; // смещение в масиве точек
				for (i = 0; i < scen.mTel.length; i++)
					if (scen.mTel[i].vis && scen.mTel[i].mp != null)
					{
						if (scen.mTel[i].mr != null)
							for (j = 0; j < scen.mTel[i].mr.length; j++, s++)
								mr[s] = new Rebro(scen.mTel[i].mr[j], end);
						end += scen.mTel[i].mp.length;
					}
			}
			if (vgran)
			{	// переписать грани обьектов
				mg = new SortGran();
				end = 0; // смещение в масиве точек
				for (i = 0; i < scen.mTel.length; i++)
					if (scen.mTel[i].vis && scen.mTel[i].mp != null && scen.mTel[i].mg != null)
					{
						for (j = 0; j < scen.mTel[i].mg.length; j++)
						{
							tGran = new Gran();
							// 3 точки
							tGran.a = scen.mTel[i].mg[j].a + end;
							tGran.b = scen.mTel[i].mg[j].b + end;
							tGran.c = scen.mTel[i].mg[j].c + end;
							double ax = mt[tGran.a].x, ay = mt[tGran.a].y, az = mt[tGran.a].z,
									bx = mt[tGran.b].x, by = mt[tGran.b].y, bz = mt[tGran.b].z,
									cx = mt[tGran.c].x, cy = mt[tGran.c].y, cz = mt[tGran.c].z;

							if (!(ax < 0 && bx < 0 && cx < 0 ||
									ax > width && bx > width && cx > width ||
									ay < 0 && by < 0 && cy < 0 ||
									ay > height && by > height && cy > height))
							{// грань или её часть видна
								// найти нормаль к грани
								tGran.nx = (by - ay) * (cz - az) - (bz - az) * (cy - ay);
								tGran.ny = (bz - az) * (cx - ax) - (bx - ax) * (cz - az);
								tGran.nz = (bx - ax) * (cy - ay) - (by - ay) * (cx - ax);

								if (osveshenie)
								{// найти нормали к вершинам
									// a
									mt[tGran.a].nx += tGran.nx;
									mt[tGran.a].ny += tGran.ny;
									mt[tGran.a].nz += tGran.nz;
									// b
									mt[tGran.b].nx += tGran.nx;
									mt[tGran.b].ny += tGran.ny;
									mt[tGran.b].nz += tGran.nz;
									// c
									mt[tGran.c].nx += tGran.nx;
									mt[tGran.c].ny += tGran.ny;
									mt[tGran.c].nz += tGran.nz;
								}
								// середина грани
								x2 = (ax + bx + cx) / 3;
								y2 = (ay + by + cy) / 3;
								z2 = (az + bz + cz) / 3;
								if (mt[tGran.a].nz < zEkr || mt[tGran.b].nz < zEkr || mt[tGran.c].nz < zEkr)
								//if (tGran.nx * tGran.nx + tGran.ny * tGran.ny + tGran.nz * tGran.nz + x2 * x2 + y2 * y2 + z2 * z2 >= (tGran.nx - x2) * (tGran.nx - x2) + (tGran.ny - y2) * (tGran.ny - y2) + (tGran.nz - z2) * (tGran.nz - z2))
								{
									tGran.material = scen.mTel[i].mg[j].material;
									tGran.z = x2 * x2 + y2 * y2 + z2 * z2;
									mg.add(tGran); // добавляем в список граней
								}
							}
						}
						end += scen.mTel[i].mp.length;
					}
			}
		}
		// прорисовка
		// сетка
		if (vsetki)
		{

		}
		if (scen.mTel != null)
		{
			// грани
			if (vgran)
			{
				while (mg != null)
				{
					if (mg.gran != null)
					{
						if (mg.gran.material >= 0)
							drawGranZ((int) mt[mg.gran.a].x, (int) mt[mg.gran.a].y, (float) mt[mg.gran.a].z,
									(int) mt[mg.gran.b].x, (int) mt[mg.gran.b].y, (float) mt[mg.gran.b].z,
									(int) mt[mg.gran.c].x, (int) mt[mg.gran.c].y, (float) mt[mg.gran.c].z, scen.mMaterial[mg.gran.material].color);
						else
							drawGranZ((int) mt[mg.gran.a].x, (int) mt[mg.gran.a].y, (float) mt[mg.gran.a].z,
									(int) mt[mg.gran.b].x, (int) mt[mg.gran.b].y, (float) mt[mg.gran.b].z,
									(int) mt[mg.gran.c].x, (int) mt[mg.gran.c].y, (float) mt[mg.gran.c].z, 0xfffefefe);

					}
					mg = mg.next;
				}
			}
			// ребра
			if (mr != null && vreber)
			{
				for (i = 0; i < mr.length; i++)
				{
					xaInt = (int) mt[mr[i].a].x;
					yaInt = (int) mt[mr[i].a].y;
					xbInt = (int) mt[mr[i].b].x;
					ybInt = (int) mt[mr[i].b].y;
					// внутри окна
					if (!(xaInt < 0 && xbInt < 0 || xaInt > width && xbInt > width
							|| yaInt < 0 && ybInt < 0 || yaInt > height && ybInt > height))
						drawLineZ(xaInt, yaInt, (float) mt[mr[i].a].z, xbInt, ybInt, (float) mt[mr[i].b].z, mr[i].color);
				}
			}
			// точки
			if (mt != null && vtochek)
			{
				for (i = 0; i < mt.length; i++)
				{
					xaInt = (int) mt[i].x;
					yaInt = (int) mt[i].y;
					if (xaInt >= 0 && xaInt < width && yaInt >= 0 && yaInt < height)
						drawPointZ(xaInt, yaInt, (float) mt[i].z, mt[i].d, mt[i].color);
				}
			}
		}
		// камеры
		if (vkam)
		{
		}
		// свет
		if (vlamp)
		{
		}
		// система координат
		if (vsk)
		{
			tx = 20;
			// поворот
			// поворот вокруг оси X
			// поворот вокруг оси Y
			tx = tx * cosY;
			// поворот вокруг оси Z
			x2 = tx * cosZ;
			ty = -tx * sinZ;
			tx = x2;
			xaInt = 25;
			yaInt = height - 25;
			xbInt = (int) (tx + xaInt);
			ybInt = (int) (-ty + yaInt);
			drawLine(xaInt, yaInt, xbInt, ybInt, 0xffff0000);
			tx = 0;
			ty = 20;
			// поворот
			// поворот вокруг оси X
			y2 = ty * cosX;
			tz = -ty * sinX;
			ty = y2;
			// поворот вокруг оси Y
			tx = tz * sinY;
			// поворот вокруг оси Z
			x2 = tx * cosZ + ty * sinZ;
			ty = ty * cosZ - tx * sinZ;
			tx = x2;
			xbInt = (int) (tx + xaInt);
			ybInt = (int) (-ty + yaInt);
			drawLine(xaInt, yaInt, xbInt, ybInt, 0xff00ff00);
			tz = 20;
			// поворот
			// поворот вокруг оси X
			ty = tz * sinX;
			tz = tz * cosX;
			// поворот вокруг оси Y
			tx = tz * sinY;
			// поворот вокруг оси Z
			x2 = tx * cosZ + ty * sinZ;
			ty = ty * cosZ - tx * sinZ;
			tx = x2;
			xbInt = (int) (tx + xaInt);
			ybInt = (int) (-ty + yaInt);
			drawLine(xaInt, yaInt, xbInt, ybInt, 0xff0000ff);
		}
		drawZbufer();
		pic = tool.createImage(new MemoryImageSource(width, height, image, 0, width));
		while (pic.getHeight(null) < 0) ;
		g.drawImage(pic, 0, 0, null);
		// надписи
		if (vtext && scen.mTel != null && mt != null)
		{
			for (i = 0; i < mt.length; i++)
				if (vtext && mt[i].text != null)
				{
					g.setColor(new Color(mt[i].colorText));
					g.setFont(textFont);
					g.drawString(mt[i].text, (int) mt[i].x, (int) mt[i].y);
				}
		}
	}

	// отобразить z буфер
	private void drawZbufer()
	{
		int i,j;
		float min= 1000000,max= -1000000;
		for (i = 0; i < imageZ.length; i++)
		{
			if (imageZ[i] > max)
				max = imageZ[i];
			if (imageZ[i] > -1000000 && imageZ[i] < min)
				min = imageZ[i];
		}
		for (i = 0; i < imageZ.length; i++)
		{
			if (imageZ[i] < min)
				image[i] = 0xff000000;
			else
			{
				j = (int) (0xff * (imageZ[i] - min) / (max - min));
				image[i] = 0xff000000 + (j << 16) + (j << 8) + j;
			}
		}
	}
	// Точка с учетом z буфера
	private void drawPointZ(int x1, int y1, float z1, int d1, int color)
	{
		int id;
		id = x1 + y1 * width;
		if (z1 >= imageZ[id])
		{
			image[id] = color;
			imageZ[id] = z1;
		}
	}

	// одноцветная линия
	private void drawLine(int x1, int y1, int x2, int y2, int color)
	{
		int xerr = 0, yerr = 0;
		int dx = x2 - x1, dy = y2 - y1;
		int incx, incy, d;
		if (dx > 0)
			incx = 1;
		else if (dx < 0)
			incx = -1;
		else
			incx = 0;
		if (dy > 0)
			incy = 1;
		else if (dy < 0)
			incy = -1;
		else
			incy = 0;
		dx = (dx > 0) ? dx : -dx;
		dy = (dy > 0) ? dy : -dy;
		d = (dx > dy) ? dx : dy;
		if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height)
			image[x1 + y1 * width] = color;
		for (int i = 0; i <= d; i++)
		{
			xerr = xerr + dx;
			yerr = yerr + dy;
			if (xerr > d)
			{
				xerr = xerr - d;
				x1 = x1 + incx;
			}
			if (yerr > d)
			{
				yerr = yerr - d;
				y1 = y1 + incy;
			}
			if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height)
				image[x1 + y1 * width] = color;
		}
	}

	// одноцветная линия с учетом Z буфера
	private void drawLineZ(int x1, int y1, float z1, int x2, int y2, float z2, int color)
	{
		int xerr = 0, yerr = 0;
		int dx = x2 - x1, dy = y2 - y1;
		int incx, incy, d;
		if (dx > 0)
			incx = 1;
		else if (dx < 0)
			incx = -1;
		else
			incx = 0;
		if (dy > 0)
			incy = 1;
		else if (dy < 0)
			incy = -1;
		else
			incy = 0;
		dx = (dx > 0) ? dx : -dx;
		dy = (dy > 0) ? dy : -dy;
		d = (dx > dy) ? dx : dy;
		float incz = (z2 - z1) / d;
		int index = x1 + y1 * width;
		if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height && z1 >= imageZ[index])
		{
			image[index] = color;
			imageZ[index] = z1;
		}
		for (int i = 0; i <= d; i++)
		{
			z1 += incz;
			xerr += dx;
			yerr += dy;
			if (xerr > d)
			{
				xerr -= d;
				x1 += incx;
			}
			if (yerr > d)
			{
				yerr -= d;
				y1 += incy;
			}
			if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height)
			{
				index = x1 + y1 * width;
				if (z1 >= imageZ[index])
				{
					image[index] = color;
					imageZ[index] = z1;
				}
			}
		}
	}


	// одноцветная горизонталь с учетом Z буфера
	private void drawHLineZ(int x1, float z1, int x2, float z2, int y1, int color)
	{
		if (y1 >= 0 && y1 < height)
		{	// горизонталь видна на экране
			int id;
			if (x1 != x2)
			{	// рисуем линию
				int i, dx;
				if (x2 < x1)
					dx = -1;
				else
					dx = 1;
				float dz = (z2 - z1) / (x2 - x1);
				for (i = x1; i != x2; i += dx)
				{
					if (i >= 0 && i < width)
					{
						id = i + y1 * width;
						if (z1 >= imageZ[id])
						{
							image[id] = color;
							imageZ[id] = z1;
						}
					}
					z1 += dz;
				}
			}
			if (x2 >= 0 && x2 < width)
			{
				id = x2 + y1 * width;
				if (z2 >= imageZ[id])
				{
					image[id] = color;
					imageZ[id] = z2;
				}
			}
		}
	}

	// одноцветная грань с учетом Z буфера
	private void drawGranZ(int ax, int ay, float az, int bx, int by, float bz, int cx, int cy, float cz, int color)
	{
		int i;
		float tFloat;
		// упорядочить вершины по y (ay>by>cy)
		if (ay < by)
		{
			i = bx;
			bx = ax;
			ax = i;
			i = by;
			by = ay;
			ay = i;
			tFloat = bz;
			bz = az;
			az = tFloat;
		}
		if (ay < cy)
		{
			i = cx;
			cx = ax;
			ax = i;
			i = cy;
			cy = ay;
			ay = i;
			tFloat = cz;
			cz = az;
			az = tFloat;
		}
		if (by < cy)
		{
			i = cx;
			cx = bx;
			bx = i;
			i = cy;
			cy = by;
			by = i;
			tFloat = cz;
			cz = bz;
			bz = tFloat;
		}

		int y;

		float x1Float, x2Float;
		float dx1Float, dx2Float;
		float z1, z2;
		float dz1Float, dz2Float;	// шаг по z

		if (ay == cy)
		{	// ac горизонтальная линия
			// грань является горизонтальной линией
			drawHLineZ(ax, az, bx, bz, ay, color);
			drawHLineZ(ax, az, cx, cz, ay, color);
			drawHLineZ(bx, bz, cx, cz, ay, color);
		} else
		{	// ac произвольная линия
			if (ay == by)
			{	// ab горизонтальная линия
				drawHLineZ(ax, az, bx, bz, ay, color);
			} else
			{   // ab произвольная линия
				// рисуем нижний треугольник
				x1Float = ax;
				z1 = az;
				x2Float = ax;
				z2 = az;
				dx1Float = (float) (bx - ax) / (by - ay);
				dz1Float = (bz - az) / (by - ay);
				dx2Float = (float) (cx - ax) / (cy - ay);
				dz2Float = (az - cz) / (ay - cy);
				for (y = ay; y > by; y--)
				{
					drawHLineZ((int) x1Float, z1, (int) x2Float, z2, y, color);
					x1Float -= dx1Float;
					z1 += dz1Float;
					x2Float -= dx2Float;
					z2 -= dz2Float;
				}
			}

			if (by == cy && by >= 0 && by < height)
			{   // bc горизонтальная линия
				drawHLineZ(bx, bz, cx, cz, by, color);
			} else
			{   // bc произвольная линия
				// рисуем верхний треугольник
				x1Float = cx;
				z1 = cz;
				x2Float = cx;
				z2 = cz;
				dx1Float = (float) (ax - cx) / (ay - cy);
				dz1Float = (az - cz) / (ay - cy);
				dx2Float = (float) (bx - cx) / (by - cy);
				dz2Float = (bz - cz) / (by - cy);
				for (y = cy; y <= by; y++)
				{
					drawHLineZ((int) x1Float, z1, (int) x2Float, z2, y, color);
					x1Float += dx1Float;
					z1 += dz1Float;
					x2Float += dx2Float;
					z2 += dz2Float;
				}
			}
		}
	}

	// -грань трехцветная
	private void drawGran(int ax, int ay, int bx, int by, int cx, int cy, int ac, int bc, int cc)
	{
		int ymin = Math.min(ay, Math.min(by, cy)),
				ymax = Math.max(ay, Math.max(by, cy));
		int y, x, x1, x2, ir1, ig1, ib1, ir2, ig2, ib2;

		float kab, kac, kbc,
				krac, krab, krbc,
				kgac, kgab, kgbc,
				kbac, kbab, kbbc,
				kx1x2;
		if (ay == by)
		{
			kab = 0;
			krab = 0;
			kgab = 0;
			kbab = 0;
		} else
		{
			kab = (bx - ax) / (by - ay);
			krab = (intR(bc) - intR(ac)) / (by - ay);
			kgab = (intG(bc) - intG(ac)) / (by - ay);
			kbab = (intB(bc) - intB(ac)) / (by - ay);
		}
		if (ay == cy)
		{
			kac = 0;
			krac = 0;
			kgac = 0;
			kbac = 0;
		} else
		{
			kac = (cx - ax) / (cy - ay);
			krac = (intR(cc) - intR(ac)) / (cy - ay);
			kgac = (intG(cc) - intG(ac)) / (cy - ay);
			kbac = (intB(cc) - intB(ac)) / (cy - ay);
		}
		if (by == cy)
		{
			kbc = 0;
			krbc = 0;
			kgbc = 0;
			kbbc = 0;
		} else
		{
			kbc = (cx - bx) / (cy - by);
			krbc = (intR(cc) - intR(bc)) / (cy - by);
			kgbc = (intG(cc) - intG(bc)) / (cy - by);
			kbbc = (intB(cc) - intB(bc)) / (cy - by);
		}

		for (y = ymin; y <= ymax; y++)
		{
			if (y == ay && y == by && y == cy)
			{
				if (ax <= bx && ax <= cx)
				{
					x1 = ax;
					ir1 = intR(ac);
					ig1 = intG(ac);
					ib1 = intB(ac);
				} else if (bx <= ax && bx <= cx)
				{
					x1 = bx;
					ir1 = intR(bc);
					ig1 = intG(bc);
					ib1 = intB(bc);
				} else
				{
					x1 = cx;
					ir1 = intR(cc);
					ig1 = intG(cc);
					ib1 = intB(cc);
				}
				if (ax >= bx && ax >= cx)
				{
					x2 = ax;
					ir2 = intR(ac);
					ig2 = intG(ac);
					ib2 = intB(ac);
				} else if (bx >= ax && bx >= cx)
				{
					x2 = bx;
					ir2 = intR(bc);
					ig2 = intG(bc);
					ib2 = intB(bc);
				} else
				{
					x2 = cx;
					ir2 = intR(cc);
					ig2 = intG(cc);
					ib2 = intB(cc);
				}
			} else if (y == ay && y == by)
			{
				x1 = ax;
				ir1 = intR(ac);
				ig1 = intG(ac);
				ib1 = intB(ac);
				x2 = bx;
				ir2 = intR(bc);
				ig2 = intG(bc);
				ib2 = intB(bc);
			} else if (y == ay && y == cy)
			{
				x1 = ax;
				ir1 = intR(ac);
				ig1 = intG(ac);
				ib1 = intB(ac);
				x2 = cx;
				ir2 = intR(cc);
				ig2 = intG(cc);
				ib2 = intB(cc);
			} else if (y == by && y == cy)
			{
				x1 = bx;
				ir1 = intR(bc);
				ig1 = intG(bc);
				ib1 = intB(bc);
				x2 = cx;
				ir2 = intR(cc);
				ig2 = intG(cc);
				ib2 = intB(cc);
			} else if (y >= ay && y >= by && y <= cy || y <= ay && y <= by && y >= cy)
			{ // ac bc
				if (y == cy)
				{
					x1 = cx;
					ir1 = intR(cc);
					ig1 = intG(cc);
					ib1 = intB(cc);
					x2 = cx;
					ir2 = ir1;
					ig2 = ig1;
					ib2 = ib1;
				} else
				{   // ac
					if (kac == 0 || y == ay)
						x1 = ax;
					else
						x1 = ax + (int) ((y - ay) * kac);
					ir1 = intR(ac) + (int) ((y - ay) * krac);
					ig1 = intG(ac) + (int) ((y - ay) * kgac);
					ib1 = intB(ac) + (int) ((y - ay) * kbac);
					// bc
					if (kbc == 0 || y == by)
						x2 = bx;
					else
						x2 = bx + (int) ((y - by) * kbc);
					ir2 = intR(bc) + (int) ((y - by) * krbc);
					ig2 = intG(bc) + (int) ((y - by) * kgbc);
					ib2 = intB(bc) + (int) ((y - by) * kbbc);
				}
			} else if (y >= ay && y >= cy && y <= by || y <= ay && y <= cy && y >= by)
			{ // ab bc
				if (y == by)
				{
					x1 = bx;
					ir1 = intR(bc);
					ig1 = intG(bc);
					ib1 = intB(bc);
					x2 = bx;
					ir2 = ir1;
					ig2 = ig1;
					ib2 = ib1;
				} else
				{   // ab
					if (kab == 0 || y == ay)
						x1 = ax;
					else
						x1 = ax + (int) ((y - ay) * kab);
					ir1 = intR(ac) + (int) ((y - ay) * krab);
					ig1 = intG(ac) + (int) ((y - ay) * kgab);
					ib1 = intB(ac) + (int) ((y - ay) * kbab);
					// bc
					if (kbc == 0 || y == cy)
						x2 = cx;
					else
						x2 = bx + (int) ((y - by) * kbc);
					ir2 = intR(bc) + (int) ((y - by) * krbc);
					ig2 = intG(bc) + (int) ((y - by) * kgbc);
					ib2 = intB(bc) + (int) ((y - by) * kbbc);
				}
			} else
			{ // ab ac
				if (y == ay)
				{
					x1 = ax;
					ir1 = intR(ac);
					ig1 = intG(ac);
					ib1 = intB(ac);
					x2 = ax;
					ir2 = ir1;
					ig2 = ig1;
					ib2 = ib1;
				} else
				{   // ab
					if (kab == 0 || y == by)
						x1 = bx;
					else
						x1 = ax + (int) ((y - ay) * kab);
					ir1 = intR(ac) + (int) ((y - ay) * krab);
					ig1 = intG(ac) + (int) ((y - ay) * kgab);
					ib1 = intB(ac) + (int) ((y - ay) * kbab);
					// ac
					if (kac == 0 || y == cy)
						x2 = cx;
					else
						x2 = ax + (int) ((y - ay) * kac);
					ir2 = intR(ac) + (int) ((y - ay) * krac);
					ig2 = intG(ac) + (int) ((y - ay) * kgac);
					ib2 = intB(ac) + (int) ((y - ay) * kbac);
				}
			}

			//
			if (x1 > x2)
			{
				x = x1;
				x1 = x2;
				x2 = x;
				x = ir1;
				ir1 = ir2;
				ir2 = x;
				x = ig1;
				ig1 = ig2;
				ig2 = x;
				x = ib1;
				ib1 = ib2;
				ib2 = x;
			}

			for (x = x1; x <= x2; x++)
				if (x >= 0 && x < width && y >= 0 && y < height)
				{
					if (x2 == x1)
						image[x + y * width] = RGBint(ir1, ig1, ib1);
					else
					{
						kx1x2 = (x - x1) / (x2 - x1);
						image[x + y * width] = RGBint(ir1 + (int) ((ir2 - ir1) * kx1x2),
								ig1 + (int) ((ig2 - ig1) * kx1x2),
								ib1 + (int) ((ib2 - ib1) * kx1x2));
					}
				}
		}
	}

	private int intR(int i)
	{
		return i & 0xff;
	}

	private int intG(int i)
	{
		return i & 0xff00 >> 8;
	}

	private int intB(int i)
	{
		return i & 0xff0000 >> 16;
	}

	private int RGBint(int r, int g, int b)
	{
		return 0xff000000 | r | g << 8 | b << 16;
	}

	// перемещение камеры
	public void mov(int movX, int movY)
	{
		double tx, ty, tz;
		double x2, y2;
		tx = -movX / me;
		ty = movY / me;
		// поворот
		// поворот вокруг оси Z
		x2 = tx * cosZ - ty * sinZ;
		ty = ty * cosZ + tx * sinZ;
		tx = x2;
		// поворот вокруг оси Y
		x2 = tx * cosY;
		tz = tx * sinY;
		tx = x2;
		// поворот вокруг оси X
		y2 = ty * cosX - tz * sinX;
		tz = tz * cosX + ty * sinX;
		ty = y2;
		x += tx;
		y += ty;
		z += tz;
		nx += tx;
		ny += ty;
		nz += tz;
	}

	//- вращение камеры
	public void rotC(int movX, int movY)
	{
		double tx = nx - x, ty = ny - y, tz = nz - z;
		double y2, x2;
		double ax, ay;
		// углы
		ay = 1.6 * movX / width;
		ax = -1.6 * movY / height;
		// наити z
		tz = Math.sqrt(tx * tx + ty * ty + tz * tz);
		// повернуть по X
		ty = -tz * Math.sin(ax);
		tz = tz * Math.cos(ax);
		// повернуть по Y
		tx = -tz * Math.sin(ay);
		tz = tz * Math.cos(ay);
		// преобразовать в мировую СК
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
		nx = tx + x;
		ny = ty + y;
		nz = tz + z;
		setUgol();
	}

	//- вращение камеры вокруг фокуса
	public void rot0(int movX, int movY)
	{
		double tx = nx - x, ty = ny - y, tz = nz - z;
		double y2, x2;
		double ax, ay;
		// углы
		ay = 1.6 * movX / width;
		ax = -1.6 * movY / height;
		// наити z
		tz = Math.sqrt(tx * tx + ty * ty + tz * tz);
		// повернуть по X
		ty = -tz * Math.sin(ax);
		tz = tz * Math.cos(ax);
		// повернуть по Y
		tx = -tz * Math.sin(ay);
		tz = tz * Math.cos(ay);
		// преобразовать в мировую СК
		// поворот вокруг оси Z
		x2 = tx * cosZ + ty * sinZ;
		ty = ty * cosZ - tx * sinZ;
		tx = x2;
		// поворот вокруг оси Y
		x2 = tx * cosY + tz * sinY;
		tz = tz * cosY - tx * sinY;
		tx = x2;
		// поворот вокруг оси X
		y2 = ty * cosX + tz * sinX;
		tz = tz * cosX - ty * sinX;
		ty = y2;
		// перенос
		x = nx - tx;
		y = ny - ty;
		z = nz - tz;
		setUgol();
	}

	// вращение камеры относительно оси
	public void rotZ(int x1, int y1, int x2, int y2)
	{
		if (x1 < width / 2)
			a -= 135.0 * (y2 - y1) / height;
		else
			a += 135.0 * (y2 - y1) / height;
		if (y1 > height / 2)
			a -= 135.0 * (x2 - x1) / width;
		else
			a += 135.0 * (x2 - x1) / width;
	}

	// изменение растояние от камеры до фокуса
	public void movZ(int movY)
	{
		double tx, ty, tz;
		tz = -movY / me;
		// поворот
		// поворот вокруг оси Z
		// поворот вокруг оси Y
		tx = -tz * sinY;
		tz = tz * cosY;
		// поворот вокруг оси X
		ty = -tz * sinX;
		tz = tz * cosX;
		x += tx;
		y += ty;
		z += tz;
	}

	// изменение размера экрана
	public void setSizeEkr(int movY)
	{
		double t = me * (1 + (double) movY / 100);
		me = (t > 0.01) ? t : me;
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeDouble(nx);
		out.writeDouble(ny);
		out.writeDouble(nz);
		out.writeDouble(a);
		out.writeDouble(d);
		out.writeDouble(me);
		out.writeBoolean(perspektiva);
		out.writeBoolean(vsetki);
		out.writeBoolean(vsk);
		out.writeBoolean(vkam);
		out.writeBoolean(vlamp);
		out.writeBoolean(vgran);
		out.writeBoolean(osveshenie);
		out.writeBoolean(vreber);
		out.writeBoolean(vtochek);
		out.writeBoolean(vtext);
	}

	public void load(DataInputStream in) throws IOException
	{
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		nx = in.readDouble();
		ny = in.readDouble();
		nz = in.readDouble();
		a = in.readDouble();
		d = in.readDouble();
		me = in.readDouble();
		perspektiva = in.readBoolean();
		vsetki = in.readBoolean();
		vsk = in.readBoolean();
		vkam = in.readBoolean();
		vlamp = in.readBoolean();
		vgran = in.readBoolean();
		osveshenie = in.readBoolean();
		vreber = in.readBoolean();
		vtochek = in.readBoolean();
		vtext = in.readBoolean();
	}


}
