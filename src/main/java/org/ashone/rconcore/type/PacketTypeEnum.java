package org.ashone.rconcore.type;

public enum PacketTypeEnum {
     SERVERDATA_RESPONSE_VALUE(0),
     SERVERDATA_AUTH(3),
     SERVERDATA_EXECCOMMAND(2),
    SERVERDATA_AUTH_RESPONSE(2);



    private final int typeId;

    public int getTypeId() {
        return typeId;
    }

    PacketTypeEnum(int typeId) {
        this.typeId = typeId;
    }
}
