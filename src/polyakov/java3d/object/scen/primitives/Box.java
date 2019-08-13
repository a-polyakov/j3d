package polyakov.java3d.object.scen.primitives;

import polyakov.java3d.object.dynamical.Telo;
import polyakov.java3d.object.dynamical.Tochka;
import polyakov.java3d.object.dynamical.Rebro;
import polyakov.java3d.object.dynamical.Gran;
import polyakov.java3d.object.dynamical.Scen;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 09.03.2010
 * Time: 13:58:56
 * параллелипипет
 * !!!
 */
public class Box extends Telo
{
	public Box(Scen scen, int id, String name, double a, double b, double c)
	{
		super(scen, id, name, 8, 12, 12);
		addTochka(0, 0, 0); //0
		addTochka(0, 0, c); //1
		addTochka(0, b, 0); //2
		addTochka(0, b, c); //3
		addTochka(a, 0, 0); //4
		addTochka(a, 0, c); //5
		addTochka(a, b, 0); //6
		addTochka(a, b, c); //7
		addRebro(0, 1);
		addRebro(0, 2);
		addRebro(0, 4);
		addRebro(3, 7);
		addRebro(1, 3);
		addRebro(1, 5);
		addRebro(2, 3);
		addRebro(2, 6);
		addRebro(4, 5);
		addRebro(4, 6);
		addRebro(5, 7);
		addRebro(6, 7);
		addGran(0, 2, 1);
		addGran(1, 2, 3);
		addGran(4, 5, 6);
		addGran(5, 7, 6);
		addGran(0, 1, 4);
		addGran(1, 5, 4);
		addGran(0, 2, 4);
		addGran(2, 6, 4);
		addGran(2, 6, 3);
		addGran(3, 6, 7);
		addGran(1, 5, 3);
		addGran(3, 5, 7);
	}
}
