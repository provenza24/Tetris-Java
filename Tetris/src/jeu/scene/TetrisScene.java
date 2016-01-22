package jeu.scene;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import jeu.jade.engine.JadeAnimation;
import jeu.jade.engine.JadeBackground;
import jeu.jade.engine.JadeDisplay;
import jeu.jade.engine.JadeFamily;
import jeu.jade.engine.JadeFont;
import jeu.jade.engine.JadeImageMosaicBackground;
import jeu.jade.engine.JadeKeyListener;
import jeu.jade.engine.JadeLoadingInterface;
import jeu.jade.engine.JadeMath;
import jeu.jade.engine.JadeMediaLoader;
import jeu.jade.engine.JadeScene;
import jeu.jade.engine.JadeSprite;
import jeu.jade.engine.JadeSpriteLayer;
import jeu.jade.loader.JadeContainer;
import jeu.tetris.Board;
import jeu.tetris.piece.Piece;
import jeu.uitl.DirectionType;
import jeu.uitl.DisplayUtilities;

/**
 * Scene de jeu du Tetris.
 */
public class TetrisScene extends JadeKeyListener implements JadeScene {

	/** Police de caracteres pour le debug : a virer en version final */
	private static final Font debugFont = new Font("Dialog ", Font.BOLD, 10);

	/** Le nombre de FPS souhaite pour la scene */
	public static final int FPS = -1;	

	/** Le layer de sprite */
	private static JadeSpriteLayer spriteLayer;

	/** Le display */
	private static JadeDisplay display;

	/**
	 * Chaine maj toutes les secondes avec le nombre de FPS reel et la charge
	 * CPU
	 */
	protected String meterString = "?? FPS ~";

	/** Variables de calcul du FPS et du % CPU - dernier temps mesure en ms */
	protected long lastTime = 0;

	/** Nombre de boucles executees */
	protected int elapsedFrames = 0;

	/** Variables qui indiquent les touchent pressees pendant le jeu */
	private static boolean escape, right, left, rotate, bottom, enter;
	
	private static boolean descenteRapide = false;

	/** Position en x ou l'on commence a afficher le tableau du jeu */
	private static int x = 252;

	/** Position en y ou l'on commence a afficher le tableau du jeu */
	private static int y = 143;

	/** Piece courante et suivante du jeu */
	private static Piece pieceCourante, pieceSuivante;

	/** Le tableau contenant les cases du jeu ( tableau de 10*18 ) */
	private static Board jeu;

	/** Temps d'attente entre la pression de 2 touches */
	private static final long KEY_TIME = 120;

	/** Variable pour les prises de temps */
	private static long debFallTime;

	/** Prise de temps pour limiter le temps entre 2 touches et donc 2 actions */
	private static long debKeyTime;

	/** Image representant une ligne vide */
	private static Image ligneVide;

	/** Image de fin (please try again */
	private static Image imageFin;

	/** Musique de tetris */
	private static AudioClip mainMusic;

	/** Musique de tetris lorsqu'on perd */
	private static AudioClip looseMusic;

	/** Font d'ecriture pour le score et le level */
	private static JadeFont font;

	/** Map qui contient les images des pieces */
	private static final Map<Integer, Image> images = new HashMap<Integer, Image>();

	/** Map contenant le nombre de points pour x lignes et pour le niveau 0 */
	private static Map<Integer, Integer> scores = new HashMap<Integer, Integer>();

	/** Score courant */
	private static int score;

	/** Level courant */
	private static int level;

	/** Nombre de lignes realisees */
	private static int nbLignes;

	/** Temps pour faire descendre la piece courante */
	private static long delay;	
	
	/** Variables utilisees pour afficher le framerate */
	private static long finAttente, debAttente;
	
	/** Delai d'attente pour reafficher le taux de rafraichissement */
	private static final long DELAI_RATE = 300;

	/**
	 * Constructeur
	 * 
	 * @param jadeDisplay
	 *            Display
	 */
	public TetrisScene(JadeDisplay jadeDisplay) {
		display = jadeDisplay;
		// Charger les donnees graphiques
		loadGraphics();
		// Indiquer le Framerate souhaite
		display.setAnimationRate(FPS);	
	}

