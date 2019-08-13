package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.object.dynamical.Telo;
import polyakov.java3d.object.dunamic.AnimBoolean;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:52:33
 * панель анимации бинарного флага
 */
public class AnimBooleanPanel extends OptionPanel implements ChangeListener, ItemListener
{
	private NumUpDown len;
	private NumUpDown key[];
	private JCheckBox value[];

	private AnimBoolean b;

	public AnimBooleanPanel(AnimBoolean b)
	{
		this.b = b;
		initAnimPanel();
	}

	// изменение поля
	public void stateChanged(ChangeEvent e)
	{
		Object event = e.getSource();
		if (len == event)
		{
			int i = len.getInt();
			if (b.getCountKey() != i)
			{
				// изменить количество ключей анимации
				b.setCountKey(i);
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
					b.moveKey(i, key[i].getInt(), b.getValue(i));
					sendScenEvent();
				}
		}
	}

	/**
	 * Invoked when an item has been selected or deselected by the user.
	 * The code written for this method performs the operations
	 * that need to occur when an item is selected (or deselected).
	 */
	public void itemStateChanged(ItemEvent e)
	{
		Object event = e.getSource();
		for (int i = 0; i < value.length; i++)
			if (value[i] == event)
			{
				b.moveKey(i, b.getKey(i), value[i].isSelected());
				sendScenEvent();
			}
	}


	private void initAnimPanel()
	{
		int n = b.getCountKey();
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
			min = b.getKey(i);
			key[i].setInt(min);
			key[i].addChangeListener(this);
			p1.add(key[i]);
		}
		value = new JCheckBox[n];
		p1.add(new JLabel("значение"));
		for (int i = 0; i < n; i++)
		{
			value[i] = new JCheckBox();
			value[i].setSelected(b.getValue(i));
			value[i].addItemListener(this);
			p1.add(value[i]);
		}
		add(new JScrollPane(p1));
	}

}