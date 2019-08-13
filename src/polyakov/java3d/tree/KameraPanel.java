package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.object.dynamical.Kamera;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:44:20
 * панель свойств камеры
 */
public class KameraPanel extends OptionPanel implements ChangeListener, ItemListener
{
	private NumUpDown kamera_x;
	private NumUpDown kamera_y;
	private NumUpDown kamera_z;
	private NumUpDown kamera_nx;
	private NumUpDown kamera_ny;
	private NumUpDown kamera_nz;
	private NumUpDown kamera_a;
	private NumUpDown kamera_me;
	private JCheckBox kamera_perspektiva;
	private JCheckBox kamera_vsetki;
	private JCheckBox kamera_vsk;
	private JCheckBox kamera_vkam;
	private JCheckBox kamera_vlamp;
	private JCheckBox kamera_vgran;
	private JCheckBox kamera_osveshenie;
	private JCheckBox kamera_vreber;
	private JCheckBox kamera_vtochek;
	private JCheckBox kamera_vtext;

	private Kamera kamera;

	public KameraPanel(Kamera kamera)
	{
		//setTitle("Свойства камеры");
		super(new GridLayout(8, 1));
		this.kamera = kamera;
		JPanel p1 = new JPanel();
		p1.add(new JLabel("Камера"));
		p1.add(new JLabel("X"));
		kamera_x = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_x.setDouble(kamera.x);
		kamera_x.addChangeListener(this);
		p1.add(kamera_x);
		p1.add(new JLabel("Y"));
		kamera_y = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_y.setDouble(kamera.y);
		kamera_y.addChangeListener(this);
		p1.add(kamera_y);
		p1.add(new JLabel("Z"));
		kamera_z = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_z.setDouble(kamera.z);
		kamera_z.addChangeListener(this);
		p1.add(kamera_z);
		add(p1);
		p1 = new JPanel();
		p1.add(new JLabel("Фокус"));
		p1.add(new JLabel("X"));
		kamera_nx = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_nx.setDouble(kamera.nx);
		kamera_nx.addChangeListener(this);
		p1.add(kamera_nx);
		p1.add(new JLabel("Y"));
		kamera_ny = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_ny.setDouble(kamera.ny);
		kamera_ny.addChangeListener(this);
		p1.add(kamera_ny);
		p1.add(new JLabel("Z"));
		kamera_nz = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_nz.setDouble(kamera.nz);
		kamera_nz.addChangeListener(this);
		p1.add(kamera_nz);
		add(p1);
		p1 = new JPanel();
		p1.add(new JLabel("Экран"));
		p1.add(new JLabel("поворот"));
		kamera_a = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_a.setDouble(kamera.angle);
		kamera_a.addChangeListener(this);
		p1.add(kamera_a);
		p1.add(new JLabel("размер"));
		kamera_me = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_me.setMin(0.00001);
		kamera_me.setDouble(kamera.me);
		kamera_me.addChangeListener(this);
		p1.add(kamera_me);
		add(p1);
		p1 = new JPanel();
		kamera_perspektiva = new JCheckBox("перспектива", kamera.perspektiva);
		kamera_perspektiva.addItemListener(this);
		p1.add(kamera_perspektiva);
		kamera_vlamp = new JCheckBox("лампы", kamera.vlamp);
		kamera_vlamp.addItemListener(this);
		p1.add(kamera_vlamp);
		add(p1);
		p1 = new JPanel();
		kamera_vsetki = new JCheckBox("сетка", kamera.vsetki);
		kamera_vsetki.addItemListener(this);
		p1.add(kamera_vsetki);
		kamera_vgran = new JCheckBox("грани", kamera.vgran);
		kamera_vgran.addItemListener(this);
		p1.add(kamera_vgran);
		add(p1);
		p1 = new JPanel();
		kamera_vsk = new JCheckBox("СК", kamera.vsk);
		kamera_vsk.addItemListener(this);
		p1.add(kamera_vsk);
		kamera_osveshenie = new JCheckBox("освещение", kamera.osveshenie);
		kamera_osveshenie.addItemListener(this);
		p1.add(kamera_osveshenie);
		add(p1);
		p1 = new JPanel();
		kamera_vkam = new JCheckBox("камеры", kamera.vkam);
		kamera_vkam.addItemListener(this);
		p1.add(kamera_vkam);
		kamera_vreber = new JCheckBox("ребра", kamera.vreber);
		kamera_vreber.addItemListener(this);
		p1.add(kamera_vreber);
		add(p1);
		p1 = new JPanel();
		kamera_vtochek = new JCheckBox("точки", kamera.vtochek);
		kamera_vtochek.addItemListener(this);
		p1.add(kamera_vtochek);
		kamera_vtext = new JCheckBox("надписи", kamera.vtext);
		kamera_vtext.addItemListener(this);
		p1.add(kamera_vtext);
		add(p1);
	}

	// изменение поля
	public void stateChanged(ChangeEvent e)
	{
		Object event = e.getSource();
		if (event == kamera_x)
		{
			kamera.x = kamera_x.getDouble();
		} else if (event == kamera_y)
		{
			kamera.y = kamera_y.getDouble();
		} else if (event == kamera_z)
		{
			kamera.z = kamera_z.getDouble();
		} else if (event == kamera_nx)
		{
			kamera.nx = kamera_nx.getDouble();
		} else if (event == kamera_ny)
		{
			kamera.ny = kamera_ny.getDouble();
		} else if (event == kamera_nz)
		{
			kamera.nz = kamera_nz.getDouble();
		} else if (event == kamera_a)
		{
			kamera.angle = kamera_a.getDouble();
		} else if (event == kamera_me)
		{
			kamera.me = kamera_me.getDouble();
		}
	}

	// события выбора
	public void itemStateChanged(ItemEvent e)
	{
		Object event = e.getSource();
		if (event == kamera_perspektiva)
		{
			kamera.perspektiva = kamera_perspektiva.isSelected();
		} else if (event == kamera_vsetki)
		{
			kamera.vsetki = kamera_vsetki.isSelected();
		} else if (event == kamera_vsk)
		{
			kamera.vsk = kamera_vsk.isSelected();
		} else if (event == kamera_vkam)
		{
			kamera.vkam = kamera_vkam.isSelected();
		} else if (event == kamera_vlamp)
		{
			kamera.vlamp = kamera_vlamp.isSelected();
		} else if (event == kamera_vgran)
		{
			kamera.vgran = kamera_vgran.isSelected();
		} else if (event == kamera_osveshenie)
		{
			kamera.osveshenie = kamera_osveshenie.isSelected();
		} else if (event == kamera_vreber)
		{
			kamera.vreber = kamera_vreber.isSelected();
		} else if (event == kamera_vtochek)
		{
			kamera.vtochek = kamera_vtochek.isSelected();
		} else if (event == kamera_vtext)
		{
			kamera.vtext = kamera_vtext.isSelected();
		}
	}
}
