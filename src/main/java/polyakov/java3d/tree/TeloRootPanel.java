package polyakov.java3d.tree;

import polyakov.java3d.object.dynamical.Scen;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.03.2010
 * Time: 13:41:33
 * панель добавления элемента
 * !!!
 * выбрать из списка приметивов
 * задать параметры приметива 
 */
public class TeloRootPanel extends OptionPanel implements ActionListener
{
	private JButton telo_add_button;	// кнопка добавить

	private Scen scen;

	public TeloRootPanel(Scen scen)
	{
		//setTitle("Добавить тело");
		this.scen=scen;
		telo_add_button = new JButton("Добавить");
		telo_add_button.addActionListener(this);
		add(telo_add_button);
	}

	// событие нажатие на кнопку
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (telo_add_button==event)
		{
			// добавить тело
			//!!!index.scen.addTelo();
			// перерисовать дерево
			//!!!
		}
	}

}
