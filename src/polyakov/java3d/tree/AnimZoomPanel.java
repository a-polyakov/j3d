package polyakov.java3d.tree;

import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.object.dynamical.Telo;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 13:52:33
 * панель анимации масштабирования
 */
public class AnimZoomPanel extends OptionPanel
{
	private Telo telo;

	public AnimZoomPanel(Telo telo)
	{
		super(new GridLayout(2,1));
		this.telo = telo;
		add(new JLabel("Анимация масштабирования"));
		add(new AnimDoublePanel(telo.mashtabA));
	}
}