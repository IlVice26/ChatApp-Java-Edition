
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    Runnables(Socket clientSocket, DataContainer users) {
        this.mySock = clientSocket;
        this.users = users;
        synchronized (users) { users.addUser(mySock);}
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
                String str = in.readLine();
                if (str.equals("quit")) {
                    System.out.println("Richesta chiusura connessione con " + mySock.getInetAddress().toString().replace("/", ""));
                    break;
                }
                System.out.println("Messaggio ricevuto da: " + mySock.getInetAddress().toString().replace("/", "") + " " + str);
                int sizeList;
                    
                for (int i = 0; i < users.getList().size(); i++) {
                    if (users.getList().get(i) != mySock){
                        stringaOut = new OutputStreamWriter(users.getList().get(i).getOutputStream());
                        buffer = new BufferedWriter(stringaOut);
                        out = new PrintWriter(buffer, true);
                        out.println(str);
                    }
                }
            }
               
            synchronized (users) {
                users.remUser(mySock);
            } 
            
        } catch (IOException ex) {
            System.out.println("Disconessione improvvisa del client " + mySock.getInetAddress().toString().replace("/", ""));
            synchronized (users) {
                users.remUser(mySock);
            }
        } 

    }

}
