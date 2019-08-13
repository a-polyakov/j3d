import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 08.01.2007
 * Time: 17:13:40
 * Экран
 */
public class Screen extends JPanel implements MouseListener, MouseMotionListener, ActionListener, ItemListener
{
	public Image image;
	public Kamera kam;

	int mouseX, mouseY;

	private PopupMenu menu;		// меню
	private Menu sOption;
	private MenuItem cFront, cBack, cTop, cBottom, cLeft, cRight;
	private CheckboxMenuItem perspektiva, vsk, vsetki, vtext, vtochek, vreber, vgran, osveshenie;

	public Screen(Kamera kam)
	{
		super();
		this.kam = kam;
		addMouseListener(this);
		addMouseMotionListener(this);
		makeMenu();
	}

	// инициализация меню
	private void makeMenu()
	{
		menu = new PopupMenu();
		cFront = new MenuItem("Front");
		cFront.addActionListener(this);
		menu.add(cFront);
		cBack = new MenuItem("Back");
		cBack.addActionListener(this);
		menu.add(cBack);
		cTop = new MenuItem("Top");
		cTop.addActionListener(this);
		menu.add(cTop);
		cBottom = new MenuItem("Bottom");
		cBottom.addActionListener(this);
		menu.add(cBottom);
		cLeft = new MenuItem("Left");
		cLeft.addActionListener(this);
		menu.add(cLeft);
		cRight = new MenuItem("Right");
		cRight.addActionListener(this);
		menu.add(cRight);
		menu.add(new MenuItem("-"));
		sOption = new Menu("настройки");
		menu.add(sOption);
		perspektiva = new CheckboxMenuItem("Перспектива", kam.perspektiva);
		perspektiva.addItemListener(this);
		sOption.add(perspektiva);
		vsk = new CheckboxMenuItem("СК", kam.vsk);
		vsk.addItemListener(this);
		sOption.add(vsk);
		vsetki = new CheckboxMenuItem("Сетка", kam.vsetki);
		vsetki.addItemListener(this);
		sOption.add(vsetki);
		vtext = new CheckboxMenuItem("Надписи", kam.vtext);
		vtext.addItemListener(this);
		sOption.add(vtext);
		vtochek = new CheckboxMenuItem("Точки", kam.vtochek);
		vtochek.addItemListener(this);
		sOption.add(vtochek);
		vreber = new CheckboxMenuItem("Ребра", kam.vreber);
		vreber.addItemListener(this);
		sOption.add(vreber);
		vgran = new CheckboxMenuItem("Грани", kam.vgran);
		vgran.addItemListener(this);
		sOption.add(vgran);
		osveshenie = new CheckboxMenuItem("Освещение", kam.osveshenie);
		osveshenie.addItemListener(this);
		sOption.add(osveshenie);
		this.add(menu);
	}

	// обработка событий меню (проекция)
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (event == cFront)
			kam.Front();
		else if (event == cTop)
			kam.Top();
		else if (event == cLeft)
			kam.Left();
		else if (event == cBottom)
			kam.Bottom();
		else if (event == cBack)
			kam.Back();
		else if (event == cRight)
			kam.Right();
		repaint();
	}

	// изминение опций
	public void itemStateChanged(ItemEvent e)
	{
		Object event = e.getItemSelectable();
		if (event == perspektiva)
			kam.perspektiva = perspektiva.getState();
		else if (event == vsk)
			kam.vsk = vsk.getState();
		else if (event == vsetki)
			kam.vsetki = vsetki.getState();
		else if (event == vtext)
			kam.vtext = vtext.getState();
		else if (event == vtochek)
			kam.vtochek = vtochek.getState();
		else if (event == vreber)
			kam.vreber = vreber.getState();
		else if (event == vgran)
			kam.vgran = vgran.getState();
		else if (event == osveshenie)
			kam.osveshenie = osveshenie.getState();
		repaint();
	}

	// прорисовка окна
	public void paint(Graphics g)
	{
		kam.setSize(getSize());
		kam.render(g);
	}

	// события мыши
	// щелчек кнопки
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{ // левая кнопка
			// выделение обекта
			// проверка нажания Ctrl
			// добавить к выделеным
			// проверка нажатия Alt
			// изять из выбраных

		} else if (e.getButton() == MouseEvent.BUTTON3)
		{ // правая кнопка
			// вызов меню
			Point p1 = e.getPoint();
			menu.show(this, p1.x, p1.y);
		}
	}

	// удерживание кнопки
	public void mousePressed(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	// отпускание кнопки
	public void mouseReleased(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	// перемещение мыши при ужетживание кнопки
	public void mouseDragged(MouseEvent e)
	{
		int x, y;
		x = e.getX();
		y = e.getY();
		if (Index.kamMov.isSelected())
			kam.mov(mouseX - x, mouseY - y);
		else if (Index.kamRot0.isSelected())
			kam.rot0(mouseX - x, mouseY - y);
		else if (Index.kamRotC.isSelected())
			kam.rotC(mouseX - x, mouseY - y);
		else if (Index.kamRotZ.isSelected())
			kam.rotZ(mouseX, mouseY, x, y);
		else if (Index.kamD.isSelected())
			kam.movZ(mouseY - y);
		else if (Index.kamHW.isSelected())
			kam.setSizeEkr(mouseY - y);
		mouseX = x;
		mouseY = y;
		repaint();
	}

	// перемещение мыши
	public void mouseMoved(MouseEvent e)
	{
	}

}
