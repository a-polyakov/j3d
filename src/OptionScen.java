import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 28.05.2007
 * Time: 13:52:06
 * Окна изменения свойств сцены и объектов
 * можно было использовать классы для каждого диологового окна, но тогда проект сильно увеличевается в размере
 */
public class OptionScen extends Dialog implements WindowListener, ChangeListener, ItemListener, ActionListener, TextListener
{
	// константы
	public static final int KADR = 1;			// окно продолжительности анимации
	public static final int SIZEOKN = 3;		// окно размр видовых окон
	public static final int KAMERA = 4;			// окно свойств камеры
	public static final int MATERIAL = 5;		// окно свойств материала
	public static final int MATERIAL_ADD = 6;	// окно добавления материала
	public static final int ANIM_MOV = 7;		// окно анимации дижения
	public static final int ANIM_ROT = 8;		// окно анимации вращения
	public static final int ANIM_ZOOM = 9;		// окно анимации мсштабирования
	public static final int ANIM_VIS = 10;		// окно анимации видимости
	public static final int ANIM_TEXT = 11;		// окно анимации текста
	public static final int TOCHKA = 12;		// окно свойств точки
	public static final int TOCHKA_ADD = 13;	// окно добавления точки
	public static final int REBRO = 14;			// окно свойств ребра
	public static final int REBRO_ADD = 15;		// окно добавления ребра
	public static final int GRAN = 16;			// окно свойств грани
	public static final int GRAN_ADD = 17;		// окно добавления грани
	public static final int TELO = 18;			// окно свойств тела
	public static final int TELO_ADD = 19;		// окно добавления тела
	public static final int GRANM = 20;			// окно свойств материала грани
	public static final int LAMP = 21;			// окно свойств источника света
	public static final int LAMP_ADD = 22;		// окно добавления источника света
	// переменные
	private Panel kadr;			// панель продолжительность анимации
	private NumUpDown kadr_max;	// поле ввода

	private Panel add;			// панель добавления элемента
	private int add_flag;
	private Button addButton;	// кнопка добавить

	private Panel sizeOkn;		// панель размер видовых окон
	private JSlider sizeOkn1;	// полоса прокрутки
	private JSlider sizeOkn2;
	private JSlider sizeOkn3;

	private Panel kamera;		// панель свойств камеры
	private NumUpDown kamera_x;
	private NumUpDown kamera_y;
	private NumUpDown kamera_z;
	private NumUpDown kamera_nx;
	private NumUpDown kamera_ny;
	private NumUpDown kamera_nz;
	private NumUpDown kamera_a;
	private NumUpDown kamera_me;
	private Checkbox kamera_perspektiva;
	private Checkbox kamera_vsetki;
	private Checkbox kamera_vsk;
	private Checkbox kamera_vkam;
	private Checkbox kamera_vlamp;
	private Checkbox kamera_vgran;
	private Checkbox kamera_osveshenie;
	private Checkbox kamera_vreber;
	private Checkbox kamera_vtochek;
	private Checkbox kamera_vtext;

	private Panel telo;			// панель свойства тела
	private TextField telo_name;
	private Button telo_save;
	private Button telo_del;

	private Panel tochka;		// панель свойств точки
	private NumUpDown tochka_d;
	private ColorField tochka_color;
	private ColorField tochka_colorText;
	private Button tochka_del;

	private Panel rebro;		// панель свойства ребра
	private JComboBox rebro_a;
	private JComboBox rebro_b;
	private NumUpDown rebro_d;
	private ColorField rebro_color;
	private Button rebro_del;


	private Panel rebro_add;	// панель добавления ребра
	private JComboBox rebro_add_a;
	private JComboBox rebro_add_b;
	private Button rebro_add_button;

	private Panel gran;			// панель свойства грани
	private JComboBox gran_a;
	private JComboBox gran_b;
	private JComboBox gran_c;
	private Button gran_del;

	private Panel gran_add;		// панель добавления грани
	private JComboBox gran_add_a;
	private JComboBox gran_add_b;
	private JComboBox gran_add_c;
	private Button gran_add_button;

	private Panel granM;		// панель материала грани
	private JComboBox granM_name;
	private NumUpDown granM_au;
	private NumUpDown granM_av;
	private NumUpDown granM_bu;
	private NumUpDown granM_bv;
	private NumUpDown granM_cu;
	private NumUpDown granM_cv;

	private Panel lamp;			// панель свойств источника света
	private TextField lamp_name;
	private Button lamp_del;

	private Panel material;		// панель свойств материала
	private TextField material_name;
	private TextField material_file;
	private ColorField material_color;
	private Button material_del;

	private JScrollPane animMov;	// панель анимации движения
	private int animMov_flag;
	private NumUpDown animMov_n;
	private NumUpDown animMov_k[];
	private NumUpDown animMov_x[];
	private NumUpDown animMov_y[];
	private NumUpDown animMov_z[];

	private JScrollPane animRot;	// панель анимации вращения
	private NumUpDown animRot_n;
	private NumUpDown animRot_k[];
	private NumUpDown animRot_rx[];
	private NumUpDown animRot_ry[];
	private NumUpDown animRot_rz[];

	private JScrollPane animZoom;	// панель анимации масштабирования

	private JScrollPane animVis;	// панель анимации видимости
	private NumUpDown animVis_n;
	private NumUpDown animVis_k[];
	private Checkbox animVis_vis[];

	private JScrollPane animText;	// панель анимации текста
	private NumUpDown animText_n;
	private NumUpDown animText_k[];
	private TextField animText_text[];

	private Dimension size;	// размер окна
	private Index index;	// аплет
	private int id, id2;	// номер выделеного элемента

	private TreeScen treeScen;	// дерево сцены

