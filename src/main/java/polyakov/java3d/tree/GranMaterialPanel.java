package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.object.dynamical.Material;
import polyakov.java3d.object.dynamical.Gran;
import polyakov.java3d.object.dynamical.Scen;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 09.03.2010
 * Time: 13:28:36
 * панель материала грани
 * !!!
 *
 */
public class GranMaterialPanel extends OptionPanel implements ItemListener
{
	private JComboBox granM_name;
	private NumUpDown granM_au;
	private NumUpDown granM_av;
	private NumUpDown granM_bu;
	private NumUpDown granM_bv;
	private NumUpDown granM_cu;
	private NumUpDown granM_cv;

	private Gran gran;
	private Scen scen;

	public GranMaterialPanel(Gran gran)
	{
		//!!!setTitle("Материал грани");
		super(new GridLayout(8, 2));
		this.gran=gran;
		add(new JLabel("материал"));
		scen=gran.getRootTelo().getScen();
		String array[]=new String[scen.mMaterialLen];
		for (int i = 0; i < scen.mMaterialLen; i++)
					array[i]=scen.mMaterial[i].name;
		granM_name = new JComboBox(array);
		Material material=gran.material;
		if (material!=null)
		{
			granM_name.setSelectedItem(material.name);
		}
		granM_name.addItemListener(this);
		add(granM_name);
		add(new JLabel("Текстурные"));
		add(new JLabel("координаты"));
		add(new JLabel("au"));
		granM_au = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		add(granM_au);
		add(new JLabel("av"));
		granM_av = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		add(granM_av);
		add(new JLabel("bu"));
		granM_bu = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		add(granM_bu);
		add(new JLabel("bv"));
		granM_bv = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		add(granM_bv);
		add(new JLabel("cu"));
		granM_cu = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		add(granM_cu);
		add(new JLabel("cv"));
		granM_cv = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		add(granM_cv);
	}

	// события выбора
	public void itemStateChanged(ItemEvent e)
	{
		Object event = e.getSource();
		if (granM_name==event)
		{
			if (granM_name.getSelectedIndex()>0)
			{
				gran.material=scen.mMaterial[granM_name.getSelectedIndex()-1];
			}
		}
		sendScenEvent();
	}
}
