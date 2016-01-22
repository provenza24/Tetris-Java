package jeu.tetris.piece;

import jeu.uitl.Position;

/**
 * Definie la piece carre.
 */
public final class Carre extends Piece {

	/**
	 * Constructeur par defaut qui initialise sa position en haut de l'ecran.
	 */
	public Carre() {
		super();
		num = 1;
		cases[0] = new Position(5, 1);
		cases[1] = new Position(4, 1);
		cases[2] = new Position(5, 2);
		cases[3] = new Position(4, 2);	
	}

	/**
	 * {@inheritDoc}
	 * Surcharge car cette piece ne tourne pas.
	 */
	public final void rotate() {
		// Ne tourne pas !!!!
	}
}
