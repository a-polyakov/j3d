import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 05.01.2007
 * Time: 23:47:24
 * Обработка событий нажатие на кнопку
 */
public class ButtonListener implements ActionListener
{
	public Index index;
	public ButtonGroup mouseOption;

	public ButtonListener(Index index)
	{
		this.index = index;
	}

	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (event == index.okno1)
			index.setCenter1();
		else if (event == index.okno4)
			index.setCenter4();
	}
}
