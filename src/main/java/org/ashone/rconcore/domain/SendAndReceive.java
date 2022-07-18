package org.ashone.rconcore.domain;

public class SendAndReceive {

    private SendPacket send;
    private ReceivePacket receive;

    public SendAndReceive(SendPacket send, ReceivePacket receive) {
        this.send = send;
        this.receive = receive;
    }

    public SendPacket getSend() {
        return send;
    }

    public void setSend(SendPacket send) {
        this.send = send;
    }

    public ReceivePacket getReceive() {
        return receive;
    }

    public void setReceive(ReceivePacket receive) {
        this.receive = receive;
    }
}
