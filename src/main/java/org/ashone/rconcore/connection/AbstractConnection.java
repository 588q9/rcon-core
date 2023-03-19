package org.ashone.rconcore.connection;

import org.ashone.rconcore.domain.ReceivePacket;
import org.ashone.rconcore.domain.SendAndReceive;
import org.ashone.rconcore.domain.SendPacket;
import org.ashone.rconcore.type.PacketTypeEnum;

import java.io.IOException;

public abstract class AbstractConnection implements Connection {

    public AbstractConnection() {
    }

    public AbstractConnection(String ip, Integer port, String password) throws IOException {


        establish(new SendPacket(password, PacketTypeEnum.SERVERDATA_AUTH), ip, port);


    }

    protected abstract void establish(SendPacket authPacket, String ip, Integer port) throws IOException;

    protected abstract ReceivePacket buildDataList() throws IOException;

    public boolean idEqual(SendPacket sendPacket, ReceivePacket receivePacket) {
        return sendPacket.getId().equals(receivePacket.getId());

    }

    protected boolean isAuthTheConnection(SendAndReceive sendAndReceive) {

        return idEqual(sendAndReceive.getSend(), sendAndReceive.getReceive());
//TODO auth不一定是这样


    }

}
