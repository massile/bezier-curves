package Main;

import GUI.BarreLaterale;
import GUI.Fenetre;
import GUI.Menu;
import GUI.Panneau;

/**
 * Classe contenant la méthode main
 * @author Massinissa
 * @author Armand
 */
public class Main
{
	public static void main(String[] args)
	{
		// Créé une fenêtre avec une barre latérale et un menu
		Panneau pan = new Panneau();
		Menu menu = new Menu(pan);
		BarreLaterale barreLaterale = new BarreLaterale(pan);
		new Fenetre(pan, barreLaterale, menu);
	}
}
