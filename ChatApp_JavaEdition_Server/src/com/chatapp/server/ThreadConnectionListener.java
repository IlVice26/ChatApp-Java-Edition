
package com.chatapp.server;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author vicen
 */
public class ThreadConnectionListener implements Runnable {
    
    public ServerSocket serverSocket;
    public DataContainer data;
    
    public ThreadConnectionListener(DataContainer data, ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.data = data;
    }
    
    /**
     * 
     */
    @Override
    public void run(){
        while (true) {
            try {
                // Server in attesa della connessione
                Socket clientSocket = serverSocket.accept();
                
                InputStreamReader stringaIn = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader in = new BufferedReader(stringaIn);
                
                // Lettura username
                String username = in.readLine();
                
                // Creazione di un thread dedicato al client
                Runnables r = new Runnables(clientSocket, data, username);
                Thread t1 = new Thread(r);
                t1.start();
 
            } catch (IOException e) {
                
            }
        }
    }
    
}
