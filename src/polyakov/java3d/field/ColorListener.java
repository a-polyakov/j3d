package polyakov.java3d.field;

import java.util.EventListener;
import java.awt.event.TextEvent;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 05.03.2010
 * Time: 15:04:38
 * To change this template use File | Settings | File Templates.
 */
public interface ColorListener extends EventListener
{
    public void colorValueChanged(ColorField colorField);
}
