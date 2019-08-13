package polyakov.java3d.object.scen;

import polyakov.java3d.object.dunamic.AnimXYZ;
import polyakov.java3d.object.statical.ObjectStatical;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 15:55:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class AnimMov extends ObjectStatical
{
	public AnimXYZ mov;		// Анимация перемещения

	public AnimMov(int id, String name)
	{
		super(id,name);
	}
	public AnimMov(int id)
	{
		super(id);
	}
}
