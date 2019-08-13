import java.io.IOException;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Alex1
 * Date: 18.11.2006
 * Time: 21:35:57
 * Точка
 * ├координаты
 * ├нормаль
 * ├текст
 * ├диаметр
 * ├цвет
 * ├массив ключевых кадров
 * │├координат
 * │└текста
 * ├значения ключевых кадров
 * │├координат
 * │└текста
 * └цвет
 */
// сохранение загрузка диаметр color
public class Tochka
{
	public double x, y, z;			// текущие координаты
	public String text;				// текущая надпись
	public int d;					// диаметр
	public int color;				// цвет
	public int aKey[];				// кадры анимации перемещения
	public double ax[], ay[], az[];	// анимация координат
	public int colorText;			// цвет текста
	public int aTextKey[];			// кадры анимации текста
	public String aText[];			// анимация текста


	Tochka()
	{
		x = 0;
		y = 0;
		z = 0;
		text = null;
		d = 1;
		color = 0xffffffff;
		colorText = 0xffffffff;
		aKey = new int[]{0};
		aTextKey = new int[]{0};
		ax = new double[]{0};
		ay = new double[]{0};
		az = new double[]{0};
		aText = new String[]{""};
	}

	Tochka(double x, double y, double z)
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
		text = null;
		d = 1;
		color = 0xffffffff;
		colorText = 0xffffffff;
		aKey = new int[]{0};
		aTextKey = new int[]{0};
		ax = new double[]{x};
		ay = new double[]{y};
		az = new double[]{z};
		aText = new String[]{""};
	}

	Tochka(DataInputStream in) throws IOException
	{
		int i,l;
		d=in.readInt();
		color=in.readInt();
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
		colorText=in.readInt();
		l=in.readInt();
		aTextKey=new int[l];
		aText= new String[l];
		for (i=0;i<l;i++)
		{
			aTextKey[i]=in.readInt();
			aText[i]=in.readUTF();
		}
	}

	public void Set(double x, double y, double z)
	{
		ax = new double[]{x};
		ay = new double[]{y};
		az = new double[]{z};
	}

	// переместить точку в указаный момент
	public void setKadr(double kadr)
	{
		boolean flag = true;
		// наити координаты
		if (kadr == 0 && aKey.length == 1)
		{	// начальное состояние, отсутствие анимации
			x = ax[0];
			y = ay[0];
			z = az[0];
		} else
		{	// промежуточное состояние
			int i;
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
		// найти текст
		if (kadr == 0 && aTextKey.length == 1)
			// начальное состояние, отсутствие анимации
			text = aText[0];
		else
		{	// промежуточное состояние
			int i;
			for (i = aTextKey.length - 1; i > 0 && kadr < aTextKey[i]; i--)
			{
			}
			if (i < 0)
				// между начальным состоянием и первым ключем
				text = aText[0];
			else
				// ключ или дальше
				text = aText[i];
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
	// изменить количество ключей анимации текста
	public void setAnimText(int n)
	{
		if (n>0 && aTextKey.length!=n)
		{
			aTextKey = Arrays.copyOf(aTextKey, n);
			aText =Arrays.copyOf(aText, n);
		}
	}

	public void save(DataOutputStream out) throws IOException
	{
		int i,l;
		out.writeInt(d);
		out.writeInt(color);
		l=aKey.length;
		out.writeInt(l);
		for (i=0;i<l;i++)
		{
			out.writeInt(aKey[i]);
			out.writeDouble(ax[i]);
			out.writeDouble(ay[i]);
			out.writeDouble(az[i]);
		}
		out.writeInt(colorText);
		l=aTextKey.length;
		out.writeInt(l);
		for (i=0;i<l;i++)
		{
			out.writeInt(aTextKey[i]);
			out.writeUTF(aText[i]);
		}
	}
}
