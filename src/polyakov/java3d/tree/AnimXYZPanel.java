package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.object.scen.AnimMov;
import polyakov.java3d.object.dunamic.AnimXYZ;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:52:33
 * панель анимации по трем координатам
 */
public class AnimXYZPanel extends OptionPanel
{
	private AnimXYZ animXYZ;

	public AnimXYZPanel(AnimXYZ animXYZ)
	{
		super(new GridLayout(3,1));
		this.animXYZ = animXYZ;
		//setTitle("Анимация по трем координатам");
		JPanel panel =new JPanel();
		panel.add(new JLabel("X"));
		panel.add(new AnimDoublePanel(animXYZ.x));
		add(panel);
		panel =new JPanel();
		panel.add(new JLabel("Y"));
		panel.add(new AnimDoublePanel(animXYZ.y));
		add(panel);
		panel =new JPanel();
		panel.add(new JLabel("Z"));
		panel.add(new AnimDoublePanel(animXYZ.z));
		add(panel);
	}
}