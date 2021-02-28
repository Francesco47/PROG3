package distributore;
import java.sql.SQLException;
/**
 * Interfaccia che contiene tutti i metodi che vengono implementati in Categoria
 *  @version 1.0
 * @author Francesco D'Angelo, Giuseppe Franzese e Salvatore Iovinella
 *
 * @param <T> paramentro di tipo Categoria
 */
public interface InterfaceCategoriaDao<T> {
	
	public void insertCategoria(T t) throws SQLException;
	public void updateCategoria(T t) throws SQLException;
	public void deleteCategoria(T t) throws SQLException;
}
