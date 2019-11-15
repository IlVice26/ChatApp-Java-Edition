
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class RunnablesClient implements Runnable {
    ArrayList<String> user = new ArrayList<>();
    Socket mySock;

    RunnablesClient(Socket socket, ArrayList<String> user) {
        this.mySock = socket;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(mySock.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            while (true) {
                if(in.equals("quit"))break;
                System.out.println(in.readLine());
            }
        } catch (IOException ex) {
            System.out.println("EchoServer: Connessione interrotta!");
            System.exit(0);
        }

    }

}
