package org.ashone.rconcore.exception;

public class IdUnIdenticalException extends RuntimeException{
    public IdUnIdenticalException(int sendId,int receiveId) {

        super("sendPacket id:"+sendId+" and receivePacket id:"+receiveId+" ,they id no equal");
    }
}
