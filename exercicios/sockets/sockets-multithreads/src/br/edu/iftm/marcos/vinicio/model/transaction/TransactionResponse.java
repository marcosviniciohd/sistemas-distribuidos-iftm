package br.edu.iftm.marcos.vinicio.model.transaction;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class TransactionResponse {
    private final int type;
    private final int nsu;
    private final int responseCode;

    public TransactionResponse(InputStream stream) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(stream.readNBytes(6));
        this.type = Byte.toUnsignedInt(buffer.get());
        this.nsu = buffer.getInt();
        this.responseCode = Byte.toUnsignedInt(buffer.get());
    }

    public static byte[] build(int nsu, int responseCode) {
        return ByteBuffer.allocate(6)
                .put((byte) 210)            // 1 byte  - type
                .putInt(nsu)                // 4 bytes - NSU
                .put((byte) responseCode)   // 1 byte  - response code
                .array();
    }

    public int getType() {
        return type;
    }

    public int getNsu() {
        return nsu;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
