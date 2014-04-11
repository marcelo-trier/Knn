package br.knn.ui;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import br.knn.Classes;


public class JanelaPrincipal extends JFrame {

	private JDesktopPane contentPane;

	ArrayList<Point> pontos = new ArrayList<Point>();
	
	public void clickLimpaPontos() {
		String px = "x = {";
		String py = "y = {";
		for( Point p : pontos ) {
			px += p.x + ", ";
			py += p.y + ", ";
		}
		px += "}";
		py += "}";
		String str = px + "  " + py;
		//JOptionPane.showInternalInputDialog( this, "Copie:", "", messageType)
		JOptionPane.showInputDialog("oi", str );
		//JOptionPane.showMessageDialog(this, str );
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		ti.pintaImagem();
	}
	
	public void clickDefinePontos() {
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		//ArrayList<Point> lista = new ArrayList<Point>();
		ti.registraPonto(10, pontos, Color.BLUE );
		//JOptionPane.showMessageDialog( this, "aa = " + Classes.MONTANHA );
		
	}
	
	
	public void mostraImagem( String titulo, BufferedImage imgOut ) {
		TelaInterna interno = new TelaInterna( titulo, imgOut );
		contentPane.add( interno );
		interno.setVisible( true );	
		
	}
	
	public void mostraImagem( BufferedImage imgOut ) {
		mostraImagem( "", imgOut );
	}

	public BufferedImage getImage() {
		// pega a janela ativa...
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		BufferedImage img;
		img = ti.getImage();
		return img;
	}

	public void clickSave() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType( JFileChooser.SAVE_DIALOG );
		File umDir = new File( System.getProperty( "user.dir" ) );
		fileChooser.setCurrentDirectory( umDir );
		if( fileChooser.showSaveDialog( this ) != JFileChooser.APPROVE_OPTION ) {
			return;
		}
		File salvar = fileChooser.getSelectedFile();
		//ImageIO.w
		BufferedImage img = getImage();
		ImageIO.write( img, "bmp", salvar );
	}

	
	public void clickOnLoad() throws Exception {
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType( JFileChooser.OPEN_DIALOG );
		File umDir = new File( System.getProperty( "user.dir" ) );
		fileChooser.setCurrentDirectory( umDir );
		if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File file = fileChooser.getSelectedFile();
		BufferedImage imagem = ImageIO.read( file );
		TelaInterna interno = new TelaInterna( imagem );
		contentPane.add( interno );
		interno.setVisible( true );
	}
	
	/**
	 * Create the frame.
	 */
	public JanelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 701, 399);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Load...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickOnLoad();
				} catch( Exception ex ) {
					
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save...");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					clickSave();
				} catch (Exception ex ) {
					
				}
			}
		});
		mnFile.add(mntmSave);
		
		JMenu mnImagens = new JMenu("Imagens");
		menuBar.add(mnImagens);
		
		JMenu mnProcessamento = new JMenu("Processamento");
		menuBar.add(mnProcessamento);
		
		JMenuItem mntmDefinepontos = new JMenuItem("DefinePontos");
		mntmDefinepontos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickDefinePontos();
			}
		});
		mnProcessamento.add(mntmDefinepontos);
		
		JMenuItem mntmLimpapontos = new JMenuItem("LimpaPontos");
		mntmLimpapontos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickLimpaPontos();
			}
		});
		mnProcessamento.add(mntmLimpapontos);
		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}
