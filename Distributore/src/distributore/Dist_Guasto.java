package distributore;
import javax.swing.JOptionPane;

public class Dist_Guasto implements StatoDistributore {

	private static final String statoGuasto = "Il distributore e' guasto";
	@Override
	public void showStato() {
		
		JOptionPane.showMessageDialog(null, statoGuasto);
		
	}
}
