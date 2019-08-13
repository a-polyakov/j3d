package polyakov.java3d.tree;

import polyakov.java3d.object.dynamical.Scen;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:41:33
 * панель добавления освещения
 * !!!
 */
public class LightRootPanel extends OptionPanel implements ActionListener
{
	private JButton light_add_button;	// кнопка добавить

	private Scen scen;

	public LightRootPanel(Scen scen)
	{
		//setTitle("Добавить свет");
		this.scen=scen;
		light_add_button = new JButton("Добавить");
		light_add_button.addActionListener(this);
		add(light_add_button);
	}

	// событие нажатие на кнопку
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (light_add_button ==event)
		{
			// добавить источник света
			//!!!
		}
	}

}