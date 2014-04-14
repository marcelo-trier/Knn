package br.knn;

public class Neighbor implements Comparable<Neighbor> {
	boolean positivo = false;
	double distancia;
	public PixelKnn p;
	
	public Neighbor( double d, boolean p ) {
		positivo = p;
		distancia = d;
	}
	
	public int compareTo(Neighbor o) { // retorna Neg se este eh MENOR... 0 se IGUAL... Posit. se MAIOR
		double result = o.distancia - this.distancia; 
		if( result < 0 )
			return -1;
		else if( result > 0 )
			return 1;
		else
			return 0;
	}

}
