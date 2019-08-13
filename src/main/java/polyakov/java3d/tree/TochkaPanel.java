package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.field.ColorField;
import polyakov.java3d.object.dynamical.Tochka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.03.2010
 * Time: 13:46:42
 * панель свойств точки
 */
public class TochkaPanel extends OptionPanel implements ActionListener
{
	private NumUpDown tochka_d;
	private ColorField tochka_color;
	private ColorField tochka_colorText;
	private JButton tochka_apply;
	private JButton tochka_del;

	private Tochka tochka;

	public TochkaPanel(Tochka tochka)
	{
		//setTitle("Опции точки");
		super(new GridLayout(5, 2));
		this.tochka = tochka;
		add(new JLabel("диаметр"));
		tochka_d = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		tochka_d.setMin(0);
		tochka_d.setDouble(tochka.d);
		add(tochka_d);
		add(new JLabel("цвет"));
		add(new JLabel("-"));
		add(new JLabel("точки"));
		tochka_color = new ColorField(tochka.color);
		add(tochka_color);
		add(new JLabel("текста"));
		tochka_colorText = new ColorField(tochka.colorText);
		add(tochka_colorText);
		tochka_apply=new JButton("Применить");
		tochka_apply.addActionListener(this);
		add(tochka_apply);
		tochka_del = new JButton("Удалить");
		tochka_del.addActionListener(this);
		add(tochka_del);
	}

	// событие нажатие на кнопку
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (tochka_apply==event)
		{
			//!!! d
			tochka.color=tochka_color.getInt();
			tochka.colorText=tochka_colorText.getInt();
		}
		else
		if (tochka_del == event)
		{	// удалить точку
			tochka.remove();
		}
	}
}
