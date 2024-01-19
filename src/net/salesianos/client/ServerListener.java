package net.salesianos.client;
import java.io.IOException;
import java.io.ObjectInputStream;

import net.salesianos.models.Message;

public class ServerListener extends Thread {

    private ObjectInputStream objInStream;

    public ServerListener(ObjectInputStream socketObjectInputStream) {
        this.objInStream = socketObjectInputStream;
    }

    @Override
    public void run() {
        Message newMessage;

        try {
            System.out.println(objInStream.readUTF());

            while (true) {
                newMessage = (Message) this.objInStream.readObject();
                System.out.println("\nNew message received: " + newMessage.getContent());

                synchronized (Client.lock) {
                    Client.lock.notify();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Server stopped receiving messages.");
        }
    }

}