import java.awt.FlowLayout;

import javax.swing.JFrame;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.ListSelectionModel;


public class FmrAutomezzi extends JFrame{
	
	private DBconnect con;
	private String cod;
	private boolean add;
	private DateFormat df ;
	private String tg;
	private String proprietario;
	private JList<String> listClienti;
	private Date immatricolazione;
	private Date data_acquisto;
	private JButton btnModifica;
	private JButton btnAggiungi;
	private JButton btnAzzera;
	private JButton btnAggiungiIncidente;
	private JButton btnAggiungiModifica;
	private JButton btnCancellaIncidente;
	private JButton btnCancella;
	  JButton btnEdit;
	private  JTable mezziTable;
	private JTextField tfTarga;
	private JTextField tfTipo;
	private JTextField tfMarca;
	private JTextField tfModello;
	private JTextField tfTelaio;
	private JTextField tfImmatricolazione;
	private JTextField tfColore;
	private JTextField tfAlimentazione;
	private JTextField tfCavalli;
	private JTextField tfCilindrata;
	private JTextField tfDataAcquisto;
	private JTextField tfPrezzo;
	private JCheckBox chckbxGaranzia;
	private JTextField tfKmL;
	private JTextField tfPortata;
	private JTextField tfKmPercorsi;
	private JTable incidentiTable;
	private JTable interventiTable;
	private JTextField tfAccessori;
	private JScrollPane scrollPane_3;
	
	public FmrAutomezzi(){
		getContentPane().setLayout(null);
		setBounds(90,90, 1300, 800);
		df = new SimpleDateFormat("dd/MM/yyyy");
		add=true;
		con=new DBconnect();
		
		Tabella();
		Dati();
		Incidenti();
		Riparazioni();
		
		
				
		
		
	}
	
	private void Tabella(){
		

		JPanel TabellaPanel = new JPanel();
		TabellaPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Tabella", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		TabellaPanel.setBounds(10, 36, 1224, 201);
		getContentPane().add(TabellaPanel);
		TabellaPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 1084, 128);
		TabellaPanel.add(scrollPane);
		
