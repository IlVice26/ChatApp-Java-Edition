
import java.util.ArrayList;
import java.net.Socket;

/**
 * 
 * DataContainer
 * 
 * Contiene tutti i dati sincronizzati del server e viene creato da EchoServer. 
 * 
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class DataContainer {
    
    // Global variables
    private ArrayList<Socket> userContainer;
    
    public DataContainer(){
        userContainer = new ArrayList<>();
    }
    
    public synchronized void addUser(Socket socket){
        userContainer.add(socket);
    }
    
    public synchronized void remUser(Socket socket){
        boolean remove = userContainer.remove(socket);
    }
    
    public synchronized String viewUsers(){
        return userContainer.toString();
    }
    
}
