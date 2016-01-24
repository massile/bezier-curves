package Geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Représente une forme géométrique de base : <br>
 * <ul>
 * <li> mémorise la position de la forme à  l'aide de son centre. </li>
 * <li> les 3 transformations du plan sont possible. </li>
 * <li> une fonction pour le dessin sur le Panneau. </li>
 * </ul>
 * @author Massinissa
 * @author Armand
 */
public interface IForme
{
	/**
	 * Déplace la forme de tel sorte que l'isobarycentre des points de contrôle ait pour abscisse x
	 * @param x
	 */
	public void setX(int x);

	/**
	 * Déplace la forme de tel sorte que l'isobarycentre des points de contrôle ait pour ordonnée y
	 * @param y
	 */
	public void setY(int y);

	/**
	 * Déplace la forme de tel sorte que l'isobarycentre des points de contrôle ait pour coordonnées (x,y)
	 * @param x
	 */
	public void set(int x, int y);

	/**
	 * @return l'abcsisse de l'isobarycentre des points de contrôle 
	 */
	public int getX();

	/**
	 * @return l'ordonnée de l'isobarycentre des points de contrôle 
	 */
	public int getY();

	/**
	 * @return l'ensemble des points du contour de la forme
	 */
	public ArrayList<Point2D> getPts();
	
	/**
	 * @return le contour de la forme obtenue en concaténant 'this' et les formes qui la précédent et la suivent
	 */
	public ArrayList<Point2D> getContour();
	
	/**
	 * @return la couleur du contour
	 */
	public Color getCouleurContour();
	
	/**
	 * @return la couleur de remplissage
	 */
	public Color getCouleurRemplissage();
	
	/**
	 * @return l'isobarycentre des points de contrôle de la forme.
	 */
	public Point2D getCentre();

	/**
	 * Dessine le contour de la forme
	 * @param g
	 */
	public void dessinerContour(Graphics2D g);

	/**
	 * Dessine l'intérieur de la forme obtenue en concaténant 'this' et les formes qui la précédent et la suivent (si elle est fermée)
	 * @param g
	 */
	public void dessinerInterieur(Graphics2D g);


	/**
	 * Dessine les éléments visuels qui améliorent la visibilité lors de l'édition
	 * @param g
	 */
	public void dessinerFeatures(Graphics2D g);

	/**
	 * Effectue une translation de vecteur (x,y) sur l'ensemble des points de contrôle de la forme.
	 * @param x
	 * @param y
	 */
	public void translater(int x, int y);

	/**
	 * Effectue une rotation de centre <b>centre</b> et d'angle <b>angle</b> sur l'ensemble des points de contrôle de la forme.
	 * @param angle
	 * @param centre
	 */
	public void tourner(double angle, Point2D centre);

	/**
	 * Applique une homothétie de centre <b>centre</b> et de coefficient <b>echelle</b>
	 * @param echelle : rapport d'agrandissement
	 * @param centre : le centre de l'homothétie
	 */
	public void agrandir(double echelle, Point2D centre);
	
	/**
	 * @return la forme qui suit directement this
	 */
	public IForme getFormeSuivante();

	/**
	 * @return la forme qui précède directement this
	 */
	public IForme getFormePrecedente();

	/**
	 * définit que la forme <b>forme</b> est à la suite de this
	 * @param forme
	 */
	public void setFormeSuivante(IForme forme);

	/**
 	 * définit que la forme <b>forme</b> est avant this
	 * @param forme
	 */
	public void setFormePrecedente(IForme forme);

	/**
	 * Permet de déréfencer correctement les formes suivantes et précédentes de this.
	 */
	public void dereference();

	/**
	 * @return vrai si et seulement si la forme est fermée (extrémités du contour reliées)
	 */
	public boolean estFermee();

}
