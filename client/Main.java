package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class Main {
    private static final String address = "127.0.0.1";
    private static final int port = 2137;
    public static void main(String[] args) {
        String msg = GsonToJson.gsonToJson(args);
        System.out.println("Client started!");
        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    output.writeUTF(msg);
                    System.out.println("Sent: " + msg);
                    String receivedMsg = input.readUTF();
                    System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
