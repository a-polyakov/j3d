package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.object.dynamical.Telo;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:52:33
 * панель анимации видимости
 */
public class AnimVisPanel extends OptionPanel
{
	private Telo telo;

	public AnimVisPanel(Telo telo)
	{
		super(new GridLayout(2,1));
		this.telo = telo;
		add(new JLabel("Анимация видимости"));
		add(new AnimBooleanPanel(telo.visA));
	}
}