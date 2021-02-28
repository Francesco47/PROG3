package distributore;
import javax.swing.JOptionPane;

public class Dist_InFunzione implements StatoDistributore{

	private static final String statoInFunzione = "Il distributore e' in funzione";
	@Override
	public void showStato() {
		
		JOptionPane.showMessageDialog(null, statoInFunzione);
		
	}
	
	

}
