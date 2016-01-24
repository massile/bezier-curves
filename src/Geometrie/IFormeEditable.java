package Geometrie;

import java.util.ArrayList;

/**
 * Interface représentant une forme éditable : <br>
 * <ul>
 * <li>Sélection est déselection de la forme possible.</li>
 * <li>Edition des points de contôle (sélection, déplacement etc..) possible. </li>
 * </ul>
 * @author Massinissa
 * @author Armand
 */
public interface IFormeEditable extends IForme
{

	/**
	 * @return l'ensemble des points de contôle de la forme.
	 */
	public ArrayList<Point2DSelectionnable> getPointsCtrl();

	/**
	 * Permet de sélectionner this
	 */
	public void selectionner();

	/**
	 * Permet de déselectionner this
	 */
	public void deselectionner();

	/**
	 * @return true si this est sélectionné
	 */
	public boolean estSelectionne();

	/**
	 * Permet de sélectionner le point de contrôle <b>pt</b>
	 * @param pt
	 */
	public void selectionnerPtCtrl(Point2DSelectionnable pt);

	/**
	 * Désélectionne tous les points de controle de la forme
	 */
	public void deselectionnerPtCtrl();
	
	/**
	 * @return le point de contrôle sélectionné sur la forme
	 */
	public Point2DSelectionnable getPointSelectionne();


	/**
	 * @return true si la forme est en mode d'édition de points
	 */
	public boolean estModeEditionPoints();

	/**
	 * Permet d'activer un des 2 modes d'édition : <br>
	 * <ul>
	 * <li> mode d'édition de points : on peut éditer les points de contrôle de la forme un par un. </li>
	 * <li> mode d'édition de formes : on édite la totalité de la forme. </li>
	 * </ul>
	 * @param modeEditionPoints : true, on passe mode d'édition de points, false, mode d'édition de formes 
	 */
	public void setModeEditionPoints(boolean modeEditionPoints);

	/**
	 * Déplace le centre de rotation aux coordonnées (x,y)
	 * @param x
	 * @param y
	 */
	public void deplacerCentreRot(int x, int y);

	/**
	 * @return le centre de rotation de la forme
	 */
	public Point2D getCentreRot();
	
	/**
	 * @param x
	 * @param y
	 * @return true si le point de coordonnées (x,y) se trouve dans la bounding box de la courbe
	 */
	public boolean pointInterieur(int x, int y);


	/**
	 * @param x
	 * @param y
	 * @return le point de controle de la forme sur lequel se trouve le point de coordonnées (x,y)
	 */
	public Point2DSelectionnable pointInterieurPointCtrl(int x, int y);

	/**
	 * @return true si on souhaite autoriser l'ajout de points à la forme. <br>
	 * par exemple : dans le case d'un carré, le nombre de points doit être fixé <br>
	 * dans le cas d'une courbe de bézier, l'ajout est autorisé
	 */
	public boolean peutAjouterPoints();
	
	/**
	 * Relie this à <b>formeSuivante</b> : <br>
	 * Ajoute le premier point de la <b>formeSuivante</b> à l'extrémité (à la fin) de this <br>
	 * On active ensuite l'action DEPLACER
	 * @param formeSuivante la forme avec laquelle on souhaite relier this
	 */
	public void combiner(IFormeEditable formeSuivante);
}
