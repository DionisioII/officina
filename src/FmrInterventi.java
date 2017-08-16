
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListModel;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.ListSelectionModel;

public class FmrInterventi extends JFrame {
	private static JTable tableInterventi;
	private DBconnect con;
	private boolean add;
	private String proprietario;
	private JTextField txtDataRip;
	private JTextField txtLuogo;
	private JTextField txtNomeMeccanico;
	private JTextField txtDataRiconsegna;
	private JTextPane txpMateriale;
	private JCheckBox chckbxSaldato;
	private JTextPane txpDescrizione;
	private JLabel lblDifettiRiscontrati;
	private JTextPane txpDifetti;
	private JTextField txtCosto;
	private String codInt;
	private JTextField txtSconto;
	private JList<String> listProprietari;
	private JList<String> listMezzi;
	private DefaultListModel<String> listModel;
	private JButton btnAggiungi;
	private static JButton btnEdit;
	private JButton btnModifica;
	private JButton btnCancella;
	private JButton btnAzzera;
	
	
	public FmrInterventi() {
		getContentPane().setLayout(null);
		setBounds(90,90, 1100, 800);
		con=new DBconnect();
		add=true;
		listModel=new DefaultListModel<String>();
		
		JPanel Tabella = new JPanel();
		Tabella.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Interventi", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Tabella.setBounds(10, 11, 738, 166);
		getContentPane().add(Tabella);
		Tabella.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 23, 559, 98);
		Tabella.add(scrollPane);
		
