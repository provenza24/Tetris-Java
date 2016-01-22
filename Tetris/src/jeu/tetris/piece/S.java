package jeu.tetris.piece;

import jeu.uitl.Position;

/**
* Definie la piece S.
*
*/
public final class S extends Piece {			
	
	/**
	 * Constructeur par defaut qui initialise sa position en haut de l'ecran.
	 */
	public S() {
		super();	
		num = 4;	
		cases[0] = new Position(4, 1);
		cases[1] = new Position(5, 1);
		cases[2] = new Position(3, 2);
		cases[3] = new Position(4, 2);
	}

	/**
	 * {@inheritDoc} Surcharge de la methode car la barre ne peut pivoter qu'une
	 * seule fois.
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
	 * Surcharge de la methode car le centre de rotation du S est variable.
	 */
	public final void rotate() {				
		super.rotate();		
		if (numPos == 0) {
			// Si la position de rotation est la 1ere
			for (int i = 0; i < 4; i++) {
				// Deplacer la piece a gauche
				cases[i].decX();
			}			
			// et changer le centre de rotation 
			cases[0].swapPositions(cases[3]);
		}
		getNextPos();		
	}	
}
