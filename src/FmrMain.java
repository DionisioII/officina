import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FmrMain extends JFrame {
	
	private JPanel contentPane;
	private JLabel presentazione;
	private JButton Clienti;
	private JButton Fornitori;
	private JButton Automezzi;
	private JButton Interventi;
	private JButton Ricambi;
	
	
	
	public FmrMain(){
		super("Gestione Officina Meccanica");
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(70, 70, 500, 450);
		
		
		presentazione=new JLabel("Software per la gestione di un officina");
		getContentPane().add(presentazione,BorderLayout.NORTH);
		
		contentPane=new JPanel();
		contentPane.setLayout(new FlowLayout());
		
				
		
	    Clienti= new JButton();
	    Clienti.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) {
	    		FmrClienti SezioneClienti= new FmrClienti();
	    		
	    		SezioneClienti.setVisible(true);
	    	}
	    });
		Clienti.setText("Clienti");
		
		contentPane.add(Clienti);
		
		
		
		 Fornitori= new JButton();
		 Fornitori.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		FmrFornitori SezioneFornitori=new FmrFornitori();
				SezioneFornitori.setVisible(true);
		 	}
		 });
		Fornitori.setText("Fornitori");
		
		contentPane.add(Fornitori);
		
		
		Automezzi= new JButton();
		Automezzi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				FmrAutomezzi SezioneMezzi=new FmrAutomezzi();
				SezioneMezzi.setVisible(true);
			}
		});
		Automezzi.setText("Automezzi");
		
		contentPane.add(Automezzi);
		
		Ricambi= new JButton();
		Ricambi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FmrRicambi SezioneRicambi=new FmrRicambi();
				SezioneRicambi.setVisible(true);
			}
		});
		Ricambi.setText("Ricambi");
		
		contentPane.add(Ricambi);
		
		Interventi= new JButton();
		Interventi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FmrInterventi SezioneInterventi=new FmrInterventi();
				SezioneInterventi.setVisible(true);
			}
		});
		Interventi.setText("Interventi");
		
		contentPane.add(Interventi);

		
		
		
		


		getContentPane().add(contentPane);
		setVisible(true);
		
	}

}
