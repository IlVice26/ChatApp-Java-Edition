
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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
        
        System.out.println("ChatApp - Java Edition (dev01) - Client"
                + "\nCopyright 2019 - Vicentini Elia & Simone Gandini"
                + "\n\nDigita /help per conoscere i comandi\n");
        
        Scanner cliKeyboard = new Scanner(System.in);
        
        ThreadCommandClient cmd = new ThreadCommandClient(cliKeyboard);
        Thread tCmd = new Thread(cmd);
        tCmd.start();
            
    }
    
}
