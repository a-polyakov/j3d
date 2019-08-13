package polyakov.java3d.tree;

import polyakov.java3d.Index;

import javax.swing.tree.DefaultMutableTreeNode;
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
public class TreeScen extends JDialog implements TreeSelectionListener
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

	private JSplitPane splitPane;

	private DefaultMutableTreeNode vScen;
	private DefaultMutableTreeNode vTel;
	private DefaultMutableTreeNode vLamp;
	private DefaultMutableTreeNode vKamer;
	private DefaultMutableTreeNode vMaterial;
	private DefaultMutableTreeNode maxKadr;

	public TreeScen(JFrame frame, Index index)
	{
		super(frame, "Дерево сцены");
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setMinimumSize(new Dimension(300, 300));
		setSize(500, 300);
		this.index = index;

		vScen = new DefaultMutableTreeNode(SCEN);
		vTel = new DefaultMutableTreeNode(TELA);
		vScen.add(vTel);
		vLamp = new DefaultMutableTreeNode(OSVECHENIE);
		vScen.add(vLamp);
		vKamer = new DefaultMutableTreeNode(KAMERI);
		vScen.add(vKamer);
		vMaterial = new DefaultMutableTreeNode(MATERIAL);
		vScen.add(vMaterial);
		maxKadr = new DefaultMutableTreeNode(LENGHT_ANIM);
		vScen.add(maxKadr);
		reBuild();

		JTree tree = new JTree(vScen);
		tree.addTreeSelectionListener(this);

		JScrollPane scrollPane = new JScrollPane(tree);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, new JPanel());
		splitPane.setDividerLocation(250);
		add(splitPane);
		setVisible(false);
	}

	// перестроить дерево
	public void reBuild()
	{
		int i, j;

		vTel.removeAllChildren();
		// список тел
		if (index.scen.mTel != null)
		{
			for (i = 0; i < index.scen.mTelLen; i++)
			{
				DefaultMutableTreeNode telo = new DefaultMutableTreeNode("[" + i + "] " + index.scen.mTel[i].name);
				// список точек тела
				DefaultMutableTreeNode vTochki = new DefaultMutableTreeNode(TOHKI);
				if (index.scen.mTel[i].mp != null)
				{
					for (j = 0; j < index.scen.mTel[i].mplen; j++)
					{
						DefaultMutableTreeNode tochka = new DefaultMutableTreeNode("p[" + j + "]");
						tochka.add(new DefaultMutableTreeNode(ANIM_MOV));
						tochka.add(new DefaultMutableTreeNode(ANIM_TEXT));
						vTochki.add(tochka);
					}
				}
				telo.add(vTochki);
				// список ребер тела
				DefaultMutableTreeNode vReber = new DefaultMutableTreeNode(REBRA);
				for (j = 0; j < index.scen.mTel[i].mrlen; j++)
					vReber.add(new DefaultMutableTreeNode("r[" + j + "]"));
				telo.add(vReber);
				// список граней тела
				DefaultMutableTreeNode vGran = new DefaultMutableTreeNode(GRANI);
				for (j = 0; j < index.scen.mTel[i].mglen; j++)
				{
					DefaultMutableTreeNode gran = new DefaultMutableTreeNode("g[" + j + "]");
					gran.add(new DefaultMutableTreeNode(GRANM));
					vGran.add(gran);
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

		vLamp.removeAllChildren();
		// список освещения
		for (i = 0; i < index.scen.mLampLen; i++)
			vLamp.add(new DefaultMutableTreeNode("[" + i + "] " + index.scen.mLamp[i].name));

		vKamer.removeAllChildren();
		// список камер
		for (i = 0; i < index.scen.mKamerLen; i++)
			vKamer.add(new DefaultMutableTreeNode("k[" + i + "]"));

		vMaterial.removeAllChildren();
		// список материалов
		for (i = 0; i < index.scen.mMaterialLen; i++)
			vMaterial.add(new DefaultMutableTreeNode("[" + i + "] " + index.scen.mMaterial[i].name));
	}

	// события дерева
	public void valueChanged(TreeSelectionEvent e)
	{
		Object source = e.getSource();
		if (source != null && source instanceof JTree)
		{
			JTree tree = (JTree) source;
			if (tree.getSelectionPath() != null)
			{   // выделен элемент дерева
				int vlogenost = tree.getSelectionPath().getPathCount();
				if (vlogenost == 1)
				{   // сцена
					setOption(new KadrPanel(index.scen));
				} else if (vlogenost == 2)
				{   // Тела, Освещение, Камеры, Материалы, Длина анимации
					String s = tree.getSelectionPath().getPathComponent(1).toString();
					if (TELA.equals(s))
					{   // Тела
						// добавить
						setOption(new TeloRootPanel(index.scen));
					} else if (OSVECHENIE.equals(s))
					{   // Освещение
						// добавить
						setOption(new LightRootPanel(index.scen));
					} else if (KAMERI.equals(s))
					{   // Камеры
						// размеры окон
						setOption(new KameraRootPanel(index));
					} else if (MATERIAL.equals(s))
					{   // Материалы
						// добавить
						setOption(new MaterialRootPanel(index.scen));
					} else if (LENGHT_ANIM.equals(s))
					{   // Длина анимации
						// поле для ввода
						setOption(new KadrPanel(index.scen));
					}
				} else if (vlogenost == 3)
				{   // элементы
					String s = tree.getSelectionPath().getPathComponent(1).toString();
					if (TELA.equals(s))
					{   // Тело
						// определить номер
						s = tree.getSelectionPath().getPathComponent(2).toString();
						int i = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
						setOption(new TeloPanel(index.scen.mTel[i]));
					} else if (OSVECHENIE.equals(s))
					{   // лампа
						// определить номер
						s = tree.getSelectionPath().getPathComponent(2).toString();
						int i = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
						setOption(new LightPanel(index.scen.mLamp[i]));
					} else if (KAMERI.equals(s))
					{   // Камера
						// определить номер
						s = tree.getSelectionPath().getPathComponent(2).toString();
						int i = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
						setOption(new KameraPanel(index.scen.mKamer[i]));
					} else if (MATERIAL.equals(s))
					{   // Материал
						// определить номер
						s = tree.getSelectionPath().getPathComponent(2).toString();
						int i = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
						setOption(new MaterialPanel(index.scen.mMaterial[i]));
					}
				} else if (vlogenost == 4)
				{   // подобекты и свойства элементов
					String s = tree.getSelectionPath().getPathComponent(1).toString();
					if (TELA.equals(s))
					{   // Тело
						// определить номер
						s = tree.getSelectionPath().getPathComponent(2).toString();
						int i = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
						s = tree.getSelectionPath().getPathComponent(3).toString();
						if (TOHKI.equals(s))
						{	// точки
							// добавить
							setOption(new TochkaRootPanel(index.scen.mTel[i]));
						} else if (REBRA.equals(s))
						{	// ребра
							// добавить
							setOption(new RebroRootPanel(index.scen.mTel[i]));
						} else if (GRANI.equals(s))
						{	// грани
							// добавить
							setOption(new GranRootPanel(index.scen.mTel[i]));
						} else if (ANIM_MOV.equals(s))
						{	// анимация движения
							setOption(new AnimXYZPanel(index.scen.mTel[i].mov));
						} else if (ANIM_ROT.equals(s))
						{	// анимация вращния
							setOption(new AnimXYZPanel(index.scen.mTel[i].rot));
						} else if (ANIM_ZOOM.equals(s))
						{	// анимация масштабирования
							setOption(new AnimZoomPanel(index.scen.mTel[i]));
						} else if (ANIM_VIS.equals(s))
						{	// анимация видимости, видимость
							setOption(new AnimVisPanel(index.scen.mTel[i]));
						}
					}
				} else if (vlogenost == 5)
				{   // элемент
					String s = tree.getSelectionPath().getPathComponent(1).toString();
					if (TELA.equals(s))
					{	// Тело
						// определить номер
						s = tree.getSelectionPath().getPathComponent(2).toString();
						int i = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
						// элемент
						s = tree.getSelectionPath().getPathComponent(3).toString();
						if (TOHKI.equals(s))
						{	// точки
							// определить номер
							s = tree.getSelectionPath().getPathComponent(4).toString();
							int j = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
							setOption(new TochkaPanel(index.scen.mTel[i].mp[j]));
						} else if (REBRA.equals(s))
						{	// ребра
							// определить номер
							s = tree.getSelectionPath().getPathComponent(4).toString();
							int j = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
							setOption(new RebroPanel(index.scen.mTel[i].mr[j]));
						} else if (GRANI.equals(s))
						{	// грани
							// определить номер
							s = tree.getSelectionPath().getPathComponent(4).toString();
							int j = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
							setOption(new GranPanel(index.scen.mTel[i].mg[j]));
						}
					}
				} else if (vlogenost == 6)
				{   //
					String s = tree.getSelectionPath().getPathComponent(1).toString();
					if (TELA.equals(s))
					{	// Тело
						// определить номер
						s = tree.getSelectionPath().getPathComponent(2).toString();
						int i = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
						// элемент
						s = tree.getSelectionPath().getPathComponent(3).toString();
						if (TOHKI.equals(s))
						{	// точки
							// определить номер
							s = tree.getSelectionPath().getPathComponent(4).toString();
							int j = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
							// элемент
							s = tree.getSelectionPath().getPathComponent(5).toString();
							if (ANIM_MOV.equals(s))
							{   // анимация перемещения
								setOption(new AnimXYZPanel(index.scen.mTel[i].mp[j].mov));
							} else if (ANIM_TEXT.equals(s))
							{   // анимация текста
								setOption(new AnimTextPanel(index.scen.mTel[i].mp[j].textA));
							}
						} else if (GRANI.equals(s))
						{	// грани
							// определить номер
							s = tree.getSelectionPath().getPathComponent(4).toString();
							int j = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
							// элемент
							s = tree.getSelectionPath().getPathComponent(5).toString();
							if (GRANM.equals(s))
							{	// материал грани
								setOption(new GranMaterialPanel(index.scen.mTel[i].mg[j]));
							}
						}
					}
				}
			}
		}
	}
	private void setOption(OptionPanel panel)
	{
		splitPane.setRightComponent(panel);
	}
}
