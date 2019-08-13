package polyakov.java3d.tree;

import polyakov.java3d.object.dynamical.Telo;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.03.2010
 * Time: 13:50:45
 * панель добавления грани
 * !!!
 * нельзя выбрать одинаковые точки
 * добавление
 */
public class GranRootPanel extends OptionPanel
{
	private JComboBox gran_add_a;
	private JComboBox gran_add_b;
	private JComboBox gran_add_c;
	private JButton gran_add_button;

	private Telo telo;

	public GranRootPanel(Telo telo)
	{
		//setTitle("Добавить грань");
		super(new GridLayout(4, 2));
		this.telo=telo;
		int count=telo.mp.length;
		Integer array[]=new Integer[count];
		for (int i=0; i<count; i++)
			array[i]=i;
		add(new JLabel("точка A"));
		gran_add_a = new JComboBox(array);
		add(gran_add_a);
		add(new JLabel("точка B"));
		gran_add_b = new JComboBox(array);
		add(gran_add_b);
		add(new JLabel("точка C"));
		gran_add_c = new JComboBox(array);
		add(gran_add_c);
		add(new JLabel("-"));
		gran_add_button = new JButton("Добавить");
		add(gran_add_button);
	}
}
