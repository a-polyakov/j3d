package polyakov.java3d.file;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11.03.2010
 * Time: 11:36:05
 * интерфейс сохранения загрузми данных
 */
public interface FileJava3D
{
	public void load(DataInputStream in) throws IOException;
	public void save(DataOutputStream out) throws IOException;
}
