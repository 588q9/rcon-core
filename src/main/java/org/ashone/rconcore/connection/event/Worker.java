package org.ashone.rconcore.connection.event;

import org.ashone.rconcore.connection.Connection;
import org.ashone.rconcore.domain.Packet;
import org.ashone.rconcore.domain.ReceivePacket;
import org.ashone.rconcore.domain.SendPacket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static org.ashone.rconcore.domain.Packet.SUFFIX_SIZE;

public class Worker {

    private Thread thread;
    private Selector selector;
    private Set<SelectionKey> events;

    Worker() {


        try {
            selector = Selector.open();
            events = selector.selectedKeys();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    Thread getThread() {
        return thread;
    }

    void setThread(Thread thread) {
        this.thread = thread;
    }

    public SocketChannelConnection openConnect(String ip, Integer port, String password) throws IOException {

        SocketChannelConnection socketChannelConnection = new SocketChannelConnection(ip, port, password);
        SocketChannel socketChannel = socketChannelConnection.getSocketChannel();
        socketChannel.configureBlocking(false);
        socketChannelConnection.setChannelKey(socketChannel.register(selector, 0, socketChannelConnection));
        return socketChannelConnection;

    }

    void eventLoop() throws IOException {


        byte tempData[] = new byte[Connection.MAX_PACK_SIZE];// 测试1024，实用时是4096
        ByteBuffer tempBuffer = ReceivePacket.wrapData(tempData, Connection.MAX_PACK_SIZE);
        int currentLength = 0;
        ReceivePacket.wrapData(tempData, Connection.MAX_PACK_SIZE);

        while (true) {


            selector.select();

            Iterator<SelectionKey> keyIterator = events.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey tempKey = keyIterator.next();

                keyIterator.remove();
                SocketChannel socketChannel = (SocketChannel) tempKey.channel();
                SocketChannelConnection tempConnection = (SocketChannelConnection) tempKey.attachment();
                if (tempKey.isReadable()) {

                    ReceivePacket receivePacket = tempConnection.getSendAndReceive().getReceive();

                    if (receivePacket == null) {
                        currentLength = socketChannel.read(tempBuffer);
                        if (currentLength == -1) {
                            this.clearSelection(tempKey);
                            continue;
                        }
                        ReceivePacket newReceive = new ReceivePacket();
                        newReceive.constructSuffix(tempBuffer.array(), currentLength);
                        int realLength = newReceive.getRealLength();
                        newReceive.setByteBuffer(ReceivePacket.wrapData(new byte[realLength], realLength));
                        tempBuffer.flip();
                        newReceive.getByteBuffer().put(tempBuffer);
                        tempBuffer.clear();


                        tempConnection.getSendAndReceive().setReceive(newReceive);
                        receivePacket = newReceive;
                    } else if (receivePacket.getRealLength() > receivePacket.getByteBuffer().position()) {

                        currentLength = socketChannel.read(receivePacket.getByteBuffer());
                    }


                    if (receivePacket.getRealLength() <= receivePacket.getByteBuffer().position()) {
                        ByteBuffer receiveBuffer = receivePacket.getByteBuffer();

                        receiveBuffer.flip();
                        receivePacket.setPayload(new String(receiveBuffer.array()
                                , Packet.SUFFIX_SIZE, receiveBuffer.array().length - SUFFIX_SIZE));
                        receivePacket.setDataGram(receiveBuffer.array());
                        tempConnection.getProcessPacket().accept(tempConnection.getSendAndReceive());
                        tempKey.interestOps(0);
                    }


                } else if (tempKey.isWritable()) {
                    SendPacket sendPacket = tempConnection.getSendAndReceive().getSend();

                    if (sendPacket.getByteBuffer().hasRemaining()) {

                        socketChannel.write(sendPacket.getByteBuffer());

                    } else {
                        sendPacket.getByteBuffer().clear();

                        tempKey.interestOps(SelectionKey.OP_READ);
                    }
                }

                if (currentLength == -1) {
                    this.clearSelection(tempKey);

                }


            }


        }


    }


    private void clearSelection(SelectionKey tempKey) throws IOException {

        tempKey.channel().close();

        tempKey.cancel();


    }
}