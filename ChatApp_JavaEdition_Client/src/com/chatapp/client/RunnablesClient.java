package com.chatapp.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


/**
 *
 * RunnablesClient
 *
 * Thread dedicato al Client per l'invio dei messaggi al server.
 *
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class RunnablesClient implements Runnable {

    Socket mySock;
    ChatAppClientFrame thread;

    RunnablesClient(Socket socket, ChatAppClientFrame thread) {
        this.mySock = socket;
        this.thread = thread;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(mySock.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(isr);
            while (true) {
                String mex = in.readLine();
                if (mex == null) {
                    ChatAppClientFrame.panelArea.append("\nDisconnesso dal server, premere invio"
                            + " per continuare.\n");
                    thread.inChat = false;
                    break;
                } else {
                    ChatAppClientFrame.panelArea.append("\n" + mex);
                }
            }
        } catch (IOException ex) {
            ChatAppClientFrame.panelArea.append("\nDisconnesso dal server\n");
            thread.inChat = false;
            try {
                mySock.close();
            } catch (IOException ex1) { }
        }

    }

}
