package GUI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import Actions.Action;

/**
 * Représente un bouton qui sélectionne une action (sans l'executer) lorsqu'on clique dessus.
 * 
 * @author Massinissa
 * @author Armand
 */
public class Bouton extends JButton implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private int actionChoisie;
	private Panneau pan;

	/**
	 * Créé un bouton sur lequel est écrit le contenu du paramètre 'nom' et qui
	 * sélectionne l'action 'action' lorsque l'utilisateur clique dessus. L'icone
	 * ajoutée correspond à l'image dont le nom est : 'nom'.png
	 * 
	 * @param nom
	 * @param action
	 * @param pan : le panneau parent
	 */
	public Bouton(String nom, int action, Panneau pan)
	{
		super(nom);
		this.actionChoisie = action;
		this.addActionListener(this);
		this.pan = pan;
		
		this.setIcon(new ImageIcon(getClass().getResource("/res/"+nom+".png")));
	}

	/**
	 * Sélectionne l'action choisie et met à jour les informations écrites sur le panneau
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String info = "Vous êtes en mode édition de";
		Action.activerAction(this.actionChoisie);
		
		switch (actionChoisie)
		{
			case Action.AJOUTER:
				if(pan.estModeEditionPoints())
					info += " points.<br> Cliquez sur le bouton gauche de la souris pour créer un point.";
				else
					info += " formes.<br> Cliquez sur le bouton gauche de la souris pour créer une courbe";
				break;
				
			case Action.COMBINER:
				if(!pan.estModeEditionPoints())
					info = "Veuillez sélectionner la courbe avec laquelle vous souhaitez relier la courbe sélectionnée";
				else
					info = "Pour utiliser la fonction combiner, veuillez passer un mode édition de courbe (bouton 'Mode'). <br><br>"
							+ "Sélectionnez la première courbe, puis cliquez sur le bouton combiner <br>"
							+ "puis sélectionnez la deuxième courbe. <br>"
							+ "L'origine de la 2ème courbe (représenté par un gros point) sera relié à l'extrémité de la première.";
				break;
			case Action.DEPLACER:
				if(pan.estModeEditionPoints())
					info += " points.<br> Laissez enfoncé le clique gauche et déplacez la souris pour positionner le point au niveau du curseur.";
				else
					info += " formes.<br> Laissez enfoncé le clique gauche et déplacez la souris pour positionner le centre de la forme au niveau du curseur.";
				break;
			case Action.MODE:
				if(pan.estModeEditionPoints())
					info += " formes.";
				else
					info += " points.";
				break;
			case Action.ROTATION:
				info = "Clique gauche pour déplacer le centre de rotation. <br>"
						+ " Clique gauche enfoncé et déplacer le curseur verticalement (ou horizontalement) pour tourner.";
				break;
			case Action.SUPPRIMER:
				if(pan.estModeEditionPoints())
					info += " points.<br> Cliquez sur le bouton gauche de la souris pour supprimer le point sélectionné.";
				else
					info += " formes.<br> Cliquez sur le bouton gauche de la souris pour supprimer la courbe sélectionnée.";
				break;
		}
		
		pan.setInfo(info+"<br>");
	}
	
	/**
	 * @return l'action choisie.
	 */
	public int getActionChoisie()
	{
		return this.actionChoisie;
	}
	
	/**
	 * @return retourne le panneau parent
	 */
	public Panneau getPanneau()
	{
		return pan;
	}
}