	public OptionScen(Frame frame, Index index, TreeScen treeScen)
	{
		super(frame, "Опции");
		this.treeScen = treeScen;
		size = new Dimension(300, 300);
		setMinimumSize(size);
		addWindowListener(this);
		initKadr();
		initAdd();
		initSizeOkn();
		initKamera();
		initMaterial();
		initAnimZoom();
		initTochka();
		initRebro();
		initRebroAdd();
		initGran();
		initGranAdd();
		initGranM();
		initLamp();
		initTelo();
		this.index = index;
	}

	// количество кадров
	private void initKadr()
	{
		kadr = new Panel();
		kadr.add(new Label("Кадров"));
		kadr_max = new NumUpDown(NumUpDown.TYPE_INT);
		kadr_max.setMin(1);
		kadr_max.addChangeListener(this);
		kadr.add(kadr_max);
	}

	// окно продолжительности анимации
	private void initAdd()
	{
		add = new Panel();
		add_flag = -1;
		addButton = new Button("Добавить");
		addButton.addActionListener(this);
		add.add(addButton);
	}
	// окно размр видовых окон
	private void initSizeOkn()
	{
		sizeOkn = new Panel(new GridLayout(6, 1));
		sizeOkn.add(new Label("по горизонтали"));
		sizeOkn1 = new JSlider();
		sizeOkn1.addChangeListener(this);
		sizeOkn.add(sizeOkn1);
		sizeOkn.add(new Label("по вертикали левый"));
		sizeOkn2 = new JSlider();
		sizeOkn2.addChangeListener(this);
		sizeOkn.add(sizeOkn2);
		sizeOkn.add(new Label("по вертикали правыйй"));
		sizeOkn3 = new JSlider();
		sizeOkn3.addChangeListener(this);
		sizeOkn.add(sizeOkn3);
	}

	// окно свойств камеры
	private void initKamera()
	{
		kamera = new Panel(new GridLayout(8, 1));
		Panel p1 = new Panel();
		p1.add(new Label("Камера"));
		p1.add(new Label("X"));
		kamera_x = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_x.addChangeListener(this);
		p1.add(kamera_x);
		p1.add(new Label("Y"));
		kamera_y = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_y.addChangeListener(this);
		p1.add(kamera_y);
		p1.add(new Label("Z"));
		kamera_z = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_z.addChangeListener(this);
		p1.add(kamera_z);
		kamera.add(p1);
		p1 = new Panel();
		p1.add(new Label("Фокус"));
		p1.add(new Label("X"));
		kamera_nx = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_nx.addChangeListener(this);
		p1.add(kamera_nx);
		p1.add(new Label("Y"));
		kamera_ny = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_ny.addChangeListener(this);
		p1.add(kamera_ny);
		p1.add(new Label("Z"));
		kamera_nz = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_nz.addChangeListener(this);
		p1.add(kamera_nz);
		kamera.add(p1);
		p1 = new Panel();
		p1.add(new Label("Экран"));
		p1.add(new Label("поворот"));
		kamera_a = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_a.addChangeListener(this);
		p1.add(kamera_a);
		p1.add(new Label("размер"));
		kamera_me = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		kamera_me.setMin(0.00001);
		kamera_me.addChangeListener(this);
		p1.add(kamera_me);
		kamera.add(p1);
		p1 = new Panel();
		kamera_perspektiva = new Checkbox("перспектива");
		kamera_perspektiva.addItemListener(this);
		p1.add(kamera_perspektiva);
		kamera_vlamp = new Checkbox("лампы");
		kamera_vlamp.addItemListener(this);
		p1.add(kamera_vlamp);
		kamera.add(p1);
		p1 = new Panel();
		kamera_vsetki = new Checkbox("сетка");
		kamera_vsetki.addItemListener(this);
		p1.add(kamera_vsetki);
		kamera_vgran = new Checkbox("грани");
		kamera_vgran.addItemListener(this);
		p1.add(kamera_vgran);
		kamera.add(p1);
		p1 = new Panel();
		kamera_vsk = new Checkbox("СК");
		kamera_vsk.addItemListener(this);
		p1.add(kamera_vsk);
		kamera_osveshenie = new Checkbox("освещение");
		kamera_osveshenie.addItemListener(this);
		p1.add(kamera_osveshenie);
		kamera.add(p1);
		p1 = new Panel();
		kamera_vkam = new Checkbox("камеры");
		kamera_vkam.addItemListener(this);
		p1.add(kamera_vkam);
		kamera_vreber = new Checkbox("ребра");
		kamera_vreber.addItemListener(this);
		p1.add(kamera_vreber);
		kamera.add(p1);
		p1 = new Panel();
		kamera_vtochek = new Checkbox("точки");
		kamera_vtochek.addItemListener(this);
		p1.add(kamera_vtochek);
		kamera_vtext = new Checkbox("надписи");
		kamera_vtext.addItemListener(this);
		p1.add(kamera_vtext);
		kamera.add(p1);
	}

	// окно свойств материала
	private void initMaterial()
	{
		material = new Panel(new GridLayout(4, 2));
		material.add(new Label("Имя"));
		material_name = new TextField();
		material_name.addTextListener(this);
		material.add(material_name);
		material.add(new Label("Файл"));
		material_file = new TextField();
		material.add(material_file);
		material.add(new Label("Цвет"));
		material_color = new ColorField(this);
		material.add(material_color);
		material.add(new Label("-"));
		material_del = new Button("Удалить");
		material.add(material_del);
	}

