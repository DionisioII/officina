
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;




public class DBconnect {
	
	private Connection con;
	private  Statement st;
	private ResultSet rs;
	
	public DBconnect(){
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/officina","root","");
			st=con.createStatement();
			
		}catch(Exception ex){
			System.out.println("error: "+ex);
			JOptionPane.showMessageDialog(null,"impossibile contattare il database");
			System.exit(0);
		}
		
	}
	
	
	
	public  List<String> getClientiNames(){
		
		List<String> clientiNames=new ArrayList<String>();
		clientiNames.add(" ");
				
		try {
			String query;
			
			 query= "select * from clienti";
			
			rs = st.executeQuery(query);
            			
			while(rs.next()){				
				clientiNames.add((String) rs.getObject(2)+" "+rs.getObject(3));			
			}
		}
			
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
						
		}
		return clientiNames;
		
	}
	
public  List<String> getMeccaniciNames(){
		
		List<String> clientiNames=new ArrayList<String>();
		clientiNames.add("%");
				
		try {
			String query;
			
			 query= "select * from interventi_tecnici";
			
			rs = st.executeQuery(query);
            			
			while(rs.next()){				
				clientiNames.add((String) rs.getObject(6));			
			}
		}
			
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
						
		}
		return clientiNames;
		
	}
	
public  List<String> getClientiCodes(){
		
		List<String> clientiCodes=new ArrayList<String>();
		clientiCodes.add(" ");
				
		try {
			String query;
			
			query= "select * from clienti";
			rs = st.executeQuery(query);
            			
			while(rs.next()){
			
				clientiCodes.add((String) rs.getObject(1));
				
			}
		}
			
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
						
		}
		return clientiCodes;
		
	}

public  List<String> getFornitoriNames(){
	
	List<String> clientiNames=new ArrayList<String>();
	clientiNames.add(" ");
			
	try {
		String query;
		
		 query= "select * from fornitori";
		
		rs = st.executeQuery(query);
        			
		while(rs.next()){				
			clientiNames.add((String) rs.getObject(3));			
		}
	}
		
	 catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
					
	}
	return clientiNames;
	
}

