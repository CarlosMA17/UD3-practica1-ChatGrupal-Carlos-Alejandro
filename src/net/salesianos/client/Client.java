package net.salesianos.client;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

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
            }

        }

    }
}
