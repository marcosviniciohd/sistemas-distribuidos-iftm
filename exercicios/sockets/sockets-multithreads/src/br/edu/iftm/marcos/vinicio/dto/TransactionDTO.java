package br.edu.iftm.marcos.vinicio.dto;

import br.edu.iftm.marcos.vinicio.model.transaction.Transaction;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class TransactionDTO {
    private final int type;
    private final int cardNumber;
    private final Transaction transaction;

    public TransactionDTO(InputStream stream) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(stream.readNBytes(22));
        this.type = Byte.toUnsignedInt(buffer.get());
        this.cardNumber = buffer.getInt();
        this.transaction = new Transaction(buffer);
    }

    public static byte[] build(int cardNumber, Transaction transaction) {
        return ByteBuffer.allocate(22)
                .put((byte) 200)                  // 1 byte  - type
                .putInt(cardNumber)               // 4 bytes - card number
                .put(transaction.toByteArray())   // 17 bytes - transaction
                .array();
    }

    public int getType() {
        return type;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