	// окно анимации перемещения
	private void initAnimMov(int n)
	{
		Panel p1 = new Panel(new GridLayout(5, n + 1));
		int i, min = 1;
		animMov_n = new NumUpDown(NumUpDown.TYPE_INT);
		animMov_n.setMin(1);
		animMov_n.setInt(n);
		animMov_n.addChangeListener(this);
		p1.add(animMov_n);
		for (i = 0; i < n; i++)
			p1.add(new Label("N" + (i + 1)));
		p1.add(new Label("кадр"));
		animMov_k = new NumUpDown[n];
		for (i = 0; i < n; i++)
		{
			animMov_k[i] = new NumUpDown(NumUpDown.TYPE_INT);
			animMov_k[i].setMin(min);
			if (animMov_flag == TELO)
			{
				if (index.scen.mTel[id].aKey[i] < min)
					index.scen.mTel[id].aKey[i] = min;
				else
					min = index.scen.mTel[id].aKey[i];
			} else
			{
				if (index.scen.mTel[id].mp[id2].aKey[i] < min)
					index.scen.mTel[id].mp[id2].aKey[i] = min;
				else
					min = index.scen.mTel[id].mp[id2].aKey[i];
			}
			animMov_k[i].setInt(min);
			animMov_k[i].addChangeListener(this);
			p1.add(animMov_k[i]);
		}
		animMov_x = new NumUpDown[n];
		animMov_y = new NumUpDown[n];
		animMov_z = new NumUpDown[n];
		p1.add(new Label("X"));
		for (i = 0; i < n; i++)
		{
			animMov_x[i] = new NumUpDown(NumUpDown.TYPE_DOUBLE);
			if ((animMov_flag == TELO))
				animMov_x[i].setDouble(index.scen.mTel[id].ax[i]);
			else
				animMov_x[i].setDouble(index.scen.mTel[id].mp[id2].ax[i]);
			animMov_x[i].addChangeListener(this);
			p1.add(animMov_x[i]);
		}
		p1.add(new Label("Y"));
		for (i = 0; i < n; i++)
		{
			animMov_y[i] = new NumUpDown(NumUpDown.TYPE_DOUBLE);
			if ((animMov_flag == TELO))
				animMov_y[i].setDouble(index.scen.mTel[id].ay[i]);
			else
				animMov_y[i].setDouble(index.scen.mTel[id].mp[id2].ay[i]);
			animMov_y[i].addChangeListener(this);
			p1.add(animMov_y[i]);
		}
		p1.add(new Label("Z"));
		for (i = 0; i < n; i++)
		{
			animMov_z[i] = new NumUpDown(NumUpDown.TYPE_DOUBLE);
			if ((animMov_flag == TELO))
				animMov_z[i].setDouble(index.scen.mTel[id].az[i]);
			else
				animMov_z[i].setDouble(index.scen.mTel[id].mp[id2].az[i]);
			animMov_z[i].addChangeListener(this);
			p1.add(animMov_z[i]);
		}
		animMov = new JScrollPane(p1);
	}

	// окно анимации вращения
	private void initAnimRot(int n)
	{
		Panel p1 = new Panel(new GridLayout(5, n + 1));
		int i, min = 1;
		animRot_n = new NumUpDown(NumUpDown.TYPE_INT);
		animRot_n.setMin(1);
		animRot_n.setInt(n);
		animRot_n.addChangeListener(this);
		p1.add(animRot_n);
		for (i = 0; i < n; i++)
			p1.add(new Label("N" + (i + 1)));
		p1.add(new Label("кадр"));
		animRot_k = new NumUpDown[n];
		for (i = 0; i < n; i++)
		{
			animRot_k[i] = new NumUpDown(NumUpDown.TYPE_INT);
			animRot_k[i].setMin(min);
			if (index.scen.mTel[id].aRotKey[i] < min)
				index.scen.mTel[id].aRotKey[i] = min;
			else
				min = index.scen.mTel[id].aRotKey[i];
			animRot_k[i].setInt(min);
			animRot_k[i].addChangeListener(this);
			p1.add(animRot_k[i]);
		}
		p1.add(new Label("αX"));
		animRot_rx = new NumUpDown[n];
		animRot_ry = new NumUpDown[n];
		animRot_rz = new NumUpDown[n];
		for (i = 0; i < n; i++)
		{
			animRot_rx[i] = new NumUpDown(NumUpDown.TYPE_DOUBLE);
			animRot_rx[i].setDouble(index.scen.mTel[id].arx[i]);
			animRot_rx[i].addChangeListener(this);
			p1.add(animRot_rx[i]);
		}
		p1.add(new Label("βY"));
		for (i = 0; i < n; i++)
		{
			animRot_ry[i] = new NumUpDown(NumUpDown.TYPE_DOUBLE);
			animRot_ry[i].setDouble(index.scen.mTel[id].ary[i]);
			animRot_ry[i].addChangeListener(this);
			p1.add(animRot_ry[i]);
		}
		p1.add(new Label("γZ"));
		for (i = 0; i < n; i++)
		{
			animRot_rz[i] = new NumUpDown(NumUpDown.TYPE_DOUBLE);
			animRot_rz[i].setDouble(index.scen.mTel[id].arz[i]);
			animRot_rz[i].addChangeListener(this);
			p1.add(animRot_rz[i]);
		}
		animRot = new JScrollPane(p1);
	}

	// окно анимации масштабирования
	private void initAnimZoom()
	{
		int kluch = 10;
		Panel p1 = new Panel(new GridLayout(3, kluch));
		int i;
		p1.add(new NumUpDown(NumUpDown.TYPE_INT));
		for (i = 0; i < kluch - 1; i++)
			p1.add(new Label("N" + (i + 1)));
		p1.add(new Label("кадр"));
		for (i = 0; i < kluch - 1; i++)
		{
			p1.add(new NumUpDown(NumUpDown.TYPE_INT));
		}
		p1.add(new Label("M"));
		for (i = 0; i < kluch - 1; i++)
		{
			p1.add(new NumUpDown(NumUpDown.TYPE_DOUBLE));
		}
		animZoom = new JScrollPane(p1);
	}

