package polyakov.java3d;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Alex1
 * Date: 19.04.2007
 * Time: 20:36:58
 * To change this template use File | Settings | File Templates.
 */
public class LoadIcon
{
	private Icon errorIcon;
	public Icon y_play;
	public Icon n_play;
	public Icon y_1;
	public Icon n_1;
	public Icon y_4;
	public Icon n_4;
	public Icon y_kam_mov;
	public Icon n_kam_mov;
	public Icon y_kam_rot1;
	public Icon n_kam_rot1;
	public Icon y_kam_rot2;
	public Icon n_kam_rot2;
	public Icon y_kam_rot3;
	public Icon n_kam_rot3;
	public Icon y_kam_mov2;
	public Icon n_kam_mov2;
	public Icon y_sel;
	public Icon n_sel;
	public Icon y_mov;
	public Icon n_mov;
	public Icon y_rot;
	public Icon n_rot;
	public Icon y_masht;
	public Icon n_masht;
	public Icon y_selOff;
	public Icon n_selOff;
	public Icon y_selAll;
	public Icon n_selAll;
	public Icon y_selList;
	public Icon n_selList;
	public Icon y_del;
	public Icon n_del;
	public Icon y_add;
	public Icon n_add;
	public Icon y_line;
	public Icon n_line;
	public Icon y_pryamougolnik;
	public Icon n_pryamougolnik;
	public Icon y_nugolnik;
	public Icon n_nugolnik;
	public Icon y_spiral;
	public Icon n_spiral;
	public Icon y_box;
	public Icon n_box;
	public Icon y_piramid;
	public Icon n_piramid;
	public Icon y_cilindr;
	public Icon n_cilindr;
	public Icon y_sfera;
	public Icon n_sfera;

