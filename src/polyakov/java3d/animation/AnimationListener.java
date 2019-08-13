package polyakov.java3d.animation;

import polyakov.java3d.Index;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Alex1
 * Date: 18.04.2007
 * Time: 22:25:49
 * Обработка событий анимации
 */
public class AnimationListener implements ChangeListener, ActionListener
{
	private Index index;

	public AnimationListener(Index index)
	{
		this.index = index;
	}

	// изменение положения ползунка
	public void stateChanged(ChangeEvent e)
	{
		Object event = e.getSource();
		if (event == index.kadr)
		{
			if (!index.play.isSelected())
			{
				int kadr = index.kadr.getValue();
				index.kadrLabel.setText(String.valueOf(kadr));
				if (index.animation != null)
					index.animation.setKadr(kadr);
				index.scen.setKadr(kadr);
				index.repaintCenter();
			}
		} else if (event == index.gh)
		{
			index.animation.setFPS(index.gh.getInt());
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (event == index.play)
		{
			if (index.play.isSelected())
			{
				index.animation = new Animation(index);
				index.animation.setFPS(index.gh.getInt());
				index.animation.setKadr(index.kadr.getValue());
				index.animation.start();
			} else
				index.animation.flag = false;
		}
	}
}
