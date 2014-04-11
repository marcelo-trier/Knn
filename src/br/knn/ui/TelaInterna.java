package br.knn.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;


public class TelaInterna extends JInternalFrame {
	static int contadorJanela = 0;
	public int id = 0;
	private ImagePanel panel;
	public BufferedImage imagem = null;
	
	public void pintaImagem() {
		panel.setImage( imagem );
		panel.repaint();
	}
	
	public void registraPonto( int qtde, List<Point> lista, Color cor ) {
		panel.pegaPonto( qtde, lista, cor );
	}
	
	public TelaInterna(BufferedImage img ) {
		super( "", true, true, true, true );
		initTela( "", img);
	}

	public void initTela( String titulo, BufferedImage img ) {
		String oTitulo = "[JanelaId: " + contadorJanela + "] :: ";
		oTitulo += titulo;
		this.setTitle( oTitulo );
		id = contadorJanela;
		int cantoX = 0, cantoY = 0;
		cantoX = 50 * id;
		cantoY = 50 * id;
		setBounds(cantoX, cantoY, img.getWidth(), img.getHeight() );
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		imagem = img;
		panel = new ImagePanel( img );
		getContentPane().add(panel, BorderLayout.CENTER);
		contadorJanela++;
		
		
	}
	
	public TelaInterna( String titulo, BufferedImage img ) {
		super( "", true, true, true, true );
		initTela( titulo, img);
	}
	
	public BufferedImage getImage() {
		return imagem;
	}

}
