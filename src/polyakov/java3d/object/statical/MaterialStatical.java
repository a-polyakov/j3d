package polyakov.java3d.object.statical;

import polyakov.java3d.object.dynamical.Scen;
import polyakov.java3d.object.dynamical.Material;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 21.04.2007
 * Time: 12:53:18
 * состояние материала в определенный момент времени
 */
public abstract class MaterialStatical extends ObjectStatical
{
	public String fileTexture;	// текстура
	public int color;			// цвет

	private Scen scen;			// сцена к которой пренадлежит материал

	public MaterialStatical(Scen scen, int id, String name, String fileTexture, int color)
	{
		super(id,name);
		this.scen = scen;
		this.fileTexture=fileTexture;
		this.color = color;
	}

	public Scen getScen()
	{
		return scen;
	}

	// удалить обект
	public void remove()
	{
		Material movObject=scen.mMaterial[scen.mMaterialLen - 1];
		movObject.id=id;
		scen.mMaterial[id]=movObject;
		scen.mMaterialLen--;
	}
}