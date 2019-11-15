
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * EchoServer
 * 
 * Classe principale per la gestione del server
 * Crea inizialmente un ServerSocket con una porta di default (5000). Ad ogni
 * creazione di un socket viene creato un apposito thread per l'ascolto dei
 * messaggi.
 * 
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class EchoServer {

    public static final int PORT = 5000; // porta al di fuori del range 1-4096 !

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        DataContainer data = new DataContainer();
        
        System.out.println("EchoServer: avviato ");
        System.out.println("Socket del server: " + serverSocket);
        ArrayList<Socket> listaSocket = new ArrayList<>();
        Scanner keyboard = new Scanner(System.in);
        
        // Thread per l'inserimento dei comandi
        ThreadCommandServer cmd = new ThreadCommandServer(keyboard, data);
        Thread tCmd = new Thread(cmd);
        tCmd.start();
        
        while (true) {
            try {
                // Server in attesa della connessione
                Socket clientSocket = serverSocket.accept();
                
                InputStreamReader stringaIn = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader in = new BufferedReader(stringaIn);
                
                // Lettura username
                String username = in.readLine();
                
                // Stampa dell'utente connesso al server
                System.out.println("\nClient connesso: " + clientSocket.getInetAddress().toString().replace("/", "") + " Username: " + username + "\n");
                
                // Creazione di un thread dedicato al client
                Runnables r = new Runnables(clientSocket, data, username);
                Thread t1 = new Thread(r);
                t1.start();

            } catch (BindException e) {
                System.err.println("Server già avviato!");   
            } catch (IOException e) {
                System.err.println("\nErrore nella creazione del server\n");
                System.exit(1);
            }
        }
    }
}
