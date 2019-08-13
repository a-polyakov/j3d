package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.object.dynamical.Tochka;
import polyakov.java3d.object.dunamic.AnimText;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:52:33
 * панель анимации текста
 */
public class AnimTextPanel extends OptionPanel implements ChangeListener, DocumentListener
{
	private NumUpDown len;
	private NumUpDown key[];
	private JTextField value[];

	private AnimText text;

	public AnimTextPanel(AnimText text)
	{
		this.text = text;
		initAnimPanel();
	}

	// изменение поля
	public void stateChanged(ChangeEvent e)
	{
		Object event = e.getSource();
		if (len == event)
		{
			int i = len.getInt();
			if (text.getCountKey() != i)
			{
				// изменить количество ключей анимации
				text.setCountKey(i);
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
					text.moveKey(i, key[i].getInt(), text.getValue(i));
					sendScenEvent();
				}
		}
	}

	private void initAnimPanel()
	{
		int n = text.getCountKey();
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
			min = text.getKey(i);
			key[i].setInt(min);
			key[i].addChangeListener(this);
			p1.add(key[i]);
		}
		value = new JTextField[n];
		p1.add(new JLabel("значение"));
		for (int i = 0; i < n; i++)
		{
			value[i] = new JTextField(text.getValue(i));
			value[i].getDocument().addDocumentListener(this);
			p1.add(value[i]);
		}
		add(new JScrollPane(p1));
	}


	// изменение поля
	/**
	 * Gives notification that there was an insert into the document.  The
	 * range given by the DocumentEvent bounds the freshly inserted region.
	 *
	 * @param e the document event
	 */
	public void insertUpdate(DocumentEvent e)
	{
		Object event = e.getDocument().getDefaultRootElement();
		textValueChanged(event);
	}

	/**
	 * Gives notification that a portion of the document has been
	 * removed.  The range is given in terms of what the view last
	 * saw (that is, before updating sticky positions).
	 *
	 * @param e the document event
	 */
	public void removeUpdate(DocumentEvent e)
	{
		Object event = e.getDocument().getDefaultRootElement();
		textValueChanged(event);
	}

	/**
	 * Gives notification that an attribute or set of attributes changed.
	 *
	 * @param e the document event
	 */
	public void changedUpdate(DocumentEvent e)
	{
	}


	private void textValueChanged(Object event)
	{
		for (int i = 0; i < value.length; i++)
			if (value[i] == event)
			{
				text.moveKey(i, text.getKey(i), value[i].getText());
				sendScenEvent();
			}
	}
}