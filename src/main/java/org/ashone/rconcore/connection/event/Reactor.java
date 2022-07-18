package org.ashone.rconcore.connection.event;

import org.ashone.rconcore.domain.ReceivePacket;
import org.ashone.rconcore.domain.SendAndReceive;

import java.io.IOException;
import java.util.function.Consumer;

public class Reactor {



private     Worker[] workers;
private static Reactor reactor;
int balance=0;

    private Reactor() {

    }

    public static  Reactor build(){
       return build(1);

    }
    public static  Reactor build(int workerNum){

        if (reactor!=null){
            return reactor;
        }

        reactor=new Reactor();
        reactor.workers=new Worker[workerNum];
        for (int i=0;i<workerNum;i++){
            reactor.workers[i]=new Worker();
int temp=i;

                Thread thread=new Thread(()->{
                    try {
                    reactor.workers[temp].eventLoop();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            reactor.workers[temp].setThread(thread);
            thread.start();


        }
        return  reactor;
    }

    public SocketChannelConnection openConnect(String ip, Integer port, String password, Consumer<SendAndReceive> processor) throws IOException {

       SocketChannelConnection socketChannelConnection= workers[balance].openConnect(ip, port, password);

       socketChannelConnection.setProcessPacket(processor);
        socketChannelConnection.getChannelKey().selector().wakeup();

        return socketChannelConnection;
    }

}
