package br.edu.iftm.marcos.vinicio.server;

import br.edu.iftm.marcos.vinicio.model.card.Card;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private static final HashMap<Integer, Card> cards = new HashMap<>();
    public static int nsu = 1;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3333);

        // Creating cards
        cards.put(123456789, new Card(123456789, "Antonio", 1000.0));
        cards.put(111111111, new Card(111111111, "Maria Oliveira", 500));
        cards.put(222222222, new Card(222222222, "José Roberto", 2000));

        while (true) {
            Socket connection = server.accept();
            ServerClient serverClient = new ServerClient(connection, cards);
            Thread thread = new Thread((Runnable) serverClient);
            thread.start();
        }
    }
}
