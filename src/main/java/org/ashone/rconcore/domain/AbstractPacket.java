package org.ashone.rconcore.domain;

import org.ashone.rconcore.type.PacketTypeEnum;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public abstract class AbstractPacket implements Packet{

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public int getRealLength() {
        return realLength;
    }

    public void setRealLength(int realLength) {
        this.realLength = realLength;
    }

    public int getRconLength() {
        return rconLength;
    }

    public void setRconLength(int rconLength) {
        this.rconLength = rconLength;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PacketTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(PacketTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public byte[] getDataGram() {
        return dataGram;
    }

    public void setDataGram(byte[] dataGram) {
        this.dataGram = dataGram;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    private Integer id=null;
    private String payload;
    private int realLength;
    private int rconLength;
    private int type;
    private PacketTypeEnum typeEnum;
    private byte[] dataGram;
private     ByteBuffer byteBuffer;

}
