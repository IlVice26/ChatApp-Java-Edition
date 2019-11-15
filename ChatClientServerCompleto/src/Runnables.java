
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
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class Runnables implements Runnable {

    private ArrayList<Socket> listaSocket = new ArrayList<>();
    private Socket mySock;
    private DataContainer users;
    
    Runnables(Socket clientSocket, DataContainer users) {
        this.listaSocket = listaSocket;
        this.mySock = clientSocket;
        this.users = users;
        synchronized (users) {
            users.addUser(mySock);
            System.out.println(users.viewUsers());
        }
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
                for (int i = 0; i < listaSocket.size(); i++) {
                    stringaOut = new OutputStreamWriter(listaSocket.get(i).getOutputStream());
                    buffer = new BufferedWriter(stringaOut);
                    out = new PrintWriter(buffer, true);
                    out.println(str);
                }
            }

            listaSocket.remove(mySock);
            
        } catch (IOException ex) {
            System.out.println("EchoServer: chiudo...");
            Logger.getLogger(Runnables.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
