package jeu.uitl;


import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import jeu.jade.engine.JadeDisplay;

/**
 * Classe utilitaire agissant sur le Display.
 * @author fprovenzano
 */
public class DisplayUtilities {

	public final static void cleanDisplay(JadeDisplay display) {
		// ******* MANIP BIZARRE!! POUR NE PAS PERDRE LE FRAMERATE *******
		Graphics2D g2d = (Graphics2D) display.getDoubleBufferedGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.drawString("", 0, 0);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);

	}

	public final static void cleanTransparence(JadeDisplay display) {
		Graphics2D g2d = (Graphics2D) display.getDoubleBufferedGraphics();
		g2d
				.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
	}
}
