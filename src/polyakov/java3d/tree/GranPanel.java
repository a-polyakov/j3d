package polyakov.java3d.tree;

import polyakov.java3d.object.dynamical.Gran;
import polyakov.java3d.object.dynamical.Telo;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:49:39
 * панель свойства грани
 * !!!
 * изменение a, b, с
 * удаление
 * нельзя выбрать одинаковые точки
 */
public class GranPanel extends OptionPanel
{
	private JComboBox gran_a;
	private JComboBox gran_b;
	private JComboBox gran_c;
	private JButton gran_del;

	private Gran gran;

	public GranPanel(Gran gran)
	{
		//!!!setTitle("Точки грани");
		super(new GridLayout(4, 2));
		this.gran=gran;
		Telo telo=gran.getRootTelo();
		int count=telo.mp.length;
		Integer array[]=new Integer[count];
		for (int i=0; i<count; i++)
			array[i]=i;
		add(new JLabel("точка A"));
		gran_a = new JComboBox(array);
		gran_a.setSelectedItem(gran.a);
		add(gran_a);
		add(new JLabel("точка B"));
		gran_b = new JComboBox(array);
		gran_b.setSelectedItem(gran.b);
		add(gran_b);
		add(new JLabel("точка C"));
		gran_c = new JComboBox(array);
		gran_c.setSelectedItem(gran.c);
		add(gran_c);
		add(new JLabel("-"));
		gran_del = new JButton("Удалить");
		add(gran_del);
	}
}
