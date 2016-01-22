package jeu.tetris;

import jeu.jade.engine.JadeMath;
import jeu.tetris.piece.Barre;
import jeu.tetris.piece.Carre;
import jeu.tetris.piece.L;
import jeu.tetris.piece.Linverse;
import jeu.tetris.piece.Piece;
import jeu.tetris.piece.S;
import jeu.tetris.piece.T;
import jeu.tetris.piece.Z;

/**
 * Classe comportant le tableau du jeu.<br>
 * La taille du tableau est de 10x18.
 * Chaque case est une combinaison de deux valeurs :<br>
 *  <ul>
 *  	<li> un entier presentant l'etat de la case : 0 = vide et 1 = occupee</li>
 *  	<li> Un entier indiquant l'image correspondant a la case</li>
 *  </ul>
 * Cette classe offre aussi differentes methodes pour agir sur le tableau comme
 * la suppression des lignes ...
 * Attention, le tableau comporte uniquement les cases vides, et les cases occupees par
 * des anciennes pieces ; la piece courante n'y figure pas.
 */
public class Board {
	
	/** Le tableau */
	private final Case tableau[][];

	/**
	 * Constructeur.
	 */
	public Board() {
		// Creer le tableau
		tableau = new Case[10][18];
		// et initialiser ses cases
		initTab();		
	}

	/**
	 * Methode qui cree une piece.
	 * @param random Indique si l'on cree la piece aleatoirement
	 * @param custom Indique le numero de la piece a creer sinon
	 * @return Une piece
	 */
	public final Piece createPiece(boolean random, int custom) {
		int num = random ? JadeMath.urand(7) : custom;
		switch (num) {
		case 0:
			return new Barre();
		case 1:
			return new Carre();
		case 2:
			return new L();
		case 3:
			return new Linverse();
		case 4:
			return new S();
		case 5:
			return new T();
		case 6:
			return new Z();
		default:
			return null;
		}
	}

	/**
	 * Initialiser toutes cases du tableau a 0.
	 */
	private final void initTab() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 18; j++) {
				tableau[i][j] = new Case(0, 0);
			}
		}
	}

	/**
	 * Indique si la position de la piece en parametre est acceptable dans le 
	 * tableau ou non.
	 * @param p La piece
	 * @return Indique si sa position est corecte ou non.
	 */
	public final boolean acceptablePos(final Piece p) {
		try {
			boolean acceptable = true;
			for (int i = 0; i < 4 && acceptable; i++) {
				// Pour chaque case occupee par la piece, verifier si dans le tableau la case est vide  
				if (tableau[p.getCases()[i].getX()][p.getCases()[i].getY()]
						.getValue() != 0)
					acceptable = false;
			}
			return acceptable;
		} catch (Exception e) {
			// Si une des cases de la piece est hors du tableau, renvoyer faux
			return false;
		}
	}

	/**
	 * Lorsque la piece ne peut plus descendre, on la pose dans le tableau.
	 * @param p
	 */
	public final void posePiece(Piece p) {
		// Parcourir les cases de la piece et mettre les cases correspondantes du tableau a 1
		for (int i = 0; i < 4; i++) {
			tableau[p.getCases()[i].getX()][p.getCases()[i].getY()].setValue(1);
			tableau[p.getCases()[i].getX()][p.getCases()[i].getY()]
					.setNumImage(p.getNum());
		}
	}

	public final void printTab() {
		for (int j = 0; j < 18; j++) {
			for (int i = 0; i < 10; i++) {

				System.out.print(tableau[i][j].getValue());
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * @return Le tableau
	 */
	public final Case[][] getTableau() {
		return tableau;
	}

	/**
	 * Retourne un tableau comportant le numero des lignes a supprimer.
	 * @param p La piece courante
	 * @return un tableau comportant le numero des lignes a supprimer
	 */
	public final int[] getLinesToSuppress(Piece p) {

		int[] res = initTabSuppress();
		int j = 0;
		int y = p.getCases()[0].getY();
		for (int i = y - 2; i < y + 2; i++) {
			// Une piece couvre au max 4 cases, donc on teste que les lignes ocuvrables			
			if (i >= 0 && i <= 17 && mustSuppress(i)) {
				// S'il faut supprimer la ligne, on l'ajoute au tableau
				/** TODO on pourrait conserver pour chaque ligne du tableau le nombre de cases couvertes */				
				res[j] = i;
				j++;				
				res[4]= res[4]+1;
			}
		}
		return res;
	}

	/**
	 * Initialise le tableau des lignes supprimees.
	 * @return Le tableau initialise
	 */
	private int[] initTabSuppress() {
		int res[] = new int[5];
		for (int i = 0; i < 4; i++)
			res[i] = -1;
		res[4]=0;
		return res;
	}

	/**
	 * Indique si l'on doit supprimer la ligne ou pas.
	 * @param line Le numero de la ligne passee en parametre.
	 * @return si l'on doit supprimer la ligne ou pas
	 */
	private final boolean mustSuppress(int line) {
		boolean suppress = true;
		for (int i = 0; i < 10 && suppress; i++) {
			if (tableau[i][line].getValue() == 0) {
				// Du moment qu'une cases est vide, il ne faut pas supprimer la ligne
				suppress = false;
			}
		}
		return suppress;
	}

	/**
	 * Supprime une ligne du tableau et gravite le reste des lignes du dessus.
	 * @param line La ligne a supprimer.
	 */
	public final void suppressLine(int line) {
		int currentLine = line -1;
		for (int i = currentLine; i > 0; i--) {
			// Parcourir toutes les lignes au dessus de la ligne supprimee
			for (int j = 0; j < 10; j++) {
				// Et les decaler vers le bas
				tableau[j][i + 1].setValue(tableau[j][i].getValue());
				tableau[j][i + 1].setNumImage(tableau[j][i].getNumImage());
				tableau[j][i].setValue(0);
			}
		}
	}
}
