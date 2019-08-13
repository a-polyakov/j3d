package polyakov.java3d.object.scen.primitives;

import polyakov.java3d.object.dynamical.Tochka;
import polyakov.java3d.object.dynamical.Rebro;
import polyakov.java3d.object.dynamical.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 09.03.2010
 * Time: 14:08:06
 * TODO.
 */
public class SK extends Telo
{
	public SK(Scen scen, int id)
	{
		super(scen, id, "xyz", 4, 3, 0);
		addTochka(0, 0, 0);	//0
		addTochka(10, 0, 0);//1
		addTochka(0, 10, 0);//2
		addTochka(0, 0, 10);//3
		addRebro(0, 1);
		addRebro(0, 2);
		addRebro(0, 3);
	}
}
