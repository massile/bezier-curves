package Geometrie;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Classe représentant une courbe de Bézier
 * @author Massinissa
 * @author Armand
 */
public class CourbeBezier extends FormeEditable
{
	public static final double RESOLUTION = 0.01; 	// Pas de calcul pour l'algorithme de Casteljau
	
	/**
	 * Créé une courbe de Bézier centré au point de coordonnées (x,y) <br>
	 * La courbe par défaut est un segment
	 * @param x
	 * @param y
	 */
	public CourbeBezier(int x, int y)
	{
		this();
		this.getPointsCtrl().add(new Point2DSelectionnable(200, 200));
		this.getPointsCtrl().add(new Point2DSelectionnable(100, 100));		
		this.set(x, y);
		
		this.getCentreRot().set(getCentre().getX(), getCentre().getY());
	}

	/**
	 * @return true : pour une courbe de Bézier, on peut ajouter des points
	 */
	@Override
	public boolean peutAjouterPoints()
	{
		return true;
	}

	/**
	 * Créé une courbe de Bézier vide.
	 */
	public CourbeBezier()
	{
		super();
	}

	/**
	 * Retourne le point obtenu en appliquant l'algorithme de casltejau sur l'ensemble des points de contrôle de this
	 * @param t : paramètre de calcul : t = 0 : premier point de la courbe de bézier, t = 1 : dernier point
	 * @return
	 */
	private Point2D deCasteljau(double t)
	{
		ArrayList<Point2D> barycentres = new ArrayList<Point2D>();
		ArrayList<Point2D> tmp = new ArrayList<Point2D>();
		barycentres.addAll(getPointsCtrl());
		
		do
		{
			tmp.clear();
			
			for(int i=0; i<barycentres.size()-1; i++)
				tmp.add(baryPoint2d(barycentres.get(i), barycentres.get(i+1), t));
			
			barycentres.clear();
			barycentres.addAll(tmp);
			
		} while(barycentres.size() > 1);
		
		return barycentres.get(0);
	}
	
	/**
	 * Retourne le barycentre de paramètre t des points pt1 et pt2
	 * @param pt1
	 * @param pt2
	 * @param t : t = 0 on situe sur le point pt1, t = 1 on se situe sur le point pt2
	 * @return
	 */
	private Point2D baryPoint2d(Point2D pt1, Point2D pt2, double t)
	{
		Point2D barycentre = new Point2D();
	
		double x = (1-t)*pt1.getX() + t*pt2.getX();
		double y = (1-t)*pt1.getY() + t*pt2.getY();
		barycentre.setX((int) x);
		barycentre.setY((int) y);
		
		return barycentre;
	}

	/**
	 * Permet de générer le contour de la courbe de Bézier. <br>
	 */
	public void genererCourbe()
	{

		this.getPts().clear();
		this.getPts().add(this.getPointsCtrl().get(0));
		for (int i = 1; i <= 1 / RESOLUTION; i++)
		{
			Point2D pt = this.deCasteljau(i * RESOLUTION);
			this.getPts().add(pt);
		}

	}
	
	@Override
	public void dessinerContour(Graphics2D g)
	{
		this.genererCourbe();
		g.setColor(this.getCouleurContour());
		g.setStroke(new BasicStroke(1));
		super.dessinerContour(g);
	}

	@Override
	public void dessinerFeatures(Graphics2D g)
	{
		super.dessinerFeatures(g);
		// On ajoute un gros point à l'origine de la courbe. 
		// Ainsi, il sera plus facile de déterminer quelle extrémité de la courbe sera ajoutée à la suivante.
		g.setColor(getCouleurContour());
		g.fillOval(getPointsCtrl().get(0).getX()-5, getPointsCtrl().get(0).getY()-5,12,12);
	}

}
