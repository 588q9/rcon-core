package org.ashone.rconcore.domain;

import org.ashone.rconcore.type.PacketTypeEnum;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class ReceivePacket extends AbstractPacket {

    private ArrayList<byte[]> dataList;


    public ReceivePacket(byte[] data) {

        dataList = new ArrayList<>();

        dataList.add(data);


    }

    public ReceivePacket() {
        this.dataList = new ArrayList<>();


    }


    public ReceivePacket(ArrayList<byte[]> dataList) {
        this.dataList = dataList;
    }

    public static PacketTypeEnum getType(ByteBuffer byteBuffer) {
        int type = byteBuffer.getInt(Packet.SIZE_BYTES + Packet.ID_BYTES);
        for (PacketTypeEnum packetTypeEnum : PacketTypeEnum.values()
        ) {
            if (type == packetTypeEnum.getTypeId()) {
                return packetTypeEnum;
            }
        }
        throw new RuntimeException("type not find");

    }

    public static ByteBuffer wrapData(byte[] data, int length) {

        ByteBuffer Bytes = ByteBuffer.wrap(data, 0, length);
        Bytes.order(ByteOrder.LITTLE_ENDIAN);
        return Bytes;
    }

    public static int getReceivePacketId(ByteBuffer suffixData) {

        return suffixData.getInt(Packet.SIZE_BYTES);
    }

    public static int getReceivePacketRconLength(ByteBuffer suffixData) {


        return suffixData.getInt(0);


    }

    public ReceivePacket constructSuffix(byte[] suffixData, int length) {
        ByteBuffer wrap = wrapData(suffixData, length);

        this.setRconLength(ReceivePacket.getReceivePacketRconLength(wrap));
        this.setId(ReceivePacket.getReceivePacketId(wrap));
        this.setRealLength(this.getRconLength() + Packet.SIZE_BYTES);
        this.setTypeEnum(getType(wrap));
        this.setType(this.getTypeEnum().getTypeId());

        return this;
    }

    public void buildPayload() {
        assert getId() != null;
        assert getRconLength() != 0;

        ByteBuffer byteBuffer = this.byteContainer(this.getRealLength() - Packet.BASE_BYTES - Packet.SIZE_BYTES);


        byte[] data = byteBuffer.array();
        this.setByteBuffer(byteBuffer);
        for (int i = 0; i < dataList.size(); i++) {
            byte[] temp = dataList.get(i);
            byteBuffer.put(temp);
        }


        this.setPayload(new String(data, SUFFIX_SIZE, data.length - SUFFIX_SIZE));
        this.setDataGram(data);


    }

    public ArrayList<byte[]> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<byte[]> dataList) {
        this.dataList = dataList;
    }


}
