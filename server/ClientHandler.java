package server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientHandler {
    private ServerSocket server;

    private static final String address = "127.0.0.1";

    private static final int port = 2137;


    public void run() throws IOException {
        startServer();
        acceptClientsMultiThreaded();
        server.close();

    }

    private void startServer() throws IOException {
        server = new ServerSocket(port, 50, InetAddress.getByName(address));
    }

    private void acceptClientsMultiThreaded() throws IOException {
        boolean exitRequested = false;
        var executor = Executors.newFixedThreadPool(10);
        try {
            while (!exitRequested) {
                exitRequested = acceptOneClient(executor);
            }
        } finally {
            shutDownExecutorAndWait(executor);
        }
    }

    private void shutDownExecutorAndWait(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean acceptOneClient(ExecutorService executor) throws IOException {
        Socket client = server.accept();
        String input = new DataInputStream(client.getInputStream()).readUTF();

        executor.submit(() -> {
            try {
                answerClientRequest(input, client);
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Parameters parameters = GsonFromJson.gsonFromJson(input);
        String command = parameters.getType();
        return command.equals("exit");
    }

    private void answerClientRequest(String input, Socket client) throws IOException {
        try (DataOutputStream output = new DataOutputStream(client.getOutputStream())
        ) {
            Parameters parameters = GsonFromJson.gsonFromJson(input);
            String command = parameters.getType();
            String key = parameters.getKey();
            String text = parameters.getValue();
            Response response = new Response();
            switch (command) {
                case "get" -> Database.get(key, response);
                case "set" -> Database.set(key, text, response);
                case "delete" -> Database.delete(key, response);
                case "exit" -> response.setResponse("OK");
                default -> System.out.println("Command invalid");
            }
            output.writeUTF(GsonToJson.gsonToJson(response.getResponse(), response.getValue(), response.getReason()));
        }
    }
}