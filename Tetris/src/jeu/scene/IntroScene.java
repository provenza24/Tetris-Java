package jeu.scene;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import jeu.jade.engine.JadeDisplay;
import jeu.jade.engine.JadeImageMosaicBackground;
import jeu.jade.engine.JadeKeyListener;
import jeu.jade.engine.JadeLoadingInterface;
import jeu.jade.engine.JadeMath;
import jeu.jade.engine.JadeMediaLoader;
import jeu.jade.engine.JadeScene;
import jeu.jade.engine.JadeSpriteLayer;
import jeu.jade.loader.JadeContainer;
import jeu.jade.loader.JadeLoader;
import jeu.uitl.DisplayUtilities;

/**
 * Scene de presentation du jeu.
 * 
 * @author fprovenzano
 */
public class IntroScene extends JadeKeyListener implements JadeLoader,
		JadeScene {

	/** FrameRate souhaite */
	private static final int FPS = -1;

	/** container d'affichage */
	private JadeContainer container;

	/** moteur et surface d'affichage */
	private static JadeDisplay display;

	/** fond d'ecran */
	private static JadeImageMosaicBackground background;

	/** layer de sprites */
	private static JadeSpriteLayer spriteLayer;

	/** La Font de ce qui est ecrit a l'ecran */
	private static Font dialogFont;

	/** Police de caracteres pour le debug */
	private static final Font debugFont = new Font("Dialog ", Font.BOLD, 10);

	/**
	 * chaine maj toutes les secondes avec le nombre de FPS reel et la charge
	 * CPU
	 */
	private static String meterString = "?? FPS ~";

	/** variables de calcul du FPS et du % CPU - dernier temps mesure en ms */
	private static long lastTime = 0;

	/** nombre de boucles executees */
	private static int elapsedFrames = 0;

	/** defini l'etat de la touche ENTER */
	private static boolean enter;

	/** defini l'etat de la touche ESCAPE */
	private static boolean escape;

	/** Variables utilisees pour le fondu du texte */
	private static long finAttente, debAttente;

	/** Delai d'attente pour le changement de la transparence du fondu */
	private static final long DELAI_ATTENTE = 70;

	/** Delai d'attente pour reafficher le taux de rafraichissement */
	private static final long DELAI_RATE = 300;

	/** Coefficient de transparence */
	private static float alpha = 0.0f;

	Image imageMario;

	/**
	 * Constructeur par defaut.
	 */
	public IntroScene() {
	}

	/**
	 * Gestion de l'appui sur une touche clavier.
	 */
	public final void onKeyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// sort si appui de la touche echappe
			escape = false;
		}

		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			// On relache la touche ENTREE
			enter = false;
		}
	}

	/**
	 * Gestion du relachement des touches du clavier.
	 */
	public final void onKeyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			// On appuie sur la touche ENTREE
			enter = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// sort si appui de la touche echappe
			escape = true;
		}
	}

	/**
	 * Gestion des evenements clavier.<br>
	 * Touche "ENTER" : commencer le jeu<br>
	 * Touche "ESC" : quitter le jeu
	 */
	private final void manageKeys() {

		if (enter) {
			// Arreter la scene courante
			display.stop();
			end();
			display.start(new TetrisScene(display));

			// Retour a la scene courante
			display.getBack();
			// Redefinir la transparence pour le fondu du texte
			alpha = 0.0f;
			// Recupere l'accroche clavier
			display.setKeyListener(this);
		}

		if (escape) {
			container.exit();
		}
	}

	/**
	 * Relache les touches du clavier.
	 */
	private final void releaseAllKeys() {
		enter = false;
		escape = false;
	}

	/**
	 * methode de chargement et de lancement de jeu
	 */
	public final void loadAndRun(JadeContainer container) {

		// Cree le container
		this.container = container;
		// Cree moteur d'affichage
		display = new JadeDisplay(container);

		// creer interface de chargement
		JadeLoadingInterface jli = JadeLoadingInterface
				.getLoadingInterface(display);

		// charge fond d'ecran
		jli.setMessage("Loading background..");
		jli.initializeProgress(100);
		JadeMediaLoader.setPath("resources/images/");

		background = new JadeImageMosaicBackground(JadeMediaLoader
				.loadImage("blackBack.png"));

		// creer layer de sprites
		// un fond d'ecran en arriere plan
		spriteLayer = new JadeSpriteLayer(display, background, null);
		// efface toute la surface du layer et affiche tous les sprites
		spriteLayer.setDisplayMode(JadeSpriteLayer.ALL, JadeSpriteLayer.ALL);

		JadeMediaLoader.setPath("resources/fonts/");
		dialogFont = JadeMediaLoader.loadFont("SMB.ttf");
		dialogFont = dialogFont.deriveFont((float) 38.0);

		JadeMediaLoader.setPath("resources/images/");
		imageMario = JadeMediaLoader.loadImage("logoTetris.gif");

		jli.setProgress(100);

		DisplayUtilities.cleanDisplay(display);

		// accroche clavier
		display.setKeyListener(this);
		// lance la scene
		display.start(this);
	}

	/**
	 * Methode de demarrage de la scene.
	 */
	public final void begin() {
		// initialise moteur
		display.setAnimationRate(FPS); // 60 images par seconde pour etre fixe
	}

	/**
	 * Methode appelee a la fin de la scene.
	 */
	public final void end() {
		releaseAllKeys();
		DisplayUtilities.cleanDisplay(display);
		DisplayUtilities.cleanTransparence(display);
	}

	/**
	 * Methode qui gere la boucle d'animation
	 */ 
	public final void mainLoop() {
		// ecouter les evenements claviers
		callKeyListener();
		// les gerer
		manageKeys();
		// afficher la page a l'ecran (background + texte)
		draw();
		// recalculer la transparence pour avoir un effet fondu inverse
		// sur le texte affiche
		calculTransparence();

	}

	/**
	 * Methode qui dessine le contenu de la scene.
	 */
	public final void draw() {
		// affiche le layer de sprite avec son fond d'ecran
		spriteLayer.animateAndDraw();
		// Affiche le texte a l'ecran
		drawText();
		// Affiche le taux de rafraichissement
		drawFPSRate();
	}

	/** 
	 * Calcule la transparence pour l'effet de fondu sur le texte d'accueil.
	 */
	private void calculTransparence() {
		if (alpha < 0.9f) {
			finAttente = System.currentTimeMillis();
			if (finAttente - debAttente >= DELAI_ATTENTE) {
				// Diminuer la transparence toutes les xxx ms
				debAttente = finAttente;
				alpha = alpha + 0.05f;
			}
		}
	}

	/**
	 * Dessine le texte d'accueil.
	 */
	private void drawText() {
		Graphics2D g2d = (Graphics2D) display.getDoubleBufferedGraphics();
		g2d.drawImage(imageMario, 240, 50, 319, 321, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));		
		g2d.setFont(dialogFont);
		g2d.setColor(Color.WHITE);
		g2d.drawString("CLASSIC TETRIS", 235, 480);
		g2d.setColor(Color.YELLOW);
		g2d.drawString("PAR FRANCK PROVENZANO", 120, 550);
		g2d
				.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
	}

	/***
	 * Affiche le framerate.
	 */
	private final void drawFPSRate() {
		String message = "Escape to quit - " + meterString;
		Graphics gc = display.getDoubleBufferedGraphics();
		gc.setFont(debugFont);
		gc.setColor(Color.black);
		gc.fillRect(0, 0, 150, 15);
		gc.setColor(Color.white);
		gc.drawString(message, 5, 12);
		checkFPSRate();
	}

	/***
	 * Calcule le framerate.
	 */
	private final void checkFPSRate() {
		// calcul FPS
		if (++elapsedFrames == JadeMath.max(10, FPS / 20)) {
			// calcul FPS
			long actualTime = System.currentTimeMillis();
			long gap = actualTime - lastTime == 0 ? 1 : actualTime - lastTime;
			int currentFPS = (int) ((JadeMath.max(10, FPS / 20) * 1000) / (gap));
			finAttente = System.currentTimeMillis();
			if (finAttente - debAttente >= DELAI_RATE) {
				debAttente = finAttente;
				meterString = currentFPS + " FPS ~ ";
			}
			lastTime = actualTime;
			elapsedFrames = 0;
		}
	}
}
