
import java.io.IOException;
import java.util.Scanner;

/**
 *  
 * ThreadCommandServer
 * 
 * Questa classe permette di inserire comandi per la gestione del server.
 * Utilizza un thread dedicato per l'ascolto dell'input della tastiera.
 * 
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class ThreadCommandServer implements Runnable {
    
    // Global variables
    private Scanner serKeyboard;
    private DataContainer data;
    
    /**
     * Costruttore
     * @param serKeyboard
     * @param data 
     */
    public ThreadCommandServer(Scanner serKeyboard, DataContainer data){
        this.serKeyboard = serKeyboard;
        this.data = data;
    }
    
    @Override
    public void run() {
        
        while (true){
            // Ascolto della tastiera per eventuali comandi
            System.out.print("Server> ");
            String cmd = serKeyboard.nextLine();
            String cmd1 = cmd.replace(" ", "");
            
            // Comandi inseriti
            switch (cmd1) {
                case "/users":
                    synchronized (data) {
                        System.out.println("\n" + data.viewUsers() + "\n");
                    }
                    break;
                case "/help":
                    System.out.println("\nComandi chat server:"
                            + "\nComando        |       Descrizione" 
                            + "\n/users         |       Visualizza tutti gli utenti collegati al server"
                            + "\n/config        |       Visualizza la configurazione del server"
                            + "\n/changename    |       Cambia il nome del server"
                            + "\n/changeport    |       Cambia la porta del server (Richiede un riavvio per il funzionamento)"
                            + "\n/changemotd    |       Cambia il motd (Message of the day) del server"
                            + "\n/help          |       Visualizza tutti i comandi del server"
                            + "\n");
                    break;
                case "/config":
                    synchronized (data) {
                        System.out.println("\nConfigurazione server:"
                                + "\nNome: " + data.getNameServer()
                                + "\nPort: " + data.getPortServer()
                                + "\nMotd: " + data.getMotdServer() + "\n");
                    }
                    break;
                case "/changename":
                    System.out.print("\nNome: ");
                    String name = serKeyboard.nextLine();
                    try {
                        data.setNameServer(name);
                    } catch (IOException e) {
                        System.out.println("Modifica non effettuta!");
                        break;
                    }
                    
                    System.out.println("Modifica effettuata con successo!\n");
                    break;
                case "/changeport":
                    System.out.print("\nPorta: ");
                    String port = serKeyboard.nextLine();
                    try {
                        data.setPortServer(port);
                    } catch (IOException e) {
                        System.out.println("Modifica non effettuta!");
                        break;
                    }  
                    
                    System.out.println("Modifica effettuata con successo! (Richiesto il riavvio per il corretto funzionamento)\n");
                    break;
                case "/changemotd":
                    System.out.print("\nMotd: ");
                    String motd = serKeyboard.nextLine();
                    try {
                        data.setMotdServer(motd);
                    } catch (IOException e) {
                        System.out.println("Modifica non effettuta!");
                        break;
                    }  
                    
                    System.out.println("Modifica effettuata con successo!\n");
                    break;
                case "/quit":
                    System.exit(0);
                default:
                    System.out.println("\nComando sconosciuto\n");
            }
        }
    }
    
}
