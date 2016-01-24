package Actions;

import GUI.Panneau;
import Geometrie.CourbeBezier;
import Geometrie.IForme;
import Geometrie.IFormeEditable;
import Geometrie.Point2D;
import Geometrie.Point2DSelectionnable;

/**
 * Une classe 'static' qui contient toutes les actions possibles sur le Panneau. <br>
 * Ces actions sont également implémentées dans cette classe. <br>
 * La liste des actions est la suivante : <br>
 * <ul>
	 * <li> Action.DEPLACER </li>
	 * <li> Action.MODE </li>
	 * <li> Action.AJOUTER </li>
	 * <li> Action.COMBINER </li>
	 * <li> Action.ROTATION </li>
	 * <li> Action.SUPPRIMER </li>
	 * </ul>
 * @author Massinissa
 * @author Armand
 */
public abstract class Action
{
	private static int actionCourante;

	// Liste des actions possibles
	
	public static final int DEPLACER = 0;
	public static final int MODE = 1;
	public static final int AJOUTER = 2;
	public static final int COMBINER = 3;
	public static final int ROTATION = 4;
	public static final int SUPPRIMER = 5;

	/**
	 * Sélectionne l'action <b>action</b>
	 * @param action
	 */
	public static void activerAction(int action)
	{
		actionCourante = action;
	}

	/**
	 * @return l'action sélectionnée (null si aucune n'est seléctionnée)
	 */
	public static int getActionCourante()
	{
		return actionCourante;
	}

	/**
	 * Si le panneau <b>pan</b> est en mode édition : <br>
	 * 		sélectionne le point de la forme en mode édition le plus proche du point de coordonnées (<b>curX</b>, <b>curY</b>). <br>
	 * Sinon : <br>
	 * 		sélectionne la forme la plus proche du point de coordonnées (<b>curX</b>, <b>curY</b>). <br>
	 * (Si un point/une forme est sélectionnée, toutes les autres sont déséléctionnées)
	 * @param pan
	 * @param curX
	 * @param curY
	 */
	public static void selectionner(Panneau pan, int curX, int curY)
	{
		if (pan.getFormeSelectionnee() == null || !pan.estModeEditionPoints())
		{
			int i = -1;
			boolean trouve = false;

			while (++i < pan.getFormesEditables().size() && !trouve)
				trouve = pan.getFormesEditables().get(i).pointInterieur(curX, curY);

			pan.deselectionnerForme();
			if (trouve)
				pan.selectionnerForme(pan.getFormesEditables().get(i - 1));
		}
		else
		{
			Point2DSelectionnable ptSelect = pan.getFormeSelectionnee().pointInterieurPointCtrl(curX, curY);
			if (ptSelect != null)
				pan.getFormeSelectionnee().selectionnerPtCtrl(ptSelect);
			else if (pan.getFormeSelectionnee().getPointSelectionne() != null)
				pan.getFormeSelectionnee().getPointSelectionne().deselectionner();
		}
	}

	/**
	 * Ajoute un point à la forme sélectionnée si elle est en mode édition, sinon ajoute une nouvelle
	 * courbe de bézier sur le panneau <b>pan</b>. Le point/La courbe sera centré(e) au niveau du point (x,y)
	 * @param pan
	 * @param x
	 * @param y
	 */
	public static void ajouter(Panneau pan, int x, int y)
	{
		// Si aucune 
		if (pan.getFormeSelectionnee() == null || !pan.estModeEditionPoints())
		{
			IFormeEditable nvelleForme = new CourbeBezier(x, y);
			pan.getFormesEditables().add(nvelleForme);
			pan.selectionnerForme(nvelleForme);
		}
		else if (pan.getFormeSelectionnee().peutAjouterPoints())
		{
			Point2DSelectionnable nvPt = new Point2DSelectionnable(x, y);
			IFormeEditable formeSelect = pan.getFormeSelectionnee();
			formeSelect.getPointsCtrl().add(nvPt);
			formeSelect.selectionnerPtCtrl(nvPt);
			formeSelect.deplacerCentreRot(formeSelect.getCentre().getX(), formeSelect.getCentre().getY());
		}
	}

