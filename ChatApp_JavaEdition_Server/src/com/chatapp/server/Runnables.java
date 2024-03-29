package com.chatapp.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


/**
 *  
 * Runnables
 * 
 * Thread creato da EchoServer per la ricezione dei messaggi con il Client.
 * 
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class Runnables implements Runnable {

    private Socket mySock;
    private DataContainer data;
    private String username;
    private InputStreamReader stringaIn;
    private OutputStreamWriter stringaOut;
    private BufferedWriter buffer;
    private PrintWriter out;
    private boolean printMessage = false;
    
    Runnables(Socket clientSocket, DataContainer users, String username) {
        this.mySock = clientSocket;
        this.data = users;
        this.username = username;
        synchronized (users) { 
            users.addUser(mySock, username);
        }
        
    }

    @Override
    public void run() {
        
        try {
    
            stringaOut = new OutputStreamWriter(mySock.getOutputStream(), StandardCharsets.UTF_8);
            buffer = new BufferedWriter(stringaOut);
            out = new PrintWriter(buffer, true);
            out.println("\nBenvenuto sul server: " + data.getNameServer() + "!\n"
                    + data.getMotdServer() + "\n\n");
            
            stringaIn = new InputStreamReader(mySock.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(stringaIn);

            while (true) {
                
                try {
                    // Lettura del messaggio da parte del client
                    String str = in.readLine();

                    // Controllo se il messaggio è la stringa di chiusura del socket
                    if (str.equals("/quit")) {
                        break;
                    }
                    
                    // Invio dei messaggi a tutti i client
                    int indexUser = getIndexOfUser();
                    int sizeList;   
                    for (int i = 0; i < data.getListSocket().size(); i++) {
                        if (data.getListSocket().get(i) != mySock){
                            stringaOut = new OutputStreamWriter(data.getListSocket().get(i).getOutputStream(), StandardCharsets.UTF_8);
                            buffer = new BufferedWriter(stringaOut);
                            out = new PrintWriter(buffer, true);
                            out.println(data.getListUsers().get(indexUser) + ": " + str);
                        }
                    }
                } catch (Exception e) {
                    break;
                }
            }
            
            // Se viene inviata una stringa di chiusura, il server elimina il socket dalla lista
            synchronized (data) {
                data.remUser(mySock, username);
            } 
            
            mySock.close();
            // stringaIn.close();
            // stringaOut.close();
            
        } catch (IOException ex) {
            synchronized (data) {
                data.remUser(mySock, username);
            }
            System.out.println("Errore rilevato");
            try {
                mySock.close();
                // stringaIn.close();
                // stringaOut.close();
            } catch (IOException ex1) {
            }
        }

    }
    
    private int getIndexOfUser(){
        for (int i = 0; i < data.getListSocket().size(); i++) {
            if (data.getListSocket().get(i) == mySock) {
                return i;
            }
        }
        return 0;
    }

}