	/**
	 * Methode d'initialisation des divers elements graphiques.
	 */
	private static final void loadGraphics() {

		// Initialiser l'interface de chargement
		JadeLoadingInterface jli = JadeLoadingInterface
				.getLoadingInterface(display);
		jli.setMessage("Loading..");
		jli.initializeProgress(100);

		// Defini chemin de chargement pour les images
		JadeMediaLoader.setPath("resources/images/");

		jli.setProgress(10);

		// BackGround de la scene : image de la GameBoy
		JadeBackground background = new JadeImageMosaicBackground(
				JadeMediaLoader.loadImage("screen.png"));

		jli.setProgress(10);

		// Iniialisation du layer de sprites
		spriteLayer = new JadeSpriteLayer(display, background, null);
		spriteLayer.setDisplayMode(JadeSpriteLayer.ALL, JadeSpriteLayer.ALL);

		jli.setProgress(10);

		// Animation pour faire clignoter la diode de la GameBoy
		JadeAnimation diodeAnim = new JadeAnimation(JadeMediaLoader.parseImage(
				JadeMediaLoader.loadImage("diode.png"), 2, 1), new int[] { 0,
				1, }, new int[] { 0, 0 }, true, false);
		JadeFamily diodeFamily = new JadeFamily(diodeAnim, new int[] { 0, 0 });
		JadeSprite diode = new JadeSprite(145, 255, diodeFamily);
		diode.playAnimation();
		diodeFamily.addElement(diode);
		spriteLayer.families.addElement(diodeFamily);

		jli.setProgress(20);

		// Images du jeu
		ligneVide = JadeMediaLoader.loadImage("vide.gif");
		imageFin = JadeMediaLoader.loadImage("end.png");
		images.put(0, JadeMediaLoader.loadImage("barre.png"));
		images.put(1, JadeMediaLoader.loadImage("carre.png"));
		images.put(2, JadeMediaLoader.loadImage("L.png"));
		images.put(3, JadeMediaLoader.loadImage("Linverse.png"));
		images.put(4, JadeMediaLoader.loadImage("carreau.png"));
		images.put(5, JadeMediaLoader.loadImage("T.png"));
		images.put(6, JadeMediaLoader.loadImage("Z.png"));

		jli.setProgress(30);

		// Police de caracteres pour afficher le Level et le Score
		font = new JadeFont(JadeMediaLoader.parseImage(JadeMediaLoader
				.loadImage("chiffres.png"), 10, 1), "0123456789");

		jli.setProgress(10);

		// Charger la musique du jeu
		JadeMediaLoader.setPath("resources/sons/");
		mainMusic = JadeMediaLoader.loadSound("tetris.mid");
		looseMusic = JadeMediaLoader.loadSound("tetrissc.mid");

		jli.setProgress(10);	
	}

