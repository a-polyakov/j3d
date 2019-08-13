package polyakov.java3d.object.statical;

import polyakov.java3d.object.dunamic.DunamicObject;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11.03.2010
 * Time: 10:53:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class ObjectStatical
{
	public int id;
	public String name;
	public ObjectStatical(int id, String name)
	{
		this.id=id;
		this.name=name;
	}
	public ObjectStatical(int id)
	{
		this(id,"Object");
	}
	// удалить обект
	public abstract void remove();
}
