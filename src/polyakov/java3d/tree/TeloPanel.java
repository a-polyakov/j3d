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
 * Time: 13:45:28
 * панель свойства тела
 */
public class TeloPanel extends OptionPanel implements ActionListener
{
	private JTextField telo_name;
	private JButton telo_save;
	private JButton telo_del;

	private Telo telo;

	public TeloPanel(Telo telo)
	{
		//setTitle("Тело");
		super(new GridLayout(2, 2));
		this.telo=telo;
		add(new Label("Имя"));
		telo_name = new JTextField(telo.name);
		add(telo_name);
		telo_save = new JButton("Сохранить");
		telo_save.addActionListener(this);
		add(telo_save);
		telo_del = new JButton("Удалить тело");
		telo_del.addActionListener(this);
		add(telo_del);
	}

	// событие нажатие на кнопку
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
	if (telo_save == event)
		{	// сохранить новое имя
			telo.name = telo_name.getText();
			sendScenEvent();
		} else if (telo_del == event)
		{	// удалить выделеное тело
			telo.remove();
			//!!! перерисавать дерево
			//!!! перерисовать сцену
		}
	}


}
