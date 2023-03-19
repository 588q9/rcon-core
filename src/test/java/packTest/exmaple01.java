package packTest;

import org.ashone.rconcore.domain.ReceivePacket;
import org.ashone.rconcore.domain.SendPacket;
import org.ashone.rconcore.setting.Setting;
import org.ashone.rconcore.setting.SettingPropertiesName;
import org.ashone.rconcore.type.PacketTypeEnum;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

//请不要在意这个类
@Deprecated
public class exmaple01 {

    SendPacket sendPacket = new SendPacket();

    public exmaple01() {
    }

    @Test
    public void testMethod02() {


        System.out.println((byte) '\n');
        System.out.println(0x15);
//byte []data=sendPacket.constructBytes();
//        System.out.println(Arrays.toString(data));
//        data=sendPacket.ConstructBytesByManualOp();
//        System.out.println(Arrays.toString(data));

    }

    @Test
    public void testSendPacket() {
        SendPacket sendPacket = new SendPacket(Setting.getProp(SettingPropertiesName.password.toString()), PacketTypeEnum.SERVERDATA_AUTH);
        System.out.println(Arrays.toString(sendPacket.getDataGram()));
        System.out.println(sendPacket.getId());
    }


    @Test
    public void testExampleRead() {


        byte data[] = new byte[4096];
        SendPacket sendPacket = new SendPacket("123", PacketTypeEnum.SERVERDATA_AUTH);

        try {
            Socket socket = new Socket("ip", 123);


            OutputStream os = socket.getOutputStream();


            System.out.println(Arrays.toString(sendPacket.getDataGram()));

            os.write(sendPacket.getDataGram(), 0, sendPacket.getRealLength());

            InputStream inputStream = socket.getInputStream();
            int readLength;

            readLength = inputStream.read(data);


            String temp = new String(data, 0, readLength, StandardCharsets.ISO_8859_1);
            System.out.println(Arrays.toString(data));
            System.out.println(Arrays.toString(temp.getBytes(StandardCharsets.ISO_8859_1)));
            System.out.println();


            SendPacket cmdPacket = new SendPacket("/help", PacketTypeEnum.SERVERDATA_EXECCOMMAND);

            os.write(cmdPacket.getDataGram());

            ReceivePacket receivePacket = new ReceivePacket();


            readLength = inputStream.read(data);

            System.out.println(ReceivePacket.getReceivePacketRconLength(ReceivePacket.wrapData(data, readLength)));

            System.out.println(readLength);


            System.out.println(Arrays.toString(data));
            System.out.println(new String(data, 12, readLength - 12));
            cmdPacket.setPayload("");
            cmdPacket.setId(13);


            cmdPacket.setTypeEnum(PacketTypeEnum.SERVERDATA_RESPONSE_VALUE);
            System.out.println(Arrays.toString(cmdPacket.getDataGram()));
            os.write(cmdPacket.getDataGram());

            readLength = inputStream.read(data);
            System.out.println(Arrays.toString(cmdPacket.getDataGram()));
            System.out.println(readLength);


            System.out.println(Arrays.toString(data));
            System.out.println(new String(data, 12, readLength - 12));


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
