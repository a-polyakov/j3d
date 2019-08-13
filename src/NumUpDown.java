import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 13.12.2006
 * Time: 18:48:33
 * To change this template use File | Settings | File Templates.
 */

// переписать, шаг, клавиатура
public class NumUpDown extends TextField implements MouseListener, MouseMotionListener, TextListener
{
	public static final int TYPE_INT = 1;
	public static final int TYPE_FLOAT = 2;
	public static final int TYPE_DOUBLE = 3;
	private int type;
	private int vInt;
	private int minInt;
	private int maxInt;
	private float vFloat;
	private float minFloat;
	private float maxFloat;
	private double vDouble;
	private double minDouble;
	private double maxDouble;

	private ChangeListener changeListener;
	private int y;

	NumUpDown(int type)
	{
		super();
		this.type=type;
		setPreferredSize(new Dimension(45, 22));
		addMouseListener(this);
		addMouseMotionListener(this);
		addTextListener(this);
		setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));

		minInt=Integer.MIN_VALUE;
		maxInt=Integer.MAX_VALUE;
		minFloat=-Float.MAX_VALUE;
		maxFloat=Float.MAX_VALUE;
		minDouble=-Double.MAX_VALUE;
		maxDouble=Double.MAX_VALUE;
	}

	public void addChangeListener(ChangeListener changeListener)
	{
		if (changeListener!=null)
			this.changeListener=changeListener;
	}

	public void setInt(int value)
	{
		if (type==TYPE_INT)
		{
			vInt=value;
			if (vInt>maxInt)
				vInt=maxInt;
			else
			if (vInt<minInt)
				vInt=minInt;
			setText(String.valueOf(vInt));
		}
		else
		if (type==TYPE_FLOAT)
		{
			vFloat=value;
			if (vFloat>maxFloat)
				vFloat=maxFloat;
			else
			if (vFloat<minFloat)
				vFloat=minFloat;
			setText(String.valueOf(vFloat));
		}
		else
		if (type==TYPE_DOUBLE)
		{
			vDouble=value;
			if (vDouble>maxDouble)
				vDouble=maxDouble;
			else
			if (vDouble<minDouble)
				vDouble=minDouble;
			setText(String.valueOf(vDouble));
		}
	}
	public void setFloat(float value)
	{
		if (type==TYPE_INT)
		{
			vInt=(int)value;
			if (vInt>maxInt)
				vInt=maxInt;
			else
			if (vInt<minInt)
				vInt=minInt;
			setText(String.valueOf(vInt));
		}
		else
		if (type==TYPE_FLOAT)
		{
			vFloat=value;
			if (vFloat>maxFloat)
				vFloat=maxFloat;
			else
			if (vFloat<minFloat)
				vFloat=minFloat;
			setText(String.valueOf(vFloat));
		}
		else
		if (type==TYPE_DOUBLE)
		{
			vDouble=value;
			if (vDouble>maxDouble)
				vDouble=maxDouble;
			else
			if (vDouble<minDouble)
				vDouble=minDouble;
			setText(String.valueOf(vDouble));
		}
	}
	public void setDouble(double value)
	{
		if (type==TYPE_INT)
		{
			vInt=(int)value;
			if (vInt>maxInt)
				vInt=maxInt;
			else
			if (vInt<minInt)
				vInt=minInt;
			setText(String.valueOf(vInt));
		}
		else
		if (type==TYPE_FLOAT)
		{
			vFloat=(float)value;
			if (vFloat>maxFloat)
				vFloat=maxFloat;
			else
			if (vFloat<minFloat)
				vFloat=minFloat;
			setText(String.valueOf(vFloat));
		}
		else
		if (type==TYPE_DOUBLE)
		{
			vDouble=value;
			if (vDouble>maxDouble)
				vDouble=maxDouble;
			else
			if (vDouble<minDouble)
				vDouble=minDouble;
			setText(String.valueOf(vDouble));
		}
	}
	public void setMin(int value)
	{
		if (type==TYPE_INT && value<maxInt)
			minInt=value;
		else
		if (type==TYPE_FLOAT && value<maxFloat)
			minFloat=value;
		else
		if (type==TYPE_DOUBLE && value<maxDouble)
			minDouble=value;
	}
	public void setMin(float value)
	{
		if (type==TYPE_INT && value<maxInt)
			minInt=(int)value;
		else
		if (type==TYPE_FLOAT && value<maxFloat)
			minFloat=value;
		else
		if (type==TYPE_DOUBLE && value<maxDouble)
			minDouble=value;
	}
	public void setMin(double value)
	{
		if (type==TYPE_INT && value<maxInt)
			minInt=(int)value;
		else
		if (type==TYPE_FLOAT && value<maxFloat)
			minFloat=(float)value;
		else
		if (type==TYPE_DOUBLE && value<maxDouble)
			minDouble=value;
	}
	public void setMax(int value)
	{
		if (type==TYPE_INT && value>minInt)
			maxInt=value;
		else
		if (type==TYPE_FLOAT && value>minFloat)
			maxFloat=value;
		else
		if (type==TYPE_DOUBLE && value>minDouble)
			maxDouble=value;
	}
	public void setMax(float value)
	{
		if (type==TYPE_INT  && value>minInt)
			maxInt=(int)value;
		else
		if (type==TYPE_FLOAT  && value>minFloat)
			maxFloat=value;
		else
		if (type==TYPE_DOUBLE  && value>minDouble)
			maxDouble=value;
	}
	public void setMax(double value)
	{
		if (type==TYPE_INT  && value>minInt)
			maxInt=(int)value;
		else
		if (type==TYPE_FLOAT && value>minFloat)
			maxFloat=(float)value;
		else
		if (type==TYPE_DOUBLE && value>minDouble)
			maxDouble=value;
	}
	public int getInt()
	{
		if (type==TYPE_INT)
			return vInt;
		else
		if (type==TYPE_FLOAT)
			return (int)vFloat;
		else
		if (type==TYPE_DOUBLE)
			return (int)vDouble;
		return 0;
	}
	public float getFloat()
	{
		if (type==TYPE_INT)
			return vInt;
		else
		if (type==TYPE_FLOAT)
			return vFloat;
		else
		if (type==TYPE_DOUBLE)
			return (float)vDouble;
		return 0;
	}
	public double getDouble()
	{
		if (type==TYPE_INT)
			return vInt;
		else
		if (type==TYPE_FLOAT)
			return vFloat;
		else
		if (type==TYPE_DOUBLE)
			return vDouble;
		return 0;
	}

	public void mouseClicked(MouseEvent e)
	{

	}

	public void mousePressed(MouseEvent e)
	{
		y = e.getY();
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mouseDragged(MouseEvent e)
	{
		if (type==TYPE_INT)
			setInt(vInt+y-e.getY());
		else
		if (type==TYPE_FLOAT)
			setFloat(vFloat+y-e.getY());
		else
		if (type==TYPE_DOUBLE)
			setDouble(vDouble+y-e.getY());
		y=e.getY();
	}

	public void mouseMoved(MouseEvent e)
	{
	}
	// событие изменения текста
	public void textValueChanged(TextEvent e)
	{
		String s=getText().trim();
		try
		{
			if (type==TYPE_INT)
				vInt=Integer.parseInt(s);
		else
		if (type==TYPE_FLOAT)
			vFloat=Float.parseFloat(s);
		else
		if (type==TYPE_DOUBLE)
			vDouble=Double.parseDouble(s);
			// вызов события изменения поля
			changeListener.stateChanged(new ChangeEvent(this));
		}
		catch(Exception ex)
		{}
	}
}
