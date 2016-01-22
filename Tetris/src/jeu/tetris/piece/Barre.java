package jeu.tetris.piece;

import jeu.uitl.Position;

/**
 * Definie la piece barre.
 *
 */
public final class Barre extends Piece {

	/**
	 * Constructeur par defaut qui initialise sa position en haut de l'ecran.
	 */
	public Barre() {
		super();
		num = 0;
		cases[0] = new Position(4, 1);
		cases[1] = new Position(3, 1);
		cases[2] = new Position(5, 1);
		cases[3] = new Position(6, 1);	
	}

	/**
	 * {@inheritDoc}
	 * Surcharge de la methode car la barre ne peut pivoter qu'une seule fois.
	 */
	public final void getNextPos() {
		numPos = numPos == 0 ? 1 : 0;
	}

	/**
	 * {@inheritDoc}
	 * Surcharge de la methode car la barre ne peut pivoter qu'une seule fois.
	 */
	public final void getPreviousPos() {
		numPos = numPos == 0 ? 1 : 0;
	}

	/**
	 * {@inheritDoc}
	 * Surcharge de la methode car le centre de rotation de la barre est variable.
	 */
	public final void rotate() {
		super.rotate();
		if (numPos == 1) {
			// Si la position de rotation est la 2eme
			for (int i = 0; i < 4; i++) {
				// on doit decaler la piece a droite
				cases[i].incX();
			}
			// Et reaffecter le bon centre de rotation
			cases[0].swapPositions(cases[2]);
		}
		getNextPos();
	}
}
