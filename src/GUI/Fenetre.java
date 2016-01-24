package GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * Représente une fenêtre contenant :
 * <ul>
 * <li>une barre de menu</li>
 * <li>une barre latérale contenant une liste d'action</li>
 * <li>un panneau central contenant tous les dessins</li>
 * </ul>
 * @author Massinissa
 * @author Armand
 */
public class Fenetre extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Panneau pan;
	private BarreLaterale barre;
	private Menu menu;
	
	public Fenetre(Panneau pan, BarreLaterale barre, Menu menu)
	{
		this.pan = pan;
		this.barre = barre;
		this.menu = menu;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Design");
	
		setLayout(new BorderLayout());	
	
		// Un panneau central
		add(this.pan, BorderLayout.CENTER);
		
		// Une barre latérale à gauche
		add(this.barre, BorderLayout.WEST);
		
		// Un menu
		add(this.menu, BorderLayout.NORTH);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
