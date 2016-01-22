package jeu.uitl;

/** 
 * Modelise une position dans un espace a deux dimensions.
 * @author fprovenzano
 */
public class Position {

	/** Coordonnee en x */
	private  int x;
	
	/** Coordonnee en y */
	private int y;

	/**
	 * Constructeur par defaut
	 * @param x Coordonnee en x
	 * @param y Coordonnee en y
	 */
	public Position(int x, int y) {		
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void incX(){
		x = x + 1;
	}
	
	public void incY(){
		y = y + 1;
	}
	
	public void decX(){
		x = x - 1;
	}
	
	public void decY(){
		y = y - 1;
	}
	
	public void swapPositions(Position pos) {
		int bx = pos.getX();
		int by = pos.getY();
		pos.setX(x);
		pos.setY(y);
		x = bx;
		y = by;
	}	
}
