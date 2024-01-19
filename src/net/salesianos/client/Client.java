package net.salesianos.client;
import net.salesianos.models.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Client {
    public static final Object lock = new Object();

    private static String msgFilter(String input) {

        if (input.length() >= 4) {
            return input.substring(0, 4);
        } else {
            return input;
        }
    }

    public static void main(String[] args) throws Exception {

        String userInput = "";
        final Scanner SCANNER = new Scanner(System.in);

        System.out.println("¿Cómo te llamas?");
        String username = SCANNER.nextLine();

        Socket socket = new Socket("localhost", 50000);
        ObjectOutputStream objOutStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objInStream = new ObjectInputStream(socket.getInputStream());

        objOutStream.writeUTF(username);
        ServerListener serverListener = new ServerListener(objInStream, username);
        serverListener.start();

        while (!userInput.equals("bye")) {

            synchronized (lock) {
                System.out.print("Escribe un mensaje o 'bye' para salir: ");
                userInput = SCANNER.nextLine();

                if (msgFilter(userInput).equals("msg:")) {

                    Message message = new Message(username, userInput);
                    objOutStream.writeObject(message);
                    lock.wait();

                } else if(!msgFilter(userInput).equals("bye")) {
                    System.out.println("El prefijo del mensaje es incorrecto, asegúrese de escribir 'msg:' al comienzo del mensaje o 0 para salir");
                }
            }
        }
        SCANNER.close();
        objInStream.close();
        objOutStream.close();
        socket.close();
    }
}