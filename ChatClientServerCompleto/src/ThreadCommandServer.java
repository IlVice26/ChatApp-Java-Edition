
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
    private DataContainer users;
    
    /**
     * Costruttore
     * @param serKeyboard
     * @param users 
     */
    public ThreadCommandServer(Scanner serKeyboard, DataContainer users){
        this.serKeyboard = serKeyboard;
        this.users = users;
    }
    
    @Override
    public void run(){
        
        while (true){
            // Ascolto della tastiera per eventuali comandi
            String cmd = serKeyboard.nextLine();

            // Comandi inseriti
            switch (cmd) {
                case "/users":
                    synchronized (users) {
                        System.out.println(users.viewUsers());
                    }
            }
        }
    }
    
}
