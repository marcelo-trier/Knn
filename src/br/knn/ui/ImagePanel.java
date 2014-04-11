package br.knn.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

public class ImagePanel extends JPanel implements MouseListener {

	BufferedImage imagem = null;
	int numeroPontos = 0;
	List<Point> umaLista;
	Color umaCor = Color.WHITE;
	
	
	public void setImage( BufferedImage img ) {
		imagem = new BufferedImage( img.getWidth(), img.getHeight(), img.getType() );
		Graphics g = imagem.createGraphics();
		g.drawImage( img, 0, 0, null );
		g.dispose();
	}
	
	public ImagePanel( BufferedImage img ) {
		super();
		setImage( img );
	}
	
	public void pegaPonto( int qtde, List<Point> l, Color c ) {
		this.setCursor(Cursor.getPredefinedCursor( Cursor.CROSSHAIR_CURSOR ) );
		numeroPontos = qtde;
		umaLista = l;
		umaCor = c;
		this.addMouseListener( this );
	}
	
	protected void paintComponent(Graphics gr ) {
    	super.paintComponent( gr );
    	
    	Graphics2D g = ( Graphics2D )gr;

    	if( imagem != null )
    	{
    		g.drawImage( imagem, 0, 0, null );
    	}
    }

    public void drawPoint( int x, int y ) {
	    Graphics2D g = imagem.createGraphics();
	    g.setColor( umaCor );
	    g.fillOval(x-5, y-5, 5, 5 ); // prá ficar no centro do click do mouse
	    this.repaint();
    }
    
	public void mouseClicked(MouseEvent e) {
		Point p = new Point();
		p.x = e.getX();
		p.y = e.getY();
		drawPoint( p.x, p.y );
		umaLista.add( p );
		numeroPontos--;
		if( numeroPontos <= 0 ) {
			this.setCursor(Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
			this.removeMouseListener( this ); 
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
	
}
