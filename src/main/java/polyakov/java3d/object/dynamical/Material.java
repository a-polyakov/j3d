package polyakov.java3d.object.dynamical;

import polyakov.java3d.file.FileJava3D;
import polyakov.java3d.object.statical.MaterialStatical;
import polyakov.java3d.object.statical.ObjectStatical;
import polyakov.java3d.object.statical.ScenStatical;
import polyakov.java3d.object.dunamic.DunamicObject;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 21.04.2007
 * Time: 12:53:18
 * материал
 * ├текстура
 * └цвет
 */
// коэфициенты преломления отражения сглаживание
public class Material extends MaterialStatical implements FileJava3D, DunamicObject
{
	public Material(Scen scen, int id, String name, String fileTexture)
	{
		super(scen, id, name, fileTexture, 0xFFFeFeFe);
	}

	public Material(Scen scen, int id, String name, int color)
	{
		super(scen, id, name, null, color);
	}
	public Material(Scen scen,int id, int color)
	{
		this(scen,id,null,color);
		String s="M-";
		for (int i=20; i>=0; i-=4)
		{
			s+=Integer.toHexString(color>>i&0xf).toUpperCase();
		}
		name = s;
	}

	public Material(Scen scen,int id)
	{
		this(scen,id, 0xFFFeFeFe);
	}

	public Material(Scen scen,int id, String name)
	{
		this(scen,id,name,0xFFFeFeFe);
	}

	public void load(DataInputStream in) throws IOException
	{
		name = in.readUTF();
		fileTexture = in.readUTF();
		color = in.readInt();
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(name);
		out.writeUTF(fileTexture);
		out.writeInt(color);
	}

	// !!!переместить обект в указаный момент времени
	public void setKadr(double kadr)
	{
		// пересщитать параметры
		// установить
		//materialScrin.set(this);
	}
}
