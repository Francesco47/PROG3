package distributore;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;


/**
 * La classe Amministratore gestisce tutte funzionalità che un amministratore puo' effettuare sul Distributore
 * @version 1.0
 * @author Francesco D'Angelo, Giuseppe Franzese e Salvatore Iovinella
 *
 */
public class Amministratore implements InterfaceCategoriaDao<Categoria>, InterfaceProdottoDao<Prodotto> {
	
	private StatoDistributore statoDistributore;
	final String jdbcURL = "jdbc:derby:distributore;create=true"; //URL per collegarsi al db
	private String nome;
	private String cognome;
	private String codice;
	int k = 0; //indice avanzamento della cronologia, serve per verificare se si arriva a 20 prodotti. In tal caso viene azzerato
	 Prodotto prod = new Prodotto();
	 
	public ArrayList<Prodotto> prodotto = new ArrayList<>();
	public ArrayList<Categoria> categoria = new ArrayList<>();
	
	/**
	 * metodo che verifica se la quantità dei prodotti e' minore di 2
	 * @throws SQLException
	 */
	public void controlloQuantita() throws SQLException { 
		Connection connection; //oggetto di tipo connection, serve per effettuare la connessione
		Statement statement; //oggetto di tipo statement, serve per effettuare una query
		ResultSet rs; //oggetto di tipo ResultSet, serve per scorrere le varie righe di una tabella una volta effettuata una SELECT
		connection = DriverManager.getConnection(jdbcURL); //connessione
		try {
			statement = connection.createStatement(); //crea lo statement per effettuare la query
			String sql = "SELECT * FROM PRODOTTO"; //query
			rs = statement.executeQuery(sql); //esecuzione e risultato di una query
			while (rs.next()) { //fin quando la riga successiva della tabella che si sta scorrendo non è vuota
				if(rs.getDouble("quantita")<2)
				{
					JOptionPane.showMessageDialog(null, "Quantita' del prodotto "+rs.getString("codice")+ " minore di 2.\n Fare rifornimento", "DISTRIBUTORE", JOptionPane.ERROR_MESSAGE);
					prod.setCodice(rs.getString("codice"));
					prod.setCampo("quantita");
					String q = JOptionPane.showInputDialog(null, "Inserisci nuova quantita:","DISTRIBUTORE", JOptionPane.INFORMATION_MESSAGE);
					prod.setValore(q);
					updateProdotto(prod);
				}
			}	
			}catch(SQLException e) {
				
			}
	}
	   
	    public void setOriginatorState(StatoDistributore state) { //setta lo stato iniziale del distributore
	        statoDistributore = state;
	    }

		public StatoDistributore getOriginatorState() { //mostra lo stato 
	       statoDistributore.showStato();
	    	return statoDistributore;
	    }
	 
	    public Memento createMemento() { //crea un nuovo oggetto di tipo memento e va a settare lo stato allo stato attuale
	        Memento memento = new Memento();
	        memento.setMementoState(statoDistributore);
	        return memento;
	    }
	 
	    public void restoreMemento(Memento memento) { //metoto get del memento
	        statoDistributore = memento.getMementoState();
	    }
	    
	    /**
	     *Classe (Design Pattern Memento) per ripristinare  lo stato del Distributore da, eventualmente, GUASTO a IN FUNZIONE.
	     *Classe visibile solo all'amministratore (Originator)
	     *
	     */
	 public class Memento { 
		 
	        private StatoDistributore mementoState;
	 
	        private StatoDistributore getMementoState() {
	            return this.mementoState;
	        }
	 
	        private void setMementoState(StatoDistributore originatorState) {
	            this.mementoState = originatorState;
	        }
	    }
	 
	 /**
	  * metodo che mostra all'amministratore la cronologia degli acquisti effettuati dai clienti
	  * @throws SQLException
	  */
	 
	 public void cronologiaAcquisti() throws SQLException //mostra all'amministratore la cronologia degli acquisti effettuati dai clienti
		{
			Connection connection;
			Statement statement;
			Statement s1;
			ResultSet rs;
			connection = DriverManager.getConnection(jdbcURL);
			try {
				statement = connection.createStatement();
				String sql = "SELECT * FROM CRONOLOGIA";
				rs = statement.executeQuery(sql);
				System.out.println("---CRONOLOGIA ACQUISTI---");
				while(rs.next())
				{
					System.out.println("ID = "+rs.getString("id"));
					k++;
				}
				if (k==20)
				{
					s1 = connection.createStatement();
					JOptionPane.showMessageDialog(null, "Mostrato il numero massimo di elementi nella cronologia. Azzero la lista..");
					String sql1 = "DELETE FROM CRONOLOGIA";
					s1.executeUpdate(sql1);
					k = 0;
				}
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
			
		}
	 /**
	  * metodo che mostra lo stato attuale del distributore
	  * @return lo stato corrente del distributore
	  */
	
	public StatoDistributore  currentStatoDistributore() { 
		statoDistributore.showStato();
		return statoDistributore;
	
	}
	
	/**
	 * metodo utilizzato per modificare lo stato del distributore
	 * @param statoDistributore
	 */
	public void setStatoDistributore(StatoDistributore statoDistributore) { 
		this.statoDistributore = statoDistributore;
	}
	
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}
	public void setCodice (String codice) 
	{
		this.codice = codice;
	}	
	
	public String getNome() {
		return nome;
	}

	public String getCognome() { 
		return cognome;
	}

	public String getCodice() { 
		return codice;
	}

