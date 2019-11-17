
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *  
 * Runnables
 * 
 * Thread creato da EchoServer per la ricezione dei messaggi con il Client.
 * 
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class Runnables implements Runnable {

    private Socket mySock;
    private DataContainer users;
    private String username;
    private boolean printMessage = false;
    
    Runnables(Socket clientSocket, DataContainer users, String username) {
        this.mySock = clientSocket;
        this.users = users;
        this.username = username;
        synchronized (users) { users.addUser(mySock, username);}
    }

    @Override
    public void run() {
        
        InputStreamReader stringaIn;
        OutputStreamWriter stringaOut;
        BufferedWriter buffer;
        PrintWriter out;

        try {
            stringaIn = new InputStreamReader(mySock.getInputStream());
            BufferedReader in = new BufferedReader(stringaIn);

            while (true) {
                
                // Lettura del messaggio da parte del client
                String str = in.readLine();
                
                // Controllo se il messaggio è la stringa di chiusura del socket
                if (str.equals("/quit")) {
                    System.out.println("\nRichesta chiusura connessione con " + mySock.getInetAddress().toString().replace("/", "") + "\n");
                    break;
                }
                
                // Invio dei messaggi a tutti i client
                int sizeList;   
                for (int i = 0; i < users.getListSocket().size(); i++) {
                    if (users.getListSocket().get(i) != mySock){
                        stringaOut = new OutputStreamWriter(users.getListSocket().get(i).getOutputStream());
                        buffer = new BufferedWriter(stringaOut);
                        out = new PrintWriter(buffer, true);
                        out.println(str);
                    }
                }
            }
            
            // Se viene inviata una stringa di chiusura, il server elimina il socket dalla lista
            synchronized (users) {
                users.remUser(mySock, username);
            } 
            
        } catch (IOException ex) {
            synchronized (users) {
                users.remUser(mySock, username);
            }
        } 

    }

}
