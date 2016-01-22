package jeu.uitl;

/**
 * Classe qui modelise un deplacement caracterise par un vecteur (dx,dy) et
 * le nombre de fois ou il est repete (occurence).
 * @author fprovenzano
 */
public class Move {

	/** Deplacement en x */
	int dx;
	
	/** Deplacement en y */
	int dy;
	
	/** Nombre de fois ou le vecteur est repete */
	int nbOccurences;
	
	/**
	 * Construit un deplacement
	 * @param x deplacement en x
	 * @param y deplacement en y
	 * @param o occurence
	 */
	public Move(int x, int y, int o){
		dx = x;
		dy = y;
		nbOccurences = o;
	}
	
	public Move(int dx, int dy) {		
		this.dx = dx;
		this.dy = dy;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public int getNbOccurences() {
		return nbOccurences;
	}

	public void setNbOccurences(int nbOccurences) {
		this.nbOccurences = nbOccurences;
	}
	
	public boolean isNotNull(){
		return dx!=0 || dy!=0;
	}
	
	public void setDep(int x, int y){
		dx = x;
		dy = y;
	}
	
}
