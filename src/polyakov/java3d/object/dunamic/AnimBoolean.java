package polyakov.java3d.object.dunamic;

import polyakov.java3d.file.FileJava3D;
import polyakov.java3d.object.ScenAnim;

import java.util.Arrays;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 15:55:01
 * флаг с анимацией
 */
public class AnimBoolean implements ScenAnim, FileJava3D
{
	private int length;			// количество клучевых значений
	private int key[];			// ключи анимации
	private boolean values[];	// значение флаг

	// statical
	public boolean value;		// текущее значение

	public AnimBoolean(int n)
	{
		length = n;
		key = new int[n];
		values = new boolean[n];
	}

	public AnimBoolean(boolean value)
	{
		length = 1;
		key = new int[]{0};
		values = new boolean[]{value};
	}

	// количество ключей
	public int getCountKey()
	{
		return length;
	}

	// изменить количество ключей анимации
	public void setCountKey(int n)
	{
		if (n > 0 && length != n)
		{
			length = n;
			if (n > key.length)
			{
				key = Arrays.copyOf(key, n);
				values = Arrays.copyOf(values, n);
			}
		}
	}

	public int getKey(int index)
	{
		return key[index];
	}

	public boolean getValue(int index)
	{
		return values[index];
	}

	public void setKey(int key, boolean value)
	{
		int i;
		for (i = 0; i < length && this.key[i] < key; i++) ;
		if (i < length)
		{
			if (this.key[i] == key)
			{   // новое значение уже имеющегося ключя
				values[i] = value;
			} else
			{	// вставить клучевое значение перед i
				setCountKey(length + 1);
				for (int j = length - 1; j > i; j--)
				{
					this.key[j] = this.key[j - 1];
					values[j] = values[j - 1];
				}
				this.key[i] = key;
				values[i] = value;
			}
		} else
		{   // добавить клучевое значение в конец
			i = length;
			setCountKey(length + 1);
			this.key[i] = key;
			values[i] = value;
		}
	}

	public void moveKey(int index, int key, boolean value)
	{
		this.key[index]=key;
		this.values[index]=value;
	}

	// определить состояние для указанного кадра
	public void setKadr(double kadr)
	{
		if (length == 1)
		{	// начальное состояние, отсутствие анимации
			value = values[0];
		} else
		{	// промежуточное состояние
			boolean flag = true;
			for (int i = 1; i < length && flag; i++)
				if (kadr < key[i])
				{	// между двумя ключами
					value = values[i - 1];
					flag = false;
				} else if (kadr == key[i])
				{	// ключевой кадр
					value = values[i];
					flag = false;
				}
			if (flag)
			{	// за последним ключем
				value = values[length - 1];
			}
		}
	}

	public void load(DataInputStream in) throws IOException
	{
		length = in.readInt();
		key = new int[length];
		values = new boolean[length];
		for (int i = 0; i < length; i++)
		{
			key[i] = in.readInt();
			values[i] = in.readBoolean();
		}
	}

	public void save(DataOutputStream out) throws IOException
	{
		out.writeInt(length);
		for (int i = 0; i < length; i++)
		{
			out.writeInt(key[i]);
			out.writeBoolean(values[i]);
		}
	}
}