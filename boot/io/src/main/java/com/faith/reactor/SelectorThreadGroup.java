package com.faith.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectorThreadGroup {

    SelectorThread[] sts;
    ServerSocketChannel server = null;

    AtomicInteger xid = new AtomicInteger(0);

    public SelectorThreadGroup(int num) {
        sts = new SelectorThread[num];

        for (int i = 0; i < num; i++) {
            sts[i] = new SelectorThread(this);
            new Thread(sts[i]).start();
        }
    }

    public void bind(int port) {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // 注册到那个selector 呢？
            nextSelector(server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 无论是serverSocketChannel还是SocketChannel，都可以复用
     *
     * @param channel
     */
    public void nextSelector(Channel channel) {

        SelectorThread st = next();
        // 通过队列传递消息，这时候还是阻塞的
        st.lbq.add(channel);
        // 所以要wakeup(),让对应的线程自己再打断后进行注册
        st.selector.wakeup();
        // channel 可能是server，可能是client
//        ServerSocketChannel s = (ServerSocketChannel) channel;
//        s.register(st.selector, SelectionKey.OP_ACCEPT); // 因为初始化sts的时候再run方法里就 select()，阻塞了，所以这里会阻塞，不能这么用，只能再run的第三步里wakeup（），因为那里是线性的
    }

    /**
     * 用轮询算法取,轮询很尴尬，肯定有一个线程是同时accept和处理r/w
     *
     * @return
     */
    private SelectorThread next() {
        int index = xid.incrementAndGet() % sts.length;
        return sts[index];
    }
}
