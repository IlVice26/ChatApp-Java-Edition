
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 17821
 */
public class Runnables implements Runnable {

    ArrayList<Socket> listaSocket = new ArrayList<>();
    Socket mySock;

    Runnables(ArrayList<Socket> listaSocket, Socket clientSocket) {
        this.listaSocket = listaSocket;
        this.mySock = clientSocket;
    }

    @Override
    public void run() {
// creazione stream di input da clientSocket:
        InputStreamReader stringaIn;
        OutputStreamWriter stringaOut;
        BufferedWriter buffer;
        PrintWriter out;

        try {
            stringaIn = new InputStreamReader(mySock.getInputStream());
            BufferedReader in = new BufferedReader(stringaIn);
// ciclo di ricezione dal client e invio di risposta
            while (true) {
                String str = in.readLine();
                if (str.equals("quit")) {
                    System.out.println("Chiudo la connesione con: " + mySock);
                    break;
                }
                //System.out.println("Ripeto il messaggio ricevuto da: " + mySock + " " + str);
                for (int i = 0; i < listaSocket.size(); i++) {
                    stringaOut = new OutputStreamWriter(listaSocket.get(i).getOutputStream());
                    buffer = new BufferedWriter(stringaOut);
                    out = new PrintWriter(buffer, true);
                    out.println(str);
                }
            }

            listaSocket.remove(mySock);
// chiusura di stream e socket
        } catch (IOException ex) {
            System.out.println("EchoServer: chiudo...");
            Logger.getLogger(Runnables.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
