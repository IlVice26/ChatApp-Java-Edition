
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * RunnablesClient
 *
 * Thread dedicato al Client per l'invio dei messaggi al server.
 *
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class RunnablesClient implements Runnable {

    Socket mySock;
    ThreadCommandClient thread;

    RunnablesClient(Socket socket, ThreadCommandClient thread) {
        this.mySock = socket;
        this.thread = thread;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(mySock.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(isr);
            while (true) {
                String mex = in.readLine();
                if (mex == null) {
                    System.out.println("Disconnesso dal server, premere invio"
                            + " per continuare.");
                    thread.inChat = false;
                    break;
                } else {
                    System.out.println(mex);
                }
            }
        } catch (IOException ex) {
            thread.inChat = false;
            try {
                mySock.close();
            } catch (IOException ex1) { }
        }

    }

}
