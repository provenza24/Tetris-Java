package jeu.tetris;

import jeu.uitl.DirectionType;


/**
 * Interface offrant des methodes pour deplacer les pieces.
 */
public interface IMoving {

	/**
	 * Methode permettant de deplacer la piece dans une direction (bas, gauche, droite)
	 * @param direction La direction du deplacement
	 * @param jeu Le tableau de jeu
	 */
	public void moveTo(DirectionType direction, Board jeu);
	
}
