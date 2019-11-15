
import java.util.Scanner;

/**
 *
 * @author 18039
 */
public class ThreadCommandServer implements Runnable {
    
    // Global variables
    private Scanner serKeyboard;
    private DataContainer users;
    
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
