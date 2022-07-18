package packTest;

import org.ashone.rconcore.connection.event.Reactor;
import org.ashone.rconcore.connection.event.SocketChannelConnection;
import org.ashone.rconcore.domain.SendAndReceive;
import org.ashone.rconcore.domain.SendPacket;
import org.ashone.rconcore.type.PacketTypeEnum;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class reactorReceive {

    @Test
    public void asyncTest() throws IOException {

        Reactor reactor=Reactor.build();
SocketChannelConnection socketChannelConnection =        reactor.openConnect("ip",0,"passwd",(SendAndReceive sendAndReceive)->{
            String payload=sendAndReceive.getReceive().getPayload();
            //i think there should use websocket,and remember close relate socketchannel on somewhere callback function
            System.out.println(payload);
        });
SendPacket sendPacket=new SendPacket();
sendPacket.build("/help", PacketTypeEnum.SERVERDATA_EXECCOMMAND);

        for (int i = 0; i <10 ; i++) {
            int temp=i;
            new Thread(()->{
                try {
                    SocketChannelConnection socketChannelConnection1=reactor.openConnect(
                            "ip",25575,"passwd",(SendAndReceive sendAndReceive)->{
                        String payload=sendAndReceive.getReceive().getPayload();
                        //i think there should use websocket
                        System.out.println(payload);
                        System.out.println(temp);
                    });
                    SendPacket sendPacket1=new SendPacket();
                    sendPacket1.build("/help", PacketTypeEnum.SERVERDATA_EXECCOMMAND);

                    socketChannelConnection1.SendPacketToServer(sendPacket1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }).start();
        }

        socketChannelConnection.SendPacketToServer(sendPacket);

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
