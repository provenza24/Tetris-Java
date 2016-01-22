package jeu.tetris.piece;

import jeu.uitl.Position;

/**
 * Definie le L.
 */
public final class L extends Piece {

	/**
	 * Constructeur par defaut qui initialise sa position en haut de l'ecran.
	 */
	public L() {
		super();
		num = 2;
		cases[0] = new Position(4, 1);
		cases[1] = new Position(3, 1);
		cases[2] = new Position(5, 1);
		cases[3] = new Position(3, 2);		
	}
}