	// окно анимации видимости
	private void initAnimVis(int n)
	{
		Panel p1 = new Panel(new GridLayout(3, n + 1));
		int i, min = 1;
		animVis_n = new NumUpDown(NumUpDown.TYPE_INT);
		animVis_n.setInt(n);
		animVis_n.setMin(1);
		animVis_n.addChangeListener(this);
		p1.add(animVis_n);
		for (i = 0; i < n; i++)
			p1.add(new Label("N" + (i + 1)));
		p1.add(new Label("кадр"));
		animVis_k = new NumUpDown[n];
		for (i = 0; i < n; i++)
		{
			animVis_k[i] = new NumUpDown(NumUpDown.TYPE_INT);
			animVis_k[i].setMin(min);
			if (index.scen.mTel[id].aVisKey[i] < min)
				index.scen.mTel[id].aVisKey[i] = min;
			else
				min = index.scen.mTel[id].aVisKey[i];
			animVis_k[i].setInt(min);
			animVis_k[i].addChangeListener(this);
			p1.add(animVis_k[i]);
		}
		p1.add(new Label("vis"));
		animVis_vis = new Checkbox[n];
		for (i = 0; i < n; i++)
		{
			animVis_vis[i] = new Checkbox();
			animVis_vis[i].setState(index.scen.mTel[id].aVis[i]);
			animVis_vis[i].addItemListener(this);
			p1.add(animVis_vis[i]);
		}
		animVis = new JScrollPane(p1);
	}

	// окно анмации текста
	private void initAnimText(int n)
	{
		Panel p1 = new Panel(new GridLayout(3, n + 1));
		int i, min = 1;
		animText_n = new NumUpDown(NumUpDown.TYPE_INT);
		animText_n.setInt(n);
		animText_n.setMin(1);
		animText_n.addChangeListener(this);
		p1.add(animText_n);
		for (i = 0; i < n; i++)
			p1.add(new Label("N" + (i + 1)));
		p1.add(new Label("кадр"));
		animText_k = new NumUpDown[n];
		for (i = 0; i < n; i++)
		{
			animText_k[i] = new NumUpDown(NumUpDown.TYPE_INT);
			animText_k[i].setMin(min);
			if (index.scen.mTel[id].mp[id2].aTextKey[i] < min)
				index.scen.mTel[id].mp[id2].aTextKey[i] = min;
			else
				min = index.scen.mTel[id].mp[id2].aTextKey[i];
			animText_k[i].setInt(min);
			animText_k[i].addChangeListener(this);
			p1.add(animText_k[i]);
		}
		p1.add(new Label("Текст"));
		animText_text = new TextField[n];
		for (i = 0; i < n; i++)
		{
			animText_text[i] = new TextField(index.scen.mTel[id].mp[id2].aText[i]);
			animText_text[i].addTextListener(this);
			p1.add(animText_text[i]);
		}
		animText = new JScrollPane(p1);
	}

	// окно свойств тела
	private void initTelo()
	{
		telo = new Panel(new GridLayout(2, 2));
		telo.add(new Label("Имя"));
		telo_name = new TextField();
		telo.add(telo_name);
		telo_save = new Button("Сохранить");
		telo_save.addActionListener(this);
		telo.add(telo_save);
		telo_del = new Button("Удалить тело");
		telo_del.addActionListener(this);
		telo.add(telo_del);
	}

	// окно свойств точки
	private void initTochka()
	{
		tochka = new Panel(new GridLayout(5, 2));
		tochka.add(new Label("диаметр"));
		tochka_d = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		tochka_d.setMin(0);
		tochka.add(tochka_d);
		tochka.add(new Label("цвет"));
		tochka.add(new Label("-"));
		tochka.add(new Label("точки"));
		tochka_color = new ColorField(this);
		tochka.add(tochka_color);
		tochka.add(new Label("текста"));
		tochka_colorText = new ColorField(this);
		tochka.add(tochka_colorText);
		tochka.add(new Label("-"));
		tochka_del=new Button("Удалить");
		tochka_del.addActionListener(this);
		tochka.add(tochka_del);
	}

	// окно свойств ребра
	private void initRebro()
	{
		rebro = new Panel(new GridLayout(5, 2));
		rebro.add(new Label("точка A"));
		rebro_a = new JComboBox(new String[]{"1", "2"});
		rebro.add(rebro_a);
		rebro.add(new Label("точка B"));
		rebro_b = new JComboBox(new String[]{"3", "4"});
		rebro.add(rebro_b);
		rebro.add(new Label("ширина"));
		rebro_d = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		rebro_d.setMin(0);
		rebro.add(rebro_d);
		rebro.add(new Label("цвет"));
		rebro_color = new ColorField(this);
		rebro.add(rebro_color);
		rebro.add(new Label("-"));
		rebro_del = new Button("Удалить");
		rebro.add(rebro_del);
	}

	// окно добавления ребра
	private void initRebroAdd()
	{
		rebro_add = new Panel(new GridLayout(3, 2));
		rebro_add.add(new Label("точка A"));
		rebro_add_a = new JComboBox(new String[]{"1", "2"});
		rebro_add.add(rebro_add_a);
		rebro_add.add(new Label("точка B"));
		rebro_add_b = new JComboBox(new String[]{"3", "4"});
		rebro_add.add(rebro_add_b);
		rebro_add.add(new Label("-"));
		rebro_add_button = new Button("Добавить");
		rebro_add_button.addActionListener(this);
		rebro_add.add(rebro_add_button);
	}

