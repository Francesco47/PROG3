package distributore;
import java.sql.SQLException;


/**
 * Interfaccia che contiene tutti i metodi che vengono implementati in Prodotto
 *  @version 1.0
 * @author Francesco D'Angelo, Giuseppe Franzese e Salvatore Iovinella
 *
 * @param <T> paramentro di tipo Prodotto
 */

public interface InterfaceProdottoDao<T> {
	public void insertProdotto(T t) throws SQLException;
	public void updateProdotto(T t) throws SQLException;
	public void updateProdottoString(T t) throws SQLException;
	public void deleteProdotto(T t) throws SQLException;
}
