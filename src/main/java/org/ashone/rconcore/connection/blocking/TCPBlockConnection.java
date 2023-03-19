package org.ashone.rconcore.connection.blocking;

import org.ashone.rconcore.connection.AbstractConnection;
import org.ashone.rconcore.domain.Packet;
import org.ashone.rconcore.domain.ReceivePacket;
import org.ashone.rconcore.domain.SendAndReceive;
import org.ashone.rconcore.domain.SendPacket;
import org.ashone.rconcore.exception.IdUnIdenticalException;
import org.ashone.rconcore.exception.ReadException;
import org.ashone.rconcore.setting.Setting;
import org.ashone.rconcore.setting.SettingPropertiesName;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class TCPBlockConnection extends AbstractConnection {


    byte receiveData[];
    private Socket socket;
    private OutputStream os;
    private InputStream is;

    public TCPBlockConnection(String ip, Integer port, String password) throws IOException {
        super(ip, port, password);
    }

    public TCPBlockConnection(SendPacket authPacket) throws IOException {
        establish(authPacket, Setting.getProp(SettingPropertiesName.propertiesKey(SettingPropertiesName.server.key(), SettingPropertiesName.ip.key())), Integer.parseInt(Setting.getProp(SettingPropertiesName.propertiesKey(SettingPropertiesName.server.key(), SettingPropertiesName.port.key()))));

    }

    protected void establish(SendPacket authPacket, String ip, Integer port) throws IOException {

        socket = new Socket(ip, port);


        os = socket.getOutputStream();
        is = socket.getInputStream();
        byte[] data = authPacket.getDataGram();
        os.write(data);

        ReceivePacket receivePacket = null;
        receivePacket = this.buildDataList();
        SendAndReceive sendAndReceive = new SendAndReceive(authPacket, receivePacket);

        if (!this.isAuthTheConnection(sendAndReceive)) {
            throw new AuthenticationException("cannot authentication please check password");

        }
    }

    @Override
    public SendAndReceive SendPacketToServer(SendPacket sendPacket) {


        ReceivePacket receivePacket = null;
        try {
            os.write(sendPacket.getDataGram());


            receivePacket = this.buildDataList();

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert receivePacket != null;
        if (!idEqual(sendPacket, receivePacket)) {

            throw new IdUnIdenticalException(sendPacket.getId(), receivePacket.getId());
        }


        return new SendAndReceive(sendPacket, receivePacket);


    }


    protected ReceivePacket buildDataList() throws IOException {
        ReceivePacket receivePacket = new ReceivePacket();
        int readLength;
        byte tempData[] = new byte[MAX_PACK_SIZE];// 测试1024，实用时是4096

        readLength = is.read(tempData);
        if (readLength == -1) {
            throw new ReadException("readLength:" + readLength);
        }
        receivePacket.getDataList().add(Arrays.copyOf(tempData, readLength));


        receivePacket.constructSuffix(tempData, readLength);


        int readLengthTotal;
        for (readLengthTotal = readLength - Packet.SIZE_BYTES; readLengthTotal < receivePacket.getRconLength(); readLengthTotal = readLengthTotal + readLength) {
            readLength = is.read(tempData);
            receivePacket.getDataList().add(Arrays.copyOf(tempData, readLength));
        }

        receivePacket.buildPayload();
        return receivePacket;
    }


    public void close() throws IOException {


        if (socket != null) {
            this.socket.close();
        }


    }


}
