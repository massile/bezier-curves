package Entrees;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.SwingUtilities;
import Actions.Action;
import GUI.Panneau;
import Geometrie.IFormeEditable;
import Geometrie.Point2D;

/**
 * Représente la souris qui agira sur les formes d'un panneau. <br>
 * Voici la liste des actions réalisables à la souris : 
 * <ul>
 * <li> SI L'ACTION DEPLACER EST ACTIVE :
 * <ul> 
 * 		<li>déplacer la forme entière : <br>
 * 			lorsque le mode édition de points est inactif, clique gauche enfoncé sur une forme sélectionnée.
 * 		</li>
 * 		
 * 		<li>déplacer un point de la forme : <br>
 * 			lorsque en mode édition de points est actif, clique gauche enfoncé sur un point de la forme sélectionnée.
 * 		</li> 		
 * </ul>
 * </li>
 * 
 * <li>  POUR N'IMPORTE QUELLE ACTION CHOISIE :
 * <ul>
 * 		<li>sélectionner une forme : <br>
 * 			clique droit sur une forme, lorsque le mode édition de points est inactif.
 * 		</li>
 * 
 * 		<li>sélectionner un point de controle de la forme : <br>
 * 			clique droit sur un point de la forme sélectionnée, lorsque le mode édition de points est actif.
 * 		</li>
 * </ul>
 * 
 * </li>
 * 
 * <li> SI L'ACTION ROTATION EST ACTIVE :
 * <ul>
 * 		<li>rotation de la forme sélectionnée: <br>
 * 			clique gauche enfoncé sur un endroit du panneau et déplacer le curseur : <br>
 * 			de gauche à droite, ou de bas en haut pour le sens trigonométrique.
 * 		</li>
 * 		<li>déplacer le centre de rotation de la forme sélectionnée: <br>
 * 			clique gauche sur un endroit du panneau.
 * 		</li>
 * </ul>
 * </li>
 * 
 * <li> SI L'ACTION AJOUTER EST ACTIVE :
 * <ul>
 * 		<li>ajouter une nouvelle forme sur le panneau : <br>
 * 			clique gauche sur un endroit du panneau lorsque le mode édition de points est inactif. <br>
 * 		</li>
 * 
 * 		<li>ajouter un nouveau point sur la forme sélectionnée : <br>
 * 			clique gauche sur un endroit du panneau lorsque le mode édition de points est actif. <br>
 * 		</li>
 * </ul>
 * </li>
 * 
 * <li> SI L'ACTION SUPPRIMER EST ACTIVE :
 * <ul>
 * 		<li>supprimer une forme sur le panneau : <br>
 * 			clique gauche pour supprimer la forme sélectionnée lorsque le mode édition de points est inactif. <br>
 * 		</li>
 * 
 * 		<li>supprimer un point sur la forme sélectionnée : <br>
 * 			clique gauche pour supprimer le point sélectionné de la courbe lorsque le mode édition de points est actif. <br>
 * 		</li>
 * </ul>
 * </li>
 * 
 * <li> /!\ SI ON SOUHAITE COMBINER DEUX COURBES /!\
 * <ul>
 * 		<li>combiner deux forme sur le panneau : <br>
 * 			sélectionner une forme au préalable,
 * 			activer l'action combiner,
 * 			et sélectionner la forme qui doit se trouver à l'extrémité de celle ci.
 * 		</li>
 * </ul>
 * </li>
 * 
 * <li> ZOOMER / DEZOOMER
 * <ul>
 * 		<li>zommer sur un élément du panneau : <br>
 * 			placer le curseur à proximité de la forme,
 * 			utiliser la roulette de la souris pour zoomer/dézommer,
 * 		</li>
 * </ul>
 * </li>
 * 
 * </ul>
 * @author Massinissa
 * @author Armand
 */
public class Souris extends MouseAdapter
{
	private Panneau pan;
	
	// Ces variables sont utilisées pour la mise à jour continues de la courbe
	// lors d'une rotation. Au fur et à mesure que l'on avancera le curseur
	// la courbe tournera autour de son centre de rotation
	private Point2D anciennePos;	// Position de la souris à l'image d'avant
	private Point2D nouvPos;		// Position actuelle de la souris

	/**
	 * Créé une souris qui agira sur le panneau d'édition pan.
	 * @param pan
	 */
	public Souris(Panneau pan)
	{
		this.pan = pan;
		this.anciennePos = new Point2D();
		this.nouvPos = new Point2D();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		this.nouvPos = new Point2D(e.getX(), e.getY());

		switch (Action.getActionCourante())
		{
			case Action.DEPLACER:
				if(SwingUtilities.isLeftMouseButton(e))
					Action.deplacer(this.pan.getFormeSelectionnee(), e.getX(), e.getY());
				break;
			case Action.ROTATION:
				if(SwingUtilities.isLeftMouseButton(e))
					Action.rotation(this.pan.getFormeSelectionnee().getCentreRot(), this.pan.getFormeSelectionnee(), this.nouvPos, this.anciennePos);
				break;
		}

		this.pan.repaint();
		
		this.anciennePos = new Point2D(this.nouvPos);
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		this.nouvPos = new Point2D(e.getX(), e.getY());
		pan.repaint();
		this.anciennePos = new Point2D(this.nouvPos);		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		int nbClicks = e.getWheelRotation(); // La vitesse à laquelle on a tourné la roulette

		for (IFormeEditable forme : this.pan.getFormesEditables())
			Action.agrandir(forme, new Point2D(e.getX(), e.getY()), 1 + 0.1 * nbClicks);
		
		this.pan.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		IFormeEditable courbePrecSelect = null;
		if (this.pan.getFormeSelectionnee() != null)
			courbePrecSelect = this.pan.getFormeSelectionnee();
		
		if(SwingUtilities.isRightMouseButton(e))
			Action.selectionner(this.pan, e.getX(), e.getY());
		
		switch (Action.getActionCourante())
		{
			case Action.AJOUTER:
				if(SwingUtilities.isLeftMouseButton(e))
					Action.ajouter(this.pan, e.getX(), e.getY());
				break;
			case Action.COMBINER:
				if(SwingUtilities.isRightMouseButton(e))
					Action.combiner(courbePrecSelect, this.pan.getFormeSelectionnee());
				break;
			case Action.SUPPRIMER:
				if(SwingUtilities.isLeftMouseButton(e))
					Action.supprimer(pan);
				break;
			case Action.ROTATION:
				if(SwingUtilities.isLeftMouseButton(e))
					Action.deplacer(this.pan.getFormeSelectionnee().getCentreRot(), e.getX(), e.getY());
				break;
		}

		this.pan.repaint();
	}
}
