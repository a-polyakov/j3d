package polyakov.java3d.tree;

import polyakov.java3d.Index;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:43:12
 * панель размер видовых окон
 */
public class KameraRootPanel extends OptionPanel implements ChangeListener
{
	private JSlider sizeOkn1;	// полоса прокрутки
	private JSlider sizeOkn2;
	private JSlider sizeOkn3;

	private Index index;

	public KameraRootPanel(Index index)
	{
		super(new GridLayout(6, 1));
		this.index=index;
		add(new JLabel("по горизонтали"));
		sizeOkn1 = new JSlider(0, index.center.getSize().width, index.center.getDividerLocation());
		sizeOkn1.addChangeListener(this);
		add(sizeOkn1);
		add(new JLabel("по вертикали левый"));
		sizeOkn2 = new JSlider(0, index.sp1.getSize().height, index.sp1.getDividerLocation());
		sizeOkn2.addChangeListener(this);
		add(sizeOkn2);
		add(new JLabel("по вертикали правыйй"));
		sizeOkn3 = new JSlider(0, index.sp2.getSize().height, index.sp2.getDividerLocation());
		sizeOkn3.addChangeListener(this);
		add(sizeOkn3);
	}

	// изменение поля
	public void stateChanged(ChangeEvent e)
	{
		Object event = e.getSource();
		if (event == sizeOkn1)
		{
			index.center.setDividerLocation(sizeOkn1.getValue());
		} else if (event == sizeOkn2)
		{
			index.sp1.setDividerLocation(sizeOkn2.getValue());
		} else if (event == sizeOkn3)
		{
			index.sp2.setDividerLocation(sizeOkn3.getValue());
		}
		index.repaintCenter();
	}
}
