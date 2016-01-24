 package GUI;

import java.awt.event.ActionEvent;
import Actions.Action;

/**
 * Bouton particulier qui execute l'action choisie dès l'appui sur celui-ci
 * @author Massinissa
 * @author Armand
 */
public class BoutonActionSurAppuie extends Bouton
{
	private static final long serialVersionUID = 1L;

	/**
	 * Créé un bouton sur lequel est écrit le contenu du paramètre 'nom' et qui
	 * sélectionne et execute l'action 'action' lorsque l'utilisateur clique dessus.
	 * L'icone ajoutée correspond à l'image dont le nom est : 'nom'.png
	 * 
	 * @param nom
	 * @param action
	 * @param pan
	 */
	public BoutonActionSurAppuie(String nom, int action, Panneau pan)
	{
		super(nom, action, pan);
	}

	/**
	 * Sélectionne est execute l'action choisie.
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		super.actionPerformed(e);
		switch (this.getActionChoisie())
		{
			case Action.MODE:
				Action.switchMode(this.getPanneau());				
				break;
		}
	}
}
