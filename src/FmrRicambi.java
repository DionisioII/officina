import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.BorderLayout;

import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


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

import javax.swing.JTextPane;
import javax.swing.JList;

public class FmrRicambi extends JFrame {
	private DBconnect con;
	private String Fornitore;
	private JList<String> listFornitore;
	private JButton btnAzzera;
	private JButton btnCancella;
	private JButton btnModifica;
	private JButton btnAggiungi;
	private JTextPane txtpnDescrizione;
	private boolean add;
	//private int cod_fornitore;
	private static JButton btnEdit;
	private static JTable table;
	private JTextField txtCodice;
	private JTextField txtTipologia;
	private JTextField txtModello;
	private JTextField txtMarca;
	private JTextField txtAccessori;
	private JTextField txtPrezzo;
	private JTextField txtGiacenza;
	public FmrRicambi() {
		getContentPane().setLayout(null);
		setBounds(90,90, 900, 600);
		//cod_fornitore=0;
		con=new DBconnect();
		add=true;
		
		Tabella();
		
		Anagrafica();
	}
	
	private void Tabella(){
		JPanel tabella = new JPanel();
		tabella.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "TabellaRicambi", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabella.setBounds(21, 11, 726, 197);
		getContentPane().add(tabella);
		tabella.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 26, 594, 129);
		tabella.add(scrollPane);
		
		DefaultTableModel ArticoliModel=con.getArticoliModel();
		
