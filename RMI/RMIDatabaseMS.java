
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIDatabaseMS extends Remote {

	/**
	 * Check, if a collection exists
	 *
	 * @param category Pass the category of the request
	 * @param searchTerm Pass the term to search
	 * @return Return true, if the collection already exists and is valid, else return false
	 * @throws RemoteException If something went wrong with RMI
	 */
	boolean hasValidCollection(String category, String searchTerm) throws RemoteException;
}
