
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * 
 * @author Vicentini Elia, Gandini Simone
 */
public class EchoServer {

    public static final int PORT = 5000; // porta al di fuori del range 1-4096 !

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("EchoServer: avviato ");
        System.out.println("Socket del server: " + serverSocket);
        ArrayList<Socket> listaSocket = new ArrayList<>();
        
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept(); // in attesa finchè non avviene una connessione
                System.out.println("EchoServer: connesso: " + clientSocket);
                listaSocket.add(clientSocket);
                Runnables r = new Runnables(listaSocket, clientSocket);
                Thread t1 = new Thread(r);
                t1.start();

            } catch (IOException e) {
                System.err.println("Ricezione fallita");
                System.exit(1);
            }
        }
    }
}
