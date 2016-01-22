package jeu;

import java.awt.Dimension;

import jeu.jade.loader.JadeWindowContainer;

/**
 * Classe d'amorce du lancement en application
 */
public class WindowRun {
		
	public static final void main(String argv[]) {
		
		// Mode fenetre
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		int hauteur = (int) tailleEcran.getHeight();
		int largeur = (int) tailleEcran.getWidth();
		JadeWindowContainer root = new JadeWindowContainer(largeur, hauteur, "",
				"jeu.scene.IntroScene");		
		root.setUndecorated(true);
		root.setAlwaysOnTop(true);		
		root.start();
	}
}