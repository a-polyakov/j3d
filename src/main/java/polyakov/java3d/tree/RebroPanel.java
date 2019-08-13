package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.field.ColorField;
import polyakov.java3d.object.dynamical.Rebro;
import polyakov.java3d.object.dynamical.Telo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.03.2010
 * Time: 13:47:36
 * панель свойства ребра
 *
 * !!!
 * изменение a, b, d, color
 * удаление
 */
public class RebroPanel extends OptionPanel implements ActionListener
{
	private JComboBox rebro_a;
	private JComboBox rebro_b;
	private NumUpDown rebro_d;
	private ColorField rebro_color;
	private JButton rebro_apply;
	private JButton rebro_del;

	private Rebro rebro;

	public RebroPanel(Rebro rebro)
	{
		//!!!setTitle("Ребро");
		super(new GridLayout(5, 2));
		this.rebro=rebro;
		Telo telo=rebro.getRootTelo();
		int count=telo.mp.length;
		Integer array[]=new Integer[count];
		for (int i=0; i<count; i++)
			array[i]=i;
		add(new JLabel("точка A"));
		rebro_a = new JComboBox(array);
		rebro_a.setSelectedItem(rebro.a);
		add(rebro_a);
		add(new JLabel("точка B"));
		rebro_b = new JComboBox(array);
		rebro_b.setSelectedItem(rebro.b);
		add(rebro_b);
		add(new JLabel("ширина"));
		rebro_d = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		rebro_d.setMin(0);
		rebro_d.setDouble(rebro.d);
		add(rebro_d);
		add(new JLabel("цвет"));
		rebro_color = new ColorField();
		rebro_color.setColor(rebro.color);
		add(rebro_color);
		rebro_apply=new JButton("Применить");
		rebro_apply.addActionListener(this);
		add(rebro_apply);
		rebro_del = new JButton("Удалить");
		rebro_del.addActionListener(this);
		add(rebro_del);
	}


	// событие нажатие на кнопку
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (rebro_apply==event)
		{
				//!!!rebro_a;
				//rebro_b;
				//rebro_d;
				//rebro_color;
		}
		else
		if (rebro_del == event)
		{	// удалить ребро
			rebro.remove();
		}
	}
}
