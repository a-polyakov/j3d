import java.awt.event.*;
import java.awt.*;
import java.net.URL;
import java.io.InputStream;
import java.io.FileInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Alex1
 * Date: 25.04.2007
 * Time: 13:09:33
 * окно преложения
 */
public class WindowApplication extends Frame implements WindowListener, ActionListener
{
	private MenuItem newScen;
	private MenuItem loadFile;
	private MenuItem saveFile;
	private MenuItem importFile3DS;
	private MenuItem exit;

	private MenuItem tree;
	private TreeScen treeDialog;
	private Index applet;
	// Конструктор инициализация окна
	WindowApplication()
	{
		super("Java3D");
		makeMenu();
		addWindowListener(this);
		applet = new Index();
		applet.init();
		applet.start();
		add("Center", applet);
		treeDialog = new TreeScen(this, applet);

		setSize(640, 480);
		setVisible(true);
	}
	// Инициализация меню
	private void makeMenu()
	{
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("Файл");
		newScen = new MenuItem("Новый");
		newScen.addActionListener(this);
		menuFile.add(newScen);
		loadFile = new MenuItem("Открыть");
		loadFile.addActionListener(this);
		menuFile.add(loadFile);
		saveFile = new MenuItem("Сохранить");
		saveFile.addActionListener(this);
		menuFile.add(saveFile);
		importFile3DS = new MenuItem("Импорт из 3DS");
		importFile3DS.addActionListener(this);
		menuFile.add(importFile3DS);
		menuFile.add("-");
		exit = new MenuItem("Выход");
		exit.addActionListener(this);
		menuFile.add(exit);
		menuBar.add(menuFile);
		Menu menuEdit = new Menu("Редактор");
		tree = new MenuItem("Дерево сцены");
		tree.addActionListener(this);
		menuEdit.add(tree);
		menuBar.add(menuEdit);
		setMenuBar(menuBar);
	}
	
	// событья окна
	public void windowOpened(WindowEvent e)
	{
	}

	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
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

	// события меню
	public void actionPerformed(ActionEvent e)
	{
		Object event = e.getSource();
		if (event.equals(newScen))
		{
			applet.scen.newScen();
			applet.repaintCenter();
			treeDialog.reBuild();
		} else if (event.equals(loadFile))
		{
			FileDialog dialog = new FileDialog(this, "Открыть файл 3d сцены", FileDialog.LOAD);
			dialog.setVisible(true);
			if (dialog.getFile() != null)
			{
				InputStream is;
				try
				{
					is=new FileInputStream(dialog.getDirectory() + dialog.getFile());
				}
				catch(Exception exception)
				{
					is=null;
				}
				if (is!=null && applet.scen.loadScen(is))
				{
					setTitle(dialog.getFile());
					applet.options.setText("Загружено");
					applet.kadr.setMaximum(applet.scen.maxKadr);
					applet.scen.setKadr(applet.kadr.getValue());
					applet.repaintCenter();
					treeDialog.reBuild();
				}
				else
				{
					setTitle("Ошибка ");
					applet.options.setText("Не загружено");
				}

			}
		} else if (event.equals(saveFile))
		{
			FileDialog dialog = new FileDialog(this, "Сохранить файл 3d сцены", FileDialog.SAVE);
			dialog.setVisible(true);
			if (dialog.getFile() != null)
			{
				if (applet.scen.saveScen(dialog.getDirectory() + dialog.getFile()))
				{
					setTitle(dialog.getFile());
					applet.options.setText("Сохранено");
				}
				else
				{
					setTitle("Ошибка ");
					applet.options.setText("Ошибка записи");
				}
			}
		} else if (event.equals(importFile3DS))
		{
			FileDialog dialog = new FileDialog(this, "Импортировать файл 3ds сцены в текущюю", FileDialog.LOAD);
			dialog.setVisible(true);
			String file = dialog.getFile();
			if (file != null)
			{
				if (file.toLowerCase().indexOf(".3ds") > 0)
				{
					applet.options.setText("загрезка " + applet.scen.importScen3DS(dialog.getDirectory() + dialog.getFile()));
					applet.scen.setKadr(applet.kadr.getValue());
					applet.repaintCenter();
					treeDialog.reBuild();
				}
			}
		} else if (event.equals(exit))
		{
			System.exit(0);
		} else if (event.equals(tree))
		{
			treeDialog.setVisible(true);
		}

	}
}
