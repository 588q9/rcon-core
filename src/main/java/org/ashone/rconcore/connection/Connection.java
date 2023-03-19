package org.ashone.rconcore.connection;

import org.ashone.rconcore.domain.SendAndReceive;
import org.ashone.rconcore.domain.SendPacket;

import java.io.IOException;

public interface Connection {

    int MAX_PACK_SIZE = 4096;

    SendAndReceive SendPacketToServer(SendPacket sendPacket);

    public void close() throws IOException;
}
