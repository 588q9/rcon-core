package org.ashone.rconcore.domain;

import org.ashone.rconcore.setting.Setting;
import org.ashone.rconcore.setting.SettingPropertiesName;
import org.ashone.rconcore.type.PacketType;
import org.ashone.rconcore.type.PacketTypeEnum;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class SendPacket extends AbstractPacket{

    public ByteBuffer getByteBuffer() {
        return super.getByteBuffer();
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {

        super.setByteBuffer(byteBuffer);

    }

    public Integer getId() {
        return super.getId();
    }

    public void setId(int id) {


        super.setId(id);

        this.getByteBuffer().putInt(Packet.SIZE_BYTES,this.getId());


    }

    public String getPayload() {
        return super.getPayload();
    }


    public int getRealLength() {
        return super.getRealLength();
    }

    public void setRealLength(int realLength) {

        super.setRealLength(realLength);
    }

    public int getRconLength() {
        return super.getRconLength();
    }

    public void setRconLength(int rconLength) {

        super.setRconLength(rconLength);
        this.getByteBuffer().putInt(0,this.getRconLength());

super.setRealLength(this.getRconLength()+Packet.SIZE_BYTES);
    }

    public int getType() {


        return super.getType();
    }

    public void setType(int type) {

        super.setType(type);
    }

    public PacketTypeEnum getTypeEnum() {
        return
        super.getTypeEnum();
    }

    public void setTypeEnum(PacketTypeEnum typeEnum) {

        super.setTypeEnum(typeEnum);
        this.getByteBuffer().putInt(Packet.SIZE_BYTES+Packet.ID_BYTES,this.getTypeEnum().getTypeId());

    }

    public byte[] getDataGram() {
        return
        super.getDataGram();
    }

    public void setDataGram(byte[] dataGram) {

        super.setDataGram(dataGram);
    }











    public SendPacket() {
    }

    public SendPacket(int id,String payload, PacketTypeEnum packetTypeEnum){

        this.setId(id);
        this.build(payload, packetTypeEnum);

    }

    public SendPacket(String payload, PacketTypeEnum packetTypeEnum) {

this.build(payload, packetTypeEnum);
    }

    public void setPayload(String payload) {
        if (this.getPayload()==null||payload.length()==this.getPayload().length()){
            super.setPayload(payload);
            this.getByteBuffer().put(Packet.SIZE_BYTES+Packet.TYPE_BYTES+Packet.ID_BYTES,this.getPayload().getBytes(StandardCharsets.US_ASCII));
            this.setRconLength(Packet.BASE_BYTES+payload.length());


        }else {
            super.setPayload(null);
this.build(payload,this.getTypeEnum());

        }




    }

    public SendPacket build(String payload, PacketTypeEnum packetTypeEnum) {
        ByteBuffer buffer=this.byteContainer(payload.length());

        this.setByteBuffer(buffer);

        if (super.getId()==null){
            this.setId(this.idGenerate());
        }else {
            this.setId(this.getId());
        }

        this.setPayload(payload);

        this.setTypeEnum(packetTypeEnum);
        this.setDataGram(buffer.array());

        return this;
    }












//
//@Deprecated
//
//    public byte[] constructBytes() throws IOException {
//
//
//
//        ByteBuffer buffer=ByteBuffer.allocate(packetSize).
//                order(ByteOrder.LITTLE_ENDIAN).putInt(packetSize-4)
//                .putInt(12).putInt(PacketType.SERVERDATA_AUTH).
//                put(password)
//                ;
//
//
//
//
//        return buffer.array();
//
//    }
//    @Deprecated
//
//    public  byte[] ConstructBytesByManualOp(){
//
//
//        ByteBuffer byteBuffer=ByteBuffer.allocate(packetSize);
//        byteBuffer.put((byte) (password.length+10));
//        byteBuffer.put(4,(byte) 12);
//        byteBuffer.put(8,(byte) PacketType.SERVERDATA_AUTH);
//
//        byteBuffer.put(12,password);
//
//        return byteBuffer.array();
//
//
//    }


}