		mezziTable = new JTable();
		mezziTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnEdit.setEnabled(true);
			}
		});
		scrollPane.setViewportView(mezziTable);
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				add=false;
				String targa= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),0);
				cod=(String) mezziTable.getValueAt(mezziTable.getSelectedRow(),1);
				String tipo= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),2);
				String marca= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),3);
				String modello= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),4);
				String telaio= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),5);
				immatricolazione= (Date) mezziTable.getValueAt(mezziTable.getSelectedRow(),6);
				String colore= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),7);
				String alimentazione= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),8);
				String cavalli= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),9);
				String cilindrata= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),10);
				data_acquisto= (Date) mezziTable.getValueAt(mezziTable.getSelectedRow(),11);
				String prezzo= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),12);
				boolean garanzia= (boolean) mezziTable.getValueAt(mezziTable.getSelectedRow(),13);
				String kmL= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),14);
				String portata= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),15);
				String accessori= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),16);
				String kmP= (String) mezziTable.getValueAt(mezziTable.getSelectedRow(),17);
				tg=targa;
				tfTarga.setText(targa);
				tfTipo.setText(tipo);
				tfMarca.setText(marca);
				tfModello.setText(modello);
				tfTelaio.setText(telaio);
				if(immatricolazione!=null)
				tfImmatricolazione.setText(FmrInterventi.formatDate(immatricolazione.toString(), "yyyy-mm-dd", "dd/mm/yyyy"));
				tfColore.setText(colore);
				tfAlimentazione.setText(alimentazione);
				tfCavalli.setText(cavalli);
				tfCilindrata.setText(cilindrata);
				if(data_acquisto!=null)
				tfDataAcquisto.setText(data_acquisto.toString());
				tfPrezzo.setText(prezzo);
				chckbxGaranzia.setSelected(garanzia);
				tfKmL.setText(kmL);
				tfPortata.setText(portata);
				tfAccessori.setText(accessori);;
				tfKmPercorsi.setText(kmP);
				listClienti.setEnabled(true);
				String m=con.getNomeCliente((String) mezziTable.getValueAt(mezziTable.getSelectedRow(),1));
				listClienti.setSelectedValue(m, true);
				btnAzzera.setEnabled(true);
				btnModifica.setEnabled(true);
				btnCancella.setEnabled(true);
				btnAggiungiIncidente.setEnabled(true);
				DefaultTableModel model= con.getMezzoIncidentiModel(targa);
				incidentiTable.setModel(model);
				DefaultTableModel model1= con.getMezzoRiparazioniModel(targa);
				interventiTable.setModel(model1);
				interventiTable.getColumn("cd").setPreferredWidth(0);
				
				
			}
		});
		
		btnEdit.setEnabled(false);
		btnEdit.setBounds(1104, 89, 89, 23);
		TabellaPanel.add(btnEdit);
		TableRefresh();
		
	}
	
	private void TableRefresh() {
		DefaultTableModel model=con.getMezziModel();
		mezziTable.setModel(model);
		btnEdit.setEnabled(false);
		//incidentiTable.;
	//	interventiTable.getModel().removeTableModelListener(interventiTable);
		//btnModifica.setEnabled(false);
		//btnAggiungi.setEnabled(false);
		//btnCancella.setEnabled(false);
		
	}

	private void Dati(){
		JPanel DatiPanel = new JPanel();
		DatiPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Dati", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		DatiPanel.setBounds(10, 250, 867, 306);
		getContentPane().add(DatiPanel);
		DatiPanel.setLayout(null);
		
		JLabel lblTarga = new JLabel("Targa");
		lblTarga.setBounds(30, 33, 46, 14);
		DatiPanel.add(lblTarga);
		
		JLabel lblTipoMezzo = new JLabel("tipo mezzo");
		lblTipoMezzo.setBounds(30, 58, 71, 14);
		DatiPanel.add(lblTipoMezzo);
		
		JLabel lblMarca = new JLabel("marca");
		lblMarca.setBounds(30, 83, 46, 14);
		DatiPanel.add(lblMarca);
		
		JLabel lblModello = new JLabel("modello");
		lblModello.setBounds(30, 108, 46, 14);
		DatiPanel.add(lblModello);
		
		JLabel lblTelaio = new JLabel("telaio");
		lblTelaio.setBounds(30, 133, 46, 14);
		DatiPanel.add(lblTelaio);
		
		JLabel lblDataImmatricolazione = new JLabel("immatricolazione");
		lblDataImmatricolazione.setBounds(30, 158, 84, 14);
		DatiPanel.add(lblDataImmatricolazione);
		
		JLabel lblColore = new JLabel("colore");
		lblColore.setBounds(30, 193, 46, 14);
		DatiPanel.add(lblColore);
		
		JLabel lblAlimentazione = new JLabel("alimentazione");
		lblAlimentazione.setBounds(30, 218, 71, 14);
		DatiPanel.add(lblAlimentazione);
		
		JLabel lblCavalliFiscali = new JLabel("cavalli fiscali");
		lblCavalliFiscali.setBounds(30, 249, 71, 14);
		DatiPanel.add(lblCavalliFiscali);
		
		JLabel lblCilindrata = new JLabel("cilindrata");
		lblCilindrata.setBounds(30, 274, 46, 14);
		DatiPanel.add(lblCilindrata);
		
		JLabel lblDataAcquisto = new JLabel("data acquisto");
		lblDataAcquisto.setBounds(331, 33, 71, 14);
		DatiPanel.add(lblDataAcquisto);
		
		JLabel lblPrezzoDacquisto = new JLabel("prezzo d'acquisto");
		lblPrezzoDacquisto.setBounds(331, 58, 89, 14);
		DatiPanel.add(lblPrezzoDacquisto);
		
		JLabel lblKml = new JLabel("Km*L");
		lblKml.setBounds(331, 83, 46, 14);
		DatiPanel.add(lblKml);
		
		JLabel lblPortataMassima = new JLabel("portata massima");
		lblPortataMassima.setBounds(331, 108, 89, 14);
		DatiPanel.add(lblPortataMassima);
		
		JLabel lblAccessori = new JLabel("accessori");
		lblAccessori.setBounds(331, 158, 46, 14);
		DatiPanel.add(lblAccessori);
		
		JLabel lblKmPercorsi = new JLabel("km percorsi");
		lblKmPercorsi.setBounds(331, 133, 71, 14);
		DatiPanel.add(lblKmPercorsi);
		
		tfTarga = new JTextField();
		tfTarga.setBounds(139, 30, 136, 20);
		tfTarga.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				//if(tFCodice.getText()=="")btnAggiungi.setEnabled(false);
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(add) {btnAggiungi.setEnabled(true);
						listClienti.setEnabled(true);}
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(tfTarga.getText().length()<1){btnAggiungi.setEnabled(false);
												 listClienti.setEnabled(false);}
			}
		});
		DatiPanel.add(tfTarga);
		tfTarga.setColumns(10);
		
		tfTipo = new JTextField();
		tfTipo.setBounds(139, 55, 136, 20);
		DatiPanel.add(tfTipo);
		tfTipo.setColumns(10);
		
		tfMarca = new JTextField();
		tfMarca.setText("");
		tfMarca.setBounds(139, 80, 136, 20);
		DatiPanel.add(tfMarca);
		tfMarca.setColumns(10);
		
		tfModello = new JTextField();
		tfModello.setText("");
		tfModello.setBounds(139, 105, 136, 20);
		DatiPanel.add(tfModello);
		tfModello.setColumns(10);
		
		tfTelaio = new JTextField();
		tfTelaio.setText("");
		tfTelaio.setBounds(139, 130, 136, 20);
		DatiPanel.add(tfTelaio);
		tfTelaio.setColumns(10);
		
		tfImmatricolazione = new JTextField();
		tfImmatricolazione.setText("");
		tfImmatricolazione.setBounds(139, 158, 136, 20);
		DatiPanel.add(tfImmatricolazione);
		tfImmatricolazione.setColumns(10);
		
		tfColore = new JTextField();
		tfColore.setBounds(139, 190, 136, 20);
		DatiPanel.add(tfColore);
		tfColore.setColumns(10);
		
		tfAlimentazione = new JTextField();
		tfAlimentazione.setBounds(139, 215, 136, 20);
		DatiPanel.add(tfAlimentazione);
		tfAlimentazione.setColumns(10);
		
		tfCavalli = new JTextField();
		tfCavalli.setText("");
		tfCavalli.setBounds(139, 246, 136, 20);
		DatiPanel.add(tfCavalli);
		tfCavalli.setColumns(10);
		
		tfCilindrata = new JTextField();
		tfCilindrata.setText("");
		tfCilindrata.setBounds(139, 271, 136, 20);
		DatiPanel.add(tfCilindrata);
		tfCilindrata.setColumns(10);
		
		tfDataAcquisto = new JTextField();
		tfDataAcquisto.setText("");
		tfDataAcquisto.setBounds(473, 30, 157, 20);
		DatiPanel.add(tfDataAcquisto);
		tfDataAcquisto.setColumns(10);
		
		tfPrezzo = new JTextField();
		tfPrezzo.setText("");
		tfPrezzo.setBounds(473, 55, 157, 20);
		DatiPanel.add(tfPrezzo);
		tfPrezzo.setColumns(10);
		
		chckbxGaranzia = new JCheckBox("garanzia");
		chckbxGaranzia.setBounds(331, 245, 97, 23);
		DatiPanel.add(chckbxGaranzia);
		
		tfKmL = new JTextField();
		tfKmL.setBounds(473, 80, 157, 20);
		DatiPanel.add(tfKmL);
		tfKmL.setColumns(10);
		
		tfPortata = new JTextField();
		tfPortata.setText("");
		tfPortata.setBounds(473, 105, 157, 20);
		DatiPanel.add(tfPortata);
		tfPortata.setColumns(10);
		
		tfKmPercorsi = new JTextField();
		tfKmPercorsi.setBounds(473, 130, 157, 20);
		DatiPanel.add(tfKmPercorsi);
		tfKmPercorsi.setColumns(10);
		
		JLabel lblProprietario = new JLabel("Proprietario");
		lblProprietario.setBounds(331, 196, 71, 14);
		DatiPanel.add(lblProprietario);
		
		tfAccessori = new JTextField();
		tfAccessori.setBounds(472, 155, 158, 20);
		DatiPanel.add(tfAccessori);
		tfAccessori.setColumns(10);
		
		btnAzzera = new JButton("Azzera");
		btnAzzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			add=true;
			azzera();
			
			}
		});
		btnAzzera.setEnabled(false);
		btnAzzera.setBounds(714, 49, 89, 23);
		DatiPanel.add(btnAzzera);
		
		btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				add=true;
				String[] Data= new String[18];
				Data[0]=tfTarga.getText();
				Data[1]=proprietario;
				Data[2]=tfTipo.getText();
				Data[3]=tfMarca.getText();
				Data[4]=tfModello.getText();
				Data[5]=tfTelaio.getText();
				Data[6]=Data[6]=formatDate(tfImmatricolazione.getText(),"dd/mm/yyyy","yyyy-mm-dd");
				Data[7]=tfColore.getText();
				Data[8]=tfAlimentazione.getText();
				Data[9]=tfCavalli.getText();
				Data[10]=tfCilindrata.getText();
				Data[11]=formatDate(tfDataAcquisto.getText(),"dd/mm/yyyy","yyyy-mm-dd");
				Data[12]=tfPrezzo.getText();
				if(chckbxGaranzia.isSelected())
					Data[13]="1";
					else
						Data[13]="0";
				Data[14]=tfKmL.getText();
				Data[15]=tfPortata.getText();
				Data[16]=tfAccessori.getText();
				Data[17]=tfKmPercorsi.getText();
				con.modificaAutomezzo(Data);
				azzera();
							
			}
		});
		btnModifica.setEnabled(false);
		btnModifica.setBounds(714, 79, 89, 23);
		DatiPanel.add(btnModifica);
		
		btnCancella = new JButton("Cancella");
		btnCancella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cod =tfTarga.getText();
				con.cancellaMezzo(cod);
				azzera();
				TableRefresh();			
			}
		});
		btnCancella.setEnabled(false);
		btnCancella.setBounds(714, 108, 89, 23);
		DatiPanel.add(btnCancella);
		
		btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String[] Data= new String[18];
				Data[0]=tfTarga.getText();
				Data[1]=proprietario;
				Data[2]=tfTipo.getText();
				Data[3]=tfMarca.getText();
				Data[4]=tfModello.getText();
				Data[5]=tfTelaio.getText();
				Data[6]=formatDate(tfImmatricolazione.getText(),"dd/mm/yyyy","yyyy-mm-dd");
				Data[7]=tfColore.getText();
				Data[8]=tfAlimentazione.getText();
				Data[9]=tfCavalli.getText();
				Data[10]=tfCilindrata.getText();
				if(Data[10]=="")Data[10]="0";
				Data[11]=formatDate(tfDataAcquisto.getText(),"dd/mm/yyyy","yyyy-mm-dd");
				Data[12]=tfPrezzo.getText();
				if(Data[12]=="")Data[12]="0";
				if(chckbxGaranzia.isSelected())
					Data[13]="1";
					else
						Data[13]="0";
				Data[14]=tfKmL.getText();
				Data[15]=tfPortata.getText();
				if(Data[15]=="")Data[15]="0";
				Data[16]=tfAccessori.getText();
				Data[17]=tfKmPercorsi.getText();
				if(Data[17]=="")Data[17]="0";
				con.insertAutomezzo(Data);
				azzera();
			}
		});
		btnAggiungi.setEnabled(false);
		btnAggiungi.setBounds(714, 214, 89, 23);
		DatiPanel.add(btnAggiungi);
		
		 String[] names= con.getClientiNames().toArray(new String[con.getClientiNames().size()]);
		final String[] codes=con.getClientiCodes().toArray(new String[con.getClientiCodes().size()]);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(473, 191, 157, 20);
		DatiPanel.add(scrollPane_3);
		listClienti = new JList<String>(names);
		listClienti.setEnabled(false);
		scrollPane_3.setViewportView(listClienti);
		listClienti.setVisibleRowCount(1);
		listClienti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listClienti.addListSelectionListener(
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent event){
						proprietario=codes[listClienti.getSelectedIndex()];
					}
				}
				
				);
		
		
	}

	private void Incidenti(){
		
		JPanel IncidentiPanel = new JPanel();
		IncidentiPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Incidenti", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		IncidentiPanel.setBounds(902, 248, 332, 306);
		getContentPane().add(IncidentiPanel);
		IncidentiPanel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(23, 39, 163, 191);
		IncidentiPanel.add(scrollPane_1);
		
		incidentiTable = new JTable();
		incidentiTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnCancellaIncidente.setEnabled(true);
				
			}
		});
		scrollPane_1.setViewportView(incidentiTable);
		
		btnCancellaIncidente = new JButton("cancella");
		btnCancellaIncidente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String data=FmrInterventi.formatDate((String) incidentiTable.getValueAt(incidentiTable.getSelectedRow(), 0), "dd/mm/yyyy","yyyy-mm-dd");
				String value=(String) incidentiTable.getValueAt(incidentiTable.getSelectedRow(), 1);
				con.cancellaIncidente(tg,data,value);
				DefaultTableModel newModel=con.getMezzoIncidentiModel(tg);
				incidentiTable.setModel(newModel);
			}
		});
		btnCancellaIncidente.setEnabled(false);
		btnCancellaIncidente.setBounds(209, 68, 89, 23);
		IncidentiPanel.add(btnCancellaIncidente);
		
		btnAggiungiIncidente = new JButton("Aggiungi incidente");
		btnAggiungiIncidente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String d=JOptionPane.showInputDialog("Inserisci la data(dd/mm/yyyy):");
				String date=FmrInterventi.formatDate(d, "dd/mm/yyyy", "yyyy-mm-dd");
				String Value=JOptionPane.showInputDialog("INserire il motivo:");
				con.aggiungiIncidente(tg,date,Value);
				DefaultTableModel newModel=con.getMezzoIncidentiModel(tg);
				incidentiTable.setModel(newModel);
			}
		});
		btnAggiungiIncidente.setEnabled(false);
		btnAggiungiIncidente.setBounds(209, 110, 89, 23);
		IncidentiPanel.add(btnAggiungiIncidente);
		
	}

	private void Riparazioni(){
		JPanel riparazioniPanel = new JPanel();
		riparazioniPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Riparazioni", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		riparazioniPanel.setBounds(10, 561, 497, 141);
		getContentPane().add(riparazioniPanel);
		riparazioniPanel.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(20, 34, 233, 68);
		riparazioniPanel.add(scrollPane_2);
		
		interventiTable = new JTable();
		interventiTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnAggiungiModifica.setEnabled(true);
				
			}
		});
		scrollPane_2.setViewportView(interventiTable);
		
		btnAggiungiModifica = new JButton("Aggiungi/Modifica");
		btnAggiungiModifica.setBounds(297, 52, 155, 23);
		riparazioniPanel.add(btnAggiungiModifica);
		btnAggiungiModifica.setEnabled(false);
		btnAggiungiModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FmrInterventi interventi=new FmrInterventi();
				interventi.visualizzaIntervento((int)interventiTable.getValueAt(interventiTable.getSelectedRow(),0));
				
				interventi.setVisible(true);
			}
		});
		
	}
	
	private void azzera(){
		tfTarga.setText("");
		tfTipo.setText("");
		tfMarca.setText("");
		tfModello.setText("");
		tfTelaio.setText("");
		tfImmatricolazione.setText("");
		tfColore.setText("");
		tfAlimentazione.setText("");
		tfCavalli.setText("");
		tfCilindrata.setText("");
		tfDataAcquisto.setText("");
		tfPrezzo.setText("");
		chckbxGaranzia.setSelected(false);
		tfKmL.setText("");
		tfPortata.setText("");
		tfAccessori.setText("");;
		tfKmPercorsi.setText("");
		
		btnModifica.setEnabled(false);
	    btnCancella.setEnabled(false);
	    btnAzzera.setEnabled(false);
	    listClienti.setEnabled(false);
	    add=true;
		TableRefresh();
	}
	
	
	public static String formatDate (String date, String initDateFormat, String endDateFormat)  {
		 String parsedDate;
	    java.util.Date initDate;
		try {
			if(date==null) return null;
			initDate = new SimpleDateFormat(initDateFormat).parse(date);
			 SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
			     parsedDate = formatter.format(initDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
			parsedDate=null;
			return parsedDate;
		}
	   

	    return parsedDate;
	}
	
	public  void visualizzaMezzo(String targa){
		
		for(int i=0;i< mezziTable.getRowCount();i++){
			if(((String)mezziTable.getValueAt(i,0)).equals(targa))
				mezziTable.setRowSelectionInterval(i,i);
			btnEdit.setEnabled(true);
			
		}
	}
}