	// окно свойств грани
	private void initGran()
	{
		gran = new Panel(new GridLayout(4, 2));
		gran.add(new Label("точка A"));
		gran_a = new JComboBox(new String[]{"1", "2"});
		gran.add(gran_a);
		gran.add(new Label("точка B"));
		gran_b = new JComboBox(new String[]{"3", "4"});
		gran.add(gran_b);
		gran.add(new Label("точка C"));
		gran_c = new JComboBox(new String[]{"5", "6"});
		gran.add(gran_c);
		gran.add(new Label("-"));
		gran_del = new Button("Удалить");
		gran.add(gran_del);
	}

	// окно добавления грани
	private void initGranAdd()
	{
		gran_add = new Panel(new GridLayout(4, 2));
		gran_add.add(new Label("точка A"));
		gran_add_a = new JComboBox(new String[]{"1", "2"});
		gran_add.add(gran_add_a);
		gran_add.add(new Label("точка B"));
		gran_add_b = new JComboBox(new String[]{"3", "4"});
		gran_add.add(gran_add_b);
		gran_add.add(new Label("точка C"));
		gran_add_c = new JComboBox(new String[]{"5", "6"});
		gran_add.add(gran_add_c);
		gran_add.add(new Label("-"));
		gran_add_button = new Button("Добавить");
		gran_add.add(gran_add_button);
	}

	// окно материала грани
	private void initGranM()
	{
		granM = new Panel(new GridLayout(8, 2));
		granM.add(new Label("материал"));
		granM_name = new JComboBox(new String[]{"белый", "серый"});
		granM_name.addItemListener(this);
		granM.add(granM_name);
		granM.add(new Label("Текстурные"));
		granM.add(new Label("координаты"));
		granM.add(new Label("au"));
		granM_au = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		granM.add(granM_au);
		granM.add(new Label("av"));
		granM_av = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		granM.add(granM_av);
		granM.add(new Label("bu"));
		granM_bu = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		granM.add(granM_bu);
		granM.add(new Label("bv"));
		granM_bv = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		granM.add(granM_bv);
		granM.add(new Label("cu"));
		granM_cu = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		granM.add(granM_cu);
		granM.add(new Label("cv"));
		granM_cv = new NumUpDown(NumUpDown.TYPE_DOUBLE);
		granM.add(granM_cv);
	}

	// окно свойств источника света
	private void initLamp()
	{
		lamp = new Panel();
		lamp.add(new Label("Имя"));
		lamp_name = new TextField();
		lamp.add(lamp_name);
		lamp_del = new Button("Удалить");
		lamp.add(lamp_del);
	}


	// выставить тип окна
	public void setOption(int t)
	{
		removeAll();
		if (t == KADR)
		{
			setTitle("Продолжительность анимации");
			kadr_max.setInt(index.scen.maxKadr);
			add(kadr);
			size.setSize(240, 60);
			setSize(size);
		} else if (t == TELO_ADD)
		{
			setTitle("Добавить тело");
			add_flag = t;
			add(add);
			size.setSize(120, 60);
			setSize(size);
		} else if (t == MATERIAL_ADD)
		{
			setTitle("Добавить материал");
			add_flag = t;
			add(add);
			size.setSize(120, 60);
			setSize(size);
		} else if (t == LAMP_ADD)
		{
			setTitle("Добавить свет");
			add_flag = t;
			add(add);
			size.setSize(100, 60);
			setSize(size);
		} else if (t == SIZEOKN)
		{
			setTitle("Размер окон");
			sizeOkn1.setValue(index.center.getDividerLocation());
			sizeOkn1.setMaximum(index.center.getSize().width);
			sizeOkn2.setValue(index.sp1.getDividerLocation());
			sizeOkn2.setMaximum(index.sp1.getSize().height);
			sizeOkn3.setValue(index.sp2.getDividerLocation());
			sizeOkn3.setMaximum(index.sp2.getSize().height);
			add(sizeOkn);
			size.setSize(300, 200);
			setSize(size);
		}
		setVisible(true);
	}

