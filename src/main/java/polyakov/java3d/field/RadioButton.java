package polyakov.java3d.field;

import polyakov.java3d.ButtonListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.01.2007
 * Time: 1:13:49
 * To change this template use File | Settings | File Templates.
 */
public class RadioButton extends JRadioButton
{
	static LineBorder lb = new LineBorder(Color.black);

	public RadioButton(Icon n_icon, Icon y_icon, String tt, ButtonGroup bg, ButtonListener bl, JPanel p)
	{
		super(n_icon);
		setSelectedIcon(y_icon);
		setToolTipText(tt);
		setBorder(lb);
		setBorderPainted(true);
		addActionListener(bl);
		bg.add(this);
		p.add(this);
	}
}
