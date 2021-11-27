package com.faith.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class SelectorThread implements Runnable {

    Selector selector = null;
    LinkedBlockingQueue<Channel> lbq = new LinkedBlockingQueue<>();
    SelectorThreadGroup stg;

    public SelectorThread(SelectorThreadGroup stg) {
        this.stg = stg;
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        // loop
        while (true) {
            try {
                // 1 select()
                // 这里如果没有事件进来，会阻塞，需要在另外的地方再调用这个selector的wakeup，但是注意，wakeup之后只能进入下一轮循环，所以阻塞再前一轮循环里的事件是进不来的
//                System.out.println(Thread.currentThread().getName() + " before: " + selector.keys().size());
                int nums = selector.select();
//                System.out.println(Thread.currentThread().getName() + " after: " + selector.keys().size());
                // 2
                // 有事件了处理selectKeys
                if (nums > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = keys.iterator();
                    while (iter.hasNext()) { // 线性处理keys
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        } else if (key.isWritable()) {

                        }
                    }
                }
                // 没有事件怎么办？传进来这个selector，wakeup起来
                // 3 ,这时候已经wakeup了，那就取出来channel来注册
                if (!lbq.isEmpty()) {
                    Channel ch = lbq.take();
                    if (ch instanceof ServerSocketChannel) {
                        ServerSocketChannel server = (ServerSocketChannel) ch;
                        server.register(selector, SelectionKey.OP_ACCEPT);
                        System.out.println(Thread.currentThread().getName() + " listen ...");
                    } else if (ch instanceof SocketChannel) {
                        SocketChannel client = (SocketChannel) ch;
                        ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                        client.register(selector, SelectionKey.OP_READ, buffer);
                        System.out.println(Thread.currentThread().getName() + " client ...");
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readHandler(SelectionKey key) {
        System.out.println(Thread.currentThread().getName() + " read...");
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel client = (SocketChannel) key.channel();
        buffer.clear();
        while (true) {
            try {
                int num = client.read(buffer);
                if (num > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (num == 0) {
                    break;
                } else { // 客户端断开
                    System.out.println("client: " + client.getRemoteAddress() + " closed...");
                    key.cancel();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void acceptHandler(SelectionKey key) {
        System.out.println("accept handler...");
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            // 选择一个selector进行注册
            stg.nextSelectorV2(client);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
