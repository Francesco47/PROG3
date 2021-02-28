package distributore;
import java.sql.SQLException;
import javax.swing.*;

/**
 * Classe DistributoreTest (main) utilizzata per eseguire il distributore
 *  @version 1.0
 * @author Francesco D'Angelo, Giuseppe Franzese e Salvatore Iovinella
 *
 */
public class DistributoreTest extends JFrame{

	public static void main(String[] args) throws SQLException, InterruptedException {

		scelta();
		
}
	public static void scelta() {
		Object [] options = {"Amministratore", "Cliente"}; //Scelta tipo utente
		String in = null; //conversione in stringa input scelta tipo utente
		Object input; //input scelta tipo utente
		input = JOptionPane.showInputDialog(null, "Che tipo di utente sei? ", "DISTRIBUTORE" , JOptionPane.INFORMATION_MESSAGE, null, options ,null);
		
		if(input != null)
		{
			 in = input.toString();
		}else {
			System.exit(0);
		}
		if(in.equals("Amministratore"))
		{
			new GraficaAdmin();
			}
		else if(in.equals("Cliente"))
		{
			new GraficaCliente();
				}
		}

	}