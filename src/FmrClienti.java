import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.BorderLayout;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FmrClienti extends JFrame{
	
	private JPanel Tabella;
	private JPanel Anagrafica;
	private JScrollPane scrollPane;
	private JButton btnVisualizzamodifica;
	private JTable table;
	private JTextField tFCodice;
	private JTextField tFNome;
	private JTextField tFCognome;
	private JTextField tFIndirizzo;
	private JTextField tFCittà;
	private JTextField tFCategoria;
	private JTextField tFPagamento;
	private JTextField tFInformazioni;
	private String codClient;
	
	private JButton btnAggiungi;
	private JButton btnAggiungiVeicolo;
	private JButton btnModifica;
	private JButton btnCancella;
	private JButton btnAzzera;
	private boolean add;
	private DBconnect con;
	private JTable tableMezzi;
	private JTable tableRecapiti;
	private JScrollPane scrollPane_2;
	private JTable tableInterventi;
	private JButton btnAggiungicancella;
	private JButton btnCancella_1;
	private JButton btnAggiungiRecapito;
	
	public FmrClienti(){
		getContentPane().setLayout(null);
		
		setBounds(90,90, 1100, 800);
		con=new DBconnect();
		add=true;
		
		Tabella();
		
		Anagrafica();
		
		
		Recapiti();
		
		ParcoMezzi();
		
		Interventi();
		
	}
		
		private void Tabella(){
			Tabella = new JPanel();
			Tabella.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Tabella", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			Tabella.setBounds(10, 11, 935, 185);
			getContentPane().add(Tabella);
			Tabella.setLayout(null);
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(48, 29, 581, 145);
			Tabella.add(scrollPane);
			
			
			DefaultTableModel clientiModel= con.getClientiModel();
			
			btnVisualizzamodifica = new JButton("edit");
			btnVisualizzamodifica.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					add=false;
					String codice = (String) table.getValueAt(table.getSelectedRow(), 0);
					String nome = (String) table.getValueAt(table.getSelectedRow(), 1);
				    String cognome = (String) table.getValueAt(table.getSelectedRow(),2);
				    String indirizzo = (String) table.getValueAt(table.getSelectedRow(), 3);
				    String città = (String) table.getValueAt(table.getSelectedRow(), 4);
				    String categoria = (String) table.getValueAt(table.getSelectedRow(), 5);
				    String modalità_pagamento = (String) table.getValueAt(table.getSelectedRow(), 6);
				    String al_informazioni = (String) table.getValueAt(table.getSelectedRow(), 7);
				    codClient=codice;
				    tFCodice.setText(codice);
				    tFNome.setText(nome);
				    tFCognome.setText(cognome);
				    tFIndirizzo.setText(indirizzo);
				    tFCittà.setText(città);
				    tFCategoria.setText(categoria);
				    tFPagamento.setText(modalità_pagamento);
				    tFInformazioni.setText(al_informazioni);
				    btnModifica.setEnabled(true);
				    btnCancella.setEnabled(true);
				    btnAzzera.setEnabled(true);
				    btnAggiungiRecapito.setEnabled(true);
				    DefaultTableModel modelMezzi= con.getMezziClienteModel((String) table.getValueAt(table.getSelectedRow(), 0));
				    tableMezzi.setModel(modelMezzi);
				    DefaultTableModel modelRecapiti=con.getRecapitiClienteModel((String) table.getValueAt(table.getSelectedRow(), 0));
				    tableRecapiti.setModel(modelRecapiti);
				    DefaultTableModel modelInterventi=con.getInterventiClienteModel((String) table.getValueAt(table.getSelectedRow(), 0));
				    tableInterventi.setModel(modelInterventi);
				}
			});
			
			
			btnVisualizzamodifica.setEnabled(false);
			btnVisualizzamodifica.setBounds(740, 80, 121, 23);
			Tabella.add(btnVisualizzamodifica);
			
			table = new JTable(clientiModel);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					btnVisualizzamodifica.setEnabled(true);
					
				}
			});
			scrollPane.setViewportView(table);
			
			
		}
		
		private void Anagrafica(){

	    Anagrafica = new JPanel();
		Anagrafica.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Anagrafica", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Anagrafica.setBounds(10, 207, 627, 192);
		getContentPane().add(Anagrafica);
		Anagrafica.setLayout(null);
		
		tFCodice = new JTextField();
		tFCodice.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				//if(tFCodice.getText()=="")btnAggiungi.setEnabled(false);
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(add) btnAggiungi.setEnabled(true);
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(tFCodice.getText().length()<1)btnAggiungi.setEnabled(false);
				
			}
		});
		tFCodice.setBounds(112, 29, 100, 20);
		Anagrafica.add(tFCodice);
		tFCodice.setColumns(10);
		
		JLabel lblCodiceFiscale = new JLabel("codice fiscale");
		lblCodiceFiscale.setBounds(26, 32, 76, 14);
		Anagrafica.add(lblCodiceFiscale);
		
		JLabel LabelNome = new JLabel("nome");
		LabelNome.setBounds(25, 57, 64, 14);
		Anagrafica.add(LabelNome);
		
		JLabel LabelCognome = new JLabel("cognome");
		LabelCognome.setBounds(25, 82, 46, 14);
		Anagrafica.add(LabelCognome);
		
		JLabel LabelIndirizzo = new JLabel("Indirizzo");
		LabelIndirizzo.setBounds(25, 107, 46, 14);
		Anagrafica.add(LabelIndirizzo);
		
		JLabel LabelCittà = new JLabel("citt\u00E0");
		LabelCittà.setBounds(26, 132, 46, 14);
		Anagrafica.add(LabelCittà);
		
		tFNome = new JTextField();
		tFNome.setBounds(112, 54, 100, 20);
		Anagrafica.add(tFNome);
		tFNome.setColumns(10);
		
		tFCognome = new JTextField();
		tFCognome.setBounds(112, 79, 100, 20);
		Anagrafica.add(tFCognome);
		tFCognome.setColumns(10);
		
		tFIndirizzo = new JTextField();
		tFIndirizzo.setBounds(112, 104, 100, 20);
		Anagrafica.add(tFIndirizzo);
		tFIndirizzo.setColumns(10);
		
		tFCittà = new JTextField();
		tFCittà.setBounds(112, 129, 100, 20);
		Anagrafica.add(tFCittà);
		tFCittà.setColumns(10);
		
		JLabel LabelCategoria = new JLabel("categoria");
		LabelCategoria.setBounds(222, 32, 46, 14);
		Anagrafica.add(LabelCategoria);
		
		tFCategoria = new JTextField();
		tFCategoria.setText("");
		tFCategoria.setBounds(335, 29, 100, 20);
		Anagrafica.add(tFCategoria);
		tFCategoria.setColumns(10);
		
		JLabel lblModpagamento = new JLabel("mod_pagamento");
		lblModpagamento.setBounds(222, 57, 89, 14);
		Anagrafica.add(lblModpagamento);
		
		tFPagamento = new JTextField();
		tFPagamento.setBounds(335, 54, 100, 20);
		Anagrafica.add(tFPagamento);
		tFPagamento.setColumns(10);
		
		JLabel lblAltreInformazioni = new JLabel("altre informazioni");
		lblAltreInformazioni.setBounds(222, 82, 89, 14);
		Anagrafica.add(lblAltreInformazioni);
		
		tFInformazioni = new JTextField();
		tFInformazioni.setBounds(335, 79, 100, 67);
		Anagrafica.add(tFInformazioni);
		tFInformazioni.setColumns(10);
		
		btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] data= new String[8];
				data[0]=tFCodice.getText();
				data[1]=tFNome.getText();
				data[2]=tFCognome.getText();
				data[3]=tFIndirizzo.getText();
				data[4]=tFCittà.getText();
				data[5]=tFCategoria.getText();
				data[6]=tFPagamento.getText();
				data[7]=tFInformazioni.getText();
				con.modificaCliente(data);
				azzera();
				tableRefresh();
			}
		});
		btnModifica.setEnabled(false);
		btnModifica.setBounds(472, 53, 89, 23);
		Anagrafica.add(btnModifica);
		
		btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String[] data= new String[8];
				data[0]=tFCodice.getText();
				data[1]=tFNome.getText();
				data[2]=tFCognome.getText();
				data[3]=tFIndirizzo.getText();
				data[4]=tFCittà.getText();
				data[5]=tFCategoria.getText();
				data[6]=tFPagamento.getText();
				data[7]=tFInformazioni.getText();
				
				con.insertCliente(data);
				azzera();
				add=true;
				tableRefresh();
			}
		});
		btnAggiungi.setEnabled(false);
		btnAggiungi.setBounds(472, 128, 89, 23);
		Anagrafica.add(btnAggiungi);
		
		btnCancella = new JButton("Cancella");
		btnCancella.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String cod =tFCodice.getText();
				con.cancellaCliente(cod);
				azzera();
				tableRefresh();				
			}
		});
		btnCancella.setEnabled(false);
		btnCancella.setBounds(472, 78, 89, 23);
		Anagrafica.add(btnCancella);
		
		btnAzzera = new JButton("Azzera");
		btnAzzera.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				azzera();
				tableRefresh();
				
			}
		});
		btnAzzera.setEnabled(false);
		btnAzzera.setBounds(472, 28, 89, 23);
		Anagrafica.add(btnAzzera);
	}

		private void Recapiti(){
			JPanel Recapiti = new JPanel();
			Recapiti.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Recapiti", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			Recapiti.setBounds(647, 207, 298, 192);
			getContentPane().add(Recapiti);
			Recapiti.setLayout(null);
			
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(20, 36, 151, 123);
			Recapiti.add(scrollPane_2);
			
			tableRecapiti = new JTable();
			tableRecapiti.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					btnCancella_1.setEnabled(true);
					
				}
			});
			scrollPane_2.setViewportView(tableRecapiti);
			
			btnCancella_1 = new JButton("cancella");
			btnCancella_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String label=(String) tableRecapiti.getValueAt(tableRecapiti.getSelectedRow(), 0);
					String value=(String) tableRecapiti.getValueAt(tableRecapiti.getSelectedRow(), 1);
					con.cancellaRecapito(codClient,label,value);
					DefaultTableModel newModel=con.getRecapitiClienteModel(codClient);
					tableRecapiti.setModel(newModel);
				}
			});
			btnCancella_1.setEnabled(false);
			btnCancella_1.setBounds(181, 49, 89, 23);
			Recapiti.add(btnCancella_1);
			
			btnAggiungiRecapito = new JButton("aggiungi");
			btnAggiungiRecapito.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String Label=JOptionPane.showInputDialog("Inserisci il tipo di contatto:");
					String Value=JOptionPane.showInputDialog("INserire il contatto:");
					con.aggiungiContatto(codClient,Label,Value);
					DefaultTableModel newModel=con.getRecapitiClienteModel(codClient);
					tableRecapiti.setModel(newModel);
				}
			});
			btnAggiungiRecapito.setEnabled(false);
			btnAggiungiRecapito.setBounds(181, 83, 89, 23);
			Recapiti.add(btnAggiungiRecapito);
		}

		private void ParcoMezzi(){
			JPanel ParcoMezzi = new JPanel();
			ParcoMezzi.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Mezzi", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			ParcoMezzi.setBounds(10, 410, 627, 245);
			getContentPane().add(ParcoMezzi);
			ParcoMezzi.setLayout(null);
			
			btnAggiungiVeicolo = new JButton("Aggiungi/cancella");
			btnAggiungiVeicolo.setEnabled(false);
			btnAggiungiVeicolo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					FmrAutomezzi auto=new FmrAutomezzi();
					auto.visualizzaMezzo((String)tableMezzi.getValueAt(tableMezzi.getSelectedRow(),0));
					auto.btnEdit.doClick();
					auto.setVisible(true);
					
				}
			});
			btnAggiungiVeicolo.setBounds(425, 85, 116, 23);
			ParcoMezzi.add(btnAggiungiVeicolo);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(10, 53, 371, 146);
			ParcoMezzi.add(scrollPane_1);
			
			tableMezzi = new JTable();
			tableMezzi.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					btnAggiungiVeicolo.setEnabled(true);
					
				}
			});
			scrollPane_1.setViewportView(tableMezzi);
			
		}

		private void Interventi(){
			JPanel Interventi = new JPanel();
			Interventi.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Interventi eseguiti", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			Interventi.setBounds(647, 410, 298, 245);
			getContentPane().add(Interventi);
			Interventi.setLayout(null);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(10, 21, 167, 181);
			Interventi.add(scrollPane_1);
			
			
			
			tableInterventi = new JTable();
			tableInterventi.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					btnAggiungicancella.setEnabled(true);
				}
			});
			scrollPane_1.setViewportView(tableInterventi);
			
			btnAggiungicancella = new JButton("Aggiungi/cancella");
			btnAggiungicancella.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					FmrInterventi interventi=new FmrInterventi();
					interventi.visualizzaIntervento((int)tableInterventi.getValueAt(tableInterventi.getSelectedRow(),0));
					
					interventi.setVisible(true);
				}
			});
			btnAggiungicancella.setEnabled(false);
			btnAggiungicancella.setBounds(188, 66, 100, 23);
			Interventi.add(btnAggiungicancella);
		}

		private void azzera(){
			tFCodice.setText(null);
		    tFNome.setText(null);
		    tFCognome.setText(null);
		    tFIndirizzo.setText(null);
		    tFCittà.setText(null);
		    tFCategoria.setText(null);
		    tFPagamento.setText(null);
		    tFInformazioni.setText(null);
		    btnModifica.setEnabled(false);
		    btnCancella.setEnabled(false);
		    btnAzzera.setEnabled(false);
		    btnAggiungiRecapito.setEnabled(false);
		    btnCancella_1.setEnabled(false);
		    btnAggiungicancella.setEnabled(false);
		    btnAggiungiVeicolo.setEnabled(false);
		    add=true;
		}
		
		private void tableRefresh(){
			
			DefaultTableModel clientiModel= con.getClientiModel();
			table.setModel(clientiModel);
			
			DefaultTableModel voidModel=new DefaultTableModel();
			tableRecapiti.setModel(voidModel);
			tableInterventi.setModel(voidModel);
			tableMezzi.setModel(voidModel);
			
		}
}
