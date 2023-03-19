package packTest;


import org.ashone.rconcore.connection.blocking.TCPBlockConnection;
import org.ashone.rconcore.domain.SendAndReceive;
import org.ashone.rconcore.domain.SendPacket;
import org.ashone.rconcore.type.PacketTypeEnum;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class testReceive {


    @Test
    public void receive() throws IOException {


//        SendPacket authPacket=new SendPacket(Setting.getProp(SettingPropertiesName.password.key()), PacketTypeEnum.SERVERDATA_AUTH);

        TCPBlockConnection connection = new TCPBlockConnection("ip", 0, "password");

        SendPacket sendPacket = new SendPacket("/op ashone", PacketTypeEnum.SERVERDATA_EXECCOMMAND);
        SendAndReceive sendAndReceive = connection.SendPacketToServer(sendPacket);
        System.out.println(sendAndReceive.getReceive().getPayload());

//        System.out.println(Arrays.toString(sendAndReceive.getReceive().getDataGram()));
//        System.out.println(Arrays.toString(sendAndReceive.getSend().getDataGram()));

        System.out.println(sendAndReceive.getReceive().getTypeEnum());
        connection.close();


    }

    @Test
    public void atomIntTest(){

        AtomicInteger atomicInteger=new AtomicInteger(Integer.MAX_VALUE);
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.incrementAndGet());




    }


}
