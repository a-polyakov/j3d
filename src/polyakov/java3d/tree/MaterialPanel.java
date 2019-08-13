package polyakov.java3d.tree;

import polyakov.java3d.field.ColorField;
import polyakov.java3d.field.ColorListener;
import polyakov.java3d.object.dynamical.Material;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 14:06:53
 * панель свойств материала
 */
public class MaterialPanel extends OptionPanel implements ColorListener, DocumentListener
{
	private JTextField material_name;
	private JTextField material_file;
	private ColorField material_color;
	private JButton material_del;

	private Material material;

	public MaterialPanel(Material material)
	{
		//setTitle("Материал");
		super(new GridLayout(4, 2));
		this.material=material;
		add(new Label("Имя"));
		material_name = new JTextField();
		material_name.getDocument().addDocumentListener(this);
		add(material_name);
		add(new JLabel("Файл"));
		material_file = new JTextField(material.name);
		add(material_file);
		add(new JLabel("Цвет"));
		material_color = new ColorField(material.color);
		material_color.addColorListener(this);
		add(material_color);
		add(new Label("-"));
		material_del = new JButton("Удалить");
		add(material_del);
	}

	public void colorValueChanged(ColorField colorField)
	{
		material.color=material_color.getInt();
	}

	 /**
     * Gives notification that there was an insert into the document.  The
     * range given by the DocumentEvent bounds the freshly inserted region.
     *
     * @param e the document event
     */
    public void insertUpdate(DocumentEvent e)
	 {
		 textValueChanged();
	 }

	/**
     * Gives notification that a portion of the document has been
     * removed.  The range is given in terms of what the view last
     * saw (that is, before updating sticky positions).
     *
     * @param e the document event
     */
    public void removeUpdate(DocumentEvent e)
	{
		textValueChanged();
	}

	/**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e the document event
     */
    public void changedUpdate(DocumentEvent e)
	{

	}

	private void textValueChanged()
	{
		String s=material_name.getText();
		if (!s.equals(""))
				material.name=s;
	}
}
