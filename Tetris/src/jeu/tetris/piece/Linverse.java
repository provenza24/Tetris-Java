package jeu.tetris.piece;

import jeu.uitl.Position;

/**
* Definie la piece  a l'envers.
*/
public final class Linverse extends Piece {

	/**
	 * Constructeur par defaut qui initialise sa position en haut de l'ecran.
	 */
	public Linverse() {
		super();	
		num = 3;
		cases[0] = new Position(4, 1);
		cases[1] = new Position(3, 1);
		cases[2] = new Position(5, 1);
		cases[3] = new Position(5, 2);		
	}
}
