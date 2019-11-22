
import java.io.IOException;
import java.util.Scanner;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vicen
 */
public class ThreadCommandClient implements Runnable {
    
    private Scanner cliKeyboard;
    private Socket mySock;
    private boolean inChat = false;
    
    public ThreadCommandClient(Scanner cliKeyboard){
        this.cliKeyboard = cliKeyboard;
    }

    @Override
    public void run(){
        
        while (true) {
            if (inChat == false) {
                // Ascolto dei comandi in caso sia 
                System.out.print("Client> ");
                String cmd = cliKeyboard.nextLine();
                String cmd1 = cmd.replace(" ", "");

                switch (cmd1) {
                    case "/connect":
                        // Lettura ip e porta
                        System.out.print("\nIp: ");
                        String ip = cliKeyboard.nextLine();
                        System.out.print("Porta: ");
                        int port = Integer.parseInt(cliKeyboard.nextLine());
                        System.out.print("Username: ");
                        String user = cliKeyboard.nextLine();

                        try {
                            // Connessione al server
                            this.mySock = new Socket(ip, port);
                            

                        } catch (IOException ex) {
                            // Errore nella connessione
                            System.out.println("\nErrore nella connessione!"
                                    + "\nNessun server trovato a questo indirizzo\n");
                        }

                        break;

                    case "/exit":
                        System.exit(0);
                    case "":
                        break;
                    default:
                        System.out.println("\nComando sconosciuto\n");
                }

            }
        }
    }
}
