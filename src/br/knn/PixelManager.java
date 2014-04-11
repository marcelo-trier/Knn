package br.knn;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public abstract class PixelManager {

	protected BufferedImage _img;
	
	public PixelManager( BufferedImage i ) {
		setImg( i );
	}

	public void setImg( BufferedImage novaImg ) {
		_img = novaImg;
	}
	
	public void percorraTodosPixels() {
		int w = _img.getWidth();
		int h = _img.getHeight();
		Raster raster = _img.getData();
		int[] pix = new int[ raster.getNumBands() ];
		try {
		for( int y=0; y<h; y++ ) {
			for( int x=0; x<w; x++ ) {
				pix = raster.getPixel( x, y, pix );
				processaPixel( pix, x, y );
			}
		}
		
		} catch( Exception ex ) {
			int aa = 0;
			aa++;
		}
	}

	/*
	public abstract BufferedImage geraImagem() {
		WritableRaster outRaster = _outImg.getRaster();
		Raster r = _img.getData();
		int[] umPixel = new int[ r.getNumBands() ];
		umPixel[ r.getNumBands() -1 ] = 255; // atribui o canal alfa para 255

		for( int y=0; y<_img.getHeight(); y++ ) {
			for( int x=0; x<_img.getWidth(); x++ ) {
				PixelKmeans atual = _matriz[x][y];
				for( int cor=0; cor<NCARACT; cor++ ){
					// para cada pixel, pegar a cor do centroide...
					int valorCor = (int)( atual._centroide._rgb[ cor ] );
					umPixel[ cor ] = valorCor;
				}
				//System.arraycopy( atual._rgb, 0, umPixel, 0, NCARACT );
				outRaster.setPixel( x,  y,  umPixel );
			}
		}
		return _outImg;
	}
	*/
	
	public abstract void processaPixel( int[] pix, int x, int y );
	public abstract void init();
	public abstract void execute();
}
