
import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * 
 * @author Vicentini Elia, Gandini Simone
 */
public class EchoClient {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException {

        ArrayList<String> user;
        user = new ArrayList<>();
        String indirizzo;
        if (args.length == 0) {
            indirizzo = "172.16.3.239";
        } else {
            indirizzo = "172.16.3.239";
        }
        try {

            Socket socket = new Socket(indirizzo, EchoServer.PORT);
            System.out.println("EchoClient: avviato");
            System.out.println("Socket del client: " + socket);
            System.out.print("Username: ");
            String str;
            String userInput;

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            str = stdIn.readLine();
            user.add(str);

            RunnablesClient r = new RunnablesClient(socket, user);
            Thread t1 = new Thread(r);
            t1.start();

            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter out = new PrintWriter(bw, true);

            while (true) {
                userInput = stdIn.readLine();
                if (userInput.equals("quit")) {
                    out.println(str + " ha abbandonato la chat ");
                    out.println("quit");
                    break;
                }
                out.println(str + ": " + userInput);
                //System.out.println("Echo: " + in.readLine());
            }

            out.close();
            //in.close();
            stdIn.close();
            socket.close();
            t1.stop();
            user.remove(str);

        } catch (UnknownHostException e) {
            System.err.println("Host non riconosciuto... " + indirizzo);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Non riesco ad avere I/O per la connessione a: " + indirizzo);
            System.exit(1);
        }
        System.out.println("EchoClient: passo e chiudo...");
    }
}
