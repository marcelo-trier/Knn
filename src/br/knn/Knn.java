package br.knn;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Knn  extends PixelManager {
	
	ArrayList<PixelKnn> todosPixels = new ArrayList<PixelKnn>();
	ArrayList<PixelKnn> positivos = new ArrayList<PixelKnn>();
	ArrayList<PixelKnn> negativos = new ArrayList<PixelKnn>();
	Classe classePositiva;
	int N = 1;
//	BufferedImage outImg;
	
	public void setN( int n ) {
		N = n;
	}
	
	public Knn(BufferedImage i, Classe c ) {
		super(i);
		classePositiva = c;
	}

	public void carregaPontosClasse() {
		Point p = new Point( 0, 0 );
		PixelKnn pk;
		ArrayList<PixelKnn> umaLista;
		Classe classesPontos[] = Classe.values();
		for( int c=0; c<classesPontos.length; c++ ) { // for para todas as classes de pontos
			int[] arrX = Classe.PONTOSX[ c ];
			int[] arrY = Classe.PONTOSY[ c ];
			int numeroPontos = 10;
			umaLista = negativos;
			if( classesPontos[ c ] == classePositiva ) {
				numeroPontos = arrX.length;
				umaLista = positivos;
			}

			for( int i=0; i<numeroPontos; i++ ) { // para a classe em questão irá pegar 30 pontos... para as outras, vai pegar os 1os 10.
				p.setLocation( arrX[ i ], arrY[ i ] );
				int id = todosPixels.indexOf( p );
				pk = todosPixels.get( id );
				umaLista.add( pk );
			}
		}
		
	} 
	
	/*
	public void setClass( Classe c ) {
		classePositiva = c;
	}
	*/

	public void processaPixel(int[] pix, int x, int y) {
		PixelKnn pk = new PixelKnn( x, y, pix );
		todosPixels.add( pk );
	}

	public void init() {
		percorraTodosPixels();
		carregaPontosClasse();
	}

	public void execute() {
		for( PixelKnn pk : todosPixels ) {
			for( PixelKnn pos : positivos ) {
				double d = pk.distance( pos );
				pk.addNeighbor( d, true );
			}
			for( PixelKnn neg : negativos ) {
				double d = pk.distance( neg );
				pk.addNeighbor( d, false );
			}
			Collections.sort( pk.listaVizinhos );
			int somaP = 0;
			int somaN = 0;
			for( int i=0; i<N; i++ ) {
				if( pk.listaVizinhos.get( i ).positivo )
					somaP += 1;
				else
					somaN += 1;
			}
			if( somaP > somaN )
				pk.aClasse = classePositiva;
			else
				pk.aClasse = null;
		}
	}
	
	public BufferedImage geraImagem() {
		BufferedImage outImg = new BufferedImage( _img.getWidth(), _img.getHeight(), _img.getType() );
		WritableRaster outRaster = outImg.getRaster();
		Raster r = _img.getData();
		int[] umPixel = new int[ r.getNumBands() ];
		umPixel[ r.getNumBands() -1 ] = 255; // atribui o canal alfa para 255

		for( PixelKnn pk : todosPixels ) {
			if( pk.aClasse == null )
				umPixel[ 0 ] = umPixel[ 1 ] = umPixel[ 2 ] = 0;
			else {
				Color cor = Classe.Cores[ pk.aClasse.valor ];
				umPixel[ 0 ] = cor.getRed();
				umPixel[ 1 ] = cor.getGreen();
				umPixel[ 2 ] = cor.getBlue();
			}
			outRaster.setPixel( pk.x, pk.y, umPixel );
		}
		
		return outImg;
	}
	
}
