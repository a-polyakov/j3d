package polyakov.java3d.tree;

import polyakov.java3d.object.dynamical.Telo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:48:53
 * панель добавления ребра
 */
public class RebroRootPanel extends OptionPanel implements ActionListener
{
	private JComboBox rebro_add_a;
	private JComboBox rebro_add_b;
	private JButton rebro_add_button;

	private Telo telo;

	public RebroRootPanel(Telo telo)
	{
		//setTitle("Добавить ребро");
		super(new GridLayout(3, 2));
		this.telo=telo;
		int count=telo.mp.length;
		Integer array[]=new Integer[count];
		for (int i=0; i<count; i++)
			array[i]=i;
		add(new JLabel("точка A"));
		rebro_add_a = new JComboBox(array);
		add(rebro_add_a);
		add(new JLabel("точка B"));
		rebro_add_b = new JComboBox(array);
		add(rebro_add_b);
		add(new JLabel("-"));
		rebro_add_button = new JButton("Добавить");
		rebro_add_button.addActionListener(this);
		add(rebro_add_button);
	}
	// событие нажатие на кнопку
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (rebro_add_button == event)
		{	// добавить ребро
			int a, b;
			a = (Integer) rebro_add_a.getSelectedItem();
			b = (Integer) rebro_add_b.getSelectedItem();
			if (a != b)
			{	// различны точки
				telo.addRebro(a, b);
			}
		}
	}
}
