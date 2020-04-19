package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    static final int SERVER_PORT = 8189;

    public static void main(String[] args) throws IOException {
        Socket clientSocket = null;
        System.out.println("Start echo-server");
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Server is waiting for connections...");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            new Thread(()->{
                while(true) {
                    try {
                        String message = inputStream.readUTF();
                        System.out.println("From client: " + message);
                        if (message.equals("/end")) {
                           System.exit(0);
                        }
                    } catch (IOException e) {
                        System.out.println("Client disconnected");
                        break;
                    }
                }
            }).start();

            writeMessages(outputStream);


        } catch (IOException e) {
            System.out.println("Port " + SERVER_PORT + " is already used!");
            e.printStackTrace();
        }
        finally {
            if (clientSocket != null) {
                clientSocket.close();
            }
        }
    }

    static private void writeMessages(DataOutputStream outputStream) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            outputStream.writeUTF(message);
            if (message.equals("/end")) {
                break;
            }
        }
    }
}
