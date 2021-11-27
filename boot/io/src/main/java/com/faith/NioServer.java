package com.faith;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class NioServer {
    public static void main(String[] args) throws IOException {

        List<SocketChannel> clients = new LinkedList<>();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9090));
        ssc.configureBlocking(false); // 接收客户端不阻塞

        while (true) {
            // 接受客户端的连接，在 bio 的时候会阻塞，这时候是 nio ，不阻塞
            SocketChannel client = ssc.accept();

            if (null != client) {
                client.configureBlocking(false); // 不阻塞 r/w
                int port = client.socket().getPort();
                System.out.println("client port: " + port);
                clients.add(client);
            }

            ByteBuffer bb = ByteBuffer.allocateDirect(4096);
            for (SocketChannel socketChannel : clients) {
                int read = socketChannel.read(bb);
                if (read > 0) { // 表示有数据进来，因为是 nio 不会阻塞
                    bb.flip(); // 反转过来才能读
                    byte[] bytes = new byte[bb.limit()];
                    bb.get(bytes);
                    String s = new String(bytes);
                    System.out.println(socketChannel.socket().getPort() + " : " + s);
                    bb.clear();
                }
                bb.flip();

            }


        }
    }
}
