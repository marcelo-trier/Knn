package br.knn;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import br.knn.Knn.KNNTypes;
import br.knn.util.Classe2;

public class Knn2 extends PixelManager {

	ArrayList<int[]> ptosPos = new ArrayList<int[]>();
	ArrayList<int[]> ptosNeg = new ArrayList<int[]>();
	Raster rImg = null;	

	// arrayzao com [ X ][ Y ][ DIST ]
	double[][][] asDistancias;

	Classe2 classePositiva;
	int N = 1;

	public void setN( KNNTypes umT ) {
		N = umT.valor;
		int w = _img.getWidth();
		int h = _img.getHeight();
		asDistancias = new double[ w ][ h ][ N ];
		for( int x=0; x<w; x++ )
			for( int y=0; y<h; y++ )
				for( int vlr=0; vlr<N; vlr++ )
					asDistancias[ x ][ y ][ vlr ] = 999;
	}
	
	public Knn2(BufferedImage i, Classe2 c ) {
		super(i);
		rImg = _img.getData();
		init();
		setClasse( c );
	}

	private void setClasse( Classe2 c ) {
		classePositiva = c;
		carregaPontosClasse();
	}
	
	public void carregaPontosClasse() {
		ArrayList<int[]> listaPtos;
		for( Classe2 c : Classe2.values() ) {
			int numPtos = 10;
			listaPtos = ptosNeg;
			if( c == classePositiva ) {
				numPtos = 30;
				listaPtos = ptosPos;
			}
			
			for( int i=0; i<numPtos; i++ ) {
				int pto[] = Classe2.PONTOSS_V3[ c.ordinal() ][ i ];
				int pix[] = null;
				pix = rImg.getPixel( pto[0], pto[1], pix );
				listaPtos.add( pix );
			}
		}
	} 
	
	@Override
	public void processaPixel(int[] pix, int x, int y) {
		// TODO: VAI FICAR VAZIOOOOO
	}

	@Override
	public void init() {
		// TODO: VAI FICAR VAZIOOOO
	}

	@Override
	public void execute() {
		int w = _img.getWidth();
		int h = _img.getHeight();
		int[] pix = { 0, 0, 0, 0 };
		for( int y=0; y<h; y++ ) {
			for( int x=0; x<w; x++ ) {
				pix = rImg.getPixel( x, y, pix );
				for( int i=0; i<ptosPos.size(); i++ ) {
					int[] pos = ptosPos.get( i );
					int[] neg = ptosNeg.get( i ) ;
					double dpos = getDistancia(pix, pos);
					double dneg = getDistancia(pix, neg);
					double d = ( dpos < dneg ) ? dpos : -dneg;
					for( int vlr=0; vlr<N; vlr++ ) {
						if( Math.abs( d ) < Math.abs( asDistancias[x][y][vlr] ) ) {
							asDistancias[x][y][vlr] = d;
							break;
						}
					}
				}
			}
		}
	}

	public double getDistancia( int[] pix1, int[] pix2 ) {
		double soma = 0;
		double tmp = 0;
		for( int i=0; i<3; i++ ) {
			tmp = pix1[ i ] - pix2[ i ]; // pega a subtracao
			tmp *= tmp; // eleva ao quadrado
			soma += tmp; // vai somando tudo...
		}
		return Math.sqrt( soma );
	}

	
	public BufferedImage geraImagem() {
		BufferedImage outImg = new BufferedImage( _img.getWidth(), _img.getHeight(), _img.getType() );
		WritableRaster outRaster = outImg.getRaster();
		//int[] umPixel = new int[ rImg.getNumBands() ];
		//umPixel[ r.getNumBands() -1 ] = 255; // atribui o canal alfa para 255

		int w = _img.getWidth();
		int h = _img.getHeight();

		int[] pixCor = { 0, 0, 0, 255 };
		Color cor = Classe2.Cores[ classePositiva.ordinal() ];
		pixCor[0] = cor.getRed();
		pixCor[1] = cor.getGreen();
		pixCor[2] = cor.getBlue();
		for( int y=0; y<h; y++ ) {
			for( int x=0; x<w; x++ ) {
				if( ehPositivo( asDistancias[x][y] ) )
					outRaster.setPixel( x, y, pixCor );
			}
		}

		return outImg;
	}
	
	public boolean ehPositivo( double[] dist ) {
		int pos=0, neg=0;
		for( double d : dist ) {
			if( d >= 0 )
				pos++;
			if( d < 0 )
				neg++;
		}
		return ( pos > neg );
	}
	
}
