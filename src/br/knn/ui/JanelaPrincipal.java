package br.knn.ui;


import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.EmptyBorder;

import br.knn.Classe;
import br.knn.Knn;
import br.knn.Knn.KNNTypes;


public class JanelaPrincipal extends JFrame {

	private JDesktopPane contentPane;
	private ButtonGroup group;

	ArrayList<Point> pontos = new ArrayList<Point>();
	
	public KNNTypes getKnnType() {
		Enumeration<AbstractButton> op = group.getElements();
		AbstractButton item = null;
		while( op.hasMoreElements() ) {
			item = op.nextElement();
			if( item.isSelected() )
				break;
		}
		if( item == null )
			return null;

		KNNTypes t = KNNTypes.getType( item.getText() );
		return t;
	}
	
	public void clickTeste() {

		KNNTypes t = getKnnType();

		String str = "escolha = " + t;
		JOptionPane.showMessageDialog(this, str );
	}
	
	public void clickMostrePontos() {
		//Point p = new Point( 0, 0 );
		Classe classesPontos[] = Classe.values();
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		for( int c=0; c<classesPontos.length; c++ ) { // for para todas as classes de pontos
			int[] arrX = Classe.PONTOSX[ c ];
			int[] arrY = Classe.PONTOSY[ c ];
			int numeroPontos = arrX.length;
			pontos.clear();
			for( int i=0; i<numeroPontos; i++ ) { // para a classe em questão irá pegar 30 pontos... para as outras, vai pegar os 1os 10.
				Point p = new Point( arrX[ i ], arrY[ i ] );
				pontos.add( p );
			}
			ti.mostrePontos( 30, pontos, Classe.Cores[ c ] );
		}
	}
	
	public void clickKNN() {
		clickKNN( Classe.MONTANHA );
	}
	
	public void clickKNN( Classe umaClasse  ) {
		Knn knn = new Knn( getImage(), umaClasse );
		KNNTypes t = getKnnType();
		knn.setN( t );
		//knn.setN( 3 );
		knn.init();
		knn.execute();
		mostraImagem( knn.geraImagem() );
	}
	
	public void clickLimpaPontos() {
		String px = "int x[] = { ";
		String py = "int y[] = {";
		for( Point p : pontos ) {
			//px += "new Point(" + p.x + "," + p.y + "), ";
			px += p.x + ", ";
			py += p.y + ", ";
		}
		px += "}";
		py += "}";
		String str = px + "  " + py;
		//String str = px ;
		JOptionPane.showInputDialog("Copie: ", str );
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		ti.pintaImagem();
	}
	
	public void clickDefinePontos() {
		TelaInterna ti = ( TelaInterna )contentPane.getSelectedFrame();
		pontos.clear();
		//ArrayList<Point> lista = new ArrayList<Point>();
		ti.registraPonto(30, pontos, Color.RED );
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
		
		JMenuItem mntmTeste = new JMenuItem("Teste");
		mntmTeste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickTeste();
			}
		});
		mnImagens.add(mntmTeste);
		
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
		
		JMenuItem mntmKnn = new JMenuItem("KNN");
		mntmKnn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickKNN();
			}
		});
		
		JMenuItem mntmMostrePontos = new JMenuItem("Mostre Pontos");
		mntmMostrePontos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickMostrePontos();
			}
		});
		mnProcessamento.add(mntmMostrePontos);
		mnProcessamento.add(mntmKnn);
		
		JMenu menuKnnConfig = new JMenu("KNN Config");
		mnProcessamento.add(menuKnnConfig);
		
		
		JRadioButtonMenuItem menuConfig;
		group = new ButtonGroup();
		boolean selected = false;
		for( Knn.KNNTypes tipoKnn : Knn.KNNTypes.values() ) {
			menuConfig = new JRadioButtonMenuItem( tipoKnn.name() );
			menuKnnConfig.add( menuConfig );
		    group.add( menuConfig );
		    if( !selected )
		    	menuConfig.setSelected( true );

		    selected = true;
		}

		
		JMenu mnKnn = new JMenu("KNN (??)");
		mnProcessamento.add(mnKnn);
		
		ActionListener alMenu = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for( Classe umaClasse : Classe.values() ) {
					if( e.getActionCommand().equals( umaClasse.toString() ) )
						clickKNN( umaClasse );
				}
			}
		};
		
		Classe asClasses[] = Classe.values();
		JMenuItem umMenu;
		for( Classe umaClasse : asClasses ) {
			umMenu = new JMenuItem( umaClasse.name() );
			umMenu.addActionListener( alMenu );
			mnKnn.add( umMenu );
		}
		
		contentPane = new JDesktopPane();
		contentPane.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}

