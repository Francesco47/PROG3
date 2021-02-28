package distributore;

/**
 * Classe Categoria per la gestione delle categorie di prodotti nel distributore
 * @version 1.0
 * @author Francesco D'Angelo, Giuseppe Franzese e Salvatore Iovinella
 *
 */
public class Categoria {

		private String codice;
		private String nome;
		private String campo;
		private String valore;
		
		/**
		 * costruttore utilizzato per effettuare la creazioni di oggetti di tipo categoria
		 */
		public Categoria()
		{
			
		}
		
		/**
		 * costruttore utilizzato per l'inserimento di una categoria
		 */
		public Categoria(String codice, String nome)
		{
			this.codice = codice;
			this.nome = nome;
		}
		
		public Categoria(String codice, String nome, String campo, String valore) {
			this.codice = codice;
			this.nome = nome;
			this.campo = campo;
			this.valore = valore;
		}
		
		/**
		 * costruttore utilizzato per ereditare il campo codice all'interno di Prodotto
		 * @param codice
		 */
		public Categoria(String codice)
		{
			this.codice =codice;
		}
		public String getCodiceCat() { 
			return codice;
			}
		public void setCodiceCat(String codice) { 
			this.codice = codice;
			}
		public String getNomeCat() {
			return nome;
			}
		public void setNomeCat(String nome) {
			this.nome = nome;
			}
		public String getCampo() {
			return campo;
			}
		public void setCampo(String campo) {
			this.campo = campo;
			}
		public String getValore() {
			return valore;
			}
		public void setValore(String valore) {
			this.valore = valore;
			}
	}