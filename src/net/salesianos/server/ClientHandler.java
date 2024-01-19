package net.salesianos.server;

import net.salesianos.models.Message;


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClientHandler extends Thread  {
    private ObjectInputStream clientObjInStream;
    private ObjectOutputStream clientObjOutStream;
    private ArrayList<ObjectOutputStream> connectedObjOutputStreamList;
    private ArrayList<Message> historyMessage;

    public ClientHandler(ObjectInputStream clientObjInStream, ObjectOutputStream clientObjOutStream,
                         ArrayList<ObjectOutputStream> connectedObjOutputStreamList, ArrayList<Message> historyMessage) {
        this.clientObjInStream = clientObjInStream;
        this.clientObjOutStream = clientObjOutStream;
        this.connectedObjOutputStreamList = connectedObjOutputStreamList;
        this.historyMessage = historyMessage;
    }

    @Override
    public void run() {
        String username = "";

        try {
            username = this.clientObjInStream.readUTF();
            String welcomeMessage = "\nBienvenido " + username;

            this.clientObjOutStream.writeUTF(welcomeMessage);
            System.out.println(username + " se ha unido, " + welcomeMessage);
            this.clientObjOutStream.writeObject(Server.getHistoryMessage());


            while (true) {
                Message messageReceived = (Message) this.clientObjInStream.readObject();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formatHour = messageReceived.getMessageTime().format(formatter);
                String printMessage = "[" + formatHour + "] <" + messageReceived.getSender() + "> " + messageReceived.getContent();

                System.out.println(printMessage);

                this.clientObjOutStream.writeObject(messageReceived);

                for (ObjectOutputStream otherObjOutputStream : connectedObjOutputStreamList) {
                    if (otherObjOutputStream != this.clientObjOutStream) {
                        otherObjOutputStream.writeObject(messageReceived);
                    }
                }
            }
        } catch (EOFException eofException) {
            this.connectedObjOutputStreamList.remove(this.clientObjOutStream);
            System.out.println("CERRANDO CONEXIÓN CON " + username.toUpperCase());
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
