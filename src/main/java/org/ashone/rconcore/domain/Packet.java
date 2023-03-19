package org.ashone.rconcore.domain;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public interface Packet {


    int SIZE_BYTES = 4;

    int ID_BYTES = 4;
    int TYPE_BYTES = 4;

    int BYTE = 1;
    int BASE_BYTES = 10;
    byte EMPTY_BYTE = 0;
    int SUFFIX_SIZE = SIZE_BYTES + ID_BYTES + TYPE_BYTES;

    default int idGenerate() {

        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);


    }

    default ByteBuffer byteContainer(int payloadBytes) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(payloadBytes + BASE_BYTES + SIZE_BYTES)
                .order(ByteOrder.LITTLE_ENDIAN);


        return byteBuffer;

    }


}