	public void setOption(int t, int id)
	{
		this.id = id;
		boolean flag = true;
		removeAll();
		if (t == KAMERA)
		{
			setTitle("Свойства камеры");
			Kamera k = index.scen.mKamer[id];
			kamera_x.setDouble(k.x);
			kamera_y.setDouble(k.y);
			kamera_z.setDouble(k.z);
			kamera_nx.setDouble(k.nx);
			kamera_ny.setDouble(k.ny);
			kamera_nz.setDouble(k.nz);
			kamera_a.setDouble(k.a);
			kamera_me.setDouble(k.me);
			kamera_perspektiva.setState(k.perspektiva);
			kamera_vsetki.setState(k.vsetki);
			kamera_vsk.setState(k.vsk);
			kamera_vkam.setState(k.vkam);
			kamera_vlamp.setState(k.vlamp);
			kamera_vgran.setState(k.vgran);
			kamera_osveshenie.setState(k.osveshenie);
			kamera_vreber.setState(k.vreber);
			kamera_vtochek.setState(k.vtochek);
			kamera_vtext.setState(k.vtext);
			add(kamera);
			size.setSize(300, 240);
			setSize(size);
		} else if (t == TOCHKA_ADD)
		{
			setTitle("Добавить точку");
			add_flag = t;
			add(add);
			size.setSize(100, 60);
			setSize(size);
		} else if (t == REBRO_ADD)
		{
			if (index.scen.mTel[id].mp != null)
			{
				setTitle("Добавить ребро");
				rebro_add_a.removeAllItems();
				rebro_add_b.removeAllItems();
				int i;
				for (i = 0; i < index.scen.mTel[id].mp.length; i++)
				{
					rebro_add_a.addItem(i);
					rebro_add_b.addItem(i);
				}
				add(rebro_add);
				size.setSize(100, 100);
				setSize(size);
			} else
				flag = false;
		} else if (t == GRAN_ADD)
		{
			if (index.scen.mTel[id].mp != null)
			{
				setTitle("Добавить грань");
				gran_add_a.removeAllItems();
				gran_add_b.removeAllItems();
				gran_add_c.removeAllItems();
				int i;
				for (i = 0; i < index.scen.mTel[id].mp.length; i++)
				{
					gran_add_a.addItem(i);
					gran_add_b.addItem(i);
					gran_add_c.addItem(i);
				}
				add(gran_add);
				size.setSize(100, 100);
				setSize(size);
			} else
				flag = false;
		} else if (t == MATERIAL)
		{
			setTitle("Материал");
			material_name.setText(index.scen.mMaterial[id].name);
			material_color.setColor(index.scen.mMaterial[id].color);
			add(material);
			size.setSize(150, 100);
			setSize(size);
		} else if (t == ANIM_MOV)
		{
			setTitle("Анимация движения");
			animMov_flag = TELO;
			initAnimMov(index.scen.mTel[id].aKey.length);
			add(animMov);
			size.setSize(400, 170);
			setSize(size);
		} else if (t == ANIM_ROT)
		{
			setTitle("Анимация вращения");
			initAnimRot(index.scen.mTel[id].aRotKey.length);
			add(animRot);
			size.setSize(400, 170);
			setSize(size);
		} else if (t == ANIM_ZOOM)
		{
			setTitle("Анимация масштабирования");
			add(animZoom);
			size.setSize(400, 115);
			setSize(size);
		} else if (t == ANIM_VIS)
		{
			setTitle("Анимация видимости");
			initAnimVis(index.scen.mTel[id].aVisKey.length);
			add(animVis);
			size.setSize(400, 115);
			setSize(size);
		} else if (t == TELO)
		{
			setTitle("Тело");
			telo_name.setText(index.scen.mTel[id].name);
			add(telo);
			size.setSize(200, 80);
			setSize(size);
		} else if (t == LAMP)
		{
			setTitle("Лампа");
			lamp_name.setText(index.scen.mLamp[id].name);
			add(lamp);
			size.setSize(250, 60);
			setSize(size);
		}

		setVisible(flag);
	}

	public void setOption(int t, int id, int id2)
	{
		this.id = id;
		this.id2 = id2;
		removeAll();
		if (t == ANIM_MOV)
		{
			setTitle("Анимация движения");
			animMov_flag = TOCHKA;
			initAnimMov(index.scen.mTel[id].mp[id2].aKey.length);
			add(animMov);
			size.setSize(400, 170);
			setSize(size);
		} else if (t == ANIM_TEXT)
		{
			setTitle("Анимация текста");
			initAnimText(index.scen.mTel[id].mp[id2].aTextKey.length);
			add(animText);
			size.setSize(400, 115);
			setSize(size);
		} else if (t == TOCHKA)
		{
			setTitle("Опции точки");
			tochka_d.setDouble(index.scen.mTel[id].mp[id2].d);
			tochka_color.setColor(index.scen.mTel[id].mp[id2].color);
			tochka_colorText.setColor(index.scen.mTel[id].mp[id2].colorText);
			add(tochka);
			size.setSize(150, 150);
			setSize(size);
		} else if (t == REBRO)
		{
			setTitle("Ребро");
			rebro_a.removeAllItems();
			rebro_b.removeAllItems();
			int i;
			for (i = 0; i < index.scen.mTel[id].mp.length; i++)
			{
				rebro_a.addItem(i);
				rebro_b.addItem(i);
			}
			rebro_a.setSelectedItem(index.scen.mTel[id].mr[id2].a);
			rebro_b.setSelectedItem(index.scen.mTel[id].mr[id2].b);
			rebro_d.setDouble(index.scen.mTel[id].mr[id2].d);
			rebro_color.setColor(index.scen.mTel[id].mr[id2].color);
			add(rebro);
			size.setSize(150, 140);
			setSize(size);
		} else if (t == GRAN)
		{
			setTitle("Точки грани");

			gran_a.removeAllItems();
			gran_b.removeAllItems();
			gran_c.removeAllItems();
			int i;
			for (i = 0; i < index.scen.mTel[id].mp.length; i++)
			{
				gran_a.addItem(i);
				gran_b.addItem(i);
				gran_c.addItem(i);
			}
			gran_a.setSelectedItem(index.scen.mTel[id].mg[id2].a);
			gran_b.setSelectedItem(index.scen.mTel[id].mg[id2].b);
			gran_c.setSelectedItem(index.scen.mTel[id].mg[id2].c);
			add(gran);
			size.setSize(150, 100);
			setSize(size);
		} else if (t == GRANM)
		{
			setTitle("Материал грани");
			granM_name.removeAllItems();
			granM_name.setSelectedIndex(-1);
			granM_name.addItem("-");
			if (index.scen.mMaterial != null)
			{
				int i;
				for (i = 0; i < index.scen.mMaterial.length; i++)
					granM_name.addItem(index.scen.mMaterial[i].name);
				if (index.scen.mTel[id].mg[id2].material!=-1)
					granM_name.setSelectedItem(index.scen.mMaterial[index.scen.mTel[id].mg[id2].material].name);
			} else
			{
				granM_name.addItem("-");
				granM_name.setSelectedItem(0);
			}
			add(granM);
			size.setSize(150, 200);
			setSize(size);
		}
		setVisible(true);
	}

