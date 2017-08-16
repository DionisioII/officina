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



public class FmrFornitori extends JFrame {
	
	private JButton btnModifica;
	private JButton btnAggiungi;
	private JButton btnCancella;
	private JButton btnAzzera;
	private int cod_fornitore;
	private DBconnect con;
	private boolean add;
	private JTable tableFornitori;
	private JTextField txtNomeditta;
	private JTextField txtNomecontatto;
	private JTextField txtIban;
	private JTextField txtModTrasporto;
	private JTextField txtModPagamento;
	private JButton btnEdit;
	private JPanel Telefono;
	private JTable tableContatti;
	private JScrollPane scrollPane_1;
	private JPanel Articoli;
	private JTable tableArticoli;
	private JScrollPane scrollPane_2;
	private JButton btnCancellaRecapito;
	private JButton btnAggiungiRecapito;
	private JButton btnAggiungicancella;
	
	
	
	public FmrFornitori() {
		getContentPane().setLayout(null);
		con=new DBconnect();
		add=true;
		
		JPanel Tabella = new JPanel();
		Tabella.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Fornitori", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Tabella.setBounds(27, 11, 907, 170);
		getContentPane().add(Tabella);
		Tabella.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 29, 522, 92);
		Tabella.add(scrollPane);
		
		DefaultTableModel fornitoriModel=con.getFornitoriModel();
		tableFornitori = new JTable(fornitoriModel);
		tableFornitori.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnEdit.setEnabled(true);
				
			}
		});
		scrollPane.setViewportView(tableFornitori);
		
		btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add=false;
				cod_fornitore=(int) tableFornitori.getValueAt(tableFornitori.getSelectedRow(), 0);
				String nome_ditta= (String) tableFornitori.getValueAt(tableFornitori.getSelectedRow(), 1);
				String nome_contatto=(String) tableFornitori.getValueAt(tableFornitori.getSelectedRow(),2);
				String Iban=(String) tableFornitori.getValueAt(tableFornitori.getSelectedRow(),3);
				String ModTrasporto= (String) tableFornitori.getValueAt(tableFornitori.getSelectedRow(),4);
				String ModPagamento= (String) tableFornitori.getValueAt(tableFornitori.getSelectedRow(),5);
				txtNomeditta.setText(nome_ditta);
				txtNomecontatto.setText(nome_contatto);
				txtIban.setText(Iban);
				txtModTrasporto.setText(ModTrasporto);
				txtModPagamento.setText(ModPagamento);
				btnAzzera.setEnabled(true);
				btnModifica.setEnabled(true);
				btnCancella.setEnabled(true);
				btnAggiungiRecapito.setEnabled(true);
				DefaultTableModel contatti=con.getRecapitiClienteModel(Integer.toString(cod_fornitore));
				tableContatti.setModel(contatti);
				DefaultTableModel articoli=con.getArticoliFornitoreModel(cod_fornitore);
				tableArticoli.setModel(articoli);
			}
		});
		btnEdit.setBounds(690, 54, 89, 23);
		Tabella.add(btnEdit);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Anagrafica", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(27, 202, 561, 258);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		txtNomeditta = new JTextField();
		txtNomeditta.getDocument().addDocumentListener(new DocumentListener() {
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
				if(txtNomeditta.getText().length()<1)btnAggiungi.setEnabled(false);
				
			}
		});
		txtNomeditta.setText("");
		txtNomeditta.setBounds(154, 46, 86, 20);
		
		panel.add(txtNomeditta);
		txtNomeditta.setColumns(10);
		
		JLabel lblNomeDitta = new JLabel("Nome Ditta");
		lblNomeDitta.setBounds(10, 49, 65, 14);
		panel.add(lblNomeDitta);
		
		JLabel lblNomeContatto = new JLabel("Nome Contatto");
		lblNomeContatto.setBounds(10, 90, 86, 14);
		panel.add(lblNomeContatto);
		
		JLabel lblIban = new JLabel("Iban");
		lblIban.setBounds(10, 128, 46, 14);
		panel.add(lblIban);
		
		JLabel lblModalitTrasporto = new JLabel("Modalit\u00E0 trasporto");
		lblModalitTrasporto.setBounds(10, 168, 95, 14);
		panel.add(lblModalitTrasporto);
		
		JLabel lblModalitPagamento = new JLabel("Modalit\u00E0 pagamento");
		lblModalitPagamento.setBounds(10, 210, 106, 14);
		panel.add(lblModalitPagamento);
		
		txtNomecontatto = new JTextField();
		txtNomecontatto.setBounds(154, 87, 86, 20);
		panel.add(txtNomecontatto);
		txtNomecontatto.setColumns(10);
		
		txtIban = new JTextField();
		txtIban.setBounds(154, 125, 86, 20);
		panel.add(txtIban);
		txtIban.setColumns(10);
		
		txtModTrasporto = new JTextField();
		txtModTrasporto.setText("");
		txtModTrasporto.setBounds(154, 165, 86, 20);
		panel.add(txtModTrasporto);
		txtModTrasporto.setColumns(10);
		
		txtModPagamento = new JTextField();
		txtModPagamento.setText("");
		txtModPagamento.setBounds(154, 207, 86, 20);
		panel.add(txtModPagamento);
		txtModPagamento.setColumns(10);
		
		btnAzzera = new JButton("Azzera");
		btnAzzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Azzera();
				add=true;
				
			}
		});
		btnAzzera.setEnabled(false);
		btnAzzera.setBounds(367, 49, 89, 23);
		panel.add(btnAzzera);
		
		 btnModifica = new JButton("Modifica");
		 btnModifica.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		String[] data= new String[8];
		 		data[0]=Integer.toString(cod_fornitore);
		 		data[1]=txtNomeditta.getText();
		 		data[2]=txtNomecontatto.getText();
		 		data[3]=txtIban.getText();
		 		data[4]=txtModTrasporto.getText();
		 		data[5]=txtModPagamento.getText();
		 		con.modificaFornitore(data);
		 		Azzera();
		 		tableRefresh();
		 		add=true;
		 	}

			
		 });
		btnModifica.setEnabled(false);
		btnModifica.setBounds(367, 90, 89, 23);
		panel.add(btnModifica);
		
		btnCancella = new JButton("Cancella");
		btnCancella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add=true;
				String cod=Integer.toString(cod_fornitore);
				con.cancellaFornitore(cod);
				Azzera();
				tableRefresh();
			}
		});
		btnCancella.setEnabled(false);
		btnCancella.setBounds(367, 128, 89, 23);
		panel.add(btnCancella);
		
		btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] data= new String[5];
		 		
		 		data[0]=txtNomeditta.getText();
		 		data[1]=txtNomecontatto.getText();
		 		data[2]=txtIban.getText();
		 		data[3]=txtModTrasporto.getText();
		 		data[4]=txtModPagamento.getText();
		 		con.insertFornitore(data);
		 		Azzera();
		 		tableRefresh();
			}
		});
		btnAggiungi.setEnabled(false);
		btnAggiungi.setBounds(367, 206, 89, 23);
		panel.add(btnAggiungi);
		
		Telefono = new JPanel();
		Telefono.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Contatti", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Telefono.setBounds(598, 202, 336, 258);
		getContentPane().add(Telefono);
		Telefono.setLayout(null);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(23, 28, 180, 154);
		Telefono.add(scrollPane_1);
		
		tableContatti = new JTable();
		tableContatti.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnCancellaRecapito.setEnabled(true);
				
			}
		});
		scrollPane_1.setViewportView(tableContatti);
		
		btnCancellaRecapito = new JButton("Cancella");
		btnCancellaRecapito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String label=(String) tableContatti.getValueAt(tableContatti.getSelectedRow(), 0);
				String value=(String) tableContatti.getValueAt(tableContatti.getSelectedRow(), 1);
				con.cancellaRecapito(Integer.toString(cod_fornitore),label,value);
				DefaultTableModel newModel=con.getRecapitiClienteModel(Integer.toString(cod_fornitore));
				tableContatti.setModel(newModel);
			}
		});
		btnCancellaRecapito.setEnabled(false);
		btnCancellaRecapito.setBounds(225, 58, 89, 23);
		Telefono.add(btnCancellaRecapito);
		
		btnAggiungiRecapito = new JButton("Aggiungi");
		btnAggiungiRecapito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Label=JOptionPane.showInputDialog("Inserisci il tipo di contatto:");
				String Value=JOptionPane.showInputDialog("INserire il contatto:");
				con.aggiungiContatto(Integer.toString(cod_fornitore),Label,Value);
				DefaultTableModel newModel=con.getRecapitiClienteModel(Integer.toString(cod_fornitore));
				tableContatti.setModel(newModel);
			}
		});
		btnAggiungiRecapito.setEnabled(false);
		btnAggiungiRecapito.setBounds(225, 93, 89, 23);
		Telefono.add(btnAggiungiRecapito);
		
		Articoli = new JPanel();
		Articoli.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Articoli", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Articoli.setBounds(27, 479, 506, 228);
		getContentPane().add(Articoli);
		Articoli.setLayout(null);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(23, 40, 295, 125);
		Articoli.add(scrollPane_2);
		
		tableArticoli = new JTable();
		tableArticoli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnAggiungicancella.setEnabled(true);
				
			}
		});
		scrollPane_2.setViewportView(tableArticoli);
		
		btnAggiungicancella = new JButton("Aggiungi/cancella");
		btnAggiungicancella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FmrRicambi Ricambi=new FmrRicambi();
				Ricambi.visualizzaPezzo((String)tableArticoli.getValueAt(tableArticoli.getSelectedRow(),0));
				Ricambi.setVisible(true);
			}
		});
		btnAggiungicancella.setEnabled(false);
		btnAggiungicancella.setBounds(377, 85, 89, 23);
		Articoli.add(btnAggiungicancella);
		setBounds(90,90, 1300, 800);
		con=new DBconnect();
	}
	
	private void tableRefresh() {
		DefaultTableModel model= con.getFornitoriModel();
		tableFornitori.setModel(model);
		
	}

	private void Azzera() {
		txtNomeditta.setText("");
		txtNomecontatto.setText("");
		txtIban.setText("");
		txtModTrasporto.setText("");
		txtModPagamento.setText("");
		btnAzzera.setEnabled(false);
		btnModifica.setEnabled(false);
		btnCancella.setEnabled(false);	
		
	}
}
