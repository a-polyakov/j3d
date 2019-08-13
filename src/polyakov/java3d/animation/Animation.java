package polyakov.java3d.animation;

import polyakov.java3d.Index;

/**
 * Created by IntelliJ IDEA.
 * User: Alex1
 * Date: 22.04.2007
 * Time: 14:59:48
 * прорисовка экрана 30 раз в секунду
 */
public class Animation extends Thread
{
	Index index;
	private double dkadr;	// изминение кадра
	boolean flag;
	private double maxKadr;
	private double kadr;

	Animation(Index index)
	{
		flag = true;
		this.index = index;
		maxKadr = index.scen.maxKadr;
		kadr = 0;
	}

	public void run()
	{
		int intKadr;
		long startTime;
		while (flag && dkadr != 0)
		{
			startTime = System.currentTimeMillis();	// начало рендеринга
			kadr += dkadr;
			if (kadr > maxKadr) kadr = 0;	// цикличность анимации
			else if (kadr < 0) kadr = maxKadr;
			intKadr = new Double(kadr).intValue();
			index.kadr.setValue(intKadr);	// выставить полосу прокрутки
			index.kadrLabel.setText(String.valueOf(intKadr));
			index.scen.setKadr(kadr);	// выставить сцену в положение текущего кадра
			index.repaintCenter();		// перерисовать окна
			if (System.currentTimeMillis() - startTime < 33)
			{	// время затраченое на рендеринг
				try
				{
					sleep(33 - System.currentTimeMillis() + startTime);
				}
				catch (InterruptedException e)
				{
				}
			}
			else
			{
				kadr+=dkadr*(System.currentTimeMillis() - startTime-33)/33; // сдвинуть кадр на время запаздывания
			}
			index.options.setText("t "+(System.currentTimeMillis() - startTime));
		}
	}

	public void setFPS(int fps)
	{
		dkadr = (double) fps / 30;
	}

	public void setKadr(int kadr)
	{
		this.kadr = kadr;
	}
}