	// события окна
	public void windowOpened(WindowEvent e)
	{
	}

	public void windowClosing(WindowEvent e)
	{
		setVisible(false);
	}

	public void windowClosed(WindowEvent e)
	{
	}

	public void windowIconified(WindowEvent e)
	{
	}

	public void windowDeiconified(WindowEvent e)
	{
	}

	public void windowActivated(WindowEvent e)
	{
	}

	public void windowDeactivated(WindowEvent e)
	{
	}

	// изменение поля
	public void stateChanged(ChangeEvent e)
	{
		Object event = e.getSource();
		if (event == kadr_max)
		{
			index.scen.maxKadr = kadr_max.getInt();
			index.kadr.setMaximum(kadr_max.getInt());
		} else if (event == kamera_x)
		{
			index.scen.mKamer[id].x = kamera_x.getDouble();
		} else if (event == kamera_y)
		{
			index.scen.mKamer[id].y = kamera_y.getDouble();
		} else if (event == kamera_z)
		{
			index.scen.mKamer[id].z = kamera_z.getDouble();
		} else if (event == kamera_nx)
		{
			index.scen.mKamer[id].nx = kamera_nx.getDouble();
		} else if (event == kamera_ny)
		{
			index.scen.mKamer[id].ny = kamera_ny.getDouble();
		} else if (event == kamera_nz)
		{
			index.scen.mKamer[id].nz = kamera_nz.getDouble();
		} else if (event == kamera_a)
		{
			index.scen.mKamer[id].a = kamera_a.getDouble();
		} else if (event == kamera_me)
		{
			index.scen.mKamer[id].me = kamera_me.getDouble();
		} else if (event == sizeOkn1)
		{
			index.center.setDividerLocation(sizeOkn1.getValue());
		} else if (event == sizeOkn2)
		{
			index.sp1.setDividerLocation(sizeOkn2.getValue());
		} else if (event == sizeOkn3)
		{
			index.sp2.setDividerLocation(sizeOkn3.getValue());
		} else if (animVis_n == event)
		{
			int i = animVis_n.getInt();
			if (index.scen.mTel[id].aVisKey.length != i)
			{
				// изменить количество ключей анимации видимости
				index.scen.mTel[id].setAnimVis(i);
				initAnimVis(i);
				// перерисовать окно
				removeAll();
				add(animVis);
				setVisible(true);
			}
		} else if (animMov_n == event)
		{
			int i = animMov_n.getInt();
			if (animMov_flag == TELO)
			{	// движение тела
				if (index.scen.mTel[id].aKey.length != i)
				{
					// изменить количество ключей анимации видимости
					index.scen.mTel[id].setAnimMov(i);
					initAnimMov(i);
					// перерисовать окно
					removeAll();
					add(animMov);
					setVisible(true);
				}
			} else	// движение точки
				if (index.scen.mTel[id].mp[id2].aKey.length != i)
				{
					// изменить количество ключей анимации видимости
					index.scen.mTel[id].mp[id2].setAnimMov(i);
					initAnimMov(i);
					// перерисовать окно
					removeAll();
					add(animMov);
					setVisible(true);
				}
		} else if (animRot_n == event)
		{
			int i = animRot_n.getInt();
			if (index.scen.mTel[id].aRotKey.length != i)
			{
				// изменить количество ключей анимации видимости
				index.scen.mTel[id].setAnimRot(i);
				initAnimRot(i);
				// перерисовать окно
				removeAll();
				add(animRot);
				setVisible(true);
			}
		} else if (animText_n == event)
		{
			int i = animText_n.getInt();
			if (index.scen.mTel[id].mp[id2].aTextKey.length != i)
			{
				// изменить количество ключей анимации текста
				index.scen.mTel[id].mp[id2].setAnimText(i);
				initAnimText(i);
				// перерисовать окно
				removeAll();
				add(animText);
				setVisible(true);
			}
		} else 
		{
			if (animVis_k != null)
			{
				int i;
				for (i = 0; i < animVis_k.length; i++)
					if (animVis_k[i] == event)
					{
						index.scen.mTel[id].aVisKey[i] = animVis_k[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
			}
			if (animText_k != null)
			{
				int i;
				for (i = 0; i < animText_k.length; i++)
					if (animText_k[i] == event)
					{
						index.scen.mTel[id].mp[id2].aTextKey[i] = animText_k[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
			}
			if (animMov_k != null)
			{
				int i;
				for (i = 0; i < animMov_k.length; i++)
					if (animMov_k[i] == event)
					{
						if (animMov_flag==TELO)
							index.scen.mTel[id].aKey[i] = animMov_k[i].getInt();
						else
							index.scen.mTel[id].mp[id2].aKey[i] = animMov_k[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
				for (i = 0; i < animMov_x.length; i++)
					if (animMov_x[i] == event)
					{
						if (animMov_flag==TELO)
						index.scen.mTel[id].ax[i] = animMov_x[i].getInt();
						else
						index.scen.mTel[id].mp[id2].ax[i] = animMov_x[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
				for (i = 0; i < animMov_y.length; i++)
					if (animMov_y[i] == event)
					{
						if (animMov_flag==TELO)
						index.scen.mTel[id].ay[i] = animMov_y[i].getInt();
						else
						index.scen.mTel[id].mp[id2].ay[i] = animMov_y[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
				for (i = 0; i < animMov_z.length; i++)
					if (animMov_z[i] == event)
					{
						if (animMov_flag==TELO)
						index.scen.mTel[id].az[i] = animMov_z[i].getInt();
						else
						index.scen.mTel[id].mp[id2].az[i] = animMov_z[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
			}
			if (animRot_k != null)
			{
				int i;
				for (i = 0; i < animRot_k.length; i++)
					if (animRot_k[i] == event)
					{
						index.scen.mTel[id].aRotKey[i] = animRot_k[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
				for (i = 0; i < animRot_rx.length; i++)
					if (animRot_rx[i] == event)
					{
						index.scen.mTel[id].arx[i] = animRot_rx[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
				for (i = 0; i < animRot_ry.length; i++)
					if (animRot_ry[i] == event)
					{
						index.scen.mTel[id].ary[i] = animRot_ry[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
				for (i = 0; i < animRot_rz.length; i++)
					if (animRot_rz[i] == event)
					{
						index.scen.mTel[id].arz[i] = animRot_rz[i].getInt();
						index.scen.setKadr(index.kadr.getValue());
					}
			}
		}
		index.repaintCenter();
	}

	// события выбора
	public void itemStateChanged(ItemEvent e)
	{
		Object event = e.getSource();
		if (event == kamera_perspektiva)
		{
			index.scen.mKamer[id].perspektiva = kamera_perspektiva.getState();
		} else if (event == kamera_vsetki)
		{
			index.scen.mKamer[id].vsetki = kamera_vsetki.getState();
		} else if (event == kamera_vsk)
		{
			index.scen.mKamer[id].vsk = kamera_vsk.getState();
		} else if (event == kamera_vkam)
		{
			index.scen.mKamer[id].vkam = kamera_vkam.getState();
		} else if (event == kamera_vlamp)
		{
			index.scen.mKamer[id].vlamp = kamera_vlamp.getState();
		} else if (event == kamera_vgran)
		{
			index.scen.mKamer[id].vgran = kamera_vgran.getState();
		} else if (event == kamera_osveshenie)
		{
			index.scen.mKamer[id].osveshenie = kamera_osveshenie.getState();
		} else if (event == kamera_vreber)
		{
			index.scen.mKamer[id].vreber = kamera_vreber.getState();
		} else if (event == kamera_vtochek)
		{
			index.scen.mKamer[id].vtochek = kamera_vtochek.getState();
		} else if (event == kamera_vtext)
		{
			index.scen.mKamer[id].vtext = kamera_vtext.getState();
		} else if (granM_name==event)
		{
			if (granM_name.getSelectedIndex()>0)
				index.scen.mTel[id].mg[id2].material=granM_name.getSelectedIndex()-1;
		} else if (animVis_vis != null)
		{
			int i;
			for (i = 0; i < animVis_vis.length; i++)
				if (animVis_vis[i] == event)
				{
					index.scen.mTel[id].aVis[i] = animVis_vis[i].getState();
					index.scen.setKadr(index.kadr.getValue());
				}
		}
		index.repaintCenter();
	}
	// событие изменения цвета
	public void colorChanged(ColorField event)
	{
		if (tochka_color ==event)
		{
			index.scen.mTel[id].mp[id2].color=tochka_color.getInt();
		} else if (tochka_colorText ==event)
		{
			index.scen.mTel[id].mp[id2].colorText=tochka_colorText.getInt();
		}else if (rebro_color ==event)
		{
			index.scen.mTel[id].mr[id2].color=rebro_color.getInt();
		}else if (material_color ==event)
		{
			index.scen.mMaterial[id].color=material_color.getInt();
		}
		index.repaintCenter();
	}
	// событие нажатие на кнопку
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (event == addButton)
		{	// добавить элемент
			if (add_flag == TELO_ADD)
			{	// добавить тело
				index.scen.addTelo();
				// перерисовать дерево
				treeScen.reBuild();
			} else if (add_flag == LAMP_ADD)
			{	// добавить лампу

			} else if (add_flag == MATERIAL_ADD)
			{	// добавить материал
				index.scen.addMaterial();
				treeScen.reBuild();
			} else if (add_flag == TOCHKA_ADD)
			{	// добавить точку
				index.scen.mTel[id].addTochka();
				treeScen.reBuild();
			}
		} else if (telo_save == event)
		{	// сохранить новое имя
			index.scen.mTel[id].name = telo_name.getText();
			setVisible(false);
			treeScen.reBuild();
		} else if (telo_del == event)
		{	// удалить выделеное тело
			index.scen.delTelo(id);
			setVisible(false);
			treeScen.reBuild();
			index.scen.setKadr(index.kadr.getValue());
			index.repaintCenter();
		} else if (gran_add == event)
		{	// добавить грань
			// различны точки
		} else if (rebro_add_button == event)
		{	// добавить ребро
			int a, b;
			a = (Integer) rebro_add_a.getSelectedItem();
			b = (Integer) rebro_add_b.getSelectedItem();
			if (a != b)
			{	// различны точки
				index.scen.mTel[id].addRebro(a, b);
				setVisible(false);
				treeScen.reBuild();
				index.scen.setKadr(index.kadr.getValue());
				index.repaintCenter();
			}
		} else if (tochka_del == event)
		{	// удалить точку
			index.scen.mTel[id].delTochka(id2);
			setVisible(false);
			treeScen.reBuild();
			index.scen.setKadr(index.kadr.getValue());
			index.repaintCenter();
		}
	}

	// событие изменение текста
	public void textValueChanged(TextEvent e)
	{
		Object event = e.getSource();
		if (material_name==event)
		{
			String s=material_name.getText();
			if (!s.equals(""))
				index.scen.mMaterial[id].name=s;
		}
		else
		{
		int i;
		if (animText_text != null)
			for (i = 0; i < animText_text.length; i++)
				if (animText_text[i] == event)
				{
					index.scen.mTel[id].mp[id2].aText[i] = animText_text[i].getText();
					index.scen.setKadr(index.kadr.getValue());
					index.repaintCenter();
				}
		}
	}
}
