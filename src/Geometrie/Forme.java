package Geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

/**
 * Implémente certaines méthodes de IForme
 * @author Massinissa
 * @author Armand
 */
public abstract class Forme implements IForme
{
	private IForme formeSuivante, formePrecedente;		// Pour la liste chainée de forme
														// la forme suivante est nécessaire pour déterminer si elle est 'fermée'
														// la forme précédente est nécessaire pour 'ouvrir' la forme (si elle est 'fermée') quand on supprime un élément de la chaine
														
	private Color couleurContour, couleurRemplissage;
	private ArrayList<Point2D> pts;	// Point du contour de la forme ACTUELLE (et non de toute la liste chainée)
	private int x, y;

	/**
	 * Créé un forme vide : <br>
	 * 	la couleur de remplissage par défaut est gris <br>
	 *  la couleur du contour par défaut est noir
	 */
	public Forme()
	{
		this.formeSuivante = null;
		this.formePrecedente = null;
		this.couleurContour = Color.BLACK;
		this.couleurRemplissage = Color.GRAY;
		this.pts = new ArrayList<Point2D>();
	}

	@Override
	public void dessinerInterieur(Graphics2D g)
	{
		if(estFermee())
		{
			Polygon p = new Polygon();
			ArrayList<Point2D> contour = this.getContour();	// On récupère tous les points de la courbe

			for (Point2D pt : contour)
				p.addPoint(pt.getX(), pt.getY());

			g.setColor(this.getCouleurRemplissage());
			g.fillPolygon(p);	// Fonction qui effectue le remplissage à l'aide de l'ensemble des points fournis.
		}
	}

	@Override
	public void dessinerContour(Graphics2D g)
	{
		g.setColor(this.getCouleurContour());
		for (int i = 1; i <getPts().size(); i++)
			g.drawLine(this.getPts().get(i - 1).getX(), this.getPts().get(i - 1).getY(), this.getPts().get(i).getX(), this.getPts().get(i).getY());
	}

	@Override
	public ArrayList<Point2D> getContour()
	{
		ArrayList<Point2D> contour = new ArrayList<Point2D>();

		IForme formeActuelle = this;
		contour.addAll(formeActuelle.getPts());

		IForme formeParcourt = this.getFormeSuivante();

		while (formeParcourt != null && formeParcourt != formeActuelle)
		{
			contour.addAll(formeParcourt.getPts());
			formeParcourt = formeParcourt.getFormeSuivante();
		}

		return contour;
	}

	@Override
	public Color getCouleurContour()
	{
		return this.couleurContour;
	}

	public Color getCouleurRemplissage()
	{
		return this.couleurRemplissage;
	}

	@Override
	public IForme getFormeSuivante()
	{
		return formeSuivante;
	}

	@Override
	public IForme getFormePrecedente()
	{
		return formePrecedente;
	}

	@Override
	public void setFormeSuivante(IForme forme)
	{
		formeSuivante = forme;
	}

	@Override
	public void setFormePrecedente(IForme forme)
	{
		formePrecedente = forme;
	}

	@Override
	public void dereference()
	{
		if(formePrecedente != null)
			this.getFormePrecedente().setFormeSuivante(null);
		if(formeSuivante != null)
			this.getFormeSuivante().setFormePrecedente(null);
	}

	@Override
	public void setX(int x)
	{
		this.translater(x - this.getCentre().getX(), 0);
		this.x = x;
	}

	@Override
	public void setY(int y)
	{
		this.translater(0, y - this.getCentre().getY());
		this.y = y;
	}

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
	public ArrayList<Point2D> getPts()
	{
		return pts;
	}

	@Override
	public boolean estFermee()
	{
		IForme formeActuelle = this;	// L'origine du parcours est complètement arbitraire (on choisit this)
		IForme formeParcourt = this.getFormeSuivante();

		// On parcourt les courbes suivantes tant que :
		// 1. on ne rencontre une extrémité (null)
		// 2. on ne retombe sur la courbe de départ (tour complet)
		while (formeParcourt != null && formeParcourt != formeActuelle)
			formeParcourt = formeParcourt.getFormeSuivante();

		return formeParcourt != null;	// La forme est fermée si on ne rencontre pas d'extrémité
	}
}
