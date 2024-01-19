package net.salesianos.server;

import net.salesianos.models.Message;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final ArrayList<ObjectOutputStream> connectedObjOutputStreamList = new ArrayList<>();
    private static ArrayList<Message> historyMessage =  new ArrayList<>();

    public static void setHistoryMessage(ArrayList<Message> historyMessage) {
        Server.historyMessage = historyMessage;
    }

    public static ArrayList<Message> getHistoryMessage() {
        return historyMessage;
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(50000, 10);

        while (true) {
            System.out.println("Waiting for connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("CONNECTION ESTABLISHED");

            ObjectOutputStream clientObjOutStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream clientObjInStream = new ObjectInputStream(clientSocket.getInputStream());

            connectedObjOutputStreamList.add(clientObjOutStream);

            new ClientHandler(clientObjInStream, clientObjOutStream, connectedObjOutputStreamList, historyMessage).start();
        }
    }
}