
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private ArrayList<String> config;
    
    public DataContainer() throws IOException{
        this.socketContainer = new ArrayList<>();
        this.usersContainer = new ArrayList<>();
        this.config = loadConfig();
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
    
    private ArrayList<String> loadConfig() throws FileNotFoundException, IOException{
        ArrayList<String> conf = new ArrayList<>();
        BufferedReader read = new BufferedReader(new FileReader("config.ini"));
        
        // Carico i dati nella Array
        String line = read.readLine();
        while (line != null) {
            String[] temp1 = line.split(" = ");
            conf.add(temp1[1]);
            line = read.readLine();
        }
        
        // Controllo che i dati siano soltanto 3
        while (conf.size() > 3) {
            conf.remove(conf.get(conf.size() - 1));
        }
        
        return conf;
    }
    
    public synchronized String getNameServer(){
        return config.get(0);
    }
    
    public synchronized int getPortServer(){
        return Integer.parseInt(config.get(1));
    }
    
    public synchronized String getMotdServer(){
        return config.get(2);
    }
    
}
