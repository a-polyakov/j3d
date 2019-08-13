package polyakov.java3d.object.scen.primitives;

import polyakov.java3d.object.dynamical.Tochka;
import polyakov.java3d.object.dynamical.Rebro;
import polyakov.java3d.object.dynamical.Gran;
import polyakov.java3d.object.dynamical.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 09.03.2010
 * Time: 14:10:53
 * треугольник
 * !!!
 * внутрений радиус(равностороний)
 * внешний радиус(равностороний)
 * способ задания три стороны
 * две стороны и угол
 * сторона и два угла
 */
public class Triangle extends Telo
{
	public Triangle(Scen scen, int id, double r)
	{
		super(scen, id, "Triangle", 3, 3, 1);
		addTochka(0, 0, 0);	//0
		addTochka(r, 0, 0);	//1
		addTochka(0, r, 0);	//2
		addRebro( 0, 1);
		addRebro( 0, 2);
		addRebro( 1, 2);
		addGran( 0, 1, 2);
	}
}
