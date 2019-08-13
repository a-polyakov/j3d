/**
 * Created by IntelliJ IDEA.
 * User: Alex
 * Date: 07.01.2007
 * Time: 17:14:00
 * To change this template use File | Settings | File Templates.
 */
public class SortGran
{
	public Gran gran;
	public SortGran next;

	public SortGran()
	{
		gran = null;
		next = null;
	}

	public SortGran(Gran g)
	{
		gran = g;
		next = null;
	}

	public void add(Gran g)
	{
		if (gran == null)
		{
			gran = g;
		} else if (gran.z < g.z)
		{
			if (next != null)
				next.add(g);
			else
				next = new SortGran(g);
		} else
		{
			SortGran temp = new SortGran(gran);
			temp.next = next;
			gran = g;
			next = temp;
		}
	}
}
