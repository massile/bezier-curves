package Actions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action qui quitte le programme s'il elle est activée.
 * @author Massinissa
 * @author Armand
 */
public class Quitter implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.exit(0);
	}

}