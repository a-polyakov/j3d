package polyakov.java3d.tree;

import polyakov.java3d.object.dynamical.Scen;
import polyakov.java3d.object.dynamical.Telo;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.03.2010
 * Time: 13:41:33
 * панель добавления точки
 * !!! начальные координаты 
 */
public class TochkaRootPanel extends OptionPanel implements ActionListener
{
	private JButton tochka_add_button;	// кнопка добавить

	private Telo telo;

	public TochkaRootPanel(Telo telo)
	{
		//setTitle("Добавить точку");
		this.telo=telo;
		tochka_add_button = new JButton("Добавить");
		tochka_add_button.addActionListener(this);
		add(tochka_add_button);
	}

	// событие нажатие на кнопку
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (tochka_add_button ==event)
		{
			// добавить точку
			//!!!
			//index.scen.mTel[id].addTochka();
			//treeScen.reBuild();
		}
	}

}