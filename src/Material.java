import java.awt.*;
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
public class Material
{
	public String name;
	public String fileTexture;
	public int color;

	public Material()
	{
		name="material";
		fileTexture = null;
		color = 0xFFFeFeFe;
	}
	public Material(int color)
	{
		String s="M-";
		for (int i=20; i>=0; i-=4)
		{
			s+=Integer.toHexString(color>>i&0xf).toUpperCase();
		}
		name = s;
		fileTexture = null;
		this.color = color;
	}
	public Material(String name)
	{
		this.name = name;
		fileTexture = null;
		this.color = 0xFFFeFeFe;
	}
	public Material(DataInputStream in) throws IOException
	{
		name = in.readUTF();
		color = in.readInt();
	}
	
	public void save(DataOutputStream out) throws IOException
	{
		out.writeUTF(name);
		out.writeInt(color);
	}
}
