package polyakov.java3d.object.scen.primitives;

import polyakov.java3d.object.dynamical.Tochka;
import polyakov.java3d.object.dynamical.Rebro;
import polyakov.java3d.object.dynamical.Gran;
import polyakov.java3d.object.dynamical.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 09.03.2010
 * Time: 14:15:50
 * TODO.
 */
public class Piramid extends Telo
{
	public Piramid(Scen scen, int id, int n, double r, double h)
	{
		super(scen, id, "Piramid", n + 1, n + n, n + n - 2);
		addTochka(0,0,h);//0	вершина
		float t = 2 * (float) Math.PI / n;
		// 1..n точки основания
		for (int i = 1; i <= n; i++)
			addTochka(r * (float) Math.sin(i * t), r * (float) Math.cos(i * t), 0);
		// боковый ребра
		for (int i = 0; i < n; i++)
			addRebro(0, i + 1);
		// ребра основания
		for (int i = 0; i < n - 1; i++)
			addRebro(i + 1, i + 2);
		// замыкающее ребро основания
		addRebro(1, n);
		// боковые грани
		for (int i = 0; i < n - 1; i++)
			addGran(0, 1 + i, 2 + i);
		// замыкающее боковая грань
		addGran(0, 1, n);
		// грани основания
		for (int i = n, j = 2; i < n + n - 2; i++, j++)
			addGran( 1, j, j + 1);
	}
}
