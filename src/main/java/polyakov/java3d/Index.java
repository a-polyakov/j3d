package polyakov.java3d;

import polyakov.java3d.animation.Animation;
import polyakov.java3d.animation.AnimationListener;
import polyakov.java3d.object.dynamical.Scen;
import polyakov.java3d.field.NumUpDown;
import polyakov.java3d.field.RadioButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.applet.Applet;
import java.awt.*;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 11.02.2007
 * Time: 16:05:02
 * Запуск програмы, инициализация аплета
 * Интерфейс
 */
public class Index extends Applet
{
	static LineBorder lb = new LineBorder(Color.black);

	private JTabbedPane top;		// панель инструментов
	public JSplitPane center;		// разделитель по горизонтали видового окна
	private JPanel button;			// панель анимации
	public JSplitPane sp1, sp2;		// разделители по вертикали видового окна
	public Screen c1, c2, c3, c4;	// четыре экрана видового окна

	private ButtonListener buttonListener;
	private AnimationListener animationListener;

	public ButtonGroup viewOption;
	public RadioButton okno1;
	public RadioButton okno4;
	public ButtonGroup mouseOption;
	static public RadioButton kamMov;
	static public RadioButton kamRotC;
	static public RadioButton kamRot0;
	static public RadioButton kamRotZ;
	static public RadioButton kamD;
	static public RadioButton kamHW;

	public JComboBox selOb;
	public RadioButton sel;
	public JComboBox selType;
	public RadioButton mov;
	public RadioButton rot;
	public RadioButton mash;
	public JButton selOff;
	public JButton selAll;
	public JButton selList;
	public RadioButton addEl;
	public JButton delEl;

	public RadioButton line;
	public RadioButton pryamougolnik;
	public RadioButton nugolnik;
	public RadioButton spiral;
	public RadioButton box;
	public RadioButton piramid;
	public RadioButton cilindr;
	public RadioButton sfera;

	public JSlider kadr;

	public JTextField options;
	public NumUpDown x;
	public NumUpDown y;
	public NumUpDown z;
	public JCheckBox play;
	public NumUpDown gh;
	public JTextField kadrLabel;
	public Scen scen;
	private LoadIcon icon;
	public Animation animation;

	// инициализация апплета
	public void init()
	{
		scen = new Scen();
		try
		{
			String s = getParameter("scen");
			if (s != null && s.indexOf(".j3d") > 0)
				scen.loadScen(new URL(getCodeBase() + s).openStream());
		}
		catch (Exception e)
		{
		}
		scen.setKadr(0);
		icon = new LoadIcon();
		setBackground(new Color(0xeeeeee));
		setLayout(new BorderLayout());
		initTop();
		initCenter();
		initButton();
		setCursor(Cursor.getPredefinedCursor(1));
	}

	// определение панели инструментов
	private void initTop()
	{
		top = new JTabbedPane();
		top.addTab("Окно", Okno());
		top.addTab("Редактор", Redaktor());
		top.addTab("Обьекты", Obekti());
		add(top, BorderLayout.NORTH);
	}

	// определение видовых окон
	private void initCenter()
	{
		Dimension minDimension = new Dimension(100, 100);
		center = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		center.setBorder(new LineBorder(Color.black));
		center.setDividerSize(3);
		center.setContinuousLayout(true);
		sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		sp1.setDividerSize(3);
		sp1.setBorder(null);
		sp1.setContinuousLayout(true);
		sp2.setDividerSize(3);
		sp2.setBorder(null);
		sp2.setContinuousLayout(true);
		c1 = new Screen(scen.mKamer[0]);
		c1.setMinimumSize(minDimension);
		c2 = new Screen(scen.mKamer[1]);
		c2.setMinimumSize(minDimension);
		c3 = new Screen(scen.mKamer[2]);
		c3.setMinimumSize(minDimension);
		c4 = new Screen(scen.mKamer[3]);
		c4.setMinimumSize(minDimension);
		sp1.add(c1);
		sp1.add(c3);
		sp2.add(c2);
		sp2.add(c4);
		center.add(sp1);
		center.add(sp2);

		add(center, BorderLayout.CENTER);
	}

	// использовать одно видовое окно
	public void setCenter1()
	{
		center.setDividerLocation(0);
		sp2.setDividerLocation(0);
	}

	// использовать четыре видовых окна
	public void setCenter4()
	{
		center.setDividerLocation(0.5);
		sp1.setDividerLocation(0.5);
		sp2.setDividerLocation(0.5);
	}

	// перерисовать видовые окна
	public void repaintCenter()
	{
		c1.repaint();
		c2.repaint();
		c3.repaint();
		c4.repaint();
	}

	// определение панели анимации
	private void initButton()
	{
		animationListener = new AnimationListener(this);
		button = new JPanel();
		button.setLayout(new BorderLayout());
		kadr = new JSlider();
		kadr.setMinimum(0);
		kadr.setMaximum(scen.maxKadr);
		kadr.setValue(0);
		kadr.addChangeListener(animationListener);
		button.add(kadr, BorderLayout.NORTH);
		JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		options = new JTextField("Загрузка", 15);
		options.setBorder(lb);
		options.setEnabled(false);
		temp.add(options);
		temp.add(new JLabel("X"));
		x = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		temp.add(x);
		temp.add(new JLabel("Y"));
		y = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		temp.add(y);
		temp.add(new JLabel("Z"));
		z = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		temp.add(z);
		play = new JCheckBox(icon.n_play);
		play.setSelectedIcon(icon.y_play);
		play.setToolTipText("Запуск-Стоп");
		play.addActionListener(animationListener);
		// прослушать
		temp.add(play);
		gh = new NumUpDown(NumUpDown.TYPE_INT);
		gh.setMin(-300);
		gh.setMax(300);
		gh.setInt(30);
		temp.add(gh);
		temp.add(new JLabel("/c"));
		kadrLabel = new JTextField("0", 4);
		kadrLabel.setToolTipText("Кадр");
		kadrLabel.setBorder(lb);
		kadrLabel.setEnabled(false);
		temp.add(kadrLabel);
		button.add(temp, BorderLayout.SOUTH);
		add(button, BorderLayout.SOUTH);
	}

