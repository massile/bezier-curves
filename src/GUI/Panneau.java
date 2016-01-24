package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import Entrees.Souris;
import Geometrie.IFormeEditable;

/**
 * Classe représentant un panneau d'édition (contenant des courbes)
 * @author Massinissa
 * @author Armand
 */
public class Panneau extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static final int LARGEUR = 1024;
	public static final int HAUTEUR = 728;

	private ArrayList<IFormeEditable> formes; // Formes à dessiner
	private IFormeEditable formeSelectionnee;
	private JLabel info;			//Texte d'information qui s'affiche en haut de la fenêtre.

	/**
	 * Créé un panneau vide de largeur 1024px et de hauteur 728px
	 */
	public Panneau()
	{
		this.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
		Souris souris = new Souris(this);
		
		// Nécessaire pour rendre le panneau réactif aux actions de la souris.
		this.addMouseListener(souris);
		this.addMouseMotionListener(souris);
		this.addMouseWheelListener(souris);
		
		this.formes = new ArrayList<IFormeEditable>();
		info = new JLabel("<html> Pour créer une nouvelle courbe cliquez sur le bouton 'Ajouter' </html>", SwingConstants.CENTER);
		this.add(info);
		info.setVisible(true);
	}
	
	/**
	 * Modifie les informations affichées sur le panneau.<br>
	 * Pour un retour à la ligne utiliser la balise "< br >".
	 * @param info
	 */
	public void setInfo(String info)
	{
		String str = "Un clique droit de la souris permet de ";
		if(estModeEditionPoints())
			str += "sélectionner des points.<br>";
		else
			str +="sélectionner des courbes.<br>";
		this.info.setText("<html><div style=\"text-align: center;\">"+info+str+"</html>");
	}

	/**
	 * @return la liste des formes contenu dans le panneau.
	 */
	public ArrayList<IFormeEditable> getFormesEditables()
	{
		return this.formes;
	}

	/**
	 * @return la forme qui est actuellement sélectionnée par l'utilisateur.
	 */
	public IFormeEditable getFormeSelectionnee()
	{
		return this.formeSelectionnee;
	}

	/**
	 * @return vrai si l'utilisateur édite les points de l'une des formes.
	 */
	public boolean estModeEditionPoints()
	{
			if(this.getFormeSelectionnee() != null)
				return this.formeSelectionnee.estModeEditionPoints();
			else
				return false;
	}

	/**
	 * Permet de selectionner uniquement la forme <b>forme</b>.
	 * @param forme : la forme à selectionner
	 */
	public void selectionnerForme(IFormeEditable forme)
	{
		this.deselectionnerForme();
		forme.selectionner();
		this.formeSelectionnee = forme;
	}

	/**
	 * Permet de déselectionner toutes les formes du panneau.
	 */
	public void deselectionnerForme()
	{
		if (this.getFormeSelectionnee() != null)
			this.getFormeSelectionnee().deselectionner();
		this.formeSelectionnee = null;
	}

	/**
	 * Méthode pour dessiner sur le panneau
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		// Anticrénelage
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Fond blanc
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, LARGEUR, HAUTEUR);
		
		// Dessin des formes
		for (IFormeEditable forme : this.formes)
			forme.dessinerInterieur((Graphics2D) g);
		for (IFormeEditable forme : this.formes)
			forme.dessinerContour((Graphics2D) g);
		for (IFormeEditable forme : this.formes)
			forme.dessinerFeatures((Graphics2D) g);
	}

}
