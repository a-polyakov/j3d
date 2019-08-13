package polyakov.java3d.object.statical;

import polyakov.java3d.object.dynamical.*;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 11.02.2007
 * Time: 16:05:02
 * состояние сцены в определенный момент времени
 */
public abstract class ScenStatical
{
	public Telo mTel[];			// массив тел
	public int mTelLen;
	public Kamera mKamer[];		// массив камер
	public int mKamerLen;
	public Lamp mLamp[];		// массив источников света
	public int mLampLen;
	public Material mMaterial[];// массив материалов
	public int mMaterialLen;

	// определить статическую сцену
	public ScenStatical()
	{
		mTel = new Telo[4];
		mTelLen = 0;
		mKamer = new Kamera[4];
		mKamerLen = 0;
		mLamp = new Lamp[4];
		mLampLen = 0;
		mMaterial = new Material[4];
		mMaterialLen = 0;
	}

	public void clear()
	{
		for (int i = mTelLen-1; i >= 0; i--)
			mTel[i].remove();
		mKamer[0].clear();
		mKamer[0].Front();
		mKamer[1].clear();
		mKamer[1].Left();
		mKamer[2].clear();
		mKamer[2].Top();
		mKamer[3].clear();
		mKamer[3].kamera.setKey(0,100,100,100);
		for (int i = mKamerLen-1; i >= 3; i--)
			mKamer[i].remove();
		for (int i = mLampLen-1; i >= 0; i--)
			mLamp[i].remove();
		for (int i = mMaterialLen-1; i >= 0; i--)
			mMaterial[i].remove();
	}

	// добавление тела
	public void addTelo(Telo telo)
	{
		mTelLen++;
		if (mTelLen > mTel.length)
		{
			mTel = Arrays.copyOf(mTel, mTelLen);
		}
		mTel[mTelLen - 1] = telo;
	}

	// добавить камеру
	public void addKamera(Kamera kamera)
	{
		mKamerLen++;
		if (mKamerLen > mKamer.length)
		{
			mKamer = Arrays.copyOf(mKamer, mKamerLen);
		}
		mKamer[mKamerLen - 1] = kamera;
	}

	// добавить источник света
	public void addLamp(Lamp lamp)
	{
		mLampLen++;
		if (mLampLen > mLamp.length)
		{
			mLamp = Arrays.copyOf(mLamp, mLampLen);
		}
		mLamp[mLampLen - 1] = lamp;
	}

	// добавить материал
	public void addMaterial(Material material)
	{
		mMaterialLen++;
		if (mMaterialLen > mMaterial.length)
		{
			mMaterial = Arrays.copyOf(mMaterial, mMaterialLen);
		}
		mMaterial[mMaterialLen - 1] = material;
	}
}