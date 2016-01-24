package Actions;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import GUI.Panneau;
import Geometrie.IForme;

/**
 * Action qui permet d'effectuer une capture d'écran (capture le contenu d'un panneau dans une image)
 * @author Massinissa
 * @author Armand
 */
public class CaptureEcran implements ActionListener
{
	private Panneau pan;

	/**
	 * @param pan, le panneau que l'on souhaite capturer sur l'image
	 */
	public CaptureEcran(Panneau pan)
	{
		this.pan =pan;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		this.capturePanneau();
	}

	/**
	 * Sauvegarde le dessin des formes contenu dans le panneau dans une image (format : <b>png</b>) <br>
	 * Une boite de dialogue s'affiche pour choisir où enregistrer l'image.
	 */
	public void capturePanneau()
	{
		// Récupération des pixels de l'image
		BufferedImage bufferedImage = new BufferedImage(Panneau.LARGEUR, Panneau.HAUTEUR, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		// On dessine uniquement les formes du panneau sur fond blanc
		g2.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		for(IForme forme : pan.getFormesEditables())
			forme.dessinerContour(g2);
		for(IForme forme : pan.getFormesEditables())
			forme.dessinerInterieur(g2);
		
		// On essaie d'enregistrer l'image
		try
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("Images png", "png"));
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				ImageIO.write(bufferedImage, "png", new File(chooser.getSelectedFile() + ".png"));
				System.out.println("Image enregistrée");
			}
		}

		catch (Exception e)
		{
			System.out.println("Erreur lors de l'enregistrement de l'image...");
			e.printStackTrace();
		}
	}

}
