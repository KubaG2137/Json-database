package server;

import java.io.IOException;



public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        new ClientHandler().run();
        Database.storeDatabase();
    }
}

