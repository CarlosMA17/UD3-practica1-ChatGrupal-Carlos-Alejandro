package net.salesianos.client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import net.salesianos.models.Message;

public class ServerListener extends Thread {

    private ObjectInputStream objInStream;
    private String username;

    public ServerListener(ObjectInputStream socketObjectInputStream, String username) {
        this.objInStream = socketObjectInputStream;
        this.username = username;
    }

    @Override
    public void run() {

        try {
            System.out.println(objInStream.readUTF());

            while (true) {
                Message newMessage = (Message) this.objInStream.readObject();
                
                if(newMessage.getSender().equals(username)) {
                    System.out.println("el mensaje que enviaste (" + newMessage.getContent() + ") se envio correctamente");
                } else {
                    System.out.println("New message received from " + newMessage.getSender() + ": " + newMessage.getContent());
                }
                

                
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("cerrando conexion con el servidor...");
            e.printStackTrace();
        }
    }

}