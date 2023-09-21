package br.edu.iftm.marcos.vinicio.server;

import br.edu.iftm.marcos.vinicio.dto.TransactionDTO;
import br.edu.iftm.marcos.vinicio.model.card.Card;
import br.edu.iftm.marcos.vinicio.model.transaction.Transaction;
import br.edu.iftm.marcos.vinicio.model.transaction.TransactionResponse;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ServerClient {
    private final HashMap<Integer, Card> cards;
    private final Socket socket;

    public ServerClient(Socket socket, HashMap<Integer, Card> cards) {
        this.socket = socket;
        this.cards = cards;
    }

    private synchronized Transaction transact(TransactionDTO transactionDTO) {
        Card card = cards.get(transactionDTO.getCardNumber());
        Transaction transaction = transactionDTO.getTransaction();

        if (card == null) {
            transaction.setResponseCode(5);
            return transaction;
        }

        card.transact(transaction);
        transaction.setNsu(Server.nsu++);
        return transaction;
    }

    private Transaction receiveTransaction() throws Exception {
        TransactionDTO transactionDto = new TransactionDTO(socket.getInputStream());
        return transact(transactionDto);
    }

    private void sendResponse(Transaction transaction) throws IOException {
        byte[] buffer = TransactionResponse.build(transaction.getNsu(), transaction.getResponseCode());
        socket.getOutputStream().write(buffer);
    }

    @Override
    public void run() {
        try {
            Transaction transaction = receiveTransaction();
            sendResponse(transaction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
