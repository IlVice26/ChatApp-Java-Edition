
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
    private ArrayList<Socket> socketContainer;
    private ArrayList<String> usersContainer;
    
    public DataContainer(){
        socketContainer = new ArrayList<>();
        usersContainer = new ArrayList<>();
    }
    
    public synchronized void addUser(Socket socket, String username){
        socketContainer.add(socket);
        usersContainer.add(username);
    }
    
    public synchronized void remUser(Socket socket, String username){
        socketContainer.remove(socket);
        usersContainer.remove(username);
    }
    
    public synchronized String viewUsers(){
        return usersContainer.toString();
    }
    
    public synchronized ArrayList<Socket> getListSocket(){
        return socketContainer;
    }
    
}
