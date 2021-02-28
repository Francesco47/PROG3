package distributore;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

/**
 * Classe Cliente gestisce tutte funzionalità che un cliente puo' effettuare nel distributore Distributore ovvero
 * {@link #selezionaProdotto(Prodotto)} seleziona il prodotto, {@link #inserisciDenaro(double)} per acquistare il prodotto scelto 
 * ed effettua il pagamento e l'erogazione tramite {#link {@link Distributore#erogaProdotto(Prodotto, double)}}
 * @version 1.0
 * @author Francesco D'Angelo, Giuseppe Franzese e Salvatore Iovinella
 *
 */
public class Cliente{	
	final String jdbcURL = "jdbc:derby:distributore;create=true";
	ArrayList<String> carrello= new ArrayList<>();
	String[] controlloProdotti = new String[100];

	private double prezzo; //prezzo dei prodotti acquistati
	private double totale = 0; //totale importo da pagare
	
	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	/**
	 * metodo per la scelta del prodotto e/o prodotti nel distributore
	 */
	public void  selezionaProdotto(Prodotto p) throws SQLException
	{	
		boolean controllo = false; //controllare se il codice scelto appartiene ad un prodotto effettivo
		Connection connection;
		Statement s1; //statement per eseguire la query utilizzata per mostrare tutti i prodotti
		Statement s2; //statement per eseguire la query utilizzata per diminuire la quantità del prodotto scelto
		Statement s3; //statement per eseguire la query utilizzata per aggiornare la tabella cronologia
		ResultSet result;
		connection = DriverManager.getConnection(jdbcURL);
		try {
			s1 = connection.createStatement();
			s2 = connection.createStatement();
			s3 = connection.createStatement();
			result = s1.executeQuery("SELECT * FROM PRODOTTO");
			while(result.next())
			{

				if(result.getString("codice").equals(p.getCodice()))
				{
					controllo = true;
					String sql = "INSERT INTO CRONOLOGIA VALUES ('"+p.getCodice()+"')";
					s3.executeUpdate(sql);
					if(result.getDouble("quantita")==0)
					{
						JOptionPane.showMessageDialog(null,"Il prodotto non e' disponibile. Ritorno al menu...", "DISTRIBUTORE", JOptionPane.ERROR_MESSAGE);
						break;
					}
					else 
					{
						prezzo = result.getDouble("prezzo");
						s2.executeUpdate("UPDATE PRODOTTO SET QUANTITA=QUANTITA-1 WHERE CODICE='"+p.getCodice()+"' ");
					}
				}		
			}
			
			if(controllo == false)
			{
				JOptionPane.showMessageDialog(null, "Il prodotto non è presente.", "DISTRIBUTORE", JOptionPane.ERROR_MESSAGE);
			}
			else {
			totale+=prezzo;
			setTotale(totale);
			JOptionPane.showMessageDialog(null,"IMPORTO TOTALE: "+totale, "DISTRIBUTORE", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * metodo per il pagamento con contanti
	 * @param t
	 * @throws InterruptedException
	 */
	public void inserisciDenaro(double t) throws InterruptedException { //metodo per il pagamento con contanti
		String pagamento;
		double p, importoRimanente = t , totalePagato = 0;
		
		do {
			JOptionPane.showMessageDialog(null, "IMPORTO DA PAGARE: "+ importoRimanente,  "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
			
			pagamento = JOptionPane.showInputDialog(null, "Inserisci denaro",  "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
			 p = Double.parseDouble(pagamento);
			while(p!=0.10 && p!=0.20 && p!=0.50 && p!=1 && p!=2 && p!=5 && p!=10 && p!=20)
			{
				JOptionPane.showMessageDialog(null, "Inserire un importo corretto!", "DISTRIBUTORE", JOptionPane.ERROR_MESSAGE);
				pagamento = JOptionPane.showInputDialog(null, "Inserisci denaro",  "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
				 p = Double.parseDouble(pagamento);
			}
			 importoRimanente -= p;
			 totalePagato += p;
			 if(importoRimanente<0)
				{
					double resto = totalePagato - t;
					JOptionPane.showMessageDialog(null, "RESTO: " +resto +"\n EROGAZIONE DEL RESTO IN CORSO...", "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
					TimeUnit.SECONDS.sleep(3);
					JOptionPane.showMessageDialog(null, "RESTO EROGATO.", "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
				}
		}while(importoRimanente>0);
		
		JOptionPane.showMessageDialog(null, "PAGAMENTO EFFETTUATO CORRETTAMENTE.",  "DISTRIBUTORE", JOptionPane.QUESTION_MESSAGE);
	}
}