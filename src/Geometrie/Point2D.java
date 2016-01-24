package Geometrie;

import java.awt.Graphics2D;

/**
 * Implémente un Point en 2D simple
 * @author Massinissa
 * @author Armand
 *
 */
public class Point2D extends Forme
{
	private int x, y;

	/**
	 * Créé un point au niveau de l'origine
	 */
	public Point2D()
	{
		this(0, 0);
	}
	
	/**
	 * Constructeur par recopie profonde.
	 * @param pt
	 */
	public Point2D(Point2D pt)
	{
		this.set(pt.getX(), pt.getY());
	}

	/**
	 * Créé un point de coordonnées (x,y)
	 * @param x
	 * @param y
	 */
	public Point2D(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public void setX(int x)
	{
		this.x = x;
	}

	@Override
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * Déplace le point aux coordonnées (x,y).
	 * @param x
	 * @param y
	 */
	@Override
	public void set(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}

	@Override
	public int getX()
	{
		return this.x;
	}

	@Override
	public int getY()
	{
		return this.y;
	}

	@Override
	public Point2D getCentre()
	{
		return this;
	}

	@Override
	public void translater(int x, int y)
	{
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
	}

	/**
	 * Effectue une rotation d'angle <b>angle</b> et de centre <b>centre</b>
	 * @param angle : l'angle de rotation en radian
	 * @param centre : le centre de rotation
	 */
	@Override
	public void tourner(double angle, Point2D centre)
	{
		int x = this.getX() - centre.getX();
		int y = this.getY() - centre.getY();
		
		this.setX((int) Math.round(x*Math.cos(angle) - y*Math.sin(angle) + centre.getX()));
		this.setY((int) Math.round(x*Math.sin(angle) + y*Math.cos(angle) + centre.getY()));
	}

	@Override
	public void agrandir(double echelle, Point2D centre)
	{
		int x = this.getX() - centre.getX();
		int y = this.getY() - centre.getY();
		
		this.setX((int)  Math.round(x*echelle + centre.getX()));
		this.setY((int)  Math.round(y*echelle + centre.getY()));
	}
	
	@Override
	public String toString()
	{
		return "("+getX()+", "+getY()+")";
	}

	@Override
	public void dessinerFeatures(Graphics2D g)
	{
	}

}
