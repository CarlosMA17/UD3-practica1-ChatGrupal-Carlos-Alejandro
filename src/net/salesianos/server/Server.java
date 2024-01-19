package net.salesianos.server;

<<<<<<< HEAD
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(50000, 1);

        while (true) {
            System.out.println("Esperando conexión...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("CONEXION ESTABLECIDA");

            DataInputStream dataInStream = new DataInputStream(clientSocket.getInputStream());
            String userName = dataInStream.readUTF();

            System.out.println(userName);
        }
    }
}
=======
import net.salesianos.models.Message;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final ArrayList<ObjectOutputStream> connectedObjOutputStreamList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(50000, 10);

        while (true) {
            System.out.println("Waiting for connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("CONNECTION ESTABLISHED");

            ObjectOutputStream clientObjOutStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream clientObjInStream = new ObjectInputStream(clientSocket.getInputStream());

            connectedObjOutputStreamList.add(clientObjOutStream);

            // Create a new thread to handle the client
            new ClientHandler(clientObjInStream, clientObjOutStream, connectedObjOutputStreamList).start();
        }
    }
}
>>>>>>> 1c4f790 (fix: se ha conseguido que el cliente reciba y pinte los mensajes del servidor y se ha mejorado el codigo)
