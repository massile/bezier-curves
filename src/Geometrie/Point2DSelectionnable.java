package Geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Représente un Point2D éditable.
 * @author Massinissa
 * @author Armand
 */
public class Point2DSelectionnable extends Point2D implements IFormeEditable
{
	private boolean estSelectionne, modeEdition;
	private Point2D centreRot;
	public static final int RAYON = 10;					// Rayon du disque qui sera dessiné pour représenter le point sur l'écran.
	public static final int RAYON_SELECTIONNABLE = 15;	// Rayon du disque dans lequel le curseur doit se trouver pour sélectionner le point.

	/**
	 * Créé un point à l'origine.
	 */
	public Point2DSelectionnable()
	{
		this(0, 0);
	}
	/**
	 * Constructeur par recopie profonde.
	 * @param pt
	 */
	public Point2DSelectionnable(Point2DSelectionnable pt)
	{
		this(pt.getX(), pt.getY());
	}
	
	/**
	 * Créé un point de coordonnées (x,y)
	 * @param x
	 * @param y
	 */
	public Point2DSelectionnable(int x, int y)
	{
		super(x, y);
		this.estSelectionne = false;
		this.modeEdition = true;
		this.centreRot = new Point2D();
	}

	/**
	 * Retourne this
	 */
	@Override
	public ArrayList<Point2DSelectionnable> getPointsCtrl()
	{
		ArrayList<Point2DSelectionnable> pts = new ArrayList<Point2DSelectionnable>();
		pts.add(this);
		return pts;
	}
	
	/**
	 * Dessine le point.
	 * @param g
	 */
	@Override
	public void dessinerInterieur(Graphics2D g)
	{
		if (this.modeEdition)
		{
			if (this.estSelectionne)
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.ORANGE);

			g.fillOval(this.getX() - RAYON / 2, this.getY() - RAYON / 2, RAYON, RAYON);
		}
	}

	/**
	 * Sélectionne le point uniquement si le mode édition est activé
	 */
	@Override
	public void selectionner()
	{
		if (this.modeEdition)
			this.estSelectionne = true;
	}

	/**
	 * Déselectionne le point uniquement si le mode édition est activé
	 */
	@Override
	public void deselectionner()
	{
		if (this.modeEdition)
			this.estSelectionne = false;
	}

	@Override
	public void setModeEditionPoints(boolean modeEdition)
	{
		this.modeEdition = modeEdition;
	}

	@Override
	public boolean estSelectionne()
	{
		return this.estSelectionne;
	}

	@Override
	public boolean estModeEditionPoints()
	{
		return this.modeEdition;
	}

	/**
	 * @return vrai si la distance entre le point de coordonnées (x,y) et this est inférieur à RAYON_SELECTIONNABLE
	 */
	@Override
	public boolean pointInterieur(int x, int y)
	{
		int dx = x - this.getX();
		int dy = y - this.getY();
		return dx * dx + dy * dy < RAYON_SELECTIONNABLE * RAYON_SELECTIONNABLE;
	}

	/**
	 * @return this si la distance entre le point de coordonnées (x,y) et this est inférieur à RAYON_SELECTIONNABLE
	 */
	@Override
	public Point2DSelectionnable pointInterieurPointCtrl(int x, int y)
	{
		if (this.pointInterieur(x, y))
			return this;
		return null;
	}

	/**
	 * Permet de sélectionner le seul point de controle de cette forme : le point en lui-même
	 * @param pt : le point que le'on souhaite sélectionner (seul this est sélectionnable)
	 */
	@Override
	public void selectionnerPtCtrl(Point2DSelectionnable pt)
	{
		if(pt == this)
			this.selectionner();
	}

	/**
	 *  Permet de désélectionner le seul point de controle de cette forme : le point en lui-même
	 */
	@Override
	public void deselectionnerPtCtrl()
	{
		this.deselectionner();
	}

	/**
	 * @return s'il est sélectionné : le seul point de controle de cette forme (le point en lui-même)
	 * 
	 */
	@Override
	public Point2DSelectionnable getPointSelectionne()
	{
		return this.estSelectionne() ? this : null;
	}

	/**
	 * @return les centre de rotation du point.
	 */
	@Override
	public Point2D getCentreRot()
	{
		return centreRot;
	}
	
	/**
	 * (On ne dessine pas le contour du point dans ce programme, mais si un jour on souhaite le faire,
	 * la couleur du contour est disponible par cette méthode)
	 * @return la couleur noire
	 */
	@Override
	public Color getCouleurContour()
	{
		return Color.BLACK;
	}
	
	/**
	 * @return la couleur du point par défaut (ici Orange)
	 */
	@Override
	public Color getCouleurRemplissage()
	{
		return Color.ORANGE;
	}
	
	/**
	 * @return false : cette forme ne peut contenir qu'un unique point, lui même
	 */
	@Override
	public boolean peutAjouterPoints()
	{
		return false;
	}

	
	@Override
	public void combiner(IFormeEditable formeSuivante)
	{
	}
	
	@Override
	public void deplacerCentreRot(int x, int y)
	{
		this.centreRot.set(x, y);
	}

}
