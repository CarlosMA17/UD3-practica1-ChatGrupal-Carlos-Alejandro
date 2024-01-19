package net.salesianos.server;

<<<<<<< HEAD
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ClientHandler {
=======
import net.salesianos.models.Message;


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ClientHandler extends Thread  {
>>>>>>> 1c4f790 (fix: se ha conseguido que el cliente reciba y pinte los mensajes del servidor y se ha mejorado el codigo)
    private ObjectInputStream clientObjInStream;
    private ObjectOutputStream clientObjOutStream;
    private ArrayList<ObjectOutputStream> connectedObjOutputStreamList;

    public ClientHandler(ObjectInputStream clientObjInStream, ObjectOutputStream clientObjOutStream,
                         ArrayList<ObjectOutputStream> connectedObjOutputStreamList) {
        this.clientObjInStream = clientObjInStream;
        this.clientObjOutStream = clientObjOutStream;
        this.connectedObjOutputStreamList = connectedObjOutputStreamList;
    }
<<<<<<< HEAD
=======

    @Override
    public void run() {
        String username = "";
        try {

            username = this.clientObjInStream.readUTF();
            String welcomeMessage = "\nBienvenido " + username;
            this.clientObjOutStream.writeUTF(welcomeMessage);
            System.out.println(username + " se ha unido, " + welcomeMessage);

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
>>>>>>> 1c4f790 (fix: se ha conseguido que el cliente reciba y pinte los mensajes del servidor y se ha mejorado el codigo)
}
