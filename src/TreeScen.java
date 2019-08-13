import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 01.05.2007
 * Time: 19:43:47
 * Дерево сцены
 * объекты и подобекты, а также их свойства
 */
public class TreeScen extends Dialog implements WindowListener, TreeSelectionListener
{
	private static String SCEN = "Сцена";
	private static String TELA = "Тела";
	private static String ANIM_MOV = "Анимация движения";
	private static String ANIM_ROT = "Анимация вращения";
	private static String ANIM_ZOOM = "Анимация масштабирования";
	private static String ANIM_VIS = "Анимация видимости";
	private static String TOHKI = "Точки";
	private static String ANIM_TEXT = "Анимация текста";
	private static String REBRA = "Ребра";
	private static String GRANI = "Грани";
	private static String GRANM = "Материал";
	private static String OSVECHENIE = "Освещение";
	private static String KAMERI = "Камеры";
	private static String MATERIAL = "Материалы";
	private static String LENGHT_ANIM = "Длина анимации";

	private Index index;
	private JTree tree;
	private OptionScen optionScen;

	TreeScen(Frame frame, Index index)
	{
		super(frame, "Дерево сцены");
		addWindowListener(this);
		setMinimumSize(new Dimension(300, 300));
		setSize(300, 300);
		this.index = index;
		optionScen = new OptionScen(frame, index, this);
		reBuild();
		setVisible(false);
	}
	// перестроить дерево 
	public void reBuild()
	{
		int i, j;
		DefaultMutableTreeNode vScen= new DefaultMutableTreeNode(SCEN),
				vTel = new DefaultMutableTreeNode(TELA),
				vLamp = new DefaultMutableTreeNode(OSVECHENIE),
				vKamer = new DefaultMutableTreeNode(KAMERI),
				vMaterial = new DefaultMutableTreeNode(MATERIAL),
				maxKadr = new DefaultMutableTreeNode(LENGHT_ANIM);

		// список тел
		if (index.scen.mTel != null)
		{
			DefaultMutableTreeNode telo;
			for (i = 0; i < index.scen.mTel.length; i++)
			{
				telo = new DefaultMutableTreeNode("[" + i + "] " + index.scen.mTel[i].name);
				// список точек тела
				DefaultMutableTreeNode vTochki = new DefaultMutableTreeNode(TOHKI);
				if (index.scen.mTel[i].mp != null)
				{
					DefaultMutableTreeNode tochka;
					for (j = 0; j < index.scen.mTel[i].mp.length; j++)
					{
						tochka = new DefaultMutableTreeNode("p[" + j + "]");
						tochka.add(new DefaultMutableTreeNode(ANIM_MOV));
						tochka.add(new DefaultMutableTreeNode(ANIM_TEXT));
						vTochki.add(tochka);
					}
				}
				telo.add(vTochki);
				// список ребер тела
				DefaultMutableTreeNode vReber = new DefaultMutableTreeNode(REBRA);
				if (index.scen.mTel[i].mr != null)
					for (j = 0; j < index.scen.mTel[i].mr.length; j++)
						vReber.add(new DefaultMutableTreeNode("r[" + j + "]"));
				telo.add(vReber);
				// список граней тела
				DefaultMutableTreeNode vGran = new DefaultMutableTreeNode(GRANI);
				if (index.scen.mTel[i].mg != null)
				{
					DefaultMutableTreeNode gran;
					for (j = 0; j < index.scen.mTel[i].mg.length; j++)
					{
						gran = new DefaultMutableTreeNode("g[" + j + "]");
						gran.add(new DefaultMutableTreeNode(GRANM));
						vGran.add(gran);
					}
				}
				telo.add(vGran);
				// свойства тела
				telo.add(new DefaultMutableTreeNode(ANIM_MOV));
				telo.add(new DefaultMutableTreeNode(ANIM_ROT));
				telo.add(new DefaultMutableTreeNode(ANIM_ZOOM));
				telo.add(new DefaultMutableTreeNode(ANIM_VIS));
				vTel.add(telo);
			}
		}
		vScen.add(vTel);
		// список освещения
		if (index.scen.mLamp != null)
			for (i = 0; i < index.scen.mLamp.length; i++)
				vLamp.add(new DefaultMutableTreeNode("[" + i + "] "+index.scen.mLamp[i].name));
		vScen.add(vLamp);
		// список камер
		if (index.scen.mKamer != null)
			for (i = 0; i < index.scen.mKamer.length; i++)
				vKamer.add(new DefaultMutableTreeNode("k[" + i + "]"));
		vScen.add(vKamer);
		// список материалов
		if (index.scen.mMaterial != null)
			for (i = 0; i < index.scen.mMaterial.length; i++)
				vMaterial.add(new DefaultMutableTreeNode("[" + i + "] "+index.scen.mMaterial[i].name));
		vScen.add(vMaterial);
		vScen.add(maxKadr);
		tree = new JTree(vScen);	// дерево
		tree.addTreeSelectionListener(this);
		JScrollPane scroll = new JScrollPane(tree);
		removeAll();
		add(scroll);
		setVisible(false);
		setVisible(true);
	}

	// события окна
	public void windowOpened(WindowEvent e)
	{
	}