		table = new JTable(ArticoliModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnEdit.setEnabled(true);
			}
		});
		scrollPane.setViewportView(table);
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCodice.setText((String) table.getValueAt(table.getSelectedRow(), 0));
				txtTipologia.setText((String) table.getValueAt(table.getSelectedRow(), 1));
				txtMarca.setText((String) table.getValueAt(table.getSelectedRow(), 2));
				txtModello.setText((String) table.getValueAt(table.getSelectedRow(), 3));
				txtAccessori.setText((String) table.getValueAt(table.getSelectedRow(), 4));
				txtpnDescrizione.setText((String) table.getValueAt(table.getSelectedRow(), 5));
				txtPrezzo.setText(Double.toString((double) table.getValueAt(table.getSelectedRow(), 6)));
				txtGiacenza.setText(Integer.toString((int) table.getValueAt(table.getSelectedRow(), 7)));
				
				add=false;
				listFornitore.setEnabled(true);
				String m=con.getNomeFornitore((int) table.getValueAt(table.getSelectedRow(),8));
				listFornitore.setSelectedValue(m, true);
				btnAzzera.setEnabled(true);
				btnModifica.setEnabled(true);
				btnCancella.setEnabled(true);
				btnAggiungi.setEnabled(false);
				
			}
		});
		btnEdit.setEnabled(false);
		btnEdit.setBounds(627, 80, 89, 23);
		tabella.add(btnEdit);
	}
	
	public void Anagrafica(){
		JPanel anagrafica = new JPanel();
		anagrafica.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Anagrafica", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		anagrafica.setBounds(21, 219, 590, 192);
		getContentPane().add(anagrafica);
		anagrafica.setLayout(null);
		
		JLabel lblCodice = new JLabel("Codice");
		lblCodice.setBounds(10, 24, 46, 14);
		anagrafica.add(lblCodice);
		
		JLabel lblTipologia = new JLabel("tipologia");
		lblTipologia.setBounds(10, 54, 46, 14);
		anagrafica.add(lblTipologia);
		
		JLabel lblMarca = new JLabel("marca");
		lblMarca.setBounds(10, 81, 46, 14);
		anagrafica.add(lblMarca);
		
		JLabel lblModello = new JLabel("modello");
		lblModello.setBounds(10, 106, 46, 14);
		anagrafica.add(lblModello);
		
		JLabel lblAccessori = new JLabel("Accessori");
		lblAccessori.setBounds(10, 132, 46, 14);
		anagrafica.add(lblAccessori);
		
		JLabel lblDescrizione = new JLabel("descrizione");
		lblDescrizione.setBounds(187, 54, 74, 14);
		anagrafica.add(lblDescrizione);
		
		JLabel lblPrezzo = new JLabel("prezzo");
		lblPrezzo.setBounds(10, 157, 46, 14);
		anagrafica.add(lblPrezzo);
		
		JLabel lblGiacenza = new JLabel("giacenza");
		lblGiacenza.setBounds(194, 24, 46, 14);
		anagrafica.add(lblGiacenza);
		
		txtCodice = new JTextField();
		txtCodice.setBounds(69, 21, 86, 20);
		txtCodice.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				//if(tFCodice.getText()=="")btnAggiungi.setEnabled(false);
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(add) {btnAggiungi.setEnabled(true);
						 listFornitore.setEnabled(true);}
				
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(txtCodice.getText().length()<1){btnAggiungi.setEnabled(false);
													listFornitore.setEnabled(false);}
				
			}
		});
		anagrafica.add(txtCodice);
		txtCodice.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(271, 54, 84, 58);
		anagrafica.add(scrollPane_1);
		
		txtpnDescrizione = new JTextPane();
		scrollPane_1.setViewportView(txtpnDescrizione);
		
		txtTipologia = new JTextField();
		txtTipologia.setText("");
		txtTipologia.setBounds(69, 51, 86, 20);
		anagrafica.add(txtTipologia);
		txtTipologia.setColumns(10);
		
		txtModello = new JTextField();
		txtModello.setText("");
		txtModello.setBounds(69, 103, 86, 20);
		anagrafica.add(txtModello);
		txtModello.setColumns(10);
		
		txtMarca = new JTextField();
		txtMarca.setBounds(69, 78, 86, 20);
		anagrafica.add(txtMarca);
		txtMarca.setColumns(10);
		
		txtAccessori = new JTextField();
		txtAccessori.setText("");
		txtAccessori.setBounds(69, 129, 86, 20);
		anagrafica.add(txtAccessori);
		txtAccessori.setColumns(10);
		
		txtPrezzo = new JTextField();
		txtPrezzo.setText("");
		txtPrezzo.setBounds(69, 154, 86, 20);
		anagrafica.add(txtPrezzo);
		txtPrezzo.setColumns(10);
		
		txtGiacenza = new JTextField();
		txtGiacenza.setBounds(269, 21, 86, 20);
		anagrafica.add(txtGiacenza);
		txtGiacenza.setColumns(10);
		
		btnAzzera = new JButton("Azzera");
		btnAzzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Azzera();
				TableRefresh();
			}
		});
		btnAzzera.setEnabled(false);
		btnAzzera.setBounds(451, 24, 89, 23);
		anagrafica.add(btnAzzera);
		
		btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String[] data= new String[9];
				data[0]=txtCodice.getText();
				data[1]=txtTipologia.getText();
				data[2]=txtMarca.getText();
				data[3]=txtModello.getText();
				data[4]=txtAccessori.getText();
				data[5]=txtpnDescrizione.getText();
				data[6]=txtPrezzo.getText();
				data[7]=txtGiacenza.getText();
				if(Fornitore!=" ")
				data[8]=Fornitore;
				else	data[8]="0";
				con.modificaArticolo(data);
				Azzera();
				TableRefresh();
			}
		});
		btnModifica.setEnabled(false);
		btnModifica.setBounds(451, 50, 89, 23);
		anagrafica.add(btnModifica);
		
		btnCancella = new JButton("Cancella");
		btnCancella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cod =txtCodice.getText();
				con.cancellaArticolo(cod);
				Azzera();
				TableRefresh();				
			}
		});
		btnCancella.setEnabled(false);
		btnCancella.setBounds(451, 77, 89, 23);
		anagrafica.add(btnCancella);
		
		btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] data= new String[9];
				data[0]=txtCodice.getText();
				data[1]=txtTipologia.getText();
				data[2]=txtMarca.getText();
				data[3]=txtModello.getText();
				data[4]=txtAccessori.getText();
				data[5]=txtpnDescrizione.getText();
				if(txtPrezzo.getText()=="")
				data[6]=txtPrezzo.getText();
				else
					data[6]="0";
				if(txtGiacenza.getText()=="")
				data[7]=txtGiacenza.getText();
				else
					data[7]="0";
				if(Fornitore!=" ")
					data[8]=Fornitore;
					else	data[8]="0";
				con.insertArticolo(data);
				Azzera();
				add=true;
				TableRefresh();
			}
		});
		btnAggiungi.setEnabled(false);
		btnAggiungi.setBounds(451, 153, 89, 23);
		anagrafica.add(btnAggiungi);
		
		JLabel lblFornitore = new JLabel("Fornitore");
		lblFornitore.setBounds(194, 157, 46, 14);
		anagrafica.add(lblFornitore);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(271, 157, 114, 14);
		anagrafica.add(scrollPane);
		
		 String[] names= con.getFornitoriNames().toArray(new String[con.getFornitoriNames().size()]);
		final String[] codes=con.getFornitoriCodes().toArray(new String[con.getFornitoriCodes().size()]);
		listFornitore = new JList<String>(names);
		listFornitore.setEnabled(false);
		scrollPane.setViewportView(listFornitore);
		listFornitore.setVisibleRowCount(1);
		listFornitore.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listFornitore.addListSelectionListener(
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent event){
						Fornitore=codes[listFornitore.getSelectedIndex()];
					}
				}
				
				);
		
	}
	
	private void Azzera(){
		txtCodice.setText("");
		txtTipologia.setText("");
		txtMarca.setText("");
		txtModello.setText("");
		txtAccessori.setText("");
		txtpnDescrizione.setText("");
		txtPrezzo.setText("");
		txtGiacenza.setText("");
		//cod_fornitore=null;
		 btnModifica.setEnabled(false);
		 btnCancella.setEnabled(false);
		 btnAzzera.setEnabled(false);
		 add=true;
		 listFornitore.setEnabled(false);
		 btnEdit.setEnabled(false);
		
	}
	
	private void TableRefresh(){
		DefaultTableModel ArticoliModel= con.getArticoliModel();
		table.setModel(ArticoliModel);
		
	}
	
	public void visualizzaPezzo(String cod){
		for(int i=0;i< table.getRowCount();i++){
			if(((String)table.getValueAt(i,0)).equals(cod))
				table.setRowSelectionInterval(i,i);
			btnEdit.setEnabled(true);
			btnEdit.doClick();
		}
	}
}
