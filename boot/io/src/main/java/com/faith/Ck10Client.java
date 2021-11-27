package com.faith;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class Ck10Client {

    public static void main(String[] args) throws IOException {
        LinkedList<SocketChannel> clients = new LinkedList<>();
        InetSocketAddress address = new InetSocketAddress("192.168.228.134", 9090);

        for (int i = 10000; i <= 50000; i++) {
            SocketChannel client2 = SocketChannel.open();
            client2.bind(new InetSocketAddress("192.168.228.1", i));
            client2.connect(address);
            clients.add(client2);

            SocketChannel client1 = SocketChannel.open();
            client1.bind(new InetSocketAddress("192.168.8.236", i));
            client1.connect(address);
            clients.add(client1);

        }
    }
}
