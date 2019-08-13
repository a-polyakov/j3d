package polyakov.java3d.tree;

import polyakov.java3d.object.dynamical.Lamp;

import javax.swing.*;


/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 09.03.2010
 * Time: 14:48:08
 * панель свойств источника света
 */
public class LightPanel extends OptionPanel
{
	private JTextField lamp_name;
	private JButton lamp_del;

	private Lamp lamp;

	public LightPanel(Lamp lamp)
	{
		//setTitle("Лампа");
		this.lamp=lamp;
		add(new JLabel("Имя"));
		lamp_name = new JTextField(lamp.name);
		add(lamp_name);
		lamp_del = new JButton("Удалить");
		add(lamp_del);
	}
}
