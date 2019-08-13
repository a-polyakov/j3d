package polyakov.java3d.object.dynamical;

import polyakov.java3d.object.dynamical.Telo;
import polyakov.java3d.object.dynamical.Kamera;
import polyakov.java3d.object.dynamical.Lamp;
import polyakov.java3d.object.dynamical.Material;
import polyakov.java3d.object.scen.primitives.Box;
import polyakov.java3d.object.statical.ScenStatical;
import polyakov.java3d.object.dunamic.DunamicObject;
import polyakov.java3d.file.File3DS;
import polyakov.java3d.file.FileJava3D;

import java.io.*;
import java.util.Arrays;

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
 * <p/>
 * !!!
 * хранить длину каждого масива и при удалении просто изменять длину масив не трогать
 * масивы увеличивать если добаляем элемент
 * сохранять в файл только указаную днину масивов
 */
public class Scen extends ScenStatical implements FileJava3D, DunamicObject
{
	public int maxKadr;			// продолжительность анимации

	private double kadr;		// текущий кадр

	// констриктор класса сцена по умолчанию
	public Scen()
	{
		Kamera kamera = new Kamera(this, mLampLen, "Front");
		kamera.Front();
		addKamera(kamera);
		kamera = new Kamera(this, mLampLen, "Left");
		kamera.Left();
		addKamera(kamera);
		kamera = new Kamera(this, mLampLen, "Top");
		kamera.Top();
		addKamera(kamera);
		kamera = new Kamera(this, mLampLen, "Kamera");
		kamera.Front();
		addKamera(kamera);

		Material material = new Material(this, mMaterialLen, 0xff0000ff);
		addMaterial(material);
		material = new Material(this, mMaterialLen, 0xff00ff00);
		addMaterial(material);
		material = new Material(this, mMaterialLen, 0xffff0000);
		addMaterial(material);

		Telo telo = new Box(this, mTelLen, "Z", 9, 9, 50);
		telo.mov.setKey(0, 0, 0, 0);
		telo.mov.setKey(100, 100, 0, 0);
		telo.mov.setKey(200, 0, 0, 0);
		telo.setMaterial(mMaterial[0]);
		telo.mp[7].textA.setKey(0, "Z");
		telo.mp[7].colorText = 0xff0000ff;
		addTelo(telo);

		telo = new Box(this, mTelLen, "Y", 6, 50, 6);
		telo.rot.setKey(0, 0, 0, 0);
		telo.rot.setKey(200, 360, 0, 0);
		telo.setMaterial(mMaterial[1]);
		telo.mp[7].textA.setKey(0, "Y");
		telo.mp[7].colorText = 0xff00ff00;
		addTelo(telo);
		telo = new Box(this, mTelLen, "X", 50, 3, 3);
		telo.mp[0].mov.setKey(0, 0, 0, 0);
		telo.mp[0].mov.setKey(100, -10, -10, -10);
		telo.mp[0].mov.setKey(200, 0, 0, 0);
		telo.setMaterial(mMaterial[2]);
		telo.mp[7].textA.setKey(0, "X");
		telo.mp[7].colorText = 0xffff0000;
		addTelo(telo);

		Lamp lamp = new Lamp(this, 0, "Lamp1", 100, 100, 100, 0xffffffff);
		addLamp(lamp);
		maxKadr = 200;
		setKadr(0);
	}

	// новая сцена, удаление тел, источников света, материалов
	public void clear()
	{
		super.clear();
		maxKadr = 100;
		setKadr(0);
	}

	// загрузка сцены
	public boolean loadScen(InputStream file)
	{
		boolean r = true;
		try
		{
			DataInputStream in = new DataInputStream(file);
			load(in);
		}
		catch (Exception e)
		{
			r = false;
		}
		return r;
	}

	// загрузка сцены
	public void load(DataInputStream in) throws IOException
	{
		mMaterialLen = in.readInt();
		if (mMaterialLen < 0)
			mMaterialLen = 0;
		mMaterial = new Material[mMaterialLen];
		for (int i = 0; i < mMaterialLen; i++)
		{
			Material material = new Material(this, i);
			material.load(in);
			mMaterial[i] = material;
		}
		mTelLen = in.readInt();
		if (mTelLen < 0)
			mTelLen = 0;
		mTel = new Telo[mTelLen];
		for (int i = 0; i < mTelLen; i++)
		{
			Telo telo = new Telo(this, i);
			telo.load(in);
			mTel[i] = telo;
		}
		mKamerLen = in.readInt();
		for (int i = 0; i < mKamerLen; i++)
		{
			Kamera kamera = new Kamera(this, i);
			kamera.load(in);
			mKamer[i] = kamera;
		}
		mLampLen = in.readInt();
		if (mLampLen < 0)
			mLampLen = 0;
		mLamp = new Lamp[mLampLen];
		for (int i = 0; i < mLampLen; i++)
		{
			Lamp lamp = new Lamp(this, i);
			lamp.load(in);
			mLamp[i] = lamp;
		}
		maxKadr = in.readInt();
	}

	// сохранение сцены
	public boolean saveScen(String fileName)
	{
		boolean r = true;
		try
		{
			DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
			save(out);
		}
		catch (Exception e)
		{
			r = false;
		}
		return r;
	}


	// сохранение сцены
	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(mMaterialLen);
		for (int i = 0; i < mMaterialLen; i++)
			mMaterial[i].save(out);
		out.writeInt(mTelLen);
		for (int i = 0; i < mTelLen; i++)
			mTel[i].save(out);
		out.writeInt(mKamerLen);
		for (int i = 0; i < mKamerLen; i++)
			mKamer[i].save(out);
		out.writeInt(mLampLen);
		for (int i = 0; i < mLampLen; i++)
			mLamp[i].save(out);
		out.writeInt(maxKadr);
	}

	// импорт объектов в сцену из 3DS файла
	public boolean importScen3DS(String fileName)
	{
		try
		{
			return File3DS.importScen3DS(new FileInputStream(fileName), this);
		}
		catch (IOException e)
		{	// ошибка чтения файла
			return false;
		}
	}


	// перевести сцену в указаный момент
	public void setKadr(double kadr)
	{
		if (this.kadr != kadr)
		{
			this.kadr = kadr;
			for (int i = 0; i < mMaterialLen; i++)
				if (mMaterial[i] != null)
					mMaterial[i].setKadr(kadr);
			for (int i = 0; i < mTelLen; i++)
				if (mTel[i] != null)
					mTel[i].setKadr(kadr);
			for (int i = 0; i < mKamerLen; i++)
				if (mKamer[i] != null)
					mKamer[i].setKadr(kadr);
			for (int i = 0; i < mLampLen; i++)
				if (mLamp[i] != null)
					mLamp[i].setKadr(kadr);
		}
	}
}