	/**
	 * Supprime un point à la forme sélectionnée si elle est en mode édition, sinon supprime la forme sélectionnée.
	 * Le point/La courbe sera centré(e) au niveau du point (x,y). <br>
	 * Dans le mode édition, si la forme n'est composé que d'un point, elle est automatiquement supprimée. <br>
	 * @param pan
	 * @param pan
	 */
	public static void supprimer(Panneau pan)
	{
		if (pan.getFormeSelectionnee() != null)
		{
			IFormeEditable formeSelect = pan.getFormeSelectionnee();

			if (pan.estModeEditionPoints() && formeSelect.peutAjouterPoints() && formeSelect.getPointSelectionne() != null)
			{
				formeSelect.getPointsCtrl().remove(formeSelect.getPointSelectionne());
				formeSelect.deplacerCentreRot(formeSelect.getCentre().getX(), formeSelect.getCentre().getY());

			} else {
				formeSelect.dereference();
				pan.getFormesEditables().remove(formeSelect);
			}

			if (formeSelect.getPointsCtrl().size() <= 1) {
				formeSelect.dereference();
				pan.getFormesEditables().remove(formeSelect);
			}
		}
	}

	/**
	 * Déplace la forme <b>forme</b> au niveau du point (x,y) <br>
	 * <br>
	 * Si la forme est une forme éditable et que celle ci est en mode édition, <br>
	 * cette méthode déplacera le point de controle sélectionné de la forme au lieu de la forme toute entière.
	 * @param forme
	 * @param x
	 * @param y
	 */
	public static void deplacer(IForme forme, int x, int y)
	{

		if (forme instanceof IFormeEditable)
		{
			IFormeEditable f = (IFormeEditable) forme;
			if (f.estModeEditionPoints())
				f.getPointSelectionne().set(x, y);
			else if (f.estSelectionne())
				forme.set(x, y);
		}
		else
			forme.set(x, y);
	}

	/**
	 * Relie les formes <b>f1</b> et <b>f2</b>
	 * @param f1
	 * @param f2
	 */
	public static void combiner(IFormeEditable f1, IFormeEditable f2)
	{
		if (f1 != null)
			f1.combiner(f2);
	}

	/**
	 * Effectue une rotation de centre <b>centre</b> sur la forme <b>forme</b>.<br>
	 * L'angle de rotation est déterminé par la distance entre p1 et p2 suivant l'abscisse ou l'ordonnée : <br>
	 * si la distance (des abscisses ou des ordonnées) entre p1 et p2 est de <b>Panneau.LARGEUR</b> alors, l'angle de rotation est 2.PI
	 * @param centre
	 * @param forme
	 * @param p1
	 * @param p2
	 */
	public static void rotation(Point2D centre, IForme forme, Point2D p1, Point2D p2)
	{
		forme.tourner(((p1.getX() - p2.getX()) + (p2.getY() - p1.getY())) * 2 * Math.PI / Panneau.LARGEUR, centre);
	}

	/**
	 * Effectue une homothétie de centre <b>centre</b> et de rapport <b>echelle</b> sur la forme <b>forme</b>.<br>
	 * @param forme
	 * @param centre
	 * @param echelle
	 */
	public static void agrandir(IForme forme, Point2D centre, double echelle)
	{
		forme.agrandir(echelle, centre);
	}

	/**
	 * Permet de passer d'un mode d'édition à un autre pour la forme sélectionnée de <b>pan</b>. <br>
	 * @param pan
	 */
	public static void switchMode(Panneau pan)
	{
		pan.getFormeSelectionnee().setModeEditionPoints(!pan.estModeEditionPoints());
		pan.repaint();
		Action.activerAction(Action.DEPLACER);
	}

}
