package org.ashone.rconcore.domain;

import org.ashone.rconcore.type.PacketTypeEnum;

import java.nio.ByteBuffer;
//sendpacket和receivepacket构造重复的逻辑有很多，既然格式都相同那么可以用一个类表示
public class SimpleRconPacket implements Packet{


    private ByteBuffer byteBuffer;


    public Integer getId() {
        return null;
    }

    public void setId(int id) {
    }

    public String getPayload() {
        return null;
    }

    public void setPayload(String payload) {
    }

    public int getRealLength() {
        return 0;
    }


    public void setRealLength(int realLength) {

    }


    public int getRconLength() {
        return 0;
    }


    public void setRconLength(int rconLength) {

    }


    public PacketTypeEnum getTypeEnum() {
        return null;
    }


    public void setTypeEnum(PacketTypeEnum typeEnum) {

    }


    public ByteBuffer getByteBuffer() {
       return null;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {

    }
}
