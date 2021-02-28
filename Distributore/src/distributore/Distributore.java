package distributore;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

/**
 * 
 * Classe Distributore che gestisce tutte le opportune funzionalità che può effettuare un distributore nell'acquisto dei prodotti
 * @version 1.0
 * @author Francesco D'Angelo, Giuseppe Franzese e Salvatore Iovinella
 * 
 *
 */
public class Distributore{
	Cliente cliente = new Cliente();
	
	public ArrayList<String> mostraCronologia() throws SQLException{
		ArrayList<String> cronologia = new ArrayList<>();
		Connection connection;
		Statement statement;
		ResultSet rs;
		
		String jdbcURL = "jdbc:derby:distributore;create=true";
		connection = DriverManager.getConnection(jdbcURL);
		
		try {
			
			String sql = "SELECT * FROM CRONOLOGIA";
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while(rs.next()==true)
			{
				cronologia.add((rs.getString("id")));
			}
			
		} catch(SQLException e) {
			System.out.println("errore:" + e.getMessage());
			}
		connection.close();
		return cronologia;
	}
	
	/**
	 *  metodo che estrapola le categoria dal database e le inserisce all'interno della lista per poi mostrarle
	 * @return lista di categoria utili per l'amministratore
	 * @throws SQLException
	 */
	public ArrayList<Categoria> mostraCategorie() throws SQLException{ //estrapola le categoria dal database e le inserisce all'interno della lista per poi mostrarle
		ArrayList<Categoria> categoria = new ArrayList<>();
		Connection connection;
		Statement statement;
		ResultSet rs;
		
		String jdbcURL = "jdbc:derby:distributore;create=true";
		connection = DriverManager.getConnection(jdbcURL);
		try {
			
			String sql = "SELECT * FROM CATEGORIA";
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while(rs.next()==true)
			{
				categoria.add(new Categoria(rs.getString("codice"), rs.getString("nome")));
			}
			
		} catch(SQLException e) {
			System.out.println("errore:" + e.getMessage());
			}
		connection.close();
		return categoria;
	}
	/**
	 * 
	 * metodo che estrapola i prodotti dal database e li inserisce all'interno della lista per poi mostrarli
	 * @return lista dei prodotti utile per l'amministratore per verificare i prodotti  e per il cliente per visualizzare i prodotti disponibili
	 * @throws SQLException
	 */
	public ArrayList<Prodotto> mostraProdotti() throws SQLException{ //estrapola i prodotti dal database e li inserisce all'interno della lista per poi mostrarli
		ArrayList<Prodotto> prodotto= new ArrayList<>();
		Connection connection;
		Statement statement;
		ResultSet rs;
		String jdbcURL = "jdbc:derby:distributore;create=true";
		connection = DriverManager.getConnection(jdbcURL);
		try {
			
			String sql = "SELECT * FROM PRODOTTO";
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while(rs.next()==true)
			{
				prodotto.add(new Prodotto(rs.getString("codice"), rs.getString("nome"),  rs.getDouble("prezzo"),  rs.getDouble("quantita"), rs.getString("categoria")));
			}
			
		} catch(SQLException e) {
			System.out.println("errore:" + e.getMessage());
			}
		connection.close();
		return prodotto;

	}
	
	/**
	 * 
	 * 
	 * metodo utilizzato per effettuare il pagamento e l'erogazione
	 * @param t totale importo da pagare
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public void erogaProdotto(Prodotto p, double t) throws SQLException, InterruptedException 
	{
		int scelta1 = 0;
		String s;
		do {
		s = JOptionPane.showInputDialog(null,"Effettuare il pagamento prima di erogare il prodotto/i\n---AREA PAGAMENTO---\n"
				+ "1. Contanti/Monete.\n2. Carta di credito.\nScelta...", "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
		}while(s==null);
		
		scelta1 = Integer.parseInt(s);
		 switch (scelta1) {
		case 1: 
		{//caso di pagamento con contanti
			JOptionPane.showMessageDialog(null, "---AREA DI PAGAMENTO CON CONTANTI---\n"
					+ "SI ACCETTANO MONETE: 0.10 - 0.20 - 0.50 - 1.00 - 2.00\n"
					+ "SI ACCETTANO BANCONOTE: 5.00 - 10.00 - 20.00", "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
			cliente.inserisciDenaro(t);	
					
		}break;
		case 2:
		{//caso di pagamento con carta di credito
			String pin;
			JOptionPane.showMessageDialog(null,"---AREA DI PAGAMENTO CON CARTA DI CREDITO---" , "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
			do {
				 pin = JOptionPane.showInputDialog(null, "Inserisci il pin della carta: " , "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
				 if(pin.length()<5||pin.length()>5)
				 {
					 JOptionPane.showMessageDialog(null, "Inserire un pin di 5 cifre", "DISTRIBUTORE", JOptionPane.ERROR_MESSAGE);
				 }
			}while(pin.length()<5||pin.length()>5);
			JOptionPane.showMessageDialog(null,"Attendi, transazione in corso..." , "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
			TimeUnit.SECONDS.sleep(5);
			JOptionPane.showMessageDialog(null,"Pagamento effettuato correttamente!" , "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
		}break;
		}
		 
		 JOptionPane.showMessageDialog(null,"Erogazione dei prodotti in corso..." , "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
		 TimeUnit.SECONDS.sleep(5);
		 JOptionPane.showMessageDialog(null,"Prodotti erogati." , "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
	}

}