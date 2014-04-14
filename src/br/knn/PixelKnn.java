package br.knn;

import java.awt.Point;
import java.util.ArrayList;

public class PixelKnn extends Point {
	int[] rgb = { 0, 0, 0 }; // preto
	Classe aClasse;
	ArrayList<Neighbor> listaVizinhos = new ArrayList<Neighbor>();
	
	public PixelKnn( int px, int py, int[] arr ) {
		super( px, py );
		System.arraycopy(arr, 0, rgb, 0, rgb.length );
	}
	
	public void addNeighbor( double d, boolean vizinho ) {
		Neighbor viz = new Neighbor( d, vizinho );
		listaVizinhos.add( viz );
	}
	
	public double distance( PixelKnn outroP ) {
		double soma = 0;
		double tmp = 0;
		for( int i=0; i<rgb.length; i++ ) {
			tmp = this.rgb[ i ] - outroP.rgb[ i ]; // pega a subtracao
			tmp *= tmp; // eleva ao quadrado
			soma += tmp; // vai somando tudo...
		}
		return Math.sqrt( soma );
	}


}
