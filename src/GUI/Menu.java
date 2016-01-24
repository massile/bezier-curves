package GUI;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import Actions.CaptureEcran;
import Actions.Quitter;

/**
 * Représente une barre de menu
 * @author Massinissa
 * @author Armand
 */
public class Menu extends JMenuBar
{
	private static final long serialVersionUID = 1L;

	private JMenu menuFichier;							// Section d'un panneau
	private JMenuItem sauvegarder, quitter;				// Options contenues dans une section

	/**
	 * Créé un menu qui réalisera des actions sur le Panneau 'pan'
	 * @param pan
	 */
	public Menu(Panneau pan)
	{
		super();
		this.menuFichier = new JMenu("Fichier");			// On ajoute une section 'Fichier'
		this.sauvegarder = new JMenuItem("Sauvegarder");	// qui contiendra l'option 'sauvegarder (export du contenu du panneau au format png)
		this.quitter = new JMenuItem("Quitter");			// et l'option 'Quitter' qui ferme le programme.

		this.menuFichier.add(this.sauvegarder);
		this.menuFichier.add(this.quitter);
		
		this.sauvegarder.addActionListener(new CaptureEcran(pan));
		this.quitter.addActionListener(new Quitter());


		this.add(this.menuFichier);
	}
}
