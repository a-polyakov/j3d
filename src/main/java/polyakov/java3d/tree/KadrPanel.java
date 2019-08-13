package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.object.dynamical.Scen;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.03.2010
 * Time: 13:39:36
 * панель продолжительность анимации
 */
public class KadrPanel extends OptionPanel implements ChangeListener
{
	private NumUpDown kadr_max;	// поле ввода

	private Scen scen;
	public KadrPanel(Scen scen)
	{
		//setTitle("Продолжительность анимации");
		this.scen=scen;
		add(new JLabel("Кадров"));
		kadr_max = new NumUpDown(NumUpDown.TYPE_INT);
		kadr_max.setMin(1);
		kadr_max.setInt(scen.maxKadr);
		kadr_max.addChangeListener(this);
		add(kadr_max);
	}

	// изменение поля
	public void stateChanged(ChangeEvent e)
	{
		scen.maxKadr = kadr_max.getInt();
		sendScenEvent();
		//!!!index.kadr.setMaximum(kadr_max.getInt());
	}
}