	/**
	 * Methode appelee lorsque l'on presse un bouton clavier. Attention, pour
	 * une bonne gestion des evenements, on definira des boolean d'etat pour les
	 * touches utilisees (ainsi, pour se deplacer a gauche par exemple, il
	 * suffit de rester appuye sur le bouton gauche ... )
	 */
	public final void onKeyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// Touche escape pressee
			escape = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_UP) {
			rotate = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
			bottom = true;
		}
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			enter = true;
		}
	}

	/**
	 * Methode appelee lorsque l'on relache une touche.
	 */
	public final void onKeyReleased(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			escape = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_UP) {
			rotate = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
			bottom = false;
		}
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			enter = false;
		}
	}

	/**
	 * Methode appelee dans la boucle principale pour effectuer une action en
	 * fonction des touches pressees.
	 */
	private final void manageKey() {

		if (escape) {
			end(); // Appeler la methode end() en permanence pour clore la
			// scene
			display.stop(); // Arreter le display
		}

		if (System.currentTimeMillis() - debKeyTime > 20) {
			if (bottom) {
				// Descente rapide de la piece
				pieceCourante.savePositions();
				// On la tourne
				pieceCourante.moveTo(DirectionType.DOWN, jeu);
				if (!jeu.acceptablePos(pieceCourante)) {
					// Et si la nouvelle position n'est pas correcte, on la
					// remet a sa place
					pieceCourante.undoMove();
					bottom = false;
				} else {
					debKeyTime = System.currentTimeMillis();
					// audioClic.play();
				}
				// Indiquer qu'on a appuyer sur bas pour incrementer le score si
				// l'on doit poser la piece
				descenteRapide = true;
			}
		}

		if (System.currentTimeMillis() - debKeyTime > KEY_TIME) {
			if (left || right) {
				// On demande a deplacer la piece a droite ou a gauche
				DirectionType direction = left ? DirectionType.LEFT
						: DirectionType.RIGHT;
				// On sauve la position courante de la piece
				pieceCourante.savePositions();
				// On effectue le deplacement
				pieceCourante.moveTo(direction, jeu);
				if (!jeu.acceptablePos(pieceCourante)) {
					// Si le deplacement est impossible ou revient a la position
					// precedente
					pieceCourante.undoMove();
					left = false;
					right = false;
				} else {
					// audioClic.play();
					debKeyTime = System.currentTimeMillis();
				}
			} else if (rotate) {
				// Rotation de la piece courante,
				pieceCourante.savePositions();
				pieceCourante.rotate();
				if (!jeu.acceptablePos(pieceCourante)) {
					// Si on ne peut pas la tourner, on annule la rotation
					pieceCourante.undoMove();
					pieceCourante.getPreviousPos();
					rotate = false;
				} else {
					// audioClic.play();
					debKeyTime = System.currentTimeMillis();
				}

			}
		}
	}

	/**
	 * Methode appelee par la methode end pour relacher toutes les touches du
	 * clavier.
	 */
	private final void releaseAllKeys() {
		escape = false;
		right = false;
		left = false;
		bottom = false;
		rotate = false;
		enter = false;		
	}

	// Methode heritee de JadeScene
	public final void loadAndRun(JadeContainer container) {
	}

	/**
	 * Methode appelee au demarrage de la scene. Attention, n'est pas appelee
	 * lors d'un getBack !!!.
	 */
	public final void begin() {

		// Brancher l'ecouteur clavier sur la scene
		display.setKeyListener(this);

		// Initialiser les prises de temps
		debFallTime = System.currentTimeMillis();
		debKeyTime = System.currentTimeMillis();

		// Initialisation des cases du jeu
		jeu = new Board();
		// et des pieces courantes et suivantes
		pieceCourante = jeu.createPiece(true, 0);
		pieceSuivante = jeu.createPiece(true, 0);

		// Initialisation des donnees globales
		score = 0;
		nbLignes = 0;
		level = 9;
		delay = 160;
		scores.put(1, 40);
		scores.put(2, 100);
		scores.put(3, 300);
		scores.put(4, 1200);

		// Lancer la musique du jeu
		mainMusic.loop();	
	}

	/**
	 * Methode end() appelee pour clore la scene. Cette methode nettoie le
	 * display.
	 */
	public final void end() {
		// Stopper la musique
		mainMusic.stop();
		// Relacher toutes les touches
		releaseAllKeys();
		// Nettoyer le display : ne pas supprimer cette ligne
		DisplayUtilities.cleanDisplay(display);				
	}

	/**
	 * Boucle principale. Ecoute les evenements, les gere et redessine le tout.
	 */
	public final void mainLoop() {
		// Ecouter les evenements
		callKeyListener();
		// Les gerer
		manageKey();
		// Faire descendre la piece
		if (System.currentTimeMillis() - debFallTime > delay) {
			pieceCourante.savePositions();
			pieceCourante.moveTo(DirectionType.DOWN, jeu);
			if (!jeu.acceptablePos(pieceCourante)) {
				// Poser la piece car elle ne peut plus descendre
				posePiece();
			}
			// Ce booleen sert a update le score, descente rapide => +11 pts
			descenteRapide = false;
			debFallTime = System.currentTimeMillis();
		}
		// Redessiner la scene
		draw();

	}

	private final void posePiece() {

		// Annuler le deplacement de la piece car il n'est plus valide
		pieceCourante.undoMove();
		// La pose dans le tableau de jeu
		jeu.posePiece(pieceCourante);
		// Relacher toutes les touches
		releaseAllKeys();
		draw();
		display.swapBuffers();

		int toSuppress[] = jeu.getLinesToSuppress(pieceCourante);
		int nbSuppress = toSuppress[4];
		if (nbSuppress > 0) {
			// On fait clignoter les lignes a supprimer
			clignoteLigne(toSuppress);
			// Et on les supprime
			for (int i = 0; i < 4; i++) {
				if (toSuppress[i] != -1) {
					jeu.suppressLine(toSuppress[i]);
					incNbLignes();
				}
			}
			incScore(nbSuppress);
		}

		if (descenteRapide) {
			// 11 pts de plus si on a poser la piece en descente rapide
			score = score + 11;
		}

		// Et on passe a la suivante
		pieceCourante = jeu.createPiece(false, pieceSuivante.getNum());
		pieceSuivante = jeu.createPiece(true, 0);
		if (!jeu.acceptablePos(pieceCourante)) {
			// Si on ne peut pas initialiser la piece courante en haut,
			// on a perdu la partie
			drawPieceCourante();
			display.swapBuffers();
			end();
			looseScreen();
			display.stop();
		}
	}

	/**
	 * Methode qui dessine la scene a l'ecran.
	 */
	public final void draw() {
		// Animer les Sprites
		spriteLayer.animateAndDraw();

		// tout est dessine dans le double buffer
		drawGameStats();
		drawGameBoard();
		drawPieceCourante();
		drawNextPiece();

		// Afficher le taux de rafraichissement
		drawFPSRate();
	}

	/**
	 * Methode qui dessine les stats (score, level et nombre de lignes).
	 */
	private final void drawGameStats() {
		font.drawString(Integer.toString(level), getPos(level), y + 135,
				display.getDoubleBufferedGraphics(), true, 0, 0);

		font.drawString(Integer.toString(nbLignes), getPos(nbLignes), y + 192,
				display.getDoubleBufferedGraphics(), true, 0, 0);

		font.drawString(Integer.toString(score), getPos(score), y + 60, display
				.getDoubleBufferedGraphics(), true, 0, 0);

	}

	/**
	 * Selon la valeur du nombre passe en parametre, le centrer a l'ecran lors
	 * de l'affichage.
	 * 
	 * @param nb
	 *            Le nombre a afficher centre
	 * @return La position d'affichage
	 */
	private final int getPos(int nb) {
		return nb < 10 ? x + 290 : nb < 100 ? x + 275 : nb < 1000 ? x + 265
				: x + 245;
	}

	/**
	 * Methode d'affichage du FrameRate.
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

	/**
	 * Faire clignoter les lignes supprimees.
	 * 
	 * @param toSuppress
	 *            Le tableau contenant les lignes a supprimer.
	 */
	private final void clignoteLigne(int[] toSuppress) {

		Graphics gc = display.getGraphics();
		gc.drawImage(ligneVide, x, y, 190, 19, null);

		// Prise de temps
		long deb = System.currentTimeMillis();
		long debCli = deb;
		boolean clign = false;

		while (System.currentTimeMillis() - deb < 800) {
			// Pendant 800ms faire clignoter les lignes
			if (System.currentTimeMillis() - debCli > 100) {
				// Toutes les 100 ms, indiquer si oui ou non on affiche la ligne
				clign = clign == true ? false : true;
				debCli = System.currentTimeMillis();
			}

			for (int i = 0; i < 4; i++) {
				if (toSuppress[i] != -1) {
					if (clign) {
						// Si clignotement, dessiner la ligne
						for (int j = 0; j < 10; j++) {
							if (jeu.getTableau()[j][toSuppress[i]].getValue() == 1) {
								gc.drawImage((Image) images.get(jeu
										.getTableau()[j][toSuppress[i]]
										.getNumImage()), x + 19 * j, y + 19
										* toSuppress[i], 19, 19, null);
							}
						}
					} else {
						// Sinon afficher une ligne vide
						gc.drawImage(ligneVide, x, y + 19 * toSuppress[i], 190,
								19, null);
					}
				}
			}
		}

	}

	/**
	 * Methode de calcul du taux de rafraichissement.
	 */
	private final void checkFPSRate() {
		// calcul FPS
		if (++elapsedFrames == JadeMath.max(10, FPS / 20)) {
			// calcul FPS
			long actualTime = finAttente = System.currentTimeMillis();
			long time = actualTime - lastTime == 0 ? 1000 : actualTime - lastTime; 
			int currentFPS = (int) ((JadeMath.max(10, FPS / 20) * 1000) / (time));
			if (finAttente - debAttente >= DELAI_RATE) {			
				debAttente = finAttente;
				meterString = currentFPS + " FPS ~ ";
			}			
			lastTime = actualTime;
			elapsedFrames = 0;
		}
	}

	/**
	 * Afficher la piece courante a l'ecran.
	 * 
	 * @param doubleBuffer
	 *            Defini si on dessine dans le double buffer ou non
	 */
	private final void drawPieceCourante() {
		Graphics gc = display.getDoubleBufferedGraphics();
		for (int i = 0; i < 4; i++) {
			gc.drawImage((Image) images.get(pieceCourante.getNum()), x + 19
					* pieceCourante.getCases()[i].getX(), y + 19
					* pieceCourante.getCases()[i].getY(), 19, 19, null);
		}
	}

	/**
	 * Dessine la piece suivante.
	 */
	private final void drawNextPiece() {
		Graphics gc = display.getDoubleBufferedGraphics();
		int posx = 0;
		int posy = 0;
		for (int i = 0; i < 4; i++) {
			// Pour les cases cases qui composent la piece
			if (pieceSuivante.getNum() == 1) {
				// Pour la barre, la position est legerement differente
				posx = x + 190 + 19 * pieceSuivante.getCases()[i].getX();
				posy = y + 250 + 19 * pieceSuivante.getCases()[i].getY();
			} else if (pieceSuivante.getNum() == 0) {
				// Ainsi que pour le carre
				posx = x + 190 + 19 * pieceSuivante.getCases()[i].getX();
				posy = y + 260 + 19 * pieceSuivante.getCases()[i].getY();
			} else {
				posx = x + 200 + 19 * pieceSuivante.getCases()[i].getX();
				posy = y + 260 + 19 * pieceSuivante.getCases()[i].getY();
			}

			gc.drawImage((Image) images.get(pieceSuivante.getNum()), posx,
					posy, 19, 19, null);
		}
	}

	/**
	 * Dessine le tableau de jeu.
	 */
	private final void drawGameBoard() {
		Graphics gc = display.getDoubleBufferedGraphics();
		for (int i = 0; i < 10; i++) {
			// Parcourir les colonnes
			for (int j = 0; j < 18; j++) {
				// Les lignes
				if (jeu.getTableau()[i][j].getValue() == 1) {
					// Et si la case n'est pas vide, la dessiner
					gc.drawImage((Image) images.get(jeu.getTableau()[i][j]
							.getNumImage()), x + 19 * i, y + 19 * j, 19, 19,
							null);
				}
			}
		}
	}

	/**
	 * Si l'on a perdu, on remplit le tableau pour afficher un ecran plein comme
	 * dans le vrai jeu.
	 */
	private final void looseScreen() {

		Graphics gc = display.getGraphics();
		long deb = System.currentTimeMillis();
		for (int j = 17; j >= 0; j--) {
			// Remplir chaque ligne a tour de role
			for (int i = 0; i < 10; i++) {
				gc.drawImage((Image) images.get(5), x + 19 * i, y + 19 * j, 19,
						19, null);
			}
			while (System.currentTimeMillis() - deb < 10) {
				// On dessine une ligne toutes les 10ms pour que ce soit
				// progressif
			}
			deb = System.currentTimeMillis();
		}
		deb = System.currentTimeMillis();
		while (System.currentTimeMillis() - deb < 300) {
		}
		gc.drawImage(imageFin, x, y, null);
		looseMusic.play();
		while (!escape && !enter) {
			callKeyListener();
		}
		looseMusic.stop();
	}

	/**
	 * Methode qui incemrente nombre de lignes, le level et le temps de chute
	 * des pieces.
	 */
	private final void incNbLignes() {
		nbLignes++;
		level = nbLignes < 100 ? 9 : level < 20 ? nbLignes / 10 : 20;
		delay = level == 9 ? 150 : 150 - (level - 9) * 10;
	}

	/**
	 * Incremente le score en fonction des lignes effectuees.
	 * 
	 * @param nbLignes
	 *            le nombre de lignes effectuees
	 */
	private final void incScore(int nbLignes) {
		score = score + scores.get(nbLignes) * (level + 1);
	}
}
