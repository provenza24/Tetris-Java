package jeu.tetris;
/**
 * Definie une case du tableau de jeu.
 */
public class Case {

	/** Valeur de la case (0=vide 1=occupee) */
	private int value;

	/** Le numero de l'image a dessiner pour cette case */
	private int numImage;

	/**
	 * Constructeur
	 * @param value Valeur de la case
	 * @param numImage Numero de l'image
	 */
	public Case(int value, int numImage) {
		super();
		this.value = value;
		this.numImage = numImage;
	}

	/**
	 * @return numImage
	 */
	public final int getNumImage() {
		return numImage;
	}

	/**
	 * @param numImage
	 */
	public final void setNumImage(int numImage) {
		this.numImage = numImage;
	}

	/**
	 * @return value
	 */
	public final int getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public final void setValue(int value) {
		this.value = value;
	}

}