	// определение закладки окна
	public JPanel Okno()
	{
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		viewOption = new ButtonGroup();
		mouseOption = new ButtonGroup();
		buttonListener = new ButtonListener(this);
		okno1 = new RadioButton(icon.n_1, icon.y_1, "Одно видовое окно", viewOption, buttonListener, p);
		okno4 = new RadioButton(icon.n_4, icon.y_4, "Четыре видовых окна", viewOption, buttonListener, p);
		okno4.setSelected(true);
		kamMov = new RadioButton(icon.n_kam_mov, icon.y_kam_mov, "Перемещение камеры", mouseOption, buttonListener, p);
		kamRot0 = new RadioButton(icon.n_kam_rot1, icon.y_kam_rot1, "Вращение камеры относительно фокуса", mouseOption, buttonListener, p);
		kamRot0.setSelected(true);
		kamRotC = new RadioButton(icon.n_kam_rot2, icon.y_kam_rot2, "Вращение камеры", mouseOption, buttonListener, p);
		kamRotZ = new RadioButton(icon.n_kam_rot3, icon.y_kam_rot3, "Вращение камеры относительно оси", mouseOption, buttonListener, p);
		kamD = new RadioButton(icon.n_kam_mov2, icon.y_kam_mov2, "Растояние от камеры до фокуса", mouseOption, buttonListener, p);
		kamHW = new RadioButton(icon.n_masht, icon.y_masht, "Размер экрана камеры", mouseOption, buttonListener, p);
		return p;
	}

	// определение закладки редактор
	public JPanel Redaktor()
	{
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		String s1[] = {"Тела", "Центр", "Грани", "Рёбра", "Точки"};
		selOb = new JComboBox(s1);
		selOb.setToolTipText("Подобект для изменений");
		selOb.setPreferredSize(new Dimension(64, 32));
		p.add(selOb);
		sel = new RadioButton(icon.n_sel, icon.y_sel, "Выделить", mouseOption, buttonListener, p);
		String s2[] = {"Новое", "Добавить", "Вычесть", "Пересечение"};
		selType = new JComboBox(s2);
		selType.setToolTipText("Стиль выделения");
		selType.setPreferredSize(new Dimension(64, 32));
		p.add(selType);
		mov = new RadioButton(icon.n_mov, icon.y_mov, "Переместиль", mouseOption, buttonListener, p);
		rot = new RadioButton(icon.n_rot, icon.y_rot, "Вращять", mouseOption, buttonListener, p);
		mash = new RadioButton(icon.n_masht, icon.y_masht, "Маштабировать", mouseOption, buttonListener, p);
		selOff = new JButton(icon.n_selOff);
		selOff.setPressedIcon(icon.y_selOff);
		selOff.setToolTipText("Отменить выделение");
		selOff.setBorder(lb);
		p.add(selOff);
		selAll = new JButton(icon.n_selAll);
		selAll.setPressedIcon(icon.y_selAll);
		selAll.setToolTipText("Выделить всё");
		selAll.setBorder(lb);
		p.add(selAll);
		selList = new JButton(icon.n_selList);
		selList.setPressedIcon(icon.y_selList);
		selList.setToolTipText("Выбрать из списка");
		selList.setBorder(lb);
		p.add(selList);
		addEl = new RadioButton(icon.n_add, icon.y_add, "Добавить элемент", mouseOption, buttonListener, p);
		delEl = new JButton(icon.n_del);
		delEl.setPressedIcon(icon.y_del);
		delEl.setToolTipText("Удалить элемент");
		delEl.setBorder(lb);
		p.add(delEl);
		return p;
	}

	// определение заклдки объекты
	public JPanel Obekti()
	{
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		line = new RadioButton(icon.n_line, icon.y_line, "Линия", mouseOption, buttonListener, p);
		pryamougolnik = new RadioButton(icon.n_pryamougolnik, icon.y_pryamougolnik, "Прямоугольник", mouseOption, buttonListener, p);
		nugolnik = new RadioButton(icon.n_nugolnik, icon.y_nugolnik, "Много гранник", mouseOption, buttonListener, p);
		spiral = new RadioButton(icon.n_spiral, icon.y_spiral, "Спираль", mouseOption, buttonListener, p);
		box = new RadioButton(icon.n_box, icon.y_box, "Параллелепипед", mouseOption, buttonListener, p);
		piramid = new RadioButton(icon.n_piramid, icon.y_piramid, "Пирамида", mouseOption, buttonListener, p);
		cilindr = new RadioButton(icon.n_cilindr, icon.y_cilindr, "Цилиндр", mouseOption, buttonListener, p);
		sfera = new RadioButton(icon.n_sfera, icon.y_sfera, "Сфера", mouseOption, buttonListener, p);
		return p;
	}

	// запуск приложения
	public static void main(String args[])
	{
		WindowApplication w = new WindowApplication();
	}

}
