package com.chatapp.server;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.net.Socket;

/**
 * 
 * DataContainer
 * 
 * Contiene tutti i dati sincronizzati del server e viene creato da EchoServer. 
 * 
 * @author Vicentini Elia, Gandini Simone
 * @version dev01
 */
public class DataContainer {
    
    // Global variables
    private ArrayList<Socket> socketContainer;
    private ArrayList<String> usersContainer;
    private ArrayList<String> config;
    
    public DataContainer() throws IOException{
        this.socketContainer = new ArrayList<>();
        this.usersContainer = new ArrayList<>();
        this.config = loadConfig();
    }
    
    public synchronized void addUser(Socket socket, String username){
        socketContainer.add(socket);
        usersContainer.add(username);
    }
    
    public synchronized void remUser(Socket socket, String username){
        socketContainer.remove(socket);
        usersContainer.remove(username);
    }
    
    public synchronized String viewUsers(){
        return usersContainer.toString();
    }
    
    public synchronized ArrayList<String> getListUsers() {
        return usersContainer;
    }
    
    public synchronized ArrayList<Socket> getListSocket(){
        return socketContainer;
    }
    
    private ArrayList<String> loadConfig() throws FileNotFoundException, IOException{
        ArrayList<String> conf = new ArrayList<>();
        
        try {
            BufferedReader read = new BufferedReader(new FileReader("config.ini"));
                // Carico i dati nella Array
            String line = read.readLine();
            while (line != null) {
                String[] temp1 = line.split(" = ");
                conf.add(temp1[1]);
                line = read.readLine();
            }
        
            // Controllo che i dati siano soltanto 3
            while (conf.size() > 3) {
            conf.remove(conf.get(conf.size() - 1));
            }
        
            return conf;
        } catch (FileNotFoundException e) {
            System.out.print("File di configurazione non trovato! Creazione in corso ... ");
            conf.add("Default Name Server");
            conf.add("5000");
            conf.add("Default Motd Server");
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("config.ini"));
            writer.write("server name = Default Name Server\n");
            writer.write("port = 5000\n");
            writer.write("motd = Default Motd Server\n");
            writer.flush();
            writer.close();
            System.out.print("Fatto!\n\n");
        }
        
        return conf;
    }
    
    public synchronized String getNameServer(){
        return config.get(0);
    }
    
    public synchronized int getPortServer(){
        return Integer.parseInt(config.get(1));
    }
    
    public synchronized String getMotdServer(){
        return config.get(2);
    }
    
    public void setNameServer(String name) throws IOException{
        config.set(0, name);
        BufferedWriter writer = new BufferedWriter(new FileWriter("config.ini"));
        writer.write("server name = " + name + "\n");
        writer.write("port = " + getPortServer() + "\n");
        writer.write("motd = " + getMotdServer() + "\n");
        writer.flush();
        writer.close();
    }
    
    public void setPortServer(String port) throws IOException{
        config.set(1, port);
        BufferedWriter writer = new BufferedWriter(new FileWriter("config.ini"));
        writer.write("server name = " + getNameServer() + "\n");
        writer.write("port = " + port + "\n");
        writer.write("motd = " + getMotdServer() + "\n");
        writer.flush();
        writer.close();
    }
    
    public void setMotdServer(String motd) throws IOException{
        config.set(2, motd);
        BufferedWriter writer = new BufferedWriter(new FileWriter("config.ini"));
        writer.write("server name = " + getNameServer() + "\n");
        writer.write("port = " + getPortServer() + "\n");
        writer.write("motd = " + motd + "\n");
        writer.flush();
        writer.close();
    }
    
}
