package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.object.dunamic.AnimDouble;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:52:33
 * панель анимации движения
 */
public class AnimDoublePanel extends OptionPanel implements ChangeListener
{
	private NumUpDown len;
	private NumUpDown key[];
	private NumUpDown value[];

	private AnimDouble d;

	public AnimDoublePanel(AnimDouble d)
	{
		this.d = d;
		initAnimPanel();
	}

	// изменение поля
	public void stateChanged(ChangeEvent e)
	{
		Object event = e.getSource();
		if (len == event)
		{
			int i = len.getInt();
			if (d.getCountKey() != i)
			{
				// изменить количество ключей анимации
				d.setCountKey(i);
				// перерисовать окно
				removeAll();
				initAnimPanel();
				sendScenEvent();
			}
		} else
		{
			for (int i = 0; i < key.length; i++)
				if (key[i] == event)
				{
					d.moveKey(i, key[i].getInt(), d.getValue(i));
					sendScenEvent();
				}
			for (int i = 0; i < value.length; i++)
				if (value[i] == event)
				{
					d.moveKey(i, d.getKey(i), value[i].getDouble());
					sendScenEvent();
				}
		}
	}

	private void initAnimPanel()
	{
		int n = d.getCountKey();
		JPanel p1 = new JPanel(new GridLayout(3, n + 1));
		len = new NumUpDown(NumUpDown.TYPE_INT);
		len.setMin(1);
		len.setInt(n);
		len.addChangeListener(this);
		p1.add(len);
		for (int i = 0; i < n; i++)
			p1.add(new JLabel("N" + (i + 1)));
		p1.add(new JLabel("кадр"));
		key = new NumUpDown[n];
		int min = 0;
		for (int i = 0; i < n; i++)
		{
			key[i] = new NumUpDown(NumUpDown.TYPE_INT);
			key[i].setMin(min);
			min = d.getKey(i);
			key[i].setInt(min);
			key[i].addChangeListener(this);
			p1.add(key[i]);
		}
		value = new NumUpDown[n];
		p1.add(new JLabel("значение"));
		for (int i = 0; i < n; i++)
		{
			value[i] = new NumUpDown(NumUpDown.TYPE_DOUBLE);
			value[i].setDouble(d.getValue(i));
			value[i].addChangeListener(this);
			p1.add(value[i]);
		}
		add(new JScrollPane(p1));
	}
}