public  List<String> getFornitoriCodes(){
	
	List<String> clientiCodes=new ArrayList<String>();
	clientiCodes.add(" ");
			
	try {
		String query;
		
		query= "select * from fornitori";
		rs = st.executeQuery(query);
        			
		while(rs.next()){
		
			clientiCodes.add((String) Integer.toString((int)rs.getObject(1)));
			
		}
	}
		
	 catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
					
	}
	return clientiCodes;
	
}
	
	
	
	public DefaultTableModel getClientiModel(){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"codice fiscale", "nome", "cognome","indirizzo","città","categoria","modalià pagamento","altre informazioni"};
		try{
			String query= "select * from clienti";
			rs = st.executeQuery(query);
			ResultSetMetaData rsMD = rs.getMetaData();
			int contcolum = rsMD.getColumnCount();
			model.setColumnCount(contcolum);
			model.setColumnIdentifiers(arraydicolonne);
			while(rs.next()) {
				   int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				   for(int c = 0; c < contcolum; c++) {
				      
				      Object valore = rs.getObject(c+1);
				      model.setValueAt(valore, riga, c);
				   }
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return model;
		
	}
	
	public void insertCliente(String[] data){
		
		try {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO clienti ("+"codice_fiscale,"+" nome,"+"cognome,"+"indirizzo,"+"città,"+"categoria,"+"modalità_pagamento,"+"altre_informazioni)"+" VALUES (?, ? ,? ,? ,? ,? ,? ,? ) ");
			pstmt.setNString(1, data[0]);
			pstmt.setNString(2, data[1]);
			pstmt.setNString(3, data[2]);
			pstmt.setNString(4, data[3]);
			pstmt.setNString(5, data[4]);
			pstmt.setNString(6, data[5]);
			pstmt.setNString(7, data[6]);
			pstmt.setNString(8, data[7]);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"esiste già un utente con questo codice fiscale!");
			//e.printStackTrace();
		}
		
	}
	
	public void cancellaCliente(String cod){
		
		try {
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM clienti WHERE codice_fiscale= ? ");
			pstmt.setNString(1,cod);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void modificaCliente(String[] data){
		
		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE clienti SET codice_fiscale=?, nome= ?, cognome=?,indirizzo=?,città=?,categoria=?,modalità_pagamento=?,altre_informazioni=? WHERE codice_fiscale=?");
			pstmt.setNString(1, data[0]);
			pstmt.setNString(2, data[1]);
			pstmt.setNString(3, data[2]);
			pstmt.setNString(4, data[3]);
			pstmt.setNString(5, data[4]);
			pstmt.setNString(6, data[5]);
			pstmt.setNString(7, data[6]);
			pstmt.setNString(8, data[7]);
			pstmt.setNString(9, data[0]);
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public DefaultTableModel getMezziClienteModel(String codi_cliente){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"targa", "tipo", "marca","modello","tipo"};
		try{
			PreparedStatement st = con.prepareStatement("SELECT * FROM automezzi WHERE cod_cliente=?");
			st.setNString(1,codi_cliente);
			rs = st.executeQuery();
			
			
			model.setColumnCount(5);
			model.setColumnIdentifiers(arraydicolonne);
			while(rs.next()) {
				int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				   for(int c = 0; c < 5; c++) {
				      
				      Object valore = rs.getObject(c+1);
				      model.setValueAt(valore, riga, c);
				   }
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return model;
		
	}
	
	
	public DefaultTableModel getRecapitiClienteModel(String codi_cliente){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"Label", "Recapito"};
		try{
			PreparedStatement st = con.prepareStatement("SELECT * FROM telefono WHERE codice_persona=?");
			st.setNString(1,codi_cliente);
			rs = st.executeQuery();
			
			
			model.setColumnCount(2);
			model.setColumnIdentifiers(arraydicolonne);
			while(rs.next()) {
				int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				   for(int c = 0; c < 2; c++) {
				      
				      Object valore = rs.getObject(c+3);
				      model.setValueAt(valore, riga, c);
				   }
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return model;
		
	}
	
	public DefaultTableModel getMezziModel(){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"Targa", "proprietario", "tipologia","marca","modello","telaio","immatricolazione","colore","alimentazione","cavalli","cilindrata","data acquisto","prezzo","garanzia","km/L","portata massima","accessori","km percorsi"};
		try{
			String query= "select * from automezzi";
			rs = st.executeQuery(query);
			ResultSetMetaData rsMD = rs.getMetaData();
			int contcolum = rsMD.getColumnCount();
			model.setColumnCount(contcolum);
			model.setColumnIdentifiers(arraydicolonne);
			while(rs.next()) {
				   int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				   for(int c = 0; c < contcolum; c++) {
				      
				      Object valore = rs.getObject(c+1);
				      model.setValueAt(valore, riga, c);
				   }
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return model;
		
	}
	
	public String getNomeCliente(String cod){
		String name="";
		try {
			PreparedStatement st = con.prepareStatement("SELECT * FROM clienti WHERE codice_fiscale=?");
			st.setNString(1,cod);
			rs=st.executeQuery();
			if(rs.first()){
				String n=(String)rs.getObject(2);
				String c=(String)rs.getObject(3);
				name=n+" "+c;
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
	public String getNomeFornitore(int cod){
		String name="";
		try {
			PreparedStatement st = con.prepareStatement("SELECT * FROM fornitori WHERE codice=?");
			st.setInt(1,cod);
			rs=st.executeQuery();
			if(rs.first()){
				
				String c=(String)rs.getObject(2);
				name=c;
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	
public void modificaAutomezzo(String[] data){
		
		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE automezzi SET numero_targa=?, cod_cliente= ?, tipo_mezzo=?,marca=?,modello=?,numero_telaio=?,data_immatricolazione=?,colore=?,tipo_alimentazione=?,cavalli_fiscali=?,cilindrata=?,data_acquisto=?,prezzo_acquisto=?,stato_garanzia=?,chilometri_percorrenza_litro=?,portata_massima=?,accessori=?,chilometri_percorsi=? WHERE numero_targa=?");
			pstmt.setNString(1, data[0]);
			pstmt.setNString(2, data[1]);
			pstmt.setNString(3, data[2]);
			pstmt.setNString(4, data[3]);
			pstmt.setNString(5, data[4]);
			pstmt.setNString(6, data[5]);
			pstmt.setNString(7, data[6]);
			pstmt.setNString(8, data[7]);
			pstmt.setNString(9, data[8]);
			pstmt.setNString(10, data[9]);
			pstmt.setNString(11, data[10]);
			pstmt.setNString(12, data[11]);
			pstmt.setNString(13, data[12]);
			pstmt.setNString(14, data[13]);
			pstmt.setNString(15, data[14]);
			pstmt.setNString(16, data[15]);
			pstmt.setNString(17, data[16]);
			pstmt.setNString(18, data[17]);
			pstmt.setNString(19, data[0]);
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}


public void insertAutomezzo(String[] data) {
	
	try {
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO automezzi (`numero_targa`, `cod_cliente`, `tipo_mezzo`, `marca`, `modello`, `numero_telaio`, `data_immatricolazione`, `colore`, `tipo_alimentazione`, `cavalli_fiscali`, `cilindrata`, `data_acquisto`, `prezzo_acquisto`, `stato_garanzia`, `chilometri_percorrenza_litro`, `portata_massima`, `accessori`, `chilometri_percorsi`)"+"VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
		pstmt.setNString(1, data[0]);
		pstmt.setNString(2, data[1]);
		pstmt.setNString(3, data[2]);
		pstmt.setNString(4, data[3]);
		pstmt.setNString(5, data[4]);
		pstmt.setNString(6, data[5]);
		pstmt.setNString(7, data[6]);		
		pstmt.setNString(8, data[7]);
		pstmt.setNString(9, data[8]);
		pstmt.setNString(10, data[11]);
		pstmt.setNString(11, data[10]);
		pstmt.setNString(12, data[11]);
		pstmt.setNString(13, data[12]);
		pstmt.setNString(14, data[13]);
		pstmt.setNString(15, data[14]);
		pstmt.setNString(16, data[15]);
		pstmt.setNString(17, data[16]);
		pstmt.setNString(18, data[17]);
		
		
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

	public void getListClienti(){
		
	}

	public void cancellaMezzo(String cod) {
		try {
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM automezzi WHERE numero_targa= ? ");
			pstmt.setNString(1,cod);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public DefaultTableModel getMezzoIncidentiModel(String cod_mezzo){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"Data", "Motivo"};
		try{
			PreparedStatement st = con.prepareStatement("SELECT * FROM incidenti_subiti WHERE numero_targa=?");
			st.setNString(1,cod_mezzo);
			rs = st.executeQuery();
			
			
			model.setColumnCount(2);
			model.setColumnIdentifiers(arraydicolonne);
			while(rs.next()) {
				int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				   for(int c = 0; c < 2; c++) {
				      Object valore=new Object();
					   if(c==0){
						   valore= FmrInterventi.formatDate((String)rs.getObject(c+3).toString(), "yyyy-mm-dd", "dd/mm/yyyy") ;
					   }
					   else
				       valore = rs.getObject(c+3);
				      model.setValueAt(valore, riga, c);
				   }
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return model;
		
	}
	
	public DefaultTableModel getMezzoRiparazioniModel(String cod_mezzo){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"cd","Data", "Luogo","Nome meccanico"};
		try{
			PreparedStatement st = con.prepareStatement("SELECT * FROM interventi_tecnici WHERE targa_automezzo=?");
			st.setNString(1,cod_mezzo);
			rs = st.executeQuery();
			
			
			model.setColumnCount(4);
			model.setColumnIdentifiers(arraydicolonne);
			while(rs.next()) {
				int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				   for(int c = 0; c < 4; c++) {
					   
					   if(c==0){
						   Object valore = rs.getObject(c+1);
						      model.setValueAt(valore, riga, c);  
					   }
					   else{
				      Object valore = rs.getObject(c+3);
				      model.setValueAt(valore, riga, c);}
				   }
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return model;
		
	}
	public DefaultTableModel getInterventiClienteModel(String codex){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"codice","Data", "targa"};
		try{
			PreparedStatement st = con.prepareStatement("SELECT * FROM interventi_tecnici WHERE codice_cliente=?");
			st.setNString(1,codex);
			rs = st.executeQuery();
			
			
			model.setColumnCount(3);
			model.setColumnIdentifiers(arraydicolonne);
			while(rs.next()) {
				int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				  /* for(int c = 0; c < 3; c++) {
					      
					      Object valore = rs.getObject(c+4);
					      model.setValueAt(valore, riga, c);
					   }*/
				      
				      Object valore = rs.getObject(1);
				      model.setValueAt(valore, riga, 0);
				      Object valore1 = rs.getObject(4);
				      model.setValueAt(valore1, riga, 1);
				      Object valore2 = rs.getObject(3);
				      model.setValueAt(valore2, riga, 2);
				   
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return model;
		
	}
	
	public DefaultTableModel getFornitoriModel(){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"cod","nome ditta", "nome contatto", "iban","mod trasporto","mod pagamento"};
		try{
			String query= "select * from fornitori";
			rs = st.executeQuery(query);
			ResultSetMetaData rsMD = rs.getMetaData();
			int contcolum = rsMD.getColumnCount() ;
			model.setColumnCount(contcolum);
			model.setColumnIdentifiers(arraydicolonne);
			
			while(rs.next()) {
				   int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				   for(int c = 0; c < contcolum; c++) {
				      
				      Object valore = rs.getObject(c+1);
				      model.setValueAt(valore, riga, c);
				   }
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		return model;
		
	}
	
public void modificaFornitore(String[] data){
		
		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE fornitori SET codice=?, nome_ditta= ?, nome_contatto=?,iban=?,modalità_trasporto=?,modalità_pagamento=? WHERE codice=?");
			pstmt.setNString(1, data[0]);
			pstmt.setNString(2, data[1]);
			pstmt.setNString(3, data[2]);
			pstmt.setNString(4, data[3]);
			pstmt.setNString(5, data[4]);
			pstmt.setNString(6, data[5]);
			pstmt.setNString(7, data[0]);
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
public void insertFornitore(String[] data){
	
	try {
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO fornitori ("+"nome_ditta,"+" nome_contatto,"+"iban,"+"modalità_trasporto,"+"modalità_pagamento)"+" VALUES (?, ? ,? ,? ,? ) ");
		pstmt.setNString(1, data[0]);
		pstmt.setNString(2, data[1]);
		pstmt.setNString(3, data[2]);
		pstmt.setNString(4, data[3]);
		pstmt.setNString(5, data[4]);
		
		
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
	public void cancellaFornitore(String cod){
	
		try {
			PreparedStatement pstmt = con.prepareStatement("DELETE FROM fornitori WHERE codice= ? ");
			pstmt.setInt(1, Integer.parseInt(cod));
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public DefaultTableModel getArticoliFornitoreModel(int cod){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"Codice", "Tipologia","marca","modello"};
		try{
			PreparedStatement st = con.prepareStatement("SELECT * FROM articoli_magazzino WHERE codice_fornitore=?");
			st.setInt(1,cod);
			rs = st.executeQuery();
			
			
			model.setColumnCount(4);
			model.setColumnIdentifiers(arraydicolonne);
			while(rs.next()) {
				int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				   for(int c = 0; c < 4; c++) {
				      
				      Object valore = rs.getObject(c+1);
				      model.setValueAt(valore, riga, c);
				   }
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return model;
		
	}
	
	public DefaultTableModel getArticoliModel(){
		DefaultTableModel model = new DefaultTableModel();
		String[] arraydicolonne = {"codice", "tipologia", "marca","modello","accessori","descrizione","prezzo","giacenza","cod_fornitore"};
		try{
			String query= "select * from articoli_magazzino";
			rs = st.executeQuery(query);
			ResultSetMetaData rsMD = rs.getMetaData();
			int contcolum = rsMD.getColumnCount();
			model.setColumnCount(contcolum);
			model.setColumnIdentifiers(arraydicolonne);
			while(rs.next()) {
				   int riga = model.getRowCount(); 
				   model.setRowCount(model.getRowCount() + 1);
				   for(int c = 0; c < contcolum; c++) {
				      
				      Object valore = rs.getObject(c+1);
				      model.setValueAt(valore, riga, c);
				   }
				}
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return model;
	}
	
	
public void modificaArticolo(String[] data){
		
		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE articoli_magazzino SET codice_articolo=?, tipologia= ?, marca=?,modello=?,accessori=?,descrizione=?,prezzo_pubblico=?,giacenza=?,codice_fornitore=? WHERE codice_articolo=?");
			pstmt.setNString(1, data[0]);
			pstmt.setNString(2, data[1]);
			pstmt.setNString(3, data[2]);
			pstmt.setNString(4, data[3]);
			pstmt.setNString(5, data[4]);
			pstmt.setNString(6, data[5]);
			pstmt.setDouble(7, Double.parseDouble(data[6]));
			pstmt.setInt(8, Integer.parseInt(data[7]));
			pstmt.setInt(9, Integer.parseInt(data[8]));
			pstmt.setNString(10, data[0]);
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

public void insertArticolo(String[] data){
	
	try {
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO articoli_magazzino ("+"codice_articolo,"+" tipologia,"+"marca,"+"modello,"+"accessori,"+"descrizione,"+"prezzo_pubblico,"+"giacenza,"+"codice_fornitore)"+" VALUES (?, ? ,? ,? ,? ,? ,? ,?,? ) ");
		pstmt.setNString(1, data[0]);
		pstmt.setNString(2, data[1]);
		pstmt.setNString(3, data[2]);
		pstmt.setNString(4, data[3]);
		pstmt.setNString(5, data[4]);
		pstmt.setNString(6, data[5]);
		pstmt.setDouble(7, Double.parseDouble(data[6]));
		pstmt.setInt(8, Integer.parseInt(data[7]));
		pstmt.setInt(9, Integer.parseInt(data[8]));
		
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
public void cancellaArticolo(String cod){
	
	try {
		PreparedStatement pstmt = con.prepareStatement("DELETE FROM articoli_magazzino WHERE codice_articolo= ? ");
		pstmt.setNString(1,cod);
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


public DefaultTableModel getInterventiModel(){
	DefaultTableModel model = new DefaultTableModel();
	String[] arraydicolonne = {"codInt","codice cliente", "targa", "data riparazione","luogo","nome meccanico","difetti riscontrati","descrizione intervento","materiale impiagato","data riconsegna","costo riparazione","sconto","saldato"};
	try{
		String query= "select * from interventi_tecnici";
		rs = st.executeQuery(query);
		ResultSetMetaData rsMD = rs.getMetaData();
		int contcolum = rsMD.getColumnCount();
		model.setColumnCount(contcolum);
		model.setColumnIdentifiers(arraydicolonne);
		while(rs.next()) {
			   int riga = model.getRowCount(); 
			   model.setRowCount(model.getRowCount() + 1);
			   for(int c = 0; c < contcolum; c++) {
			      
			      Object valore = rs.getObject(c+1);
			      model.setValueAt(valore, riga, c);
			   }
			}
		
	}catch(SQLException ex){
		ex.printStackTrace();
	}
	return model;
	
}

public DefaultTableModel getInterventiModel(boolean saldato){
	DefaultTableModel model = new DefaultTableModel();
	String[] arraydicolonne = {"codInt","codice cliente", "targa", "data riparazione","luogo","nome meccanico","difetti riscontrati","descrizione intervento","materiale impiagato","data riconsegna","costo riparazione","sconto","saldato"};
	try{
		String query;
		if(saldato)
		 query= "select * from interventi_tecnici where saldato='false'";
		else
			query="select * from interventi_tecnici where data_riconsegna is NULL";
		rs = st.executeQuery(query);
		ResultSetMetaData rsMD = rs.getMetaData();
		int contcolum = rsMD.getColumnCount();
		model.setColumnCount(contcolum);
		model.setColumnIdentifiers(arraydicolonne);
		while(rs.next()) {
			   int riga = model.getRowCount(); 
			   model.setRowCount(model.getRowCount() + 1);
			   for(int c = 0; c < contcolum; c++) {
			      
			      Object valore = rs.getObject(c+1);
			      model.setValueAt(valore, riga, c);
			   }
			}
		
	}catch(SQLException ex){
		ex.printStackTrace();
	}
	return model;
	
}

public DefaultTableModel getInterventiModel(String giorno1,String mese1,String anno1,String giorno2,String mese2,String anno2,String nomeMeccanico,String proprietario){
	DefaultTableModel model = new DefaultTableModel();
	String formatted_date1;
	String[] arraydicolonne = {"codInt","codice cliente", "targa", "data riparazione","luogo","nome meccanico","difetti riscontrati","descrizione intervento","materiale impiagato","data riconsegna","costo riparazione","sconto","saldato"};
	try{
		
		if(giorno1.equals(" ") )giorno1="01";
		if(mese1.equals(" ") )mese1="01";
		if(giorno2.equals(" ") )giorno2="30";
		if(mese2.equals(" ") )mese2="12";
			
		formatted_date1=anno1+"-"+mese1+"-"+giorno1;
		String formatted_date2=anno2+"-"+mese2+"-"+giorno2;
		if (proprietario.equals(" ")) proprietario="%";
		String query= "SELECT * FROM interventi_tecnici WHERE (data_riparazione >= ? AND data_riparazione <=?) AND nome_meccanico LIKE ? AND codice_cliente LIKE ?";
		PreparedStatement st = con.prepareStatement(query);
		st.setString(1,formatted_date1);
		st.setString(2,formatted_date2);
		st.setString(3,nomeMeccanico);
		st.setString(4,proprietario);
		rs=st.executeQuery();
		ResultSetMetaData rsMD = rs.getMetaData();
		int contcolum = rsMD.getColumnCount();
		model.setColumnCount(contcolum);
		model.setColumnIdentifiers(arraydicolonne);
		while(rs.next()) {
			   int riga = model.getRowCount(); 
			   model.setRowCount(model.getRowCount() + 1);
			   for(int c = 0; c < contcolum; c++) {
			      
			      Object valore = rs.getObject(c+1);
			      model.setValueAt(valore, riga, c);
			   }
			}
		
	}catch(SQLException ex){
		ex.printStackTrace();
	}
	return model;
	
}




public String[] getMezzi(String proprietario) {
	List<String> Mezzi=new ArrayList<String>();
	try
	{PreparedStatement st = con.prepareStatement("SELECT * FROM automezzi WHERE cod_cliente=?");
	st.setString(1,proprietario);
	rs = st.executeQuery();
	while(rs.next()){				
		Mezzi.add((String) rs.getObject(1));			
	}
	 
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String[] mezzi=Mezzi.toArray(new String[Mezzi.size()]);
	
	return mezzi;
}


public void modificaIntervento(String[] data){
	
	try {
		PreparedStatement pstmt = con.prepareStatement("UPDATE interventi_tecnici SET codice_cliente=?, targa_automezzo= ?, data_riparazione=?,luogo=?,nome_meccanico=?,difetti_riscontrati=?,descrizione_intervento=?,materiale_impiegato=?,data_riconsegna=?,costo_riparazione=?,sconto=?,saldato=? WHERE codice_riparazione=?");
		pstmt.setNString(1, data[1]);
		pstmt.setNString(2, data[2]);
		pstmt.setNString(3, data[3]);
		pstmt.setNString(4, data[4]);
		pstmt.setNString(5, data[5]);
		pstmt.setNString(6, data[6]);
		pstmt.setNString(7, data[7]);
		pstmt.setNString(8, data[8]);
		pstmt.setNString(9, data[9]);
		pstmt.setNString(10, data[10]);
		pstmt.setNString(11, data[11]);
		pstmt.setNString(12, data[12]);
		pstmt.setNString(13, data[0]);
		
		pstmt.executeUpdate();
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}


public void insertIntervento(String[] data) {
	
	try {
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO `officina`.`interventi_tecnici` ( `codice_cliente`, `targa_automezzo`, `data_riparazione`, `luogo`, `nome_meccanico`, `difetti_riscontrati`, `descrizione_intervento`, `materiale_impiegato`, `data_riconsegna`, `costo_riparazione`, `sconto`, `saldato`)"+"VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		pstmt.setNString(1, data[0]);
		pstmt.setNString(2, data[1]);
		pstmt.setNString(3, data[2]);
		pstmt.setNString(4, data[3]);
		pstmt.setNString(5, data[4]);
		pstmt.setNString(6, data[5]);
		pstmt.setNString(7, data[6]);		
		pstmt.setNString(8, data[7]);
		pstmt.setNString(9, data[8]);
		pstmt.setDouble(10, Double.parseDouble(data[9]));
		pstmt.setDouble(11, Double.parseDouble(data[10]));
		pstmt.setNString(12, data[11]);
		
		
		
		
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

public void cancellaIntervento(int cod){
	
	try {
		PreparedStatement pstmt = con.prepareStatement("DELETE FROM interventi_tecnici WHERE codice_riparazione= ? ");
		pstmt.setInt(1,cod);
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}



public void cancellaRecapito(String codClient, String label, String value) {
	try {
		PreparedStatement pstmt = con.prepareStatement("DELETE FROM telefono WHERE codice_persona= ? and tipo_recapito=? and recapito=?  ");
		pstmt.setString(1,codClient);
		pstmt.setString(2,label);
		pstmt.setString(3,value);
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}



public void aggiungiContatto(String codClient, String label, String value) {
	try {
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO `officina`.`telefono` (`cod`,`codice_persona`, `tipo_recapito`, `recapito`) VALUES (NULL,?,?,?)");
		pstmt.setString(1,codClient);
		pstmt.setString(2,label);
		pstmt.setString(3,value);
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

}



public void cancellaIncidente(String tg, String data, String value) {
	try {
		PreparedStatement pstmt = con.prepareStatement("DELETE FROM incidenti_subiti WHERE numero_targa= ? and  data=? and motivo=?  ");
		pstmt.setString(1,tg);
		pstmt.setString(2,data);
		pstmt.setString(3,value);
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}



public void aggiungiIncidente(String tg, String date, String value) {
	try {
		PreparedStatement pstmt = con.prepareStatement("INSERT INTO `officina`.`incidenti_subiti` (`codice_incidente`,`numero_targa`, `data`, `motivo`) VALUES (NULL,?,?,?)");
		pstmt.setString(1,tg);
		pstmt.setString(2,date);
		pstmt.setString(3,value);
		pstmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


}
