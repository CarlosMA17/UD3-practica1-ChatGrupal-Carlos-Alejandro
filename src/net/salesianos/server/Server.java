package net.salesianos.server;

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
