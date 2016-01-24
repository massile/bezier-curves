package GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import Actions.Action;

/**
 * Représente la barre qui se situera sur le côté du panneau. <br>
 * Cette barre contient les boutons permettant de choisir une action à réaliser
 * sur les formes présentent sur le panneau. Voici la liste des boutons présents : <br>
 * <ul>
 * <li>Ajouter : sélectionne l'action AJOUTER jusqu'à ce qu'une autre action soit appelée</li>
 * <li>Supprimer : sélectionne l'action SUPPRIMER jusqu'à ce qu'une autre action soit appelée</li>
 * <li>Mode : sélectionne et active l'action MODE qui a pour effet de passer du mode édition de formes au mode édition de points. Puis active l'action DEPLACER</li>
 * <li>Combiner : sélectionne l'action COMBINER jusqu'à ce qu'une deuxième courbe soit sélectionnée, puis active l'action DEPLACER</li>
 * <li>Déplacer : sélectionne l'action DEPLACER jusqu'à ce qu'une autre action soit appelée</li>
 * <li>Rotation : sélectionne l'action ROTATION jusqu'à ce qu'une autre action soit appelée</li>
 * <li></li>
 * </ul>
 * @author Massinissa
 * @author Armand
 */
public class BarreLaterale extends JPanel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Créé une barre contenant la liste des actions possibles.
	 * @param pan
	 */
	public BarreLaterale(Panneau pan)
	{

		// Ajout d'une bordure pour bien montrer la séparation avec le panneau
		this.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.white));

		this.setBounds(0, 0, 180, (int) this.getBounds().getHeight());
		this.setLayout(new GridBagLayout());

		JPanel transformations = new JPanel();
		transformations.setBorder(BorderFactory.createTitledBorder("Transformations"));			// Une section pour les transformations
		transformations.setLayout(new GridBagLayout());

		JPanel modifications = new JPanel();
		modifications.setBorder(BorderFactory.createTitledBorder("Modification sur la courbe"));	// Une section pour les modifications
		modifications.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.ipady = 10;

		c.insets = new Insets(10, 0, 0, 0);
		// Section transformations
		c.gridy = 0;
		modifications.add(new Bouton("Ajouter", Action.AJOUTER, pan), c);
		c.gridy = 1;
		modifications.add(new Bouton("Supprimer", Action.SUPPRIMER, pan), c);
		c.gridy = 2;
		modifications.add(new BoutonActionSurAppuie("Mode", Action.MODE, pan), c);
		c.gridy = 3;
		modifications.add(new Bouton("Combiner", Action.COMBINER, pan), c);

		// Section modifications
		c.gridy = 0;
		transformations.add(new Bouton("Déplacer", Action.DEPLACER, pan), c);
		c.gridy = 1;
		transformations.add(new Bouton("Rotation", Action.ROTATION, pan), c);

		c.insets = new Insets(0, 5, 5, 5);
		c.gridy = 0;
		this.add(transformations, c);

		c.ipadx = 70;
		c.gridy = 1;
		this.add(modifications, c);
	}
	
}
