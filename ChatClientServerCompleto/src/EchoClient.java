
import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * 
 * EchoClient
 * 
 * Classe principale del Client.
 * 
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class EchoClient {

    public static void main(String[] args) throws IOException {

        ArrayList<String> user;
        user = new ArrayList<>();
        String indirizzo;
        if (args.length == 0) {
            indirizzo = "localhost";
        } else {
            indirizzo = "localhost";
        }
        try {
            System.out.print("Provo la connessione con " + indirizzo + " ... ");
            Socket socket = new Socket(indirizzo, EchoServer.PORT);
            System.out.print("Connesso al server!");
            System.out.println("\nSocket del client: " + socket);
            System.out.print("Username: ");
            String str;
            String userInput;

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            str = stdIn.readLine();
            user.add(str);

            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter out = new PrintWriter(bw, true);
            
            // Invio dell'username
            out.println(str);
            
            RunnablesClient r = new RunnablesClient(socket, user);
            Thread t1 = new Thread(r);
            t1.start();

            while (true) {
                userInput = stdIn.readLine();
                if (userInput.equals("quit")) {
                    out.println("quit");
                    break;
                }
                out.println(str + ": " + userInput);
            }

            out.close();
            //in.close();
            stdIn.close();
            socket.close();
            t1.stop();
            user.remove(str);

        } catch (UnknownHostException e) {
            System.err.print("Host non riconosciuto... " + indirizzo + "\n");
            System.exit(1);
        } catch (IOException e) {
            System.err.print("Non riesco ad avere I/O per la connessione a: " + indirizzo + "\n");
            System.exit(1);
        }
        System.out.println("EchoClient: passo e chiudo...");
    }
}