		DefaultTableModel model=con.getInterventiModel();
		tableInterventi = new JTable(model);
		tableInterventi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnEdit.setEnabled(true);
			}
		});
		scrollPane.setViewportView(tableInterventi);
		
	    btnEdit = new JButton("Edit");
	    btnEdit.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		azzera();
	    		codInt=(String)Integer.toString( (int)tableInterventi.getValueAt(tableInterventi.getSelectedRow(),0));
	    		String m=con.getNomeCliente((String) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),1));
				listProprietari.setSelectedValue(m, true);
				Date DRip=(Date) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),3);
				if (DRip!=null)
				txtDataRip.setText(formatDate(DRip.toString(),"yyyy-mm-dd","dd/mm/yyyy"));
				txtLuogo.setText((String) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),4));
				txtNomeMeccanico.setText((String) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),5));
				txpDifetti.setText((String) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),6));
	    		txpDescrizione.setText((String) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),7));
	    		txpMateriale.setText((String) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),8));
	    		Date DRic=(Date) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),9);
	    		if(DRic!=null)
	    		txtDataRiconsegna.setText(formatDate(DRic.toString(),"yyyy-mm-dd","dd/mm/yyyy"));
	    		
	    		txtCosto.setText((String) Double.toString((double)tableInterventi.getValueAt(tableInterventi.getSelectedRow(),10)));
	    		txtSconto.setText((String) Double.toString((double)tableInterventi.getValueAt(tableInterventi.getSelectedRow(),11)));
	    		chckbxSaldato.setSelected((boolean) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),12));
	    		btnModifica.setEnabled(true);
	    		btnCancella.setEnabled(true);
	    		btnAzzera.setEnabled(true);
	    		add=false;
	    		
	    	}
	    });
		btnEdit.setBounds(627, 67, 89, 23);
		Tabella.add(btnEdit);
		btnEdit.setEnabled(false);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableRefresh();
			}
		});
		btnRefresh.setBounds(627, 26, 89, 23);
		Tabella.add(btnRefresh);
		
		JButton btnContiInsoluti = new JButton("insolvenze");
		btnContiInsoluti.setBounds(627, 98, 89, 23);
		Tabella.add(btnContiInsoluti);
		
		JButton btnNonRiconsegnati = new JButton("Da riconsegnare");
		btnNonRiconsegnati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel modelFiltrato=con.getInterventiModel(false);
				tableInterventi.setModel(modelFiltrato);	
			}
		});
		btnNonRiconsegnati.setBounds(605, 132, 111, 23);
		Tabella.add(btnNonRiconsegnati);
		btnContiInsoluti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel modelFiltrato=con.getInterventiModel(true);
				tableInterventi.setModel(modelFiltrato);	
			}
		});
		
		JPanel Anagrafica = new JPanel();
		Anagrafica.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Anagrafica", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Anagrafica.setBounds(0, 188, 898, 542);
		getContentPane().add(Anagrafica);
		Anagrafica.setLayout(null);
		
		JLabel lblProprietario = new JLabel("Proprietario");
		lblProprietario.setBounds(10, 35, 67, 14);
		Anagrafica.add(lblProprietario);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(109, 35, 121, 20);
		Anagrafica.add(scrollPane_1);
		
		
		 String[] names= con.getClientiNames().toArray(new String[con.getClientiNames().size()]);
			final String[] codes=con.getClientiCodes().toArray(new String[con.getClientiCodes().size()]);
		listProprietari = new JList<String>(names);
		scrollPane_1.setViewportView(listProprietari);
		listProprietari.setVisibleRowCount(1);
		listProprietari.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listProprietari.addListSelectionListener(
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent event){
						proprietario=codes[listProprietari.getSelectedIndex()];
						String[] mezzi=con.getMezzi(proprietario);
						for(int i=0;i<mezzi.length;i++)
							listModel.add(i,mezzi[i]);
						if(add==false)
							listMezzi.setSelectedValue((String) tableInterventi.getValueAt(tableInterventi.getSelectedRow(),2), true);
						else
							listMezzi.setSelectedIndex(0);
							btnAggiungi.setEnabled(true);
						
						
						
					}
				}
				
				);
		
		JLabel lblMezzo = new JLabel("Mezzo");
		lblMezzo.setBounds(10, 73, 46, 14);
		Anagrafica.add(lblMezzo);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(108, 73, 122, 20);
		Anagrafica.add(scrollPane_2);
		
		listMezzi = new JList<String>(listModel);
		listMezzi.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnAzzera.setEnabled(true);
			}
		});
		
		scrollPane_2.setViewportView(listMezzi);
		
		JLabel lblDataRiparazione = new JLabel("Data riparazione");
		lblDataRiparazione.setBounds(10, 118, 79, 14);
		Anagrafica.add(lblDataRiparazione);
		
		txtDataRip = new JTextField();
		txtDataRip.setBounds(144, 115, 79, 20);
		Anagrafica.add(txtDataRip);
		txtDataRip.setColumns(10);
		
		JLabel lblLuogo = new JLabel("Luogo");
		lblLuogo.setBounds(10, 167, 46, 14);
		Anagrafica.add(lblLuogo);
		
		txtLuogo = new JTextField();
		txtLuogo.setBounds(144, 164, 86, 20);
		Anagrafica.add(txtLuogo);
		txtLuogo.setColumns(10);
		
		JLabel lblNomeMeccanico = new JLabel("Nome meccanico");
		lblNomeMeccanico.setBounds(10, 213, 94, 14);
		Anagrafica.add(lblNomeMeccanico);
		
		txtNomeMeccanico = new JTextField();
		txtNomeMeccanico.setBounds(144, 210, 86, 20);
		Anagrafica.add(txtNomeMeccanico);
		txtNomeMeccanico.setColumns(10);
		
		lblDifettiRiscontrati = new JLabel("Difetti Riscontrati");
		lblDifettiRiscontrati.setBounds(10, 284, 108, 14);
		Anagrafica.add(lblDifettiRiscontrati);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(109, 278, 157, 170);
		Anagrafica.add(scrollPane_3);
		
		txpDifetti = new JTextPane();
		scrollPane_3.setViewportView(txpDifetti);
		
		JLabel lblDescrizizioneIntervento = new JLabel("Descrizizione Intervento");
		lblDescrizizioneIntervento.setBounds(299, 284, 125, 14);
		Anagrafica.add(lblDescrizizioneIntervento);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(425, 284, 155, 168);
		Anagrafica.add(scrollPane_4);
		
		txpDescrizione = new JTextPane();
		scrollPane_4.setViewportView(txpDescrizione);
		
		JLabel lblMaterialeImpiegato = new JLabel("Materiale impiegato");
		lblMaterialeImpiegato.setBounds(613, 284, 104, 14);
		Anagrafica.add(lblMaterialeImpiegato);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(727, 284, 155, 168);
		Anagrafica.add(scrollPane_5);
		
		txpMateriale = new JTextPane();
		scrollPane_5.setViewportView(txpMateriale);
		
		JLabel lblDataRiconsegna = new JLabel("Data riconsegna");
		lblDataRiconsegna.setBounds(320, 35, 86, 14);
		Anagrafica.add(lblDataRiconsegna);
		
		txtDataRiconsegna = new JTextField();
		txtDataRiconsegna.setBounds(451, 32, 86, 20);
		Anagrafica.add(txtDataRiconsegna);
		txtDataRiconsegna.setColumns(10);
		
		JLabel lblCosto = new JLabel("Costo");
		lblCosto.setBounds(320, 73, 46, 14);
		Anagrafica.add(lblCosto);
		
		txtCosto = new JTextField();
		txtCosto.setBounds(451, 70, 86, 20);
		Anagrafica.add(txtCosto);
		txtCosto.setColumns(10);
		
		JLabel lblSconto = new JLabel("Sconto");
		lblSconto.setBounds(320, 118, 46, 14);
		Anagrafica.add(lblSconto);
		
		txtSconto = new JTextField();
		txtSconto.setBounds(451, 115, 86, 20);
		Anagrafica.add(txtSconto);
		txtSconto.setColumns(10);
		
		chckbxSaldato = new JCheckBox("Saldato");
		chckbxSaldato.setBounds(415, 209, 97, 23);
		Anagrafica.add(chckbxSaldato);
		
		btnAzzera = new JButton("Azzera");
		btnAzzera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			azzera();	
			}

			
		});
		btnAzzera.setEnabled(false);
		btnAzzera.setBounds(738, 31, 89, 23);
		Anagrafica.add(btnAzzera);
		
		btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] Data=new String[13];
				Data[0]=codInt;
				Data[1]=proprietario;
				Data[2]=(String) listMezzi.getSelectedValue();
				Data[3]=formatDate(txtDataRip.getText(),"dd/mm/yyyy","yyyy-mm-dd");
				Data[4]=txtLuogo.getText();
				Data[5]=txtNomeMeccanico.getText();
				Data[6]=txpDifetti.getText();
				Data[7]=txpDescrizione.getText();
				Data[8]=txpMateriale.getText();
				Data[9]=formatDate(txtDataRiconsegna.getText(),"dd/mm/yyyy","yyyy-mm-dd");
				
				Data[10]=txtCosto.getText();
				Data[11]=txtSconto.getText();
				if(chckbxSaldato.isSelected())
					Data[12]="1";
					else
						Data[12]="0";
				con.modificaIntervento(Data);
				azzera();
				tableRefresh();
				
			}
		});
		btnModifica.setEnabled(false);
		btnModifica.setBounds(738, 69, 89, 23);
		Anagrafica.add(btnModifica);
		
		btnCancella = new JButton("Cancella");
		btnCancella.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				con.cancellaIntervento(Integer.parseInt(codInt));
				azzera();
				tableRefresh();
			}
		});
		btnCancella.setEnabled(false);
		btnCancella.setBounds(738, 114, 89, 23);
		Anagrafica.add(btnCancella);
		
		btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String[] Data=new String[12];
				Data[0]=proprietario;
				Data[1]=(String) listMezzi.getSelectedValue();
				Data[2]=formatDate(txtDataRip.getText(),"dd/mm/yyyy","yyyy-mm-dd");
				Data[3]=txtLuogo.getText();
				Data[4]=txtNomeMeccanico.getText();
				Data[5]=txpDifetti.getText();
				Data[6]=txpDescrizione.getText();
				Data[7]=txpMateriale.getText();
				Data[8]=formatDate(txtDataRiconsegna.getText(),"dd/mm/yyyy","yyyy-mm-dd");
				
				Data[9]=txtCosto.getText();
				if(Data[9].equals(""))Data[9]="0";
				Data[10]=txtSconto.getText();
				if(Data[10].equals(""))Data[10]="0";
				if(chckbxSaldato.isSelected())
					Data[11]="1";
					else
						Data[11]="0";
				con.insertIntervento(Data);
				azzera();
				tableRefresh();
			}
		});
		btnAggiungi.setEnabled(false);
		btnAggiungi.setBounds(738, 209, 89, 23);
		Anagrafica.add(btnAggiungi);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Filtra", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(758, 11, 316, 166);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAl = new JLabel("al");
		lblAl.setBounds(10, 36, 15, 14);
		panel.add(lblAl);
		
		JLabel lblMeccanico = new JLabel("meccanico");
		lblMeccanico.setBounds(8, 74, 63, 14);
		panel.add(lblMeccanico);
		
		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(10, 106, 46, 14);
		panel.add(lblCliente);
		
		JLabel lblDa = new JLabel("dal");
		lblDa.setBounds(10, 11, 23, 14);
		panel.add(lblDa);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(35, 10, 36, 15);
		panel.add(scrollPane_6);
		
		String[] giorni=new String[]{" ","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		final JList<String> list = new JList<String>(giorni);
		list.setVisibleRowCount(1);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedValue(" ", true);
		scrollPane_6.setViewportView(list);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(82, 10, 36, 16);
		panel.add(scrollPane_7);
		
		String[] mesi= new String[]{" ","01","02","03","04","05","06","07","08","09","10","11","12"};

		final JList<String> list_1 = new JList<String>(mesi);
		list_1.setVisibleRowCount(1);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.setSelectedValue(" ", true);
		scrollPane_7.setViewportView(list_1);
		
		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(131, 10, 60, 16);
		panel.add(scrollPane_8);
		
		String[] anni= new String[]{"2013","2014","2015"};
		final JList<String> list_2 = new JList<String>(anni);
		list_2.setVisibleRowCount(1);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_2.setSelectedValue("2013", true);
		scrollPane_8.setViewportView(list_2);
		
		JScrollPane scrollPane_9 = new JScrollPane();
		scrollPane_9.setBounds(35, 36, 35, 21);
		panel.add(scrollPane_9);
		
		
		final JList<String> list_3 = new JList<String>(giorni);
		list_3.setVisibleRowCount(1);
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_3.setSelectedValue(" ", true);
		scrollPane_9.setViewportView(list_3);
		
		JScrollPane scrollPane_10 = new JScrollPane();
		scrollPane_10.setBounds(83, 35, 36, 21);
		panel.add(scrollPane_10);
		
		final JList<String> list_4 = new JList<String>(mesi);
		list_4.setVisibleRowCount(1);
		list_4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_4.setSelectedValue(" ", true);
		scrollPane_10.setViewportView(list_4);
		
		JScrollPane scrollPane_11 = new JScrollPane();
		scrollPane_11.setBounds(130, 34, 61, 21);
		panel.add(scrollPane_11);
		
		final JList<String> list_5 = new JList<String>(anni);
		list_5.setVisibleRowCount(1);
		list_5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_5.setSelectedValue("2015", true);
		scrollPane_11.setViewportView(list_5);
		
		JScrollPane scrollPane_12 = new JScrollPane();
		scrollPane_12.setBounds(71, 72, 106, 21);
		panel.add(scrollPane_12);
		
		String[] nomiMeccanici=con.getMeccaniciNames().toArray(new String[con.getMeccaniciNames().size()]);
		final JList<String> list_6 = new JList<String>(nomiMeccanici);
		list_6.setVisibleRowCount(1);
		list_6.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_6.setSelectedValue("%", true);
		scrollPane_12.setViewportView(list_6);
		
		JScrollPane scrollPane_13 = new JScrollPane();
		scrollPane_13.setBounds(66, 104, 125, 30);
		panel.add(scrollPane_13);
		
		
		final JList<String> list_7 = new JList<String>(names);
		list_7.setVisibleRowCount(1);
		list_7.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_7.setSelectedValue(" ", true);
		scrollPane_13.setViewportView(list_7);
		
		JButton btnFiltra = new JButton("Filtra");
		btnFiltra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel modelFiltrato=con.getInterventiModel(list.getSelectedValue(), list_1.getSelectedValue(),list_2.getSelectedValue(),(String) list_3.getSelectedValue(),(String) list_4.getSelectedValue(),(String) list_5.getSelectedValue(),(String) list_6.getSelectedValue(),codes[list_7.getSelectedIndex()]);
				tableInterventi.setModel(modelFiltrato);
			}
		});
		btnFiltra.setBounds(217, 32, 89, 23);
		panel.add(btnFiltra);
		
		JButton btnGuadagni = new JButton("Guadagni");
		btnGuadagni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel modelFiltrato=con.getInterventiModel(list.getSelectedValue(), list_1.getSelectedValue(),list_2.getSelectedValue(), list_3.getSelectedValue(), list_4.getSelectedValue(), list_5.getSelectedValue(), list_6.getSelectedValue(),codes[list_7.getSelectedIndex()]);
				tableInterventi.setModel(modelFiltrato);

				JOutput console = new JOutput();
				  console.setEditable(false);
				  System.setOut(console.getPrintStream());
				  
				  
				  JFrame frame = new JFrame("Guadagni");
				  console.setBorder(new TitledBorder("Conti"));
				  frame.getContentPane().add(new JScrollPane(console));
				  frame.setSize(500,400);
				  frame.setVisible(true);
				  double total=0.0;
				  System.out.println("Guadagni netti...\n\n");
				  System.out.println("Data Riparazione\tVeicolo\tDescrizione Intervento   \tImporto ricevuto");
				  for(int i=0;i<tableInterventi.getRowCount();i++){
					 System.out.print(tableInterventi.getValueAt(i, 3)+"\t\t");
					 System.out.print(tableInterventi.getValueAt(i, 2)+"\t");
					 System.out.print(tableInterventi.getValueAt(i, 7)+"\t");
					 double prezzo=(Double)tableInterventi.getValueAt(i, 10);
					 double sconto=(Double)tableInterventi.getValueAt(i, 11);
					 double importo=prezzo- sconto/100*prezzo;
					 total+=importo;
					 System.out.println(importo+"$");
				  }
				  System.out.println("\n\n\t\t\t  Totale nell'arco temporale: "+total+"$");
			}
		});
		btnGuadagni.setBounds(217, 70, 89, 23);
		panel.add(btnGuadagni);
	}
	
	private void azzera(){
		listProprietari.setSelectedValue(" ", true);
		listModel.clear();
		txtDataRip.setText("");
		txtLuogo.setText("");
		txtNomeMeccanico.setText("");
		txpDifetti.setText("");
		txpDescrizione.setText("");
		txpMateriale.setText("");
		txtDataRiconsegna.setText("");
		txtCosto.setText("");
		txtSconto.setText("");
		chckbxSaldato.setSelected(false);
		add=true;
		btnModifica.setEnabled(false);
		btnCancella.setEnabled(false);
		btnAzzera.setEnabled(false);
		btnEdit.setEnabled(false);
		btnAggiungi.setEnabled(false);
	}
	
	private void tableRefresh(){
		DefaultTableModel interventiModel= con.getInterventiModel();
		tableInterventi.setModel(interventiModel);
		
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
	
public void visualizzaIntervento(int cod){
		
		for(int i=0;i< tableInterventi.getRowCount();i++){
			if(((int)tableInterventi.getValueAt(i,0))==cod)
				tableInterventi.setRowSelectionInterval(i,i);}
			btnEdit.setEnabled(true);
			btnEdit.doClick();
			
		
		}


}
