package polyakov.java3d.tree;

import polyakov.java3d.object.ScenListener;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.03.2010
 * Time: 16:20:43
 * TODO.
 */
public abstract class OptionPanel extends JPanel
{
	private List<ScenListener> listeners;
	public OptionPanel()
	{
		listeners=new ArrayList();
	}
	public OptionPanel(LayoutManager layoutManager)
	{
		super(layoutManager);
		listeners=new ArrayList();
	}
	public void addScenListener(ScenListener scenListener)
	{
		listeners.add(scenListener);
	}

	protected void sendScenEvent()
	{
		for (ScenListener listener:listeners)
			listener.scenChanged();
	}
}
