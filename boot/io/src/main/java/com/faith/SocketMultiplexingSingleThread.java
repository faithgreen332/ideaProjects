package com.faith;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class SocketMultiplexingSingleThread {

    private ServerSocketChannel server = null;
    private Selector selector;

    private void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(9090));
            server.configureBlocking(false);

            // 1.内核调用 epoll_create=>fd6，创建一个链接，返回连接的文件描述符
            // 2.select poll epoll 优先选择 epoll，可以用 -D 参数修正,相当于 bio 的 accept，
            selector = Selector.open();

            // 注册 accept 事件，内核调用 epoll_ctl(fd3,add,fd6
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() {
        initServer();
        System.out.println("server started ....");

        try {
            while (true) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println(selectionKeys.size() + " size");

                // 相当于内核里调用 epoll_wait(fd4)，如果是select，poll，就调用select(fd4)/poll(fd4)
                // select 可以带超时时间，0的话就是阻塞在这个调用，可以在别的地方调用 selector.wakeup()唤醒
                while (selector.select() > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys(); // 返回所有有状态的fd

                    // 只是返回所有的fd的状态，接下来还是要程序自己去处理 r/w，只是相对于 nio 来说只需要调用一次系统调用，nio是自己去调用内核函数检查状态
                    Iterator<SelectionKey> iter = keys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove(); // 为什么要移除呢，因为后边还在register进去，如果不移除，每次遍历都会重复遍历
                        if (key.isAcceptable()) { // 语义上是要接受新的连接，会返回fd，那这个新的fd怎么办，select/poll，因为他们在内核里没有空间，那么会在jvm里保存跟之前listen得到的fd一起，epoll的话就再次注册到状态链表里
                            handleAcceptable(key);
                        } else if (key.isReadable()) {
                            handleReadable(key);
                        } else if (key.isWritable()) {
                            handleWritable(key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写事件，只要 send-queue 有空间，就会返回这个事件
     * 我们程序员要明白，我们什么时候写，不能依赖系统的send-queue有没有空间，而是：
     * 1.我们准备好写什么了
     * 2.才关心send-queue有没有空间
     * 3.因此如果一开始就注册写事件，会进入死循环
     *
     * @param key
     */
    private void handleWritable(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer bb = ByteBuffer.allocateDirect(288);
        bb.put("recieve".getBytes(StandardCharsets.UTF_8));
        try {
            client.write(bb);
            key.interestOps(SelectionKey.OP_READ); // 写完了，这个 channel 改成对读事件感兴趣
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleReadable(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        while (true) {
            try {
                read = client.read(buffer);
                if (read > 0) { // 有数据，就反转读出来
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        char[] data = new char[read];
                        System.out.println("读取到：" + new String(data, 0, read));
                    }
                    key.interestOps(SelectionKey.OP_WRITE); // 表示这个channel 接下来对写事件感兴趣
                } else if (read == 0) { // 没有数据进来，那这次的readable的key就不处理了
                    break;
                } else { // 没有客户端连进来
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAcceptable(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept(); // 目的是调用accept接受客户端，返回新的fd
            client.configureBlocking(false);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8092);

            // select/poll 会在jvm里开辟一个数组保存返回的fd
            // epoll，相当于调用 epoll_ctl(fd3,add,fd7
            client.register(selector, SelectionKey.OP_READ, byteBuffer);
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SocketMultiplexingSingleThread smst = new SocketMultiplexingSingleThread();
        smst.start();
    }
}
