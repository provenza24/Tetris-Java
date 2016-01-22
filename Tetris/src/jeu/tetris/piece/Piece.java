package jeu.tetris.piece;

import jeu.tetris.Board;
import jeu.tetris.IMoving;
import jeu.tetris.IPivoting;
import jeu.uitl.DirectionType;
import jeu.uitl.Move;
import jeu.uitl.Position;


/**
 * Classe abstraite qui definit une piece du jeu.
 * Une piece est representee par 4 cases, on dispose egalement d'un tableau
 * permettant de stocker l'ancienne position d'une piece.
 * Chaque piece a un numero et une position courante (pour la rotation).
 */
public abstract class Piece implements IMoving, IPivoting {

	/** Cases occupees par la piece */
	protected final Position[] cases;

	/** Anciennes cases occupees par la piece */
	protected final Position[] oldCases;
	
	/** Position de la piece pour la rotation */
	protected int numPos;
	
	/** Numero de la piece */
	protected int num;

	/**
	 * Construit une piece.
	 */
	public Piece() {
		numPos = 0;
		cases = new Position[4];
		oldCases = new Position[4];
		for (int i = 0; i < 4; i++) {
			oldCases[i] = new Position(0,0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public final void moveTo(DirectionType direction, Board jeu) {
		Move dep = getDep(direction);
		for (int i = 0; i < 4; i++) {
			cases[i].setX(cases[i].getX() + dep.getDx());
			cases[i].setY(cases[i].getY() + dep.getDy());
		}
	}

	/**
	 * Retourne un deplacement (dx,dy) a effectuer sur une piece en fonction
	 * d'une direction de deplacement.
	 * @param direction Direction
	 * @return Un deplacement (dx,dy)
	 */
	private final Move getDep(DirectionType direction) {
		return direction == DirectionType.DOWN ? new Move(0, 1)
				: direction == DirectionType.LEFT ? new Move(-1, 0)
						: direction == DirectionType.RIGHT ? new Move(
								1, 0) : null;
	}
	
	/**
	 * {@inheritDoc}
	 * Methode qui pivote une piece de 90 degre dans le sens inverse des aiguiilles
	 * d'une montre par rapport a son centre.
	 * Une piece comporte 4 cases (dans un tableau), son centre est la 1ere case
	 * de ce tableau.<br>
	 * Cette methode marche pour les pieces dont le centre de rotation est fixe ; pour
	 * les autres, on surchargera cette methode.
	 */
	public void rotate() {
		for (int i = 1; i < 4; i++) {
			Position p = new Position(cases[i].getX() - cases[0].getX(),
					cases[i].getY() - cases[0].getY());
			Position resul = new Position( p.getY(), -p.getX());
			resul.setX(resul.getX() + cases[0].getX());
			resul.setY(resul.getY() + cases[0].getY());
			cases[i].setX(resul.getX());
			cases[i].setY(resul.getY());
		}
	}

	/**
	 * {@inheritDoc}
	 * La quasi totalite des pieces peuvent tourner 3 fois, pour les 
	 * autres on surchargera cette methode.
	 */
	public void getNextPos() {
		numPos = numPos == 3 ? 0 : numPos +1;
	}

	/**
	 * {@inheritDoc}
	 * La quasi totalite des pieces peuvent tourner 3 fois, pour les 
	 * autres on surchargera cette methode.
	 */

	public void getPreviousPos() {
		numPos = numPos == 0 ? 3 : numPos - 1;
	}	
	
	/**
	 * Methode qui permet de sauver la position courante de la piece 
	 * avant de la deplacer par exemple.<br>
	 * Ainsi pour un deplacement invalide, on peut redonner a la piece sa
	 * position anterieure.
	 */
	public final void savePositions(){
		for (int i = 0; i < 4; i++) {
			oldCases[i].setX(cases[i].getX());
			oldCases[i].setY(cases[i].getY());
		}		
	}
	
	/**
	 * Reaffecte a la piece son ancienne position.
	 */
	public final void undoMove(){
		for (int i = 0; i < 4; i++) {
			cases[i].setX(oldCases[i].getX());
			cases[i].setY(oldCases[i].getY());
		}				
	}

	/**
	 * @return La liste des cases de la piece
	 */
	public Position[] getCases() {
		return cases;
	}

	/**
	 * @return Le numero de la piece
	 */
	public int getNum() {
		return num;
	}	
}