	public void windowClosing(WindowEvent e)
	{
		optionScen.setVisible(false);
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

	// события дерева
	public void valueChanged(TreeSelectionEvent e)
	{
		if (tree.getSelectionPath() != null)
		{   // выделен элемент дерева
			int vlogenost = tree.getSelectionPath().getPathCount();
			if (vlogenost == 1)
			{   // сцена
				optionScen.setOption(OptionScen.KADR);
			} else if (vlogenost == 2)
			{   // Тела, Освещение, Камеры, Материалы, Длина анимации
				String s = tree.getSelectionPath().getPathComponent(1).toString();
				if (TELA.equals(s))
				{   // Тела
					// добавить
					optionScen.setOption(OptionScen.TELO_ADD);
				} else if (OSVECHENIE.equals(s))
				{   // Освещение
					// добавить
					optionScen.setOption(OptionScen.LAMP_ADD);
				} else if (KAMERI.equals(s))
				{   // Камеры
					// размеры окон
					optionScen.setOption(OptionScen.SIZEOKN);
				} else if (MATERIAL.equals(s))
				{   // Материалы
					// добавить
					optionScen.setOption(OptionScen.MATERIAL_ADD);
				} else if (LENGHT_ANIM.equals(s))
				{   // Длина анимации
					// поле для ввода
					optionScen.setOption(OptionScen.KADR);
				}
			} else if (vlogenost == 3)
			{   // элементы
				String s = tree.getSelectionPath().getPathComponent(1).toString();
				if (TELA.equals(s))
				{   // Тело
					// определить номер
					s = tree.getSelectionPath().getPathComponent(2).toString();
					int i=Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
					optionScen.setOption(OptionScen.TELO, i);
				} else if (OSVECHENIE.equals(s))
				{   // лампа
					// определить номер
					s = tree.getSelectionPath().getPathComponent(2).toString();
					int i=Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
					optionScen.setOption(OptionScen.LAMP, i);
				} else if (KAMERI.equals(s))
				{   // Камера
					// определить номер
					s = tree.getSelectionPath().getPathComponent(2).toString();
					int i=Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
					optionScen.setOption(OptionScen.KAMERA, i);
				} else if (MATERIAL.equals(s))
				{   // Материал
					// определить номер
					s = tree.getSelectionPath().getPathComponent(2).toString();
					int i=Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
					optionScen.setOption(OptionScen.MATERIAL, i);
				}
			} else if (vlogenost == 4)
			{   // подобекты и свойства элементов
				String s = tree.getSelectionPath().getPathComponent(1).toString();
				if (TELA.equals(s))
				{   // Тело
					// определить номер
					s = tree.getSelectionPath().getPathComponent(2).toString();
					int i=Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
					s = tree.getSelectionPath().getPathComponent(3).toString();
					if (TOHKI.equals(s))
					{	// точки
						// добавить
						optionScen.setOption(OptionScen.TOCHKA_ADD, i);
					} else if (REBRA.equals(s))
					{	// ребра
						// добавить
						optionScen.setOption(OptionScen.REBRO_ADD, i);
					} else if (GRANI.equals(s))
					{	// грани
						// добавить
						optionScen.setOption(OptionScen.GRAN_ADD, i);
					} else if (ANIM_MOV.equals(s))
					{	// анимация движения
						optionScen.setOption(OptionScen.ANIM_MOV, i);
					} else if (ANIM_ROT.equals(s))
					{	// анимация вращния
						optionScen.setOption(OptionScen.ANIM_ROT, i);
					} else if (ANIM_ZOOM.equals(s))
					{	// анимация масштабированияштаб
						optionScen.setOption(OptionScen.ANIM_ZOOM, i);
					} else if (ANIM_VIS.equals(s))
					{	// анимация видимости, видимость
						optionScen.setOption(OptionScen.ANIM_VIS, i);
					}
				}
			} else if (vlogenost == 5)
			{   // элемент
				String s = tree.getSelectionPath().getPathComponent(1).toString();
				if (TELA.equals(s))
				{	// Тело
					// определить номер
					s = tree.getSelectionPath().getPathComponent(2).toString();
					int i=Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
					// элемент
					s = tree.getSelectionPath().getPathComponent(3).toString();
					if (TOHKI.equals(s))
					{	// точки
						// определить номер
						s = tree.getSelectionPath().getPathComponent(4).toString();
						int j= Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
						optionScen.setOption(OptionScen.TOCHKA, i,j);
					} else if (REBRA.equals(s))
					{	// ребра
						// определить номер
						s = tree.getSelectionPath().getPathComponent(4).toString();
						int j= Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
						optionScen.setOption(OptionScen.REBRO, i,j);
					} else if (GRANI.equals(s))
					{	// грани
						// определить номер
						s = tree.getSelectionPath().getPathComponent(4).toString();
						int j= Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
						optionScen.setOption(OptionScen.GRAN, i,j);
					}
				}
			} else if (vlogenost == 6)
			{   //
				String s = tree.getSelectionPath().getPathComponent(1).toString();
				if (TELA.equals(s))
				{	// Тело
					// определить номер
					s = tree.getSelectionPath().getPathComponent(2).toString();
					int i=Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
					// элемент
					s = tree.getSelectionPath().getPathComponent(3).toString();
					if (TOHKI.equals(s))
					{	// точки
						// определить номер
						s = tree.getSelectionPath().getPathComponent(4).toString();
						int j=Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
						// элемент
						s = tree.getSelectionPath().getPathComponent(5).toString();
						if (ANIM_MOV.equals(s))
						{   // анимация перемещения
							optionScen.setOption(OptionScen.ANIM_MOV, i,j);
						} else if (ANIM_TEXT.equals(s))
						{   // анимация текста
							optionScen.setOption(OptionScen.ANIM_TEXT, i,j);
						}
					} else if (GRANI.equals(s))
					{	// грани
						// определить номер
						s = tree.getSelectionPath().getPathComponent(4).toString();
						int j=Integer.parseInt(s.substring(s.indexOf("[")+1,s.indexOf("]")));
						// элемент
						s = tree.getSelectionPath().getPathComponent(5).toString();
						if (GRANM.equals(s))
						{	// материал грани
							optionScen.setOption(OptionScen.GRANM, i,j);
						}
					}
				}
			}
		}
	}
}
