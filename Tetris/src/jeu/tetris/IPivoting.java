package jeu.tetris;

/**
 * Interface proposant des methodes pour pivoter de 90 degre une piece.
 * Une piece dispose donc de plusieurs positions.
 */
public interface IPivoting {

	/**
	 * Faire pivoter la piece de 90 degres.
	 */
	public void rotate();	
	
	/**
	 * Position suivante de la piece.
	 */
	public void getNextPos();
	
	/**
	 * Position precedente de la piece.
	 */
	public void getPreviousPos();
}
