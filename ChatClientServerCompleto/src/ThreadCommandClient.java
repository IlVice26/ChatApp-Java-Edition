
import java.io.IOException;
import java.io.*;
import java.util.Scanner;
import java.net.Socket;


/**
 *
 * ThreadCommandClient
 * 
 * description
 * 
 * @author Vicentini Elia
 * @version dev01
 */
public class ThreadCommandClient implements Runnable {
    
    private Scanner cliKeyboard;
    
    private Socket mySock;
    private InputStreamReader stringaIn;
    private BufferedReader in;
    private OutputStreamWriter stringaOut;
    private BufferedWriter out;
    private PrintWriter send;
    
    private RunnablesClient t1;
    
    public boolean inChat = false;
    
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

                            // Input e Output del server
                            this.stringaIn = new InputStreamReader(mySock.getInputStream());
                            this.stringaOut = new OutputStreamWriter(mySock.getOutputStream());
                            this.in = new BufferedReader(this.stringaIn);
                            this.out = new BufferedWriter(this.stringaOut);
                            this.send = new PrintWriter(this.out, true);

                            // Invio dell'username
                            send.println(user);

                            RunnablesClient t1 = new RunnablesClient(mySock, this);
                            Thread th1 = new Thread(t1);
                            th1.start();

                            this.inChat = true;
                            break;

                        } catch (IOException ex) {
                            // Errore nella connessione
                            System.out.println("\nErrore nella connessione!"
                                    + "\nNessun server trovato a questo indirizzo\n");
                        }

                        break;
                    case "/help":
                        System.out.println("\nComandi chat client:"
                            + "\nComando        |       Descrizione"
                            + "\n/connect       |       Connette il client al server" 
                            + "\n/quit          |       Uscita dal server"
                            + "\n");
                        break;
                    case "/quit":
                        System.exit(0);
                    case "":
                        break;
                    default:
                        System.out.println("\nComando sconosciuto\n");
                }

            } else {
                
                String cmd = cliKeyboard.nextLine();
                
                switch (cmd) {
                    case "/disconnect":
                        try {
                            mySock.close();
                            stringaIn.close();
                            in.close();
                            stringaOut.close();
                            out.close();
                            send.close();
                            this.inChat = false;
                            System.out.println("\n");
                        } catch (IOException ex) { 
                            this.inChat = false;
                        }
                        break;
                    case "/help":
                        System.out.println("\nComandi chat client:"
                            + "\nComando        |       Descrizione"
                            + "\n/disconnect    |       Disconnette il client dal server" 
                            + "\n/quit          |       Uscita dal server"
                            + "\n");
                        break;
                    default:
                        send.println(cmd);
                }
            }
        }
    }
}
