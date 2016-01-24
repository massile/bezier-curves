package Geometrie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import Actions.Action;

/**
 * Implémente certaines méthodes relatives à l'édition de l'interface IFormeEditable et de la classe Forme.
 * @author Massinissa
 * @author Armand
 */
public abstract class FormeEditable extends Forme implements IFormeEditable
{
	public static final int PADDING = 10;

	private ArrayList<Point2DSelectionnable> ptsCtrl;
	protected boolean modeEditionPoints;

	protected boolean estSelectionne;
	private Point2DSelectionnable ptSelectionne;
	
	private Point2D centreRot;

	/**
	 * Créé un forme vide sélectionnée par défaut.
	 */
	public FormeEditable()
	{
		centreRot = new Point2D();
		this.ptsCtrl = new ArrayList<Point2DSelectionnable>();
		this.modeEditionPoints = false;
		this.estSelectionne = true;
	}
	
	@Override
	public Point2D getCentreRot()
	{
		return centreRot;
	}

	@Override
	public void agrandir(double echelle, Point2D centre)
	{
		for (Point2DSelectionnable pt : this.ptsCtrl)
			pt.agrandir(echelle, centre);
		if (this.getFormeSuivante() != null)
			this.getPointsCtrl().get(this.getPointsCtrl().size() - 1).agrandir(1 / echelle, centre);
		deplacerCentreRot(getCentre().getX(), getCentre().getY());
	}
	
	@Override
	public void deplacerCentreRot(int x, int y)
	{
		this.centreRot.set(x, y);
	}

	@Override
	public void dessinerFeatures(Graphics2D g)
	{
		// En mode édition de points : ajout de segments bleus en pointilliés entre les points de contrôle.
		if (this.estModeEditionPoints())
		{
			this.getPointsCtrl().get(0).dessinerInterieur(g);
			for (int i = 1; i < this.getPointsCtrl().size(); i++)
			{
				Point2DSelectionnable pt1 = this.getPointsCtrl().get(i - 1);
				Point2DSelectionnable pt2 = this.getPointsCtrl().get(i);

				g.setColor(Color.BLUE);
				g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{ 10.0f }, 0.0f));
				g.drawLine(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
				pt2.dessinerInterieur(g);
			}
		}

		// En mode édition de courbe : dessin d'un rectangle bleu en pointillé entourant la forme lorsqu'on la sélectionne.
		else if (this.estSelectionne)
		{
			g.setColor(Color.BLUE);
			g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]
			{ 10.0f }, 0.0f));
			g.drawRect(this.minX() - PADDING, this.minY() - PADDING, this.maxX() - this.minX() + 2 * PADDING, this.maxY() - this.minY() + 2 * PADDING);
		}
		
		// Dessin d'une croix en violet au niveau du centre de rotation
		if(Action.getActionCourante() == Action.ROTATION)
		{
			g.setColor(Color.MAGENTA);
			int x = getCentreRot().getX();
			int y = getCentreRot().getY();
			g.drawLine(x, y-5, x, y+5);
			g.drawLine(x-5, y, x+5, y);
		}
	}

	@Override
	public Point2D getCentre()
	{
		Point2D centre = new Point2D();
		for (Point2DSelectionnable pt : this.getPointsCtrl())
			centre.set(centre.getX() + pt.getX(), centre.getY() + pt.getY());
		centre.set(centre.getX() / this.getPointsCtrl().size(), centre.getY() / this.getPointsCtrl().size());
		return centre;
	}

	@Override
	public void translater(int x, int y)
	{
		for (Point2DSelectionnable pt : this.ptsCtrl)
			pt.translater(x, y);
	}

	@Override
	public void tourner(double angle, Point2D centre)
	{
		for (Point2DSelectionnable pt : this.ptsCtrl)
			pt.tourner(angle, centre);
	}

	@Override
	public ArrayList<Point2DSelectionnable> getPointsCtrl()
	{
		return this.ptsCtrl;
	}

	@Override
	public void setModeEditionPoints(boolean modeEditionPoints)
	{
		for (Point2DSelectionnable pt : this.ptsCtrl)
			pt.setModeEditionPoints(modeEditionPoints);

		this.modeEditionPoints = modeEditionPoints;
	}

	@Override
	public void selectionner()
	{
		this.estSelectionne = true;
	}

	@Override
	public void deselectionner()
	{
		this.estSelectionne = false;
	}

	@Override
	public boolean estSelectionne()
	{
		return this.estSelectionne;
	}

	@Override
	public Point2DSelectionnable pointInterieurPointCtrl(int x, int y)
	{
		Point2DSelectionnable point = null;
		boolean trouve = false;
		int i = 0;
		while (i < this.getPointsCtrl().size() && !trouve)
		{
			trouve = this.getPointsCtrl().get(i).pointInterieur(x, y);
			i++;
		}

		if (trouve)
			point = this.getPointsCtrl().get(i - 1);

		return point;
	}

	@Override
	public void selectionnerPtCtrl(Point2DSelectionnable pt)
	{
		if (this.ptSelectionne != null)
			this.ptSelectionne.deselectionner();
		pt.selectionner();
		this.ptSelectionne = pt;
	}

	@Override
	public void deselectionnerPtCtrl()
	{
		if (this.ptSelectionne != null)
		{
			this.ptSelectionne.deselectionner();
			this.ptSelectionne = null;
		}
	}

	@Override
	public boolean estModeEditionPoints()
	{
		return this.modeEditionPoints;
	}

	
	@Override
	public Point2DSelectionnable getPointSelectionne()
	{
		return this.ptSelectionne;
	}

	@Override
	public void combiner(IFormeEditable formeSuivante)
	{
		this.setFormeSuivante(formeSuivante);
		if (formeSuivante != null)
		{
			this.getFormeSuivante().setFormePrecedente(this);
			this.getPointsCtrl().add(formeSuivante.getPointsCtrl().get(0));
		}
		Action.activerAction(Action.DEPLACER);
	}

	/**
	 * @return le plus grand abscisse des points de contrôle
	 */
	private int maxX()
	{
		int maxX = this.getPts().get(0).getX();
		for (int i = 0; i < this.getPts().size(); i++)
			if (this.getPts().get(i).getX() > maxX)
				maxX = this.getPts().get(i).getX();
		return maxX;
	}

	/**
	 * @return la plus grand ordonnée des points de contrôle
	 */
	private int maxY()
	{
		int maxY = this.getPts().get(0).getY();
		for (int i = 0; i < this.getPts().size(); i++)
			if (this.getPts().get(i).getY() > maxY)
				maxY = this.getPts().get(i).getY();
		return maxY;
	}


	/**
	 * @return le plus petit abscisse des points de contrôle
	 */
	private int minX()
	{
		int minX = this.getPts().get(0).getX();
		for (int i = 0; i < this.getPts().size(); i++)
			if (this.getPts().get(i).getX() < minX)
				minX = this.getPts().get(i).getX();
		return minX;
	}

	/**
	 * @return la plus petite ordonnée des points de contrôle
	 */
	private int minY()
	{
		int minY = this.getPts().get(0).getY();
		for (int i = 0; i < this.getPts().size(); i++)
			if (this.getPts().get(i).getY() < minY)
				minY = this.getPts().get(i).getY();
		return minY;
	}

	@Override
	public boolean pointInterieur(int x, int y)
	{
		return x > this.minX() && x < this.maxX() && y < this.maxY() && y > this.minY();
	}

}
