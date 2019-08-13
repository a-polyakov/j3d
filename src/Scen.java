import java.io.*;
import java.util.Arrays;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 11.02.2007
 * Time: 16:05:02
 * Сцена
 * ├камеры
 * ├тела
 * ├свет
 * ├материалы
 * └продолжетельность анимации
 */
public class Scen
{
	public Telo mTel[];			// массив тел
	public Kamera mKamer[];		// массив камер
	public Lamp mLamp[];		// массив источников света
	public Material mMaterial[];// массив материалов
	public int maxKadr;			// продолжительность анимации

	// констриктор класса сцена по умолчанию
	Scen()
	{
		mKamer = new Kamera[4];
		mKamer[0] = new Kamera(this);
		mKamer[0].Front();
		mKamer[1] = new Kamera(this);
		mKamer[1].Left();
		mKamer[2] = new Kamera(this);
		mKamer[2].Top();
		mKamer[3] = new Kamera(this);

		mMaterial = new Material[3];
		mMaterial[0] = new Material(0xff0000ff);
		mMaterial[1] = new Material(0xff00ff00);
		mMaterial[2] = new Material(0xffff0000);

		mTel = new Telo[3];
		mTel[0] = new Telo("Z");
		mTel[0].Box(9, 9, 50);
		mTel[0].aKey = new int[]{0, 100, 200};
		mTel[0].ax = new double[]{0, 100, 0};
		mTel[0].ay = new double[]{0, 0, 0};
		mTel[0].az = new double[]{0, 0, 0};
		mTel[0].setMaterial(0);
		mTel[0].mp[7].aText =new String[]{"Z"};
		mTel[0].mp[7].colorText=0xff0000ff;
		mTel[1] = new Telo("Y");
		mTel[1].Box(6, 50, 6);
		mTel[1].aRotKey = new int[]{0, 200};
		mTel[1].arx = new double[]{0, 360};
		mTel[1].ary = new double[]{0, 0};
		mTel[1].arz = new double[]{0, 0};
		mTel[1].setMaterial(1);
		mTel[1].mp[7].aText =new String[]{"Y"};
		mTel[1].mp[7].colorText=0xff00ff00;
		mTel[2] = new Telo("X");
		mTel[2].Box(50, 3, 3);
		mTel[2].mp[0].aKey = new int[]{0, 100, 200};
		mTel[2].mp[0].ax = new double[]{0, -10, 0};
		mTel[2].mp[0].ay = new double[]{0, -10, 0};
		mTel[2].mp[0].az = new double[]{0, -10, 0};
		mTel[2].setMaterial(2);
		mTel[2].mp[7].aText =new String[]{"X"};
		mTel[2].mp[7].colorText=0xffff0000;

		mLamp = new Lamp[1];
		mLamp[0] = new Lamp();

		maxKadr = 200;
		setKadr(0);
	}

	// перевести сцену в указаный момент
	public void setKadr(double kadr)
	{
		int i;
		if (mTel!=null)
		for (i = 0; i < mTel.length; i++)
			if (mTel[i]!=null)
				mTel[i].setKadr(kadr);
		if (mLamp != null)
		for (i = 0; i < mLamp.length; i++)
			if (mLamp[i]!=null)
				mLamp[i].setKadr(kadr);
	}

	// новая сцена, удаление тел, источников света, материалов
	public void newScen()
	{
		mMaterial = null;
		mTel = null;
		mLamp = null;
		maxKadr = 100;
		setKadr(0);
	}

	// загрузка сцены
	public boolean loadScen(InputStream file)
	{
		try
		{
			DataInputStream in = new DataInputStream(file);
			int i,l;
			l=in.readInt();
			if (l>0)
			{
				mTel=new Telo[l];
				for (i=0;i<l; i++)
					mTel[i]=new Telo(in);
			}
			else
				mTel=null;
			mKamer[0].load(in);
			mKamer[1].load(in);
			mKamer[2].load(in);
			mKamer[3].load(in);
			l=in.readInt();
			if (l>0)
			{
				mLamp=new Lamp[l];
				for (i=0;i<l; i++)
					mLamp[i]=new Lamp(in);
			}
			else
				mLamp=null;
			l=in.readInt();
			if (l>0)
			{
				mMaterial=new Material[l];
				for (i=0;i<l; i++)
					mMaterial[i]=new Material(in);
			}
			else
				mMaterial=null;
			maxKadr=in.readInt();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	// сохранение сцены
	public boolean saveScen(String fileName)
	{
		try
		{
			DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
			int i,l;
			if (mTel!=null)
			{
				l=mTel.length;
				out.writeInt(l);
				for (i=0;i<l; i++)
					mTel[i].save(out);
			}
			else
				out.writeInt(0);
			mKamer[0].save(out);
			mKamer[1].save(out);
			mKamer[2].save(out);
			mKamer[3].save(out);
			if (mLamp!=null)
			{
				l=mLamp.length;
				out.writeInt(l);
				for (i=0;i<l; i++)
					mLamp[i].save(out);
			}
			else
				out.writeInt(0);
			if (mMaterial!=null)
			{
				l=mMaterial.length;
				out.writeInt(l);
				for (i=0;i<l; i++)
					mMaterial[i].save(out);
			}
			else
				out.writeInt(0);
			out.writeInt(maxKadr);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	// импорт объектов в сцену из 3DS файла
	public boolean importScen3DS(String fileName)
	{
		try
		{
			return File3DS.importScen3DS(new FileInputStream(fileName),this);
		}
		catch (IOException e)
		{	// ошибка чтения файла
			return false;
		}
	}


	// добавление тела
	public void addTelo()
	{
		if (mTel!=null)
		{
			int i=mTel.length;
			mTel= Arrays.copyOf(mTel, i+1);
			mTel[i]=new Telo("Telo "+i);
		}
		else
			mTel=new Telo[]{new Telo("Telo 1")};
	}
	// удалить тело с номером id
	public void delTelo(int id)
	{
		if (mTel!=null && mTel.length>id)
		{
			mTel[id]=mTel[mTel.length-1];
			mTel= Arrays.copyOf(mTel, mTel.length-1);
		}
	}
	// добавление материал
	public void addMaterial()
	{
		if (mMaterial !=null)
		{
			int i=mMaterial.length;
			mMaterial= Arrays.copyOf(mMaterial, i+1);
			mMaterial[i]=new Material("Материал "+i);
		}
		else
			mMaterial=new Material[]{new Material("Материал 1")};
	}
	// удалить материал с номером id
	public void delMaterial(int id)
	{
		if (mMaterial !=null && mMaterial.length>id)
		{
			mMaterial[id]=mMaterial[mMaterial.length-1];
			mMaterial= Arrays.copyOf(mMaterial, mMaterial.length-1);
		}
	}
}
