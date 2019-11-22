
import java.util.Scanner;

/**
 *
 * @author vicen
 */
public class ThreadCommandClient implements Runnable {
    
    private Scanner cliKeyboard;
    private boolean inChat = false;
    
    public ThreadCommandClient(Scanner cliKeyboard){
        this.cliKeyboard = cliKeyboard;
    }

    @Override
    public void run(){
        
        if (inChat != false) {
            // Ascolto dei comandi in caso sia 
            System.out.print("Client> ");
            String cmd = cliKeyboard.nextLine();
            String cmd1 = cmd.replace(" ", "");
            
            switch (cmd1) {
                case "/connect":
                    break;
                case "/exit":
                    System.exit(0);
                default:
                    System.out.println("\nComando sconosciuto\n");
            }
            
        }
        
    }
}
