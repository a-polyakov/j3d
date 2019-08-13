package polyakov.java3d.tree;

import polyakov.java3d.Index;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 28.05.2007
 * Time: 13:52:06
 * Окна изменения свойств сцены и объектов
 * можно было использовать классы для каждого диологового окна, но тогда проект сильно увеличевается в размере
 */
public class OptionScen extends JDialog
{
	// константы
	public static final int MATERIAL_ADD = 6;	// окно добавления материала
	public static final int TOCHKA_ADD = 13;	// окно добавления точки
	public static final int LAMP = 21;			// окно свойств источника света
	public static final int LAMP_ADD = 22;		// окно добавления источника света
	// переменные
	private Dimension size;	// размер окна
	private Index index;	// аплет
	private int id, id2;	// номер выделеного элемента

	private TreeScen treeScen;	// дерево сцены

	public OptionScen(JFrame frame, Index index, TreeScen treeScen)
	{
		super(frame, "Опции");
		this.treeScen = treeScen;
		size = new Dimension(300, 300);
		setMinimumSize(size);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.index = index;
	}
}
