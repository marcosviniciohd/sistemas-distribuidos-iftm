package br.edu.iftm.marcos.vinicio.client;

import br.edu.iftm.marcos.vinicio.dto.TransactionDTO;
import br.edu.iftm.marcos.vinicio.model.transaction.Transaction;
import br.edu.iftm.marcos.vinicio.model.transaction.TransactionResponse;
import br.edu.iftm.marcos.vinicio.util.PaymentType;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Card number: ");
                int cardNumber = scanner.nextInt();
                System.out.print("1 - Credit\n2 - Debit\nPayment type: ");
                int paymentType = scanner.nextInt();
                System.out.print("Value R$: ");
                double value = scanner.nextDouble();
                Transaction transaction = new Transaction(value, PaymentType.values()[paymentType - 1]);

                TransactionResponse response = sendTransaction(cardNumber, transaction);
                switch (response.getResponseCode()) {
                    case 0 -> System.out.println("Transaction successful");
                    case 5 -> System.out.println("Card not found");
                    case 51 -> System.out.println("Insufficient funds");
                    default -> System.out.println("Unknown error");
                }
            } catch (IOException e) {
                System.out.println("Server not found");
            } catch (Exception e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            } finally {
                System.out.println();
            }
        }
    }

    private static TransactionResponse sendTransaction(int cardNumber, Transaction transaction) throws Exception {
        try (Socket socket = new Socket("localhost", 3333)) {
            socket.getOutputStream().write(TransactionDTO.build(cardNumber, transaction));
            return new TransactionResponse(socket.getInputStream());
        }
    }
}
