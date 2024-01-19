package net.salesianos.client;
<<<<<<< HEAD

import java.io.DataOutputStream;
=======
import net.salesianos.models.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
>>>>>>> 1c4f790 (fix: se ha conseguido que el cliente reciba y pinte los mensajes del servidor y se ha mejorado el codigo)
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

<<<<<<< HEAD
public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 50000);

        DataOutputStream dataOutStream = new DataOutputStream(socket.getOutputStream());

        int userOption = 0;

        while (userOption != -1) {

            Scanner sc = new Scanner(System.in);
            System.out.println("indica su nombre");
            String userName = sc.nextLine();

            if (userName != null && userName != "") {

                dataOutStream.writeUTF(userName);
                System.out.println("binevenido al servidor");

                try {
                    System.out.println("pulse -1 para salir o cualquier tecla para continuar");
                    userOption = sc.nextInt();
                    sc.nextLine();

                } catch (InputMismatchException e) {
                    System.out.println("Continuamos...");
                }


                sc.close();
                socket.close();
=======


public class Client {
    public static final Object lock = new Object();

    // method to filter if is a message (contains msg) or not
    private static String msgFilter(String input) {

        if (input.length() >= 3) {

            return input.substring(0, 3);
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
        objOutStream.writeUTF(username);

        ObjectInputStream objInStream = new ObjectInputStream(socket.getInputStream());
        ServerListener serverListener = new ServerListener(objInStream);
        serverListener.start();



        while (!userInput.equals("0")) {

            synchronized (lock) {
                System.out.print("Escribe un mensaje o 0 para salir: ");
                userInput = SCANNER.nextLine();

                if (msgFilter(userInput).equals("msg")) {
                    Message message = new Message(username, userInput);
                    objOutStream.writeObject(message);
                    lock.wait();
                } else {
                    System.out.println("El prefijo del mensaje es incorrecto, asegúrese de escribir 'msg:' al comienzo del mensaje o 0 para salir");
                }
>>>>>>> 1c4f790 (fix: se ha conseguido que el cliente reciba y pinte los mensajes del servidor y se ha mejorado el codigo)
            }

        }

<<<<<<< HEAD
    }
}
=======
        SCANNER.close();

        objInStream.close();
        objOutStream.close();
        socket.close();
    }
}
>>>>>>> 1c4f790 (fix: se ha conseguido que el cliente reciba y pinte los mensajes del servidor y se ha mejorado el codigo)
