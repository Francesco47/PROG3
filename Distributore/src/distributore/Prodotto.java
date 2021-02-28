package distributore;
/**
 * 
 * Classe Prodotto per gestire i prodotti nel distributore
 * @version 1.0
 * @author Francesco D'Angelo, Giuseppe Franzese e Salvatore Iovinella
 *
 *
 */
public class Prodotto extends Categoria{
	private String codice;
	private String nome;
	private double prezzo;
	private double quantita;
	private String campo;
	private String valore;
	
	/**
	 * 	costruttore utilizzato per creare oggetti all'interno di altre classi
	 */
	public Prodotto() { 
		
	}
	
	/**
	 * costruttore della classe prodotto che presenta l'istruzione
	 *	super(codiceCat) prende il codice della categoria dalla superclasse Categoria
	 * 
	 */
	public Prodotto (String codice, String nome, double prezzo , double quantita, String codiceCat)
	{
		
		super(codiceCat); 
		this.codice = codice;
		this.nome = nome;
		this.prezzo = prezzo;
		this.quantita = quantita;
		
	}
	public Prodotto(String campo, String val)
	{
		this.campo = campo;
		this.valore = val;
	}
	

	public String getCodice() { 
		return codice; 
		}
	public void setCodice(String codice) {
		this.codice = codice; 
		}
	public String getNome() {
		return nome; 
		}
	public void setNome(String nome) {
		this.nome = nome;
		}
	public double getPrezzo() {
		return prezzo;
		}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
		}
	public double getQuantita() {
		return quantita;
		}
	public void setQuantita(double quantita) {
		this.quantita = quantita;
		}
	@Override
	public void setCampo(String campo) {
		this.campo = campo;
		}
	@Override
	public String getCampo() {
		return campo;
		}
	@Override
	public void setValore(String valore) {
		this.valore = valore;
		}
	@Override
	public String getValore() {
		return valore;
		}
}
