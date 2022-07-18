package org.ashone.rconcore.connection.event;

import org.ashone.rconcore.connection.AbstractConnection;
import org.ashone.rconcore.domain.Packet;
import org.ashone.rconcore.domain.ReceivePacket;
import org.ashone.rconcore.domain.SendAndReceive;
import org.ashone.rconcore.domain.SendPacket;
import org.ashone.rconcore.exception.ReadException;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class SocketChannelConnection extends AbstractConnection{

private SocketChannel socketChannel ;

private SendAndReceive sendAndReceive;
SelectionKey channelKey;
Queue<String> order=new ConcurrentLinkedDeque<>();

     SelectionKey getChannelKey() {
        return channelKey;
    }

     void setChannelKey(SelectionKey channelKey) {
        this.channelKey = channelKey;
    }

    private Consumer<SendAndReceive> processPacket;
     Consumer<SendAndReceive> getProcessPacket() {
        return processPacket;
    }

     void setProcessPacket(Consumer<SendAndReceive> processPacket) {
        this.processPacket = processPacket;
    }

    public SendAndReceive getSendAndReceive() {
        return sendAndReceive;
    }

     void setSendAndReceive(SendAndReceive sendAndReceive) {
        this.sendAndReceive = sendAndReceive;
    }

     SocketChannel getSocketChannel() {
        return socketChannel;
    }

     void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }


     SocketChannelConnection(String ip, Integer port, String password) throws IOException {

        super(ip, port, password);
    }
    @Override
    protected void establish(SendPacket authPacket, String ip, Integer port) throws IOException {
        socketChannel=SocketChannel.open(new InetSocketAddress(ip,port));

socketChannel.write(authPacket.getByteBuffer());
authPacket.getByteBuffer().clear();
ReceivePacket receivePacket=this.buildDataList();
        SendAndReceive sendAndReceive=new SendAndReceive(authPacket,receivePacket);

        if (!this.isAuthTheConnection(sendAndReceive)) {
            throw new AuthenticationException("cannot authentication please check password");

        }

    }


    @Override
    protected ReceivePacket buildDataList() throws IOException {
        ReceivePacket receivePacket=new ReceivePacket();
        int readLength;
        byte tempData[]=new byte[MAX_PACK_SIZE];// 测试1024，实用时是4096
        ByteBuffer wrap=ReceivePacket.wrapData(tempData,MAX_PACK_SIZE);

        readLength=socketChannel.read(wrap);
        if (readLength==-1){
            throw new ReadException("readLength:"+readLength);
        }
        receivePacket.getDataList().add(Arrays.copyOf(tempData,readLength));


        receivePacket.constructSuffix(wrap.array(),readLength);
wrap.clear();

        int readLengthTotal;
        for(readLengthTotal=readLength- Packet.SIZE_BYTES; readLengthTotal<receivePacket.getRconLength(); readLengthTotal=readLengthTotal+readLength){
            readLength=socketChannel.read(wrap);
            wrap.clear();
            receivePacket.getDataList().add(Arrays.copyOf(tempData,readLength));
        }
        receivePacket.buildPayload();
        return receivePacket;
    }



    @Override
    public SendAndReceive SendPacketToServer(SendPacket sendPacket) {
         SendAndReceive sendAndReceive=new SendAndReceive(sendPacket,null);
         setSendAndReceive(sendAndReceive);

getChannelKey().interestOps(SelectionKey.OP_WRITE);
        return sendAndReceive;
    }





    @Override
    public void close()  {
        try {
            if (this.socketChannel!=null)

                this.socketChannel.close();
            this.channelKey.cancel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