	public LoadIcon()
	{
		initErrorIcon();
		try
		{
			y_play = new ImageIcon(getClass().getResource("pic/play.gif"));
		}
		catch (Exception e)
		{
			y_play = errorIcon;
		}
		try
		{
			n_play = new ImageIcon(getClass().getResource("pic/play-.gif"));
		}
		catch (Exception e)
		{
			n_play = errorIcon;
		}
		try
		{
			y_1 = new ImageIcon(getClass().getResource("pic/1.gif"));
		}
		catch (Exception e)
		{
			y_1 = errorIcon;
		}
		try
		{
			n_1 = new ImageIcon(getClass().getResource("pic/1-.gif"));
		}
		catch (Exception e)
		{
			n_1 = errorIcon;
		}
		try
		{
			y_4 = new ImageIcon(getClass().getResource("pic/4.gif"));
		}
		catch (Exception e)
		{
			y_4 = errorIcon;
		}
		try
		{
			n_4 = new ImageIcon(getClass().getResource("pic/4-.gif"));
		}
		catch (Exception e)
		{
			n_4 = errorIcon;
		}
		try
		{
			y_kam_mov = new ImageIcon(getClass().getResource("pic/kam_mov.gif"));
		}
		catch (Exception e)
		{
			y_kam_mov = errorIcon;
		}
		try
		{
			n_kam_mov = new ImageIcon(getClass().getResource("pic/kam_mov-.gif"));
		}
		catch (Exception e)
		{
			n_kam_mov = errorIcon;
		}
		try
		{
			y_kam_mov2 = new ImageIcon(getClass().getResource("pic/kam_mov2.gif"));
		}
		catch (Exception e)
		{
			y_kam_mov2 = errorIcon;
		}
		try
		{
			n_kam_mov2 = new ImageIcon(getClass().getResource("pic/kam_mov2-.gif"));
		}
		catch (Exception e)
		{
			n_kam_mov2 = errorIcon;
		}
		try
		{
			y_kam_rot1 = new ImageIcon(getClass().getResource("pic/kam_rot1.gif"));
		}
		catch (Exception e)
		{
			y_kam_rot1 = errorIcon;
		}
		try
		{
			n_kam_rot1 = new ImageIcon(getClass().getResource("pic/kam_rot1-.gif"));
		}
		catch (Exception e)
		{
			n_kam_rot1 = errorIcon;
		}
		try
		{
			y_kam_rot2 = new ImageIcon(getClass().getResource("pic/kam_rot2.gif"));
		}
		catch (Exception e)
		{
			y_kam_rot2 = errorIcon;
		}
		try
		{
			n_kam_rot2 = new ImageIcon(getClass().getResource("pic/kam_rot2-.gif"));
		}
		catch (Exception e)
		{
			n_kam_rot2 = errorIcon;
		}
		try
		{
			y_kam_rot3 = new ImageIcon(getClass().getResource("pic/kam_rot3.gif"));
		}
		catch (Exception e)
		{
			y_kam_rot3 = errorIcon;
		}
		try
		{
			n_kam_rot3 = new ImageIcon(getClass().getResource("pic/kam_rot3-.gif"));
		}
		catch (Exception e)
		{
			n_kam_rot3 = errorIcon;
		}
		try
		{
			y_sel = new ImageIcon(getClass().getResource("pic/sel.gif"));
		}
		catch (Exception e)
		{
			y_sel = errorIcon;
		}
		try
		{
			n_sel = new ImageIcon(getClass().getResource("pic/sel-.gif"));
		}
		catch (Exception e)
		{
			n_sel = errorIcon;
		}
		try
		{
			y_mov = new ImageIcon(getClass().getResource("pic/mov.gif"));
		}
		catch (Exception e)
		{
			y_mov = errorIcon;
		}
		try
		{
			n_mov = new ImageIcon(getClass().getResource("pic/mov-.gif"));
		}
		catch (Exception e)
		{
			n_mov = errorIcon;
		}
		try
		{
			y_rot = new ImageIcon(getClass().getResource("pic/rot.gif"));
		}
		catch (Exception e)
		{
			y_rot = errorIcon;
		}
		try
		{
			n_rot = new ImageIcon(getClass().getResource("pic/rot-.gif"));
		}
		catch (Exception e)
		{
			n_rot = errorIcon;
		}
		try
		{
			y_masht = new ImageIcon(getClass().getResource("pic/masht.gif"));
		}
		catch (Exception e)
		{
			y_masht = errorIcon;
		}
		try
		{
			n_masht = new ImageIcon(getClass().getResource("pic/masht-.gif"));
		}
		catch (Exception e)
		{
			n_masht = errorIcon;
		}
		try
		{
			y_selOff = new ImageIcon(getClass().getResource("pic/selOff.gif"));
		}
		catch (Exception e)
		{
			y_selOff = errorIcon;
		}
		try
		{
			n_selOff = new ImageIcon(getClass().getResource("pic/selOff-.gif"));
		}
		catch (Exception e)
		{
			n_selOff = errorIcon;
		}
		try
		{
			y_selAll = new ImageIcon(getClass().getResource("pic/selAll.gif"));
		}
		catch (Exception e)
		{
			y_selAll = errorIcon;
		}
		try
		{
			n_selAll = new ImageIcon(getClass().getResource("pic/selAll-.gif"));
		}
		catch (Exception e)
		{
			n_selAll = errorIcon;
		}
		try
		{
			y_selList = new ImageIcon(getClass().getResource("pic/selList.gif"));
		}
		catch (Exception e)
		{
			y_selList = errorIcon;
		}
		try
		{
			n_selList = new ImageIcon(getClass().getResource("pic/selList-.gif"));
		}
		catch (Exception e)
		{
			n_selList = errorIcon;
		}
		try
		{
			y_del = new ImageIcon(getClass().getResource("pic/del.gif"));
		}
		catch (Exception e)
		{
			y_del = errorIcon;
		}
		try
		{
			n_del = new ImageIcon(getClass().getResource("pic/del-.gif"));
		}
		catch (Exception e)
		{
			n_del = errorIcon;
		}
		try
		{
			y_add = new ImageIcon(getClass().getResource("pic/add.gif"));
		}
		catch (Exception e)
		{
			y_add = errorIcon;
		}
		try
		{
			n_add = new ImageIcon(getClass().getResource("pic/add-.gif"));
		}
		catch (Exception e)
		{
			n_add = errorIcon;
		}
		try
		{
			y_line = new ImageIcon(getClass().getResource("pic/line.gif"));
		}
		catch (Exception e)
		{
			y_line = errorIcon;
		}
		try
		{
			n_line = new ImageIcon(getClass().getResource("pic/line-.gif"));
		}
		catch (Exception e)
		{
			n_line = errorIcon;
		}
		try
		{
			y_pryamougolnik = new ImageIcon(getClass().getResource("pic/pryamougolnik.gif"));
		}
		catch (Exception e)
		{
			y_pryamougolnik = errorIcon;
		}
		try
		{
			n_pryamougolnik = new ImageIcon(getClass().getResource("pic/pryamougolnik-.gif"));
		}
		catch (Exception e)
		{
			n_pryamougolnik = errorIcon;
		}
		try
		{
			y_nugolnik = new ImageIcon(getClass().getResource("pic/nugolnik.gif"));
		}
		catch (Exception e)
		{
			y_nugolnik = errorIcon;
		}
		try
		{
			n_nugolnik = new ImageIcon(getClass().getResource("pic/nugolnik-.gif"));
		}
		catch (Exception e)
		{
			n_nugolnik = errorIcon;
		}
		try
		{
			y_spiral = new ImageIcon(getClass().getResource("pic/spiral.gif"));
		}
		catch (Exception e)
		{
			y_spiral = errorIcon;
		}
		try
		{
			n_spiral = new ImageIcon(getClass().getResource("pic/spiral-.gif"));
		}
		catch (Exception e)
		{
			n_spiral = errorIcon;
		}
		try
		{
			y_box = new ImageIcon(getClass().getResource("pic/box.gif"));
		}
		catch (Exception e)
		{
			y_box = errorIcon;
		}
		try
		{
			n_box = new ImageIcon(getClass().getResource("pic/box-.gif"));
		}
		catch (Exception e)
		{
			n_box = errorIcon;
		}
		try
		{
			y_piramid = new ImageIcon(getClass().getResource("pic/piramid.gif"));
		}
		catch (Exception e)
		{
			y_piramid = errorIcon;
		}
		try
		{
			n_piramid = new ImageIcon(getClass().getResource("pic/piramid-.gif"));
		}
		catch (Exception e)
		{
			n_piramid = errorIcon;
		}
		try
		{
			y_cilindr = new ImageIcon(getClass().getResource("pic/cilindr.gif"));
		}
		catch (Exception e)
		{
			y_cilindr = errorIcon;
		}
		try
		{
			n_cilindr = new ImageIcon(getClass().getResource("pic/cilindr-.gif"));
		}
		catch (Exception e)
		{
			n_cilindr = errorIcon;
		}
		try
		{
			y_sfera = new ImageIcon(getClass().getResource("pic/sfera.gif"));
		}
		catch (Exception e)
		{
			y_sfera = errorIcon;
		}
		try
		{
			n_sfera = new ImageIcon(getClass().getResource("pic/sfera-.gif"));
		}
		catch (Exception e)
		{
			n_sfera = errorIcon;
		}
	}

	private void initErrorIcon()
	{
		Image img = new BufferedImage(32, 32, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = img.getGraphics();
		g.drawString("error", 3, 8);
		g.drawArc(2, 8, 28, 22, 0, 360);
		g.drawLine(2, 8, 30, 30);
		g.drawLine(2, 30, 30, 8);
		errorIcon = new ImageIcon(img);
	}
}
