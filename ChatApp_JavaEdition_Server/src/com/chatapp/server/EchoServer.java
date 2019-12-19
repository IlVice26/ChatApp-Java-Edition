package com.chatapp.server;


import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * 
 * EchoServer
 * 
 * Classe principale per la gestione del server
 * Crea inizialmente un ServerSocket con una porta di default (5000). Ad ogni
 * creazione di un socket viene creato un apposito thread per l'ascolto dei
 * messaggi.
 * 
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {
        
        System.out.println("ChatApp - Java Edition (dev01) - Server"
                + "\nCopyright 2019 - Vicentini Elia & Simone Gandini"
                + "\n\nDigita /help per conoscere i comandi\n");
        
        DataContainer data = new DataContainer();
        ServerSocket serverSocket = new ServerSocket(data.getPortServer());
        
        Scanner keyboard = new Scanner(System.in);
        
        // Thread per l'inserimento dei comandi
        ThreadCommandServer cmd = new ThreadCommandServer(keyboard, data);
        Thread tCmd = new Thread(cmd);
        tCmd.start();
        
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
