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
 * панель добавления материала
 * !!!
 */
public class MaterialRootPanel extends OptionPanel implements ActionListener
{
	private JButton material_add_button;	// кнопка добавить

	private Scen scen;

	public MaterialRootPanel(Scen scen)
	{
		//setTitle("Добавить материал");
		this.scen=scen;
		material_add_button = new JButton("Добавить");
		material_add_button.addActionListener(this);
		add(material_add_button);
	}

	// событие нажатие на кнопку
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (material_add_button ==event)
		{
			// добавить материал
			//!!!
			//index.scen.addMaterial();
			//treeScen.reBuild();
		}
	}

}