	@Override
	/**
	 * metodo utilizzato per l'inserimento di un prodotto nel distributore
	 * @Override
	 */
	public void insertProdotto(Prodotto p) throws SQLException{
		Connection connection;
		Statement statement;
		connection = DriverManager.getConnection(jdbcURL);
		try {
			
			String sql = "INSERT INTO PRODOTTO VALUES('"+p.getCodice()+"', '"+p.getNome()+"', "+p.getPrezzo()+" , "+p.getQuantita()+", '"+p.getCodiceCat()+"')";
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			ResultSet rs = statement.executeQuery("SELECT * FROM PRODOTTO");
			while(rs.next())
			{
				if(rs.getString("codice").equals(p.getCodice()))
				{
					while(prodotto.iterator().hasNext()==false)
					prodotto.add(new Prodotto(rs.getString("codice"), rs.getString("nome"),rs.getDouble("prezzo"), rs.getDouble("quantita"), rs.getString("categoria")));
				}
			}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"errore:" + e.getMessage());
			}
		connection.close();
		
	}
	@Override
	/**
	 * metodo utilizzato per la modifica delle quantita' o prezzo di un prodotto nel distributore
	 * @Override
	 */
	public void updateProdotto(Prodotto p) throws SQLException{ 
		Connection connection;
		Statement statement;
		ResultSet rs;
		connection = DriverManager.getConnection(jdbcURL);
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE PRODOTTO SET "+p.getCampo()+"="+p.getValore()+" WHERE CODICE = '"+p.getCodice()+"'");
			rs = statement.executeQuery("SELECT * FROM PRODOTTO");
					while(rs.next())
					{
						if(rs.getString("codice").equals(p.getCodice()))
						{
							p.setCampo(p.getValore());
						}
								
							
					}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"errore:" + e.getMessage());
			}
		connection.close();
		
	}

	@Override
	/**
	 * metodo utilizzato per modificare il codice, nome o categoria di un prodotto nel distributore
	 * @Override
	 */
	public void updateProdottoString(Prodotto p) throws SQLException{
		Connection connection;
		Statement statement;
		ResultSet rs;
		connection = DriverManager.getConnection(jdbcURL);
		try {
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE PRODOTTO SET "+p.getCampo()+"='"+p.getValore()+"' WHERE CODICE = '"+p.getCodice()+"'");
			rs = statement.executeQuery("SELECT * FROM PRODOTTO");
					while(rs.next())
					{
						if(rs.getString("codice").equals(p.getCodice()))
						{
							p.setCampo(p.getValore());
						}
								
							
					}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"errore:" + e.getMessage());
			}
		connection.close();
		
	}

	@Override
	/**
	 * metodo utilizzato per la rimozione di un prodotto nel distributore
	 * @Override
	 */
	public void deleteProdotto(Prodotto p) throws SQLException{ 
		Connection connection;
		Statement statement;
		ResultSet rs;
		connection = DriverManager.getConnection(jdbcURL);
		try {
			
			statement = connection.createStatement();
			 statement.executeUpdate("DELETE FROM PRODOTTO WHERE CODICE='"+p.getCodice()+"'");
			 rs = statement.executeQuery("SELECT * FROM PRODOTTO");
				while (rs.next()) {
					
					 if(rs.getString("codice").equals(p.getCodice())) {
						 prodotto.remove(rs.getRow());
					 }
				}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"errore:" + e.getMessage());
			}
		connection.close();
	     
	}
	@Override
	/**
	 * metodo utilizzato per l'inserimento di una categoria nel distributore
	 * @Override
	 */
	public void insertCategoria(Categoria c) throws SQLException { 
		Connection connection;
		Statement statement;
		connection = DriverManager.getConnection(jdbcURL);
		
		try {
			
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO CATEGORIA VALUES('"+c.getCodiceCat()+"', '"+c.getNomeCat()+"')");
			ResultSet rs = statement.executeQuery("SELECT * FROM CATEGORIA");
			while(rs.next())
			{
				
				if(rs.getString("codice").equals(c.getCodiceCat()))
				{
					while(categoria.iterator().hasNext()==false)
					categoria.add(new Categoria(rs.getString("codice"), rs.getString("nome")));
				}
			}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"errore:" + e.getMessage());
			}
		connection.close();
		  
	}
	@Override
	/**
	 * metodo utilizzato per la modifica di una categoria nel distributore
	 * @Override
	 */
	public void updateCategoria(Categoria c) throws SQLException{ 
		Connection connection;
		Statement statement;
		ResultSet rs;
		connection = DriverManager.getConnection(jdbcURL);
		try {
			
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE CATEGORIA SET "+c.getCampo()+"='"+c.getValore()+"' WHERE CODICE = '"+c.getCodiceCat()+"'");
			String sql = "SELECT * FROM CATEGORIA";
			rs = statement.executeQuery(sql);
			while(rs.next())
			{
				if(rs.getString("codice").equals(c.getCodiceCat()))
					
					 c.setCampo(c.getValore()); 
			}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"errore:" + e.getMessage());
			}
		connection.close();
		
	}
	@Override
	/**
	 * metodo utilizzato per la rimozione di una categoria nel distributore
	 * @Override
	 */
	public void deleteCategoria(Categoria c) throws SQLException{ 
		Connection connection;
		Statement statement;
		ResultSet rs;
		connection = DriverManager.getConnection(jdbcURL);
		try {
			
			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM CATEGORIA WHERE CODICE='"+c.getCodiceCat()+ "'");
			rs = statement.executeQuery("SELECT * FROM CATEGORIA");
			while (rs.next()) {
				
				 if(rs.getString("codice").equals(c.getCodiceCat())) {
					 categoria.remove(rs.getRow());
				 }
			}
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"errore:" + e.getMessage());
			}
		connection.close();
		
	}
}