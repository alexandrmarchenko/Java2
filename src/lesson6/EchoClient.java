package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    private static final String SERVER_ADDR = "localhost";

    public static void main(String[] args) {
        initServerConnection();
    }

    private static void initServerConnection() {
        try (Socket socket = new Socket(SERVER_ADDR, EchoServer.SERVER_PORT)){
            System.out.println("Client started");
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                while(true) {
                    try {
                        String message = inputStream.readUTF();
                        System.out.println("From server: " + message);
                    } catch (IOException e) {
                        System.out.println("Connection was closed");
                        break;
                    }
                }
            }).start();

            writeMessages(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
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
