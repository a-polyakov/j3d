package polyakov.java3d.field;

import polyakov.java3d.tree.OptionScen;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 09.06.2007
 * Time: 12:40:58
 * Поле для ввода цвета
 * ввод в формане WEB
 * пример:	000000	черный
 * FF0000	красный
 * 00FF00	зелёный
 * 0000FF	голубой
 * FFFFFF	белый
 */
public class ColorField extends JTextField implements DocumentListener
{
	private int color;
	private List<ColorListener> listeners;

	public ColorField(int color)
	{
		getDocument().addDocumentListener(this);
		setColor(color);
		listeners = new ArrayList();
	}

	public ColorField()
	{
		this(0xFFFFFFFF);
	}

	public void addColorListener(ColorListener colorListener)
	{
		listeners.add(colorListener);
	}

	public boolean setColor(String colorString)
	{
		if (colorString != null)
		{
			int temp = color;
			int aInt = 0xff, rInt = 0, gInt = 0, bInt = 0;
			int lenght = colorString.length();
			// выделить подстроки
			try
			{
				if (lenght > 8)
				{
					colorString = colorString.substring(0, 8);
					setText(colorString);
				}
				if (lenght == 8)
				{
					aInt = Integer.parseInt(colorString.substring(0, 2), 16);
					rInt = Integer.parseInt(colorString.substring(2, 4), 16);
					gInt = Integer.parseInt(colorString.substring(4, 6), 16);
					bInt = Integer.parseInt(colorString.substring(6, 8), 16);
				} else if (lenght == 7)
				{
					aInt = Integer.parseInt(colorString.substring(0, 1), 16);
					rInt = Integer.parseInt(colorString.substring(1, 3), 16);
					gInt = Integer.parseInt(colorString.substring(3, 5), 16);
					bInt = Integer.parseInt(colorString.substring(5, 7), 16);
				} else if (lenght == 6)
				{
					rInt = Integer.parseInt(colorString.substring(0, 2), 16);
					gInt = Integer.parseInt(colorString.substring(2, 4), 16);
					bInt = Integer.parseInt(colorString.substring(4, 6), 16);
				} else if (lenght == 5)
				{
					rInt = Integer.parseInt(colorString.substring(0, 1), 16);
					gInt = Integer.parseInt(colorString.substring(1, 3), 16);
					bInt = Integer.parseInt(colorString.substring(3, 5), 16);
				} else if (lenght == 4)
				{
					gInt = Integer.parseInt(colorString.substring(0, 2), 16);
					bInt = Integer.parseInt(colorString.substring(2, 4), 16);
				} else if (lenght == 3)
				{
					gInt = Integer.parseInt(colorString.substring(0, 1), 16);
					bInt = Integer.parseInt(colorString.substring(1, 3), 16);
				} else
					bInt = Integer.parseInt(colorString, 16);
				color = (aInt << 24) + (rInt << 16) + (gInt << 8) + bInt;
			}
			catch (NumberFormatException e)
			{
				setText(Integer.toHexString((color >= 0xff000000) ? color & 0xffffff : color).toUpperCase());
			}
			if (temp != color)
				return true;
		}
		return false;
	}

	public void setColor(int color)
	{
		this.color = color;
		setBackground(getColor());
		if (((color & 0xff0000 >> 16) + (color & 0xff00 >> 8) + (color & 0xff)) < 600)
			setForeground(Color.WHITE);
		else
			setForeground(Color.BLACK);
		int p=getCaretPosition();
		setText(Integer.toHexString((color >= 0xff000000) ? color & 0xffffff : color).toUpperCase());
		setCaretPosition(p);
	}

	public int getInt()
	{
		return color;
	}

	public Color getColor()
	{
		return new Color(color);
	}

	 /**
     * Gives notification that there was an insert into the document.  The
     * range given by the DocumentEvent bounds the freshly inserted region.
     *
     * @param e the document event
     */
    public void insertUpdate(DocumentEvent e)
	 {
		 textValueChanged();
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
		textValueChanged();
	}

	/**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e the document event
     */
    public void changedUpdate(DocumentEvent e)
	{

	}

	private void textValueChanged()
	{
		if (setColor(getText()))
		{	// цвет был изменен
			setBackground(getColor());
			if (((color & 0xff0000 >> 16) + (color & 0xff00 >> 8) + (color & 0xff)) < 600)
				setForeground(Color.WHITE);
			else
				setForeground(Color.BLACK);
			int p=getCaretPosition();
			setText(Integer.toHexString((color >= 0xff000000) ? color & 0xffffff : color).toUpperCase());
			setCaretPosition(p);
			for (ColorListener listener:listeners)
				listener.colorValueChanged(this);
		}
	}
}
